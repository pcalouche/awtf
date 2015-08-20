package com.pcalouche.awtf_core.stepDefinitions;

import com.pcalouche.awtf_core.TestInstance;
import com.pcalouche.awtf_core.util.enums.HTMLElementState;

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
		TestInstance.getCoreStepHandler().iTakeAScreenshot();
	}

	@Then("^I click on \"(.*?)\"$")
	public void iClickOn(String text) throws Throwable {
		TestInstance.getCoreStepHandler().iClickOn(text);
	}

	@Then("^I input \"(.*?)\" as \"(.*?)\"$")
	public void iInputAs(String description, String value) {
		TestInstance.getCoreStepHandler().iInputAs(description, value);
	}

	@Then("^I \"(.*?)\" the \"(.*?)\" (?:radio button|checkbox)$")
	public void iTheRadioButtonCheckbox(String actionValue, String description) {
		TestInstance.getCoreStepHandler().iTheRadioButtonCheckbox(actionValue, description);
	}

	@Then("^I input \"(.*?)\" as value containing \"(.*?)\"$")
	public void iInputAsValueContaining(String description, String value) {
		TestInstance.getCoreStepHandler().iInputAsValueContaining(description, value);
	}

	@Then("^I input \"(.*?)\" (\\d+) times? into \"(.*?)\"$")
	public void iInputTimesInto(String textString, int numberOfTimes, String description) {
		TestInstance.getCoreStepHandler().iInputTimesInto(textString, numberOfTimes, description);
	}

	@Then("^I input \"(.*?)\" range as \"(.*?)\" to \"(.*?)\"$")
	public void iInputRangeAsTo(String description, String startValue, String endValue) {
		TestInstance.getCoreStepHandler().iInputRangeAsTo(description, startValue, endValue);
	}

	@Then("^I see \"(.*?)\" has value of \"(.*?)\"$")
	public void iSeeHasValueOf(String description, String value) {
		TestInstance.getCoreStepHandler().iSeeHasValueOf(description, value);
	}

	@Then("^I see \"(.*?)\" has value containing \"(.*?)\"$")
	public void iSeeHasValueContaining(String description, String value) {
		TestInstance.getCoreStepHandler().iSeeHasValueContaining(description, value);
	}

	@Then("^I \"(.*?)\" \"(.*?)\" in the \"(.*?)\" dropdown$")
	public void iInTheDropdown(String action, String value, String description) {
		TestInstance.getCoreStepHandler().iInTheDropdown(action, value, description);
	}

	@Then("^I \"(.*?)\" an option containing \"(.*?)\" in the \"(.*?)\" dropdown$")
	public void iAnOptionContainingInTheDropdown(String action, String value, String description) throws Throwable {
		TestInstance.getCoreStepHandler().iAnOptionContainingInTheDropdown(action, value, description);
	}

	@Then("^I see \"(.*?)\" contains \"(.*?)\" (\\d+) times?$")
	public void iSeeContainsTimes(String description, String textString, int numberOfTimes) {
		TestInstance.getCoreStepHandler().iSeeContainsTimes(description, textString, numberOfTimes);
	}

	@Then("^I see the (?:message|label|text) \"(.*?)\"$")
	public void iSeeTheMessage(String searchString) {
		TestInstance.getCoreStepHandler().iSeeTheMessage(searchString);
	}

	@Then("^I do not see the (?:message|label|text) \"(.*?)\"$")
	public void iDoNotSeeTheMessage(String searchString) {
		TestInstance.getCoreStepHandler().iDoNotSeeTheMessage(searchString);
	}

	@Then("^I see the error message \"(.*?)\"$")
	public void iSeeTheErrorMessage(String errorMessage) {
		TestInstance.getCoreStepHandler().iSeeTheErrorMessage(errorMessage);
	}

	@Then("^I do not see the error message \"(.*?)\"$")
	public void iDoNotSeeTheErrorMessage(String errorMessage) {
		TestInstance.getCoreStepHandler().iDoNotSeeTheErrorMessage(errorMessage);
	}

	@Then("^I see the \"(.*?)\" element is \"(.*?)\"$")
	public void iSeeTheElementIs(String description, HTMLElementState htmlElementState) {
		TestInstance.getCoreStepHandler().iSeeTheElementIs(description, htmlElementState);
	}

	@Then("^I see the \"(.*?)\" button is \"(.*?)\"$")
	public void iSeeTheButtonIs(String buttonText, HTMLElementState htmlElementState) {
		TestInstance.getCoreStepHandler().iSeeTheButtonIs(buttonText, htmlElementState);
	};

	@Then("^I wait for all load masks to disappear$")
	public void iWaitForAllLoadMasksToDisappear() {
		TestInstance.getCoreStepHandler().iWaitForAllLoadMasksToDisappear();
	}

	@Then("^I hover over \"([^\"]*)\"$")
	public void iHoverOver(String description) {
		TestInstance.getCoreStepHandler().iHoverOver(description);
	}

	@Then("^I hover over the \"(.*?)\" tooltip element I see a tooltip that says \"(.*?)\"$")
	public void iHoverOverTheTooltipElementISeeATooltipThatSays(String tooltipDescription, String tooltipText) {
		TestInstance.getCoreStepHandler().iHoverOverTheTooltipElementISeeATooltipThatSays(tooltipDescription, tooltipText);
	}

	@Then("^I wait for the \"(.*?)\" modal to appear$")
	public void iWaitForTheModalToLoad(String modalDescription) {
		TestInstance.getCoreStepHandler().iWaitForTheModalToAppear(modalDescription);
	}

	@Then("^I wait for the \"(.*?)\" modal to disappear$")
	public void iWaitForTheModalToDisappear(String modalDescription) {
		TestInstance.getCoreStepHandler().iWaitForTheModalToDisappear(modalDescription);
	}

	@Then("^I \"(.*?)\" the (?:row|rows) with the following criteria:$")
	public void iTheRowWithTheFollowingCriteria(String rowActionDescription, DataTable criteria) {
		TestInstance.getCoreStepHandler().iTheRowWithTheFollowingCriteria(rowActionDescription, criteria);
	}
}
