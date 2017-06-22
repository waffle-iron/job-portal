package com.factly.jobportal.service;

import com.factly.jobportal.service.dto.SelectionProcedureDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing SelectionProcedure.
 */
public interface SelectionProcedureService {

    /**
     * Save a selectionProcedure.
     *
     * @param selectionProcedureDTO the entity to save
     * @return the persisted entity
     */
    SelectionProcedureDTO save(SelectionProcedureDTO selectionProcedureDTO);

    /**
     *  Get all the selectionProcedures.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SelectionProcedureDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" selectionProcedure.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SelectionProcedureDTO findOne(Long id);

    /**
     *  Delete the "id" selectionProcedure.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the selectionProcedure corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SelectionProcedureDTO> search(String query, Pageable pageable);
}
