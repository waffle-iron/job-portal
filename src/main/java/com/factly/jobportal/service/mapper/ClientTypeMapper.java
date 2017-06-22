package com.factly.jobportal.service.mapper;

import com.factly.jobportal.domain.*;
import com.factly.jobportal.service.dto.ClientTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ClientType and its DTO ClientTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClientTypeMapper extends EntityMapper <ClientTypeDTO, ClientType> {
    
    
    default ClientType fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClientType clientType = new ClientType();
        clientType.setId(id);
        return clientType;
    }
}
