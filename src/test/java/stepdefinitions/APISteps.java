package stepdefinitions;

import api.BaseAPI;
import data.UserData;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import utils.LoggerUtil;

import static org.hamcrest.MatcherAssert.assertThat;

public class APISteps {

    private BaseAPI baseAPI = new BaseAPI();
    private Response response;


    @When("I send a GET request to {string}")
    public void iSendAGETRequestTo(String endpoint) {
        response = baseAPI.get(endpoint);
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int statusCode) {
        assertThat(response.getStatusCode(), Matchers.equalTo(statusCode));
    }

    @Then("the response should contain {string}")
    public void theResponseShouldContain(String key) {
        assertThat(response.getBody().asString(), Matchers.containsString(key));
    }


    @When("I send a POST request to {string} with body:")
    public void iSendAPOSTRequestTo(String endpoint, String body) {
        LoggerUtil.logger.info("Request Body: " + body);
        response = baseAPI.post(endpoint, body);
        LoggerUtil.logger.info("Response Body: " + response.body().asString());
    }


//    @When("I send a DELETE request to {string}")
//    public void i_send_a_delete_request_to(String endpoint) {
//        String id = response.jsonPath().getString("id");
//        response = baseAPI.delete(endpoint + id);
//    }

    @When("I send a GET request by id to {string}")
    public void i_send_a_get_request_to(String endpoint) {
        LoggerUtil.logger.info("Sending GET request for" + endpoint+ UserData.id);
        response = baseAPI.get(endpoint + UserData.id);
        LoggerUtil.logger.info("Response Body: " + response.body().asString());
    }


    @Then("I delete the created user")
    public void iDeleteTheCreatedUser() {
        response = baseAPI.delete("/api/users" + UserData.id);
        LoggerUtil.logger.info("Response Body: " + response.body().asString());
    }

    @Then("I saved the Id for created user")
    public void iSavedTheIdForCreatedUser() {
        UserData.id= response.jsonPath().getString("id");
    }
}
