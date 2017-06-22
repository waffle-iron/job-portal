package com.factly.jobportal.service.impl;

import com.factly.jobportal.service.EductationService;
import com.factly.jobportal.domain.Eductation;
import com.factly.jobportal.repository.EductationRepository;
import com.factly.jobportal.repository.search.EductationSearchRepository;
import com.factly.jobportal.service.dto.EductationDTO;
import com.factly.jobportal.service.mapper.EductationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Eductation.
 */
@Service
@Transactional
public class EductationServiceImpl implements EductationService{

    private final Logger log = LoggerFactory.getLogger(EductationServiceImpl.class);

    private final EductationRepository eductationRepository;

    private final EductationMapper eductationMapper;

    private final EductationSearchRepository eductationSearchRepository;

    public EductationServiceImpl(EductationRepository eductationRepository, EductationMapper eductationMapper, EductationSearchRepository eductationSearchRepository) {
        this.eductationRepository = eductationRepository;
        this.eductationMapper = eductationMapper;
        this.eductationSearchRepository = eductationSearchRepository;
    }

    /**
     * Save a eductation.
     *
     * @param eductationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EductationDTO save(EductationDTO eductationDTO) {
        log.debug("Request to save Eductation : {}", eductationDTO);
        Eductation eductation = eductationMapper.toEntity(eductationDTO);
        eductation = eductationRepository.save(eductation);
        EductationDTO result = eductationMapper.toDto(eductation);
        eductationSearchRepository.save(eductation);
        return result;
    }

    /**
     *  Get all the eductations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EductationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Eductations");
        return eductationRepository.findAll(pageable)
            .map(eductationMapper::toDto);
    }

    /**
     *  Get one eductation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EductationDTO findOne(Long id) {
        log.debug("Request to get Eductation : {}", id);
        Eductation eductation = eductationRepository.findOne(id);
        return eductationMapper.toDto(eductation);
    }

    /**
     *  Delete the  eductation by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Eductation : {}", id);
        eductationRepository.delete(id);
        eductationSearchRepository.delete(id);
    }

    /**
     * Search for the eductation corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EductationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Eductations for query {}", query);
        Page<Eductation> result = eductationSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(eductationMapper::toDto);
    }
}
