package com.demoqa.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Page Object for DemoQA Book Store Page.
 * URL: https://demoqa.com/books
 */
public class BookStorePage extends BasePage {

    private static final String BOOKS_URL = "/books";

    @FindBy(id = "searchBox")
    private WebElement searchBox;

    @FindBy(css = ".rt-tbody .rt-tr-group")
    private List<WebElement> bookRows;

    @FindBy(css = ".rt-tbody .action-buttons a")
    private List<WebElement> bookTitleLinks;

    @FindBy(css = "#see-book-Git Pocket Guide a")
    private WebElement gitPocketGuideLink;

    @FindBy(css = ".books-wrapper")
    private WebElement booksWrapper;

    @FindBy(css = "#ISBN-wrapper #userName-value")
    private WebElement bookIsbn;

    @FindBy(css = "#title-wrapper #userName-value")
    private WebElement bookTitle;

    @FindBy(css = "#subtitle-wrapper #userName-value")
    private WebElement bookSubtitle;

    @FindBy(css = "#author-wrapper #userName-value")
    private WebElement bookAuthor;

    @FindBy(css = "#publisher-wrapper #userName-value")
    private WebElement bookPublisher;

    @FindBy(css = "#pages-wrapper #userName-value")
    private WebElement bookPages;

    @FindBy(css = "#description-wrapper #userName-value")
    private WebElement bookDescription;

    @FindBy(css = "#website-wrapper #userName-value")
    private WebElement bookWebsite;

    @FindBy(css = "#addNewRecordButton")
    private WebElement backToStoreButton;

    @FindBy(css = ".rt-noData")
    private WebElement noDataMessage;

    /**
     * Open Book Store page directly.
     * 
     * @return BookStorePage instance for chaining
     */
    @Step("Open Book Store page")
    public BookStorePage open() {
        navigateToPath(BOOKS_URL);
        waitForVisibility(searchBox);
        removeOverlays();
        return this;
    }

    /**
     * Search for a book.
     * 
     * @param searchTerm Search query
     * @return BookStorePage instance for chaining
     */
    @Step("Search for book: {searchTerm}")
    public BookStorePage searchBook(String searchTerm) {
        type(searchBox, searchTerm);
        // Wait for search to filter results
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return this;
    }

    /**
     * Clear search box.
     * 
     * @return BookStorePage instance for chaining
     */
    @Step("Clear search")
    public BookStorePage clearSearch() {
        searchBox.clear();
        return this;
    }

    /**
     * Get number of visible books.
     * 
     * @return Count of visible book rows
     */
    @Step("Get book count")
    public int getBookCount() {
        return (int) bookRows.stream()
                .filter(row -> !row.getText().trim().isEmpty())
                .count();
    }

    /**
     * Get all visible book titles.
     * 
     * @return List of book titles
     */
    @Step("Get all book titles")
    public List<String> getBookTitles() {
        return bookTitleLinks.stream()
                .filter(link -> !link.getText().trim().isEmpty())
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Click on a book by title.
     * 
     * @param title Book title
     * @return BookStorePage instance for chaining
     */
    @Step("Click on book: {title}")
    public BookStorePage clickBook(String title) {
        WebElement bookLink = driver.findElement(
                By.xpath("//a[contains(text(),'" + title + "')]"));
        scrollToElement(bookLink);
        jsClick(bookLink);
        return this;
    }

    /**
     * Check if book details are displayed.
     * 
     * @return true if on book detail page
     */
    public boolean isBookDetailDisplayed() {
        try {
            waitForVisibility(bookIsbn);
            return isDisplayed(bookIsbn);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get book ISBN from detail page.
     * 
     * @return ISBN value
     */
    @Step("Get book ISBN")
    public String getBookIsbn() {
        waitForVisibility(bookIsbn);
        return getText(bookIsbn);
    }

    /**
     * Get book title from detail page.
     * 
     * @return Title value
     */
    @Step("Get book title from detail page")
    public String getBookDetailTitle() {
        waitForVisibility(bookTitle);
        return getText(bookTitle);
    }

    /**
     * Get book author from detail page.
     * 
     * @return Author name
     */
    @Step("Get book author")
    public String getBookAuthor() {
        waitForVisibility(bookAuthor);
        return getText(bookAuthor);
    }

    /**
     * Get book publisher from detail page.
     * 
     * @return Publisher name
     */
    @Step("Get book publisher")
    public String getBookPublisher() {
        waitForVisibility(bookPublisher);
        return getText(bookPublisher);
    }

    /**
     * Get book page count from detail page.
     * 
     * @return Number of pages
     */
    @Step("Get book pages")
    public String getBookPages() {
        waitForVisibility(bookPages);
        return getText(bookPages);
    }

    /**
     * Get book description from detail page.
     * 
     * @return Description text
     */
    @Step("Get book description")
    public String getBookDescription() {
        waitForVisibility(bookDescription);
        return getText(bookDescription);
    }

    /**
     * Click back to book store button.
     * 
     * @return BookStorePage instance for chaining
     */
    @Step("Click Back To Book Store button")
    public BookStorePage clickBackToStore() {
        scrollToElement(backToStoreButton);
        jsClick(backToStoreButton);
        return this;
    }

    /**
     * Check if no data message is displayed.
     * 
     * @return true if no books found
     */
    public boolean isNoDataDisplayed() {
        try {
            return isDisplayed(noDataMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if a specific book is visible.
     * 
     * @param bookTitle Title to check
     * @return true if book is visible
     */
    @Step("Check if book is visible: {bookTitle}")
    public boolean isBookVisible(String bookTitle) {
        return getBookTitles().stream()
                .anyMatch(title -> title.contains(bookTitle));
    }
}
