package com.group3.ws_server.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MongoDBConfig {

    @Bean
    public MongoTemplateLoader mongoTemplateLoader() {
        return new MongoTemplateLoader();
    }
}


