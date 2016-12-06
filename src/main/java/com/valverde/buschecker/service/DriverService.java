package com.valverde.buschecker.service;

import com.valverde.buschecker.entity.Bus;
import com.valverde.buschecker.entity.Driver;
import com.valverde.buschecker.entity.User;
import com.valverde.buschecker.repository.DriverRepository;
import com.valverde.buschecker.util.BusUtils;
import com.valverde.buschecker.web.dto.BusDTO;
import com.valverde.buschecker.web.dto.DriverDTO;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@CommonsLog
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    Driver getDriverFromDTO(DriverDTO dto) {
        return driverRepository.findByFirstnameAndLastname(dto.getFirstname(), dto.getLastname());
    }

    List<DriverDTO> getOtherDriversFromUser(User user) {
        Iterable<Driver> allDrivers = driverRepository.findAll();
        List<DriverDTO> otherDriversDTO = new ArrayList<>();
        for (Driver driver : allDrivers)
            if (!user.getDrivers().contains(driver)) {
                otherDriversDTO.add(new DriverDTO(driver));
            }

        return otherDriversDTO;
    }

    List<Driver> getChosenDrivers(List<DriverDTO> driverDTOs) {
        List<Driver> drivers = new ArrayList<>();
        for (DriverDTO dto : driverDTOs)
            if (dto.getChosen())
                drivers.add(getDriverFromDTO(dto));

        return drivers;
    }

    void setFieldsOnDriver(Driver driver, DriverDTO driverDTO) {
        driver.setFirstname(driverDTO.getFirstname());
        driver.setLastname(driverDTO.getLastname());
        driver.setEmail(driverDTO.getEmail());
        driver.setNotificationBefore(driverDTO.getNotificationBefore());
        driver.setPhoneNumber(driverDTO.getPhoneNumber());
    }

    void setBusesToDriver(Driver driver, DriverDTO driverDTO) {
        for (BusDTO busDTO : driverDTO.getBuses()) {
            Bus bus = getBusFromDriver(driver, busDTO);
            if (bus == null) {
                bus = new Bus();
                bus.setDriver(driver);
                List<Bus> buses = driver.getBuses();
                buses.add(bus);
            }
            BusUtils.setFieldsToBus(bus, busDTO);
            BusUtils.updateSittersInBus(bus, busDTO);
        }
    }

    private Bus getBusFromDriver(Driver driver, BusDTO busDTO) {
        for (Bus bus : driver.getBuses())
            if (bus.getId().equals(busDTO.getId()))
                return bus;

        return null;
    }
}