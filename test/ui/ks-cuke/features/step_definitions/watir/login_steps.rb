Given /^(?:|I )am loged in as "([^\"]*)" with the password "([^\"]*)"$/ do |username, password|
  @browser.goto path_to("the kuali homepage")
  @browser.text_field(:name, "j_username").set(username)
  @browser.text_field(:name, "j_password").set(password)
  @browser.button(:name, "submit").click
end