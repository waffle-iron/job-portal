package com.factly.jobportal.service;

import com.factly.jobportal.service.dto.EducationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Education.
 */
public interface EducationService {

    /**
     * Save a education.
     *
     * @param educationDTO the entity to save
     * @return the persisted entity
     */
    EducationDTO save(EducationDTO educationDTO);

    /**
     *  Get all the educations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<EducationDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" education.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EducationDTO findOne(Long id);

    /**
     *  Delete the "id" education.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the education corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<EducationDTO> search(String query, Pageable pageable);
}
