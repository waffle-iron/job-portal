package com.factly.jobportal.repository;

import com.factly.jobportal.domain.ClientType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ClientType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientTypeRepository extends JpaRepository<ClientType,Long> {
    
}
