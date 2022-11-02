#Author: Santosh
@Login
Feature: Login (Covers all Login scenarios )

  @ValidLogin
  Scenario: Valid Login Scenario
    Given Saucedemo Website
    When User enters UserName & Password
    And Clicks on LOGIN Button
    Then Home page should be displayed

  @InvalidLogin
  Scenario Outline: Invalid Login Scenario
    Given Saucedemo Website
    When User enters "<UserName>" & "<Password>"
    And Clicks on LOGIN Button
    Then Invalid Password message should be displayed

    Examples: 
      | UserName              | Password     |
      | sskbehera | Passw! |

