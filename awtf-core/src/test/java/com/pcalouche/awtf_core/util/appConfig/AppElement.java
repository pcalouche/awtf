package com.pcalouche.awtf_core.util.appConfig;

import org.openqa.selenium.By;

public class AppElement {
	private String description;
	private String locator;
	private AppElementLocatorType locatorType;

	public AppElement() {
	}

	public AppElement(String description, String locator, AppElementLocatorType locatorType) {
		this.description = description;
		this.locator = locator;
		this.locatorType = locatorType;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the locator
	 */
	public String getLocator() {
		return locator;
	}

	/**
	 * @param locator
	 *            the locator to set
	 */
	public void setLocator(String locator) {
		this.locator = locator;
	}

	/**
	 * @return the locatorType
	 */
	public AppElementLocatorType getLocatorType() {
		return locatorType;
	}

	/**
	 * @param locatorType
	 *            the locatorType to set
	 */
	public void setLocatorType(AppElementLocatorType locatorType) {
		this.locatorType = locatorType;
	}

	/**
	 * Method to get an App Element's Selenium by Locator
	 * 
	 * @return the Selenium By Locator
	 */
	public By getByLocator() {
		switch (this.getLocatorType()) {
		case css:
			return By.cssSelector(this.getLocator());
		case xpath:
			return By.xpath(this.getLocator());
		default:
			return null;
		}
	}
}
