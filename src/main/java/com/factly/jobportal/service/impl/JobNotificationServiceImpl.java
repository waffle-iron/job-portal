package com.factly.jobportal.service.impl;

import com.factly.jobportal.service.JobNotificationService;
import com.factly.jobportal.domain.JobNotification;
import com.factly.jobportal.repository.JobNotificationRepository;
import com.factly.jobportal.repository.search.JobNotificationSearchRepository;
import com.factly.jobportal.service.dto.JobNotificationDTO;
import com.factly.jobportal.service.mapper.JobNotificationMapper;
import com.factly.jobportal.web.domain.JobsCount;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing JobNotification.
 */
@Service
@Transactional
public class JobNotificationServiceImpl implements JobNotificationService{

    private final Logger log = LoggerFactory.getLogger(JobNotificationServiceImpl.class);

    private final JobNotificationRepository jobNotificationRepository;

    private final JobNotificationMapper jobNotificationMapper;

    private final JobNotificationSearchRepository jobNotificationSearchRepository;

    public JobNotificationServiceImpl(JobNotificationRepository jobNotificationRepository, JobNotificationMapper jobNotificationMapper, JobNotificationSearchRepository jobNotificationSearchRepository) {
        this.jobNotificationRepository = jobNotificationRepository;
        this.jobNotificationMapper = jobNotificationMapper;
        this.jobNotificationSearchRepository = jobNotificationSearchRepository;
    }

    /**
     * Save a jobNotification.
     *
     * @param jobNotificationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public JobNotificationDTO save(JobNotificationDTO jobNotificationDTO) {
        log.debug("Request to save JobNotification : {}", jobNotificationDTO);
        JobNotification jobNotification = jobNotificationMapper.toEntity(jobNotificationDTO);
        jobNotification = jobNotificationRepository.save(jobNotification);
        JobNotificationDTO result = jobNotificationMapper.toDto(jobNotification);
        jobNotificationSearchRepository.save(jobNotification);
        return result;
    }

    /**
     *  Get all the jobNotifications.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<JobNotificationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all JobNotifications");
        return jobNotificationRepository.findAll(pageable)
            .map(jobNotificationMapper::toDto);
    }

    /**
     *  Get one jobNotification by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public JobNotificationDTO findOne(Long id) {
        log.debug("Request to get JobNotification : {}", id);
        JobNotification jobNotification = jobNotificationRepository.findOneWithEagerRelationships(id);
        return jobNotificationMapper.toDto(jobNotification);
    }

    /**
     *  Delete the  jobNotification by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete JobNotification : {}", id);
        jobNotificationRepository.delete(id);
        jobNotificationSearchRepository.delete(id);
    }

    /**
     * Search for the jobNotification corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<JobNotificationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of JobNotifications for query {}", query);
        Page<JobNotification> result = jobNotificationSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(jobNotificationMapper::toDto);
    }

    /**
     * get job notification counts based on jobtype
     */
    @Override
    @Transactional(readOnly = true)
    public JobsCount findJobsCount(String jobType) {
        return jobNotificationRepository.retrieveJobsCount(jobType);
    }

    /**
     * get job notification counts based on sector
     */
    @Override
    @Transactional(readOnly = true)
    public JobsCount findSectorJobsCount(String sector) {
        return jobNotificationRepository.retrieveSectorJobsCount(sector);
    }
}
