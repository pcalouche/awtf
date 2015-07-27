package com.pcalouche.awtf_app_example;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;

import com.pcalouche.awtf_core.StepsUtil;
import com.pcalouche.awtf_core.TestInstance;

/**
 * This is a general Utility class for helper methods related to Cucumber and Selenium step testing.
 *
 * @author Philip Calouche
 *
 */
public class MyAppStepsUtil extends StepsUtil {

	public MyAppStepsUtil() {
		logger = LogManager.getLogger();
	}

	@Override
	public String parseText(String text) {
		// Calling super version to use that parsing as a start.
		String returnText = super.parseText(text);
		// Perform any other substitutions as needed.
		if (returnText.contains("{MM/DD/YYYY}")) {
			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			format.setTimeZone(TimeZone.getTimeZone("America/New_York"));
			returnText = returnText.replace("{MM/DD/YYYY}", format.format(Calendar.getInstance().getTime()));
			// Log to scenario to help with review
			if (TestInstance.getCurrentScenario() != null) {
				TestInstance.getCurrentScenario().write(String.format("%s is: %s<br>", "{MM/DD/YYYY}", format.format(Calendar.getInstance().getTime())));
			}
		}
		if (returnText.contains("{Confirmation Code}")) {
			returnText = returnText.replace("{Confirmation Code}", TestInstance.getTempMap().get("lastConfirmationCode"));
			// Log to scenario to help with review
			if (TestInstance.getCurrentScenario() != null) {
				TestInstance.getCurrentScenario().write(String.format("%s is: %s<br>", "{Confirmation Code}", TestInstance.getTempMap().get("lastConfirmationCode")));
			}
		}
		if (returnText.contains("{Service Request ID}")) {
			returnText = returnText.replace("{Service Request ID}", TestInstance.getTempMap().get("lastServiceRequestID"));
			// Log to scenario to help with review
			if (TestInstance.getCurrentScenario() != null) {
				TestInstance.getCurrentScenario().write(String.format("%s is: %s<br>", "{Service Request ID}", TestInstance.getTempMap().get("lastServiceRequestID")));
			}
		}
		if (returnText.contains("{Requested Effective Date}")) {
			returnText = returnText.replace("{Requested Effective Date}", TestInstance.getTempMap().get("Requested Effective Date"));
			// Log to scenario to help with review
			if (TestInstance.getCurrentScenario() != null) {
				TestInstance.getCurrentScenario().write(String.format("%s is: %s<br>", "{Requested Effective Date}", TestInstance.getTempMap().get("Requested Effective Date")));
			}
		}
		if (returnText.contains("{Effective Date}")) {
			returnText = returnText.replace("{Effective Date}", TestInstance.getTempMap().get("Effective Date"));
			// Log to scenario to help with review
			if (TestInstance.getCurrentScenario() != null) {
				TestInstance.getCurrentScenario().write(String.format("%s is: %s<br>", "{Effective Date}", TestInstance.getTempMap().get("Effective Date")));
			}
		}
		return returnText;
	}
}