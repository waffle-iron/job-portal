package com.factly.jobportal.web.view;

public class JobNotificationView {

    private Long id;
    private String headLineText;
    private String location;
    private String jobType;
    private String organization;
    private String salary;

    public JobNotificationView(Long id, String headLineText, String location, String jobType, String organization, String salary) {
        this.id = id;
        this.headLineText = headLineText;
        this.location = location;
        this.jobType = jobType;
        this.organization = organization;
        this.salary = salary;
    }

    public Long getId() { return id; }

    public String getHeadLineText() {
        return headLineText;
    }

    public String getLocation() {
        return location;
    }

    public String getJobType() {
        return jobType;
    }

    public String getOrganization() {
        return organization;
    }

    public String getSalary() {
        return salary;
    }

}
