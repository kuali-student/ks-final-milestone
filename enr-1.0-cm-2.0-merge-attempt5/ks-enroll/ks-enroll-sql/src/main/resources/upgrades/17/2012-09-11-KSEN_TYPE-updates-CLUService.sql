-- Set all LuService to CluService 
update KSEN_TYPE set SERVICE_URI = 'http://student.kuali.org/wsdl/clu/CluService' where SERVICE_URI = 'http://student.kuali.org/wsdl/lu/LuService'
/

