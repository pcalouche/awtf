package com.pcalouche.awtf.steps;

//public class MyAppSteps implements En {
//
//    public MyAppSteps() {
//        Then("^I do this step2$", () -> {
//            // Write code here that turns the phrase above into concrete actions
//            System.out.println("HERE!!!");
//        });
//    }
//}

import cucumber.api.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyAppSteps {
    private static final Logger logger = LoggerFactory.getLogger(MyAppSteps.class);

    @Then("^I do this step that is specific to my application$")
    public void iDoThisStepThatIsSpecificToMyApplication() throws Throwable {
        logger.info("Run something specific to my appliation here");
    }
}
