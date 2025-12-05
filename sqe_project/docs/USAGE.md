# Usage Guide

Comprehensive guide for using the DemoQA Test Automation Framework.

## Running Tests

### Basic Test Execution

```bash
# Run all tests
mvn clean test

# Run with specific browser
mvn test -Dbrowser=firefox

# Run in headless mode
mvn test -Dheadless=true
```

### Running by Tags

```bash
# Smoke tests only
mvn test -Dcucumber.filter.tags="@smoke"

# Specific feature
mvn test -Dcucumber.filter.tags="@textbox"

# Multiple tags (AND)
mvn test -Dcucumber.filter.tags="@smoke and @positive"

# Multiple tags (OR)
mvn test -Dcucumber.filter.tags="@textbox or @login"

# Exclude tests
mvn test -Dcucumber.filter.tags="not @skip"
```

### Common Tag Categories

| Tag | Description |
|-----|-------------|
| `@smoke` | Quick validation tests |
| `@positive` | Happy path scenarios |
| `@negative` | Error handling tests |
| `@data-driven` | Parameterized tests |
| `@excel` | Excel data tests |

## Writing New Tests

### 1. Create Feature File

Create `src/test/resources/features/newfeature.feature`:

```gherkin
@newfeature
Feature: New Feature Name
  As a user
  I want to perform some action
  So that I get expected result

  Background:
    Given I am on the target page

  @smoke @positive
  Scenario: Basic test scenario
    When I perform action
    Then I should see expected result

  @data-driven
  Scenario Outline: Data-driven scenario
    When I enter "<input>"
    Then I should see "<output>"

    Examples:
      | input  | output  |
      | value1 | result1 |
      | value2 | result2 |
```

### 2. Create Page Object

Create `src/main/java/com/demoqa/pages/NewPage.java`:

```java
package com.demoqa.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NewPage extends BasePage {
    
    private static final String PAGE_URL = "/new-page";
    
    @FindBy(id = "inputField")
    private WebElement inputField;
    
    @FindBy(id = "submitBtn")
    private WebElement submitButton;
    
    @FindBy(css = ".result")
    private WebElement resultText;
    
    @Step("Open new page")
    public NewPage open() {
        navigateToPath(PAGE_URL);
        waitForVisibility(inputField);
        return this;
    }
    
    @Step("Enter value: {value}")
    public NewPage enterValue(String value) {
        type(inputField, value);
        return this;
    }
    
    @Step("Click submit")
    public NewPage clickSubmit() {
        click(submitButton);
        return this;
    }
    
    @Step("Get result text")
    public String getResult() {
        waitForVisibility(resultText);
        return getText(resultText);
    }
}
```

### 3. Add to TestContext

Update `TestContext.java`:

```java
private NewPage newPage;

public NewPage getNewPage() {
    if (newPage == null) {
        newPage = new NewPage();
    }
    return newPage;
}
```

### 4. Create Step Definitions

Create `src/test/java/com/demoqa/stepdefinitions/NewSteps.java`:

```java
package com.demoqa.stepdefinitions;

import com.demoqa.context.TestContext;
import com.demoqa.pages.NewPage;
import io.cucumber.java.en.*;
import static org.assertj.core.api.Assertions.assertThat;

public class NewSteps {
    
    private final TestContext testContext;
    private final NewPage newPage;
    
    public NewSteps(TestContext testContext) {
        this.testContext = testContext;
        this.newPage = testContext.getNewPage();
    }
    
    @Given("I am on the target page")
    public void iAmOnTheTargetPage() {
        newPage.open();
    }
    
    @When("I enter {string}")
    public void iEnter(String input) {
        newPage.enterValue(input);
        newPage.clickSubmit();
    }
    
    @Then("I should see {string}")
    public void iShouldSee(String expected) {
        String actual = newPage.getResult();
        assertThat(actual).isEqualTo(expected);
    }
}
```

## Excel Data-Driven Tests

### 1. Add Data to Excel

Add a new sheet to `testdata.xlsx`:

| column1 | column2 | expected |
|---------|---------|----------|
| value1  | data1   | result1  |
| value2  | data2   | result2  |

### 2. Use in Feature File

```gherkin
@excel
Scenario: Test with Excel data
  Given I load test data from sheet "NewSheet"
  And I use test data row 1
  When I perform action with Excel data
  Then I verify result
```

### 3. Implement Step

```java
@When("I perform action with Excel data")
public void actionWithExcelData() {
    Map<String, String> row = testContext.getScenarioData("currentRow");
    String value = row.get("column1");
    // Use the value
}
```

## Viewing Reports

### Allure Report

```bash
# Generate and open in browser
mvn allure:serve

# Just generate (view later)
mvn allure:report
# Report at: target/site/allure-maven-plugin/index.html
```

### Cucumber Report

After test execution:
- Open `target/cucumber-reports/cucumber.html`

## Configuration Options

### config.properties

| Property | Default | Description |
|----------|---------|-------------|
| `base.url` | https://demoqa.com | Target application URL |
| `browser` | chrome | Browser to use |
| `headless` | false | Run without display |
| `implicit.wait` | 10 | Default wait (seconds) |
| `explicit.wait` | 15 | Max element wait |
| `page.load.timeout` | 30 | Page load timeout |

### Command Line Overrides

```bash
mvn test -Dbase.url=https://staging.example.com
mvn test -Dbrowser=firefox
mvn test -Dheadless=true
```

## Debugging Tips

### Enable Verbose Logging

Add to test run:
```bash
mvn test -Dorg.slf4j.simpleLogger.defaultLogLevel=debug
```

### View Screenshots

On failure, screenshots are saved to:
- `target/screenshots/`
- Also attached in Allure report

### Check Browser Console

Add to BasePage:
```java
public void printBrowserLogs() {
    LogEntries logs = driver.manage().logs().get(LogType.BROWSER);
    logs.forEach(entry -> logger.info(entry.getMessage()));
}
```

## CI/CD Usage

### GitHub Actions

Tests run automatically on:
- Push to `main` or `develop`
- Pull requests

View results:
1. Go to Actions tab
2. Click on workflow run
3. Download Allure/Cucumber report artifacts

### Running in CI

```bash
# Typical CI command
mvn clean test -Dheadless=true -Dbrowser=chrome

# With specific tags
mvn test -Dheadless=true -Dcucumber.filter.tags="@smoke"
```

## Troubleshooting

| Issue | Solution |
|-------|----------|
| Element not found | Increase explicit wait or check locator |
| Click intercepted | Use `jsClick()` or `scrollToElement()` |
| Stale element | Re-find element after page change |
| Timeout | Check network, increase timeout |
| Browser crash | Update browser/driver, use headless |
