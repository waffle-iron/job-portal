package com.factly.jobportal.repository;

import com.factly.jobportal.domain.SelectionProcedure;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SelectionProcedure entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SelectionProcedureRepository extends JpaRepository<SelectionProcedure,Long> {
    
}
