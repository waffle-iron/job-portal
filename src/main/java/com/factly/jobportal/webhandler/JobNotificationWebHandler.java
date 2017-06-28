package com.factly.jobportal.webhandler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.factly.jobportal.service.JobNotificationService;
import com.factly.jobportal.service.dto.JobNotificationDTO;
import com.factly.jobportal.web.domain.JobsCount;
import com.factly.jobportal.web.view.JobNotificationView;

@Component
public class JobNotificationWebHandler {

    @Autowired
    private JobNotificationService jobNotificationService;

    public List<JobNotificationView> getJobNotifications(String searchKey, Pageable pageable) {

        Page<JobNotificationDTO> jobNotificationDTOS = jobNotificationService.search(searchKey, pageable);
        return buildJobNotificationView(jobNotificationDTOS.getContent());

    }

    public long getCentralJobsCount() {
        JobsCount jobCount = jobNotificationService.findJobsCount("Central Government");
        return (jobCount.getJobsCount() == null)  ? 0 : jobCount.getJobsCount();
    }

    public long getStateJobsCount() {
        JobsCount jobCount = jobNotificationService.findJobsCount("State Government");
        return (jobCount.getJobsCount() == null)  ? 0 : jobCount.getJobsCount();
    }

    public long getOtherStateJobsCount() {
        JobsCount jobCount = jobNotificationService.findJobsCount("PSU & Other States");
        return (jobCount.getJobsCount() == null)  ? 0 : jobCount.getJobsCount();
    }

    private List<JobNotificationView> buildJobNotificationView(List<JobNotificationDTO> jobNitificationsList) {
        List<JobNotificationView> jobNotificationViews = new ArrayList<>();
        for (JobNotificationDTO jobNotificationDTO : jobNitificationsList) {
            JobNotificationView jobNotificationView = new JobNotificationView(jobNotificationDTO.getHeadline(),
                    jobNotificationDTO.getJobLocation(), jobNotificationDTO.getJobTypeType(),
                    jobNotificationDTO.getOrganization(), jobNotificationDTO.getSalary());
            jobNotificationViews.add(jobNotificationView);
        }
        return jobNotificationViews;
    }
}
