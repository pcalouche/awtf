package com.pcalouche.awtf.testRunners;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/**
 * This is an example test runner that demonstrates the core framework.
 *
 * @author Philip Calouche
 */
@RunWith(Cucumber.class)
@CucumberOptions(glue = {"com.pcalouche.awtf"},
        plugin = {"html:target/cucumber/htmlReports/devTestRunner", "json:target/cucumber/jsonReports/devTestRunner.json"},
        features = {"classpath:features/"},
        tags = {"~@pending", "@formInputAndVerification"},
        snippets = SnippetType.CAMELCASE)
@TestExecutionListeners(listeners = DependencyInjectionTestExecutionListener.class)
public class DevTestRunner {
}