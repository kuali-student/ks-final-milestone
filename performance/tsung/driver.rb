#!/usr/bin/env ruby
# 
# == Synopsis
#
# tsung-driver: used to generate tsung XML from api test branch and initiate load
#
# == Usage
#
# tsung-driver.rb [OPTIONS] ... SUITE_FILE_NAME
#
# -h, --help:
#     show help
#
# -c, --config [file.xml]:
#     path to xml config file for everything after <tsung> and before <sessions>
#
# -d, --debug:
#     enable debug logging
#
# -x, --execute:
#     start the load after generating the XML. If not specified, only XML will be generated.
#
# SUITE_FILE_NAME:
#     suite file name containg list of tests. File must exist in suites directory
#
# == Examples
#
#

require 'getoptlong'
require 'rdoc/usage'


# Interactive config setup
def setup
  puts "Let's setup your test run config..."
  
  
  #
  # Clients
  #
  print "What clients are you driving your tests from? [hostname1,hostname2,...] "
  clients = gets.chomp!
  while(clients !~ /^(\w+)(\,\w+)*?$/) # validate format
    print "Please use correct csv format [hostname1,hostname2,...] "
    clients = gets.chomp!
  end
  
  @@config[:clients] = clients.split(/,/)
  
  
  #
  # Servers
  #
  print "What servers do you want to test? [hostname1:port,hostname2:port,...] "
  servers = gets.chomp!
  while(servers !~ /^(\w+:\d+)(\,\w+:\d+)*?$/) # validate format
    print "Please use correct format [hostname1:port,hostname2:port,...] "
    servers = gets.chomp!
  end
  
  @@config[:servers] = servers.split(/,/)
  
  
  #
  # Phases
  #
  @@config[:phases] = {}
  puts "Let's setup your test scenario or phases, you can define multiple phases"
  phase = 0
  begin
    phase += 1
    @@config[:phases][phase] = {}
    
    print "Phase #{phase}: How many minutes? "
    min_duration = gets.chomp!
    while(min_duration =~ /\D/) # validate only digits
      print "Please specify only numeric entry: "
      min_duration = gets.chomp!
    end
    
    @@config[:phases][phase][:duration] = min_duration
    @@config[:phases][phase][:unit] = 'minute' # hardcoded for now for simplicity
    
    print "Do you want to add another phase? [y/n] "
    add_phase = gets.chomp!
  end while (add_phase == 'y')
  
  
  #
  # User Agent
  #
  puts "Let's setup what user agents you'd like to test with"
  probability_total = 0
  @@config[:agents] = {}
  agent_list = []
  
  File.open("agent_list", "r") do |agent_file|
    while(line = agent_file.gets)
      next if(line =~ /^\#/) # skip if it's a comment
      agent_list << line.chomp
    end
  end
  
  puts "Here are the agents you can specify..."
  option = 0
  agent_list.each {|agent| puts "#{option+=1}: #{agent}"}
  available_perc = 100
  
  # Loop over collecting agents until we have 100%
  begin
    print "Specify the number of the agent you'd like to use: "
    agent_num = gets.chomp
    while(agent_num =~ /\D/) # validate only digits
      print "Please specify only numeric entry: "
      agent_num = gets.chomp
    end
    
    print "Specify the probability that this agent will be used: [1-100] "
    agent_prob = gets.chomp
    
    # validate only digits and doesn't exceed available probability
    while(agent_prob =~ /\D/ || agent_prob.to_i > available_perc) 
      print "Please specify only numeric entry that doesn't exceed #{available_perc}: "
      agent_prob = gets.chomp
    end
    
    # Subtract specified probability from remaining available
    available_perc-=agent_prob.to_i
    @@config[:agents][agent_list[agent_num.to_i - 1]] = agent_prob
  
    # See if we need another agent
    if(available_perc > 0)
      puts "You have #{available_perc}% remaining in agent probability. Please add another agent."
      add_agent = 'y'
    else
      add_agent = 'n'
    end
    
  end while (add_agent == 'y')
  
end

########

# Get cmd line options
opts = GetoptLong.new(
  [ '--help', '-h', GetoptLong::NO_ARGUMENT ],
  [ '--config', '-c', GetoptLong::REQUIRED_ARGUMENT ],
  [ '--debug', '-d', GetoptLong::NO_ARGUMENT ],
  [ '--execute', '-x', GetoptLong::NO_ARGUMENT ]
)

errors = [] # Gather arg validation errors

# Initialize true/false vars
@@config = {}
@@config[:debug] = false
@@config[:execute] = false

opts.each do |opt, arg|
  case opt
    when '--help'
      RDoc::usage
    when '--config'
      @@config[:intro_xml] = arg
      errors.push("#{opt} must be numeric") unless(File.file?(@@config[intro_xml]))
    when '--debug'
      @@config[:debug] = true
    when '--execute'
      @@config[:execute] = true
  end
end

if(ARGV.length != 1)
  puts "Must specify test suite"
  exit 0
end

@@config[:suite] = ARGV.shift

# Validate suite file in suites dir
# Should use env var for suite dir
# Exit if we have problems
if(!errors.empty?)
  errors.each { |err| puts "Error> #{err}" }
  exit
end

# If no options are passed then we'll use interactive setup
setup if(!opts.get)

# Default strings
puts @@config.inspect