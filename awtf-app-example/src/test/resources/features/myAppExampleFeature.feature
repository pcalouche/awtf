Feature: Provide example of how core steps can be used in conjuction with your own steps, configuration, and message bundle

  @myAppExampleFeature
  Scenario: Demonstrate using the core framework in addition to code that is required for you app testing
    Given I go to the sign on page
    Then I input "Login ID" as "myLogin"
    Then I input "Password" as "myPassword"
    Then I click on "Sign On"
    Then I see the message "[welcomeText]"
