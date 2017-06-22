package com.factly.jobportal.service;

import com.factly.jobportal.service.dto.ClientTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ClientType.
 */
public interface ClientTypeService {

    /**
     * Save a clientType.
     *
     * @param clientTypeDTO the entity to save
     * @return the persisted entity
     */
    ClientTypeDTO save(ClientTypeDTO clientTypeDTO);

    /**
     *  Get all the clientTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ClientTypeDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" clientType.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ClientTypeDTO findOne(Long id);

    /**
     *  Delete the "id" clientType.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the clientType corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ClientTypeDTO> search(String query, Pageable pageable);
}
