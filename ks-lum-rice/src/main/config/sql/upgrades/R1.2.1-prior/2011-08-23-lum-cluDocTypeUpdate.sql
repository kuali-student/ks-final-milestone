-- Update KIM responsibilities to point to new parent clu doc
UPDATE
    KRIM_RSP_ATTR_DATA_T
SET
    ATTR_VAL='CluParentDocument'
WHERE
    ATTR_VAL='CluCreditCourseParentDocument'
AND KIM_ATTR_DEFN_ID='13'
/
