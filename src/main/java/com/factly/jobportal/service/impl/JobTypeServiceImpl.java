package com.factly.jobportal.service.impl;

import com.factly.jobportal.service.JobTypeService;
import com.factly.jobportal.domain.JobType;
import com.factly.jobportal.repository.JobTypeRepository;
import com.factly.jobportal.repository.search.JobTypeSearchRepository;
import com.factly.jobportal.service.dto.JobTypeDTO;
import com.factly.jobportal.service.mapper.JobTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing JobType.
 */
@Service
@Transactional
public class JobTypeServiceImpl implements JobTypeService{

    private final Logger log = LoggerFactory.getLogger(JobTypeServiceImpl.class);

    private final JobTypeRepository jobTypeRepository;

    private final JobTypeMapper jobTypeMapper;

    private final JobTypeSearchRepository jobTypeSearchRepository;

    public JobTypeServiceImpl(JobTypeRepository jobTypeRepository, JobTypeMapper jobTypeMapper, JobTypeSearchRepository jobTypeSearchRepository) {
        this.jobTypeRepository = jobTypeRepository;
        this.jobTypeMapper = jobTypeMapper;
        this.jobTypeSearchRepository = jobTypeSearchRepository;
    }

    /**
     * Save a jobType.
     *
     * @param jobTypeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public JobTypeDTO save(JobTypeDTO jobTypeDTO) {
        log.debug("Request to save JobType : {}", jobTypeDTO);
        JobType jobType = jobTypeMapper.toEntity(jobTypeDTO);
        jobType = jobTypeRepository.save(jobType);
        JobTypeDTO result = jobTypeMapper.toDto(jobType);
        jobTypeSearchRepository.save(jobType);
        return result;
    }

    /**
     *  Get all the jobTypes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<JobTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all JobTypes");
        return jobTypeRepository.findAll(pageable)
            .map(jobTypeMapper::toDto);
    }

    /**
     *  Get one jobType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public JobTypeDTO findOne(Long id) {
        log.debug("Request to get JobType : {}", id);
        JobType jobType = jobTypeRepository.findOne(id);
        return jobTypeMapper.toDto(jobType);
    }

    /**
     *  Delete the  jobType by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete JobType : {}", id);
        jobTypeRepository.delete(id);
        jobTypeSearchRepository.delete(id);
    }

    /**
     * Search for the jobType corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<JobTypeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of JobTypes for query {}", query);
        Page<JobType> result = jobTypeSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(jobTypeMapper::toDto);
    }
}
