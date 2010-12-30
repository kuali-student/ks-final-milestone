Feature: Proposal workflow 

  In order to be able to create and approve a proposal
  As a student, teacher or admin
  I want create a course proposal
  
  Scenario: I should see a menu with links when I log in
    Given I am on the kuali homepage
	When I fill in "j_username" with "admin"
	And I fill in "j_password" with "admin"
	And I click "submit"
	Then I should see "Organizations"
	And I should see "Curriculum Management"
	And I should see "Rice"
	
  Scenario: I want to create an organization
    Given I am loged in as "admin" with the password "admin"
	And I am on the kuali homepage 
	When I follow "Organizations"
	And I click the "Organization" label
# no name on field...wtf?	And I select "Adhoc Comittee" from "Type"
	And I fill in "orgName" with "name1"
	And I fill in "orgAbbrev" with "abbrev1"
	And I fill in "orgDesc" with "desc1"
# Relationships / Links. Again nothing that I can use to select it
	And I click the "/html/body/table/tbody/tr/td/div/div/div/table/tbody/tr[2]/td[2]/div/table/tbody/tr[2]/td/table/tbody/tr/td/a/table/tbody/tr/td[2]" xpath
# Positions. Again nothing that I can use to select it
	And I click the "/html/body/table/tbody/tr/td/div/div/div/table/tbody/tr[2]/td[2]/div/table/tbody/tr[3]/td/table/tbody/tr/td/a/table/tbody/tr/td[2]" xpath
	And I press "Save"
	Then I should see "Saving.."
#date fields with no names...wtf?
	
  Scenario: I want to see the curiculum management page
	Given I am loged in as "admin" with the password "admin"
	And I am on the kuali homepage
	When I follow "Curriculum Management"
	Then I should see "Create an Academic Credit Course"
	And I should see "Create a Non Credit Course"
	And I should see "Create a Program"
	And I should see "Modify Course"
	And I should see "Modify a Program"
	And I should see "Retire a Program"
	And I should see "Find Course or Proposal"
	And I should see "View Process Overview"
	And I should see "Start Blank Proposal"
	And I should see "Select Proposal Template"
	And I should see "Copy Course Proposal"
	And I should see "Copy Existing Course"
	And I should see "Help Me Decide"
	