package com.factly.jobportal.service.impl;

import com.factly.jobportal.service.TestSkillService;
import com.factly.jobportal.domain.TestSkill;
import com.factly.jobportal.repository.TestSkillRepository;
import com.factly.jobportal.repository.search.TestSkillSearchRepository;
import com.factly.jobportal.service.dto.TestSkillDTO;
import com.factly.jobportal.service.mapper.TestSkillMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing TestSkill.
 */
@Service
@Transactional
public class TestSkillServiceImpl implements TestSkillService{

    private final Logger log = LoggerFactory.getLogger(TestSkillServiceImpl.class);

    private final TestSkillRepository testSkillRepository;

    private final TestSkillMapper testSkillMapper;

    private final TestSkillSearchRepository testSkillSearchRepository;

    public TestSkillServiceImpl(TestSkillRepository testSkillRepository, TestSkillMapper testSkillMapper, TestSkillSearchRepository testSkillSearchRepository) {
        this.testSkillRepository = testSkillRepository;
        this.testSkillMapper = testSkillMapper;
        this.testSkillSearchRepository = testSkillSearchRepository;
    }

    /**
     * Save a testSkill.
     *
     * @param testSkillDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TestSkillDTO save(TestSkillDTO testSkillDTO) {
        log.debug("Request to save TestSkill : {}", testSkillDTO);
        TestSkill testSkill = testSkillMapper.toEntity(testSkillDTO);
        testSkill = testSkillRepository.save(testSkill);
        TestSkillDTO result = testSkillMapper.toDto(testSkill);
        testSkillSearchRepository.save(testSkill);
        return result;
    }

    /**
     *  Get all the testSkills.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TestSkillDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TestSkills");
        return testSkillRepository.findAll(pageable)
            .map(testSkillMapper::toDto);
    }

    /**
     *  Get one testSkill by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TestSkillDTO findOne(Long id) {
        log.debug("Request to get TestSkill : {}", id);
        TestSkill testSkill = testSkillRepository.findOne(id);
        return testSkillMapper.toDto(testSkill);
    }

    /**
     *  Delete the  testSkill by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TestSkill : {}", id);
        testSkillRepository.delete(id);
        testSkillSearchRepository.delete(id);
    }

    /**
     * Search for the testSkill corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TestSkillDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TestSkills for query {}", query);
        Page<TestSkill> result = testSkillSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(testSkillMapper::toDto);
    }
}
