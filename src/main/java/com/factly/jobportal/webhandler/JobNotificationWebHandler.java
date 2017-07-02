package com.factly.jobportal.webhandler;

import com.factly.jobportal.service.JobNotificationService;
import com.factly.jobportal.service.dto.JobListDTO;
import com.factly.jobportal.service.dto.JobNotificationDTO;
import com.factly.jobportal.web.view.JobListView;
import com.factly.jobportal.web.view.JobNotificationView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JobNotificationWebHandler {

    @Autowired
    private JobNotificationService jobNotificationService;

    public JobListView getJobNotifications(String searchKey, Pageable pageable) {

        JobListDTO jobListDTO = jobNotificationService.search(searchKey, pageable);
        return buildJobListView(jobListDTO);
    }

    public JobListView getJobNotificationsByDate(Pageable pageable) {

        JobListDTO jobListDTO = jobNotificationService.findJobNotificationsByDate(pageable);
        return buildJobListView(jobListDTO);
    }

    public JobListView getJobNotificationsByClientType(String clientType, Pageable pageable) {

        JobListDTO jobListDTO = jobNotificationService.findJobNotificationsByClientType(clientType, pageable);
        return buildJobListView(jobListDTO);
    }

    public Object getJobNotificationsByJobSector(String jobSector, Pageable pageable) {
        JobListDTO jobListDTO = jobNotificationService.findJobNotificationsByJobSector(jobSector, pageable);
        return buildJobListView(jobListDTO);
    }

    private JobListView buildJobListView(JobListDTO jobListDTO) {

        // set list of notifications
        List<JobNotificationView> notifications = buildJobNotificationView(jobListDTO.getNotificationsPage().getContent());

        JobListView jobListView = new JobListView(
            notifications,
            jobListDTO.getClientTypeAggregations(),
            jobListDTO.getJobSectorAggregations(),
            jobListDTO.getJobTypeAggregations(),
            jobListDTO.getEducationAggregations(),
            jobListDTO.getOrganizationAggregations(),
            jobListDTO.getJobRoleAggregations(),
            jobListDTO.getJobLocationAggregations()
        );

        return jobListView;
    }

    private List<JobNotificationView> buildJobNotificationView(List<JobNotificationDTO> jobNitificationsList) {
        List<JobNotificationView> jobNotificationViews = new ArrayList<>();
        for (JobNotificationDTO jobNotificationDTO : jobNitificationsList) {
            JobNotificationView jobNotificationView = new JobNotificationView(jobNotificationDTO.getId(), jobNotificationDTO.getHeadline(),
                jobNotificationDTO.getJobLocation(), jobNotificationDTO.getJobTypeType(),
                jobNotificationDTO.getOrganization(), jobNotificationDTO.getSalary()+" Rs");
            jobNotificationViews.add(jobNotificationView);
        }
        return jobNotificationViews;
    }
}
