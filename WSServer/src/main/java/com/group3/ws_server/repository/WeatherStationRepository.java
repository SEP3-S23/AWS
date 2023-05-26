package com.group3.ws_server.repository;

import com.group3.ws_server.model.WeatherStation;
import com.group3.ws_server.model.WeatherStationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherStationRepository extends JpaRepository<WeatherStation, WeatherStationId> {

    void deleteAllByName(String name);
}
