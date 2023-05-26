package com.group3.ws_server.dto;

import com.group3.ws_server.model.WeatherStation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherStationDTO {

    private String name;
    private String sensor;

    public WeatherStation toEntity() {
        return new WeatherStation(name, sensor);
    }
}
