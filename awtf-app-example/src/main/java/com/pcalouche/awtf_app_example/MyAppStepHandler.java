package com.pcalouche.awtf_app_example;

import com.pcalouche.awtf_core.CoreStepHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyAppStepHandler extends CoreStepHandler {
    private static final Logger logger = LoggerFactory.getLogger(MyAppStepHandler.class);
    private final MyAppStepsUtil myAppStepsUtil;
    private final MyAppTestInstance myAppTestInstance;
    private final MyAppTestEnvironmentConfig myAppTestEnvironmentConfig;

    public MyAppStepHandler(MyAppStepsUtil myAppStepsUtil) {
        super(myAppStepsUtil);
        this.myAppStepsUtil = myAppStepsUtil;
        this.myAppTestInstance = myAppStepsUtil.getMyAppTestInstance();
        this.myAppTestEnvironmentConfig = myAppStepsUtil.getMyAppTestInstance().getMyAppTestEnvironmentConfig();
    }

    public MyAppTestInstance getMyAppTestInstance() {
        return myAppTestInstance;
    }

    @Override
    public void iTakeAScreenshot() {
        super.iTakeAScreenshot();
        logger.info("My override for screenshot!!");
    }
}
