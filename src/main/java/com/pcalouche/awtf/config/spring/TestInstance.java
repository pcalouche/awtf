package com.pcalouche.awtf.config.spring;

import com.pcalouche.awtf.util.appConfig.AppConfig;
import cucumber.api.Scenario;
import org.apache.commons.lang3.time.StopWatch;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * This class creates a test instance to encapsulate all that is needed to run a test.
 *
 * @author Philip Calouche
 */
public class TestInstance {
    private static final Logger logger = LoggerFactory.getLogger(TestInstance.class);
    private final TestEnvironmentConfig testEnvironmentConfig;
    private final StopWatch stopWatch = new StopWatch();
    private final AppConfig appConfig;
    private final WebDriver webDriver;
    private final WebDriverWait webDriverWait;
    private final JavascriptExecutor jsExecutor;
    private Scenario currentScenario;
    /*
     * Used to store temporary values that subsequent steps may later need. A good use case is storing a confirmation ID that comes from a form submission, and then retrieving it later to verify that
     * is appears on the screen to the user.
     */
    private Map<String, String> tempMap = new HashMap<>();

    public TestInstance(TestEnvironmentConfig testEnvironmentConfig,
                        AppConfig appConfig,
                        WebDriver webDriver) {
        logger.info("creating new test instance");
        this.testEnvironmentConfig = testEnvironmentConfig;
        this.appConfig = appConfig;
        // If test instance is extended this can be overridden to allow for custom browser setup
        this.webDriver = webDriver;
        // Set window size and position
        webDriver.manage().window().setSize(new Dimension(1280, 1024));
        webDriver.manage().window().setPosition(new Point(0, 0));
        // Set the web driver wait
        webDriverWait = new WebDriverWait(webDriver, testEnvironmentConfig.getSecondsToWait());
        // Set the JavaScript Executor
        jsExecutor = (JavascriptExecutor) webDriver;
    }

    public TestEnvironmentConfig getTestEnvironmentConfig() {
        return testEnvironmentConfig;
    }

    public AppConfig getAppConfig() {
        return appConfig;
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public WebDriverWait getWebDriverWait() {
        return webDriverWait;
    }

    public JavascriptExecutor getJsExecutor() {
        return jsExecutor;
    }

    public Scenario getCurrentScenario() {
        return currentScenario;
    }

    public void setCurrentScenario(Scenario currentScenario) {
        this.currentScenario = currentScenario;
    }

    public StopWatch getStopWatch() {
        return stopWatch;
    }

    public Map<String, String> getTempMap() {
        return tempMap;
    }
}
