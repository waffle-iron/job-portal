package com.factly.jobportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.factly.jobportal.webhandler.JobNotificationWebHandler;

@RequestMapping("/job-portal")
@Controller
public class JobPortalHomePageController {

	private static final String HOME_PAGE = "index";
    private static final String JOB_NOTIFICATION_PAGE = "job-list";
	private static final String JOB_NOTIFICATION = "jobNotification";

	@Autowired
    private JobNotificationWebHandler jobNotificationWebHandler;


    @RequestMapping(method = RequestMethod.GET)
    public String homePage(Model model) {

        /*model.addAttribute("centralJobsCount", jobNotificationWebHandler.getCentralJobsCount());
        model.addAttribute("stateJobsCount", jobNotificationWebHandler.getStateJobsCount());
        model.addAttribute("otherJobsCount", jobNotificationWebHandler.getOtherStateJobsCount());*/
        return HOME_PAGE;
    }

    @RequestMapping(value= "/search", method = RequestMethod.GET)
    public String searchPage(Model model, Pageable pageable, @RequestParam("searchKey") String searchKey) {

        model.addAttribute(JOB_NOTIFICATION, jobNotificationWebHandler.getJobNotifications(searchKey, pageable));
        return JOB_NOTIFICATION_PAGE;
    }
}
