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
 * A JobSector.
 */
@Entity
@Table(name = "job_sector")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "jobsector")
public class JobSector implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "sector", length = 100, nullable = false)
    private String sector;

    @Size(max = 500)
    @Column(name = "icon_url", length = 500)
    private String iconUrl;

    @OneToMany(mappedBy = "jobSector")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<JobNotification> jobNotifications = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSector() {
        return sector;
    }

    public JobSector sector(String sector) {
        this.sector = sector;
        return this;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public JobSector iconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
        return this;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Set<JobNotification> getJobNotifications() {
        return jobNotifications;
    }

    public JobSector jobNotifications(Set<JobNotification> jobNotifications) {
        this.jobNotifications = jobNotifications;
        return this;
    }

    public JobSector addJobNotification(JobNotification jobNotification) {
        this.jobNotifications.add(jobNotification);
        jobNotification.setJobSector(this);
        return this;
    }

    public JobSector removeJobNotification(JobNotification jobNotification) {
        this.jobNotifications.remove(jobNotification);
        jobNotification.setJobSector(null);
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
        JobSector jobSector = (JobSector) o;
        if (jobSector.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jobSector.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JobSector{" +
            "id=" + getId() +
            ", sector='" + getSector() + "'" +
            ", iconUrl='" + getIconUrl() + "'" +
            "}";
    }
}
