package com.demoqa.pages;

import com.demoqa.config.ConfigurationManager;
import com.demoqa.driver.DriverFactory;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * Base Page class for all Page Objects.
 * Contains common methods and utilities for page interactions.
 */
public abstract class BasePage {
    
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final Logger logger;
    protected final ConfigurationManager config;
    
    protected BasePage() {
        this.driver = DriverFactory.getDriver();
        this.config = ConfigurationManager.getInstance();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(config.getExplicitWait()));
        this.logger = LoggerFactory.getLogger(this.getClass());
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Navigate to a specific URL.
     * @param url URL to navigate to
     */
    @Step("Navigate to URL: {url}")
    public void navigateTo(String url) {
        logger.info("Navigating to: {}", url);
        driver.get(url);
    }
    
    /**
     * Navigate to a page relative to base URL.
     * @param path Path relative to base URL
     */
    @Step("Navigate to path: {path}")
    public void navigateToPath(String path) {
        String url = config.getBaseUrl() + path;
        navigateTo(url);
    }
    
    /**
     * Wait for element to be visible.
     * @param element WebElement to wait for
     * @return The visible element
     */
    protected WebElement waitForVisibility(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
    
    /**
     * Wait for element to be clickable.
     * @param element WebElement to wait for
     * @return The clickable element
     */
    protected WebElement waitForClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    
    /**
     * Wait for element by locator.
     * @param locator By locator
     * @return The visible element
     */
    protected WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    /**
     * Click on element with wait.
     * @param element WebElement to click
     */
    @Step("Click on element")
    protected void click(WebElement element) {
        logger.debug("Clicking element: {}", element);
        waitForClickable(element);
        scrollToElement(element);
        element.click();
    }
    
    /**
     * Click using JavaScript executor (for elements hidden by overlays).
     * @param element WebElement to click
     */
    @Step("JavaScript click on element")
    protected void jsClick(WebElement element) {
        logger.debug("JavaScript clicking element: {}", element);
        scrollToElement(element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
    
    /**
     * Type text into element.
     * @param element WebElement to type into
     * @param text Text to type
     */
    @Step("Type text: {text}")
    protected void type(WebElement element, String text) {
        logger.debug("Typing '{}' into element", text);
        waitForVisibility(element);
        element.clear();
        element.sendKeys(text);
    }
    
    /**
     * Get text from element.
     * @param element WebElement to get text from
     * @return Text content
     */
    protected String getText(WebElement element) {
        waitForVisibility(element);
        return element.getText();
    }
    
    /**
     * Get attribute value from element.
     * @param element WebElement
     * @param attribute Attribute name
     * @return Attribute value
     */
    protected String getAttribute(WebElement element, String attribute) {
        waitForVisibility(element);
        return element.getAttribute(attribute);
    }
    
    /**
     * Check if element is displayed.
     * @param element WebElement to check
     * @return true if displayed
     */
    protected boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }
    
    /**
     * Scroll to element.
     * @param element WebElement to scroll to
     */
    protected void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
        try {
            Thread.sleep(300); // Allow scroll animation to complete
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Scroll page by pixels.
     * @param pixels Pixels to scroll (positive = down, negative = up)
     */
    protected void scrollByPixels(int pixels) {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, " + pixels + ");");
    }
    
    /**
     * Get page title.
     * @return Page title
     */
    public String getPageTitle() {
        return driver.getTitle();
    }
    
    /**
     * Get current URL.
     * @return Current URL
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
    /**
     * Wait for page to load completely.
     */
    protected void waitForPageLoad() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
            .executeScript("return document.readyState").equals("complete"));
    }
    
    /**
     * Remove any overlays/popups that might block clicks.
     */
    protected void removeOverlays() {
        try {
            ((JavascriptExecutor) driver).executeScript(
                "document.querySelectorAll('[id*=\"google_ads\"], [id*=\"fixedban\"], .ad-container').forEach(el => el.remove());"
            );
        } catch (Exception e) {
            logger.debug("No overlays found to remove");
        }
    }
}
