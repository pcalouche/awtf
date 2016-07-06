package com.pcalouche.awtf_app_example;

import com.pcalouche.awtf_core.TestInstance;
import com.pcalouche.awtf_core.util.appConfig.AppConfig;
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

    public MyAppTestInstance(MyAppTestEnvironmentConfig myAppTestEnvironmentConfig, AppConfig appConfig) {
        super(myAppTestEnvironmentConfig, appConfig);
        this.myAppTestEnvironmentConfig = myAppTestEnvironmentConfig;
        logger.info("MyAppTestInstance constructor, browserType->" + this.getMyAppTestEnvironmentConfig().getBrowserType());
    }

    public MyAppTestEnvironmentConfig getMyAppTestEnvironmentConfig() {
        return myAppTestEnvironmentConfig;
    }

    @Override
    protected void setupWebDriver() {
        // Just calling super here, but this can be completely changed to do whatever you need
        super.setupWebDriver();
    }
}
