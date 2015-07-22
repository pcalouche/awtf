package com.pcalouche.awtf_core.testRunners;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(glue = { "classpath:" }, plugin = { "html:target/cucumber/testRunner", "json:target/cucumber/testRunner.json", "rerun:target/cucumber/testRunnerRerun.txt" }, features = { "classpath:" }, tags = { "~@pending" }, snippets = SnippetType.CAMELCASE)
public class TestRunner {

}