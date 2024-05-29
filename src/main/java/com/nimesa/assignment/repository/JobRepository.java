package com.nimesa.assignment.repository;

import com.nimesa.assignment.data.entity.JobEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends CrudRepository<JobEntity, String> {
}
