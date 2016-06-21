package com.pcalouche.awtf_core;

import com.pcalouche.awtf_core.util.enums.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TestEnvironmentConfigSpring {
    private static final Logger logger = LoggerFactory.getLogger(TestEnvironmentConfigSpring.class);
    private final BrowserType browserType;
    private final int secondsToWait;
    private final String url;
    private final boolean screenshotBeforeClick;
    private final boolean screenshotOnScenarioCompletion;

    @Autowired
    public TestEnvironmentConfigSpring(@Value("${browserType}") BrowserType browserType,
                                       @Value("${secondsToWait}") int secondsToWait,
                                       @Value("${url}") String url,
                                       @Value("${screenshotBeforeClick}") boolean screenshotBeforeClick,
                                       @Value("${screenshotOnScenarioCompletion}") boolean screenshotOnScenarioCompletion) {
        this.browserType = browserType;
        this.secondsToWait = secondsToWait;
        this.url = url;
        this.screenshotBeforeClick = screenshotBeforeClick;
        this.screenshotOnScenarioCompletion = screenshotOnScenarioCompletion;
        logger.info("done with TestEnvironmentConfigSpring constructor");
    }


    public BrowserType getBrowserType() {
        return browserType;
    }

    public int getSecondsToWait() {
        return secondsToWait;
    }

    public String getUrl() {
        return url;
    }

    public boolean isScreenshotBeforeClick() {
        return screenshotBeforeClick;
    }

    public boolean isScreenshotOnScenarioCompletion() {
        return screenshotOnScenarioCompletion;
    }
}
