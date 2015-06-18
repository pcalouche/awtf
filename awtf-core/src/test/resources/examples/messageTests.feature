Feature: Examples Testing the various step definitions and framework capabilities

  @messages
  Scenario: 
    Given I go to the demo page
    Then I see the text "Text in a span to look for"
    Then I see the text "Text in a span"
    Then I see the label "Text in a label to look for"
    Then I see the label "Text in a label"
    Then I see the message "Text in a paragraph to look for"
    Then I see the message "Text in a paragraph"
    Then I see the message "[disclaimer1]"
    Then I see the error message "Error message 1"
    Then I see the error message "Error message 123456"
    Then I see the error message "123456"
    Then I see the error message "[error1]"
