package com.factly.jobportal.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Eductation.
 */
@Entity
@Table(name = "eductation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "eductation")
public class Eductation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "education", length = 100, nullable = false)
    private String education;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEducation() {
        return education;
    }

    public Eductation education(String education) {
        this.education = education;
        return this;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Eductation eductation = (Eductation) o;
        if (eductation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eductation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Eductation{" +
            "id=" + getId() +
            ", education='" + getEducation() + "'" +
            "}";
    }
}
