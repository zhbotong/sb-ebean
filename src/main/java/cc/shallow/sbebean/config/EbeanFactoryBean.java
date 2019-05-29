package cc.shallow.sbebean.config;

import io.ebean.EbeanServer;
import io.ebean.EbeanServerFactory;
import io.ebean.config.ServerConfig;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * ebean 注册
 */
@Component
public class EbeanFactoryBean implements FactoryBean<EbeanServer> {
    @Override
    public EbeanServer getObject() throws Exception {
        ServerConfig config = new ServerConfig();
        config.setName("db");
        config.loadFromProperties();
        return EbeanServerFactory.create(config);
    }

    @Override
    public Class<?> getObjectType() {
        return EbeanServer.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
