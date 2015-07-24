package com.pcalouche.awtf_core.util.appConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;

import com.pcalouche.awtf_core.util.enums.RowAction;

public class AppConfig {
	private AppElement loadingIndicatorLocator;
	private AppElement modalLocator;
	private AppElement tooltipLocator;
	private List<AppElement> appWebElements = new ArrayList<AppElement>();
	private List<RowActionDefinition> rowActionDefinitions = new ArrayList<RowActionDefinition>();
	private List<String> errorMessageClasses = new ArrayList<String>();
	private String messageBundleLocation;
	private ResourceBundle messageBundle;

	public AppConfig() {

	}

	/**
	 * @return the loadingIndicatorLocator
	 */
	public AppElement getLoadingIndicatorLocator() {
		return loadingIndicatorLocator;
	}

	/**
	 * @param loadingIndicatorLocator
	 *            the loadingIndicatorLocator to set
	 */
	public void setLoadingIndicatorLocator(AppElement loadingIndicatorLocator) {
		this.loadingIndicatorLocator = loadingIndicatorLocator;
	}

	/**
	 * @return the modalLocator
	 */
	public AppElement getModalLocator() {
		return modalLocator;
	}

	/**
	 * @param modalLocator
	 *            the modalLocator to set
	 */
	public void setModalLocator(AppElement modalLocator) {
		this.modalLocator = modalLocator;
	}

	/**
	 * @return the tooltipLocator
	 */
	public AppElement getTooltipLocator() {
		return tooltipLocator;
	}

	/**
	 * @param tooltipLocator
	 *            the tooltipLocator to set
	 */
	public void setTooltipLocator(AppElement tooltipLocator) {
		this.tooltipLocator = tooltipLocator;
	}

	/**
	 * @return the appWebElements
	 */
	public List<AppElement> getAppWebElements() {
		return appWebElements;
	}

	/**
	 * @param appWebElements
	 *            the appWebElements to set
	 */
	public void setAppWebElements(List<AppElement> appWebElements) {
		this.appWebElements = appWebElements;
	}

	/**
	 * @return the rowActionDefinitions
	 */
	public List<RowActionDefinition> getRowActionDefinitions() {
		return rowActionDefinitions;
	}

	/**
	 * @param rowActionDefinitions
	 *            the rowActionDefinitions to set
	 */
	public void setRowActionDefinitions(List<RowActionDefinition> rowActionDefinitions) {
		this.rowActionDefinitions = rowActionDefinitions;
	}

	/**
	 * @return the errorMessageClasses
	 */
	public List<String> getErrorMessageClasses() {
		return errorMessageClasses;
	}

	/**
	 * @return the messageBundleLocation
	 */
	public String getMessageBundleLocation() {
		return messageBundleLocation;
	}

	/**
	 * @param messageBundleLocation
	 *            the messageBundleLocation to set
	 */
	public void setMessageBundleLocation(String messageBundleLocation) {
		this.messageBundleLocation = messageBundleLocation;
	}

	/**
	 * @return the messageBundle
	 */
	public ResourceBundle getMessageBundle() {
		if (messageBundle == null) {
			messageBundle = ResourceBundle.getBundle(messageBundleLocation);
		}
		return messageBundle;
	}

	/**
	 * @param errorMessageClasses
	 *            the errorMessageClasses to set
	 */
	public void setErrorMessageClasses(List<String> errorMessageClasses) {
		this.errorMessageClasses = errorMessageClasses;
	}

	public AppElement findAppWebElement(String description) {
		return findAppWebElement(description, null);
	}

	public AppElement findAppWebElement(String description, Class<?> clazz) {
		AppElement foundBaseWebElement = null;
		for (AppElement appWebElement : appWebElements) {
			if ((clazz == null || appWebElement.getClass().equals(clazz)) && appWebElement.getDescription().equals(description)) {
				foundBaseWebElement = appWebElement;
				break;
			}
		}
		return foundBaseWebElement;
	}

	public RowActionDefinition findRowActionLocator(RowAction rowAction) {
		RowActionDefinition foundRowActionLocator = null;
		for (RowActionDefinition rowActionLocator : rowActionDefinitions) {
			if (rowActionLocator.getRowAction() == rowAction) {
				foundRowActionLocator = rowActionLocator;
				break;
			}
		}
		return foundRowActionLocator;
	}

	public String getValidKnownDescriptions() {
		return getValidKnownDescriptions(null);
	}

	public String getValidKnownDescriptions(Class<?> clazz) {
		List<String> descriptions = new ArrayList<String>();
		for (AppElement appWebElement : appWebElements) {
			if (clazz == null || appWebElement.getClass().equals(clazz)) {
				descriptions.add(appWebElement.getDescription());
			}
		}
		return StringUtils.join(descriptions, ", ");
	}

	public boolean webElementHasErrorClass(WebElement webElement) {
		String webElementClasses = webElement.getAttribute("class");
		for (String errorMessageClass : this.getErrorMessageClasses()) {
			if (webElementClasses.contains(errorMessageClass)) {
				return true;
			}
		}
		return false;
	}
}
