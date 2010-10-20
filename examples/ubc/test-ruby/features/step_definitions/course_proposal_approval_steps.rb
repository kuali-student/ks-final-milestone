When /^I approve proposal "([^\"]*)" with user "([^\"]*)" and password "([^\"]*)"$/ do |title, username, password|
    Given "I am on logout"
    And "I wait for text \"Password\""
    When "I log in with user \"#{username}\" and password \"#{password}\""
    And "I wait for the page to load"
    Then "I should see \"#{username}\""
    And "I select the Action List"
    And "I wait for text \"Action List\""
    Then "I should see \"#{title}\""
    And "I should see \"Credit Course Proposal\""
    And "I open the proposal \"#{title}\""
    Then "I should see \"Workflow Actions\""
    And "I approve the proposal"
end

When /^I open the proposal "([^\"]*)" with user "([^\"]*)" and password "([^\"]*)"$/ do |title, username, password|
    Given "I am on logout"
    And "I wait for text \"Password\""
    When "I log in with user \"#{username}\" and password \"#{password}\""
    And "I wait for the page to load"
    Then "I should see \"#{username}\""
    And "I select the Action List"
    And "I wait for text \"Action List\""
    Then "I should see \"#{title}\""
    And "I should see \"Credit Course Proposal\""
    And "I open the proposal \"#{title}\""  
end

When /^I log in as "([^\"]*)"$/ do |user|
  selenium.type("j_username", user)
  selenium.type("j_password", user)
  click_button
end
When /^I goto the route log for the proposal$/ do
  url = selenium.get_location
  sub_string = /docId=([0-9]*)\&?/.match(url)
  doc_id = sub_string[1]
  When "I am on action list"
  When "I select the Action List"
  selenium.click("jquery=tr:contains('#{doc_id}'):last a:has('img')")
end
