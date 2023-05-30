package com.group3.ws_server.configuration;

import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import jakarta.annotation.PostConstruct;
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

    @Value("${rabbitmq.exchanges}")
    String[] exchanges;

    @Value("${spring.data.mongodb.username}")
    String username;

    @Value("${spring.data.mongodb.password}")
    String password;

    private Map<String, MongoTemplate> mongoTemplates = new HashMap();

    @PostConstruct
    public Map<String, MongoTemplate> mongoTemplates() {
        for (String ws : exchanges) {
            MongoClient mongoClient = MongoClients.create("mongodb://" + username + ":" + password + "@" + host + ":" + port);
            MongoDatabaseFactory dbFactory = new SimpleMongoClientDatabaseFactory(mongoClient, ws);
            MongoTemplate mongoTemplate = new MongoTemplate(dbFactory);
            mongoTemplates.put(ws, mongoTemplate);
        }

        return mongoTemplates;
    }

    public MongoTemplate get(String dbName) {
        return mongoTemplates.get(dbName);
    }

}
