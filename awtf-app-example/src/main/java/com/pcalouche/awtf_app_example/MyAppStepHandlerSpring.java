package com.pcalouche.awtf_app_example;

import com.pcalouche.awtf_core.CoreStepHandlerSpring;
import com.pcalouche.awtf_core.StepsUtilSpring;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("myAppStepHandler")
public class MyAppStepHandlerSpring extends CoreStepHandlerSpring {
    @Autowired
    public MyAppStepHandlerSpring(StepsUtilSpring stepsUtil) {
        super(stepsUtil);
        logger = LogManager.getLogger();
    }

    //    public MyAppStepHandler() {
    //        super();
    //        logger = LogManager.getLogger();
    //    }

    @Override
    public void iTakeAScreenshot() {
        logger.info("My override for screenshot!!");
    }
}
