Feature: Testing Lipsum Web
  Scenario: Find All Words that Repeated 2 Times
    Given open the lipsum url
    When scroll to element text box for changing the value of text
    Then Verify all words that repeated more than 2 times