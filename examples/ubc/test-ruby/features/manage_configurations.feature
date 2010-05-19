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

  Scenario: See customized button
    Given I am logged out
    And I am on the home page
    When I log in as an administrator
    And I wait for the page to load
    When I go to "curriculum management"
    And take a desktop screenshot
    Then I should see "Start Blank Proposal - CDM"