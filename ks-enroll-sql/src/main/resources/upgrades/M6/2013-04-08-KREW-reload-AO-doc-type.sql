--These are the original SQLs

--UPDATE KREW_DOC_TYP_T SET PARNT_ID='2681',DOC_TYP_NM='ActivityOfferingInfoMaintenanceDocument',DOC_TYP_VER_NBR='1',ACTV_IND='true',CUR_IND='false',DOC_TYP_DESC='Create a New Activity Offering Maintenance Document',LBL='org.kuali.student.r2.lum.course.infc.Activity Offering Info Maintenance Document',PREV_DOC_TYP_VER_NBR='3021',DOC_HDR_ID='',DOC_HDLR_URL='${application.url}/kr-krad/maintenance?methodToCall=docHandler&dataObjectClassName=org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper>',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR='org.kuali.rice.krad.workflow.postprocessor.KualiPostProcessor',GRP_ID='1',BLNKT_APPR_GRP_ID='1',BLNKT_APPR_PLCY='',RPT_GRP_ID='',RTE_VER_NBR='2',NOTIFY_ADDR='',SEC_XML='',EMAIL_XSL='',APPL_ID='',OBJ_ID='6ecbe98e-3fa7-422c-9104-52d9e7fb961c',VER_NBR='2' WHERE DOC_TYP_ID = '3024'  AND VER_NBR = '1'
--UPDATE KREW_DOC_TYP_PROC_T SET DOC_TYP_ID='3024',INIT_RTE_NODE_ID='',NM='PRIMARY',INIT_IND='true',VER_NBR='2' WHERE DOC_TYP_PROC_ID = '3047'  AND VER_NBR = '1'
--INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR) VALUES ('77301','2681','ActivityOfferingInfoMaintenanceDocument','2','true','true','Create a New Activity Offering Maintenance Document','org.kuali.student.r2.lum.course.infc.Activity Offering Info Maintenance Document','3024','','${application.url}/kr-krad/maintenance?methodToCall=docHandler&dataObjectClassName=org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper>','','','org.kuali.rice.krad.workflow.postprocessor.KualiPostProcessor','1','1','','','2','','','','','ab5e54ae-f558-4d4a-b8c6-fe2cc9c7b35c','1')
--INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_PROC_ID,DOC_TYP_ID,INIT_RTE_NODE_ID,NM,INIT_IND,VER_NBR) VALUES ('3048','77301','','PRIMARY','true','1')

--This one didn't seem needed
--UPDATE KREW_DOC_TYP_T SET PARNT_ID='2680',DOC_TYP_NM='RiceDocument',DOC_TYP_VER_NBR='0',ACTV_IND='true',CUR_IND='true',DOC_TYP_DESC='Parent Document Type for all Rice Documents',LBL='Rice Document',PREV_DOC_TYP_VER_NBR='',DOC_HDR_ID='',DOC_HDLR_URL='',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR='',GRP_ID='',BLNKT_APPR_GRP_ID='',BLNKT_APPR_PLCY='',RPT_GRP_ID='',RTE_VER_NBR='2',NOTIFY_ADDR='',SEC_XML=null,EMAIL_XSL='',APPL_ID='',OBJ_ID='6166CBA1BA82644DE0404F8189D86C09',VER_NBR='5' WHERE DOC_TYP_ID = '2681'  AND VER_NBR = '4'


--Do doc type ingestion simulation
UPDATE
    KREW_DOC_TYP_T
SET
    CUR_IND=0,
    RTE_VER_NBR='2',
    VER_NBR=2
WHERE
    DOC_TYP_ID = (select DOC_TYP_ID from KREW_DOC_TYP_T where DOC_TYP_NM='ActivityOfferingInfoMaintenanceDocument')
AND VER_NBR = 1
/

UPDATE
    KREW_DOC_TYP_PROC_T
SET
    DOC_TYP_ID=(select DOC_TYP_ID from KREW_DOC_TYP_T where DOC_TYP_NM='ActivityOfferingInfoMaintenanceDocument'),
    INIT_RTE_NODE_ID='',
    NM='PRIMARY',
    INIT_IND=1,
    VER_NBR=2
WHERE
    DOC_TYP_PROC_ID = (select DOC_TYP_PROC_ID from KREW_DOC_TYP_PROC_T where DOC_TYP_ID=(select DOC_TYP_ID from KREW_DOC_TYP_T where DOC_TYP_NM='ActivityOfferingInfoMaintenanceDocument'))
AND VER_NBR = 1
/

INSERT
INTO
    KREW_DOC_TYP_T
    (
        DOC_TYP_ID,
        PARNT_ID,
        DOC_TYP_NM,
        DOC_TYP_VER_NBR,
        ACTV_IND,
        CUR_IND,
        DOC_TYP_DESC,
        LBL,
        PREV_DOC_TYP_VER_NBR,
        DOC_HDR_ID,
        DOC_HDLR_URL,
        HELP_DEF_URL,
        DOC_SEARCH_HELP_URL,
        POST_PRCSR,
        GRP_ID,
        BLNKT_APPR_GRP_ID,
        BLNKT_APPR_PLCY,
        RPT_GRP_ID,
        RTE_VER_NBR,
        NOTIFY_ADDR,
        SEC_XML,
        EMAIL_XSL,
        APPL_ID,
        OBJ_ID,
        VER_NBR
    )
    VALUES
    (
        'KS-KREW-DOC-TYP-3051',
        (select DOC_TYP_ID from KREW_DOC_TYP_T where DOC_TYP_NM='RiceDocument'),
        'ActivityOfferingInfoMaintenanceDocument',
        2,
        1,
        1,
        'Create a New Activity Offering Maintenance Document',
        'org.kuali.student.r2.lum.course.infc.Activity Offering Info Maintenance Document',
        (select DOC_TYP_ID from KREW_DOC_TYP_T where DOC_TYP_NM='ActivityOfferingInfoMaintenanceDocument'),
        '',
        '${application.url}/kr-krad/maintenance?methodToCall=docHandler&dataObjectClassName=org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper>'
        ,
        '',
        '',
        'org.kuali.rice.krad.workflow.postprocessor.KualiPostProcessor',
        '1',
        '1',
        '',
        '',
        '2',
        '',
        '',
        '',
        '',
        'ab5e54ae-f558-4d4a-b8c6-fe2cc9c7b35c',
        1
    )
/

INSERT
INTO
    KREW_DOC_TYP_PROC_T
    (
        DOC_TYP_PROC_ID,
        DOC_TYP_ID,
        INIT_RTE_NODE_ID,
        NM,
        INIT_IND,
        VER_NBR
    )
    VALUES
    (
        'KS-KREW-DOC-TYP-PROC-3031',
        (select DOC_TYP_ID from KREW_DOC_TYP_T where DOC_TYP_NM='ActivityOfferingInfoMaintenanceDocument' and CUR_IND=1),
        '',
        'PRIMARY',
        1,
        1
    )
/
