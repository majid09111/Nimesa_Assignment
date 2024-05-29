package com.nimesa.assignment.data.entity;

import javax.persistence.*;

import com.nimesa.assignment.data.enums.Status;
import lombok.Data;

@Entity
@Data
@Table(name="job_details")
public class JobEntity {

    @Id
    @Column(name="id")
    private String id;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private Status status;

}
