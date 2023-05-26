package com.group3.ws_server.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoricalDataDTO {
    private String name;
    private String wsName;
    private List<Data> data;
    private String unit;
}
