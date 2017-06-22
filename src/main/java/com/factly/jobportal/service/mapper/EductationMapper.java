package com.factly.jobportal.service.mapper;

import com.factly.jobportal.domain.*;
import com.factly.jobportal.service.dto.EductationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Eductation and its DTO EductationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EductationMapper extends EntityMapper <EductationDTO, Eductation> {
    
    
    default Eductation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Eductation eductation = new Eductation();
        eductation.setId(id);
        return eductation;
    }
}
