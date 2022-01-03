Feature: Find Pet
  Scenario: Existing pet is found with ID
    Given There is a pet with ID
    When The pet is looked up by his ID
    Then The pet is returned correctly
