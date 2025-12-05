package com.demoqa.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Singleton Configuration Manager for managing framework properties.
 * Loads configuration from config.properties file.
 */
public class ConfigurationManager {
    
    private static ConfigurationManager instance;
    private final Properties properties;
    private static final String CONFIG_FILE = "src/test/resources/config.properties";
    
    private ConfigurationManager() {
        properties = new Properties();
        loadProperties();
    }
    
    /**
     * Get singleton instance of ConfigurationManager.
     * @return ConfigurationManager instance
     */
    public static synchronized ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
        }
        return instance;
    }
    
    /**
     * Load properties from config file.
     */
    private void loadProperties() {
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file: " + CONFIG_FILE, e);
        }
    }
    
    /**
     * Get property value by key.
     * @param key Property key
     * @return Property value or null if not found
     */
    public String getProperty(String key) {
        String systemProperty = System.getProperty(key);
        if (systemProperty != null) {
            return systemProperty;
        }
        return properties.getProperty(key);
    }
    
    /**
     * Get property value with default.
     * @param key Property key
     * @param defaultValue Default value if not found
     * @return Property value or default
     */
    public String getProperty(String key, String defaultValue) {
        String value = getProperty(key);
        return value != null ? value : defaultValue;
    }
    
    /**
     * Get base URL for the application under test.
     * @return Base URL
     */
    public String getBaseUrl() {
        return getProperty("base.url", "https://demoqa.com");
    }
    
    /**
     * Get browser type for test execution.
     * @return Browser name (chrome, firefox, edge)
     */
    public String getBrowser() {
        return getProperty("browser", "chrome");
    }
    
    /**
     * Check if headless mode is enabled.
     * @return true if headless mode is enabled
     */
    public boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless", "false"));
    }
    
    /**
     * Get implicit wait timeout in seconds.
     * @return Implicit wait timeout
     */
    public int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit.wait", "10"));
    }
    
    /**
     * Get explicit wait timeout in seconds.
     * @return Explicit wait timeout
     */
    public int getExplicitWait() {
        return Integer.parseInt(getProperty("explicit.wait", "15"));
    }
    
    /**
     * Get page load timeout in seconds.
     * @return Page load timeout
     */
    public int getPageLoadTimeout() {
        return Integer.parseInt(getProperty("page.load.timeout", "30"));
    }
    
    /**
     * Get Excel test data file path.
     * @return Path to Excel file
     */
    public String getExcelFilePath() {
        return getProperty("excel.file.path", "src/test/resources/testdata/testdata.xlsx");
    }
}
