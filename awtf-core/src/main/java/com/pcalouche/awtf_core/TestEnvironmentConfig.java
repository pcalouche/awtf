package com.pcalouche.awtf_core;

import com.pcalouche.awtf_core.util.enums.BrowserType;

public class TestEnvironmentConfig {

	private BrowserType browser = BrowserType.phantomJS;
	private int secondsToWait = 15;
	private String url;
	private boolean screenshotBeforeClick = false;
	private boolean screenshotOnScenarioCompletion = true;
	private String coreStepHandlerClass = "com.pcalouche.awtf_core.CoreStepHandler";
	private String stepsUtilClass = "com.pcalouche.awtf_core.StepsUtil";

	/**
	 * Needed for YAML loading of this object, but not useful for anything else
	 */
	public TestEnvironmentConfig() {
	}

	/**
	 * @return the browser
	 */
	public BrowserType getBrowser() {
		return browser;
	}

	/**
	 * @param browser
	 *            the browser to set
	 */
	public void setBrowser(BrowserType browser) {
		this.browser = browser;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the secondsToWait
	 */
	public int getSecondsToWait() {
		return secondsToWait;
	}

	/**
	 * @param secondsToWait
	 *            the secondsToWait to set
	 */
	public void setSecondsToWait(int secondsToWait) {
		this.secondsToWait = secondsToWait;
	}

	/**
	 * @return the screenshotBeforeClick
	 */
	public boolean isScreenshotBeforeClick() {
		return screenshotBeforeClick;
	}

	/**
	 * @param screenshotBeforeClick
	 *            the screenshotBeforeClick to set
	 */
	public void setScreenshotBeforeClick(boolean screenshotBeforeClick) {
		this.screenshotBeforeClick = screenshotBeforeClick;
	}

	/**
	 * @return the screenshotOnScenarioCompletion
	 */
	public boolean isScreenshotOnScenarioCompletion() {
		return screenshotOnScenarioCompletion;
	}

	/**
	 * @param screenshotOnScenarioCompletion
	 *            the screenshotOnScenarioCompletion to set
	 */
	public void setScreenshotOnScenarioCompletion(boolean screenshotOnScenarioCompletion) {
		this.screenshotOnScenarioCompletion = screenshotOnScenarioCompletion;
	}

	/**
	 * @return the coreStepHandlerClass
	 */
	public String getCoreStepHandlerClass() {
		return coreStepHandlerClass;
	}

	/**
	 * @param coreStepHandlerClass
	 *            the coreStepHandlerClass to set
	 */
	public void setCoreStepHandlerClass(String coreStepHandlerClass) {
		this.coreStepHandlerClass = coreStepHandlerClass;
	}

	/**
	 * @return the stepsUtilClass
	 */
	public String getStepsUtilClass() {
		return stepsUtilClass;
	}

	/**
	 * @param stepsUtilClass
	 *            the stepsUtilClass to set
	 */
	public void setStepsUtilClass(String stepsUtilClass) {
		this.stepsUtilClass = stepsUtilClass;
	}
}