package com.valverde.buschecker.web.rest;

import com.valverde.buschecker.web.dto.CheckerDTO;
import com.valverde.buschecker.entity.BusDriver;
import com.valverde.buschecker.entity.Sitter;
import com.valverde.buschecker.entity.User;
import com.valverde.buschecker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CheckerRestController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/secured/rest/checkerdata", method = RequestMethod.POST)
    public CheckerDTO getCheckerData(@RequestBody String username) {
        User user = userService.getUser(username);
        BusDriver bus = user.getBuses().get(0);
        return new CheckerDTO(bus, user.getNotificationBetweenEventDays());
    }

    @RequestMapping(value = "/secured/rest/save", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> saveData(@RequestBody CheckerDTO dto) {
        Map<String, String> map = new HashMap<>();
        try {
            User user = userService.getUser(dto.getLoggedUser());
            user = setUserFields(user, dto);
            userService.saveUser(user);
            map.put("response", "ok");
        } catch (Exception e) {
            map.put("response", "error");
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    private User setUserFields(User user, CheckerDTO dto) {
        user.setNotificationBetweenEventDays(dto.getNotificationBetweenEventDays());

        BusDriver busDriver = user.getBuses().get(0);
        busDriver.setFirstname(dto.getFirstname());
        busDriver.setLastname(dto.getLastname());
        busDriver.setSideNumber(dto.getSideNumber());
        busDriver.setBusName(dto.getBusName());
        busDriver.setPhoneNumber(dto.getPhoneNumber());
        busDriver.setRejestrNumber(dto.getRejestrNumber());
        busDriver.setNumberOfSeats(dto.getNumberOfSeats());
        busDriver.setExtinguisherReviewDate(dto.getExtinguisherReviewDate());
        busDriver.setInsuranceDate(dto.getInsuranceDate());
        busDriver.setTechnicalReviewDate(dto.getTechnicalReviewDate());
        busDriver.setTachographReviewDate(dto.getTachographReviewDate());
        busDriver.setLiftReviewDate(dto.getLiftReviewDate());

        List<Sitter> sitters = busDriver.getSitters();
        int counter = 0;
        if (sitters.size() == dto.getSitters().size()) {
            for (Sitter sitter : sitters) {
                sitter.setBusDriver(busDriver);
                sitter.setFirstname(dto.getSitters().get(counter).getFirstname());
                sitter.setLastname(dto.getSitters().get(counter++).getLastname());
            }
        }
        return user;
    }
}