package com.factly.jobportal.web.view;

public class JobNotificationView {

    private long id;
    private String headLineText;
    private String location;
    private String jobType;
    private String organization;
    private int salary;

    public JobNotificationView(long id, String headLineText, String location, String jobType, String organization, int salary) {
        this.id = id;
        this.headLineText = headLineText;
        this.location = location;
        this.jobType = jobType;
        this.organization = organization;
        this.salary = salary;
    }

    public long getId() { return id; }

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

    public int getSalary() {
        return salary;
    }

}
