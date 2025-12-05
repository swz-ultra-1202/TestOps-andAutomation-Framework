@smoke @practiceform
Feature: Practice Form Functionality
  As a user
  I want to fill out the student registration form
  So that I can register for courses

  Background:
    Given I am on the Practice Form page

  @positive
  Scenario: Complete student registration form submission
    When I enter first name "John"
    And I enter last name "Doe"
    And I enter user email "johndoe@example.com"
    And I select gender "Male"
    And I enter mobile number "1234567890"
    And I select hobby "Sports"
    And I enter address "123 Test Street, Test City"
    And I submit the practice form
    Then the form submission modal should be displayed
    And the modal title should be "Thanks for submitting the form"
    And the submission confirmation should display the student name

  @positive @data-driven
  Scenario Outline: Student registration with various data sets
    When I enter first name "<firstName>"
    And I enter last name "<lastName>"
    And I enter user email "<email>"
    And I select gender "<gender>"
    And I enter mobile number "<mobile>"
    And I select hobbies "<hobbies>"
    And I enter address "<address>"
    And I submit the practice form
    Then the form submission modal should be displayed
    And the modal should show "Student Name" as "<firstName> <lastName>"
    And the modal should show "Student Email" as "<email>"
    And the modal should show "Mobile" as "<mobile>"

    Examples:
      | firstName | lastName  | email                  | gender | mobile     | hobbies        | address                    |
      | John      | Doe       | john.doe@example.com   | Male   | 1234567890 | Sports         | 123 Main St, NYC           |
      | Jane      | Smith     | jane.smith@test.com    | Female | 9876543210 | Reading        | 456 Oak Ave, LA            |
      | Alex      | Johnson   | alex.j@company.org     | Other  | 5551234567 | Sports,Music   | 789 Pine Rd, Chicago       |
      | Maria     | Garcia    | maria.g@domain.net     | Female | 1112223333 | Reading,Music  | 321 Elm St, Miami          |

  @positive
  Scenario: Form submission with datatable input
    When I fill the practice form with:
      | firstName | Robert                   |
      | lastName  | Williams                 |
      | email     | robert.w@example.com     |
      | gender    | Male                     |
      | mobile    | 4445556666               |
      | hobbies   | Sports,Reading           |
      | address   | 555 Cedar Lane, Boston   |
    And I submit the practice form
    Then the form submission modal should be displayed
    And the modal title should be "Thanks for submitting the form"

  @positive
  Scenario: Close modal after form submission
    When I enter first name "Test"
    And I enter last name "User"
    And I enter user email "test@test.com"
    And I select gender "Male"
    And I enter mobile number "1111111111"
    And I submit the practice form
    Then the form submission modal should be displayed
    And I close the submission modal
