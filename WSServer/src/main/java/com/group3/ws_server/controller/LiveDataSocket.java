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
public class LiveDataSocket extends Thread {

    @Autowired
    RabbitMQListener rabbitMQListener;

    private ServerSocket serverSocket;

    private Map<Socket, String> clients = new HashMap<>();
    private Map<Socket, PropertyChangeListener> listeners = new HashMap<>();

    public void start() {
        try {
            serverSocket = new ServerSocket(8100);
            System.out.println("Server socket started on port 8100.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

                InputStream inputStream = clientSocket.getInputStream();

                if (inputStream != null) {
                    String ws = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

                    if (ws.equals("close")) {
                        System.out.println("Client close the connection");
                        closeSocket(clientSocket);
                    } else {
                        System.out.println("Client connected to: " + ws);

                        clients.put(clientSocket, ws);

                        listeners.put(clientSocket,
                                rabbitMQListener.addListener(ws, (PropertyChangeEvent evt) -> {
                                            try {
                                                this.sendData(clientSocket, (SensorData) evt.getNewValue());
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }
                                )
                        );
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendData(Socket clientSocket, SensorData data) throws IOException {
        OutputStream outputStream = clientSocket.getOutputStream();
        Gson gson = new Gson();
        String json = gson.toJson(data);
        outputStream.write(json.getBytes());
        outputStream.flush();
    }

    public void closeSocket(Socket clientSocket) throws IOException {

        rabbitMQListener.removeListener(clients.get(clientSocket), listeners.get(clientSocket));
        clients.remove(clientSocket);
        listeners.remove(clientSocket);

        clientSocket.close();
    }
}
