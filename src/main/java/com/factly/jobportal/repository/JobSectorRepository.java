package com.factly.jobportal.repository;

import com.factly.jobportal.domain.JobSector;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the JobSector entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobSectorRepository extends JpaRepository<JobSector,Long> {
    
}
