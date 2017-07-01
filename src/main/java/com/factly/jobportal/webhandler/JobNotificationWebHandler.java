package com.factly.jobportal.webhandler;

import com.factly.jobportal.service.JobNotificationService;
import com.factly.jobportal.service.dto.JobListDTO;
import com.factly.jobportal.service.dto.JobNotificationDTO;
import com.factly.jobportal.web.domain.JobsCount;
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

        JobListDTO jobListDTO = jobNotificationService.findJobsByNotificationDate(pageable);
        return buildJobListView(jobListDTO);
    }

    public JobListView getJobNotificationsByClientType(String clientType, Pageable pageable) {

        JobListDTO jobListDTO = jobNotificationService.findJobByClientType(clientType, pageable);
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

    public long getFoodAgricultureJobsCount() {
        JobsCount jobCount = jobNotificationService.findSectorJobsCount("Food, Agriculture & Environment");
        return (jobCount.getJobsCount() == null)  ? 0 : jobCount.getJobsCount();
    }

    public long getlifeSciencesHealthCareJobsCount() {
        JobsCount jobCount = jobNotificationService.findSectorJobsCount("Life Sciences & Health Care");
        return (jobCount.getJobsCount() == null)  ? 0 : jobCount.getJobsCount();
    }

    public long getEnergyPowerJobsCount() {
        JobsCount jobCount = jobNotificationService.findSectorJobsCount("Energy & Power");
        return (jobCount.getJobsCount() == null)  ? 0 : jobCount.getJobsCount();
    }

    public long getBankingFinanceInsuranceJobsCount() {
        JobsCount jobCount = jobNotificationService.findSectorJobsCount("Banking, Finance & Insurance");
        return (jobCount.getJobsCount() == null)  ? 0 : jobCount.getJobsCount();
    }

    public long getTechnologyJobsCount() {
        JobsCount jobCount = jobNotificationService.findSectorJobsCount("Technology");
        return (jobCount.getJobsCount() == null)  ? 0 : jobCount.getJobsCount();
    }

    public long getCommunicationsMediaJobsCount() {
        JobsCount jobCount = jobNotificationService.findSectorJobsCount("Communications & Media");
        return (jobCount.getJobsCount() == null)  ? 0 : jobCount.getJobsCount();
    }

    public long getTourismJobsCount() {
        JobsCount jobCount = jobNotificationService.findSectorJobsCount("Tourisim");
        return (jobCount.getJobsCount() == null)  ? 0 : jobCount.getJobsCount();
    }


    public long getEducationJobsCount() {
        JobsCount jobCount = jobNotificationService.findSectorJobsCount("Education");
        return (jobCount.getJobsCount() == null)  ? 0 : jobCount.getJobsCount();
    }

    public long getTransporInfrastructureJobsCount() {
        JobsCount jobCount = jobNotificationService.findSectorJobsCount("Transport & Infrastructure");
        return (jobCount.getJobsCount() == null)  ? 0 : jobCount.getJobsCount();
    }

    public long getLawJudiciaryJobsCount() {
        JobsCount jobCount = jobNotificationService.findSectorJobsCount("Law & Judiciary");
        return (jobCount.getJobsCount() == null)  ? 0 : jobCount.getJobsCount();
    }

    public long getMilitarySecurityIntelligenceJobsCount() {
        JobsCount jobCount = jobNotificationService.findSectorJobsCount("Military, Security & Intelligence");
        return (jobCount.getJobsCount() == null)  ? 0 : jobCount.getJobsCount();
    }

    public long getGovernanceAdministrationJobsCount() {
        JobsCount jobCount = jobNotificationService.findSectorJobsCount("Governance & Administration");
        return (jobCount.getJobsCount() == null)  ? 0 : jobCount.getJobsCount();
    }

    public long getYouthAffairsSportsJobsCount() {
        JobsCount jobCount = jobNotificationService.findSectorJobsCount("Youth Affairs & Sports");
        return (jobCount.getJobsCount() == null)  ? 0 : jobCount.getJobsCount();
    }

    public long getManufacturingJobsCount() {
        JobsCount jobCount = jobNotificationService.findSectorJobsCount("Manufacturing");
        return (jobCount.getJobsCount() == null)  ? 0 : jobCount.getJobsCount();
    }

    public long getOtherSectorJobsCount() {
        JobsCount jobCount = jobNotificationService.findSectorJobsCount("Others");
        return (jobCount.getJobsCount() == null)  ? 0 : jobCount.getJobsCount();
    }

}
