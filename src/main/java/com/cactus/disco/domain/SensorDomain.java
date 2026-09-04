package com.cactus.disco.domain;

import com.cactus.disco.dto.outbound.MetricResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class SensorDomain {
    private Long sensorId;
    List<MetricResult> metrics;
    Date from;
    Date to;
}