Feature: Manage Course Proposals
  In order to develop curriculum to support student learning
  As a proponent
  I want to create and manage course proposals

  Scenario: Submit Proposal
    Given I am logged out
    And I am on the home page
    When I log in as "gordonbates"
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
    And I add "michaelblades" to "UBC Instructor" by typing "blade"
    And I fill in "Description" with "Principles of chemical kinetics, reaction mechanisms and chemical thermodynamics. Credit will be given for only one of CHEM 201 and 205."
    And I fill in "Proposal Rationale" with "This course will bridge the gap between first year and third year chemistry courses in the area of thermodynamics."
    And I follow "Save & Continue"
    And I click the link "Save"
    And I follow "Ok"
    And I click the link "Governance"
    Then I should see "Curriculum Oversight"
    When I check "UBC Vancouver"
    And I type in "Curriculum Oversight" with "Chem"
    And I select "Department of Chemistry" from an autocomplete list
    And I add to list
    And I type in "Administering Organization" with "Chem"
    And I select "Department of Chemistry" from an autocomplete list
    And I follow "Save & Continue"
    And I wait for no text "Saving"
    Then I should see "Save Successful"
    And I follow "Ok"
    And I click the link "Course Logistics"
    And I check "Winter"
    And I select "Week" from "Duration Type"
    And I fill in "Duration Count" with "12"
    And I check "Percentage"
    And I choose "Standard final Exam"
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

  Scenario: Save Complete Proposal
    Given I am logged out
    And I am on the home page
    When I log in as "gordonbates"
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
    And I add "michaelblades" to "UBC Instructor" by typing "blade"
    And I fill in "Description" with "Principles of chemical kinetics, reaction mechanisms and chemical thermodynamics. Credit will be given for only one of CHEM 201 and 205."
    And I fill in "Proposal Rationale" with "This course will bridge the gap between first year and third year chemistry courses in the area of thermodynamics."
    And I follow "Save & Continue"
    And I click the link "Save"
    And I follow "Ok"
    And I click the link "Governance"
    Then I should see "Curriculum Oversight"
    When I check "UBC Vancouver"
    And I type in "Curriculum Oversight" with "Chem"
    And I select "Department of Chemistry" from an autocomplete list
    And I add to list
    And I type in "Administering Organization" with "Chem"
    And I select "Department of Chemistry" from an autocomplete list
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

# Used for testing approve proposal step
  Scenario: Approve Proposal
    Given I am on logout
    And I wait for text "Password"
    When I log in with user "markthachuk" and password "markthachuk"
    And I wait for the page to load
    Then I should see "markthachuk"
    And I select the Action List
    And I wait for text "Action List"
    Then I should see "Chemistry 201"
    And I should see "Credit Course Proposal"
    And I open the proposal "Chemistry 201"
    Then I should see "Workflow Actions"
    And I approve the proposal

  Scenario: I can select a department
    Given I am logged out
    And I am on the home page
    When I log in as an administrator
    And I wait for the page to load
    When I go to "curriculum management"
    And I follow "Start Blank Proposal"
    And I wait for no text "Loading"
    Then I should see "Authors & Rationale"
    When I click the link "Governance"
    Then I should see "Curriculum Oversight"
    And I type in "Curriculum Oversight" with "Zoo"
    And I select "Department of Zoology" from an autocomplete list

  Scenario: I can fill in the active dates
    Given I am logged out
    And I am on the home page
    When I log in as an administrator
    And I wait for the page to load
    When I go to "curriculum management"
    And I follow "Start Blank Proposal"
    And I wait for no text "Loading"
    Then I should see "Authors & Rationale"
    When I click the link "Active Dates"
    Then I should see "Start Date"
    When I fill in Start Date and End Date

  Scenario: I can log in as different users
    Given I am logged out
    And I am on the home page
    When I log in with user "admin" and password "admin"
    And I wait for the page to load
    Then I should see "admin"
    When I am on logout
    When I log in with user "eric" and password "eric"
    And I wait for the page to load
    Then I should see "eric"

  Scenario: I can see action list
    Given I am logged out
    And I am on the home page
    When I log in with user "admin" and password "admin"
    And I wait for the page to load
    Then I should see "admin"
    And I select the Action List
    And I should see "Action List"
    And I should see "Action Requested"

  Scenario: I can navigate to the Action List
    Given I am logged out
    And I am on the home page
    When I log in as "elliottburnell"
    And I wait for the page to load
    And I follow "Curriculum Management"
    And I follow "Start Blank Proposal"
    Then I should see "Course Information"
    When I am on action list
    Then I should see "Action List"


  Scenario: select multiple items from action list
    Given I am logged out
    And I am on the home page
    When I log in with user "edwardgrant" and password "edwardgrant"
    Then I should see "Home"
    And I click My Action List
    And I select the Action List
    Then I should see "Chemistry 201"
    When I follow "3012"
    And I switch to opened proposal
    Then I should see "New Course"
    When I click My Action List
    And I follow "3013"
    And I switch to opened proposal
    Then I should see "New Course"
    When I click My Action List
    And I follow "3014"
    And I switch to opened proposal
    Then I should see "New Course"
    When I click My Action List
    And I follow "3015"
    And I switch to opened proposal
    Then I should see "New Course"

  Scenario: I can clear out the text in a text box
    Given I am logged out
    And I am on the home page
    When I log in as "gordonbates"
    And I wait for the page to load
    And I follow "Curriculum Management"
    And I follow "Start Blank Proposal"
    Then I should see "Course Information"
    When I type in "UBC Instructor" with "Michael"
    Then I should see "Michael"
    When I clear "UBC Instructor"
    And I type in "UBC Instructor" with "Blades"
    Then I should see "Blades"
    
  Scenario: I can add a department to Curriculum Oversight
    Given I am logged out
    And I am on the home page
    When I log in as "gordonbates"
    And I wait for the page to load
    And I follow "Curriculum Management"
    And I follow "Start Blank Proposal"
    And I click the link "Governance"
    Then I should see "Curriculum Oversight"
    When I add "Department of Chemistry" to "Curriculum Oversight" by typing "Chem"
    Then I should see "Department of Chemistry"

  Scenario: I can pilot a course
    Given I am logged out
    And I am on the home page
    When I log in as "gordonbates"
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
    And I add "michaelblades" to "UBC Instructor" by typing "blade"
    And I fill in "Description" with "Principles of chemical kinetics, reaction mechanisms and chemical thermodynamics. Credit will be given for only one of CHEM 201 and 205."
    And I fill in "Proposal Rationale" with "This course will bridge the gap between first year and third year chemistry courses in the area of thermodynamics."
    And I follow "Save & Continue"
    And I click the link "Save"
    And I follow "Ok"
    And I click the link "Governance"
    Then I should see "Curriculum Oversight"
    When I check "UBC Vancouver"
    And I type in "Curriculum Oversight" with "Chem"
    And I select "Department of Chemistry" from an autocomplete list
    And I add to list
    And I type in "Administering Organization" with "Chem"
    And I select "Department of Chemistry" from an autocomplete list
    And I follow "Save & Continue"
    And I wait for no text "Saving"
    Then I should see "Save Successful"
    And I follow "Ok"
    And I click the link "Course Logistics"
    And I check "Winter"
    And I select "Week" from "Duration Type"
    And I fill in "Duration Count" with "12"
    And I check "Percentage"
    #And I choose "Standard final Exam"
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
    And I choose "Yes, this is a one-time/pilot course"
    And I follow "Save & Continue"
    And I wait for no text "Saving"
    Then I should see "Save Successful"
    When I follow "Ok"
    And I click the link "Review Proposal"
    Then I should see "Workflow Actions"
    When I submit the proposal
    Then I should see "Proposal has been routed to workflow"
    And I follow "Ok"
    When I approve proposal "Chemistry 201" with user "markthachuk" and password "markthachuk"
    Then I should see "Proposal was approved"
    And I follow "Ok"
    And I wait for text "Review Proposal"
    Then I approve proposal "Chemistry 201" with user "edwardgrant" and password "edwardgrant"
    Then I should see "Proposal was approved"
    And I follow "Ok"
    And I wait for text "Review Proposal"
    When I approve proposal "Chemistry 201" with user "chriseaton" and password "chriseaton"
    Then I should see "Proposal was approved"
    And I follow "Ok"
    And I wait for text "Review Proposal"
    And I should not see "Enroute"


    
    