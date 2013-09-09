--KSENROLL-5381
--This updates the RVG keys so that they reflect the .5 credits correctly

--Copy the RVGs but add '.by.0.5' to the key
INSERT
INTO
    KSEN_LRC_RVG
    (
        ID,
        OBJ_ID,
        RVG_TYPE,
        RVG_STATE,
        NAME,
        DESCR_PLAIN,
        DESCR_FORMATTED,
        RESULT_SCALE_ID,
        RANGE_MIN_VALUE,
        RANGE_MAX_VALUE,
        RANGE_INCREMENT,
        EFF_DT,
        EXPIR_DT,
        VER_NBR,
        CREATETIME,
        CREATEID,
        UPDATETIME,
        UPDATEID
    )
SELECT
    ID||'.by.0.5',
    OBJ_ID,
    RVG_TYPE,
    RVG_STATE,
    NAME,
    DESCR_PLAIN,
    DESCR_FORMATTED,
    RESULT_SCALE_ID,
    RANGE_MIN_VALUE,
    RANGE_MAX_VALUE,
    RANGE_INCREMENT,
    EFF_DT,
    EXPIR_DT,
    VER_NBR,
    CREATETIME,
    CREATEID,
    UPDATETIME,
    UPDATEID
FROM
    KSEN_LRC_RVG rvg
WHERE
    rvg.id IN ( 'kuali.creditType.credit.degree.1-16', 'kuali.creditType.credit.degree.1-6' ,
    'kuali.creditType.credit.degree.1-8' )
/

--update RVG->value references
UPDATE
    KSEN_LRC_RVG_RESULT_VALUE
SET
    RVG_ID=RVG_ID||'.by.0.5'
WHERE
    RVG_ID IN('kuali.creditType.credit.degree.1-16', 'kuali.creditType.credit.degree.1-6' ,
    'kuali.creditType.credit.degree.1-8')
/

--update lui references
update KSEN_LUI_RESULT_VAL_GRP
set RESULT_VAL_GRP_ID=RESULT_VAL_GRP_ID||'.by.0.5'
WHERE
    RESULT_VAL_GRP_ID IN('kuali.creditType.credit.degree.1-16', 'kuali.creditType.credit.degree.1-6' ,
    'kuali.creditType.credit.degree.1-8')
/

--update clu references
update KSLU_RSLT_OPT
set RES_COMP_ID=RES_COMP_ID||'.by.0.5'
WHERE
    RES_COMP_ID IN('kuali.creditType.credit.degree.1-16', 'kuali.creditType.credit.degree.1-6' ,
    'kuali.creditType.credit.degree.1-8')
/

--delete the old
delete from KSEN_LRC_RVG where ID  IN('kuali.creditType.credit.degree.1-16', 'kuali.creditType.credit.degree.1-6' ,
    'kuali.creditType.credit.degree.1-8')
/

--delete all the other weird ones that don't have references
delete from KSEN_LRC_RVG_RESULT_VALUE where RVG_ID in (
'kuali.creditType.credit.degree..5-3',
'kuali.creditType.credit.degree.0-12',
'kuali.creditType.credit.degree.1-10',
'kuali.creditType.credit.degree.1-12',
'kuali.creditType.credit.degree.1-15',
'kuali.creditType.credit.degree.1-2',
'kuali.creditType.credit.degree.1-20',
'kuali.creditType.credit.degree.1-3',
'kuali.creditType.credit.degree.1-4',
'kuali.creditType.credit.degree.1-5',
'kuali.creditType.credit.degree.1-9',
'kuali.creditType.credit.degree.1.5-2',
'kuali.creditType.credit.degree.2-12',
'kuali.creditType.credit.degree.2-3',
'kuali.creditType.credit.degree.2-4',
'kuali.creditType.credit.degree.2-5',
'kuali.creditType.credit.degree.2-6',
'kuali.creditType.credit.degree.2-8',
'kuali.creditType.credit.degree.3-12',
'kuali.creditType.credit.degree.3-16',
'kuali.creditType.credit.degree.3-4',
'kuali.creditType.credit.degree.3-5',
'kuali.creditType.credit.degree.3-6',
'kuali.creditType.credit.degree.3-8',
'kuali.creditType.credit.degree.3-9',
'kuali.creditType.credit.degree.4-11',
'kuali.creditType.credit.degree.4-12',
'kuali.creditType.credit.degree.4-5',
'kuali.creditType.credit.degree.4-6',
'kuali.creditType.credit.degree.4-8',
'kuali.creditType.credit.degree.6-11',
'kuali.creditType.credit.degree.6-12')
/

delete from KSEN_LRC_RVG where ID in (
'kuali.creditType.credit.degree..5-3',
'kuali.creditType.credit.degree.0-12',
'kuali.creditType.credit.degree.1-10',
'kuali.creditType.credit.degree.1-12',
'kuali.creditType.credit.degree.1-15',
'kuali.creditType.credit.degree.1-2',
'kuali.creditType.credit.degree.1-20',
'kuali.creditType.credit.degree.1-3',
'kuali.creditType.credit.degree.1-4',
'kuali.creditType.credit.degree.1-5',
'kuali.creditType.credit.degree.1-9',
'kuali.creditType.credit.degree.1.5-2',
'kuali.creditType.credit.degree.2-12',
'kuali.creditType.credit.degree.2-3',
'kuali.creditType.credit.degree.2-4',
'kuali.creditType.credit.degree.2-5',
'kuali.creditType.credit.degree.2-6',
'kuali.creditType.credit.degree.2-8',
'kuali.creditType.credit.degree.3-12',
'kuali.creditType.credit.degree.3-16',
'kuali.creditType.credit.degree.3-4',
'kuali.creditType.credit.degree.3-5',
'kuali.creditType.credit.degree.3-6',
'kuali.creditType.credit.degree.3-8',
'kuali.creditType.credit.degree.3-9',
'kuali.creditType.credit.degree.4-11',
'kuali.creditType.credit.degree.4-12',
'kuali.creditType.credit.degree.4-5',
'kuali.creditType.credit.degree.4-6',
'kuali.creditType.credit.degree.4-8',
'kuali.creditType.credit.degree.6-11',
'kuali.creditType.credit.degree.6-12')
/

--update the increment to 0.5
update KSEN_LRC_RVG set RANGE_INCREMENT=0.5 WHERE ID IN('kuali.creditType.credit.degree.1-16.by.0.5', 'kuali.creditType.credit.degree.1-6.by.0.5', 'kuali.creditType.credit.degree.1-8.by.0.5')
/
