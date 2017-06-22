package com.factly.jobportal.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the QuotaJobDetails entity.
 */
public class QuotaJobDetailsDTO implements Serializable {

    private Long id;

    @NotNull
    @Min(value = 0)
    private Integer vacancyCount;

    @NotNull
    private LocalDate bornBefore;

    @NotNull
    private LocalDate bornAfter;

    private Long quotaCategoryId;

    private String quotaCategoryCategory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVacancyCount() {
        return vacancyCount;
    }

    public void setVacancyCount(Integer vacancyCount) {
        this.vacancyCount = vacancyCount;
    }

    public LocalDate getBornBefore() {
        return bornBefore;
    }

    public void setBornBefore(LocalDate bornBefore) {
        this.bornBefore = bornBefore;
    }

    public LocalDate getBornAfter() {
        return bornAfter;
    }

    public void setBornAfter(LocalDate bornAfter) {
        this.bornAfter = bornAfter;
    }

    public Long getQuotaCategoryId() {
        return quotaCategoryId;
    }

    public void setQuotaCategoryId(Long quotaCategoryId) {
        this.quotaCategoryId = quotaCategoryId;
    }

    public String getQuotaCategoryCategory() {
        return quotaCategoryCategory;
    }

    public void setQuotaCategoryCategory(String quotaCategoryCategory) {
        this.quotaCategoryCategory = quotaCategoryCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        QuotaJobDetailsDTO quotaJobDetailsDTO = (QuotaJobDetailsDTO) o;
        if(quotaJobDetailsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), quotaJobDetailsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuotaJobDetailsDTO{" +
            "id=" + getId() +
            ", vacancyCount='" + getVacancyCount() + "'" +
            ", bornBefore='" + getBornBefore() + "'" +
            ", bornAfter='" + getBornAfter() + "'" +
            "}";
    }
}
