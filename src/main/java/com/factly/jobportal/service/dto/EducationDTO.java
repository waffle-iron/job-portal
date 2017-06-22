package com.factly.jobportal.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Education entity.
 */
public class EducationDTO implements Serializable {

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

        EducationDTO educationDTO = (EducationDTO) o;
        if(educationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), educationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EducationDTO{" +
            "id=" + getId() +
            ", education='" + getEducation() + "'" +
            "}";
    }
}
