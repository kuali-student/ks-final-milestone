UPDATE
    KSEN_LUI
SET
    LUI_STATE='kuali.lui.format.offering.state.offered'
WHERE
    LUI_TYPE='kuali.lui.type.course.format.offering'
AND LUI_STATE ='kuali.lui.activity.offering.state.offered'
/
