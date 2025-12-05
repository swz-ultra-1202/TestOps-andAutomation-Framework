@smoke @bookstore
Feature: Book Store Functionality
  As a user
  I want to search and browse books in the Book Store
  So that I can find books I'm interested in

  Background:
    Given I am on the Book Store page

  @positive @search
  Scenario: Search for a book by title
    When I search for "JavaScript"
    Then I should see books containing "JavaScript" in the results

  @positive @search @data-driven
  Scenario Outline: Search for books with various keywords
    When I search for "<searchTerm>"
    Then I should see books containing "<searchTerm>" in the results

    Examples:
      | searchTerm      |
      | JavaScript      |
      | Design          |
      | Programming     |
      | Git             |

  @positive
  Scenario: View book details
    When I click on book "Git Pocket Guide"
    Then the book details page should be displayed
    And the book title should be "Git Pocket Guide"
    And the book author should be "Richard E. Silverman"
    And the book publisher should be "O'Reilly Media"
    And the book ISBN should not be empty
    And the book should have a description

  @positive @navigation
  Scenario: Navigate back from book details to book list
    When I click on book "Git Pocket Guide"
    Then the book details page should be displayed
    When I click Back To Book Store button
    Then I should be back on the Book Store page

  @positive
  Scenario: View all available books
    Then I should see 8 or more books in the results

  @positive @data-driven
  Scenario Outline: Verify specific books are available
    Then I should see the book "<bookTitle>" in the results

    Examples:
      | bookTitle                                          |
      | Git Pocket Guide                                   |
      | Learning JavaScript Design Patterns                |
      | Designing Evolvable Web APIs with ASP.NET          |
      | Speaking JavaScript                                |

  @negative @search
  Scenario: Search for non-existent book
    When I search for "XYZ123NonExistentBook"
    Then I should not see any books in the results

  @positive @search
  Scenario: Clear search and view all books
    When I search for "JavaScript"
    And I clear the search
    Then I should see 8 or more books in the results
