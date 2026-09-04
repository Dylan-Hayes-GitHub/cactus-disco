package com.cactus.disco.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity(name = "Sensor")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long sensorId;

    private Integer temperature;
    private Integer humidity;
    private Integer windspeed;
    private Date timestamp;

}
