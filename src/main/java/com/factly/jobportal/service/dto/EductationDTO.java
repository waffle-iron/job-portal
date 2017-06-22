package com.factly.jobportal.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Eductation entity.
 */
public class EductationDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String education;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EductationDTO eductationDTO = (EductationDTO) o;
        if(eductationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eductationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EductationDTO{" +
            "id=" + getId() +
            ", education='" + getEducation() + "'" +
            "}";
    }
}
