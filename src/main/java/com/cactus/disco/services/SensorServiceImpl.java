package com.cactus.disco.services;

import com.cactus.disco.dto.inbound.SensorDataDTO;
import com.cactus.disco.dto.outbound.SensorMetricDTO;
import com.cactus.disco.entity.Sensor;
import com.cactus.disco.repository.SensorRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class SensorServiceImpl implements SensorService {

    private final SensorRepository sensorRepository;
    private final SensorDataAggregator sensorDataAggregator;

    public SensorServiceImpl(SensorRepository sensorRepository, SensorDataAggregator sensorDataAggregator) {
        this.sensorRepository = sensorRepository;
        this.sensorDataAggregator = sensorDataAggregator;
    }



    @Override
    public List<SensorMetricDTO> findSensorById(Map<String, String> allParams) {
        int decision = decideQuery(allParams);

        List<Sensor> sensorList = execute(decision, allParams);

        return sensorDataAggregator.aggregate(sensorList, allParams);
    }

    @Override
    public Sensor findSensorById(Long id) {
        return sensorRepository.findById(id).orElse(null);
    }

    @Override
    public List<SensorMetricDTO> findAllSensors(Map<String, String> allParams) {

        int decision = decideQuery(allParams);

        List<Sensor> sensorList = execute(decision, allParams);

        return sensorDataAggregator.aggregate(sensorList, allParams);
    }

    @Override
    public void save(SensorDataDTO sensorDataDTO) {

        Sensor sensor = Sensor
                .builder()
                .sensorId(sensorDataDTO.getSensorId())
                .humidity(sensorDataDTO.getHumidity())
                .windspeed(sensorDataDTO.getWindspeed())
                .temperature(sensorDataDTO.getTemperature())
                .timestamp(sensorDataDTO.getTimestamp())
                .build();

        sensorRepository.save(sensor);
    }

    /**
     * Simple decision method that determines if we should be getting all sensors or just one, I know something like a
     * jpa criteria object exists that utilises predicates. I'm not too sure how to set them up right but they would be better here.
     */
    private int decideQuery(Map<String, String> allParams) {

        int decision = 0;

        // This wont be always set if you do a get all its expected to be null
        String requestedSensors = allParams.getOrDefault("sensorId", null);

        if (Objects.isNull(requestedSensors)) {
            decision = -1;
        } else {
            decision = 1;
        }

        return decision;
    }

    public List<Sensor> execute(int decision, Map<String, String> allParams) {

        if (decision == -1) {
            return sensorRepository.findAllByOrderByTimestampAsc();
        }

        List<Long> sensorIds = Arrays.stream(
                        allParams.get("sensorId").split(",")
                )
                .map(Long::valueOf)
                .toList();

        return sensorRepository.findBySensorIdInOrderByTimestampAsc(sensorIds);

    }
}
