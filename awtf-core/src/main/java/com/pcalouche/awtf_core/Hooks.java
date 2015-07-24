package com.pcalouche.awtf_core;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

/**
 * Class that contains Cucumber hooks since classes with hooks or step definitions can't be subclassed.
 *
 * @author Philip Calouche
 *
 */
public class Hooks {
	// This instance will be used by the current scenario
	TestInstance testInstance = new TestInstance();

	@Before
	public void setup(Scenario scenario) {
		testInstance.setup(scenario);
	}

	@After
	public void teardown(Scenario scenario) {
		testInstance.teardown(scenario);
	}
}
