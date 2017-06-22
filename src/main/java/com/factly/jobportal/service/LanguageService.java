package com.factly.jobportal.service;

import com.factly.jobportal.service.dto.LanguageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Language.
 */
public interface LanguageService {

    /**
     * Save a language.
     *
     * @param languageDTO the entity to save
     * @return the persisted entity
     */
    LanguageDTO save(LanguageDTO languageDTO);

    /**
     *  Get all the languages.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<LanguageDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" language.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    LanguageDTO findOne(Long id);

    /**
     *  Delete the "id" language.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the language corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<LanguageDTO> search(String query, Pageable pageable);
}
