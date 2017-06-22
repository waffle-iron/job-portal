package com.factly.jobportal.service.mapper;

import com.factly.jobportal.domain.*;
import com.factly.jobportal.service.dto.EducationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Education and its DTO EducationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EducationMapper extends EntityMapper <EducationDTO, Education> {
    
    
    default Education fromId(Long id) {
        if (id == null) {
            return null;
        }
        Education education = new Education();
        education.setId(id);
        return education;
    }
}
