package com.valverde.buschecker.rest;

import com.valverde.buschecker.dto.CheckerDTO;
import com.valverde.buschecker.entity.BusDriver;
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
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
public class CheckerRestController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/secured/rest/checkerdata", method = RequestMethod.POST)
    public CheckerDTO getCheckerData(@RequestBody String username) {
        User user = userService.getUser(username);
        BusDriver bus = user.getBuses().get(0);

        CheckerDTO dto = new CheckerDTO(bus, user.getNotificationBetweenEventDays());
        return dto;
    }

    @RequestMapping(value = "/secured/rest/save", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> saveData(@RequestBody CheckerDTO dto) {
        Map<String, String> map = new HashMap<>();

        try {
            map.put("response", "ok");
        } catch (Exception e) {
            map.put("response", "error");
        }

        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
