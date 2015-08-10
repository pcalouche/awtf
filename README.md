# Automatic WebApp Testing Framework -- (README IS STILL IN PROGRESS)

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
- Ability to create multipe JUnit Test Runners to organize testing
- Allow for configuration of multiple browsers
- A configurable and extendable Test Environment object to allowing testing in multiple environments and storing custom data needed for those environments.

##Project Listing
- **awtf-core** - Contains the core of the framework
- **awtf-app-example** - An example of how to use the core framework and how to extend its existing steps and configuration to suit your application's testing needs

##Test Run Flow
This could be determined by looking at the code, but I believe this writeup will give more clarity.

1. It all starts in the Test Runner (e.g. TestRunner.java) which is run like a JUnit test.   You can create multiple ones to group your tests together however you want.  From it Cucumber Options are parsed to:
  1. Determine where to look for glue code.  Glue code tells the test runner where to look for Cucumber Hooks and Step Definitions.  It can be done to look at the entire class path or just in certain Java packages.
  2. Setup plugins for things like HTML reporting.
  3. Where to look for Cucumber Feature files.
  4. What Cucumber Scenarios the flagged Cucumber Feature files need to be run based on their Cucumber Tags.
2. Any methods in the Glue code path annotated with Cucumber's @Before is run before any Cucumber steps are run for the current Cucumber Scenario.  There is only one @Before method in the core framework which is the setup method in Hooks.java.  Some key things happen in this method:
  1. It first tests if TestInstance's static members have been setup.  If not then an instance is created with the following things happening in that constructor:
    1. If It tries to look for a "testEnvironment" string first as a system property and then as an environment property to determine what TestEnvironmentConfig YAML file should be loaded.  If one isn't found the string defaults to "localhost".
    2. The App Config is loaded for the web application.  See the section on App Config for more details.
    3. The CoreStepHandler and StepsUtil classes to be used are instantiated.  These can be extended and overridden to suit your application's test needs.  This is shown in the awtf-app-example project.
    4. The web driver to use (Internet Explorer, PhantonJS, etc.) is setup.  Your system must have those web drivers setup.
    5. From then on what was setup in the TestInstance constructor is accessed through static getters throughout the rest of the framework.
  2. If TestInstance was created for the first time, a RunTime shutdown hook is added to quit the created web driver at the end of the TestRunner's execution.  This is done to avoid re-creating this for each scenario which improves test run performance.
3. The setup method then does some miscellaneous setup like determining the max web driver wait timeout based on the Cucumber Scenario's tags, resetting a stop watch, and setting a reference to the current scenario. 
4. The step definitions for the Cucumber Scenario are executed.
5. Whether a scenario passes or fails the @After annotated methods in the Glue code are run.  There is only one @After method in the core framework which is the teardown method in Hooks.java.  This method stops the stop watch to record some timings, takes a final screenshot, and calls the readyWebAppForNextScenario method to reset the page for the next Cucumber Scenario.
6. Steps 1-4 repeat until there are no Cucumber Scenarios to run by the Test Runner.
7. When the last Cucumber Scenario has run the RunTime shutdown hook is run to close the running web driver instance.

NOTE: You can setup your Test Runners to use your own Hooks and ignore the ones in the core framework.  This shown in awtf-app-example project.

##TestEnvironmentConfig Details
This is a listing of some built in configuration options for this class used by the framework:
* **browser** - The browser to run your test with.  It must match a valid enum in BrowserType, and your system must have the web driver for that browser configured.
* **secondsToWait** - The default number of seconds to for the web driver to wait for an expected condition to happen (presence of an element, visibility of an element, element to be clickable, etc.) before timing out.  This can be increased on a per Cucumber Scenario basis.  See the WaitTag.java and the loadMask.feature for an example of this. 
* **url** - The initial URL of the web application.
* **screenshotBeforeClick** - Turn this on to take a screenshot before click.  With this on it can show a better pictoral history of a Cucumber Scenario.  It is off by default, because it can generate a lot of screenshots for some tests.
* **screenshotOnScenarioCompletion** - Turn this on to take a screenshot on completion of a Cucumber Scenario.  This is on by default.
* **coreStepHandlerClass** - The class to use for handilng Core Step.  Extend CoreStepHandler as needed if you need to change the default behavior.
* **stepsUtilClass** - The utility class to use for the Core Step Handler.  Extend StepsUtil as needed if you need to change the default behavior.

##App Config Details
TODO

##CoreStepHandler and StepsUtilClass
TODO

##WebDriver Setup
TODO

##Feature File Examples
Some feature files exist in the projects to demonstrate what can be done with the framework.  They are run against the sample HTML pages that are in the project.

There are some feature files are in the awtf-core project that demonstrate the usage of the core steps.  I believe their contents are self explanatory. They are:
- elementState.feature
- formInputAndVerifcation.feature
- loadMask.feature
- messages.feature
- modals.feature
- tables.feature
- tooltips.feature

There are feature files in the awtf-app-example project that demonstrate how to use a mixture of the core framework and steps for your own application. They are:
- myAppExampleFeature.feature
