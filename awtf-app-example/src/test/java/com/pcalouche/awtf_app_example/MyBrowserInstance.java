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
}
