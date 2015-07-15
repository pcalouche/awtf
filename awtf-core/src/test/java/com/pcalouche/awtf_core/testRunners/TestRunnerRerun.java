package com.pcalouche.awtf_core.testRunners;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(glue = { "classpath:" }, plugin = { "html:target/cucumber/testRunnerRerun", "json:target/cucumber/testRunnerRerun.json" }, features = { "@target/cucumber/testRunnerRerun.txt" }, snippets = SnippetType.CAMELCASE)
public class TestRunnerRerun {
}