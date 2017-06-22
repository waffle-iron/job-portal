package com.factly.jobportal.service.mapper;

import com.factly.jobportal.domain.*;
import com.factly.jobportal.service.dto.JobNotificationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity JobNotification and its DTO JobNotificationDTO.
 */
@Mapper(componentModel = "spring", uses = {ClientTypeMapper.class, JobSectorMapper.class, JobTypeMapper.class, EducationMapper.class, TestSkillMapper.class, LanguageMapper.class, SelectionProcedureMapper.class, })
public interface JobNotificationMapper extends EntityMapper <JobNotificationDTO, JobNotification> {

    @Mapping(source = "clientType.id", target = "clientTypeId")
    @Mapping(source = "clientType.type", target = "clientTypeType")

    @Mapping(source = "jobSector.id", target = "jobSectorId")
    @Mapping(source = "jobSector.sector", target = "jobSectorSector")

    @Mapping(source = "jobType.id", target = "jobTypeId")
    @Mapping(source = "jobType.type", target = "jobTypeType")

    @Mapping(source = "education.id", target = "educationId")
    @Mapping(source = "education.education", target = "educationEducation")
    JobNotificationDTO toDto(JobNotification jobNotification); 

    @Mapping(source = "clientTypeId", target = "clientType")

    @Mapping(source = "jobSectorId", target = "jobSector")

    @Mapping(source = "jobTypeId", target = "jobType")

    @Mapping(source = "educationId", target = "education")
    @Mapping(target = "quotaJobDetails", ignore = true)
    JobNotification toEntity(JobNotificationDTO jobNotificationDTO); 
    default JobNotification fromId(Long id) {
        if (id == null) {
            return null;
        }
        JobNotification jobNotification = new JobNotification();
        jobNotification.setId(id);
        return jobNotification;
    }
}
