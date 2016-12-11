package com.valverde.buschecker.service;

import com.valverde.buschecker.entity.Bus;
import com.valverde.buschecker.entity.Driver;
import com.valverde.buschecker.repository.BusRepository;
import com.valverde.buschecker.util.BusUtils;
import com.valverde.buschecker.web.dto.BusDTO;
import com.valverde.buschecker.web.dto.DriverDTO;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import static com.valverde.buschecker.util.BusUtils.removeDuplicateSitters;

@Service
@CommonsLog
@Transactional
public class BusService {

    @Autowired
    private DriverService driverService;

    @Autowired
    private BusRepository busRepository;

    public void save(Bus bus) {
        busRepository.save(bus);
    }

    public void updateBuses(DriverDTO driverDTO) {
        Driver driver = driverService.getDriver(driverDTO.getId());
        updateBusesOnDriver(driver, driverDTO);
        driverService.save(driver);
    }

    private void saveRemovedBuses(List<Bus> deletedBuses) {
        busRepository.save(deletedBuses);
    }

    public Iterable<BusDTO> getAllOtherBuses(Long driverId) {
        Iterable<Bus> buses = busRepository.findOtherBuses(driverId);
        List<BusDTO> dtos = new ArrayList<>();
        for (Bus bus : buses)
            dtos.add(new BusDTO(bus));

        return dtos;
    }

    Bus getBus(Long id) {
        Bus bus = busRepository.findOne(id);
        removeDuplicateSitters(bus);
        return bus;
    }

    private void updateBusesOnDriver(Driver driver, DriverDTO driverDTO) {
        List<Bus> removedBuses = removeDeletedBusesFromDriver(driver, driverDTO);
        saveRemovedBuses(removedBuses);
        List<Bus> newBuses = new ArrayList<>();
        for (BusDTO busDTO : driverDTO.getBuses()) {
            Bus bus = getBusFromDriver(driver, busDTO);
            if (bus == null)
                bus = createNewBus(driver, newBuses);

            bus.setId(busDTO.getId());
            BusUtils.setFieldsToBus(bus, busDTO);
        }
        List<Bus> buses = driver.getBuses();
        buses.addAll(newBuses);
    }

    private Bus createNewBus(Driver driver, List<Bus> newBuses) {
        Bus bus = new Bus();
        bus.setDriver(driver);
        bus.setSitters(new ArrayList<>());
        newBuses.add(bus);
        return bus;
    }

    private  List<Bus> removeDeletedBusesFromDriver(Driver driver, DriverDTO driverDTO) {
        List<Bus> removedBuses = new ArrayList<>();
        for (Iterator<Bus> iterator = driver.getBuses().iterator(); iterator.hasNext();) {
            Bus bus = iterator.next();
            if (!isInBusDTOs(bus, driverDTO)) {
                bus.setDriver(null);
                removedBuses.add(bus);
                iterator.remove();
            }
        }
        return removedBuses;
    }

    private boolean isInBusDTOs(Bus bus, DriverDTO driverDTO) {
        for (BusDTO b : driverDTO.getBuses())
            if (b.getId() != null && b.getId().equals(bus.getId()))
                return true;

        return false;
    }

    private Bus getBusFromDriver(Driver driver, BusDTO busDTO) {
        for (Bus bus : driver.getBuses())
            if (bus.getId().equals(busDTO.getId()))
                return bus;

        return null;
    }
}