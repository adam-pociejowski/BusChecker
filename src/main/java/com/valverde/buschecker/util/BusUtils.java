package com.valverde.buschecker.util;

import com.valverde.buschecker.entity.Bus;
import com.valverde.buschecker.entity.Sitter;
import com.valverde.buschecker.web.dto.BusDTO;
import com.valverde.buschecker.web.dto.SitterDTO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BusUtils {

    public static void setFieldsToBus(Bus bus, BusDTO busDTO) throws BusUtilsException {
        if (bus == null || busDTO == null)
            throw new BusUtilsException("One of parameters is null.");

        bus.setBusName(busDTO.getBusName());
        bus.setSideNumber(busDTO.getSideNumber());
        bus.setRegisterNumber(busDTO.getRegisterNumber());
        bus.setExtinguisherReviewDate(busDTO.getExtinguisherReviewDate());
        bus.setInsuranceDate(busDTO.getInsuranceDate());
        bus.setLiftReviewDate(busDTO.getLiftReviewDate());
        bus.setTachographReviewDate(busDTO.getTachographReviewDate());
        bus.setTechnicalReviewDate(busDTO.getTechnicalReviewDate());
        bus.setNumberOfSeats(busDTO.getNumberOfSeats());
    }

    public static void updateSittersInBus(Bus bus, BusDTO busDTO) throws BusUtilsException  {
        if (bus == null || busDTO == null)
            throw new BusUtilsException("One of parameters is null.");

        removeDeletedSittersFromBus(bus, busDTO);

        List<Sitter> newSitters = new ArrayList<>();
        for (SitterDTO sitterDTO : busDTO.getSitters())
            updateSitter(bus, newSitters, sitterDTO);

        List<Sitter> sitters = bus.getSitters();
        sitters.addAll(newSitters);
    }

    private static void removeDeletedSittersFromBus(Bus bus, BusDTO busDTO) {
        for (Iterator<Sitter> iterator = bus.getSitters().iterator(); iterator.hasNext();)
            if (!isInSitterDTOs(iterator.next(), busDTO))
                iterator.remove();
    }

    private static Boolean isInSitterDTOs(Sitter sitter, BusDTO busDTO) {
        for (SitterDTO s : busDTO.getSitters())
            if (s.getId().equals(sitter.getId()))
                return true;

        return false;
    }

    private static void updateSitter(Bus bus, List<Sitter> newSitters, SitterDTO sitterDTO) {
        Sitter sitter = getSitterFromBus(bus, sitterDTO);
        if (sitter == null)
            sitter = addNewSitter(bus, newSitters);

        setFieldsToSitter(sitter, sitterDTO);
    }

    private static Sitter addNewSitter(Bus bus, List<Sitter> newSitters) {
        Sitter sitter;
        sitter = new Sitter();
        sitter.setBus(bus);
        newSitters.add(sitter);
        return sitter;
    }

    private static void setFieldsToSitter(Sitter sitter, SitterDTO sitterDTO) {
        sitter.setFirstname(sitterDTO.getFirstname());
        sitter.setLastname(sitterDTO.getLastname());
    }

    private static Sitter getSitterFromBus(Bus bus, SitterDTO sitterDTO) {
        for (Sitter sitter : bus.getSitters())
            if (sitter.getId().equals(sitterDTO.getId()))
                return sitter;

        return null;
    }

    static class BusUtilsException extends RuntimeException {
        BusUtilsException(String message) {
            super(message);
        }
    }
}
