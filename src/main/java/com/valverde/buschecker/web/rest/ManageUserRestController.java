package com.valverde.buschecker.web.rest;

import com.valverde.buschecker.entity.User;
import com.valverde.buschecker.service.*;
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
    private UserService2 userService2;

    @Autowired
    private DriverService2 driverService2;

    @Autowired
    private BusService2 busService2;

    @Autowired
    private SitterService sitterService;


    @GetMapping("/getotherbuses/{id}")
    public ResponseEntity<Iterable<BusDTO>> getOtherBuses(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(busService2.getAllOtherBuses(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getotherdrivers/{username}")
    public ResponseEntity<List<DriverDTO>> getOtherDrivers(@PathVariable String username) {
        try {
            List<DriverDTO> drivers = userService2.getOtherDrivers(username);
            return new ResponseEntity<>(drivers, HttpStatus.OK);
        } catch (Exception e) {
            log.error("No user with username: "+username+" found.", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getuserdata/{username}")
    public ResponseEntity<UserDTO> getUserData(@PathVariable String username) {
        try {
            User user = userService2.getUserByUsername(username);
            UserDTO dto = new UserDTO(user);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            log.error("No user with username: "+username+" found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @PostMapping("/savebus")
//    public HttpStatus saveBus(@RequestBody BusDTO bus) {
//        try {
//            busService.saveBusFromDTO(bus);
//            return HttpStatus.OK;
//        } catch (Exception e) {
//            log.error("Could'n save bus: "+bus.toString(), e);
//            return HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//    }

    @PostMapping("/savesitters")
    public HttpStatus saveSitters(@RequestBody BusDTO busDTO) {
        try {
            sitterService.updateSitters(busDTO);
            return HttpStatus.OK;
        } catch (Exception e) {
            log.error("Error while saving sitters to bus: "+busDTO.toString(), e);
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @PostMapping("/savebuses")
    public HttpStatus saveBuses(@RequestBody DriverDTO driverDTO) {
        try {
            busService2.updateBuses(driverDTO);
            return HttpStatus.OK;
        } catch (Exception e) {
            log.error("Error while saving buses to driver: "+driverDTO.toString(), e);
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @PostMapping("/savedrivers")
    public HttpStatus saveDrivers(@RequestBody UserDTO userDTO) {
        try {
            driverService2.updateDrivers(userDTO);
            return HttpStatus.OK;
        } catch (Exception e) {
            log.error("Error while saving drivers to user: "+userDTO.toString(), e);
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}