package com.demoqa.stepdefinitions;

import com.demoqa.context.TestContext;
import com.demoqa.pages.PracticeFormPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for Practice Form feature.
 */
public class PracticeFormSteps {
    
    private final TestContext testContext;
    private final PracticeFormPage practiceFormPage;
    
    public PracticeFormSteps(TestContext testContext) {
        this.testContext = testContext;
        this.practiceFormPage = testContext.getPracticeFormPage();
    }
    
    @Given("I am on the Practice Form page")
    public void iAmOnThePracticeFormPage() {
        practiceFormPage.open();
    }
    
    @When("I enter first name {string}")
    public void iEnterFirstName(String firstName) {
        practiceFormPage.enterFirstName(firstName);
        testContext.setScenarioData("firstName", firstName);
    }
    
    @When("I enter last name {string}")
    public void iEnterLastName(String lastName) {
        practiceFormPage.enterLastName(lastName);
        testContext.setScenarioData("lastName", lastName);
    }
    
    @When("I enter user email {string}")
    public void iEnterUserEmail(String email) {
        practiceFormPage.enterEmail(email);
        testContext.setScenarioData("email", email);
    }
    
    @When("I select gender {string}")
    public void iSelectGender(String gender) {
        practiceFormPage.selectGender(gender);
        testContext.setScenarioData("gender", gender);
    }
    
    @When("I enter mobile number {string}")
    public void iEnterMobileNumber(String mobile) {
        practiceFormPage.enterMobile(mobile);
        testContext.setScenarioData("mobile", mobile);
    }
    
    @When("I enter subject {string}")
    public void iEnterSubject(String subject) {
        practiceFormPage.enterSubject(subject);
        testContext.setScenarioData("subject", subject);
    }
    
    @When("I select hobby {string}")
    public void iSelectHobby(String hobby) {
        practiceFormPage.selectHobby(hobby);
        testContext.setScenarioData("hobby", hobby);
    }
    
    @When("I select hobbies {string}")
    public void iSelectHobbies(String hobbies) {
        practiceFormPage.selectHobbies(hobbies);
        testContext.setScenarioData("hobbies", hobbies);
    }
    
    @When("I enter address {string}")
    public void iEnterAddress(String address) {
        practiceFormPage.enterAddress(address);
        testContext.setScenarioData("address", address);
    }
    
    @When("I select state {string}")
    public void iSelectState(String state) {
        practiceFormPage.selectState(state);
        testContext.setScenarioData("state", state);
    }
    
    @When("I select city {string}")
    public void iSelectCity(String city) {
        practiceFormPage.selectCity(city);
        testContext.setScenarioData("city", city);
    }
    
    @When("I submit the practice form")
    public void iSubmitThePracticeForm() {
        practiceFormPage.clickSubmit();
    }
    
    @When("I fill the practice form with:")
    public void iFillThePracticeFormWith(io.cucumber.datatable.DataTable dataTable) {
        var data = dataTable.asMap(String.class, String.class);
        
        if (data.containsKey("firstName")) practiceFormPage.enterFirstName(data.get("firstName"));
        if (data.containsKey("lastName")) practiceFormPage.enterLastName(data.get("lastName"));
        if (data.containsKey("email")) practiceFormPage.enterEmail(data.get("email"));
        if (data.containsKey("gender")) practiceFormPage.selectGender(data.get("gender"));
        if (data.containsKey("mobile")) practiceFormPage.enterMobile(data.get("mobile"));
        if (data.containsKey("hobbies")) practiceFormPage.selectHobbies(data.get("hobbies"));
        if (data.containsKey("address")) practiceFormPage.enterAddress(data.get("address"));
        
        // Store for verification
        data.forEach(testContext::setScenarioData);
    }
    
    @Then("the form submission modal should be displayed")
    public void theFormSubmissionModalShouldBeDisplayed() {
        assertThat(practiceFormPage.isModalDisplayed())
            .as("Submission modal should be displayed")
            .isTrue();
    }
    
    @Then("the modal should show {string} as {string}")
    public void theModalShouldShowAs(String label, String expectedValue) {
        String actualValue = practiceFormPage.getModalValue(label);
        assertThat(actualValue)
            .as("Modal value for " + label + " should match")
            .contains(expectedValue);
    }
    
    @Then("the modal title should be {string}")
    public void theModalTitleShouldBe(String expectedTitle) {
        String actualTitle = practiceFormPage.getModalTitle();
        assertThat(actualTitle)
            .as("Modal title should match")
            .isEqualTo(expectedTitle);
    }
    
    @Then("the submission confirmation should display the student name")
    public void theSubmissionConfirmationShouldDisplayTheStudentName() {
        String firstName = testContext.getScenarioData("firstName");
        String lastName = testContext.getScenarioData("lastName");
        String expectedName = firstName + " " + lastName;
        
        String actualValue = practiceFormPage.getModalValue("Student Name");
        assertThat(actualValue)
            .as("Student name should match")
            .isEqualTo(expectedName);
    }
    
    @Then("I close the submission modal")
    public void iCloseTheSubmissionModal() {
        practiceFormPage.closeModal();
    }
}
