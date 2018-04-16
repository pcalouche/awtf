package com.pcalouche.awtf.demotests.steps.demo;

import com.pcalouche.awtf.demotests.pages.demo.DemoPage;
import com.pcalouche.awtf.demotests.steps.AbstractSteps;
import cucumber.api.java.en.Given;

public class DemoSteps extends AbstractSteps {
    private final DemoPage demoPage;

    public DemoSteps(DemoPage demoPage) {
        this.demoPage = demoPage;
    }

    @Given("^I navigate to \"([^\"]*)\"$")
    public void iNavigateTo(String url) {
        demoPage.navigateToUrl(url);
    }
}
