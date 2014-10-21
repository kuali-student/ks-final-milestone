--KSENROLL-13669 -- on a reg date check failure, inform user of the relevant date
UPDATE
    KSEN_PROCESS_INSTRN
SET
    MESG_FORMATTED='"messageKey":"kuali.lpr.trans.message.course.not.open","actionDate":"$userActionDate","startDate":"$scheduleAdjustmentStart","endDate":"$scheduleAdjustmentEnd"'
WHERE
    ID in ('0f6e519c-7552-486b-aa0f-30595fa4d2ac', '01CC67C7-6A46-82C3-E050-007F010105C1', '01CC67C7-6A4E-82C3-E050-007F010105C1', '01CC67C7-6A51-82C3-E050-007F010105C1', '01CC67C7-6A56-82C3-E050-007F010105C1')
/
