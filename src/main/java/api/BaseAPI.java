package api;

import config.Configuration;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.LoggerUtil;

import java.util.Map;

public class BaseAPI {



    protected RequestSpecification request;

    public BaseAPI() {
        RestAssured.baseURI = Configuration.getProperty("base.url");
        request = RestAssured.given().contentType("application/json");
    }


    /**
     * This method used for GET request
     * @param endpoint    Path of the GET request.
     */
    public Response get(String endpoint) {
        LoggerUtil.logger.info("Sending GET request for" + endpoint);
        return request.get(endpoint);
    }


    /**
     * This method used for POST request
     * @param endpoint    Path of the POST request.
     * @param body        Request payload.
     */
    public Response post(String endpoint, String body) {
        LoggerUtil.logger.info("Sending POST request for" + endpoint+ "with body "+body);
        return request.body(body).post(endpoint);
    }

    /**
     * This method used for PUT request
     * @param endpoint    Path of the PUT request.
     * @param body        Request payload.
     */
    public Response put(String endpoint, String body) {
        LoggerUtil.logger.info("Sending PUT request for" + endpoint+ "with body "+body);
        return request.body(body).put(endpoint);
    }


    /**
     * This method used for DELETE request
     * @param endpoint    Path of the DELETE request.
     */
    public Response delete(String endpoint) {
        LoggerUtil.logger.info("Sending DELETE request for" + endpoint);
        return request.delete(endpoint);
    }


    /**
     * This method used for PATCH request
     * @param endpoint    Path of the PATCH request.
     * @param body        Request payload.
     */
    public Response patch(String endpoint, String body) {
        LoggerUtil.logger.info("Sending PATCH request for" + endpoint+ "with body "+body);
        return request.body(body).patch(endpoint);
    }

    /**
     * This method used for adding headers for request
     * @param headers    Map of headers
     */
    public void addHeaders(Map<String, String> headers) {
        LoggerUtil.logger.debug("Adding Headers for Request "+headers);
        request.headers(headers);
    }

    /**
     * This method used for adding Query Params
     * @param queryParams    Map of Query Params
     */
    public void addQueryParams(Map<String, String> queryParams) {
        LoggerUtil.logger.debug("Adding Query Params for Request "+queryParams);
        request.queryParams(queryParams);
    }


    /**
     * This method used for Extract Field from Response
     * @param response    Response Object
     * @param fieldPath   Field Path to be extracted
     * @param type        Type of the field
     */
    public <T> T extractField(Response response, String fieldPath, Class<T> type) {
        return response.jsonPath().getObject(fieldPath, type);
    }





}
