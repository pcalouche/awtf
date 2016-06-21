package com.pcalouche.awtf_core.stepDefinitions;

import com.pcalouche.awtf_core.TestEnvironmentConfigSpring;
import com.pcalouche.awtf_core.TestInstanceSpring;
import cucumber.api.java.en.Given;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DemoOnlySteps {
    private static final Logger logger = LoggerFactory.getLogger(DemoOnlySteps.class);
    private final TestEnvironmentConfigSpring testEnvironmentConfig;
    private final TestInstanceSpring testInstance;

    @Autowired
    public DemoOnlySteps(TestEnvironmentConfigSpring testEnvironmentConfig, TestInstanceSpring testInstance) {
        this.testEnvironmentConfig = testEnvironmentConfig;
        this.testInstance = testInstance;
        logger.info("Done with DemoOnlySteps constructor->" + testEnvironmentConfig.getBrowserType());
    }


    @Given("^I go to the demo page$")
    public void iGoToTheDemoPage() {
        logger.info("file:///" + System.getProperty("user.dir") + testEnvironmentConfig.getUrl());
        testInstance.getWebDriver().get("file:///" + System.getProperty("user.dir") + testEnvironmentConfig.getUrl());
    }
}
