package com.nimesa.assignment.repository;

import com.nimesa.assignment.data.entity.S3BucketObjects;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface S3ObjectRepository extends CrudRepository<S3BucketObjects, Long> {

    @Query("SELECT s.fileName FROM S3BucketObjects s WHERE s.bucketName=:bucketName AND s.fileName LIKE %:searchToken%")
    List<String> findObjectLikePattern(@Param("bucketName") String bucketName,@Param("searchToken")String searchToken);

    @Query("SELECT COUNT(s) FROM S3BucketObjects s where s.bucketName=:bucketName")
    int getCountOfObjects(@Param("bucketName")String bucketName);
}
