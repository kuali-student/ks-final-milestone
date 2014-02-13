--KSENROLL-5868
UPDATE
    KSEN_TYPE
SET
    REF_OBJECT_URI='http://student.kuali.org/wsdl/atp/AtpInfo',
    SERVICE_URI='http://student.kuali.org/wsdl/atp/AtpService'
WHERE
    TYPE_KEY LIKE 'kuali.atp.type.%'
AND REF_OBJECT_URI = 'http://student.kuali.org/wsdl/atp/MilestoneInfo'
/
