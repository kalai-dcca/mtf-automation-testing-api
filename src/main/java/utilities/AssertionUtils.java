package utilities;

import io.cucumber.core.exception.ExceptionUtils;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.util.ExceptionUtil;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AssertionUtils {

    public enum Functions{
        VERIFY_STATUS_CODE,
        ASSERT_FIELD_EXISTS,
        ASSERT_FIELD_VALUE,
        ASSERT_RESPONSE_TIME,
        ASSERT_FIELD_MATCHES_REGEX,
        ASSERT_MAP_CONTAINS,
        VERIFY_STATUS_CODE_AND_MESSAGE
    }

    /**
     * Validates that the response status code matches the expected value.
     *
     * @param response          The Response object.
     * @param expectedStatusCode The expected status code.
     */
    public static void assertStatusCode(Response response, int expectedStatusCode) {
        assertThat("Unexpected status code!", response.getStatusCode(), equalTo(expectedStatusCode));
    }

    public static boolean verifyStatusCode(Response response, int expectedStatusCode){
        boolean status = false;
        try{
            if(Objects.nonNull(response)){
                status = Objects.equals(response.getStatusCode(),expectedStatusCode);
                LoggerUtil.logger.info(String.format("Function[%s]::Actual[%s]::Expected[%s]::Status[%s]%n",
                        Functions.VERIFY_STATUS_CODE,response.getStatusCode(),expectedStatusCode,status));
            }else{
                throw new Exception();
            }
        } catch (Exception e) {
            LoggerUtil.logger.error(String.format("Function[%s]::Actual[%s]::Expected[%s]::Status[%s]%n",
                    Functions.VERIFY_STATUS_CODE,response.getStatusCode(),expectedStatusCode,status));
            LoggerUtil.logger.error(ExceptionUtils.printStackTrace(e));
        }
        return status;

    }



    /**
     * Validates that the response body contains a specific field.
     *
     * @param response The Response object.
     * @param field    The field to check.
     */
//    public static void assertFieldExists(Response response, String field) {
//        assertThat("Field '" + field + "' is missing in the response!",
//                response.jsonPath().get(field), notNullValue());
//    }

    public static boolean assertFieldExists(Response response, String field){
        boolean status = false;
        try{
            if(Objects.nonNull(response) && StringUtils.isNoneBlank(field)){
                assertThat("Field '" + field + "' is missing in the response!",
                        response.jsonPath().get(field), notNullValue());
                status = true;
                LoggerUtil.logger.info(String.format("Function[%s]::Field[%s]::Status[%s]%n",
                        Functions.ASSERT_FIELD_EXISTS, field, status));
            }else{
                throw new Exception();
            }
        } catch (Exception e) {
            LoggerUtil.logger.error(String.format("Function[%s]::Field[%s]::Status[%s]%n",
                    Functions.ASSERT_FIELD_EXISTS, field, status));
            LoggerUtil.logger.error(ExceptionUtils.printStackTrace(e));
        }
        return status;
    }



    /**
     * Validates that the response body contains a specific field with the expected value.
     *
     * @param response   The Response object.
     * @param field      The field to check.
     * @param expectedValue The expected value of the field.
     */
//    public static void assertFieldValue(Response response, String field, Object expectedValue) {
//        assertThat("Field '" + field + "' does not have the expected value!",
//                response.jsonPath().get(field), equalTo(expectedValue));
//    }

    public static boolean assertFieldValue(Response response, String field, Object expectedValue){
        boolean status = false;
        try{
            if(Objects.nonNull(response) && StringUtils.isNoneBlank(field)){
                assertThat("Field '" + field + "' does not have the expected value!",
                        response.jsonPath().get(field), equalTo(expectedValue));
                status = true;
                LoggerUtil.logger.info(String.format("Function[%s]::Field[%s]::Value[%s]::Status[%s]%n",
                        Functions.ASSERT_FIELD_VALUE, field,expectedValue,status));
            }else{
                throw new Exception();
            }
        } catch (Exception e) {
            LoggerUtil.logger.error(String.format("Function[%s]::Field[%s]::Value[%s]::Status[%s]%n",
                    Functions.ASSERT_FIELD_VALUE, field,expectedValue,status));
            LoggerUtil.logger.error(ExceptionUtils.printStackTrace(e));
        }
        return status;
    }





    /**
     * Validates that the response time is within the acceptable limit.
     *
     * @param response        The Response object.
     * @param maxResponseTime The maximum acceptable response time in milliseconds.
     */
//    public static void assertResponseTime(Response response, long maxResponseTime) {
//        assertThat("Response time exceeded the acceptable limit!",
//                response.getTime(), lessThanOrEqualTo(maxResponseTime));
//    }

    public static boolean assertResponseTime(Response response, long maxResponseTime){
        boolean status = false;
        try{
            if(Objects.nonNull(response)){
                assertThat("Response time exceeded the acceptable limit!",
                        response.getTime(), lessThanOrEqualTo(maxResponseTime));
                status = true;
                LoggerUtil.logger.info(String.format("Function[%s]::MaxResponseTime[%s]::Status[%s]%n",
                        Functions.ASSERT_RESPONSE_TIME, maxResponseTime,status));
            }else{
                throw new Exception();
            }
        } catch (Exception e) {
            LoggerUtil.logger.error(String.format("Function[%s]::MaxResponseTime[%s]::Status[%s]%n",
                    Functions.ASSERT_RESPONSE_TIME, maxResponseTime,status));
            LoggerUtil.logger.error(ExceptionUtils.printStackTrace(e));
        }
        return status;
    }



    /**
     * Validates that a field in the response matches the provided regular expression.
     *
     * @param response The Response object.
     * @param field    The field to check.
     * @param regex    The regular expression to match.
     */
//    public static void assertFieldMatchesRegex(Response response, String field, String regex) {
//        String value = response.jsonPath().getString(field);
//        assertThat("Field '" + field + "' does not match the regex!",
//                value, matchesPattern(regex));
//    }

    public static boolean assertFieldMatchesRegex(Response response, String field, String regex){
        boolean status = false;
        try{
            if(Objects.nonNull(response)){
                String value = response.jsonPath().getString(field);
                assertThat("Field '" + field + "' does not match the regex!",
                        value, matchesPattern(regex));
                status = true;
                LoggerUtil.logger.info(String.format("Function[%s]::Field[%s]::Regex[%s]::Status[%s]%n",
                        Functions.ASSERT_FIELD_MATCHES_REGEX, field,regex,status));
            }else{
                throw new Exception();
            }
        } catch (Exception e) {
            LoggerUtil.logger.error(String.format("Function[%s]::Field[%s]::Regex[%s]::Status[%s]%n",
                    Functions.ASSERT_FIELD_MATCHES_REGEX, field,regex,status));
            LoggerUtil.logger.error(ExceptionUtils.printStackTrace(e));
        }
        return status;
    }



    /**
     * Validates that a map field in the response contains all expected key-value pairs.
     *
     * @param response      The Response object.
     * @param mapField      The map field to check.
     * @param expectedEntries The expected key-value pairs.
     */
//    public static void assertMapContains(Response response, String mapField, Map<String, Object> expectedEntries) {
//        Map<String, Object> actualEntries = response.jsonPath().getMap(mapField);
//
//    }

    public static boolean assertMapContains(Response response, String mapField, Map<String, Object> expectedEntries){
        boolean status = false;
        try{
            if(Objects.nonNull(response)){
                Map<String, Object> actualEntries = response.jsonPath().getMap(mapField);
                expectedEntries.forEach((key, value) -> {
                    assertThat("Map '" + mapField + "' does not contain expected entry!",
                            actualEntries, hasEntry(key, value));
                });
                status = true;
                LoggerUtil.logger.info(String.format("Function[%s]::MapField[%s]::Status[%s]%n",
                        Functions.ASSERT_MAP_CONTAINS, mapField,status));
            }else{
                throw new Exception();
            }
        } catch (Exception e) {
            LoggerUtil.logger.error(String.format("Function[%s]::MapField[%s]::Status[%s]%n",
                    Functions.ASSERT_MAP_CONTAINS, mapField,status));
            LoggerUtil.logger.error(ExceptionUtils.printStackTrace(e));
        }
        return status;
    }

    /**
     * Validates that the response status code and a specific message in the response body match the expected values.
     *
     * @param response          The Response object.
     * @param expectedStatusCode The expected status code.
     * @param messageField       The field in the response containing the message.
     * @param expectedMessage    The expected message value.
     * @return True if both validations pass, false otherwise.
     */
    public static boolean verifyStatusCodeAndMessage(Response response, int expectedStatusCode, String messageField, String expectedMessage) {
        boolean status = false;
        try {
            if (Objects.nonNull(response) && StringUtils.isNotBlank(messageField)) {
                // Validate the status code
                boolean statusCodeMatch = Objects.equals(response.getStatusCode(), expectedStatusCode);
                assertThat("Unexpected status code!", response.getStatusCode(), equalTo(expectedStatusCode));

                // Validate the message field
                String actualMessage = response.jsonPath().getString(messageField);
                assertThat("Unexpected message!", actualMessage, equalTo(expectedMessage));

                status = statusCodeMatch && actualMessage.equals(expectedMessage);
                LoggerUtil.logger.info(String.format("Function[%s]::StatusCode[%s]::ExpectedMessage[%s]::ActualMessage[%s]::Status[%s]%n",
                        Functions.VERIFY_STATUS_CODE_AND_MESSAGE, expectedStatusCode, expectedMessage, actualMessage, status));
            } else {
                throw new IllegalArgumentException("Response or messageField is null or blank.");
            }
        } catch (Exception e) {
            LoggerUtil.logger.error(String.format("Function[%s]::ExpectedStatusCode[%s]::ExpectedMessage[%s]::Status[%s]%n",
                    Functions.VERIFY_STATUS_CODE_AND_MESSAGE, expectedStatusCode, expectedMessage, status));
            LoggerUtil.logger.error(ExceptionUtils.printStackTrace(e));
        }
        return status;
    }

}
