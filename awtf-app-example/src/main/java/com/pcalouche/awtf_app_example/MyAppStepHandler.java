package com.pcalouche.awtf_app_example;

import com.pcalouche.awtf_core.CoreStepHandler;
import cucumber.api.Scenario;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyAppStepHandler extends CoreStepHandler {
    private static final Logger logger = LoggerFactory.getLogger(MyAppStepHandler.class);
    private final MyAppTestEnvironmentConfig myAppTestEnvironmentConfig;
    private final MyAppTestInstance myAppTestInstance;
    private final MyAppStepsUtil myAppStepsUtil;

    public MyAppStepHandler(MyAppTestEnvironmentConfig myAppTestEnvironmentConfig,
                            MyAppTestInstance myAppTestInstance,
                            MyAppStepsUtil myAppStepsUtil) {
        super(myAppTestEnvironmentConfig, myAppTestInstance, myAppStepsUtil);
        this.myAppTestEnvironmentConfig = myAppTestEnvironmentConfig;
        this.myAppTestInstance = myAppStepsUtil.getMyAppTestInstance();
        this.myAppStepsUtil = myAppStepsUtil;
    }

    public MyAppTestInstance getMyAppTestInstance() {
        return myAppTestInstance;
    }

    @Override
    protected void handleScenarioSetup(Scenario scenario) {
        // This could be overridden with for your app's needs
        super.handleScenarioSetup(scenario);
    }

    @Override
    protected void handleScenarioTearDown(Scenario scenario) {
        try {
            myAppTestInstance.getStopWatch().stop();
            logger.info(String.format("Scenario: \"%s\" completed in %.3f seconds", scenario.getName(), myAppTestInstance.getStopWatch().getTime() / 1000.0));
            scenario.write(String.format("Completed in %.3f seconds.", myAppTestInstance.getStopWatch().getTime() / 1000.0));
            if (myAppTestInstance.getTestEnvironmentConfig().isScreenshotOnScenarioCompletion() || scenario.isFailed()) {
                iTakeAScreenshot();
            }
        } catch (Exception e) {
            logger.error("Failed to tearDown scenario", e);
        } finally {
            /**
             * For this application let me handle logout instead of just doing a page refresh like the core framework does.
             * Let's test if the logout button is visible first before logging out.
             */
            if (myAppTestInstance.getWebDriver().findElement(By.cssSelector("button[value='logout']")).isDisplayed()) {
                iClickOn("Logout");
                iSeeTheMessage("You are logged out");
            }
        }
    }

    @Override
    public void iTakeAScreenshot() {
        super.iTakeAScreenshot();
    }
}
