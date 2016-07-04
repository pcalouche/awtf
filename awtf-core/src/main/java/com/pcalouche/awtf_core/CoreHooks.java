package com.pcalouche.awtf_core;

import com.pcalouche.awtf_core.util.enums.WaitTag;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.concurrent.TimeUnit;

/**
 * Class that contains Cucumber hooks for the core framework.
 *
 * @author Philip Calouche
 */
@ContextConfiguration(classes = {CoreConfig.class})
public class CoreHooks {
    private static final Logger logger = LoggerFactory.getLogger(CoreHooks.class);
    private final CoreStepHandler coreStepHandler;
    private final TestInstance testInstance;
    private final TestEnvironmentConfig testEnvironmentConfig;

    @Autowired
    public CoreHooks(CoreStepHandler coreStepHandler) {
        this.coreStepHandler = coreStepHandler;
        this.testInstance = coreStepHandler.getTestInstance();
        this.testEnvironmentConfig = coreStepHandler.getTestInstance().getTestEnvironmentConfig();
        // Add a runtime shutdown hook to have the web driver quit when all tests are done
        //        Runtime.getRuntime().addShutdownHook(new Thread() {
        //            @Override
        //            public void run() {
        //                logger.debug("Inside shutdown hook");
        //                testInstance.getWebDriver().quit();
        //            }
        //        });
        logger.info("Done with CoreHooks constructor->" + testEnvironmentConfig.getBrowserType());
    }

    @Before
    public void setup(Scenario scenario) {
        // Always check if a the web driver wait needs to be changed from the default for a scenario based on its tags. Also reset the stop watch and current scenario.
        try {
            logger.info("in CoreHooks setup");
            // Set seconds to wait. Will use what is in the default Test Environment Config if wait tag is not found
            int secondsToWait = testEnvironmentConfig.getSecondsToWait();
            boolean displayWaitTag = false;
            for (WaitTag waitTag : WaitTag.values()) {
                if (scenario.getSourceTagNames().contains(waitTag.getTagName())) {
                    displayWaitTag = true;
                    secondsToWait = waitTag.getSecondsToWait();
                    break;
                }
            }
            // Update the web driver wait time for the scenario
            testInstance.getWebDriverWait().withTimeout(secondsToWait, TimeUnit.SECONDS);
            testInstance.getStopWatch().reset();
            testInstance.getStopWatch().start();
            logger.info(String.format("Starting Scenario: \"%s\"", scenario.getName()));
            if (displayWaitTag) {
                logger.info(String.format("Wait Tag was set to: %d seconds based on given tag.", secondsToWait));
            }
            testInstance.setCurrentScenario(scenario);
        } catch (Exception e) {
            logger.error("Failed to initialize scenario", e);
            tearDown(scenario);
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        logger.info("in CoreHooks tearDown");
        // Stop the stop watch and take a final screenshot of configured to do so
        try {
            testInstance.getStopWatch().stop();
            logger.debug(String.format("Scenario: \"%s\" completed in %.3f seconds", scenario.getName(), testInstance.getStopWatch().getTime() / 1000.00));
            scenario.write(String.format("Completed in %.3f seconds.", testInstance.getStopWatch().getTime() / 1000.00));
            if (testInstance.getTestEnvironmentConfig().isScreenshotOnScenarioCompletion() || scenario.isFailed()) {
                coreStepHandler.iTakeAScreenshot();
            }
        } catch (Exception e) {
            logger.error("Failed to tearDown scenario", e);
        } finally {
            this.readyWebAppForNextScenario();
        }
    }

    /**
     * Do something to reset the page to a known state versus just quitting the web driver. In the case of the static demo web page used in the core framework for testing that is refreshing the page.
     * In an actual web application you may want to do a logout. This can be overridden in your hooks class.
     */
    private void readyWebAppForNextScenario() {
        testInstance.getWebDriver().navigate().refresh();
    }
}
