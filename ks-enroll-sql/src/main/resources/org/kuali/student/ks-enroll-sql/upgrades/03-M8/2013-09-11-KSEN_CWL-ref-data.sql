-- Create a waitlist for each AO (save ao id and fo id in the create and update ids temporarily
INSERT
INTO
    KSEN_CWL
    (
        ALLOW_HOLD_UTIL_ENTRIES_IND,
        AUTO_PROCESSED_IND,
        CHECK_IN_REQ_IND,
        CONF_REQ_IND,
        CREATEID,
        CREATETIME,
        CWL_STATE,
        CWL_TYPE,
        EFF_DT,
        EXPIR_DT,
        ID,
        MAX_SIZE,
        OBJ_ID,
        REG_IN_FIRST_AAO_IND,
        STD_DUR_TIME_QTY,
        STD_DUR_TYPE,
        UPDATEID,
        UPDATETIME,
        VER_NBR
    )
SELECT
    1,
    1,
    0,
    1,
    ao.ID, --save the ao id as the create id temporarlily
    TIMESTAMP '2013-09-11 00:00:00',
    'kuali.course.waitlist.state.active',
    'kuali.course.waitlist.type.course.waitlist',
    ao.EFF_DT,
    ao.EXPIR_DT,
    SYS_GUID(),
    NULL,
    SYS_GUID(),
    1,
    NULL,
    NULL,
    fo2ao.LUI_ID, --save the fo id as the update id temporarlily
    TIMESTAMP '2013-09-11 00:00:00',
    0
FROM
    KSEN_LUI ao,
    KSEN_LUILUI_RELTN fo2ao
WHERE
    LUI_TYPE IN ('kuali.lui.type.activity.offering.lecture',
                 'kuali.lui.type.activity.offering.discussion',
                 'kuali.lui.type.activity.offering.lab')
AND fo2ao.LUILUI_RELTN_TYPE='kuali.lui.lui.relation.type.deliveredvia.fo2ao'
AND fo2ao.RELATED_LUI_ID = ao.ID
/

--Add data ao-cwl and fo-cwl in the join tables
INSERT INTO KSEN_CWL_ACTIV_OFFER (ACTIV_OFFER_ID, CWL_ID) SELECT cwl.CREATEID, cwl.ID FROM KSEN_CWL cwl
/
INSERT INTO KSEN_CWL_FORMAT_OFFER (FORMAT_OFFER_ID, CWL_ID) SELECT cwl.UPDATEID, cwl.ID FROM KSEN_CWL cwl
/

--Fix up the create and update ids
UPDATE KSEN_CWL SET CREATEID = 'REF_DATA', UPDATEID = 'REF_DATA'
/

--CO Dynamic attribute cleanup
--Clean up all the old dynamic attributes
DELETE FROM KSEN_LUI_ATTR WHERE ATTR_KEY IN ('isWaitlistCheckinRequired',
'kuali.attribute.wait.list.indicator',
'kuali.attribute.wait.list.level.type.key',
'kuali.attribute.wait.list.type.key')
/

--Add in has waitlist and waitlist level to all the COs
INSERT INTO KSEN_LUI_ATTR (ATTR_KEY, ATTR_VALUE, ID, OBJ_ID, OWNER_ID) SELECT 'kuali.attribute.wait.list.indicator', 'true', SYS_GUID(), SYS_GUID(), co.ID FROM KSEN_LUI co where co.LUI_TYPE='kuali.lui.type.course.offering'
/
INSERT INTO KSEN_LUI_ATTR (ATTR_KEY, ATTR_VALUE, ID, OBJ_ID, OWNER_ID) SELECT 'kuali.attribute.wait.list.level.type.key', 'AO', SYS_GUID(), SYS_GUID(), co.ID FROM KSEN_LUI co where co.LUI_TYPE='kuali.lui.type.course.offering'
/
