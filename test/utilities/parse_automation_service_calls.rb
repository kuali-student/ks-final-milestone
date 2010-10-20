#!/usr/bin/env ruby

# Pass into it the xml file generated for tsung consumption

if(ARGV.size < 1)
  puts "You must pass the path to the automation log xml file"
  exit 1
end

auto_file = ARGV[0]
services = {} # Hold service breakdown

# File handles
rf = File.open(auto_file)

while(line = rf.gets)
  #contents='5|0|6|http://appserv-1.ks.kuali.net:80/ks-standalone/org.kuali.student.lum.lu.ui.main.LUMMain/|FD960014C4FBDB0F0848F901314B9F4E|org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService|getWorkflowIdFromDataId|
  if(line =~ /contents=\'[^http]+[^|]+\|[^|]+\|([^|]+)\|([^|]+)/)
    service = $1
    method  = $2
    #puts "Service: #{service}"
    
    if(!services[service])
      services[service] = {} 
      services[service]['number'] = 0
      services[service]['methods'] = {}
    end
    
    services[service]['number'] += 1
    services[service]['methods'][method] = 0 if(!services[service]['methods'][method])
    services[service]['methods'][method] += 1
  end
  
end

#puts services.inspect

services.each_key do |service|
  
  puts "Service: #{service}"
  puts "-----------------------------------------"
  
  services[service]['methods'].each_pair do |method, number|
    puts sprintf("%25s => %4d", method, number)
  end
  
  puts ""
  
end