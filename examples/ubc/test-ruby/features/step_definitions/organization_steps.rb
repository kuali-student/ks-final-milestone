Given /^I am logged out$/ do
  visit path_to("logout")
end

When /^I log out$/ do
  "Given I am logged out"
end

When /^I log in as an administrator$/ do
  selenium.type "j_username", "admin"
  selenium.type "j_password", "admin"
  click_button
end

When /^I fill in organization data for a university$/ do
  selenium.click "jquery=td.KS-FormLayout-Label:contains('Type') + td select"
  selenium.click "jquery=select.KS-Dropdown:contains('School')"
  selenium.select "jquery=select.KS-Dropdown:contains('School')", "label=School"
  selenium.click "css=option:contains('School')"
  selenium.type "orgName", "University of British Columbia"
  selenium.type "orgAbbrev", "UBC"
  selenium.type "orgDesc", "UBC"
  selenium.click "jquery=td.KS-FormLayout-Label:contains('Effective Date') + td input"
  selenium.click "jquery=td.datePickerDay:contains('11')"
  selenium.click "jquery=td.KS-FormLayout-Label:contains('Expiration Date') + td input"
  selenium.click "jquery=td.datePickerDay:contains('22')"
end

# clicks the tree node arrow to expand and collapse a node in the Organization browse tree
When /^I expand "([^\"]*)"$/ do |tree_node|
  locator = "jquery=tr:has(div.gwt-TreeItem):contains('#{tree_node}'):last td:eq(0) img" 
  selenium.mouse_down(locator)
  selenium.mouse_up(locator)
end
When /^I click the tab "([^\"]*)"$/ do |tab_text|
  selenium.click "jquery=div.KS-TabPanel-Tab div.gwt-Label:contains('#{tab_text}')"
end