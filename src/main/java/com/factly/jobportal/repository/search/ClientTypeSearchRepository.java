package com.factly.jobportal.repository.search;

import com.factly.jobportal.domain.ClientType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ClientType entity.
 */
public interface ClientTypeSearchRepository extends ElasticsearchRepository<ClientType, Long> {
}
