package com.factly.jobportal.service.mapper;

import com.factly.jobportal.domain.*;
import com.factly.jobportal.service.dto.SelectionProcedureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SelectionProcedure and its DTO SelectionProcedureDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SelectionProcedureMapper extends EntityMapper <SelectionProcedureDTO, SelectionProcedure> {
    
    
    default SelectionProcedure fromId(Long id) {
        if (id == null) {
            return null;
        }
        SelectionProcedure selectionProcedure = new SelectionProcedure();
        selectionProcedure.setId(id);
        return selectionProcedure;
    }
}
