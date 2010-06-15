Feature: Scenarios: smoke, organizations, roles

@smoke @timer
	 Scenario: Create a new course proposal
		Given I am logged in as "eric" with the password "eric"
		And I am on the kuali homepage
		And I wait for "2" seconds
		And I click the label "Home" with class "gwt-Label KS-Label KS-CutomDropDown-TitleLabel"
		And I press ajax link "Curriculum Management"
		And I wait for "2" seconds
		Then I should see "Create an Academic Credit Course"
		And I wait for "2" seconds
		And I follow "Start Blank Proposal"
		And I wait for "2" seconds
		Then I should see "New Course Proposal"
		And I follow "Governance"			
		And I fill in field number "1" with "Botany Dept"
		And I should see checkbox number "1"
		And I set checkbox number "1"
		And I fill in field number "2" with "Botany Dept"
		And I follow "Save"
		And I wait for "1" seconds
		And I fill in field number "3" with "Botany 123456"
		And I follow link "Save" in div_class "ks-lightbox-content KS-Vertical-Flow"
		And I wait for div "Save Successful"
		And I follow ajax link "Ok"
		When I follow "Course Logistics"
		And I fill in field number "1" with "fran, fran(fran)"
		And I fill in field number "2" with "2"
		And I select "Year" from selectbox number "1"
		And I set checkbox number "1"
		And I select "1 Credit" from selectbox number "2"
		And I follow "Add Additional Format"
		And I follow "Add Activity"
		And I select "Lecture" from ajax selectbox number "3"
		And I fill in field number "3" with "2"
		And I select "Half Semester" from ajax selectbox number "4"
		And I fill in field number "4" with "2"		
		And I select "per week" from ajax selectbox number "5"
		And I fill in field number "5" with "10"
		And I follow "Save"
		And I follow ajax link "Ok"
		When I follow "Course Information"
		And I fill in field number "1" with "BOTA"
		And I fill in field number "2" with "123"	
		And I fill in field number "3" with "BOTA"
		And I fill in field number "4" with "Botany 123"
		And I fill in field number "5" with "botany 123 intro"
		And I follow "Save"
		And I follow ajax link "Ok"
		And I follow "Active Dates"
		And I fill in field number "1" with "04032010"
		And I fill in field number "2" with "05302010"
		And I select "First Summer Session of 2010" from ajax selectbox number "1"
		And I follow "Save"
		And I follow ajax link "Ok"
		And I follow "Authors & Rationale"
		And I fill in field number "1" with "Botany 123"
		And I fill in field number "2" with "Botny Intro 123"
		And I follow "Save"
		And I follow ajax link "Ok"	
		And I follow "Financials"
		And I fill in field number "1" with "Free"
		And I fill in field number "2" with "Botany Dept	"
		And I fill in field number "3" with "100"
		And I follow "Save"
		And I follow ajax link "Ok"			
		And I click the "/html/body/div/table/tbody/tr/td/table/tbody/tr[2]/td/div/div/div/div/table/tbody/tr[2]/td/div/div/div/div/table/tbody/tr/td[2]" xpath
		And I wait for "2" seconds
		And I follow "Submit Proposal"
		And I wait for "1" second
		And I follow "Submit"
		And I wait for "2" seconds		
		
@org1 @timer
    Scenario: Organization entry1 The College of Ocean and Fishery Sciences
		Given I am logged in as "admin" with the password "admin"
		And I am on the kuali homepage
		And I wait for "1" second
		And I click the label "Home" with class "gwt-Label KS-Label KS-CutomDropDown-TitleLabel"
		When I follow ajax link "Organization Management"
		Then I press ajax label "Organization"
		Then I select "College" from ajax selectbox number "1"
		And I fill in field number "1" with "The College of Ocean and Fishery Sciences"
		And I fill in field number "2" with "COFS"
		And I fill in field number "3" with "The College of Ocean and Fishery Sciences"
		And I fill in field number "4" with "04162010"
		# And I follow "Save"
		# And I follow ajax link "Ok"
		And I follow link "Add" with index "1"
		#And I fill in field number "6" with "Senate Subcommittee on Curricula"
		Then I select "is Curriculum Child of" from ajax selectbox number "2"
		And I fill in field number "6" with "The College of Ocean and Fishery Sciences"
		And I fill in field number "7" with "04162010"
		And I follow link "Add" with index "2"
		And I wait for "2" seconds
		Then I select "Member" from ajax selectbox number "3"
		And I fill in field number "9" with "Member"
		And I fill in field number "10" with "Member"
		And I fill in field number "11" with "1"
		And I fill in field number "12" with "1000"
		#And I follow "Save"
		#And I wait for "2" seconds
		#And I follow ajax link "Ok"

@org2 @timer
Scenario: Organization entry1 The College of Ocean and Fishery Sciences
		Given I am logged in as "admin" with the password "admin"
		And I am on the kuali homepage
		And I wait for "1" second
		And I wait for "1" second
		And I click the label "Home" with class "gwt-Label KS-Label KS-CutomDropDown-TitleLabel"
		When I follow ajax link "Organization Management"
		Then I press ajax label "Organization"
		Then I select "COC" from ajax selectbox number "1"
		And I fill in field number "1" with "The College of Ocean and Fishery Sciences COC"
		And I fill in field number "2" with "COFS"
		And I fill in field number "3" with "The College of Ocean and Fishery Sciences COC"
		And I fill in field number "4" with "04162010"
		And I follow link "Add" with index "1"
		Then I select "is Curriculum Child of" from ajax selectbox number "2"
		And I fill in field number "6" with "The College of Ocean and Fishery Sciences"
		And I fill in field number "7" with "04162010"
		And I follow link "Add" with index "2"
		And I wait for "2" seconds
		Then I select "Chair" from ajax selectbox number "3"
		And I fill in field number "9" with "Chair"
		And I fill in field number "10" with "Chair"
		And I fill in field number "11" with "1"
		And I fill in field number "12" with "5"
		And I follow link "Add" with index "2"
		Then I select "Member" from ajax selectbox number "4"
		And I fill in field number "13" with "Member"
		And I fill in field number "14" with "Member COC"
		And I fill in field number "15" with "1"
		And I fill in field number "16" with "9"		
		And I follow "Save"
		And I wait for "2" seconds
		And I follow ajax link "Ok"


		And I wait for "2" second
		And I click the label "Home" with class "gwt-Label KS-Label KS-CutomDropDown-TitleLabel"
		When I follow ajax link "Organization Management"
		Then I press ajax label "Organization"
		Then I select "Division" from ajax selectbox number "1"
		And I fill in field number "1" with "Oceanography Division"
		And I fill in field number "2" with "Oceanography Division"
		And I fill in field number "3" with "Oceanography Division"
		And I fill in field number "4" with "04162010"
		And I follow link "Add" with index "1"
		Then I select "is Curriculum Child of" from ajax selectbox number "2"
		And I fill in field number "6" with "The College of Ocean and Fishery Sciences"
		And I fill in field number "7" with "04162010"
		And I follow link "Add" with index "2"
		Then I select "Member" from ajax selectbox number "3"
		And I fill in field number "9" with "Member"
		And I fill in field number "10" with "Member"
		And I fill in field number "11" with "1"
		And I fill in field number "12" with "1000"
		And I follow "Save"
		And I wait for "2" seconds
		And I follow ajax link "Ok"
		

		And I wait for "2" second
		And I click the label "Home" with class "gwt-Label KS-Label KS-CutomDropDown-TitleLabel"
		When I follow ajax link "Organization Management"
		Then I press ajax label "Organization"
		Then I select "COC" from ajax selectbox number "1"
		And I fill in field number "1" with "Oceanography Division COC"
		And I fill in field number "2" with "Oceanography Division COC"
		And I fill in field number "3" with "Oceanography Division COC"
		And I fill in field number "4" with "04162010"
		And I follow link "Add" with index "1"
		Then I select "is Curriculum Child of" from ajax selectbox number "2"
		And I fill in field number "6" with "Oceanography Division"
		And I fill in field number "7" with "04162010"	
		And I follow link "Add" with index "2"
		Then I select "Chair" from ajax selectbox number "3"
		And I fill in field number "9" with "Chair"
		And I fill in field number "10" with "Chair"
		And I fill in field number "11" with "1"
		And I fill in field number "12" with "5"
		And I follow link "Add" with index "2"
		Then I select "Member" from ajax selectbox number "4"
		And I fill in field number "13" with "Member"
		And I fill in field number "14" with "Member COC"
		And I fill in field number "15" with "1"
		And I fill in field number "16" with "9"		
		And I follow "Save"
		And I follow ajax link "Ok"


		And I wait for "2" second
		And I click the label "Home" with class "gwt-Label KS-Label KS-CutomDropDown-TitleLabel"
		When I follow ajax link "Organization Management"
		Then I press ajax label "Organization"
		Then I select "Department" from ajax selectbox number "1"
		And I fill in field number "1" with "Fisheries Department"
		And I fill in field number "2" with "Fisheries Department"
		And I fill in field number "3" with "Fisheries Department"
		And I fill in field number "4" with "04162010"
		And I follow link "Add" with index "1"
		Then I select "is Curriculum Child of" from ajax selectbox number "2"
		And I fill in field number "6" with "Oceanography Division"
		And I fill in field number "7" with "04162010"
		And I follow link "Add" with index "2"
		And I wait for "2" seconds
		Then I select "Member" from ajax selectbox number "3"
		And I fill in field number "9" with "Member"
		And I fill in field number "10" with "Member"
		And I fill in field number "11" with "1"
		And I fill in field number "12" with "1000"
		And I follow "Save"
		And I wait for "2" seconds
		And I follow ajax link "Ok"


		And I wait for "2" second
		And I click the label "Home" with class "gwt-Label KS-Label KS-CutomDropDown-TitleLabel"
		When I follow ajax link "Organization Management"
		Then I press ajax label "Organization"
		Then I select "COC" from ajax selectbox number "1"
		And I fill in field number "1" with "Fisheries Department COC"
		And I fill in field number "2" with "Fisheries Department COC"
		And I fill in field number "3" with "Fisheries Department COC"
		And I fill in field number "4" with "04162010"	
		And I follow link "Add" with index "1"
		Then I select "is Curriculum Child of" from ajax selectbox number "2"
		And I fill in field number "6" with "Fisheries Department"
		And I fill in field number "7" with "04162010"	
		And I follow link "Add" with index "2"
		And I wait for "2" seconds
		Then I select "Chair" from ajax selectbox number "3"
		And I fill in field number "9" with "Chair"
		And I fill in field number "10" with "Chair"
		And I fill in field number "11" with "1"
		And I fill in field number "12" with "5"
		And I follow link "Add" with index "2"
		Then I select "Member" from ajax selectbox number "4"
		And I fill in field number "13" with "Member"
		And I fill in field number "14" with "Member COC"
		And I fill in field number "15" with "1"
		And I fill in field number "16" with "9"		
		And I follow "Save"
		And I wait for "2" seconds
		And I follow ajax link "Ok"

		And I wait for "2" second
		And I click the label "Home" with class "gwt-Label KS-Label KS-CutomDropDown-TitleLabel"
		When I follow ajax link "Organization Management"
		Then I press ajax label "Organization"
		Then I select "Department" from ajax selectbox number "1"
		And I fill in field number "1" with "Psychology Dept"
		And I fill in field number "2" with "Psychology"
		And I fill in field number "3" with "Psychology"
		And I fill in field number "4" with "04162010"	
		And I follow link "Add" with index "2"
		And I wait for "2" seconds
		Then I select "Member" from ajax selectbox number "2"
		And I fill in field number "6" with "Member"
		And I fill in field number "7" with "Member"
		And I fill in field number "8" with "1"
		And I fill in field number "9" with "1000"
		And I follow "Save"
		And I wait for "2" seconds
		And I follow ajax link "Ok"
	
		And I wait for "1" second
		And I click the label "Home" with class "gwt-Label KS-Label KS-CutomDropDown-TitleLabel"
		When I follow ajax link "Organization Management"
		Then I press ajax label "Organization"
		Then I select "COC" from ajax selectbox number "1"
		And I fill in field number "1" with "The College of Behavioral and Social Science COC"
		And I fill in field number "2" with "The College of Behavioral and Social Science COC"
		And I fill in field number "3" with "The College of Behavioral and Social Science COC"
		And I fill in field number "4" with "04162010"	
		And I follow link "Add" with index "1"
		Then I select "is Curriculum Child of" from ajax selectbox number "2"
		And I fill in field number "6" with "The College of Behavioral and Social Science"
		And I fill in field number "7" with "04162010"	
		And I follow link "Add" with index "2"
		Then I select "Chair" from ajax selectbox number "3"
		And I fill in field number "9" with "Chair"
		And I fill in field number "10" with "Chair"
		And I fill in field number "11" with "1"
		And I fill in field number "12" with "5"
		And I follow link "Add" with index "2"
		And I wait for "2" seconds
		Then I select "Member" from ajax selectbox number "4"
		And I fill in field number "13" with "Member"
		And I fill in field number "14" with "Member COC"
		And I fill in field number "15" with "1"
		And I fill in field number "16" with "9"		
		And I follow "Save"
		And I wait for "2" seconds
		And I follow ajax link "Ok"

		And I wait for "2" second
		And I click the label "Home" with class "gwt-Label KS-Label KS-CutomDropDown-TitleLabel"
		When I follow ajax link "Organization Management"
		Then I press ajax label "Organization"
		Then I select "COC" from ajax selectbox number "1"
		And I fill in field number "1" with "The College of Life and Chemical Sciences Curriculum Committee"
		And I fill in field number "2" with "The College of Life and Chemical Sciences Curriculum Committee"
		And I fill in field number "3" with "The College of Life and Chemical Sciences Curriculum Committee"
		And I fill in field number "4" with "04162010"	
		And I follow link "Add" with index "1"
		Then I select "is Curriculum Child of" from ajax selectbox number "2"
		And I fill in field number "6" with "The College of Life and Chemical Sciences"
		And I fill in field number "7" with "04162010"	
		And I follow link "Add" with index "2"
		Then I select "Chair" from ajax selectbox number "3"
		And I fill in field number "9" with "Chair"
		And I fill in field number "10" with "Chair COC"
		And I fill in field number "11" with "1"
		And I fill in field number "12" with "5"
		And I follow link "Add" with index "2"
		And I wait for "2" seconds
		Then I select "Member" from ajax selectbox number "4"
		And I fill in field number "13" with "Member"
		And I fill in field number "14" with "Member COC"
		And I fill in field number "15" with "1"
		And I fill in field number "16" with "9"		
		And I follow "Save"
		And I wait for "2" seconds
		And I follow ajax link "Ok"

		And I wait for "2" second
		And I click the label "Home" with class "gwt-Label KS-Label KS-CutomDropDown-TitleLabel"
		And I follow ajax link "Organization Management"
		Then I press ajax label "Search/Modify"
		And I fill in field number "1" with "Psychology Dept"
		And I wait for "2" seconds
		And I click item in divclass "suggestPopupMiddleCenterInner suggestPopupContent" table number "1" with "Psychology Dept"
		And I wait for "3" seconds
		And I follow "Modify"
		And I wait for "2" seconds
		And I need to find number of input fields
		And I need to find number of selectboxes			
		And I follow link "Add" with index "2"
		And I wait for "2" seconds
		And I select "Member" from next ajax selectbox
		And I fill in next field with "Member"
		And I fill in next field with "Member"
		And I fill in next field with "1"			
		And I fill in next field with "1000"
		And I follow "Save"
		And I wait for "2" seconds
		And I follow ajax link "Ok"		

		And I wait for "2" seconds
		And I click the label "Home" with class "gwt-Label KS-Label KS-CutomDropDown-TitleLabel"
		And I follow ajax link "Organization Management"
		Then I press ajax label "Search/Modify"
		And I fill in field number "1" with "Geology Dept"
		And I wait for "2" seconds
		And I click item in divclass "suggestPopupMiddleCenterInner suggestPopupContent" table number "1" with "Geology Dept"
		And I wait for "3" seconds
		And I follow "Modify"
		And I wait for "2" seconds
		And I need to find number of input fields
		And I need to find number of selectboxes			
		And I follow link "Add" with index "2"
		And I wait for "2" seconds
		And I select "Member" from next ajax selectbox
		And I fill in next field with "Member of the Geology Dept"
		And I fill in next field with "Member of the Geology Dept"
		And I fill in next field with "1"			
		And I fill in next field with "1000"
		And I follow "Save"
		And I wait for "2" seconds
		And I follow ajax link "Ok"

		And I wait for "1" seconds
		And I click the label "Home" with class "gwt-Label KS-Label KS-CutomDropDown-TitleLabel"
		And I follow ajax link "Organization Management"
		Then I press ajax label "Search/Modify"
		And I fill in field number "1" with "Chemical Science Division Coc"
		And I wait for "2" seconds
		And I click item in divclass "suggestPopupMiddleCenterInner suggestPopupContent" table number "1" with "Chemical Science Division Coc"
		And I wait for "3" seconds
		And I follow "Modify"
		And I wait for "2" seconds
		And I need to find number of input fields
		And I need to find number of selectboxes			
		And I follow link "Add" with index "2"
		And I wait for "2" seconds
		And I select "Member" from next ajax selectbox
		And I fill in next field with "Member of the Chemical Division COC"
		And I fill in next field with "Member of the Chemical Division COC"
		And I fill in next field with "1"			
		And I fill in next field with "1000"
		And I follow "Save"
		And I follow ajax link "Ok"
		And I follow link "Add" with index "2"
		And I select "Chair" from next ajax selectbox
		And I fill in next field with "Chair of the Chemical Division COC"
		And I fill in next field with "Chair of the Chemical Division COC"
		And I fill in next field with "1"			
		And I fill in next field with "5"
		And I follow "Save"
		And I wait for "2" seconds
		And I follow ajax link "Ok"

		And I wait for "2" second	
		And I click the label "Home" with class "gwt-Label KS-Label KS-CutomDropDown-TitleLabel"
		And I follow ajax link "Organization Management"
		Then I press ajax label "Search/Modify"
		And I fill in field number "1" with "The College of Life and Chemical Sciences Curriculum Committee"
		And I wait for "2" seconds
		And I click item in divclass "suggestPopupMiddleCenterInner suggestPopupContent" table number "1" with "The College of Life and Chemical Sciences Curriculum Committee"
		And I wait for "3" seconds
		And I follow "Modify"
		And I wait for "2" seconds
		And I need to find number of input fields
		And I need to find number of selectboxes			
		And I follow link "Add" with index "2"
		And I select "Chair" from next ajax selectbox
		And I fill in next field with "Chair of COC"
		And I fill in next field with "Chair of COC"
		And I fill in next field with "1"			
		And I fill in next field with "5"
		And I follow "Save"
		And I follow ajax link "Ok"
		And I follow link "Add" with index "2"
		And I wait for "2" seconds
		And I select "Member" from next ajax selectbox
		And I fill in next field with "Member of COC"
		And I fill in next field with "Member of COC"
		And I fill in next field with "1"			
		And I fill in next field with "1000"
		And I follow "Save"
		And I wait for "2" seconds
		And I follow ajax link "Ok"

		And I wait for "2" second
		And I click the label "Home" with class "gwt-Label KS-Label KS-CutomDropDown-TitleLabel"
		And I follow ajax link "Organization Management"
		Then I press ajax label "Search/Modify"
		And I fill in field number "1" with "Senate Subcommittee on Curricula"
		And I wait for "2" seconds
		And I click item in divclass "suggestPopupMiddleCenterInner suggestPopupContent" table number "1" with "Senate Subcommittee on Curricula"
		And I wait for "3" seconds
		And I follow "Modify"
		And I wait for "2" seconds
		And I need to find number of input fields
		And I need to find number of selectboxes			
		And I follow link "Add" with index "2"
		And I wait for "2" seconds
		And I select "Chair" from next ajax selectbox
		And I fill in next field with "Chair of the COC"
		And I fill in next field with "Chair of the COC"
		And I fill in next field with "1"			
		And I fill in next field with "5"
		And I follow "Save"
		And I follow ajax link "Ok"
		And I follow link "Add" with index "2"
		And I select "Member" from next ajax selectbox
		And I fill in next field with "Member of the COC"
		And I fill in next field with "Member of the COC"
		And I fill in next field with "1"			
		And I fill in next field with "1000"
		And I follow "Save"
		And I wait for "2" seconds
		And I follow ajax link "Ok"		


@roles @timer
	Scenario: Roles entry
		Given I am logged in as "admin" with the password "admin"
		And I am on the kuali homepage
		And I wait for "2" seconds
		And I click the label "Home" with class "gwt-Label KS-Label KS-CutomDropDown-TitleLabel"
		And I wait for "2" seconds
		And I follow ajax link "Organization Management"
		And I wait for "3" seconds
		And I map csv users from "2" to "162" rows to organizations

