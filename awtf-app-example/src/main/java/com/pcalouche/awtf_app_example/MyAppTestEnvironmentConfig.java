package com.pcalouche.awtf_app_example;

import com.pcalouche.awtf_core.TestEnvironmentConfig;
import com.pcalouche.awtf_core.util.enums.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * This class creates a test instance to encapsulate all that is needed to run a test.
 *
 * @author Philip Calouche
 */
@Component
public class MyAppTestEnvironmentConfig extends TestEnvironmentConfig {
    private static final Logger logger = LoggerFactory.getLogger(MyAppTestEnvironmentConfig.class);
    private final String loginID;
    private final String password;

    public MyAppTestEnvironmentConfig(@Value("${browserType}") BrowserType browserType,
                                      @Value("${secondsToWait}") int secondsToWait,
                                      @Value("${url}") String url,
                                      @Value("${screenshotBeforeClick}") boolean screenshotBeforeClick,
                                      @Value("${screenshotOnScenarioCompletion}") boolean screenshotOnScenarioCompletion,
                                      @Value("${loginID}") String loginID,
                                      @Value("${password}") String password) {
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
