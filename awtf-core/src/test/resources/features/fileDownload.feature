Feature: Provide steps to test the downloading a file.  This only works with Chrome and Firefox currently.

  @fileDownload
  Scenario: Demonstrate file download of an Excel File
    Given I go to the demo page
    And I click on "Download Excel File"
    Then A file named "SampleExcel.xlsx" is downloaded

#  @fileDownload
#  Scenario: Demonstrate file download of a PDF File
#    Given I go to the demo page
#    And I click on "Download Excel File"
#    Then A file named "SamplePdf.pdf" is downloaded
