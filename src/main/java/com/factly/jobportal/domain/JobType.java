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
 * A JobType.
 */
@Entity
@Table(name = "job_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "jobtype")
public class JobType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "jhi_type", length = 100, nullable = false)
    @Field(
        type = FieldType.String,
        index = FieldIndex.not_analyzed,
        store = true
    )
    private String type;

    @OneToMany(mappedBy = "jobType")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<JobNotification> jobNotifications = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public JobType type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<JobNotification> getJobNotifications() {
        return jobNotifications;
    }

    public JobType jobNotifications(Set<JobNotification> jobNotifications) {
        this.jobNotifications = jobNotifications;
        return this;
    }

    public JobType addJobNotification(JobNotification jobNotification) {
        this.jobNotifications.add(jobNotification);
        jobNotification.setJobType(this);
        return this;
    }

    public JobType removeJobNotification(JobNotification jobNotification) {
        this.jobNotifications.remove(jobNotification);
        jobNotification.setJobType(null);
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
        JobType jobType = (JobType) o;
        if (jobType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jobType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JobType{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            "}";
    }
}
