package com.factly.jobportal.repository;

import com.factly.jobportal.domain.JobType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the JobType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobTypeRepository extends JpaRepository<JobType,Long> {
    
}
