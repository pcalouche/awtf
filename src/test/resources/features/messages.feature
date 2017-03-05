Feature: Provide steps to test if text is visible or not on the screen
  The step can use text/message/label interchangeably, and common code is executed for both.
  The text match does just a partial match when evaluating HTML text nodes.
  Because of this the given text to test for should be unique enough for the current state of the page.
  If message text to look for is within double brackets ("[]") then its text will be evaluated from configured message bundle.

  @messages
  Scenario: Demonstrate checking if certain text is visible on the page or not
    Given I go to the demo page
    Then I see the text "Text in a span to look for"
    Then I see the text "Text in a span"
    Then I see the label "Text in a label to look for"
    Then I see the label "Text in a label"
    Then I see the message "Text in a paragraph to look for"
    Then I see the message "Text in a paragraph"
    Then I see the message "[disclaimer1]"
    Then I do not see the message "Bogus message"
    Then I see the error message "Error message 1"
    Then I see the error message "Error message 123456"
    Then I see the error message "123456"
    Then I see the error message "[error1]"
    Then I do not see the error message "Bogus Error Message"
    Then I click on "Toggle Error Messages"
    Then I do not see the error message "Error message 123456"
    Then I do not see the error message "123456"
    Then I do not see the error message "[error1]"
