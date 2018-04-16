package com.pcalouche.awtf.core.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class PageObject {
    public static final int DEFAULT_WEB_DRIVER_WAIT = 45;
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected WebDriver webDriver;
    protected WebDriverWait webDriverWait;

    public PageObject() {
    }

    @Autowired
    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    @Autowired
    public void setWebDriverWait(WebDriverWait webDriverWait) {
        this.webDriverWait = webDriverWait;
    }

    /**
     * Method to update the web driver wait's default wait time needs to be increased.
     * This is useful for when a UI action is known to take a longer amount of time than the default .
     * <p>
     * The resetWebDriverWait method should be called to reset this value, so other tests aren't affected
     *
     * @param secondsToWait the new wait value for the web driver wait in seconds
     */
    protected void updateWebDriverWait(int secondsToWait) {
        webDriverWait.withTimeout(Duration.ofSeconds(secondsToWait));
    }

    /**
     * Method to reset the web driver wait back to the default value
     */
    protected void resetWebDriverWait() {
        webDriverWait.withTimeout(Duration.ofSeconds(DEFAULT_WEB_DRIVER_WAIT));
    }

    /**
     * Use this method to navigate to a new URL.
     * The current main window reference is also updated when this called.
     *
     * @param url the URL to navigate to
     */
    public void navigateToUrl(String url) {
        webDriver.get(url);
    }

    /**
     * Method to wait for the visibility of a web element
     * If expected condition is not met a timeout exception will be thrown.
     * This can be caught and handle in the page object code if needed
     *
     * @param webElement the web element to evaluate
     * @return the visible web element
     */
    protected WebElement waitForVisibilityOfElement(WebElement webElement) {
        return webDriverWait.until(ExpectedConditions.visibilityOf(webElement));
    }

    /**
     * Method to wait for the visibility of a web element list
     * If expected condition is not met a timeout exception will be thrown.
     * This can be caught and handle in the page object code if needed
     *
     * @param webElements the web element list to evaluate
     * @return the visible web element list
     */
    protected List<WebElement> waitForVisibilityOfElements(List<WebElement> webElements) {
        return webDriverWait.until(ExpectedConditions.visibilityOfAllElements(webElements));
    }

    /**
     * Method to wait for the visibility of a web element list based on a locator
     * If expected condition is not met a timeout exception will be thrown.
     * This can be caught and handle in the page object code if needed
     *
     * @param locator the locator to use
     * @return the visible web element
     */
    protected WebElement waitForVisibilityOfElementLocatedBy(By locator) {
        return webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Method to wait for the visibility of a web element list based on a locator
     * If expected condition is not met a timeout exception will be thrown.
     * This can be caught and handle in the page object code if needed
     *
     * @param locator the locator to use
     * @return the visible web element list
     */
    protected List<WebElement> waitForVisibilityOfElementsLocatedBy(By locator) {
        return webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    /**
     * Method to wait for the visibility of a nested web element based on a locator
     * If expected condition is not met a timeout exception will be thrown.
     * This can be caught and handle in the page object code if needed
     *
     * @param parentWebElement the parent element
     * @param locator          the locator to use for the nested elements
     * @return the visible nested web element
     */
    protected WebElement waitForVisibilityOfNestedElement(WebElement parentWebElement, By locator) {
        List<WebElement> webElements = webDriverWait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(waitForVisibilityOfElement(parentWebElement), locator));
        return webElements.get(0);
    }

    /**
     * Method to wait for the visibility of a nested web element list based on a locator
     * If expected condition is not met a timeout exception will be thrown.
     * This can be caught and handle in the page object code if needed
     *
     * @param parentWebElement the parent element
     * @param locator          the locator to use for the nested elements
     * @return the visible nested web element list
     */
    protected List<WebElement> waitForVisibilityOfNestedElements(WebElement parentWebElement, By locator) {
        return webDriverWait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(waitForVisibilityOfElement(parentWebElement), locator));
    }

    /**
     * Method to wait for the invisibility of a web element
     * If expected condition is not met a timeout exception will be thrown.
     * This can be caught and handle in the page object code if needed
     *
     * @param webElement the web element to evaluate
     * @return boolean
     */
    protected boolean waitForInvisibilityOfElement(WebElement webElement) {
        return webDriverWait.until(ExpectedConditions.invisibilityOf(webElement));
    }

    /**
     * Method to wait for the invisibility of a web element list
     * If expected condition is not met a timeout exception will be thrown.
     * This can be caught and handle in the page object code if needed
     *
     * @param webElements the web element list to evaluate
     */
    protected boolean waitForInvisibilityOfElements(List<WebElement> webElements) {
        return webDriverWait.until(ExpectedConditions.invisibilityOfAllElements(webElements));
    }

    /**
     * Method to wait for the invisibility of a web element list based on a locator
     * If expected condition is not met a timeout exception will be thrown.
     * This can be caught and handle in the page object code if needed
     *
     * @param locator the locator to use
     * @return boolean
     */
    protected boolean waitForInvisibilityOfElementLocatedBy(By locator) {
        return webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Method to perform a click on the web element if it is clickable
     * If expected condition is not met a timeout exception will be thrown.
     * This can be caught and handle in the page object code if needed
     *
     * @param webElement the web element to click
     */
    protected void webElementClick(WebElement webElement) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement)).click();
    }

    public void webElementClick(By by) {
        webElementClick(webDriver.findElement(by));
    }

    /**
     * Method to select a web element.  This is meant to be used on radio and check boxes.
     * If expected condition is not met a timeout exception will be thrown.
     * This can be caught and handle in the page object code if needed
     *
     * @param webElement the web element to select
     * @return the web element that was just selected
     */
    protected boolean isSelected(WebElement webElement) {
        return webDriverWait.until(ExpectedConditions.visibilityOf(webElement)).isSelected();
    }

    /**
     * Method to select an item in a single select list box.
     * If expected condition is not met a timeout exception will be thrown.
     * This can be caught and handle in the page object code if needed
     *
     * @param webElement the web element that is a single select list box
     * @param listItem   the value to set the single select list box to
     */
    protected void listboxSelect(WebElement webElement, String listItem) {
        // Waits for the select to be clickable
        Select select = new Select(webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement)));
        select.selectByVisibleText(listItem);
    }

    /**
     * Method to get the selected value in a single select list box.
     * If expected condition is not met a timeout exception will be thrown.
     * This can be caught and handle in the page object code if needed
     *
     * @param webElement the web element that is a single select list box
     * @return the selected value
     */
    protected String listboxSelectedValue(WebElement webElement) {
        Select select = new Select(webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement)));
        if (select.isMultiple()) {
            throw new RuntimeException("Using a single-select method on a multi-select.  Check method you are using for selection.");
        }
        return select.getFirstSelectedOption().getText();
    }

    /**
     * Method to select a series of items in a multi select list box.
     * A semicolon delimited string is expected.
     * Previously selected values are NOT deselected
     * If expected condition is not met a timeout exception will be thrown.
     * This can be caught and handle in the page object code if needed
     *
     * @param webElement the web element that is a multi select list box
     * @param listItems  the values to select in the multi select list box
     */
    protected void listboxMultiSelect(WebElement webElement, String listItems) {
        Select select = new Select(webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement)));
        if (!select.isMultiple()) {
            throw new RuntimeException("Using a multi-select method on a single-select.  Check method you are using for selection.");
        }
        for (String listItem : listItems.split(";")) {
            select.selectByVisibleText(listItem);
        }
    }

    /**
     * Method to select a series of items in a multi select list box.
     * A semicolon delimited string is expected.
     * If expected condition is not met a timeout exception will be thrown.
     * This can be caught and handle in the page object code if needed
     *
     * @param webElement the web element that is a multi select list box
     * @param listItems  the values to deselect in the multi select list box
     */
    protected void listboxMultiDeselect(WebElement webElement, String listItems) {
        Select select = new Select(webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement)));
        if (!select.isMultiple()) {
            throw new RuntimeException("Using a multi-select method on a single-select.  Check method you are using for selection.");
        }
        for (String listItem : listItems.split(";")) {
            select.deselectByVisibleText(listItem);
        }
    }

    /**
     * Method to get the selected values in a multi select list box.
     * If expected condition is not met a timeout exception will be thrown.
     * This can be caught and handle in the page object code if needed
     *
     * @param webElement the web element that is a multi select list box
     * @return the selected values in the multi select list box
     */
    protected List<String> listboxMultiSelectedValues(WebElement webElement) {
        List<String> selectedValues = new ArrayList<>();
        Select select = new Select(webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement)));
        if (!select.isMultiple()) {
            throw new RuntimeException("Using a multi-select method on a single-select.  Check method you are using for selection.");
        }
        for (WebElement option : select.getAllSelectedOptions()) {
            selectedValues.add(option.getText());
        }
        return selectedValues;
    }

    /**
     * Method to select all values in a multi select list box.
     *
     * @param webElement the web element that is a multi select list box
     */
    protected void listboxMultiSelectAll(WebElement webElement) {
        Select select = new Select(webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement)));
        if (!select.isMultiple()) {
            throw new RuntimeException("Using a multi-select method on a single-select.  Check method you are using for selection.");
        }
        select.getOptions().stream()
                .filter(option -> !option.isSelected())
                .forEach(option -> webDriverWait.until(ExpectedConditions.elementToBeClickable(option)).click());
    }

    /**
     * Method to deselect all values in a multi select list box.
     *
     * @param webElement the web element that is a multi select list box
     */
    protected void listboxMultiDeselectAll(WebElement webElement) {
        Select select = new Select(webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement)));
        if (!select.isMultiple()) {
            throw new RuntimeException("Using a multi-select method on a single-select.  Check method you are using for selection.");
        }
        select.getOptions().stream()
                .filter(WebElement::isSelected)
                .forEach(option -> webDriverWait.until(ExpectedConditions.elementToBeClickable(option)).click());
    }

    /**
     * Method to get all of the options in a select list box.
     *
     * @param webElement the web element that is a select list box
     * @return a string list of containing the options in a select list box.
     */
    protected List<String> listboxGetOptions(WebElement webElement) {
        Select select = new Select(webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement)));
        List<String> options = new ArrayList<>();
        select.getOptions().forEach(o -> options.add(o.getText()));
        return options;
    }

    /**
     * Method to set a text input field.  This works for text fields, password fields, text areas, and file download fields.
     *
     * @param webElement the web element to input text for
     * @param value      the value of the input text to use
     */
    protected void clearAndSetText(WebElement webElement, String value) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement));
        webElement.clear();
        webElement.sendKeys(value.replace("\\s", " "));
    }

    /**
     * Test if an element has the expected state
     *
     * @param webElement       the web element to test
     * @param htmlElementState the element state to test for
     * @return true if the element has the expected state, false otherwise
     */
    protected boolean elementHasExpectedState(WebElement webElement, HtmlElementState htmlElementState) {
        if (!htmlElementState.equals(HtmlElementState.hidden)) {
            waitForVisibilityOfElement(webElement);
        }
        switch (htmlElementState) {
            case disabled:
                return (!webElement.isEnabled() || webElement.getAttribute("class").contains("disabled"));
            case enabled:
                return (webElement.isEnabled() && !webElement.getAttribute("class").contains("disabled"));
            case hidden:
                return (!webElement.isDisplayed());
            case visible:
                return (webElement.isDisplayed());
            case selected:
                return (webElement.isSelected());
            case unselected:
                return (!webElement.isSelected());
            default:
                return false;
        }
    }

    /**
     * Method that will hover the cursor over a web element.  This can be used to initiate things
     * like tooltips.  If a clearing web element is applied
     *
     * @param webElement the web element to move to
     */
    protected void hoverOverElement(WebElement webElement) {
        // Move cursor to a far offset to clear out any old tooltips
        new Actions(webDriver).moveByOffset(-10000, -10000).build().perform();
        // Add a small delay so any tooltips that were previously visible have time to fade out.  Ideally all tooltips in the
        // application would have the same css locator and we'd wait for the invisibility of that tooltip by a locator before
        // proceeding.  This should hopefully work for now though.
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            logger.error("InterruptedException", e);
        }
        // Now hover over the web web element
        new Actions(webDriver).moveToElement(webElement).build().perform();
    }

    /**
     * Gets current page title.
     *
     * @return current page title.
     */
    protected String getPageTitle() {
        return webDriver.getTitle();
    }

    /**
     * Method to close the active window.
     */
    protected void closeCurrentWindow() {
        webDriver.close();
    }

    /**
     * Method to switch to one or more frame.  Frames should be separated by a ";".
     * This method will try to switch to each frame in order given.
     *
     * @param frameList the frame list used for frame switching
     */
    protected void switchToFrame(String frameList) {
        webDriver.switchTo().defaultContent();
        for (String frame : frameList.split(";")) {
            webDriverWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));
        }
    }

    /**
     * Method to switch to the default content.
     */
    protected void switchToParentFrame() {
        webDriver.switchTo().defaultContent();
    }

    /**
     * method to wait until the alert pops up and then accept it
     */

    protected void acceptAlert() {
        webDriverWait.until(ExpectedConditions.alertIsPresent()).accept();
    }

    /**
     * method to wait until the alert pops up and then dismiss it
     */
    protected void dismissAlert() {
        webDriverWait.until(ExpectedConditions.alertIsPresent()).dismiss();
    }
}
