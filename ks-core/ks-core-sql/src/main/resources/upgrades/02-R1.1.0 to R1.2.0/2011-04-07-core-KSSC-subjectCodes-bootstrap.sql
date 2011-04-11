CREATE
    TABLE KSSC_SUBJ_CD_TYPE
    (
        TYPE_KEY VARCHAR2(255) NOT NULL,
        TYPE_DESC VARCHAR2(2000),
        EFF_DT TIMESTAMP(6),
        EXPIR_DT TIMESTAMP(6),
        NAME VARCHAR2(255),
        OBJ_ID VARCHAR2(36),
        VER_NBR NUMBER(19),
        CONSTRAINT KSSC_SUBJ_CD_TYPEP1 PRIMARY KEY (TYPE_KEY)
    )
/
   
insert into KSSC_SUBJ_CD_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME, OBJ_ID, VER_NBR) values ('ks.core.subjectcode.usage.all', 'All of', to_date('2000-01-01', 'yyyy-mm-dd'), null, 'All', '1', 0)
/
insert into KSSC_SUBJ_CD_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME, OBJ_ID, VER_NBR) values ('ks.core.subjectcode.usage.one', 'One of', to_date('2000-01-01', 'yyyy-mm-dd'), null, 'One', '2', 0)
/


CREATE
    TABLE KSSC_SUBJ_CD
    (
        ID VARCHAR2(255) NOT NULL,
        CREATEID VARCHAR2(255),
        CREATETIME TIMESTAMP(6),
        UPDATEID VARCHAR2(255),
        UPDATETIME TIMESTAMP(6),
        VER_NBR NUMBER(19) NOT NULL,
        NAME VARCHAR2(255),
        STATE VARCHAR2(255),
        CD VARCHAR2(255),
        TYPE VARCHAR2(255),
        OBJ_ID VARCHAR2(36),
        CONSTRAINT KSSC_SUBJ_CD_P1 PRIMARY KEY (ID),
        CONSTRAINT KSSC_SUBJ_CD_FK1 FOREIGN KEY (TYPE) REFERENCES KSSC_SUBJ_CD_TYPE (TYPE_KEY)
    )
/

CREATE
    TABLE KSSC_SUBJ_CD_JN_ORG
    (
        ID VARCHAR2(255) NOT NULL,
        ORG_ID VARCHAR2(255),
        SUBJ_CD_ID VARCHAR2(255),
        VER_NBR NUMBER(19) NOT NULL,
        EFF_DT TIMESTAMP(6),
        EXPIR_DT TIMESTAMP(6),
        OBJ_ID VARCHAR2(36),
        CONSTRAINT KSSC_SUBJ_CD_JN_ORG_P1 PRIMARY KEY (ID),
        CONSTRAINT KSSC_SUBJ_CD_JN_ORG_FK1 FOREIGN KEY (SUBJ_CD_ID) REFERENCES KSSC_SUBJ_CD (ID)
    )
/

insert into KREW_DOC_TYP_T (DOC_TYP_ID, PARNT_ID, DOC_TYP_NM, DOC_TYP_VER_NBR, ACTV_IND, CUR_IND, LBL, PREV_DOC_TYP_VER_NBR, DOC_HDR_ID, DOC_TYP_DESC, DOC_HDLR_URL, POST_PRCSR, JNDI_URL, BLNKT_APPR_PLCY, ADV_DOC_SRCH_URL, CSTM_ACTN_LIST_ATTRIB_CLS_NM, CSTM_ACTN_EMAIL_ATTRIB_CLS_NM, CSTM_DOC_NTE_ATTRIB_CLS_NM, RTE_VER_NBR, NOTIFY_ADDR, SVC_NMSPC, EMAIL_XSL, SEC_XML, VER_NBR, BLNKT_APPR_GRP_ID, RPT_GRP_ID, GRP_ID, HELP_DEF_URL, OBJ_ID, DOC_SEARCH_HELP_URL) values (3028, 3009, 'SubjectCodeTypeMaintenanceDocument', 0, 1, 1, 'Subject Code Type', null, null, null, null, null, null, null, null, null, null, null, '2', null, null, null, null, 1, null, null, null, null, '5E9B1033-09E7-F99D-546A-0F845682823D', null)
/
insert into KREW_DOC_TYP_T (DOC_TYP_ID, PARNT_ID, DOC_TYP_NM, DOC_TYP_VER_NBR, ACTV_IND, CUR_IND, LBL, PREV_DOC_TYP_VER_NBR, DOC_HDR_ID, DOC_TYP_DESC, DOC_HDLR_URL, POST_PRCSR, JNDI_URL, BLNKT_APPR_PLCY, ADV_DOC_SRCH_URL, CSTM_ACTN_LIST_ATTRIB_CLS_NM, CSTM_ACTN_EMAIL_ATTRIB_CLS_NM, CSTM_DOC_NTE_ATTRIB_CLS_NM, RTE_VER_NBR, NOTIFY_ADDR, SVC_NMSPC, EMAIL_XSL, SEC_XML, VER_NBR, BLNKT_APPR_GRP_ID, RPT_GRP_ID, GRP_ID, HELP_DEF_URL, OBJ_ID, DOC_SEARCH_HELP_URL) values (3029, 3009, 'SubjectCodeMaintenanceDocument', 0, 1, 1, 'Subject Code', null, null, null, null, null, null, null, null, null, null, null, '2', null, null, null, null, 1, null, null, null, null, '5E9B1033-09E7-F99D-546A-0F845682823E', null)
/
insert into KREW_DOC_TYP_T (DOC_TYP_ID, PARNT_ID, DOC_TYP_NM, DOC_TYP_VER_NBR, ACTV_IND, CUR_IND, LBL, PREV_DOC_TYP_VER_NBR, DOC_HDR_ID, DOC_TYP_DESC, DOC_HDLR_URL, POST_PRCSR, JNDI_URL, BLNKT_APPR_PLCY, ADV_DOC_SRCH_URL, CSTM_ACTN_LIST_ATTRIB_CLS_NM, CSTM_ACTN_EMAIL_ATTRIB_CLS_NM, CSTM_DOC_NTE_ATTRIB_CLS_NM, RTE_VER_NBR, NOTIFY_ADDR, SVC_NMSPC, EMAIL_XSL, SEC_XML, VER_NBR, BLNKT_APPR_GRP_ID, RPT_GRP_ID, GRP_ID, HELP_DEF_URL, OBJ_ID, DOC_SEARCH_HELP_URL) values (3030, 3009, 'SubjectCodeJoinOrgMaintenanceDocument', 0, 1, 1, 'Subject Code Join Org', null, null, null, null, null, null, null, null, null, null, null, '2', null, null, null, null, 1, null, null, null, null, '5E9B1033-09E7-F99D-546A-0F845682823F', null)
/

insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('1', 'kr', to_date('2011-04-08', 'yyyy-mm-dd'), null, null, 1, 'Architecture', 'Actual', 'AACH', 'ks.core.subjectcode.usage.all', '3a512547-5442-4c3b-9965-6f3a0b60866c')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('2', 'kr', to_date('2011-04-09', 'yyyy-mm-dd'), null, null, 1, 'African American Studies', 'Actual', 'AASP', 'ks.core.subjectcode.usage.all', '0677e556-fb00-4d0c-8798-1ac838f658e7')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('3', 'kr', to_date('2011-04-10', 'yyyy-mm-dd'), null, null, 1, 'Asian American Studies', 'Actual', 'AAST', 'ks.core.subjectcode.usage.all', 'bde40bf9-0f10-44e1-9e88-5a3a450bc76a')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('4', 'kr', to_date('2011-04-11', 'yyyy-mm-dd'), null, null, 1, 'Accounting', 'Actual', 'ACCT', 'ks.core.subjectcode.usage.all', 'db04c658-f506-4393-a6bc-c26a5e779ac1')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('5', 'kr', to_date('2011-04-12', 'yyyy-mm-dd'), null, null, 1, 'Arts and Humanities', 'Actual', 'ARHU', 'ks.core.subjectcode.usage.all', 'fa824f46-d252-4afc-8294-bc2d3a581fa0')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('6', 'kr', to_date('2011-04-13', 'yyyy-mm-dd'), null, null, 1, 'Art History', 'Actual', 'ARTH', 'ks.core.subjectcode.usage.all', '73c56e37-6273-4061-9069-668ffdb84908')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('7', 'kr', to_date('2011-04-14', 'yyyy-mm-dd'), null, null, 1, 'FineArts', 'Actual', 'ARTS', 'ks.core.subjectcode.usage.all', '22a76253-78db-4194-ae82-35f17918cbd9')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('8', 'kr', to_date('2011-04-15', 'yyyy-mm-dd'), null, null, 1, 'Bio Chemistry', 'Actual', 'BCHM', 'ks.core.subjectcode.usage.all', 'e831a7f4-1553-4c45-a942-fabed9873c31')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('9', 'kr', to_date('2011-04-16', 'yyyy-mm-dd'), null, null, 1, 'Biomed', 'Actual', 'BENG', 'ks.core.subjectcode.usage.all', 'b481b138-fb62-4eae-ab58-9a2d34a6b257')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('10', 'kr', to_date('2011-04-17', 'yyyy-mm-dd'), null, null, 1, 'Biochemistry', 'Actual', 'BIOC', 'ks.core.subjectcode.usage.all', '95bb66d8-5dbd-4901-a3d1-a42cc42bd6a2')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('11', 'kr', to_date('2011-04-18', 'yyyy-mm-dd'), null, null, 1, 'Biology', 'Actual', 'BIOL', 'ks.core.subjectcode.usage.all', 'e4c689bf-8378-4a00-9a95-a55302ba0ddb')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('12', 'kr', to_date('2011-04-19', 'yyyy-mm-dd'), null, null, 1, 'Biometrics', 'Actual', 'BIOM', 'ks.core.subjectcode.usage.all', 'b2ade3b1-ec73-4e55-8ec0-c8d1deb4030c')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('13', 'kr', to_date('2011-04-20', 'yyyy-mm-dd'), null, null, 1, 'Business Management', 'Actual', 'BMGT', 'ks.core.subjectcode.usage.all', '5f613895-e03c-463a-8483-e22a2bd65f68')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('14', 'kr', to_date('2011-04-21', 'yyyy-mm-dd'), null, null, 1, 'Botany', 'Actual', 'BOTA', 'ks.core.subjectcode.usage.all', '3b755af6-d9ea-40ed-9159-cf338d3531c0')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('15', 'kr', to_date('2011-04-22', 'yyyy-mm-dd'), null, null, 1, 'Biological Sciences', 'Actual', 'BSCI', 'ks.core.subjectcode.usage.all', '23beb29b-7e73-44bd-ae5a-b2590cb2fb48')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('16', 'kr', to_date('2011-04-23', 'yyyy-mm-dd'), null, null, 1, 'Criminal Justice', 'Actual', 'CCJS', 'ks.core.subjectcode.usage.all', '4dc5f871-dd0e-4b3f-9880-3bcc2c3eca4f')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('17', 'kr', to_date('2011-04-24', 'yyyy-mm-dd'), null, null, 1, 'Chemical Engineering', 'Actual', 'CHEE', 'ks.core.subjectcode.usage.all', '26514b91-66b4-44f9-a20c-97d366cf8d59')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('18', 'kr', to_date('2011-04-25', 'yyyy-mm-dd'), null, null, 1, 'Chemistry', 'Actual', 'CHEM', 'ks.core.subjectcode.usage.all', '04d1f81e-dcf3-4cc7-8384-2366f84a6684')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('19', 'kr', to_date('2011-04-26', 'yyyy-mm-dd'), null, null, 1, 'Chemistry', 'Actual', 'CHEM', 'ks.core.subjectcode.usage.all', '5cfae2c6-069b-44a5-8140-20d06dc0f00a')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('20', 'kr', to_date('2011-04-27', 'yyyy-mm-dd'), null, null, 1, 'Civil Engineering', 'Actual', 'CIVI', 'ks.core.subjectcode.usage.all', 'c1dfcfa7-0fc1-4662-82b4-5ba944415e3c')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('21', 'kr', to_date('2011-04-28', 'yyyy-mm-dd'), null, null, 1, 'CompSci', 'Actual', 'CSCI', 'ks.core.subjectcode.usage.all', '1c461855-a6b6-4890-86d7-d41d0c5f7d37')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('22', 'kr', to_date('2011-04-29', 'yyyy-mm-dd'), null, null, 1, 'Economics', 'Actual', 'ECON', 'ks.core.subjectcode.usage.all', '7344fae8-3767-4c8a-9c73-70f0838108b1')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('23', 'kr', to_date('2011-04-30', 'yyyy-mm-dd'), null, null, 1, 'Education', 'Actual', 'EDUC', 'ks.core.subjectcode.usage.all', '0bc2b673-2361-4c53-a5c1-b0eb5fc13241')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('24', 'kr', to_date('2011-05-01', 'yyyy-mm-dd'), null, null, 1, 'English', 'Actual', 'ENGL', 'ks.core.subjectcode.usage.all', '1eb81f63-4d49-474d-a9db-4a87c3cb7aa9')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('25', 'kr', to_date('2011-05-02', 'yyyy-mm-dd'), null, null, 1, 'English', 'Actual', 'ENGL', 'ks.core.subjectcode.usage.all', '0ad6ced8-d9fd-4f8b-87af-b8621cfdda5a')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('26', 'kr', to_date('2011-05-03', 'yyyy-mm-dd'), null, null, 1, 'Finance', 'Actual', 'FINA', 'ks.core.subjectcode.usage.all', '66948f84-3a2a-4e37-a1c1-83d620b2fd12')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('27', 'kr', to_date('2011-05-04', 'yyyy-mm-dd'), null, null, 1, 'French', 'Actual', 'FREN', 'ks.core.subjectcode.usage.all', '678daa79-049e-4344-9d05-67a5dcfc090e')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('28', 'kr', to_date('2011-05-05', 'yyyy-mm-dd'), null, null, 1, 'Gemstone', 'Actual', 'GEMS', 'ks.core.subjectcode.usage.all', 'fe6b7d6a-90b6-4e4c-be83-c7ec69002e31')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('29', 'kr', to_date('2011-05-06', 'yyyy-mm-dd'), null, null, 1, 'Geography', 'Actual', 'GEOG', 'ks.core.subjectcode.usage.all', '8cc2451a-01cd-4e6e-9383-5e8d902357c3')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('30', 'kr', to_date('2011-05-07', 'yyyy-mm-dd'), null, null, 1, 'Geology', 'Actual', 'GEOL', 'ks.core.subjectcode.usage.all', 'b244d1bd-cb72-4a38-88dd-f79387eccaec')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('31', 'kr', to_date('2011-05-08', 'yyyy-mm-dd'), null, null, 1, 'Political Science', 'Actual', 'GVPT', 'ks.core.subjectcode.usage.all', '5d35d8eb-2001-4de3-bd44-3b9d0faed3ee')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('32', 'kr', to_date('2011-05-09', 'yyyy-mm-dd'), null, null, 1, 'History', 'Actual', 'HIST', 'ks.core.subjectcode.usage.all', '6920a32e-0391-49c8-8254-c91a2dd801b5')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('33', 'kr', to_date('2011-05-10', 'yyyy-mm-dd'), null, null, 1, 'Honors', 'Actual', 'HONR', 'ks.core.subjectcode.usage.all', 'c81ccb41-ea37-4f98-b9dd-542d21f9004d')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('34', 'kr', to_date('2011-05-11', 'yyyy-mm-dd'), null, null, 1, 'InternationalBusiness', 'Actual', 'INTB', 'ks.core.subjectcode.usage.all', '1f985699-b0fd-4827-bb8e-9dc6ef735bbf')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('35', 'kr', to_date('2011-05-12', 'yyyy-mm-dd'), null, null, 1, 'Linguistics', 'Actual', 'LING', 'ks.core.subjectcode.usage.all', '650c4e16-23a4-407a-88fc-e9bcff76512b')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('36', 'kr', to_date('2011-05-13', 'yyyy-mm-dd'), null, null, 1, 'Marketing ', 'Actual', 'MARK', 'ks.core.subjectcode.usage.all', 'd0a33485-7754-4923-bc44-56bc2cc7c3bb')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('37', 'kr', to_date('2011-05-14', 'yyyy-mm-dd'), null, null, 1, 'Math', 'Actual', 'MATH', 'ks.core.subjectcode.usage.all', '58992156-f68f-4a51-9b5d-c78f42471914')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('38', 'kr', to_date('2011-05-15', 'yyyy-mm-dd'), null, null, 1, 'Mechanical Engineering', 'Actual', 'MENG', 'ks.core.subjectcode.usage.all', '59062a42-072b-421c-b252-21e3256f286b')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('39', 'kr', to_date('2011-05-16', 'yyyy-mm-dd'), null, null, 1, 'Music', 'Actual', 'MUSC', 'ks.core.subjectcode.usage.all', 'a0a073fc-15f4-43a0-a59f-090fba286877')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('40', 'kr', to_date('2011-05-17', 'yyyy-mm-dd'), null, null, 1, 'Philosophy', 'Actual', 'PHIL', 'ks.core.subjectcode.usage.all', 'e28006c3-47f3-4f48-84a4-4b87efb26690')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('41', 'kr', to_date('2011-05-18', 'yyyy-mm-dd'), null, null, 1, 'Physics', 'Actual', 'PHYS', 'ks.core.subjectcode.usage.all', '00707ba9-46d3-4baf-a38b-4b13e3e80f34')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('42', 'kr', to_date('2011-05-19', 'yyyy-mm-dd'), null, null, 1, 'PolySci', 'Actual', 'POLI', 'ks.core.subjectcode.usage.all', '88ca03dd-d8f3-4f41-a8eb-edf8ce1ce797')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('43', 'kr', to_date('2011-05-20', 'yyyy-mm-dd'), null, null, 1, 'Psychology', 'Actual', 'PSYC', 'ks.core.subjectcode.usage.all', '4dc34d04-25a3-451d-97db-242189c2b06a')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('44', 'kr', to_date('2011-05-21', 'yyyy-mm-dd'), null, null, 1, 'PubAdmin', 'Actual', 'PUAD', 'ks.core.subjectcode.usage.all', '1cd8c58b-b1a6-4e15-93e0-02e3f06c5b16')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('45', 'kr', to_date('2011-05-22', 'yyyy-mm-dd'), null, null, 1, 'CompSci', 'Actual', 'ROBT', 'ks.core.subjectcode.usage.all', '00ee04e9-14af-435c-9f0b-cea34400fc1b')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('46', 'kr', to_date('2011-05-23', 'yyyy-mm-dd'), null, null, 1, 'Robotics', 'Actual', 'ROBT', 'ks.core.subjectcode.usage.all', 'e0077878-6712-48d4-88b3-5b7487393dfc')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('47', 'kr', to_date('2011-05-24', 'yyyy-mm-dd'), null, null, 1, 'Mechanical', 'Actual', 'ROBT', 'ks.core.subjectcode.usage.all', 'cdb55575-6881-44b7-bc78-99eff78bd9a2')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('48', 'kr', to_date('2011-05-25', 'yyyy-mm-dd'), null, null, 1, 'Sociology', 'Actual', 'SOCY', 'ks.core.subjectcode.usage.all', '246618ab-44d2-42d7-ae14-2b07a0de3a0c')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('49', 'kr', to_date('2011-05-26', 'yyyy-mm-dd'), null, null, 1, 'Sociology', 'Actual', 'SOCY', 'ks.core.subjectcode.usage.all', 'b84474a4-787e-4bf8-a40f-380e296d44ae')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('50', 'kr', to_date('2011-05-27', 'yyyy-mm-dd'), null, null, 1, 'Social Work', 'Actual', 'SOWK', 'ks.core.subjectcode.usage.all', '249bba4a-c5fb-4192-8688-04059af3724f')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('51', 'kr', to_date('2011-05-28', 'yyyy-mm-dd'), null, null, 1, 'Statistics', 'Actual', 'STAT', 'ks.core.subjectcode.usage.all', 'efadc2f0-e793-4297-82b8-83e0e247b5ee')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('52', 'kr', to_date('2011-05-29', 'yyyy-mm-dd'), null, null, 1, 'Theater', 'Actual', 'THET', 'ks.core.subjectcode.usage.all', 'fa78480f-ba62-451f-8d1f-fac23d712878')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('53', 'kr', to_date('2011-05-30', 'yyyy-mm-dd'), null, null, 1, 'University Studies', 'Actual', 'UNIV', 'ks.core.subjectcode.usage.all', '48cc8048-aa6b-4f40-8e95-d7940ef758a9')
/

