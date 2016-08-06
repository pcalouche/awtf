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
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

@Configuration
@PropertySources({
        @PropertySource("classpath:/testEnvironmentConfigs/test_environment_config.${testEnvironment:localhost2}.properties"),
        @PropertySource("classpath:/messages_en.properties")
})
public class MyAppConfig {
    private static final Logger logger = LoggerFactory.getLogger(MyAppConfig.class);
    @Autowired
    private Environment environment;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public MyAppTestInstance myAppTestInstance() {
        return new MyAppTestInstance(myAppTestEnvironmentConfig(), appConfig());
    }

    @Bean
    public MyAppStepsUtil myAppStepsUtil() {
        return new MyAppStepsUtil(myAppTestInstance());
    }

    @Bean
    MyAppStepHandler myAppStepHandler() {
        return new MyAppStepHandler(myAppTestEnvironmentConfig(), myAppTestInstance(), myAppStepsUtil());
    }

    private MyAppTestEnvironmentConfig myAppTestEnvironmentConfig() {
        logger.info("Detected test environment was->" + environment.getProperty("testEnvironment"));
        BrowserType browserType = BrowserType.valueOf(environment.getProperty("browserType"));
        int secondsToWait = Integer.valueOf(environment.getProperty("secondsToWait"));
        String url = environment.getProperty("url");
        boolean screenshotBeforeClick = Boolean.valueOf(environment.getProperty("screenshotBeforeClick"));
        boolean screenshotOnScenarioCompletion = Boolean.valueOf(environment.getProperty("screenshotOnScenarioCompletion"));
        String loginID = environment.getProperty("loginID");
        String password = environment.getProperty("password");
        return new MyAppTestEnvironmentConfig(browserType, secondsToWait, url, screenshotBeforeClick, screenshotOnScenarioCompletion, loginID, password);
    }

    private AppConfig appConfig() {
        AppConfig appConfig = new AppConfig();
        // Global Locators
        appConfig.setLoadingIndicatorLocator(new AppElement("Loading Locator", ".load-mask-large, .load-mask-medium, .load-mask-small", AppElementLocatorType.css));
        appConfig.setModalLocator(new AppElement("Modal Locator", "//*[contains(@class,'modal')]", AppElementLocatorType.xpath));
        appConfig.setTooltipLocator(new AppElement("Tooltip Locator", ".//*[contains(@class, 'tooltip')]", AppElementLocatorType.xpath));
        return appConfig;
    }
}
