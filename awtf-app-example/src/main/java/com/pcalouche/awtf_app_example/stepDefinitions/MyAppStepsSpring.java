package com.pcalouche.awtf_app_example.stepDefinitions;

import com.pcalouche.awtf_app_example.MyAppTestInstance;
import com.pcalouche.awtf_core.CoreStepHandler;
import com.pcalouche.awtf_core.TestInstance;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Step Definitions specific for my app
 *
 * @author Philip Calouche
 */
@Component("myAppSteps")
public class MyAppStepsSpring {
    private static Logger logger = LogManager.getLogger();
    private final CoreStepHandler coreStepHandler;

    @Autowired
    public MyAppStepsSpring(CoreStepHandler coreStepHandler) {
        this.coreStepHandler = coreStepHandler;
    }

    @Given("^I go to the sign on page$")
    public void iGoToTheDemoPage() {
        logger.info("file:///" + System.getProperty("user.dir") + TestInstance.getTestEnvironmentConfig().getUrl());
        MyAppTestInstance.getWebDriver().get("file:///" + System.getProperty("user.dir") + MyAppTestInstance.getTestEnvironmentConfig().getUrl());
    }

    @Then("^I sign into my application$")
    public void iSignIntoMyApplication() {
        coreStepHandler.iInputAs("Login ID", MyAppTestInstance.getMyAppTestEnvironmentConfig().getLoginID());
        coreStepHandler.iInputAs("Password", MyAppTestInstance.getMyAppTestEnvironmentConfig().getPassword());
    }
}
