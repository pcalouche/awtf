package com.pcalouche.awtf.demotests.config;

import com.pcalouche.awtf.core.service.CucumberScenarioService;
import org.openqa.selenium.WebDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * This config class is for creating beans for things needed from the
 * Test Automation Core framework.
 */
@Configuration
public class CoreFrameworkBeanConfig {

    @Bean
    @Scope(value = "cucumber-glue")
    public CucumberScenarioService cucumberScenarioService(WebDriver webDriver) {
        return new CucumberScenarioService(webDriver);
    }
}
