package com.factly.jobportal.cucumber.stepdefs;

import com.factly.jobportal.JobportalApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = JobportalApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
