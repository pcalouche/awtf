package com.pcalouche.awtf_core;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;

/**
 * Class that contains Cucumber hooks for the core framework.
 *
 * @author Philip Calouche
 */
@ContextConfiguration(classes = AwtfCoreConfig.class)
public class HooksSpring {
    //        private final ApplicationContext ctx = new AnnotationConfigApplicationContext(AwtfCoreConfig.class);
    private final TestEnvironmentConfigSpring testEnvironmentConfig;
    //    private final TestInstanceSpring testInstance;
//    private final CoreStepHandlerSpring coreStepHandler = ctx.getBean("coreStepHandler", CoreStepHandlerSpring.class);
    protected Logger logger = LogManager.getLogger();

    @Autowired
    public HooksSpring(TestEnvironmentConfigSpring testEnvironmentConfig) {
        this.testEnvironmentConfig = testEnvironmentConfig;
        logger.info("in HooksSpring constructor");
        new AnnotationConfigApplicationContext(AwtfCoreConfig.class);
//        ctx = new AnnotationConfigApplicationContext(AwtfCoreConfig.class);
//        this.testEnvironmentConfig = ctx.getBean("testEnvironmentConfigSpring", TestEnvironmentConfigSpring.class);
//        this.testInstance = ctx.getBean("testInstance", TestInstanceSpring.class);
        //        this.coreStepHandler = ctx.getBean("coreStepHandler", CoreStepHandlerSpring.class);
//        logger.info("Seconds to Wait->" + (this.testEnvironmentConfig.getSecondsToWait()));
//        logger.info("in hookspring status of webdriver->" + (testInstance.getWebDriver() == null));
        // Add a runtime shutdown hook to have the web driver quit when all tests are done
        //        Runtime.getRuntime().addShutdownHook(new Thread() {
        //            @Override
        //            public void run() {
        //                logger.info("LOGGER22!!!! in shutdown hook");
        //                testInstance.getWebDriver().quit();
        //            }
        //        });

//        logger.info("Done with HooksSpring constructor->" + testInstance.getTestEnvironmentConfig().getBrowserType());
    }

    @Before
    public void setup(Scenario scenario) {
        logger.info("in  setup " + testEnvironmentConfig.getBrowserType());
        // Always check if a the web driver wait needs to be changed from the default for a scenario based on its tags. Also reset the stop watch and current scenario.
//        try {
//            // Set seconds to wait. Will use what is in the default Test Environment Config if wait tag is not found
//            //            int secondsToWait = testEnvironmentConfig.getSecondsToWait();
//            int secondsToWait = testInstance.getTestEnvironmentConfig().getSecondsToWait();
//            boolean displayWaitTag = false;
//            for (WaitTag waitTag : WaitTag.values()) {
//                if (scenario.getSourceTagNames().contains(waitTag.getTagName())) {
//                    displayWaitTag = true;
//                    secondsToWait = waitTag.getSecondsToWait();
//                    break;
//                }
//            }
//            // Update the web driver wait time for the scenario
//            testInstance.getWebDriverWait().withTimeout(secondsToWait, TimeUnit.SECONDS);
//            testInstance.getStopWatch().reset();
//            testInstance.getStopWatch().start();
//            logger.info(String.format("Starting Scenario: \"%s\"", scenario.getName()));
//            if (displayWaitTag) {
//                logger.info(String.format("Wait Tag was set to: %d seconds based on given tag.", secondsToWait));
//            }
//            testInstance.setCurrentScenario(scenario);
//        } catch (Exception e) {
//            logger.error("Failed to initialize scenario", e);
//            tearDown(scenario);
//        }
    }

    @After
    public void tearDown(Scenario scenario) {
        logger.info("in  tearDown " + testEnvironmentConfig.getBrowserType());
        // Stop the stop watch and take a final screenshot of configured to do so
//        logger.info("In tearDown->" + testEnvironmentConfig.getBrowserType());
//        try {
//            testInstance.getStopWatch().stop();
//            logger.debug(String.format("Scenario: \"%s\" completed in %.3f seconds", scenario.getName(), testInstance.getStopWatch().getTime() / 1000.00));
//            scenario.write(String.format("Completed in %.3f seconds.", testInstance.getStopWatch().getTime() / 1000.00));
//            //            if (testEnvironmentConfig.isScreenshotOnScenarioCompletion() || scenario.isFailed()) {
//            //                coreStepHandler.iTakeAScreenshot();
//            //            }
//        } catch (Exception e) {
//            logger.error("Failed to tearDown scenario", e);
//        } finally {
//            this.readyWebAppForNextScenario();
//        }
    }

    /**
     * Do something to reset the page to a known state versus just quitting the web driver. In the case of the static demo web page used in the core framework for testing that is refreshing the page.
     * In an actual web application you may want to do a logout. This can be overridden in your hooks class.
     */
    protected void readyWebAppForNextScenario() {
//        testInstance.getWebDriver().navigate().refresh();
    }
}
