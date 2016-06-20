package com.pcalouche.awtf_app_example.stepDefinitions;

import com.pcalouche.awtf_core.CoreStepHandlerSpring;
import com.pcalouche.awtf_core.stepDefinitions.CoreSteps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("myCoreSteps")
public class MyCoreSteps extends CoreSteps {
    @Autowired
    public MyCoreSteps(CoreStepHandlerSpring stepHandler) {
        super(stepHandler);
    }
}
