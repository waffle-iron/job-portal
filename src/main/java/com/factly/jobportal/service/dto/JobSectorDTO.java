package com.factly.jobportal.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the JobSector entity.
 */
public class JobSectorDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String sector;

    @Size(max = 500)
    private String iconUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JobSectorDTO jobSectorDTO = (JobSectorDTO) o;
        if(jobSectorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jobSectorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JobSectorDTO{" +
            "id=" + getId() +
            ", sector='" + getSector() + "'" +
            ", iconUrl='" + getIconUrl() + "'" +
            "}";
    }
}
