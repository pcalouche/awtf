package com.pcalouche.awtf_app_example;

import com.pcalouche.awtf_core.CoreStepsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * This is a general Utility class for helper methods related to Cucumber and Selenium step testing.
 *
 * @author Philip Calouche
 */
public class MyAppStepsUtil extends CoreStepsUtil {
    private static final Logger logger = LoggerFactory.getLogger(MyAppStepsUtil.class);
    private final MyAppTestInstance myAppTestInstance;

    public MyAppStepsUtil(MyAppTestInstance myAppTestInstance) {
        super(myAppTestInstance);
        this.myAppTestInstance = myAppTestInstance;
        logger.info("Done with MyAppStepsUtil constructor");
    }

    public MyAppTestInstance getMyAppTestInstance() {
        return myAppTestInstance;
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
            if (myAppTestInstance.getCurrentScenario() != null) {
                myAppTestInstance.getCurrentScenario().write(String.format("%s is: %s<br>", "{MM/DD/YYYY}", format.format(Calendar.getInstance().getTime())));
            }
        }
        if (returnText.contains("{Confirmation Code}")) {
            returnText = returnText.replace("{Confirmation Code}", myAppTestInstance.getTempMap().get("lastConfirmationCode"));
            // Log to scenario to help with review
            if (myAppTestInstance.getCurrentScenario() != null) {
                myAppTestInstance.getCurrentScenario().write(String.format("%s is: %s<br>", "{Confirmation Code}", myAppTestInstance.getTempMap().get("lastConfirmationCode")));
            }
        }
        if (returnText.contains("{Service Request ID}")) {
            returnText = returnText.replace("{Service Request ID}", myAppTestInstance.getTempMap().get("lastServiceRequestID"));
            // Log to scenario to help with review
            if (myAppTestInstance.getCurrentScenario() != null) {
                myAppTestInstance.getCurrentScenario().write(String.format("%s is: %s<br>", "{Service Request ID}", myAppTestInstance.getTempMap().get("lastServiceRequestID")));
            }
        }
        if (returnText.contains("{Requested Effective Date}")) {
            returnText = returnText.replace("{Requested Effective Date}", myAppTestInstance.getTempMap().get("Requested Effective Date"));
            // Log to scenario to help with review
            if (myAppTestInstance.getCurrentScenario() != null) {
                myAppTestInstance.getCurrentScenario().write(String.format("%s is: %s<br>", "{Requested Effective Date}", myAppTestInstance.getTempMap().get("Requested Effective Date")));
            }
        }
        if (returnText.contains("{Effective Date}")) {
            returnText = returnText.replace("{Effective Date}", myAppTestInstance.getTempMap().get("Effective Date"));
            // Log to scenario to help with review
            if (myAppTestInstance.getCurrentScenario() != null) {
                myAppTestInstance.getCurrentScenario().write(String.format("%s is: %s<br>", "{Effective Date}", myAppTestInstance.getTempMap().get("Effective Date")));
            }
        }
        return returnText;
    }
}