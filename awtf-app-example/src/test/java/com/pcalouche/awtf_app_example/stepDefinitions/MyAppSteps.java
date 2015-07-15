package com.pcalouche.awtf_app_example.stepDefinitions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pcalouche.awtf_core.BrowserInstance;

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
		logger.info("file:///" + System.getProperty("user.dir") + BrowserInstance.getTestEnvironmentConfig().getUrl());
		BrowserInstance.getWebDriver().get("file:///" + System.getProperty("user.dir") + BrowserInstance.getTestEnvironmentConfig().getUrl());
	}
}
