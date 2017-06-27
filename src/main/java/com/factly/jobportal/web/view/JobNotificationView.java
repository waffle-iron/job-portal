package com.factly.jobportal.web.view;

public class JobNotificationView {

    private String headLineText;
    private String location;
    private String jobType;
    private String organization;
    private int salary;

    public JobNotificationView(String headLineText, String location, String jobType, String organization, int salary) {
        this.headLineText = headLineText;
        this.location = location;
        this.jobType = jobType;
        this.organization = organization;
        this.salary = salary;
    }

    public String getHeadLineText() {
        return headLineText;
    }

    public void setHeadLineText(String headLineText) {
        this.headLineText = headLineText;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
