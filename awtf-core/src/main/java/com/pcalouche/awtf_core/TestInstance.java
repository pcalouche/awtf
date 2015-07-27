package com.pcalouche.awtf_core;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.pcalouche.awtf_core.util.appConfig.AppConfig;
import com.pcalouche.awtf_core.util.enums.WaitTag;

import cucumber.api.Scenario;

/**
 * This class creates a test instance to encapsulate all that is needed to run a test.
 *
 * @author Philip Calouche
 *
 */
public class TestInstance {
	protected Logger logger = LogManager.getLogger();
	protected static TestEnvironmentConfig testEnvironmentConfig;
	protected static CoreStepHandler coreStepHandler;
	protected static StepsUtil stepsUtil;
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
	private static Map<String, String> tempMap = new HashMap<String, String>();

	public TestInstance() {
		String testEnvironment;
		if (System.getProperty("testEnvironment") != null) {
			testEnvironment = System.getProperty("testEnvironment");
			logger.info("Test environment received from Command Line as: " + testEnvironment);
		} else if (System.getenv("testEnvironment") != null) {
			testEnvironment = System.getenv("testEnvironment");
			logger.info("Test environment received from Enviroment Variable as: " + testEnvironment);
		} else {
			logger.info("Test enviroment not specified in Command Line or Enviroment Variable, defaulting to localhost test environment");
			testEnvironment = "localhost";
		}

		testEnvironmentConfig = (TestEnvironmentConfig) YamlHelper.loadFromInputStream(String.format("/yaml/testEnvironments/TestEnvironmentConfig.%s.yml", testEnvironment));
		appConfig = (AppConfig) YamlHelper.loadFromInputStream("/yaml/appConfig.yml");
		/*
		 * Create an instance of the class that will handle the core steps. This defaults to com.pcalouche.awtf_core.CoreStepHandler. This class can be extended with your own version if you need to
		 * override or add to what is in CoreStepHandler
		 */
		if (StringUtils.isEmpty(testEnvironmentConfig.getCoreStepHandlerClass())) {
			coreStepHandler = new CoreStepHandler();
		} else {
			try {
				Class<?> c = Class.forName(testEnvironmentConfig.getCoreStepHandlerClass());
				coreStepHandler = (CoreStepHandler) c.newInstance();
				c = Class.forName(testEnvironmentConfig.getStepsUtilClass());
				stepsUtil = (StepsUtil) c.newInstance();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				System.exit(1);
			} catch (InstantiationException e) {
				e.printStackTrace();
				System.exit(1);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		// If test instance is extended this can be overridden easily to allow for customer browser setup
		this.setupWebDriver();
	}

	protected void setupWebDriver() {
		// Set common desired capabilities for our browsers
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setJavascriptEnabled(true);
		desiredCapabilities.setCapability("takesScreenshot", true);
		desiredCapabilities.setCapability("acceptSslCerts", true);
		switch (testEnvironmentConfig.getBrowser()) {
		case phantomJS:
			desiredCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[] { "--web-security=no", "--ignore-ssl-errors=yes", "--webdriver-loglevel=NONE" });
			webDriver = new PhantomJSDriver(desiredCapabilities);
			break;
		case firefox:
			webDriver = new FirefoxDriver(desiredCapabilities);
			break;
		case internetExplorer:
			webDriver = new InternetExplorerDriver(desiredCapabilities);
			break;
		case chrome:
			webDriver = new ChromeDriver(desiredCapabilities);
			break;
		case safari:
			break;
		default:
			break;
		}
		// Set window size and position
		webDriver.manage().window().setSize(new Dimension(1280, 1024));
		webDriver.manage().window().setPosition(new Point(0, 0));
		// Set the JavaScript Executor
		jsExecutor = (JavascriptExecutor) webDriver;
	}

	public void setup(Scenario scenario) {
		try {
			// Set seconds to wait. Will use what is in the default Test Environment Config if wait tag is not found
			int secondsToWait = testEnvironmentConfig.getSecondsToWait();
			boolean displayWaitTag = false;
			for (WaitTag waitTag : WaitTag.values()) {
				if (scenario.getSourceTagNames().contains(waitTag.getTagName())) {
					displayWaitTag = true;
					secondsToWait = waitTag.getSecondsToWait();
					break;
				}
			}
			webDriverWait = new WebDriverWait(webDriver, secondsToWait);
			stopWatch.reset();
			stopWatch.start();
			logger.info(String.format("Starting Scenario: \"%s\"", scenario.getName()));
			if (displayWaitTag) {
				logger.info(String.format("Wait Tag was set to: %d seconds based on given tag.", secondsToWait));
			}
			// Store a reference to the current scenario, so it can be accessed to do things like embed additional screenshots into a scenario.
			currentScenario = scenario;
		} catch (Exception e) {
			logger.error("Failed to initialize scenario", e);
			teardown(scenario);
		}
	}

	public void teardown(Scenario scenario) {
		try {
			stopWatch.stop();
			logger.debug(String.format("Scenario: \"%s\" completed in %.3f seconds", scenario.getName(), stopWatch.getTime() / 1000.00));
			scenario.write(String.format("Completed in %.3f seconds.", stopWatch.getTime() / 1000.00));
			if (TestInstance.testEnvironmentConfig.isScreenshotOnScenarioCompletion() || scenario.isFailed()) {
				scenario.embed(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES), "image/png");
			}
		} catch (Exception e) {
			logger.error("Failed to teardown scenario", e);
		} finally {
			webDriver.quit();
		}
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
	public static CoreStepHandler getCoreStepHandler() {
		return coreStepHandler;
	}

	/**
	 * @return the stepsUtils
	 */
	public static StepsUtil getStepsUtil() {
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
}
