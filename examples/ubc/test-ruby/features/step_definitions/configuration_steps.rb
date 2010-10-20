Given /^I have a new campus$/ do
#  Enumeration.exists?("1000000").should be_true

end

Then /^I should see the configured campus$/ do
  assert_contain(CDM_CONFIGURED)
end
