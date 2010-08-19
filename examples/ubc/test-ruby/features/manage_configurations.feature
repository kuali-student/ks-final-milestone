Feature: Manage configurations
  In order to develop curriculum
  a proposer
  wants to use configurations for UBC

  @campusdata
  Scenario: Configure enumeration - see a new campus and modified campus
    #Given  I have a new campus
    Given I am logged out
    And I am on the home page
    When I log in as an administrator
    And I wait for the page to load
    When I go to "curriculum management"
    And I follow "Start Blank Proposal"
    And I wait for no text "Loading"
    And I wait for text "New Course Proposal"
    And I click the link "Governance"
    Then I should see "UBC Test Campus"
    And I should see the configured campus

  Scenario: See UBC Campuses
    Given I am logged out
    And I am on the home page
    When I log in as an administrator
    And I wait for the page to load
    When I go to "curriculum management"
    And I follow "Start Blank Proposal"
    And I wait for no text "Loading"
    And I wait for text "Authors & Rationale"
    And I click the link "Governance"
    Then I should see "UBC Vancouver"
    And I should see "UBC Okanagan"
    And I should see "All Campuses"
    And I should see "Great Northern Way"
    And I should see "Robson Square"

  Scenario: See customized instructor
    Given I am logged out
    And I am on the home page
    When I log in as an administrator
    And I wait for the page to load
    When I go to "curriculum management"
    And I follow "Start Blank Proposal"
    And I wait for no text "Loading"
    And I wait for text "Authors & Rationale"
    When I click the link "Course Logistics"
    Then I should see "UBC Instructor"

  Scenario: Configure Learning Objectives
    Given I am logged out
    And I am on the home page
    When I log in as an administrator
    And I wait for the page to load
    When I go to "curriculum management"
    And I follow "Start Blank Proposal"
    And I wait for no text "Loading"
    Then I should see "Authors & Rationale"
    And I click the link "Authors & Rationale"
    Then I should see "Proposal Title and Rationale"
    When I fill in "Proposal Title" with "Biology 200"
    And I fill in "Proposal Rationale" with "Replaces Biology 201"
    And I follow "Save & Continue"
    And I click the link "Save"
    Then I should see "Save Successful"
    And I click the link "Ok"
    When I click the link "Learning Objectives"
    Then I should see "Search for Learning Objectives"
    When I click the link "Save & Continue"
    Then I should not see "Save Successful"

  Scenario: Configure ATPs
    Given I am logged out
    And I am on the home page
    When I log in as an administrator
    And I wait for the page to load
    When I go to "curriculum management"
    And I follow "Start Blank Proposal"
    And I wait for no text "Loading"
    Then I should see "Authors & Rationale"
    And I follow "Active Dates"
    Then I should see "Summer Semester of 2010"
    
  Scenario: Change Suffix Code to Course Number
    Given I am logged out
    And I am on the home page
    When I log in as an administrator
    And I wait for the page to load
    When I go to "curriculum management"
    And I follow "Start Blank Proposal"
    And I wait for no text "Loading"
    Then I should see "Authors & Rationale"
    And I click the link "Review Proposal"
    And I should see "Subject Code"
    And I should see "Course Code"
    And I should not see "Suffix Code"

#  Scenario: See customized button
#    Given I am logged out
#    And I am on the home page
#    When I log in as an administrator
#    And I wait for the page to load
#    When I go to "curriculum management"
#    Then I should see "Start Blank Proposal - CDM"