package com.nimesa.assignment.repository;

import com.nimesa.assignment.data.entity.EC2Entity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EC2Repository extends CrudRepository<EC2Entity,Long> {

    @Query("SELECT e.instanceId FROM EC2Entity e")
    List<String> getListOfAllInstances();

    Optional<EC2Entity> findByInstanceId(String instanceId);

}
