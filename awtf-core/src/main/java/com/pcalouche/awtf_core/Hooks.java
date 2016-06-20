package com.pcalouche.awtf_core;

import com.pcalouche.awtf_core.util.enums.WaitTag;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

/**
 * Class that contains Cucumber hooks for the core framework.
 *
 * @author Philip Calouche
 */
public class Hooks {
    protected Logger logger = LogManager.getLogger();

    @Before
    public void setup(Scenario scenario) {
        /*
		 * This should only run on the first test. Doing this here allows us to reuse the same web driver and avoid the expense in reading in the config files and setting up a new instance of the web
		 * driver on every scenario.
		 */
        if (TestInstance.getWebDriver() == null) {
            logger.info("setting up TestInstance for the first time");
            // Run constructor to setup static members
            new TestInstance();
            // Add a runtime shutdown hook to have the web driver quit when all tests are done
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    TestInstance.getWebDriver().quit();
                }
            });
        }

        // Always check if a the web driver wait needs to be changed from the default for a scenario based on its tags. Also reset the stop watch and current scenario.
        try {
            // Set seconds to wait. Will use what is in the default Test Environment Config if wait tag is not found
            int secondsToWait = TestInstance.getTestEnvironmentConfig().getSecondsToWait();
            boolean displayWaitTag = false;
            for (WaitTag waitTag : WaitTag.values()) {
                if (scenario.getSourceTagNames().contains(waitTag.getTagName())) {
                    displayWaitTag = true;
                    secondsToWait = waitTag.getSecondsToWait();
                    break;
                }
            }
            // Update the web driver wait time for the scenario
            TestInstance.getWebDriverWait().withTimeout(secondsToWait, TimeUnit.SECONDS);
            TestInstance.getStopWatch().reset();
            TestInstance.getStopWatch().start();
            logger.info(String.format("Starting Scenario: \"%s\"", scenario.getName()));
            if (displayWaitTag) {
                logger.info(String.format("Wait Tag was set to: %d seconds based on given tag.", secondsToWait));
            }
            TestInstance.setCurrentScenario(scenario);
        } catch (Exception e) {
            logger.error("Failed to initialize scenario", e);
            tearDown(scenario);
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        // Stop the stop watch and take a final screenshot of configured to do so
        try {
            TestInstance.getStopWatch().stop();
            logger.debug(String.format("Scenario: \"%s\" completed in %.3f seconds", scenario.getName(), TestInstance.getStopWatch().getTime() / 1000.00));
            scenario.write(String.format("Completed in %.3f seconds.", TestInstance.getStopWatch().getTime() / 1000.00));
            if (TestInstance.getTestEnvironmentConfig().isScreenshotOnScenarioCompletion() || scenario.isFailed()) {
                TestInstance.getCoreStepHandler().iTakeAScreenshot();
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
    protected void readyWebAppForNextScenario() {
        TestInstance.getWebDriver().navigate().refresh();
    }
}
