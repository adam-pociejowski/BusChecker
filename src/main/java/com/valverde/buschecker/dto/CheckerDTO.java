package com.valverde.buschecker.dto;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class CheckerDTO {
    private Person driver;
    private String busName;
    private String sideNumber;
    private String rejestrNumber;
    private List<Person> babySitters;
    private int numberOfSeats;
    private Date technicalReviewDate;
    private Date liftReviewDate;
    private Date extinguisherReviewDate;
    private Date tachographReviewDate;
    private Date insuranceDate;
    private int notificationBetweenEventDays;
}
