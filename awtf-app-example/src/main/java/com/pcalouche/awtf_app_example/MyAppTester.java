package com.pcalouche.awtf_app_example;

import com.pcalouche.awtf_core.CoreStepHandlerSpring;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MyAppTester {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(MyAppConfig.class);
        //                TestEnvironmentConfigSpring testEnvironmentConfig = ctx.getBean("testEnvironmentConfigSpring", TestEnvironmentConfigSpring.class);
        //        CoreStepHandlerSpring coreStepHandler = ctx.getBean("coreStepHandler", CoreStepHandlerSpring.class);
        //        StepsUtilSpring stepsUtilSpring = ctx.getBean("coreStepsUtil", StepsUtilSpring.class);
        //        System.out.println(testEnvironmentConfig.getBrowserType());


        //        TestInstanceSpring testInstanceSpring = ctx.getBean("testInstance", TestInstanceSpring.class);
        //        System.out.println("coreStepHandlerTest->" + (testInstanceSpring.getCoreStepHandler() != null));
        //        System.out.println("stepsUtil->" + (testInstanceSpring.getStepsUtil() != null));
        //        System.out.println("coreAppConfig->" + (testInstanceSpring.getAppConfig() != null));

        CoreStepHandlerSpring coreStepHandler = ctx.getBean("myAppStepHandler", CoreStepHandlerSpring.class);
        coreStepHandler.iTakeAScreenshot();
    }
}
