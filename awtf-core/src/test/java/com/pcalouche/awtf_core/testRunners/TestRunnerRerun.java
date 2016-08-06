package com.pcalouche.awtf_core.testRunners;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * This is an example test re-runner that demonstrates the core framework. Steps that fail from TestRunner are dumped to the file that this test runner will then read.
 *
 * @author Philip Calouche
 */
@RunWith(Cucumber.class)
@CucumberOptions(glue = {"classpath:"},
        plugin = {"html:target/cucumber/testRunnerRerun", "json:target/cucumber/testRunnerRerun.json"},
        features = {"@target/cucumber/testRunnerRerun.txt"},
        snippets = SnippetType.CAMELCASE)
public class TestRunnerRerun {
}