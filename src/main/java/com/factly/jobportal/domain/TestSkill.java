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
 * A TestSkill.
 */
@Entity
@Table(name = "test_skill")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "testskill")
public class TestSkill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "skill", length = 100, nullable = false)
    private String skill;

    @ManyToMany(mappedBy = "testSkills")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<JobNotification> jobNotifications = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkill() {
        return skill;
    }

    public TestSkill skill(String skill) {
        this.skill = skill;
        return this;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public Set<JobNotification> getJobNotifications() {
        return jobNotifications;
    }

    public TestSkill jobNotifications(Set<JobNotification> jobNotifications) {
        this.jobNotifications = jobNotifications;
        return this;
    }

    public TestSkill addJobNotification(JobNotification jobNotification) {
        this.jobNotifications.add(jobNotification);
        jobNotification.getTestSkills().add(this);
        return this;
    }

    public TestSkill removeJobNotification(JobNotification jobNotification) {
        this.jobNotifications.remove(jobNotification);
        jobNotification.getTestSkills().remove(this);
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
        TestSkill testSkill = (TestSkill) o;
        if (testSkill.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), testSkill.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TestSkill{" +
            "id=" + getId() +
            ", skill='" + getSkill() + "'" +
            "}";
    }
}
