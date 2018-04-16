Feature: Provide steps to test the current state of an HTML element
  Valid element states are visible, hidden, enabled, and disabled.
  There is a step definition to test an element description is in a certain state.
  If the element description is within double brackets ("[]") then a lookup is done from the App Config for that element and that element state is evaluated.
  If the element description not within double brackets then a label element with that text that also has a non-empty for attribute is looked up.
  The state of the corresponding form element is the evaluated.
  There is also a second step definition that is meant to handle looking up the state of a button based on its text.

  @elementState
  Scenario: Demonstrate state validation for different elements
    Given I go to the demo page
    Then I see the "Windows 7" element is "visible"
    Then I see the "Windows 7" element is "enabled"
    Then I see the "Windows XP" element is "visible"
    Then I see the "Windows XP" element is "disabled"
    Then I see the "[Animal Dropdown]" element is "visible"
    Then I see the "[Animal Dropdown]" element is "enabled"
    Then I see the "Text Field 2" element is "hidden"
    Then I see the "[Error Box]" element is "visible"
    Then I see the "[Error Box]" element is "enabled"
    Then I see the "Enabled Input Tag Button" button is "visible"
    Then I see the "Enabled Input Tag Button" button is "enabled"
    Then I see the "Disabled Input Tag Button" button is "visible"
    Then I see the "Disabled Input Tag Button" button is "disabled"
    Then I see the "Disabled Input Tag Button By Class" button is "visible"
    Then I see the "Disabled Input Tag Button By Class" button is "disabled"
    Then I see the "Hidden Input Tag Button" button is "hidden"
    Then I see the "Enabled Button Tag Button" button is "visible"
    Then I see the "Enabled Button Tag Button" button is "enabled"
    Then I see the "Disabled Button Tag Button" button is "visible"
    Then I see the "Disabled Button Tag Button" button is "disabled"
    Then I see the "Disabled Button Tag Button By Class" button is "visible"
    Then I see the "Disabled Button Tag Button By Class" button is "disabled"
    Then I see the "Hidden Button Tag Button" button is "hidden"
