package step.demoStep;

import api.demo.demoApiRequest;
import api.demo.demoApiResponse;
import core.BaseClass;
import enums.SheetType;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;
import utilities.AssertionUtils;
import utilities.ExcelUtils;
import utilities.LoggerUtil;

import java.util.Objects;

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
}
