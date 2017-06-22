package com.factly.jobportal.service;

import com.factly.jobportal.service.dto.TestSkillDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing TestSkill.
 */
public interface TestSkillService {

    /**
     * Save a testSkill.
     *
     * @param testSkillDTO the entity to save
     * @return the persisted entity
     */
    TestSkillDTO save(TestSkillDTO testSkillDTO);

    /**
     *  Get all the testSkills.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TestSkillDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" testSkill.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TestSkillDTO findOne(Long id);

    /**
     *  Delete the "id" testSkill.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the testSkill corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TestSkillDTO> search(String query, Pageable pageable);
}
