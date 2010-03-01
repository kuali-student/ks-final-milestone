Feature: Proposal workflow

  In order to be able to create and approve a proposal
  As a student, teacher or admin
  I want create a course proposal
  
  Scenario: I should see a menu with links when I log in
    Given I am on the kuali homepage
	When I fill in "j_username" with "admin"
	And I fill in "j_password" with "admin"
	And I click on "/html/body/form/table/tbody/tr[3]/td/input" xpath
	Then I should see "Organizations"
	And I should see "Curriculum Management"
	And I should see "Rice"
	
	#   Scenario: I want to create a proposal
	# Given I am on the kuali homepage
	# When I follow "Curriculum Management"
	# Then I should see "Create an Academic Credit Course"
	# And I should see "Create a Non Credit Course"
	# And I should see "Create a Program"
	# And I should see "Modify Course"
	# And I should see "Modify a Program"
	# And I should see "Retire a Program"
	# And I should see "Find Course or Proposal"
	
		# When I follow "org.kuali.student.core.organization.ui.OrgEntry/OrgEntry.html"
		# Then I should see a link to "Action List":"#Actionlist"
		# #When I click on the link "Action List"
		# #Then I should see button "Close"
		# #And I press "Close"
		# And I press the label "Organization"
		# Then I should be on the CreateOrganization page
		# Then I should see a field "orgName"
		# And I press the label "Position"
		# Then I should see a field "title"
		# Then I test bad the Login3 page
		# Then I should be on the CreatePosition page
		# And I press the label "Logout"
		# Then I test bad the Login3 page
	