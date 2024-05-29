package com.nimesa.assignment.controller;

import com.nimesa.assignment.data.request.JobRequest;
import com.nimesa.assignment.data.response.BaseResponse;
import com.nimesa.assignment.data.response.JobResponse;
import com.nimesa.assignment.data.response.ResponseUtil;
import com.nimesa.assignment.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping("/api/v1/nimesa")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Component
public class ServicesController {

    @Autowired
    private JobService jobService;

    @RequestMapping(value = "/discover", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<JobResponse>> discoverServices(@RequestBody @Valid JobRequest request){
        ResponseUtil<JobResponse> responseUtil = new ResponseUtil<>();
        return responseUtil.getResponse(()->jobService.getListOfServices(request));
    }

    @RequestMapping(value = "/job/status", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<String>> getJobStatus(@RequestParam(name="jobId")String jobId){
        ResponseUtil<String> responseUtil = new ResponseUtil<>();
        return responseUtil.getResponse(()->jobService.getJobStatus(jobId));
    }

    @RequestMapping(value = "/list/service", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<List<String>>> getServices(@RequestParam(name="serviceName")String serviceName){
        ResponseUtil<List<String>> responseUtil = new ResponseUtil<>();
        return responseUtil.getResponse(()-> jobService.getDiscoveryResult(serviceName));
    }

    @RequestMapping(value = "/discover/objects", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<String>> discoverBucketObjects(@RequestParam(name="bucketName")String bucketName){
        ResponseUtil<String> responseUtil = new ResponseUtil<>();
        return responseUtil.getResponse(()->jobService.getS3ObjectList(bucketName));
    }

    @RequestMapping(value = "/object/count", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<Integer>> getObjectCount(@RequestParam(name="bucketName")String bucketName){
        ResponseUtil<Integer> responseUtil = new ResponseUtil<Integer>();
        return responseUtil.getResponse(()->jobService.getS3BucketObjectCount(bucketName));
    }

    @RequestMapping(value = "/search/object", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse<List<String>>> searchObjectCount(@RequestParam(name="bucketName")String bucketName,@RequestParam(name="pattern")String pattern){
        ResponseUtil<List<String>> responseUtil = new ResponseUtil<>();
        return responseUtil.getResponse(()->jobService.getS3ObjectHavingPattern(bucketName,pattern));
    }

}
