package com.pcalouche.awtf_app_example.testRunners;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(glue = { "com.pcalouche.awtf_core.stepDefinitions", "com.pcalouche.awtf_app_example" }, plugin = { "html:target/cucumber/appTestRunnerRerun",
		"json:target/cucumber/appTestRunnerRerun.json" }, features = { "@target/cucumber/appTestRunnerRerun.txt" }, snippets = SnippetType.CAMELCASE)
public class AppTestRunnerRerun {
}