Given /^(?:|I )am on (.+)$/ do |page_name|
  @browser.goto path_to(page_name)
end

When /^(?:|I )fill in "([^\"]*)" with "([^\"]*)"$/ do |field, value|
  @browser.text_field(:name, field).set(value)
end

When /^(?:|I )click "([^\"]*)"$/ do |btng|
  @browser.button(:name, btng).click
end

When /^(?:|I )press "([^\"]*)"$/ do |btng|
  @browser.button(:text, btng).click
end

When /^(?:|I )follow "([^\"]*)"$/ do |text|
    @browser.link(:text, text).click
end

When /^(?:|I )select "([^\"]*)" from "([^\"]*)"$/ do |value, field|
  @browser.select_list(:name, field).set(value)
end

When /^(?:|I )click the "([^\"]*)" label$/ do |field|
	@browser.div(:text, field).click
end

When /^(?:|I )click the "([^\"]*)" xpath$/ do |field|
	@browser.element_by_xpath(field).click()
end

Then /^(?:|I )should see "([^\"]*)"$/ do |text|
  @browser.text.should include(text)
end

After do
  @browser.goto path_to("the logout path")
end
