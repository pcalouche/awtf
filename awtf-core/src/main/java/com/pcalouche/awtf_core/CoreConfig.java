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
@PropertySource("classpath:/testEnvironmentConfigs/test_environment_config.${testEnvironment:localhost}.properties")
public class CoreConfig {
    private static final Logger logger = LoggerFactory.getLogger(CoreConfig.class);

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        logger.info(String.format("Detected testEnvironment was->%s", System.getenv("testEnvironment")));
        return new PropertySourcesPlaceholderConfigurer();
    }
}
