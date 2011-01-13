require 'uri'
require 'cgi'
require File.expand_path(File.join(File.dirname(__FILE__), "..", "support", "paths"))

When /^(?:|I )press "([^\"]*)" with Selenium$/ do |button|
  selenium.click("jquery=button:contains('#{button}'):last")
end
When /^I wait for the page to load$/ do
  selenium.wait_for_page(5)
end

When /^take a screenshot$/ do
  filename = "#{Dir.pwd}/screenshot-#{Time.now.to_i}.png"
  if selenium.chrome_backend?
    selenium.capture_entire_page_screenshot(filename, '')
  else
    selenium.capture_screenshot(filename)
  end
end

When /^take a desktop screenshot$/ do
  filename = "#{Dir.pwd}/screenshot-#{Time.now.to_i}.png"
  selenium.capture_screenshot(filename)
end

When /^I wait (.*) seconds?$/ do |seconds|
  sleep seconds.to_i
end

def type_each(locator, text)
  text.each_char do |c|
    selenium.key_down(locator, c)
    selenium.key_press(locator, c)
    selenium.key_up(locator, c)
  end
end

def fire_mouse_click_events(locator)
  selenium.mouse_over(locator)
  selenium.mouse_down(locator)
  selenium.mouse_up(locator)
  selenium.mouse_out(locator)
end

When /^I wait for text "([^\"]*)"$/ do |text|
  selenium.wait_for_text(text)
end

When /^I wait for no text "([^\"]*)"$/ do |text|
  selenium.wait_for_no_text(text)
end

#When /^I click the link with the exact text "([^\"]*)"$/ do |text|
#  selenium.click
#end
When /^I goto path "([^\"]*)"$/ do |path|
  visit path
end