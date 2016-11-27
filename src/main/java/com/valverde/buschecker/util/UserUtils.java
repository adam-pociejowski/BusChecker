package com.valverde.buschecker.util;

import com.valverde.buschecker.entity.Driver;
import com.valverde.buschecker.web.dto.SitterDTO;
import com.valverde.buschecker.entity.Sitter;

import java.util.List;

public class UserUtils {

    public static List<Sitter> sitterDTOToEntity(List<Sitter> sitters, List<SitterDTO> dtos, Driver driver) {
        for (SitterDTO sitterDTO : dtos) {
            Sitter sitter = new Sitter();
//            sitter.setBusDriver(busDriver);
            sitter.setFirstname(sitterDTO.getFirstname());
            sitter.setLastname(sitterDTO.getLastname());
            sitters.add(sitter);
        }
        return sitters;
    }
}
