package com.pcalouche.awtf_app_example;

import com.pcalouche.awtf_core.util.appConfig.AppConfig;
import com.pcalouche.awtf_core.util.appConfig.AppElement;
import com.pcalouche.awtf_core.util.appConfig.AppElementLocatorType;
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
    public AppConfig appConfig() {
        logger.info("in appConfig bean for MyAppConfig");
        AppConfig appConfig = new AppConfig();
        // Global Locators
        appConfig.setLoadingIndicatorLocator(new AppElement("Loading Locator", ".load-mask-large, .load-mask-medium, .load-mask-small", AppElementLocatorType.css));
        appConfig.setModalLocator(new AppElement("Modal Locator", "//*[contains(@class,'modal')]", AppElementLocatorType.xpath));
        appConfig.setTooltipLocator(new AppElement("Tooltip Locator", ".//*[contains(@class, 'tooltip')]", AppElementLocatorType.xpath));
        return appConfig;
    }

    @Bean
    public MyAppTestInstance myAppTestInstance() {
        logger.info("in myAppTestInstance bean");
        return new MyAppTestInstance(myAppTestEnvironmentConfig(), appConfig());
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
        BrowserType browserType = BrowserType.valueOf(environment.getProperty("browserType"));
        int secondsToWait = Integer.valueOf(environment.getProperty("secondsToWait"));
        String url = environment.getProperty("url");
        boolean screenshotBeforeClick = Boolean.valueOf(environment.getProperty("screenshotBeforeClick"));
        boolean screenshotOnScenarioCompletion = Boolean.valueOf(environment.getProperty("screenshotOnScenarioCompletion"));
        String loginID = environment.getProperty("loginID");
        String password = environment.getProperty("password");
        return new MyAppTestEnvironmentConfig(browserType, secondsToWait, url, screenshotBeforeClick, screenshotOnScenarioCompletion, loginID, password);
    }

}
