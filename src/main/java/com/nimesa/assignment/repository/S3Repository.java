package com.nimesa.assignment.repository;

import com.nimesa.assignment.data.entity.S3Entity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface S3Repository extends CrudRepository<S3Entity,Long> {

    @Query("SELECT s.bucketName FROM S3Entity s")
    List<String> getAllBuckets();

    @Query("SELECT COUNT(s) FROM S3Entity s WHERE s.bucketName = :bucketName")
    int getObjectCount(String bucketName);

    Optional<S3Entity> findByBucketName(String bucketName);
}
