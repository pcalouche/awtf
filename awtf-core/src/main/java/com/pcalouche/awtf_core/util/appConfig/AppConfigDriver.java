package com.pcalouche.awtf_core.util.appConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcalouche.awtf_core.util.enums.RowAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Driver class that can be used to setup your App Elements
 *
 * @author Philip Calouche
 */
public class AppConfigDriver {
    private static final Logger logger = LoggerFactory.getLogger(AppConfigDriver.class);

    public static void main(String[] args) {
        // Set this for your needs

        Path outputDirectory = Paths.get("C:/Users/Philip Calouche/Documents/Projects/awtf/awtf-core/src/test/resources/appConfigs");
        try {
            Files.createDirectories(outputDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Path outputFile = outputDirectory.resolve("coreAppConfig.json");

        // Create the AppConfig as needed
        AppConfig appConfig = new AppConfig();
        // Global Locators
        appConfig.setLoadingIndicatorLocator(new AppElement("Loading Locator", ".load-mask-large, .load-mask-medium, .load-mask-small", AppElementLocatorType.css));
        appConfig.setModalLocator(new AppElement("Modal Locator", "//*[contains(@class,'modal')]", AppElementLocatorType.xpath));
        appConfig.setTooltipLocator(new AppElement("Tooltip Locator", ".//*[contains(@class, 'tooltip')]", AppElementLocatorType.xpath));
        // Web Application Elements
        List<AppElement> appWebElements = new ArrayList<>();
        // Misc App Elements
        appWebElements.add(new AppElement("Global Search", "#globalSearch", AppElementLocatorType.css));
        appWebElements.add(new AppElement("Account History Dropdown", "#accountHistoryDropdown", AppElementLocatorType.css));
        appWebElements.add(new AppElement("Animal Dropdown", "#animalDropdown", AppElementLocatorType.css));
        appWebElements.add(new AppElement("Error Box", ".error-box", AppElementLocatorType.css));
        appWebElements.add(new AppElement("Test Span App Element", "#testSpanAppElement", AppElementLocatorType.css));
        appWebElements.add(new AppElement("Web Design Menu Item", "Web Design", AppElementLocatorType.linkText));
        // Modals
        appWebElements.add(new Modal("Test", "#testModal", AppElementLocatorType.css));
        appConfig.setAppWebElements(appWebElements);
        // Elements with Tooltip
        appWebElements.add(new ElementWithTooltip("Info Icon", ".info-icon", AppElementLocatorType.css, "#infoIconTooltip", AppElementLocatorType.css));
        appWebElements.add(new ElementWithTooltip("Help Icon", ".help-icon", AppElementLocatorType.css, "#helpIconTooltip", AppElementLocatorType.css));
        // Row Action Definitions - Note framework currently requires these to be of type xpath, and they must be relative to a table row
        List<RowActionDefinition> rowActionDefinitions = new ArrayList<>();
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
        List<String> errorMessageClasses = new ArrayList<>();
        errorMessageClasses.add("error");
        errorMessageClasses.add("invalid");
        appConfig.setErrorMessageClasses(errorMessageClasses);
        // Set the location of the message bundle
        appConfig.setMessageBundleLocation("messages_en");

        // Finally write the result to file and printer it back out for testing purposes
        try (
                BufferedWriter bufferedWriter = Files.newBufferedWriter(outputFile)
        ) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(bufferedWriter, appConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader bufferedReader = Files.newBufferedReader(outputFile)) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
            AppConfig appConfigFromFile = objectMapper.readValue(bufferedReader, AppConfig.class);
            logger.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(appConfigFromFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
