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

    if selenium.is_element_present(locator="jquery=div.KS-LUM-Section:contains('#{label}'):last input[type='text']")
    elsif selenium.is_element_present(locator="jquery=div.ks-form-module:contains('#{label}'):last input[type='text']")
    end
    selenium.type(locator, text)
end

When /^I fill in the "([^\"]*)" textarea section with "([^\"]*)"$/ do |label, text|
    if selenium.is_element_present(locator = "jquery=div.KS-LUM-Section:contains('#{label}'):last textarea")
    elsif selenium.is_element_present(locator = "jquery=div.ks-form-module:contains('#{label}'):last textarea") 
    end
    selenium.type(locator, text)
end

When /^I select "([^\"]*)" from an autocomplete list$/ do |item|
  locator = "jquery=td[role=menuitem]:contains('#{item}')"
  selenium.wait_for_element locator, :timeout_in_seconds => 10
  selenium.click(locator)
  #fire_mouse_click_events locator
end

When /^I choose "([^\"]*)" from "([^\"]*)"$/ do |option, label|
  selenium.select("jquery=div.KS-Horizontal-Block-Flow:contains('#{label}'):last select", option)
end

When /^I type in "([^\"]*)" with "([^\"]*)"$/ do |field_identifier, text|
  #locator = "jquery=div.ks-form-module-elements:contains('#{label}') input[type='text']:last"
  locator = "webrat=#{field_identifier}"
  selenium.wait_for_element locator, :timeout_in_seconds => 10
  selenium.focus locator
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
  elsif selenium.is_element_present("jquery=div.KS-Basic-Menu-Clickable-Item-Label:contains('#{text}')")
      selenium.click("jquery=div.KS-Basic-Menu-Clickable-Item-Label:contains('#{text}')")
  else
    selenium.click("jquery=a:contains('#{text}'):last")
  end

end

When /^I fill in Course Identifier with "([^\"]*)" and "([^\"]*)"$/ do |code, number|
  type_each("jquery=table.course-subjectArea input[type='text']", code)
  When "I select \"#{code}\" from an autocomplete list"
  fill_in("gwt-uid-14", :with => number)
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
      months.times { fire_mouse_click_events("jquery=div.html-face:contains('»')") }
    end
  elsif target < today
    # pick a past date
    # find number of months in past
    months = (today.month - target.month) + 12 * (today.year - target.year)
    if months > 0
      months.times { fire_mouse_click_events("jquery=div:contains('«'):last") }
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
  locator_workflow = "jquery=div.KS-CutomDropDown-TitleLabel:contains('Workflow Actions'):first"
  locator_submit = "jquery=div.KS-Basic-Menu-Clickable-Item-Label:contains('Submit Proposal')"
  selenium.window_focus
  selenium.wait_for_element(locator_workflow, 10000)
  selenium.click(locator_workflow)
  selenium.wait_for_element(locator_submit, 10000)
  selenium.click(locator_submit, 10000)
  When "I follow \"Submit\""
end

When /^I fill in the Proposal Title with "([^\"]*)"$/ do |text|
  When "I fill in a text area div labeled \"Proposal Title\" with \"#{text} - #{Time.now.to_formatted_s(:number)}\""
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
  selenium.close
  sleep 1
  selenium.select_window("LUM Application")
  #selenium.wait_for_text("New Course", :timeout_in_seconds => 5)
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

When /^I logout from "([^\"]*)"$/ do |user|
  When "I click the link \"#{user}\""
  selenium.click("jquery=div.KS-Basic-Menu-Item-Label:contains('Logout')")
  When "I wait for text \"Password\""
end
When /^I click My Action List$/ do
  selenium.click("jquery=div.KS-CutomDropDown-TitleLabel:contains('Home')")
  selenium.click("jquery=div.KS-Basic-Menu-Clickable-Item-Label:contains('My Action List')")
end
When /^I open proposal Id "([^\"]*)"$/ do |id|
  When "I follow \"#{id}\""
  sleep 1
  selenium.select_window("LUM Application")
end
When /^I switch to opened proposal$/ do
  sleep 1
  selenium.close
  sleep 1
  selenium.select_window("LUM Application")
end
When /^I choose "([^\"]*)" from a select$/ do |option| 
  selenium.select("jquery=select.KS-Dropdown:contains('#{option}')", "label=#{option}")
end
When /^I click the div "([^\"]*)"$/ do |id|
  selenium.click(id)
end
When /^I expand the Future Actions Requests$/ do
  locator = "jquery=tr:contains('Future Action Requests'):last a:has('img')"
  selenium.wait_for_element locator, :timeout_in_seconds => 10
  selenium.click(locator)
end

When /^I add to list$/ do
  locator = "css=a:contains('Add to list')"
  selenium.wait_for_element locator, :timeout_in_seconds => 5
  selenium.click locator
end

When /^I add "([^\"]*)" to "([^\"]*)" by typing "([^\"]*)"$/ do |item, field, text_to_type|
    #selenium.focus("webrat=#{field}")
    #selenium.select_window(nil)
    selenium.window_focus
    When "I type in \"#{field}\" with \"#{text_to_type}\""
#    When "I wait 1 seconds"
    When "I select \"#{item}\" from an autocomplete list"
#    When "I wait 1 seconds"

     begin       
       When "I add to list"
     rescue Exception => e
       When "I clear \"#{field}\""
       When "I type in \"#{field}\" with \"#{item}\""
       When "I select \"#{item}\" from an autocomplete list"
       When "I add to list"
     end

end
When /^I clear "([^\"]*)"$/ do |field|
       locator = "webrat=#{field}"
       id = selenium.get_attribute("#{locator}@id")
       selenium.get_eval("window.document.getElementById('#{id}').value = ''")
end