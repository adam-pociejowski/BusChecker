package com.valverde.buschecker.repository;

import com.valverde.buschecker.entity.Driver;
import org.springframework.data.repository.CrudRepository;

public interface DriverRepository extends CrudRepository<Driver, Long> {

    Driver findByFirstnameAndLastname(String firstname, String lastname);

}