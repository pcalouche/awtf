package com.pcalouche.awtf_core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
@ComponentScan("com.pcalouche.awtf_core")
public class AwtfCoreConfig {
    private static final Logger logger = LogManager.getLogger();

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
        String testEnvironment;
        if (System.getProperty("testEnvironment") != null) {
            testEnvironment = System.getProperty("testEnvironment");
            logger.info("Test environment received from System Property as: " + testEnvironment);
        } else if (System.getenv("testEnvironment") != null) {
            testEnvironment = System.getenv("testEnvironment");
            logger.info("Test environment received from environment variable as: " + testEnvironment);
        } else {
            logger.info("Test environment not specified in Command Line or environment variable, defaulting to localhost test environment");
            testEnvironment = "localhost";
        }
        ClassPathResource testEnvironmentConfig = new ClassPathResource(String.format("/testEnvironmentConfigs/test_environment_config.%s.properties", testEnvironment));
        pspc.setLocations(testEnvironmentConfig);
        return pspc;
    }
}
