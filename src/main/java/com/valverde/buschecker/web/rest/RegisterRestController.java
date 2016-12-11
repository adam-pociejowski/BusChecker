package com.valverde.buschecker.web.rest;

import com.valverde.buschecker.entity.User;
import com.valverde.buschecker.service.UserService;
import com.valverde.buschecker.service.UserService2;
import com.valverde.buschecker.web.dto.RegisterDTO;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@RestController
@CommonsLog
public class RegisterRestController {

    @Autowired
    private UserService2 userService2;

    @RequestMapping(value = "/registerUser", method = RequestMethod.POST)
    public ResponseEntity<List<String>> register(@RequestBody RegisterDTO dto) {
        List<String> errors = new ArrayList<>();
        if (!userService2.existUser(dto.getUsername())) {
            User newUser = getUserFromRegisterDTO(dto);
            userService2.save(newUser);
        } else {
            String errorMessage = "Użytkownik o loginie "+dto.getUsername()+" już istnieje.";
            errors.add(errorMessage);
            log.info(errorMessage);
            return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private User getUserFromRegisterDTO(RegisterDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        return user;
    }
}