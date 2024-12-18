@JSONFile-API-Test
Feature: DEMO API DataTable Testing

  Scenario: Validate API DataTable submission
    When TestCaseDataSetup, JSONFile-"create.json"
    When DemoAPI: Launch "/api/users", Method: "POST"
    Then Verify status code 201 and message "2024"