package com.pcalouche.awtf_core;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.pcalouche.awtf_core.util.appConfig.AppConfig;
import com.pcalouche.awtf_core.util.enums.WaitTag;

import cucumber.api.Scenario;

/**
 * This class creates a browser instance that all step definitions can leverage to interact with a page.
 *
 * @author Philip Calouche
 *
 */
public class BrowserInstance {
	protected static final Logger logger = LogManager.getLogger();
	protected static TestEnvironmentConfig testEnvironmentConfig;
	protected static ResourceBundle messageBundle = ResourceBundle.getBundle("messages_en");
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

	public BrowserInstance() {
		String testEnvironment;
		if (System.getProperty("testEnvironment") != null) {
			testEnvironment = System.getProperty("testEnvironment");
			logger.info("Test Environment received from Command Line as: " + testEnvironment);
		} else if (System.getenv("testEnvironment") != null) {
			testEnvironment = System.getenv("testEnvironment");
			logger.info("Test Environment received from Enviroment Variable as: " + testEnvironment);
		} else {
			logger.info("Test Environment not specified in Command Line or Enviroment Varaible.  Defaulting to localhost testEnvironment");
			testEnvironment = "localhost";
		}

		testEnvironmentConfig = (TestEnvironmentConfig) YamlHelper.loadFromInputStream(String.format("/yaml/testEnvironments/TestEnvironmentConfig.%s.yml", testEnvironment));
		appConfig = (AppConfig) YamlHelper.loadFromInputStream("/yaml/appConfig.yml");

		switch (testEnvironmentConfig.getBrowser()) {
		case phantomJS:
			DesiredCapabilities phantomCapabilities = new DesiredCapabilities();
			phantomCapabilities.setJavascriptEnabled(true);
			phantomCapabilities.setCapability("takesScreenshot", true);
			phantomCapabilities.setCapability("acceptSslCerts", true);
			phantomCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[] { "--web-security=no", "--ignore-ssl-errors=yes", "--webdriver-loglevel=NONE" });
			webDriver = new PhantomJSDriver(phantomCapabilities);
			break;
		case firefox:
			DesiredCapabilities ffCapabilities = new DesiredCapabilities();
			ffCapabilities.setJavascriptEnabled(true);
			ffCapabilities.setCapability("takesScreenshot", true);
			ffCapabilities.setCapability("acceptSslCerts", true);
			webDriver = new FirefoxDriver(ffCapabilities);
			break;
		case internetExplorer:
			// DesiredCapabilities ieCapabilities = new DesiredCapabilities();
			// ieCapabilities.setJavascriptEnabled(true);
			// ieCapabilities.setCapability("takesScreenshot", true);
			// ieCapabilities.setCapability("acceptSslCerts", true);
			// webDriver = new InternetExplorerDriver(ieCapabilities);
			break;
		case chrome:
			break;
		case safari:
			break;
		default:
			break;
		}
		// Set window size. Sometimes phantom needs this because it has issues interacting with certain elements if it its window size is set to the default (400, 300)
		// TODO play with screenshot settings
		webDriver.manage().window().setSize(new Dimension(1280, 1024));
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
			jsExecutor = (JavascriptExecutor) webDriver;
			stopWatch.start();
			logger.info(String.format("Starting Scenario: \"%s\"", scenario.getName()));
			if (displayWaitTag) {
				logger.info(String.format("Wait Tag was set to: %d seconds", secondsToWait));
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
			stopWatch.reset();
			logger.debug(String.format("Scenario: \"%s\" completed in %.3f seconds", scenario.getName(), stopWatch.getTime() / 1000.00));
			scenario.write(String.format("Completed in %.3f seconds.", stopWatch.getTime() / 1000.00));
			if (BrowserInstance.testEnvironmentConfig.isScreenshotOnScenarioCompletion() || scenario.isFailed()) {
				scenario.embed(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES), "image/png");
			}
		} catch (Exception e) {
			logger.error("Failed to teardown scenario", e);
		} finally {
			webDriver.quit();
		}
	}

	public static TestEnvironmentConfig getTestEnvironmentConfig() {
		return testEnvironmentConfig;
	}

	public static ResourceBundle getMessageBundle() {
		return messageBundle;
	}

	public static AppConfig getAppConfig() {
		return appConfig;
	}

	public static WebDriver getWebDriver() {
		return webDriver;
	}

	public static WebDriverWait getWebDriverWait() {
		return webDriverWait;
	}

	public static JavascriptExecutor getJsExecutor() {
		return jsExecutor;
	}

	public static Scenario getCurrentScenario() {
		return currentScenario;
	}

	public static StopWatch getStopWatch() {
		return stopWatch;
	}

	public static Map<String, String> getTempMap() {
		return tempMap;
	}

	public static void takeAScreenShot() {
		BrowserInstance.getCurrentScenario().embed(((TakesScreenshot) BrowserInstance.getWebDriver()).getScreenshotAs(OutputType.BYTES), "image/png");
	}
}
