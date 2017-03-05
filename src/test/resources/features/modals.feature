Feature: Provide steps to interact with modals
  Any application modals must be registered in the App Config as a Modal object along with the application wide modal locator.
  If a modal is present on the page certain steps like doing input on a form element will only look for a matching input inside that modal.
  This because modals should be the primary user focus when they are displayed, and in some page designs 
  this resolves conflicts that may occur if there are ambiguous  matching elements on the page.  For example, a parent page and 
  its currently displayed modal may both have a cancel button or matching labels on a form element.

  @modals
  Scenario: Demonstrates checking for the presence and disappearance of a specific modal
    Given I go to the demo page
    Then I click on "Show Modal"
    Then I wait for the "Test" modal to appear
    Then I input "Text Field 1" as "Text Field 1 Modal Input"
    Then I take a screenshot
    Then I click on "Close"
    Then I wait for the "Test" modal to disappear
