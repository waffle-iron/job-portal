package com.factly.jobportal.service.impl;

import com.factly.jobportal.service.LanguageService;
import com.factly.jobportal.domain.Language;
import com.factly.jobportal.repository.LanguageRepository;
import com.factly.jobportal.repository.search.LanguageSearchRepository;
import com.factly.jobportal.service.dto.LanguageDTO;
import com.factly.jobportal.service.mapper.LanguageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Language.
 */
@Service
@Transactional
public class LanguageServiceImpl implements LanguageService{

    private final Logger log = LoggerFactory.getLogger(LanguageServiceImpl.class);

    private final LanguageRepository languageRepository;

    private final LanguageMapper languageMapper;

    private final LanguageSearchRepository languageSearchRepository;

    public LanguageServiceImpl(LanguageRepository languageRepository, LanguageMapper languageMapper, LanguageSearchRepository languageSearchRepository) {
        this.languageRepository = languageRepository;
        this.languageMapper = languageMapper;
        this.languageSearchRepository = languageSearchRepository;
    }

    /**
     * Save a language.
     *
     * @param languageDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LanguageDTO save(LanguageDTO languageDTO) {
        log.debug("Request to save Language : {}", languageDTO);
        Language language = languageMapper.toEntity(languageDTO);
        language = languageRepository.save(language);
        LanguageDTO result = languageMapper.toDto(language);
        languageSearchRepository.save(language);
        return result;
    }

    /**
     *  Get all the languages.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LanguageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Languages");
        return languageRepository.findAll(pageable)
            .map(languageMapper::toDto);
    }

    /**
     *  Get one language by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LanguageDTO findOne(Long id) {
        log.debug("Request to get Language : {}", id);
        Language language = languageRepository.findOne(id);
        return languageMapper.toDto(language);
    }

    /**
     *  Delete the  language by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Language : {}", id);
        languageRepository.delete(id);
        languageSearchRepository.delete(id);
    }

    /**
     * Search for the language corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LanguageDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Languages for query {}", query);
        Page<Language> result = languageSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(languageMapper::toDto);
    }
}
