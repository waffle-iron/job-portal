package com.factly.jobportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Education.
 */
@Entity
@Table(name = "education")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "education")
public class Education implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "education", length = 100, nullable = false)
    @Field(
        type = FieldType.String,
        index = FieldIndex.not_analyzed,
        store = true
    )
    private String education;

    @OneToMany(mappedBy = "education")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<JobNotification> jobNotifications = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEducation() {
        return education;
    }

    public Education education(String education) {
        this.education = education;
        return this;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public Set<JobNotification> getJobNotifications() {
        return jobNotifications;
    }

    public Education jobNotifications(Set<JobNotification> jobNotifications) {
        this.jobNotifications = jobNotifications;
        return this;
    }

    public Education addJobNotification(JobNotification jobNotification) {
        this.jobNotifications.add(jobNotification);
        jobNotification.setEducation(this);
        return this;
    }

    public Education removeJobNotification(JobNotification jobNotification) {
        this.jobNotifications.remove(jobNotification);
        jobNotification.setEducation(null);
        return this;
    }

    public void setJobNotifications(Set<JobNotification> jobNotifications) {
        this.jobNotifications = jobNotifications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Education education = (Education) o;
        if (education.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), education.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Education{" +
            "id=" + getId() +
            ", education='" + getEducation() + "'" +
            "}";
    }
}
