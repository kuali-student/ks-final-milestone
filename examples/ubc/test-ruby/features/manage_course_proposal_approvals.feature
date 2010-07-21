Feature: Manage course_proposal_approvals
  In order to speed up the management of curriculum
  as an approver
  wants to approve course proposals

  Background:
    Given I am logged out
    And I am on the home page
    When I log in as "elliottburnell"
    And I wait for the page to load
    When I go to "curriculum management"
    And I follow "Start Blank Proposal"
    And I wait for no text "Loading"
    Then I should see "Authors & Rationale"
    And I click the link "Authors & Rationale"
    Then I should see "Proposal Title and Rationale"
    When I fill in the Proposal Title with "Chemistry 201"
    And I fill in a text area div labeled "Proposal Rationale" with "Principles of chemical kinetics, reaction mechanisms and chemical thermodynamics"
    And I follow "Save & Continue"
    And I click the link "Save"
    Then I should see "Save Successful"
    And I click the link "Ok"
    And I click the link "Course Logistics"
    Then I should see "Instructor"
    And I fill in the "Instructor" section with "Gordon Bates"
    And I fill in the "Duration" field with "12"
    And I select "Week" from div labeled "Duration Type"
    And I select "3 Credits" from div labeled "Type"
    And I click the link "Add Additional Format"
    And I click the link "Add Activity"
    And I select "Lecture" from div labeled "Activity Type"
    And I type in "Duration" with "12"
    And I select "Week" from div labeled "Duration Type"
    And I enter in Contact Hours with "3" "per week"
    And I follow "Save & Continue"
    And I wait for no text "Saving"
    And I click the link "Course Information"
    Then I should see "Course Identifier"
    And I fill in Course Identifier with "CHEM" and "201"
    And I fill in the "Short Course Title" section with "INTR PHYS CHEM"
    And I fill in the "Course Title" section with "Introduction to Physical Chemistry"
    And I fill in the "Course Description" textarea section with "Principles of chemical kinetics, reaction mechanisms and chemical thermodynamics. Credit will be given for only one of CHEM 201 and 205."
    And I follow "Save & Continue"
    And I wait for no text "Saving"
    And I click the link "Governance"
    Then I should see "Curriculum Oversight"
    And I type in "Curriculum Oversight" with "Chem"
    And I select "Department of Chemistry" from an autocomplete list
    And I check "UBC Vancouver"
    And I type in "Administering Organization" with "Chem"
    And I select "Department of Chemistry" from an autocomplete list
    And I follow "Save & Continue"
    And I wait for no text "Saving"
    And I click the link "Active Dates"
    Then I should see "Course active on"
    When I fill in Start Date and End Date
    And I select "Fall Semester of 2010" from a drop down with a heading of "First Expected Offering"
    And I follow "Save & Continue"
    And I wait for no text "Saving"
    And I click the link "Financials"
    Then I should see "Course Fees"
    And I type in "Revenue" with "Chem"
    And I select "Department of Chemistry" from an autocomplete list
    And I follow "Save"
    And I wait 2 seconds
    And I click the link "Review Proposal"
    Then I should see "Proposal Brief"
    And I submit the proposal
    Then I should see "Proposal has been routed to workflow"
    And I follow "Ok"

  Scenario: Approve a Proposal at the Department Curriculum Level
    When I approve proposal "Chemistry 201" with user "markthachuk" and password "markthachuk"
    Then I should see "Proposal was approved"
    And I follow "Ok"
    And I wait for text "Review Proposal"

  Scenario: Approve a Proposal at the Department Level
    When I approve proposal "Chemistry 201" with user "markthachuk" and password "markthachuk"
    Then I should see "Proposal was approved"
    And I follow "Ok"
    And I wait for text "Review Proposal"
    And I approve proposal "Chemistry 201" with user "williamramey" and password "williamramey"
    Then I should see "Proposal was approved"
    And I follow "Ok"
    And I wait for text "Review Proposal"

  Scenario: Approve a Proposal at the Facutly Level

