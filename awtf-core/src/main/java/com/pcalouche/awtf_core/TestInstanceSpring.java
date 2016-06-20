package com.pcalouche.awtf_core;

import com.pcalouche.awtf_core.util.appConfig.AppConfig;
import cucumber.api.Scenario;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * This class creates a test instance to encapsulate all that is needed to run a test.
 *
 * @author Philip Calouche
 */
@Component("testInstance")
public class TestInstanceSpring {
    /*
     * Used to store temporary values that subsequent steps may later need. A good use case is storing a confirmation ID that comes from a form submission, and then retrieving it later to verify that
     * is appears on the screen to the user.
     */
    private static Map<String, String> tempMap = new HashMap<>();
    private final TestEnvironmentConfigSpring testEnvironmentConfig;
    protected AppConfig appConfig;
    protected WebDriver webDriver;
    protected WebDriverWait webDriverWait;
    protected JavascriptExecutor jsExecutor;
    protected Scenario currentScenario;
    protected StopWatch stopWatch = new StopWatch();
    protected Logger logger = LogManager.getLogger();

    @Autowired
    public TestInstanceSpring(TestEnvironmentConfigSpring testEnvironmentConfig) {
        this.testEnvironmentConfig = testEnvironmentConfig;
        logger.info("testEnvironmentConfig browserType->" + this.testEnvironmentConfig.getBrowserType());
        // If test instance is extend this can be overridden to allow for custom loading of the application config
        this.loadApplicationConfig();
        // If test instance is extended this can be overridden to allow for custom browser setup
        this.setupWebDriver();
        logger.info("done with TestInstanceSpring constructor");
    }

    /**
     * @return the tempMap
     */
    public static Map<String, String> getTempMap() {
        return tempMap;
    }

    /**
     * @return the appConfig
     */
    public AppConfig getAppConfig() {
        return appConfig;
    }

    /**
     * @return the webDriver
     */
    public WebDriver getWebDriver() {
        return webDriver;
    }

    /**
     * @return the webDriverWait
     */
    public WebDriverWait getWebDriverWait() {
        return webDriverWait;
    }

    /**
     * @return the jsExecutor
     */
    public JavascriptExecutor getJsExecutor() {
        return jsExecutor;
    }

    /**
     * @return the currentScenario
     */
    public Scenario getCurrentScenario() {
        return currentScenario;
    }

    /**
     * @param currentScenario the currentScenario to set
     */
    public void setCurrentScenario(Scenario currentScenario) {
        this.currentScenario = currentScenario;
    }

    /**
     * @return the stopWatch
     */
    public StopWatch getStopWatch() {
        return stopWatch;
    }

    /**
     * @return the testEnvironmentConfigs
     */
    public TestEnvironmentConfigSpring getTestEnvironmentConfig() {
        return testEnvironmentConfig;
    }

    protected void loadApplicationConfig() {
        this.appConfig = (AppConfig) YamlHelper.loadFromInputStream("/yaml/appConfig.yml");
    }

    protected void setupWebDriver() {
        // Set common desired capabilities for our browsers
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setJavascriptEnabled(true);
        desiredCapabilities.setCapability("takesScreenshot", true);
        desiredCapabilities.setCapability("acceptSslCerts", true);
        switch (testEnvironmentConfig.getBrowserType()) {
            case phantomJS:
                desiredCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[]{"--web-security=no", "--ignore-ssl-errors=yes", "--webdriver-loglevel=NONE"});
                desiredCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "C:\\Users\\pcalouch\\Projects\\webdrivers\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");
                webDriver = new PhantomJSDriver(desiredCapabilities);
                break;
            case firefox:
                webDriver = new FirefoxDriver(desiredCapabilities);
                break;
            case internetExplorer:
                webDriver = new InternetExplorerDriver(desiredCapabilities);
                break;
            case edge:
                webDriver = new EdgeDriver(desiredCapabilities);
                break;
            case chrome:
                webDriver = new ChromeDriver(desiredCapabilities);
                break;
            case safari:
                webDriver = new SafariDriver(desiredCapabilities);
                break;
            default:
                break;
        }
        // Set window size and position
        webDriver.manage().window().setSize(new Dimension(1280, 1024));
        webDriver.manage().window().setPosition(new Point(0, 0));
        // Set the web driver wait
        webDriverWait = new WebDriverWait(webDriver, testEnvironmentConfig.getSecondsToWait());
        // Set the JavaScript Executor
        jsExecutor = (JavascriptExecutor) webDriver;
    }
}
