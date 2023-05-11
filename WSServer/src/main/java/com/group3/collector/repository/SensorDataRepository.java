package com.group3.collector.repository;

import com.group3.collector.model.SensorData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

@Repository
public class SensorDataRepository {

    private MongoOperations mongoOperations;

    @Autowired
    public SensorDataRepository(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public void insert(SensorData data) {
        this.mongoOperations.insert(data, data.getName());
    }
}
