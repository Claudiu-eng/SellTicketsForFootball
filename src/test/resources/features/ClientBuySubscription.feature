Feature: Buy ticket

  Scenario: Check buy ticket is succesful as guest
    Given browser is open for buy ticket
    And user is on login page client for buy ticket
    When user clicks on guest for buy ticket
    Then user is navigated to the client page for buy ticket
    And user clicks on match with id equal to 1
    Then user clicks view seats
    And user select seat numero 1
    Then user clicks on buy
    And user introduces personal data
    And user clicks on cumpara
    Then user see his ticket