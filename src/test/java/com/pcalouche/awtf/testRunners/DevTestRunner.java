package com.pcalouche.awtf.testRunners;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * This is an example test runner that demonstrates the core framework.
 *
 * @author Philip Calouche
 */
@RunWith(Cucumber.class)
@CucumberOptions(glue = {"com.pcalouche.awtf"},
        plugin = {"html:target/cucumber/htmlReports/devTestRunner", "json:target/cucumber/jsonReports/devTestRunner.json"},
        features = {"classpath:features/"},
        tags = {"~@pending", "@fileDownload"},
        snippets = SnippetType.CAMELCASE)
public class DevTestRunner {
}