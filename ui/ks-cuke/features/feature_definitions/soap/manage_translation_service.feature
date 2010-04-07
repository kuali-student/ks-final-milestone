Feature: Translation web service

  In order be able to call a web service for translation
  As a student, teacher or admin
  I want call the service and get some results
  
  Scenario: I should be able to connect to the service
    Given I am connected to the "TranslationService" service
	When I call the "get_natural_language_for_req_component" action with xml "get_natural_language_for_req_component_info"
	Then response should match xml "get_natural_language_for_req_component_response"
	#Then response parameter "return" for the action "get_natural_language_for_req_component_response" should contain "Student must have completed 1 of MATH152, MATH180"
	#Then show me the output in the console
	