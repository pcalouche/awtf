package com.pcalouche.awtf_core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcalouche.awtf_core.util.appConfig.AppConfig;
import cucumber.api.Scenario;
import org.apache.commons.lang3.time.StopWatch;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * This class creates a test instance to encapsulate all that is needed to run a test.
 *
 * @author Philip Calouche
 */
public class TestInstance {
    private static final Logger logger = LoggerFactory.getLogger(TestInstance.class);
    protected final TestEnvironmentConfig testEnvironmentConfig;
    protected AppConfig appConfig;
    protected WebDriver webDriver;
    protected WebDriverWait webDriverWait;
    protected JavascriptExecutor jsExecutor;
    protected Scenario currentScenario;
    protected StopWatch stopWatch = new StopWatch();
    /*
     * Used to store temporary values that subsequent steps may later need. A good use case is storing a confirmation ID that comes from a form submission, and then retrieving it later to verify that
     * is appears on the screen to the user.
     */
    protected Map<String, String> tempMap = new HashMap<>();

    public TestInstance(TestEnvironmentConfig testEnvironmentConfig) {
        this.testEnvironmentConfig = testEnvironmentConfig;
        // If test instance is extend this can be overridden to allow for custom loading of the application config
        this.loadApplicationConfig();
        // If test instance is extended this can be overridden to allow for custom browser setup
        this.setupWebDriver();
        logger.info("TestInstance constructor, browserType->" + this.testEnvironmentConfig.getBrowserType());
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

    protected void loadApplicationConfig() {
        try (InputStream inputStream = new ClassPathResource("appConfigs/coreAppConfig.json").getInputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
            this.appConfig = objectMapper.readValue(inputStream, AppConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void setupWebDriver() {
        // Set common desired capabilities for our browsers
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setJavascriptEnabled(true);
        desiredCapabilities.setCapability("takesScreenshot", true);
        desiredCapabilities.setCapability("acceptSslCerts", true);
        logger.info("in setupWebDriver->" + testEnvironmentConfig.getBrowserType());
        switch (testEnvironmentConfig.getBrowserType()) {
            case phantomJS:
                desiredCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[]{"--web-security=no", "--ignore-ssl-errors=yes", "--webdriver-loglevel=NONE"});
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
