package com.factly.jobportal.service;

import com.factly.jobportal.service.dto.QuotaJobDetailsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing QuotaJobDetails.
 */
public interface QuotaJobDetailsService {

    /**
     * Save a quotaJobDetails.
     *
     * @param quotaJobDetailsDTO the entity to save
     * @return the persisted entity
     */
    QuotaJobDetailsDTO save(QuotaJobDetailsDTO quotaJobDetailsDTO);

    /**
     *  Get all the quotaJobDetails.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<QuotaJobDetailsDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" quotaJobDetails.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    QuotaJobDetailsDTO findOne(Long id);

    /**
     *  Delete the "id" quotaJobDetails.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the quotaJobDetails corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<QuotaJobDetailsDTO> search(String query, Pageable pageable);
}
