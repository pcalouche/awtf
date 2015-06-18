Feature: Examples Testing the various step definitions and framework capabilities

  @tooltips
  Scenario: 
    Given I go to the demo page
    When I hover over the "Info Icon" tooltip element I see a tooltip that says "This is the info icon tooltip"
    When I hover over the "Help Icon" tooltip element I see a tooltip that says "[helpIconTooltipMessage]"
