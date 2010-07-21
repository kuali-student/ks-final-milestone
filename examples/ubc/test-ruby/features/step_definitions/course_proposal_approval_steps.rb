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
    Then "I should see \"Authors & Rationale\""
    And "I approve the proposal"
end
When /^I log in as "([^\"]*)"$/ do |user|
  selenium.type("j_username", user)
  selenium.type("j_password", user)
  click_button
end