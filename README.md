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
- A configurable and extendable Test Environment object to allowing testing in multiple environments and storing custom data
needed for those environments.

##Project Listing
- **awtf-core** - Contains the core of the framework
- **awtf-app-example** - An example of how to use the core framework and how to extend its existing steps and configuration to suit your application's testing needs

##Test Run Flow
This could be determined by looking at the code, but I believe this writeup will give more clarity.

1. It all starts in the Test Runner (e.g. TestRunner.java) which is run like a JUnit test.   You can create multiple ones to group your tests together however you want.  From it Cucumber Options are parsed to:
  1. Determine where to look for glue code.  Glue code tells the test runner where to look for Cucumber Hooks and Step Definitions.  It can be done to look at the entire classpath or just in certain Java packages.
  2. Setup plugins for things like HTML reporting.
  3. Where to look for Feature files.
  4. What Scenarios in those Feature files need to be run or not run based on Tags.
2. Any methods in the Glue code path annotated with Cucumber's @Before is run before any Cucubmer steps are run for the current Scenario.  There is only one @Before method in the core framework which is in Hooks.java.  You can setup your Test Runners to use your own Hooks and ignore the ones in the core framework.  This shown in awtf-app-example project.
  1. Inside of Hooks.java an instance of TestInstance is created.  Some key things happen in this constructor:
    1. It tries to look for a "testEnvironment" string first as a system property and then as an environment property to determine what TestEnvironmentConfig YAML file should be loaded.  If one isn't found the string defaults to "localhost".
    2. The App Config is loading for the web application.  See that section for more details.
    3. The CoreStepHandler and StepsUtil classes to be used are instantiated.  These can be extended and overridden to suit your application's test needs.  This is shown in the awtf-app-example project.
    3. Much of what is setup in the TestInstance constructor are static member that are accessed using getters in the rest of the framework.
    4. The webdriver to use (Internet Explorer, PhantonJS, etc.) is setup.  Your system must have those web drivers available in order for this to work.  See the WebDriver section for more details.
2. The setup method is then called on the TestInstance object that was created which sets up some other things before the scenario is run.
3. The step definitions for the Scenario are executed.
4. Whether a scenario passes or fails the @After annoted methods in the Glue code are run.  There is only one @After method in the core framework which is in Hooks.java.  This method calls the TestInstance's teardown method to record some timings, take a final screenshot, and close the web driver to get ready for the next Scenario.
5. Steps 1-4 repeat for the next Scenario until there are not more flagged Scenarios to run by the Test Runner.

##TestEnvironmentConfig Details
TODO

##AppConfig Details
TODO

##WebDriver Setup

##Feature File Examples
Some feature files exist in the projects to demonstrate what can be done with the framework.  They are run against the sample HTML pages that are in the project.

There are some feature files are in the awtf-core project that demonstrate the usage of the core steps.  I believe their 
contains are self explanatory. They are:
- elementState.feature
- formInputAndVerifcation.feature
- loadMask.feature
- messages.feature
- modals.feature
- tables.feature
- tooltips.feature

There are feature files in the awtf-app-example project that demonstrate how to use a mixture of the core framework and steps 
for your own applcation. They are:
- myAppExampleFeature.feature
