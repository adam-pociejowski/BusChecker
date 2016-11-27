package com.valverde.buschecker.service;

import com.valverde.buschecker.entity.Driver;
import com.valverde.buschecker.entity.User;
import com.valverde.buschecker.repository.DriverRepository;
import com.valverde.buschecker.web.dto.DriverDTO;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@CommonsLog
@Transactional
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    public void saveNewDriver(DriverDTO driverDTO) throws Exception {
        Driver newDriver = convertToDriver(driverDTO);
        driverRepository.save(newDriver);
    }

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

    private Driver convertToDriver(DriverDTO driverDTO) {
        Driver newDriver = new Driver();
        newDriver.setFirstname(driverDTO.getFirstname());
        newDriver.setLastname(driverDTO.getLastname());
        newDriver.setEmail(driverDTO.getEmail());
        newDriver.setNotificationBefore(driverDTO.getNotificationBefore());
        newDriver.setPhoneNumber(driverDTO.getPhoneNumber());
        return newDriver;
    }
}