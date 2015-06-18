Feature: Examples Testing the various step definitions and framework capabilities

  @formInputAndVerification
  Scenario: 
    Given I go to the demo page
    Then I input "Text Field 1" as "some input"
    Then I see "Text Field 1" has value of "some input"
    Then I input "Dropdown 1" as "Option 3"
    Then I see "Dropdown 1" has value of "Option 3"
    Then I see "Dropdown 2" has value containing "Pay to 636883435"
    Then I input "Dropdown 2" as value containing "Pay to 345345423"
    Then I see "Dropdown 2" has value containing "Pay to 345345423"
    Then I "see" "Option 2" in the "Dropdown 1" dropdown
    Then I "do not see" "Option 4" in the "Dropdown 1" dropdown
    Then I "select" the "Radio Option 2" radio button
    Then I see "Radio Option 2" has value of "selected"
    Then I "select" the "Partial Radio Option Text Match" radio button
    Then I see "Partial Radio Option Text Match" has value of "selected"
    Then I "select" the "Checkbox 1" checkbox
    Then I see "Checkbox 1" has value of "selected"
    Then I "deselect" the "Checkbox 1" checkbox
    Then I see "Checkbox 1" has value of "deselected"
    Then I "select" the "Checkbox 2" checkbox
    Then I see "Checkbox 2" has value of "selected"
    Then I "deselect" the "Checkbox 2" checkbox
    Then I see "Checkbox 2" has value of "deselected"
    Then I "select" the "Partial Checkbox Text Match" checkbox
    Then I see "Partial Checkbox Text Match" has value of "selected"
    Then I input "Textarea 1" as "These are my great comments to the world."
    Then I input "a" 50 times into "Textarea 1"
    Then I see "Textarea 1" contains "a" 50 times
    Then I input "b" 201 times into "Textarea 1"
    Then I see "Textarea 1" contains "b" 200 times
    Then I input "b" 10 times into "[Global Search]"
    Then I see "[Global Search]" contains "b" 10 times
    Then I input "[Global Search]" as "cute kittens"
    Then I see "[Global Search]" has value of "cute kittens"
    Then I input "[Account History Dropdown]" as "60 Days"
    Then I see "[Account History Dropdown]" has value of "60 Days"
