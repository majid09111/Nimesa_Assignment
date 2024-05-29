package com.nimesa.assignment.service;

import com.nimesa.assignment.data.entity.JobEntity;
import com.nimesa.assignment.data.entity.S3Entity;
import com.nimesa.assignment.data.enums.Status;
import com.nimesa.assignment.data.exception.ApiException;
import com.nimesa.assignment.data.request.JobRequest;
import com.nimesa.assignment.data.response.JobResponse;
import com.nimesa.assignment.repository.EC2Repository;
import com.nimesa.assignment.repository.JobRepository;
import com.nimesa.assignment.repository.S3ObjectRepository;
import com.nimesa.assignment.repository.S3Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class JobService {

    @Autowired
    private AwsService awsService;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private EC2Repository ec2Repository;

    @Autowired
    private S3Repository s3Repository;

    @Autowired
    private S3ObjectRepository s3ObjectRepository;

    public JobResponse getListOfServices(JobRequest request){
        List<String> servicesList = request.getServices();
        String jobId = String.valueOf(UUID.randomUUID());
        JobEntity job = new JobEntity();
        job.setId(jobId);
        job.setStatus(Status.IN_PROGRESS);
        jobRepository.save(job);
        if(servicesList.contains("EC2")){
            awsService.getEC2List(jobId);
        }
        if(servicesList.contains("S3")){
            awsService.getS3Buckets(jobId);
        }

        return new JobResponse(jobId);
    }

    public String getJobStatus(String jobId) throws ApiException {
        JobEntity jobEntity = jobRepository.findById(jobId).orElseThrow(()->new ApiException("Invalid Job Id", HttpStatus.BAD_REQUEST));
        return jobEntity.getStatus().name();

    }

    public List<String> getDiscoveryResult(String serviceName){
        if(serviceName.equals("EC2")){
            return ec2Repository.getListOfAllInstances();
        }else{
            return s3Repository.getAllBuckets();
        }
    }

    public String getS3ObjectList(String bucketName) throws ApiException {
        S3Entity s3Entity = s3Repository.findByBucketName(bucketName).orElseThrow(()-> new ApiException("Invalid Bucket Name", HttpStatus.BAD_REQUEST));
        awsService.getS3Objects(s3Entity.getId(), s3Entity.getBucketName());
        return s3Entity.getJobId();
    }

    public int getS3BucketObjectCount(String bucketName){
        return s3ObjectRepository.getCountOfObjects(bucketName);
    }

    public List<String> getS3ObjectHavingPattern(String bucketName, String pattern){
        return s3ObjectRepository.findObjectLikePattern(bucketName,pattern);
    }

}
