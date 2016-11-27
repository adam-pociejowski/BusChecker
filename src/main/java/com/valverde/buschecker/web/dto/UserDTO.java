package com.valverde.buschecker.web.dto;

import com.valverde.buschecker.entity.Driver;
import com.valverde.buschecker.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UserDTO {

    private String username;

    private List<DriverDTO> drivers;

    public UserDTO(User user) {
        this.username = user.getUsername();
        drivers = new ArrayList<>();
        for (Driver driver : user.getDrivers())
            drivers.add(new DriverDTO(driver));
    }
}