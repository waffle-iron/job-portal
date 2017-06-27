package com.factly.jobportal.web.domain;

/**
 * Created by Sravan on 6/26/2017.
 */
public class JobsCount {

    private long jobsCount;

    public JobsCount(long jobCount) {
        this.jobsCount = jobCount;
    }

    public long getJobsCount() {
        return jobsCount;
    }

}
