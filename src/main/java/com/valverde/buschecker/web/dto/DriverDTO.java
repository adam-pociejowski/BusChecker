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

    private Long id;

    private String firstname;

    private String lastname;

    private String phoneNumber;

    private String email;

    private Integer notificationBefore;

    private List<BusDTO> buses;

    private Boolean chosen;

    public DriverDTO(Driver driver) {
        this.id = driver.getId();
        this.firstname = driver.getFirstname();
        this.lastname = driver.getLastname();
        this.phoneNumber = driver.getPhoneNumber();
        this.email = driver.getEmail();
        this.notificationBefore = driver.getNotificationBefore();
        this.chosen = false;
        buses = new ArrayList<>();
        for (Bus bus : driver.getBuses())
            buses.add(new BusDTO(bus));
    }
}