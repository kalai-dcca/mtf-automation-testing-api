package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ExcelUtils {
  
   private Workbook workbook;
    private Sheet sheet;
    private String filePath;

    // Constructor to initialize Excel file and sheet
    public ExcelUtils(String filePath, String sheetName) {

        this.filePath = filePath;
        try {
            FileInputStream fis = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("Sheet: " + sheetName + " does not exist in the file: " + filePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Excel file: " + e.getMessage());
        }
    }

    public static String readTestCaseIdFromExcel(String testcaseFile) {
        // Read TestCaseId from the specified Excel file
        return "TC1000"; // For simplicity
    }

    public static String readJsonFileForTestCase(String testCaseId) {
        // Read the JSON file associated with the TestCaseId
        return testCaseId + ".json"; // For simplicity, we assume json file is named as TestCaseId.json
    }

    public static String getEndpointFromTestCase(String testCaseId) {
        // Fetch endpoint from Excel sheet
        return "/api/demo"; // Dummy endpoint for illustration
    }

    public static String getHttpMethodFromTestCase(String testCaseId) {
        // Fetch HTTP method (GET/POST/PUT/DELETE) from the Excel sheet
        return "POST"; // Dummy method for illustration
    }

    public static String getTestDataFromSheet(String sheetName, String testCaseId) {
        // Implement logic to fetch test data from the specified sheet in the Excel file
        return "Test data for " + testCaseId;
    }
   
    
    // Method to get cell data as String

    public String getCellData(int rowNum, int colNum) {
        Row row = sheet.getRow(rowNum);
        if (row == null) return null;
        Cell cell = row.getCell(colNum);
        if (cell == null) return null;
        return cell.toString();
    }

    // Method to get all data from the sheet
    public List<List<String>> getSheetData() {

        List<List<String>> data = new ArrayList<>();
        for (Row row : sheet) {
            List<String> rowData = new ArrayList<>();
            for (Cell cell : row) {
                rowData.add(cell.toString());
            }
            data.add(rowData);
        }
        return data;
    }
    // Method to set data into a specific cell
    public void setCellData(int rowNum, int colNum, String value) {
        Row row = sheet.getRow(rowNum);
        if (row == null) row = sheet.createRow(rowNum);
        Cell cell = row.getCell(colNum);
        if (cell == null) cell = row.createCell(colNum);
        cell.setCellValue(value);
        save();
    }
    // Method to save changes to the Excel file

    private void save() {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save Excel file: " + e.getMessage());
        }
    }
    // Method to close the workbook

    public void close() {
        try {
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to close Excel workbook: " + e.getMessage());
        }
    }
}

