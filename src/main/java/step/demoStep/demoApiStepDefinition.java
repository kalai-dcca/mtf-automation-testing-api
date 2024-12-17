package step.demoStep;

import api.demo.demoApiRequest;
import api.demo.demoApiResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.BaseClass;
import enums.SheetType;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;
import utilities.AssertionUtils;
import utilities.ExcelUtils;
import utilities.LoggerUtil;

import java.io.File;
import java.util.*;

import static core.BaseClass.getTestScenarioClass;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class demoApiStepDefinition {

    private demoApiRequest demoApiMethods = new demoApiRequest();
    private demoApiResponse apiResponse;

    @When("Launch Demo API service and Review API service with test data from the testcase file {string}")
    public void launchApiService(String testcaseFile) {
        // Read TestCaseId and data from Excel
        String testCaseId = ExcelUtils.readTestCaseIdFromExcel(testcaseFile);
        String jsonFile = ExcelUtils.readJsonFileForTestCase(testCaseId);

        // Fetch API endpoint, HTTP method, and expected values from the test case
        String endpoint = ExcelUtils.getEndpointFromTestCase(testCaseId);
        String method = ExcelUtils.getHttpMethodFromTestCase(testCaseId);

        // Launch API request
        apiResponse = demoApiMethods.launchDemoApi(endpoint, method, jsonFile);
    }

    @Then("Read test data from the sheet {string} for the {string}")
    public void readTestDataFromExcel(String sheetName, String testCaseId) {
        // Validate the test data read from the Excel file (for demonstration purposes)
        String testData = ExcelUtils.getTestDataFromSheet(sheetName, testCaseId);
        assertNotNull(testData, "Test data not found for TestCaseId: " + testCaseId);
    }

    @When("DemoAPI: Launch {string}, Method: {string}, request params: File {string}")
    public void launchApiWithParams(String endpoint, String method, String jsonFile) {
        // Launch the API using the DemoApiMethods
        apiResponse = demoApiMethods.launchDemoApi(endpoint, method, jsonFile);
    }

    //@Then("Verify status code {int} and message {string}")
    //public void verifyStatusCodeAndMessage(int expectedStatusCode, String expectedMessage) {
        // Validate the status code and response message
      //  assertEquals(expectedStatusCode, apiResponse.getStatusCode(), "Status code mismatch");
       // assertTrue(apiResponse.containsMessage(expectedMessage), "Response message mismatch");
    //}

    @Then("Verify status code {int} and message {string}")
    public void verifyStatusCodeAndMessage(int expectedStatusCode, String expectedMessage) {

        // Comment about entering state
        LoggerUtil.logger.info("Verifying status code {} and message {}", expectedStatusCode, expectedMessage);

        // Validate the status code and response message using the AssertionUtils method
        AssertionUtils.verifyStatusCodeAndMessage(getTestScenarioClass().getResponse(), expectedStatusCode, expectedMessage);

        // Comment about closing state
        LoggerUtil.logger.info("Validation completed successfully for status code {} and message {}",
                expectedStatusCode, expectedMessage);
        
    }

    @When("TestCaseDataSetup, File-{string}, Sheet-{string}, TestCase-{string}")
    public void testcasedatasetupFileSheetTestCase(String fileName, String sheet, String testCase) {
        try{
            ExcelUtils excelUtils = new ExcelUtils(BaseClass.TEST_DATA_PATH+fileName,sheet);
            getTestScenarioClass().setExcelUtils(excelUtils);
            getTestScenarioClass().setJsonObject(ExcelUtils.getDataBasedOnTestCaseAndCallType(testCase, sheet));
            getTestScenarioClass().setTestCaseID(testCase);
            getTestScenarioClass().setSheet(sheet);
            if(!sheet.equalsIgnoreCase(SheetType.CREATE.getEnumData())){
                getTestScenarioClass().setUserID(ExcelUtils.getUserId(testCase));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @When("DemoAPI: Launch {string}, Method: {string}")
    public void demoapiLaunchMethod(String url, String APICall) {
        getTestScenarioClass().setResponse(demoApiMethods.launchDemoApiAndGetResponse(url, APICall));
    }

    @Then("Verify status code {int}")
    public void verifyStatusCode(int expectedStatusCode) {

        // Comment about entering state
        LoggerUtil.logger.info("Verifying status code {}", expectedStatusCode);

        // Validate the status code
        AssertionUtils.verifyStatusCode(getTestScenarioClass().getResponse(), expectedStatusCode);

        // Comment about closing state
        LoggerUtil.logger.info("Validation completed successfully for status code {}", expectedStatusCode);
    }

    @When("DemoAPI: Launch {string}, QParam:{string} Method: {string}")
    public void demoapiLaunchQParamMethod(String url, String queryParam, String APICall) {

        getTestScenarioClass().setResponse(demoApiMethods.launchQueryDemoApiAndGetResponse(url,queryParam,APICall));
    }

    @Then("Verify status code {int} and the response array {string} contains the following key-value pairs:")
    public void verifyStatusCodeAndArrayEntries(int expectedStatusCode, String arrayField, DataTable dataTable) {
        // Validate the status code
        int actualStatusCode = getTestScenarioClass().getResponse().getStatusCode();
        assertThat("Unexpected status code!", actualStatusCode, equalTo(expectedStatusCode));
        LoggerUtil.logger.info("Status code validated successfully: {}", actualStatusCode);

        // Convert DataTable to Map for expected key-value pairs
        List<Map<String, String>> table = dataTable.asMaps(String.class, String.class);
        Map<String, Object> expectedEntries = new HashMap<>();
        table.forEach(row -> expectedEntries.put(row.get("key"), row.get("value")));

        // Validate the array field
        AssertionUtils.assertArrayContainsEntries(getTestScenarioClass().getResponse(), arrayField, expectedEntries);
    }

    @Then("Verify status code {int} and the response array {string} matches expected values from {string}")
    public void validateResponseArrayFromFile(int expectedStatusCode, String arrayField, String expectedFilePath) throws Exception {
        // Log entry into the state
        LoggerUtil.logger.info("Verifying status code {} and response array '{}' matches expected values from '{}'",
                expectedStatusCode, arrayField, expectedFilePath);

        // Load expected values from the JSON file
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> expectedData;

        try {
            expectedData = objectMapper.readValue(
                    new File("src/test/resources/testData/expectedResults/" + expectedFilePath),
                    new TypeReference<List<Map<String, Object>>>() {});
        } catch (Exception e) {
            LoggerUtil.logger.error("Failed to load expected data from file: {}", expectedFilePath, e);
            throw new RuntimeException("Unable to load expected data from file: " + expectedFilePath, e);
        }

        // Ensure the expected data is valid
        assertNotNull(expectedData, "Expected data file is empty or invalid!");

        // Validate the status code
        Response response = getTestScenarioClass().getResponse();
        AssertionUtils.verifyStatusCode(response, expectedStatusCode);

        // Validate the response array matches the expected values
        AssertionUtils.assertArrayContainsEntriesFromFile(response, arrayField, expectedData);

        // Log successful completion of the validation
        LoggerUtil.logger.info("Validation completed successfully for status code {} and response array '{}'",
                expectedStatusCode, arrayField);
    }

    @When("Fetch all pages from {string} with query param {string} and method {string}")
    public void fetchAllPages(String endpoint, String queryParam, String method) {
        LoggerUtil.logger.info("Fetching all pages from endpoint '{}' using query param '{}'", endpoint, queryParam);

        List<Map<String, Object>> allPagesData = new ArrayList<>();
        int currentPage = 1;
        int totalPages;

        do {
            // Send the request for the current page
            getTestScenarioClass().setResponse(
                    demoApiMethods.launchQueryDemoApiWithDynamicParam(endpoint, queryParam, String.valueOf(currentPage), method)
            );
            Response response = getTestScenarioClass().getResponse();

            // Log the current page response
            LoggerUtil.logger.info("Fetched page {}: {}", currentPage, response.getBody().asString());

            // Retrieve total pages from the response (only on the first page)
            if (currentPage == 1) {
                totalPages = response.jsonPath().getInt("total_pages");
                LoggerUtil.logger.info("Total pages: {}", totalPages);
            } else {
                totalPages = getTestScenarioClass().getResponse().jsonPath().getInt("total_pages");
            }

            // Extract the 'data' array from the current page and add it to allPagesData
            List<Map<String, Object>> currentPageData = response.jsonPath().getList("data");
            allPagesData.addAll(currentPageData);

            currentPage++;
        } while (currentPage <= totalPages);

        // Store the combined data in the scenario class for validation
        getTestScenarioClass().setCombinedData(allPagesData);

        LoggerUtil.logger.info("Fetched all pages successfully. Total records: {}", allPagesData.size());
    }

    @Then("Verify status code {int} and the combined response array {string} matches expected values from {string}")
    public void verifyCombinedResponse(int expectedStatusCode, String arrayField, String expectedFilePath) throws Exception {
        LoggerUtil.logger.info("Verifying combined response array '{}' matches expected values from '{}'", arrayField, expectedFilePath);

        // Retrieve the response and combined data
        Response response = getTestScenarioClass().getResponse();
        List<Map<String, Object>> combinedData = getTestScenarioClass().getCombinedData();

        // Validate status code
        AssertionUtils.verifyStatusCode(response, expectedStatusCode);

        // Load expected values from the JSON file
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> expectedData = objectMapper.readValue(
                new File("src/test/resources/" + expectedFilePath),
                new TypeReference<List<Map<String, Object>>>() {});

        // Validate combined data against the expected data
        AssertionUtils.assertAllPagesContains(combinedData, expectedData);

        LoggerUtil.logger.info("Validation successful for combined response array '{}'.", arrayField);
    }

}
