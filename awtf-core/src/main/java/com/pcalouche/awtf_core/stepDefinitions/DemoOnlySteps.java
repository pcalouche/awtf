package com.pcalouche.awtf_core.stepDefinitions;

import com.pcalouche.awtf_core.TestInstance;
import cucumber.api.java.en.Given;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DemoOnlySteps {
    private static final Logger logger = LoggerFactory.getLogger(DemoOnlySteps.class);
    private final TestInstance testInstance;

    @Autowired
    public DemoOnlySteps(TestInstance testInstance) {
        this.testInstance = testInstance;
        logger.info("Done with DemoOnlySteps constructor->" + testInstance.getTestEnvironmentConfig().getBrowserType());
    }


    @Given("^I go to the demo page$")
    public void iGoToTheDemoPage() {
        logger.info("file:///" + System.getProperty("user.dir") + testInstance.getTestEnvironmentConfig().getUrl());
        testInstance.getWebDriver().get("file:///" + System.getProperty("user.dir") + testInstance.getTestEnvironmentConfig().getUrl());
    }
}
