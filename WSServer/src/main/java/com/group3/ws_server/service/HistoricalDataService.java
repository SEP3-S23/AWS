package com.group3.ws_server.service;

import com.group3.ws_server.dto.Data;
import com.group3.ws_server.dto.HistoricalDataDTO;
import com.group3.ws_server.repository.SensorDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HistoricalDataService {

    @Autowired
    private SensorDataRepository sensorDataRepository;

    public HistoricalDataDTO getData(int startDate, int endDate, String wsName, String name) {
        Map.Entry<List<Data>, String> result = sensorDataRepository.getHistoricalData(wsName, name, startDate, endDate);
        return new HistoricalDataDTO(name, wsName, result.getKey(), result.getValue());
    }
}
