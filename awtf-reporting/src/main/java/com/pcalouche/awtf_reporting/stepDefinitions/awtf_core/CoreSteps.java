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
		Reporter.track("^I take a screenshot$", "This is used to explicitly take a screenshot.", "Then I take a screenshot", "CoreSteps.java", 1);
	}

	@Then("^I click on \"(.*?)\"$")
	public void iClickOn(String text) throws Throwable {
		Reporter.track("^I click on \"(.*?)\"$", "This is used to handle click on a link or button based on the text of that link or button", "Then I click on \"Submit\"", "CoreSteps.java", 2);
	}

	@Then("^I input \"(.*?)\" as \"(.*?)\"$")
	public void iInputAs(String description, String value) {
		Reporter.track("^I input \"(.*?)\" as \"(.*?)\"$", "This is used to handle input of a form element that has an associated label, or it can be used to handle input for a defined AppElement.",
				"Then I input \"Car Type\" as \"Honda\"<br>Then I input \"[Global Search]\" as \"cute kittens\"", "CoreSteps.java", 3);
	}

	@Then("^I \"(.*?)\" the \"(.*?)\" (?:radio button|checkbox)$")
	public void iTheRadioButtonCheckbox(String actionValue, String description) {
		Reporter.track("^I \"(.*?)\" the \"(.*?)\" (?:radio button|checkbox)$", "This is used to handle selecting and deselecting radio buttons and checkboxes.",
				"Then I \"select\" the \"Radio Option 2\" radio buttonThen I \"deselect\" the \"Checkbox 1\" checkbox", "CoreSteps.java", 4);
	}

	@Then("^I input \"(.*?)\" as value containing \"(.*?)\"$")
	public void iInputAsValueContaining(String description, String value) {
		Reporter.track("^I input \"(.*?)\" as value containing \"(.*?)\"$",
				"This is used to handle input for a dropdown that has an associated label, or it can be used to handle input for a defined AppElement.  This is useful if the values of the form element always have a dyanmic portion.",
				"Then I input \"Dropdown 2\" as value containing \"Pay to 345345423\"", "CoreSteps.java", 5);
	}

	@Then("^I input \"(.*?)\" (\\d+) times? into \"(.*?)\"$")
	public void iInputTimesInto(String textString, int numberOfTimes, String description) {
		Reporter.track("^I input \"(.*?)\" (\\d+) times? into \"(.*?)\"$",
				"This is used to input a value X number of times as the value of a form element. This can be useful for testing the max length of a field.",
				"Then I input \"a\" 50 times into \"Textarea 1\"<br>Then I input \"b\" 10 times into \"[Global Search]\"", "CoreSteps.java", 6);
	}

	@Then("^I input \"(.*?)\" range as \"(.*?)\" to \"(.*?)\"$")
	public void iInputRangeAsTo(String description, String startValue, String endValue) {
		Reporter.track("^I input \"(.*?)\" range as \"(.*?)\" to \"(.*?)\"$",
				"This is used to handle input for two form elements that represent the range.  This step assumes the inputs are siblings.",
				"Then I input \"Price Range Example\" range as \"50.00\" to \"150.00\"", "CoreSteps.java", 7);
	}

	@Then("^I see \"(.*?)\" has value of \"(.*?)\"$")
	public void iSeeHasValueOf(String description, String value) {
		Reporter.track("^I see \"(.*?)\" has value of \"(.*?)\"$",
				"This is used to verify the value of a form element that has an associated label, or it can be used to handle input for a defined AppElement. If the form element is a checkbox or radio button, the value to verify against should either be \"selected\" or \"deselected\".",
				"Then I see \"Text Field 1\" has value of \"some input\"<br>Then I see \"Checkbox 1\" has value of \"selected\"<br>Then I see \"Checkbox 1\" has value of \"deselected\"<br>Then I see \"[Global Search]\" has value of \"cute kittens\"<br>Then I see \"[Account History Dropdown]\" has value of \"60 Days\"",
				"CoreSteps.java", 8);
	}

	@Then("^I see \"(.*?)\" has value containing \"(.*?)\"$")
	public void iSeeHasValueContaining(String description, String value) {
		Reporter.track("^I see \"(.*?)\" has value containing \"(.*?)\"$",
				"This is used to verify if the value of a form element contains the given value.  This is useful to test the value of a form element if part of its value is always dynamic.",
				"Then I see \"Dropdown 2\" has value containing \"Pay to 636883435\"", "CoreSteps.java", 9);
	}

	@Then("^I \"(.*?)\" \"(.*?)\" in the \"(.*?)\" dropdown$")
	public void iInTheDropdown(String action, String value, String description) {
		Reporter.track("^I \"(.*?)\" \"(.*?)\" in the \"(.*?)\" dropdown$", "This is used to verify if an option is in a dropdown or not.",
				"Then I \"see\" \"Option 2\" in the \"Dropdown 1\" dropdown <br>Then I \"do not see\" \"Option 4\" in the \"Dropdown 1\" dropdown", "CoreSteps.java", 10);
	}

	@Then("^I \"(.*?)\" an option containing \"(.*?)\" in the \"(.*?)\" dropdown$")
	public void iAnOptionContainingInTheDropdown(String action, String value, String description) throws Throwable {
		Reporter.track("^I \"(.*?)\" an option containing \"(.*?)\" in the \"(.*?)\" dropdown$", "This is used to verify if an option that contains a given value is in a dropdown or not.",
				"Then I \"see\" an option containing \"Option\" in the \"Dropdown 1\" dropdown <br>Then I \"do not see\" an option containing \"Option 4\" in the \"Dropdown 1\" dropdown",
				"CoreSteps.java", 11);
	}

	@Then("^I see \"(.*?)\" contains \"(.*?)\" (\\d+) times?$")
	public void iSeeContainsTimes(String description, String textString, int numberOfTimes) {
		Reporter.track("^I see \"(.*?)\" contains \"(.*?)\" (\\d+) times?$",
				"This is used to determine if a value appears X number of times in the value of a form element. This can be useful for testing the max length of a field.",
				"Then I see \"Textarea 1\" contains \"a\" 50 times <br>Then I see \"[Global Search]\" contains \"b\" 10 times", "CoreSteps.java", 12);
	}

	@Then("^I see the (?:message|label|text) \"(.*?)\"$")
	public void iSeeTheMessage(String searchString) {
		Reporter.track("^I see the (?:message|label|text) \"(.*?)\"$",
				"This is used to see if a message/text/label is visible. The words message, text, and label can all be used interchangebly. Plain text can be used, or reference to a resources file can be used to determine what text to look for.",
				"Then I see the text \"Text in a span to look for\"<br>Then I see the label \"Text in a label to look for\"<br>Then I see the message \"[disclaimer1]\"", "CoreSteps.java", 13);
	}

	@Then("^I do not see the (?:message|label|text) \"(.*?)\"$")
	public void iDoNotSeeTheMessage(String searchString) {
		Reporter.track("^I do not see the (?:message|label|text) \"(.*?)\"$",
				"This is used to see if a message/text/label is not visible. The words message, text, and label can all be used interchangebly. Plain text can be used, or reference to a resources file can be used to determine what text to look for.",
				"Then I do not see the text \"Text in a span to look for\"<br>Then I do not see the label \"Text in a label to look for\"<br>Then I do not see the message \"[disclaimer1]\"",
				"CoreSteps.java", 14);
	}

	@Then("^I see the error message \"(.*?)\"$")
	public void iSeeTheErrorMessage(String errorMessage) {
		Reporter.track("^I see the error message \"(.*?)\"$",
				"This is used to see if an error message is visible. Plain text can be used, or reference to a resources file can be used to determine what text to look for. The error message selector can be configured as part of the AppConfig.",
				"Then I see the error message \"Error message 1\"<br>Then I see the error message \"[error1]\"", "CoreSteps.java", 15);
	}

	@Then("^I do not see the error message \"(.*?)\"$")
	public void iDoNotSeeTheErrorMessage(String errorMessage) {
		Reporter.track("^I do not see the error message \"(.*?)\"$",
				"This is used to see if an error message is not visible. Plain text can be used, or reference to a resources file can be used to determine what text to look for. The error message selector can be configured as part of the AppConfig.",
				"Then I do not see the error message \"Error message 1\"<br>Then I do not see the error message \"[error1]\"", "CoreSteps.java", 16);
	}

	@Then("^I see the \"(.*?)\" element is \"(.*?)\"$")
	public void iSeeTheElementIs(String description, HTMLElementState htmlElementState) {
		Reporter.track("^I see the \"(.*?)\" element is \"(.*?)\"$",
				"This step is used to validate the state of an element. Text can be used to look for a form element that is associated with a label that has that text, or a defined AppElement can be used to find the element to verify.",
				"Then I see the \"Windows 7\" element is \"visible\"<br>Then I see the \"Windows 7\" element is \"hidden\"<br>Then I see the \"[Animal Dropdown]\" element is \"enabled\"<br>Then I see the \"[Animal Dropdown]\" element is \"disabled\"",
				"CoreSteps.java", 17);
	}

	@Then("^I see the \"(.*?)\" button is \"(.*?)\"$")
	public void iSeeTheButtonIs(String buttonText, HTMLElementState htmlElementState) {
		Reporter.track("^I see the \"(.*?)\" button is \"(.*?)\"$", "This step is used to validate the state of a button. This just searches for a button with the given text.",
				"Then I see the \"Enabled Button Tag Button\" button is \"enabled\"<br>Then I see the \"Disabled Button Tag Button\" button is \"visible\"<br>Then I see the \"Disabled Button Tag Button\" button is \"disabled\"<br>Then I see the \"Hidden Button Tag Button\" button is \"hidden\"",
				"CoreSteps.java", 18);
	};

	@Then("^I wait for all load masks to disappear$")
	public void iWaitForAllLoadMasksToDisappear() {
		Reporter.track("^I wait for all load masks to disappear$",
				"This step is used to explicity wait for all load masks to disappaer. The selector for loading indicators can be configured as part of the AppConfig.",
				"Then I wait for all load masks to disappear", "CoreSteps.java", 19);
	}

	@Then("^I hover over \"([^\"]*)\"$")
	public void iHoverOver(String description) {
		Reporter.track("^I hover over \"([^\"]*)\"$", "This is used to hover an element. Text can be used to look for text to hover over, or a defined AppElement can be used.",
				"When I hover over \"Tutorials\"<br>And I hover over \"[Web Design Menu Item]\"", "CoreSteps.java", 20);
	}

	@Then("^I hover over the \"(.*?)\" tooltip element I see a tooltip that says \"(.*?)\"$")
	public void iHoverOverTheTooltipElementISeeATooltipThatSays(String tooltipDescription, String tooltipText) {
		Reporter.track("^I hover over the \"(.*?)\" tooltip element I see a tooltip that says \"(.*?)\"$",
				"This is used to hover over an element that has a tooltip and verify the text of that tooltip.",
				"When I hover over the \"Info Icon\" tooltip element I see a tooltip that says \"This is the info icon tooltip\"<br>When I hover over the \"Help Icon\" tooltip element I see a tooltip that says \"[helpIconTooltipMessage]\"",
				"CoreSteps.java", 21);
	}

	@Then("^I wait for the \"(.*?)\" modal to appear$")
	public void iWaitForTheModalToLoad(String modalDescription) {
		Reporter.track("^I wait for the \"(.*?)\" modal to appear$", "This is used to wait for a specific modal to appear.", "Then I wait for the \"Confirm Purchase\" modal to appear",
				"CoreSteps.java", 22);
	}

	@Then("^I wait for the \"(.*?)\" modal to disappear$")
	public void iWaitForTheModalToDisappear(String modalDescription) {
		Reporter.track("^I wait for the \"(.*?)\" modal to disappear$", "This is used to wait for a specific modal to disappear.", "Then I wait for the \"Confirm Purchase\" modal to disappear",
				"CoreSteps.java", 23);
	}

	@Then("^I \"(.*?)\" the (?:row|rows) with the following criteria:$")
	public void iTheRowWithTheFollowingCriteria(String rowActionDescription, DataTable criteria) {
		Reporter.track("^I \"(.*?)\" the (?:row|rows) with the following criteria:$", "", "", "CoreSteps.java", 24);
	}
}
