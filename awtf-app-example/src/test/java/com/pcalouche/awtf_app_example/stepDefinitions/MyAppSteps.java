package com.pcalouche.awtf_app_example.stepDefinitions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cucumber.api.java.en.Then;

/**
 * Step Definitions specific for my app
 *
 * @author Philip Calouche
 *
 */
public class MyAppSteps {
	private static Logger logger = LogManager.getLogger();

	@Then("^I make up \"(.*?)\" step$")
	public void iMakeUpStep(String arg1) {
		logger.info("this is my own step");
	}
}
