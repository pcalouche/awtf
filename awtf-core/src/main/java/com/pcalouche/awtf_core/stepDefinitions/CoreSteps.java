package com.pcalouche.awtf_core.stepDefinitions;

import com.pcalouche.awtf_core.CoreStepHandler;
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
    private final CoreStepHandler coreStepHandler;

    @Autowired
    public CoreSteps(CoreStepHandler coreStepHandler) {
        this.coreStepHandler = coreStepHandler;
        logger.info("Done with CoreSteps constructor");
    }

    @Then("^I take a screenshot$")
    public void iTakeAScreenshot() {
        coreStepHandler.iTakeAScreenshot();
    }

    @Then("^I click on \"(.*?)\"$")
    public void iClickOn(String text) throws Throwable {
        coreStepHandler.iClickOn(text);
    }

    @Then("^I input \"(.*?)\" as \"(.*?)\"$")
    public void iInputAs(String description, String value) {
        coreStepHandler.iInputAs(description, value);
    }

    @Then("^I \"(.*?)\" the \"(.*?)\" (?:radio button|checkbox)$")
    public void iTheRadioButtonCheckbox(String actionValue, String description) {
        coreStepHandler.iTheRadioButtonCheckbox(actionValue, description);
    }

    @Then("^I input \"(.*?)\" as value containing \"(.*?)\"$")
    public void iInputAsValueContaining(String description, String value) {
        coreStepHandler.iInputAsValueContaining(description, value);
    }

    @Then("^I input \"(.*?)\" (\\d+) times? into \"(.*?)\"$")
    public void iInputTimesInto(String textString, int numberOfTimes, String description) {
        coreStepHandler.iInputTimesInto(textString, numberOfTimes, description);
    }

    @Then("^I input \"(.*?)\" range as \"(.*?)\" to \"(.*?)\"$")
    public void iInputRangeAsTo(String description, String startValue, String endValue) {
        coreStepHandler.iInputRangeAsTo(description, startValue, endValue);
    }

    @Then("^I see \"(.*?)\" has value of \"(.*?)\"$")
    public void iSeeHasValueOf(String description, String value) {
        coreStepHandler.iSeeHasValueOf(description, value);
    }

    @Then("^I see \"(.*?)\" has value containing \"(.*?)\"$")
    public void iSeeHasValueContaining(String description, String value) {
        coreStepHandler.iSeeHasValueContaining(description, value);
    }

    @Then("^I \"(.*?)\" \"(.*?)\" in the \"(.*?)\" dropdown$")
    public void iInTheDropdown(String action, String value, String description) {
        coreStepHandler.iInTheDropdown(action, value, description);
    }

    @Then("^I \"(.*?)\" an option containing \"(.*?)\" in the \"(.*?)\" dropdown$")
    public void iAnOptionContainingInTheDropdown(String action, String value, String description) throws Throwable {
        coreStepHandler.iAnOptionContainingInTheDropdown(action, value, description);
    }

    @Then("^I see \"(.*?)\" contains \"(.*?)\" (\\d+) times?$")
    public void iSeeContainsTimes(String description, String textString, int numberOfTimes) {
        coreStepHandler.iSeeContainsTimes(description, textString, numberOfTimes);
    }

    @Then("^I see the (?:message|label|text) \"(.*?)\"$")
    public void iSeeTheMessage(String searchString) {
        coreStepHandler.iSeeTheMessage(searchString);
    }

    @Then("^I do not see the (?:message|label|text) \"(.*?)\"$")
    public void iDoNotSeeTheMessage(String searchString) {
        coreStepHandler.iDoNotSeeTheMessage(searchString);
    }

    @Then("^I see the error message \"(.*?)\"$")
    public void iSeeTheErrorMessage(String errorMessage) {
        coreStepHandler.iSeeTheErrorMessage(errorMessage);
    }

    @Then("^I do not see the error message \"(.*?)\"$")
    public void iDoNotSeeTheErrorMessage(String errorMessage) {
        coreStepHandler.iDoNotSeeTheErrorMessage(errorMessage);
    }

    @Then("^I see the \"(.*?)\" element is \"(.*?)\"$")
    public void iSeeTheElementIs(String description, HTMLElementState htmlElementState) {
        coreStepHandler.iSeeTheElementIs(description, htmlElementState);
    }

    @Then("^I see the \"(.*?)\" button is \"(.*?)\"$")
    public void iSeeTheButtonIs(String buttonText, HTMLElementState htmlElementState) {
        coreStepHandler.iSeeTheButtonIs(buttonText, htmlElementState);
    }

    @Then("^I wait for all load masks to disappear$")
    public void iWaitForAllLoadMasksToDisappear() {
        coreStepHandler.iWaitForAllLoadMasksToDisappear();
    }

    @Then("^I hover over \"([^\"]*)\"$")
    public void iHoverOver(String description) {
        coreStepHandler.iHoverOver(description);
    }

    @Then("^I hover over the \"(.*?)\" tooltip element I see a tooltip that says \"(.*?)\"$")
    public void iHoverOverTheTooltipElementISeeATooltipThatSays(String tooltipDescription, String tooltipText) {
        coreStepHandler.iHoverOverTheTooltipElementISeeATooltipThatSays(tooltipDescription, tooltipText);
    }

    @Then("^I wait for the \"(.*?)\" modal to appear$")
    public void iWaitForTheModalToLoad(String modalDescription) {
        coreStepHandler.iWaitForTheModalToAppear(modalDescription);
    }

    @Then("^I wait for the \"(.*?)\" modal to disappear$")
    public void iWaitForTheModalToDisappear(String modalDescription) {
        coreStepHandler.iWaitForTheModalToDisappear(modalDescription);
    }

    @Then("^I \"(.*?)\" the (?:row|rows) with the following criteria:$")
    public void iTheRowWithTheFollowingCriteria(String rowActionDescription, DataTable criteria) {
        coreStepHandler.iTheRowWithTheFollowingCriteria(rowActionDescription, criteria);
    }
}
