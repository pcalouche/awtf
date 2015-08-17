package com.pcalouche.awtf_reporting;

import java.util.HashMap;
import java.util.Map;

public class Reporter {
	// private static Map<String, ReporterStep> stepsMap = new HashMap<String, ReporterStep>();
	// private static Map<String, TagInstance> tagsMap = new HashMap<String, TagInstance>();

	private static Map<String, ReporterStep> stepsMap;
	private static Map<String, TagInstance> tagsMap;

	public Reporter() {
		stepsMap = new HashMap<String, ReporterStep>();
		tagsMap = new HashMap<String, TagInstance>();
	}

	/**
	 * @return the stepsMap
	 */
	public static Map<String, ReporterStep> getStepsMap() {
		return stepsMap;
	}

	/**
	 * @param stepsMap
	 *            the stepsMap to set
	 */
	public static void setStepsMap(Map<String, ReporterStep> stepsMap) {
		Reporter.stepsMap = stepsMap;
	}

	/**
	 * @return the tagsMap
	 */
	public static Map<String, TagInstance> getTagsMap() {
		return tagsMap;
	}

	/**
	 * @param tagsMap
	 *            the tagsMap to set
	 */
	public static void setTagsMap(Map<String, TagInstance> tagsMap) {
		Reporter.tagsMap = tagsMap;
	}

	public static void track(String step) {
		if (!Reporter.getStepsMap().containsKey(step)) {
			Reporter.getStepsMap().put(step, new ReporterStep(step, "", ""));
		}
		Reporter.getStepsMap().get(step).incrementCount();
	}
}
