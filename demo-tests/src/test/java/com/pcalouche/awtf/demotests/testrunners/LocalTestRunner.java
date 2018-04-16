package com.pcalouche.awtf.demotests.testrunners;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        glue = {"com.pcalouche.awtf.demotests.steps"},
        features = {"classpath:features"},
        tags = {"@fileDownload"},
        plugin = {"pretty", "json:target/cucumber-json-reports/localTest.json", "html:target/cucumber-html-reports"},
        strict = true,
        snippets = SnippetType.CAMELCASE
)
public class LocalTestRunner {

}
