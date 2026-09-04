package com.cactus.disco.component

import com.cactus.disco.dto.Metric
import com.cactus.disco.dto.MetricType
import com.cactus.disco.dto.inbound.SensorDataDTO
import com.cactus.disco.dto.outbound.SensorMetricDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.resttestclient.TestRestTemplate
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
class SensorControllerTest extends Specification {

    @Autowired
    TestRestTemplate restTemplate


    def setup() {
        restTemplate.postForEntity(
                "/api/v1/Sensor",
                createDTO(),
                SensorMetricDTO
        )
    }

    //I would want to add a lot more scenarios here for error paths etc just dont have the time.

    def "Successfully can query all sensors"(MetricType metricType ,Metric metric) {

        given: "There is sensor data in the database"


        when: "There is a request for all sensor data and max is requested"
        def response = restTemplate.exchange(
                "/api/v1/Sensor?metric=TEMPERATURE&metricType=MAX",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<SensorMetricDTO>>() {}
        )

        println response.body
        List<SensorMetricDTO> sensorMetricDTOList = response.body

        then: "The result should be successful"
        assert response.statusCode.is2xxSuccessful()

        and: "There should be a max value set"
        sensorMetricDTOList.each
                { sensorMetric ->
                    sensorMetric.metrics.each
                            { metricResult -> assert metricResult.metricType == MetricType.MAX
                            }
                }

        where:
        metricType         | metric
        MetricType.MAX     | Metric.HUMIDITY
        MetricType.MIN     | Metric.TEMPERATURE
        MetricType.AVERAGE | Metric.WIND_SPEED
    }


    SensorDataDTO createDTO() {
        return SensorDataDTO.builder()
                .sensorId(123)
                .humidity(1)
                .temperature(2)
                .windspeed(3)
                .build()

    }

}