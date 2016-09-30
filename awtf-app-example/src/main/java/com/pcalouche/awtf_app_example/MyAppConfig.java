package com.pcalouche.awtf_app_example;

import com.pcalouche.awtf_core.util.appConfig.AppConfig;
import com.pcalouche.awtf_core.util.appConfig.AppElement;
import com.pcalouche.awtf_core.util.appConfig.AppElementLocatorType;
import com.pcalouche.awtf_core.util.enums.BrowserType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

@Configuration
@PropertySources({
        @PropertySource("classpath:/testEnvironmentConfigs/test_environment_config.${testEnvironment:localhost2}.properties"),
        @PropertySource("classpath:/messages_en.properties")
})
public class MyAppConfig {
    private static final Logger logger = LoggerFactory.getLogger(MyAppConfig.class);
    private final Environment environment;

    @Autowired
    public MyAppConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    @Scope(value = "cucumber-glue")
    public WebDriver webDriver() {
        // Set common desired capabilities for our browsers
        DesiredCapabilities desiredCapabilities;
        logger.info("in setupWebDriver->" + environment.getProperty("browserType"));
        BrowserType browserType = BrowserType.valueOf(environment.getProperty("browserType"));
        WebDriver webDriver = null;
        switch (browserType) {
            case phantomJS:
                desiredCapabilities = DesiredCapabilities.phantomjs();
                desiredCapabilities.setCapability("acceptSslCerts", true);
                desiredCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[]{"--web-security=no", "--ignore-ssl-errors=yes", "--webdriver-loglevel=NONE"});
                webDriver = new PhantomJSDriver(desiredCapabilities);
                break;
            case firefox:
                System.setProperty("webdriver.gecko.driver", "C:\\webdrivers\\geckodriver0.10.0.exe");
                desiredCapabilities = DesiredCapabilities.firefox();
//                desiredCapabilities.setCapability("marionette", true);
                webDriver = new FirefoxDriver(desiredCapabilities);
//                webDriver = new MarionetteDriver();
                break;
            case internetExplorer:
                desiredCapabilities = DesiredCapabilities.internetExplorer();
                webDriver = new InternetExplorerDriver(desiredCapabilities);
                break;
            case edge:
                desiredCapabilities = DesiredCapabilities.edge();
                webDriver = new EdgeDriver(desiredCapabilities);
                break;
            case chrome:
                desiredCapabilities = DesiredCapabilities.chrome();
                webDriver = new ChromeDriver(desiredCapabilities);
                break;
            default:
                break;
        }
        return webDriver;
    }

    @Bean
    public MyAppTestEnvironmentConfig myAppTestEnvironmentConfig() {
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

    @Bean
    public AppConfig appConfig() {
        AppConfig appConfig = new AppConfig();
        // Global Locators
        appConfig.setLoadingIndicatorLocator(new AppElement("Loading Locator", ".load-mask-large, .load-mask-medium, .load-mask-small", AppElementLocatorType.css));
        appConfig.setModalLocator(new AppElement("Modal Locator", "//*[contains(@class,'modal')]", AppElementLocatorType.xpath));
        appConfig.setTooltipLocator(new AppElement("Tooltip Locator", ".//*[contains(@class, 'tooltip')]", AppElementLocatorType.xpath));
        return appConfig;
    }

    @Bean
    @Scope(value = "cucumber-glue")
    public MyAppTestInstance myAppTestInstance(MyAppTestEnvironmentConfig myAppTestEnvironmentConfig,
                                               AppConfig appConfig,
                                               WebDriver webDriver) {
        return new MyAppTestInstance(myAppTestEnvironmentConfig, appConfig, webDriver);
    }

    @Bean
    @Scope(value = "cucumber-glue")
    public MyAppStepsUtil myAppStepsUtil(MyAppTestInstance myAppTestInstance) {
        return new MyAppStepsUtil(this.environment, myAppTestInstance);
    }

    @Bean
    @Scope(value = "cucumber-glue")
    MyAppStepHandler myAppStepHandler(MyAppTestEnvironmentConfig myAppTestEnvironmentConfig,
                                      MyAppTestInstance myAppTestInstance,
                                      MyAppStepsUtil myAppStepsUtil) {
        return new MyAppStepHandler(myAppTestEnvironmentConfig, myAppTestInstance, myAppStepsUtil);
    }


}
