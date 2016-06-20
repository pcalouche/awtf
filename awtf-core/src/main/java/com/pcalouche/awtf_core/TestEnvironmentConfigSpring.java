package com.pcalouche.awtf_core;

import com.pcalouche.awtf_core.util.enums.BrowserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TestEnvironmentConfigSpring {
    private BrowserType browserType = BrowserType.phantomJS;
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
    }


    public BrowserType getBrowserType() {
        return browserType;
    }

//    public void setBrowserType(BrowserType browserType) {
//        this.browserType = browserType;
//    }

    public int getSecondsToWait() {
        return secondsToWait;
    }

//    public void setSecondsToWait(int secondsToWait) {
//        this.secondsToWait = secondsToWait;
//    }

    public String getUrl() {
        return url;
    }

//    public void setUrl(String url) {
//        this.url = url;
//    }

    public boolean isScreenshotBeforeClick() {
        return screenshotBeforeClick;
    }

//    public void setScreenshotBeforeClick(boolean screenshotBeforeClick) {
//        this.screenshotBeforeClick = screenshotBeforeClick;
//    }

    public boolean isScreenshotOnScenarioCompletion() {
        return screenshotOnScenarioCompletion;
    }

//    public void setScreenshotOnScenarioCompletion(boolean screenshotOnScenarioCompletion) {
//        this.screenshotOnScenarioCompletion = screenshotOnScenarioCompletion;
//    }
}
