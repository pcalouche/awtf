package com.pcalouche.awtf_app_example;

import com.pcalouche.awtf_core.TestEnvironmentConfig;
import com.pcalouche.awtf_core.util.enums.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class creates a test instance to encapsulate all that is needed to run a test.
 *
 * @author Philip Calouche
 */
public class MyAppTestEnvironmentConfig extends TestEnvironmentConfig {
    private static final Logger logger = LoggerFactory.getLogger(MyAppTestEnvironmentConfig.class);
    private final String loginID;
    private final String password;

    public MyAppTestEnvironmentConfig(BrowserType browserType,
                                      int secondsToWait,
                                      String url,
                                      boolean screenshotBeforeClick,
                                      boolean screenshotOnScenarioCompletion,
                                      String loginID,
                                      String password) {
        super(browserType, secondsToWait, url, screenshotBeforeClick, screenshotOnScenarioCompletion);
        this.loginID = loginID;
        this.password = password;
        logger.info("done with MyAppTestEnvironmentConfig constructor");
    }

    public String getLoginID() {
        return loginID;
    }

    public String getPassword() {
        return password;
    }
}
