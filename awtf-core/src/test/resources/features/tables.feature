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
