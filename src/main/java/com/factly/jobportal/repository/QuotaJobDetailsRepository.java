package com.factly.jobportal.repository;

import com.factly.jobportal.domain.QuotaJobDetails;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the QuotaJobDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuotaJobDetailsRepository extends JpaRepository<QuotaJobDetails,Long> {
    
}
