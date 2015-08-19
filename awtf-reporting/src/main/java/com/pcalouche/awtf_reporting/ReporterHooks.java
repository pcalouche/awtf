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
	@Before
	public void initialize(Scenario scenario) {
		if (Reporter.getStepsMap() == null) {
			new Reporter();
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					Reporter.printData();
				}
			});
		}
		for (String tag : scenario.getSourceTagNames()) {
			if (!Reporter.getTagsMap().containsKey(tag)) {
				Reporter.getTagsMap().put(tag, new TagInstance());
			}
			Reporter.getTagsMap().get(tag).incrementCount();
		}
	}

	@After
	public void teardown(Scenario scenario) {
		// if (Reporter.getStepsMap().containsKey("^I go to the demo page$")) {
		// }
		// for (ReporterStep reporterStep : Repor)
	}
}
