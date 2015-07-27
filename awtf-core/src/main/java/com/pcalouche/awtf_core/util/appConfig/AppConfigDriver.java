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
		YamlHelper.printToScreen(appConfig);
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
		appWebElements.add(new AppElement("Test Span App Element", "#testSpanAppElement", AppElementLocatorType.css));
		// Modals
		appWebElements.add(new Modal("Test", "#testModal", AppElementLocatorType.css));
		// Elements with Tooltip
		appWebElements.add(new ElementWithTooltip("Info Icon", ".info-icon", AppElementLocatorType.css, "#infoIconTooltip", AppElementLocatorType.css));
		appWebElements.add(new ElementWithTooltip("Help Icon", ".help-icon", AppElementLocatorType.css, "#helpIconTooltip", AppElementLocatorType.css));
		appConfig.setAppWebElements(appWebElements);
		// Row Action Definitions - Note framework currently requires these to be of type xpath, and they must be relative to a table row
		List<RowActionDefinition> rowActionDefinitions = new ArrayList<RowActionDefinition>();
		rowActionDefinitions.add(new RowActionDefinition("select", ".//input[@type='checkbox' or @type='radio']", AppElementLocatorType.xpath, RowAction.SELECT));
		rowActionDefinitions.add(new RowActionDefinition("deselect", ".//input[@type='checkbox' or @type='radio']", AppElementLocatorType.xpath, RowAction.DESELECT));
		rowActionDefinitions.add(new RowActionDefinition("click", ".//a", AppElementLocatorType.xpath, RowAction.CLICK));
		rowActionDefinitions.add(new RowActionDefinition("expand", ".//*[contains(@class,'row-expander collapsed')]", AppElementLocatorType.xpath, RowAction.EXPAND));
		rowActionDefinitions.add(new RowActionDefinition("collapse", ".//*[contains(@class,'row-expander expanded')]", AppElementLocatorType.xpath, RowAction.COLLAPSE));
		rowActionDefinitions.add(new RowActionDefinition("can select", ".//input[@type='checkbox' or @type='radio']", AppElementLocatorType.xpath, RowAction.CAN_SELECT));
		rowActionDefinitions.add(new RowActionDefinition("cannot select", ".//input[@type='checkbox' or @type='radio']", AppElementLocatorType.xpath, RowAction.CANNOT_SELECT));
		rowActionDefinitions.add(new RowActionDefinition("see", "", AppElementLocatorType.xpath, RowAction.SEE));
		rowActionDefinitions.add(new RowActionDefinition("do not see", "", AppElementLocatorType.xpath, RowAction.DO_NOT_SEE));
		rowActionDefinitions.add(new RowActionDefinition("see selected", ".//input[@type='checkbox' or @type='radio']", AppElementLocatorType.xpath, RowAction.SEE_SELECTED));
		rowActionDefinitions.add(new RowActionDefinition("see deselected", ".//input[@type='checkbox' or @type='radio']", AppElementLocatorType.xpath, RowAction.SEE_DESELECTED));
		appConfig.setRowActionDefinitions(rowActionDefinitions);
		// Setup CSS class to look for when looking for error message
		List<String> errorMessageClasses = new ArrayList<String>();
		errorMessageClasses.add("error");
		errorMessageClasses.add("invalid");
		appConfig.setErrorMessageClasses(errorMessageClasses);
		// Set the location of the message bundle
		appConfig.setMessageBundleLocation("messages_en");
		YamlHelper.printToScreen(appConfig);
		YamlHelper.writeToFile(appConfig, System.getProperty("user.dir") + "\\src\\test\\resources\\yaml\\appConfig.yml");
	}
}
