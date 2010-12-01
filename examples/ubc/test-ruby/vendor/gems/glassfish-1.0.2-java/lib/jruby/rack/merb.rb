# Merb init file.
# Thanks to Yehuda Katz for coding help

#translate merb/Glassfish log levels

#load required files
require 'rubygems'
gem 'merb-core', '>= 0'
require 'merb-core'
require 'rack/handler/grizzly'
require 'rack/adapter/merb'
Merb::Rack::Adapter.register %w{grizzly}, :GrizzlyMerb

#Figure out the correct log level to use
case $glassfish_log_level
  when "FATAL" then log_level = :fatal
  when "ERROR" then log_level = :error
  when "WARN" then log_level = :warn
  when "INFO" then log_level = :info
  when "DEBUG" then log_level = :debug
end

# Set up the server and log stream, supress merb signal trapping
Merb.disable(:signals)

# Start merb
Merb.start({
   :environment => $glassfish_config.environment,
   :merb_root => $glassfish_config.appRoot,
   :adapter => "grizzly",
   :log_level => log_level,
   :path_prefix => $glassfish_config.contextRoot == "/"?
           nil:$glassfish_config.contextRoot
})
