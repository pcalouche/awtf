Feature: Provide steps to verify and interact with tables
  Valid actions/verbs that are available in the table steps are:
  "see" --
  "do not see"  --
  "can select" --
  "cannot select" --
  "see selected" --
  "see deselected" --
  "select" --
  "deselect" --
  "expand" -- 
  "collapse" --
  "click" --
  In order to use any of of these table actions they must be registered in the App Config as a RowActionDefinition.

  @tables
  Scenario: Demonstrate available table steps
    Given I go to the demo page
    Then I "see" the rows with the following criteria:
      | Process | Memory Usage |
      | MySQL   | 1GB          |
      | Eclipse | 1.5GB        |
      | WinLess | 200MB        |
    Then I "do not see" the row with the following criteria:
      | Process | Memory Usage |
      | WinLess | 20MB         |
    Then I "can select" the rows with the following criteria:
      | Process | Memory Usage |
      | MySQL   | 1GB          |
      | Eclipse | 1.5GB        |
    Then I "select" the row with the following criteria:
      | Process | Memory Usage |
      | MySQL   | 1GB          |
      | Eclipse | 1.5GB        |
    Then I "see selected" the row with the following criteria:
      | Process | Memory Usage |
      | Eclipse | 1.5GB        |
    Then I "select" the row with the following criteria:
      | Process | Memory Usage |
      | Eclipse | 1.5GB        |
    Then I "see deselected" the row with the following criteria:
      | Process | Memory Usage |
      | MySQL   | 1GB          |
    Then I "cannot select" the row with the following criteria:
      | Process | Memory Usage |
      | WinLess | 200MB        |
    Then I "select" the row with the following criteria:
      | Process | Memory Usage |
      | Skype   | 400MB        |
    Then I "see selected" the row with the following criteria:
      | Process | Memory Usage |
      | Skype   | 400MB        |
    Then I "deselect" the row with the following criteria:
      | Process | Memory Usage |
      | Skype   | 400MB        |
    Then I "see deselected" the row with the following criteria:
      | Process | Memory Usage |
      | Skype   | 400MB        |
    Then I "cannot select" the rows with the following criteria:
      | Process | Memory Usage |
      | Defrag  | 100MB        |
    Then I "expand" the row with the following criteria:
      | Process           | Memory Usage |
      | Internet Explorer | 500MB        |
    Then I "collapse" the row with the following criteria:
      | Process | Memory Usage |
      | Firefox | 400MB        |
    Then I "click" the row with the following criteria:
      | Process           | Memory Usage |
      | Clickable Process | 800MB        |
