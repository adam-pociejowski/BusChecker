package com.valverde.buschecker.entity;

import com.valverde.buschecker.enums.NotificationState;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "notification")
@Data
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_seq_gen")
    @SequenceGenerator(name = "notification_seq_gen", sequenceName = "notification_id_seq")
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Driver driver;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private NotificationState state;

    private Date date;

}