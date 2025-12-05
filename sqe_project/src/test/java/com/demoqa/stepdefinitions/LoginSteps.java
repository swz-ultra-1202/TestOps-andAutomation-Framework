package com.demoqa.stepdefinitions;

import com.demoqa.context.TestContext;
import com.demoqa.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for Login feature.
 */
public class LoginSteps {
    
    private final TestContext testContext;
    private final LoginPage loginPage;
    
    public LoginSteps(TestContext testContext) {
        this.testContext = testContext;
        this.loginPage = testContext.getLoginPage();
    }
    
    @Given("I am on the Login page")
    public void iAmOnTheLoginPage() {
        loginPage.open();
    }
    
    @Given("the login page is displayed")
    public void theLoginPageIsDisplayed() {
        assertThat(loginPage.isLoginPageDisplayed())
            .as("Login page should be displayed")
            .isTrue();
    }
    
    @When("I enter username {string}")
    public void iEnterUsername(String username) {
        loginPage.enterUsername(username);
        testContext.setScenarioData("username", username);
    }
    
    @When("I enter password {string}")
    public void iEnterPassword(String password) {
        loginPage.enterPassword(password);
    }
    
    @When("I click the Login button")
    public void iClickTheLoginButton() {
        loginPage.clickLogin();
    }
    
    @When("I login with username {string} and password {string}")
    public void iLoginWithUsernameAndPassword(String username, String password) {
        loginPage.login(username, password);
        testContext.setScenarioData("username", username);
    }
    
    @When("I click the New User button")
    public void iClickTheNewUserButton() {
        loginPage.clickNewUser();
    }
    
    @Then("I should be logged in successfully")
    public void iShouldBeLoggedInSuccessfully() {
        assertThat(loginPage.isLoggedIn())
            .as("User should be logged in")
            .isTrue();
    }
    
    @Then("I should see the logged in username as {string}")
    public void iShouldSeeTheLoggedInUsernameAs(String expectedUsername) {
        String actualUsername = loginPage.getLoggedInUsername();
        assertThat(actualUsername)
            .as("Logged in username should match")
            .isEqualTo(expectedUsername);
    }
    
    @Then("I should see an error message")
    public void iShouldSeeAnErrorMessage() {
        assertThat(loginPage.isErrorDisplayed())
            .as("Error message should be displayed")
            .isTrue();
    }
    
    @Then("the error message should contain {string}")
    public void theErrorMessageShouldContain(String expectedText) {
        String errorMessage = loginPage.getErrorMessage();
        assertThat(errorMessage)
            .as("Error message should contain expected text")
            .containsIgnoringCase(expectedText);
    }
    
    @Then("I should remain on the login page")
    public void iShouldRemainOnTheLoginPage() {
        assertThat(loginPage.isLoginPageDisplayed())
            .as("Should remain on login page")
            .isTrue();
    }
    
    @When("I logout")
    public void iLogout() {
        loginPage.clickLogout();
    }
    
    @Then("I should be redirected to the login page")
    public void iShouldBeRedirectedToTheLoginPage() {
        assertThat(loginPage.isLoginPageDisplayed())
            .as("Should be redirected to login page")
            .isTrue();
    }
}
