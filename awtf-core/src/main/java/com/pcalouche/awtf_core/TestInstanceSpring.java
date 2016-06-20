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
    protected static TestEnvironmentConfig testEnvironmentConfig;
    protected static CoreStepHandlerSpring coreStepHandler;
    protected static StepsUtilSpring stepsUtil;
    protected static AppConfig appConfig;
    protected static WebDriver webDriver;
    protected static WebDriverWait webDriverWait;
    protected static JavascriptExecutor jsExecutor;
    protected static Scenario currentScenario;
    protected static StopWatch stopWatch = new StopWatch();
    /*
     * Used to store temporary values that subsequent steps may later need. A good use case is storing a confirmation ID that comes from a form submission, and then retrieving it later to verify that
     * is appears on the screen to the user.
     */
    private static Map<String, String> tempMap = new HashMap<>();
    protected Logger logger = LogManager.getLogger();

    @Autowired
    public TestInstanceSpring(CoreStepHandlerSpring coreStepHandler, StepsUtilSpring stepsUtil) {
        this.coreStepHandler = coreStepHandler;
        this.stepsUtil = stepsUtil;

        // If test instance is extend this can be overridden to allow for custom loading of the test environment setup
//        this.loadTestEnvironmentConfig();
        // If test instance is extend this can be overridden to allow for custom loading of the application config
//        this.loadApplicationConfig();

		/*
         * Create an instance of the class that will handle the core steps. This defaults to com.pcalouche.awtf_core.CoreStepHandler. This class can be extended with your own version if you need to
		 * override or add to what is in CoreStepHandler
		 */
//        if (StringUtils.isEmpty(testEnvironmentConfig.getCoreStepHandlerClass())) {
//            coreStepHandler = new CoreStepHandler();
//        } else {
//            try {
//                Class<?> c = Class.forName(testEnvironmentConfig.getCoreStepHandlerClass());
//                coreStepHandler = (CoreStepHandler) c.newInstance();
//                c = Class.forName(testEnvironmentConfig.getStepsUtilClass());
//                stepsUtil = (StepsUtil) c.newInstance();
//            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
//                e.printStackTrace();
//                System.exit(1);
//            }
//        }
        // If test instance is extended this can be overridden to allow for custom browser setup
//        this.setupWebDriver();
    }

    /**
     * @return the testEnvironmentConfig
     */
    public static TestEnvironmentConfig getTestEnvironmentConfig() {
        return testEnvironmentConfig;
    }

    /**
     * @return the coreStepHandler
     */
    public static CoreStepHandlerSpring getCoreStepHandler() {
        return coreStepHandler;
    }

    /**
     * @return the stepsUtils
     */
    public static StepsUtilSpring getStepsUtil() {
        return stepsUtil;
    }

    /**
     * @return the appConfig
     */
    public static AppConfig getAppConfig() {
        return appConfig;
    }

    /**
     * @return the webDriver
     */
    public static WebDriver getWebDriver() {
        return webDriver;
    }

    /**
     * @return the webDriverWait
     */
    public static WebDriverWait getWebDriverWait() {
        return webDriverWait;
    }

    /**
     * @return the jsExecutor
     */
    public static JavascriptExecutor getJsExecutor() {
        return jsExecutor;
    }

    /**
     * @return the currentScenario
     */
    public static Scenario getCurrentScenario() {
        return currentScenario;
    }

    /**
     * @param currentScenario the currentScenario to set
     */
    public static void setCurrentScenario(Scenario currentScenario) {
        TestInstanceSpring.currentScenario = currentScenario;
    }

    /**
     * @return the stopWatch
     */
    public static StopWatch getStopWatch() {
        return stopWatch;
    }

    /**
     * @return the tempMap
     */
    public static Map<String, String> getTempMap() {
        return tempMap;
    }

    protected void loadTestEnvironmentConfig() {
        String testEnvironment;
        if (System.getProperty("testEnvironment") != null) {
            testEnvironment = System.getProperty("testEnvironment");
            logger.info("Test environment received from System Property as: " + testEnvironment);
        } else if (System.getenv("testEnvironment") != null) {
            testEnvironment = System.getenv("testEnvironment");
            logger.info("Test environment received from Enviroment Variable as: " + testEnvironment);
        } else {
            logger.info("Test enviroment not specified in Command Line or Enviroment Variable, defaulting to localhost test environment");
            testEnvironment = "localhost";
        }

        testEnvironmentConfig = (TestEnvironmentConfig) YamlHelper.loadFromInputStream(String.format("/yaml/testEnvironments/TestEnvironmentConfig.%s.yml", testEnvironment));
    }

    protected void loadApplicationConfig() {
        appConfig = (AppConfig) YamlHelper.loadFromInputStream("/yaml/appConfig.yml");
    }

    protected void setupWebDriver() {
        // Set common desired capabilities for our browsers
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setJavascriptEnabled(true);
        desiredCapabilities.setCapability("takesScreenshot", true);
        desiredCapabilities.setCapability("acceptSslCerts", true);
        switch (testEnvironmentConfig.getBrowser()) {
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
