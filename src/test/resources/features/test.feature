Feature: API Testing

  Scenario: Verify GET request
    When I send a GET request to "/api/users?page=2"
    Then the response status code should be 200
    And the response should contain "email"








