Feature: Manage course_proposal_approvals
  In order to speed up the management of curriculum
  as an approver
  wants to approve course proposals

  After do |scenario|
    And I am logged out
  end

  Background:
    Given I am logged out
    And I am on the home page
    When I log in as "elliottburnell"
    And I wait for the page to load
    And I follow "Curriculum Management"
    And I follow "Start Blank Proposal"
    Then I should see "Course Information"
    When I fill in "Proposal Title" with "Chemistry 201"
    And I fill in "Course Title" with "Introduction to Physical Chemistry"
    And I fill in "Transcript Course Title" with "INTR PHYS CHEM"
    And I type in "Subject Code" with "CHEM"
    And I select "CHEM" from an autocomplete list
    And I fill in "Course Number" with "201"
    And I add "gordonbates" to "UBC Instructor" by typing "Bates"
    And I fill in "Proposal Rationale" with "Second year Chemistry fundamentals"
    And I fill in "Description" with "Principles of chemical kinetics, reaction mechanisms and chemical thermodynamics. Credit will be given for only one of CHEM 201 and 205."
    And I follow "Save & Continue"
    And I click the link "Save"
    And I follow "Ok"
    And I click the link "Governance"
    Then I should see "Curriculum Oversight"
    When I check "UBC Vancouver"
    And I add "Department of Chemistry" to "Curriculum Oversight" by typing "Chem"
    And I add "Department of Chemistry" to "Administering Organization" by typing "Chem"
    And I follow "Save & Continue"
    And I wait for no text "Saving"
    Then I should see "Save Successful"
    And I follow "Ok"
    And I click the link "Course Logistics"
    And I check "Winter"
    And I select "Week" from "Duration Type"
    And I fill in "Duration Count" with "12"
    And I check "Percentage"
    And I follow "Outcome"
    Then I should see "Outcome 1"
    When I select "Credits, Fixed" from "Type"
    Then I should see "Credit Value"
    When I fill in "Credit Value" with "3"
    And I follow "Add Additional Format"
    Then I should see "Course Format 1"
    When I follow "Add Activity"
    Then I should see "Activity 1"
    When I select "Lecture" from "Activity Type"
    And I fill in "Contact Hours" with "3"
    And I choose "per week" from a select
    And I follow "Save & Continue"
    And I wait for no text "Saving"
    Then I should see "Save Successful"
    When I follow "Ok"
    And I click the link "Active Dates"
    Then I should see "Start Term"
    When I choose "Winter Semester of 2009" from a select
    And I follow "Save & Continue"
    And I wait for no text "Saving"
    Then I should see "Save Successful"
    When I follow "Ok"
    And I click the link "Review Proposal"
    Then I should see "Workflow Actions"
    When I submit the proposal
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
    Then I approve proposal "Chemistry 201" with user "edwardgrant" and password "edwardgrant"
    Then I should see "Proposal was approved"
    And I follow "Ok"
    And I wait for text "Review Proposal"



  Scenario: Approve a Proposal at the Faculty Committee Level
    When I approve proposal "Chemistry 201" with user "markthachuk" and password "markthachuk"
    Then I should see "Proposal was approved"
    And I follow "Ok"
    And I wait for text "Review Proposal"
    Then I approve proposal "Chemistry 201" with user "edwardgrant" and password "edwardgrant"
    Then I should see "Proposal was approved"
    And I follow "Ok"
    And I wait for text "Review Proposal"
    Then I approve proposal "Chemistry 201" with user "williamramey" and password "williamramey"
    Then I should see "Proposal was approved"
    And I wait for text "Review Proposal"

  Scenario: Approve a Proposal at the Senate Curriculum Committee Level
    When I approve proposal "Chemistry 201" with user "markthachuk" and password "markthachuk"
    Then I should see "Proposal was approved"
    And I follow "Ok"
    And I wait for text "Review Proposal"
    Then I approve proposal "Chemistry 201" with user "edwardgrant" and password "edwardgrant"
    Then I should see "Proposal was approved"
    And I follow "Ok"
    And I wait for text "Review Proposal"
    Then I approve proposal "Chemistry 201" with user "williamramey" and password "williamramey"
    Then I should see "Proposal was approved"
    And I follow "Ok"
    And I wait for text "Review Proposal"
    Then I approve proposal "Chemistry 201" with user "simonpeacock" and password "simonpeacock"
    Then I should see "Proposal was approved"
    And I follow "Ok"
    And I wait for text "Review Proposal"
    Then I approve proposal "Chemistry 201" with user "iancavers" and password "iancavers"
    Then I should see "Proposal was approved"
    And I follow "Ok"    
    And I wait for text "Review Proposal"
    Then I approve proposal "Chemistry 201" with user "petermarshall" and password "petermarshall"
    Then I should see "Proposal was approved"
    And I follow "Ok"
    And I wait for text "Review Proposal"

  Scenario: Workflow routes to Graduate Subcommittee for graduate level courses
    When I open the proposal "Chemistry 201" with user "markthachuk" and password "markthachuk"
    And I click the link "Course Information"
    And I fill in "Course Number" with "500"
    And I fill in "Proposal Title" with "Chemistry 500"
    And I follow "Save & Continue"
    And I wait for no text "Saving"
    Then I should see "Save Successful"
    When I goto the route log for the proposal
    Then I should see "Route Log"
    And I expand the Future Actions Requests
    Then I should see "Evans, Barbara"

  Scenario: Approve a Proposal at the Publication Office Level
    When I approve proposal "Chemistry 201" with user "markthachuk" and password "markthachuk"
    Then I should see "Proposal was approved"
    And I follow "Ok"
    And I wait for text "Review Proposal"
    Then I approve proposal "Chemistry 201" with user "edwardgrant" and password "edwardgrant"
    Then I should see "Proposal was approved"
    And I follow "Ok"
    And I wait for text "Review Proposal"
    Then I approve proposal "Chemistry 201" with user "williamramey" and password "williamramey"
    Then I should see "Proposal was approved"
    And I follow "Ok"
    And I wait for text "Review Proposal"
    Then I approve proposal "Chemistry 201" with user "simonpeacock" and password "simonpeacock"
    Then I should see "Proposal was approved"
    And I follow "Ok"
    And I wait for text "Review Proposal"
    Then I approve proposal "Chemistry 201" with user "iancavers" and password "iancavers"
    Then I should see "Proposal was approved"
    And I follow "Ok"
    And I wait for text "Review Proposal"
    Then I approve proposal "Chemistry 201" with user "petermarshall" and password "petermarshall"
    Then I should see "Proposal was approved"
    And I follow "Ok"
    And I wait for text "Review Proposal"
    Then I approve proposal "Chemistry 201" with user "chriseaton" and password "chriseaton"
    Then I should see "Proposal was approved"
    And I follow "Ok"
    And I wait for text "Review Proposal"
    And I should not see "Enroute"

    

