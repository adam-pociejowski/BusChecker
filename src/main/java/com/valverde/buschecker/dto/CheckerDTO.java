package com.valverde.buschecker.dto;

import com.valverde.buschecker.entity.BusDriver;
import com.valverde.buschecker.entity.Sitter;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class CheckerDTO {
    private String busName;
    private String sideNumber;
    private String rejestrNumber;
    private String firstname;
    private String lastname;
    private int numberOfSeats;
    private Date technicalReviewDate;
    private Date liftReviewDate;
    private Date extinguisherReviewDate;
    private Date tachographReviewDate;
    private Date insuranceDate;
    private int notificationBetweenEventDays;
    private List<SitterDTO> sitters;

    public CheckerDTO() {}

    public CheckerDTO(BusDriver bus, int notificationBetweenEventDays) {
        this.busName = bus.getBusName();
        this.sideNumber = bus.getSideNumber();
        this.rejestrNumber = bus.getRejestrNumber();
        this.firstname = bus.getFirstname();
        this.lastname = bus.getLastname();
        this.numberOfSeats = bus.getNumberOfSeats();
        this.technicalReviewDate = bus.getTechnicalReviewDate();
        this.liftReviewDate = bus.getLiftReviewDate();
        this.extinguisherReviewDate = bus.getExtinguisherReviewDate();
        this.tachographReviewDate = bus.getTachographReviewDate();
        this.insuranceDate = bus.getExtinguisherReviewDate();
        this.notificationBetweenEventDays = notificationBetweenEventDays;
        sitters = new ArrayList<>();
        for (Sitter sitter : bus.getSitters()) {
            SitterDTO dto = new SitterDTO();
            dto.setFirstname(sitter.getFirstname());
            dto.setLastname(sitter.getLastname());
            sitters.add(dto);
        }
    }
}
