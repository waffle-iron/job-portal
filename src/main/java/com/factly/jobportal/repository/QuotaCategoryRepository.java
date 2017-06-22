package com.factly.jobportal.repository;

import com.factly.jobportal.domain.QuotaCategory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the QuotaCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuotaCategoryRepository extends JpaRepository<QuotaCategory,Long> {
    
}
