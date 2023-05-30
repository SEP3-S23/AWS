package com.group3.ws_server.service;

import com.group3.ws_server.model.WeatherStation;
import com.group3.ws_server.repository.WeatherStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherStationService {

    @Autowired
    private WeatherStationRepository weatherStationRepository;

    @Autowired
    private ListenerService listenerService;

    public void addWS(WeatherStation ws) {
        listenerService.addListener(ws);
        weatherStationRepository.saveAndFlush(ws);
    }

    public void deleteWS(String name) {
        listenerService.removeListener(name);
        weatherStationRepository.deleteAllByName(name);
    }
}
