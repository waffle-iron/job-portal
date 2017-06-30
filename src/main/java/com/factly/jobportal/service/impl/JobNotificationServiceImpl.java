package com.factly.jobportal.service.impl;

import com.factly.jobportal.domain.JobNotification;
import com.factly.jobportal.repository.JobNotificationRepository;
import com.factly.jobportal.repository.search.JobNotificationSearchRepository;
import com.factly.jobportal.service.JobNotificationService;
import com.factly.jobportal.service.dto.JobNotificationDTO;
import com.factly.jobportal.service.mapper.JobNotificationMapper;
import com.factly.jobportal.web.domain.JobsCount;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

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

    private final String ALL_FIELDS = "_all";

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
        QueryStringQueryBuilder queryBuilder = queryStringQuery(query);

        /* For now comment this straight forward approach, we might use this for other scenarios
        Page<JobNotification> result = jobNotificationSearchRepository.search(queryBuilder, pageable);
        return result.map(jobNotificationMapper::toDto);*/
        if(query.isEmpty()) {
            query = null;
        }

        Page<JobNotification> result = findApplicableJobsByNotificationDate(pageable, query);
        return result.map(jobNotificationMapper::toDto);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<JobNotificationDTO> findJobByClientType(String type, Pageable pageable) {
        log.debug("Request to search for a page of JobNotifications for query {}", type);
//        QueryStringQueryBuilder queryBuilder = queryStringQuery(query).field("clientType.type");
//        Page<JobNotification> result = jobNotificationSearchRepository.search(queryBuilder, pageable);

        Page<JobNotification> result = jobNotificationSearchRepository.findByClientTypeType(type, pageable);
        return result.map(jobNotificationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<JobNotificationDTO> findJobsByNotificationDate(Pageable pageable) {
        log.debug("Request to search for a page of JobNotifications for query {}");

        Page<JobNotification> result = findApplicableJobsByNotificationDate(pageable, null);
        return result.map(jobNotificationMapper::toDto);
    }

    private Page<JobNotification> findApplicableJobsByNotificationDate(Pageable pageable, String queryString) {
        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("applicationDeadline");
        rangeQuery.gte(LocalDate.now());

        QueryBuilder query = null;
        if(queryString == null) {
            query = QueryBuilders.matchAllQuery();
        } else {
            query = QueryBuilders.multiMatchQuery(queryString, ALL_FIELDS);
        }

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
            .withQuery(query)
            .withPageable(pageable).withFilter(rangeQuery)
            .withSort(
                SortBuilders
                    .fieldSort("notificationDate")
                    .order(SortOrder.DESC))
            .build();

        return jobNotificationSearchRepository.search(searchQuery);
    }

    //Find Notification with job id
    @Override
    @Transactional(readOnly = true)
    public JobNotificationDTO findJobNotificationById(Long id) {
        log.debug("Request to get JobNotification : {}", id);
        JobNotification jobNotification = jobNotificationSearchRepository.findOne(id);
        return jobNotificationMapper.toDto(jobNotification);
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
