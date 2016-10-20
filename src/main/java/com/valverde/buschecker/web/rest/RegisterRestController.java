package com.valverde.buschecker.web.rest;

import com.valverde.buschecker.entity.BusDriver;
import com.valverde.buschecker.entity.Sitter;
import com.valverde.buschecker.entity.User;
import com.valverde.buschecker.service.UserService;
import com.valverde.buschecker.util.DateUtils;
import com.valverde.buschecker.util.UserUtils;
import com.valverde.buschecker.web.dto.RegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class RegisterRestController {

    @Autowired
    private UserService userService;

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
        busDriver.setPhoneNumber(dto.getPhoneNumber());
        busDriver.setNumberOfSeats(dto.getNumberOfSeats());
        busDriver.setExtinguisherReviewDate(DateUtils.stringToDate(dto.getExtinguisherReviewDate(), "dd/MM/yyyy"));
        busDriver.setInsuranceDate(DateUtils.stringToDate(dto.getInsuranceDate(), "dd/MM/yyyy"));
        busDriver.setTechnicalReviewDate(DateUtils.stringToDate(dto.getTechnicalReviewDate(), "dd/MM/yyyy"));
        busDriver.setTachographReviewDate(DateUtils.stringToDate(dto.getTachographReviewDate(), "dd/MM/yyyy"));
        busDriver.setLiftReviewDate(DateUtils.stringToDate(dto.getLiftReviewDate(), "dd/MM/yyyy"));

        List<Sitter> sitters = new ArrayList<>();
        busDriver.setSitters(UserUtils.sitterDTOToEntity(sitters, dto.getSitters(), busDriver));

        List<BusDriver> busses = new ArrayList<>();
        busses.add(busDriver);
        user.setBuses(busses);
        return user;
    }
}