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

    public List<JobNotificationView> getAllJobNotifications(Pageable pageable) {

        Page<JobNotificationDTO> jobNotificationDTOS = jobNotificationService.search("*", pageable);
        return buildJobNotificationView(jobNotificationDTOS.getContent());
    }

    public List<JobNotificationView> getJobNotificationsByClientType(String searchKey, Pageable pageable) {

        Page<JobNotificationDTO> jobNotificationDTOS = jobNotificationService.findByClientType(searchKey, pageable);
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

    private List<JobNotificationView> buildJobNotificationView(List<JobNotificationDTO> jobNitificationsList) {
        List<JobNotificationView> jobNotificationViews = new ArrayList<>();
        for (JobNotificationDTO jobNotificationDTO : jobNitificationsList) {
            JobNotificationView jobNotificationView = new JobNotificationView(jobNotificationDTO.getId(), jobNotificationDTO.getHeadline(),
                    jobNotificationDTO.getJobLocation(), jobNotificationDTO.getJobTypeType(),
                    jobNotificationDTO.getOrganization(), jobNotificationDTO.getSalary());
            jobNotificationViews.add(jobNotificationView);
        }
        return jobNotificationViews;
    }
}
