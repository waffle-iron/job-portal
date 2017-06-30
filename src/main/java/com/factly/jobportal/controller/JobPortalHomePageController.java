package com.factly.jobportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.factly.jobportal.webhandler.JobNotificationWebHandler;

@Controller
public class JobPortalHomePageController {

	private static final String HOME_PAGE = "index";
    private static final String JOB_NOTIFICATION_PAGE = "job-list";
    private static final String ABOUT_US_PAGE = "about-us";
    private static final String SIGN_IN_PAGE = "signin";
	private static final String JOB_NOTIFICATION = "jobNotification";

	@Autowired
    private JobNotificationWebHandler jobNotificationWebHandler;


    @RequestMapping(value = "/job-portal", method = RequestMethod.GET)
    public String homePage(Model model) {

        model.addAttribute("centralJobsCount", jobNotificationWebHandler.getCentralJobsCount());
        model.addAttribute("stateJobsCount", jobNotificationWebHandler.getStateJobsCount());
        model.addAttribute("otherJobsCount", jobNotificationWebHandler.getOtherStateJobsCount());
        model.addAttribute("foodAgricultureEnvironmentJobsCount", jobNotificationWebHandler.getFoodAgricultureJobsCount());
        model.addAttribute("lifeSciencesHealthCareJobsCount", jobNotificationWebHandler.getlifeSciencesHealthCareJobsCount());
        model.addAttribute("energyPowerJobsCount", jobNotificationWebHandler.getEnergyPowerJobsCount());
        model.addAttribute("bankingFinanceInsuranceJobsCount", jobNotificationWebHandler.getBankingFinanceInsuranceJobsCount());
        model.addAttribute("technologyJobsCount", jobNotificationWebHandler.getTechnologyJobsCount());
        model.addAttribute("communicationsMediaJobsCount", jobNotificationWebHandler.getCommunicationsMediaJobsCount());
        model.addAttribute("tourismJobsCount", jobNotificationWebHandler.getTourismJobsCount());
        model.addAttribute("educationJobsCount", jobNotificationWebHandler.getEducationJobsCount());
        model.addAttribute("transportInfrastructureJobsCount", jobNotificationWebHandler.getTransporInfrastructureJobsCount());
        model.addAttribute("lawJudiciaryJobsCount", jobNotificationWebHandler.getLawJudiciaryJobsCount());
        model.addAttribute("militarySecurityIntelligenceJobsCount", jobNotificationWebHandler.getMilitarySecurityIntelligenceJobsCount());
        model.addAttribute("governanceAdministrationJobsCount", jobNotificationWebHandler.getGovernanceAdministrationJobsCount());
        model.addAttribute("youthAffairsSportsJobsCount", jobNotificationWebHandler.getYouthAffairsSportsJobsCount());
        model.addAttribute("manufacturingJobsCount", jobNotificationWebHandler.getManufacturingJobsCount());
        model.addAttribute("otherSectorJobsCount", jobNotificationWebHandler.getOtherSectorJobsCount());
        return HOME_PAGE;
    }

    @RequestMapping(value= "/job-list", method = RequestMethod.GET)
    public String searchPage(Model model, Pageable pageable, @RequestParam("searchKey") String searchKey) {

        model.addAttribute(JOB_NOTIFICATION, jobNotificationWebHandler.getJobNotifications(searchKey, pageable));
        return JOB_NOTIFICATION_PAGE;
    }

    @RequestMapping(value= "/job-list/all", method = RequestMethod.GET)
    public String searchAllJobs(Model model, Pageable pageable) {

        model.addAttribute(JOB_NOTIFICATION, jobNotificationWebHandler.getAllJobNotifications(pageable));
        return JOB_NOTIFICATION_PAGE;
    }

    @RequestMapping(value= "/job-list/type", method = RequestMethod.GET)
    public String searchByClient(Model model, Pageable pageable, @RequestParam("searchKey") String searchKey) {

        model.addAttribute(JOB_NOTIFICATION, jobNotificationWebHandler.getJobNotificationsByClientType(searchKey, pageable));
        return JOB_NOTIFICATION_PAGE;
    }

    @RequestMapping(value= "/about-us", method = RequestMethod.GET)
    public String aboutUs() {
        return ABOUT_US_PAGE;
    }

    @RequestMapping(value= "/sign-in", method = RequestMethod.GET)
    public String signIn() {
        return SIGN_IN_PAGE;
    }
}
