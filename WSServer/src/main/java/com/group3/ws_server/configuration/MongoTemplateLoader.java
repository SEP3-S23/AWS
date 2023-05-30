package com.group3.ws_server.configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import java.util.HashMap;
import java.util.Map;

public class MongoTemplateLoader {

    @Value("${spring.data.mongodb.port}")
    String port;

    @Value("${spring.data.mongodb.host}")
    String host;

    @Value("${spring.data.mongodb.username}")
    String username;

    @Value("${spring.data.mongodb.password}")
    String password;

    private Map<String, MongoTemplate> mongoTemplates = new HashMap();


    public MongoTemplate get(String dbName) {
        MongoTemplate mongoTemplate = mongoTemplates.get(dbName);

        if (mongoTemplate == null) {
            MongoClient mongoClient = MongoClients.create("mongodb://" + username + ":" + password + "@" + host + ":" + port);
            MongoDatabaseFactory dbFactory = new SimpleMongoClientDatabaseFactory(mongoClient, dbName);
            mongoTemplate = new MongoTemplate(dbFactory);
            mongoTemplates.put(dbName, mongoTemplate);
        }

        return mongoTemplate;
    }

}
