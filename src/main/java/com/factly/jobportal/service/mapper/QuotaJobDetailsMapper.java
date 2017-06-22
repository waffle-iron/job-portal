package com.factly.jobportal.service.mapper;

import com.factly.jobportal.domain.*;
import com.factly.jobportal.service.dto.QuotaJobDetailsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity QuotaJobDetails and its DTO QuotaJobDetailsDTO.
 */
@Mapper(componentModel = "spring", uses = {QuotaCategoryMapper.class, JobNotificationMapper.class, })
public interface QuotaJobDetailsMapper extends EntityMapper <QuotaJobDetailsDTO, QuotaJobDetails> {

    @Mapping(source = "quotaCategory.id", target = "quotaCategoryId")
    @Mapping(source = "quotaCategory.category", target = "quotaCategoryCategory")

    @Mapping(source = "jobNotification.id", target = "jobNotificationId")
    QuotaJobDetailsDTO toDto(QuotaJobDetails quotaJobDetails); 

    @Mapping(source = "quotaCategoryId", target = "quotaCategory")

    @Mapping(source = "jobNotificationId", target = "jobNotification")
    QuotaJobDetails toEntity(QuotaJobDetailsDTO quotaJobDetailsDTO); 
    default QuotaJobDetails fromId(Long id) {
        if (id == null) {
            return null;
        }
        QuotaJobDetails quotaJobDetails = new QuotaJobDetails();
        quotaJobDetails.setId(id);
        return quotaJobDetails;
    }
}
