package com.group3.ws_server.controller;

import com.group3.ws_server.service.WSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ws")
public class WSController {

    @Autowired
    private WSService wsService;

    @GetMapping("/list")
    public ResponseEntity<List<String>> getWSList() {
        return ResponseEntity.ok(wsService.getWSList());
    }
}
