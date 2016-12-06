package com.valverde.buschecker.web.rest;

import com.valverde.buschecker.entity.User;
import com.valverde.buschecker.service.BusService;
import com.valverde.buschecker.service.DriverService;
import com.valverde.buschecker.service.UserService;
import com.valverde.buschecker.web.dto.BusDTO;
import com.valverde.buschecker.web.dto.DriverDTO;
import com.valverde.buschecker.web.dto.UserDTO;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CommonsLog
public class ManageUserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private BusService busService;


    @GetMapping("/getotherbuses/{id}")
    public ResponseEntity<Iterable<BusDTO>> getOtherBuses(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(busService.getAllOtherBuses(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getotherdrivers/{username}")
    public ResponseEntity<List<DriverDTO>> getOtherDrivers(@PathVariable String username) {
        try {
            List<DriverDTO> drivers = userService.getOtherDrivers(username);
            return new ResponseEntity<>(drivers, HttpStatus.OK);
        } catch (Exception e) {
            log.error("No user with username: "+username+" found.", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getuserdata/{username}")
    public ResponseEntity<UserDTO> getUserData(@PathVariable String username) {
        try {
            User user = userService.getUserByUsername(username);
            UserDTO dto = new UserDTO(user);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("No user with username: "+username+" found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/saveuser")
    public HttpStatus saveUser(@RequestBody UserDTO userDTO) {
        try {
            userService.updateUser(userDTO);
            log.info("User data saved: "+userDTO.toString());
            return HttpStatus.OK;
        } catch (Exception e) {
            log.error("Problem with saving user: "+userDTO.toString(), e);
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @PostMapping("/savebus")
    public HttpStatus saveBus(@RequestBody BusDTO bus) {
        try {
            busService.saveBusFromDTO(bus);
            return HttpStatus.OK;
        } catch (Exception e) {
            log.error("Could'n save bus: "+bus.toString(), e);
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @PostMapping("deletedriver/{username}")
    public HttpStatus deleteDriverFromUser(@RequestBody DriverDTO driver, @PathVariable String username) {
        try {
            userService.deleteDriverFromUser(driver, username);
            return HttpStatus.OK;
        } catch (Exception e) {
            log.error("Error while removing driver from user: "+username+" "+
                    driver.toString(), e);
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @PostMapping("/savedrivers/{username}")
    public HttpStatus saveDrivers(@RequestBody List<DriverDTO> drivers, @PathVariable String username) {
        try {
            userService.saveDriversOnUser(drivers, username);
            return HttpStatus.OK;
        } catch (Exception e) {
            log.error("Error while saving drivers to user: "+username+" "+
                    drivers.toString(), e);
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}