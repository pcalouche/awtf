package com.pcalouche.awtf_core.steps;

import com.pcalouche.awtf_core.config.spring.CoreConfig;
import com.pcalouche.awtf_core.config.spring.TestInstance;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;

import java.nio.file.Paths;

@ContextHierarchy({
        @ContextConfiguration(classes = {CoreConfig.class})
})
public class DemoOnlySteps {
    private final TestInstance testInstance;

    @Autowired
    public DemoOnlySteps(TestInstance testInstance) {
        this.testInstance = testInstance;
    }

    @Given("^I go to the demo page$")
    public void iGoToTheDemoPage() {
        // Change to appropriate URL
        testInstance.getWebDriver().get(Paths.get(System.getProperty("user.dir") + testInstance.getTestEnvironmentConfig().getUrl()).toUri().toString());
    }
}
