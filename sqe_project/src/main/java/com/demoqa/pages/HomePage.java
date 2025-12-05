package com.demoqa.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object for DemoQA Home Page.
 */
public class HomePage extends BasePage {
    
    private static final String HOME_URL = "/";
    
    @FindBy(css = ".category-cards")
    private WebElement categoryCards;
    
    @FindBy(xpath = "//h5[text()='Elements']/..")
    private WebElement elementsCard;
    
    @FindBy(xpath = "//h5[text()='Forms']/..")
    private WebElement formsCard;
    
    @FindBy(xpath = "//h5[text()='Alerts, Frame & Windows']/..")
    private WebElement alertsCard;
    
    @FindBy(xpath = "//h5[text()='Widgets']/..")
    private WebElement widgetsCard;
    
    @FindBy(xpath = "//h5[text()='Interactions']/..")
    private WebElement interactionsCard;
    
    @FindBy(xpath = "//h5[text()='Book Store Application']/..")
    private WebElement bookStoreCard;
    
    @FindBy(css = "header img")
    private WebElement headerLogo;
    
    /**
     * Open DemoQA home page.
     * @return HomePage instance for chaining
     */
    @Step("Open DemoQA Home Page")
    public HomePage open() {
        navigateToPath(HOME_URL);
        waitForVisibility(categoryCards);
        removeOverlays();
        return this;
    }
    
    /**
     * Navigate to Elements section.
     * @return HomePage instance
     */
    @Step("Click on Elements card")
    public HomePage clickElements() {
        scrollToElement(elementsCard);
        jsClick(elementsCard);
        return this;
    }
    
    /**
     * Navigate to Forms section.
     * @return HomePage instance
     */
    @Step("Click on Forms card")
    public HomePage clickForms() {
        scrollToElement(formsCard);
        jsClick(formsCard);
        return this;
    }
    
    /**
     * Navigate to Alerts section.
     * @return HomePage instance
     */
    @Step("Click on Alerts, Frame & Windows card")
    public HomePage clickAlerts() {
        scrollToElement(alertsCard);
        jsClick(alertsCard);
        return this;
    }
    
    /**
     * Navigate to Widgets section.
     * @return HomePage instance
     */
    @Step("Click on Widgets card")
    public HomePage clickWidgets() {
        scrollToElement(widgetsCard);
        jsClick(widgetsCard);
        return this;
    }
    
    /**
     * Navigate to Interactions section.
     * @return HomePage instance
     */
    @Step("Click on Interactions card")
    public HomePage clickInteractions() {
        scrollToElement(interactionsCard);
        jsClick(interactionsCard);
        return this;
    }
    
    /**
     * Navigate to Book Store Application.
     * @return HomePage instance
     */
    @Step("Click on Book Store Application card")
    public HomePage clickBookStore() {
        scrollToElement(bookStoreCard);
        jsClick(bookStoreCard);
        return this;
    }
    
    /**
     * Check if home page is displayed.
     * @return true if category cards are visible
     */
    public boolean isHomePageDisplayed() {
        return isDisplayed(categoryCards);
    }
}
