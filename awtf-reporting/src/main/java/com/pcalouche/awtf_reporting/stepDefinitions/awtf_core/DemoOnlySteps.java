package com.pcalouche.awtf_reporting.stepDefinitions.awtf_core;

import com.pcalouche.awtf_reporting.Reporter;

import cucumber.api.java.en.Given;

public class DemoOnlySteps {

	@Given("^I go to the demo page$")
	public void iGoToTheDemoPage() {
		Reporter.track("^I go to the demo page$");
	}
}
