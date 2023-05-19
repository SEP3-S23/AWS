package com.group3.ws_server.repository;

import com.group3.ws_server.configuration.MongoTemplateLoader;
import com.group3.ws_server.model.SensorData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class SensorDataRepository {

    private MongoTemplateLoader mongoTemplateLoader;

    @Autowired
    public SensorDataRepository(MongoTemplateLoader mongoTemplateLoader) {
        this.mongoTemplateLoader = mongoTemplateLoader;
    }

    public void insert(SensorData data) {
        synchronized(this) {
            this.mongoTemplateLoader.get(data.getWsName()).insert(data, data.getName());
        }
    }
}
