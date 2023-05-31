package com.group3.ws_server.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.sql.Date;

@Data
public class SensorData implements Serializable {

    @Id
    private String id;
    private long date_time;
    private String name;
    private Object value;
    private String unit;
    private String wsName;

}
