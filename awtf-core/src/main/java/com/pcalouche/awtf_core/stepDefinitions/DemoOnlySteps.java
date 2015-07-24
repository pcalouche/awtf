package com.pcalouche.awtf_core.stepDefinitions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pcalouche.awtf_core.TestInstance;

import cucumber.api.java.en.Given;

public class DemoOnlySteps {
	protected static Logger logger = LogManager.getLogger();

	@Given("^I go to the demo page$")
	public void iGoToTheDemoPage() {
		logger.info("file:///" + System.getProperty("user.dir") + TestInstance.getTestEnvironmentConfig().getUrl());
		TestInstance.getWebDriver().get("file:///" + System.getProperty("user.dir") + TestInstance.getTestEnvironmentConfig().getUrl());
	}
}
