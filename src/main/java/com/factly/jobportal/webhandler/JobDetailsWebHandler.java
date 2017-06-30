package com.factly.jobportal.webhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.factly.jobportal.service.JobNotificationService;
import com.factly.jobportal.service.dto.JobNotificationDTO;
import com.factly.jobportal.web.view.JobDetailsView;

/**
 * Created by Sravan on 6/29/2017.
 */
@Component
public class JobDetailsWebHandler {

    @Autowired
    private JobNotificationService jobNotificationService;

    public JobDetailsView getJobDetailsByJobId(Long jobId) {
        JobNotificationDTO jobNotificationDTO = jobNotificationService.findJobNotificationById(jobId);
        return buildJobDetailsView(jobNotificationDTO);
    }

    private JobDetailsView buildJobDetailsView(JobNotificationDTO jobNotificationDTO) {
        JobDetailsView jobDetailsView = new JobDetailsView();
        jobDetailsView.setHeadline(jobNotificationDTO.getHeadline());
        jobDetailsView.setLocation(jobNotificationDTO.getJobLocation());
        jobDetailsView.setSalary(jobNotificationDTO.getSalary());
        jobDetailsView.setJobType(jobNotificationDTO.getJobTypeType());
        jobDetailsView.setJobSector(jobNotificationDTO.getJobSectorSector());
        jobDetailsView.setDeadLine(jobNotificationDTO.getApplicationDeadline());
        jobDetailsView.setDescription(jobNotificationDTO.getDescription());
        return jobDetailsView;
    }
}
