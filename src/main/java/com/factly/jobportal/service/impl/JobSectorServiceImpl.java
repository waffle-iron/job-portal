package com.factly.jobportal.service.impl;

import com.factly.jobportal.service.JobSectorService;
import com.factly.jobportal.domain.JobSector;
import com.factly.jobportal.repository.JobSectorRepository;
import com.factly.jobportal.repository.search.JobSectorSearchRepository;
import com.factly.jobportal.service.dto.JobSectorDTO;
import com.factly.jobportal.service.mapper.JobSectorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing JobSector.
 */
@Service
@Transactional
public class JobSectorServiceImpl implements JobSectorService{

    private final Logger log = LoggerFactory.getLogger(JobSectorServiceImpl.class);

    private final JobSectorRepository jobSectorRepository;

    private final JobSectorMapper jobSectorMapper;

    private final JobSectorSearchRepository jobSectorSearchRepository;

    public JobSectorServiceImpl(JobSectorRepository jobSectorRepository, JobSectorMapper jobSectorMapper, JobSectorSearchRepository jobSectorSearchRepository) {
        this.jobSectorRepository = jobSectorRepository;
        this.jobSectorMapper = jobSectorMapper;
        this.jobSectorSearchRepository = jobSectorSearchRepository;
    }

    /**
     * Save a jobSector.
     *
     * @param jobSectorDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public JobSectorDTO save(JobSectorDTO jobSectorDTO) {
        log.debug("Request to save JobSector : {}", jobSectorDTO);
        JobSector jobSector = jobSectorMapper.toEntity(jobSectorDTO);
        jobSector = jobSectorRepository.save(jobSector);
        JobSectorDTO result = jobSectorMapper.toDto(jobSector);
        jobSectorSearchRepository.save(jobSector);
        return result;
    }

    /**
     *  Get all the jobSectors.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<JobSectorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all JobSectors");
        return jobSectorRepository.findAll(pageable)
            .map(jobSectorMapper::toDto);
    }

    /**
     *  Get one jobSector by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public JobSectorDTO findOne(Long id) {
        log.debug("Request to get JobSector : {}", id);
        JobSector jobSector = jobSectorRepository.findOne(id);
        return jobSectorMapper.toDto(jobSector);
    }

    /**
     *  Delete the  jobSector by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete JobSector : {}", id);
        jobSectorRepository.delete(id);
        jobSectorSearchRepository.delete(id);
    }

    /**
     * Search for the jobSector corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<JobSectorDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of JobSectors for query {}", query);
        Page<JobSector> result = jobSectorSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(jobSectorMapper::toDto);
    }
}
