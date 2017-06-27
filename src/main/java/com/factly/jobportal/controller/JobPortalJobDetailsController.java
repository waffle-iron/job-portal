package com.factly.jobportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.factly.jobportal.webhandler.JobNotificationWebHandler;

@RequestMapping("/job-details")
@Controller
public class JobPortalJobDetailsController {

	private static final String JOB_DETAILS = "details";
    private static final String JOB_ID = "jobId";

	@Autowired
    private JobNotificationWebHandler jobNotificationWebHandler;


    @RequestMapping(method = RequestMethod.GET)
    public String jobDetailsPage(Model model) {
        return JOB_DETAILS;
    }
}
