package com.valverde.buschecker.entity;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "sitter")
@Data
public class Sitter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstname;

    private String lastname;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bus_id")
    private BusDriver busDriver;
}