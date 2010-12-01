Given /^the following searches:$/ do |searches|
  Search.create!(searches.hashes)
end

When /^I delete the (\d+)(?:st|nd|rd|th) search$/ do |pos|
  visit searches_url
  within("table tr:nth-child(#{pos.to_i+1})") do
    click_link "Destroy"
  end
end

Then /^I should see the following searches:$/ do |expected_searches_table|
  expected_searches_table.diff!(tableish('table tr', 'td,th'))
end

When /^I click the Search button$/ do
  selenium.click("jquery=a.ks-button-primary:contains('Search')")
end