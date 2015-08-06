Feature: Provide example of how core steps can be used in conjuction with your own steps, configuration, and message bundle

  @myAppExampleFeature
  Scenario: Demonstrate using the core framework in addition to code that is required for you app testing
    Given I go to the sign on page
    Then I sign into my application
    Then I click on "Sign On"
    Then I see the message "[welcomeText]"

  @myAppExampleFeature
  Scenario: Another demonstration of using the core framework in addition to code that is required for you app testing
    Given I go to the sign on page
    Then I sign into my application
    Then I click on "Sign On"
    Then I see the message "[welcomeText]"
    Then I click on "Logout"
    Then I see the message "[logoutText]"
