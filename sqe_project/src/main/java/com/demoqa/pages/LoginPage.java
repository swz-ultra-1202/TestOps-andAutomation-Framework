package com.demoqa.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object for DemoQA Login Page.
 * URL: https://demoqa.com/login
 */
public class LoginPage extends BasePage {
    
    private static final String LOGIN_URL = "/login";
    
    @FindBy(id = "userName")
    private WebElement usernameInput;
    
    @FindBy(id = "password")
    private WebElement passwordInput;
    
    @FindBy(id = "login")
    private WebElement loginButton;
    
    @FindBy(id = "newUser")
    private WebElement newUserButton;
    
    @FindBy(id = "name")
    private WebElement loggedInUserName;
    
    @FindBy(css = "#userName-value")
    private WebElement userNameValue;
    
    @FindBy(id = "submit")
    private WebElement logoutButton;
    
    @FindBy(css = "#name")
    private WebElement invalidCredentialsMessage;
    
    @FindBy(css = ".mr-2")
    private WebElement errorMessage;
    
    @FindBy(css = "#loading-label")
    private WebElement loadingLabel;
    
    /**
     * Open Login page directly.
     * @return LoginPage instance for chaining
     */
    @Step("Open Login page")
    public LoginPage open() {
        navigateToPath(LOGIN_URL);
        waitForVisibility(usernameInput);
        removeOverlays();
        return this;
    }
    
    /**
     * Enter username.
     * @param username Username to enter
     * @return LoginPage instance for chaining
     */
    @Step("Enter username: {username}")
    public LoginPage enterUsername(String username) {
        type(usernameInput, username);
        return this;
    }
    
    /**
     * Enter password.
     * @param password Password to enter
     * @return LoginPage instance for chaining
     */
    @Step("Enter password")
    public LoginPage enterPassword(String password) {
        type(passwordInput, password);
        return this;
    }
    
    /**
     * Click login button.
     * @return LoginPage instance for chaining
     */
    @Step("Click Login button")
    public LoginPage clickLogin() {
        scrollToElement(loginButton);
        jsClick(loginButton);
        return this;
    }
    
    /**
     * Perform complete login.
     * @param username Username
     * @param password Password
     * @return LoginPage instance for chaining
     */
    @Step("Login with username: {username}")
    public LoginPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        return this;
    }
    
    /**
     * Click new user button.
     * @return LoginPage instance for chaining
     */
    @Step("Click New User button")
    public LoginPage clickNewUser() {
        scrollToElement(newUserButton);
        jsClick(newUserButton);
        return this;
    }
    
    /**
     * Check if user is logged in.
     * @return true if logged in successfully
     */
    @Step("Check if user is logged in")
    public boolean isLoggedIn() {
        try {
            waitForVisibility(userNameValue);
            return isDisplayed(userNameValue);
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Get logged in username.
     * @return Username of logged in user
     */
    @Step("Get logged in username")
    public String getLoggedInUsername() {
        waitForVisibility(userNameValue);
        return getText(userNameValue);
    }
    
    /**
     * Check if error message is displayed.
     * @return true if error message is visible
     */
    @Step("Check if error message is displayed")
    public boolean isErrorDisplayed() {
        try {
            return isDisplayed(invalidCredentialsMessage) || isDisplayed(errorMessage);
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Get error message text.
     * @return Error message
     */
    @Step("Get error message")
    public String getErrorMessage() {
        try {
            if (isDisplayed(invalidCredentialsMessage)) {
                return getText(invalidCredentialsMessage);
            }
            return getText(errorMessage);
        } catch (Exception e) {
            return "";
        }
    }
    
    /**
     * Click logout button.
     * @return LoginPage instance for chaining
     */
    @Step("Click Logout button")
    public LoginPage clickLogout() {
        scrollToElement(logoutButton);
        jsClick(logoutButton);
        return this;
    }
    
    /**
     * Check if login page is displayed.
     * @return true if login form is visible
     */
    public boolean isLoginPageDisplayed() {
        return isDisplayed(usernameInput) && isDisplayed(passwordInput);
    }
}
