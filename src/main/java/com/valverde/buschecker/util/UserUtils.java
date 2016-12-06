package com.valverde.buschecker.util;

import com.valverde.buschecker.entity.Driver;
import com.valverde.buschecker.entity.User;
import com.valverde.buschecker.web.dto.DriverDTO;
import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
public class UserUtils {

    public static Driver getDriverFromUser(User user, DriverDTO driverDTO) throws Exception {
        if (driverDTO.getBuses() == null) {
            log.error("DriverDTO without buses list. "+driverDTO.toString());
            throw new Exception("DriverDTO without buses list. "+driverDTO.toString());
        } else {
            for (Driver driver : user.getDrivers())
                if (driver.getId().equals(driverDTO.getId()))
                    return driver;
        }
        return null;
    }
}