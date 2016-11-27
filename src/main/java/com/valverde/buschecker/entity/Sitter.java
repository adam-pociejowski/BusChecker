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

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="bus2sitter", joinColumns=@JoinColumn(name="sitter_id"),
            inverseJoinColumns=@JoinColumn(name="bus_id"))
    private List<Bus> buses;

}