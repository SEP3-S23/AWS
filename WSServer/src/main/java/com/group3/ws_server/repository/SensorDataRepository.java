package com.group3.ws_server.repository;

import com.group3.ws_server.configuration.MongoTemplateLoader;
import com.group3.ws_server.dto.Data;
import com.group3.ws_server.model.SensorData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Repository
public class SensorDataRepository {

    private MongoTemplateLoader mongoTemplateLoader;

    @Autowired
    public SensorDataRepository(MongoTemplateLoader mongoTemplateLoader) {
        this.mongoTemplateLoader = mongoTemplateLoader;
    }

    public void insert(SensorData data) {
        this.mongoTemplateLoader.get(data.getWsName()).insert(data, data.getName());
    }

    public Map.Entry<List<Data>, String> getHistoricalData(String wsName, String name, int startDate, int endDate) {
        Query query = new Query();
        query.addCriteria(Criteria.where("date_time").gte(startDate).lte(endDate));
        List<SensorData> data = this.mongoTemplateLoader.get(wsName).find(query, SensorData.class, name);
        return Map.entry(data.stream().map((SensorData _data) -> new Data(_data.getValue(), _data.getDate_time())).toList(), data.get(0).getUnit());
    }
}
