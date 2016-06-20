package com.pcalouche.awtf_core.stepDefinitions;

import com.pcalouche.awtf_core.TestEnvironmentConfigSpring;
import com.pcalouche.awtf_core.TestInstanceSpring;
import cucumber.api.java.en.Given;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//@Component
public class DemoOnlySteps {
    protected static Logger logger = LogManager.getLogger();
    private final TestEnvironmentConfigSpring testEnvironmentConfig;
    private final TestInstanceSpring testInstance;

    //    @Autowired
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
