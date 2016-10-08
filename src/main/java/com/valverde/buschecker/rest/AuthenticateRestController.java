package com.valverde.buschecker.rest;

import com.valverde.buschecker.dto.AuthDTO;
import com.valverde.buschecker.dto.CheckerDTO;
import com.valverde.buschecker.dto.RegisterDTO;
import com.valverde.buschecker.dto.SitterDTO;
import com.valverde.buschecker.entity.BusDriver;
import com.valverde.buschecker.entity.Sitter;
import com.valverde.buschecker.entity.User;
import com.valverde.buschecker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class AuthenticateRestController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody AuthDTO auth) {
        User user = userService.getUser(auth.getUsername());
        if (user != null) {
            String password = user.getPassword();
            if (password.equals(auth.getPassword())) {
                Map<String, String> map = new HashMap<>();
                map.put("username", user.getUsername());
                return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
            }
        }
        return null;
    }

    @RequestMapping(value = "/registerUser", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterDTO dto) {
        User registeredUser = getUserFromRegisterDTO(dto);
        Boolean success = userService.saveUser(registeredUser);
        if (success) {
            System.out.println("success");

        }
        else {
            System.out.println("fail");
        }

        return null;
    }

    private User getUserFromRegisterDTO(RegisterDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setNotificationBetweenEventDays(dto.getNotificationBetweenEventDays());

        BusDriver busDriver = new BusDriver();
        busDriver.setFirstname(dto.getFirstname());
        busDriver.setLastname(dto.getLastname());
        busDriver.setSideNumber(dto.getSideNumber());
        busDriver.setBusName(dto.getBusName());
        busDriver.setRejestrNumber(dto.getRejestrNumber());
        busDriver.setNumberOfSeats(dto.getNumberOfSeats());
        busDriver.setExtinguisherReviewDate(stringToDate(dto.getExtinguisherReviewDate()));
        busDriver.setInsuranceDate(stringToDate(dto.getInsuranceDate()));
        busDriver.setTechnicalReviewDate(stringToDate(dto.getTechnicalReviewDate()));
        busDriver.setTachographReviewDate(stringToDate(dto.getTachographReviewDate()));
        busDriver.setLiftReviewDate(stringToDate(dto.getLiftReviewDate()));

        List<Sitter> sitters = new ArrayList<>();
        for (SitterDTO sitterDTO : dto.getSitters()) {
            Sitter sitter = new Sitter();
            sitter.setBusDriver(busDriver);
            sitter.setFirstname(sitterDTO.getFirstname());
            sitter.setLastname(sitterDTO.getLastname());
            sitters.add(sitter);
        }
        busDriver.setSitters(sitters);
        List<BusDriver> busses = new ArrayList<>();
        busses.add(busDriver);
        user.setBuses(busses);

        return user;
    }

    private Date stringToDate(String dateString) {
        Date date = null;
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }
}
