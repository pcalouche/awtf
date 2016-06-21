package com.pcalouche.awtf_core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan(basePackages = {"com.pcalouche.awtf_core"})
@PropertySource("classpath:/testEnvironmentConfigs/test_environment_config.${testEnvironment}.properties")
public class AwtfCoreConfig {
    private static final Logger logger = LoggerFactory.getLogger(AwtfCoreConfig.class);

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
        logger.info(String.format("Detected testEnvironment was->%s", System.getenv("testEnvironment")));
        return new PropertySourcesPlaceholderConfigurer();
    }
}
