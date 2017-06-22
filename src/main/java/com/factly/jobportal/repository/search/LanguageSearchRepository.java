package com.factly.jobportal.repository.search;

import com.factly.jobportal.domain.Language;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Language entity.
 */
public interface LanguageSearchRepository extends ElasticsearchRepository<Language, Long> {
}
