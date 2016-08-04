package com.pcalouche.awtf_core;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * Class that contains Cucumber hooks for the core framework.
 *
 * @author Philip Calouche
 */
@ContextConfiguration(classes = {CoreConfig.class})
public class CoreHooks {
    private static final Logger logger = LoggerFactory.getLogger(CoreHooks.class);
    private final CoreStepHandler coreStepHandler;

    @Autowired
    public CoreHooks(CoreStepHandler coreStepHandler) {
        this.coreStepHandler = coreStepHandler;
    }

    @Before
    public void setup(Scenario scenario) {
        coreStepHandler.handleScenarioSetup(scenario);
    }

    @After
    public void tearDown(Scenario scenario) {
        coreStepHandler.handleScenarioTearDown(scenario);
    }
}
