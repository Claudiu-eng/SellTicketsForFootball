Feature: Add Item

  Scenario: Check login admin is succes
    Given browser is open
    And user is on login page admin
    When user enters email3 and password3
    And user clicks on sign-in
    Then user is navigated to the admin page


  Scenario: Check add item
    Given browser is open for add item
    And user is on login page admin for add item
    Then user enters email3 and password3 for add item
    And user clicks on sign-in for add item
    Then user is navigated to the admin page for addItem
    And user is clicking adding a stadium
    And user is adding a stadium
    Then user is navigate to stadiums page