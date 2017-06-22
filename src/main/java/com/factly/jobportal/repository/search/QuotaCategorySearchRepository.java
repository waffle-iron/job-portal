package com.factly.jobportal.repository.search;

import com.factly.jobportal.domain.QuotaCategory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the QuotaCategory entity.
 */
public interface QuotaCategorySearchRepository extends ElasticsearchRepository<QuotaCategory, Long> {
}
