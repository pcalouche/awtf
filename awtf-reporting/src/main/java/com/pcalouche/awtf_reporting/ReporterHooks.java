package com.pcalouche.awtf_reporting;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

/**
 * Hooks class for AWTF reporter
 *
 * @author Philip Calouche
 *
 */
public class ReporterHooks {
	// This instance will be used by the current scenario
	// Reporter reporter = new Reporter();

	@Before
	public void initialize(Scenario scenario) {
		if (Reporter.getStepsMap() == null) {
			new Reporter();
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					for (String key : Reporter.getTagsMap().keySet()) {
						System.out.println(key);
					}
					// Reporter.getTagsMap().en
				}
			});
		}
		System.out.println("BEFORE " + Reporter.getStepsMap().size());
		// reporter.setup(scenario);
		for (String tag : scenario.getSourceTagNames()) {
			if (!Reporter.getTagsMap().containsKey(tag)) {
				Reporter.getTagsMap().put(tag, new TagInstance());
			}
			Reporter.getTagsMap().get(tag).incrementCount();
		}
	}

	@After
	public void teardown(Scenario scenario) {
		System.out.println("AFTER " + Reporter.getStepsMap().size());
		// reporter.teardown(scenario);
		if (Reporter.getStepsMap().containsKey("^I go to the demo page$")) {
			System.out.println("AFTER " + Reporter.getStepsMap().get("^I go to the demo page$").getCount());
		}
	}
}
