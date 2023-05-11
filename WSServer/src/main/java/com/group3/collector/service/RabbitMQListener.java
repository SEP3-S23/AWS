package com.group3.collector.service;

import com.group3.collector.model.SensorData;
import com.group3.collector.repository.SensorDataRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQListener {

    @Autowired
    SensorDataRepository sensorDataRepository;

    @RabbitListener(id="listener")
    public void receiveMessage(SensorData data) {
        System.out.println("Consuming Message - " + data);
        sensorDataRepository.insert(data);
    }


}