package com.demoqa.utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for capturing screenshots.
 * Integrates with Allure for report attachments.
 */
public class ScreenshotUtils {

    private static final Logger logger = LoggerFactory.getLogger(ScreenshotUtils.class);
    private static final String SCREENSHOT_DIR = "target/screenshots";

    private ScreenshotUtils() {
        // Private constructor to prevent instantiation
    }

    /**
     * Capture screenshot and save to file.
     * 
     * @param driver WebDriver instance
     * @param name   Screenshot name
     * @return Path to saved screenshot
     */
    public static String captureScreenshot(WebDriver driver, String name) {
        try {
            // Create screenshot directory if it doesn't exist
            Path screenshotDir = Paths.get(SCREENSHOT_DIR);
            if (!Files.exists(screenshotDir)) {
                Files.createDirectories(screenshotDir);
            }

            // Generate filename with timestamp
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = String.format("%s_%s.png", name, timestamp);
            Path filePath = screenshotDir.resolve(fileName);

            // Capture screenshot
            TakesScreenshot ts = (TakesScreenshot) driver;
            byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
            Files.write(filePath, screenshot);

            logger.info("Screenshot saved: {}", filePath);
            return filePath.toString();

        } catch (IOException e) {
            logger.error("Failed to capture screenshot", e);
            return null;
        }
    }

    /**
     * Capture screenshot and attach to Allure report.
     * 
     * @param driver WebDriver instance
     * @param name   Attachment name
     */
    public static void attachScreenshot(WebDriver driver, String name) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);

            Allure.addAttachment(name, "image/png", new ByteArrayInputStream(screenshot), ".png");
            logger.info("Screenshot attached to Allure: {}", name);

        } catch (Exception e) {
            logger.error("Failed to attach screenshot to Allure", e);
        }
    }

    /**
     * Capture screenshot on test failure.
     * Saves to file and attaches to Allure.
     * 
     * @param driver   WebDriver instance
     * @param testName Failed test name
     */
    public static void captureOnFailure(WebDriver driver, String testName) {
        String cleanName = testName.replaceAll("[^a-zA-Z0-9]", "_");

        // Save to file
        captureScreenshot(driver, "FAILED_" + cleanName);

        // Attach to Allure
        attachScreenshot(driver, "Failure Screenshot - " + testName);
    }

    /**
     * Capture screenshot as byte array.
     * 
     * @param driver WebDriver instance
     * @return Screenshot bytes
     */
    public static byte[] getScreenshotBytes(WebDriver driver) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        return ts.getScreenshotAs(OutputType.BYTES);
    }

    /**
     * Capture screenshot as Base64 string.
     * 
     * @param driver WebDriver instance
     * @return Base64 encoded screenshot
     */
    public static String getScreenshotBase64(WebDriver driver) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        return ts.getScreenshotAs(OutputType.BASE64);
    }
}
