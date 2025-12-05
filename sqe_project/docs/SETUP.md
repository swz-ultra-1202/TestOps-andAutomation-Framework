# Setup Guide

Complete setup instructions for the DemoQA Test Automation Framework.

## Prerequisites

### 1. Java Development Kit (JDK)

**Required Version**: JDK 11 or higher

**Installation**:
- Download from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://adoptium.net/)
- Set `JAVA_HOME` environment variable
- Add `%JAVA_HOME%\bin` to PATH

**Verify Installation**:
```bash
java -version
javac -version
```

### 2. Apache Maven

**Required Version**: Maven 3.6+

**Installation**:
- Download from [Maven Official Site](https://maven.apache.org/download.cgi)
- Extract and set `MAVEN_HOME`
- Add `%MAVEN_HOME%\bin` to PATH

**Verify Installation**:
```bash
mvn -version
```

### 3. Web Browsers

Install at least one of:
- [Google Chrome](https://www.google.com/chrome/)
- [Mozilla Firefox](https://www.mozilla.org/firefox/)
- [Microsoft Edge](https://www.microsoft.com/edge)

> **Note**: WebDriverManager automatically downloads the required browser drivers.

### 4. Allure CLI (Optional)

For viewing reports locally:

**Windows (Scoop)**:
```bash
scoop install allure
```

**Mac (Homebrew)**:
```bash
brew install allure
```

**Manual Installation**:
- Download from [Allure GitHub Releases](https://github.com/allure-framework/allure2/releases)
- Extract and add `bin` folder to PATH

## Project Setup

### Step 1: Clone Repository

```bash
git clone https://github.com/yourusername/test-automation-framework.git
cd test-automation-framework
```

### Step 2: Install Dependencies

```bash
mvn clean install -DskipTests
```

This downloads all required dependencies:
- Selenium WebDriver
- Cucumber
- Apache POI
- Allure
- JUnit 5

### Step 3: Generate Test Data

Create the Excel test data file:

```bash
mvn compile exec:java -Dexec.mainClass="com.demoqa.utils.TestDataGenerator"
```

Or manually create `src/test/resources/testdata/testdata.xlsx` with the required sheets.

### Step 4: Configure Framework

Edit `src/test/resources/config.properties` as needed:

```properties
# Application URL
base.url=https://demoqa.com

# Browser (chrome, firefox, edge)
browser=chrome

# Headless mode for CI/CD
headless=false

# Timeouts
implicit.wait=10
explicit.wait=15
page.load.timeout=30
```

### Step 5: Verify Setup

Run a quick test:

```bash
mvn test -Dcucumber.filter.tags="@smoke" -Dtest=TestRunner
```

## IDE Setup

### IntelliJ IDEA

1. Open project: `File > Open > Select pom.xml`
2. Install plugins:
   - Cucumber for Java
   - Gherkin
3. Enable annotation processing: `Settings > Build > Compiler > Annotation Processors`

### Eclipse

1. Import: `File > Import > Maven > Existing Maven Projects`
2. Install plugins:
   - Cucumber Eclipse Plugin
3. Add JUnit 5 to build path

### VS Code

1. Open folder containing project
2. Install extensions:
   - Java Extension Pack
   - Cucumber (Gherkin) Full Support
   - Maven for Java

## Troubleshooting

### Maven Build Fails

```bash
# Clean Maven cache
mvn dependency:purge-local-repository

# Force update dependencies
mvn clean install -U
```

### Browser Driver Issues

```bash
# Clear WebDriverManager cache
rm -rf ~/.cache/selenium
```

### Test Execution Fails

1. Check browser is installed
2. Verify base URL is accessible
3. Check network/firewall settings
4. Try headless mode: `-Dheadless=true`

## Next Steps

- Read [Architecture Documentation](ARCHITECTURE.md)
- Follow [Usage Guide](USAGE.md)
- Run your first test!
