package com.factly.jobportal.service.impl;

import com.factly.jobportal.service.QuotaJobDetailsService;
import com.factly.jobportal.domain.QuotaJobDetails;
import com.factly.jobportal.repository.QuotaJobDetailsRepository;
import com.factly.jobportal.repository.search.QuotaJobDetailsSearchRepository;
import com.factly.jobportal.service.dto.QuotaJobDetailsDTO;
import com.factly.jobportal.service.mapper.QuotaJobDetailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing QuotaJobDetails.
 */
@Service
@Transactional
public class QuotaJobDetailsServiceImpl implements QuotaJobDetailsService{

    private final Logger log = LoggerFactory.getLogger(QuotaJobDetailsServiceImpl.class);

    private final QuotaJobDetailsRepository quotaJobDetailsRepository;

    private final QuotaJobDetailsMapper quotaJobDetailsMapper;

    private final QuotaJobDetailsSearchRepository quotaJobDetailsSearchRepository;

    public QuotaJobDetailsServiceImpl(QuotaJobDetailsRepository quotaJobDetailsRepository, QuotaJobDetailsMapper quotaJobDetailsMapper, QuotaJobDetailsSearchRepository quotaJobDetailsSearchRepository) {
        this.quotaJobDetailsRepository = quotaJobDetailsRepository;
        this.quotaJobDetailsMapper = quotaJobDetailsMapper;
        this.quotaJobDetailsSearchRepository = quotaJobDetailsSearchRepository;
    }

    /**
     * Save a quotaJobDetails.
     *
     * @param quotaJobDetailsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public QuotaJobDetailsDTO save(QuotaJobDetailsDTO quotaJobDetailsDTO) {
        log.debug("Request to save QuotaJobDetails : {}", quotaJobDetailsDTO);
        QuotaJobDetails quotaJobDetails = quotaJobDetailsMapper.toEntity(quotaJobDetailsDTO);
        quotaJobDetails = quotaJobDetailsRepository.save(quotaJobDetails);
        QuotaJobDetailsDTO result = quotaJobDetailsMapper.toDto(quotaJobDetails);
        quotaJobDetailsSearchRepository.save(quotaJobDetails);
        return result;
    }

    /**
     *  Get all the quotaJobDetails.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<QuotaJobDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all QuotaJobDetails");
        return quotaJobDetailsRepository.findAll(pageable)
            .map(quotaJobDetailsMapper::toDto);
    }

    /**
     *  Get one quotaJobDetails by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public QuotaJobDetailsDTO findOne(Long id) {
        log.debug("Request to get QuotaJobDetails : {}", id);
        QuotaJobDetails quotaJobDetails = quotaJobDetailsRepository.findOne(id);
        return quotaJobDetailsMapper.toDto(quotaJobDetails);
    }

    /**
     *  Delete the  quotaJobDetails by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete QuotaJobDetails : {}", id);
        quotaJobDetailsRepository.delete(id);
        quotaJobDetailsSearchRepository.delete(id);
    }

    /**
     * Search for the quotaJobDetails corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<QuotaJobDetailsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of QuotaJobDetails for query {}", query);
        Page<QuotaJobDetails> result = quotaJobDetailsSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(quotaJobDetailsMapper::toDto);
    }
}
