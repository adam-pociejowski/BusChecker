package com.valverde.buschecker.controller;

import com.valverde.buschecker.entity.BusDriver;
import com.valverde.buschecker.entity.Sitter;
import com.valverde.buschecker.entity.User;
import com.valverde.buschecker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showLoginPage() {
        return "index";
    }

    private void createTestUser() {
        BusDriver bus = new BusDriver();
        bus.setBusName("Movistar");
        bus.setLastname("Valverde");
        bus.setFirstname("Alejandro");
        bus.setNumberOfSeats(20);
        bus.setSideNumber("20000-12");
        List<BusDriver> buses = new ArrayList<>();
//        busRepository.save(bus);

        User user = new User();
        user.setPassword("test");
        user.setUsername("test");
//        User user = userRepository.findByUsername("test");
        user.setNotificationBetweenEventDays(14);
//        List<BusDriver> busDrivers = user.getBuses();
//        BusDriver busDriver = busDrivers.get(0);
//        driver.setBusId(bus.getId());
//        driverRepository.save(driver);

        Sitter sitter = new Sitter();
        sitter.setFirstname("one");
        sitter.setLastname("last");
        sitter.setBusDriver(bus);

        Sitter sitter2 = new Sitter();
        sitter2.setFirstname("one2");
        sitter2.setLastname("last2");
        sitter2.setBusDriver(bus);

        List<Sitter> sitters = new ArrayList<>();
        sitters.add(sitter);
        sitters.add(sitter2);
        bus.setSitters(sitters);
        buses.add(bus);

        user.setBuses(buses);
//        userRepository.save(user);
    }
}
