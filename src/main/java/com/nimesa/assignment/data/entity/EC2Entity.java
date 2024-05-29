package com.nimesa.assignment.data.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="ec2_details")
public class EC2Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private long id;

    @Column(name="job_id")
    private String jobId;

    @Column(name="instance_id")
    private String instanceId;

    @Column(name="instance_name")
    private String instanceName;

}
