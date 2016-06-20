package com.pcalouche.awtf_core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainTester {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AwtfCoreConfig.class);

        //        TestEnvironmentConfigSpring testInstanceSpring = ctx.getBean("A", TestEnvironmentConfigSpring.class);
        //        TestEnvironmentConfigSpring testInstanceSpring1 = ctx.getBean("B", TestEnvironmentConfigSpring.class);

        TestEnvironmentConfigSpring testEnvironmentConfig = ctx.getBean("testEnvironmentConfigSpring", TestEnvironmentConfigSpring.class);
        //        CoreStepHandlerSpring coreStepHandler = ctx.getBean("coreStepHandler", CoreStepHandlerSpring.class);
        //        StepsUtilSpring stepsUtilSpring = ctx.getBean("coreStepsUtil", StepsUtilSpring.class);
        System.out.println(testEnvironmentConfig.getBrowserType());
        TestInstanceSpring testInstanceSpring = ctx.getBean("testInstance", TestInstanceSpring.class);
        //        System.out.println("coreStepHandlerTest->" + (testInstanceSpring.getCoreStepHandler() != null));
    }
}
