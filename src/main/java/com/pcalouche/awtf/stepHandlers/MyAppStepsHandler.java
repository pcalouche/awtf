package com.pcalouche.awtf.stepHandlers;

import com.pcalouche.awtf.config.spring.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "cucumber-glue")
public class MyAppStepsHandler {
    private final TestInstance testInstance;

    @Autowired
    public MyAppStepsHandler(TestInstance testInstance) {
        this.testInstance = testInstance;
    }
}
