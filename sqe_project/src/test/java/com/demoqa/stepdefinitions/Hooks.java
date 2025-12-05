package com.demoqa.stepdefinitions;

import com.demoqa.context.TestContext;
import com.demoqa.driver.DriverFactory;
import com.demoqa.utils.ScreenshotUtils;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Cucumber Hooks for setup and teardown operations.
 * Handles browser lifecycle and screenshot capture.
 */
public class Hooks {

    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);
    private final TestContext testContext;

    public Hooks(TestContext testContext) {
        this.testContext = testContext;
    }

    /**
     * Before each scenario - Initialize browser.
     */
    @Before(order = 0)
    public void setUp(Scenario scenario) {
        logger.info("========================================");
        logger.info("Starting Scenario: {}", scenario.getName());
        logger.info("Tags: {}", scenario.getSourceTagNames());
        logger.info("========================================");

        // Initialize the driver
        testContext.getDriver();

        // Add scenario info to Allure
        Allure.epic("DemoQA Test Automation");
        Allure.feature(getFeatureName(scenario));
    }

    /**
     * After each step - Capture screenshot on failure.
     */
    @AfterStep
    public void afterStep(Scenario scenario) {
        if (scenario.isFailed() && DriverFactory.hasDriver()) {
            // Capture screenshot on step failure
            WebDriver driver = testContext.getDriver();
            ScreenshotUtils.attachScreenshot(driver, "Step Failed - " + scenario.getName());
        }
    }

    /**
     * After each scenario - Cleanup.
     */
    @After(order = 0)
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed() && DriverFactory.hasDriver()) {
                // Capture final screenshot on failure
                WebDriver driver = testContext.getDriver();
                ScreenshotUtils.captureOnFailure(driver, scenario.getName());

                // Log failure details
                logger.error("Scenario FAILED: {}", scenario.getName());
            } else {
                logger.info("Scenario PASSED: {}", scenario.getName());
            }

            // Cleanup test context
            testContext.clearScenarioData();
            testContext.closeExcelReader();
            testContext.resetPages();

        } finally {
            // Quit browser
            DriverFactory.quitDriver();
            logger.info("Browser closed");
            logger.info("========================================\n");
        }
    }

    /**
     * Extract feature name from scenario for Allure reporting.
     */
    private String getFeatureName(Scenario scenario) {
        String uri = scenario.getUri().toString();
        String featureName = uri.substring(uri.lastIndexOf('/') + 1);
        featureName = featureName.replace(".feature", "");
        return capitalize(featureName.replace("-", " ").replace("_", " "));
    }

    /**
     * Capitalize first letter of each word.
     */
    private String capitalize(String text) {
        String[] words = text.split(" ");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        return result.toString().trim();
    }
}
