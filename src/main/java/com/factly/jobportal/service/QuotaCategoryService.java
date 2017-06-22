package com.factly.jobportal.service;

import com.factly.jobportal.service.dto.QuotaCategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing QuotaCategory.
 */
public interface QuotaCategoryService {

    /**
     * Save a quotaCategory.
     *
     * @param quotaCategoryDTO the entity to save
     * @return the persisted entity
     */
    QuotaCategoryDTO save(QuotaCategoryDTO quotaCategoryDTO);

    /**
     *  Get all the quotaCategories.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<QuotaCategoryDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" quotaCategory.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    QuotaCategoryDTO findOne(Long id);

    /**
     *  Delete the "id" quotaCategory.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the quotaCategory corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<QuotaCategoryDTO> search(String query, Pageable pageable);
}
