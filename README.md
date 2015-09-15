# Automated WebApp Testing Framework

##Background
This is my attempt at an automatic web application testing framework that makes use of Cucumber and Selenium.  In my job I got the opportunity to learn automatic web application test.  I really liked learning what could be done with it and how much it can improve testing.  I have tried to create a framework that serves as a good starting point to do automated UI testing on any type of web application.  When learning these tools, I often ran across a lot of good examples, but not a lot in the way of putting it all together in a generic manner that any web application could use.

Some of the features this has that allows for that are listed below:
- Create steps to handle things common to many web applications such as:
  - Form input and validation
  - Verifying the state of elements (visible, hidden, enabled, and disabled)
  - Detecting the presence and non-presence of text and messages on screen
  - Verifying data in tables and perform table row actions such as selection and expansion
  - Working with Modals
  - Working with Tooltips
- Ability to easily override the behavior of existing steps and step utility code to suit one's application.
- Ability to create multiple JUnit Test Runners to organize testing
- Allow for configuration of multiple browsers
- A configurable and extendable Test Environment object to allowing testing in multiple environments and storing custom data needed for those environments.

##Project Listing
- **awtf-core** - Contains the core of the framework
- **awtf-app-example** - An example of how to use the core framework and how to extend its existing steps and configuration to suit your application's testing needs
- **awtf-reporting** - A reporting tool for tracking all the Cucumber Steps used in your project.  Generates a report showing how often a step was used along with usage and example details.

##Test Run Flow
This could be determined by looking at the code, but I believe this write-up will give more clarity.

1. It all starts in the Test Runner (e.g. TestRunner.java) which is run like a JUnit test.   You can create multiple ones to group your tests together however you want.  From it Cucumber Options are parsed to:
  1. Determine where to look for glue code.  Glue code tells the test runner where to look for Cucumber Hooks and Step Definitions.  It can be done to look at the entire class path or just in certain Java packages.
  2. Setup plugins for things like HTML reporting.
  3. Where to look for Cucumber Feature files.
  4. What Cucumber Scenarios the flagged Cucumber Feature files need to be run based on their Cucumber Tags.
2. Any methods in the Glue code path annotated with Cucumber's @Before is run before any Cucumber steps are run for the current Cucumber Scenario.  There is only one @Before method in the core framework which is the setup method in Hooks.java.  Some key things happen in this method:
  1. It first tests if TestInstance's static members have been setup.  If not then an instance is created with the following things happening in that constructor:
    1. If it tries to look for a "testEnvironment" string first as a system property and then as an environment property to determine what TestEnvironmentConfig YAML file should be loaded.  If one isn't found the string defaults to "localhost".
    2. The App Config is loaded for the web application.  See the section on App Config for more details.
    3. The CoreStepHandler and StepsUtil classes to be used are instantiated.  These can be extended and overridden to suit your application's test needs.  This is shown in the awtf-app-example project.
    4. The web driver to use (Internet Explorer, PhantomJS, etc.) is setup.  Your system must have those web drivers setup.
    5. From then on what was setup in the TestInstance constructor is accessed through static getters throughout the rest of the framework.
  2. If TestInstance was created for the first time, a RunTime shutdown hook is added to quit the created web driver at the end of the TestRunner's execution.  This is done to avoid re-creating this for each scenario which improves test run performance.
3. The setup method then does some miscellaneous setup like determining the max web driver wait timeout based on the Cucumber Scenario's tags, resetting a stop watch, and setting a reference to the current scenario.
4. The step definitions for the Cucumber Scenario are executed.
5. Whether a scenario passes or fails the @After annotated methods in the Glue code are run.  There is only one @After method in the core framework which is the teardown method in Hooks.java.  This method stops the stop watch to record some timings, takes a final screenshot, and calls the readyWebAppForNextScenario method to reset the page for the next Cucumber Scenario.
6. Steps 1-4 repeat until there are no Cucumber Scenarios to run by the Test Runner.
7. When the last Cucumber Scenario has run the RunTime shutdown hook is run to close the running web driver instance.

NOTE: You can setup your Test Runners to use your own Hooks and ignore the ones in the core framework.  This shown in awtf-app-example project.

##Test Environment Config Details
The AWTF uses TestEnvironmentConfig.java to manage configuration properties for a given test environment.  These are read in from YAML files.  Any number TestEnvironmentConfig YAML files can be maintained for testing.  For instance, there could be a test environment a local, integration, or production environment.  A “testEnvironment” variable must be set as either a System property (think Java’s System.getProperty method) or as an environment property (think Java’s System.getenv method and Eclipse’s Run Configuration Environment variables).

Maven System Property Example:
```
mvn -DargLine="-DtestEnvironment=localhost"  -Dtest=TestRunner 
```

If not it defaults to “localhost”.  The AWTF then looks for a YAML file in the resources folder by the name of TestEnvironmentConfig.[yourTestEnvironmentValue].yml.

The following is a listing of some built in configuration options for this class used by the framework:
* **browser** - The browser to run your test with.  It must match a valid enumerated type in BrowserType, and your system must have the web driver for that browser configured.
* **secondsToWait** - The default number of seconds to for the web driver to wait for an expected condition to happen (presence of an element, visibility of an element, element to be clickable, etc.) before timing out.  This can be increased on a per Cucumber Scenario basis.  See the WaitTag.java and the loadMask.feature for an example of this.
* **url** - The initial URL of the web application.
* **screenshotBeforeClick** - Turn this on to take a screenshot before click.  With this on it can show a better pictorial history of a Cucumber Scenario.  It is off by default, because it can generate a lot of screenshots for some tests.
* **screenshotOnScenarioCompletion** - Turn this on to take a screenshot on completion of a Cucumber Scenario.  This is on by default.
* **coreStepHandlerClass** - The class to use for handling Core Step.  Extend CoreStepHandler as needed if you need to change the default behavior.
* **stepsUtilClass** - The utility class to use for the Core Step Handler.  Extend StepsUtil as needed if you need to change the default behavior.

The loading behavior can be overridden as you wish.  Refer to MyAppTestInstance and MyAppTestEnvironmentConfig for an example of how this can be done. 

##AppConfig Details
This class configuration specific to you specific webapp.  Not everything is required to be configured, but if you use steps that rely on something that needs to be set in the AppConfig they likely will not work correctly. 

The AppConfig lives under /test/resources/yaml/appConfig.yml.  AppConfigDriver provides an example of how to configure the AppConfig.  In it the AppElements lists and other AppConfig properties are set.  AppConfigDriver can be updated and then run to update your appConfig.yml.

The AppElement class represents a way to help the steps locate elements that are specific to your app.  Steps will parse the description given and if that description is surrounded by two brackets “[]” it will look in the AppConfig for that element by the given description.  See the example feature files and step implementations for more details.

##CoreStepHandler and StepsUtilClass Details
These classes handle the default logic for running the core steps.  CoreStepHandler contains methods for handling each step definition, and StepUtilClass contains helper methods for thing like locating form elements based on label or handling input for form elements.

The default methods for these classes can be overridden by extending these classes and specifying the class in coreStepHandlerClass and stepsUtilClass in the Test Environment Config.

MyAppStepHandler and MyAppStepsUtil in awtf-app-example provides an example of this.  

##WebDriver Setup
The framework has a BrowserType enumerated type for testing with different browsers/web drivers, but has not be tested with all of them.  Configurations may need to differ for your environment.  The framework can be extended to handle how you want your browser and web driver to be configured.  Here are some notes of what was tested with this framework:

1. PhantomJS - Downloaded PhantomJS web driver and added its .exe to the system PATH.  Runs well.
2. Firefox - No web driver exe needed.  Have later version of Firefox up to version 40.  Runs well.
3. Internet Explorer - Downloaded internet explorer web driver and added its .exe to the system PATH.  Worked for 32bit and 64bit versions of the web driver.  Runs well.  Sometimes security settings in Internet Explorer may need to be updated in order for it work.  There is online help for this.
4. Edge - Have had success getting the Edge web driver to start up and attempt to run the features files, but some web driver commands that the framework uses do not seem to be supported yet.  I believe this is still a work in progress for Microsoft.
5. Safari - Do not have a Mac to test this.
6. Chrome - Downloaded chrome web driver and added its .exe to the system PATH.  Runs well.

To reiterate, the code that handles browser and web driver setup and setup can be overridden for your testing needs.  What is currently in the framework just serves as one way to do it.

##Feature File Examples
Some feature files exist in the projects to demonstrate what can be done with the framework.  They are run against the sample HTML pages that are in the project.

There are some feature files are in the awtf-core project that demonstrate the usage of the core steps.  I believe their contents are self-explanatory. They are:
- elementState.feature
- formInputAndVerifcation.feature
- loadMask.feature
- messages.feature
- modals.feature
- tables.feature
- tooltips.feature

There are feature files in the awtf-app-example project that demonstrate how to use a mixture of the core framework and steps for your own application. They are:
- myAppExampleFeature.feature

##AWTF Reporting
The AWTF Reporting project produces a report showing information about each step that was used in your feature files.  This includes usage of the CoreSteps and Steps you defined for your app.  In order to create a report for your steps, the steps must be stubbed out in the AWTF Reporting project that calls a track method.  The data can contain HTML since that is what will go in the report.

AwtfReporterTestRunner.java can run as a JUnit test or the AWTF Reporting project can be run as a Maven test to generate the report which will be under target/awtfReport/awtfReport.html.

```java
public class CoreSteps {

  @Then("^I take a screenshot$")
  public void iTakeAScreenshot() {
    Reporter.track("^I take a screenshot$", "This is used to explicitly take a screenshot.", "Then I take a screenshot", "CoreSteps.java", 1);
  }

public class MyAppSteps {

  @Given("^I go to the sign on page$")
  public void iGoToTheSignOnPage() {
    Reporter.track("^I go to the sign on page$", "This is used to launch sign on page for my application.", "Given I go to the sign on page", "MyAppSteps.java", 26);
  }
```
