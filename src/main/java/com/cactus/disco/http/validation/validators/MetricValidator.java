package com.cactus.disco.http.validation.validators;

import com.cactus.disco.dto.Metric;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class MetricValidator implements ParamValidator {

    private final String INVALID_METRIC_PROVIDED = "Unsupported metric type provided: %s, supported types are " +
            "Temperature, Humidity and Wind speed";

    private final String NO_METRIC_PROVIDED = "No Metric was provided please provide one of the following Temperature, Humidity and Wind speed";

    @Override
    public String validate(Map<String, String> input) {

        String metricsParamValue = input.get("metric");

        if (Objects.isNull(metricsParamValue)) {
            return NO_METRIC_PROVIDED;
        }

        List<String> metrics = List.of(metricsParamValue.split(","));
        EnumSet<Metric> validMetrics = EnumSet.of(Metric.TEMPERATURE, Metric.HUMIDITY, Metric.WIND_SPEED);
        List<String> invalidMetrics = metrics.stream()
                .filter(
                        it -> validMetrics.stream().noneMatch(valid -> valid.name().equalsIgnoreCase(it))
                ).toList();

        if (CollectionUtils.isNotEmpty(invalidMetrics)) {
            String invalidMetricsProvided = String.join(", ", invalidMetrics);

            return generateErrorMessage(INVALID_METRIC_PROVIDED, invalidMetricsProvided);
        }

        return null;
    }
}
