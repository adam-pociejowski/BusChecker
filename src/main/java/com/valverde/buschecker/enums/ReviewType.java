package com.valverde.buschecker.enums;

import lombok.Getter;

@Getter
public enum ReviewType {
    TECHNICAL(6, "Przegląd techniczny"),
    LIFT(24, "Przegląd windy"),
    INSURANCE(12, "Ubezpieczenie"),
    EXTIGUISHERY(12, "Przegląd gaśnicy"),
    TACHOGRAPH(24, "Przegląd tachografu");

    private Integer month;

    private String name;

    ReviewType(Integer month, String name) {
        this.month = month;
        this.name = name;
    }
}