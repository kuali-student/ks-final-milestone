Feature: Log in

  In order to be show how mechanize works
  As a user
  I want to log in
  
  Scenario: I should see a menu with links when I log in
    Given I am on the kuali homepage
	When I fill in "j_username" with "admin"
	And I fill in "j_password" with "admin"
	And I press ""
  