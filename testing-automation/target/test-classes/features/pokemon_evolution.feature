Feature: Testing Pokemon API
  Scenario: Find the next evolution of Pikachu
    Given Get the Pokemon list
    When I find Pikachu in the list
    Then I should get Pikachu species details
    And I should get Pikachu evolution chain
    And I should find the next evolution of Pikachu