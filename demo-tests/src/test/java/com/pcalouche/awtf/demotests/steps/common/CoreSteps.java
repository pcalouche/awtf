package com.pcalouche.awtf.demotests.steps.common;

import com.pcalouche.awtf.core.service.CucumberScenarioService;
import com.pcalouche.awtf.demotests.pages.GenericPage;
import com.pcalouche.awtf.demotests.steps.AbstractSteps;
import cucumber.api.java.en.And;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;

public class CoreSteps extends AbstractSteps {
    private final GenericPage genericPage;
    private final CucumberScenarioService cucumberScenarioService;

    @Autowired
    public CoreSteps(GenericPage genericPage,
                     CucumberScenarioService cucumberScenarioService) {
        this.genericPage = genericPage;
        this.cucumberScenarioService = cucumberScenarioService;
    }

    @And("^I take a screenshot$")
    public void takeScreenshot() {
        cucumberScenarioService.takeScreenshot();
    }

    @And("^I click on the \"([^\"]*)\" link$")
    public void iClickOnTheLink(String linkText) {
        genericPage.webElementClick(By.linkText(linkText));
    }
}
