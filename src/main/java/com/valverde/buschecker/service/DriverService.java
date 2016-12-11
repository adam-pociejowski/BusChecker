package com.valverde.buschecker.service;

import com.valverde.buschecker.entity.Bus;
import com.valverde.buschecker.entity.Driver;
import com.valverde.buschecker.entity.User;
import com.valverde.buschecker.repository.DriverRepository;
import com.valverde.buschecker.util.BusUtils;
import com.valverde.buschecker.web.dto.BusDTO;
import com.valverde.buschecker.web.dto.DriverDTO;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import static com.valverde.buschecker.util.BusUtils.removeDuplicateBuses;

@Service
@CommonsLog
public class DriverService {

//    @Autowired
//    private DriverRepository driverRepository;
//
//    Driver getDriverFromDTO(DriverDTO dto) {
//        return driverRepository.findByFirstnameAndLastname(dto.getFirstname(), dto.getLastname());
//    }
//
//    List<DriverDTO> getOtherDriversFromUser(User user) {
//        Iterable<Driver> allDrivers = driverRepository.findAll();
//        List<DriverDTO> otherDriversDTO = new ArrayList<>();
//        for (Driver driver : allDrivers)
//            if (!user.getDrivers().contains(driver)) {
//                otherDriversDTO.add(new DriverDTO(driver));
//            }
//
//        return otherDriversDTO;
//    }
//
//    List<Driver> getChosenDrivers(List<DriverDTO> driverDTOs) {
//        List<Driver> drivers = new ArrayList<>();
//        for (DriverDTO dto : driverDTOs)
//            if (dto.getChosen())
//                drivers.add(getDriverFromDTO(dto));
//
//        return drivers;
//    }
//
//    void setFieldsOnDriver(Driver driver, DriverDTO driverDTO) {
//        driver.setFirstname(driverDTO.getFirstname());
//        driver.setLastname(driverDTO.getLastname());
//        driver.setEmail(driverDTO.getEmail());
//        driver.setNotificationBefore(driverDTO.getNotificationBefore());
//        driver.setPhoneNumber(driverDTO.getPhoneNumber());
//    }
//
//    void setBusesToDriver(Driver driver, DriverDTO driverDTO) {
//        removeDuplicateBuses(driver);
//        removeDeletedBusesFromDriver(driver, driverDTO);
//        List<Bus> newBuses = new ArrayList<>();
//        for (BusDTO busDTO : driverDTO.getBuses()) {
//            Bus bus = getBusFromDriver(driver, busDTO);
//            if (bus == null) {
//                bus = new Bus();
//                bus.setDriver(driver);
//                bus.setSitters(new ArrayList<>());
//                newBuses.add(bus);
//            }
//            BusUtils.setFieldsToBus(bus, busDTO);
//            BusUtils.updateSittersInBus(bus, busDTO);
//        }
//        List<Bus> buses = driver.getBuses();
//        buses.addAll(newBuses);
//    }
//
//    private void removeDeletedBusesFromDriver(Driver driver, DriverDTO driverDTO) {
//        for (Iterator<Bus> iterator = driver.getBuses().iterator(); iterator.hasNext();)
//            if (!isInBusDTOs(iterator.next(), driverDTO))
//                iterator.remove();
//    }
//
//    private boolean isInBusDTOs(Bus bus, DriverDTO driverDTO) {
//        for (BusDTO b : driverDTO.getBuses())
//            if (b.getId().equals(bus.getId()))
//                return true;
//
//        return false;
//    }
//
//    private Bus getBusFromDriver(Driver driver, BusDTO busDTO) {
//        for (Bus bus : driver.getBuses())
//            if (bus.getId().equals(busDTO.getId()))
//                return bus;
//
//        return null;
//    }
}