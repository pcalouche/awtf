package com.pcalouche.awtf_reporting;

import java.util.SortedMap;
import java.util.TreeMap;

public class Reporter {
	private static SortedMap<String, ReporterStep> stepsMap;
	private static SortedMap<String, TagInstance> tagsMap;

	public Reporter() {
		stepsMap = new TreeMap<String, ReporterStep>();
		tagsMap = new TreeMap<String, TagInstance>();
	}

	/**
	 * @return the stepsMap
	 */
	public static SortedMap<String, ReporterStep> getStepsMap() {
		return stepsMap;
	}

	/**
	 * @param stepsMap
	 *            the stepsMap to set
	 */
	public static void setStepsMap(SortedMap<String, ReporterStep> stepsMap) {
		Reporter.stepsMap = stepsMap;
	}

	/**
	 * @return the tagsMap
	 */
	public static SortedMap<String, TagInstance> getTagsMap() {
		return tagsMap;
	}

	/**
	 * @param tagsMap
	 *            the tagsMap to set
	 */
	public static void setTagsMap(SortedMap<String, TagInstance> tagsMap) {
		Reporter.tagsMap = tagsMap;
	}

	public static void track(String step, String usage, String example) {
		if (!Reporter.getStepsMap().containsKey(step)) {
			Reporter.getStepsMap().put(step, new ReporterStep(step, usage, example));
		} else {
			Reporter.getStepsMap().get(step).incrementCount();
		}
	}

	public static void printData() {
		for (ReporterStep reporterStep : Reporter.getStepsMap().values()) {
			System.out.println(reporterStep.getStep());
			System.out.println(reporterStep.getUsage());
			System.out.println(reporterStep.getExample());
			System.out.println("Count: " + reporterStep.getCount() + "\n");
		}
		for (String key : Reporter.getTagsMap().keySet()) {
			System.out.println(key + " " + Reporter.getTagsMap().get(key).getCount());
		}
	}
}
