package com.factly.jobportal.repository.search;

import com.factly.jobportal.domain.SelectionProcedure;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SelectionProcedure entity.
 */
public interface SelectionProcedureSearchRepository extends ElasticsearchRepository<SelectionProcedure, Long> {
}
