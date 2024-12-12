package step.demoStep;

import api.BaseAPI;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import utilities.AssertionUtils;

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

}
