package com.demoqa.driver;

import com.demoqa.config.ConfigurationManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * Factory class for WebDriver management.
 * Supports Chrome, Firefox, and Edge browsers with thread-safe driver management.
 */
public class DriverFactory {
    
    private static final Logger logger = LoggerFactory.getLogger(DriverFactory.class);
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static final ConfigurationManager config = ConfigurationManager.getInstance();
    
    private DriverFactory() {
        // Private constructor to prevent instantiation
    }
    
    /**
     * Initialize and get WebDriver instance.
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            initializeDriver();
        }
        return driverThreadLocal.get();
    }
    
    /**
     * Initialize WebDriver based on configuration.
     */
    private static void initializeDriver() {
        String browserName = config.getBrowser();
        BrowserType browserType = BrowserType.fromString(browserName);
        boolean headless = config.isHeadless();
        
        logger.info("Initializing {} browser (headless: {})", browserName, headless);
        
        WebDriver driver = createDriver(browserType, headless);
        configureDriver(driver);
        driverThreadLocal.set(driver);
        
        logger.info("Browser initialized successfully");
    }
    
    /**
     * Create WebDriver instance based on browser type.
     */
    private static WebDriver createDriver(BrowserType browserType, boolean headless) {
        switch (browserType) {
            case CHROME:
                return createChromeDriver(headless);
            case FIREFOX:
                return createFirefoxDriver(headless);
            case EDGE:
                return createEdgeDriver(headless);
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browserType);
        }
    }
    
    /**
     * Create Chrome WebDriver.
     */
    private static WebDriver createChromeDriver(boolean headless) {
        WebDriverManager.chromedriver().setup();
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--remote-allow-origins=*");
        
        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        }
        
        return new ChromeDriver(options);
    }
    
    /**
     * Create Firefox WebDriver.
     */
    private static WebDriver createFirefoxDriver(boolean headless) {
        WebDriverManager.firefoxdriver().setup();
        
        FirefoxOptions options = new FirefoxOptions();
        
        if (headless) {
            options.addArguments("--headless");
            options.addArguments("--width=1920");
            options.addArguments("--height=1080");
        }
        
        return new FirefoxDriver(options);
    }
    
    /**
     * Create Edge WebDriver.
     */
    private static WebDriver createEdgeDriver(boolean headless) {
        WebDriverManager.edgedriver().setup();
        
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        
        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        }
        
        return new EdgeDriver(options);
    }
    
    /**
     * Configure WebDriver timeouts.
     */
    private static void configureDriver(WebDriver driver) {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getImplicitWait()));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(config.getPageLoadTimeout()));
    }
    
    /**
     * Quit and cleanup WebDriver.
     */
    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            logger.info("Closing browser");
            driver.quit();
            driverThreadLocal.remove();
        }
    }
    
    /**
     * Check if driver is active.
     * @return true if driver exists
     */
    public static boolean hasDriver() {
        return driverThreadLocal.get() != null;
    }
}
