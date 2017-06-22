package com.factly.jobportal.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the QuotaCategory entity.
 */
public class QuotaCategoryDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        QuotaCategoryDTO quotaCategoryDTO = (QuotaCategoryDTO) o;
        if(quotaCategoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), quotaCategoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuotaCategoryDTO{" +
            "id=" + getId() +
            ", category='" + getCategory() + "'" +
            "}";
    }
}
