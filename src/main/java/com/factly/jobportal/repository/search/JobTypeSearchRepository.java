package com.factly.jobportal.repository.search;

import com.factly.jobportal.domain.JobType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the JobType entity.
 */
public interface JobTypeSearchRepository extends ElasticsearchRepository<JobType, Long> {
}
