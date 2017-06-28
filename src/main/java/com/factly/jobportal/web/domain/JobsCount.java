package com.factly.jobportal.web.domain;

/**
 * Created by Sravan on 6/26/2017.
 */
public class JobsCount {

    private Long jobsCount;

    public JobsCount(Long jobCount) {
        this.jobsCount = jobCount;
    }

    public Long getJobsCount() {
        return jobsCount;
    }

}
