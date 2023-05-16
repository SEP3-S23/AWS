package com.group3.ws_server.controller;

import com.google.gson.Gson;
import com.group3.ws_server.model.SensorData;
import com.group3.ws_server.service.RabbitMQListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SocketHandler implements Runnable {

    @Autowired
    RabbitMQListener rabbitMQListener;

    private Socket clientSocket;

    private String ws;
    private PropertyChangeListener listener;

    public SocketHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        System.out.println("start thread");

        rabbitMQListener.addListener("ws1", (PropertyChangeEvent evt) -> {
            System.out.println("fired");
        });

        try {
            InputStream inputStream = clientSocket.getInputStream();

            if (inputStream != null) {
                String ws = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

                if (ws.equals("close")) {
                    System.out.println("Client close the connection");
                    closeSocket(clientSocket);
                } else {
                    System.out.println("Client connected to: " + ws);
                    this.ws = ws;

                    this.listener = rabbitMQListener.addListener(ws, (PropertyChangeEvent evt) -> {
                                try {
                                    this.sendData(clientSocket, (SensorData) evt.getNewValue());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                    );
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendData(Socket clientSocket, SensorData data) throws IOException {
        //OutputStream outputStream = clientSocket.getOutputStream();
        Gson gson = new Gson();
        String json = gson.toJson(data);
        System.out.println(json);
        //outputStream.write(json.getBytes());
        //outputStream.flush();
    }

    public void closeSocket(Socket clientSocket) throws IOException {

        rabbitMQListener.removeListener(this.ws, this.listener);
        clientSocket.close();
        Thread.currentThread().interrupt();
    }
}
