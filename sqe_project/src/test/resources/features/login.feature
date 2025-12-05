@smoke @login
Feature: Login Functionality
  As a user
  I want to login to the Book Store application
  So that I can access my account and manage books

  Background:
    Given I am on the Login page
    And the login page is displayed

  @negative
  Scenario: Login with invalid credentials
    When I enter username "invaliduser"
    And I enter password "invalidpass"
    And I click the Login button
    Then I should see an error message
    And I should remain on the login page

  @negative @data-driven
  Scenario Outline: Login with various invalid credentials
    When I login with username "<username>" and password "<password>"
    Then I should see an error message
    And I should remain on the login page

    Examples:
      | username       | password        |
      | wronguser      | wrongpass       |
      | testuser       | badpassword     |
      | invalid@email  | test123         |
      | admin          | admin           |
      |                | somepassword    |
      | someuser       |                 |

  @negative
  Scenario: Login with empty username
    When I enter password "testpassword"
    And I click the Login button
    Then I should remain on the login page

  @negative
  Scenario: Login with empty password
    When I enter username "testuser"
    And I click the Login button
    Then I should remain on the login page

  @navigation
  Scenario: Navigate to registration page
    When I click the New User button
    Then I should see the registration form

# Note: Positive login test requires valid registered credentials
# To test positive login, first register a user through the UI or API
# @positive @skip
# Scenario: Login with valid credentials
#   When I login with username "validuser" and password "ValidPass123!"
#   Then I should be logged in successfully
#   And I should see the logged in username as "validuser"
