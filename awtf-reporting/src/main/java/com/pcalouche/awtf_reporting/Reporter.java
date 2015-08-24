package com.pcalouche.awtf_reporting;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang3.StringEscapeUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

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

	public static void track(String step, String usage, String example, String javaClass) {
		if (!Reporter.getStepsMap().containsKey(step)) {
			Reporter.getStepsMap().put(step, new ReporterStep(step, usage, example, javaClass));
		} else {
			Reporter.getStepsMap().get(step).incrementCount();
		}
	}

	public static void updateTagData(Collection<String> sourceTagNames) {
		for (String tagName : sourceTagNames) {
			if (!Reporter.getTagsMap().containsKey(tagName)) {
				Reporter.getTagsMap().put(tagName, new TagInstance(tagName));
			} else {
				Reporter.getTagsMap().get(tagName).incrementCount();
			}
		}
	}

	public static void handleShutdown() {
		// for (ReporterStep reporterStep : Reporter.getStepsMap().values()) {
		// System.out.println(reporterStep.getStep());
		// System.out.println(reporterStep.getUsage());
		// System.out.println(reporterStep.getExample());
		// System.out.println(reporterStep.getJavaClass());
		// System.out.println("Count: " + reporterStep.getCount() + "\n");
		// }
		// for (String key : Reporter.getTagsMap().keySet()) {
		// System.out.println(key + " " + Reporter.getTagsMap().get(key).getCount());
		// }
		ObjectMapper mapper = new ObjectMapper();
		try {
			Map<String, Object> awtfReportData = new HashMap<String, Object>();
			awtfReportData.put("stepData", Reporter.getStepsMap().values());
			awtfReportData.put("tagNameData", Reporter.getTagsMap().values());
			File outputPath = new File(System.getProperty("user.dir") + "\\target\\awtfReport\\js");
			outputPath.mkdir();
			Writer fileWriter = new FileWriter(new File(outputPath.getPath() + "\\awtfReportData.js"));
			// Write to JavaScript file as a variable instead of to a straight JSON since a lot of browsers prevented reading of local JSON file by default as a security measure.
			fileWriter.write("var awtfReportData = \"" + StringEscapeUtils.escapeJson(mapper.writeValueAsString(awtfReportData)) + "\";");
			fileWriter.close();
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}