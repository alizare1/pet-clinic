Feature: Save Pet
  Scenario: owner's pet is saved
    Given There is an owner
    When A save action is performed on a new pet named "Amin" for the owner
    Then The pet named "Amin" is saved correctly
