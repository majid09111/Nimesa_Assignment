package com.nimesa.assignment.data.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="s3_details")
public class S3Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private long id;

    @Column(name="job_id")
    private String jobId;


    @Column(name="bucket_name")
    private String bucketName;

}
