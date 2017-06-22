package com.factly.jobportal.service.mapper;

import com.factly.jobportal.domain.*;
import com.factly.jobportal.service.dto.JobTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity JobType and its DTO JobTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface JobTypeMapper extends EntityMapper <JobTypeDTO, JobType> {
    
    @Mapping(target = "jobNotifications", ignore = true)
    JobType toEntity(JobTypeDTO jobTypeDTO); 
    default JobType fromId(Long id) {
        if (id == null) {
            return null;
        }
        JobType jobType = new JobType();
        jobType.setId(id);
        return jobType;
    }
}
