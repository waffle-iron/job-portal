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
 * A QuotaCategory.
 */
@Entity
@Table(name = "quota_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "quotacategory")
public class QuotaCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "category", length = 100, nullable = false)
    private String category;

    @OneToMany(mappedBy = "quotaCategory")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<QuotaJobDetails> quotaJobDetails = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public QuotaCategory category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<QuotaJobDetails> getQuotaJobDetails() {
        return quotaJobDetails;
    }

    public QuotaCategory quotaJobDetails(Set<QuotaJobDetails> quotaJobDetails) {
        this.quotaJobDetails = quotaJobDetails;
        return this;
    }

    public QuotaCategory addQuotaJobDetails(QuotaJobDetails quotaJobDetails) {
        this.quotaJobDetails.add(quotaJobDetails);
        quotaJobDetails.setQuotaCategory(this);
        return this;
    }

    public QuotaCategory removeQuotaJobDetails(QuotaJobDetails quotaJobDetails) {
        this.quotaJobDetails.remove(quotaJobDetails);
        quotaJobDetails.setQuotaCategory(null);
        return this;
    }

    public void setQuotaJobDetails(Set<QuotaJobDetails> quotaJobDetails) {
        this.quotaJobDetails = quotaJobDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QuotaCategory quotaCategory = (QuotaCategory) o;
        if (quotaCategory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), quotaCategory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuotaCategory{" +
            "id=" + getId() +
            ", category='" + getCategory() + "'" +
            "}";
    }
}
