@smoke @textbox
Feature: Text Box Functionality
  As a user
  I want to fill out the text box form
  So that I can submit my personal information

  Background:
    Given I am on the Text Box page

  @positive
  Scenario: Fill text box with valid name and email
    When I enter "John Doe" in full name field
    And I enter "john.doe@example.com" in email field
    And I click the Submit button
    Then I should see the output section displayed
    And the output should show name as "John Doe"
    And the output should show email as "john.doe@example.com"

  @positive @data-driven
  Scenario Outline: Fill text box form with multiple data sets
    When I fill the text box form with full name "<fullName>" and email "<email>"
    And I click the Submit button
    Then I should see the output section displayed
    And the output should show the submitted name and email

    Examples:
      | fullName        | email                    |
      | John Doe        | john.doe@example.com     |
      | Jane Smith      | jane.smith@test.com      |
      | Muhammad Ali    | m.ali@company.org        |
      | Maria Garcia    | maria.garcia@domain.net  |

  @positive
  Scenario: Fill complete text box form with all fields
    When I enter "Robert Johnson" in full name field
    And I enter "robert.j@example.com" in email field
    And I enter "123 Main Street, New York, NY 10001" in current address field
    And I enter "456 Oak Avenue, Los Angeles, CA 90001" in permanent address field
    And I click the Submit button
    Then I should see the output section displayed
    And the output should display all submitted information

  @boundary
  Scenario Outline: Text box accepts various email formats
    When I enter "Test User" in full name field
    And I enter "<email>" in email field
    And I click the Submit button
    Then I should see the output section displayed
    And the output should show email as "<email>"

    Examples:
      | email                        |
      | simple@example.com           |
      | user.name@domain.org         |
      | user+tag@example.co.uk       |
