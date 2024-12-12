package api.demo;

import api.base.ApiRequestClient;
import io.restassured.response.Response;

public class demoApiRequest {


    private ApiRequestClient apiRequestClient = new ApiRequestClient();

    // Method to handle Demo API request logic
    public demoApiResponse launchDemoApi(String endpoint, String method, String jsonFile) {
        // Send API request using ApiRequestClient
        Response response = apiRequestClient.sendApiRequest(endpoint, method, jsonFile);
        // Return wrapped response as ApiResponse
        return new demoApiResponse(response);
    }
}

