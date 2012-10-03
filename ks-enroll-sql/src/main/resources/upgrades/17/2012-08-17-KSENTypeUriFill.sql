UPDATE KSEN_TYPE
SET REF_OBJECT_URI='http://student.kuali.org/wsdl/lpr/LprInfo'
WHERE TYPE_KEY like 'kuali.lpr.%' AND TYPE_KEY not like  'kuali.lpr.trans.%'
/

UPDATE KSEN_TYPE
SET REF_OBJECT_URI='http://student.kuali.org/wsdl/lpr/LprTransactionInfo'
WHERE TYPE_KEY like 'kuali.lpr.trans%' AND TYPE_KEY not like  'kuali.lpr.trans.item%'
/

UPDATE KSEN_TYPE
SET REF_OBJECT_URI='http://student.kuali.org/wsdl/lpr/LprTransactionItemInfo'
WHERE TYPE_KEY like 'kuali.lpr.trans.item%'
/

UPDATE KSEN_TYPE
SET REF_OBJECT_URI='http://student.kuali.org/wsdl/lrc/ResultScaleInfo'
WHERE TYPE_KEY like 'kuali.result.scale%'
/

UPDATE KSEN_TYPE
SET REF_OBJECT_URI='http://student.kuali.org/wsdl/lrc/ResultValueInfo'
WHERE TYPE_KEY like 'kuali.result.value.type%'
/

UPDATE KSEN_TYPE
SET REF_OBJECT_URI='http://student.kuali.org/wsdl/lrc/ResultValuesGroupInfo'
WHERE TYPE_KEY like 'kuali.result.values.group%'
/

UPDATE KSEN_TYPE
SET REF_OBJECT_URI='http://student.kuali.org/wsdl/lrc/ResultValueRangeInfo'
WHERE TYPE_KEY like 'kuali.result.value.range%'
/

UPDATE KSEN_TYPE
SET SERVICE_URI='http://student.kuali.org/wsdl/appointment/AppointmentService'
WHERE TYPE_KEY like 'kuali.appointment%'
/

UPDATE KSEN_TYPE
SET SERVICE_URI='http://student.kuali.org/wsdl/atp/AtpService'
WHERE TYPE_KEY like 'kuali.atp%'
/

UPDATE KSEN_TYPE
SET SERVICE_URI='http://student.kuali.org/wsdl/lu/LuService'
WHERE TYPE_KEY like 'kuali.lu%' AND TYPE_KEY not like  'kuali.lui%'
/

UPDATE KSEN_TYPE
SET SERVICE_URI='http://student.kuali.org/wsdl/lui/LuiService'
WHERE TYPE_KEY like 'kuali.lui%'
/

UPDATE KSEN_TYPE
SET SERVICE_URI='http://student.kuali.org/wsdl/hold/HoldService'
WHERE TYPE_KEY like 'kuali.hold%'
/

UPDATE KSEN_TYPE
SET SERVICE_URI='http://student.kuali.org/wsdl/lpr/LprService'
WHERE TYPE_KEY like 'kuali.lpr%'
/

UPDATE KSEN_TYPE
SET SERVICE_URI='http://student.kuali.org/wsdl/population/PopulationService'
WHERE TYPE_KEY like 'kuali.population%'
/

UPDATE KSEN_TYPE
SET SERVICE_URI='http://student.kuali.org/wsdl/scheduling/SchedulingService'
WHERE TYPE_KEY like 'kuali.scheduling%'
/

UPDATE KSEN_TYPE
SET SERVICE_URI='http://student.kuali.org/wsdl/type/TypeService'
WHERE TYPE_KEY like 'kuali.type%'
/

UPDATE KSEN_TYPE
SET SERVICE_URI='http://student.kuali.org/wsdl/lrc/LrcService'
WHERE TYPE_KEY like 'kuali.result%'
/

UPDATE KSEN_TYPE
SET SERVICE_URI='http://student.kuali.org/wsdl/atp/AtpService'
WHERE TYPE_KEY like 'kuali.milestone%'
/

UPDATE KSEN_TYPE
SET SERVICE_URI='http://student.kuali.org/wsdl/courseOfferingSet/CourseOfferingSetService'
WHERE TYPE_KEY like 'kuali.soc%'
/