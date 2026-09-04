package com.cactus.disco.services;

import com.cactus.disco.dto.inbound.SensorDataDTO;
import com.cactus.disco.dto.outbound.SensorMetricDTO;
import com.cactus.disco.entity.Sensor;

import java.util.List;
import java.util.Map;

public interface SensorService {
    List<SensorMetricDTO> findSensorById(Map<String, String> allParams);

    Sensor findSensorById(Long id);


    //On the jpa interface ideally you should possibly limit the rows returned here but for now this is ok
    List<SensorMetricDTO> findAllSensors(Map<String, String> allParams);

    void save(SensorDataDTO sensorDataDTO);
}
