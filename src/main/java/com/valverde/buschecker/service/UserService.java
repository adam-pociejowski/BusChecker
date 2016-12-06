package com.valverde.buschecker.service;

import com.valverde.buschecker.entity.Driver;
import com.valverde.buschecker.entity.User;
import com.valverde.buschecker.repository.UserRepository;
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

@Service
@CommonsLog
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DriverService driverService;

    @Transactional
    public User getUserByUsername(String username) throws Exception {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new Exception("User with username: "+username+" not found");

        return user;
    }

    @Transactional
    public User getUserById(Long id) throws Exception {
        User user = userRepository.findOne(id);
        if (user == null)
            throw new Exception("User with id: "+id+" not found");

        return user;
    }

    @Transactional
    public void updateUser(UserDTO userDTO) throws Exception {
        User user = convertToUser(userDTO);
        updateDriversOnUser(user, userDTO);
        userRepository.save(user);
    }

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void saveDriversOnUser(List<DriverDTO> drivers, String username) throws Exception {
        User user = getUserByUsername(username);

        List<Driver> chosenDrivers = driverService.getChosenDrivers(drivers);
        List<Driver> userDrivers = user.getDrivers();
        userDrivers.addAll(chosenDrivers);
        user.setDrivers(userDrivers);
        userRepository.save(user);
    }

    @Transactional
    public void deleteDriverFromUser(DriverDTO driverDTO, String username) throws Exception {
        Driver driver = driverService.getDriverFromDTO(driverDTO);
        if (driver == null)
            throw new Exception("Driver with lastname: "+driverDTO.getLastname()+" not found.");

        User user = getUserByUsername(username);
        List<Driver> drivers = user.getDrivers();
        drivers.remove(driver);
        user.setDrivers(drivers);
        userRepository.save(user);
    }

    public Boolean existUser(String username) {
        try {
            getUserByUsername(username);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<DriverDTO> getOtherDrivers(String username) throws Exception {
        User user = getUserByUsername(username);
        return driverService.getOtherDriversFromUser(user);
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
                    newDrivers.add(driver);
                }
                driverService.setFieldsOnDriver(driver, driverDTO);
                driverService.setBusesToDriver(driver, driverDTO);
            }
            List<Driver> drivers = user.getDrivers();
            drivers.addAll(newDrivers);
        }
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

    private User convertToUser(UserDTO userDTO) {
        User user = userRepository.findOne(userDTO.getId());
        if (user == null) {
            user = new User();
            user.setDrivers(new ArrayList<>());
        } else if (user.getDrivers() == null) {
            user.setDrivers(new ArrayList<>());
        }
        user.setUsername(userDTO.getUsername());
        return user;
    }
}