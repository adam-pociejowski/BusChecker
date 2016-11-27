package com.valverde.buschecker.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "bus")
@Data
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bus_seq_gen")
    @SequenceGenerator(name = "bus_seq_gen", sequenceName = "bus_id_seq")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="bus2sitter", joinColumns=@JoinColumn(name="bus_id"),
            inverseJoinColumns=@JoinColumn(name="sitter_id"))
    private List<Sitter> sitters;

    private String busName;

    private String sideNumber;

    private String registerNumber;

    private int numberOfSeats;

    private Date technicalReviewDate;

    private Date liftReviewDate;

    private Date extinguisherReviewDate;

    private Date tachographReviewDate;

    private Date insuranceDate;

}