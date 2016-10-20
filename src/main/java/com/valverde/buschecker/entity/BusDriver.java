package com.valverde.buschecker.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "bus_driver")
@Data
public class BusDriver {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "busDriver", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Sitter> sitters;

    private String firstname;

    private String lastname;

    private String busName;

    private String sideNumber;

    private String phoneNumber;

    private String rejestrNumber;

    private int numberOfSeats;

    private Date technicalReviewDate;

    private Date liftReviewDate;

    private Date extinguisherReviewDate;

    private Date tachographReviewDate;

    private Date insuranceDate;
}