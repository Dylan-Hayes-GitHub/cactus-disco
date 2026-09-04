package com.cactus.disco.dto.inbound;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
@Builder
public class SensorDataDTO {

    private Long sensorId;
    private Integer temperature;
    private Integer humidity;
    private Integer windspeed;
    private Date timestamp;

}
