When /^I select "([^\"]*)" from a drop down with a heading of "([^\"]*)"$/ do |selected, heading|
  selenium.select "css=select.gwt-ListBox", "label=#{selected}"
end

When /^I fill in div labeled "([^\"]*)" with "([^\"]*)"$/ do |label, text|
  selenium.type "jquery=div:contains('#{label}')~input", text
end

When /^I select "([^\"]*)" from div labeled "([^\"]*)"$/ do |selected, label|
  if (selenium.is_element_present("jquery=div:contains('#{label}')~select:last"))
    selenium.select "jquery=div:contains('#{label}')~select:last", "label=#{selected}"
  else
    selenium.select "jquery=div:contains('#{label}') select:last", "label=#{selected}"
  end
end

# presses the last button
When /^(?:|I )click "([^\"]*)"$/ do |button|
  selenium.click "jquery=button:contains('#{button}'):last"
end

When /^I fill in a text area div labeled "([^\"]*)" with "([^\"]*)"$/ do |label, text|
  selenium.type "jquery=div:contains('#{label}')~textarea", text
end

When /^I fill in the "([^\"]*)" field with "([^\"]*)"$/ do |label, text|
    if selenium.is_element_present("jquery=div.ks-form-module-fields:contains('#{label}') input[type='text']")
      locator = "jquery=div.ks-form-module-fields:contains('#{label}') input[type='text']"
    elsif selenium.is_element_present("jquery=div.ks-form-module-elements:contains('#{label}') input[type='text']")
      locator = "jquery=div.ks-form-module-elements:contains('#{label}') input[type='text']"
    end
    selenium.type(locator, text)
end

When /^I fill in the "([^\"]*)" section with "([^\"]*)"$/ do |label, text|
    locator = "jquery=div.KS-LUM-Section:contains('#{label}'):last input[type='text']"
    selenium.type(locator, text)
end

When /^I fill in the "([^\"]*)" textarea section with "([^\"]*)"$/ do |label, text|
    locator = "jquery=div.KS-LUM-Section:contains('#{label}'):last textarea"
    selenium.type(locator, text)
end

When /^I select "([^\"]*)" from an autocomplete list$/ do |item|
  locator = "jquery=td[role=menuitem]:contains('#{item}')"
  selenium.wait_for_element locator
  selenium.click locator
end

When /^I choose "([^\"]*)" from "([^\"]*)"$/ do |option, label|
  selenium.select("jquery=div.KS-Horizontal-Block-Flow:contains('#{label}'):last select", option)
end

When /^I type in "([^\"]*)" with "([^\"]*)"$/ do |label, text|
  locator = "jquery=div.ks-form-module-elements:contains('#{label}') input[type='text']:last"
  selenium.focus(locator)
  type_each(locator, text)
end

When /^I type in "([^\"]*)" section with "([^\"]*)"$/ do |label, text|
  locator = "jquery=div.KS-LUM-Section:contains('#{label}'):last input[type='text']"
  selenium.focus(locator)
  type_each(locator, text)
end

When /^I click the textbox "([^\"]*)"$/ do |label|
  selenium.focus "jquery=div.ks-form-module-fields:contains('#{label}') input[type='text']"
  fire_key_event_sequence "jquery=div.ks-form-module-fields:contains('#{label}') input[type='text']"
end

When /^I click the link "([^\"]*)"$/ do |text|

  if selenium.is_element_present("jquery=div.gwt-Label:contains('#{text}')")
    selenium.click("jquery=div.gwt-Label:contains('#{text}')")
  else
    selenium.click("jquery=a:contains('#{text}'):last")
  end

end

When /^I fill in Course Identifier with "([^\"]*)" and "([^\"]*)"$/ do |code, number|
  type_each("jquery=div.KS-LUM-Section:contains('Course Identifier') input[type=text]:eq(0)", code)
  When "I select \"BIOL\" from an autocomplete list"
  selenium.type("jquery=div.KS-LUM-Section:contains('Course Identifier') input[type=text]:eq(1)", number)
end

When /^I fill in a learning objective$/ do
  When %{I click the link "Add new Learnging Objective"}
  selenium.type("css=input", "critical thinking")
end
When /^I fill in Start Date and End Date$/ do
  When "I pick a date 25 days in the future for \"Course active on\""
  When "I pick a date 125 days in the future for \"Course inactive on\""
end

When /^I pick a date (\d*)\/(\d*)\/(\d*) for "([^\"]*)"$/ do |year, month, day, locator|
  selenium.click(locator)
  target = Date.new(year.to_i, month.to_i, day.to_i)
  today = Date.today
  # find number of months between today and end date

  # determine if date is future or past
  if target > today
    # pick future date
    # find out number of months in future
    months = (target.month - today.month) + 12 * (target.year - today.year)
    if months > 0
      months.times { fire_mouse_events("jquery=div.html-face:contains('»')") }
    end
  elsif target < today
    # pick a past date
    # find number of months in past
    months = (today.month - target.month) + 12 * (today.year - target.year)
    if months > 0
      months.times { fire_mouse_events("jquery=div:contains('«'):last") }
    end
  end
  selenium.click("jquery=td.datePickerDay:contains('#{target.day.to_s}')")
end

When /^I pick a date (\d*) days? in the (past|future) for "([^\"]*)"$/ do |num, direction, label|
  direction == "future" ? target = Date.today + num.to_i.days : target = Date.today - num.to_i.days
  #When %{I pick a date #{target.year}/#{target.month}/#{target.day} for "#{label}"}
  locator = "jquery=div.ks-form-module-elements:contains('#{label}') input"
  When %{I pick a date #{target.year}/#{target.month}/#{target.day} for "#{locator}"}
end

When /^I enter in Contact Hours with "([^\"]*)" "([^\"]*)"$/ do |hours, per_type|
  When "I type in \"Contact Hours\" with \"3\""
  selenium.select("jquery=select:last", per_type)
end

When /^I submit the proposal$/ do
  selenium.click("jquery=div.KS-CutomDropDown-TitleLabel:contains('Workflow Actions')")
  selenium.click("jquery=div.KS-Basic-Menu-Clickable-Item-Label:contains('Submit Proposal')")
  When "I follow \"Submit\""
end

When /^I fill in the Proposal Title with "([^\"]*)"$/ do |text|
  When "I fill in a text area div labeled \"Proposal Title\" with \"#{text + Time.now.to_formatted_s(:number)}\""
end

When /^I log in with user "([^\"]*)" and password "([^\"]*)"$/ do |user, password|
  selenium.type "j_username", user
  selenium.type "j_password", password
  click_button
end
When /^I open the proposal "([^\"]*)"$/ do |title|
  locator = "jquery=tr:contains('#{title}'):last td:first a"
  selenium.click locator
  sleep 1
  selenium.select_window("LUM Application")
  selenium.wait_for_text("New Course", :timeout_in_seconds => 5)
end

# to test against iframe contents, we need to select it in selenium
When /^I select the Action List/ do
  locator = "jquery=iframe.KS-Action-List"
  selenium.wait_for_element locator
  selenium.select_frame locator
end

#When /^I switch to the Proposal$/ do
#
#end
When /^I approve the proposal$/ do
  selenium.click("jquery=div.KS-CutomDropDown-TitleLabel:contains('Workflow Actions')")
  selenium.click("jquery=div.KS-Basic-Menu-Clickable-Item-Label:contains('Approve Proposal')")
end
