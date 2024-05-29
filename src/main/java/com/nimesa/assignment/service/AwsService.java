package com.nimesa.assignment.service;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.nimesa.assignment.data.entity.EC2Entity;
import com.nimesa.assignment.data.entity.JobEntity;
import com.nimesa.assignment.data.entity.S3BucketObjects;
import com.nimesa.assignment.data.entity.S3Entity;
import com.nimesa.assignment.data.enums.Status;
import com.nimesa.assignment.repository.EC2Repository;
import com.nimesa.assignment.repository.JobRepository;
import com.nimesa.assignment.repository.S3ObjectRepository;
import com.nimesa.assignment.repository.S3Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesRequest;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesResponse;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.Instance;
import software.amazon.awssdk.services.ec2.model.Reservation;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@EnableAsync
public class AwsService {

    @Value("${aws.access.key}")
    private String accessKey;

    @Value("${aws.secret.key}")
    private String secretKey;
    private S3Client s3ClientForFile;

    private Ec2Client ec2Client;

    @Autowired
    private EC2Repository ec2Repository;

    @Autowired
    private S3Repository s3Repository;

    @Autowired
    private S3ObjectRepository s3ObjectRepository;

    @Autowired
    private JobRepository jobRepository;

    @PostConstruct
    public void createBasicAwsClient() {
        try {
            log.info("initializing creation of aws client");
            AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(accessKey, secretKey);
            AwsCredentialsProvider awsCredentialsProvider = StaticCredentialsProvider.create(awsBasicCredentials);
            software.amazon.awssdk.regions.Region s3Region = software.amazon.awssdk.regions.Region.AP_SOUTH_1;
            log.info("created aws client successfully");
            s3ClientForFile = S3Client.builder()
                    .credentialsProvider(awsCredentialsProvider)
                    .region(s3Region)
                    .build();
            ec2Client =Ec2Client.builder()
                            .credentialsProvider(awsCredentialsProvider)
                                    .region(s3Region)
                                            .build();

        } catch (Exception e) {
            log.error("error in configuring aws client", e);
        }
    }

    @Async
    public void getEC2List(String jobId){
        JobEntity jobEntity = jobRepository.findById(jobId).get();
        try {
            DescribeInstancesRequest request = DescribeInstancesRequest.builder().build();
            DescribeInstancesResponse response = ec2Client.describeInstances(request);
            for (Reservation reservation : response.reservations()) {
                for (Instance instance : reservation.instances()) {
                    Optional<EC2Entity> optionalEC2Entity = ec2Repository.findByInstanceId(instance.instanceId());
                    if(!optionalEC2Entity.isPresent()) {
                        EC2Entity ec2Entity = new EC2Entity();
                        ec2Entity.setInstanceId(instance.instanceId());
                        ec2Entity.setJobId(jobId);
                        ec2Repository.save(ec2Entity);
                    }
                }
            }
            jobEntity.setStatus(Status.COMPLETED);
            jobRepository.save(jobEntity);
        }catch(Exception e){
            jobEntity.setStatus(Status.FAILED);
            jobRepository.save(jobEntity);
            log.error(String.valueOf(e));
        }


    }

    @Async
    public void getS3Buckets(String jobId){
        JobEntity jobEntity = jobRepository.findById(jobId).get();
        try {
            ListBucketsResponse listBucketsResponse = s3ClientForFile.listBuckets();
            List<Bucket> buckets = listBucketsResponse.buckets();
            for (Bucket bucket : buckets) {
                Optional<S3Entity> optionalS3Entity = s3Repository.findByBucketName(bucket.name());
                if(!optionalS3Entity.isPresent()) {
                    S3Entity s3Entity = new S3Entity();
                    s3Entity.setBucketName(bucket.name());
                    s3Entity.setJobId(jobId);
                    s3Repository.save(s3Entity);
                }
            }
            jobEntity.setStatus(Status.COMPLETED);
            jobRepository.save(jobEntity);
        }catch(Exception e){
            jobEntity.setStatus(Status.FAILED);
            jobRepository.save(jobEntity);
            log.error(String.valueOf(e));
        }
    }

    @Async
    public void getS3Objects(long bucketId,String bucketName){
        try{
            ListObjectsV2Request listObjectsV2Request = ListObjectsV2Request.builder()
                    .bucket(bucketName)
                    .build();

            ListObjectsV2Response listObjectsV2Response;

            do{
                listObjectsV2Response = s3ClientForFile.listObjectsV2(listObjectsV2Request);

                for (S3Object s3Object : listObjectsV2Response.contents()) {
                    S3BucketObjects s3BucketObjects = new S3BucketObjects();
                    s3BucketObjects.setBucketId(bucketId);
                    s3BucketObjects.setBucketName(bucketName);
                    s3BucketObjects.setFileName(s3Object.key());
                    s3ObjectRepository.save(s3BucketObjects);
                }
                String continuationToken = listObjectsV2Response.nextContinuationToken();
                listObjectsV2Request = listObjectsV2Request.toBuilder()
                        .continuationToken(continuationToken)
                        .build();

            } while (listObjectsV2Response.isTruncated());
        }catch (Exception e){
            log.error(String.valueOf(e));
        }
    }

}
