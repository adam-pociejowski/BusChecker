package com.valverde.buschecker.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sitter")
@Data
public class Sitter {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sitter_seq_gen")
    @SequenceGenerator(name = "sitter_seq_gen", sequenceName = "sitter_id_seq")
    private Long id;

    private String firstname;

    private String lastname;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bus_id")
    private Bus bus;

}