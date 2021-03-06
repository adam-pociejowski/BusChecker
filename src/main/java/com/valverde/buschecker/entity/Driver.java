package com.valverde.buschecker.entity;

import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "driver")
@Data
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "driver_seq_gen")
    @SequenceGenerator(name = "driver_seq_gen", sequenceName = "driver_id_seq")
    @Column(unique = true)
    private Long id;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    private String phoneNumber;

    private String email;

    private Integer notificationBefore;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="user_driver",
            joinColumns=@JoinColumn(name="driver_id"),
            inverseJoinColumns=@JoinColumn(name="user_id"))
    private List<User> users;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Bus> buses;
}