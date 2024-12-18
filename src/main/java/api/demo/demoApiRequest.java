package api.demo;

import api.base.ApiRequestClient;
import enums.SheetType;
import io.restassured.response.Response;
import utilities.ExcelUtils;

import java.util.Objects;

import static core.BaseClass.getTestScenarioClass;

public class demoApiRequest {


    private final ApiRequestClient apiRequestClient = new ApiRequestClient();

    // Method to handle Demo API request logic
    public demoApiResponse launchDemoApi(String endpoint, String method, String jsonFile) {
        // Send API request using ApiRequestClient
        Response response = apiRequestClient.sendApiRequest(endpoint, method, jsonFile);
        // Return wrapped response as ApiResponse
        return new demoApiResponse(response);
    }

    public demoApiResponse launchDemoApi(String endpoint, String method){
        Response response = apiRequestClient.sendApiRequest(endpoint, method);
        return new demoApiResponse(response);
    }

    public Response launchDemoApiAndGetResponse(String endpoint, String method){
        if(Objects.nonNull(getTestScenarioClass().getSheet())){
            if(!getTestScenarioClass().getSheet().equalsIgnoreCase(SheetType.CREATE.getEnumData())){
                endpoint = endpoint + "/" + getTestScenarioClass().getUserID();
            }
        }
        return apiRequestClient.sendApiRequest(endpoint, method);
    }

    public Response launchQueryDemoApiAndGetResponse(String endpoint, String queryParam, String method){
        endpoint = endpoint + "?" + queryParam + "=" + ExcelUtils.getUserId(getTestScenarioClass().getTestCaseID());
        return apiRequestClient.sendApiRequest(endpoint, method);
    }
}

