package com.pcalouche.awtf_core;

import com.pcalouche.awtf_core.util.appConfig.*;
import com.pcalouche.awtf_core.util.enums.BrowserType;
import com.pcalouche.awtf_core.util.enums.RowAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;

@Configuration
@PropertySources({
        @PropertySource("classpath:/testEnvironmentConfigs/test_environment_config.${testEnvironment:localhost}.properties"),
        @PropertySource("classpath:/messages_en.properties")
})
public class CoreConfig {
    private static final Logger logger = LoggerFactory.getLogger(CoreConfig.class);
    @Autowired
    private Environment environment;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public TestInstance testInstance() {
        return new TestInstance(this.testEnvironmentConfig(), this.appConfig());
    }

    @Bean
    public CoreStepsUtil coreStepsUtil() {
        return new CoreStepsUtil(testInstance());
    }

    @Bean
    public CoreStepHandler coreStepHandler() {
        return new CoreStepHandler(testEnvironmentConfig(), testInstance(), coreStepsUtil());
    }

    private TestEnvironmentConfig testEnvironmentConfig() {
        logger.info("Detected test environment was->" + environment.getProperty("testEnvironment"));
        BrowserType browserType = BrowserType.valueOf(environment.getProperty("browserType"));
        int secondsToWait = Integer.valueOf(environment.getProperty("secondsToWait"));
        String url = environment.getProperty("url");
        boolean screenshotBeforeClick = Boolean.valueOf(environment.getProperty("screenshotBeforeClick"));
        boolean screenshotOnScenarioCompletion = Boolean.valueOf(environment.getProperty("screenshotOnScenarioCompletion"));
        return new TestEnvironmentConfig(browserType, secondsToWait, url, screenshotBeforeClick, screenshotOnScenarioCompletion);
    }

    private AppConfig appConfig() {
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
        return appConfig;
    }
}
