package com.group3.ws_server.repository;

import com.group3.ws_server.model.WeatherStation;
import com.group3.ws_server.model.WeatherStationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherStationRepository extends JpaRepository<WeatherStation, WeatherStationId> {

    void deleteAllByName(String name);
    @Query("SELECT DISTINCT ws.name FROM WeatherStation ws")
    List<String> findDistinctNames();

}
