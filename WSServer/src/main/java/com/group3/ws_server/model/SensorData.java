package com.group3.ws_server.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class SensorData {

    @Id
    private String id;
    private String date_time;
    private String name;
    private Object value;
    private String unit;

}
