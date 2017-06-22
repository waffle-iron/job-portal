package com.factly.jobportal.repository;

import com.factly.jobportal.domain.TestSkill;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TestSkill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TestSkillRepository extends JpaRepository<TestSkill,Long> {
    
}
