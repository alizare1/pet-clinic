Feature: Find Owner

  Scenario: Existing owner is found with ID
    Given There is an owner with ID
    When He is looked up by his ID
    Then He is returned correctly
