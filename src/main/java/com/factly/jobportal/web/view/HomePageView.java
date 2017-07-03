package com.factly.jobportal.web.view;

import com.factly.jobportal.service.dto.JobNotificationDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by ntalla on 7/1/17.
 */
public class HomePageView {

    public List<JobNotificationView> notifications;

    private Map<String, Long> clientTypeAggregations;
    private Map<String, Long> jobSectorAggregations;

    public HomePageView(List<JobNotificationView> notifications, Map<String, Long> clientTypeAggregations, Map<String, Long> jobSectorAggregations) {
        this.notifications = notifications;
        this.clientTypeAggregations = clientTypeAggregations;
        this.jobSectorAggregations = jobSectorAggregations;
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
}