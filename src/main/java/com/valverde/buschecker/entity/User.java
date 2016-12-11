package com.valverde.buschecker.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq_gen")
    @SequenceGenerator(name = "users_seq_gen", sequenceName = "users_id_seq")
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

//    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinTable(name="user2driver", joinColumns=@JoinColumn(name="user_id"), inverseJoinColumns=@JoinColumn(name="driver_id"))
//    private List<Driver> drivers;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="user_driver",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="driver_id"))
    private List<Driver> drivers;
}