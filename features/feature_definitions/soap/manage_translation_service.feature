Feature: Translation web service

  In order be able to call a web service for translation
  As a student, teacher or admin
  I want call the service and get some results

  Scenario: I should be able to connect to the service
    Given I am connected to the "TranslationService" service
	When I call the "get_natural_language_for_req_component" action with xml "get_natural_language_for_req_component_info"
	Then response should match xml "get_natural_language_for_req_component_response"
	Then response parameter "return" for the action "get_natural_language_for_req_component_response" should contain "Student must have completed 1 of MATH152, MATH180"
	Then show me the output in the console

@soap
  Scenario: I should be able to create a CLU
	Given I am connected to the "LuService" service
	When I call the "create_clu" action with params ":luTypeKey=>'kuali.lu.type.CreditCourse', :cluInfo=>{:attributes! => {:cluInfo=>{:type=>'kuali.lu.type.CreditCourse', :state=>'draft', :id=>''}}, :officialIdentifier=>{:attributes! => {:officialIdentifier=>{:type=>'',:state=>'',:id=>''}}}, :primaryAdminOrg=>{:orgId=>'51'}, :campusLocations=>'NorthCountyCampus', :campusLocations=>'SouthCountyCampus'}" and namespace "http://student.kuali.org/wsdl/lu"
	Then response for the action "create_clu_response" should contain ":can_create_lui=>false, :type=>'kuali.lu.type.CreditCourse'"
	And add "[:id]" from "create_clu_response" to the input hash as ":cluId => '!value'"
	
@soap	
  Scenario: I should be able to get a CLU
	Given I am connected to the "LuService" service
	When I call the "get_clu" action with params "" and namespace "http://student.kuali.org/wsdl/lu"
	Then response for the action "get_clu_response" should contain ":can_create_lui=>false, :type=>'kuali.lu.type.CreditCourse'"
	#And I clean the input hash
	And add "[:id]" from "get_clu_response" to the input hash as ":proposalInfo => {:proposalReference => '!value'}"
	#new add
	And add "[:official_identifier][:id]" from "get_clu_response" to the input hash as ":officialIdentifier => {:id => '!value'}"
	And add "[:primary_admin_org][:id]" from "get_clu_response" to the input hash as ":primaryAdminOrg => {:id => '!value'}"

@soap
	Scenario: I should be able to create a Proposal
	Given I am connected to the "ProposalService" service
	When I call the "create_proposal" action with params ":proposalTypeKey=>'kuali.proposal.type.course.create', :proposalInfo=>{:attributes! => {:proposalInfo=>{:type=>'kuali.proposal.type.course.create'}}, :name=>'Proposal Title', :proposalReferenceType=>'kuali.proposal.referenceType.clu'}" and namespace "http://student.kuali.org/wsdl/proposal"
	#Then response for the action "create_proposal_response" should contain ":name=>'Proposal Title', :proposalReferenceType=>'kuali.proposal.referenceType.clu'"
	Then response for the action "create_proposal_response" should contain ":type=>'kuali.proposal.type.course.create', :attributes=>nil,:name=>'Proposal Title'"
	#And I clean the input hash
	And add "[:id]" from "create_proposal_response" to the input hash as ":copyProposalId => '!value'"
	And add "[:id]" from "create_proposal_response" to the input hash as ":copyProposalId2 => '!value'"
	And add "[:proposal_reference]" from "create_proposal_response" to the input hash as ":copyCluId => '!value'"
	And add "[:meta_info][:version_ind]" from "create_proposal_response" to the input hash as ":metaInfo => {:versionInd=> '!value'}"
	#And add value "[:proposal_reference]" from "create_proposal_response" to the input hash as ":copyCluId => '!value'"
	#And add value "[:meta_info][:version_ind]" from "create_proposal_response" to the input hash as ":versionInd=> '!value'"

@soap2
	Scenario: I should be able to update a Proposal
	Given I am connected to the "ProposalService" service
	When I call the "update_proposal" action with params ":proposalTypeKey=>'kuali.proposal.type.course.create', :proposalInfo=>{:attributes! => {:proposalInfo=>{:type=>'kuali.proposal.type.course.create'}}, :name=>'Updated Proposal Title', :proposalReferenceType=>'kuali.proposal.referenceType.clu'}" and namespace "http://student.kuali.org/wsdl/proposal"
	#Then response for the action "create_proposal_response" should contain ":name=>'Proposal Title', :proposalReferenceType=>'kuali.proposal.referenceType.clu'"
	Then response for the action "update_proposal_response" should contain ":type=>'kuali.proposal.type.course.create', :attributes=>nil,:name=>'Updated Proposal Title'"
	#And I clean the input hash
	#And add "[:id]" from "update_proposal_response" to the input hash as ":copyCluIdToCluIdElement => '!value'"
	#And add "[:id]" from "update_proposal_response" to the input hash as ":copyCluIdToCluIdAttribute => '!value'"
	#And add value "[:proposal_reference]" from "update_proposal_response" to the input hash as ":copyCluId => '!value'"
	#And add value "[:meta_info][:version_ind]" from "update_proposal_response" to the input hash as ":versionInd=> '!value'"
