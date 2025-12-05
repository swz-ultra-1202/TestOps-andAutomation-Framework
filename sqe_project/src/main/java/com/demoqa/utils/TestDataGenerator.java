package com.demoqa.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Utility class to generate sample test data Excel file.
 * Run this class to create testdata.xlsx
 */
public class TestDataGenerator {

    public static void main(String[] args) {
        String filePath = "src/test/resources/testdata/testdata.xlsx";

        try (Workbook workbook = new XSSFWorkbook()) {
            // Create header style
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Create TextBoxData sheet
            createTextBoxSheet(workbook, headerStyle);

            // Create PracticeFormData sheet
            createPracticeFormSheet(workbook, headerStyle);

            // Create LoginData sheet
            createLoginSheet(workbook, headerStyle);

            // Create BookSearchData sheet
            createBookSearchSheet(workbook, headerStyle);

            // Write to file
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }

            System.out.println("Test data file created successfully: " + filePath);

        } catch (IOException e) {
            System.err.println("Error creating test data file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void createTextBoxSheet(Workbook workbook, CellStyle headerStyle) {
        Sheet sheet = workbook.createSheet("TextBoxData");

        // Headers
        Row headerRow = sheet.createRow(0);
        String[] headers = { "fullName", "email", "currentAddress", "permanentAddress" };
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
            sheet.setColumnWidth(i, 6000);
        }

        // Data rows
        String[][] data = {
                { "John Doe", "john.doe@example.com", "123 Main St, New York", "456 Oak Ave, LA" },
                { "Jane Smith", "jane.smith@test.com", "789 Pine Rd, Chicago", "321 Elm St, Miami" },
                { "Robert Johnson", "r.johnson@company.org", "555 Cedar Ln, Boston", "777 Maple Dr, Seattle" },
                { "Maria Garcia", "maria.g@domain.net", "888 Birch Way, Denver", "999 Spruce Ct, Phoenix" }
        };

        for (int i = 0; i < data.length; i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < data[i].length; j++) {
                row.createCell(j).setCellValue(data[i][j]);
            }
        }
    }

    private static void createPracticeFormSheet(Workbook workbook, CellStyle headerStyle) {
        Sheet sheet = workbook.createSheet("PracticeFormData");

        // Headers
        Row headerRow = sheet.createRow(0);
        String[] headers = { "firstName", "lastName", "email", "gender", "mobile", "hobbies", "address" };
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
            sheet.setColumnWidth(i, 5000);
        }

        // Data rows
        String[][] data = {
                { "John", "Doe", "john@example.com", "Male", "1234567890", "Sports", "123 Main St" },
                { "Jane", "Smith", "jane@test.com", "Female", "9876543210", "Reading", "456 Oak Ave" },
                { "Alex", "Johnson", "alex@company.org", "Other", "5551234567", "Sports,Music", "789 Pine Rd" },
                { "Maria", "Garcia", "maria@domain.net", "Female", "1112223333", "Reading,Music", "321 Elm St" }
        };

        for (int i = 0; i < data.length; i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < data[i].length; j++) {
                row.createCell(j).setCellValue(data[i][j]);
            }
        }
    }

    private static void createLoginSheet(Workbook workbook, CellStyle headerStyle) {
        Sheet sheet = workbook.createSheet("LoginData");

        // Headers
        Row headerRow = sheet.createRow(0);
        String[] headers = { "username", "password", "expectedResult" };
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
            sheet.setColumnWidth(i, 5000);
        }

        // Data rows (using invalid credentials for testing)
        String[][] data = {
                { "invaliduser1", "wrongpass1", "failure" },
                { "invaliduser2", "wrongpass2", "failure" },
                { "testuser", "test123", "failure" },
                { "admin", "admin123", "failure" }
        };

        for (int i = 0; i < data.length; i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < data[i].length; j++) {
                row.createCell(j).setCellValue(data[i][j]);
            }
        }
    }

    private static void createBookSearchSheet(Workbook workbook, CellStyle headerStyle) {
        Sheet sheet = workbook.createSheet("BookSearchData");

        // Headers
        Row headerRow = sheet.createRow(0);
        String[] headers = { "searchTerm", "expectedBook", "expectedAuthor" };
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
            sheet.setColumnWidth(i, 8000);
        }

        // Data rows
        String[][] data = {
                { "Git", "Git Pocket Guide", "Richard E. Silverman" },
                { "JavaScript", "Learning JavaScript Design Patterns", "Addy Osmani" },
                { "Design", "Designing Evolvable Web APIs with ASP.NET", "Glenn Block" },
                { "Speaking", "Speaking JavaScript", "Axel Rauschmayer" }
        };

        for (int i = 0; i < data.length; i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < data[i].length; j++) {
                row.createCell(j).setCellValue(data[i][j]);
            }
        }
    }
}
