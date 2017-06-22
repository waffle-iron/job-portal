package com.factly.jobportal.repository.search;

import com.factly.jobportal.domain.Eductation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Eductation entity.
 */
public interface EductationSearchRepository extends ElasticsearchRepository<Eductation, Long> {
}
