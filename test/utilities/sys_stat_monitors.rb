#!/usr/bin/env ruby

require 'optparse'
require 'yaml'

# Initialize
option = false
@config = {}
errors = [] # Gather arg validation errors


# Get cmd line options
optparse = OptionParser.new do |opts|
  
  # Banner
  opts.banner = "Usage: sys_stat_monitors.rb [OPTIONS]"
  
  # Definition of options
  opts.on('-h', '--help', 'Display help screen') do
    puts opts
    exit
  end
  
  # Config file
  @config[:config_file] = nil
  opts.on('-c', '--config FILE', 'path to yaml config file') do |file|
    @config[:config_file] = file
    errors.push("#{file} does not exist") unless(File.file?(@config[:config_file]))
    errors.push("#{file} does not appear to be a yaml file, must end in .yaml") unless(file =~ /\.yaml$/)
  end
  
  # Output config file
  @config[:output] = nil
  opts.on('-o', '--output FILE', 'path to config output') do |file|
    @config[:output] = file
  end
  
  # Log file
  @config[:log] = nil
  opts.on('-l', '--log FILE', 'path to log output') do |file|
    @config[:log] = file
  end

  # Enable debug
  @config[:debug] = false
  opts.on('-d', '--debug', 'enable debug logging') do
    @config[:debug] = true
  end
  
end

optparse.parse!


# METHODS

# Save off config
def save_config
  
  # Write yml file 
  yf = File.open(@config[:output], "w+")
  YAML.dump(@config, yf)
  yf.close
  
end


# Parse the passed in config file
def parse_config(config)
  @config = YAML.load_file(config)
end


# Start log, return File obj
def start_log(log)
  File.open(log, '+w')
end


# Start monitors
def start_monitors
  
  @config[:phases].each do |phase|
    
    info_msg("Launching phase 1 monitors...")
    info_msg(Time.now.to_s)
    
    `mem-stat.plx #{@config[:pid]} #{@config[:phases][phase][:interval]} #{@config[:phases][phase][:amount]} > #{@config[:base_log_name]}-phase#{phase}-mem_stats.log &`
    `sar -u -x #{@config[:pid]} #{@config[:phases][phase][:interval]} #{@config[:phases][phase][:amount]} > #{@config[:base_log_name]}-phase#{phase}-cpu_stats.log &`
    `net-mon.plx #{@config[:phases][phase][:interval]} #{@config[:phases][phase][:amount]} #{@config[:http_port]} > #{@config[:base_log_name]}-phase#{phase}-cpu_stats.log`
    
  end
  
end

# Print messages to log and stdout
def info_msg(msg)
  @log.puts(msg)
  puts msg
end


# Build the config hash
def generate_config
  
  # Config output
  if(@config[:output].nil?)
    print "Config file name: "
    @config[:output] = gets.chomp
  end
  
  if(@config[:log].nil?)
    print "Log file name: "
    @config[:log] = gets.chomp
  end
  
  # PID
  print "PID you want to monitor? "
  @config[:pid] = gets.chomp
  
  # HTTP Port
  print "HTTP port you want to monitor? "
  @config[:http_port] = gets.chomp
  
  # Base log name
  print "Base log file name? "
  @config[:base_log_name] = gets.chomp
  
  # Number of phases
  print "Number of test phases? "
  @config[:num_phases] = gets.chomp
  
  # Build phases
  phase = 0
  begin
    phase += 1
    puts "Configuring phase #{phase}..."
    
    @config[:phases] = {}
    @config[:phases][phase] = {}
    
    print "Snapshot interval(secs)? "
    @config[:phases][phase][:interval] = gets.chomp
    
    print "Number of snapshots? "
    @config[:phases][phase][:amount] = gets.chomp
    
  end while(phase <= @config[:num_phases])
  
end




# MAIN

# If we have a passed in config, parse it, else build it
if(@config[:config_file].nil?)
  generate_config
  save_config
else
  parse_config(@config[:config_file])
end

@log = start_log(@config[:log])
start_monitors







