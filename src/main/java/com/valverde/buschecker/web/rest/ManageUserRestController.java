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
    private UserService userService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private BusService busService;

    @Autowired
    private SitterService sitterService;


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
            busService.updateBuses(driverDTO);
            return HttpStatus.OK;
        } catch (Exception e) {
            log.error("Error while saving buses to driver: "+driverDTO.toString(), e);
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @PostMapping("/savedrivers")
    public HttpStatus saveDrivers(@RequestBody UserDTO userDTO) {
        try {
            driverService.updateDrivers(userDTO);
            return HttpStatus.OK;
        } catch (Exception e) {
            log.error("Error while saving drivers to user: "+userDTO.toString(), e);
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}