package com.valverde.buschecker.util;

import com.valverde.buschecker.entity.Sitter;
import com.valverde.buschecker.web.dto.SitterDTO;

public class SitterUtils {

    public static void setFieldsOnSitter(Sitter sitter, SitterDTO sitterDTO)
            throws SitterUtilsException {
        if (sitter == null || sitterDTO == null)
            throw new BusUtils.BusUtilsException("One of parameters is null.");

        sitter.setLastname(sitterDTO.getLastname());
        sitter.setFirstname(sitterDTO.getFirstname());
    }

    static class SitterUtilsException extends RuntimeException {
        SitterUtilsException(String message) {
            super(message);
        }
    }
}