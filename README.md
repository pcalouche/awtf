# Automatic WebApp Testing Framework -- (README IS STILL IN PROGRESS)

##Background
This is my attempt at an automatic web application testing framework that makes use of Cucumber and Selenium.  In my job I got 
the opportunity to learn automatic web application test.  I really liked learning what could be done with it and how much it can
improve testing.  I have tried to create a framework that serves as a good starting point to do automated UI testing on any
type of web application.  When learning these tools, I often ran across a lot of good examples, but not a lot in the way of 
putting it all together in a generic manner that any web application could make use of.

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
- **awtf-app-example** - An example of how to use the framework and how to extend upon the core framework's existing steps and
configuration to suit needs

##Flow of a Test Scenario
TODO

##Config Options
TODO

##Configuring Elements
TODO

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
