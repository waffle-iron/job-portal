package com.factly.jobportal.service.mapper;

import com.factly.jobportal.domain.*;
import com.factly.jobportal.service.dto.TestSkillDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TestSkill and its DTO TestSkillDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TestSkillMapper extends EntityMapper <TestSkillDTO, TestSkill> {
    
    
    default TestSkill fromId(Long id) {
        if (id == null) {
            return null;
        }
        TestSkill testSkill = new TestSkill();
        testSkill.setId(id);
        return testSkill;
    }
}
