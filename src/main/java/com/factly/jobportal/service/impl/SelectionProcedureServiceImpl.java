package com.factly.jobportal.service.impl;

import com.factly.jobportal.service.SelectionProcedureService;
import com.factly.jobportal.domain.SelectionProcedure;
import com.factly.jobportal.repository.SelectionProcedureRepository;
import com.factly.jobportal.repository.search.SelectionProcedureSearchRepository;
import com.factly.jobportal.service.dto.SelectionProcedureDTO;
import com.factly.jobportal.service.mapper.SelectionProcedureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing SelectionProcedure.
 */
@Service
@Transactional
public class SelectionProcedureServiceImpl implements SelectionProcedureService{

    private final Logger log = LoggerFactory.getLogger(SelectionProcedureServiceImpl.class);

    private final SelectionProcedureRepository selectionProcedureRepository;

    private final SelectionProcedureMapper selectionProcedureMapper;

    private final SelectionProcedureSearchRepository selectionProcedureSearchRepository;

    public SelectionProcedureServiceImpl(SelectionProcedureRepository selectionProcedureRepository, SelectionProcedureMapper selectionProcedureMapper, SelectionProcedureSearchRepository selectionProcedureSearchRepository) {
        this.selectionProcedureRepository = selectionProcedureRepository;
        this.selectionProcedureMapper = selectionProcedureMapper;
        this.selectionProcedureSearchRepository = selectionProcedureSearchRepository;
    }

    /**
     * Save a selectionProcedure.
     *
     * @param selectionProcedureDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SelectionProcedureDTO save(SelectionProcedureDTO selectionProcedureDTO) {
        log.debug("Request to save SelectionProcedure : {}", selectionProcedureDTO);
        SelectionProcedure selectionProcedure = selectionProcedureMapper.toEntity(selectionProcedureDTO);
        selectionProcedure = selectionProcedureRepository.save(selectionProcedure);
        SelectionProcedureDTO result = selectionProcedureMapper.toDto(selectionProcedure);
        selectionProcedureSearchRepository.save(selectionProcedure);
        return result;
    }

    /**
     *  Get all the selectionProcedures.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SelectionProcedureDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SelectionProcedures");
        return selectionProcedureRepository.findAll(pageable)
            .map(selectionProcedureMapper::toDto);
    }

    /**
     *  Get one selectionProcedure by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SelectionProcedureDTO findOne(Long id) {
        log.debug("Request to get SelectionProcedure : {}", id);
        SelectionProcedure selectionProcedure = selectionProcedureRepository.findOne(id);
        return selectionProcedureMapper.toDto(selectionProcedure);
    }

    /**
     *  Delete the  selectionProcedure by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SelectionProcedure : {}", id);
        selectionProcedureRepository.delete(id);
        selectionProcedureSearchRepository.delete(id);
    }

    /**
     * Search for the selectionProcedure corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SelectionProcedureDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SelectionProcedures for query {}", query);
        Page<SelectionProcedure> result = selectionProcedureSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(selectionProcedureMapper::toDto);
    }
}
