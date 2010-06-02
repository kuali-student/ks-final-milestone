Feature: Manage Organizations
  In order to configure workflows for course approvals
  Administrators should be able to manage organizations
  
  Scenario: Create an Organization
    Given I am logged out
    And I am on the home page
    When I log in as an administrator
    And I wait for the page to load
    And I am on "organization management"
    Then I should see "Organization Management"
    When I click the tab "Organization"
    Then I should see "Effective Date"
    When I select "School" from a drop down with a heading of "Type"
    And I fill in div labeled "Name" with "University of British Columbia"
    And I fill in div labeled "Abbreviation" with "UBC"
    And I fill in div labeled "Description" with "University"
    And I pick a date 10 days in the future for "Effective Date"
    And I click the link "Save"
    Then I should see "Save Successful"
    And I follow "Ok"

  Scenario: View UBC Hierarchy
    Given I am logged out
    And I am on the home page
    When I log in as an administrator
    And I wait for the page to load
    And I am on "organization management"
    Then I should see "Organization Management"
    When I click the link "Browse"
    Then I should see "UBC Senate Subcommittee on Curricula"
    When I expand "UBC Senate"
    Then I should see "Faculty of Science"
    When I expand "Faculty of Science"
    Then I should see "Faculty of Science Curriculum Committee"
    When I expand "Faculty of Science Curriculum Committee"
    Then I should see "Department of Zoology"
    When I expand "Zoology"
    Then I should see "Zoology Curriculum Committee" 
