UPDATE
    KSEN_LUI
SET
    KSEN_LUI.ATP_ID = 'kuali.atp.' || SUBSTR(KSEN_LUI.ATP_ID,1,4) ||
    CASE SUBSTR(ATP_ID,5,2)
        WHEN '08'
        THEN 'Fall'
        WHEN '01'
        THEN 'Spring'
        WHEN '07'
        THEN 'Summer2'
        WHEN '05'
        THEN 'Summer1'
        WHEN '12'
        THEN 'Winter'
        ELSE 'ERROR'
    END
WHERE
    LENGTH(KSEN_LUI.ATP_ID) = 6
/
