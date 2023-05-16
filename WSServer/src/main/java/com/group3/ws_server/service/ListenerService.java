package com.group3.ws_server.service;

import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListenerService {

    @Autowired
    private RabbitListenerEndpointRegistry listenerEndpointRegistry;

    @Value("${rabbitmq.exchanges}")
    String[] exchanges;

    @Value("${rabbitmq.queues}")
    String[] queues;

    static List<String> consumers = new ArrayList<>();

    private void addListener(String exchange, String queue) {
        String queueName = exchange + "." + queue;
        if (!consumers.contains(queueName)) {
            consumers.add(queueName);
            ((AbstractMessageListenerContainer) listenerEndpointRegistry.getListenerContainer("listener"))
                    .addQueueNames(queueName);
        }
    }

    public void addListeners() {
        for (String exchange : exchanges) {
            System.out.println("exchange -> " + exchange);
            for (String queue : queues) {
                addListener(exchange, queue);
            }
        }
    }
}
