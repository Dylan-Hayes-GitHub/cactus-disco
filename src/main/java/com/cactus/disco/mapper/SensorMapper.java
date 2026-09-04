package com.cactus.disco.mapper;


//Ideally map struct would be used but in the essence of time will use a normal mapper class with static methods

import com.cactus.disco.domain.SensorDomain;
import com.cactus.disco.dto.Metric;
import com.cactus.disco.dto.MetricType;
import com.cactus.disco.dto.outbound.MetricResult;
import com.cactus.disco.entity.Sensor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SensorMapper {


    /**
     * This maps to a sensor domain object {@link MetricType} and {@link Metric } determine what value to add to metricResultsList
     * @param sensor
     * @param toDate
     * @param fromDate
     * @param metricType
     * @param metric
     * @return
     */
    public static SensorDomain toSensorDomain(Sensor sensor, Date toDate, Date fromDate,
                                              MetricType metricType, Metric metric, Integer metricValue) {


        List<MetricResult> metricResultList = new ArrayList<>();

        MetricResult metricResult = MetricResult.builder()
                .metric(metric)
                .metricType(metricType)
                .metricValue(metricValue)
                .build();

        metricResultList.add(metricResult);


        return SensorDomain
                .builder()
                .sensorId(sensor.getSensorId())
                .metrics(metricResultList)
                .to(toDate)
                .from(fromDate)
                .build();

    }

}
