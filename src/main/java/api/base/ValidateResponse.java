package api.base;

import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import utilities.AssertionUtils;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static utilities.LoggerUtil.logger;

public class ValidateResponse {

    @Then("Verify status code {int} and message {string}")
    public void verifyStatusCodeAndMessage(Response response, int expectedStatusCode, String expectedMessage) {
        logger.info("Starting validation for status code {} and message {}", expectedStatusCode, expectedMessage);
        boolean result = AssertionUtils.verifyStatusCodeAndMessage(response, expectedStatusCode, "message", expectedMessage);
        assertThat("Status code or message validation failed!", result, equalTo(true));
        logger.info("Validation completed successfully for function [{}], status code {}, and message {}",
                AssertionUtils.Functions.VERIFY_STATUS_CODE_AND_MESSAGE, expectedStatusCode, expectedMessage);
    }

}
