package com.valverde.buschecker.repository;

import com.valverde.buschecker.entity.Bus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BusRepository extends CrudRepository<Bus, Long> {

    @Query(value = "SELECT b FROM Bus b WHERE b.driver.id != :driverId OR b.driver.id IS NULL")
    List<Bus> findOtherBuses(@Param("driverId") Long driverId);
}