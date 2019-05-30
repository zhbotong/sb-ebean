package cc.shallow.sbebean.config;

import cc.shallow.sbebean.interceptor.AuthHandlerInterceptor;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    private static String DATETIME_FORMART = "yyyy-MM-dd HH:mm:ss";

    private static String DATE_FORMART = "yyyy-MM-dd";

    private static String TIME_FORMART = "HH:mm:ss";

    /**
     * 拦截器
     * excludePath 不需要拦截的url
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        var excludePath = Arrays.asList("/login","ww");
        registry
                .addInterceptor(new AuthHandlerInterceptor())
                .excludePathPatterns(excludePath);
    }


    /**
     * 全局日期格式转化器（jackson-jsr310会自动注册）
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class,new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIME_FORMART)));
        javaTimeModule.addSerializer(LocalDate.class,new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_FORMART)));
        javaTimeModule.addSerializer(LocalTime.class,new LocalTimeSerializer(DateTimeFormatter.ofPattern(TIME_FORMART)));

        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
                .indentOutput(true)
                .simpleDateFormat(DATETIME_FORMART)
                .modulesToInstall(new ParameterNamesModule())
                .modulesToInstall(javaTimeModule);
        converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
    }

    /**
     * 跨域
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods("*");
    }
}
