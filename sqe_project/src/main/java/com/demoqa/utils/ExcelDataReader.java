package com.demoqa.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Utility class for reading test data from Excel files.
 * Supports .xlsx format using Apache POI.
 */
public class ExcelDataReader {

    private static final Logger logger = LoggerFactory.getLogger(ExcelDataReader.class);
    private final String filePath;
    private Workbook workbook;

    /**
     * Create ExcelDataReader for specified file.
     * 
     * @param filePath Path to Excel file
     */
    public ExcelDataReader(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Open the Excel workbook.
     */
    private void openWorkbook() throws IOException {
        if (workbook == null) {
            FileInputStream fis = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(fis);
            logger.info("Opened Excel file: {}", filePath);
        }
    }

    /**
     * Close the workbook.
     */
    public void close() {
        if (workbook != null) {
            try {
                workbook.close();
                logger.info("Closed Excel file: {}", filePath);
            } catch (IOException e) {
                logger.error("Error closing workbook", e);
            }
        }
    }

    /**
     * Read all data from a sheet.
     * First row is treated as headers.
     * 
     * @param sheetName Name of the sheet
     * @return List of maps where each map represents a row with header-value pairs
     */
    public List<Map<String, String>> readSheet(String sheetName) {
        List<Map<String, String>> data = new ArrayList<>();

        try {
            openWorkbook();
            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                logger.error("Sheet not found: {}", sheetName);
                return data;
            }

            // Get headers from first row
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                logger.error("Header row is empty");
                return data;
            }

            List<String> headers = new ArrayList<>();
            for (Cell cell : headerRow) {
                headers.add(getCellValue(cell));
            }

            // Read data rows
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null)
                    continue;

                Map<String, String> rowData = new LinkedHashMap<>();
                for (int j = 0; j < headers.size(); j++) {
                    Cell cell = row.getCell(j);
                    String value = cell != null ? getCellValue(cell) : "";
                    rowData.put(headers.get(j), value);
                }
                data.add(rowData);
            }

            logger.info("Read {} rows from sheet: {}", data.size(), sheetName);

        } catch (IOException e) {
            logger.error("Error reading Excel file", e);
        }

        return data;
    }

    /**
     * Read a specific row by index.
     * 
     * @param sheetName Sheet name
     * @param rowIndex  Row index (0 = header, 1 = first data row)
     * @return Map of header-value pairs
     */
    public Map<String, String> readRow(String sheetName, int rowIndex) {
        List<Map<String, String>> allData = readSheet(sheetName);
        if (rowIndex > 0 && rowIndex <= allData.size()) {
            return allData.get(rowIndex - 1);
        }
        return new HashMap<>();
    }

    /**
     * Get data for Cucumber Scenario Outline.
     * Returns Object[][] format suitable for data providers.
     * 
     * @param sheetName Sheet name
     * @return Object array for data-driven tests
     */
    public Object[][] getDataForScenarioOutline(String sheetName) {
        List<Map<String, String>> data = readSheet(sheetName);
        Object[][] result = new Object[data.size()][1];

        for (int i = 0; i < data.size(); i++) {
            result[i][0] = data.get(i);
        }

        return result;
    }

    /**
     * Read specific columns from a sheet.
     * 
     * @param sheetName Sheet name
     * @param columns   Column names to read
     * @return List of maps with only specified columns
     */
    public List<Map<String, String>> readColumns(String sheetName, String... columns) {
        List<Map<String, String>> allData = readSheet(sheetName);
        List<Map<String, String>> filteredData = new ArrayList<>();

        for (Map<String, String> row : allData) {
            Map<String, String> filteredRow = new LinkedHashMap<>();
            for (String column : columns) {
                if (row.containsKey(column)) {
                    filteredRow.put(column, row.get(column));
                }
            }
            filteredData.add(filteredRow);
        }

        return filteredData;
    }

    /**
     * Find rows matching a condition.
     * 
     * @param sheetName  Sheet name
     * @param columnName Column to search in
     * @param value      Value to match
     * @return List of matching rows
     */
    public List<Map<String, String>> findRows(String sheetName, String columnName, String value) {
        List<Map<String, String>> allData = readSheet(sheetName);
        List<Map<String, String>> matchingRows = new ArrayList<>();

        for (Map<String, String> row : allData) {
            if (value.equals(row.get(columnName))) {
                matchingRows.add(row);
            }
        }

        return matchingRows;
    }

    /**
     * Get cell value as string.
     * 
     * @param cell Cell to read
     * @return String value
     */
    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getLocalDateTimeCellValue().toString();
                }
                // Remove decimal point for whole numbers
                double numValue = cell.getNumericCellValue();
                if (numValue == Math.floor(numValue)) {
                    return String.valueOf((long) numValue);
                }
                return String.valueOf(numValue);
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try {
                    return String.valueOf(cell.getNumericCellValue());
                } catch (Exception e) {
                    return cell.getStringCellValue();
                }
            default:
                return "";
        }
    }

    /**
     * Get all sheet names in the workbook.
     * 
     * @return List of sheet names
     */
    public List<String> getSheetNames() {
        List<String> sheetNames = new ArrayList<>();
        try {
            openWorkbook();
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                sheetNames.add(workbook.getSheetName(i));
            }
        } catch (IOException e) {
            logger.error("Error getting sheet names", e);
        }
        return sheetNames;
    }

    /**
     * Get row count in a sheet (excluding header).
     * 
     * @param sheetName Sheet name
     * @return Number of data rows
     */
    public int getRowCount(String sheetName) {
        try {
            openWorkbook();
            Sheet sheet = workbook.getSheet(sheetName);
            return sheet != null ? sheet.getLastRowNum() : 0;
        } catch (IOException e) {
            logger.error("Error getting row count", e);
            return 0;
        }
    }
}
