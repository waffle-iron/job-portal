package com.factly.jobportal.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SelectionProcedure entity.
 */
public class SelectionProcedureDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String procedure;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SelectionProcedureDTO selectionProcedureDTO = (SelectionProcedureDTO) o;
        if(selectionProcedureDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), selectionProcedureDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SelectionProcedureDTO{" +
            "id=" + getId() +
            ", procedure='" + getProcedure() + "'" +
            "}";
    }
}
