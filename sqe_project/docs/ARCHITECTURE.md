# Architecture Documentation

Detailed explanation of the framework architecture and design decisions.

## Overview

This framework follows industry best practices for test automation:

```
┌─────────────────────────────────────────────────────────────┐
│                     Test Layer (BDD)                        │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐  │
│  │   Feature   │  │    Step     │  │      Hooks &        │  │
│  │   Files     │──│ Definitions │──│   Test Context      │  │
│  │  (Gherkin)  │  │   (Java)    │  │                     │  │
│  └─────────────┘  └─────────────┘  └─────────────────────┘  │
└────────────────────────────┬────────────────────────────────┘
                             │
┌────────────────────────────▼────────────────────────────────┐
│                   Page Object Layer                          │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐  │
│  │  BasePage   │◄─│  HomePage   │  │   TextBoxPage       │  │
│  │  (Abstract) │  ├─────────────┤  ├─────────────────────┤  │
│  │             │◄─│ LoginPage   │  │  PracticeFormPage   │  │
│  │             │  ├─────────────┤  ├─────────────────────┤  │
│  │             │◄─│ BookStore   │  │      ...more        │  │
│  └─────────────┘  └─────────────┘  └─────────────────────┘  │
└────────────────────────────┬────────────────────────────────┘
                             │
┌────────────────────────────▼────────────────────────────────┐
│                    Core Framework Layer                      │
│  ┌──────────────┐  ┌────────────────┐  ┌─────────────────┐  │
│  │DriverFactory │  │ Configuration  │  │    Utilities    │  │
│  │  (Browser)   │  │    Manager     │  │ (Excel,Screenshot│  │
│  └──────────────┘  └────────────────┘  └─────────────────┘  │
└────────────────────────────┬────────────────────────────────┘
                             │
┌────────────────────────────▼────────────────────────────────┐
│              External Dependencies                           │
│  ┌──────────────┐  ┌─────────────┐  ┌─────────────────────┐ │
│  │   Selenium   │  │   Cucumber  │  │      Allure         │ │
│  │   WebDriver  │  │    BDD      │  │    Reporting        │ │
│  └──────────────┘  └─────────────┘  └─────────────────────┘ │
└─────────────────────────────────────────────────────────────┘
```

## Design Patterns

### 1. Page Object Model (POM)

**Why POM?**
- Separates test logic from page structure
- Reduces code duplication
- Easier maintenance when UI changes
- Improved readability

**Implementation**:

```java
public class LoginPage extends BasePage {
    @FindBy(id = "userName")
    private WebElement usernameInput;
    
    @FindBy(id = "password")
    private WebElement passwordInput;
    
    public LoginPage enterCredentials(String user, String pass) {
        type(usernameInput, user);
        type(passwordInput, pass);
        return this;
    }
}
```

### 2. Factory Pattern (DriverFactory)

**Why Factory?**
- Centralized browser management
- Thread-safe for parallel execution
- Easy browser switching via configuration

**Implementation**:

```java
public class DriverFactory {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    
    public static WebDriver getDriver() {
        if (driver.get() == null) {
            initializeDriver();
        }
        return driver.get();
    }
}
```

### 3. Singleton Pattern (ConfigurationManager)

**Why Singleton?**
- Single source of configuration
- Memory efficient
- Thread-safe access

### 4. Context Pattern (TestContext)

**Why Context?**
- Share state between step definitions
- Manage page object lifecycle
- Store scenario-specific data

## Key Components

### DriverFactory

| Feature | Description |
|---------|-------------|
| ThreadLocal | Thread-safe driver instances |
| WebDriverManager | Automatic driver downloads |
| Browser Options | Configurable Chrome/Firefox/Edge |
| Timeouts | Configurable wait strategies |

### BasePage

| Method | Purpose |
|--------|---------|
| `click()` | Wait and click element |
| `type()` | Clear and type text |
| `waitForVisibility()` | Explicit wait for element |
| `jsClick()` | JavaScript click for overlays |
| `scrollToElement()` | Scroll element into view |

### TestContext

| Responsibility | How |
|----------------|-----|
| Share WebDriver | Singleton driver instance |
| Manage Pages | Lazy page object creation |
| Store Data | HashMap for scenario data |
| Handle Excel | ExcelDataReader lifecycle |

## BDD Structure

### Feature Files

```
features/
├── textbox.feature      # Text box form tests
├── practiceform.feature # Practice form tests
├── login.feature        # Login functionality tests
├── bookstore.feature    # Book store tests
└── excel_data.feature   # Excel-driven tests
```

### Step Definitions

Each feature has corresponding step definitions:

```
stepdefinitions/
├── Hooks.java           # Setup/teardown
├── TextBoxSteps.java    # Text box steps
├── PracticeFormSteps.java
├── LoginSteps.java
├── BookStoreSteps.java
└── ExcelDataSteps.java  # Excel data steps
```

## Data Management

### Excel Integration

```
testdata/
└── testdata.xlsx
    ├── TextBoxData      # Text box test data
    ├── PracticeFormData # Form test data
    ├── LoginData        # Login credentials
    └── BookSearchData   # Search terms
```

**Usage in Steps**:
```java
@Given("I load test data from sheet {string}")
public void loadData(String sheet) {
    ExcelDataReader reader = new ExcelDataReader(path);
    List<Map<String, String>> data = reader.readSheet(sheet);
}
```

## Reporting

### Allure Integration

- Automatic step logging via annotations
- Screenshot on failure
- Scenario metadata (tags, feature names)
- Historical trends

### Report Features

| Feature | Implementation |
|---------|----------------|
| Steps | `@Step` annotations |
| Screenshots | `attachScreenshot()` |
| Categories | Cucumber tags |
| Environment | `allure.properties` |

## CI/CD Integration

### GitHub Actions Workflow

```yaml
jobs:
  test:
    - Checkout code
    - Setup Java 11
    - Install Chrome
    - Run tests (headless)
    - Generate Allure report
    - Deploy to GitHub Pages
```

## Best Practices Followed

1. **DRY Principle**: Reusable page methods
2. **Single Responsibility**: Each class has one purpose
3. **Explicit Waits**: No hard-coded sleeps
4. **Configuration Over Code**: External properties
5. **Meaningful Names**: Clear method/variable names
6. **Documentation**: Javadoc and comments
7. **Error Handling**: Graceful failure handling
