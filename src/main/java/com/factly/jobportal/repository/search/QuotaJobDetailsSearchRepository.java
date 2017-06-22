package com.factly.jobportal.repository.search;

import com.factly.jobportal.domain.QuotaJobDetails;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the QuotaJobDetails entity.
 */
public interface QuotaJobDetailsSearchRepository extends ElasticsearchRepository<QuotaJobDetails, Long> {
}
