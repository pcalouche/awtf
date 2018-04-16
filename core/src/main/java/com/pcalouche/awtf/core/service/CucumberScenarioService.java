package com.pcalouche.awtf.core.service;

import cucumber.api.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class CucumberScenarioService {
    private Scenario scenario = null;
    private final WebDriver webDriver;

    public CucumberScenarioService(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public Scenario getCurrentScenario() {
        return scenario;
    }

    public void setCurrentScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public void takeScreenshot() {
        // For database only tests a web driver still gets created.
        // Check that the URL contains http, so blank screenshots are not added unnecessarily.
        if (webDriver.getCurrentUrl().contains("http")) {
            scenario.embed(((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES), "image/png");
        }
    }

    public void embedDataText(String displayText, String data) {
        scenario.embed(String.format("%s:%s", displayText, data).getBytes(), "text/plain");
    }

    //TODO port over other Cucumber Scenario methods here like embed data, embed HTML etc.
}
