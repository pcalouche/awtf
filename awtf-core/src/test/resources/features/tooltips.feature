Feature: Provide steps to interact and verify tooltips
  Any application tooltips must be registered in the App Config as an ElementWithTooltip object.
  If the tooltip text to look for is within double brackets ("[]") then its text will be evaluated from configured message bundle.

  @tooltips
  Scenario: Demonstrate how to test tooltips
    Given I go to the demo page
    When I hover over the "Info Icon" tooltip element I see a tooltip that says "This is the info icon tooltip"
    When I hover over the "Help Icon" tooltip element I see a tooltip that says "[helpIconTooltipMessage]"
