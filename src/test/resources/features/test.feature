Feature: DEMO API Testing

  Scenario Outline: Validate API submission
#    PREP DATA
    When Launch Demo API service and Review API service with test data from the testcase file "sample.xlsx"
    Then Read test data from the sheet "demoTest" for the "<TestCaseId>"

    When DemoAPI: Launch "/api/users", Method: "POST", request params: File "TC1000"
    Then Verify status code <statusCode> and message "<message>"

    Examples:
      | TestCaseId |
      | TC1000    |