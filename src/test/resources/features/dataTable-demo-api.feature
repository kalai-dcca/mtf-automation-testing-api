@DataTable-API-Test
Feature: DEMO API DataTable Testing

  Scenario: Validate API DataTable submission
    When TestCaseDataSetup
      | userName | testUserName |
      | userRole | Manager      |
    When DemoAPI: Launch "/api/users", Method: "POST"
    Then Verify status code 201 and message "2024"