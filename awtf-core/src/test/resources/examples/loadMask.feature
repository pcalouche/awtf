Feature: Examples Testing the various step definitions and framework capabilities

  @loadMask @wait60sec
  Scenario: 
    Given I go to the demo page
    Then I wait for all load masks to disappear
