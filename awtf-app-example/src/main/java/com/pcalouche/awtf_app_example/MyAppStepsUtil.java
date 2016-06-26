package com.pcalouche.awtf_app_example;

import com.pcalouche.awtf_core.CoreStepsUtil;
import com.pcalouche.awtf_core.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * This is a general Utility class for helper methods related to Cucumber and Selenium step testing.
 *
 * @author Philip Calouche
 */
@Component("myAppStepsUtil")
public class MyAppStepsUtil extends CoreStepsUtil {
    @Autowired
    public MyAppStepsUtil(TestInstance testInstance) {
        super(testInstance);
    }

    @Override
    public String resolveText(String text) {
        // Calling super version to use that parsing as a start.
        String returnText = super.resolveText(text);
        // Perform any other substitutions as needed.
        if (returnText.contains("{MM/DD/YYYY}")) {
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
            format.setTimeZone(TimeZone.getTimeZone("America/New_York"));
            returnText = returnText.replace("{MM/DD/YYYY}", format.format(Calendar.getInstance().getTime()));
            // Log to scenario to help with review
            if (testInstance.getCurrentScenario() != null) {
                testInstance.getCurrentScenario().write(String.format("%s is: %s<br>", "{MM/DD/YYYY}", format.format(Calendar.getInstance().getTime())));
            }
        }
        if (returnText.contains("{Confirmation Code}")) {
            returnText = returnText.replace("{Confirmation Code}", testInstance.getTempMap().get("lastConfirmationCode"));
            // Log to scenario to help with review
            if (testInstance.getCurrentScenario() != null) {
                testInstance.getCurrentScenario().write(String.format("%s is: %s<br>", "{Confirmation Code}", testInstance.getTempMap().get("lastConfirmationCode")));
            }
        }
        if (returnText.contains("{Service Request ID}")) {
            returnText = returnText.replace("{Service Request ID}", testInstance.getTempMap().get("lastServiceRequestID"));
            // Log to scenario to help with review
            if (testInstance.getCurrentScenario() != null) {
                testInstance.getCurrentScenario().write(String.format("%s is: %s<br>", "{Service Request ID}", testInstance.getTempMap().get("lastServiceRequestID")));
            }
        }
        if (returnText.contains("{Requested Effective Date}")) {
            returnText = returnText.replace("{Requested Effective Date}", testInstance.getTempMap().get("Requested Effective Date"));
            // Log to scenario to help with review
            if (testInstance.getCurrentScenario() != null) {
                testInstance.getCurrentScenario().write(String.format("%s is: %s<br>", "{Requested Effective Date}", testInstance.getTempMap().get("Requested Effective Date")));
            }
        }
        if (returnText.contains("{Effective Date}")) {
            returnText = returnText.replace("{Effective Date}", testInstance.getTempMap().get("Effective Date"));
            // Log to scenario to help with review
            if (testInstance.getCurrentScenario() != null) {
                testInstance.getCurrentScenario().write(String.format("%s is: %s<br>", "{Effective Date}", testInstance.getTempMap().get("Effective Date")));
            }
        }
        return returnText;
    }
}