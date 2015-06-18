package com.pcalouche.awtf_core.util.appConfig;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;

import com.pcalouche.awtf_core.util.enums.RowAction;

public class AppConfig {
	private AppElement loadingIndicatorLocator;
	private AppElement modalLocator;
	private AppElement tooltipLocator;
	private List<AppElement> appWebElements = new ArrayList<AppElement>();
	private List<RowActionLocator> rowActionLocators = new ArrayList<RowActionLocator>();
	private List<String> errorMessageClasses = new ArrayList<String>();

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
	 * @return the rowActionLocators
	 */
	public List<RowActionLocator> getRowActionLocators() {
		return rowActionLocators;
	}

	/**
	 * @param rowActionLocators
	 *            the rowActionLocators to set
	 */
	public void setRowActionLocators(List<RowActionLocator> rowActionLocators) {
		this.rowActionLocators = rowActionLocators;
	}

	/**
	 * @return the errorMessageClasses
	 */
	public List<String> getErrorMessageClasses() {
		return errorMessageClasses;
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

	public RowActionLocator findRowActionLocator(RowAction rowAction) {
		RowActionLocator foundRowActionLocator = null;
		for (RowActionLocator rowActionLocator : rowActionLocators) {
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
