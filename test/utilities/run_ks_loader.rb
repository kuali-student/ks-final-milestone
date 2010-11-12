# This sets up and run's the ks-loader
# https://test.kuali.org/svn/student/tools/ks-loader

environment = ARGV[0]
if(environment !~ /dev|staging/)
  puts "Need to pass argument, either: dev or staging"
  exit 1;
end

url = (environment == 'staging' ? ENV['STAGING_URL'] : ENV['DEV_URL'])

# Make sure we have the latest data
`svn up /root/repos/student/tools/ks-loader`

# Run ks loader
out=`java -jar ~/ks-loader.jar organization "#{ENV['KS_LOADER_RESOURCES']}/Organizations for BSCI program.xls" #{url}`
# error check
out=`java -jar ~/ks-loader.jar course "#{ENV['KS_LOADER_RESOURCES']}/Courses for BSCI program.xls" #{url}`
out=`java -jar ~/ks-loader.jar atp "#{ENV['KS_LOADER_RESOURCES']}/ATP Calendar.xls" #{url}`