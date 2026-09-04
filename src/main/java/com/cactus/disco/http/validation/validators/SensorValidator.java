package com.cactus.disco.http.validation.validators;

import com.cactus.disco.entity.Sensor;
import com.cactus.disco.services.SensorService;
import org.springframework.stereotype.Component;
import org.springframework.util.NumberUtils;

import java.util.Map;
import java.util.Objects;

@Component
public class SensorValidator implements ParamValidator {

    private final String NO_SENSOR_ID_PROVIDED = "No sensor id provided";
    private final String INVALID_SENSOR_ID_VALUE = "Sensor id must be a numeric value";
    private final String SENSOR_WITH_ID_DOESNT_EXIST = "Sensor with id %s does not exist";

    private final SensorService sensorService;

    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public String validate(Map<String, String> input) {

        String sensorIdValue = input.getOrDefault("sensorId", null);

        if (Objects.isNull(sensorIdValue)) {
            return NO_SENSOR_ID_PROVIDED;
        }

        Long sensorId = null;
        try {
            sensorId = NumberUtils.parseNumber(sensorIdValue, Long.class);
        } catch (NumberFormatException exception) {
            return generateErrorMessage(INVALID_SENSOR_ID_VALUE, sensorIdValue);
        }

        // Its not good doing this check each time as its a call to the DB a nice to have would be a map cache to sensors
        // would improve performance but for now this will do, as each time this flow is invoked you assume n * n number of db calls
        // where n is the number of sensors

        Sensor sensor = sensorService.findSensorById(sensorId);

        if (Objects.isNull(sensor)) {
            return generateErrorMessage(SENSOR_WITH_ID_DOESNT_EXIST, sensorId);
        }

        return null;
    }
}
