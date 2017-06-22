package com.factly.jobportal.service.impl;

import com.factly.jobportal.service.ClientTypeService;
import com.factly.jobportal.domain.ClientType;
import com.factly.jobportal.repository.ClientTypeRepository;
import com.factly.jobportal.repository.search.ClientTypeSearchRepository;
import com.factly.jobportal.service.dto.ClientTypeDTO;
import com.factly.jobportal.service.mapper.ClientTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ClientType.
 */
@Service
@Transactional
public class ClientTypeServiceImpl implements ClientTypeService{

    private final Logger log = LoggerFactory.getLogger(ClientTypeServiceImpl.class);

    private final ClientTypeRepository clientTypeRepository;

    private final ClientTypeMapper clientTypeMapper;

    private final ClientTypeSearchRepository clientTypeSearchRepository;

    public ClientTypeServiceImpl(ClientTypeRepository clientTypeRepository, ClientTypeMapper clientTypeMapper, ClientTypeSearchRepository clientTypeSearchRepository) {
        this.clientTypeRepository = clientTypeRepository;
        this.clientTypeMapper = clientTypeMapper;
        this.clientTypeSearchRepository = clientTypeSearchRepository;
    }

    /**
     * Save a clientType.
     *
     * @param clientTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ClientTypeDTO save(ClientTypeDTO clientTypeDTO) {
        log.debug("Request to save ClientType : {}", clientTypeDTO);
        ClientType clientType = clientTypeMapper.toEntity(clientTypeDTO);
        clientType = clientTypeRepository.save(clientType);
        ClientTypeDTO result = clientTypeMapper.toDto(clientType);
        clientTypeSearchRepository.save(clientType);
        return result;
    }

    /**
     *  Get all the clientTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClientTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClientTypes");
        return clientTypeRepository.findAll(pageable)
            .map(clientTypeMapper::toDto);
    }

    /**
     *  Get one clientType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ClientTypeDTO findOne(Long id) {
        log.debug("Request to get ClientType : {}", id);
        ClientType clientType = clientTypeRepository.findOne(id);
        return clientTypeMapper.toDto(clientType);
    }

    /**
     *  Delete the  clientType by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClientType : {}", id);
        clientTypeRepository.delete(id);
        clientTypeSearchRepository.delete(id);
    }

    /**
     * Search for the clientType corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClientTypeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ClientTypes for query {}", query);
        Page<ClientType> result = clientTypeSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(clientTypeMapper::toDto);
    }
}
