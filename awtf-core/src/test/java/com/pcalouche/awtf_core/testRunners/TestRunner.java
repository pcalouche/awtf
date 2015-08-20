package com.pcalouche.awtf_core.testRunners;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

/**
 * This is an example test runner that demonstrates the core framework.
 *
 * @author Philip Calouche
 *
 */
@RunWith(Cucumber.class)
@CucumberOptions(glue = { "classpath:" }, plugin = { "html:target/cucumber/testRunner", "json:target/cucumber/testRunner.json", "rerun:target/cucumber/testRunnerRerun.txt" }, features = { "classpath:features/" }, tags = {
		"~@pending", "@textHover" }, snippets = SnippetType.CAMELCASE)
public class TestRunner {

}