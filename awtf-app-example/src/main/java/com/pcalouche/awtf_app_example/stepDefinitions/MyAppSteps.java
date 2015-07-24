package com.pcalouche.awtf_app_example.stepDefinitions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pcalouche.awtf_core.TestInstance;

import cucumber.api.java.en.Given;

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
		TestInstance.getWebDriver().get("file:///" + System.getProperty("user.dir") + TestInstance.getTestEnvironmentConfig().getUrl());
	}
}
