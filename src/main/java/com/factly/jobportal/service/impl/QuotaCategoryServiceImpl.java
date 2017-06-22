package com.factly.jobportal.service.impl;

import com.factly.jobportal.service.QuotaCategoryService;
import com.factly.jobportal.domain.QuotaCategory;
import com.factly.jobportal.repository.QuotaCategoryRepository;
import com.factly.jobportal.repository.search.QuotaCategorySearchRepository;
import com.factly.jobportal.service.dto.QuotaCategoryDTO;
import com.factly.jobportal.service.mapper.QuotaCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing QuotaCategory.
 */
@Service
@Transactional
public class QuotaCategoryServiceImpl implements QuotaCategoryService{

    private final Logger log = LoggerFactory.getLogger(QuotaCategoryServiceImpl.class);

    private final QuotaCategoryRepository quotaCategoryRepository;

    private final QuotaCategoryMapper quotaCategoryMapper;

    private final QuotaCategorySearchRepository quotaCategorySearchRepository;

    public QuotaCategoryServiceImpl(QuotaCategoryRepository quotaCategoryRepository, QuotaCategoryMapper quotaCategoryMapper, QuotaCategorySearchRepository quotaCategorySearchRepository) {
        this.quotaCategoryRepository = quotaCategoryRepository;
        this.quotaCategoryMapper = quotaCategoryMapper;
        this.quotaCategorySearchRepository = quotaCategorySearchRepository;
    }

    /**
     * Save a quotaCategory.
     *
     * @param quotaCategoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public QuotaCategoryDTO save(QuotaCategoryDTO quotaCategoryDTO) {
        log.debug("Request to save QuotaCategory : {}", quotaCategoryDTO);
        QuotaCategory quotaCategory = quotaCategoryMapper.toEntity(quotaCategoryDTO);
        quotaCategory = quotaCategoryRepository.save(quotaCategory);
        QuotaCategoryDTO result = quotaCategoryMapper.toDto(quotaCategory);
        quotaCategorySearchRepository.save(quotaCategory);
        return result;
    }

    /**
     *  Get all the quotaCategories.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<QuotaCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all QuotaCategories");
        return quotaCategoryRepository.findAll(pageable)
            .map(quotaCategoryMapper::toDto);
    }

    /**
     *  Get one quotaCategory by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public QuotaCategoryDTO findOne(Long id) {
        log.debug("Request to get QuotaCategory : {}", id);
        QuotaCategory quotaCategory = quotaCategoryRepository.findOne(id);
        return quotaCategoryMapper.toDto(quotaCategory);
    }

    /**
     *  Delete the  quotaCategory by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete QuotaCategory : {}", id);
        quotaCategoryRepository.delete(id);
        quotaCategorySearchRepository.delete(id);
    }

    /**
     * Search for the quotaCategory corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<QuotaCategoryDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of QuotaCategories for query {}", query);
        Page<QuotaCategory> result = quotaCategorySearchRepository.search(queryStringQuery(query), pageable);
        return result.map(quotaCategoryMapper::toDto);
    }
}
