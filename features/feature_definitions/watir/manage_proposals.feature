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
		Then I should see "New Course"
		#Smoke1
		And I wait for "2" seconds
		And I follow "Authors & Rationale"
		And I wait for "1" seconds
		And I fill in labeled field "Proposal Title" with "BOTANY 123"
		And I fill in labeled field "Proposal Rationale" with "It is BOTANY 123"
		And I follow "Save & Continue"	
		And I follow link "Save" in div_class "ks-lightbox-content KS-Vertical-Flow"	
		And I follow ajax link "Ok"
		#Smoke2
		And I follow "Governance"
		And I wait for "1" seconds
		And I fill in field number "5" with "Botany Dept"
		And I wait for "3" seconds
		And I click item in divclass "suggestPopupMiddleCenterInner suggestPopupContent" table number "1" with "Botany Dept"				
		#And I fill in labeled field "Curriculum Oversight" with "Botany Dept"
		And I should see checkbox number "1"
		And I set checkbox number "1"
		#And I fill in field number "6" with "Botany Dept"
		And I fill in field number "6" with "Botany Dept"
		And I wait for "2" seconds
		And I click item in divclass "suggestPopupMiddleCenterInner suggestPopupContent" table number "1" with "Botany Dept"		
		And I follow "Save & Continue"
		And I wait for div "Save Successful"
		And I follow ajax link "Ok"
        #Smoke3	
		#And I refresh browser
		And I wait for "2" seconds
		And I follow "Course Logistics"	
		And I wait for "3" seconds
		And I fill in field number "4" with "fran"
		And I wait for "4" seconds
		And I click item in divclass "suggestPopupMiddleCenterInner suggestPopupContent" table number "1" with "fran, fran(fran)"
		And I wait for "2" seconds
		#And I fill in field number "4" with "2"
		And I fill in labeled field "Duration" with "2"
		And I select "Year" from selectbox number "1"
		And I set checkbox number "1"
		And I select "1 Credit" from selectbox number "2"
		And I follow "Add Additional Format"
		And I follow "Add Activity"
		And I select "Lecture" from ajax selectbox number "3"
		#And I fill in field number "5" with "2"
		And I fill in labeled field "Contact Hours" with "2" 
		And I select "Half Semester" from ajax selectbox number "4"
		And I fill in field number "6" with "2"
		And I select "per week" from ajax selectbox number "5"
		#And I fill in field number "7" with "10"
		And I fill in labeled field "Class Size" with "10"	
		And I follow "Save & Continue"
		And I wait for div "Save Successful"
		And I follow ajax link "Ok"
		And I wait for "3" seconds		
		#Smoke4
		And I follow "Course Information"
		And I wait for "1" second
		And I fill in field number "2" with "BOTA"
		And I wait for "1" second
		And I click item in divclass "suggestPopupMiddleCenterInner suggestPopupContent" table number "1" with "BOTA"		
		#And I fill in field number "4" with "123"
		And I fill in labeled field "Course Number" with "123"	
		And I fill in field number "4" with "BOTA"
		And I fill in field number "5" with "Botany 123"
		And I fill in field number "6" with "botany 123456 intro"
		And I follow "Save & Continue"
		And I wait for div "Save Successful"
		And I follow ajax link "Ok"						
		#Smoke5
		And I follow "Active Dates"
		And I wait for "1" second
		And I fill in labeled field "Course active on" with "04032010"
		And I fill in labeled field "Course inactive on" with "05302010"
		And I select "First Summer Session of 2010" from ajax selectbox number "1"
		And I follow "Save & Continue"
		And I wait for div "Save Successful"
		And I follow ajax link "Ok"
		#Smoke6
		And I follow "Financials"
		#And I fill in field number "1" with "Free"
		And I fill in labeled field "Justification of Fees" with "Free"
		#And I fill in field number "2" with "Botany Dept"
		And I fill in field number "3" with "Botany Dept"
		And I wait for "2" seconds
		And I click item in divclass "suggestPopupMiddleCenterInner suggestPopupContent" table number "1" with "Botany Dept"		
		#And I fill in labeled field "Justification of Fees" with "Botany Dept"
		And I fill in labeled field "Amount" with "1000"
		And I follow "Save & Continue"
		And I wait for div "Save Successful"
		And I follow ajax link "Ok"			
		And I click the label "Workflow Actions" with class "gwt-Label KS-Label KS-CutomDropDown-TitleLabel"
		And I wait for "2" seconds
		And I follow "Submit Proposal"
		And I wait for "1" second
		And I follow "Submit"
		And I wait for "2" seconds		

@smoke0 @timer
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
		#Smoke1
		And I wait for "2" seconds
		And I follow "Authors & Rationale"
		#And I fill in field number "1" with "Botany 123"
		#And I fill in field number "2" with "Botny Intro 123"
		And I wait for "1" seconds
		And I fill in labeled field "Proposal Title" with "BOTANY 123"
		And I fill in labeled field "Proposal Rationale" with "It is BOTANY 123"
		And I follow "Save"	
		And I follow link "Save" in div_class "ks-lightbox-content KS-Vertical-Flow"	
		And I follow ajax link "Ok"
		#Smoke2
		And I follow "Governance"
		And I wait for "1" seconds
		And I fill in field number "3" with "Botany Dept"
		#And I fill in labeled field "Curriculum Oversight" with "Botany Dept"
		And I should see checkbox number "1"
		And I set checkbox number "1"
		And I fill in field number "6" with "Botany Dept"
		And I wait for "2" seconds
		And I click item in divclass "suggestPopupMiddleCenterInner suggestPopupContent" table number "1" with "Botany Dept"		
		And I follow "Save"
		And I wait for div "Save Successful"
		And I follow ajax link "Ok"		
			
@smoke9 @timer
	 Scenario: Create a new course proposal
		Given I am logged in as "eric" with the password "eric"
		And I am on the kuali homepage
		And I wait for "4" seconds	
		And I follow link "3001" in frameclass "gwt-Frame KS-Action-List"
		#Smoke1
		And I wait for "2" seconds
		And I follow "Authors & Rationale"
		And I wait for "1" seconds
		And I fill in labeled field "Proposal Title" with "BOTANY 123"
		And I fill in labeled field "Proposal Rationale" with "It is BOTANY 123"
		And I follow "Save"	
		And I wait for div "Save Successful"
		And I follow ajax link "Ok"
		#Smoke2
		And I wait for "2" seconds
		And I follow "Governance"
		And I wait for "3" seconds
		And I fill in field number "4" with "Botany Dept"
		And I wait for "3" seconds
		And I click item in divclass "suggestPopupMiddleCenterInner suggestPopupContent" table number "1" with "Botany Dept"		
		#And I fill in labeled field "Curriculum Oversight" with "Botany Dept"
		And I should see checkbox number "1"
		And I set checkbox number "1"
		And I fill in field number "5" with "Botany Dept"
		And I wait for "2" seconds
		And I click item in divclass "suggestPopupMiddleCenterInner suggestPopupContent" table number "1" with "Botany Dept"		
		And I wait for div "Save Successful"
		And I follow "Save"
		And I wait for div "Save Successful"
		And I follow ajax link "Ok"					
		And I wait for "2" seconds	
        #Smoke3	
		#And I refresh browser
		When I follow "Course Logistics"	
		And I fill in field number "5" with "fran"
		And I wait for "2" seconds
		#previous entry needs special click on autocomplete box
		And I click item in divclass "suggestPopupMiddleCenterInner suggestPopupContent" table number "1" with "fran, fran(fran)"
		#And I fill in field number "4" with "2"
		And I fill in labeled field "Duration" with "2"
		And I select "Year" from selectbox number "1"
		And I set checkbox number "1"
		And I select "1 Credit" from selectbox number "2"
		#And I follow "Add Additional Format"
		#And I follow "Add Activity"
		And I select "Lecture" from ajax selectbox number "3"
		#And I fill in field number "5" with "2"
		And I fill in labeled field "Contact Hours" with "2" 
		And I select "Half Semester" from ajax selectbox number "4"
		And I fill in field number "7" with "2"		
		And I select "per week" from ajax selectbox number "5"
		#And I fill in field number "7" with "10"
		And I fill in labeled field "Class Size" with "10"	
		And I follow "Save"
		And I wait for div "Save Successful"
		And I follow ajax link "Ok"
		And I wait for "3" seconds
		#Smoke4
		When I follow "Course Information"
		And I wait for "2" second
		And I fill in field number "6" with "BOTA"
		And I wait for "2" second
		And I click item in divclass "suggestPopupMiddleCenterInner suggestPopupContent" table number "1" with "BOTA"		
		#And I fill in field number "4" with "123"
		And I fill in labeled field "Course Number" with "123"
		And I fill in field number "8" with "BOTA"
		And I fill in field number "9" with "Botany 123"
		And I fill in field number "10" with "botany 123 intro"
		And I wait for "2" seconds
		And I follow "Save"
		And I wait for div "Save Successful"
		And I follow ajax link "Ok"
		And I wait for "2" seconds	
		#Smoke5
		And I follow "Active Dates"
		And I wait for "2" seconds
		And I fill in labeled field "Course active on" with "04032010"
		And I fill in labeled field "Course inactive on" with "05302010"
		And I select "First Summer Session of 2010" from ajax selectbox number "1"
		And I follow "Save"
		And I wait for div "Save Successful"
		And I follow ajax link "Ok"
		And I wait for "2" seconds
		#Smoke6
		And I follow "Financials"
		And I wait for "2" seconds
		#And I fill in field number "1" with "Free"
		And I fill in labeled field "Justification of Fees" with "Free"
		And I wait for "2" seconds
		#And I fill in field number "2" with "Botany Dept"
		And I fill in field number "3" with "Botany"
		#And I fill in labeled field "Revenue" with "Botany"
		And I wait for "2" seconds
		And I click item in divclass "suggestPopupMiddleCenterInner suggestPopupContent" table number "1" with "Botany Dept"	
		#And I fill in field number "3" with "100"
		And I fill in labeled field "Amount" with "1000"
		And I wait for "2" seconds
		And I follow "Save"
		And I wait for div "Save Successful"
		And I follow ajax link "Ok"
		And I wait for "2" seconds
		And I click the "/html/body/div/table/tbody/tr/td/table/tbody/tr[2]/td/div/div/div/div/table/tbody/tr[2]/td/div/div/div/div/table/tbody/tr/td[2]" xpath
		And I wait for "2" seconds
		And I follow "Submit Proposal"
		And I wait for "1" second
		And I follow "Submit"
		And I wait for "2" seconds

@smoke9b @timer
	 Scenario: Enter Authors & Rationale
		Given I am logged in as "eric" with the password "eric"
		And I am on the kuali homepage
		And I wait for "2" seconds	
		#And I follow link "3001" in frameclass "gwt-Frame KS-Action-List"				
		#And I click the "/html/body/div/table/tbody/tr/td/table/tbody/tr[2]/td/div/div/div/div/table/tbody/tr[2]/td/div/div/div/div/table/tbody/tr/td[2]" xpath
		And I wait for "2" seconds
		And I follow "Submit Proposal"
		And I wait for "1" second
		And I follow "Submit"
		And I wait for "2" seconds		

		
@smoke1 @timer
	 Scenario: Enter Authors & Rationale
		Given I am logged in as "eric" with the password "eric"
		And I am on the kuali homepage
		And I wait for "2" seconds	
		#And I click the label "Home" with class "gwt-Label KS-Label KS-CutomDropDown-TitleLabel"
		#And I wait for "2" seconds		
		And I follow link "3000" in frameclass "gwt-Frame KS-Action-List"
		And I wait for "2" seconds
		And I follow "Authors & Rationale"
		And I wait for "2" seconds
		And I fill in labeled field "Proposal Title" with "BOTANY 123"
		And I fill in labeled field "Proposal Rationale" with "It is BOTANY 123"
		And I follow "Save & Continue"	
		And I wait for "2" seconds
		And I wait for div "Save Successful"	
		And I follow ajax link "Ok"
		And I wait for "5" seconds

@smoke2 @timer
	 Scenario: Enter Governance
		Given I am logged in as "eric" with the password "eric"
		And I am on the kuali homepage
		And I wait for "2" seconds	
		And I follow link "3000" in frameclass "gwt-Frame KS-Action-List"
		And I wait for "2" seconds
		And I follow "Governance"
		And I wait for "1" seconds
		And I fill in field number "3" with "Botany Dept"
		And I wait for "2" seconds
		And I click item in divclass "suggestPopupMiddleCenterInner suggestPopupContent" table number "1" with "Botany Dept"		
		#And I fill in labeled field "Curriculum Oversight" with "Botany Dept"
		And I should see checkbox number "1"
		And I set checkbox number "1"
		And I fill in field number "4" with "Botany Dept"
		And I wait for "2" seconds
		And I click item in divclass "suggestPopupMiddleCenterInner suggestPopupContent" table number "1" with "Botany Dept"		
		And I wait for div "Save Successful"
		And I follow "Save & Continue"
		And I wait for div "Save Successful"
		And I follow ajax link "Ok"					
		And I wait for "5" seconds

@smoke3 @timer
	 Scenario: Enter Course Logistics
		Given I am logged in as "eric" with the password "eric"
		And I am on the kuali homepage
		And I wait for "2" seconds	
		And I follow link "3000" in frameclass "gwt-Frame KS-Action-List"
		And I wait for "3" seconds		
		When I follow "Course Logistics"	
		And I fill in field number "3" with "fran"
		And I wait for "3" seconds
		#previous entry needs special click on autocomplete box
		And I click item in divclass "suggestPopupMiddleCenterInner suggestPopupContent" table number "1" with "fran, fran(fran)"
		#And I fill in field number "4" with "2"
		And I fill in labeled field "Duration" with "2"
		And I select "Year" from selectbox number "1"
		And I set checkbox number "1"
		And I select "1 Credit" from selectbox number "2"
		#And I follow "Add Additional Format"
		#And I follow "Add Activity"
		And I select "Lecture" from ajax selectbox number "3"
		#And I fill in field number "5" with "2"
		And I fill in labeled field "Contact Hours" with "2" 
		And I select "Half Semester" from ajax selectbox number "4"
		And I fill in field number "6" with "2"		
		And I select "per week" from ajax selectbox number "5"
		#And I fill in field number "7" with "10"
		And I fill in labeled field "Class Size" with "10"	
		And I follow "Save & Continue"
		And I wait for div "Save Successful"
		And I follow ajax link "Ok"
		And I wait for "3" seconds		

@smoke4 @timer
	 Scenario: Enter Course Information
		Given I am logged in as "eric" with the password "eric"
		And I am on the kuali homepage
		And I wait for "2" seconds	
		And I follow link "3000" in frameclass "gwt-Frame KS-Action-List"
		And I wait for "3" seconds		
		When I follow "Course Information"
		And I wait for "1" second
		And I fill in field number "3" with "BOTA"
		And I wait for "1" second
		And I click item in divclass "suggestPopupMiddleCenterInner suggestPopupContent" table number "1" with "BOTA"		
		#And I fill in field number "4" with "123"
		And I fill in labeled field "Course Number" with "123"
		And I fill in field number "5" with "BOTA"
		And I fill in field number "6" with "Botany 123"
		And I fill in field number "7" with "botany 123 intro"
		And I wait for "5" seconds
		And I follow "Save & Continue"
		And I wait for div "Save Successful"
		And I follow ajax link "Ok"
		And I wait for "5" seconds
		
@smoke5 @timer
	 Scenario: Enter Active Dates
		Given I am logged in as "eric" with the password "eric"
		And I am on the kuali homepage
		And I wait for "2" seconds	
		And I follow link "3000" in frameclass "gwt-Frame KS-Action-List"
		And I wait for "3" seconds
		And I follow "Active Dates"
		#And I fill in field number "1" with "04032010"
		#And I fill in field number "2" with "05302010"
		And I fill in labeled field "Course active on" with "04032010"
		And I fill in labeled field "Course inactive on" with "05302010"
		And I select "First Summer Session of 2010" from ajax selectbox number "1"
		And I follow "Save & Continue"
		And I wait for div "Save Successful"
		And I follow ajax link "Ok"
		And I wait for "3" seconds
						
@smoke6 @timer
	 Scenario: Enter Financials
		Given I am logged in as "eric" with the password "eric"
		And I am on the kuali homepage
		And I wait for "2" seconds	
		And I follow link "3000" in frameclass "gwt-Frame KS-Action-List"
		And I wait for "2" seconds		
		And I follow "Financials"
		And I wait for "2" seconds
		#And I fill in field number "1" with "Free"
		And I fill in labeled field "Justification of Fees" with "Free"
		And I wait for "2" seconds
		#And I fill in field number "2" with "Botany Dept"
		And I fill in field number "4" with "Botany"
		#And I fill in labeled field "Revenue" with "Botany"
		And I wait for "2" seconds
		And I click item in divclass "suggestPopupMiddleCenterInner suggestPopupContent" table number "1" with "Botany Dept"		
		#And I fill in labeled field "Justification of Fees" with "Botany Dept"
		#And I fill in field number "3" with "100"
		And I fill in labeled field "Amount" with "1000"
		And I wait for "5" seconds
		And I follow "Save & Continue"
		And I wait for div "Save Successful"
		And I follow ajax link "Ok"
		And I wait for "5" seconds

@smoke7 @timer
	 Scenario: Submit course proposal
		Given I am logged in as "eric" with the password "eric"
		And I am on the kuali homepage
		And I wait for "2" seconds
		And I follow link "3000" in frameclass "gwt-Frame KS-Action-List"
		And I wait for "3" seconds
		And I follow "Review Proposal"
		And I wait for "2" seconds		
		And I click the label "Workflow Actions" with class "gwt-Label KS-Label KS-CutomDropDown-TitleLabel"
		And I wait for "2" seconds
		And I follow "Submit Proposal"
		And I wait for "5" second
		#And I follow "Submit"
		#And I wait for "5" seconds
						
						
@org0 @timer
Scenario: Organization entry1 The College of Ocean and Fishery Sciences
		Given I am logged in as "admin" with the password "admin"
		And I am on the kuali homepage
		And I wait for "2" second
		And I click the label "Home" with class "gwt-Label KS-Label KS-CutomDropDown-TitleLabel"
		And I follow ajax link "Organization Management"
		Then I press ajax label "Search/Modify"
		#And I fill in field number "1" with "Senate Subcommittee on Curricula"
		And I need to find number of input fields 
		And I fill in last field with "Senate Subcommittee on Curricula"
		And I wait for "2" seconds
		And I click item in divclass "suggestPopupMiddleCenterInner suggestPopupContent" table number "1" with "Senate Subcommittee on Curricula"
		And I wait for "3" seconds
		
@org1 @timer
Scenario: Organization entry1 The College of Ocean and Fishery Sciences
		Given I am logged in as "admin" with the password "admin"
		And I am on the kuali homepage
		And I wait for "2" seconds
		And I click the label "Home" with class "gwt-Label KS-Label KS-CutomDropDown-TitleLabel"
		When I follow ajax link "Organization Management"
		Then I press ajax label "Organization"
		Then I select "College" from ajax selectbox number "1"
		And I wait for "1" second
		#And I fill field id "gwt-uid-19" with "The College of Ocean and Fishery Sciences"
		And I fill in labeled field "Name" with "The College of Ocean and Fishery Sciences"
		#And I fill field id "gwt-uid-20" with "COFS"
		And I fill in labeled field "Abbreviation" with "COFS" 
		#And I fill field id "gwt-uid-21" with "The College of Ocean and Fishery Sciences"
		And I fill in labeled field "Description" with "The College of Ocean and Fishery Sciences"
		#And I fill field id "gwt-uid-22" with "04162010"
		And I fill in labeled field "Effective Date" with "04162010"
		And I follow "Save"
		And I follow ajax link "Ok"
		And I wait for "2" seconds
		And I need to find number of input fields
		And I follow link "Add" with index "1"
		And I wait for "1" seconds
		Then I select "is Curriculum Child of" from ajax selectbox number "2"
		And I fill in next field with "The College of Ocean and Fishery Sciences"
		And I wait for "2" seconds
		And I click item in divclass "suggestPopupMiddleCenterInner suggestPopupContent" table number "1" with "The College of Ocean and Fishery Sciences"
		And I fill in next field with "04162010"
		And I need to find number of input fields
		And I follow link "Add" with index "2"
		Then I select "Member" from ajax selectbox number "3"
		And I fill in next field with "Member"
		And I fill in next field with "Member"
		And I fill in next field with "1"
		And I fill in next field with "1000"
		And I follow "Save"
		And I follow ajax link "Ok"

@org5 @timer
Scenario: Organization entry1 The College of Ocean and Fishery Sciences
	Given I am logged in as "admin" with the password "admin"
		And I am on the kuali homepage	
		And I wait for "2" second
		And I click the label "Home" with class "gwt-Label KS-Label KS-CutomDropDown-TitleLabel"
		When I follow ajax link "Organization Management"
		Then I press ajax label "Organization"
		Then I select "COC" from ajax selectbox number "1"
		And I wait for "1" second
		#And I fill field id "gwt-uid-19" with "The College of Ocean and Fishery Sciences"
		And I fill in labeled field "Name" with "The College of Ocean and Fishery Sciences COC"
		#And I fill field id "gwt-uid-20" with "COFS"
		And I fill in labeled field "Abbreviation" with "COFS COC" 
		#And I fill field id "gwt-uid-21" with "The College of Ocean and Fishery Sciences"
		And I fill in labeled field "Description" with "The College of Ocean and Fishery Sciences COC"
		#And I fill field id "gwt-uid-22" with "04162010"
		And I fill in labeled field "Effective Date" with "04162010"
		And I follow "Save"
		And I follow ajax link "Ok"
		And I wait for "2" seconds
		And I need to find number of input fields
		And I follow link "Add" with index "1"
		And I wait for "1" seconds
		Then I select "is Curriculum Child of" from ajax selectbox number "2"
		And I fill in next field with "The College of Ocean and Fishery Sciences COC"
		And I wait for "2" seconds
		And I click item in divclass "suggestPopupMiddleCenterInner suggestPopupContent" table number "1" with "The College of Ocean and Fishery Sciences COC"
		And I fill in next field with "04162010"
		And I need to find number of input fields
		And I follow link "Add" with index "2"
		Then I select "Chair" from ajax selectbox number "3"
		And I fill in next field with "Chair"
		And I fill in next field with "Chair"
		And I fill in next field with "1"
		And I fill in next field with "5"
		And I follow link "Add" with index "2"
		Then I select "Member" from ajax selectbox number "4"
		And I fill in next field with "Member"
		And I fill in next field with "Member COC"
		And I fill in next field with "1"
		And I fill in next field with "9"		
		And I follow "Save"
		And I follow ajax link "Ok"
	
@org2 @timer
Scenario: Organization entry1 The College of Ocean and Fishery Sciences
	Given I am logged in as "admin" with the password "admin"
	And I am on the kuali homepage	
	And I wait for "2" second
	And I click the label "Home" with class "gwt-Label KS-Label KS-CutomDropDown-TitleLabel"
	When I follow ajax link "Organization Management"
	Then I press ajax label "Organization"
	Then I select "COC" from ajax selectbox number "1"
	And I wait for "1" second
	#And I fill field id "gwt-uid-19" with "The College of Ocean and Fishery Sciences"
	And I fill in labeled field "Name" with "The College of Ocean and Fishery Sciences"
	#And I fill field id "gwt-uid-20" with "COFS"
	And I fill in labeled field "Abbreviation" with "COFS" 
	#And I fill field id "gwt-uid-21" with "The College of Ocean and Fishery Sciences"
	And I fill in labeled field "Description" with "The College of Ocean and Fishery Sciences"
	#And I fill field id "gwt-uid-22" with "04162010"
	And I fill in labeled field "Effective Date" with "04162010"
	And I follow "Save"
	And I follow ajax link "Ok"
	And I wait for "2" seconds
	And I need to find number of input fields
	And I follow link "Add" with index "1"
	And I wait for "1" seconds
	Then I select "is Curriculum Child of" from ajax selectbox number "2"
	And I fill in next field with "The College of Ocean and Fishery Sciences"
	And I wait for "2" seconds
	And I click item in divclass "suggestPopupMiddleCenterInner suggestPopupContent" table number "1" with "The College of Ocean and Fishery Sciences"
	And I fill in next field with "04162010"
	And I need to find number of input fields
	And I follow link "Add" with index "2"
	Then I select "Chair" from ajax selectbox number "3"
	And I fill in next field with "Chair"
	And I fill in next field with "Chair"
	And I fill in next field with "1"
	And I fill in next field with "5"
	And I follow link "Add" with index "2"
	Then I select "Member" from ajax selectbox number "4"
	And I fill in next field with "Member"
	And I fill in next field with "Member COC"
	And I fill in next field with "1"
	And I fill in next field with "9"		
	And I follow "Save"
	And I follow ajax link "Ok"

	And I wait for "2" second
	And I click the label "Home" with class "gwt-Label KS-Label KS-CutomDropDown-TitleLabel"
	When I follow ajax link "Organization Management"
	Then I press ajax label "Organization"
	Then I select "Division" from ajax selectbox number "1"
	And I wait for "1" second
	#And I fill field id "gwt-uid-19" with "Oceanography Division"
	And I fill in labeled field "Name" with "Oceanography Division"
	#And I fill field id "gwt-uid-20" with "Oceanography Division"
	And I fill in labeled field "Abbreviation" with "Oceanography Division"
	#And I fill field id "gwt-uid-21" with "Oceanography Division"
	And I fill in labeled field "Description" with "Oceanography Division"
	#And I fill field id "gwt-uid-22" with "04162010"
	And I fill in labeled field "Effective Date" with "04162010"
	And I need to find number of input fields
	And I follow link "Add" with index "1"
	Then I select "is Curriculum Child of" from ajax selectbox number "2"
	And I fill in next field with "The College of Ocean and Fishery Sciences"
	And I wait for "2" seconds	
	And I click item in divclass "suggestPopupMiddleCenterInner suggestPopupContent" table number "1" with "The College of Ocean and Fishery Sciences"
	And I fill in next field with "04162010"
	And I need to find number of input fields
	And I follow link "Add" with index "2"
	Then I select "Member" from ajax selectbox number "3"
	And I fill in next field with "Member"
	And I fill in next field with "Member"
	And I fill in next field with "1"
	And I fill in next field with "1000"
	And I follow "Save"
	And I follow ajax link "Ok"

		
	And I wait for "1" second
	And I click the label "Home" with class "gwt-Label KS-Label KS-CutomDropDown-TitleLabel"
	When I follow ajax link "Organization Management"
	Then I press ajax label "Organization"
	Then I select "COC" from ajax selectbox number "1"
	And I wait for "1" second
	#And I fill field id "gwt-uid-19" with "Oceanography Division COC"
	And I fill in labeled field "Name" with "Oceanography Division COC"
	#And I fill field id "gwt-uid-20" with "Oceanography Division COC"
	And I fill in labeled field "Abbreviation" with "Oceanography Division COC"
	#And I fill field id "gwt-uid-21" with "Oceanography Division COC"
	And I fill in labeled field "Description" with "Oceanography Division COC"
	#And I fill field id "gwt-uid-22" with "04162010"
	And I fill in labeled field "Effective Date" with "04162010"
	And I need to find number of input fields
	And I follow link "Add" with index "1"
	Then I select "is Curriculum Child of" from ajax selectbox number "2"
	And I fill in next field with "Oceanography Division"
	And I wait for "2" seconds
	And I click item in divclass "suggestPopupMiddleCenterInner suggestPopupContent" table number "1" with "Oceanography Division"
	And I fill in next field with "04162010"
	And I need to find number of input fields	
	And I follow link "Add" with index "2"
	Then I select "Chair" from ajax selectbox number "3"
	And I fill in next field with "Chair"
	And I fill in next field with "Chair"
	And I fill in next field with "1"
	And I fill in next field with "5"
	And I follow link "Add" with index "2"
	Then I select "Member" from ajax selectbox number "4"
	And I fill in next field with "Member"
	And I fill in next field with "Member COC"
	And I fill in next field with "1"
	And I fill in next field with "9"		
	And I follow "Save"
	And I follow ajax link "Ok"


	And I wait for "2" second
	And I click the label "Home" with class "gwt-Label KS-Label KS-CutomDropDown-TitleLabel"
	When I follow ajax link "Organization Management"
	Then I press ajax label "Organization"
	Then I select "Department" from ajax selectbox number "1"
	And I wait for "1" second
	#And I fill field id "gwt-uid-19" with "Fisheries Department"
	And I fill in labeled field "Name" with "Fisheries Department"
	#And I fill field id "gwt-uid-20" with "Fisheries Department"
	And I fill in labeled field "Abbreviation" with "Fisheries Department"
	#And I fill field id "gwt-uid-21" with "Fisheries Department"
	And I fill in labeled field "Description" with "Fisheries Department"
	#And I fill field id "gwt-uid-22" with "04162010"
	And I fill in labeled field "Effective Date" with "04162010"
	And I need to find number of input fields
	And I follow link "Add" with index "1"
	Then I select "is Curriculum Child of" from ajax selectbox number "2"
	And I fill in next field with "Oceanography Division"
	And I wait for "2" seconds
	And I click item in divclass "suggestPopupMiddleCenterInner suggestPopupContent" table number "1" with "Oceanography Division"
	And I fill in next field with "04162010"
	And I need to find number of input fields
	And I follow link "Add" with index "2"
	Then I select "Member" from ajax selectbox number "3"
	And I fill in next field with "Member"
	And I fill in next field with "Member"
	And I fill in next field with "1"
	And I fill in next field with "1000"
	And I follow "Save"
	And I follow ajax link "Ok"

	And I wait for "1" second
	And I click the label "Home" with class "gwt-Label KS-Label KS-CutomDropDown-TitleLabel"
	When I follow ajax link "Organization Management"
	Then I press ajax label "Organization"
	Then I select "COC" from ajax selectbox number "1"
	And I wait for "1" second
	#And I fill field id "gwt-uid-19" with "Fisheries Department COC"
	And I fill in labeled field "Name" with "Fisheries Department COC"
	#And I fill field id "gwt-uid-20" with "Fisheries Department COC"
	And I fill in labeled field "Abbreviation" with "Fisheries Department COC"
	#And I fill field id "gwt-uid-21" with "Fisheries Department COC"
	And I fill in labeled field "Description" with "Fisheries Department COC"
	#And I fill field id "gwt-uid-22" with "04162010"
	And I fill in labeled field "Effective Date" with "04162010"
	And I need to find number of input fields
	And I follow link "Add" with index "1"
	Then I select "is Curriculum Child of" from ajax selectbox number "2"
	And I fill in next field with "Fisheries Department"
	And I wait for "2" seconds
	And I click item in divclass "suggestPopupMiddleCenterInner suggestPopupContent" table number "1" with "Fisheries Department"
	And I fill in next field with "04162010"
	And I need to find number of input fields	
	And I follow link "Add" with index "2"
	Then I select "Chair" from ajax selectbox number "3"
	And I fill in next field with "Chair"
	And I fill in next field with "Chair"
	And I fill in next field with "1"
	And I fill in next field with "5"
	And I follow link "Add" with index "2"
	Then I select "Member" from ajax selectbox number "4"
	And I fill in next field with "Member"
	And I fill in next field with "Member COC"
	And I fill in next field with "1"
	And I fill in next field with "9"		
	And I follow "Save"
	And I follow ajax link "Ok"

		And I wait for "1" second
		And I click the label "Home" with class "gwt-Label KS-Label KS-CutomDropDown-TitleLabel"
		When I follow ajax link "Organization Management"
		Then I press ajax label "Organization"
		Then I select "COC" from ajax selectbox number "1"
		And I wait for "1" second
		#And I fill field id "gwt-uid-19" with "The College of Behavioral and Social Science COC"
		And I fill in labeled field "Name" with "The College of Behavioral and Social Science COC"
		#And I fill field id "gwt-uid-20" with "The College of Behavioral and Social Science COC"
		And I fill in labeled field "Abbreviation" with "The College of Behavioral and Social Science COC"
		#And I fill field id "gwt-uid-21" with "The College of Behavioral and Social Science COC"
		And I fill in labeled field "Description" with "The College of Behavioral and Social Science COC"
		#And I fill field id "gwt-uid-22" with "04162010"
		And I fill in labeled field "Effective Date" with "04162010"
		And I need to find number of input fields	
		And I follow link "Add" with index "1"
		Then I select "is Curriculum Child of" from ajax selectbox number "2"
		And I fill in next field with "The College of Behavioral and Social Science"
		And I wait for "2" seconds
		And I click item in divclass "suggestPopupMiddleCenterInner suggestPopupContent" table number "1" with "The College of Behavioral and Social Science"
		And I fill in next field with "04162010"
		And I need to find number of input fields	
		And I follow link "Add" with index "2"
		Then I select "Chair" from ajax selectbox number "3"
		And I fill in next field with "Chair"
		And I fill in next field with "Chair"
		And I fill in next field with "1"
		And I fill in next field with "5"
		And I follow link "Add" with index "2"
		And I wait for "2" seconds
		Then I select "Member" from ajax selectbox number "4"
		And I fill in next field with "Member"
		And I fill in next field with "Member COC"
		And I fill in next field with "1"
		And I fill in next field with "9"		
		And I follow "Save"
		And I wait for "2" seconds
		And I follow ajax link "Ok"

@org2b @timer
Scenario: Organization entry1 The College of Ocean and Fishery Sciences
	Given I am logged in as "admin" with the password "admin"
		And I am on the kuali homepage	
		And I wait for "2" second
		And I click the label "Home" with class "gwt-Label KS-Label KS-CutomDropDown-TitleLabel"
		When I follow ajax link "Organization Management"
		Then I press ajax label "Organization"
		And I wait for "2" second
		And I click the label "Home" with class "gwt-Label KS-Label KS-CutomDropDown-TitleLabel"
		When I follow ajax link "Organization Management"
		Then I press ajax label "Organization"
		Then I select "COC" from ajax selectbox number "1"
		And I wait for "1" second
		#And I fill field id "gwt-uid-19" with "The College of Life and Chemical Sciences Curriculum Committee"
		And I fill in labeled field "Name" with "The College of Life and Chemical Sciences Curriculum Committee"
		#And I fill field id "gwt-uid-20" with "The College of Life and Chemical Sciences Curriculum Committee"
		And I fill in labeled field "Abbreviation" with "The College of Life and Chemical Sciences Curriculum Committee"
		#And I fill field id "gwt-uid-21" with "The College of Life and Chemical Sciences Curriculum Committee"
		And I fill in labeled field "Description" with "The College of Life and Chemical Sciences Curriculum Committee"
		#And I fill field id "gwt-uid-22" with "04162010"
		And I fill in labeled field "Effective Date" with "04162010"
		And I need to find number of input fields	
		And I follow link "Add" with index "1"
		Then I select "is Curriculum Child of" from ajax selectbox number "2"
		And I fill in next field with "The College of Life and Chemical Sciences"
		And I wait for "2" seconds
		And I click item in divclass "suggestPopupMiddleCenterInner suggestPopupContent" table number "1" with "The College of Life and Chemical Sciences"
		And I fill in next field with "04162010"
		And I need to find number of input fields	
		And I follow link "Add" with index "2"
		Then I select "Chair" from ajax selectbox number "3"
		And I fill in next field with "Chair of COC"
		And I fill in next field with "Chair of COC"
		And I fill in next field with "1"
		And I fill in next field with "5"
		And I follow link "Add" with index "2"
		And I wait for "2" seconds
		Then I select "Member" from ajax selectbox number "4"
		And I fill in next field with "Member of COC"
		And I fill in next field with "Member of COC"
		And I fill in next field with "1"
		And I fill in next field with "9"		
		And I follow "Save"
		And I wait for "2" seconds
		And I follow ajax link "Ok"


@org3 @timer
Scenario: Organization entry1 The College of Ocean and Fishery Sciences
	Given I am logged in as "admin" with the password "admin"
		And I am on the kuali homepage	
		And I wait for "2" second
		And I click the label "Home" with class "gwt-Label KS-Label KS-CutomDropDown-TitleLabel"
		When I follow ajax link "Organization Management"
		Then I press ajax label "Organization"
		And I wait for "2" second
		And I click the label "Home" with class "gwt-Label KS-Label KS-CutomDropDown-TitleLabel"
		And I follow ajax link "Organization Management"
		Then I press ajax label "Search/Modify"
		#And I fill in field number "1" with "Psychology Dept"
		And I need to find number of input fields
		And I fill in last field with "Psychology Dept"
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

		And I wait for "1" seconds
		And I click the label "Home" with class "gwt-Label KS-Label KS-CutomDropDown-TitleLabel"
		And I follow ajax link "Organization Management"
		Then I press ajax label "Search/Modify"
		#And I fill in field number "1" with "Geology Dept"
		And I need to find number of input fields
		And I fill in last field with "Geology Dept"
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
		#And I fill in field number "1" with "Chemical Science Division Coc"
		And I need to find number of input fields 
		And I fill in last field with "Chemical Science Division Coc"
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
		And I need to find number of input fields
		And I need to find number of selectboxes
		And I follow link "Add" with index "2"
		And I select "Chair" from next ajax selectbox
		And I fill in next field with "Chair of the Chemical Division COC"
		And I fill in next field with "Chair of the Chemical Division COC"
		And I fill in next field with "1"			
		And I fill in next field with "5"
		And I follow "Save"
		And I wait for "2" seconds
		And I follow ajax link "Ok"

@org4 @timer
Scenario: Organization entry1 The College of Ocean and Fishery Sciences
	Given I am logged in as "admin" with the password "admin"
		And I am on the kuali homepage
		And I wait for "2" second
		And I click the label "Home" with class "gwt-Label KS-Label KS-CutomDropDown-TitleLabel"
		And I follow ajax link "Organization Management"
		Then I press ajax label "Search/Modify"
		#And I fill in field number "1" with "Senate Subcommittee on Curricula"
		And I need to find number of input fields 
		And I fill in last field with "Senate Subcommittee on Curricula"
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
		And I need to find number of input fields
		And I need to find number of selectboxes
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
		And I map csv users from "144" to "163" rows to organizations




@smokeX @timer
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
		#Smoke1
		And I wait for "2" seconds
		And I follow "Authors & Rationale"
		#And I fill in field number "1" with "Botany 123"
		#And I fill in field number "2" with "Botny Intro 123"
		And I wait for "1" seconds
		And I fill in labeled field "Proposal Title" with "BOTANY 123"
		And I fill in labeled field "Proposal Rationale" with "It is BOTANY 123"
		And I follow "Save"	
		And I follow link "Save" in div_class "ks-lightbox-content KS-Vertical-Flow"	
		And I follow ajax link "Ok"
		#Smoke2
		And I follow "Governance"
		And I wait for "1" seconds
		And I fill in field number "3" with "Botany Dept"
		#And I fill in labeled field "Curriculum Oversight" with "Botany Dept"
		And I should see checkbox number "1"
		And I set checkbox number "1"
		And I fill in field number "6" with "Botany Dept"
		And I wait for "2" seconds
		And I click item in divclass "suggestPopupMiddleCenterInner suggestPopupContent" table number "1" with "Botany Dept"		
		And I follow "Save"
		And I wait for div "Save Successful"
		And I follow ajax link "Ok"
		#
		#And I follow "Course Information"
		#And I wait for "2" seconds
		#And I fill in field number "2" with "BOTA"
		#And I fill in field number "3" with "123"
		##next label is not good, input does not have id
		##And I fill in labeled field "Subject Code" with "BOTA"
		##And I fill in labeled field "Course Number" with "123"		
		#And I fill in field number "4" with "BOTA123"
		#And I fill in field number "5" with "BOTANY 123"
		#And I fill in field number "6" with "Botany 123456"
		##And I follow link "Save" in div_class "ks-lightbox-content KS-Vertical-Flow"
		#And I follow "Save"
		#And I wait for div "Save Successful"
		#And I follow ajax link "Ok"
        #Smoke3	
		#And I refresh browser
		And I wait for "2" seconds
		And I follow "Course Logistics"	
		And I wait for "3" seconds
		And I fill in field number "3" with "fran"
		And I wait for "4" seconds
		And I click item in divclass "suggestPopupMiddleCenterInner suggestPopupContent" table number "1" with "fran, fran(fran)"
		And I wait for "2" seconds
		And I fill in field number "4" with "2"
		And I select "Year" from selectbox number "1"
		And I set checkbox number "1"
		And I select "1 Credit" from selectbox number "2"
		And I follow "Add Additional Format"
		And I follow "Add Activity"
		And I select "Lecture" from ajax selectbox number "3"
		And I fill in field number "5" with "2"
		And I select "Half Semester" from ajax selectbox number "4"
		And I fill in field number "6" with "2"		
		And I select "per week" from ajax selectbox number "5"
		And I fill in field number "7" with "10"
		And I fill in labeled field "Contact Hours" with "2" 
		And I follow "Save"
		And I wait for div "Save Successful"
		And I follow ajax link "Ok"
		And I wait for "3" seconds		
		#Smoke4
		And I follow "Course Information"
		And I wait for "1" second
		And I fill in field number "3" with "BOTA"
		And I fill in field number "4" with "123"	
		And I fill in field number "5" with "BOTA"
		And I fill in field number "6" with "Botany 123"
		And I fill in field number "7" with "botany 123456 intro"
		And I follow "Save"
		And I wait for div "Save Successful"
		And I follow ajax link "Ok"						
		#Smoke5
		And I follow "Active Dates"
		And I fill in labeled field "Course active on" with "04032010"
		And I fill in labeled field "Course inactive on" with "05302010"
		And I select "First Summer Session of 2010" from ajax selectbox number "1"
		And I follow "Save"
		And I wait for div "Save Successful"
		And I follow ajax link "Ok"
		#Smoke6
		And I follow "Financials"
		#And I fill in field number "1" with "Free"
		And I fill in labeled field "Justification of Fees" with "Free"
		#And I fill in field number "2" with "Botany Dept"
		And I fill in field number "4" with "Botany Dept"
		#And I fill in labeled field "Justification of Fees" with "Botany Dept"
		#And I fill in field number "3" with "100"
		And I fill in labeled field "Amount" with "1000"
		And I follow "Save"
		And I wait for div "Save Successful"
		And I follow ajax link "Ok"			
		And I click the "/html/body/div/table/tbody/tr/td/table/tbody/tr[2]/td/div/div/div/div/table/tbody/tr[2]/td/div/div/div/div/table/tbody/tr/td[2]" xpath
		And I wait for "2" seconds
		And I follow "Submit Proposal"
		And I wait for "1" second
		And I follow "Submit"
		And I wait for "2" seconds		
