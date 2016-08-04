package com.pcalouche.awtf_core.stepDefinitions;

import com.pcalouche.awtf_core.CoreStepHandler;
import com.pcalouche.awtf_core.TestEnvironmentConfig;
import com.pcalouche.awtf_core.TestInstance;
import cucumber.api.java.en.Given;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DemoOnlySteps {
    private static final Logger logger = LoggerFactory.getLogger(DemoOnlySteps.class);
    private final CoreStepHandler coreStepHandler;
    private final TestInstance testInstance;
    private final TestEnvironmentConfig testEnvironmentConfig;

    @Autowired
    public DemoOnlySteps(CoreStepHandler coreStepHandler) {
        this.coreStepHandler = coreStepHandler;
        this.testInstance = coreStepHandler.getTestInstance();
        this.testEnvironmentConfig = coreStepHandler.getTestInstance().getTestEnvironmentConfig();
    }

    @Given("^I go to the demo page$")
    public void iGoToTheDemoPage() {
        testInstance.getWebDriver().get("file:///" + System.getProperty("user.dir") + testEnvironmentConfig.getUrl());
    }
}
