package com.valverde.buschecker.rest;

import com.valverde.buschecker.dto.AuthDTO;
import com.valverde.buschecker.dto.CheckerDTO;
import com.valverde.buschecker.entity.BusDriver;
import com.valverde.buschecker.entity.User;
import com.valverde.buschecker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
}
