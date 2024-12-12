Feature: API Testing

  Scenario: Verify GET request
    When I send a GET request to "/api/users?page=2"
    Then the response status code should be 200
    And the response should contain "data[0].email"


  @wip
  Scenario: Verify POST request
    When I send a POST request to "/api/users" with body:
     """
      {
          "name": "John Doe",
          "job": "Developer"
      }
      """
    Then the response status code should be 201
    Then the response should contain field "name" equal "John Doe"
    Then I saved the Id for created user
    Then I delete the created user
    Then the response status code should be 204
    When I send a GET request by id to "/api/users/"
    Then the response status code should be 404






