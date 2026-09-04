package com.cactus.disco.dto.outbound;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class SensorMetricDTO {

    Long sensorId;
    List<MetricResult> metrics;
    Date from;
    Date to;

}
