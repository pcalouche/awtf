package com.pcalouche.awtf_app_example;

import com.pcalouche.awtf_core.BrowserInstance;

import cucumber.api.Scenario;

/**
 * This class demonstrates how BrowserInstance can be extended for your needs
 *
 * @author Philip Calouche
 *
 */
public class MyBrowserInstance extends BrowserInstance {
	// Objects like AppConfig or TestEnvironmentConfig could be extended with additional fields as needed or entirely new objects can be added here to suite your applciation's configuration needs.
	public MyBrowserInstance() {
		super();
	}

	@Override
	public void setup(Scenario scenario) {
		// Just calling super here, but this can be completely changed to do whatever you need
		super.setup(scenario);
	}

	@Override
	public void teardown(Scenario scenario) {
		// Just calling super here, but this can be completely changed to do whatever you need
		super.teardown(scenario);
	}

	@Override
	protected void setupWebDriver() {
		// Just calling super here, but this can be completely changed to do whatever you need
		super.setupWebDriver();
		logger.debug("Would call some additional code here");
	}
}
