Feature: API PLUS WEB
  Scenario: Verify about us
    Given User hit API from transaction data -> seller data
    And User open url data from the api
    When User go to about us
    Then User verify people on about us contains with "Engineering"