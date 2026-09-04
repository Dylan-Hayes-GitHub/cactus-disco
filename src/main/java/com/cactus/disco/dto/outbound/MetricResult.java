package com.cactus.disco.dto.outbound;

import com.cactus.disco.dto.Metric;
import com.cactus.disco.dto.MetricType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class MetricResult {
    private Metric metric;
    private MetricType metricType;
    private Integer metricValue;
}
