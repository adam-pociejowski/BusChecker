package com.valverde.buschecker.notification.sms.enums;

import lombok.Getter;

public enum ReviewType {
    TECHNICAL(6, "Przegląd techniczny"),
    LIFT(24, "Przegląd windy"),
    INSURANCE(12, "Ubezpieczenie"),
    EXTIGUISHERY(12, "Przegląd gaśnicy"),
    TACHOGRAPH(24, "Przegląd tachografu");

    @Getter
    private Integer month;

    @Getter
    private String name;

    ReviewType(Integer month, String name) {
        this.month = month;
        this.name = name;
    }
}