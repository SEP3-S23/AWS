package com.group3.ws_server.service;

import com.group3.ws_server.repository.WeatherStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WSService {

    @Autowired
    private WeatherStationRepository weatherStationRepository;
    public List<String> getWSList() {
        return weatherStationRepository.findDistinctNames();
    }
}
