package core;

import io.restassured.response.Response;
import org.apache.poi.ss.usermodel.Sheet;
import org.json.JSONObject;
import utilities.ExcelUtils;

public class TestScenarioClass extends BaseClass{
    private ExcelUtils excelUtils;
    private JSONObject jsonObject;
    private Response response;
    private String testCaseID;
    private String sheet;
    private int  userID;

    public ExcelUtils getExcelUtils() {
        return excelUtils;
    }

    public void setExcelUtils(ExcelUtils excelUtils) {
        this.excelUtils = excelUtils;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public String getTestCaseID() {
        return testCaseID;
    }

    public void setTestCaseID(String testCaseID) {
        this.testCaseID = testCaseID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getSheet() {
        return sheet;
    }

    public void setSheet(String sheet) {
        this.sheet = sheet;
    }
}
