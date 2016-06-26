package com.pcalouche.awtf_app_example;

import com.pcalouche.awtf_core.CoreStepHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("myAppStepHandler")
public class MyAppStepHandler extends CoreStepHandler {
    private static final Logger logger = LoggerFactory.getLogger(MyAppStepHandler.class);
    private final MyAppTestInstance myAppTestInstance;
    private final MyAppStepsUtil myAppStepsUtil;

    @Autowired
    public MyAppStepHandler(MyAppTestInstance myAppTestInstance, MyAppStepsUtil myAppStepsUtil) {
        super(myAppTestInstance, myAppStepsUtil);
        this.myAppTestInstance = myAppTestInstance;
        this.myAppStepsUtil = myAppStepsUtil;
    }

    @Override
    public void iTakeAScreenshot() {
        super.iTakeAScreenshot();
        logger.info("My override for screenshot!!");
    }
}
