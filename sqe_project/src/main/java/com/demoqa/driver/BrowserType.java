package com.demoqa.driver;

/**
 * Enum representing supported browser types.
 */
public enum BrowserType {
    CHROME("chrome"),
    FIREFOX("firefox"),
    EDGE("edge");
    
    private final String browserName;
    
    BrowserType(String browserName) {
        this.browserName = browserName;
    }
    
    public String getBrowserName() {
        return browserName;
    }
    
    /**
     * Get BrowserType from string.
     * @param browserName Browser name string
     * @return BrowserType enum
     */
    public static BrowserType fromString(String browserName) {
        for (BrowserType type : BrowserType.values()) {
            if (type.getBrowserName().equalsIgnoreCase(browserName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unsupported browser: " + browserName);
    }
}
