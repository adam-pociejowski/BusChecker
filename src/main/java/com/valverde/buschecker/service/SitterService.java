package com.valverde.buschecker.service;

import com.valverde.buschecker.entity.Bus;
import com.valverde.buschecker.entity.Sitter;
import com.valverde.buschecker.repository.SitterRepository;
import com.valverde.buschecker.util.SitterUtils;
import com.valverde.buschecker.web.dto.BusDTO;
import com.valverde.buschecker.web.dto.SitterDTO;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@CommonsLog
public class SitterService {

    @Autowired
    private SitterRepository sitterRepository;

    @Autowired
    private BusService busService;

    @Transactional
    public void updateSitters(BusDTO busDTO) {
        Bus bus = busService.getBus(busDTO.getId());
        updateSittersOnBus(bus, busDTO);
        busService.save(bus);
    }

    private void saveRemovedSitters(List<Sitter> deletedSitters) {
        sitterRepository.save(deletedSitters);
    }

    private void updateSittersOnBus(Bus bus, BusDTO busDTO) {
        List<Sitter> removedSitters = removeDeletedSittersFromBus(bus, busDTO);
        saveRemovedSitters(removedSitters);
        List<Sitter> newSitters = new ArrayList<>();
        for (SitterDTO sitterDTO : busDTO.getSitters()) {
            Sitter sitter = getSitterFromBus(bus, sitterDTO);
            if (sitter == null)
                sitter = createNewSitter(bus, newSitters);

            sitter.setId(sitterDTO.getId());
            SitterUtils.setFieldsOnSitter(sitter, sitterDTO);
        }
        List<Sitter> sitters = bus.getSitters();
        sitters.addAll(newSitters);
    }

    private Sitter createNewSitter(Bus bus, List<Sitter> newSitters) {
        Sitter sitter = new Sitter();
        sitter.setBus(bus);
        newSitters.add(sitter);
        return sitter;
    }

    private List<Sitter> removeDeletedSittersFromBus(Bus bus, BusDTO busDTO) {
        List<Sitter> removedSitters = new ArrayList<>();
        for (Iterator<Sitter> iterator = bus.getSitters().iterator(); iterator.hasNext();) {
            Sitter sitter = iterator.next();
            if (!isInSitterDTOs(sitter, busDTO)) {
                sitter.setBus(null);
                removedSitters.add(sitter);
                iterator.remove();
            }
        }
        return removedSitters;
    }

    private boolean isInSitterDTOs(Sitter sitter, BusDTO busDTO) {
        for (SitterDTO s : busDTO.getSitters())
            if (s.getId() != null && s.getId().equals(sitter.getId()))
                return true;

        return false;
    }

    private Sitter getSitterFromBus(Bus bus, SitterDTO sitterDTO) {
        for (Sitter sitter : bus.getSitters())
            if (sitter.getId().equals(sitterDTO.getId()))
                return sitter;

        return null;
    }
}