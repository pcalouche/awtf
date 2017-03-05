package com.pcalouche.awtf.util.appConfig;

import org.openqa.selenium.By;

public class AppElement {
    private final String description;
    private final String locator;
    private final AppElementLocatorType locatorType;

    public AppElement(String description, String locator, AppElementLocatorType locatorType) {
        this.description = description;
        this.locator = locator;
        this.locatorType = locatorType;
    }

    public String getDescription() {
        return description;
    }

    public String getLocator() {
        return locator;
    }

    public AppElementLocatorType getLocatorType() {
        return locatorType;
    }

    /**
     * Method to get an App Element's Selenium by Locator
     *
     * @return the Selenium By Locator
     */
    public By getByLocator() {
        switch (this.getLocatorType()) {
            case className:
                return By.className(this.getLocator());
            case css:
                return By.cssSelector(this.getLocator());
            case id:
                return By.id(this.getLocator());
            case linkText:
                return By.linkText(this.getLocator());
            case name:
                return By.name(this.getLocator());
            case partialLinkText:
                return By.partialLinkText(this.getLocator());
            case tagName:
                return By.tagName(this.getLocator());
            case xpath:
                return By.xpath(this.getLocator());
            default:
                return null;
        }
    }
}
