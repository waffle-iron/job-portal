package com.factly.jobportal.web.view;

import java.util.List;
import java.util.Map;

/**
 * Created by ntalla on 7/1/17.
 */
public class JobListView {
    public List<JobNotificationView> notifications;

    private Map<String, Long> clientTypeAggregations;
    private Map<String, Long> jobSectorAggregations;
    private Map<String, Long> jobTypeAggregations;
    private Map<String, Long> educationAggregations;
    private Map<String, Long> organizationAggregations;
    private Map<String, Long> jobRoleAggregations;
    private Map<String, Long> jobLocationAggregations;

    public JobListView(List<JobNotificationView> notifications, Map<String, Long> clientTypeAggregations, Map<String, Long> jobSectorAggregations, Map<String, Long> jobTypeAggregations, Map<String, Long> educationAggregations, Map<String, Long> organizationAggregations, Map<String, Long> jobRoleAggregations, Map<String, Long> jobLocationAggregations) {
        this.notifications = notifications;
        this.clientTypeAggregations = clientTypeAggregations;
        this.jobSectorAggregations = jobSectorAggregations;
        this.jobTypeAggregations = jobTypeAggregations;
        this.educationAggregations = educationAggregations;
        this.organizationAggregations = organizationAggregations;
        this.jobRoleAggregations = jobRoleAggregations;
        this.jobLocationAggregations = jobLocationAggregations;
    }

    public List<JobNotificationView> getNotifications() {
        return notifications;
    }

    public Map<String, Long> getClientTypeAggregations() {
        return clientTypeAggregations;
    }

    public Map<String, Long> getJobSectorAggregations() {
        return jobSectorAggregations;
    }

    public Map<String, Long> getJobTypeAggregations() {
        return jobTypeAggregations;
    }

    public Map<String, Long> getEducationAggregations() {
        return educationAggregations;
    }

    public Map<String, Long> getOrganizationAggregations() {
        return organizationAggregations;
    }

    public Map<String, Long> getJobRoleAggregations() {
        return jobRoleAggregations;
    }

    public Map<String, Long> getJobLocationAggregations() {
        return jobLocationAggregations;
    }
}
