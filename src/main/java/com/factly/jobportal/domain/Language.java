package com.factly.jobportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Language.
 */
@Entity
@Table(name = "language")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "language")
public class Language implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "language", length = 100, nullable = false)
    private String language;

    @ManyToMany(mappedBy = "writtenExamLanguages")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<JobNotification> jobNotificationWrittenExamLanguages = new HashSet<>();

    @ManyToMany(mappedBy = "languageProficiencies")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<JobNotification> jobNotificationLanguageProficiencies = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public Language language(String language) {
        this.language = language;
        return this;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Set<JobNotification> getJobNotificationWrittenExamLanguages() {
        return jobNotificationWrittenExamLanguages;
    }

    public Language jobNotificationWrittenExamLanguages(Set<JobNotification> jobNotifications) {
        this.jobNotificationWrittenExamLanguages = jobNotifications;
        return this;
    }

    public Language addJobNotificationWrittenExamLanguage(JobNotification jobNotification) {
        this.jobNotificationWrittenExamLanguages.add(jobNotification);
        jobNotification.getWrittenExamLanguages().add(this);
        return this;
    }

    public Language removeJobNotificationWrittenExamLanguage(JobNotification jobNotification) {
        this.jobNotificationWrittenExamLanguages.remove(jobNotification);
        jobNotification.getWrittenExamLanguages().remove(this);
        return this;
    }

    public void setJobNotificationWrittenExamLanguages(Set<JobNotification> jobNotifications) {
        this.jobNotificationWrittenExamLanguages = jobNotifications;
    }

    public Set<JobNotification> getJobNotificationLanguageProficiencies() {
        return jobNotificationLanguageProficiencies;
    }

    public Language jobNotificationLanguageProficiencies(Set<JobNotification> jobNotifications) {
        this.jobNotificationLanguageProficiencies = jobNotifications;
        return this;
    }

    public Language addJobNotificationLanguageProficiency(JobNotification jobNotification) {
        this.jobNotificationLanguageProficiencies.add(jobNotification);
        jobNotification.getLanguageProficiencies().add(this);
        return this;
    }

    public Language removeJobNotificationLanguageProficiency(JobNotification jobNotification) {
        this.jobNotificationLanguageProficiencies.remove(jobNotification);
        jobNotification.getLanguageProficiencies().remove(this);
        return this;
    }

    public void setJobNotificationLanguageProficiencies(Set<JobNotification> jobNotifications) {
        this.jobNotificationLanguageProficiencies = jobNotifications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Language language = (Language) o;
        if (language.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), language.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Language{" +
            "id=" + getId() +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
