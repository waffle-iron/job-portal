package com.factly.jobportal.service;

import com.factly.jobportal.service.dto.EductationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Eductation.
 */
public interface EductationService {

    /**
     * Save a eductation.
     *
     * @param eductationDTO the entity to save
     * @return the persisted entity
     */
    EductationDTO save(EductationDTO eductationDTO);

    /**
     *  Get all the eductations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<EductationDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" eductation.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EductationDTO findOne(Long id);

    /**
     *  Delete the "id" eductation.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the eductation corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<EductationDTO> search(String query, Pageable pageable);
}
