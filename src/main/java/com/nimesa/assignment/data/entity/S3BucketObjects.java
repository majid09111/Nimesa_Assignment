package com.nimesa.assignment.data.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="s3_bucket_objects")
public class S3BucketObjects {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private long id;

    @Column(name="bucket_id")
    private long bucketId;

    @Column(name="bucket_name")
    private String bucketName;

    @Column(name="file_name")
    private String fileName;
}
