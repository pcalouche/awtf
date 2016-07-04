package com.pcalouche.awtf_app_example.stepDefinitions;

import com.pcalouche.awtf_app_example.MyAppStepHandler;
import com.pcalouche.awtf_app_example.MyAppTestEnvironmentConfig;
import com.pcalouche.awtf_app_example.MyAppTestInstance;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Step Definitions specific for my app
 *
 * @author Philip Calouche
 */
public class MyAppSteps {
    private static final Logger logger = LoggerFactory.getLogger(MyAppSteps.class);
    private final MyAppStepHandler myAppStepHandler;
    private final MyAppTestInstance myAppTestInstance;
    private final MyAppTestEnvironmentConfig myAppTestEnvironmentConfig;

    @Autowired
    public MyAppSteps(MyAppStepHandler myAppStepHandler) {
        this.myAppStepHandler = myAppStepHandler;
        this.myAppTestInstance = myAppStepHandler.getMyAppTestInstance();
        this.myAppTestEnvironmentConfig = myAppStepHandler.getMyAppTestInstance().getMyAppTestEnvironmentConfig();
        logger.info("end of MyAppSteps constructor");
    }

    @Given("^I go to the sign on page$")
    public void iGoToTheDemoPage() {
        logger.info("file:///" + System.getProperty("user.dir") + myAppTestEnvironmentConfig.getUrl());
        myAppTestInstance.getWebDriver().get("file:///" + System.getProperty("user.dir") + myAppTestEnvironmentConfig.getUrl());
    }

    @Then("^I sign into my application$")
    public void iSignIntoMyApplication() {
        myAppStepHandler.iInputAs("Login ID", myAppTestEnvironmentConfig.getLoginID());
        myAppStepHandler.iInputAs("Password", myAppTestEnvironmentConfig.getPassword());
    }
}
