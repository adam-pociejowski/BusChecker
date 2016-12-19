package com.valverde.buschecker.entity;

import com.valverde.buschecker.enums.Task;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "task_report")
@Data
public class TaskReport {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_report_seq_gen")
    @SequenceGenerator(name = "task_report_seq_gen", sequenceName = "task_report_id_seq")
    private Long id;

    private Integer amountSuccess;

    private Integer amountFailed;

    @Enumerated(EnumType.STRING)
    private Task task;

    private Date date;

}