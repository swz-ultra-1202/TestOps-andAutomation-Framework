package com.demoqa.stepdefinitions;

import com.demoqa.context.TestContext;
import io.cucumber.java.en.Then;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Common step definitions shared across features.
 */
public class CommonSteps {

    private final TestContext testContext;

    public CommonSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Then("I should see the registration form")
    public void iShouldSeeTheRegistrationForm() {
        String currentUrl = testContext.getDriver().getCurrentUrl();
        assertThat(currentUrl)
                .as("Should be on registration page")
                .contains("register");
    }
}
