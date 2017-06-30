package com.factly.jobportal.repository.search;

import com.factly.jobportal.domain.ClientType;
import com.factly.jobportal.domain.JobNotification;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the JobNotification entity.
 */
public interface JobNotificationSearchRepository extends ElasticsearchRepository<JobNotification, Long> {

    Page<JobNotification> findByClientTypeType(String type, Pageable pageable);

}
