package com.cactus.disco.controller;


import com.cactus.disco.dto.inbound.SensorDataDTO;
import com.cactus.disco.dto.outbound.MetricResult;
import com.cactus.disco.dto.outbound.SensorMetricDTO;
import com.cactus.disco.entity.Sensor;
import com.cactus.disco.http.validation.GetRequestValidator;
import com.cactus.disco.services.SensorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*") //Ideally a cors filter exists but this is just a poc
@RequestMapping("/api/v1/Sensor")
public class SensorController {

    private final SensorService sensorService;
    private final GetRequestValidator getRequestValidator;

    public SensorController(SensorService sensorService, GetRequestValidator getRequestValidator) {
        this.sensorService = sensorService;
        this.getRequestValidator = getRequestValidator;
    }

    @GetMapping
    public ResponseEntity<List<SensorMetricDTO>> getMetricForAllSensors(@RequestParam Map<String, String> allParams) {
        getRequestValidator.validateGetAllSensors(allParams);
        List<SensorMetricDTO> sensorMetricDTOS = sensorService.findAllSensors(allParams);

        return ResponseEntity.ok().body(sensorMetricDTOS);
    }

    @GetMapping("/{sensorId}")
    public ResponseEntity<List<SensorMetricDTO>> getMetricForSensor(@PathVariable Long sensorId, @RequestParam Map<String, String> allParams) {

        allParams.put("sensorId", String.valueOf(sensorId));
        getRequestValidator.validateGetSensorById(allParams);

        List<SensorMetricDTO> sensorMetricDTOS = sensorService.findSensorById(allParams);

        return ResponseEntity.ok(sensorMetricDTOS);
    }

    @PostMapping
    public void sensorDataUpdate(@RequestBody SensorDataDTO sensorDataDTO) {


        //Ideally i would do validations here again but in the essence of time it will be skipped

        sensorService.save(sensorDataDTO);
    }

}
