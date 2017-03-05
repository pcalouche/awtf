package com.pcalouche.awtf.steps;

import com.pcalouche.awtf.config.spring.CoreConfig;
import com.pcalouche.awtf.stepHandlers.CoreStepsHandler;
import com.pcalouche.awtf.util.enums.HTMLElementState;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

import java.io.IOException;

/**
 * The core step definitions for the framework.
 *
 * @author Philip Calouche
 */
@ContextHierarchy({
        @ContextConfiguration(classes = {CoreConfig.class})
})
public class CoreSteps {
    private final CoreStepsHandler coreStepsHandler;

    @Autowired
    public CoreSteps(CoreStepsHandler coreStepsHandler) {
        this.coreStepsHandler = coreStepsHandler;
    }

    @Then("^I take a screenshot$")
    public void iTakeAScreenshot() {
        coreStepsHandler.iTakeAScreenshot();
    }

    @Then("^I click on \"(.*?)\"$")
    public void iClickOn(String text) throws Throwable {
        coreStepsHandler.iClickOn(text);
    }

    @Then("^I input \"(.*?)\" as \"(.*?)\"$")
    public void iInputAs(String description, String value) {
        coreStepsHandler.iInputAs(description, value);
    }

    @Then("^I \"(.*?)\" the \"(.*?)\" (?:radio button|checkbox)$")
    public void iTheRadioButtonCheckbox(String actionValue, String description) {
        coreStepsHandler.iTheRadioButtonCheckbox(actionValue, description);
    }

    @Then("^I input \"(.*?)\" as value containing \"(.*?)\"$")
    public void iInputAsValueContaining(String description, String value) {
        coreStepsHandler.iInputAsValueContaining(description, value);
    }

    @Then("^I input \"(.*?)\" (\\d+) times? into \"(.*?)\"$")
    public void iInputTimesInto(String textString, int numberOfTimes, String description) {
        coreStepsHandler.iInputTimesInto(textString, numberOfTimes, description);
    }

    @Then("^I input \"(.*?)\" range as \"(.*?)\" to \"(.*?)\"$")
    public void iInputRangeAsTo(String description, String startValue, String endValue) {
        coreStepsHandler.iInputRangeAsTo(description, startValue, endValue);
    }

    @Then("^I see \"(.*?)\" has value of \"(.*?)\"$")
    public void iSeeHasValueOf(String description, String value) {
        coreStepsHandler.iSeeHasValueOf(description, value);
    }

    @Then("^I see \"(.*?)\" has value containing \"(.*?)\"$")
    public void iSeeHasValueContaining(String description, String value) {
        coreStepsHandler.iSeeHasValueContaining(description, value);
    }

    @Then("^I \"(.*?)\" \"(.*?)\" in the \"(.*?)\" dropdown$")
    public void iInTheDropdown(String action, String value, String description) {
        coreStepsHandler.iInTheDropdown(action, value, description);
    }

    @Then("^I \"(.*?)\" an option containing \"(.*?)\" in the \"(.*?)\" dropdown$")
    public void iAnOptionContainingInTheDropdown(String action, String value, String description) throws Throwable {
        coreStepsHandler.iAnOptionContainingInTheDropdown(action, value, description);
    }

    @Then("^I see \"(.*?)\" contains \"(.*?)\" (\\d+) times?$")
    public void iSeeContainsTimes(String description, String textString, int numberOfTimes) {
        coreStepsHandler.iSeeContainsTimes(description, textString, numberOfTimes);
    }

    @Then("^I see the (?:message|label|text) \"(.*?)\"$")
    public void iSeeTheMessage(String searchString) {
        coreStepsHandler.iSeeTheMessage(searchString);
    }

    @Then("^I do not see the (?:message|label|text) \"(.*?)\"$")
    public void iDoNotSeeTheMessage(String searchString) {
        coreStepsHandler.iDoNotSeeTheMessage(searchString);
    }

    @Then("^I see the error message \"(.*?)\"$")
    public void iSeeTheErrorMessage(String errorMessage) {
        coreStepsHandler.iSeeTheErrorMessage(errorMessage);
    }

    @Then("^I do not see the error message \"(.*?)\"$")
    public void iDoNotSeeTheErrorMessage(String errorMessage) {
        coreStepsHandler.iDoNotSeeTheErrorMessage(errorMessage);
    }

    @Then("^I see the \"(.*?)\" element is \"(.*?)\"$")
    public void iSeeTheElementIs(String description, HTMLElementState htmlElementState) {
        coreStepsHandler.iSeeTheElementIs(description, htmlElementState);
    }

    @Then("^I see the \"(.*?)\" button is \"(.*?)\"$")
    public void iSeeTheButtonIs(String buttonText, HTMLElementState htmlElementState) {
        coreStepsHandler.iSeeTheButtonIs(buttonText, htmlElementState);
    }

    @Then("^I wait for all load masks to disappear$")
    public void iWaitForAllLoadMasksToDisappear() {
        coreStepsHandler.iWaitForAllLoadMasksToDisappear();
    }

    @Then("^I hover over \"([^\"]*)\"$")
    public void iHoverOver(String description) {
        coreStepsHandler.iHoverOver(description);
    }

    @Then("^I hover over the \"(.*?)\" tooltip element I see a tooltip that says \"(.*?)\"$")
    public void iHoverOverTheTooltipElementISeeATooltipThatSays(String tooltipDescription, String tooltipText) {
        coreStepsHandler.iHoverOverTheTooltipElementISeeATooltipThatSays(tooltipDescription, tooltipText);
    }

    @Then("^I wait for the \"(.*?)\" modal to appear$")
    public void iWaitForTheModalToLoad(String modalDescription) {
        coreStepsHandler.iWaitForTheModalToAppear(modalDescription);
    }

    @Then("^I wait for the \"(.*?)\" modal to disappear$")
    public void iWaitForTheModalToDisappear(String modalDescription) {
        coreStepsHandler.iWaitForTheModalToDisappear(modalDescription);
    }

    @Then("^I \"(.*?)\" the (?:row|rows) with the following criteria:$")
    public void iTheRowWithTheFollowingCriteria(String rowActionDescription, DataTable criteria) {
        coreStepsHandler.iTheRowWithTheFollowingCriteria(rowActionDescription, criteria);
    }

    @And("^I wait up to \"([^\"]*)\" seconds a file named \"([^\"]*)\" to download$")
    public void iWaitUpToSecondsForAFileNameToDownload(int maxSecondsToWait, String filename) throws IOException, InterruptedException {
        coreStepsHandler.iWaitUpToSecondsForAFileNameToDownload(filename, maxSecondsToWait);
    }

    @And("^I delete the file named \"([^\"]*)\" if it exists$")
    public void iDeleteTheFileNamedIfItExists(String filename) throws IOException {
        coreStepsHandler.iDeleteTheFileNamedIfItExists(filename);
    }
}
