package com.factly.jobportal.service.mapper;

import com.factly.jobportal.domain.*;
import com.factly.jobportal.service.dto.LanguageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Language and its DTO LanguageDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LanguageMapper extends EntityMapper <LanguageDTO, Language> {
    
    @Mapping(target = "jobNotificationWrittenExamLanguages", ignore = true)
    @Mapping(target = "jobNotificationLanguageProficiencies", ignore = true)
    Language toEntity(LanguageDTO languageDTO); 
    default Language fromId(Long id) {
        if (id == null) {
            return null;
        }
        Language language = new Language();
        language.setId(id);
        return language;
    }
}
