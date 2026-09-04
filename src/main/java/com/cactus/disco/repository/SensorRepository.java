package com.cactus.disco.repository;

import com.cactus.disco.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {

    List<Sensor> findBySensorIdInOrderByTimestampAsc(List<Long> sensorIds);

    List<Sensor> findAllByOrderByTimestampAsc();
}
