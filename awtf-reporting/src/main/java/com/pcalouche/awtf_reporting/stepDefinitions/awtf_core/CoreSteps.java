package com.pcalouche.awtf_reporting.stepDefinitions.awtf_core;

import com.pcalouche.awtf_core.util.enums.HTMLElementState;
import com.pcalouche.awtf_reporting.Reporter;

import cucumber.api.DataTable;
import cucumber.api.java.en.Then;

/**
 * The core step definitions for the framework.
 *
 * @author Philip Calouche
 *
 */
public class CoreSteps {
	@Then("^I take a screenshot$")
	public void iTakeAScreenshot() {
		Reporter.track("^I take a screenshot$", "This is used to explicitly take a screenshot.", "Then I take a screenshot", "CoreSteps.java");
	}

	@Then("^I click on \"(.*?)\"$")
	public void iClickOn(String text) throws Throwable {
		Reporter.track("^I click on \"(.*?)\"$", "This is used to handle click on a link or button based on the text of that link or button", "Then I click on \"Submit\"", "CoreSteps.java");
	}

	@Then("^I input \"(.*?)\" as \"(.*?)\"$")
	public void iInputAs(String description, String value) {
		Reporter.track("^I input \"(.*?)\" as \"(.*?)\"$", "This is used to handle input of a form element that has an associated label, or it can be used to handle input for a define AppElement",
				"Then I input \"Car Type\" as \"Honda\"<br>Then I input \"[Global Search]\" as \"cute kittens\"", "CoreSteps.java");
	}

	@Then("^I \"(.*?)\" the \"(.*?)\" (?:radio button|checkbox)$")
	public void iTheRadioButtonCheckbox(String actionValue, String description) {
	}

	@Then("^I input \"(.*?)\" as value containing \"(.*?)\"$")
	public void iInputAsValueContaining(String description, String value) {
	}

	@Then("^I input \"(.*?)\" (\\d+) times? into \"(.*?)\"$")
	public void iInputTimesInto(String textString, int numberOfTimes, String description) {
	}

	@Then("^I input \"(.*?)\" range as \"(.*?)\" to \"(.*?)\"$")
	public void iInputRangeAsTo(String description, String startValue, String endValue) {
	}

	@Then("^I see \"(.*?)\" has value of \"(.*?)\"$")
	public void iSeeHasValueOf(String description, String value) {
	}

	@Then("^I see \"(.*?)\" has value containing \"(.*?)\"$")
	public void iSeeHasValueContaining(String description, String value) {
	}

	@Then("^I \"(.*?)\" \"(.*?)\" in the \"(.*?)\" dropdown$")
	public void iInTheDropdown(String action, String value, String description) {
	}

	@Then("^I \"(.*?)\" an option containing \"(.*?)\" in the \"(.*?)\" dropdown$")
	public void iAnOptionContainingInTheDropdown(String action, String value, String description) throws Throwable {
	}

	@Then("^I see \"(.*?)\" contains \"(.*?)\" (\\d+) times?$")
	public void iSeeContainsTimes(String description, String textString, int numberOfTimes) {
	}

	@Then("^I see the (?:message|label|text) \"(.*?)\"$")
	public void iSeeTheMessage(String searchString) {
	}

	@Then("^I do not see the (?:message|label|text) \"(.*?)\"$")
	public void iDoNotSeeTheMessage(String searchString) {
	}

	@Then("^I see the error message \"(.*?)\"$")
	public void iSeeTheErrorMessage(String errorMessage) {
	}

	@Then("^I do not see the error message \"(.*?)\"$")
	public void iDoNotSeeTheErrorMessage(String errorMessage) {
	}

	@Then("^I see the \"(.*?)\" element is \"(.*?)\"$")
	public void iSeeTheElementIs(String description, HTMLElementState htmlElementState) {
	}

	@Then("^I see the \"(.*?)\" button is \"(.*?)\"$")
	public void iSeeTheButtonIs(String buttonText, HTMLElementState htmlElementState) {
	};

	@Then("^I wait for all load masks to disappear$")
	public void iWaitForAllLoadMasksToDisappear() {
	}

	@Then("^I hover over \"([^\"]*)\"$")
	public void iHoverOver(String description) {
	}

	@Then("^I hover over the \"(.*?)\" tooltip element I see a tooltip that says \"(.*?)\"$")
	public void iHoverOverTheTooltipElementISeeATooltipThatSays(String tooltipDescription, String tooltipText) {
	}

	@Then("^I wait for the \"(.*?)\" modal to appear$")
	public void iWaitForTheModalToLoad(String modalDescription) {
	}

	@Then("^I wait for the \"(.*?)\" modal to disappear$")
	public void iWaitForTheModalToDisappear(String modalDescription) {
	}

	@Then("^I \"(.*?)\" the (?:row|rows) with the following criteria:$")
	public void iTheRowWithTheFollowingCriteria(String rowActionDescription, DataTable criteria) {
	}
}
