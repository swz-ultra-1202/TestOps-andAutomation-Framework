package com.demoqa.stepdefinitions;

import com.demoqa.context.TestContext;
import com.demoqa.pages.BookStorePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for Book Store feature.
 */
public class BookStoreSteps {

    private final TestContext testContext;
    private final BookStorePage bookStorePage;

    public BookStoreSteps(TestContext testContext) {
        this.testContext = testContext;
        this.bookStorePage = testContext.getBookStorePage();
    }

    @Given("I am on the Book Store page")
    public void iAmOnTheBookStorePage() {
        bookStorePage.open();
    }

    @When("I search for {string}")
    public void iSearchFor(String searchTerm) {
        bookStorePage.searchBook(searchTerm);
        testContext.setScenarioData("searchTerm", searchTerm);
    }

    @When("I clear the search")
    public void iClearTheSearch() {
        bookStorePage.clearSearch();
    }

    @When("I click on book {string}")
    public void iClickOnBook(String bookTitle) {
        bookStorePage.clickBook(bookTitle);
        testContext.setScenarioData("selectedBook", bookTitle);
    }

    @When("I click Back To Book Store button")
    public void iClickBackToBookStoreButton() {
        bookStorePage.clickBackToStore();
    }

    @Then("I should see books containing {string} in the results")
    public void iShouldSeeBooksContainingInTheResults(String searchTerm) {
        List<String> bookTitles = bookStorePage.getBookTitles();
        assertThat(bookTitles)
                .as("Search results should contain books matching: " + searchTerm)
                .anyMatch(title -> title.toLowerCase().contains(searchTerm.toLowerCase()));
    }

    @Then("I should see {int} or more books in the results")
    public void iShouldSeeOrMoreBooksInTheResults(int minCount) {
        int actualCount = bookStorePage.getBookCount();
        assertThat(actualCount)
                .as("Book count should be at least " + minCount)
                .isGreaterThanOrEqualTo(minCount);
    }

    @Then("I should see the book {string} in the results")
    public void iShouldSeeTheBookInTheResults(String bookTitle) {
        assertThat(bookStorePage.isBookVisible(bookTitle))
                .as("Book should be visible: " + bookTitle)
                .isTrue();
    }

    @Then("I should not see any books in the results")
    public void iShouldNotSeeAnyBooksInTheResults() {
        int bookCount = bookStorePage.getBookCount();
        assertThat(bookCount)
                .as("No books should be visible")
                .isEqualTo(0);
    }

    @Then("the book details page should be displayed")
    public void theBookDetailsPageShouldBeDisplayed() {
        assertThat(bookStorePage.isBookDetailDisplayed())
                .as("Book details page should be displayed")
                .isTrue();
    }

    @Then("the book title should be {string}")
    public void theBookTitleShouldBe(String expectedTitle) {
        String actualTitle = bookStorePage.getBookDetailTitle();
        assertThat(actualTitle)
                .as("Book title should match")
                .isEqualTo(expectedTitle);
    }

    @Then("the book author should be {string}")
    public void theBookAuthorShouldBe(String expectedAuthor) {
        String actualAuthor = bookStorePage.getBookAuthor();
        assertThat(actualAuthor)
                .as("Book author should match")
                .isEqualTo(expectedAuthor);
    }

    @Then("the book publisher should be {string}")
    public void theBookPublisherShouldBe(String expectedPublisher) {
        String actualPublisher = bookStorePage.getBookPublisher();
        assertThat(actualPublisher)
                .as("Book publisher should match")
                .isEqualTo(expectedPublisher);
    }

    @Then("the book ISBN should not be empty")
    public void theBookISBNShouldNotBeEmpty() {
        String isbn = bookStorePage.getBookIsbn();
        assertThat(isbn)
                .as("Book ISBN should not be empty")
                .isNotEmpty();
    }

    @Then("the book should have a description")
    public void theBookShouldHaveADescription() {
        String description = bookStorePage.getBookDescription();
        assertThat(description)
                .as("Book description should not be empty")
                .isNotEmpty();
    }

    @Then("I should be back on the Book Store page")
    public void iShouldBeBackOnTheBookStorePage() {
        assertThat(bookStorePage.getCurrentUrl())
                .as("Should be back on Book Store page")
                .contains("/books");
    }
}
