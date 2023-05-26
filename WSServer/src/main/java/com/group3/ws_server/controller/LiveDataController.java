package com.group3.ws_server.controller;

import com.group3.ws_server.service.RabbitMQListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.beans.PropertyChangeEvent;
import java.io.IOException;

import com.google.gson.Gson;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/ws")
public class LiveDataController {

    @Autowired
    RabbitMQListener rabbitMQListener;

    private final List<SseEmitter> emitters = new ArrayList<>();
    ExecutorService executor = null;

    @GetMapping(value= "/{name}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter sseEndpoint(@PathVariable String name) {
        SseEmitter emitter = new SseEmitter();
        emitters.add(emitter);

        // Remove the emitter when it is completed or times out
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onError((Throwable e) -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));

        if (executor == null) {
            executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                rabbitMQListener.addListener(name, this::sendMessage);
            });
        }

        return emitter;
    }

    private void sendMessage(PropertyChangeEvent evt) {
        Gson gson = new Gson();
        String data = gson.toJson(evt.getNewValue());

        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(data);
            } catch (IOException e) {
                emitter.completeWithError(e);
            }
        }
    }
}
