Feature: Manage searches
  In order to search for relevant data
  a proposer
  wants configure search
  
  # https://test.kuali.org/jira/browse/KSQATC-15
  Scenario: Configure Organization Search - Add "Type" criteria, remove "Short Name" criteria, redefine search and add new "Description" criteria to search definition
    Given I am logged out
    When I log in as an administrator
    And I go to organization management
    And I wait for the page to load
    Then I should see "Organization Management"
    And I click the link "Search/Modify"
    And I follow "Advanced Search"
    Then I should see "Find Organization (Customized Search Results and Criteria List)"
    And I should not see "Short Name"
    And I should see "Type"
    And I should see "Description"

  # https://test.kuali.org/jira/browse/KSQATC-15
  Scenario: Configure Organization Search - Add Organization Type result and Remove Short Name Result
    Given I am logged out
    When I log in as an administrator
    And I go to organization management
    Then I should see "Organization Management"
    And I click the link "Search/Modify"
    And I follow "Advanced Search"
    And I fill in the "Type" field with "kuali.org.Department"
#    And I wait 2 seconds
#    And take a desktop screenshot
    And I click the Search button
#    And I wait 2 seconds
#    And take a desktop screenshot
    Then I should see "Organization Type"
    And I should not see "Short Name"

  # https://test.kuali.org/jira/browse/KSQATC-15
  Scenario: Configure Curriculum Management Search - Add Level to Find Course Criteria and Results
    Given I am logged out
    And I am on the home page
    When I log in as an administrator
    And I wait for the page to load
    When I go to "curriculum management"
    And I follow "Start Blank Proposal"
    And I wait for no text "Loading"
    And I wait for text "Authors & Rationale"
    Then I should see "Course Information"
    And I click the link "Course Information"
    And I follow "Add Course"
    And I follow "advanced search"
    Then I should see "Course or Proposal"
    And I should see "Level"
    When I fill in the "Level" field with "100"
    And I click the Search button
    Then I should see "Course Level"

  # https://test.kuali.org/jira/browse/KSQATC-19
  Scenario: Configure Organization Search - Redefine Search and Add New Criteria
    Given I am logged out
    When I log in as an administrator
    And I go to organization management
    Then I should see "Organization Management"
    And I click the link "Search/Modify"
    And I follow "Advanced Search"
    And I follow "Customize This Search"
    Then I should see "Description"
    
    


  

