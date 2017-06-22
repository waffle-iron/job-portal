package com.factly.jobportal.service.mapper;

import com.factly.jobportal.domain.*;
import com.factly.jobportal.service.dto.JobSectorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity JobSector and its DTO JobSectorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface JobSectorMapper extends EntityMapper <JobSectorDTO, JobSector> {
    
    
    default JobSector fromId(Long id) {
        if (id == null) {
            return null;
        }
        JobSector jobSector = new JobSector();
        jobSector.setId(id);
        return jobSector;
    }
}
