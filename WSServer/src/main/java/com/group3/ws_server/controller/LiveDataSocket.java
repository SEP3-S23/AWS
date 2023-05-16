package com.group3.ws_server.controller;

import com.group3.ws_server.model.SensorData;
import com.group3.ws_server.service.RabbitMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;
import com.google.gson.Gson;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LiveDataSocket {

    @Autowired
    RabbitMQListener rabbitMQListener;

    private ServerSocket serverSocket;

    public void start() {
        try {
            serverSocket = new ServerSocket(8100);
            System.out.println("Server socket started on port 8100.");

            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();

                    System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

                    new Thread(new SocketHandler(clientSocket)).start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
