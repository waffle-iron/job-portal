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
 * A SelectionProcedure.
 */
@Entity
@Table(name = "selection_procedure")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "selectionprocedure")
public class SelectionProcedure implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "jhi_procedure", length = 100, nullable = false)
    private String procedure;

    @ManyToMany(mappedBy = "selectionProcedures")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<JobNotification> jobNotifications = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcedure() {
        return procedure;
    }

    public SelectionProcedure procedure(String procedure) {
        this.procedure = procedure;
        return this;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public Set<JobNotification> getJobNotifications() {
        return jobNotifications;
    }

    public SelectionProcedure jobNotifications(Set<JobNotification> jobNotifications) {
        this.jobNotifications = jobNotifications;
        return this;
    }

    public SelectionProcedure addJobNotification(JobNotification jobNotification) {
        this.jobNotifications.add(jobNotification);
        jobNotification.getSelectionProcedures().add(this);
        return this;
    }

    public SelectionProcedure removeJobNotification(JobNotification jobNotification) {
        this.jobNotifications.remove(jobNotification);
        jobNotification.getSelectionProcedures().remove(this);
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
        SelectionProcedure selectionProcedure = (SelectionProcedure) o;
        if (selectionProcedure.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), selectionProcedure.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SelectionProcedure{" +
            "id=" + getId() +
            ", procedure='" + getProcedure() + "'" +
            "}";
    }
}
