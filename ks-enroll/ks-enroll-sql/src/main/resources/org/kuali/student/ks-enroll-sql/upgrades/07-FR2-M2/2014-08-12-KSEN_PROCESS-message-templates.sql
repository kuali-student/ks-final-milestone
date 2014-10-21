--KSENROLL-14070 KSENROLL-13669 added template to instruction so messages can be more flexible
UPDATE
    KSEN_PROCESS_INSTRN
SET
    MESG_FORMATTED='"messageKey":"kuali.lpr.trans.message.course.not.open","asOfDate":"$asOfDate"'
WHERE
    ID='0f6e519c-7552-486b-aa0f-30595fa4d2ac'
/
