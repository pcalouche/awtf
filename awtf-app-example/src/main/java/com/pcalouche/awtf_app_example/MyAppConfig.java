package com.pcalouche.awtf_app_example;

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
//@ComponentScan(basePackages = {"com.pcalouche.awtf_app_example"})
@PropertySource("classpath:/testEnvironmentConfigs/test_environment_config.${testEnvironment:localhost2}.properties")
@PropertySource("classpath:/messages_en.properties")
public class MyAppConfig {
    private static final Logger logger = LoggerFactory.getLogger(MyAppConfig.class);
    @Autowired
    private Environment environment;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        logger.info(String.format("Detected testEnvironment was->%s", System.getenv("testEnvironment")));
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public MyAppTestInstance myAppTestInstance() {
        logger.info("in myAppTestInstance bean ");
        return new MyAppTestInstance(myAppTestEnvironmentConfig());
    }

    @Bean
    public MyAppStepsUtil myAppStepsUtil() {
        logger.info("in myAppStepsUtil bean ");
        return new MyAppStepsUtil(myAppTestInstance());
    }

    @Bean
    MyAppStepHandler myAppStepHandler() {
        logger.info("in myAppStepHandler bean ");
        return new MyAppStepHandler(myAppStepsUtil());
    }

    private MyAppTestEnvironmentConfig myAppTestEnvironmentConfig() {
        logger.info("In MyAppConfig myAppTestEnvironmentConfig");
        logger.info("browserType from env->" + environment.getProperty("browserType"));
        logger.info("disclaimer1 from env->" + environment.getProperty("disclaimer1"));
        logger.info("welcomeText from env->" + environment.getProperty("welcomeText"));
        BrowserType browserType = BrowserType.valueOf(environment.getProperty("browserType"));
        int secondsToWait = Integer.valueOf(environment.getProperty("secondsToWait"));
        String url = environment.getProperty("url");
        boolean screenshotBeforeClick = Boolean.valueOf(environment.getProperty("screenshotBeforeClick"));
        boolean screenshotOnScenarioCompletion = Boolean.valueOf(environment.getProperty("screenshotOnScenarioCompletion"));
        String loginID = environment.getProperty("loginID");
        String password = environment.getProperty("password");
        return new MyAppTestEnvironmentConfig(browserType, secondsToWait, url, screenshotBeforeClick,
                screenshotOnScenarioCompletion, loginID, password);
    }

}
