Feature: Examples Testing the various step definitions and framework capabilities

  @tables
  Scenario: 
    Given I go to the demo page
    Then I "see" the rows with the following criteria:
      | Process | Memory Usage |
      | MySQL   | 1GB          |
      | Eclipse | 1.5GB        |
      | WinLess | 200MB        |
    Then I "do not see" the row with the following criteria:
      | Process | Memory Usage |
      | WinLess | 20MB        |
    Then I "can select" the row with the following criteria:
      | Process | Memory Usage |
      | MySQL   | 1GB          |
      | Eclipse | 1.5GB        |
    Then I "cannot select" the rows with the following criteria:
      | Process | Memory Usage |
      | WinLess | 200MB        |
