package com.pcalouche.awtf_reporting;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class Reporter {
    //    private static final Logger logger = LoggerFactory.getLogger(Reporter.class);
    private static SortedMap<String, ReporterStep> stepsMap;
    private static SortedMap<String, TagInstance> tagsMap;

    public Reporter() {
        stepsMap = new TreeMap<>();
        tagsMap = new TreeMap<>();
    }

    /**
     * @return the stepsMap
     */
    public static SortedMap<String, ReporterStep> getStepsMap() {
        return stepsMap;
    }

    /**
     * @param stepsMap the stepsMap to set
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
     * @param tagsMap the tagsMap to set
     */
    public static void setTagsMap(SortedMap<String, TagInstance> tagsMap) {
        Reporter.tagsMap = tagsMap;
    }

    /**
     * Method to track the usage of a step. If the step does not exist it will be added. If it does exist its count will be incremented.
     *
     * @param step          - unique identifier for the step such as the Gherkin string
     * @param usage         - explanation of when to use the step.
     * @param example       - example Gherkin steps for the step
     * @param javaClass     - the Java class the step can be found in
     * @param customerOrder - a custom order to order how the step appears in the report. Useful if you want your steps order as manual for a test writer.
     */
    public static void track(String step, String usage, String example, String javaClass, int customerOrder) {
        if (!Reporter.getStepsMap().containsKey(step)) {
            Reporter.getStepsMap().put(step, new ReporterStep(step, usage, example, javaClass, customerOrder));
        } else {
            Reporter.getStepsMap().get(step).incrementCount();
        }
    }

    /**
     * Method to update the tag data for the report. If the tag does not exist it will be added. If it does exist its count will be incremented.
     *
     * @param sourceTagNames the list of tages for a gives Cucumber Scenario
     */
    public static void updateTagData(Collection<String> sourceTagNames) {
        for (String tagName : sourceTagNames) {
            if (!Reporter.getTagsMap().containsKey(tagName)) {
                Reporter.getTagsMap().put(tagName, new TagInstance(tagName));
            } else {
                Reporter.getTagsMap().get(tagName).incrementCount();
            }
        }
    }

    /**
     * Method to handle what happens after the last feature is analyzed for the report
     */
    public static void handleShutdown() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> awtfReportData = new HashMap<>();
            awtfReportData.put("stepData", Reporter.getStepsMap().values());
            awtfReportData.put("tagNameData", Reporter.getTagsMap().values());
            File outputPath = new File(System.getProperty("user.dir") + "\\target\\awtfReport\\js");
            outputPath.mkdirs();
            Writer fileWriter = new FileWriter(new File(outputPath.getPath() + "\\awtfReportData.js"));
            // Write to JavaScript file as a variable instead of to a straight JSON since a lot of browsers prevented reading of local JSON file by default as a security measure.
//            System.out.println(mapper.writeValueAsString(awtfReportData));
//            System.out.println(StringEscapeUtils.escapeJson(mapper.writeValueAsString(awtfReportData)));
//            logger.info(mapper.writeValueAsString(awtfReportData));
            fileWriter.write("var awtfReportData = \"" + StringEscapeUtils.escapeJson(mapper.writeValueAsString(awtfReportData)) + "\";");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}