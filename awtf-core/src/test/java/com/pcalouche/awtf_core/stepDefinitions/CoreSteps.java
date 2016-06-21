package com.pcalouche.awtf_core.stepDefinitions;

import com.pcalouche.awtf_core.CoreStepHandlerSpring;
import com.pcalouche.awtf_core.util.enums.HTMLElementState;
import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The core step definitions for the framework.
 *
 * @author Philip Calouche
 */
public class CoreSteps {
    private static final Logger logger = LoggerFactory.getLogger(CoreSteps.class);
    private final CoreStepHandlerSpring stepHandler;

    @Autowired
    public CoreSteps(CoreStepHandlerSpring stepHandler) {
        this.stepHandler = stepHandler;
        logger.info("Done with CoreSteps constructor");
    }

    @Then("^I take a screenshot$")
    public void iTakeAScreenshot() {
        stepHandler.iTakeAScreenshot();
    }

    @Then("^I click on \"(.*?)\"$")
    public void iClickOn(String text) throws Throwable {
        stepHandler.iClickOn(text);
    }

    @Then("^I input \"(.*?)\" as \"(.*?)\"$")
    public void iInputAs(String description, String value) {
        stepHandler.iInputAs(description, value);
    }

    @Then("^I \"(.*?)\" the \"(.*?)\" (?:radio button|checkbox)$")
    public void iTheRadioButtonCheckbox(String actionValue, String description) {
        stepHandler.iTheRadioButtonCheckbox(actionValue, description);
    }

    @Then("^I input \"(.*?)\" as value containing \"(.*?)\"$")
    public void iInputAsValueContaining(String description, String value) {
        stepHandler.iInputAsValueContaining(description, value);
    }

    @Then("^I input \"(.*?)\" (\\d+) times? into \"(.*?)\"$")
    public void iInputTimesInto(String textString, int numberOfTimes, String description) {
        stepHandler.iInputTimesInto(textString, numberOfTimes, description);
    }

    @Then("^I input \"(.*?)\" range as \"(.*?)\" to \"(.*?)\"$")
    public void iInputRangeAsTo(String description, String startValue, String endValue) {
        stepHandler.iInputRangeAsTo(description, startValue, endValue);
    }

    @Then("^I see \"(.*?)\" has value of \"(.*?)\"$")
    public void iSeeHasValueOf(String description, String value) {
        stepHandler.iSeeHasValueOf(description, value);
    }

    @Then("^I see \"(.*?)\" has value containing \"(.*?)\"$")
    public void iSeeHasValueContaining(String description, String value) {
        stepHandler.iSeeHasValueContaining(description, value);
    }

    @Then("^I \"(.*?)\" \"(.*?)\" in the \"(.*?)\" dropdown$")
    public void iInTheDropdown(String action, String value, String description) {
        stepHandler.iInTheDropdown(action, value, description);
    }

    @Then("^I \"(.*?)\" an option containing \"(.*?)\" in the \"(.*?)\" dropdown$")
    public void iAnOptionContainingInTheDropdown(String action, String value, String description) throws Throwable {
        stepHandler.iAnOptionContainingInTheDropdown(action, value, description);
    }

    @Then("^I see \"(.*?)\" contains \"(.*?)\" (\\d+) times?$")
    public void iSeeContainsTimes(String description, String textString, int numberOfTimes) {
        stepHandler.iSeeContainsTimes(description, textString, numberOfTimes);
    }

    @Then("^I see the (?:message|label|text) \"(.*?)\"$")
    public void iSeeTheMessage(String searchString) {
        stepHandler.iSeeTheMessage(searchString);
    }

    @Then("^I do not see the (?:message|label|text) \"(.*?)\"$")
    public void iDoNotSeeTheMessage(String searchString) {
        stepHandler.iDoNotSeeTheMessage(searchString);
    }

    @Then("^I see the error message \"(.*?)\"$")
    public void iSeeTheErrorMessage(String errorMessage) {
        stepHandler.iSeeTheErrorMessage(errorMessage);
    }

    @Then("^I do not see the error message \"(.*?)\"$")
    public void iDoNotSeeTheErrorMessage(String errorMessage) {
        stepHandler.iDoNotSeeTheErrorMessage(errorMessage);
    }

    @Then("^I see the \"(.*?)\" element is \"(.*?)\"$")
    public void iSeeTheElementIs(String description, HTMLElementState htmlElementState) {
        stepHandler.iSeeTheElementIs(description, htmlElementState);
    }

    @Then("^I see the \"(.*?)\" button is \"(.*?)\"$")
    public void iSeeTheButtonIs(String buttonText, HTMLElementState htmlElementState) {
        stepHandler.iSeeTheButtonIs(buttonText, htmlElementState);
    }

    @Then("^I wait for all load masks to disappear$")
    public void iWaitForAllLoadMasksToDisappear() {
        stepHandler.iWaitForAllLoadMasksToDisappear();
    }

    @Then("^I hover over \"([^\"]*)\"$")
    public void iHoverOver(String description) {
        stepHandler.iHoverOver(description);
    }

    @Then("^I hover over the \"(.*?)\" tooltip element I see a tooltip that says \"(.*?)\"$")
    public void iHoverOverTheTooltipElementISeeATooltipThatSays(String tooltipDescription, String tooltipText) {
        stepHandler.iHoverOverTheTooltipElementISeeATooltipThatSays(tooltipDescription, tooltipText);
    }

    @Then("^I wait for the \"(.*?)\" modal to appear$")
    public void iWaitForTheModalToLoad(String modalDescription) {
        stepHandler.iWaitForTheModalToAppear(modalDescription);
    }

    @Then("^I wait for the \"(.*?)\" modal to disappear$")
    public void iWaitForTheModalToDisappear(String modalDescription) {
        stepHandler.iWaitForTheModalToDisappear(modalDescription);
    }

    @Then("^I \"(.*?)\" the (?:row|rows) with the following criteria:$")
    public void iTheRowWithTheFollowingCriteria(String rowActionDescription, DataTable criteria) {
        stepHandler.iTheRowWithTheFollowingCriteria(rowActionDescription, criteria);
    }
}
