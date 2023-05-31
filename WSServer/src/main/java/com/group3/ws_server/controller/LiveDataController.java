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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/ws")
public class LiveDataController {

    @Autowired
    RabbitMQListener rabbitMQListener;

    private final Map<String, List<SseEmitter>> emitters = new HashMap<>();
    private final Map<String, ExecutorService> executors = new HashMap<>();

    @GetMapping(value= "/{name}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter sseEndpoint(@PathVariable String name) {
        SseEmitter emitter = new SseEmitter();
        List<SseEmitter> _emitters = emitters.get(name);
        if (_emitters == null) {
            _emitters = new ArrayList<>();
            emitters.put(name, _emitters);
        }
        _emitters.add(emitter);

        // Remove the emitter when it is completed or times out
        emitter.onCompletion(() -> emitters.get(name).remove(emitter));
        emitter.onError((Throwable e) -> emitters.get(name).remove(emitter));
        emitter.onTimeout(() -> emitters.get(name).remove(emitter));

        ExecutorService _executor = executors.get(name);

        if (_executor == null) {
            _executor = Executors.newSingleThreadExecutor();
            _executor.execute(() -> {
                rabbitMQListener.addListener(name, (PropertyChangeEvent evt) -> this.sendMessage(evt, name));
            });
            executors.put(name, _executor);
        }

        return emitter;
    }

    private void sendMessage(PropertyChangeEvent evt, String name) {
        Gson gson = new Gson();
        String data = gson.toJson(evt.getNewValue());

        List<SseEmitter> _emitters = emitters.get(name);
        if (_emitters != null) {
            for (SseEmitter emitter : _emitters) {
                try {
                    emitter.send(data);
                } catch (IOException e) {
                    emitter.completeWithError(e);
                }
            }
        }
    }
}
