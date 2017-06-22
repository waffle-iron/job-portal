package com.factly.jobportal.repository;

import com.factly.jobportal.domain.Eductation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Eductation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EductationRepository extends JpaRepository<Eductation,Long> {
    
}
