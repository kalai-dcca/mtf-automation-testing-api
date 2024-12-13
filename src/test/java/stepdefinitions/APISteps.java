package stepdefinitions;

import api.BaseAPI;
import data.UserData;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import utils.AssertionUtils;

public class APISteps {

    private BaseAPI baseAPI = new BaseAPI();
    private Response response;


    @When("I send a GET request to {string}")
    public void iSendAGETRequestTo(String endpoint) {
        response = baseAPI.get(endpoint);
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int statusCode) {
        AssertionUtils.assertStatusCode(response, statusCode);
    }

    @Then("the response should contain {string}")
    public void theResponseShouldContain(String key) {
        AssertionUtils.assertFieldExists(response,key);
    }

    @When("I send a POST request to {string} with body:")
    public void iSendAPOSTRequestTo(String endpoint, String body) {
        response = baseAPI.post(endpoint, body);
    }

    @When("I send a GET request by id to {string}")
    public void i_send_a_get_request_to(String endpoint) {
        response = baseAPI.get(endpoint + UserData.id);
    }

    @Then("I delete the created user")
    public void iDeleteTheCreatedUser() {
        response = baseAPI.delete("/api/users" + UserData.id);
    }

    @Then("I saved the Id for created user")
    public void iSavedTheIdForCreatedUser() {
        UserData.id= response.jsonPath().getString("id");
    }
}