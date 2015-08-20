package com.pcalouche.awtf_reporting.stepDefinitions.awtf_app_example;

import com.pcalouche.awtf_reporting.Reporter;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

/**
 * Step Definitions specific for my app
 *
 * @author Philip Calouche
 *
 */
public class MyAppSteps {

	@Given("^I go to the sign on page$")
	public void iGoToTheDemoPage() {
		Reporter.track("^I go to the sign on page$", "This is used to launch sign on page for my application.", "Given I go to the sign on page", "MyAppSteps.java");
	}

	@Then("^I sign into my application$")
	public void iSignIntoMyApplication() {
		Reporter.track("^I sign into my application$", "This is used to sign into my application.", "Then I sign into my application", "MyAppSteps.java");
	}
}
