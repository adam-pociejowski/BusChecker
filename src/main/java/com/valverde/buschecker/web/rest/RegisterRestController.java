package com.valverde.buschecker.web.rest;

import com.valverde.buschecker.entity.User;
import com.valverde.buschecker.service.UserService;
import com.valverde.buschecker.web.dto.RegisterDTO;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@CommonsLog
public class RegisterRestController {

    @PostMapping(value = "/registerUser")
    public ResponseEntity<List<String>> register(@RequestBody RegisterDTO dto) {
        System.out.println("test");
        List<String> errors = new ArrayList<>();
        if (!userService.existUser(dto.getUsername())) {
            User newUser = getUserFromRegisterDTO(dto);
            userService.save(newUser);
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

    @Autowired
    public RegisterRestController(UserService userService) {
        this.userService = userService;
    }

    private final UserService userService;
}