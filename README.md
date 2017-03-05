# Automated WebApp Testing Framework

##Background
This is my attempt at an automatic web application testing framework that makes use of Cucumber and Selenium.  In my job I got the opportunity to learn automatic web application test.  I really liked learning what could be done with it and how much it can improve testing.  I have tried to create a framework that serves as a good starting point to do automated UI testing on any type of web application.  When learning these tools, I often ran across a lot of good examples, but not a lot in the way of putting it all together in a generic manner that any web application could use.  Most recently this framework was updated to use Spring to help with configuration and dependency injection.

Some of the features this has that allows for that are listed below:
- Create steps to handle things common to many web applications such as:
  - Form input and validation
  - Verifying the state of elements (visible, hidden, enabled, and disabled)
  - Detecting the presence and non-presence of text and messages on screen
  - Verifying data in tables and perform table row actions such as selection and expansion
  - Working with Modals
  - Working with Tooltips
- Ability to create multiple JUnit Test Runners to organize testing
- Allow for configuration of multiple browsers
- A configurable and extensible Test Environment object to allowing testing in multiple environments and storing custom data needed for those environments.

##Test Run Flow
This could be determined by looking at the code, but I believe this write-up will give more clarity.

1. It all starts in the Test Runner (e.g. TestRunner.java) which is run like a JUnit test.   You can create multiple ones to group your tests together however you want.  From it Cucumber Options are parsed to:
    1. Determine where to look for glue code.  Glue code tells the test runner where to look for Cucumber Hooks and Step Definitions.  It can be done to look at the entire class path or just in certain Java packages.
    2. Setup plugins for things like HTML reporting.
    3. Where to look for Cucumber Feature files.
    4. What Cucumber Scenarios the flagged Cucumber Feature files need to be run based on their Cucumber Tags.
2. Any methods in the Glue code path annotated with Cucumber's @Before is run before any Cucumber steps are run for the current Cucumber Scenario.
    1. Hooks is the only class that has a Cucumber @Before annotation.  Hooks classes also need to be given a ContextConfiguration so Spring can create the created beans from your configuration.
    ```java
     @ContextHierarchy({
             @ContextConfiguration(classes = {CoreConfig.class})
     })
     public class Hooks {
         private static final Logger logger = LoggerFactory.getLogger(Hooks.class);
         private final TestInstance testInstance;
     
         public Hooks(TestInstance testInstance) {
             this.testInstance = testInstance;
         }
     
         @Before
         public void setup(Scenario scenario) {
           // Omitted for brevity
         }
     
         @After
         public void tearDown(Scenario scenario) {
           // Omitted for brevity
         }
     }
    ```
    2. CoreConfig is contains all the Spring configuration to setup the test framework.  After it complements the following things in CoreConfig are now available for the test run:
        1. A Spring Environment instance that contains access to the key value pairs from the supplied properties files.
        2. A configured component scan for adding additional Spring components you may want such as Step Handlers and Utils classes. 
        3. A TestEnvironmentConfig that was configured from the supplied test environment properties file.
        4. A WebDriver that was configured based on the test environment properties file.
        5. An AppConfig that can be used to looking up element locators specific to your app.
        6. A TestInstance which consists of a TestEnvironmentConfig, AppConfig, and WebDriver and has methods to interact with the Selenium Web Driver instance.
4. Now the feature files from the test runner are run against the web app using the instantiated test environment and step definitions.
5. Whether a scenario passes or fails the @After annotated methods in the Glue code are run.  There is only one @After method in the core framework which is the tear down method in Hooks.java.
6. Steps 1-4 repeat until there are no Cucumber Scenarios to run by the Test Runner.

##Test Environment Config Details
The AWTF uses TestEnvironmentConfig bean to manage configuration properties for a given test environment.  The Spring Configuration should loads it from a properties file based on a matching properties filename.  In CoreConfig.java a "testEnvironment" system or environment variable can be used to resolve the properties filename.  Using this convention isn't required, but it does allow for an easy way manage properties files for whatever test environments the automated tests are run in (e.g. localhost, dev, team, prod, etc.).

```java
@Configuration
@PropertySources({
        @PropertySource("classpath:/testEnvironmentConfigs/test_environment_config.${testEnvironment:localhost}.properties"),
        @PropertySource("classpath:/messages_en.properties")
})
public class CoreConfig {
     // Omitted for brevity
}
```
When testing in my IDE (IntelliJ/Eclipse) I will set an environment variable on the run configuration.

When running from Maven I will set a system property like so:
```
mvn test -DargLine="-DtestEnvironment=localhost"
```

If a "testEnvironment" isn't found it defaults to “localhost”.  Other properties files can be used for things like storing expected message or text in your web app.

The following is a listing of some built in configuration options for this class used by the framework:
* **browserType** - The browser to run your test with.  It must match a valid enumerated type in BrowserType.  The open sources web drivers are included in the framework to reduce additional configuration.
* **runRemote** - Set to true to run the tests against a Selenium grid.  This is expected to be configured accordingly.
* **seleniumGridUrl** - The URL of the Selenium grid to use if running against remote web drivers.
* **secondsToWait** - The default number of seconds to for the web driver to wait for an expected condition to happen (presence of an element, visibility of an element, element to be clickable, etc.) before timing out.  This can be increased on a per Cucumber Scenario basis.  See the WaitTag.java and the loadMask.feature for an example of this.
* **url** - The initial URL of the web application.
* **screenshotBeforeClick** - Turn this on to take a screenshot before click.  With this on it can show a better pictorial history of a Cucumber Scenario.  It is off by default, because it can generate a lot of screenshots for some tests.
* **screenshotOnScenarioCompletion** - Turn this on to take a screenshot on completion of a Cucumber Scenario.  This is on by default.

##AppConfig Details
This class configuration specific to you specific web app.  Not everything is required to be configured, but if you use steps that rely on something that needs to be set in the AppConfig they likely will not work correctly.

The AppConfig lives in CoreConfig, but nothing stops you from creating it as a bean in a separating class.  AppConfigDriver provides an example of how to configure the AppConfig.  In it the AppElements lists and other AppConfig properties are set.

The AppElement class represents a way to help the steps locate elements that are specific to your app.  Steps will parse the description given and if that description is surrounded by two brackets “[]” it will look in the AppConfig for that element by the given description.  See the example feature files and step implementations for more details.

##Feature File Examples
Some feature files exist in the projects to demonstrate what can be done with the framework.  They are run against the sample HTML pages that are in the project.

There are some feature files are in the AWTF Project that demonstrate the usage of the core steps.  I believe their contents are self-explanatory. They are:
- elementState.feature
- formInputAndVerification.feature
- loadMask.feature
- messages.feature
- modals.feature
- tables.feature
- tooltips.feature