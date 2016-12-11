package com.valverde.buschecker.service;

import com.valverde.buschecker.entity.Driver;
import com.valverde.buschecker.entity.User;
import com.valverde.buschecker.repository.DriverRepository;
import com.valverde.buschecker.util.UserUtils;
import com.valverde.buschecker.web.dto.DriverDTO;
import com.valverde.buschecker.web.dto.UserDTO;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import static com.valverde.buschecker.util.DriverUtils.removeDuplicateBuses;

@Service
@CommonsLog
public class DriverService {

    @Autowired
    private UserService userService;

    @Autowired
    private DriverRepository driverRepository;

    @Transactional
    Driver getDriver(Long id) {
        Driver driver = driverRepository.findOne(id);
        removeDuplicateBuses(driver);
        return driver;
    }

    @Transactional
    public void save(Driver driver) {
        driverRepository.save(driver);
    }

    @Transactional
    public void updateDrivers(UserDTO userDTO) throws Exception {
        User user = userService.getUser(userDTO.getId());
        updateDriversOnUser(user, userDTO);
        userService.save(user);
    }

    List<DriverDTO> getOtherDriversFromUser(User user) {
        Iterable<Driver> allDrivers = driverRepository.findAll();
        List<DriverDTO> otherDriversDTO = new ArrayList<>();
        for (Driver driver : allDrivers)
            if (!user.getDrivers().contains(driver))
                otherDriversDTO.add(new DriverDTO(driver));

        return otherDriversDTO;
    }

    private void updateDriversOnUser(User user, UserDTO userDTO) throws Exception {
        if (userDTO.getDrivers() == null) {
            log.error("UserDTO without drivers list, "+userDTO.getDrivers().toString());
            throw new Exception();
        }
        else {
            removeDeletedDriversFromUser(user, userDTO);
            List<Driver> newDrivers = new ArrayList<>();
            for (DriverDTO driverDTO : userDTO.getDrivers()) {
                Driver driver = UserUtils.getDriverFromUser(user, driverDTO);
                if (driver == null) {
                    driver = new Driver();
                    driver.setBuses(new ArrayList<>());
                    newDrivers.add(driver);
                }
                setFieldsOnDriver(driver, driverDTO);
            }
            List<Driver> drivers = user.getDrivers();
            drivers.addAll(newDrivers);
        }
    }

    private void setFieldsOnDriver(Driver driver, DriverDTO driverDTO) {
        driver.setId(driverDTO.getId());
        driver.setFirstname(driverDTO.getFirstname());
        driver.setLastname(driverDTO.getLastname());
        driver.setEmail(driverDTO.getEmail());
        driver.setNotificationBefore(driverDTO.getNotificationBefore());
        driver.setPhoneNumber(driverDTO.getPhoneNumber());
    }

    private static void removeDeletedDriversFromUser(User user, UserDTO userDTO) {
        for (Iterator<Driver> iterator = user.getDrivers().iterator(); iterator.hasNext();)
            if (!isInDriverDTOs(iterator.next(), userDTO))
                iterator.remove();
    }

    private static boolean isInDriverDTOs(Driver driver, UserDTO userDTO) {
        for (DriverDTO d : userDTO.getDrivers())
            if (d.getId().equals(driver.getId()))
                return true;

        return false;
    }
}