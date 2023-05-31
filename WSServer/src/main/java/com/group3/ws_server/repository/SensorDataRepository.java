package com.group3.ws_server.repository;

import com.group3.ws_server.configuration.MongoTemplateLoader;
import com.group3.ws_server.dto.Data;
import com.group3.ws_server.model.SensorData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.ArrayList;
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
        if (data.size()>0 && data.get(0).getValue() instanceof Number){
            int groupDimension = Math.round(data.size()/100);
            List<Data> dataAggregated = new ArrayList<>();
            double valueSum = Double.valueOf(data.get(0).getValue().toString());
            long dateSum = data.get(0).getDate_time();

            for (int i = 0; i < data.size(); i++) {
                valueSum += Double.valueOf(data.get(i).getValue().toString());
                dateSum += data.get(i).getDate_time();
                if (i % groupDimension == 0) {
                    dataAggregated.add(new Data(valueSum/groupDimension, dateSum/groupDimension));
                     valueSum = 0;
                     dateSum = 0;
                }
            };
            return Map.entry(dataAggregated, data.get(0).getUnit());

        }
            return Map.entry(new ArrayList<>(), "" );
    }
}
