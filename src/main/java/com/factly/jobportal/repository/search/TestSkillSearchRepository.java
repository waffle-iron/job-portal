package com.factly.jobportal.repository.search;

import com.factly.jobportal.domain.TestSkill;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TestSkill entity.
 */
public interface TestSkillSearchRepository extends ElasticsearchRepository<TestSkill, Long> {
}
