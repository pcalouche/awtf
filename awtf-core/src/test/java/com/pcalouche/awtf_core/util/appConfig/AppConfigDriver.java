package com.pcalouche.awtf_core.util.appConfig;

import java.util.ArrayList;
import java.util.List;

import com.pcalouche.awtf_core.YamlHelper;
import com.pcalouche.awtf_core.util.enums.RowAction;

/**
 * Driver class that can be used to setup your App Elements
 * 
 * @author Philip Calouche
 *
 */
public class AppConfigDriver {
	public static void main(String[] args) {
		AppConfig appConfig = new AppConfig();
		// Global Locators
		appConfig.setLoadingIndicatorLocator(new AppElement("Loading Locator", ".load-mask-large, .load-mask-medium, .load-mask-small", AppElementLocatorType.css));
		appConfig.setModalLocator(new AppElement("Modal Locator", "//*[contains(@class,'modal')]", AppElementLocatorType.xpath));
		appConfig.setTooltipLocator(new AppElement("Tooltip Locator", ".//*[contains(@class, 'tooltip')]", AppElementLocatorType.xpath));
		// Web Application Elements
		List<AppElement> appWebElements = new ArrayList<AppElement>();
		// Misc App Elements
		appWebElements.add(new AppElement("Global Search", "#globalSearch", AppElementLocatorType.css));
		appWebElements.add(new AppElement("Account History Dropdown", "#accountHistoryDropdown", AppElementLocatorType.css));
		appWebElements.add(new AppElement("Animal Dropdown", "#animalDropdown", AppElementLocatorType.css));
		appWebElements.add(new AppElement("Error Box", ".error-box", AppElementLocatorType.css));
		// Modals
		appWebElements.add(new Modal("Test", "#testModal", AppElementLocatorType.css));
		// Elements with Tooltip
		appWebElements.add(new ElementWithTooltip("Info Icon", ".info-icon", AppElementLocatorType.css, "#infoIconTooltip", AppElementLocatorType.css));
		appWebElements.add(new ElementWithTooltip("Help Icon", ".help-icon", AppElementLocatorType.css, "#helpIconTooltip", AppElementLocatorType.css));
		appConfig.setAppWebElements(appWebElements);
		// Row Action Locators - Note framework currently requires these to be of type xpath, and they must be relative to a table row
		List<RowActionLocator> rowActionLocators = new ArrayList<RowActionLocator>();
		rowActionLocators.add(new RowActionLocator("select", ".//input[@type='checkbox' or @type='radio']", AppElementLocatorType.xpath, RowAction.SELECT));
		rowActionLocators.add(new RowActionLocator("deselect", ".//input[@type='checkbox' or @type='radio']", AppElementLocatorType.xpath, RowAction.DESELECT));
		rowActionLocators.add(new RowActionLocator("click", ".//a", AppElementLocatorType.xpath, RowAction.CLICK));
		rowActionLocators.add(new RowActionLocator("expand", ".//*[contains(@class,'row-expander collapsed')]", AppElementLocatorType.xpath, RowAction.EXPAND));
		rowActionLocators.add(new RowActionLocator("collapse", ".//*[contains(@class,'row-expander expanded')]", AppElementLocatorType.xpath, RowAction.COLLAPSE));
		rowActionLocators.add(new RowActionLocator("can select", ".//input[@type='checkbox' or @type='radio']", AppElementLocatorType.xpath, RowAction.CAN_SELECT));
		rowActionLocators.add(new RowActionLocator("cannot select", ".//input[@type='checkbox' or @type='radio']", AppElementLocatorType.xpath, RowAction.CANNOT_SELECT));
		rowActionLocators.add(new RowActionLocator("see", "", AppElementLocatorType.xpath, RowAction.SEE));
		rowActionLocators.add(new RowActionLocator("do not see", "", AppElementLocatorType.xpath, RowAction.DO_NOT_SEE));
		rowActionLocators.add(new RowActionLocator("see selected", ".//input[@type='checkbox' or @type='radio']", AppElementLocatorType.xpath, RowAction.SEE_SELECTED));
		rowActionLocators.add(new RowActionLocator("see deselected", ".//input[@type='checkbox' or @type='radio']", AppElementLocatorType.xpath, RowAction.SEE_DESELECTED));
		appConfig.setRowActionLocators(rowActionLocators);
		// Setup CSS class to look for when looking for error message
		List<String> errorMessageClasses = new ArrayList<String>();
		errorMessageClasses.add("error");
		errorMessageClasses.add("invalid");
		appConfig.setErrorMessageClasses(errorMessageClasses);
		YamlHelper.printToScreen(appConfig);
		YamlHelper.writeToFile(appConfig, System.getProperty("user.dir") + "\\src\\test\\resources\\yaml\\appConfig.yml");

		AppConfig appConfig2 = (AppConfig) YamlHelper.loadFromInputStream("/yaml/appConfig.yml");
		System.out.println(appConfig2.getAppWebElements().size());
		AppElement appElement = appConfig2.findAppWebElement("Global Search");
		System.out.println(appElement.getDescription());
	}
}
