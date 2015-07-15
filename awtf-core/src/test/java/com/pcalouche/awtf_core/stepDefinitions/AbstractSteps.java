package com.pcalouche.awtf_core.stepDefinitions;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import com.pcalouche.awtf_core.BrowserInstance;
import com.pcalouche.awtf_core.StepsUtil;
import com.pcalouche.awtf_core.util.appConfig.ElementWithTooltip;
import com.pcalouche.awtf_core.util.appConfig.Modal;
import com.pcalouche.awtf_core.util.enums.HTMLElementState;
import com.pcalouche.awtf_core.util.enums.HTMLFormElement;
import com.pcalouche.awtf_core.util.enums.RowAction;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

/**
 * Step Definitions for general things any page may have.
 *
 * @author Philip Calouche
 *
 */
public class AbstractSteps {
	private static Logger logger = LogManager.getLogger();

	@Given("^I go to the demo page$")
	public void iGoToTheDemoPage() {
		logger.info("file:///" + System.getProperty("user.dir") + BrowserInstance.getTestEnvironmentConfig().getUrl());
		BrowserInstance.getWebDriver().get("file:///" + System.getProperty("user.dir") + BrowserInstance.getTestEnvironmentConfig().getUrl());
	}

	@Then("^I take a screenshot$")
	public void iTakeAScreenshot() {
		StepsUtil.takeAScreenShot();
	}

	@Then("^I click on \"(.*?)\"$")
	public void iClickOn(String text) throws Throwable {
		if (BrowserInstance.getTestEnvironmentConfig().isScreenshotBeforeClick()) {
			StepsUtil.takeAScreenShot();
		}
		// Determine what parent locator to use based on what is currently displayed on the UI
		String parentLocatorToUse = StepsUtil.isModalDisplayed() ? BrowserInstance.getAppConfig().getModalLocator().getLocator() : "";
		String locator = String.format("%s//*[.='%s']|%s//input[@value='%s'][@type='submit' or @type='button']", parentLocatorToUse, text, parentLocatorToUse, text);
		// In case there is more than one matching element, find the first visible one and click it
		List<WebElement> webElements = BrowserInstance.getWebDriverWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(locator)));
		boolean visibleItemWasClicked = false;
		for (WebElement webElement : webElements) {
			if (webElement.isDisplayed()) {
				StepsUtil.handleGeneralClick(webElement);
				visibleItemWasClicked = true;
				break;
			}
		}
		assertTrue(String.format("Did not find a visible item with \"%s\" text to click", text), visibleItemWasClicked);
	}

	@Then("^I input \"(.*?)\" as \"(.*?)\"$")
	public void iInputAs(String description, String value) {
		StepsUtil.handleFormElementInput(StepsUtil.findFormElementByDescription(description), value);
	}

	@Then("^I \"(.*?)\" the \"(.*?)\" (?:radio button|checkbox)$")
	public void iTheRadioButtonCheckbox(String actionValue, String description) {
		StepsUtil.handleFormElementInput(StepsUtil.findFormElementByDescription(description), actionValue);
	}

	@Then("^I input \"(.*?)\" as value containing \"(.*?)\"$")
	public void iInputAsValueContaining(String description, String value) {
		WebElement inputField = StepsUtil.findFormElementByDescription(description);
		assertTrue("Input found was not a select", HTMLFormElement.select.toString().equals(inputField.getTagName()));
		Select select = new Select(inputField);
		List<WebElement> options = select.getOptions();
		for (WebElement option : options) {
			if (option.getText().contains(value)) {
				StepsUtil.handleFormElementInput(inputField, option.getText());
				break;
			}
		}
	}

	@Then("^I input \"(.*?)\" (\\d+) times? into \"(.*?)\"$")
	public void iInputTimesInto(String textString, int numberOfTimes, String description) {
		iInputAs(description, StringUtils.leftPad(textString, numberOfTimes, textString));
	}

	@Then("^I input \"(.*?)\" range as \"(.*?)\" to \"(.*?)\"$")
	public void iInputRangeAsTo(String description, String startValue, String endValue) {
		WebElement startInputField = StepsUtil.findFormElementByDescription(description);
		StepsUtil.handleFormElementInput(startInputField, startValue);
		WebElement endInputField = startInputField.findElement(By.xpath("./following-sibling::*[@name]"));
		StepsUtil.handleFormElementInput(endInputField, endValue);
	}

	@Then("^I see \"(.*?)\" has value of \"(.*?)\"$")
	public void iSeeHasValueOf(String description, String value) {
		StepsUtil.verifyDescriptionValueCombination(description, value, true);
	}

	@Then("^I see \"(.*?)\" has value containing \"(.*?)\"$")
	public void iSeeHasValueContaining(String description, String value) {
		StepsUtil.verifyDescriptionValueCombination(description, value, false);
	}

	@Then("^I \"(.*?)\" \"(.*?)\" in the \"(.*?)\" dropdown$")
	public void iInTheDropdown(String action, String value, String description) {
		WebElement inputField = StepsUtil.findFormElementByDescription(description);
		StepsUtil.verifySelectOption(action, new Select(inputField), description, value, true);
	}

	@Then("^I \"(.*?)\" an option containing \"(.*?)\" in the \"(.*?)\" dropdown$")
	public void iAnOptionContainingInTheDropdown(String action, String value, String description) throws Throwable {
		WebElement inputField = StepsUtil.findFormElementByDescription(description);
		StepsUtil.verifySelectOption(action, new Select(inputField), description, value, false);
	}

	@Then("^I see \"(.*?)\" contains \"(.*?)\" (\\d+) times?$")
	public void iSeeContainsTimes(String description, String textString, int numberOfTimes) {
		iSeeHasValueOf(description, StringUtils.leftPad(textString, numberOfTimes, textString));
	}

	@Then("^I see the (?:message|label|text) \"(.*?)\"$")
	public void iSeeTheMessage(String searchString) {
		String messageTextToUse = StepsUtil.parseText(searchString);
		List<WebElement> webElements = StepsUtil.findMatchingChildElemenstWithText(messageTextToUse);
		boolean isDisplayed = false;
		for (WebElement webElement : webElements) {
			if (webElement.isDisplayed()) {
				isDisplayed = true;
				break;
			}
		}
		assertTrue(String.format("Following text is not visible: %s", messageTextToUse), isDisplayed);
	}

	@Then("^I do not see the (?:message|label|text) \"(.*?)\"$")
	public void iDoNotSeeTheMessage(String searchString) {
		String messageTextToUse = StepsUtil.parseText(searchString);
		List<WebElement> webElements = StepsUtil.findMatchingChildElemenstWithText(messageTextToUse);
		boolean isDisplayed = false;
		for (WebElement webElement : webElements) {
			if (webElement.isDisplayed()) {
				isDisplayed = true;
				break;
			}
		}
		assertTrue(String.format("Following text is visible: %s", messageTextToUse), !isDisplayed);
	}

	@Then("^I see the error message \"(.*?)\"$")
	public void iSeeTheErrorMessage(String errorText) {
		String errorTextToUse = StepsUtil.parseText(errorText);
		List<WebElement> webElements = StepsUtil.findMatchingChildElemenstWithText(errorTextToUse);
		boolean errorMessageFound = false;
		for (WebElement webElement : webElements) {
			if (webElement.isDisplayed() && BrowserInstance.getAppConfig().webElementHasErrorClass(webElement)) {
				errorMessageFound = true;
				break;
			}
		}
		assertTrue(String.format("Error message should be displayed: \"%s\"", errorTextToUse), errorMessageFound);
	}

	@Then("^I do not see the error message \"(.*?)\"$")
	public void iDoNotSeeTheErrorMessage(String errorText) {
		String errorTextToUse = StepsUtil.parseText(errorText);
		List<WebElement> webElements = StepsUtil.findMatchingChildElemenstWithText(errorTextToUse);
		for (WebElement webElement : webElements) {
			if (webElement.isDisplayed() && BrowserInstance.getAppConfig().webElementHasErrorClass(webElement)) {
				fail(String.format("Error message should not be displayed: \"%s\"", errorTextToUse));
				break;
			}
		}
	}

	@Then("^I see the \"(.*?)\" element is \"(.*?)\"$")
	public void iSeeTheElementIs(String description, HTMLElementState htmlElementState) {
		String errorDescription = description.startsWith("[") && description.endsWith("]") ? description.substring(1, description.length() - 1) : description;
		for (WebElement webElement : StepsUtil.findFormElementsByDescription(description)) {
			assertTrue(String.format("%s element does not have expected state of: %s", errorDescription, htmlElementState.toString()), StepsUtil.elementHasCorrectState(webElement, htmlElementState));
		}
	}

	@Then("^I see the \"(.*?)\" button is \"(.*?)\"$")
	public void iSeeTheButtonIs(String buttonText, HTMLElementState htmlElementState) {
		// Determine what parent locator to use based on what is currently displayed on the UI
		String parentLocatorToUse = StepsUtil.isModalDisplayed() ? BrowserInstance.getAppConfig().getModalLocator().getLocator() : "";
		String locator = String.format("%s//input[@value='%s'][@type='submit' or @type='button']|%s//button[.='%s']", parentLocatorToUse, buttonText, parentLocatorToUse, buttonText);
		List<WebElement> buttons = BrowserInstance.getWebDriverWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(locator)));
		for (WebElement button : buttons) {
			assertTrue(String.format("%s button does not have expected state of: %s", buttonText, htmlElementState.toString()), StepsUtil.elementHasCorrectState(button, htmlElementState));
		}
	};

	@Then("^I wait for all load masks to disappear$")
	public void iWaitForAllLoadMasksToDisappear() {
		StepsUtil.waitForLoadMasks();
	}

	@Then("^I wait for the \"(.*?)\" modal to load$")
	public void iWaitForTheModalToLoad(String modalDescription) {
		// Lookup the modal from the app configuration to find out how to locate it
		Modal modal = (Modal) BrowserInstance.getAppConfig().findAppWebElement(modalDescription, Modal.class);
		if (modal == null) {
			fail(String.format("Bad modal description.  Valid descriptions are: %s", BrowserInstance.getAppConfig().getValidKnownDescriptions(Modal.class)));
		}
		// Wait for modal to appear and no load masks to be visible
		BrowserInstance.getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(modal.getByLocator()));
		StepsUtil.waitForLoadMasks();
	}

	@Then("^I wait for the \"(.*?)\" modal to disappear$")
	public void iWaitForTheModalToDisappear(String modalDescription) {
		// Lookup the modal from the app configuration to find out how to locate it
		Modal modal = (Modal) BrowserInstance.getAppConfig().findAppWebElement(modalDescription, Modal.class);
		if (modal == null) {
			fail(String.format("Bad modal description.  Valid descriptions are: %s", BrowserInstance.getAppConfig().getValidKnownDescriptions(Modal.class)));
		}
		BrowserInstance.getWebDriverWait().until(ExpectedConditions.invisibilityOfElementLocated(modal.getByLocator()));
	}

	@Then("^I hover over the \"(.*?)\" tooltip element I see a tooltip that says \"(.*?)\"$")
	public void iHoverOverTheTooltipElementISeeATooltipThatSays(String tooltipDescription, String tooltipText) {
		// Lookup the element that has the tooltip from the App Config to find out how to locate it
		ElementWithTooltip appWebElement = (ElementWithTooltip) BrowserInstance.getAppConfig().findAppWebElement(tooltipDescription, ElementWithTooltip.class);
		if (appWebElement == null) {
			fail(String.format("Bad tooltip description.  Valid descriptions are: %s", BrowserInstance.getAppConfig().getValidKnownDescriptions(ElementWithTooltip.class)));
		}
		WebElement elementWithToolTip = BrowserInstance.getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(appWebElement.getByLocator()));
		/*
		 * Hover over the element with the tooltip. Move to a far enough offset. This should be far enough to hide an an existing visible tooltip that could be covering up another element with a
		 * tooltip that we want to hover over.
		 */
		new Actions(BrowserInstance.getWebDriver()).moveByOffset(200, 200).build().perform();
		new Actions(BrowserInstance.getWebDriver()).moveToElement(elementWithToolTip).build().perform();
		// Ensures we have focus. Workaround for some cases where
		elementWithToolTip.click();
		// Confirm that a tooltip is displayed and that its description matches what is expected
		String tooltipTextToUse = StepsUtil.parseText(tooltipText);
		WebElement tooltip = BrowserInstance.getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(appWebElement.getTooltipElement().getByLocator()));
		assertTrue(String.format("Tooltip should be displayed: \"%s\"", tooltipTextToUse), tooltip.getText().contains(tooltipTextToUse));
	}

	@Then("^I \"(.*?)\" the (?:row|rows) with the following criteria:$")
	public void iTheRowWithTheFollowingCriteria(String rowActionDescription, DataTable criteria) {
		RowAction rowAction = RowAction.getByDescription(rowActionDescription);
		if (rowAction == null) {
			fail(String.format("Bad row action description.  Valid descriptions are: %s", RowAction.getValidDescriptions()));
		}
		List<WebElement> actionableRowElements;
		switch (rowAction) {
		case SELECT:
			actionableRowElements = StepsUtil.findActionableRowElements(rowAction, criteria, null);
			assertTrue("No matching rows found", !actionableRowElements.isEmpty());
			for (WebElement actionableRowElement : actionableRowElements) {
				if (!actionableRowElement.isSelected()) {
					BrowserInstance.getWebDriverWait().until(ExpectedConditions.elementToBeClickable(actionableRowElement)).click();
				}
			}
			break;
		case DESELECT:
			actionableRowElements = StepsUtil.findActionableRowElements(rowAction, criteria, null);
			assertTrue("No matching rows found", !actionableRowElements.isEmpty());
			for (WebElement actionableRowElement : actionableRowElements) {
				if (actionableRowElement.isSelected()) {
					BrowserInstance.getWebDriverWait().until(ExpectedConditions.elementToBeClickable(actionableRowElement)).click();
				}
			}
			break;
		case CLICK:
			actionableRowElements = StepsUtil.findActionableRowElements(rowAction, criteria, null);
			assertTrue("No matching rows found", !actionableRowElements.isEmpty());
			if (BrowserInstance.getTestEnvironmentConfig().isScreenshotBeforeClick()) {
				StepsUtil.takeAScreenShot();
			}
			for (WebElement actionableRowElement : actionableRowElements) {
				BrowserInstance.getWebDriverWait().until(ExpectedConditions.elementToBeClickable(actionableRowElement)).click();
			}
			break;
		case EXPAND:
			actionableRowElements = StepsUtil.findActionableRowElements(rowAction, criteria, null);
			assertTrue("No matching rows found", !actionableRowElements.isEmpty());
			for (WebElement actionableRowElement : actionableRowElements) {
				BrowserInstance.getWebDriverWait().until(ExpectedConditions.elementToBeClickable(actionableRowElement)).click();
			}
			StepsUtil.waitForLoadMasks();
			break;
		case COLLAPSE:
			actionableRowElements = StepsUtil.findActionableRowElements(rowAction, criteria, null);
			assertTrue("No matching rows found", !actionableRowElements.isEmpty());
			for (WebElement actionableRowElement : actionableRowElements) {
				BrowserInstance.getWebDriverWait().until(ExpectedConditions.elementToBeClickable(actionableRowElement)).click();
			}
			break;
		case CAN_SELECT:
			actionableRowElements = StepsUtil.findActionableRowElements(rowAction, criteria, null);
			assertTrue("No matching rows found", !actionableRowElements.isEmpty());
			for (WebElement actionableRowElement : actionableRowElements) {
				assertTrue("An expected row is not selectable", actionableRowElement.isEnabled());
			}
			break;
		case CANNOT_SELECT:
			actionableRowElements = StepsUtil.findActionableRowElements(rowAction, criteria, null);
			for (WebElement actionableRowElement : actionableRowElements) {
				assertTrue("An expected row is selectable", !actionableRowElement.isEnabled());
			}
			break;
		case SEE:
			StepsUtil.findTableRowsWithMatchingCriteria(rowAction, criteria);
			break;
		case DO_NOT_SEE:
			StepsUtil.findTableRowsWithMatchingCriteria(rowAction, criteria);
			break;
		case SEE_SELECTED:
			actionableRowElements = StepsUtil.findActionableRowElements(rowAction, criteria, null);
			for (WebElement actionableRowElement : actionableRowElements) {
				assertTrue("An expected row is not selected", actionableRowElement.isSelected());
			}
			break;
		case SEE_DESELECTED:
			actionableRowElements = StepsUtil.findActionableRowElements(rowAction, criteria, null);
			for (WebElement actionableRowElement : actionableRowElements) {
				assertTrue("An expected row is not deselected", !actionableRowElement.isSelected());
			}
			break;
		default:
			break;
		}
	}
}
