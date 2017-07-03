package com.factly.jobportal.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.completion.Completion;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A JobNotification.
 */
@Entity
@Table(name = "job_notification")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "jobnotification",  refreshInterval = "-1")
//@Mapping(mappingPath = "/es-mapping/textvalue-mapping.json")
//@Setting("")
public class JobNotification implements Serializable {

    private static final long serialVersionUID = 1L;


    //@CompletionField(payloads = true, maxInputLength = 100)
    //transient private Completion suggest;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 500)
    @Column(name = "headline", length = 500, nullable = false)

    private String headline;

    @NotNull
    @Column(name = "notification_date", nullable = false)
    private LocalDate notificationDate;

    @NotNull
    @Size(max = 250)
    @Column(name = "jhi_organization", length = 250, nullable = false)
    @Field(
        type = FieldType.String,
        index = FieldIndex.not_analyzed,
        store = true
    )
    private String organization;

    @NotNull
    @Size(max = 100)
    @Column(name = "job_role", length = 100, nullable = false)
    @Field(
        type = FieldType.String,
        index = FieldIndex.not_analyzed,
        store = true
    )
    private String jobRole;

    @Size(max = 250)
    @Column(name = "job_location", length = 250)
    @Field(
        type = FieldType.String,
        index = FieldIndex.not_analyzed,
        store = true
    )
    private String jobLocation;

    @NotNull
    @Min(value = 0)
    @Column(name = "total_vacancy_count", nullable = false)
    private Integer totalVacancyCount;

    @Lob
    @Column(name = "additional_qualification")
    private String additionalQualification;

    @Min(value = 0)
    @Column(name = "work_experience")
    private Integer workExperience;

    @Min(value = 0)
    @Column(name = "salary")
    private Integer salary;

    @NotNull
    @Column(name = "application_deadline", nullable = false)
    private LocalDate applicationDeadline;

    @NotNull
    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Lob
    @Column(name = "notification_link", nullable = false)
    private String notificationLink;

    @Lob
    @Column(name = "application_link")
    private String applicationLink;

    @Size(max = 500)
    @Column(name = "exam_location", length = 500)
    private String examLocation;

    @ManyToOne(optional = false)
    @NotNull
    @Field(
        type = FieldType.Nested
    )
    private ClientType clientType;

    @ManyToOne(optional = false)
    @NotNull
    @Field(
        type = FieldType.Nested
    )
    private JobSector jobSector;

    @ManyToOne(optional = false)
    @NotNull
    @Field(
        type = FieldType.Nested
    )
    private JobType jobType;

    @ManyToOne
    @Field(
        type = FieldType.Nested
    )
    private Education education;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "job_notification_test_skill",
               joinColumns = @JoinColumn(name="job_notifications_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="test_skills_id", referencedColumnName="id"))
    private Set<TestSkill> testSkills = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "job_notification_selection_procedure",
               joinColumns = @JoinColumn(name="job_notifications_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="selection_procedures_id", referencedColumnName="id"))
    private Set<SelectionProcedure> selectionProcedures = new HashSet<>();

    @OneToMany(mappedBy = "jobNotification")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<QuotaJobDetails> quotaJobDetails = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "job_notification_written_exam_language",
               joinColumns = @JoinColumn(name="job_notifications_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="written_exam_languages_id", referencedColumnName="id"))
    private Set<Language> writtenExamLanguages = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "job_notification_language_proficiency",
               joinColumns = @JoinColumn(name="job_notifications_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="language_proficiencies_id", referencedColumnName="id"))
    private Set<Language> languageProficiencies = new HashSet<>();
//
//    public Completion getSuggest() {
//        return suggest;
//    }
//
//    public void setSuggest(Completion suggest) {
//        this.suggest = suggest;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeadline() {
        return headline;
    }

    public JobNotification headline(String headline) {
        this.headline = headline;
        return this;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public LocalDate getNotificationDate() {
        return notificationDate;
    }

    public JobNotification notificationDate(LocalDate notificationDate) {
        this.notificationDate = notificationDate;
        return this;
    }

    public void setNotificationDate(LocalDate notificationDate) {
        this.notificationDate = notificationDate;
    }

    public String getOrganization() {
        return organization;
    }

    public JobNotification organization(String organization) {
        this.organization = organization;
        return this;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getJobRole() {
        return jobRole;
    }

    public JobNotification jobRole(String jobRole) {
        this.jobRole = jobRole;
        return this;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public JobNotification jobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
        return this;
    }

    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }

    public Integer getTotalVacancyCount() {
        return totalVacancyCount;
    }

    public JobNotification totalVacancyCount(Integer totalVacancyCount) {
        this.totalVacancyCount = totalVacancyCount;
        return this;
    }

    public void setTotalVacancyCount(Integer totalVacancyCount) {
        this.totalVacancyCount = totalVacancyCount;
    }

    public String getAdditionalQualification() {
        return additionalQualification;
    }

    public JobNotification additionalQualification(String additionalQualification) {
        this.additionalQualification = additionalQualification;
        return this;
    }

    public void setAdditionalQualification(String additionalQualification) {
        this.additionalQualification = additionalQualification;
    }

    public Integer getWorkExperience() {
        return workExperience;
    }

    public JobNotification workExperience(Integer workExperience) {
        this.workExperience = workExperience;
        return this;
    }

    public void setWorkExperience(Integer workExperience) {
        this.workExperience = workExperience;
    }

    public Integer getSalary() {
        return salary;
    }

    public JobNotification salary(Integer salary) {
        this.salary = salary;
        return this;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public LocalDate getApplicationDeadline() {
        return applicationDeadline;
    }

    public JobNotification applicationDeadline(LocalDate applicationDeadline) {
        this.applicationDeadline = applicationDeadline;
        return this;
    }

    public void setApplicationDeadline(LocalDate applicationDeadline) {
        this.applicationDeadline = applicationDeadline;
    }

    public String getDescription() {
        return description;
    }

    public JobNotification description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotificationLink() {
        return notificationLink;
    }

    public JobNotification notificationLink(String notificationLink) {
        this.notificationLink = notificationLink;
        return this;
    }

    public void setNotificationLink(String notificationLink) {
        this.notificationLink = notificationLink;
    }

    public String getApplicationLink() {
        return applicationLink;
    }

    public JobNotification applicationLink(String applicationLink) {
        this.applicationLink = applicationLink;
        return this;
    }

    public void setApplicationLink(String applicationLink) {
        this.applicationLink = applicationLink;
    }

    public String getExamLocation() {
        return examLocation;
    }

    public JobNotification examLocation(String examLocation) {
        this.examLocation = examLocation;
        return this;
    }

    public void setExamLocation(String examLocation) {
        this.examLocation = examLocation;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public JobNotification clientType(ClientType clientType) {
        this.clientType = clientType;
        return this;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public JobSector getJobSector() {
        return jobSector;
    }

    public JobNotification jobSector(JobSector jobSector) {
        this.jobSector = jobSector;
        return this;
    }

    public void setJobSector(JobSector jobSector) {
        this.jobSector = jobSector;
    }

    public JobType getJobType() {
        return jobType;
    }

    public JobNotification jobType(JobType jobType) {
        this.jobType = jobType;
        return this;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public Education getEducation() {
        return education;
    }

    public JobNotification education(Education education) {
        this.education = education;
        return this;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    public Set<TestSkill> getTestSkills() {
        return testSkills;
    }

    public JobNotification testSkills(Set<TestSkill> testSkills) {
        this.testSkills = testSkills;
        return this;
    }

    public JobNotification addTestSkill(TestSkill testSkill) {
        this.testSkills.add(testSkill);
        testSkill.getJobNotifications().add(this);
        return this;
    }

    public JobNotification removeTestSkill(TestSkill testSkill) {
        this.testSkills.remove(testSkill);
        testSkill.getJobNotifications().remove(this);
        return this;
    }

    public void setTestSkills(Set<TestSkill> testSkills) {
        this.testSkills = testSkills;
    }

    public Set<SelectionProcedure> getSelectionProcedures() {
        return selectionProcedures;
    }

    public JobNotification selectionProcedures(Set<SelectionProcedure> selectionProcedures) {
        this.selectionProcedures = selectionProcedures;
        return this;
    }

    public JobNotification addSelectionProcedure(SelectionProcedure selectionProcedure) {
        this.selectionProcedures.add(selectionProcedure);
        selectionProcedure.getJobNotifications().add(this);
        return this;
    }

    public JobNotification removeSelectionProcedure(SelectionProcedure selectionProcedure) {
        this.selectionProcedures.remove(selectionProcedure);
        selectionProcedure.getJobNotifications().remove(this);
        return this;
    }

    public void setSelectionProcedures(Set<SelectionProcedure> selectionProcedures) {
        this.selectionProcedures = selectionProcedures;
    }

    public Set<QuotaJobDetails> getQuotaJobDetails() {
        return quotaJobDetails;
    }

    public JobNotification quotaJobDetails(Set<QuotaJobDetails> quotaJobDetails) {
        this.quotaJobDetails = quotaJobDetails;
        return this;
    }

    public JobNotification addQuotaJobDetails(QuotaJobDetails quotaJobDetails) {
        this.quotaJobDetails.add(quotaJobDetails);
        quotaJobDetails.setJobNotification(this);
        return this;
    }

    public JobNotification removeQuotaJobDetails(QuotaJobDetails quotaJobDetails) {
        this.quotaJobDetails.remove(quotaJobDetails);
        quotaJobDetails.setJobNotification(null);
        return this;
    }

    public void setQuotaJobDetails(Set<QuotaJobDetails> quotaJobDetails) {
        this.quotaJobDetails = quotaJobDetails;
    }

    public Set<Language> getWrittenExamLanguages() {
        return writtenExamLanguages;
    }

    public JobNotification writtenExamLanguages(Set<Language> languages) {
        this.writtenExamLanguages = languages;
        return this;
    }

    public JobNotification addWrittenExamLanguage(Language language) {
        this.writtenExamLanguages.add(language);
        language.getJobNotificationWrittenExamLanguages().add(this);
        return this;
    }

    public JobNotification removeWrittenExamLanguage(Language language) {
        this.writtenExamLanguages.remove(language);
        language.getJobNotificationWrittenExamLanguages().remove(this);
        return this;
    }

    public void setWrittenExamLanguages(Set<Language> languages) {
        this.writtenExamLanguages = languages;
    }

    public Set<Language> getLanguageProficiencies() {
        return languageProficiencies;
    }

    public JobNotification languageProficiencies(Set<Language> languages) {
        this.languageProficiencies = languages;
        return this;
    }

    public JobNotification addLanguageProficiency(Language language) {
        this.languageProficiencies.add(language);
        language.getJobNotificationLanguageProficiencies().add(this);
        return this;
    }

    public JobNotification removeLanguageProficiency(Language language) {
        this.languageProficiencies.remove(language);
        language.getJobNotificationLanguageProficiencies().remove(this);
        return this;
    }

    public void setLanguageProficiencies(Set<Language> languages) {
        this.languageProficiencies = languages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JobNotification jobNotification = (JobNotification) o;
        if (jobNotification.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jobNotification.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JobNotification{" +
            "id=" + getId() +
            ", headline='" + getHeadline() + "'" +
            ", notificationDate='" + getNotificationDate() + "'" +
            ", organization='" + getOrganization() + "'" +
            ", jobRole='" + getJobRole() + "'" +
            ", jobLocation='" + getJobLocation() + "'" +
            ", totalVacancyCount='" + getTotalVacancyCount() + "'" +
            ", additionalQualification='" + getAdditionalQualification() + "'" +
            ", workExperience='" + getWorkExperience() + "'" +
            ", salary='" + getSalary() + "'" +
            ", applicationDeadline='" + getApplicationDeadline() + "'" +
            ", description='" + getDescription() + "'" +
            ", notificationLink='" + getNotificationLink() + "'" +
            ", applicationLink='" + getApplicationLink() + "'" +
            ", examLocation='" + getExamLocation() + "'" +
            "}";
    }
}
