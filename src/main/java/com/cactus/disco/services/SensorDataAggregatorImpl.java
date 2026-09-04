package com.cactus.disco.services;

import com.cactus.disco.dto.Metric;
import com.cactus.disco.dto.MetricType;
import com.cactus.disco.dto.outbound.MetricResult;
import com.cactus.disco.dto.outbound.SensorMetricDTO;
import com.cactus.disco.entity.Sensor;
import com.cactus.disco.mapper.SensorMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class SensorDataAggregatorImpl implements SensorDataAggregator {


    /**
     * Agrregator function to filter data based on the param inputs ideally should be a domain object rather than passing them down
     * post validation
     * @param sensorList
     * @param allParams
     * @return List<SensorMetricDTO>
     */
    @Override
    public List<SensorMetricDTO> aggregate(List<Sensor> sensorList, Map<String, String> allParams) {

        List<Metric> metrics = Arrays.stream(allParams.get("metric")
                .split(","))
                .map(Metric::valueOf)
                .toList();

        List<MetricType> metricTypes = Arrays.stream(allParams.get("metricType")
                .split(","))
                .map(MetricType::valueOf)
                .toList();

        //Convert sensor list to map of <Long, List<Sensor> to pair the snesorId to its associated metrics

        Map<Long, List<Sensor>> sensorIdToSensorValues = sensorList.stream()
                .collect(Collectors.groupingBy(Sensor::getSensorId));

        List<SensorMetricDTO> sensorMetricDTOS = new ArrayList<>();
        sensorIdToSensorValues.forEach((sensorIdKey, sensors) -> {

            List<MetricResult> metricResultList = new ArrayList<>();
            for (MetricType metricType : metricTypes) {
                for (Metric metric : metrics) {
                    metricResultList.add(generateResult(metric, metricType, sensorList));
                }
            }

            sensorMetricDTOS.add(
                    SensorMetricDTO
                            .builder()
                            .sensorId(sensorIdKey)
                            .metrics(metricResultList)
                            .build()
            );

        });

        return sensorMetricDTOS;
    }

    MetricResult generateResult(Metric metric, MetricType metricType, List<Sensor> sensorList) {
        Integer metricValue = null;

        List<Integer> relevantMetrics = getMetrics(sensorList, metric);

        switch (metricType) {
            case MAX -> {
                metricValue = relevantMetrics
                        .stream()
                        .mapToInt(Integer::intValue)
                        .max()
                        .orElse(0);
            }
            case MIN -> {
                metricValue = relevantMetrics
                        .stream()
                        .mapToInt(Integer::intValue)
                        .min()
                        .orElse(0);
            }
            case SUM -> {
                metricValue = relevantMetrics
                        .stream()
                        .mapToInt(Integer::intValue)
                        .sum();
            }
            case AVERAGE -> {
                metricValue =  (int) relevantMetrics
                        .stream()
                        .mapToInt(Integer::intValue)
                        .average()
                        .orElse(0);
            }
        }

        return MetricResult.builder()
                .metricValue(metricValue)
                .metric(metric)
                .metricType(metricType)
                .build();

    }

    private List<Integer> getMetrics(List<Sensor> sensorList, Metric metric) {
        switch (metric) {
            case HUMIDITY -> {
                return sensorList.stream().map(Sensor::getHumidity).toList();
            }
            case TEMPERATURE -> {
                return sensorList.stream().map(Sensor::getTemperature).toList();
            }

            case WIND_SPEED -> {
                return sensorList.stream().map(Sensor::getWindspeed).toList();
            }
        }
        return List.of();
    }


}
