package com.pcalouche.awtf_core;

import com.pcalouche.awtf_core.util.enums.BrowserType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
//@EnableWebMvc
@ComponentScan(basePackages = {"com.pcalouche.awtf_core", "com.pcalouche.awtf_core.stepDefinitions"})
//@ImportResource(value = {"classpath*:/context.xml"})

@PropertySource("classpath:/testEnvironmentConfigs/test_environment_config.${testEnvironment}.properties")
public class AwtfCoreConfig {
    private static final Logger logger = LogManager.getLogger();

//    @Autowired
//    private Environment environment;
//    @Value("${browserType}")
//    private BrowserType browserType;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
//        logger.info("resolving properties");
//        PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
//        String testEnvironment;
//        if (System.getProperty("testEnvironment") != null) {
//            testEnvironment = System.getProperty("testEnvironment");
//            logger.info("Test environment received from System Property as: " + testEnvironment);
//        } else if (System.getenv("testEnvironment") != null) {
//            testEnvironment = System.getenv("testEnvironment");
//            logger.info("Test environment received from environment variable as: " + testEnvironment);
//        } else {
//            logger.info("Test environment not specified in Command Line or environment variable, defaulting to localhost test environment");
//            testEnvironment = "localhost";
//        }
//        ClassPathResource testEnvironmentConfig = new ClassPathResource(String.format("/testEnvironmentConfigs/test_environment_config.%s.properties", testEnvironment));
//        pspc.setLocations(testEnvironmentConfig);
//        return pspc;
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public TestEnvironmentConfigSpring testEnvironmentConfig(@Value("${browserType}") BrowserType browserType,
                                                             @Value("${secondsToWait}") int secondsToWait,
                                                             @Value("${url}") String url,
                                                             @Value("${screenshotBeforeClick}") boolean screenshotBeforeClick,
                                                             @Value("${screenshotOnScenarioCompletion}") boolean screenshotOnScenarioCompletion) {
        logger.info("here->" + browserType);
        logger.info("here->" + secondsToWait);
//        logger.info(environment.getProperty("testEnvironment"));
//        logger.info(environment.getProperty("browserType"));
//        logger.info(environment.getProperty("url"));
        return new TestEnvironmentConfigSpring(browserType, secondsToWait, url, screenshotBeforeClick, screenshotOnScenarioCompletion);
    }

//    @Bean
//    public TestInstanceSpring testInstance() {
//        return new TestInstanceSpring(this.testEnvironmentConfig());
//    }
}
