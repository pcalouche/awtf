package com.pcalouche.awtf_core;

import com.pcalouche.awtf_core.util.FileDownloadUtils;
import com.pcalouche.awtf_core.util.appConfig.*;
import com.pcalouche.awtf_core.util.enums.BrowserType;
import com.pcalouche.awtf_core.util.enums.RowAction;
import cucumber.runtime.java.spring.GlueCodeScopeConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@PropertySources({
        @PropertySource("classpath:/testEnvironmentConfigs/test_environment_config.${testEnvironment:localhost}.properties"),
        @PropertySource("classpath:/messages_en.properties")
})
@ComponentScan("com.pcalouche.awtf_core")
@Import({GlueCodeScopeConfig.class})
public class CoreConfig {
    private static final Logger logger = LoggerFactory.getLogger(CoreConfig.class);
    private final Environment environment;

    @Autowired
    public CoreConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public TestEnvironmentConfig testEnvironmentConfig() {
        logger.info("Detected test environment was->" + environment.getProperty("testEnvironment"));
        int secondsToWait = Integer.valueOf(environment.getProperty("secondsToWait"));
        String url = environment.getProperty("url");
        boolean screenshotBeforeClick = Boolean.valueOf(environment.getProperty("screenshotBeforeClick"));
        boolean screenshotOnScenarioCompletion = Boolean.valueOf(environment.getProperty("screenshotOnScenarioCompletion"));
        return new TestEnvironmentConfig(secondsToWait, url, screenshotBeforeClick, screenshotOnScenarioCompletion);
    }

    @Bean
    public AppConfig appConfig() {
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

    @Bean
    @Scope(value = "cucumber-glue")
    public WebDriver webDriver() throws MalformedURLException {
        WebDriver webDriver = null;
        BrowserType browserType = BrowserType.valueOf(environment.getProperty("browserType"));
        boolean runRemote = Boolean.valueOf(environment.getProperty("runRemote"));
        logger.info("Browser type->" + browserType.name() + " Run Remote->" + runRemote);
        DesiredCapabilities desiredCapabilities;
        switch (browserType) {
            case chrome:
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("download.default_directory", FileDownloadUtils.DOWNLOAD_PATH.toString());

                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setExperimentalOption("prefs", prefs);

                desiredCapabilities = DesiredCapabilities.chrome();
                desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                if (runRemote) {
                    webDriver = new RemoteWebDriver(new URL(environment.getProperty("seleniumGridUrl")), desiredCapabilities);
                } else {
                    System.setProperty("webdriver.chrome.driver", environment.getProperty("chromeDriverPath"));
                    webDriver = new ChromeDriver(desiredCapabilities);
                }
                break;
            case firefox:
                FirefoxProfile firefoxProfile = new FirefoxProfile();
                firefoxProfile.setPreference("browser.download.folderList", 2);
                firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
                firefoxProfile.setPreference("browser.download.dir", FileDownloadUtils.DOWNLOAD_PATH.toString());
                firefoxProfile.setPreference("browser.helperApps.neverAsk.openFile",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
                firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
                firefoxProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
                firefoxProfile.setPreference("browser.download.manager.alertOnEXEOpen", false);
                firefoxProfile.setPreference("browser.download.manager.focusWhenStarting", false);
                firefoxProfile.setPreference("browser.download.manager.useWindow", false);
                firefoxProfile.setPreference("browser.download.manager.showAlertOnComplete", false);
                firefoxProfile.setPreference("browser.download.manager.closeWhenDone", false);
                desiredCapabilities = DesiredCapabilities.firefox();
                desiredCapabilities.setCapability(FirefoxDriver.PROFILE, firefoxProfile);

                if (runRemote) {
                    webDriver = new RemoteWebDriver(new URL(environment.getProperty("seleniumGridUrl")), desiredCapabilities);
                } else {
                    System.setProperty("webdriver.gecko.driver", environment.getProperty("geckoDriverPath"));
                    webDriver = new FirefoxDriver(desiredCapabilities);
                }
                break;
            case phantomjs:
                desiredCapabilities = DesiredCapabilities.phantomjs();
                desiredCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, environment.getProperty("phantomjsDriverPath"));
                desiredCapabilities.setCapability("acceptSslCerts", true);
                desiredCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[]{"--web-security=no", "--ignore-ssl-errors=yes", "--webdriver-loglevel=NONE"});
                if (runRemote) {
                    webDriver = new RemoteWebDriver(new URL(environment.getProperty("seleniumGridUrl")), desiredCapabilities);
                } else {
                    webDriver = new PhantomJSDriver(desiredCapabilities);
                }
                break;
            case edge:
                desiredCapabilities = DesiredCapabilities.edge();
                if (runRemote) {
                    webDriver = new RemoteWebDriver(new URL(environment.getProperty("seleniumGridUrl")), desiredCapabilities);
                } else {
                    System.setProperty("webdriver.edge.driver", environment.getProperty("edgeDriverPath"));
                    webDriver = new EdgeDriver(desiredCapabilities);
                }
                break;
            case internetExplorer:
                desiredCapabilities = DesiredCapabilities.internetExplorer();
                if (runRemote) {
                    webDriver = new RemoteWebDriver(new URL(environment.getProperty("seleniumGridUrl")), desiredCapabilities);
                } else {
                    System.setProperty("webdriver.ie.driver", environment.getProperty("internetExplorerDriverPath"));
                    webDriver = new InternetExplorerDriver(desiredCapabilities);
                }
                break;
            default:
                break;
        }
        return webDriver;
    }

    @Bean
    @Scope(value = "cucumber-glue")
    public TestInstance testInstance(TestEnvironmentConfig testEnvironmentConfig,
                                     AppConfig appConfig,
                                     WebDriver webDriver) {
        return new TestInstance(testEnvironmentConfig, appConfig, webDriver);
    }
}
