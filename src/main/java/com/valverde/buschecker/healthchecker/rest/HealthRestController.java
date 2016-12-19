package com.valverde.buschecker.healthchecker.rest;

import com.valverde.buschecker.healthchecker.dto.HealthDTO;
import com.valverde.buschecker.notification.sms.SmsClient;
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

    @Autowired
    public HealthRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/status")
    public ResponseEntity<HealthDTO> getHealthPoint() {
        HealthDTO healthDTO = new HealthDTO();
        try {
            userRepository.count();
            healthDTO.setState(HEALTHY);
            return new ResponseEntity<>(healthDTO, HttpStatus.OK);
        } catch (Exception e) {
            healthDTO.setState(NOT_HEALTHY);
            return new ResponseEntity<>(healthDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sendsms")
    public ResponseEntity<Map<String, String>> sendSmsViaApi(@RequestParam("phone") String phone,
                                                             @RequestParam("message") String message) {

        Map<String, String> response = new HashMap<>();
        try {
            new SmsClient().sendMessage("48"+phone, message);
            response.put("state", SUCCESS.toString());
        } catch (Exception e) {
            response.put("state", ERROR.toString());
            log.error("Problem while sending test message: "+message+" to "+phone, e);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}