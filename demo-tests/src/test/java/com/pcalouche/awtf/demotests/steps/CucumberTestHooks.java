package com.pcalouche.awtf.demotests.steps;

import com.pcalouche.awtf.core.service.CucumberScenarioService;
import com.pcalouche.awtf.demotests.Application;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest
public class CucumberTestHooks {
    private final CucumberScenarioService cucumberScenarioService;

    @Autowired
    public CucumberTestHooks(CucumberScenarioService cucumberScenarioService) {
        this.cucumberScenarioService = cucumberScenarioService;
    }

    @Before
    public void before(Scenario scenario) {
        cucumberScenarioService.setCurrentScenario(scenario);
    }

    @After
    public void after(Scenario scenario) {
        cucumberScenarioService.takeScreenshot();
    }
}
