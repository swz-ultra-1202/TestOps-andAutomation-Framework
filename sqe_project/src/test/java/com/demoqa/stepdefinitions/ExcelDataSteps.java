package com.demoqa.stepdefinitions;

import com.demoqa.context.TestContext;
import com.demoqa.config.ConfigurationManager;
import com.demoqa.utils.ExcelDataReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for Excel data-driven tests.
 */
public class ExcelDataSteps {

    private final TestContext testContext;
    private List<Map<String, String>> testData;
    private Map<String, String> currentRow;

    public ExcelDataSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("I load test data from sheet {string}")
    public void iLoadTestDataFromSheet(String sheetName) {
        String excelPath = ConfigurationManager.getInstance().getExcelFilePath();
        ExcelDataReader reader = testContext.getExcelDataReader(excelPath);
        testData = reader.readSheet(sheetName);
        testContext.setScenarioData("testData", testData);
    }

    @Given("I use test data row {int}")
    public void iUseTestDataRow(int rowNumber) {
        if (testData != null && rowNumber > 0 && rowNumber <= testData.size()) {
            currentRow = testData.get(rowNumber - 1);
            testContext.setScenarioData("currentRow", currentRow);
        }
    }

    @When("I fill the text box form with data from Excel")
    public void iFillTheTextBoxFormWithDataFromExcel() {
        if (currentRow != null) {
            var textBoxPage = testContext.getTextBoxPage();

            if (currentRow.containsKey("fullName")) {
                textBoxPage.enterFullName(currentRow.get("fullName"));
            }
            if (currentRow.containsKey("email")) {
                textBoxPage.enterEmail(currentRow.get("email"));
            }
            if (currentRow.containsKey("currentAddress")) {
                textBoxPage.enterCurrentAddress(currentRow.get("currentAddress"));
            }
            if (currentRow.containsKey("permanentAddress")) {
                textBoxPage.enterPermanentAddress(currentRow.get("permanentAddress"));
            }
        }
    }

    @When("I fill the practice form with data from Excel")
    public void iFillThePracticeFormWithDataFromExcel() {
        if (currentRow != null) {
            var practiceFormPage = testContext.getPracticeFormPage();

            if (currentRow.containsKey("firstName")) {
                practiceFormPage.enterFirstName(currentRow.get("firstName"));
            }
            if (currentRow.containsKey("lastName")) {
                practiceFormPage.enterLastName(currentRow.get("lastName"));
            }
            if (currentRow.containsKey("email")) {
                practiceFormPage.enterEmail(currentRow.get("email"));
            }
            if (currentRow.containsKey("gender")) {
                practiceFormPage.selectGender(currentRow.get("gender"));
            }
            if (currentRow.containsKey("mobile")) {
                practiceFormPage.enterMobile(currentRow.get("mobile"));
            }
            if (currentRow.containsKey("hobbies")) {
                practiceFormPage.selectHobbies(currentRow.get("hobbies"));
            }
            if (currentRow.containsKey("address")) {
                practiceFormPage.enterAddress(currentRow.get("address"));
            }
        }
    }

    @When("I login with credentials from Excel")
    public void iLoginWithCredentialsFromExcel() {
        if (currentRow != null) {
            var loginPage = testContext.getLoginPage();

            String username = currentRow.getOrDefault("username", "");
            String password = currentRow.getOrDefault("password", "");

            loginPage.login(username, password);
        }
    }

    @Then("the test data should have {int} rows")
    public void theTestDataShouldHaveRows(int expectedRows) {
        assertThat(testData)
                .as("Test data should have expected number of rows")
                .hasSize(expectedRows);
    }

    @Then("the current row should have column {string} with value {string}")
    public void theCurrentRowShouldHaveColumnWithValue(String column, String expectedValue) {
        assertThat(currentRow)
                .as("Current row should contain column: " + column)
                .containsKey(column);
        assertThat(currentRow.get(column))
                .as("Column value should match")
                .isEqualTo(expectedValue);
    }
}
