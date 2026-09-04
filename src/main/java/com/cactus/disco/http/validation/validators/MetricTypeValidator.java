package com.cactus.disco.http.validation.validators;

import com.cactus.disco.dto.MetricType;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class MetricTypeValidator implements ParamValidator {

    private final String INVALID_METRIC_TYPE_PROVIDED = "Invalid metric type provided: %s, supported types are " +
            "Max, Min, Average and Sum";

    private final String NO_METRIC_TYPE_PROVIDED = "No metric type was provided please provide one of the following Max, Min, Average and Sum";

    @Override
    public String validate(Map<String, String> input) {

        String metricTypesParamValue = input.getOrDefault("metricType", null);

        if (Objects.isNull(metricTypesParamValue)) {
            return NO_METRIC_TYPE_PROVIDED;
        }

        List<String> metricTypes = List.of(metricTypesParamValue.strip().split(","));
        EnumSet<MetricType> validMetricTypes = EnumSet.of(MetricType.MAX, MetricType.AVERAGE, MetricType.MIN, MetricType.SUM);

        List<String> invalidMetrics = metricTypes.stream()
                .filter(
                        it -> validMetricTypes.stream().noneMatch(valid -> valid.name().equalsIgnoreCase(it))
                ).toList();

        if (CollectionUtils.isNotEmpty(invalidMetrics)) {
            String invalidMetricsProvided = String.join(", ", invalidMetrics);

            return generateErrorMessage(INVALID_METRIC_TYPE_PROVIDED, invalidMetricsProvided);
        }

        return null;
    }
}
