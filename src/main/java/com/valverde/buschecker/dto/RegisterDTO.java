package com.valverde.buschecker.dto;

import lombok.Data;
import java.util.List;

@Data
public class RegisterDTO {
    private String username;
    private String password;
    private String busName;
    private String sideNumber;
    private String rejestrNumber;
    private String firstname;
    private String lastname;
    private int numberOfSeats;
    private String technicalReviewDate;
    private String liftReviewDate;
    private String extinguisherReviewDate;
    private String tachographReviewDate;
    private String insuranceDate;
    private int notificationBetweenEventDays;
    private List<SitterDTO> sitters;
}
