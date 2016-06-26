package com.pcalouche.awtf_app_example.stepDefinitions;

import com.pcalouche.awtf_app_example.MyAppStepHandler;
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
//@Component("myAppSteps")
public class MyAppSteps {
    private static final Logger logger = LoggerFactory.getLogger(MyAppSteps.class);
    private final MyAppTestInstance myAppTestInstance;
    private final MyAppStepHandler myAppStepHandler;

    @Autowired
    public MyAppSteps(MyAppTestInstance myAppTestInstance, MyAppStepHandler myAppStepHandler) {
        this.myAppTestInstance = myAppTestInstance;
        this.myAppStepHandler = myAppStepHandler;
        logger.info("end of MyAppSteps constructor");
    }

    @Given("^I go to the sign on page$")
    public void iGoToTheDemoPage() {
        logger.info("file:///" + System.getProperty("user.dir") + myAppTestInstance.getTestEnvironmentConfig().getUrl());
        myAppTestInstance.getWebDriver().get("file:///" + System.getProperty("user.dir") + myAppTestInstance.getTestEnvironmentConfig().getUrl());
    }

    @Then("^I sign into my application$")
    public void iSignIntoMyApplication() {
        myAppStepHandler.iInputAs("Login ID", myAppTestInstance.getMyAppTestEnvironmentConfig().getLoginID());
        myAppStepHandler.iInputAs("Password", myAppTestInstance.getMyAppTestEnvironmentConfig().getPassword());
    }
}
