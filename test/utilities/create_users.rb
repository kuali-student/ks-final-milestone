#!/usr/bin/env ruby

require 'optparse'
require "rexml/document"
include REXML

# Defaults
campus_code = 'EA'
affiliation = 'AFLT'
active = 'True'
file_path = "create_users_#{Time.now.to_i}.xml"

# Get cmd line options
optparse = OptionParser.new do |opts|
  
  # Banner
  opts.banner = "Usage: create_users.rb [OPTIONS] ... NUM_USERS USER_BASE_NAME"
  
  # Definition of options
  opts.on('-h', '--help', 'Display help screen') do
    puts opts
    exit
  end
  
  # Campus code
  opts.on('-c', '--campus_code CODE', 'Campus Code for user, default: EA') do |code|
    campus_code = code
  end
  
  # Affiliation
  opts.on('-a', '--affiliation AFF', 'Affiliation Code for user, default: AFLT') do |aff|
    affiliation = aff
  end
  
  # Active
  opts.on('-n', '--notactive', 'Set if you want user set to not active') do
    active = 'False'
  end
  
  # Output file
  opts.on('-o', '--output FILE', 'File for output xml') do |file|
    file_path = file
  end
  
end

optparse.parse!

if(ARGV.length != 2)
  puts "Must specify NUM_USERS and USER_BASE_NAME"
  puts "use -h option to see usage"
  exit 2
end

num_users = ARGV.shift.to_i
user_base = ARGV.shift

# Write the XML
xml_doc = Document.new("<?xml version='1.0?>")

data_opts = {
  "xmlns" => "ns:workflow",
  "xmlns:xsi" => "http://www.w3.org/2001/XMLSchema-instance",
  "xsi:schemaLocation" => "ns:workflow resource:WorkflowData"
}

data_element = xml_doc.add_element('data', data_opts)

users_opts = {
  "xmlns" => "ns:workflow/User",
  "xsi:schemaLocation" => "ns:workflow/User resource:User"
}

users_element = data_element.add_element('users', users_opts)

user_element = []
for num in 1..num_users
  
  user_element << users_element.add_element('user')
  user_element[num-1].add_element('principalId').add_text("#{user_base}_#{num}")
  user_element[num-1].add_element('password').add_text("#{user_base}_#{num}")
  user_element[num-1].add_element('empId').add_text("#{user_base}_#{num}-empId")
  user_element[num-1].add_element('principalName').add_text("#{user_base}_#{num}")
  user_element[num-1].add_element('givenName').add_text("#{user_base}_#{num}")
  user_element[num-1].add_element('lastName').add_text("#{user_base}_#{num}")
  user_element[num-1].add_element('campusCode').add_text(campus_code)
  user_element[num-1].add_element('affiliationTypeCode').add_text(affiliation)
  user_element[num-1].add_element('active').add_text(active)
  
end

file = File.open(file_path, 'w')
xml_doc.write(file, 2) # Write xml to file
file.close