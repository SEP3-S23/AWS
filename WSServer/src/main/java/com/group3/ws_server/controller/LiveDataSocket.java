package com.group3.ws_server.controller;

import com.group3.ws_server.model.SensorData;
import com.group3.ws_server.service.RabbitMQListener;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/ws")
public class LiveDataSocket {

    @Autowired
    RabbitMQListener rabbitMQListener;

    @GetMapping("/{name}")
    public SseEmitter stream(@PathVariable String name) {
        SseEmitter emitter = new SseEmitter();

        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            rabbitMQListener.addListener(name, (PropertyChangeEvent evt) -> {
                try {
                    Gson gson = new Gson();
                    emitter.send(gson.toJson(evt.getNewValue()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        });
        executor.shutdown();
        return emitter;
    }
}
