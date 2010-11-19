#!/usr/bin/env ruby

# This sets up and run's the ks-loader
# https://test.kuali.org/svn/student/tools/ks-loader

environment = ARGV[0]
if(environment != 'dev' and
   environment != 'staging')
  puts "Need to pass argument, either: dev or staging"
  exit 1;
end

url = (environment == 'staging' ? ENV['STAGING_URL'] : ENV['DEV_URL'])

# Make sure we have the latest data
out=`svn up /root/repos/student/tools/ks-loader`
puts out

# Run ks loader
puts "Cmd: java -jar ~/ks-loader.jar organization #{ENV['KS_LOADER_RESOURCES']}/Organizations for BSCI program.xls #{url}"
out=`java -jar ~/ks-loader.jar organization "#{ENV['KS_LOADER_RESOURCES']}/Organizations for BSCI program.xls" #{url}`
puts "Org load:"
puts out

puts ""
puts "Cmd: java -jar ~/ks-loader.jar course #{ENV['KS_LOADER_RESOURCES']}/Courses for BSCI program.xls #{url}"
out=`java -jar ~/ks-loader.jar course "#{ENV['KS_LOADER_RESOURCES']}/Courses for BSCI program.xls" #{url}`
puts "Course load:"
puts out

puts ""
puts "Cmd: java -jar ~/ks-loader.jar atp #{ENV['KS_LOADER_RESOURCES']}/ATP Calendar.xls #{url}"
out=`java -jar ~/ks-loader.jar atp "#{ENV['KS_LOADER_RESOURCES']}/ATP Calendar.xls" #{url}`
puts "ATP load:"
puts out
