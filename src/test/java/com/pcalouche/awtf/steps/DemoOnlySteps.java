package com.pcalouche.awtf.steps;

import com.pcalouche.awtf.config.spring.CoreConfig;
import com.pcalouche.awtf.config.spring.TestInstance;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import java.nio.file.Paths;

@ContextHierarchy({
        @ContextConfiguration(classes = {CoreConfig.class})
})
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
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
