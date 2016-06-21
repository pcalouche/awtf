package com.pcalouche.awtf_core.stepDefinitions;

import com.pcalouche.awtf_core.AwtfCoreConfig;
import com.pcalouche.awtf_core.TestEnvironmentConfigSpring;
import cucumber.api.java.en.Given;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

//@Component("demoOnlySteps")
//@ContextConfiguration({"classpath*:cucumber.xml"})
//@ContextConfiguration(AwtfCoreConfig.class)
//@StepDefAnnotation
//@ContextHierarchy({
@ContextConfiguration(classes = AwtfCoreConfig.class)
//})
public class DemoOnlySteps {
    protected static Logger logger = LogManager.getLogger();
    //    @Autowired
//    private TestInstanceSpring testInstance;
//    @Autowired
//    @Qualifier("work")
    private final TestEnvironmentConfigSpring testEnvironmentConfig;

    //    public DemoOnlySteps() {
//    }
//    public DemoOnlySteps() {
//        logger.info("in no arg ");
//    }

    @Autowired
    public DemoOnlySteps(TestEnvironmentConfigSpring testEnvironmentConfig) {
//    public DemoOnlySteps(TestInstanceSpring testInstance) {
        logger.info("in arg ");
        this.testEnvironmentConfig = testEnvironmentConfig;
//        logger.info("!!!! xxx->" + testEnvironmentConfig.getBrowserType());
//        this.testInstance = testInstance;
//        logger.info("Done with DemoOnlySteps constructor->" + testInstance.getTestEnvironmentConfig().getBrowserType());
    }


    @Given("^I go to the demo page$")
    public void iGoToTheDemoPage() {
        logger.info("!!!! xxx->" + testEnvironmentConfig.getBrowserType());
//        logger.info("file:///" + System.getProperty("user.dir") + testInstance.getTestEnvironmentConfig().getUrl());
//        testInstance.getWebDriver().get("file:///" + System.getProperty("user.dir") + testInstance.getTestEnvironmentConfig().getUrl());
    }
}
