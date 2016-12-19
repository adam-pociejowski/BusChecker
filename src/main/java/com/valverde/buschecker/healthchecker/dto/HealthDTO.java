package com.valverde.buschecker.healthchecker.dto;

import com.valverde.buschecker.healthchecker.enums.HealthState;
import lombok.Data;

@Data
public class HealthDTO {

    private HealthState state;

}