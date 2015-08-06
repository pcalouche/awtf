package com.pcalouche.awtf_app_example;

import org.apache.logging.log4j.LogManager;

import com.pcalouche.awtf_core.TestEnvironmentConfig;
import com.pcalouche.awtf_core.TestInstance;
import com.pcalouche.awtf_core.YamlHelper;
import com.pcalouche.awtf_core.util.appConfig.AppConfig;

/**
 * This class demonstrates how TestInstance can be extended for your needs
 *
 * @author Philip Calouche
 *
 */
public class MyAppTestInstance extends TestInstance {
	// Objects like AppConfig or TestEnvironmentConfig could be extended with additional fields as needed or entirely new objects can be added here to suite your applciation's configuration needs.
	protected static MyAppTestEnvironmentConfig myAppTestEnvironmentConfig;

	public MyAppTestInstance() {
		logger = LogManager.getLogger();
	}

	@Override
	protected void setupTestEnvironment() {
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

		// Set this to keep the core framework happy
		testEnvironmentConfig = (TestEnvironmentConfig) YamlHelper.loadFromInputStream(String.format("/yaml/testEnvironments/MyAppTestEnvironmentConfig.%s.yml", testEnvironment));
		// Set this to get access to our extended version for use in our app's step definitions
		myAppTestEnvironmentConfig = (MyAppTestEnvironmentConfig) testEnvironmentConfig;
		appConfig = (AppConfig) YamlHelper.loadFromInputStream("/yaml/appConfig.yml");
	}

	@Override
	protected void loadApplicationConfig() {
		// Just calling super here, but this can be completely changed to do whatever you need
		super.loadApplicationConfig();
	}

	@Override
	protected void setupWebDriver() {
		// Just calling super here, but this can be completely changed to do whatever you need
		super.setupWebDriver();
	}

	/**
	 * @return the myAppTestEnvironmentConfig
	 */
	public static MyAppTestEnvironmentConfig getMyAppTestEnvironmentConfig() {
		return myAppTestEnvironmentConfig;
	}
}
