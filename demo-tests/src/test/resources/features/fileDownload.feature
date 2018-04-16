Feature: Provide steps to test the downloading a file.  This only works with Chrome and Firefox currently.

  @fileDownload
  Scenario: Demonstrate file download of an Excel File
    Given I navigate to "localhost:8080/demoPage.html"
    And I delete the file named "SampleExcel.xlsx" if it exists
    And I take a screenshot
    And I click on the "Download Excel File" link
    Then I wait up to "5" seconds for a file named "SampleExcel.xlsx" to download

  @fileDownload
  Scenario: Demonstrate file download of a PDF File
    Given I delete the file named "SamplePdf.pdf" if it exists
    Given I navigate to "localhost:8080/demoPage.html"
    And I click on the "Download PDF File" link
    Then I wait up to "5" seconds for a file named "SamplePdf.pdf" to download
