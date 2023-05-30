package com.group3.ws_server.service;

import com.group3.ws_server.model.WeatherStation;
import com.group3.ws_server.repository.WeatherStationRepository;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListenerService {

    @Autowired
    private RabbitListenerEndpointRegistry listenerEndpointRegistry;

    @Autowired
    private WeatherStationRepository weatherStationRepository;

    private static List<String> consumers = new ArrayList<>();

    public void addListener(WeatherStation ws) {
        String queueName = ws.getName() + "." + ws.getSensor();
        if (!consumers.contains(queueName)) {
            consumers.add(queueName);
            ((AbstractMessageListenerContainer) listenerEndpointRegistry.getListenerContainer("listener"))
                    .addQueueNames(queueName);
        }
    }

    public void removeListener(String name) {
        List<String> toDelete = consumers.stream().filter((String queueName) -> queueName.startsWith(name)).toList();
        for (String queueName : toDelete) {
            consumers.remove(queueName);
            ((AbstractMessageListenerContainer) listenerEndpointRegistry.getListenerContainer("listener"))
                    .removeQueueNames(queueName);
        }
    }

    public void loadListener() {
        List<WeatherStation> allSensors = weatherStationRepository.findAll();
        for (WeatherStation ws : allSensors) {
            addListener(ws);
        }
    }
}
