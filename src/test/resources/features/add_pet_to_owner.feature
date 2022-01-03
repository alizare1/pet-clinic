Feature: Add pet to owner
  Scenario: Adding a pet for hamid
    Given There is an owner named "Hamid"
    When Add a new pet is performed with him
    Then He has the new pet
