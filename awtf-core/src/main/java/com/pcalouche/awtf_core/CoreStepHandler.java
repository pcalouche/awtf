package com.pcalouche.awtf_core;

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

import com.pcalouche.awtf_core.util.appConfig.ElementWithTooltip;
import com.pcalouche.awtf_core.util.appConfig.Modal;
import com.pcalouche.awtf_core.util.enums.HTMLElementState;
import com.pcalouche.awtf_core.util.enums.HTMLFormElement;
import com.pcalouche.awtf_core.util.enums.RowAction;

import cucumber.api.DataTable;

public class CoreStepHandler {
	protected Logger logger = LogManager.getLogger();

	/**
	 * Method to handle taking a screen shot during a scenario
	 */
	public void iTakeAScreenshot() {
		StepsUtil.takeAScreenShot();
	}

	/**
	 * Method to handle clicks for links and buttons
	 * 
	 * @param text
	 *            the text of the link or button
	 */
	public void iClickOn(String text) {
		if (TestInstance.getTestEnvironmentConfig().isScreenshotBeforeClick()) {
			StepsUtil.takeAScreenShot();
		}
		// Determine what parent locator to use based on what is currently displayed on the UI
		String parentLocatorToUse = StepsUtil.isModalDisplayed() ? TestInstance.getAppConfig().getModalLocator().getLocator() : "";
		String locator = String.format("%s//*[.='%s']|%s//input[@value='%s'][@type='submit' or @type='button']", parentLocatorToUse, text, parentLocatorToUse, text);
		// In case there is more than one matching element, find the first visible one and click it
		List<WebElement> webElements = TestInstance.getWebDriverWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(locator)));
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

	/**
	 * Method for handle input into form elements that aren't check boxes or radio buttons
	 * 
	 * @param description
	 *            the description of the form element to use
	 * @param value
	 *            the value to input into the form element
	 */
	public void iInputAs(String description, String value) {
		StepsUtil.handleFormElementInput(StepsUtil.findFormElementByDescription(description), value);
	}

	/**
	 * Method to handle input for check boxes and radio buttons.
	 * 
	 * @param actionValue
	 *            action to perform on the check box or radio. Must be either "select" or "deselect"
	 * @param description
	 *            the description of the form element to to use
	 */
	public void iTheRadioButtonCheckbox(String actionValue, String description) {
		StepsUtil.handleFormElementInput(StepsUtil.findFormElementByDescription(description), actionValue);
	}

	/**
	 * Method to handle input for a dropdown using just a partial text match
	 * 
	 * @param description
	 *            the description of the dropdown to use
	 * @param value
	 *            the partial value that will be used to match to an actual possible value for the dropdown
	 */
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

	/**
	 * Method to handle inputting a string a given number of times into a form element
	 * 
	 * @param textString
	 *            the text string to input into the form element
	 * @param numberOfTimes
	 *            the number of times to input the text string
	 * @param description
	 *            the description of the form element to use
	 */
	public void iInputTimesInto(String textString, int numberOfTimes, String description) {
		iInputAs(description, StringUtils.leftPad(textString, numberOfTimes, textString));
	}

	/**
	 * Method to handle input for form elements that represent a min to max range
	 * 
	 * @param description
	 *            the description of the form element to use
	 * @param startValue
	 *            the min value to enter
	 * @param endValue
	 *            the max value to enter
	 */
	public void iInputRangeAsTo(String description, String startValue, String endValue) {
		WebElement startInputField = StepsUtil.findFormElementByDescription(description);
		StepsUtil.handleFormElementInput(startInputField, startValue);
		WebElement endInputField = startInputField.findElement(By.xpath("./following-sibling::*[@name]"));
		StepsUtil.handleFormElementInput(endInputField, endValue);
	}

	/**
	 * Method to verify the value of a form element
	 * 
	 * @param description
	 *            the description of the form element to use
	 * @param value
	 *            the value to to check the form element against
	 */
	public void iSeeHasValueOf(String description, String value) {
		StepsUtil.verifyDescriptionValueCombination(description, value, true);
	}

	/**
	 * Method to verify the value of a form element partially matches the given value
	 * 
	 * @param description
	 *            the description of the form element to use
	 * @param value
	 *            the partial value to to check the form element against
	 */
	public void iSeeHasValueContaining(String description, String value) {
		StepsUtil.verifyDescriptionValueCombination(description, value, false);
	}

	/**
	 * Method to verify if a value is in a dropdown or not
	 * 
	 * @param verificationToPerform
	 *            the verification to perform. Must either be "see" or "do not see"
	 * @param value
	 *            the value to verify
	 * @param description
	 *            the description of the form element to use
	 */
	public void iInTheDropdown(String verificationToPerform, String value, String description) {
		WebElement inputField = StepsUtil.findFormElementByDescription(description);
		StepsUtil.verifySelectOption(verificationToPerform, new Select(inputField), description, value, true);
	}

	/**
	 * Method to verify if a value is in a dropdown or not using just a partial match
	 * 
	 * @param verificationToPerform
	 *            the verification to perform. Must either be "see" or "do not see"
	 * @param value
	 *            the value to verify by partial match
	 * @param description
	 *            the description of the form element to use
	 */
	public void iAnOptionContainingInTheDropdown(String action, String value, String description) {
		WebElement inputField = StepsUtil.findFormElementByDescription(description);
		StepsUtil.verifySelectOption(action, new Select(inputField), description, value, false);
	}

	/**
	 * Method to verify if a string appears in a form element a given number of times
	 * 
	 * @param description
	 *            the description of the form element to use
	 * @param textString
	 *            the text string to input into the form element
	 * @param numberOfTimes
	 *            the number of times to input the text string
	 */
	public void iSeeContainsTimes(String description, String textString, int numberOfTimes) {
		iSeeHasValueOf(description, StringUtils.leftPad(textString, numberOfTimes, textString));
	}

	/**
	 * Method to verify if a message/text/label is visible on the on the page
	 * 
	 * @param searchString
	 *            the string to search for
	 */
	public void iSeeTheMessage(String searchString) {
		String messageTextToUse = parseText(searchString);
		List<WebElement> webElements = StepsUtil.findMatchingChildElementsWithText(messageTextToUse);
		boolean isDisplayed = false;
		for (WebElement webElement : webElements) {
			if (webElement.isDisplayed()) {
				isDisplayed = true;
				break;
			}
		}
		assertTrue(String.format("Following text is not visible: %s", messageTextToUse), isDisplayed);
	}

	/**
	 * Method to verify if a message/text/label is not visible on the on the page
	 * 
	 * @param searchString
	 *            the string to search for
	 */
	public void iDoNotSeeTheMessage(String searchString) {
		String messageTextToUse = parseText(searchString);
		List<WebElement> webElements = StepsUtil.findMatchingChildElementsWithText(messageTextToUse);
		boolean isDisplayed = false;
		for (WebElement webElement : webElements) {
			if (webElement.isDisplayed()) {
				isDisplayed = true;
				break;
			}
		}
		assertTrue(String.format("Following text is visible: %s", messageTextToUse), !isDisplayed);
	}

	/**
	 * Method to verify if an error message is visible on the on the page
	 * 
	 * @param errorMessage
	 *            error message to look for
	 */
	public void iSeeTheErrorMessage(String errorMessage) {
		String errorTextToUse = parseText(errorMessage);
		List<WebElement> webElements = StepsUtil.findMatchingChildElementsWithText(errorTextToUse);
		boolean errorMessageFound = false;
		for (WebElement webElement : webElements) {
			if (webElement.isDisplayed() && TestInstance.getAppConfig().webElementHasErrorClass(webElement)) {
				errorMessageFound = true;
				break;
			}
		}
		assertTrue(String.format("Error message should be displayed: \"%s\"", errorTextToUse), errorMessageFound);
	}

	/**
	 * Method to verify if an error message is not visible on the on the page
	 * 
	 * @param errorMessage
	 *            error message to look for
	 */
	public void iDoNotSeeTheErrorMessage(String errorMessage) {
		String errorTextToUse = parseText(errorMessage);
		List<WebElement> webElements = StepsUtil.findMatchingChildElementsWithText(errorTextToUse);
		for (WebElement webElement : webElements) {
			if (webElement.isDisplayed() && TestInstance.getAppConfig().webElementHasErrorClass(webElement)) {
				fail(String.format("Error message should not be displayed: \"%s\"", errorTextToUse));
				break;
			}
		}
	}

	/**
	 * Method to verify if a page element is in a given state
	 * 
	 * @param description
	 *            the description of the element to use
	 * @param htmlElementState
	 *            the state to verify for the element
	 */
	public void iSeeTheElementIs(String description, HTMLElementState htmlElementState) {
		String errorDescription = description.startsWith("[") && description.endsWith("]") ? description.substring(1, description.length() - 1) : description;
		for (WebElement webElement : StepsUtil.findFormElementsByDescription(description)) {
			assertTrue(String.format("%s element does not have expected state of: %s", errorDescription, htmlElementState.toString()), StepsUtil.elementHasCorrectState(webElement, htmlElementState));
		}
	}

	/**
	 * Method to verify if a button is in a given state
	 * 
	 * @param buttonText
	 *            the button text of the button to verify state for
	 * @param htmlElementState
	 *            the state to verify for the element
	 */
	public void iSeeTheButtonIs(String buttonText, HTMLElementState htmlElementState) {
		// Determine what parent locator to use based on what is currently displayed on the UI
		String parentLocatorToUse = StepsUtil.isModalDisplayed() ? TestInstance.getAppConfig().getModalLocator().getLocator() : "";
		String locator = String.format("%s//input[@value='%s'][@type='submit' or @type='button']|%s//button[.='%s']", parentLocatorToUse, buttonText, parentLocatorToUse, buttonText);
		List<WebElement> buttons = TestInstance.getWebDriverWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(locator)));
		for (WebElement button : buttons) {
			assertTrue(String.format("%s button does not have expected state of: %s", buttonText, htmlElementState.toString()), StepsUtil.elementHasCorrectState(button, htmlElementState));
		}
	};

	/**
	 * Method to verify no load masks/indicators are visible on the page
	 */
	public void iWaitForAllLoadMasksToDisappear() {
		StepsUtil.waitForLoadMasks();
	}

	/**
	 * Method to wait for a given modal to appear
	 * 
	 * @param modalDescription
	 *            the description of the modal to use
	 */
	public void iWaitForTheModalToAppear(String modalDescription) {
		// Lookup the modal from the app configuration to find out how to locate it
		Modal modal = (Modal) TestInstance.getAppConfig().findAppWebElement(modalDescription, Modal.class);
		if (modal == null) {
			fail(String.format("Bad modal description.  Valid descriptions are: %s", TestInstance.getAppConfig().getValidKnownDescriptions(Modal.class)));
		}
		// Wait for modal to appear and no load masks to be visible
		TestInstance.getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(modal.getByLocator()));
		StepsUtil.waitForLoadMasks();
	}

	/**
	 * Method to wait for a given modal to disappear
	 * 
	 * @param modalDescription
	 *            the description of the modal to use
	 */
	public void iWaitForTheModalToDisappear(String modalDescription) {
		// Lookup the modal from the app configuration to find out how to locate it
		Modal modal = (Modal) TestInstance.getAppConfig().findAppWebElement(modalDescription, Modal.class);
		if (modal == null) {
			fail(String.format("Bad modal description.  Valid descriptions are: %s", TestInstance.getAppConfig().getValidKnownDescriptions(Modal.class)));
		}
		TestInstance.getWebDriverWait().until(ExpectedConditions.invisibilityOfElementLocated(modal.getByLocator()));
	}

	/**
	 * Method to verify that when an element is hovered over, the expected tooltip appears with the expected text
	 * 
	 * @param tooltipDescription
	 *            the tooltip description to use
	 * @param tooltipText
	 *            the tooltip text to verify
	 */
	public void iHoverOverTheTooltipElementISeeATooltipThatSays(String tooltipDescription, String tooltipText) {
		// Lookup the element that has the tooltip from the App Config to find out how to locate it
		ElementWithTooltip appWebElement = (ElementWithTooltip) TestInstance.getAppConfig().findAppWebElement(tooltipDescription, ElementWithTooltip.class);
		if (appWebElement == null) {
			fail(String.format("Bad tooltip description.  Valid descriptions are: %s", TestInstance.getAppConfig().getValidKnownDescriptions(ElementWithTooltip.class)));
		}
		WebElement elementWithToolTip = TestInstance.getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(appWebElement.getByLocator()));
		/*
		 * Hover over the element with the tooltip. Move to a far enough offset. This should be far enough to hide an an existing visible tooltip that could be covering up another element with a
		 * tooltip that we want to hover over.
		 */
		new Actions(TestInstance.getWebDriver()).moveByOffset(200, 200).build().perform();
		new Actions(TestInstance.getWebDriver()).moveToElement(elementWithToolTip).build().perform();
		// Ensures we have focus. Workaround for some cases where
		elementWithToolTip.click();
		// Confirm that a tooltip is displayed and that its description matches what is expected
		String tooltipTextToUse = parseText(tooltipText);
		WebElement tooltip = TestInstance.getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(appWebElement.getTooltipElement().getByLocator()));
		assertTrue(String.format("Tooltip should be displayed: \"%s\"", tooltipTextToUse), tooltip.getText().contains(tooltipTextToUse));
	}

	/**
	 * Method to handle all table row actions
	 * 
	 * @param rowActionDescription
	 *            the row action to use
	 * @param criteria
	 *            the criteria to verify for that row
	 */
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
					TestInstance.getWebDriverWait().until(ExpectedConditions.elementToBeClickable(actionableRowElement)).click();
				}
			}
			break;
		case DESELECT:
			actionableRowElements = StepsUtil.findActionableRowElements(rowAction, criteria, null);
			assertTrue("No matching rows found", !actionableRowElements.isEmpty());
			for (WebElement actionableRowElement : actionableRowElements) {
				if (actionableRowElement.isSelected()) {
					TestInstance.getWebDriverWait().until(ExpectedConditions.elementToBeClickable(actionableRowElement)).click();
				}
			}
			break;
		case CLICK:
			actionableRowElements = StepsUtil.findActionableRowElements(rowAction, criteria, null);
			assertTrue("No matching rows found", !actionableRowElements.isEmpty());
			if (TestInstance.getTestEnvironmentConfig().isScreenshotBeforeClick()) {
				StepsUtil.takeAScreenShot();
			}
			for (WebElement actionableRowElement : actionableRowElements) {
				TestInstance.getWebDriverWait().until(ExpectedConditions.elementToBeClickable(actionableRowElement)).click();
			}
			break;
		case EXPAND:
			actionableRowElements = StepsUtil.findActionableRowElements(rowAction, criteria, null);
			assertTrue("No matching rows found", !actionableRowElements.isEmpty());
			for (WebElement actionableRowElement : actionableRowElements) {
				TestInstance.getWebDriverWait().until(ExpectedConditions.elementToBeClickable(actionableRowElement)).click();
			}
			StepsUtil.waitForLoadMasks();
			break;
		case COLLAPSE:
			actionableRowElements = StepsUtil.findActionableRowElements(rowAction, criteria, null);
			assertTrue("No matching rows found", !actionableRowElements.isEmpty());
			for (WebElement actionableRowElement : actionableRowElements) {
				TestInstance.getWebDriverWait().until(ExpectedConditions.elementToBeClickable(actionableRowElement)).click();
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

	/**
	 * Method to parse text to get the actual text to use. If the text to parse is surround by [] such [Message_Text] then the text will be looked up from a resource bundle based on that key. If the
	 * text to parse is not surrounded by [] it is just returned in its original form. Other text substitutions may happen to the text based on codes inside the message. The convention for
	 * substitutions inside the text is that they should be surrounded by {}. However, this method can be completely overridden as needed with your own step handler. It recommended to avoid using <>
	 * as a delimiter because Cucumber uses that itself in the feature files for substitution.
	 *
	 * @param text
	 *            the text to parse
	 * @return the text to use
	 */
	public String parseText(String text) {
		String returnText = null;
		// See if message needs to pulled from resource bundle or not
		if (text.startsWith("[") && text.endsWith("]")) {
			returnText = TestInstance.getAppConfig().getMessageBundle().getString(text.substring(1, text.length() - 1));
			// Log to scenario to help with review
			if (TestInstance.getCurrentScenario() != null) {
				TestInstance.getCurrentScenario().write(String.format("%s is: %s<br>", text, returnText));
			}
		} else {
			returnText = text;
		}
		return returnText;
	}
}