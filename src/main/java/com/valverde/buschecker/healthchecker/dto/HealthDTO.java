package com.valverde.buschecker.healthchecker.dto;

import com.valverde.buschecker.healthchecker.enums.HealthState;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class HealthDTO {

    private String appName;

    private HealthState state;

    private List<StatDTO> stats;

    private List<String> messages;

    public HealthDTO() {
        stats = new ArrayList<>();
        messages = new ArrayList<>();
    }
}