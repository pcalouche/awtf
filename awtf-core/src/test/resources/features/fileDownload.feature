Feature: Provide steps to test the downloading a file.  This only works with Chrome and Firefox currently.

  @fileDownload
  Scenario: Demonstrate file download of an Excel File
    Given I delete the file named "SampleExcel.xlsx" if it exists
    And I go to the demo page
    And I click on "Download Excel File"
    Then I wait up to "5" seconds a file named "SampleExcel.xlsx" to download

#  @fileDownload
#  Scenario: Demonstrate file download of a PDF File
#    Given I delete the file named "SamplePdf.pdf" if it exists
#    Given I go to the demo page
#    And I click on "Download PDF File"
#    Then I wait up to "5" seconds a file named "SamplePdf.pdf" to download
