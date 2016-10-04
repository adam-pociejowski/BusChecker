package com.valverde.buschecker.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String username;

    private String password;

    private Integer notificationBetweenEventDays;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user2bus",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "bus_id"))
    private List<BusDriver> buses;
}
