package com.pcalouche.awtf_app_example;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * Class that demonstrates how the core hooks class can be extended for your application's needs.
 *
 * @author Philip Calouche
 */
@ContextConfiguration(classes = {MyAppConfig.class})
public class MyAppHooks {
    private static final Logger logger = LoggerFactory.getLogger(MyAppHooks.class);
    private final MyAppStepHandler myAppStepHandler;

    @Autowired
    public MyAppHooks(MyAppStepHandler myAppStepHandler) {
        Thread CLOSE_THREAD = new Thread() {
            @Override
            public void run() {
                try {
                    logger.info("Shutting down web driver now that all tests are done");
                    myAppStepHandler.getTestInstance().getWebDriver().quit();
                } catch (Exception e) {
                }
            }
        };
        Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
        this.myAppStepHandler = myAppStepHandler;
    }

    @Before
    public void setup(Scenario scenario) {
        myAppStepHandler.handleScenarioSetup(scenario);
    }

    @After
    public void tearDown(Scenario scenario) {
        myAppStepHandler.handleScenarioTearDown(scenario);
    }
}
