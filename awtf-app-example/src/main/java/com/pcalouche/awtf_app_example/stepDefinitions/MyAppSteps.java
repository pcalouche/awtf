package com.pcalouche.awtf_app_example.stepDefinitions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pcalouche.awtf_app_example.MyAppTestInstance;
import com.pcalouche.awtf_core.TestInstance;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

/**
 * Step Definitions specific for my app
 *
 * @author Philip Calouche
 *
 */
public class MyAppSteps {
	private static Logger logger = LogManager.getLogger();

	@Given("^I go to the sign on page$")
	public void iGoToTheDemoPage() {
		logger.info("file:///" + System.getProperty("user.dir") + TestInstance.getTestEnvironmentConfig().getUrl());
		MyAppTestInstance.getWebDriver().get("file:///" + System.getProperty("user.dir") + MyAppTestInstance.getTestEnvironmentConfig().getUrl());
	}

	@Then("^I sign into my application$")
	public void iSignIntoMyApplication() {
		MyAppTestInstance.getCoreStepHandler().iInputAs("Login ID", MyAppTestInstance.getMyAppTestEnvironmentConfig().getLoginID());
		MyAppTestInstance.getCoreStepHandler().iInputAs("Password", MyAppTestInstance.getMyAppTestEnvironmentConfig().getPassword());
	}
}
