package com.cactus.disco.http.validation.config;


import com.cactus.disco.http.validation.validators.DateValidator;
import com.cactus.disco.http.validation.validators.MetricTypeValidator;
import com.cactus.disco.http.validation.validators.MetricValidator;
import com.cactus.disco.http.validation.validators.ParamValidator;
import com.cactus.disco.http.validation.validators.SensorValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ValidationConfig {

    @Bean(name = "getSensorByIdValidators")
    public List<ParamValidator> getSensorBySensorIdValidator(MetricTypeValidator metricTypeValidator,
                                                             MetricValidator metricValidator, DateValidator dateValidator,
                                                             SensorValidator sensorValidator) {
        return List.of(metricTypeValidator, metricValidator, dateValidator, sensorValidator);
    }

    @Bean(name = "getAllSensorValidators")
    public List<ParamValidator> getAllSensorValidators(DateValidator dateValidator) {
        return List.of(dateValidator);
    }

}
