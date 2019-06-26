package cc.shallow.sbebean.utils.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.time.format.DateTimeFormatter.ISO_INSTANT;

/**
 * json工具类,代码来自vertx-core json
 */
public class Json {
    public static ObjectMapper mapper = new ObjectMapper();
    public static ObjectMapper prettyMapper = new ObjectMapper();

    private static String DATETIME_FORMART = "yyyy-MM-dd HH:mm:ss";

    private static String DATE_FORMART = "yyyy-MM-dd";

    private static String TIME_FORMART = "HH:mm:ss";

    static {
        // Non-standard json but we allow C style comments in our json
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        prettyMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        prettyMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        SimpleModule module = new SimpleModule();
        // custom types
        module.addSerializer(JsonObject.class, new JsonObjectSerializer());
        module.addSerializer(JsonArray.class, new JsonArraySerializer());
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class,new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIME_FORMART)));
        javaTimeModule.addSerializer(LocalDate.class,new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_FORMART)));
        javaTimeModule.addSerializer(LocalTime.class,new LocalTimeSerializer(DateTimeFormatter.ofPattern(TIME_FORMART)));
        List<Module> moduleList = Arrays.asList(module,
                new ParameterNamesModule(),
                new Jdk8Module(),
                javaTimeModule);
        mapper.registerModules(moduleList);
        prettyMapper.registerModules(moduleList);
    }

    /**
     * Encode a POJO to json using the underlying Jackson mapper.
     *
     * @param obj a POJO
     * @return a String containing the json representation of the given POJO.
     * @throws EncodeException if a property cannot be encoded.
     */
    public static String encode(Object obj) throws EncodeException {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new EncodeException("Failed to encode as json: " + e.getMessage());
        }
    }

    /**
     * Encode a POJO to json with pretty indentation, using the underlying Jackson mapper.
     *
     * @param obj a POJO
     * @return a String containing the json representation of the given POJO.
     * @throws EncodeException if a property cannot be encoded.
     */
    public static String encodePrettily(Object obj) throws EncodeException {
        try {
            return prettyMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new EncodeException("Failed to encode as json: " + e.getMessage());
        }
    }

    /**
     * Decode a given json string to a POJO of the given class type.
     * @param str the json string.
     * @param clazz the class to map to.
     * @param <T> the generic type.
     * @return an instance of T
     * @throws DecodeException when there is a parsing or invalid mapping.
     */
    public static <T> T decodeValue(String str, Class<T> clazz) throws DecodeException {
        try {
            return mapper.readValue(str, clazz);
        } catch (Exception e) {
            throw new DecodeException("Failed to decode: " + e.getMessage());
        }
    }

    public static class JsonObjectSerializer extends JsonSerializer<JsonObject> {
        @Override
        public void serialize(JsonObject value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            jgen.writeObject(value.getMap());
        }
    }

    public static class JsonArraySerializer extends JsonSerializer<JsonArray> {
        @Override
        public void serialize(JsonArray value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            jgen.writeObject(value.getList());
        }
    }

    static <T> Stream<T> asStream(Iterator<T> sourceIterator) {
        Iterable<T> iterable = () -> sourceIterator;
        return StreamSupport.stream(iterable.spliterator(), false);
    }
}
