Feature: Log in

  Scenario: Check login is failed with invalid credentials
    Given browser is open
    And user is on login page
    When user enters email1 and password1
    And user clicks on login
    Then user is navigated to the client page

  Scenario: Check login is succesful as guest
    Given browser is open
    And user is on login page
    When user clicks on guest
    Then user is navigated to the client page

  Scenario: Check login is succes with valid credentials
    Given browser is open
    And user is on login page
    When user enters email2 and password2
    And user clicks on login
    Then user is navigated to the client page

  Scenario: Check login admin is succes
    Given browser is open
    And user is on login page admin
    When user enters email3 and password3
    And user clicks on sign-in
    Then user is navigated to the admin page

  Scenario: Check login admin is failed
    Given browser is open
    And user is on login page admin
    When user enters email4 and password4
    And user clicks on sign-in
    Then user is navigated to the admin page