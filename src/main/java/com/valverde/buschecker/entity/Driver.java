package com.valverde.buschecker.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "driver")
@Data
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "driver_seq_gen")
    @SequenceGenerator(name = "driver_seq_gen", sequenceName = "driver_id_seq")
    private Long id;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    private String phoneNumber;

    private String email;

    private Integer notificationBefore;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Bus> buses;

//    @ManyToMany(cascade=CascadeType.PERSIST)
//    @JoinTable(name="user2driver", joinColumns=@JoinColumn(name="driver_id"),
//            inverseJoinColumns=@JoinColumn(name="user_id"))
//    private List<User> users;

}