@excel @data-driven
Feature: Excel Data Driven Tests
  As a tester
  I want to run tests with data from Excel files
  So that I can easily manage test data externally

  @textbox @excel
  Scenario: Fill text box form with Excel data - Row 1
    Given I load test data from sheet "TextBoxData"
    And I use test data row 1
    And I am on the Text Box page
    When I fill the text box form with data from Excel
    And I click the Submit button
    Then I should see the output section displayed

  @textbox @excel
  Scenario: Fill text box form with Excel data - Row 2
    Given I load test data from sheet "TextBoxData"
    And I use test data row 2
    And I am on the Text Box page
    When I fill the text box form with data from Excel
    And I click the Submit button
    Then I should see the output section displayed

  @practiceform @excel
  Scenario: Fill practice form with Excel data - Row 1
    Given I load test data from sheet "PracticeFormData"
    And I use test data row 1
    And I am on the Practice Form page
    When I fill the practice form with data from Excel
    And I submit the practice form
    Then the form submission modal should be displayed

  @practiceform @excel
  Scenario: Fill practice form with Excel data - Row 2
    Given I load test data from sheet "PracticeFormData"
    And I use test data row 2
    And I am on the Practice Form page
    When I fill the practice form with data from Excel
    And I submit the practice form
    Then the form submission modal should be displayed

  @login @excel
  Scenario: Login with Excel credentials - Invalid Row 1
    Given I load test data from sheet "LoginData"
    And I use test data row 1
    And I am on the Login page
    When I login with credentials from Excel
    Then I should remain on the login page
