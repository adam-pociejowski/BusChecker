package com.valverde.buschecker.util;

import com.valverde.buschecker.entity.Bus;
import com.valverde.buschecker.entity.Driver;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DriverUtils {

    public static void removeDuplicateBuses(Driver driver) {
        if (driver != null && driver.getBuses() != null) {
            Set<Bus> busesSet = new HashSet<>(driver.getBuses());
            List<Bus> busesList = new ArrayList<>(busesSet);
            driver.setBuses(busesList);
        }
    }
}