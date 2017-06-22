package com.factly.jobportal.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A QuotaJobDetails.
 */
@Entity
@Table(name = "quota_job_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "quotajobdetails")
public class QuotaJobDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Min(value = 0)
    @Column(name = "vacancy_count", nullable = false)
    private Integer vacancyCount;

    @NotNull
    @Column(name = "born_before", nullable = false)
    private LocalDate bornBefore;

    @NotNull
    @Column(name = "born_after", nullable = false)
    private LocalDate bornAfter;

    @ManyToOne(optional = false)
    @NotNull
    private QuotaCategory quotaCategory;

    @ManyToOne(optional = false)
    @NotNull
    private JobNotification jobNotification;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVacancyCount() {
        return vacancyCount;
    }

    public QuotaJobDetails vacancyCount(Integer vacancyCount) {
        this.vacancyCount = vacancyCount;
        return this;
    }

    public void setVacancyCount(Integer vacancyCount) {
        this.vacancyCount = vacancyCount;
    }

    public LocalDate getBornBefore() {
        return bornBefore;
    }

    public QuotaJobDetails bornBefore(LocalDate bornBefore) {
        this.bornBefore = bornBefore;
        return this;
    }

    public void setBornBefore(LocalDate bornBefore) {
        this.bornBefore = bornBefore;
    }

    public LocalDate getBornAfter() {
        return bornAfter;
    }

    public QuotaJobDetails bornAfter(LocalDate bornAfter) {
        this.bornAfter = bornAfter;
        return this;
    }

    public void setBornAfter(LocalDate bornAfter) {
        this.bornAfter = bornAfter;
    }

    public QuotaCategory getQuotaCategory() {
        return quotaCategory;
    }

    public QuotaJobDetails quotaCategory(QuotaCategory quotaCategory) {
        this.quotaCategory = quotaCategory;
        return this;
    }

    public void setQuotaCategory(QuotaCategory quotaCategory) {
        this.quotaCategory = quotaCategory;
    }

    public JobNotification getJobNotification() {
        return jobNotification;
    }

    public QuotaJobDetails jobNotification(JobNotification jobNotification) {
        this.jobNotification = jobNotification;
        return this;
    }

    public void setJobNotification(JobNotification jobNotification) {
        this.jobNotification = jobNotification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QuotaJobDetails quotaJobDetails = (QuotaJobDetails) o;
        if (quotaJobDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), quotaJobDetails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuotaJobDetails{" +
            "id=" + getId() +
            ", vacancyCount='" + getVacancyCount() + "'" +
            ", bornBefore='" + getBornBefore() + "'" +
            ", bornAfter='" + getBornAfter() + "'" +
            "}";
    }
}
