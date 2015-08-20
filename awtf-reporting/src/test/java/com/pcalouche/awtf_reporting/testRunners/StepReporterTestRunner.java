package com.pcalouche.awtf_reporting.testRunners;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;

/**
 * This test runner will parse all features files that were copied in awtf-reporting's pom.xml from other cucumber test projects. Strict checking is turned on to catch typos in those features files or
 * flag any unimplemented steps that are throwing PendingException.
 *
 * @author Philip Calouche
 *
 */
@RunWith(Cucumber.class)
@CucumberOptions(glue = { "com.pcalouche.awtf_reporting" }, plugin = { "html:target/cucumber/stepReporterTestRunner", "json:target/cucumber/stepReporterTestRunner.json" }, features = { "classpath:" }, tags = {}, snippets = SnippetType.CAMELCASE, strict = true)
public class StepReporterTestRunner {

}