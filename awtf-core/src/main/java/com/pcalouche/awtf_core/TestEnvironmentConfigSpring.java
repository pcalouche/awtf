package com.pcalouche.awtf_core;

import com.pcalouche.awtf_core.util.enums.BrowserType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TestEnvironmentConfigSpring {
    private Logger logger = LogManager.getLogger();
    private BrowserType browserType = BrowserType.firefox;
    private int secondsToWait = 15;
    private String url;
    private boolean screenshotBeforeClick = false;
    private boolean screenshotOnScenarioCompletion = true;

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
