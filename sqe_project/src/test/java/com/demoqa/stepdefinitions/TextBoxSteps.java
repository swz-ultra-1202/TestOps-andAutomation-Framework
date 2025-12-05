package com.demoqa.stepdefinitions;

import com.demoqa.context.TestContext;
import com.demoqa.pages.TextBoxPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for Text Box feature.
 */
public class TextBoxSteps {
    
    private final TestContext testContext;
    private final TextBoxPage textBoxPage;
    
    public TextBoxSteps(TestContext testContext) {
        this.testContext = testContext;
        this.textBoxPage = testContext.getTextBoxPage();
    }
    
    @Given("I am on the Text Box page")
    public void iAmOnTheTextBoxPage() {
        textBoxPage.open();
    }
    
    @When("I enter {string} in full name field")
    public void iEnterInFullNameField(String fullName) {
        textBoxPage.enterFullName(fullName);
        testContext.setScenarioData("fullName", fullName);
    }
    
    @When("I enter {string} in email field")
    public void iEnterInEmailField(String email) {
        textBoxPage.enterEmail(email);
        testContext.setScenarioData("email", email);
    }
    
    @When("I enter {string} in current address field")
    public void iEnterInCurrentAddressField(String address) {
        textBoxPage.enterCurrentAddress(address);
        testContext.setScenarioData("currentAddress", address);
    }
    
    @When("I enter {string} in permanent address field")
    public void iEnterInPermanentAddressField(String address) {
        textBoxPage.enterPermanentAddress(address);
        testContext.setScenarioData("permanentAddress", address);
    }
    
    @When("I click the Submit button")
    public void iClickTheSubmitButton() {
        textBoxPage.clickSubmit();
    }
    
    @When("I fill the text box form with full name {string} and email {string}")
    public void iFillTheTextBoxFormWithNameAndEmail(String fullName, String email) {
        textBoxPage.enterFullName(fullName);
        textBoxPage.enterEmail(email);
        testContext.setScenarioData("fullName", fullName);
        testContext.setScenarioData("email", email);
    }
    
    @When("I fill the complete text box form")
    public void iFillTheCompleteTextBoxForm() {
        String fullName = testContext.getScenarioData("fullName");
        String email = testContext.getScenarioData("email");
        String currentAddress = testContext.getScenarioData("currentAddress");
        String permanentAddress = testContext.getScenarioData("permanentAddress");
        
        textBoxPage.fillForm(fullName, email, currentAddress, permanentAddress);
    }
    
    @Then("I should see the output section displayed")
    public void iShouldSeeTheOutputSectionDisplayed() {
        assertThat(textBoxPage.isOutputDisplayed())
            .as("Output section should be displayed")
            .isTrue();
    }
    
    @Then("the output should show name as {string}")
    public void theOutputShouldShowNameAs(String expectedName) {
        String actualName = textBoxPage.getOutputName();
        assertThat(actualName)
            .as("Output name should match")
            .isEqualTo(expectedName);
    }
    
    @Then("the output should show email as {string}")
    public void theOutputShouldShowEmailAs(String expectedEmail) {
        String actualEmail = textBoxPage.getOutputEmail();
        assertThat(actualEmail)
            .as("Output email should match")
            .isEqualTo(expectedEmail);
    }
    
    @Then("the output should show the submitted name and email")
    public void theOutputShouldShowTheSubmittedNameAndEmail() {
        String expectedName = testContext.getScenarioData("fullName");
        String expectedEmail = testContext.getScenarioData("email");
        
        assertThat(textBoxPage.isOutputDisplayed())
            .as("Output section should be displayed")
            .isTrue();
        
        assertThat(textBoxPage.getOutputName())
            .as("Output name should match")
            .isEqualTo(expectedName);
        
        assertThat(textBoxPage.getOutputEmail())
            .as("Output email should match")
            .isEqualTo(expectedEmail);
    }
    
    @Then("the output should display all submitted information")
    public void theOutputShouldDisplayAllSubmittedInformation() {
        assertThat(textBoxPage.isOutputDisplayed())
            .as("Output section should be displayed")
            .isTrue();
        
        String expectedName = testContext.getScenarioData("fullName");
        String expectedEmail = testContext.getScenarioData("email");
        
        if (expectedName != null) {
            assertThat(textBoxPage.getOutputName()).isEqualTo(expectedName);
        }
        if (expectedEmail != null) {
            assertThat(textBoxPage.getOutputEmail()).isEqualTo(expectedEmail);
        }
    }
}
