package com.valverde.buschecker.service;

import com.valverde.buschecker.entity.Driver;
import com.valverde.buschecker.entity.User;
import com.valverde.buschecker.repository.UserRepository;
import com.valverde.buschecker.web.dto.DriverDTO;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@CommonsLog
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DriverService driverService;


    public User getUser(String username) throws Exception {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new Exception("User with username: "+username+" not found");

        return user;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public Boolean existUser(String username) {
        try {
            getUser(username);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<DriverDTO> getOtherDrivers(String username) throws Exception {
        User user = getUser(username);
        return driverService.getOtherDriversFromUser(user);
    }

    public void saveDriversOnUser(List<DriverDTO> drivers, String username) throws Exception {
        User user = getUser(username);

        List<Driver> chosenDrivers = driverService.getChosenDrivers(drivers);
        List<Driver> userDrivers = user.getDrivers();
        userDrivers.addAll(chosenDrivers);
        user.setDrivers(userDrivers);
        saveUser(user);
    }

    public void deleteDriverFromUser(DriverDTO driverDTO, String username) throws Exception {
        Driver driver = driverService.getDriverFromDTO(driverDTO);
        if (driver == null)
            throw new Exception("Driver with lastname: "+driverDTO.getLastname()+" not found.");

        User user = getUser(username);
        List<Driver> drivers = user.getDrivers();
        drivers.remove(driver);
        user.setDrivers(drivers);
        saveUser(user);
    }
}