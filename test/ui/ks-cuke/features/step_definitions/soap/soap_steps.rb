Given /^(?:|I )am connected to the "([^\"]*)" service$/ do |service_name|
  @client = Savon::Client.new soap_path_to(service_name)
end

When /^(?:|I )call the "([^\"]*)" action with params "([^\"]*)"$/ do |action_name, params|
  @response = @client.instance_eval (action_name + build_action_params(params))
end

When /^(?:|I )call the "([^\"]*)" action with xml "([^\"]*)"$/ do |action_name, xml|
  @response = @client.instance_eval (action_name + build_xml_route(xml))
end

Then /^response parameter "([^\"]*)" for the action "([^\"]*)" should contain "([^\"]*)"$/ do |response_parameter, action_name, value|
  @response.to_hash[action_name.to_sym][response_parameter.to_sym].should == value
end

Then /^response should match xml "([^\"]*)"$/ do |xml|
  @response.to_xml.should == xml_to_s(xml)
end

Then /^show me the output in the console$/ do
  puts @response.to_xml
end


def build_action_params(params)
  "{|soap| soap.body={#{params}}}"
end

def xml_to_s(file)
  body = ''
  f = File.open(File.expand_path(File.join(File.dirname(__FILE__), "../../", "feature_definitions", "soap", "input_xml", "#{file}.xml")), "r") 
  f.each_line do |line|
    body += line
  end
  body
end

def build_xml_route(file)
  "{|soap| soap.body='#{xml_to_s(file)}'}"
end