package com.factly.jobportal.service.dto;


import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the JobNotification entity.
 */
public class JobNotificationDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 500)
    private String headline;

    @NotNull
    private LocalDate notificationDate;

    @NotNull
    @Size(max = 250)
    private String organization;

    @NotNull
    @Size(max = 100)
    private String jobRole;

    @Size(max = 250)
    private String jobLocation;

    @NotNull
    @Min(value = 0)
    private Integer totalVacancyCount;

    @Lob
    private String additionalQualification;

    @Min(value = 0)
    private Integer workExperience;

    @Min(value = 0)
    private Integer salary;

    @NotNull
    private LocalDate applicationDeadline;

    @NotNull
    @Lob
    private String description;

    @NotNull
    @Lob
    private String notificationLink;

    @Lob
    private String applicationLink;

    @Size(max = 500)
    private String examLocation;

    private Long clientTypeId;

    private String clientTypeType;

    private Long jobSectorId;

    private String jobSectorSector;

    private Long jobTypeId;

    private String jobTypeType;

    private Long educationId;

    private String educationEducation;

    private Set<TestSkillDTO> testSkills = new HashSet<>();

    private Set<LanguageDTO> writtenExamLanguages = new HashSet<>();

    private Set<SelectionProcedureDTO> selectionProcedures = new HashSet<>();

    private Set<LanguageDTO> languageProficiencies = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public LocalDate getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(LocalDate notificationDate) {
        this.notificationDate = notificationDate;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }

    public Integer getTotalVacancyCount() {
        return totalVacancyCount;
    }

    public void setTotalVacancyCount(Integer totalVacancyCount) {
        this.totalVacancyCount = totalVacancyCount;
    }

    public String getAdditionalQualification() {
        return additionalQualification;
    }

    public void setAdditionalQualification(String additionalQualification) {
        this.additionalQualification = additionalQualification;
    }

    public Integer getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(Integer workExperience) {
        this.workExperience = workExperience;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public LocalDate getApplicationDeadline() {
        return applicationDeadline;
    }

    public void setApplicationDeadline(LocalDate applicationDeadline) {
        this.applicationDeadline = applicationDeadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotificationLink() {
        return notificationLink;
    }

    public void setNotificationLink(String notificationLink) {
        this.notificationLink = notificationLink;
    }

    public String getApplicationLink() {
        return applicationLink;
    }

    public void setApplicationLink(String applicationLink) {
        this.applicationLink = applicationLink;
    }

    public String getExamLocation() {
        return examLocation;
    }

    public void setExamLocation(String examLocation) {
        this.examLocation = examLocation;
    }

    public Long getClientTypeId() {
        return clientTypeId;
    }

    public void setClientTypeId(Long clientTypeId) {
        this.clientTypeId = clientTypeId;
    }

    public String getClientTypeType() {
        return clientTypeType;
    }

    public void setClientTypeType(String clientTypeType) {
        this.clientTypeType = clientTypeType;
    }

    public Long getJobSectorId() {
        return jobSectorId;
    }

    public void setJobSectorId(Long jobSectorId) {
        this.jobSectorId = jobSectorId;
    }

    public String getJobSectorSector() {
        return jobSectorSector;
    }

    public void setJobSectorSector(String jobSectorSector) {
        this.jobSectorSector = jobSectorSector;
    }

    public Long getJobTypeId() {
        return jobTypeId;
    }

    public void setJobTypeId(Long jobTypeId) {
        this.jobTypeId = jobTypeId;
    }

    public String getJobTypeType() {
        return jobTypeType;
    }

    public void setJobTypeType(String jobTypeType) {
        this.jobTypeType = jobTypeType;
    }

    public Long getEducationId() {
        return educationId;
    }

    public void setEducationId(Long educationId) {
        this.educationId = educationId;
    }

    public String getEducationEducation() {
        return educationEducation;
    }

    public void setEducationEducation(String educationEducation) {
        this.educationEducation = educationEducation;
    }

    public Set<TestSkillDTO> getTestSkills() {
        return testSkills;
    }

    public void setTestSkills(Set<TestSkillDTO> testSkills) {
        this.testSkills = testSkills;
    }

    public Set<LanguageDTO> getWrittenExamLanguages() {
        return writtenExamLanguages;
    }

    public void setWrittenExamLanguages(Set<LanguageDTO> languages) {
        this.writtenExamLanguages = languages;
    }

    public Set<SelectionProcedureDTO> getSelectionProcedures() {
        return selectionProcedures;
    }

    public void setSelectionProcedures(Set<SelectionProcedureDTO> selectionProcedures) {
        this.selectionProcedures = selectionProcedures;
    }

    public Set<LanguageDTO> getLanguageProficiencies() {
        return languageProficiencies;
    }

    public void setLanguageProficiencies(Set<LanguageDTO> languages) {
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

        JobNotificationDTO jobNotificationDTO = (JobNotificationDTO) o;
        if(jobNotificationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jobNotificationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JobNotificationDTO{" +
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
