@wip
Feature: API Testing


  Scenario: Verify PUT request
    Given the following headers are set:
      | Content-Type  | application/json |
      | Authorization | Bearer abc123xyz |
      | Accept        | application/json |
    Then I send a PUT request to "/api/users/2" with body:
     """
      {
          "name": "John Doe",
          "job": "Developer"
      }
      """
    Then the response status code should be 200
    Then the response should contain field "name" equal "John Doe"
    Then the response should contain field "job" equal "Developer"


  Scenario Outline: Verify PATCH request for different fields
    Given I send a PATCH request to "/api/users/2" with body:
     """
      {
          "<Key>": "<Value>"
      }
      """
    Then the response status code should be 200
    Then the response should contain field "<Key>" equal "<Value>"

    Examples:
      | Key  | Value     |
      | name | John Doe  |
      | job  | Developer |
