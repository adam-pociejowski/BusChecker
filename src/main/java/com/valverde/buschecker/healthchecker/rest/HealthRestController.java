package com.valverde.buschecker.healthchecker.rest;

import com.valverde.buschecker.healthchecker.dto.HealthDTO;
import com.valverde.buschecker.healthchecker.dto.StatDTO;
import com.valverde.buschecker.repository.UserRepository;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;
import static com.valverde.buschecker.healthchecker.enums.HealthState.*;

@RestController
@CommonsLog
@RequestMapping("/health")
public class HealthRestController {

    private final UserRepository userRepository;

    private final static String APP_NAME = "BusChecker";

    @Autowired
    public HealthRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/status")
    public ResponseEntity<HealthDTO> getHealthPoint() {
        HealthDTO healthDTO = new HealthDTO();
        healthDTO.setAppName(APP_NAME);
        try {
            healthDTO.setState(HEALTHY);
            healthDTO.getStats().add(new StatDTO("USERS_AMOUNT", userRepository.count()));
            return new ResponseEntity<>(healthDTO, HttpStatus.OK);
        } catch (Exception e) {
            healthDTO.setState(ERROR);
            healthDTO.getMessages().add("Couldn't connect with datasource. "+e.toString());
            return new ResponseEntity<>(healthDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}