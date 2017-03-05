package com.pcalouche.awtf.config.spring;

import com.pcalouche.awtf.util.enums.WaitTag;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import java.util.concurrent.TimeUnit;

/**
 * Class that contains Cucumber hooks for the core framework.
 *
 * @author Philip Calouche
 */
@ContextHierarchy({
        @ContextConfiguration(classes = {CoreConfig.class})
})
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class Hooks {
    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);
    private final TestInstance testInstance;

    @Autowired
    public Hooks(TestInstance testInstance) {
        this.testInstance = testInstance;
    }

    @Before
    public void setup(Scenario scenario) {
        // Set the currentScenario of the test instance, so it can be used if needed
        testInstance.setCurrentScenario(scenario);
        // Always check if a the web driver wait needs to be changed from the default for a scenario based on its tags. Also reset the stop watch and current scenario.
        try {
            // Set seconds to wait. Will use what is in the default Test Environment Config if wait tag is not found
            int secondsToWait = testInstance.getTestEnvironmentConfig().getSecondsToWait();
            boolean displayWaitTag = false;
            for (WaitTag waitTag : WaitTag.values()) {
                if (scenario.getSourceTagNames().contains(waitTag.getTagName())) {
                    displayWaitTag = true;
                    secondsToWait = waitTag.getSecondsToWait();
                    break;
                }
            }
            logger.info("setting second to wait " + secondsToWait);
            // Update the web driver wait time for the scenario
            testInstance.getWebDriverWait().withTimeout(secondsToWait, TimeUnit.SECONDS);
            testInstance.getStopWatch().reset();
            testInstance.getStopWatch().start();
            logger.info(String.format("Starting Scenario: \"%s\"", scenario.getName()));
            if (displayWaitTag) {
                logger.info(String.format("Wait Tag was set to: %d seconds based on given tag.", secondsToWait));
            }
        } catch (Exception e) {
            logger.error("Failed to setup scenario", e);
            tearDown(scenario);
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            testInstance.getStopWatch().stop();
            logger.info(String.format("Scenario: \"%s\" completed in %.3f seconds", scenario.getName(), testInstance.getStopWatch().getTime() / 1000.0));
            testInstance.getCurrentScenario().write(String.format("Completed in %.3f seconds.", testInstance.getStopWatch().getTime() / 1000.0));
            if (testInstance.getTestEnvironmentConfig().isScreenshotOnScenarioCompletion() || scenario.isFailed()) {
                scenario.embed(((TakesScreenshot) testInstance.getWebDriver()).getScreenshotAs(OutputType.BYTES), "image/png");
            }
        } catch (Exception e) {
            logger.error("Failed to tear down scenario", e);
        }
    }
}
