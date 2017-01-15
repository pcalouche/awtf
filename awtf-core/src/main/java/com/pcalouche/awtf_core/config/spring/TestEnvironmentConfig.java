package com.pcalouche.awtf_core.config.spring;

public class TestEnvironmentConfig {
    private final int secondsToWait;
    private final String url;
    private final boolean screenshotBeforeClick;
    private final boolean screenshotOnScenarioCompletion;

    public TestEnvironmentConfig(int secondsToWait,
                                 String url,
                                 boolean screenshotBeforeClick,
                                 boolean screenshotOnScenarioCompletion) {
        this.secondsToWait = secondsToWait;
        this.url = url;
        this.screenshotBeforeClick = screenshotBeforeClick;
        this.screenshotOnScenarioCompletion = screenshotOnScenarioCompletion;
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
