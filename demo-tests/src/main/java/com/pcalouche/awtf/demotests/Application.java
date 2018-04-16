package com.pcalouche.awtf.demotests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableConfigurationProperties
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(final String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        DefaultApplicationArguments applicationArguments = new DefaultApplicationArguments(args);
        if (applicationArguments.containsOption("pre-test-run")) {
            logger.info("in pre test run ");
        }
        if (applicationArguments.containsOption("post-test-run")) {
            logger.info("in post test run ");
        }
        SpringApplication.exit(context);
    }
}
