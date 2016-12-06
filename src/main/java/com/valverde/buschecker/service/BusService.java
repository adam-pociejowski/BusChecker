package com.valverde.buschecker.service;

import com.valverde.buschecker.entity.Bus;
import com.valverde.buschecker.entity.Sitter;
import com.valverde.buschecker.repository.BusRepository;
import com.valverde.buschecker.util.BusUtils;
import com.valverde.buschecker.web.dto.BusDTO;
import com.valverde.buschecker.web.dto.SitterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class BusService {

    @Autowired
    private BusRepository busRepository;

    @Transactional
    public void saveBusFromDTO(BusDTO busDTO) throws Exception {
        Bus bus = convertFromDTO(busDTO);
        busRepository.save(bus);
    }

    public Iterable<BusDTO> getAllOtherBuses(Long driverId) {
        Iterable<Bus> buses = busRepository.findOtherBuses(driverId);
        List<BusDTO> dtos = new ArrayList<>();
        for (Bus bus : buses)
            dtos.add(new BusDTO(bus));

        return dtos;
    }

    private Bus convertFromDTO(BusDTO busDTO) {
        Bus bus = new Bus();
        if (busDTO.getId() != null)
            bus = busRepository.findOne(busDTO.getId());

        BusUtils.setFieldsToBus(bus, busDTO);
        return bus;
    }
}