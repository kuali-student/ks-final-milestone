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

puts ""
puts "Cmd: java -jar #{ENV['KS_LOADER_RESOURCES']}/ks-loader.jar atp #{ENV['KS_LOADER_RESOURCES']}/ATP Calendar.xls #{url}"
out=`java -jar #{ENV['KS_LOADER_RESOURCES']}/ks-loader.jar atp "#{ENV['KS_LOADER_RESOURCES']}/ATP Calendar.xls" #{url}`
puts "ATP load:"
puts out

puts "Cmd: java -jar #{ENV['KS_LOADER_RESOURCES']}/ks-loader.jar organization #{ENV['KS_LOADER_RESOURCES']}/NewReferenceOrganizations.xls #{url}"
out=`java -jar #{ENV['KS_LOADER_RESOURCES']}/ks-loader.jar organization "#{ENV['KS_LOADER_RESOURCES']}/NewReferenceOrganizations.xls" #{url}`
puts "Org load:"
puts out

puts "Cmd: java -jar #{ENV['KS_LOADER_RESOURCES']}/ks-loader.jar organization #{ENV['KS_LOADER_RESOURCES']}/Organizations for BSCI program.xls #{url}"
out=`java -jar #{ENV['KS_LOADER_RESOURCES']}/ks-loader.jar organization "#{ENV['KS_LOADER_RESOURCES']}/Organizations for BSCI program.xls" #{url}`
puts "Org load:"
puts out

#puts "Cmd: java -jar #{ENV['KS_LOADER_RESOURCES']}/ks-loader.jar orgorgrelation #{ENV['KS_LOADER_RESOURCES']}/Organizations.xls #{url}"
#out=`java -jar #{ENV['KS_LOADER_RESOURCES']}/ks-loader.jar orgorgrelation "#{ENV['KS_LOADER_RESOURCES']}/Organizations.xls" #{url}`
#puts "Org/Org load:"
#puts out

#puts "Cmd: java -jar #{ENV['KS_LOADER_RESOURCES']}/ks-loader.jar enumeration #{ENV['KS_LOADER_RESOURCES']}/NewReferenceSubjectAreaEnumerations.xls #{url}"
#out=`java -jar #{ENV['KS_LOADER_RESOURCES']}/ks-loader.jar enumeration "#{ENV['KS_LOADER_RESOURCES']}/NewReferenceSubjectAreaEnumerations.xls" #{url}`
#puts "Org load:"
#puts out

puts ""
puts "Cmd: java -jar #{ENV['KS_LOADER_RESOURCES']}/ks-loader.jar course #{ENV['KS_LOADER_RESOURCES']}/ReferenceCourses.xls #{url}"
out=`java -jar #{ENV['KS_LOADER_RESOURCES']}/ks-loader.jar course "#{ENV['KS_LOADER_RESOURCES']}/ReferenceCourses.xls" #{url}`
puts "Test load:"
puts out

puts ""
puts "Cmd: java -jar #{ENV['KS_LOADER_RESOURCES']}/ks-loader.jar test #{ENV['KS_LOADER_RESOURCES']}/StandardizedTests.xls #{url}"
out=`java -jar #{ENV['KS_LOADER_RESOURCES']}/ks-loader.jar test "#{ENV['KS_LOADER_RESOURCES']}/StandardizedTests.xls" #{url}`
puts "Test load:"
puts out

puts ""
puts "Cmd: java -jar #{ENV['KS_LOADER_RESOURCES']}/ks-loader.jar program #{ENV['KS_LOADER_RESOURCES']}/ReferencePrograms.xls #{url}"
out=`java -jar #{ENV['KS_LOADER_RESOURCES']}/ks-loader.jar program "#{ENV['KS_LOADER_RESOURCES']}/ReferencePrograms.xls" #{url}`
puts "Test load:"
puts out






