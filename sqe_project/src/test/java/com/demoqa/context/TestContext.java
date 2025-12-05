package com.demoqa.context;

import com.demoqa.driver.DriverFactory;
import com.demoqa.pages.*;
import com.demoqa.utils.ExcelDataReader;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

/**
 * Test Context class for sharing state between step definitions.
 * Manages page objects and test data across scenarios.
 */
public class TestContext {

    private WebDriver driver;
    private final Map<String, Object> scenarioData;
    private ExcelDataReader excelDataReader;

    // Page Objects
    private HomePage homePage;
    private TextBoxPage textBoxPage;
    private PracticeFormPage practiceFormPage;
    private LoginPage loginPage;
    private BookStorePage bookStorePage;

    public TestContext() {
        this.scenarioData = new HashMap<>();
    }

    /**
     * Get WebDriver instance.
     * 
     * @return WebDriver
     */
    public WebDriver getDriver() {
        if (driver == null) {
            driver = DriverFactory.getDriver();
        }
        return driver;
    }

    /**
     * Store data in scenario context.
     * 
     * @param key   Data key
     * @param value Data value
     */
    public void setScenarioData(String key, Object value) {
        scenarioData.put(key, value);
    }

    /**
     * Get data from scenario context.
     * 
     * @param key Data key
     * @return Data value
     */
    @SuppressWarnings("unchecked")
    public <T> T getScenarioData(String key) {
        return (T) scenarioData.get(key);
    }

    /**
     * Clear scenario data.
     */
    public void clearScenarioData() {
        scenarioData.clear();
    }

    /**
     * Get Excel data reader.
     * 
     * @param filePath Path to Excel file
     * @return ExcelDataReader instance
     */
    public ExcelDataReader getExcelDataReader(String filePath) {
        if (excelDataReader == null) {
            excelDataReader = new ExcelDataReader(filePath);
        }
        return excelDataReader;
    }

    /**
     * Close Excel data reader.
     */
    public void closeExcelReader() {
        if (excelDataReader != null) {
            excelDataReader.close();
            excelDataReader = null;
        }
    }

    // Page Object Getters with lazy initialization

    public HomePage getHomePage() {
        if (homePage == null) {
            homePage = new HomePage();
        }
        return homePage;
    }

    public TextBoxPage getTextBoxPage() {
        if (textBoxPage == null) {
            textBoxPage = new TextBoxPage();
        }
        return textBoxPage;
    }

    public PracticeFormPage getPracticeFormPage() {
        if (practiceFormPage == null) {
            practiceFormPage = new PracticeFormPage();
        }
        return practiceFormPage;
    }

    public LoginPage getLoginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage();
        }
        return loginPage;
    }

    public BookStorePage getBookStorePage() {
        if (bookStorePage == null) {
            bookStorePage = new BookStorePage();
        }
        return bookStorePage;
    }

    /**
     * Reset all page objects (useful between scenarios).
     */
    public void resetPages() {
        homePage = null;
        textBoxPage = null;
        practiceFormPage = null;
        loginPage = null;
        bookStorePage = null;
    }
}
