package com.pcalouche.awtf_app_example;

import com.pcalouche.awtf_core.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class demonstrates how TestInstance can be extended for your needs
 *
 * @author Philip Calouche
 */
public class MyAppTestInstance extends TestInstance {
    private static final Logger logger = LoggerFactory.getLogger(MyAppTestInstance.class);
    private final MyAppTestEnvironmentConfig myAppTestEnvironmentConfig;

    public MyAppTestInstance(MyAppTestEnvironmentConfig myAppTestEnvironmentConfig) {
        super(myAppTestEnvironmentConfig);
        this.myAppTestEnvironmentConfig = myAppTestEnvironmentConfig;
        logger.info("MyAppTestInstance constructor, browserType->" + this.testEnvironmentConfig.getBrowserType());
    }

    public MyAppTestEnvironmentConfig getMyAppTestEnvironmentConfig() {
        return myAppTestEnvironmentConfig;
    }

    @Override
    protected void loadApplicationConfig() {
        // Just calling super here, but this can be completely changed to do whatever you need
        super.loadApplicationConfig();
    }

    @Override
    protected void setupWebDriver() {
        // Just calling super here, but this can be completely changed to do whatever you need
        super.setupWebDriver();
    }
}
