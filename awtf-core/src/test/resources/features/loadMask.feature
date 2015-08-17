Feature: Provide step to check if all loading mask/indicators are no longer visible
  This explicit step is helpful to ensure that all loading on a page has happened before moving onto the next step.
  Tags can also be placed on the scenario to increase the default timeout for scenarios that may take longer to complete.

  @loadMask @wait60sec
  Scenario: Demonstrate waiting for load masks/indicators to complete
    Given I go to the demo page
    Then I click on "Start Load Masks"
    Then I wait for all load masks to disappear
    Then I wait for all load masks to  disappear
