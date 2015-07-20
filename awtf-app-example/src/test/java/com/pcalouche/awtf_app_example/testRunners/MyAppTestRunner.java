package com.pcalouche.awtf_app_example.testRunners;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

/**
 * This is an example test runner that demonstrates how you can reference the core step definitions of the AWTF framework and your own step definitions and hooks. This is done by providing the
 * packages to look for in the glue option.
 * 
 * @author Philip Calouche
 *
 */
@RunWith(Cucumber.class)
@CucumberOptions(glue = { "com.pcalouche.awtf_core.stepDefinitions", "com.pcalouche.awtf_app_example" }, plugin = { "html:target/cucumber/myAppTestRunner",
		"json:target/cucumber/myAppTestRunner.json", "rerun:target/cucumber/myAppTestRunnerRerun.txt" }, features = { "classpath:" }, tags = { "~@pending", "@loadMask" }, snippets = SnippetType.CAMELCASE)
public class MyAppTestRunner {
}