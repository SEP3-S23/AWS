package com.group3.ws_server.service;

import com.group3.ws_server.model.SensorData;
import com.group3.ws_server.repository.SensorDataRepository;
import com.group3.ws_server.util.Subject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

@Service
public class RabbitMQListener implements Subject {

    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    @Autowired
    SensorDataRepository sensorDataRepository;

    @RabbitListener(id="listener")
    public void receiveMessage(SensorData data) {
        System.out.println("Consuming Message - " + data);
        sensorDataRepository.insert(data);
        support.firePropertyChange(data.getWsName(), null, data);
    }

    @Override
    public PropertyChangeListener addListener(String eventName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(eventName, listener);
        return listener;
    }

    @Override
    public void removeListener(String eventName, PropertyChangeListener listener) {
        support.removePropertyChangeListener(eventName, listener);
    }

}