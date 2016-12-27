package com.pcalouche.awtf_core;

import com.pcalouche.awtf_core.util.enums.BrowserType;

public class TestEnvironmentConfig {
    private final BrowserType browserType;
    private final int secondsToWait;
    private final String url;
    private final boolean screenshotBeforeClick;
    private final boolean screenshotOnScenarioCompletion;

    public TestEnvironmentConfig(BrowserType browserType,
                                 int secondsToWait,
                                 String url,
                                 boolean screenshotBeforeClick,
                                 boolean screenshotOnScenarioCompletion) {
        this.browserType = browserType;
        this.secondsToWait = secondsToWait;
        this.url = url;
        this.screenshotBeforeClick = screenshotBeforeClick;
        this.screenshotOnScenarioCompletion = screenshotOnScenarioCompletion;
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
