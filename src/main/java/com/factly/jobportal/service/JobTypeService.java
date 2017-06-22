package com.factly.jobportal.service;

import com.factly.jobportal.service.dto.JobTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing JobType.
 */
public interface JobTypeService {

    /**
     * Save a jobType.
     *
     * @param jobTypeDTO the entity to save
     * @return the persisted entity
     */
    JobTypeDTO save(JobTypeDTO jobTypeDTO);

    /**
     *  Get all the jobTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<JobTypeDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" jobType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    JobTypeDTO findOne(Long id);

    /**
     *  Delete the "id" jobType.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the jobType corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<JobTypeDTO> search(String query, Pageable pageable);
}
