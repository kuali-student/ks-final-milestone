---------------------------------------------------------------------------------------------------------
-- *These scripts was used TO laod DB -  do normal load via impex
--  and then apply the following 2 *
---------------------------------------------------------------------------------------------------------
--1---
drop table KRSB_SVC_DEF_T
/
drop table KRSB_FLT_SVC_DEF_T
/
drop sequence KRSB_SVC_DEF_S
/
drop sequence KRSB_FLT_SVC_DEF_S
/
CREATE TABLE KRSB_SVC_DSCRPTR_T (
  SVC_DSCRPTR_ID varchar2(40) NOT NULL,
  DSCRPTR clob NOT NULL,
  PRIMARY KEY (SVC_DSCRPTR_ID)
)
/
CREATE SEQUENCE KRSB_SVC_DSCRPTR_S INCREMENT BY 1 START WITH 10000 NOMAXVALUE NOCYCLE NOCACHE ORDER
/
CREATE TABLE KRSB_SVC_DEF_T (
  SVC_DEF_ID varchar2(40) NOT NULL,
  SVC_NM varchar2(255) NOT NULL,
  SVC_URL varchar2(500) NOT NULL,
  INSTN_ID varchar2(255) NOT NULL,
  APPL_NMSPC varchar2(255) NOT NULL,
  SRVR_IP varchar2(40) NOT NULL,
  TYP_CD varchar2(40) NOT NULL,
  SVC_VER varchar2(40) NOT NULL,
  STAT_CD varchar2(1) NOT NULL,
  SVC_DSCRPTR_ID varchar2(40) NOT NULL,
  CHKSM varchar2(30) NOT NULL,
  VER_NBR number(8) DEFAULT 0 NOT NULL,
  PRIMARY KEY (SVC_DEF_ID),
  CONSTRAINT KRSB_SVC_DEF_FK1
    FOREIGN KEY (SVC_DSCRPTR_ID)
    REFERENCES KRSB_SVC_DSCRPTR_T(SVC_DSCRPTR_ID) ON DELETE CASCADE
  
  
)
/
CREATE SEQUENCE KRSB_SVC_DEF_S INCREMENT BY 1 START WITH 10000 NOMAXVALUE NOCYCLE NOCACHE ORDER
/
CREATE INDEX KRSB_SVC_DEF_TI1 on KRSB_SVC_DEF_T (INSTN_ID)
/
CREATE INDEX KRSB_SVC_DEF_TI2 on KRSB_SVC_DEF_T (SVC_NM, STAT_CD)
/
CREATE INDEX KRSB_SVC_DEF_TI3 on KRSB_SVC_DEF_T (STAT_CD)
/

----------------------------------------------------------------------------------------------------------
--2--
----------------------------------------------------------------------------------------------------------
ALTER TABLE KREW_DOC_TYP_T RENAME COLUMN SVC_NMSPC TO APPL_ID
/
ALTER TABLE KREW_RULE_ATTR_T RENAME COLUMN SVC_NMSPC TO APPL_ID
/
ALTER TABLE KRSB_SVC_DEF_T RENAME COLUMN APPL_NMSPC TO APPL_ID
/
ALTER TABLE KRSB_MSG_QUE_T RENAME COLUMN SVC_NMSPC TO APPL_ID
/
ALTER TABLE KRNS_NMSPC_T RENAME COLUMN APPL_NMSPC_CD TO APPL_ID
/
ALTER TABLE KRNS_PARM_T RENAME COLUMN APPL_NMSPC_CD TO APPL_ID
/

ALTER TABLE KRNS_NMSPC_T RENAME TO KRCR_NMSPC_T
/
ALTER TABLE KRNS_PARM_TYP_T RENAME TO KRCR_PARM_TYP_T
/
ALTER TABLE KRNS_PARM_DTL_TYP_T RENAME TO KRCR_PARM_DTL_TYP_T
/
ALTER TABLE KRNS_PARM_T RENAME TO KRCR_PARM_T
/

ALTER TABLE KRNS_CAMPUS_T RENAME TO KRLC_CMP_T
/
ALTER TABLE KRNS_CMP_TYP_T RENAME TO KRLC_CMP_TYP_T
/
ALTER TABLE KR_COUNTRY_T RENAME TO KRLC_CNTRY_T
/
ALTER TABLE KR_STATE_T RENAME TO KRLC_ST_T
/
ALTER TABLE KR_POSTAL_CODE_T RENAME TO KRLC_PSTL_CD_T
/
ALTER TABLE KR_COUNTY_T RENAME TO KRLC_CNTY_T
/



---------------------------------------------------------------------------------------------------------
-- Create table KSLU_CLU_IDENT with DIVISION as column
---------------------------------------------------------------------------------------------------------
CREATE TABLE KSLU_CLU_IDENT
(
      ID VARCHAR2(255)
        , CD VARCHAR2(255)
        , DIVISION VARCHAR2(255)
        , LVL VARCHAR2(255)
        , LNG_NAME VARCHAR2(255)
        , ORG_ID VARCHAR2(255)
        , SHRT_NAME VARCHAR2(255)
        , ST VARCHAR2(255)
        , SUFX_CD VARCHAR2(255)
        , TYPE VARCHAR2(255)
        , VARTN VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

