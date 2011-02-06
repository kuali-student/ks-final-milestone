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
    When I follow "Curriculum Management"
    And I follow "Start Blank Proposal"
    And I wait for no text "Loading"
    And I click the link "Governance"
    Then I should see "UBC Test Campus"
    And I should see the configured campus

  Scenario: See UBC Campuses and default campus should not be there
    Given I am logged out
    And I am on the home page
    When I log in as an administrator
    And I wait for the page to load
    When I follow "Curriculum Management"
    And I follow "Start Blank Proposal"
    And I wait for no text "Loading"
    And I wait for text "Authors & Rationale"
    And I click the link "Governance"
    Then I should see "UBC Vancouver"
    And I should see "UBC Okanagan"
    And I should see "All Campuses"
    And I should see "Great Northern Way"
    And I should see "Robson Square"
    And I should not see "North Campus"

  Scenario: See customized instructor label
    Given I am logged out
    And I am on the home page
    When I log in as an administrator
    And I wait for the page to load
    When I follow "Curriculum Management"
    And I follow "Start Blank Proposal"
    And I wait for no text "Loading"
    Then I should see "UBC Instructor(s)"

#  Scenario: Configure Learning Objectives
#    Given I am logged out
#    And I am on the home page
#    When I log in as an administrator
#    And I wait for the page to load
#    When I follow "Curriculum Management"
#    And I follow "Start Blank Proposal"
#    And I wait for no text "Loading"
#    And I click the link "Course Information"
#    Then I should see "New Course"
#    When I fill in "Proposal Title" with "Biology 200"
#    And I fill in "Course Title" with "Biology 200"
#    And I fill in "Proposal Rationale" with "Replaces Biology 201"
#    And I follow "Save & Continue"
#    And I click the link "Save"
#    Then I should see "Save Successful"
#    And I click the link "Ok"
#    When I click the link "Learning Objectives"
#    Then I should see "Search for Learning Objectives"

  Scenario: Configure ATPs to see Summer and Winter Sessions
    Given I am logged out
    And I am on the home page
    When I log in as an administrator
    And I wait for the page to load
    When I follow "Curriculum Management"
    And I follow "Start Blank Proposal"
    And I wait for no text "Loading"
    And I follow "Active Dates"
    And I select "Summer Semester of 2010" from "Start Term"
    Then I should see "Summer Semester of 2010"
    
  Scenario: Change Suffix Code to Course Number
    Given I am logged out
    And I am on the home page
    When I log in as an administrator
    And I wait for the page to load
    When I follow "Curriculum Management"
    And I follow "Start Blank Proposal"
    And I wait for no text "Loading"
    And I click the link "Review Proposal"
    And I should see "Course Subject"
    And I should see "Course Number"
    And I should not see "Suffix Code"

  Scenario: Change application title to CDM
    Given I am logged out
    And I am on the home page
    When I log in as an administrator
    And I wait for the page to load
    Then I should see "CDM"

#  Scenario: See customized button
#    Given I am logged out
#    And I am on the home page
#    When I log in as an administrator
#    And I wait for the page to load
#    When I follow "Curriculum Management"
#    Then I should see "Start Blank Proposal - CDM"

  Scenario: Customize view based on user privilege
    Given I am logged out
    And I am on the home page
    When I log in as "elliottburnell"
    And I wait for the page to load
    And I follow "Curriculum Management" 
    And I follow "Start Blank Proposal"
    Then I should see "Course Information"
    When I fill in "Proposal Title" with "Chemistry 201"
    And I fill in "Course Title" with "Chemistry 201"
    And I fill in "Proposal Rationale" with "Principles of chemical kinetics, reaction mechanisms and chemical thermodynamics"
    And I follow "Save & Continue"
    And I click the link "Save"
    Then I should see "Save Successful"
    And I click the link "Ok"
    And I click the link "Financials"
    Then I should not see "fee"
    When I am logged out
    And I am on the home page
    When I log in as "michaelblades"
    And I wait for the page to load
    When I follow "Curriculum Management"
    And I follow "Start Blank Proposal"
    And I wait for no text "Loading"
    Then I should see "Proposal Title and Rationale"
    When I fill in "Proposal Title" with "Chemistry 201"
    And I fill in "Proposal Rationale" with "Principles of chemical kinetics, reaction mechanisms and chemical thermodynamics"
    And I follow "Save & Continue"
    And I click the link "Save"
    Then I should see "Save Successful"
    And I click the link "Ok"
    And I click the link "Financials"
    Then I should see "fee"

  Scenario: I can specify if the course change is a Category 1 (major) or Category 2 (minor) change
    Given I am logged out
    And I am on the home page
    When I log in as "elliottburnell"
    And I wait for the page to load
    And I follow "Curriculum Management"
    And I follow "Start Blank Proposal"
    Then I should see "Change Category"
    When I select "Category 2 Change(s) - Minor" from "Change Category"
    And I click the link "Financials"
    And I click the link "Course Information"
    Then I should see "2"

