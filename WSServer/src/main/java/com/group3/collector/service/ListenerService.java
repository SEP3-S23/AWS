package com.group3.collector.service;

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

    @Value("${spring.data.mongodb.database}")
    String exchange;

    @Value("${rabbitmq.queues}")
    String[] queues;

    static List<String> consumers = new ArrayList<>();

    private void addListener(String queue) {
        if (!consumers.contains(queue)) {
            consumers.add(queue);
            ((AbstractMessageListenerContainer) listenerEndpointRegistry.getListenerContainer("listener"))
                    .addQueueNames(exchange + "." + queue);

            System.out.println("Listener crater for queue: " + queue);

        }
    }

    public void addListeners() {
        for (String queue : queues) {
            addListener(queue);
        }
    }


}
