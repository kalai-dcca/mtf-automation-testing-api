package utilities;

import io.cucumber.core.exception.ExceptionUtils;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.util.ExceptionUtil;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.Assertions;

public class AssertionUtils {

    /**
     * Validates that the response status code matches the expected value.
     *
     * @param response          The Response object.
     * @param expectedStatusCode The expected status code.
     */
    public static void verifyStatusCode(Response response, int expectedStatusCode){
        //boolean status = false;
        if(Objects.isNull(response)){
            LoggerUtil.logger.error("Error: Response is null");
            throw new RuntimeException(String.format("Actual[%s]::Expected[%s]::Status[%s]%n",
                    null,expectedStatusCode,false));
        }
        AssertionHandler.logAssertionError(() ->{
            Assertions.assertThat(response.getStatusCode()).isEqualTo((expectedStatusCode));
        },"Error: Received status code " + response.getStatusCode() + " instead of " + expectedStatusCode);
        //return status;

    }

    /**
     * Validates that the response body contains a specific field.
     *
     * @param response The Response object.
     * @param field    The field to check.
     */
    public static void assertFieldExists(Response response, String field){
        //boolean status = false;
        if(Objects.isNull(response)){
            LoggerUtil.logger.error("Error: Response is null");
            throw new RuntimeException(String.format("Actual[%s]::Expected[%s]::Status[%s]%n",
                    null,field,false));
        } else if ( StringUtils.isEmpty(field)) {
            LoggerUtil.logger.error("Error: Field is empty");
            throw new RuntimeException(String.format("Actual[%s]::Expected[%s]::Status[%s]%n",
                    "",field,false));
        }
        AssertionHandler.logAssertionError(() ->{
            Assertions.assertThat(response.jsonPath().getString(field)).isNotNull();
        },"Field '" + field + "' is missing in the response!");
        //return status;
    }


    /**
     * Validates that the response body contains a specific field with the expected value.
     *
     * @param response   The Response object.
     * @param field      The field to check.
     * @param expectedValue The expected value of the field.
     */
    public static void assertFieldValue(Response response, String field, Object expectedValue){
        //boolean status = false;
        if(Objects.isNull(response)){
            LoggerUtil.logger.error("Error: Response is null");
            throw new RuntimeException(String.format("Actual[%s]::Expected[%s]::Status[%s]%n",
                    null,expectedValue,false));
        } else if ( StringUtils.isEmpty(field)) {
            LoggerUtil.logger.error("Error: Field is empty");
            throw new RuntimeException(String.format("Actual[%s]::Expected[%s]::Status[%s]%n",
                    "",expectedValue,false));
        }

        AssertionHandler.logAssertionError(() ->{
            Assertions.assertThat(response.jsonPath().getString(field)).isEqualTo(expectedValue);
        },"Field '" + field + "' does not have the expected value " + expectedValue + "!");
        //return status;
    }

    /**
     * Validates that the response time is within the acceptable limit.
     *
     * @param response        The Response object.
     * @param maxResponseTime The maximum acceptable response time in milliseconds.
     */
    public static void assertResponseTime(Response response, long maxResponseTime){
       // boolean status = false;
        if(Objects.isNull(response)){
            LoggerUtil.logger.error("Error: Response is null");
            throw new RuntimeException(String.format("Actual[%s]::Status[%s]%n",
                    null,false));
        }
        AssertionHandler.logAssertionError(() ->{
            Assertions.assertThat(response.getTime()).isLessThanOrEqualTo(maxResponseTime);
        },"Response time exceeded " + maxResponseTime);
       // return status;
    }



    /**
     * Validates that a field in the response matches the provided regular expression.
     *
     * @param response The Response object.
     * @param field    The field to check.
     * @param regex    The regular expression to match.
     */
    public static void assertFieldMatchesRegex(Response response, String field, String regex){
        //boolean status = false;
        if(Objects.isNull(response)){
            LoggerUtil.logger.error("Error: Response is null");
            throw new RuntimeException(String.format("Actual[%s]::Expected[%s]::Status[%s]%n",
                    null,regex,false));
        }
        AssertionHandler.logAssertionError(() ->{
            Assertions.assertThat(response.jsonPath().getString(field)).matches(regex);
        },"Field '" + field + "' does not match the regex " + regex + "!");
        //return status;
    }

    /**
     * Validates that a map field in the response contains all expected key-value pairs.
     *
     * @param response      The Response object.
     * @param mapField      The map field to check.
     * @param expectedEntries The expected key-value pairs.
     */
    public static void assertMapContains(Response response, String mapField, Map<String, Object> expectedEntries){
        //boolean status = false;
        if(Objects.isNull(response)){
            LoggerUtil.logger.error("Error: Response is null");
            throw new RuntimeException(String.format("Actual[%s]::Status[%s]%n",
                    null,false));
        }
        System.out.println("this is the body" + response.getBody().asString());
        Map<String, Object> actualEntries = response.jsonPath().getMap(mapField);
        expectedEntries.forEach((key, value) -> {
            AssertionHandler.logAssertionError(() ->{
                Object actualValue = response.jsonPath().get(key);
                Assertions.assertThat(actualValue).isEqualTo(value);
            },"Map '" + mapField + "' does not contain expected entry " + value);
        });

        //return status;
    }

    /**
     * Validates that the response status code and a specific message in the response body match the expected values.
     *
     * @param response          The Response object.
     * @param expectedStatusCode The expected status code.
     * @param expectedMessage    The expected message value.
     */

    public static void verifyStatusCodeAndMessage(Response response, int expectedStatusCode, String expectedMessage) {
        //boolean status = false;
        // Suggest separating into two granular assertion methods for fail status clarity!!!!!!!
        if(Objects.isNull(response)){
            LoggerUtil.logger.error("Error: Response is null");
            throw new RuntimeException(String.format("Actual[%s]::Expected[%s]::Status[%s]%n",
                    null,expectedStatusCode,false));
        } else if (StringUtils.isEmpty(expectedMessage)) {
            LoggerUtil.logger.error("Error: Message is empty");
            throw new RuntimeException(String.format("Actual[%s]::Expected[%s]::Status[%s]%n",
                    "", expectedMessage, false));
        }

        SoftAssertions soft = new SoftAssertions();
        AssertionHandler.logAssertionError(() ->{
            soft.assertThat(response.getStatusCode()).isEqualTo((expectedStatusCode));
            soft.assertThat(response.getBody().asString()).contains((expectedMessage));
            soft.assertAll();
        }, "Error: Status code or message validation failed!");

        //return status;
    }

    /**
     * Validates that each object in an array field contains all expected key-value pairs.
     *
     * @param response       The Response object.
     * @param arrayField     The JSON path to the array (e.g., "data").
     * @param expectedEntries The expected key-value pairs to validate for each object in the array.
     */
    public static void assertArrayContainsEntries(Response response, String arrayField, Map<String, Object> expectedEntries) {
        // Null and input validation
        if (Objects.isNull(response)) {
            LoggerUtil.logger.error("Error: Response is null");
            throw new RuntimeException(String.format("Actual[%s]::ArrayField[%s]::Status[%s]%n",
                    null, arrayField, false));
        } else if (StringUtils.isEmpty(arrayField)) {
            LoggerUtil.logger.error("Error: Array field path is empty");
            throw new RuntimeException(String.format("ArrayField[%s]::ExpectedEntries[%s]::Status[%s]%n",
                    "", expectedEntries, false));
        } else if (Objects.isNull(expectedEntries) || expectedEntries.isEmpty()) {
            LoggerUtil.logger.error("Error: Expected entries map is null or empty");
            throw new RuntimeException(String.format("ArrayField[%s]::ExpectedEntries[%s]::Status[%s]%n",
                    arrayField, expectedEntries, false));
        }

        SoftAssertions soft = new SoftAssertions();

        try {
            // Retrieve array of maps from response
            List<Map<String, Object>> arrayObjects = response.jsonPath().getList(arrayField);
            if (arrayObjects == null || arrayObjects.isEmpty()) {
                LoggerUtil.logger.error("Error: Array field '{}' is empty or does not exist", arrayField);
                throw new AssertionError(String.format("ArrayField[%s]::Status[%s]%n", arrayField, false));
            }

            // Validate each entry in the array
            for (int i = 0; i < arrayObjects.size(); i++) {
                Map<String, Object> actualObject = arrayObjects.get(i);

                for (Map.Entry<String, Object> expectedEntry : expectedEntries.entrySet()) {
                    String key = expectedEntry.getKey();
                    Object expectedValue = expectedEntry.getValue();
                    Object actualValue = actualObject.get(key);

                    // Perform soft assertions for better failure reporting
                    soft.assertThat(actualValue)
                            .as("Mismatch at array index %d for key '%s'", i, key)
                            .isEqualTo(expectedValue);
                }
            }

            // Assert all collected results
            soft.assertAll();

            LoggerUtil.logger.info("Validation successful: Array field '{}' contains expected key-value pairs.", arrayField);
        } catch (Exception e) {
            LoggerUtil.logger.error("Error during array validation: {}", e.getMessage());
            throw new RuntimeException("Validation failed for array field: " + arrayField, e);
        }
    }

    /**
     * Validates that the array field in the response contains all expected key-value pairs, regardless of order.
     *
     * @param response       The Response object.
     * @param arrayField     The JSON path to the array (e.g., "data").
     * @param expectedEntries The list of expected objects (key-value pairs).
     */
    public static void assertArrayContainsEntriesFromFile(Response response, String arrayField, List<Map<String, Object>> expectedEntries) {
        if (Objects.isNull(response)) {
            throw new IllegalArgumentException("Response is null");
        }

        // Fetch the array as a list of maps
        List<Map<String, Object>> actualArray = response.jsonPath().getList(arrayField);
        if (actualArray == null || actualArray.isEmpty()) {
            throw new AssertionError("The array field '" + arrayField + "' is empty or does not exist.");
        }

        // Normalize actual and expected arrays: Convert maps to sorted strings for comparison
        Set<String> actualSet = actualArray.stream()
                .map(AssertionUtils::normalizeMap)
                .collect(Collectors.toSet());

        Set<String> expectedSet = expectedEntries.stream()
                .map(AssertionUtils::normalizeMap)
                .collect(Collectors.toSet());

        // Compare sets
        Assertions.assertThat(actualSet)
                .as("The response array does not match the expected data")
                .containsExactlyInAnyOrderElementsOf(expectedSet);

        System.out.println("Validation successful: The array '" + arrayField + "' matches the expected values (unordered).");
    }

    public static void assertAllPagesContains(List<Map<String, Object>> actualData, List<Map<String, Object>> expectedData) {
        if (Objects.isNull(actualData) || actualData.isEmpty()) {
            LoggerUtil.logger.error("Error: Combined response data is null or empty.");
            throw new AssertionError("Combined response data is null or empty.");
        }

        if (Objects.isNull(expectedData) || expectedData.isEmpty()) {
            LoggerUtil.logger.error("Error: Expected data is null or empty.");
            throw new AssertionError("Expected data is null or empty.");
        }

        SoftAssertions soft = new SoftAssertions();
        AssertionHandler.logAssertionError(() -> {
            soft.assertThat(actualData.size()).as("Record count mismatch").isEqualTo(expectedData.size());
            soft.assertThat(actualData).as("Data mismatch").containsExactlyInAnyOrderElementsOf(expectedData);
            soft.assertAll();
        }, "Validation failed: Combined response data does not match expected values.");
    }


    /**
     * Converts a map into a normalized string for consistent comparison.
     *
     * @param map The map to normalize.
     * @return A string representation of the map with sorted keys and values.
     */
    private static String normalizeMap(Map<String, Object> map) {
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining(","));
    }
}

