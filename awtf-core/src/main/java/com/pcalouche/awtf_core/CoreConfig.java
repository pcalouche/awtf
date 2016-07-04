package com.pcalouche.awtf_core;

import com.pcalouche.awtf_core.util.enums.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

@Configuration
//@ComponentScan(basePackages = {"com.pcalouche.awtf_core"})
@PropertySource("classpath:/testEnvironmentConfigs/test_environment_config.${testEnvironment:localhost}.properties")
@PropertySource("classpath:/messages_en.properties")
public class CoreConfig {
    private static final Logger logger = LoggerFactory.getLogger(CoreConfig.class);
    @Autowired
    private Environment environment;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        logger.info(String.format("Detected testEnvironment was->%s", System.getenv("testEnvironment")));
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public TestInstance testInstance() {
        logger.info("in testInstance bean");
        return new TestInstance(testEnvironmentConfig());
    }

    @Bean
    public CoreStepsUtil coreStepsUtil() {
        logger.info("in coreStepsUtil bean");
        return new CoreStepsUtil(testInstance());
    }

    @Bean
    public CoreStepHandler coreStepHandler() {
        logger.info("in coreStepHandler bean");
        return new CoreStepHandler(coreStepsUtil());
    }

    private TestEnvironmentConfig testEnvironmentConfig() {
        logger.info("In CoreConfig testEnvironmentConfig");
        logger.info("browserType from env->" + environment.getProperty("browserType"));
        logger.info("disclaimer1 from env->" + environment.getProperty("disclaimer1"));
        BrowserType browserType = BrowserType.valueOf(environment.getProperty("browserType"));
        int secondsToWait = Integer.valueOf(environment.getProperty("secondsToWait"));
        String url = environment.getProperty("url");
        boolean screenshotBeforeClick = Boolean.valueOf(environment.getProperty("screenshotBeforeClick"));
        boolean screenshotOnScenarioCompletion = Boolean.valueOf(environment.getProperty("screenshotOnScenarioCompletion"));
        return new TestEnvironmentConfig(browserType, secondsToWait, url, screenshotBeforeClick, screenshotOnScenarioCompletion);
    }
}
