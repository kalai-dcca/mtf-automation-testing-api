package api;

import config.Configuration;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseAPI {
    protected RequestSpecification request;

    public BaseAPI() {
        RestAssured.baseURI = Configuration.getProperty("base.url");
        request = RestAssured.given().contentType("application/json");
    }

    public Response get(String endpoint) {
        return request.get(endpoint);
    }

    public Response post(String endpoint, String body) {
        return request.body(body).post(endpoint);
    }

    public Response put(String endpoint, String body) {
        return request.body(body).put(endpoint);
    }

    public Response delete(String endpoint) {
        return request.delete(endpoint);
    }

}
