package com.group3.ws_server.controller;

import com.group3.ws_server.dto.WeatherStationDTO;
import com.group3.ws_server.model.WeatherStation;
import com.group3.ws_server.service.WeatherStationService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class InitWSController {

    @Autowired
    private WeatherStationService weatherStationService;

    @PostMapping("/init")
    public ResponseEntity<HttpStatus> wsInit(@RequestBody WeatherStationDTO ws) {
        weatherStationService.addWS(ws.toEntity());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/init/{name}")
    public ResponseEntity<HttpStatus> wsDelete(@PathVariable String name) {
        weatherStationService.deleteWS(name);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
