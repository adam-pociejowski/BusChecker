package com.valverde.buschecker.web.dto;

import com.valverde.buschecker.entity.Bus;
import com.valverde.buschecker.entity.Sitter;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class BusDTO {

    private Long id;

    private String busName;

    private String sideNumber;

    private String registerNumber;

    private int numberOfSeats;

    private Date technicalReviewDate;

    private Date liftReviewDate;

    private Date extinguisherReviewDate;

    private Date tachographReviewDate;

    private Date insuranceDate;

    private Boolean chosen;

    private List<SitterDTO> sitters;


    public BusDTO(Bus bus) {
        this.id = bus.getId();
        this.busName = bus.getBusName();
        this.sideNumber = bus.getSideNumber();
        this.registerNumber = bus.getRegisterNumber();
        this.numberOfSeats = bus.getNumberOfSeats();
        this.technicalReviewDate = bus.getTechnicalReviewDate();
        this.liftReviewDate = bus.getLiftReviewDate();
        this.extinguisherReviewDate = bus.getExtinguisherReviewDate();
        this.tachographReviewDate = bus.getTachographReviewDate();
        this.insuranceDate = bus.getInsuranceDate();
        this.sitters = new ArrayList<>();
        for (Sitter sitter : bus.getSitters())
            sitters.add(new SitterDTO(sitter));
        chosen = false;
    }
}