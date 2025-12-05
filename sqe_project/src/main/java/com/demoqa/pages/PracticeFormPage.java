package com.demoqa.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object for DemoQA Practice Form Page.
 * URL: https://demoqa.com/automation-practice-form
 */
public class PracticeFormPage extends BasePage {

    private static final String FORM_URL = "/automation-practice-form";

    @FindBy(id = "firstName")
    private WebElement firstNameInput;

    @FindBy(id = "lastName")
    private WebElement lastNameInput;

    @FindBy(id = "userEmail")
    private WebElement emailInput;

    @FindBy(css = "label[for='gender-radio-1']")
    private WebElement maleRadio;

    @FindBy(css = "label[for='gender-radio-2']")
    private WebElement femaleRadio;

    @FindBy(css = "label[for='gender-radio-3']")
    private WebElement otherRadio;

    @FindBy(id = "userNumber")
    private WebElement mobileInput;

    @FindBy(id = "dateOfBirthInput")
    private WebElement dateOfBirthInput;

    @FindBy(id = "subjectsInput")
    private WebElement subjectsInput;

    @FindBy(css = "label[for='hobbies-checkbox-1']")
    private WebElement sportsCheckbox;

    @FindBy(css = "label[for='hobbies-checkbox-2']")
    private WebElement readingCheckbox;

    @FindBy(css = "label[for='hobbies-checkbox-3']")
    private WebElement musicCheckbox;

    @FindBy(id = "currentAddress")
    private WebElement currentAddressInput;

    @FindBy(id = "state")
    private WebElement stateDropdown;

    @FindBy(id = "city")
    private WebElement cityDropdown;

    @FindBy(id = "submit")
    private WebElement submitButton;

    @FindBy(id = "example-modal-sizes-title-lg")
    private WebElement modalTitle;

    @FindBy(css = ".modal-body table tbody")
    private WebElement modalTable;

    @FindBy(id = "closeLargeModal")
    private WebElement closeModalButton;

    @FindBy(css = ".react-datepicker__month-select")
    private WebElement monthSelect;

    @FindBy(css = ".react-datepicker__year-select")
    private WebElement yearSelect;

    /**
     * Open Practice Form page directly.
     * 
     * @return PracticeFormPage instance for chaining
     */
    @Step("Open Practice Form page")
    public PracticeFormPage open() {
        navigateToPath(FORM_URL);
        waitForVisibility(firstNameInput);
        removeOverlays();
        return this;
    }

    /**
     * Enter first name.
     * 
     * @param firstName First name
     * @return PracticeFormPage instance for chaining
     */
    @Step("Enter first name: {firstName}")
    public PracticeFormPage enterFirstName(String firstName) {
        type(firstNameInput, firstName);
        return this;
    }

    /**
     * Enter last name.
     * 
     * @param lastName Last name
     * @return PracticeFormPage instance for chaining
     */
    @Step("Enter last name: {lastName}")
    public PracticeFormPage enterLastName(String lastName) {
        type(lastNameInput, lastName);
        return this;
    }

    /**
     * Enter email.
     * 
     * @param email Email address
     * @return PracticeFormPage instance for chaining
     */
    @Step("Enter email: {email}")
    public PracticeFormPage enterEmail(String email) {
        type(emailInput, email);
        return this;
    }

    /**
     * Select gender.
     * 
     * @param gender Gender (Male, Female, Other)
     * @return PracticeFormPage instance for chaining
     */
    @Step("Select gender: {gender}")
    public PracticeFormPage selectGender(String gender) {
        switch (gender.toLowerCase()) {
            case "male":
                jsClick(maleRadio);
                break;
            case "female":
                jsClick(femaleRadio);
                break;
            case "other":
                jsClick(otherRadio);
                break;
            default:
                throw new IllegalArgumentException("Invalid gender: " + gender);
        }
        return this;
    }

    /**
     * Enter mobile number.
     * 
     * @param mobile 10-digit mobile number
     * @return PracticeFormPage instance for chaining
     */
    @Step("Enter mobile number: {mobile}")
    public PracticeFormPage enterMobile(String mobile) {
        type(mobileInput, mobile);
        return this;
    }

    /**
     * Enter date of birth.
     * 
     * @param date Date in format DD MMM YYYY
     * @return PracticeFormPage instance for chaining
     */
    @Step("Enter date of birth: {date}")
    public PracticeFormPage enterDateOfBirth(String date) {
        click(dateOfBirthInput);
        dateOfBirthInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        dateOfBirthInput.sendKeys(date);
        dateOfBirthInput.sendKeys(Keys.ESCAPE);
        return this;
    }

    /**
     * Enter subject.
     * 
     * @param subject Subject name
     * @return PracticeFormPage instance for chaining
     */
    @Step("Enter subject: {subject}")
    public PracticeFormPage enterSubject(String subject) {
        subjectsInput.sendKeys(subject);
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".subjects-auto-complete__menu")));
        subjectsInput.sendKeys(Keys.ENTER);
        return this;
    }

    /**
     * Select hobby.
     * 
     * @param hobby Hobby name (Sports, Reading, Music)
     * @return PracticeFormPage instance for chaining
     */
    @Step("Select hobby: {hobby}")
    public PracticeFormPage selectHobby(String hobby) {
        switch (hobby.toLowerCase()) {
            case "sports":
                jsClick(sportsCheckbox);
                break;
            case "reading":
                jsClick(readingCheckbox);
                break;
            case "music":
                jsClick(musicCheckbox);
                break;
            default:
                throw new IllegalArgumentException("Invalid hobby: " + hobby);
        }
        return this;
    }

    /**
     * Select multiple hobbies.
     * 
     * @param hobbies Comma-separated hobbies
     * @return PracticeFormPage instance for chaining
     */
    @Step("Select hobbies: {hobbies}")
    public PracticeFormPage selectHobbies(String hobbies) {
        for (String hobby : hobbies.split(",")) {
            selectHobby(hobby.trim());
        }
        return this;
    }

    /**
     * Enter current address.
     * 
     * @param address Address text
     * @return PracticeFormPage instance for chaining
     */
    @Step("Enter current address: {address}")
    public PracticeFormPage enterAddress(String address) {
        type(currentAddressInput, address);
        return this;
    }

    /**
     * Select state from dropdown.
     * 
     * @param state State name
     * @return PracticeFormPage instance for chaining
     */
    @Step("Select state: {state}")
    public PracticeFormPage selectState(String state) {
        scrollToElement(stateDropdown);
        jsClick(stateDropdown);
        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'menu')]//div[text()='" + state + "']")));
        jsClick(option);
        return this;
    }

    /**
     * Select city from dropdown.
     * 
     * @param city City name
     * @return PracticeFormPage instance for chaining
     */
    @Step("Select city: {city}")
    public PracticeFormPage selectCity(String city) {
        scrollToElement(cityDropdown);
        jsClick(cityDropdown);
        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'menu')]//div[text()='" + city + "']")));
        jsClick(option);
        return this;
    }

    /**
     * Click submit button.
     * 
     * @return PracticeFormPage instance for chaining
     */
    @Step("Click Submit button")
    public PracticeFormPage clickSubmit() {
        scrollToElement(submitButton);
        jsClick(submitButton);
        return this;
    }

    /**
     * Fill the complete form with all required fields.
     */
    @Step("Fill complete practice form")
    public PracticeFormPage fillForm(String firstName, String lastName, String email,
            String gender, String mobile, String subjects,
            String hobbies, String address) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmail(email);
        selectGender(gender);
        enterMobile(mobile);
        if (subjects != null && !subjects.isEmpty()) {
            for (String subject : subjects.split(",")) {
                enterSubject(subject.trim());
            }
        }
        if (hobbies != null && !hobbies.isEmpty()) {
            selectHobbies(hobbies);
        }
        enterAddress(address);
        return this;
    }

    /**
     * Check if submission modal is displayed.
     * 
     * @return true if modal is visible
     */
    @Step("Check if submission modal is displayed")
    public boolean isModalDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(modalTitle));
            return modalTitle.getText().contains("Thanks for submitting the form");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get modal title text.
     * 
     * @return Modal title
     */
    @Step("Get modal title")
    public String getModalTitle() {
        waitForVisibility(modalTitle);
        return getText(modalTitle);
    }

    /**
     * Get value from modal table by label.
     * 
     * @param label Row label
     * @return Value from the row
     */
    @Step("Get modal value for: {label}")
    public String getModalValue(String label) {
        WebElement row = driver.findElement(
                By.xpath("//td[text()='" + label + "']/following-sibling::td"));
        return row.getText();
    }

    /**
     * Close the submission modal.
     * 
     * @return PracticeFormPage instance for chaining
     */
    @Step("Close submission modal")
    public PracticeFormPage closeModal() {
        jsClick(closeModalButton);
        return this;
    }
}
