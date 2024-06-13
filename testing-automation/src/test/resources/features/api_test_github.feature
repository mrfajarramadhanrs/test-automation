Feature: Testing Github API

  Scenario: Validate fields not null
    Given User get data from "checkout transaction"
    Then Verify response from "checkout transaction"

  Scenario: Validate compare price and amount
    Given User get data from "checkout transaction"
    Then Verify price from checkout transaction

  Scenario: Validate compare customer data and shipping data
    Given User get data from "checkout transaction"
    Then Verify data customer and shipping is not equal

  Scenario: Validate last login and update status
    Given User get data from "user history"
    And Verify response from "user history"
    Then Verify last login with more than 1 years

  Scenario: Validate palindrome words
    Given User get data from "palindrome"
    And Verify response from "palindrome"
    Then Verify if the words are palindrome
