@REG-API-CREATE-LIST-USERS @REG-API
Feature: DEMO Create API Testing LIST-USERS

  Scenario Outline: Validate API submission Create LIST-USERS GET request
    When TestCaseDataSetup, File-"demoData.xlsx", Sheet-"List users", TestCase-"<TestCaseId>"
    #When DemoAPI: Launch "/api/users", QParam:"page" Method: "GET"
    When Fetch all pages from "/api/users" with query param "page" and method "GET"
    Then Verify status code 200 and the response array "data" matches expected values from "expectedListUsers.json"
    Examples:
      | TestCaseId |
      | LU-TC001   |
      | LU-TC002   |
      | LU-TC003   |
      | LU-TC004   |