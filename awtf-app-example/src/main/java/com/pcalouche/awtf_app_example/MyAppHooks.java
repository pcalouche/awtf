package com.pcalouche.awtf_app_example;

import com.pcalouche.awtf_core.util.enums.WaitTag;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.concurrent.TimeUnit;

/**
 * Class that demonstrates how the core hooks class can be extended for your application's needs.
 *
 * @author Philip Calouche
 */
@ContextConfiguration(classes = {MyAppConfig.class})
public class MyAppHooks {
    private static final Logger logger = LoggerFactory.getLogger(MyAppHooks.class);
    private final MyAppTestInstance myAppTestInstance;
    private final MyAppStepHandler myAppStepHandler;

    @Autowired
    public MyAppHooks(MyAppTestInstance myAppTestInstance, MyAppStepHandler myAppStepHandler) {
//        super(myAppTestInstance, myAppStepHandler);
        this.myAppTestInstance = myAppTestInstance;
        this.myAppStepHandler = myAppStepHandler;
        // Add a runtime shutdown hook to have the web driver quit when all tests are done
        //        Runtime.getRuntime().addShutdownHook(new Thread() {
        //            @Override
        //            public void run() {
        //                logger.debug("Inside shutdown hook");
        //                testInstance.getWebDriver().quit();
        //            }
        //        });
        logger.info("Done with CoreHooks constructor->" + this.myAppTestInstance.getTestEnvironmentConfig().getBrowserType());
    }

    @Before
    public void setup(Scenario scenario) {
        // Always check if a the web driver wait needs to be changed from the default for a scenario based on its tags. Also reset the stop watch and current scenario.
        try {
            logger.info("in  setup " + this.myAppTestInstance.getTestEnvironmentConfig().getBrowserType());
            // Set seconds to wait. Will use what is in the default Test Environment Config if wait tag is not found
            int secondsToWait = this.myAppTestInstance.getTestEnvironmentConfig().getSecondsToWait();
            boolean displayWaitTag = false;
            for (WaitTag waitTag : WaitTag.values()) {
                if (scenario.getSourceTagNames().contains(waitTag.getTagName())) {
                    displayWaitTag = true;
                    secondsToWait = waitTag.getSecondsToWait();
                    break;
                }
            }
            // Update the web driver wait time for the scenario
            myAppTestInstance.getWebDriverWait().withTimeout(secondsToWait, TimeUnit.SECONDS);
            myAppTestInstance.getStopWatch().reset();
            myAppTestInstance.getStopWatch().start();
            logger.info(String.format("Starting Scenario: \"%s\"", scenario.getName()));
            if (displayWaitTag) {
                logger.info(String.format("Wait Tag was set to: %d seconds based on given tag.", secondsToWait));
            }
            myAppTestInstance.setCurrentScenario(scenario);
        } catch (Exception e) {
            logger.error("Failed to initialize scenario", e);
            tearDown(scenario);
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        logger.info("in  tearDown " + this.myAppTestInstance.getTestEnvironmentConfig().getBrowserType());
        // Stop the stop watch and take a final screenshot of configured to do so
        try {
            myAppTestInstance.getStopWatch().stop();
            logger.debug(String.format("Scenario: \"%s\" completed in %.3f seconds", scenario.getName(), myAppTestInstance.getStopWatch().getTime() / 1000.00));
            scenario.write(String.format("Completed in %.3f seconds.", myAppTestInstance.getStopWatch().getTime() / 1000.00));
            if (myAppTestInstance.getTestEnvironmentConfig().isScreenshotOnScenarioCompletion() || scenario.isFailed()) {
                myAppStepHandler.iTakeAScreenshot();
            }
        } catch (Exception e) {
            logger.error("Failed to tearDown scenario", e);
        } finally {
            this.readyWebAppForNextScenario();
        }
    }

//    @Override
//    @Before
//    public void setup(Scenario scenario) {
//        /*
//         * This should only run on the first test. Doing this here allows us to reuse the same web driver and avoid the expense in reading in the config files and setting up a new instance of the web
//		 * driver on every scenario.
//		 */
//        if (MyAppTestInstance.getWebDriver() == null) {
//            logger.info("setting up MyAppTestInstance for the first time");
//            // Run constructor to setup static members
//            new MyAppTestInstance();
//            // Add a runtime shutdown hook to have the web driver quit when all tests are done
//            Runtime.getRuntime().addShutdownHook(new Thread() {
//                @Override
//                public void run() {
//                    MyAppTestInstance.getWebDriver().quit();
//                }
//            });
//        }
//
//        // Always check if a the web driver wait needs to be changed from the default for a scenario based on its tags. Also reset the stop watch and current scenario.
//        try {
//            // Set seconds to wait. Will use what is in the default Test Environment Config if wait tag is not found
//            int secondsToWait = MyAppTestInstance.getTestEnvironmentConfig().getSecondsToWait();
//            boolean displayWaitTag = false;
//            for (WaitTag waitTag : WaitTag.values()) {
//                if (scenario.getSourceTagNames().contains(waitTag.getTagName())) {
//                    displayWaitTag = true;
//                    secondsToWait = waitTag.getSecondsToWait();
//                    break;
//                }
//            }
//            // Update the web driver wait time for the scenario
//            MyAppTestInstance.getWebDriverWait().withTimeout(secondsToWait, TimeUnit.SECONDS);
//            MyAppTestInstance.getStopWatch().reset();
//            MyAppTestInstance.getStopWatch().start();
//            logger.info(String.format("Starting Scenario: \"%s\"", scenario.getName()));
//            if (displayWaitTag) {
//                logger.info(String.format("Wait Tag was set to: %d seconds based on given tag.", secondsToWait));
//            }
//            MyAppTestInstance.setCurrentScenario(scenario);
//        } catch (Exception e) {
//            logger.error("Failed to initialize scenario", e);
//            tearDown(scenario);
//        }
//    }

    //    @Override
//    @After
//    public void tearDown(Scenario scenario) {
//       // Stop the stop watch and take a final screenshot of configured to do so
//        try {
//            MyAppTestInstance.getStopWatch().stop();
//            logger.debug(String.format("Scenario: \"%s\" completed in %.3f seconds", scenario.getName(), MyAppTestInstance.getStopWatch().getTime() / 1000.00));
//            scenario.write(String.format("Completed in %.3f seconds.", MyAppTestInstance.getStopWatch().getTime() / 1000.00));
//            if (MyAppTestInstance.getTestEnvironmentConfig().isScreenshotOnScenarioCompletion() || scenario.isFailed()) {
//                MyAppTestInstance.getCoreStepHandler().iTakeAScreenshot();
//            }
//        } catch (Exception e) {
//            logger.error("Failed to tearDown scenario", e);
//        } finally {
//            this.readyWebAppForNextScenario();
//        }
//    }
//
//    @Override
    protected void readyWebAppForNextScenario() {
        /*
         * For my application let me handle logout instead of just doing a page refresh like the core framework does. Let's test if the logout button is visible first before logging out. If we had a
		 * scenario that tested the logout button and verified the text this wouldn't be valid to run here.
		 */
        if (myAppTestInstance.getWebDriver().findElement(By.cssSelector("button[value='logout']")).isDisplayed()) {
            myAppStepHandler.iClickOn("Logout");
            myAppStepHandler.iSeeTheMessage("You are logged out");
        }
    }
}
