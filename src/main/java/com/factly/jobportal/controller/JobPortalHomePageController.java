package com.factly.jobportal.controller;

import com.factly.jobportal.domain.JobNotification;
import com.factly.jobportal.web.view.HomePageView;
import com.factly.jobportal.web.view.Tag;
import com.factly.jobportal.webhandler.HomePageWebHandler;
import com.factly.jobportal.webhandler.JobNotificationWebHandler;
import org.elasticsearch.action.suggest.SuggestResponse;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionFuzzyBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class JobPortalHomePageController {

	private static final String HOME_PAGE = "index";
    private static final String JOB_NOTIFICATION_PAGE = "job-list";
    private static final String ABOUT_US_PAGE = "about-us";
    private static final String SIGN_IN_PAGE = "signin";
	private static final String JOB_NOTIFICATION_VIEW = "jobListView";
    private static final String HOME_PAGE_VIEW = "homePageView";

	@Autowired
    private JobNotificationWebHandler jobNotificationWebHandler;

	@Autowired
	private HomePageWebHandler homePageWebHandler;


    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @RequestMapping(value = "/job-portal", method = RequestMethod.GET)
    public String homePage(Model model, Pageable pageable) {

        HomePageView homePageView = homePageWebHandler
            .getJobNotificationsCountByClientTypeAndJobSector(pageable);
        model.addAttribute(HOME_PAGE_VIEW, homePageView);
        return HOME_PAGE;
    }

    @RequestMapping(value= "/job-list", method = RequestMethod.GET)
    public String searchPage(Model model, Pageable pageable, @RequestParam("searchKey") String searchKey) {

        model.addAttribute(JOB_NOTIFICATION_VIEW, jobNotificationWebHandler.getJobNotifications(searchKey, pageable));
        return JOB_NOTIFICATION_PAGE;
    }

    @RequestMapping(value= "/job-list/all", method = RequestMethod.GET)
    public String searchAllJobs(Model model, Pageable pageable) {

        model.addAttribute(JOB_NOTIFICATION_VIEW, jobNotificationWebHandler.getJobNotificationsByDate(pageable));
        return JOB_NOTIFICATION_PAGE;
    }

    @RequestMapping(value= "/job-list/client-type", method = RequestMethod.GET)
    public String searchByClient(Model model, Pageable pageable, @RequestParam("value") String clientType) {

        model.addAttribute(JOB_NOTIFICATION_VIEW, jobNotificationWebHandler.getJobNotificationsByClientType(clientType, pageable));
        return JOB_NOTIFICATION_PAGE;
    }

    @RequestMapping(value= "/job-list/job-sector", method = RequestMethod.GET)
    public String searchBySector(Model model, Pageable pageable, @RequestParam("value") String jobSector) {
    //public String searchBySector(Model model, Pageable pageable, @RequestBody MultiValueMap jobSectorMap) {
        //String jobSector = (String)jobSectorMap.get("");
        model.addAttribute(JOB_NOTIFICATION_VIEW, jobNotificationWebHandler.getJobNotificationsByJobSector(jobSector, pageable));
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



    List<Tag> data = new ArrayList<Tag>();

    JobPortalHomePageController() {
        // init data for testing
        data.add(new Tag(1, "ruby"));
        data.add(new Tag(2, "rails"));
        data.add(new Tag(3, "c / c++"));
        data.add(new Tag(4, ".net"));
        data.add(new Tag(5, "python"));
        data.add(new Tag(6, "java"));
        data.add(new Tag(7, "javascript"));
        data.add(new Tag(8, "jscript"));

    }

    @RequestMapping(value = "/job-portal/getTags", method = RequestMethod.GET)
    public @ResponseBody
    List<Tag> getTags(@RequestParam String tagName) {

        elasticsearchTemplate.putMapping(JobNotification.class);

        List<Tag> result = jobNotificationWebHandler.getJobNotificationsTags(tagName);

        return simulateSearchResult(tagName);
    }

    private List<Tag> simulateSearchResult(String tagName) {

        List<Tag> result = new ArrayList<Tag>();

        // iterate a list and filter by tagName
        for (Tag tag : data) {
            if (tag.getTagName().contains(tagName)) {
                result.add(tag);
            }
        }

        return result;
    }
}
