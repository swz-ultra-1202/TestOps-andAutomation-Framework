# DemoQA Test Automation Framework

A comprehensive BDD (Behavior-Driven Development) Test Automation Framework for Web UI testing built with Java, Selenium WebDriver, Cucumber, and Allure Reports.

![Java](https://img.shields.io/badge/Java-11+-orange)
![Selenium](https://img.shields.io/badge/Selenium-4.15.0-green)
![Cucumber](https://img.shields.io/badge/Cucumber-7.14.0-brightgreen)
![Allure](https://img.shields.io/badge/Allure-2.24.0-blue)

## 🚀 Features

- **Cross-Browser Support**: Chrome, Firefox, Edge
- **BDD with Cucumber**: Gherkin syntax for readable test scenarios
- **Page Object Model**: Clean, maintainable test architecture
- **Data-Driven Testing**: Excel-based test data management
- **Allure Reporting**: Beautiful, detailed test reports
- **Parallel Execution**: Thread-safe design
- **CI/CD Ready**: GitHub Actions workflow included

## 📁 Project Structure

```
sqe_project/
├── pom.xml                          # Maven configuration
├── .github/workflows/test.yml       # CI/CD pipeline
├── src/
│   ├── main/java/com/demoqa/
│   │   ├── config/                  # Configuration management
│   │   ├── driver/                  # WebDriver factory
│   │   ├── pages/                   # Page Object classes
│   │   └── utils/                   # Utilities (Excel, Screenshot)
│   └── test/
│       ├── java/com/demoqa/
│       │   ├── runners/             # Test runner
│       │   ├── stepdefinitions/     # Cucumber step definitions
│       │   └── context/             # Test context
│       └── resources/
│           ├── features/            # Gherkin feature files
│           ├── testdata/            # Excel test data
│           └── *.properties         # Configuration files
└── docs/                            # Documentation
```

## 🛠️ Prerequisites

- **Java JDK 11** or higher
- **Maven 3.6+**
- **Chrome/Firefox/Edge** browser installed
- **Allure CLI** (optional, for local report viewing)

## ⚡ Quick Start

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/test-automation-framework.git
cd test-automation-framework
```

### 2. Install Dependencies

```bash
mvn clean install -DskipTests
```

### 3. Generate Test Data (First Time Only)

```powershell
# PowerShell (Windows) - use single quotes around -D parameters with dots
mvn exec:java '-Dexec.mainClass=com.demoqa.utils.TestDataGenerator'
```

```bash
# Bash/Linux/Mac
mvn exec:java -Dexec.mainClass="com.demoqa.utils.TestDataGenerator"
```

### 4. Run All Tests

```bash
mvn clean test
```

### 5. Generate Allure Report

```bash
mvn allure:serve
```

## 🧪 Running Tests

### Run All Tests

```bash
mvn clean test
```

### Run Tests by Tag

> **⚠️ PowerShell Users**: In PowerShell, `-D` parameters containing dots (`.`) must be wrapped in **single quotes** to prevent parsing errors.

```powershell
# PowerShell (Windows) - ALWAYS use single quotes for -D parameters with dots

# Run smoke tests
mvn test '-Dcucumber.filter.tags=@smoke'

# Run specific feature
mvn test '-Dcucumber.filter.tags=@textbox'

# Run multiple tags (use double quotes inside single quotes)
mvn test '-Dcucumber.filter.tags=@smoke and @positive'

# Exclude tags
mvn test '-Dcucumber.filter.tags=not @skip'
```

```bash
# Bash/Linux/Mac - double quotes work fine
mvn test -Dcucumber.filter.tags="@smoke"
mvn test -Dcucumber.filter.tags="@textbox"
```

### Run with Different Browser

```powershell
# PowerShell (Windows)
mvn test '-Dbrowser=chrome'
mvn test '-Dbrowser=firefox'
mvn test '-Dbrowser=edge'
```

```bash
# Bash/Linux/Mac
mvn test -Dbrowser=chrome
mvn test -Dbrowser=firefox
mvn test -Dbrowser=edge
```

### Run in Headless Mode

```powershell
# PowerShell (Windows)
mvn test '-Dheadless=true'
```

```bash
# Bash/Linux/Mac
mvn test -Dheadless=true
```

## 📊 Test Reports

### Allure Report

```bash
# Generate and open report
mvn allure:serve

# Just generate report
mvn allure:report
```

### Cucumber HTML Report

After test execution, find reports at:

- `target/cucumber-reports/cucumber.html`

## 📝 Writing Test Cases

### Feature File Example

```gherkin
@smoke @textbox
Feature: Text Box Functionality

  Scenario Outline: Fill text box with valid data
    Given I am on the Text Box page
    When I enter "<fullName>" in full name field
    And I enter "<email>" in email field
    And I click the Submit button
    Then I should see the output section displayed

    Examples:
      | fullName   | email              |
      | John Doe   | john@example.com   |
      | Jane Smith | jane@test.com      |
```

### Adding New Page Objects

```java
public class NewPage extends BasePage {
    @FindBy(id = "elementId")
    private WebElement element;
  
    public NewPage open() {
        navigateToPath("/new-page");
        return this;
    }
  
    public void doSomething() {
        click(element);
    }
}
```

## 🏗️ Architecture

| Component                | Description                      |
| ------------------------ | -------------------------------- |
| `DriverFactory`        | Thread-safe WebDriver management |
| `BasePage`             | Common page utilities            |
| `ConfigurationManager` | Singleton configuration          |
| `ExcelDataReader`      | Excel test data handling         |
| `TestContext`          | Shared state between steps       |
| `Hooks`                | Cucumber before/after hooks      |

## 📋 Test Scenarios

| Feature       | Scenarios | Tags              |
| ------------- | --------- | ----------------- |
| Text Box      | 4         | `@textbox`      |
| Practice Form | 4         | `@practiceform` |
| Login         | 6         | `@login`        |
| Book Store    | 8         | `@bookstore`    |
| Excel Data    | 5         | `@excel`        |

## 🔧 Configuration

Edit `src/test/resources/config.properties`:

```properties
base.url=https://demoqa.com
browser=chrome
headless=false
implicit.wait=10
explicit.wait=15
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License.

## 📚 Documentation

- [Setup Guide](docs/SETUP.md)
- [Architecture](docs/ARCHITECTURE.md)
- [Usage Guide](docs/USAGE.md)
