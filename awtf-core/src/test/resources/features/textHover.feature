Feature: Provide steps to hover over elements on the page

  @textHover
  Scenario: Demonstrate how to do element hovering
    Given I go to the demo page
    When I hover over "Tutorials"
    And I click on "Photoshop"
    Then I see the message "You clicked Photoshop!"
    When I hover over "Tutorials"
    And I hover over "[Web Design Menu Item]"
    And I click on "CSS"
    Then I see the message "You clicked CSS!"
