package com.demo.api.rest.config.spring;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@PropertySource("classpath:mongo.properties")
public class SpringMongoConfig {

    @Value("${mongo.host}")
    private String mongoHost;

    @Value("${mongo.port}")
    private int mongoPort;

    @Value("${mongo.database}")
    private String mongoDatabase;

    @Bean
    public Mongo mongo() throws Exception {
        return new MongoClient(mongoHost, mongoPort);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), mongoDatabase);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
