package com.factly.jobportal.service.mapper;

import com.factly.jobportal.domain.*;
import com.factly.jobportal.service.dto.QuotaCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity QuotaCategory and its DTO QuotaCategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface QuotaCategoryMapper extends EntityMapper <QuotaCategoryDTO, QuotaCategory> {
    
    
    default QuotaCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        QuotaCategory quotaCategory = new QuotaCategory();
        quotaCategory.setId(id);
        return quotaCategory;
    }
}
