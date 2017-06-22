package com.factly.jobportal.service;

import com.factly.jobportal.service.dto.JobSectorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing JobSector.
 */
public interface JobSectorService {

    /**
     * Save a jobSector.
     *
     * @param jobSectorDTO the entity to save
     * @return the persisted entity
     */
    JobSectorDTO save(JobSectorDTO jobSectorDTO);

    /**
     *  Get all the jobSectors.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<JobSectorDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" jobSector.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    JobSectorDTO findOne(Long id);

    /**
     *  Delete the "id" jobSector.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the jobSector corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<JobSectorDTO> search(String query, Pageable pageable);
}
