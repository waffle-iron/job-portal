package com.factly.jobportal.service.impl;

import com.factly.jobportal.domain.JobNotification;
import com.factly.jobportal.repository.JobNotificationRepository;
import com.factly.jobportal.repository.search.JobNotificationSearchRepository;
import com.factly.jobportal.service.JobNotificationService;
import com.factly.jobportal.service.dto.JobListDTO;
import com.factly.jobportal.service.dto.JobNotificationDTO;
import com.factly.jobportal.service.mapper.JobNotificationMapper;
import com.factly.jobportal.web.domain.JobsCount;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static com.factly.jobportal.service.util.ESAggregationUtil.*;

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

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

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

    @Override
    @Transactional(readOnly = true)
    public JobListDTO findJobNotificationsCountByClientTypeAndJobSector(Pageable pageable) {
        log.debug("Request to search for a page of JobNotifications for query {}");

        //QueryBuilder queryBuilder = QueryBuilders
        //    .nestedQuery("clientType", QueryBuilders.matchAllQuery());

        QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
        List<AbstractAggregationBuilder> aggs = createClientTypeAndJobSectorAggregation();


        JobListDTO jobListDTO = findJobNotificationsByDeadlineDate(pageable, queryBuilder, aggs);

        if(aggs != null) {
            iterateReverseNestedAggregationResults(jobListDTO, jobListDTO.getAggregations());
        }

        return jobListDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public JobListDTO findJobNotificationsByClientType(String type, Pageable pageable) {
        log.debug("Request to search for a page of JobNotifications for query {}", type);
        QueryBuilder queryBuilder = QueryBuilders
            .nestedQuery("clientType", QueryBuilders.matchQuery("clientType.type", type));
        List<AbstractAggregationBuilder> aggs = createAggregations();

        JobListDTO jobListDTO = findJobNotificationsByDeadlineDate(pageable, queryBuilder, aggs);
        iterateNestedAggregationResults("", "", jobListDTO, jobListDTO.getAggregations());
        return jobListDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public JobListDTO findJobNotificationsByJobSector(String type, Pageable pageable) {
        log.debug("Request to search for a page of JobNotifications for query {}", type);
        QueryBuilder queryBuilder = QueryBuilders
            .nestedQuery("jobSector", QueryBuilders.matchQuery("jobSector.sector", type));
        List<AbstractAggregationBuilder> aggs = createAggregations();

        JobListDTO jobListDTO = findJobNotificationsByDeadlineDate(pageable, queryBuilder, aggs);

        if(aggs != null) {
            iterateNestedAggregationResults("", "", jobListDTO, jobListDTO.getAggregations());
        }
        return jobListDTO;
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
    public JobListDTO search(String query, Pageable pageable) {
        log.debug("Request to search for a page of JobNotifications for query {}", query);

        if(query.isEmpty()) {
            return findJobNotificationsByDate(pageable);
        } else {
            QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(query, ALL_FIELDS);
            List<AbstractAggregationBuilder> aggs = createAggregations();
            JobListDTO jobListDTO = findJobNotificationsByDeadlineDate(pageable, queryBuilder, aggs);

            if(aggs != null) {
                iterateNestedAggregationResults("", "", jobListDTO, jobListDTO.getAggregations());
            }
            return jobListDTO;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public JobListDTO findJobNotificationsByDate(Pageable pageable) {
        log.debug("Request to search for a page of JobNotifications for query {}");

        QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
        List<AbstractAggregationBuilder> aggs = createAggregations();

        JobListDTO jobListDTO = findJobNotificationsByDeadlineDate(pageable, queryBuilder, aggs);

        if(aggs != null) {
            iterateNestedAggregationResults("", "", jobListDTO, jobListDTO.getAggregations());
        }
        return jobListDTO;
    }

    private JobListDTO findJobNotificationsByDeadlineDate(Pageable pageable,
                                                          QueryBuilder query,
                                                          List<AbstractAggregationBuilder> aggs) {

        JobListDTO jobListDTO = new JobListDTO();

        // Do not show notifications which past the deadline
        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("applicationDeadline");
        rangeQuery.gte(LocalDate.now());

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder()
            .withQuery(query)
            .withPageable(pageable).withFilter(rangeQuery)
            .withSort(
                SortBuilders
                    .fieldSort("notificationDate")
                    .order(SortOrder.DESC));

        if(aggs != null) {
            aggs.forEach(agg -> queryBuilder.addAggregation(agg));
        }
        SearchQuery searchQuery = queryBuilder.build();

        // set notifications
        Page<JobNotification> notifications = jobNotificationSearchRepository.search(searchQuery);
        jobListDTO.setNotificationsPage(notifications.map(jobNotificationMapper::toDto));

        if(aggs != null) {
            // collect the aggregation results
            elasticsearchTemplate.putMapping(JobNotification.class);
            elasticsearchTemplate.refresh(JobNotification.class);
            Aggregations aggregations = elasticsearchTemplate.query(searchQuery,
                response -> response.getAggregations());
            Map<String, Aggregation> result = aggregations.asMap();
            jobListDTO.setAggregations(result);
        }
        return jobListDTO;
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
