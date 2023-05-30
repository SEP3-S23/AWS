package com.group3.ws_server.controller;

import com.google.gson.Gson;
import com.group3.ws_server.dto.HistoricalDataRequest;
import com.group3.ws_server.service.HistoricalDataService;
import com.group3.ws_server.service.RabbitMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/historical")
public class HistoricalDataController {

    @Autowired
    private HistoricalDataService historicalDataService;

    @PostMapping("/{wsName}/{name}")
    public ResponseEntity<?> getHistoricalData(
            @PathVariable("wsName") String wsName,
            @PathVariable("name") String name,
            @RequestBody HistoricalDataRequest historicalDataRequest) {

        return ResponseEntity.ok(
                historicalDataService.getData(
                        historicalDataRequest.getStartDate(),
                        historicalDataRequest.getEndDate(),
                        wsName,
                        name)
        );
    }

}
