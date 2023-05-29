package com.group3.ws_server;

import com.group3.ws_server.service.ListenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class WSServer {

	@Autowired
	ListenerService listenerService;

	public static void main(String[] args) {
		SpringApplication.run(WSServer.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		listenerService.addListeners();
	}

}
