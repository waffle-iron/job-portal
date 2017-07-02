package com.factly.jobportal.service.dto;

import org.springframework.data.domain.Page;

import java.util.Map;

/**
 * Created by ntalla on 7/1/17.
 */
public class JobListDTO {
    private Page<JobNotificationDTO> notificationsPage;

    private Map<String, Long> clientTypeAggregations;
    private Map<String, Long> jobSectorAggregations;
    private Map<String, Long> jobTypeAggregations;
    private Map<String, Long> educationAggregations;
    private Map<String, Long> organizationAggregations;
    private Map<String, Long> jobRoleAggregations;
    private Map<String, Long> jobLocationAggregations;

    public Page<JobNotificationDTO> getNotificationsPage() {
        return notificationsPage;
    }

    public void setNotificationsPage(Page<JobNotificationDTO> notificationsPage) {
        this.notificationsPage = notificationsPage;
    }

    public Map<String, Long> getClientTypeAggregations() {
        return clientTypeAggregations;
    }

    public void setClientTypeAggregations(Map<String, Long> clientTypeAggregations) {
        this.clientTypeAggregations = clientTypeAggregations;
    }

    public Map<String, Long> getJobSectorAggregations() {
        return jobSectorAggregations;
    }

    public void setJobSectorAggregations(Map<String, Long> jobSectorAggregations) {
        this.jobSectorAggregations = jobSectorAggregations;
    }

    public Map<String, Long> getJobTypeAggregations() {
        return jobTypeAggregations;
    }

    public void setJobTypeAggregations(Map<String, Long> jobTypeAggregations) {
        this.jobTypeAggregations = jobTypeAggregations;
    }

    public Map<String, Long> getEducationAggregations() {
        return educationAggregations;
    }

    public void setEducationAggregations(Map<String, Long> educationAggregations) {
        this.educationAggregations = educationAggregations;
    }

    public Map<String, Long> getOrganizationAggregations() {
        return organizationAggregations;
    }

    public void setOrganizationAggregations(Map<String, Long> organizationAggregations) {
        this.organizationAggregations = organizationAggregations;
    }

    public Map<String, Long> getJobRoleAggregations() {
        return jobRoleAggregations;
    }

    public void setJobRoleAggregations(Map<String, Long> jobRoleAggregations) {
        this.jobRoleAggregations = jobRoleAggregations;
    }

    public Map<String, Long> getJobLocationAggregations() {
        return jobLocationAggregations;
    }

    public void setJobLocationAggregations(Map<String, Long> jobLocationAggregations) {
        this.jobLocationAggregations = jobLocationAggregations;
    }
}
