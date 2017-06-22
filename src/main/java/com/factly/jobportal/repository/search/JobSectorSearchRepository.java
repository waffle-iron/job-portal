package com.factly.jobportal.repository.search;

import com.factly.jobportal.domain.JobSector;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the JobSector entity.
 */
public interface JobSectorSearchRepository extends ElasticsearchRepository<JobSector, Long> {
}
