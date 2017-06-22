package com.factly.jobportal.repository.search;

import com.factly.jobportal.domain.JobNotification;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the JobNotification entity.
 */
public interface JobNotificationSearchRepository extends ElasticsearchRepository<JobNotification, Long> {
}
