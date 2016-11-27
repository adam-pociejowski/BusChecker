package com.valverde.buschecker.web.dto;

import com.valverde.buschecker.entity.Bus;
import com.valverde.buschecker.entity.Driver;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class DriverDTO {

    private String firstname;

    private String lastname;

    private String phoneNumber;

    private String email;

    private Integer notificationBefore;

    private List<BusDTO> busses;

    private Boolean chosen;

    public DriverDTO(Driver driver) {
        this.firstname = driver.getFirstname();
        this.lastname = driver.getLastname();
        this.phoneNumber = driver.getPhoneNumber();
        this.email = driver.getEmail();
        this.notificationBefore = driver.getNotificationBefore();
        this.chosen = false;
        busses = new ArrayList<>();
        for (Bus bus : driver.getBusses())
            busses.add(new BusDTO(bus));
    }
}