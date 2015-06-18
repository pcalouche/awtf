Feature: Examples Testing the various step definitions and framework capabilities

  @modals
  Scenario: 
    Given I go to the demo page
    Then I click on "Show Modal"
    Then I wait for the "Test" modal to load
    Then I input "Text Field 3" as "Modal Text Field Input"
    Then I click on "Close"
    Then I wait for the "Test" modal to disappear
