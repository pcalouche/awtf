package com.pcalouche.awtf_core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import com.pcalouche.awtf_core.util.appConfig.AppElement;
import com.pcalouche.awtf_core.util.enums.HTMLElementState;
import com.pcalouche.awtf_core.util.enums.HTMLFormElement;
import com.pcalouche.awtf_core.util.enums.HTMLInputType;
import com.pcalouche.awtf_core.util.enums.RowAction;

import cucumber.api.DataTable;
import gherkin.formatter.model.DataTableRow;

/**
 * This is a general Utility class for helper methods related to Cucumber and Selenium step testing.
 *
 * @author Philip Calouche
 *
 */
public class StepsUtil {
	protected Logger logger = LogManager.getLogger();

	/**
	 * Helper method that take a screenshot
	 */
	public void takeAScreenShot() {
		TestInstance.getCurrentScenario().embed(((TakesScreenshot) TestInstance.getWebDriver()).getScreenshotAs(OutputType.BYTES), "image/png");
	}

	/**
	 * Method to test if an element is present on a page.
	 *
	 * @param locator
	 *            the locator to search by
	 * @return true if element is present, false otherwise
	 */
	public boolean elementExists(By locator) {
		try {
			TestInstance.getWebDriver().findElement(locator);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * Method to test if a child element exists under a web element.
	 *
	 * @param parentWebElement
	 *            the parent web element
	 * @param locator
	 *            the locator to search by
	 * @return true if element is present, false otherwise
	 */
	public boolean elementExists(WebElement parentWebElement, By locator) {
		try {
			parentWebElement.findElement(locator);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * Method to handle a click on a web element. This is done because sometimes when a matching web element is found it contains an anchor tag that should be clicked instead to make the desired
	 * action happen.
	 *
	 * @param webElement
	 *            the web element to click on
	 */
	public void handleGeneralClick(WebElement webElement) {
		// See if the matching element has a anchor tag underneath it. If so click that instead of the element.
		try {
			WebElement linkElement = webElement.findElement(By.tagName("a"));
			linkElement.click();
		} catch (NoSuchElementException e) {
			webElement.click();
		}
	}

	/**
	 * Method to find and return a matching form element based on a given description. The return element can then be acted on to perform actions such as input or value verification. *
	 *
	 * @param description
	 *            the description to search by
	 * @return the matching form element
	 */
	public WebElement findFormElementByDescription(String description) {
		/*
		 * Parse description to see if it is surrounded by []. If it is then look up the form input from the App Config. If it isn't then look for a label with matching text and a non empty for
		 * attribute. The input that has an ID that matches the for attribute value will be returned.
		 */
		if (description.startsWith("[") && description.endsWith("]")) {
			AppElement appElement = TestInstance.getAppConfig().findAppWebElement(description.substring(1, description.length() - 1));
			return TestInstance.getWebDriverWait().until(ExpectedConditions.presenceOfElementLocated(appElement.getByLocator()));
		} else {
			// Determine what parent locator to use based on what is currently displayed on the UI
			String parentLocatorToUse = TestInstance.getStepsUtil().isModalDisplayed() ? TestInstance.getAppConfig().getModalLocator().getLocator() : "";
			String locator = String.format("%s//label[contains(normalize-space(string()),'%s')][@for!='']", parentLocatorToUse, description);
			// Find all matching label elements on the page
			List<WebElement> fieldLabels = TestInstance.getWebDriverWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(locator)));
			WebElement inputField = null;
			for (WebElement fieldLabel : fieldLabels) {
				// Act on the first non hidden label and return the form input it is associated with
				if (fieldLabel.isDisplayed()) {
					inputField = TestInstance.getWebDriver().findElement(By.id(fieldLabel.getAttribute("for")));
					break;
				}
			}
			return inputField;
		}
	}

	/**
	 * Method to find a list of matching form elements based on a given description versus just the first one of the page. The returned elements can then be acted on to perform actions such as input
	 * or value verification. *
	 *
	 * @param description
	 *            the description to search by
	 * @return the matching form element
	 */
	public List<WebElement> findFormElementsByDescription(String description) {
		/*
		 * Parse description to see if it is surrounded by []. If it is then look up the form input from the App Config. If it isn't then look for a label with matching text and a non empty for
		 * attribute. The input that has an ID that matches the for attribute value will be returned.
		 */
		if (description.startsWith("[") && description.endsWith("]")) {
			AppElement appElement = TestInstance.getAppConfig().findAppWebElement(description.substring(1, description.length() - 1));
			return TestInstance.getWebDriverWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(appElement.getByLocator()));
		} else {
			// Determine what parent locator to use based on what is currently displayed on the UI
			String parentLocatorToUse = TestInstance.getStepsUtil().isModalDisplayed() ? TestInstance.getAppConfig().getModalLocator().getLocator() : "";
			String locator = String.format("%s//label[contains(normalize-space(string()),'%s')][@for!='']", parentLocatorToUse, description);
			// Find all matching label elements on the page
			List<WebElement> fieldLabels = TestInstance.getWebDriverWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(locator)));
			List<WebElement> webElements = new ArrayList<WebElement>();
			for (WebElement fieldLabel : fieldLabels) {
				webElements.add(TestInstance.getWebDriver().findElement(By.id(fieldLabel.getAttribute("for"))));
			}
			return webElements;
		}
	}

	/**
	 * Method to handle input for a form element. Checks will be done to make sure the form element is a valid type and that it is in a state to receive input.
	 *
	 * @param formElement
	 *            the form element to do input for
	 * @param value
	 *            the value to form field to
	 */
	public void handleFormElementInput(WebElement formElement, Object value) {
		/*
		 * Sometimes hidden fields are used to store the value of a disabled field since disabled field values are not submitted. One example is a disabled select because there is only one option to
		 * choose. Selenium will throw an exception if we interact with a hidden input, so don't try and set it if the input is one.
		 */
		if (formElement.getAttribute("type").equalsIgnoreCase("hidden")) {
			return;
		}
		// Make sure it is enabled before setting any values. This is useful for things like a select that is disabled until an AJAX call to load its data has finished.
		TestInstance.getWebDriverWait().until(ExpectedConditions.elementToBeClickable(formElement));
		HTMLFormElement htmlFormElement = HTMLFormElement.valueOf(formElement.getTagName());
		switch (htmlFormElement) {
		case input:
			// Determine what type of input it is in order to act on it properly
			HTMLInputType htmlInputType = HTMLInputType.valueOf(formElement.getAttribute("type"));
			switch (htmlInputType) {
			// Text and password are set the same way so let the case fall through
			case text:
			case password:
				formElement.clear();
				formElement.sendKeys((String) value);
				break;
			// Radios and checkboxes are set the same way so let the case fall through
			case radio:
			case checkbox:
				if (((String) value).equals("select")) {
					if (!formElement.isSelected()) {
						formElement.click();
					}
				} else if (((String) value).equals("deselect")) {
					if (formElement.isSelected()) {
						formElement.click();
					}
				} else {
					fail("Invalid radio button or checkbox input.  Must be either select or deselect");
				}
				break;
			default:
				break;
			}
			break;
		case textarea:
			formElement.clear();
			formElement.sendKeys((String) value);
			break;
		case select:
			new Select(formElement).selectByVisibleText((String) value);
			break;
		default:
			break;
		}
		/*
		 * Blur the input. This is done because often times in order for validation to be done on an element it needs to lose focus. Handle the possibility of StaleElementReferenceException in case
		 * the element is no longer on the page. This happens to input fields that are dynamically rendered by the data tables plugin when a row update is called. We aren't too concerned with blurring
		 * these types of inputs.
		 */
		try {
			TestInstance.getJsExecutor().executeScript("arguments[0].blur()", formElement);
		} catch (StaleElementReferenceException e) {

		}
	}

	/**
	 * Method to verify a form element's value. Checks will be done to make sure the form element is a valid type.
	 *
	 * @param formElement
	 *            the form element to verify the value of
	 * @param value
	 *            the value to verify against the form element
	 * @param matchValueExactly
	 *            true to match the value exactly
	 */
	public void verifyFormElementValue(WebElement formElement, Object value, boolean matchValueExactly) {
		/*
		 * Sometimes hidden fields are used to store the value of a disabled field since disabled field values are not submitted. One example is a disabled select because there is only one option to
		 * choose. Selenium will throw an exception if we interact with a hidden input, so don't try and set it if the input is one.
		 */
		if (formElement.getAttribute("type").equalsIgnoreCase("hidden")) {
			return;
		}

		// Make sure it is visible before verifying any values.
		TestInstance.getWebDriverWait().until(ExpectedConditions.visibilityOf(formElement));
		HTMLFormElement htmlTag = HTMLFormElement.valueOf(formElement.getTagName());
		switch (htmlTag) {
		case input:
			// Determine what type of input it is in order to act on it properly
			HTMLInputType htmlInputType = HTMLInputType.valueOf(formElement.getAttribute("type"));
			switch (htmlInputType) {
			// Text and password are verified the same way so let the case fall through
			case text:
			case password:
				if (matchValueExactly) {
					assertEquals(value, formElement.getAttribute("value"));
				} else {
					assertTrue(formElement.getAttribute("value").contains((String) value));
				}
				break;
			// Radios and checkboxes are verified the same way so let the case fall through
			case radio:
			case checkbox:
				if (((String) value).equals("selected")) {
					assertTrue("Radio/Checkbox is not selected as expected", formElement.isSelected());
				} else if (((String) value).equals("deselected")) {
					assertTrue("Radio/Checkbox is unexpectedly selected", !formElement.isSelected());
				} else {
					fail("Invalid radio button or checkbox value to verify.  Must be either selected or deselected");
				}
				break;
			default:
				break;
			}
			break;
		case textarea:
			if (matchValueExactly) {
				assertEquals(value, formElement.getAttribute("value"));
			} else {
				assertTrue(formElement.getAttribute("value").contains((String) value));
			}
			break;
		case select:
			List<WebElement> selectedOptions = new Select(formElement).getAllSelectedOptions();
			boolean valueFound = false;
			for (WebElement webElement : selectedOptions) {
				if (matchValueExactly) {
					valueFound = webElement.getText().equals(value);
				} else {
					valueFound = webElement.getText().contains((String) value);
				}
				if (valueFound) {
					break;
				}
			}
			if (matchValueExactly) {
				assertTrue(String.format("Expected value: %s was not found in any of the dropdown options.", value), valueFound);
			} else {
				assertTrue(String.format("Expected value: %s was not contained in any of the dropdown options.", value), valueFound);
			}
			break;
		default:
			break;
		}
	}

	/**
	 *
	 * @param verificationToPerform
	 *            the verification to perform. valid values are just "see" and "do not see" right now
	 * @param select
	 *            the select to verify the option for
	 * @param description
	 *            the description of the select
	 * @param value
	 *            the option value to verify in the select
	 * @param matchValueExactly
	 *            true to match the value exactly
	 */
	public void verifySelectOption(String verificationToPerform, Select select, String description, String value, boolean matchValueExactly) {
		String parsedValue = TestInstance.getStepsUtil().resolveText(value);
		boolean valueFound = false;
		for (WebElement webElement : select.getOptions()) {
			if (matchValueExactly) {
				valueFound = webElement.getText().equals(parsedValue);
			} else {
				valueFound = webElement.getText().contains(parsedValue);
			}
			if (valueFound) {
				break;
			}
		}
		if (verificationToPerform.equals("see")) {
			assertTrue(String.format("Expected value: %s was not found in any of the the %s dropdown options.", parsedValue, description), valueFound);
		} else if (verificationToPerform.equals("do not see")) {
			assertTrue(String.format("Unexpected value: %s was found in the %s dropdown options.", parsedValue, description), !valueFound);
		} else {
			fail(String.format("Bad action.  Valid actions are: %s", "see, do not see"));
		}
	}

	/**
	 * Method to verify a name value combination that is on the screen. This could either be a label element that has an form field associated with it or a text element that has a neighboring text two
	 * text elements that represents a name value combination.
	 *
	 * @param description
	 *            the description to check the value for
	 * @param value
	 *            the expected value for the given name
	 * @param matchValueExactly
	 *            true to match the value exactly
	 */
	public void verifyDescriptionValueCombination(String description, String value, boolean matchValueExactly) {
		// Get parsed value for locators and testing
		String parsedValue = TestInstance.getStepsUtil().resolveText(value);
		AppElement appElement = null;
		List<WebElement> webElements = null;
		String matchExactlyErrorFormat = "Could not find %s with value of %s";
		String matchPartialErrorFormat = "Could not find %s with value that contains %s";
		/*
		 * Parse description to see if it is surrounded by []. If it is then look up the form input from the App Config. If it isn't then look for a label with matching text and a non empty for
		 * attribute. The input that has an ID that matches the for attribute value will be returned.
		 */
		if (description.startsWith("[") && description.endsWith("]")) {
			appElement = TestInstance.getAppConfig().findAppWebElement(description.substring(1, description.length() - 1));
			webElements = TestInstance.getWebDriverWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(appElement.getByLocator()));
		} else {
			// Determine what parent locator to use based on what is currently displayed on the UI
			String parentLocatorToUse = TestInstance.getStepsUtil().isModalDisplayed() ? TestInstance.getAppConfig().getModalLocator().getLocator() : "";
			String labelWithForAttributeLocator = String.format("%s//label[contains(normalize-space(string()), '%s')][@for!='']", parentLocatorToUse, description);
			String basicNameWithValueLocator;
			if (matchValueExactly) {
				basicNameWithValueLocator = String.format("%s//*[contains(normalize-space(string()), '%s')]//following-sibling::*[normalize-space(string()) = '%s']", parentLocatorToUse, description,
						parsedValue);
			} else {
				basicNameWithValueLocator = String.format("%s//*[contains(normalize-space(string()), '%s')]//following-sibling::*[contains(normalize-space(string()), '%s')]", parentLocatorToUse,
						description, parsedValue);
			}
			String locator = String.format("%s|%s", labelWithForAttributeLocator, basicNameWithValueLocator);
			webElements = TestInstance.getWebDriverWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(locator)));
		}

		// Now go through the web element results, check if it is displayed, and then depending on its properties perform additional verification
		boolean isDisplayed = false;
		for (WebElement webElement : webElements) {
			if (webElement.isDisplayed()) {
				isDisplayed = true;
				if (StringUtils.isNotBlank(webElement.getAttribute("for"))) {
					verifyFormElementValue(TestInstance.getWebDriver().findElement(By.id(webElement.getAttribute("for"))), parsedValue, matchValueExactly);
				} else if (appElement != null) {
					// Test if appElement is a form element and handle accordingly. If then try and match its text against the expected value as a last resort.
					try {
						HTMLFormElement.valueOf(webElement.getTagName());
						verifyFormElementValue(webElement, parsedValue, matchValueExactly);
					} catch (IllegalArgumentException e) {
						if (matchValueExactly) {
							assertEquals(String.format(matchExactlyErrorFormat, appElement.getDescription(), parsedValue), parsedValue, webElement.getText());
						} else {
							assertTrue(String.format(matchPartialErrorFormat, appElement.getDescription(), parsedValue), webElement.getText().contains(parsedValue));
						}
					}
				}
			}
		}
		// Assert that at least one web element was visible as the last check
		String errorMessage;
		if (matchValueExactly) {
			errorMessage = appElement != null ? String.format(matchExactlyErrorFormat, appElement.getDescription(), parsedValue) : String.format(matchExactlyErrorFormat, description, parsedValue);
		} else {
			errorMessage = appElement != null ? String.format(matchPartialErrorFormat, appElement.getDescription(), parsedValue) : String.format(matchPartialErrorFormat, description, parsedValue);
		}
		assertTrue(errorMessage, isDisplayed);
	}

	/**
	 * Method that check if a WebElement meets the given element state
	 *
	 * @param webElement
	 *            the web element
	 * @param htmlElementState
	 *            the web element state to test
	 * @return true if it meets the given element state, false otherwise
	 */
	public boolean elementHasCorrectState(WebElement webElement, HTMLElementState htmlElementState) {
		switch (htmlElementState) {
		case disabled:
			return (!webElement.isEnabled() || webElement.getAttribute("class").contains("disabled"));
		case enabled:
			return (webElement.isEnabled() && !webElement.getAttribute("class").contains("disabled"));
		case hidden:
			return (!webElement.isDisplayed());
		case visible:
			return (webElement.isDisplayed());
		default:
			return false;
		}
	}

	/**
	 * Method that returns the first matching table row that contains the given text. An optional table ID can be given to narrow the search to a specific table. This can be helpful if there are
	 * multiple tables on the same page that may contain the ambiguous data.
	 *
	 * @param text
	 *            the the text to look for
	 * @param tableId
	 *            the table ID to for the item in
	 * @return the table row
	 */
	public WebElement findTableRowContainingText(String text, String tableId) {
		String locator;
		if (StringUtils.isNotEmpty(tableId)) {
			locator = String.format("//table[@id='%s']//td[contains(normalize-space(string()), '%s')]/ancestor::tr", tableId, text);
		} else {
			locator = String.format("//td[contains(normalize-space(string()), '%s')]/ancestor::tr", text);
		}
		return TestInstance.getWebDriverWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
	}

	public WebElement findTableRowContainingText(String text) {
		return findTableRowContainingText(text, null);
	}

	/**
	 * Method that returns all visible table rows that match criteria in the given Data Table. An optional table ID can be given to narrow the search to a specific table. This can be helpful if there
	 * are multiple tables on the same page that may contain the same data.
	 *
	 * @param criteria
	 *            the data table that has the criteria to match on
	 * @param tableId
	 *            the table ID of the table to search in
	 * @return the matching table rows
	 */
	public List<WebElement> findTableRowsWithMatchingCriteria(RowAction rowAction, DataTable criteria, String tableId) {
		// Wait for any load masks to be gone
		TestInstance.getStepsUtil().waitForLoadMasks();
		List<WebElement> matchedRows = new ArrayList<WebElement>();
		List<DataTableRow> dataTableRowList = criteria.getGherkinRows();
		for (int i = 1; i < dataTableRowList.size(); i++) {
			// First find matching cells that meet the first criterion to narrow things down faster
			List<String> rowCriteria = dataTableRowList.get(i).getCells();
			String firstCriterion = TestInstance.getStepsUtil().resolveText(rowCriteria.get(0));
			// Determine what parent locator to use based on what is currently displayed on the UI
			String parentLocatorToUse = TestInstance.getStepsUtil().isModalDisplayed() ? TestInstance.getAppConfig().getModalLocator().getLocator() : "";
			String locator;
			if (StringUtils.isNotEmpty(tableId)) {
				locator = String.format("%s//table[@id='%s']//tr//td[normalize-space(string())='%s']", parentLocatorToUse, tableId, firstCriterion);
			} else {
				locator = String.format("%s//tr//td[normalize-space(string())='%s']", parentLocatorToUse, firstCriterion);
			}
			List<WebElement> matchedCells;
			try {
				matchedCells = TestInstance.getWebDriverWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(locator)));
			} catch (TimeoutException e) {
				matchedCells = new ArrayList<WebElement>();
			}
			// logger.debug("matchedCells size " + matchedCells.size());
			/*
			 * Check those matching cells are actually visible. This it get around the old balances page using hidden cells to store other data for that row instead of something like data tables or
			 * jQuery's data method to manage that data.
			 */
			List<WebElement> initiallyMatchedRows = new ArrayList<WebElement>();
			for (WebElement matchedCell : matchedCells) {
				// If it is visible add it to the initial list of matching rows
				if (matchedCell.isDisplayed()) {
					initiallyMatchedRows.add(matchedCell.findElement(By.xpath("./ancestor::tr")));
				}
			}
			// logger.debug("initiallyMatchedRows size " + initiallyMatchedRows.size());
			// Go through each matching row and check that is contains the other criteria that is in the given data table
			for (WebElement initiallyMatchedRow : initiallyMatchedRows) {
				boolean rowMatch = true;
				// Start at 1 because we already tested 0 with the initial query
				for (int j = 1; j < dataTableRowList.get(i).getCells().size(); j++) {
					try {
						WebElement rowCell = initiallyMatchedRow
								.findElement(By.xpath(String.format(".//td[normalize-space(string())='%s']", TestInstance.getStepsUtil().resolveText(rowCriteria.get(j)))));
						// logger.debug(rowCell.getText() + " found");
						// Have to check if this is displayed because the old UI likes to put data that could match in hidden cells.
						if (!rowCell.isDisplayed()) {
							rowMatch = false;
							break;
						}
					} catch (NoSuchElementException e) {
						rowMatch = false;
						break;
					}
					// logger.debug(rowCriteria.get(j) + " not found");
				}
				if (rowMatch) {
					matchedRows.add(initiallyMatchedRow);
				}
			}
		}

		if (logger.isDebugEnabled()) {
			if (!matchedRows.isEmpty()) {
				StringBuilder sb = new StringBuilder();
				for (WebElement matchingRow : matchedRows) {
					sb.append("\t" + matchingRow.getText() + "\n");
				}
				logger.debug("\nMatching rows found (" + matchedRows.size() + " total):\n" + sb.substring(0, sb.length() - 1).toString());
			} else {
				logger.info("No matching rows found.");
			}
		}

		// Check expected counts here
		switch (rowAction) {
		case COLLAPSE:
		case EXPAND:
			/*
			 * For Collapse and Expand just assert it is not empty. Going forward we may need to tighten this up by supplying a table reference because some screen like balances have a credit balances
			 * table and a invoices table that contains cells with just customer number info. This make the selection ambiguous and for now will cause an expand or collapse to happen in both tables.
			 */
			assertTrue(String.format("Expected to find at least one mathing row in order to perform a %s on", rowAction.getDescription()), !matchedRows.isEmpty());
			break;
		case DO_NOT_SEE:
			assertEquals("Expected to find no matching rows. Check your row criteria.", 0, matchedRows.size());
			break;
		default:
			assertEquals("Number of matching rows differs from expected number of rows.  Check your row criteria.", dataTableRowList.size() - 1, matchedRows.size());
			break;
		}
		return matchedRows;
	}

	/**
	 * Method that returns all visible table rows that match criteria in the given Data Table. An optional table ID can be given to narrow the search to a specific table. This can be helpful if there
	 * are multiple tables on the same page that may contain the same data.
	 *
	 * @param criteria
	 *            the data table that has the criteria to match on
	 * @return the matching table rows
	 */
	public List<WebElement> findTableRowsWithMatchingCriteria(RowAction rowAction, DataTable criteria) {
		return findTableRowsWithMatchingCriteria(rowAction, criteria, null);
	}

	/**
	 * Method that returns a list of checkable items that are in table rows that match criteria in the given Data Table. An optional table ID can be given to narrow the search to a specific table.
	 * This can be helpful if there are multiple tables on the same page that may contain the same data.
	 *
	 * @param rowAction
	 *            the row action to evaluate
	 * @param criteria
	 *            the data table that has the criteria to match on
	 * @param tableId
	 *            the table ID of the table to search in
	 * @return the matching checkable items in the table
	 */
	public List<WebElement> findActionableRowElements(RowAction rowAction, DataTable criteria, String tableId) {
		List<WebElement> matchedRows = TestInstance.getStepsUtil().findTableRowsWithMatchingCriteria(rowAction, criteria, tableId);
		By locator = TestInstance.getAppConfig().findRowActionLocator(rowAction).getByLocator();
		List<WebElement> actionableRowElements = new ArrayList<WebElement>();
		for (WebElement matchedRow : matchedRows) {
			try {
				actionableRowElements.add(matchedRow.findElement(locator));
			} catch (NoSuchElementException e) {
				// Handle cases where we know there may not be elements and it is not OK for the cannot select case.
				if (rowAction != RowAction.CANNOT_SELECT) {
					throw e;
				}
			}
		}
		return actionableRowElements;
	}

	/**
	 * Method that returns a list of checkable items that are in table rows that match criteria in the given Data Table. An optional table ID can be given to narrow the search to a specific table.
	 * This can be helpful if there are multiple tables on the same page that may contain the same data.
	 *
	 * @param rowAction
	 *            the row action to evaluate
	 * @param criteria
	 *            the data table that has the criteria to match on
	 * @return the matching checkable items in the table
	 */
	public List<WebElement> findActionableRowElements(RowAction rowAction, DataTable criteria) {
		return findActionableRowElements(rowAction, criteria, null);
	}

	/**
	 * Helper method to indicate if a modal is displayed. This will be used to in other methods to determine if only modal children should be considered when performing things like click, form input,
	 * or table row searches. There are two main reasons for this:
	 *
	 * 1. When a modal is displayed it is the primary focus in the UI.
	 *
	 * 2. Avoid cases of ambiguity on the UI. For instance if a modal is displayed that has a cancel button and there is also a cancel button on the page in the background which should we click? If
	 * the modal is displayed we should ensure the cancel button on the modal is the one clicked.
	 *
	 * @return true if a modal is displayed, false otherwise
	 */
	public boolean isModalDisplayed() {
		boolean modalDisplayed = false;
		// Assumption is modals will always pre-exist on the page even though they may not be visible.
		if (TestInstance.getAppConfig().getModalLocator() != null) {
			List<WebElement> modals = TestInstance.getWebDriver().findElements(TestInstance.getAppConfig().getModalLocator().getByLocator());
			for (WebElement modal : modals) {
				if (modal.isDisplayed()) {
					modalDisplayed = true;
					break;
				}
			}
		}
		return modalDisplayed;
	}

	/**
	 * Helper method that waits for all load masks to be gone from the page.
	 */
	public void waitForLoadMasks() {
		By locator = TestInstance.getAppConfig().getLoadingIndicatorLocator().getByLocator();
		List<WebElement> loadMasks = null;
		try {
			loadMasks = TestInstance.getWebDriverWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		} catch (TimeoutException e) {
			logger.info("here");
			// If not loading on the page, just return
			return;
		}
		// Have to check for state element in case load mask is removed from page during a page transition
		for (WebElement loadMask : loadMasks) {
			try {
				TestInstance.getWebDriverWait().until(ExpectedConditions.not(ExpectedConditions.visibilityOf(loadMask)));
			} catch (StaleElementReferenceException e) {
				return;
			}
		}
	}

	/**
	 * Helper method to find all matching child elements on the page that contain the given text. It does not check if they are visible or not.
	 *
	 * @param messageTextToUse
	 *            the message text to search for
	 * @return list of web elements with matching text
	 */
	public List<WebElement> findMatchingChildElementsWithText(String messageTextToUse) {
		String locator = String.format("//body//*[contains(normalize-space(string()), '%s')]", messageTextToUse);
		// This will return the parents elements that have the text as well.
		List<WebElement> webElements;
		try {
			webElements = TestInstance.getWebDriverWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(locator)));
		} catch (TimeoutException e) {
			logger.debug(String.format("No matching web elements found with text: %s", messageTextToUse));
			return new ArrayList<WebElement>();
		}
		// Iterator through all elements that have the matching text and remove any elements that are parents that have the matching next, so only the child text elements remain
		for (Iterator<WebElement> webElementsIter = webElements.iterator(); webElementsIter.hasNext();) {
			try {
				WebElement currentElement = webElementsIter.next();
				currentElement.findElement(By.xpath(String.format(".//*[contains(normalize-space(string()), '%s')]", messageTextToUse)));
				webElementsIter.remove();
			} catch (NoSuchElementException e) {

			}
		}
		return webElements;
	}

	/**
	 * Method to resolve text to get the actual text to use. If the text to parse is surround by [] such [Message_Text] then the text will be looked up from a resource bundle based on that key. If the
	 * text to resolve is not surrounded by [] it is just returned in its original form. Other text substitutions may happen to the text based on codes inside the message. The convention for
	 * substitutions inside the text is that they should be surrounded by {}. However, this method can be completely overridden as needed with your own step handler. It recommended to avoid using
	 * <> as a delimiter because Cucumber uses that itself in the feature files for substitution.
	 *
	 * @param text
	 *            the text to resolve
	 * @return the text to use
	 */
	public String resolveText(String text) {
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