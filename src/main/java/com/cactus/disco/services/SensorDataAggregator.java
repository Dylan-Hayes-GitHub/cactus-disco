package com.cactus.disco.services;

import com.cactus.disco.dto.Metric;
import com.cactus.disco.dto.MetricType;
import com.cactus.disco.dto.outbound.SensorMetricDTO;
import com.cactus.disco.entity.Sensor;

import java.util.List;
import java.util.Map;

public interface SensorDataAggregator {

    List<SensorMetricDTO> aggregate(List<Sensor> sensorList, Map<String, String> allParams);

}
