Feature: Provide steps to hover over text/links on the page

  @textHover
  Scenario: Demonstrate how to text/link hovering
    Given I go to the demo page
    Then I hover over "Tutorials"
    Then I click on "Photoshop"
    Then I see the message "You clicked Photoshop!"
    Then I hover over "Tutorials"
    Then I hover over "Web Design"
    Then I click on "CSS"
    Then I see the message "You clicked CSS!"
