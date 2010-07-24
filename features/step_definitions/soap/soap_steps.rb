
# ==== :order!
#
# In case your service requires the tags to be in a specific order (parameterOrder), you have two
# options. The first is to specify your body as an XML string. The second is to specify the order
# through an additional array stored under the +:order!+ key.
#
#   { :name => "Eve", :id => 123, :order! => [:id, :name] }.to_soap_xml
#   # => "<id>123</id><name>Eve</name>"
#
# ==== :attributes!
#
# If you need attributes, you could either go with an XML string or add another hash under the
# +:attributes!+ key.
#
#   { :person => "Eve", :attributes! => { :person => { :id => 666 } } }.to_soap_xml
#   # => '<person id="666">Eve</person>'

Given /^(?:|I )am connected to the "([^\"]*)" service$/ do |service_name|
  @client = Savon::Client.new soap_path_to(service_name)
end

#When /^(?:|I )call the "([^\"]*)" action with params "([^\"]*)"$/ do |action_name, params|
#  @response = @client.instance_eval (action_name + build_action_params(params))
#end

When /^(?:|I )call the "([^\"]*)" action with params "([^\"]*)"$/ do |action_name, params|
  instance_eval("$p = {#{params}}")
  $p.merge!($input_hash) { |k,v1,v2| v1.merge(v2) }
  @response = @client.instance_eval(action_name + build_action_params)
end

When /^(?:|I )call the "([^\"]*)" action with params "([^\"]*)" and namespace "([^\"]*)"$/ do |action_name, params, namespace|
  instance_eval("$p = {#{params}}")
  $p.merge!($input_hash) { |k,v1,v2| v1.merge(v2) }
  @response = @client.instance_eval(action_name + build_action_params(namespace))
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

#def build_action_params(params)
#  "{|soap| soap.body={#{params}}}"
#end

def build_action_params(params = nil, namespace = nil)
  name_space = namespace ? "soap.namespace='#{namespace}';" : ""
  "{|soap| #{name_space}soap.body=$p;}"
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

Then /^response for the action "([^\"]*)" should contain "([^\"]*)"$/ do |action_name, value|
  puts @response.inspect
  @response.to_hash[action_name.to_sym][:return].should include( instance_eval("{#{value}}") )
end

Then /^add "([^\"]*)" from "([^\"]*)" to the input hash as "([^\"]*)"$/ do |output, action_name, input|
  #puts "Output: " + output
  instance_eval("@value = @response.to_hash['#{action_name}'.to_sym][:return]#{output}")
  instance_eval("$input_hash.merge!(#{input.gsub('!value', @value)})")
  puts @value
end

Then /^add value "([^\"]*)" from "([^\"]*)" to the input hash as "([^\"]*)"$/ do |output, action_name, input|
  #puts "Output: " + output
  #@value = @response.to_hash[action_name.to_sym][output.to_sym]
  #instance_eval("@value = @response.to_hash['#{action_name}'.to_sym]['#{output}'.to_sym]")
  instance_eval("@value = @response.to_hash['#{action_name}'.to_sym][:return]#{output}")
  #instance_eval("$input_hash.merge!(#{input.gsub('!value', @value)})")
  puts @value
  #puts @response.to_hash[:create_proposal_response][:return][:proposal_reference]
end

Then /^I clean the input hash$/ do
  $input_hash = {}
end
