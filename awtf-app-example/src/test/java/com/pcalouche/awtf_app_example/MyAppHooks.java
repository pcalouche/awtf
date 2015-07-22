package com.pcalouche.awtf_app_example;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

/**
 * Class that contains Cucumber hooks since classes with hooks or step definitions can't be subclassed.
 *
 * @author Philip Calouche
 *
 */
public class MyAppHooks {
	// This instance will be used by the current scenario
	MyAppTestInstance myAppTestInstance = new MyAppTestInstance();

	@Before
	public void setup(Scenario scenario) {
		myAppTestInstance.setup(scenario);
	}

	@After
	public void teardown(Scenario scenario) {
		myAppTestInstance.teardown(scenario);
	}
}
