Feature: Login

  Scenario Outline: Successful Login to the page and logout after
    Given I open web browser
    When I navigate to login.html page
    And I provide login as "<login>" and password as "<password>"
    And I click on login button
    And I click on menu button if it displayed
    Then name should be "<name>"
    And click logout button
    Then user logged out

    Examples:
      | login | password | name |
      | admin | admin | admin |



