#!/usr/bin/env ruby

# 
# == Synopsis
#
# tsung-driver: used to generate tsung XML from api test branch and initiate load
#
# == Usage
#
# driver.rb [OPTIONS] ... SUITE_FILE_NAME APP_CONTEXT
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
# -o, --output:
#     filename(path) that you want to use for output XML
#
# SUITE_FILE_NAME:
#     suite file name containg list of tests. File must exist in suites directory
#
# APP_CONTEXT:
#     context for the app , e.g. ks-embedded
#
# == Examples
#
#

require 'optparse'
require 'rdoc/usage'
require File.dirname(__FILE__) + '/lib/common.rb'
require File.dirname(__FILE__) + '/lib/config.rb'
require 'drb'
include Common

########

errors = [] # Gather arg validation errors

# Initialize true/false vars
config = AutoConfig.new
option = false

# Get cmd line options
optparse = OptionParser.new do |opts|
  
  # Banner
  opts.banner = "Usage: driver.rb [OPTIONS] ... SUITE_FILE_NAME APP_CONTEXT"
  
  # Definition of options
  opts.on('-h', '--help', 'Display help screen') do
    puts opts
    exit
  end
  
  # Config file
  opts.on('-c', '--config FILE', 'path to xml config file for everything after <tsung> and before <sessions>') do |file|
    config.intro_xml = file
    errors.push("#{opt} does not exist") unless(File.file?(config.intro_xml))
    option = true
  end
  
  # Output xml file
  opts.on('-o', '--output FILE', 'path to xml output') do |file|
    config.output = file
  end
  
  # Log file
  opts.on('-l', '--log FILE', 'path to log output from this framework') do |file|
    config.log_path = file
  end
  
  # Execute tests after generating xml
  opts.on('-x', '--execute', 'start the load after generating the XML') do
    config.execute = true
  end
  
  # Enable debug
  opts.on('-d', '--debug', 'enable debug logging') do
    config.debug = true
  end
  
  
end

optparse.parse!

# Need to collect drb port
config.drb_port = ENV['DRB_PORT']
errors << "Must specify DRB_PORT in shell environment" unless(!config.drb_port.nil?)

# Exit if we have problems
if(!errors.empty?)
  errors.each { |err| puts "#{err}" }
  exit
end

if(ARGV.length != 2)
  puts "Must specify test suite and app context"
  exit 2
end

# Validate suite file in suites dir
config.suite = ARGV.shift
config.context = ARGV.shift
exit 3 if(!config.validate_suite)
config.parse_suite

# If no options are passed then we'll use interactive setup
config.initialize_logs
config.setup if(!option)

# write initial config XML here before we get to tests if no -c option was secified
config.initialize_output_xml


config.log.debug_msg("----------CONFIG----------")
config.log.debug_msg(config.inspect)
config.log.debug_msg("--------------------------")
#
# EXECUTE TESTS
#

# Fire up service for tests to read config information from
DRb.start_service "druby://localhost:#{config.drb_port}", config
config.log.debug_msg("DRB service running at #{DRb.uri}")

begin
  
  config.tests.each_key do |test|
    puts `#{config.test_dir}/#{test}`
  end
  
end

config.xml_obj.write($stdout, 2) if(config.debug)
config.xml_obj.write(config.xml_writer.file, 2)

#trap("INT") {DRb.stop_service}
#DRb.thread.join
DRb.stop_service