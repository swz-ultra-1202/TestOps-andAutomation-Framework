package com.demoqa.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object for DemoQA Text Box Page.
 * URL: https://demoqa.com/text-box
 */
public class TextBoxPage extends BasePage {
    
    private static final String TEXT_BOX_URL = "/text-box";
    
    @FindBy(id = "userName")
    private WebElement fullNameInput;
    
    @FindBy(id = "userEmail")
    private WebElement emailInput;
    
    @FindBy(id = "currentAddress")
    private WebElement currentAddressInput;
    
    @FindBy(id = "permanentAddress")
    private WebElement permanentAddressInput;
    
    @FindBy(id = "submit")
    private WebElement submitButton;
    
    @FindBy(id = "output")
    private WebElement outputDiv;
    
    @FindBy(id = "name")
    private WebElement outputName;
    
    @FindBy(id = "email")
    private WebElement outputEmail;
    
    @FindBy(css = "#output #currentAddress")
    private WebElement outputCurrentAddress;
    
    @FindBy(css = "#output #permanentAddress")
    private WebElement outputPermanentAddress;
    
    /**
     * Open Text Box page directly.
     * @return TextBoxPage instance for chaining
     */
    @Step("Open Text Box page")
    public TextBoxPage open() {
        navigateToPath(TEXT_BOX_URL);
        waitForVisibility(fullNameInput);
        removeOverlays();
        return this;
    }
    
    /**
     * Enter full name.
     * @param fullName Full name to enter
     * @return TextBoxPage instance for chaining
     */
    @Step("Enter full name: {fullName}")
    public TextBoxPage enterFullName(String fullName) {
        type(fullNameInput, fullName);
        return this;
    }
    
    /**
     * Enter email address.
     * @param email Email to enter
     * @return TextBoxPage instance for chaining
     */
    @Step("Enter email: {email}")
    public TextBoxPage enterEmail(String email) {
        type(emailInput, email);
        return this;
    }
    
    /**
     * Enter current address.
     * @param address Current address to enter
     * @return TextBoxPage instance for chaining
     */
    @Step("Enter current address: {address}")
    public TextBoxPage enterCurrentAddress(String address) {
        type(currentAddressInput, address);
        return this;
    }
    
    /**
     * Enter permanent address.
     * @param address Permanent address to enter
     * @return TextBoxPage instance for chaining
     */
    @Step("Enter permanent address: {address}")
    public TextBoxPage enterPermanentAddress(String address) {
        type(permanentAddressInput, address);
        return this;
    }
    
    /**
     * Click submit button.
     * @return TextBoxPage instance for chaining
     */
    @Step("Click Submit button")
    public TextBoxPage clickSubmit() {
        scrollToElement(submitButton);
        jsClick(submitButton);
        return this;
    }
    
    /**
     * Fill complete form.
     * @param fullName Full name
     * @param email Email
     * @param currentAddress Current address
     * @param permanentAddress Permanent address
     * @return TextBoxPage instance for chaining
     */
    @Step("Fill text box form with all details")
    public TextBoxPage fillForm(String fullName, String email, String currentAddress, String permanentAddress) {
        enterFullName(fullName);
        enterEmail(email);
        enterCurrentAddress(currentAddress);
        enterPermanentAddress(permanentAddress);
        return this;
    }
    
    /**
     * Check if output is displayed.
     * @return true if output div is visible
     */
    public boolean isOutputDisplayed() {
        try {
            waitForVisibility(outputDiv);
            return isDisplayed(outputDiv);
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Get output name text.
     * @return Name from output
     */
    @Step("Get output name")
    public String getOutputName() {
        waitForVisibility(outputName);
        return getText(outputName).replace("Name:", "").trim();
    }
    
    /**
     * Get output email text.
     * @return Email from output
     */
    @Step("Get output email")
    public String getOutputEmail() {
        waitForVisibility(outputEmail);
        return getText(outputEmail).replace("Email:", "").trim();
    }
    
    /**
     * Get output current address.
     * @return Current address from output
     */
    @Step("Get output current address")
    public String getOutputCurrentAddress() {
        waitForVisibility(outputCurrentAddress);
        return getText(outputCurrentAddress).replace("Current Address :", "").trim();
    }
    
    /**
     * Get output permanent address.
     * @return Permanent address from output
     */
    @Step("Get output permanent address")
    public String getOutputPermanentAddress() {
        waitForVisibility(outputPermanentAddress);
        return getText(outputPermanentAddress).replace("Permananet Address :", "").trim();
    }
}
