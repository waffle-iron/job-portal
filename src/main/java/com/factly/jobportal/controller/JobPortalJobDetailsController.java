package com.factly.jobportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.factly.jobportal.webhandler.JobDetailsWebHandler;

@Controller
public class JobPortalJobDetailsController {

	private static final String JOB_DETAILS = "details";
    private static final String JOB_ID = "jobId";
    private static final String JOB_DETAILS_VIEW = "jobDetailsView";

	@Autowired
    private JobDetailsWebHandler jobDetailsWebHandler;


    @RequestMapping( value = "/job-details", method = RequestMethod.GET)
    public String jobDetailsPage(Model model, @RequestParam(value = JOB_ID) String jobId) {
        model.addAttribute(JOB_DETAILS_VIEW, jobDetailsWebHandler.getJobDetailsByJobId(Long.parseLong(jobId)));
        return JOB_DETAILS;
    }
}
