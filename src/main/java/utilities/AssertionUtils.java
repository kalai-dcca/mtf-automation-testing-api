package utilities;

import io.restassured.response.Response;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AssertionUtils {

    /**
     * Validates that the response status code matches the expected value.
     *
     * @param response          The Response object.
     * @param expectedStatusCode The expected status code.
     */
    public static void assertStatusCode(Response response, int expectedStatusCode) {
        assertThat("Unexpected status code!", response.getStatusCode(), equalTo(expectedStatusCode));
    }



    /**
     * Validates that the response body contains a specific field.
     *
     * @param response The Response object.
     * @param field    The field to check.
     */
    public static void assertFieldExists(Response response, String field) {
        assertThat("Field '" + field + "' is missing in the response!",
                response.jsonPath().get(field), notNullValue());
    }



    /**
     * Validates that the response body contains a specific field with the expected value.
     *
     * @param response   The Response object.
     * @param field      The field to check.
     * @param expectedValue The expected value of the field.
     */
    public static void assertFieldValue(Response response, String field, Object expectedValue) {
        assertThat("Field '" + field + "' does not have the expected value!",
                response.jsonPath().get(field), equalTo(expectedValue));
    }




    /**
     * Validates that the response time is within the acceptable limit.
     *
     * @param response        The Response object.
     * @param maxResponseTime The maximum acceptable response time in milliseconds.
     */
    public static void assertResponseTime(Response response, long maxResponseTime) {
        assertThat("Response time exceeded the acceptable limit!",
                response.getTime(), lessThanOrEqualTo(maxResponseTime));
    }



    /**
     * Validates that a field in the response matches the provided regular expression.
     *
     * @param response The Response object.
     * @param field    The field to check.
     * @param regex    The regular expression to match.
     */
    public static void assertFieldMatchesRegex(Response response, String field, String regex) {
        String value = response.jsonPath().getString(field);
        assertThat("Field '" + field + "' does not match the regex!",
                value, matchesPattern(regex));
    }



    /**
     * Validates that a map field in the response contains all expected key-value pairs.
     *
     * @param response      The Response object.
     * @param mapField      The map field to check.
     * @param expectedEntries The expected key-value pairs.
     */
    public static void assertMapContains(Response response, String mapField, Map<String, Object> expectedEntries) {
        Map<String, Object> actualEntries = response.jsonPath().getMap(mapField);
        expectedEntries.forEach((key, value) -> {
            assertThat("Map '" + mapField + "' does not contain expected entry!",
                    actualEntries, hasEntry(key, value));
        });
    }

}
