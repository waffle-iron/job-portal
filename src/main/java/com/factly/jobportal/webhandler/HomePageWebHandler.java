package com.factly.jobportal.webhandler;

import com.factly.jobportal.service.JobNotificationService;
import com.factly.jobportal.service.dto.JobListDTO;
import com.factly.jobportal.service.dto.JobNotificationDTO;
import com.factly.jobportal.web.view.HomePageView;
import com.factly.jobportal.web.view.JobNotificationView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ntalla on 7/1/17.
 */
@Component
public class HomePageWebHandler {

    @Autowired
    private JobNotificationService jobNotificationService;


    public HomePageView getJobNotificationsCountByClientTypeAndJobSector(Pageable pageable) {

        JobListDTO jobListDTO = jobNotificationService.
            findJobNotificationsCountByClientTypeAndJobSector(pageable);
        List<JobNotificationView> notifications = buildJobNotificationView(jobListDTO.getNotificationsPage().getContent());

        HomePageView homePageView = new HomePageView(notifications,
            jobListDTO.getClientTypeReverseNestedAggregations(),
            jobListDTO.getJobSectorReverseNestedAggregations());
        return homePageView;
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
