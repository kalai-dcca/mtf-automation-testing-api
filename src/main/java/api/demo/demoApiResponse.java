package api.demo;

import io.restassured.response.Response;

public class demoApiResponse {

    private Response response;

    public demoApiResponse(Response response) {
        this.response = response;
    }

    public int getStatusCode() {
        return response.statusCode();
    }

    public String getResponseBody() {
        return response.body().asString();
    }

    public boolean containsMessage(String expectedMessage) {
        return getResponseBody().contains(expectedMessage);
    }
}
