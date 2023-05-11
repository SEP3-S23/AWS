package com.group3.collector;

import com.group3.collector.service.ListenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class CollectorApplication {

	@Autowired
	ListenerService listenerService;

	public static void main(String[] args) {
		SpringApplication.run(CollectorApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		listenerService.addListeners();
	}

}
