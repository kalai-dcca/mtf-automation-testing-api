package utilities;

public class ExcelUtils {

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
}

