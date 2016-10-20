package com.valverde.buschecker.web.rest;

import com.valverde.buschecker.notification.SmsClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SmsTestRestController {

    @RequestMapping("/test/send")
    public ResponseEntity<Map<String, String>> sendSmsViaApi(@RequestParam("phone") String phone,
                                                             @RequestParam("message") String message) {

        Map<String, String> response = new HashMap<>();
        try {
            new SmsClient().sendMessage("48"+phone, message);
            response.put("state", "success");
        }
        catch (Exception e) {
            response.put("state", "error");
        }
        return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
    }
}
