
-----------------------------------------------------------------------------
-- KSLU_CLU
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU
(
      ID VARCHAR2(255)
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , VERSIONIND NUMBER(19) NOT NULL
        , ACCRED_ORG_ID VARCHAR2(255)
        , ADMIN_ORG_ID VARCHAR2(255)
        , CAN_CREATE_LUI NUMBER(1)
        , DEF_ENRL_EST NUMBER(10)
        , DEF_MAX_ENRL NUMBER(10)
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , HAS_EARLY_DROP_DEDLN NUMBER(1)
        , CLU_INTSTY_TYPE VARCHAR2(255)
        , CLU_INTSTY_QTY NUMBER(10)
        , IS_ENRL NUMBER(1)
        , IS_HAZR_DISBLD_STU NUMBER(1)
        , NEXT_REVIEW_PRD VARCHAR2(255)
        , REF_URL VARCHAR2(255)
        , ST VARCHAR2(255)
        , ATPDURATIONTYPEKEY VARCHAR2(255)
        , TIMEQUANTITY NUMBER(10)
        , STDY_SUBJ_AREA VARCHAR2(255)
        , ACCT_ID VARCHAR2(255)
        , CR_ID VARCHAR2(255)
        , RT_DESCR_ID VARCHAR2(255)
        , FEE_ID VARCHAR2(255)
        , LUTYPE_ID VARCHAR2(255)
        , RT_MKTG_DESCR_ID VARCHAR2(255)
        , OFFIC_CLU_ID VARCHAR2(255)
        , PRI_ADMIN_ORG_ID VARCHAR2(255)
        , PRI_INSTR_ID VARCHAR2(255)
        , PUBL_ID VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_CLU
    ADD CONSTRAINT KSLU_CLUP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_CLUCLU_RELTN
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLUCLU_RELTN';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLUCLU_RELTN CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLUCLU_RELTN
(
      ID VARCHAR2(255)
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , VERSIONIND NUMBER(19) NOT NULL
        , CLU_RELTN_REQ NUMBER(1)
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , ST VARCHAR2(255)
        , CLU_ID VARCHAR2(255)
        , LU_RELTN_TYPE_ID VARCHAR2(255)
        , RELATED_CLU_ID VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_CLUCLU_RELTN
    ADD CONSTRAINT KSLU_CLUCLU_RELTNP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_CLUCLU_RELTN_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLUCLU_RELTN_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLUCLU_RELTN_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLUCLU_RELTN_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(255)
        , OWNER VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_CLUCLU_RELTN_ATTR
    ADD CONSTRAINT KSLU_CLUCLU_RELTN_ATTRP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_CLU_ACCRED
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_ACCRED';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_ACCRED CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_ACCRED
(
      ID VARCHAR2(255)
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , VERSIONIND NUMBER(19) NOT NULL
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , ORG_ID VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_CLU_ACCRED
    ADD CONSTRAINT KSLU_CLU_ACCREDP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_CLU_ACCRED_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_ACCRED_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_ACCRED_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_ACCRED_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(255)
        , OWNER VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_CLU_ACCRED_ATTR
    ADD CONSTRAINT KSLU_CLU_ACCRED_ATTRP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_CLU_ACCT
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_ACCT';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_ACCT CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_ACCT
(
      ID VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_CLU_ACCT
    ADD CONSTRAINT KSLU_CLU_ACCTP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_CLU_ACCT_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_ACCT_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_ACCT_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_ACCT_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(255)
        , OWNER VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_CLU_ACCT_ATTR
    ADD CONSTRAINT KSLU_CLU_ACCT_ATTRP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_CLU_ADMIN_ORG
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_ADMIN_ORG';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_ADMIN_ORG CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_ADMIN_ORG
(
      ID VARCHAR2(255)
        , ORG_ID VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_CLU_ADMIN_ORG
    ADD CONSTRAINT KSLU_CLU_ADMIN_ORGP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_CLU_ADMIN_ORG_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_ADMIN_ORG_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_ADMIN_ORG_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_ADMIN_ORG_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(255)
        , OWNER VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_CLU_ADMIN_ORG_ATTR
    ADD CONSTRAINT KSLU_CLU_ADMIN_ORG_ATTRP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_CLU_ATP_TYPE_KEY
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_ATP_TYPE_KEY';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_ATP_TYPE_KEY CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_ATP_TYPE_KEY
(
      ID VARCHAR2(255)
        , ATP_TYPE_KEY VARCHAR2(255)
        , CLU_ID VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_CLU_ATP_TYPE_KEY
    ADD CONSTRAINT KSLU_CLU_ATP_TYPE_KEYP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_CLU_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(255)
        , OWNER VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_CLU_ATTR
    ADD CONSTRAINT KSLU_CLU_ATTRP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_CLU_CR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_CR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_CR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_CR
(
      ID VARCHAR2(255)
        , INSTR_UNIT NUMBER(10)
        , MAX_ALOW_INACV_ATP VARCHAR2(255)
        , MAX_ALOW_INACV_TMQ NUMBER(10)
        , MAX_TM_RSLT_RCGZ_ATP VARCHAR2(255)
        , MAX_TM_RSLT_RCGZ_TMQ NUMBER(10)
        , MAX_TM_TO_COMP_ATP VARCHAR2(255)
        , MAX_TM_TO_COMP_TMQ NUMBER(10)
        , MAX_TOT_UNIT NUMBER(10)
        , MIN_TM_TO_COMP_ATP VARCHAR2(255)
        , MIN_TM_TO_COMP_TMQ NUMBER(10)
        , MIN_TOT_UNIT NUMBER(10)
        , REPEAT_CNT VARCHAR2(255)
        , REPEAT_TM_ATP VARCHAR2(255)
        , REPEAT_TM_TMQ NUMBER(10)
        , REPEAT_UNIT VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_CLU_CR
    ADD CONSTRAINT KSLU_CLU_CRP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_CLU_FEE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_FEE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_FEE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_FEE
(
      ID VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_CLU_FEE
    ADD CONSTRAINT KSLU_CLU_FEEP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_CLU_FEE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_FEE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_FEE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_FEE_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(255)
        , OWNER VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_CLU_FEE_ATTR
    ADD CONSTRAINT KSLU_CLU_FEE_ATTRP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_CLU_IDENT
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_IDENT';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_IDENT CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_IDENT
(
      ID VARCHAR2(255)
        , CD VARCHAR2(255)
        , DIV VARCHAR2(255)
        , LVL VARCHAR2(255)
        , LNG_NAME VARCHAR2(255)
        , ORG_ID VARCHAR2(255)
        , SHRT_NAME VARCHAR2(255)
        , ST VARCHAR2(255)
        , SUFX_CD VARCHAR2(255)
        , TYPE VARCHAR2(255)
        , VARTN VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_CLU_IDENT
    ADD CONSTRAINT KSLU_CLU_IDENTP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_CLU_INSTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_INSTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_INSTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_INSTR
(
      ID VARCHAR2(255)
        , ORG_ID VARCHAR2(255)
        , PERS_ID VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_CLU_INSTR
    ADD CONSTRAINT KSLU_CLU_INSTRP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_CLU_INSTR_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_INSTR_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_INSTR_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_INSTR_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(255)
        , OWNER VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_CLU_INSTR_ATTR
    ADD CONSTRAINT KSLU_CLU_INSTR_ATTRP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_CLU_JN_ALT_ADMIN_ORG
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_JN_ALT_ADMIN_ORG';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_JN_ALT_ADMIN_ORG CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_JN_ALT_ADMIN_ORG
(
      CLU_ID VARCHAR2(255) NOT NULL
        , ALT_ORG_ID VARCHAR2(255) NOT NULL
    
    , CONSTRAINT SYS_C0099713 UNIQUE (ALT_ORG_ID)

)
/








-----------------------------------------------------------------------------
-- KSLU_CLU_JN_CAMP_LOC
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_JN_CAMP_LOC';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_JN_CAMP_LOC CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_JN_CAMP_LOC
(
      ID VARCHAR2(255)
        , CAMP_LOC VARCHAR2(255)
        , CLU_ID VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_CLU_JN_CAMP_LOC
    ADD CONSTRAINT KSLU_CLU_JN_CAMP_LOCP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_CLU_JN_CLU_ACCRED
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_JN_CLU_ACCRED';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_JN_CLU_ACCRED CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_JN_CLU_ACCRED
(
      CLU_ID VARCHAR2(255) NOT NULL
        , ACCRED_ORG_ID VARCHAR2(255) NOT NULL
    
    , CONSTRAINT SYS_C0099718 UNIQUE (ACCRED_ORG_ID)

)
/








-----------------------------------------------------------------------------
-- KSLU_CLU_JN_CLU_IDENT
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_JN_CLU_IDENT';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_JN_CLU_IDENT CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_JN_CLU_IDENT
(
      CLU_ID VARCHAR2(255) NOT NULL
        , ALT_CLU_ID VARCHAR2(255) NOT NULL
    
    , CONSTRAINT SYS_C0099721 UNIQUE (ALT_CLU_ID)

)
/








-----------------------------------------------------------------------------
-- KSLU_CLU_JN_CLU_INSTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_JN_CLU_INSTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_JN_CLU_INSTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_JN_CLU_INSTR
(
      CLU_ID VARCHAR2(255) NOT NULL
        , CLU_INSTR_ID VARCHAR2(255) NOT NULL
    
    , CONSTRAINT SYS_C0099724 UNIQUE (CLU_INSTR_ID)

)
/








-----------------------------------------------------------------------------
-- KSLU_CLU_JN_LU_STMT
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_JN_LU_STMT';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_JN_LU_STMT CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_JN_LU_STMT
(
      LU_STMT_ID VARCHAR2(255) NOT NULL
        , CLU_ID VARCHAR2(255) NOT NULL
    

)
/








-----------------------------------------------------------------------------
-- KSLU_CLU_JN_ORG
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_JN_ORG';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_JN_ORG CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_JN_ORG
(
      ID VARCHAR2(255)
        , ORG_ID VARCHAR2(255)
        , CLU_ID VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_CLU_JN_ORG
    ADD CONSTRAINT KSLU_CLU_JN_ORGP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_CLU_JN_SUBJ_ORG
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_JN_SUBJ_ORG';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_JN_SUBJ_ORG CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_JN_SUBJ_ORG
(
      ID VARCHAR2(255)
        , ORG_ID VARCHAR2(255)
        , CLU_ID VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_CLU_JN_SUBJ_ORG
    ADD CONSTRAINT KSLU_CLU_JN_SUBJ_ORGP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_CLU_PUBL
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_PUBL';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_PUBL CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_PUBL
(
      ID VARCHAR2(255)
        , END_CYCLE VARCHAR2(255)
        , START_CYCLE VARCHAR2(255)
        , ST VARCHAR2(255)
        , TYPE VARCHAR2(255)
        , PRI_INSTR_ID VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_CLU_PUBL
    ADD CONSTRAINT KSLU_CLU_PUBLP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_CLU_PUBL_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_PUBL_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_PUBL_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_PUBL_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(255)
        , OWNER VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_CLU_PUBL_ATTR
    ADD CONSTRAINT KSLU_CLU_PUBL_ATTRP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_CLU_PUBL_JN_CLU_INSTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_PUBL_JN_CLU_INSTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_PUBL_JN_CLU_INSTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_PUBL_JN_CLU_INSTR
(
      CLU_PUBL_ID VARCHAR2(255) NOT NULL
        , CLU_INSTR_ID VARCHAR2(255) NOT NULL
    
    , CONSTRAINT SYS_C0099737 UNIQUE (CLU_INSTR_ID)

)
/








-----------------------------------------------------------------------------
-- KSLU_CLU_SET
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_SET';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_SET CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_SET
(
      ID VARCHAR2(255)
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , VERSIONIND NUMBER(19) NOT NULL
        , CRIT_SET NUMBER(1)
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , NAME VARCHAR2(255)
        , RT_DESCR_ID VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_CLU_SET
    ADD CONSTRAINT KSLU_CLU_SETP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_CLU_SET_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_SET_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_SET_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_SET_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(255)
        , OWNER VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_CLU_SET_ATTR
    ADD CONSTRAINT KSLU_CLU_SET_ATTRP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_CLU_SET_JN_CLU
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_SET_JN_CLU';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_SET_JN_CLU CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_SET_JN_CLU
(
      CLU_SET_ID VARCHAR2(255) NOT NULL
        , CLU_ID VARCHAR2(255) NOT NULL
    

)
/








-----------------------------------------------------------------------------
-- KSLU_CLU_SET_JN_CLU_SET
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_SET_JN_CLU_SET';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_SET_JN_CLU_SET CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_SET_JN_CLU_SET
(
      CLU_SET_PARENT_ID VARCHAR2(255) NOT NULL
        , CLU_SET_CHILD_ID VARCHAR2(255) NOT NULL
    

)
/








-----------------------------------------------------------------------------
-- KSLU_LO
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_LO';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_LO CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_LO
(
      ID VARCHAR2(255)
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , VERSIONIND NUMBER(19) NOT NULL
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , NAME VARCHAR2(255)
        , ST VARCHAR2(255)
        , RT_DESCR_ID VARCHAR2(255)
        , LOHIRCHY_ID VARCHAR2(255)
        , LOTYPE_ID VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_LO
    ADD CONSTRAINT KSLU_LOP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_LO_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_LO_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_LO_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_LO_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(255)
        , OWNER VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_LO_ATTR
    ADD CONSTRAINT KSLU_LO_ATTRP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_LO_CATEGORY
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_LO_CATEGORY';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_LO_CATEGORY CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_LO_CATEGORY
(
      ID VARCHAR2(255)
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , VERSIONIND NUMBER(19) NOT NULL
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , NAME VARCHAR2(255)
        , RT_DESCR_ID VARCHAR2(255)
        , LOHIRCHY_ID VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_LO_CATEGORY
    ADD CONSTRAINT KSLU_LO_CATEGORYP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_LO_CATEGORY_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_LO_CATEGORY_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_LO_CATEGORY_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_LO_CATEGORY_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(255)
        , OWNER VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_LO_CATEGORY_ATTR
    ADD CONSTRAINT KSLU_LO_CATEGORY_ATTRP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_LO_HIERARCHY_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_LO_HIERARCHY_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_LO_HIERARCHY_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_LO_HIERARCHY_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(255)
        , OWNER VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_LO_HIERARCHY_ATTR
    ADD CONSTRAINT KSLU_LO_HIERARCHY_ATTRP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_LO_HIRCHY
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_LO_HIRCHY';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_LO_HIRCHY CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_LO_HIRCHY
(
      ID VARCHAR2(255)
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , VERSIONIND NUMBER(19) NOT NULL
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , NAME VARCHAR2(255)
        , RT_DESCR_ID VARCHAR2(255)
        , LO_ROOT_ID VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_LO_HIRCHY
    ADD CONSTRAINT KSLU_LO_HIRCHYP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_LO_JN_CLU
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_LO_JN_CLU';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_LO_JN_CLU CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_LO_JN_CLU
(
      ID VARCHAR2(255)
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , VERSIONIND NUMBER(19) NOT NULL
        , LO_ID VARCHAR2(255)
        , CLU_ID VARCHAR2(255)
    
    , CONSTRAINT SYS_C0099750 UNIQUE (LO_ID, CLU_ID)

)
/

ALTER TABLE KSLU_LO_JN_CLU
    ADD CONSTRAINT KSLU_LO_JN_CLUP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_LO_LO_EQUIV_RELTN
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_LO_LO_EQUIV_RELTN';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_LO_LO_EQUIV_RELTN CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_LO_LO_EQUIV_RELTN
(
      LO_ID VARCHAR2(255) NOT NULL
        , EQUIV_LO_ID VARCHAR2(255) NOT NULL
    

)
/








-----------------------------------------------------------------------------
-- KSLU_LO_LO_HIRCHY_RELTN
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_LO_LO_HIRCHY_RELTN';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_LO_LO_HIRCHY_RELTN CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_LO_LO_HIRCHY_RELTN
(
      PARENT_LO_ID VARCHAR2(255) NOT NULL
        , CHILD_LO_ID VARCHAR2(255) NOT NULL
    

)
/








-----------------------------------------------------------------------------
-- KSLU_LO_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_LO_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_LO_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_LO_TYPE
(
      ID VARCHAR2(255)
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , VERSIONIND NUMBER(19) NOT NULL
        , DESCR VARCHAR2(255)
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , NAME VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_LO_TYPE
    ADD CONSTRAINT KSLU_LO_TYPEP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_LO_TYPE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_LO_TYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_LO_TYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_LO_TYPE_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(255)
        , OWNER VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_LO_TYPE_ATTR
    ADD CONSTRAINT KSLU_LO_TYPE_ATTRP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_LR_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_LR_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_LR_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_LR_TYPE
(
      TYPE_KEY VARCHAR2(255)
        , TYPE_DESC VARCHAR2(2000)
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , NAME VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_LR_TYPE
    ADD CONSTRAINT KSLU_LR_TYPEP1
PRIMARY KEY (TYPE_KEY)
/







-----------------------------------------------------------------------------
-- KSLU_LR_TYPE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_LR_TYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_LR_TYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_LR_TYPE_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(255)
        , OWNER VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_LR_TYPE_ATTR
    ADD CONSTRAINT KSLU_LR_TYPE_ATTRP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_LUI
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_LUI';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_LUI CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_LUI
(
      ID VARCHAR2(255)
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , VERSIONIND NUMBER(19) NOT NULL
        , ATP_ID VARCHAR2(255)
        , EFF_DT TIMESTAMP
        , EXP_DT TIMESTAMP
        , LUI_CODE VARCHAR2(255)
        , MAX_SEATS NUMBER(10)
        , ST VARCHAR2(255)
        , CLU_ID VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_LUI
    ADD CONSTRAINT KSLU_LUIP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_LUILUI_RELTN
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_LUILUI_RELTN';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_LUILUI_RELTN CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_LUILUI_RELTN
(
      ID VARCHAR2(255)
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , VERSIONIND NUMBER(19) NOT NULL
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , ST VARCHAR2(255)
        , LULU_RELTN_TYPE_ID VARCHAR2(255)
        , LUI_ID VARCHAR2(255)
        , RELATED_LUI_ID VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_LUILUI_RELTN
    ADD CONSTRAINT KSLU_LUILUI_RELTNP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_LUILUI_RELTN_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_LUILUI_RELTN_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_LUILUI_RELTN_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_LUILUI_RELTN_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(255)
        , OWNER VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_LUILUI_RELTN_ATTR
    ADD CONSTRAINT KSLU_LUILUI_RELTN_ATTRP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_LUI_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_LUI_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_LUI_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_LUI_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(255)
        , OWNER VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_LUI_ATTR
    ADD CONSTRAINT KSLU_LUI_ATTRP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_LULU_RELTN_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_LULU_RELTN_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_LULU_RELTN_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_LULU_RELTN_TYPE
(
      ID VARCHAR2(255)
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , VERSIONIND NUMBER(19) NOT NULL
        , DESCR VARCHAR2(255)
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , NAME VARCHAR2(255)
        , REV_DESC VARCHAR2(255)
        , REV_NAME VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_LULU_RELTN_TYPE
    ADD CONSTRAINT KSLU_LULU_RELTN_TYPEP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_LULU_RELTN_TYPE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_LULU_RELTN_TYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_LULU_RELTN_TYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_LULU_RELTN_TYPE_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(255)
        , OWNER VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_LULU_RELTN_TYPE_ATTR
    ADD CONSTRAINT KSLU_LULU_RELTN_TYPE_ATTRP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_LULU_RELTN_TYPE_JN_LU_TYP
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_LULU_RELTN_TYPE_JN_LU_TYP';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_LULU_RELTN_TYPE_JN_LU_TYP CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_LULU_RELTN_TYPE_JN_LU_TYP
(
      LULU_RELTN_TYPE_ID VARCHAR2(255) NOT NULL
        , LU_TYPE_ID VARCHAR2(255) NOT NULL
    

)
/








-----------------------------------------------------------------------------
-- KSLU_LUTYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_LUTYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_LUTYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_LUTYPE
(
      TYPE_KEY VARCHAR2(255)
        , TYPE_DESC VARCHAR2(2000)
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , NAME VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_LUTYPE
    ADD CONSTRAINT KSLU_LUTYPEP1
PRIMARY KEY (TYPE_KEY)
/







-----------------------------------------------------------------------------
-- KSLU_LU_CODE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_LU_CODE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_LU_CODE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_LU_CODE
(
      ID VARCHAR2(255)
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , VERSIONIND NUMBER(19) NOT NULL
        , DESCR VARCHAR2(255)
        , TYPE VARCHAR2(255)
        , VALUE VARCHAR2(255)
        , CLU_ID VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_LU_CODE
    ADD CONSTRAINT KSLU_LU_CODEP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_LU_CODE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_LU_CODE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_LU_CODE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_LU_CODE_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(255)
        , OWNER VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_LU_CODE_ATTR
    ADD CONSTRAINT KSLU_LU_CODE_ATTRP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_LU_DOC_RELTN
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_LU_DOC_RELTN';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_LU_DOC_RELTN CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_LU_DOC_RELTN
(
      ID VARCHAR2(255)
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , VERSIONIND NUMBER(19) NOT NULL
        , DOC_ID VARCHAR2(255)
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , ST VARCHAR2(255)
        , TITLE VARCHAR2(255)
        , CLU_ID VARCHAR2(255)
        , DESCR VARCHAR2(255)
        , LU_DOC_RELTN_TYPE_ID VARCHAR2(255)
    
    , CONSTRAINT SYS_C0099782 UNIQUE (CLU_ID, DOC_ID)

)
/

ALTER TABLE KSLU_LU_DOC_RELTN
    ADD CONSTRAINT KSLU_LU_DOC_RELTNP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_LU_DOC_RELTN_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_LU_DOC_RELTN_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_LU_DOC_RELTN_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_LU_DOC_RELTN_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(255)
        , OWNER VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_LU_DOC_RELTN_ATTR
    ADD CONSTRAINT KSLU_LU_DOC_RELTN_ATTRP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_LU_DOC_RELTN_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_LU_DOC_RELTN_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_LU_DOC_RELTN_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_LU_DOC_RELTN_TYPE
(
      TYPE_KEY VARCHAR2(255)
        , TYPE_DESC VARCHAR2(2000)
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , NAME VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_LU_DOC_RELTN_TYPE
    ADD CONSTRAINT KSLU_LU_DOC_RELTN_TYPEP1
PRIMARY KEY (TYPE_KEY)
/







-----------------------------------------------------------------------------
-- KSLU_LU_DOC_RELTN_TYPE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_LU_DOC_RELTN_TYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_LU_DOC_RELTN_TYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_LU_DOC_RELTN_TYPE_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(255)
        , OWNER VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_LU_DOC_RELTN_TYPE_ATTR
    ADD CONSTRAINT KSLU_LU_DOC_RELTN_TYPE_ATTRP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_LU_STMT_TYPE_JN_LU_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_LU_STMT_TYPE_JN_LU_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_LU_STMT_TYPE_JN_LU_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_LU_STMT_TYPE_JN_LU_TYPE
(
      LU_STMT_TYPE_ID VARCHAR2(255) NOT NULL
        , LU_TYPE_ID VARCHAR2(255) NOT NULL
    

)
/








-----------------------------------------------------------------------------
-- KSLU_LU_TYPE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_LU_TYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_LU_TYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_LU_TYPE_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(255)
        , OWNER VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_LU_TYPE_ATTR
    ADD CONSTRAINT KSLU_LU_TYPE_ATTRP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_REQCOMTYP_JN_REQCOMFLDTYP
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_REQCOMTYP_JN_REQCOMFLDTYP';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_REQCOMTYP_JN_REQCOMFLDTYP CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_REQCOMTYP_JN_REQCOMFLDTYP
(
      REQ_COMP_TYPE_ID VARCHAR2(255) NOT NULL
        , REQ_COMP_FIELD_TYPE_ID VARCHAR2(255) NOT NULL
    

)
/








-----------------------------------------------------------------------------
-- KSLU_REQ_COM
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_REQ_COM';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_REQ_COM CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_REQ_COM
(
      ID VARCHAR2(255)
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , VERSIONIND NUMBER(19) NOT NULL
        , DESCR VARCHAR2(255)
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , ST VARCHAR2(255)
        , REQ_COM_TYPE_ID VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_REQ_COM
    ADD CONSTRAINT KSLU_REQ_COMP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_REQ_COM_FIELD
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_REQ_COM_FIELD';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_REQ_COM_FIELD CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_REQ_COM_FIELD
(
      ID VARCHAR2(255)
        , REQ_COM_FIELD_KEY VARCHAR2(255)
        , VALUE VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_REQ_COM_FIELD
    ADD CONSTRAINT KSLU_REQ_COM_FIELDP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_REQ_COM_FIELD_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_REQ_COM_FIELD_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_REQ_COM_FIELD_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_REQ_COM_FIELD_TYPE
(
      ID VARCHAR2(255)
        , DATA_TYPE VARCHAR2(255) NOT NULL
        , DESCR VARCHAR2(255) NOT NULL
        , INVALID_CHARS VARCHAR2(255)
        , MAX_LENGTH NUMBER(10)
        , MAX_OCCURS NUMBER(10)
        , MAX_VALUE VARCHAR2(255)
        , MIN_LENGTH NUMBER(10)
        , MIN_OCCURS NUMBER(10)
        , MIN_VALUE VARCHAR2(255)
        , NAME VARCHAR2(255) NOT NULL
        , READ_ONLY NUMBER(1) NOT NULL
        , VALID_CHARS VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_REQ_COM_FIELD_TYPE
    ADD CONSTRAINT KSLU_REQ_COM_FIELD_TYPEP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_REQ_COM_JN_REQ_COM_FIELD
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_REQ_COM_JN_REQ_COM_FIELD';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_REQ_COM_JN_REQ_COM_FIELD CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_REQ_COM_JN_REQ_COM_FIELD
(
      REQ_COM_ID VARCHAR2(255) NOT NULL
        , REQ_COM_FIELD_ID VARCHAR2(255) NOT NULL
    
    , CONSTRAINT SYS_C0099808 UNIQUE (REQ_COM_FIELD_ID)

)
/








-----------------------------------------------------------------------------
-- KSLU_REQ_COM_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_REQ_COM_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_REQ_COM_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_REQ_COM_TYPE
(
      TYPE_KEY VARCHAR2(255)
        , TYPE_DESC VARCHAR2(2000)
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , NAME VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_REQ_COM_TYPE
    ADD CONSTRAINT KSLU_REQ_COM_TYPEP1
PRIMARY KEY (TYPE_KEY)
/







-----------------------------------------------------------------------------
-- KSLU_REQ_COM_TYPE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_REQ_COM_TYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_REQ_COM_TYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_REQ_COM_TYPE_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(255)
        , OWNER VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_REQ_COM_TYPE_ATTR
    ADD CONSTRAINT KSLU_REQ_COM_TYPE_ATTRP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_REQ_COM_TYPE_NL_TMPL
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_REQ_COM_TYPE_NL_TMPL';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_REQ_COM_TYPE_NL_TMPL CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_REQ_COM_TYPE_NL_TMPL
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(255)
        , LANGUAGE VARCHAR2(2)
        , NL_USUAGE_TYPE_KEY VARCHAR2(255)
        , TEMPLATE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_REQ_COM_TYPE_NL_TMPL
    ADD CONSTRAINT KSLU_REQ_COM_TYPE_NL_TMPLP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_RSRC
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_RSRC';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_RSRC CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_RSRC
(
      ID VARCHAR2(255)
        , RSRC_TYPE_ID VARCHAR2(255)
        , CLU_ID VARCHAR2(255)
    
    , CONSTRAINT SYS_C0099817 UNIQUE (RSRC_TYPE_ID, CLU_ID)

)
/

ALTER TABLE KSLU_RSRC
    ADD CONSTRAINT KSLU_RSRCP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_STMT
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_STMT';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_STMT CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_STMT
(
      ID VARCHAR2(255)
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , VERSIONIND NUMBER(19) NOT NULL
        , DESCR VARCHAR2(255)
        , NAME VARCHAR2(255)
        , OPERATOR VARCHAR2(255)
        , ST VARCHAR2(255)
        , LU_STMT_TYPE_ID VARCHAR2(255)
        , PARENT_LU_STMT_ID VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_STMT
    ADD CONSTRAINT KSLU_STMTP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_STMT_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_STMT_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_STMT_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_STMT_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(255)
        , OWNER VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_STMT_ATTR
    ADD CONSTRAINT KSLU_STMT_ATTRP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_STMT_JN_REQ_COM
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_STMT_JN_REQ_COM';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_STMT_JN_REQ_COM CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_STMT_JN_REQ_COM
(
      LU_STMT_ID VARCHAR2(255) NOT NULL
        , REQ_COM_ID VARCHAR2(255) NOT NULL
    

)
/








-----------------------------------------------------------------------------
-- KSLU_STMT_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_STMT_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_STMT_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_STMT_TYPE
(
      TYPE_KEY VARCHAR2(255)
        , TYPE_DESC VARCHAR2(2000)
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , NAME VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_STMT_TYPE
    ADD CONSTRAINT KSLU_STMT_TYPEP1
PRIMARY KEY (TYPE_KEY)
/







-----------------------------------------------------------------------------
-- KSLU_STMT_TYPE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_STMT_TYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_STMT_TYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_STMT_TYPE_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(255)
        , OWNER VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_STMT_TYPE_ATTR
    ADD CONSTRAINT KSLU_STMT_TYPE_ATTRP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_STMT_TYPE_HEADER_TMPL
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_STMT_TYPE_HEADER_TMPL';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_STMT_TYPE_HEADER_TMPL CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_STMT_TYPE_HEADER_TMPL
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(255)
        , LANGUAGE VARCHAR2(2)
        , NL_USUAGE_TYPE_KEY VARCHAR2(255)
        , TEMPLATE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
    

)
/

ALTER TABLE KSLU_STMT_TYPE_HEADER_TMPL
    ADD CONSTRAINT KSLU_STMT_TYPE_HEADER_TMPLP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_STY_JN_LUSTY
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_STY_JN_LUSTY';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_STY_JN_LUSTY CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_STY_JN_LUSTY
(
      LU_STMT_TYPE_ID VARCHAR2(255) NOT NULL
        , CHLD_LU_STMT_TYPE_ID VARCHAR2(255) NOT NULL
    
    , CONSTRAINT SYS_C0099833 UNIQUE (CHLD_LU_STMT_TYPE_ID)

)
/








-----------------------------------------------------------------------------
-- KSLU_STY_JN_RQTY
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_STY_JN_RQTY';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_STY_JN_RQTY CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_STY_JN_RQTY
(
      LU_STMT_TYPE_ID VARCHAR2(255) NOT NULL
        , REQ_COM_TYPE_ID VARCHAR2(255) NOT NULL
    
    , CONSTRAINT SYS_C0099836 UNIQUE (REQ_COM_TYPE_ID)

)
/








-----------------------------------------------------------------------------
-- KS_LO_RICH_TEXT_T
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KS_LO_RICH_TEXT_T';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KS_LO_RICH_TEXT_T CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KS_LO_RICH_TEXT_T
(
      ID VARCHAR2(255)
        , FORMATTED VARCHAR2(2000)
        , PLAIN VARCHAR2(2000)
    

)
/

ALTER TABLE KS_LO_RICH_TEXT_T
    ADD CONSTRAINT KS_LO_RICH_TEXT_TP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KS_RICH_TEXT_T
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KS_RICH_TEXT_T';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KS_RICH_TEXT_T CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KS_RICH_TEXT_T
(
      ID VARCHAR2(255)
        , FORMATTED VARCHAR2(2000)
        , PLAIN VARCHAR2(2000)
    

)
/

ALTER TABLE KS_RICH_TEXT_T
    ADD CONSTRAINT KS_RICH_TEXT_TP1
PRIMARY KEY (ID)
/







-- -----------------------------------------------------------------------
-- CTX_CLASSES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CTX_CLASSES AS 

/

-- -----------------------------------------------------------------------
-- CTX_INDEX_SETS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CTX_INDEX_SETS AS 

/

-- -----------------------------------------------------------------------
-- CTX_INDEX_SET_INDEXES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CTX_INDEX_SET_INDEXES AS 

/

-- -----------------------------------------------------------------------
-- CTX_OBJECTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CTX_OBJECTS AS 

/

-- -----------------------------------------------------------------------
-- CTX_OBJECT_ATTRIBUTES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CTX_OBJECT_ATTRIBUTES AS 

/

-- -----------------------------------------------------------------------
-- CTX_OBJECT_ATTRIBUTE_LOV
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CTX_OBJECT_ATTRIBUTE_LOV AS 

/

-- -----------------------------------------------------------------------
-- CTX_PARAMETERS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CTX_PARAMETERS AS 

/

-- -----------------------------------------------------------------------
-- CTX_PREFERENCES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CTX_PREFERENCES AS 

/

-- -----------------------------------------------------------------------
-- CTX_PREFERENCE_VALUES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CTX_PREFERENCE_VALUES AS 

/

-- -----------------------------------------------------------------------
-- CTX_SECTIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CTX_SECTIONS AS 

/

-- -----------------------------------------------------------------------
-- CTX_SECTION_GROUPS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CTX_SECTION_GROUPS AS 

/

-- -----------------------------------------------------------------------
-- CTX_SQES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CTX_SQES AS 

/

-- -----------------------------------------------------------------------
-- CTX_STOPLISTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CTX_STOPLISTS AS 

/

-- -----------------------------------------------------------------------
-- CTX_STOPWORDS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CTX_STOPWORDS AS 

/

-- -----------------------------------------------------------------------
-- CTX_SUB_LEXERS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CTX_SUB_LEXERS AS 

/

-- -----------------------------------------------------------------------
-- CTX_THESAURI
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CTX_THESAURI AS 

/

-- -----------------------------------------------------------------------
-- CTX_THES_PHRASES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CTX_THES_PHRASES AS 

/

-- -----------------------------------------------------------------------
-- CTX_TRACE_VALUES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CTX_TRACE_VALUES AS 

/

-- -----------------------------------------------------------------------
-- CTX_USER_INDEXES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CTX_USER_INDEXES AS 

/

-- -----------------------------------------------------------------------
-- CTX_USER_INDEX_ERRORS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CTX_USER_INDEX_ERRORS AS 

/

-- -----------------------------------------------------------------------
-- CTX_USER_INDEX_OBJECTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CTX_USER_INDEX_OBJECTS AS 

/

-- -----------------------------------------------------------------------
-- CTX_USER_INDEX_PARTITIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CTX_USER_INDEX_PARTITIONS AS 

/

-- -----------------------------------------------------------------------
-- CTX_USER_INDEX_SETS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CTX_USER_INDEX_SETS AS 

/

-- -----------------------------------------------------------------------
-- CTX_USER_INDEX_SET_INDEXES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CTX_USER_INDEX_SET_INDEXES AS 

/

-- -----------------------------------------------------------------------
-- CTX_USER_INDEX_SUB_LEXERS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CTX_USER_INDEX_SUB_LEXERS AS 

/

-- -----------------------------------------------------------------------
-- CTX_USER_INDEX_SUB_LEXER_VALS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CTX_USER_INDEX_SUB_LEXER_VALS AS 

/

-- -----------------------------------------------------------------------
-- CTX_USER_INDEX_VALUES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CTX_USER_INDEX_VALUES AS 

/

-- -----------------------------------------------------------------------
-- CTX_USER_PENDING
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CTX_USER_PENDING AS 

/

-- -----------------------------------------------------------------------
-- CTX_USER_PREFERENCES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CTX_USER_PREFERENCES AS 

/

-- -----------------------------------------------------------------------
-- CTX_USER_PREFERENCE_VALUES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CTX_USER_PREFERENCE_VALUES AS 

/

-- -----------------------------------------------------------------------
-- CTX_USER_SECTIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CTX_USER_SECTIONS AS 

/

-- -----------------------------------------------------------------------
-- CTX_USER_SECTION_GROUPS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CTX_USER_SECTION_GROUPS AS 

/

-- -----------------------------------------------------------------------
-- CTX_USER_SQES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CTX_USER_SQES AS 

/

-- -----------------------------------------------------------------------
-- CTX_USER_STOPLISTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CTX_USER_STOPLISTS AS 

/

-- -----------------------------------------------------------------------
-- CTX_USER_STOPWORDS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CTX_USER_STOPWORDS AS 

/

-- -----------------------------------------------------------------------
-- CTX_USER_SUB_LEXERS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CTX_USER_SUB_LEXERS AS 

/

-- -----------------------------------------------------------------------
-- CTX_USER_THESAURI
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CTX_USER_THESAURI AS 

/

-- -----------------------------------------------------------------------
-- CTX_USER_THES_PHRASES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CTX_USER_THES_PHRASES AS 

/

-- -----------------------------------------------------------------------
-- DRV$DELETE
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW DRV$DELETE AS 

/

-- -----------------------------------------------------------------------
-- DRV$DELETE2
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW DRV$DELETE2 AS 

/

-- -----------------------------------------------------------------------
-- DRV$ONLINE_PENDING
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW DRV$ONLINE_PENDING AS 

/

-- -----------------------------------------------------------------------
-- DRV$PENDING
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW DRV$PENDING AS 

/

-- -----------------------------------------------------------------------
-- DRV$UNINDEXED
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW DRV$UNINDEXED AS 

/

-- -----------------------------------------------------------------------
-- DRV$UNINDEXED2
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW DRV$UNINDEXED2 AS 

/

-- -----------------------------------------------------------------------
-- DRV$WAITING
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW DRV$WAITING AS 

/

-- -----------------------------------------------------------------------
-- WWV_FLOW_CLICKTHRU_LOG
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW WWV_FLOW_CLICKTHRU_LOG AS 

/

-- -----------------------------------------------------------------------
-- WWV_FLOW_COLLECTIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW WWV_FLOW_COLLECTIONS AS 

/

-- -----------------------------------------------------------------------
-- WWV_FLOW_FILES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW WWV_FLOW_FILES AS 

/

-- -----------------------------------------------------------------------
-- WWV_FLOW_GROUP_USERS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW WWV_FLOW_GROUP_USERS AS 

/

-- -----------------------------------------------------------------------
-- WWV_FLOW_HOURS_12
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW WWV_FLOW_HOURS_12 AS 

/

-- -----------------------------------------------------------------------
-- WWV_FLOW_HOURS_24
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW WWV_FLOW_HOURS_24 AS 

/

-- -----------------------------------------------------------------------
-- WWV_FLOW_MINUTES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW WWV_FLOW_MINUTES AS 

/

-- -----------------------------------------------------------------------
-- WWV_FLOW_MINUTES_5
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW WWV_FLOW_MINUTES_5 AS 

/

-- -----------------------------------------------------------------------
-- WWV_FLOW_MONTHS_MON
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW WWV_FLOW_MONTHS_MON AS 

/

-- -----------------------------------------------------------------------
-- WWV_FLOW_MONTHS_MONTH
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW WWV_FLOW_MONTHS_MONTH AS 

/

-- -----------------------------------------------------------------------
-- WWV_FLOW_PLSQL_JOBS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW WWV_FLOW_PLSQL_JOBS AS 

/

-- -----------------------------------------------------------------------
-- WWV_FLOW_USERS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW WWV_FLOW_USERS AS 

/

-- -----------------------------------------------------------------------
-- WWV_FLOW_USER_ACTIVITY_LOG
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW WWV_FLOW_USER_ACTIVITY_LOG AS 

/

-- -----------------------------------------------------------------------
-- WWV_FLOW_USER_MAIL_LOG
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW WWV_FLOW_USER_MAIL_LOG AS 

/

-- -----------------------------------------------------------------------
-- WWV_FLOW_USER_MAIL_QUEUE
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW WWV_FLOW_USER_MAIL_QUEUE AS 

/

-- -----------------------------------------------------------------------
-- WWV_FLOW_YEARS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW WWV_FLOW_YEARS AS 

/

-- -----------------------------------------------------------------------
-- ALL_GEOMETRY_COLUMNS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_GEOMETRY_COLUMNS AS 

/

-- -----------------------------------------------------------------------
-- ALL_SDO_GEOM_METADATA
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SDO_GEOM_METADATA AS 

/

-- -----------------------------------------------------------------------
-- ALL_SDO_INDEX_INFO
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SDO_INDEX_INFO AS 

/

-- -----------------------------------------------------------------------
-- ALL_SDO_INDEX_METADATA
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SDO_INDEX_METADATA AS 

/

-- -----------------------------------------------------------------------
-- ALL_SDO_LRS_METADATA
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SDO_LRS_METADATA AS 

/

-- -----------------------------------------------------------------------
-- ALL_SDO_MAPS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SDO_MAPS AS 

/

-- -----------------------------------------------------------------------
-- ALL_SDO_STYLES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SDO_STYLES AS 

/

-- -----------------------------------------------------------------------
-- ALL_SDO_THEMES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SDO_THEMES AS 

/

-- -----------------------------------------------------------------------
-- ALL_SDO_TOPO_INFO
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SDO_TOPO_INFO AS 

/

-- -----------------------------------------------------------------------
-- ALL_SDO_TOPO_METADATA
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SDO_TOPO_METADATA AS 

/

-- -----------------------------------------------------------------------
-- CS_SRS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CS_SRS AS 

/

-- -----------------------------------------------------------------------
-- DBA_SDO_MAPS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW DBA_SDO_MAPS AS 

/

-- -----------------------------------------------------------------------
-- DBA_SDO_STYLES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW DBA_SDO_STYLES AS 

/

-- -----------------------------------------------------------------------
-- DBA_SDO_THEMES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW DBA_SDO_THEMES AS 

/

-- -----------------------------------------------------------------------
-- GEODETIC_SRIDS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW GEODETIC_SRIDS AS 

/

-- -----------------------------------------------------------------------
-- MY_SDO_INDEX_METADATA
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW MY_SDO_INDEX_METADATA AS 

/

-- -----------------------------------------------------------------------
-- SDO_ANGLE_UNITS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW SDO_ANGLE_UNITS AS 

/

-- -----------------------------------------------------------------------
-- SDO_AREA_UNITS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW SDO_AREA_UNITS AS 

/

-- -----------------------------------------------------------------------
-- SDO_AVAILABLE_ELEM_OPS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW SDO_AVAILABLE_ELEM_OPS AS 

/

-- -----------------------------------------------------------------------
-- SDO_AVAILABLE_NON_ELEM_OPS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW SDO_AVAILABLE_NON_ELEM_OPS AS 

/

-- -----------------------------------------------------------------------
-- SDO_AVAILABLE_OPS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW SDO_AVAILABLE_OPS AS 

/

-- -----------------------------------------------------------------------
-- SDO_COORD_REF_SYSTEM
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW SDO_COORD_REF_SYSTEM AS 

/

-- -----------------------------------------------------------------------
-- SDO_CRS_COMPOUND
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW SDO_CRS_COMPOUND AS 

/

-- -----------------------------------------------------------------------
-- SDO_CRS_ENGINEERING
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW SDO_CRS_ENGINEERING AS 

/

-- -----------------------------------------------------------------------
-- SDO_CRS_GEOCENTRIC
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW SDO_CRS_GEOCENTRIC AS 

/

-- -----------------------------------------------------------------------
-- SDO_CRS_GEOGRAPHIC2D
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW SDO_CRS_GEOGRAPHIC2D AS 

/

-- -----------------------------------------------------------------------
-- SDO_CRS_GEOGRAPHIC3D
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW SDO_CRS_GEOGRAPHIC3D AS 

/

-- -----------------------------------------------------------------------
-- SDO_CRS_PROJECTED
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW SDO_CRS_PROJECTED AS 

/

-- -----------------------------------------------------------------------
-- SDO_CRS_VERTICAL
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW SDO_CRS_VERTICAL AS 

/

-- -----------------------------------------------------------------------
-- SDO_DATUMS_OLD_FORMAT
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW SDO_DATUMS_OLD_FORMAT AS 

/

-- -----------------------------------------------------------------------
-- SDO_DATUM_ENGINEERING
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW SDO_DATUM_ENGINEERING AS 

/

-- -----------------------------------------------------------------------
-- SDO_DATUM_GEODETIC
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW SDO_DATUM_GEODETIC AS 

/

-- -----------------------------------------------------------------------
-- SDO_DATUM_VERTICAL
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW SDO_DATUM_VERTICAL AS 

/

-- -----------------------------------------------------------------------
-- SDO_DIST_UNITS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW SDO_DIST_UNITS AS 

/

-- -----------------------------------------------------------------------
-- SDO_ELLIPSOIDS_OLD_FORMAT
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW SDO_ELLIPSOIDS_OLD_FORMAT AS 

/

-- -----------------------------------------------------------------------
-- SDO_PROJECTIONS_OLD_FORMAT
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW SDO_PROJECTIONS_OLD_FORMAT AS 

/

-- -----------------------------------------------------------------------
-- SDO_RELATEMASK_TABLE
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW SDO_RELATEMASK_TABLE AS 

/

-- -----------------------------------------------------------------------
-- SDO_TOPO_TRANSACT_DATA$
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW SDO_TOPO_TRANSACT_DATA$ AS 

/

-- -----------------------------------------------------------------------
-- USER_GEOMETRY_COLUMNS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_GEOMETRY_COLUMNS AS 

/

-- -----------------------------------------------------------------------
-- USER_SDO_GEOM_METADATA
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SDO_GEOM_METADATA AS 

/

-- -----------------------------------------------------------------------
-- USER_SDO_INDEX_INFO
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SDO_INDEX_INFO AS 

/

-- -----------------------------------------------------------------------
-- USER_SDO_INDEX_METADATA
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SDO_INDEX_METADATA AS 

/

-- -----------------------------------------------------------------------
-- USER_SDO_LRS_METADATA
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SDO_LRS_METADATA AS 

/

-- -----------------------------------------------------------------------
-- USER_SDO_MAPS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SDO_MAPS AS 

/

-- -----------------------------------------------------------------------
-- USER_SDO_STYLES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SDO_STYLES AS 

/

-- -----------------------------------------------------------------------
-- USER_SDO_THEMES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SDO_THEMES AS 

/

-- -----------------------------------------------------------------------
-- USER_SDO_TOPO_INFO
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SDO_TOPO_INFO AS 

/

-- -----------------------------------------------------------------------
-- USER_SDO_TOPO_METADATA
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SDO_TOPO_METADATA AS 

/

-- -----------------------------------------------------------------------
-- ALL_ALL_TABLES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_ALL_TABLES AS 

/

-- -----------------------------------------------------------------------
-- ALL_APPLY
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_APPLY AS 

/

-- -----------------------------------------------------------------------
-- ALL_APPLY_CONFLICT_COLUMNS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_APPLY_CONFLICT_COLUMNS AS 

/

-- -----------------------------------------------------------------------
-- ALL_APPLY_DML_HANDLERS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_APPLY_DML_HANDLERS AS 

/

-- -----------------------------------------------------------------------
-- ALL_APPLY_ENQUEUE
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_APPLY_ENQUEUE AS 

/

-- -----------------------------------------------------------------------
-- ALL_APPLY_ERROR
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_APPLY_ERROR AS 

/

-- -----------------------------------------------------------------------
-- ALL_APPLY_EXECUTE
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_APPLY_EXECUTE AS 

/

-- -----------------------------------------------------------------------
-- ALL_APPLY_KEY_COLUMNS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_APPLY_KEY_COLUMNS AS 

/

-- -----------------------------------------------------------------------
-- ALL_APPLY_PARAMETERS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_APPLY_PARAMETERS AS 

/

-- -----------------------------------------------------------------------
-- ALL_APPLY_PROGRESS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_APPLY_PROGRESS AS 

/

-- -----------------------------------------------------------------------
-- ALL_APPLY_TABLE_COLUMNS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_APPLY_TABLE_COLUMNS AS 

/

-- -----------------------------------------------------------------------
-- ALL_ARGUMENTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_ARGUMENTS AS 

/

-- -----------------------------------------------------------------------
-- ALL_ASSOCIATIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_ASSOCIATIONS AS 

/

-- -----------------------------------------------------------------------
-- ALL_AWS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_AWS AS 

/

-- -----------------------------------------------------------------------
-- ALL_AW_PS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_AW_PS AS 

/

-- -----------------------------------------------------------------------
-- ALL_BASE_TABLE_MVIEWS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_BASE_TABLE_MVIEWS AS 

/

-- -----------------------------------------------------------------------
-- ALL_CAPTURE
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_CAPTURE AS 

/

-- -----------------------------------------------------------------------
-- ALL_CAPTURE_EXTRA_ATTRIBUTES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_CAPTURE_EXTRA_ATTRIBUTES AS 

/

-- -----------------------------------------------------------------------
-- ALL_CAPTURE_PARAMETERS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_CAPTURE_PARAMETERS AS 

/

-- -----------------------------------------------------------------------
-- ALL_CAPTURE_PREPARED_DATABASE
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_CAPTURE_PREPARED_DATABASE AS 

/

-- -----------------------------------------------------------------------
-- ALL_CAPTURE_PREPARED_SCHEMAS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_CAPTURE_PREPARED_SCHEMAS AS 

/

-- -----------------------------------------------------------------------
-- ALL_CAPTURE_PREPARED_TABLES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_CAPTURE_PREPARED_TABLES AS 

/

-- -----------------------------------------------------------------------
-- ALL_CATALOG
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_CATALOG AS 

/

-- -----------------------------------------------------------------------
-- ALL_CLUSTERS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_CLUSTERS AS 

/

-- -----------------------------------------------------------------------
-- ALL_CLUSTER_HASH_EXPRESSIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_CLUSTER_HASH_EXPRESSIONS AS 

/

-- -----------------------------------------------------------------------
-- ALL_COLL_TYPES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_COLL_TYPES AS 

/

-- -----------------------------------------------------------------------
-- ALL_COL_COMMENTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_COL_COMMENTS AS 

/

-- -----------------------------------------------------------------------
-- ALL_COL_PRIVS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_COL_PRIVS AS 

/

-- -----------------------------------------------------------------------
-- ALL_COL_PRIVS_MADE
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_COL_PRIVS_MADE AS 

/

-- -----------------------------------------------------------------------
-- ALL_COL_PRIVS_RECD
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_COL_PRIVS_RECD AS 

/

-- -----------------------------------------------------------------------
-- ALL_CONSTRAINTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_CONSTRAINTS AS 

/

-- -----------------------------------------------------------------------
-- ALL_CONS_COLUMNS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_CONS_COLUMNS AS 

/

-- -----------------------------------------------------------------------
-- ALL_CONS_OBJ_COLUMNS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_CONS_OBJ_COLUMNS AS 

/

-- -----------------------------------------------------------------------
-- ALL_CONTEXT
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_CONTEXT AS 

/

-- -----------------------------------------------------------------------
-- ALL_DB_LINKS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_DB_LINKS AS 

/

-- -----------------------------------------------------------------------
-- ALL_DEF_AUDIT_OPTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_DEF_AUDIT_OPTS AS 

/

-- -----------------------------------------------------------------------
-- ALL_DEPENDENCIES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_DEPENDENCIES AS 

/

-- -----------------------------------------------------------------------
-- ALL_DEQUEUE_QUEUES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_DEQUEUE_QUEUES AS 

/

-- -----------------------------------------------------------------------
-- ALL_DIMENSIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_DIMENSIONS AS 

/

-- -----------------------------------------------------------------------
-- ALL_DIM_ATTRIBUTES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_DIM_ATTRIBUTES AS 

/

-- -----------------------------------------------------------------------
-- ALL_DIM_CHILD_OF
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_DIM_CHILD_OF AS 

/

-- -----------------------------------------------------------------------
-- ALL_DIM_HIERARCHIES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_DIM_HIERARCHIES AS 

/

-- -----------------------------------------------------------------------
-- ALL_DIM_JOIN_KEY
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_DIM_JOIN_KEY AS 

/

-- -----------------------------------------------------------------------
-- ALL_DIM_LEVELS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_DIM_LEVELS AS 

/

-- -----------------------------------------------------------------------
-- ALL_DIM_LEVEL_KEY
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_DIM_LEVEL_KEY AS 

/

-- -----------------------------------------------------------------------
-- ALL_DIRECTORIES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_DIRECTORIES AS 

/

-- -----------------------------------------------------------------------
-- ALL_ENCRYPTED_COLUMNS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_ENCRYPTED_COLUMNS AS 

/

-- -----------------------------------------------------------------------
-- ALL_ERRORS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_ERRORS AS 

/

-- -----------------------------------------------------------------------
-- ALL_EVALUATION_CONTEXTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_EVALUATION_CONTEXTS AS 

/

-- -----------------------------------------------------------------------
-- ALL_EVALUATION_CONTEXT_TABLES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_EVALUATION_CONTEXT_TABLES AS 

/

-- -----------------------------------------------------------------------
-- ALL_EVALUATION_CONTEXT_VARS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_EVALUATION_CONTEXT_VARS AS 

/

-- -----------------------------------------------------------------------
-- ALL_EXTERNAL_LOCATIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_EXTERNAL_LOCATIONS AS 

/

-- -----------------------------------------------------------------------
-- ALL_EXTERNAL_TABLES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_EXTERNAL_TABLES AS 

/

-- -----------------------------------------------------------------------
-- ALL_FILE_GROUPS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_FILE_GROUPS AS 

/

-- -----------------------------------------------------------------------
-- ALL_FILE_GROUP_EXPORT_INFO
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_FILE_GROUP_EXPORT_INFO AS 

/

-- -----------------------------------------------------------------------
-- ALL_FILE_GROUP_FILES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_FILE_GROUP_FILES AS 

/

-- -----------------------------------------------------------------------
-- ALL_FILE_GROUP_TABLES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_FILE_GROUP_TABLES AS 

/

-- -----------------------------------------------------------------------
-- ALL_FILE_GROUP_TABLESPACES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_FILE_GROUP_TABLESPACES AS 

/

-- -----------------------------------------------------------------------
-- ALL_FILE_GROUP_VERSIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_FILE_GROUP_VERSIONS AS 

/

-- -----------------------------------------------------------------------
-- ALL_INDEXES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_INDEXES AS 

/

-- -----------------------------------------------------------------------
-- ALL_INDEXTYPES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_INDEXTYPES AS 

/

-- -----------------------------------------------------------------------
-- ALL_INDEXTYPE_ARRAYTYPES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_INDEXTYPE_ARRAYTYPES AS 

/

-- -----------------------------------------------------------------------
-- ALL_INDEXTYPE_COMMENTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_INDEXTYPE_COMMENTS AS 

/

-- -----------------------------------------------------------------------
-- ALL_INDEXTYPE_OPERATORS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_INDEXTYPE_OPERATORS AS 

/

-- -----------------------------------------------------------------------
-- ALL_IND_COLUMNS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_IND_COLUMNS AS 

/

-- -----------------------------------------------------------------------
-- ALL_IND_EXPRESSIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_IND_EXPRESSIONS AS 

/

-- -----------------------------------------------------------------------
-- ALL_IND_PARTITIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_IND_PARTITIONS AS 

/

-- -----------------------------------------------------------------------
-- ALL_IND_STATISTICS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_IND_STATISTICS AS 

/

-- -----------------------------------------------------------------------
-- ALL_IND_SUBPARTITIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_IND_SUBPARTITIONS AS 

/

-- -----------------------------------------------------------------------
-- ALL_INTERNAL_TRIGGERS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_INTERNAL_TRIGGERS AS 

/

-- -----------------------------------------------------------------------
-- ALL_JOIN_IND_COLUMNS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_JOIN_IND_COLUMNS AS 

/

-- -----------------------------------------------------------------------
-- ALL_LIBRARIES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_LIBRARIES AS 

/

-- -----------------------------------------------------------------------
-- ALL_LOBS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_LOBS AS 

/

-- -----------------------------------------------------------------------
-- ALL_LOB_PARTITIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_LOB_PARTITIONS AS 

/

-- -----------------------------------------------------------------------
-- ALL_LOB_SUBPARTITIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_LOB_SUBPARTITIONS AS 

/

-- -----------------------------------------------------------------------
-- ALL_LOB_TEMPLATES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_LOB_TEMPLATES AS 

/

-- -----------------------------------------------------------------------
-- ALL_LOG_GROUPS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_LOG_GROUPS AS 

/

-- -----------------------------------------------------------------------
-- ALL_LOG_GROUP_COLUMNS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_LOG_GROUP_COLUMNS AS 

/

-- -----------------------------------------------------------------------
-- ALL_METHOD_PARAMS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_METHOD_PARAMS AS 

/

-- -----------------------------------------------------------------------
-- ALL_METHOD_RESULTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_METHOD_RESULTS AS 

/

-- -----------------------------------------------------------------------
-- ALL_MVIEWS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_MVIEWS AS 

/

-- -----------------------------------------------------------------------
-- ALL_MVIEW_AGGREGATES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_MVIEW_AGGREGATES AS 

/

-- -----------------------------------------------------------------------
-- ALL_MVIEW_ANALYSIS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_MVIEW_ANALYSIS AS 

/

-- -----------------------------------------------------------------------
-- ALL_MVIEW_COMMENTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_MVIEW_COMMENTS AS 

/

-- -----------------------------------------------------------------------
-- ALL_MVIEW_DETAIL_RELATIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_MVIEW_DETAIL_RELATIONS AS 

/

-- -----------------------------------------------------------------------
-- ALL_MVIEW_JOINS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_MVIEW_JOINS AS 

/

-- -----------------------------------------------------------------------
-- ALL_MVIEW_KEYS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_MVIEW_KEYS AS 

/

-- -----------------------------------------------------------------------
-- ALL_MVIEW_LOGS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_MVIEW_LOGS AS 

/

-- -----------------------------------------------------------------------
-- ALL_MVIEW_REFRESH_TIMES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_MVIEW_REFRESH_TIMES AS 

/

-- -----------------------------------------------------------------------
-- ALL_NESTED_TABLES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_NESTED_TABLES AS 

/

-- -----------------------------------------------------------------------
-- ALL_NESTED_TABLE_COLS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_NESTED_TABLE_COLS AS 

/

-- -----------------------------------------------------------------------
-- ALL_OBJECTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_OBJECTS AS 

/

-- -----------------------------------------------------------------------
-- ALL_OBJECT_TABLES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_OBJECT_TABLES AS 

/

-- -----------------------------------------------------------------------
-- ALL_OBJ_COLATTRS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_OBJ_COLATTRS AS 

/

-- -----------------------------------------------------------------------
-- ALL_OPANCILLARY
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_OPANCILLARY AS 

/

-- -----------------------------------------------------------------------
-- ALL_OPARGUMENTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_OPARGUMENTS AS 

/

-- -----------------------------------------------------------------------
-- ALL_OPBINDINGS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_OPBINDINGS AS 

/

-- -----------------------------------------------------------------------
-- ALL_OPERATORS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_OPERATORS AS 

/

-- -----------------------------------------------------------------------
-- ALL_OPERATOR_COMMENTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_OPERATOR_COMMENTS AS 

/

-- -----------------------------------------------------------------------
-- ALL_PARTIAL_DROP_TABS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_PARTIAL_DROP_TABS AS 

/

-- -----------------------------------------------------------------------
-- ALL_PART_COL_STATISTICS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_PART_COL_STATISTICS AS 

/

-- -----------------------------------------------------------------------
-- ALL_PART_HISTOGRAMS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_PART_HISTOGRAMS AS 

/

-- -----------------------------------------------------------------------
-- ALL_PART_INDEXES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_PART_INDEXES AS 

/

-- -----------------------------------------------------------------------
-- ALL_PART_KEY_COLUMNS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_PART_KEY_COLUMNS AS 

/

-- -----------------------------------------------------------------------
-- ALL_PART_LOBS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_PART_LOBS AS 

/

-- -----------------------------------------------------------------------
-- ALL_PART_TABLES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_PART_TABLES AS 

/

-- -----------------------------------------------------------------------
-- ALL_PENDING_CONV_TABLES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_PENDING_CONV_TABLES AS 

/

-- -----------------------------------------------------------------------
-- ALL_PLSQL_OBJECT_SETTINGS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_PLSQL_OBJECT_SETTINGS AS 

/

-- -----------------------------------------------------------------------
-- ALL_POLICIES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_POLICIES AS 

/

-- -----------------------------------------------------------------------
-- ALL_POLICY_CONTEXTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_POLICY_CONTEXTS AS 

/

-- -----------------------------------------------------------------------
-- ALL_POLICY_GROUPS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_POLICY_GROUPS AS 

/

-- -----------------------------------------------------------------------
-- ALL_PROBE_OBJECTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_PROBE_OBJECTS AS 

/

-- -----------------------------------------------------------------------
-- ALL_PROCEDURES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_PROCEDURES AS 

/

-- -----------------------------------------------------------------------
-- ALL_PROPAGATION
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_PROPAGATION AS 

/

-- -----------------------------------------------------------------------
-- ALL_PUBLISHED_COLUMNS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_PUBLISHED_COLUMNS AS 

/

-- -----------------------------------------------------------------------
-- ALL_QUEUES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_QUEUES AS 

/

-- -----------------------------------------------------------------------
-- ALL_QUEUE_PUBLISHERS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_QUEUE_PUBLISHERS AS 

/

-- -----------------------------------------------------------------------
-- ALL_QUEUE_SUBSCRIBERS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_QUEUE_SUBSCRIBERS AS 

/

-- -----------------------------------------------------------------------
-- ALL_QUEUE_TABLES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_QUEUE_TABLES AS 

/

-- -----------------------------------------------------------------------
-- ALL_REFRESH
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REFRESH AS 

/

-- -----------------------------------------------------------------------
-- ALL_REFRESH_CHILDREN
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REFRESH_CHILDREN AS 

/

-- -----------------------------------------------------------------------
-- ALL_REFRESH_DEPENDENCIES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REFRESH_DEPENDENCIES AS 

/

-- -----------------------------------------------------------------------
-- ALL_REFS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REFS AS 

/

-- -----------------------------------------------------------------------
-- ALL_REGISTERED_MVIEWS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REGISTERED_MVIEWS AS 

/

-- -----------------------------------------------------------------------
-- ALL_REGISTERED_SNAPSHOTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REGISTERED_SNAPSHOTS AS 

/

-- -----------------------------------------------------------------------
-- ALL_REGISTRY_BANNERS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REGISTRY_BANNERS AS 

/

-- -----------------------------------------------------------------------
-- ALL_REPAUDIT_ATTRIBUTE
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REPAUDIT_ATTRIBUTE AS 

/

-- -----------------------------------------------------------------------
-- ALL_REPAUDIT_COLUMN
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REPAUDIT_COLUMN AS 

/

-- -----------------------------------------------------------------------
-- ALL_REPCAT
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REPCAT AS 

/

-- -----------------------------------------------------------------------
-- ALL_REPCATLOG
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REPCATLOG AS 

/

-- -----------------------------------------------------------------------
-- ALL_REPCAT_REFRESH_TEMPLATES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REPCAT_REFRESH_TEMPLATES AS 

/

-- -----------------------------------------------------------------------
-- ALL_REPCAT_TEMPLATE_OBJECTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REPCAT_TEMPLATE_OBJECTS AS 

/

-- -----------------------------------------------------------------------
-- ALL_REPCAT_TEMPLATE_PARMS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REPCAT_TEMPLATE_PARMS AS 

/

-- -----------------------------------------------------------------------
-- ALL_REPCAT_TEMPLATE_SITES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REPCAT_TEMPLATE_SITES AS 

/

-- -----------------------------------------------------------------------
-- ALL_REPCAT_USER_AUTHORIZATIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REPCAT_USER_AUTHORIZATIONS AS 

/

-- -----------------------------------------------------------------------
-- ALL_REPCAT_USER_PARM_VALUES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REPCAT_USER_PARM_VALUES AS 

/

-- -----------------------------------------------------------------------
-- ALL_REPCOLUMN
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REPCOLUMN AS 

/

-- -----------------------------------------------------------------------
-- ALL_REPCOLUMN_GROUP
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REPCOLUMN_GROUP AS 

/

-- -----------------------------------------------------------------------
-- ALL_REPCONFLICT
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REPCONFLICT AS 

/

-- -----------------------------------------------------------------------
-- ALL_REPDDL
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REPDDL AS 

/

-- -----------------------------------------------------------------------
-- ALL_REPFLAVORS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REPFLAVORS AS 

/

-- -----------------------------------------------------------------------
-- ALL_REPFLAVOR_COLUMNS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REPFLAVOR_COLUMNS AS 

/

-- -----------------------------------------------------------------------
-- ALL_REPFLAVOR_OBJECTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REPFLAVOR_OBJECTS AS 

/

-- -----------------------------------------------------------------------
-- ALL_REPGENERATED
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REPGENERATED AS 

/

-- -----------------------------------------------------------------------
-- ALL_REPGENOBJECTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REPGENOBJECTS AS 

/

-- -----------------------------------------------------------------------
-- ALL_REPGROUP
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REPGROUP AS 

/

-- -----------------------------------------------------------------------
-- ALL_REPGROUPED_COLUMN
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REPGROUPED_COLUMN AS 

/

-- -----------------------------------------------------------------------
-- ALL_REPGROUP_PRIVILEGES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REPGROUP_PRIVILEGES AS 

/

-- -----------------------------------------------------------------------
-- ALL_REPKEY_COLUMNS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REPKEY_COLUMNS AS 

/

-- -----------------------------------------------------------------------
-- ALL_REPOBJECT
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REPOBJECT AS 

/

-- -----------------------------------------------------------------------
-- ALL_REPPARAMETER_COLUMN
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REPPARAMETER_COLUMN AS 

/

-- -----------------------------------------------------------------------
-- ALL_REPPRIORITY
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REPPRIORITY AS 

/

-- -----------------------------------------------------------------------
-- ALL_REPPRIORITY_GROUP
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REPPRIORITY_GROUP AS 

/

-- -----------------------------------------------------------------------
-- ALL_REPPROP
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REPPROP AS 

/

-- -----------------------------------------------------------------------
-- ALL_REPRESOLUTION
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REPRESOLUTION AS 

/

-- -----------------------------------------------------------------------
-- ALL_REPRESOLUTION_METHOD
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REPRESOLUTION_METHOD AS 

/

-- -----------------------------------------------------------------------
-- ALL_REPRESOLUTION_STATISTICS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REPRESOLUTION_STATISTICS AS 

/

-- -----------------------------------------------------------------------
-- ALL_REPRESOL_STATS_CONTROL
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REPRESOL_STATS_CONTROL AS 

/

-- -----------------------------------------------------------------------
-- ALL_REPSCHEMA
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REPSCHEMA AS 

/

-- -----------------------------------------------------------------------
-- ALL_REPSITES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REPSITES AS 

/

-- -----------------------------------------------------------------------
-- ALL_REWRITE_EQUIVALENCES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_REWRITE_EQUIVALENCES AS 

/

-- -----------------------------------------------------------------------
-- ALL_RULES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_RULES AS 

/

-- -----------------------------------------------------------------------
-- ALL_RULESETS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_RULESETS AS 

/

-- -----------------------------------------------------------------------
-- ALL_RULE_SETS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_RULE_SETS AS 

/

-- -----------------------------------------------------------------------
-- ALL_RULE_SET_RULES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_RULE_SET_RULES AS 

/

-- -----------------------------------------------------------------------
-- ALL_SCHEDULER_CHAINS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SCHEDULER_CHAINS AS 

/

-- -----------------------------------------------------------------------
-- ALL_SCHEDULER_CHAIN_RULES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SCHEDULER_CHAIN_RULES AS 

/

-- -----------------------------------------------------------------------
-- ALL_SCHEDULER_CHAIN_STEPS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SCHEDULER_CHAIN_STEPS AS 

/

-- -----------------------------------------------------------------------
-- ALL_SCHEDULER_GLOBAL_ATTRIBUTE
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SCHEDULER_GLOBAL_ATTRIBUTE AS 

/

-- -----------------------------------------------------------------------
-- ALL_SCHEDULER_JOBS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SCHEDULER_JOBS AS 

/

-- -----------------------------------------------------------------------
-- ALL_SCHEDULER_JOB_ARGS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SCHEDULER_JOB_ARGS AS 

/

-- -----------------------------------------------------------------------
-- ALL_SCHEDULER_JOB_CLASSES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SCHEDULER_JOB_CLASSES AS 

/

-- -----------------------------------------------------------------------
-- ALL_SCHEDULER_JOB_LOG
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SCHEDULER_JOB_LOG AS 

/

-- -----------------------------------------------------------------------
-- ALL_SCHEDULER_JOB_RUN_DETAILS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SCHEDULER_JOB_RUN_DETAILS AS 

/

-- -----------------------------------------------------------------------
-- ALL_SCHEDULER_PROGRAMS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SCHEDULER_PROGRAMS AS 

/

-- -----------------------------------------------------------------------
-- ALL_SCHEDULER_PROGRAM_ARGS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SCHEDULER_PROGRAM_ARGS AS 

/

-- -----------------------------------------------------------------------
-- ALL_SCHEDULER_RUNNING_CHAINS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SCHEDULER_RUNNING_CHAINS AS 

/

-- -----------------------------------------------------------------------
-- ALL_SCHEDULER_RUNNING_JOBS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SCHEDULER_RUNNING_JOBS AS 

/

-- -----------------------------------------------------------------------
-- ALL_SCHEDULER_SCHEDULES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SCHEDULER_SCHEDULES AS 

/

-- -----------------------------------------------------------------------
-- ALL_SCHEDULER_WINDOWS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SCHEDULER_WINDOWS AS 

/

-- -----------------------------------------------------------------------
-- ALL_SCHEDULER_WINDOW_DETAILS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SCHEDULER_WINDOW_DETAILS AS 

/

-- -----------------------------------------------------------------------
-- ALL_SCHEDULER_WINDOW_GROUPS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SCHEDULER_WINDOW_GROUPS AS 

/

-- -----------------------------------------------------------------------
-- ALL_SCHEDULER_WINDOW_LOG
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SCHEDULER_WINDOW_LOG AS 

/

-- -----------------------------------------------------------------------
-- ALL_SCHEDULER_WINGROUP_MEMBERS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SCHEDULER_WINGROUP_MEMBERS AS 

/

-- -----------------------------------------------------------------------
-- ALL_SECONDARY_OBJECTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SECONDARY_OBJECTS AS 

/

-- -----------------------------------------------------------------------
-- ALL_SEC_RELEVANT_COLS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SEC_RELEVANT_COLS AS 

/

-- -----------------------------------------------------------------------
-- ALL_SEQUENCES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SEQUENCES AS 

/

-- -----------------------------------------------------------------------
-- ALL_SNAPSHOTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SNAPSHOTS AS 

/

-- -----------------------------------------------------------------------
-- ALL_SNAPSHOT_LOGS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SNAPSHOT_LOGS AS 

/

-- -----------------------------------------------------------------------
-- ALL_SOURCE
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SOURCE AS 

/

-- -----------------------------------------------------------------------
-- ALL_SOURCE_TABLES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SOURCE_TABLES AS 

/

-- -----------------------------------------------------------------------
-- ALL_SQLJ_TYPES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SQLJ_TYPES AS 

/

-- -----------------------------------------------------------------------
-- ALL_SQLJ_TYPE_ATTRS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SQLJ_TYPE_ATTRS AS 

/

-- -----------------------------------------------------------------------
-- ALL_SQLJ_TYPE_METHODS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SQLJ_TYPE_METHODS AS 

/

-- -----------------------------------------------------------------------
-- ALL_SQLSET
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SQLSET AS 

/

-- -----------------------------------------------------------------------
-- ALL_SQLSET_BINDS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SQLSET_BINDS AS 

/

-- -----------------------------------------------------------------------
-- ALL_SQLSET_PLANS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SQLSET_PLANS AS 

/

-- -----------------------------------------------------------------------
-- ALL_SQLSET_REFERENCES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SQLSET_REFERENCES AS 

/

-- -----------------------------------------------------------------------
-- ALL_SQLSET_STATEMENTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SQLSET_STATEMENTS AS 

/

-- -----------------------------------------------------------------------
-- ALL_STORED_SETTINGS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_STORED_SETTINGS AS 

/

-- -----------------------------------------------------------------------
-- ALL_STREAMS_GLOBAL_RULES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_STREAMS_GLOBAL_RULES AS 

/

-- -----------------------------------------------------------------------
-- ALL_STREAMS_MESSAGE_CONSUMERS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_STREAMS_MESSAGE_CONSUMERS AS 

/

-- -----------------------------------------------------------------------
-- ALL_STREAMS_MESSAGE_RULES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_STREAMS_MESSAGE_RULES AS 

/

-- -----------------------------------------------------------------------
-- ALL_STREAMS_NEWLY_SUPPORTED
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_STREAMS_NEWLY_SUPPORTED AS 

/

-- -----------------------------------------------------------------------
-- ALL_STREAMS_RULES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_STREAMS_RULES AS 

/

-- -----------------------------------------------------------------------
-- ALL_STREAMS_SCHEMA_RULES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_STREAMS_SCHEMA_RULES AS 

/

-- -----------------------------------------------------------------------
-- ALL_STREAMS_TABLE_RULES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_STREAMS_TABLE_RULES AS 

/

-- -----------------------------------------------------------------------
-- ALL_STREAMS_TRANSFORM_FUNCTION
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_STREAMS_TRANSFORM_FUNCTION AS 

/

-- -----------------------------------------------------------------------
-- ALL_STREAMS_UNSUPPORTED
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_STREAMS_UNSUPPORTED AS 

/

-- -----------------------------------------------------------------------
-- ALL_SUBPARTITION_TEMPLATES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SUBPARTITION_TEMPLATES AS 

/

-- -----------------------------------------------------------------------
-- ALL_SUBPART_COL_STATISTICS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SUBPART_COL_STATISTICS AS 

/

-- -----------------------------------------------------------------------
-- ALL_SUBPART_HISTOGRAMS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SUBPART_HISTOGRAMS AS 

/

-- -----------------------------------------------------------------------
-- ALL_SUBPART_KEY_COLUMNS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SUBPART_KEY_COLUMNS AS 

/

-- -----------------------------------------------------------------------
-- ALL_SUBSCRIBED_COLUMNS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SUBSCRIBED_COLUMNS AS 

/

-- -----------------------------------------------------------------------
-- ALL_SUBSCRIBED_TABLES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SUBSCRIBED_TABLES AS 

/

-- -----------------------------------------------------------------------
-- ALL_SUBSCRIPTIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SUBSCRIPTIONS AS 

/

-- -----------------------------------------------------------------------
-- ALL_SUMDELTA
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SUMDELTA AS 

/

-- -----------------------------------------------------------------------
-- ALL_SUMMARIES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SUMMARIES AS 

/

-- -----------------------------------------------------------------------
-- ALL_SUMMARY_AGGREGATES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SUMMARY_AGGREGATES AS 

/

-- -----------------------------------------------------------------------
-- ALL_SUMMARY_DETAIL_TABLES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SUMMARY_DETAIL_TABLES AS 

/

-- -----------------------------------------------------------------------
-- ALL_SUMMARY_JOINS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SUMMARY_JOINS AS 

/

-- -----------------------------------------------------------------------
-- ALL_SUMMARY_KEYS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SUMMARY_KEYS AS 

/

-- -----------------------------------------------------------------------
-- ALL_SYNONYMS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_SYNONYMS AS 

/

-- -----------------------------------------------------------------------
-- ALL_TABLES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_TABLES AS 

/

-- -----------------------------------------------------------------------
-- ALL_TAB_COLS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_TAB_COLS AS 

/

-- -----------------------------------------------------------------------
-- ALL_TAB_COLUMNS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_TAB_COLUMNS AS 

/

-- -----------------------------------------------------------------------
-- ALL_TAB_COL_STATISTICS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_TAB_COL_STATISTICS AS 

/

-- -----------------------------------------------------------------------
-- ALL_TAB_COMMENTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_TAB_COMMENTS AS 

/

-- -----------------------------------------------------------------------
-- ALL_TAB_HISTOGRAMS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_TAB_HISTOGRAMS AS 

/

-- -----------------------------------------------------------------------
-- ALL_TAB_MODIFICATIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_TAB_MODIFICATIONS AS 

/

-- -----------------------------------------------------------------------
-- ALL_TAB_PARTITIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_TAB_PARTITIONS AS 

/

-- -----------------------------------------------------------------------
-- ALL_TAB_PRIVS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_TAB_PRIVS AS 

/

-- -----------------------------------------------------------------------
-- ALL_TAB_PRIVS_MADE
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_TAB_PRIVS_MADE AS 

/

-- -----------------------------------------------------------------------
-- ALL_TAB_PRIVS_RECD
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_TAB_PRIVS_RECD AS 

/

-- -----------------------------------------------------------------------
-- ALL_TAB_STATISTICS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_TAB_STATISTICS AS 

/

-- -----------------------------------------------------------------------
-- ALL_TAB_STATS_HISTORY
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_TAB_STATS_HISTORY AS 

/

-- -----------------------------------------------------------------------
-- ALL_TAB_SUBPARTITIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_TAB_SUBPARTITIONS AS 

/

-- -----------------------------------------------------------------------
-- ALL_TRIGGERS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_TRIGGERS AS 

/

-- -----------------------------------------------------------------------
-- ALL_TRIGGER_COLS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_TRIGGER_COLS AS 

/

-- -----------------------------------------------------------------------
-- ALL_TYPES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_TYPES AS 

/

-- -----------------------------------------------------------------------
-- ALL_TYPE_ATTRS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_TYPE_ATTRS AS 

/

-- -----------------------------------------------------------------------
-- ALL_TYPE_METHODS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_TYPE_METHODS AS 

/

-- -----------------------------------------------------------------------
-- ALL_TYPE_VERSIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_TYPE_VERSIONS AS 

/

-- -----------------------------------------------------------------------
-- ALL_UNUSED_COL_TABS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_UNUSED_COL_TABS AS 

/

-- -----------------------------------------------------------------------
-- ALL_UPDATABLE_COLUMNS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_UPDATABLE_COLUMNS AS 

/

-- -----------------------------------------------------------------------
-- ALL_USERS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_USERS AS 

/

-- -----------------------------------------------------------------------
-- ALL_USTATS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_USTATS AS 

/

-- -----------------------------------------------------------------------
-- ALL_VARRAYS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_VARRAYS AS 

/

-- -----------------------------------------------------------------------
-- ALL_VIEWS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_VIEWS AS 

/

-- -----------------------------------------------------------------------
-- ALL_WARNING_SETTINGS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_WARNING_SETTINGS AS 

/

-- -----------------------------------------------------------------------
-- ALL_XML_INDEXES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_XML_INDEXES AS 

/

-- -----------------------------------------------------------------------
-- ALL_XML_SCHEMAS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_XML_SCHEMAS AS 

/

-- -----------------------------------------------------------------------
-- ALL_XML_SCHEMAS2
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_XML_SCHEMAS2 AS 

/

-- -----------------------------------------------------------------------
-- ALL_XML_TABLES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_XML_TABLES AS 

/

-- -----------------------------------------------------------------------
-- ALL_XML_TAB_COLS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_XML_TAB_COLS AS 

/

-- -----------------------------------------------------------------------
-- ALL_XML_VIEWS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_XML_VIEWS AS 

/

-- -----------------------------------------------------------------------
-- ALL_XML_VIEW_COLS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ALL_XML_VIEW_COLS AS 

/

-- -----------------------------------------------------------------------
-- CATALOG
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW CATALOG AS 

/

-- -----------------------------------------------------------------------
-- COL
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW COL AS 

/

-- -----------------------------------------------------------------------
-- COLUMN_PRIVILEGES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW COLUMN_PRIVILEGES AS 

/

-- -----------------------------------------------------------------------
-- DATABASE_COMPATIBLE_LEVEL
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW DATABASE_COMPATIBLE_LEVEL AS 

/

-- -----------------------------------------------------------------------
-- DATABASE_EXPORT_OBJECTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW DATABASE_EXPORT_OBJECTS AS 

/

-- -----------------------------------------------------------------------
-- DATABASE_PROPERTIES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW DATABASE_PROPERTIES AS 

/

-- -----------------------------------------------------------------------
-- DATAPUMP_DDL_TRANSFORM_PARAMS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW DATAPUMP_DDL_TRANSFORM_PARAMS AS 

/

-- -----------------------------------------------------------------------
-- DATAPUMP_OBJECT_CONNECT
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW DATAPUMP_OBJECT_CONNECT AS 

/

-- -----------------------------------------------------------------------
-- DATAPUMP_PATHMAP
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW DATAPUMP_PATHMAP AS 

/

-- -----------------------------------------------------------------------
-- DATAPUMP_PATHS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW DATAPUMP_PATHS AS 

/

-- -----------------------------------------------------------------------
-- DATAPUMP_REMAP_OBJECTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW DATAPUMP_REMAP_OBJECTS AS 

/

-- -----------------------------------------------------------------------
-- DATAPUMP_TABLE_DATA
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW DATAPUMP_TABLE_DATA AS 

/

-- -----------------------------------------------------------------------
-- DBA_AUTO_SEGADV_CTL
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW DBA_AUTO_SEGADV_CTL AS 

/

-- -----------------------------------------------------------------------
-- DBA_AUTO_SEGADV_SUMMARY
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW DBA_AUTO_SEGADV_SUMMARY AS 

/

-- -----------------------------------------------------------------------
-- DEFERRCOUNT
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW DEFERRCOUNT AS 

/

-- -----------------------------------------------------------------------
-- DICTIONARY
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW DICTIONARY AS 

/

-- -----------------------------------------------------------------------
-- DICT_COLUMNS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW DICT_COLUMNS AS 

/

-- -----------------------------------------------------------------------
-- EXPCOMPRESSEDPART
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXPCOMPRESSEDPART AS 

/

-- -----------------------------------------------------------------------
-- EXPCOMPRESSEDSUB
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXPCOMPRESSEDSUB AS 

/

-- -----------------------------------------------------------------------
-- EXPCOMPRESSEDTAB
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXPCOMPRESSEDTAB AS 

/

-- -----------------------------------------------------------------------
-- EXPEXEMPT
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXPEXEMPT AS 

/

-- -----------------------------------------------------------------------
-- EXPGETENCCOLNAM
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXPGETENCCOLNAM AS 

/

-- -----------------------------------------------------------------------
-- EXPTABSUBPART
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXPTABSUBPART AS 

/

-- -----------------------------------------------------------------------
-- EXPTABSUBPARTDATA_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXPTABSUBPARTDATA_VIEW AS 

/

-- -----------------------------------------------------------------------
-- EXPTABSUBPARTLOBFRAG
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXPTABSUBPARTLOBFRAG AS 

/

-- -----------------------------------------------------------------------
-- EXPTABSUBPARTLOB_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXPTABSUBPARTLOB_VIEW AS 

/

-- -----------------------------------------------------------------------
-- EXU102XTYPU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU102XTYPU AS 

/

-- -----------------------------------------------------------------------
-- EXU10ADEFPSWITCHES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU10ADEFPSWITCHES AS 

/

-- -----------------------------------------------------------------------
-- EXU10AOBJSWITCH
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU10AOBJSWITCH AS 

/

-- -----------------------------------------------------------------------
-- EXU10ASCU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU10ASCU AS 

/

-- -----------------------------------------------------------------------
-- EXU10CCLO
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU10CCLO AS 

/

-- -----------------------------------------------------------------------
-- EXU10CCLU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU10CCLU AS 

/

-- -----------------------------------------------------------------------
-- EXU10COEU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU10COEU AS 

/

-- -----------------------------------------------------------------------
-- EXU10DEFPSWITCHES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU10DEFPSWITCHES AS 

/

-- -----------------------------------------------------------------------
-- EXU10DOSO
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU10DOSO AS 

/

-- -----------------------------------------------------------------------
-- EXU10IND_BASE
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU10IND_BASE AS 

/

-- -----------------------------------------------------------------------
-- EXU10LNKU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU10LNKU AS 

/

-- -----------------------------------------------------------------------
-- EXU10MVL
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU10MVL AS 

/

-- -----------------------------------------------------------------------
-- EXU10MVLU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU10MVLU AS 

/

-- -----------------------------------------------------------------------
-- EXU10OBJSWITCH
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU10OBJSWITCH AS 

/

-- -----------------------------------------------------------------------
-- EXU10R2DEFPSWITCHES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU10R2DEFPSWITCHES AS 

/

-- -----------------------------------------------------------------------
-- EXU10R2OBJSWITCH
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU10R2OBJSWITCH AS 

/

-- -----------------------------------------------------------------------
-- EXU10SNAPLU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU10SNAPLU AS 

/

-- -----------------------------------------------------------------------
-- EXU10SNAPU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU10SNAPU AS 

/

-- -----------------------------------------------------------------------
-- EXU10TABSU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU10TABSU AS 

/

-- -----------------------------------------------------------------------
-- EXU10TABU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU10TABU AS 

/

-- -----------------------------------------------------------------------
-- EXU816MAXSQV
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU816MAXSQV AS 

/

-- -----------------------------------------------------------------------
-- EXU816TGRU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU816TGRU AS 

/

-- -----------------------------------------------------------------------
-- EXU81ACTIONOBJ
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU81ACTIONOBJ AS 

/

-- -----------------------------------------------------------------------
-- EXU81ACTIONPKG
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU81ACTIONPKG AS 

/

-- -----------------------------------------------------------------------
-- EXU81ASSOC
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU81ASSOC AS 

/

-- -----------------------------------------------------------------------
-- EXU81CSC
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU81CSC AS 

/

-- -----------------------------------------------------------------------
-- EXU81DOIU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU81DOIU AS 

/

-- -----------------------------------------------------------------------
-- EXU81IND
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU81IND AS 

/

-- -----------------------------------------------------------------------
-- EXU81IND_BASE
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU81IND_BASE AS 

/

-- -----------------------------------------------------------------------
-- EXU81ITYU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU81ITYU AS 

/

-- -----------------------------------------------------------------------
-- EXU81IXCPU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU81IXCPU AS 

/

-- -----------------------------------------------------------------------
-- EXU81IXSPU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU81IXSPU AS 

/

-- -----------------------------------------------------------------------
-- EXU81JAV
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU81JAV AS 

/

-- -----------------------------------------------------------------------
-- EXU81JAVT
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU81JAVT AS 

/

-- -----------------------------------------------------------------------
-- EXU81LBCPU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU81LBCPU AS 

/

-- -----------------------------------------------------------------------
-- EXU81LBPU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU81LBPU AS 

/

-- -----------------------------------------------------------------------
-- EXU81LBSPU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU81LBSPU AS 

/

-- -----------------------------------------------------------------------
-- EXU81NOS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU81NOS AS 

/

-- -----------------------------------------------------------------------
-- EXU81OBJECTPKG
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU81OBJECTPKG AS 

/

-- -----------------------------------------------------------------------
-- EXU81OPRU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU81OPRU AS 

/

-- -----------------------------------------------------------------------
-- EXU81PLBU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU81PLBU AS 

/

-- -----------------------------------------------------------------------
-- EXU81PROCOBJ
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU81PROCOBJ AS 

/

-- -----------------------------------------------------------------------
-- EXU81PROCOBJINSTANCE
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU81PROCOBJINSTANCE AS 

/

-- -----------------------------------------------------------------------
-- EXU81RGCU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU81RGCU AS 

/

-- -----------------------------------------------------------------------
-- EXU81RGSU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU81RGSU AS 

/

-- -----------------------------------------------------------------------
-- EXU81RLS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU81RLS AS 

/

-- -----------------------------------------------------------------------
-- EXU81SCMU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU81SCMU AS 

/

-- -----------------------------------------------------------------------
-- EXU81SLFCU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU81SLFCU AS 

/

-- -----------------------------------------------------------------------
-- EXU81SNAPLU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU81SNAPLU AS 

/

-- -----------------------------------------------------------------------
-- EXU81SNAPU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU81SNAPU AS 

/

-- -----------------------------------------------------------------------
-- EXU81SPOKIU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU81SPOKIU AS 

/

-- -----------------------------------------------------------------------
-- EXU81SPOKU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU81SPOKU AS 

/

-- -----------------------------------------------------------------------
-- EXU81SRTU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU81SRTU AS 

/

-- -----------------------------------------------------------------------
-- EXU81TABSU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU81TABSU AS 

/

-- -----------------------------------------------------------------------
-- EXU81TABU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU81TABU AS 

/

-- -----------------------------------------------------------------------
-- EXU81TBCPU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU81TBCPU AS 

/

-- -----------------------------------------------------------------------
-- EXU81TBSPU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU81TBSPU AS 

/

-- -----------------------------------------------------------------------
-- EXU81TGRU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU81TGRU AS 

/

-- -----------------------------------------------------------------------
-- EXU81TYPU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU81TYPU AS 

/

-- -----------------------------------------------------------------------
-- EXU81USCIU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU81USCIU AS 

/

-- -----------------------------------------------------------------------
-- EXU8ANAL
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8ANAL AS 

/

-- -----------------------------------------------------------------------
-- EXU8ASCU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8ASCU AS 

/

-- -----------------------------------------------------------------------
-- EXU8BSZ
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8BSZ AS 

/

-- -----------------------------------------------------------------------
-- EXU8CCLO
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8CCLO AS 

/

-- -----------------------------------------------------------------------
-- EXU8CCLU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8CCLU AS 

/

-- -----------------------------------------------------------------------
-- EXU8CCOU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8CCOU AS 

/

-- -----------------------------------------------------------------------
-- EXU8CGRU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8CGRU AS 

/

-- -----------------------------------------------------------------------
-- EXU8CLUU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8CLUU AS 

/

-- -----------------------------------------------------------------------
-- EXU8CMTU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8CMTU AS 

/

-- -----------------------------------------------------------------------
-- EXU8COEU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8COEU AS 

/

-- -----------------------------------------------------------------------
-- EXU8COLU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8COLU AS 

/

-- -----------------------------------------------------------------------
-- EXU8CONU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8CONU AS 

/

-- -----------------------------------------------------------------------
-- EXU8COOU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8COOU AS 

/

-- -----------------------------------------------------------------------
-- EXU8CPO
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8CPO AS 

/

-- -----------------------------------------------------------------------
-- EXU8CSET
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8CSET AS 

/

-- -----------------------------------------------------------------------
-- EXU8CSNU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8CSNU AS 

/

-- -----------------------------------------------------------------------
-- EXU8DIM
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8DIM AS 

/

-- -----------------------------------------------------------------------
-- EXU8DIMU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8DIMU AS 

/

-- -----------------------------------------------------------------------
-- EXU8FPTU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8FPTU AS 

/

-- -----------------------------------------------------------------------
-- EXU8FUL
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8FUL AS 

/

-- -----------------------------------------------------------------------
-- EXU8GLOB
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8GLOB AS 

/

-- -----------------------------------------------------------------------
-- EXU8GRNU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8GRNU AS 

/

-- -----------------------------------------------------------------------
-- EXU8HSTU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8HSTU AS 

/

-- -----------------------------------------------------------------------
-- EXU8ICO
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8ICO AS 

/

-- -----------------------------------------------------------------------
-- EXU8ICOU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8ICOU AS 

/

-- -----------------------------------------------------------------------
-- EXU8ICPLSQL
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8ICPLSQL AS 

/

-- -----------------------------------------------------------------------
-- EXU8INDU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8INDU AS 

/

-- -----------------------------------------------------------------------
-- EXU8INKU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8INKU AS 

/

-- -----------------------------------------------------------------------
-- EXU8IOVU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8IOVU AS 

/

-- -----------------------------------------------------------------------
-- EXU8IXPU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8IXPU AS 

/

-- -----------------------------------------------------------------------
-- EXU8JBQU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8JBQU AS 

/

-- -----------------------------------------------------------------------
-- EXU8LIBU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8LIBU AS 

/

-- -----------------------------------------------------------------------
-- EXU8LNKU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8LNKU AS 

/

-- -----------------------------------------------------------------------
-- EXU8LOBU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8LOBU AS 

/

-- -----------------------------------------------------------------------
-- EXU8NTBU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8NTBU AS 

/

-- -----------------------------------------------------------------------
-- EXU8NXPU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8NXPU AS 

/

-- -----------------------------------------------------------------------
-- EXU8OIDU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8OIDU AS 

/

-- -----------------------------------------------------------------------
-- EXU8OPT
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8OPT AS 

/

-- -----------------------------------------------------------------------
-- EXU8ORD
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8ORD AS 

/

-- -----------------------------------------------------------------------
-- EXU8ORDU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8ORDU AS 

/

-- -----------------------------------------------------------------------
-- EXU8ORFS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8ORFS AS 

/

-- -----------------------------------------------------------------------
-- EXU8PDSU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8PDSU AS 

/

-- -----------------------------------------------------------------------
-- EXU8PNTU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8PNTU AS 

/

-- -----------------------------------------------------------------------
-- EXU8POKIU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8POKIU AS 

/

-- -----------------------------------------------------------------------
-- EXU8POKU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8POKU AS 

/

-- -----------------------------------------------------------------------
-- EXU8PSTU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8PSTU AS 

/

-- -----------------------------------------------------------------------
-- EXU8REFU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8REFU AS 

/

-- -----------------------------------------------------------------------
-- EXU8RFSU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8RFSU AS 

/

-- -----------------------------------------------------------------------
-- EXU8RGCU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8RGCU AS 

/

-- -----------------------------------------------------------------------
-- EXU8RGSU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8RGSU AS 

/

-- -----------------------------------------------------------------------
-- EXU8SCMU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8SCMU AS 

/

-- -----------------------------------------------------------------------
-- EXU8SEQU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8SEQU AS 

/

-- -----------------------------------------------------------------------
-- EXU8SLFCU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8SLFCU AS 

/

-- -----------------------------------------------------------------------
-- EXU8SLOGU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8SLOGU AS 

/

-- -----------------------------------------------------------------------
-- EXU8SNAPLU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8SNAPLU AS 

/

-- -----------------------------------------------------------------------
-- EXU8SNAPU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8SNAPU AS 

/

-- -----------------------------------------------------------------------
-- EXU8SPSU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8SPSU AS 

/

-- -----------------------------------------------------------------------
-- EXU8SPU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8SPU AS 

/

-- -----------------------------------------------------------------------
-- EXU8SRTU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8SRTU AS 

/

-- -----------------------------------------------------------------------
-- EXU8STOU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8STOU AS 

/

-- -----------------------------------------------------------------------
-- EXU8SYNU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8SYNU AS 

/

-- -----------------------------------------------------------------------
-- EXU8TABU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8TABU AS 

/

-- -----------------------------------------------------------------------
-- EXU8TBPU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8TBPU AS 

/

-- -----------------------------------------------------------------------
-- EXU8TGRU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8TGRU AS 

/

-- -----------------------------------------------------------------------
-- EXU8TNE
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8TNE AS 

/

-- -----------------------------------------------------------------------
-- EXU8TNEB
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8TNEB AS 

/

-- -----------------------------------------------------------------------
-- EXU8TYPBU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8TYPBU AS 

/

-- -----------------------------------------------------------------------
-- EXU8TYPTU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8TYPTU AS 

/

-- -----------------------------------------------------------------------
-- EXU8TYPU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8TYPU AS 

/

-- -----------------------------------------------------------------------
-- EXU8USCU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8USCU AS 

/

-- -----------------------------------------------------------------------
-- EXU8USRU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8USRU AS 

/

-- -----------------------------------------------------------------------
-- EXU8VDPTU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8VDPTU AS 

/

-- -----------------------------------------------------------------------
-- EXU8VER
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8VER AS 

/

-- -----------------------------------------------------------------------
-- EXU8VEWU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8VEWU AS 

/

-- -----------------------------------------------------------------------
-- EXU8VINFU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8VINFU AS 

/

-- -----------------------------------------------------------------------
-- EXU8VNCU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU8VNCU AS 

/

-- -----------------------------------------------------------------------
-- EXU92FPTPU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU92FPTPU AS 

/

-- -----------------------------------------------------------------------
-- EXU92FPTU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU92FPTU AS 

/

-- -----------------------------------------------------------------------
-- EXU92TGRU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU92TGRU AS 

/

-- -----------------------------------------------------------------------
-- EXU92TSP
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU92TSP AS 

/

-- -----------------------------------------------------------------------
-- EXU92TSPL
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU92TSPL AS 

/

-- -----------------------------------------------------------------------
-- EXU9ACTIONOBJ
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9ACTIONOBJ AS 

/

-- -----------------------------------------------------------------------
-- EXU9BJF
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9BJF AS 

/

-- -----------------------------------------------------------------------
-- EXU9BJW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9BJW AS 

/

-- -----------------------------------------------------------------------
-- EXU9CCLO
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9CCLO AS 

/

-- -----------------------------------------------------------------------
-- EXU9CCLU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9CCLU AS 

/

-- -----------------------------------------------------------------------
-- EXU9COEU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9COEU AS 

/

-- -----------------------------------------------------------------------
-- EXU9COOU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9COOU AS 

/

-- -----------------------------------------------------------------------
-- EXU9DEFPSWITCHES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9DEFPSWITCHES AS 

/

-- -----------------------------------------------------------------------
-- EXU9DOIU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9DOIU AS 

/

-- -----------------------------------------------------------------------
-- EXU9DOSO
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9DOSO AS 

/

-- -----------------------------------------------------------------------
-- EXU9EIP
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9EIP AS 

/

-- -----------------------------------------------------------------------
-- EXU9FGA
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9FGA AS 

/

-- -----------------------------------------------------------------------
-- EXU9GSAS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9GSAS AS 

/

-- -----------------------------------------------------------------------
-- EXU9IND
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9IND AS 

/

-- -----------------------------------------------------------------------
-- EXU9IND_BASE
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9IND_BASE AS 

/

-- -----------------------------------------------------------------------
-- EXU9INHCOLCONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9INHCOLCONS AS 

/

-- -----------------------------------------------------------------------
-- EXU9IXCPU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9IXCPU AS 

/

-- -----------------------------------------------------------------------
-- EXU9LBCPU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9LBCPU AS 

/

-- -----------------------------------------------------------------------
-- EXU9LBPU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9LBPU AS 

/

-- -----------------------------------------------------------------------
-- EXU9LNKU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9LNKU AS 

/

-- -----------------------------------------------------------------------
-- EXU9LOBU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9LOBU AS 

/

-- -----------------------------------------------------------------------
-- EXU9MVL
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9MVL AS 

/

-- -----------------------------------------------------------------------
-- EXU9MVLCDCCC
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9MVLCDCCC AS 

/

-- -----------------------------------------------------------------------
-- EXU9MVLCDCS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9MVLCDCS AS 

/

-- -----------------------------------------------------------------------
-- EXU9MVLCDCSC
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9MVLCDCSC AS 

/

-- -----------------------------------------------------------------------
-- EXU9MVLCDCST
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9MVLCDCST AS 

/

-- -----------------------------------------------------------------------
-- EXU9MVLU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9MVLU AS 

/

-- -----------------------------------------------------------------------
-- EXU9NLS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9NLS AS 

/

-- -----------------------------------------------------------------------
-- EXU9NOS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9NOS AS 

/

-- -----------------------------------------------------------------------
-- EXU9OBJSWITCH
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9OBJSWITCH AS 

/

-- -----------------------------------------------------------------------
-- EXU9OTNNULL
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9OTNNULL AS 

/

-- -----------------------------------------------------------------------
-- EXU9PCT
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9PCT AS 

/

-- -----------------------------------------------------------------------
-- EXU9PDSU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9PDSU AS 

/

-- -----------------------------------------------------------------------
-- EXU9PGP
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9PGP AS 

/

-- -----------------------------------------------------------------------
-- EXU9PLBU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9PLBU AS 

/

-- -----------------------------------------------------------------------
-- EXU9PTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9PTS AS 

/

-- -----------------------------------------------------------------------
-- EXU9RLS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9RLS AS 

/

-- -----------------------------------------------------------------------
-- EXU9SNAPLU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9SNAPLU AS 

/

-- -----------------------------------------------------------------------
-- EXU9SNAPU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9SNAPU AS 

/

-- -----------------------------------------------------------------------
-- EXU9STOU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9STOU AS 

/

-- -----------------------------------------------------------------------
-- EXU9SYNU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9SYNU AS 

/

-- -----------------------------------------------------------------------
-- EXU9TABSU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9TABSU AS 

/

-- -----------------------------------------------------------------------
-- EXU9TABU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9TABU AS 

/

-- -----------------------------------------------------------------------
-- EXU9TAB_UNUSED_COLS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9TAB_UNUSED_COLS AS 

/

-- -----------------------------------------------------------------------
-- EXU9TBCPU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9TBCPU AS 

/

-- -----------------------------------------------------------------------
-- EXU9TNE
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9TNE AS 

/

-- -----------------------------------------------------------------------
-- EXU9TNEB
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9TNEB AS 

/

-- -----------------------------------------------------------------------
-- EXU9TYPTU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9TYPTU AS 

/

-- -----------------------------------------------------------------------
-- EXU9TYPTU2
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9TYPTU2 AS 

/

-- -----------------------------------------------------------------------
-- EXU9TYPU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9TYPU AS 

/

-- -----------------------------------------------------------------------
-- EXU9UTSU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9UTSU AS 

/

-- -----------------------------------------------------------------------
-- EXU9XDBUID
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9XDBUID AS 

/

-- -----------------------------------------------------------------------
-- EXU9XMLST
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9XMLST AS 

/

-- -----------------------------------------------------------------------
-- EXU9XTB
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW EXU9XTB AS 

/

-- -----------------------------------------------------------------------
-- FLASHBACK_TRANSACTION_QUERY
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW FLASHBACK_TRANSACTION_QUERY AS 

/

-- -----------------------------------------------------------------------
-- GLOBAL_CONTEXT
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW GLOBAL_CONTEXT AS 

/

-- -----------------------------------------------------------------------
-- GLOBAL_NAME
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW GLOBAL_NAME AS 

/

-- -----------------------------------------------------------------------
-- GV_$ACTIVE_INSTANCES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW GV_$ACTIVE_INSTANCES AS 

/

-- -----------------------------------------------------------------------
-- GV_$ACTIVE_SESS_POOL_MTH
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW GV_$ACTIVE_SESS_POOL_MTH AS 

/

-- -----------------------------------------------------------------------
-- GV_$AW_AGGREGATE_OP
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW GV_$AW_AGGREGATE_OP AS 

/

-- -----------------------------------------------------------------------
-- GV_$AW_ALLOCATE_OP
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW GV_$AW_ALLOCATE_OP AS 

/

-- -----------------------------------------------------------------------
-- GV_$AW_CALC
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW GV_$AW_CALC AS 

/

-- -----------------------------------------------------------------------
-- GV_$AW_LONGOPS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW GV_$AW_LONGOPS AS 

/

-- -----------------------------------------------------------------------
-- GV_$AW_OLAP
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW GV_$AW_OLAP AS 

/

-- -----------------------------------------------------------------------
-- GV_$AW_SESSION_INFO
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW GV_$AW_SESSION_INFO AS 

/

-- -----------------------------------------------------------------------
-- GV_$BH
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW GV_$BH AS 

/

-- -----------------------------------------------------------------------
-- GV_$BLOCKING_QUIESCE
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW GV_$BLOCKING_QUIESCE AS 

/

-- -----------------------------------------------------------------------
-- GV_$LOADISTAT
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW GV_$LOADISTAT AS 

/

-- -----------------------------------------------------------------------
-- GV_$LOADPSTAT
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW GV_$LOADPSTAT AS 

/

-- -----------------------------------------------------------------------
-- GV_$LOCK_ACTIVITY
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW GV_$LOCK_ACTIVITY AS 

/

-- -----------------------------------------------------------------------
-- GV_$MAX_ACTIVE_SESS_TARGET_MTH
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW GV_$MAX_ACTIVE_SESS_TARGET_MTH AS 

/

-- -----------------------------------------------------------------------
-- GV_$NLS_PARAMETERS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW GV_$NLS_PARAMETERS AS 

/

-- -----------------------------------------------------------------------
-- GV_$NLS_VALID_VALUES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW GV_$NLS_VALID_VALUES AS 

/

-- -----------------------------------------------------------------------
-- GV_$OPTION
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW GV_$OPTION AS 

/

-- -----------------------------------------------------------------------
-- GV_$PARALLEL_DEGREE_LIMIT_MTH
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW GV_$PARALLEL_DEGREE_LIMIT_MTH AS 

/

-- -----------------------------------------------------------------------
-- GV_$PQ_SESSTAT
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW GV_$PQ_SESSTAT AS 

/

-- -----------------------------------------------------------------------
-- GV_$PQ_TQSTAT
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW GV_$PQ_TQSTAT AS 

/

-- -----------------------------------------------------------------------
-- GV_$QUEUEING_MTH
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW GV_$QUEUEING_MTH AS 

/

-- -----------------------------------------------------------------------
-- GV_$RESTORE_POINT
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW GV_$RESTORE_POINT AS 

/

-- -----------------------------------------------------------------------
-- GV_$RSRC_CONSUMER_GROUP
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW GV_$RSRC_CONSUMER_GROUP AS 

/

-- -----------------------------------------------------------------------
-- GV_$RSRC_CONSUME_GROUP_CPU_MTH
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW GV_$RSRC_CONSUME_GROUP_CPU_MTH AS 

/

-- -----------------------------------------------------------------------
-- GV_$RSRC_CONS_GROUP_HISTORY
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW GV_$RSRC_CONS_GROUP_HISTORY AS 

/

-- -----------------------------------------------------------------------
-- GV_$RSRC_PLAN
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW GV_$RSRC_PLAN AS 

/

-- -----------------------------------------------------------------------
-- GV_$RSRC_PLAN_CPU_MTH
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW GV_$RSRC_PLAN_CPU_MTH AS 

/

-- -----------------------------------------------------------------------
-- GV_$RSRC_PLAN_HISTORY
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW GV_$RSRC_PLAN_HISTORY AS 

/

-- -----------------------------------------------------------------------
-- GV_$RSRC_SESSION_INFO
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW GV_$RSRC_SESSION_INFO AS 

/

-- -----------------------------------------------------------------------
-- GV_$SESSION_LONGOPS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW GV_$SESSION_LONGOPS AS 

/

-- -----------------------------------------------------------------------
-- GV_$TEMPORARY_LOBS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW GV_$TEMPORARY_LOBS AS 

/

-- -----------------------------------------------------------------------
-- GV_$TIMEZONE_FILE
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW GV_$TIMEZONE_FILE AS 

/

-- -----------------------------------------------------------------------
-- GV_$TIMEZONE_NAMES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW GV_$TIMEZONE_NAMES AS 

/

-- -----------------------------------------------------------------------
-- GV_$VERSION
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW GV_$VERSION AS 

/

-- -----------------------------------------------------------------------
-- IMP8CDTU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW IMP8CDTU AS 

/

-- -----------------------------------------------------------------------
-- IMP8REPCAT
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW IMP8REPCAT AS 

/

-- -----------------------------------------------------------------------
-- IMP8TTDU
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW IMP8TTDU AS 

/

-- -----------------------------------------------------------------------
-- IMP8UEC
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW IMP8UEC AS 

/

-- -----------------------------------------------------------------------
-- IMP9COMPAT
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW IMP9COMPAT AS 

/

-- -----------------------------------------------------------------------
-- IMP9SYN4
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW IMP9SYN4 AS 

/

-- -----------------------------------------------------------------------
-- IMP9TVOID
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW IMP9TVOID AS 

/

-- -----------------------------------------------------------------------
-- IMP9USR
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW IMP9USR AS 

/

-- -----------------------------------------------------------------------
-- IMP_LOB_INFO
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW IMP_LOB_INFO AS 

/

-- -----------------------------------------------------------------------
-- IMP_LOB_NOTNULL
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW IMP_LOB_NOTNULL AS 

/

-- -----------------------------------------------------------------------
-- IMP_TAB_TRIG
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW IMP_TAB_TRIG AS 

/

-- -----------------------------------------------------------------------
-- INDEX_HISTOGRAM
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW INDEX_HISTOGRAM AS 

/

-- -----------------------------------------------------------------------
-- INDEX_STATS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW INDEX_STATS AS 

/

-- -----------------------------------------------------------------------
-- KU$_10_1_COMMENT_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_10_1_COMMENT_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_10_1_DBLINK_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_10_1_DBLINK_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_10_1_FHTABLE_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_10_1_FHTABLE_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_10_1_HTABLE_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_10_1_HTABLE_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_10_1_IND_STATS_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_10_1_IND_STATS_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_10_1_IOTABLE_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_10_1_IOTABLE_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_10_1_PFHTABLE_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_10_1_PFHTABLE_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_10_1_PHTABLE_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_10_1_PHTABLE_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_10_1_PIOTABLE_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_10_1_PIOTABLE_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_10_1_PROXY_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_10_1_PROXY_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_10_1_SYSGRANT_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_10_1_SYSGRANT_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_10_1_TABLE_DATA_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_10_1_TABLE_DATA_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_10_1_TAB_STATS_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_10_1_TAB_STATS_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_2NDTAB_INFO_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_2NDTAB_INFO_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_ALTER_FUNC_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_ALTER_FUNC_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_ALTER_PKGBDY_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_ALTER_PKGBDY_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_ALTER_PKGSPC_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_ALTER_PKGSPC_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_ALTER_PROC_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_ALTER_PROC_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_ASSOC_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_ASSOC_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_AUDIT_OBJ_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_AUDIT_OBJ_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_AUDIT_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_AUDIT_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_CLUSTER_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_CLUSTER_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_CLU_TS_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_CLU_TS_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_COMMENT_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_COMMENT_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_CONSTRAINT_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_CONSTRAINT_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_CONTEXT_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_CONTEXT_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_DBLINK_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_DBLINK_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_DEFROLE_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_DEFROLE_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_DEPTYPES_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_DEPTYPES_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_DEPVIEWS_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_DEPVIEWS_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_DIMENSION_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_DIMENSION_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_DIRECTORY_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_DIRECTORY_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_DOMIDX_OBJNUM_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_DOMIDX_OBJNUM_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_EXP_PKG_BODY_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_EXP_PKG_BODY_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_EXP_TYPE_BODY_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_EXP_TYPE_BODY_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_EXTTAB_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_EXTTAB_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_FGA_POLICY_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_FGA_POLICY_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_FHTABLE_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_FHTABLE_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_FIND_HIDDEN_CONS_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_FIND_HIDDEN_CONS_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_FIND_SGC_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_FIND_SGC_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_FULL_PKG_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_FULL_PKG_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_FULL_TYPE_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_FULL_TYPE_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_FUNC_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_FUNC_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_HTABLE_DATA_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_HTABLE_DATA_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_HTABLE_OBJNUM_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_HTABLE_OBJNUM_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_HTABLE_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_HTABLE_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_HTPART_DATA_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_HTPART_DATA_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_HTSPART_DATA_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_HTSPART_DATA_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_INC_TYPE_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_INC_TYPE_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_INDEXTYPE_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_INDEXTYPE_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_INDEX_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_INDEX_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_IND_STATS_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_IND_STATS_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_IND_TS_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_IND_TS_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_INSTANCE_CALLOUT_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_INSTANCE_CALLOUT_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_IOTABLE_DATA_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_IOTABLE_DATA_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_IOTABLE_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_IOTABLE_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_IOTPART_DATA_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_IOTPART_DATA_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_JAVA_CLASS_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_JAVA_CLASS_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_JAVA_OBJNUM_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_JAVA_OBJNUM_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_JAVA_RESOURCE_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_JAVA_RESOURCE_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_JAVA_SOURCE_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_JAVA_SOURCE_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_JOB_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_JOB_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_LIBRARY_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_LIBRARY_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_MONITOR_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_MONITOR_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_MVL_TS_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_MVL_TS_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_MV_TS_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_MV_TS_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_M_VIEW_FH_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_M_VIEW_FH_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_M_VIEW_H_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_M_VIEW_H_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_M_VIEW_IOT_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_M_VIEW_IOT_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_M_VIEW_LOG_FH_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_M_VIEW_LOG_FH_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_M_VIEW_LOG_H_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_M_VIEW_LOG_H_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_M_VIEW_LOG_PFH_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_M_VIEW_LOG_PFH_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_M_VIEW_LOG_PH_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_M_VIEW_LOG_PH_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_M_VIEW_PFH_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_M_VIEW_PFH_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_M_VIEW_PH_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_M_VIEW_PH_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_M_VIEW_PIOT_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_M_VIEW_PIOT_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_M_VIEW_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_M_VIEW_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_NTABLE_DATA_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_NTABLE_DATA_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_NTABLE_OBJNUM_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_NTABLE_OBJNUM_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_OBJGRANT_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_OBJGRANT_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_OBJPKG_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_OBJPKG_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_OPERATOR_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_OPERATOR_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_OUTLINE_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_OUTLINE_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_PARTITION_EST_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_PARTITION_EST_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_PFHTABLE_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_PFHTABLE_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_PHTABLE_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_PHTABLE_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_PIOTABLE_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_PIOTABLE_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_PKGBDY_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_PKGBDY_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_PKG_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_PKG_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_PLUGTS_BEGIN_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_PLUGTS_BEGIN_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_PLUGTS_BLK_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_PLUGTS_BLK_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_PLUGTS_CHECKPL_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_PLUGTS_CHECKPL_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_PLUGTS_TSNAME_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_PLUGTS_TSNAME_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_POST_TABLE_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_POST_TABLE_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_PRE_TABLE_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_PRE_TABLE_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_PROCACT_INSTANCE_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_PROCACT_INSTANCE_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_PROCACT_SCHEMA_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_PROCACT_SCHEMA_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_PROCDEPOBJ_AUDIT_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_PROCDEPOBJ_AUDIT_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_PROCDEPOBJ_GRANT_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_PROCDEPOBJ_GRANT_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_PROCDEPOBJ_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_PROCDEPOBJ_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_PROCOBJ_AUDIT_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_PROCOBJ_AUDIT_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_PROCOBJ_GRANT_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_PROCOBJ_GRANT_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_PROCOBJ_OBJNUM_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_PROCOBJ_OBJNUM_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_PROCOBJ_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_PROCOBJ_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_PROC_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_PROC_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_PROFILE_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_PROFILE_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_PROXY_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_PROXY_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_QTRANS_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_QTRANS_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_QUEUES_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_QUEUES_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_QUEUE_TABLE_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_QUEUE_TABLE_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_REFGROUP_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_REFGROUP_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_REF_CONSTRAINT_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_REF_CONSTRAINT_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_RLS_CONTEXT_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_RLS_CONTEXT_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_RLS_GROUP_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_RLS_GROUP_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_RLS_POLICY_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_RLS_POLICY_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_ROGRANT_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_ROGRANT_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_ROLE_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_ROLE_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_ROLLBACK_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_ROLLBACK_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_SCHEMA_CALLOUT_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_SCHEMA_CALLOUT_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_SEQUENCE_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_SEQUENCE_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_STRMTABLE_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_STRMTABLE_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_SUBPARTITION_EST_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_SUBPARTITION_EST_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_SWITCH_COMPILER_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_SWITCH_COMPILER_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_SYNONYM_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_SYNONYM_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_SYSGRANT_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_SYSGRANT_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_TABLESPACE_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_TABLESPACE_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_TABLE_DATA_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_TABLE_DATA_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_TABLE_EST_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_TABLE_EST_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_TABLE_OBJNUM_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_TABLE_OBJNUM_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_TAB_STATS_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_TAB_STATS_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_TAB_TS_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_TAB_TS_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_TEMP_SUBPARTDATA_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_TEMP_SUBPARTDATA_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_TEMP_SUBPARTLOB_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_TEMP_SUBPARTLOB_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_TRIGGER_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_TRIGGER_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_TRLINK_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_TRLINK_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_TSQUOTA_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_TSQUOTA_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_TYPE_BODY_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_TYPE_BODY_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_TYPE_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_TYPE_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_USER_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_USER_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_VIEW_OBJNUM_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_VIEW_OBJNUM_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_VIEW_STATUS_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_VIEW_STATUS_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_VIEW_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_VIEW_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU$_XMLSCHEMA_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU$_XMLSCHEMA_VIEW AS 

/

-- -----------------------------------------------------------------------
-- KU_NOEXP_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW KU_NOEXP_VIEW AS 

/

-- -----------------------------------------------------------------------
-- LOADER_COL_FLAGS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW LOADER_COL_FLAGS AS 

/

-- -----------------------------------------------------------------------
-- LOADER_COL_INFO
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW LOADER_COL_INFO AS 

/

-- -----------------------------------------------------------------------
-- LOADER_COL_TYPE
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW LOADER_COL_TYPE AS 

/

-- -----------------------------------------------------------------------
-- LOADER_CONSTRAINT_INFO
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW LOADER_CONSTRAINT_INFO AS 

/

-- -----------------------------------------------------------------------
-- LOADER_DIR_OBJS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW LOADER_DIR_OBJS AS 

/

-- -----------------------------------------------------------------------
-- LOADER_FILE_TS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW LOADER_FILE_TS AS 

/

-- -----------------------------------------------------------------------
-- LOADER_FULL_ATTR_NAME
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW LOADER_FULL_ATTR_NAME AS 

/

-- -----------------------------------------------------------------------
-- LOADER_INTCOL_INFO
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW LOADER_INTCOL_INFO AS 

/

-- -----------------------------------------------------------------------
-- LOADER_LOB_FLAGS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW LOADER_LOB_FLAGS AS 

/

-- -----------------------------------------------------------------------
-- LOADER_NESTED_VARRAYS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW LOADER_NESTED_VARRAYS AS 

/

-- -----------------------------------------------------------------------
-- LOADER_OID_INFO
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW LOADER_OID_INFO AS 

/

-- -----------------------------------------------------------------------
-- LOADER_PARAM_INFO
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW LOADER_PARAM_INFO AS 

/

-- -----------------------------------------------------------------------
-- LOADER_PART_INFO
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW LOADER_PART_INFO AS 

/

-- -----------------------------------------------------------------------
-- LOADER_REF_INFO
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW LOADER_REF_INFO AS 

/

-- -----------------------------------------------------------------------
-- LOADER_SKIP_UNUSABLE_INDEXES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW LOADER_SKIP_UNUSABLE_INDEXES AS 

/

-- -----------------------------------------------------------------------
-- LOADER_TAB_INFO
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW LOADER_TAB_INFO AS 

/

-- -----------------------------------------------------------------------
-- LOADER_TRIGGER_INFO
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW LOADER_TRIGGER_INFO AS 

/

-- -----------------------------------------------------------------------
-- NLS_DATABASE_PARAMETERS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW NLS_DATABASE_PARAMETERS AS 

/

-- -----------------------------------------------------------------------
-- NLS_INSTANCE_PARAMETERS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW NLS_INSTANCE_PARAMETERS AS 

/

-- -----------------------------------------------------------------------
-- NLS_SESSION_PARAMETERS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW NLS_SESSION_PARAMETERS AS 

/

-- -----------------------------------------------------------------------
-- ORA_KGLR7_DB_LINKS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ORA_KGLR7_DB_LINKS AS 

/

-- -----------------------------------------------------------------------
-- ORA_KGLR7_DEPENDENCIES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ORA_KGLR7_DEPENDENCIES AS 

/

-- -----------------------------------------------------------------------
-- ORA_KGLR7_IDL_CHAR
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ORA_KGLR7_IDL_CHAR AS 

/

-- -----------------------------------------------------------------------
-- ORA_KGLR7_IDL_SB4
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ORA_KGLR7_IDL_SB4 AS 

/

-- -----------------------------------------------------------------------
-- ORA_KGLR7_IDL_UB1
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ORA_KGLR7_IDL_UB1 AS 

/

-- -----------------------------------------------------------------------
-- ORA_KGLR7_IDL_UB2
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ORA_KGLR7_IDL_UB2 AS 

/

-- -----------------------------------------------------------------------
-- PRODUCT_COMPONENT_VERSION
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW PRODUCT_COMPONENT_VERSION AS 

/

-- -----------------------------------------------------------------------
-- PUBLICSYN
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW PUBLICSYN AS 

/

-- -----------------------------------------------------------------------
-- PUBLIC_DEPENDENCY
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW PUBLIC_DEPENDENCY AS 

/

-- -----------------------------------------------------------------------
-- QUEUE_PRIVILEGES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW QUEUE_PRIVILEGES AS 

/

-- -----------------------------------------------------------------------
-- RESOURCE_COST
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW RESOURCE_COST AS 

/

-- -----------------------------------------------------------------------
-- ROLE_ROLE_PRIVS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ROLE_ROLE_PRIVS AS 

/

-- -----------------------------------------------------------------------
-- ROLE_SYS_PRIVS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ROLE_SYS_PRIVS AS 

/

-- -----------------------------------------------------------------------
-- ROLE_TAB_PRIVS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW ROLE_TAB_PRIVS AS 

/

-- -----------------------------------------------------------------------
-- SCHEMA_EXPORT_OBJECTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW SCHEMA_EXPORT_OBJECTS AS 

/

-- -----------------------------------------------------------------------
-- SESSION_CONTEXT
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW SESSION_CONTEXT AS 

/

-- -----------------------------------------------------------------------
-- SESSION_PRIVS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW SESSION_PRIVS AS 

/

-- -----------------------------------------------------------------------
-- SESSION_ROLES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW SESSION_ROLES AS 

/

-- -----------------------------------------------------------------------
-- SM_$VERSION
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW SM_$VERSION AS 

/

-- -----------------------------------------------------------------------
-- SYNONYMS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW SYNONYMS AS 

/

-- -----------------------------------------------------------------------
-- SYSCATALOG
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW SYSCATALOG AS 

/

-- -----------------------------------------------------------------------
-- SYSFILES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW SYSFILES AS 

/

-- -----------------------------------------------------------------------
-- SYSSEGOBJ
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW SYSSEGOBJ AS 

/

-- -----------------------------------------------------------------------
-- TAB
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW TAB AS 

/

-- -----------------------------------------------------------------------
-- TABLESPACE_EXPORT_OBJECTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW TABLESPACE_EXPORT_OBJECTS AS 

/

-- -----------------------------------------------------------------------
-- TABLE_EXPORT_OBJECTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW TABLE_EXPORT_OBJECTS AS 

/

-- -----------------------------------------------------------------------
-- TABLE_PRIVILEGES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW TABLE_PRIVILEGES AS 

/

-- -----------------------------------------------------------------------
-- TABQUOTAS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW TABQUOTAS AS 

/

-- -----------------------------------------------------------------------
-- TRANSPORTABLE_EXPORT_OBJECTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW TRANSPORTABLE_EXPORT_OBJECTS AS 

/

-- -----------------------------------------------------------------------
-- USER_ADVISOR_ACTIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_ADVISOR_ACTIONS AS 

/

-- -----------------------------------------------------------------------
-- USER_ADVISOR_DIRECTIVES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_ADVISOR_DIRECTIVES AS 

/

-- -----------------------------------------------------------------------
-- USER_ADVISOR_FINDINGS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_ADVISOR_FINDINGS AS 

/

-- -----------------------------------------------------------------------
-- USER_ADVISOR_JOURNAL
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_ADVISOR_JOURNAL AS 

/

-- -----------------------------------------------------------------------
-- USER_ADVISOR_LOG
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_ADVISOR_LOG AS 

/

-- -----------------------------------------------------------------------
-- USER_ADVISOR_OBJECTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_ADVISOR_OBJECTS AS 

/

-- -----------------------------------------------------------------------
-- USER_ADVISOR_PARAMETERS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_ADVISOR_PARAMETERS AS 

/

-- -----------------------------------------------------------------------
-- USER_ADVISOR_RATIONALE
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_ADVISOR_RATIONALE AS 

/

-- -----------------------------------------------------------------------
-- USER_ADVISOR_RECOMMENDATIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_ADVISOR_RECOMMENDATIONS AS 

/

-- -----------------------------------------------------------------------
-- USER_ADVISOR_SQLA_REC_SUM
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_ADVISOR_SQLA_REC_SUM AS 

/

-- -----------------------------------------------------------------------
-- USER_ADVISOR_SQLA_WK_MAP
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_ADVISOR_SQLA_WK_MAP AS 

/

-- -----------------------------------------------------------------------
-- USER_ADVISOR_SQLA_WK_STMTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_ADVISOR_SQLA_WK_STMTS AS 

/

-- -----------------------------------------------------------------------
-- USER_ADVISOR_SQLW_COLVOL
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_ADVISOR_SQLW_COLVOL AS 

/

-- -----------------------------------------------------------------------
-- USER_ADVISOR_SQLW_JOURNAL
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_ADVISOR_SQLW_JOURNAL AS 

/

-- -----------------------------------------------------------------------
-- USER_ADVISOR_SQLW_PARAMETERS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_ADVISOR_SQLW_PARAMETERS AS 

/

-- -----------------------------------------------------------------------
-- USER_ADVISOR_SQLW_STMTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_ADVISOR_SQLW_STMTS AS 

/

-- -----------------------------------------------------------------------
-- USER_ADVISOR_SQLW_SUM
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_ADVISOR_SQLW_SUM AS 

/

-- -----------------------------------------------------------------------
-- USER_ADVISOR_SQLW_TABLES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_ADVISOR_SQLW_TABLES AS 

/

-- -----------------------------------------------------------------------
-- USER_ADVISOR_SQLW_TABVOL
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_ADVISOR_SQLW_TABVOL AS 

/

-- -----------------------------------------------------------------------
-- USER_ADVISOR_SQLW_TEMPLATES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_ADVISOR_SQLW_TEMPLATES AS 

/

-- -----------------------------------------------------------------------
-- USER_ADVISOR_TASKS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_ADVISOR_TASKS AS 

/

-- -----------------------------------------------------------------------
-- USER_ADVISOR_TEMPLATES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_ADVISOR_TEMPLATES AS 

/

-- -----------------------------------------------------------------------
-- USER_ALL_TABLES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_ALL_TABLES AS 

/

-- -----------------------------------------------------------------------
-- USER_APPLICATION_ROLES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_APPLICATION_ROLES AS 

/

-- -----------------------------------------------------------------------
-- USER_AQ_AGENT_PRIVS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_AQ_AGENT_PRIVS AS 

/

-- -----------------------------------------------------------------------
-- USER_ARGUMENTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_ARGUMENTS AS 

/

-- -----------------------------------------------------------------------
-- USER_ASSOCIATIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_ASSOCIATIONS AS 

/

-- -----------------------------------------------------------------------
-- USER_ATTRIBUTE_TRANSFORMATIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_ATTRIBUTE_TRANSFORMATIONS AS 

/

-- -----------------------------------------------------------------------
-- USER_AUDIT_OBJECT
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_AUDIT_OBJECT AS 

/

-- -----------------------------------------------------------------------
-- USER_AUDIT_SESSION
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_AUDIT_SESSION AS 

/

-- -----------------------------------------------------------------------
-- USER_AUDIT_STATEMENT
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_AUDIT_STATEMENT AS 

/

-- -----------------------------------------------------------------------
-- USER_AUDIT_TRAIL
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_AUDIT_TRAIL AS 

/

-- -----------------------------------------------------------------------
-- USER_AWS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_AWS AS 

/

-- -----------------------------------------------------------------------
-- USER_AW_PS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_AW_PS AS 

/

-- -----------------------------------------------------------------------
-- USER_BASE_TABLE_MVIEWS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_BASE_TABLE_MVIEWS AS 

/

-- -----------------------------------------------------------------------
-- USER_CATALOG
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_CATALOG AS 

/

-- -----------------------------------------------------------------------
-- USER_CHANGE_NOTIFICATION_REGS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_CHANGE_NOTIFICATION_REGS AS 

/

-- -----------------------------------------------------------------------
-- USER_CLUSTERS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_CLUSTERS AS 

/

-- -----------------------------------------------------------------------
-- USER_CLUSTER_HASH_EXPRESSIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_CLUSTER_HASH_EXPRESSIONS AS 

/

-- -----------------------------------------------------------------------
-- USER_CLU_COLUMNS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_CLU_COLUMNS AS 

/

-- -----------------------------------------------------------------------
-- USER_COLL_TYPES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_COLL_TYPES AS 

/

-- -----------------------------------------------------------------------
-- USER_COL_COMMENTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_COL_COMMENTS AS 

/

-- -----------------------------------------------------------------------
-- USER_COL_PRIVS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_COL_PRIVS AS 

/

-- -----------------------------------------------------------------------
-- USER_COL_PRIVS_MADE
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_COL_PRIVS_MADE AS 

/

-- -----------------------------------------------------------------------
-- USER_COL_PRIVS_RECD
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_COL_PRIVS_RECD AS 

/

-- -----------------------------------------------------------------------
-- USER_CONSTRAINTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_CONSTRAINTS AS 

/

-- -----------------------------------------------------------------------
-- USER_CONS_COLUMNS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_CONS_COLUMNS AS 

/

-- -----------------------------------------------------------------------
-- USER_CONS_OBJ_COLUMNS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_CONS_OBJ_COLUMNS AS 

/

-- -----------------------------------------------------------------------
-- USER_DATAPUMP_JOBS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_DATAPUMP_JOBS AS 

/

-- -----------------------------------------------------------------------
-- USER_DB_LINKS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_DB_LINKS AS 

/

-- -----------------------------------------------------------------------
-- USER_DEPENDENCIES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_DEPENDENCIES AS 

/

-- -----------------------------------------------------------------------
-- USER_DIMENSIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_DIMENSIONS AS 

/

-- -----------------------------------------------------------------------
-- USER_DIM_ATTRIBUTES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_DIM_ATTRIBUTES AS 

/

-- -----------------------------------------------------------------------
-- USER_DIM_CHILD_OF
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_DIM_CHILD_OF AS 

/

-- -----------------------------------------------------------------------
-- USER_DIM_HIERARCHIES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_DIM_HIERARCHIES AS 

/

-- -----------------------------------------------------------------------
-- USER_DIM_JOIN_KEY
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_DIM_JOIN_KEY AS 

/

-- -----------------------------------------------------------------------
-- USER_DIM_LEVELS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_DIM_LEVELS AS 

/

-- -----------------------------------------------------------------------
-- USER_DIM_LEVEL_KEY
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_DIM_LEVEL_KEY AS 

/

-- -----------------------------------------------------------------------
-- USER_ENCRYPTED_COLUMNS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_ENCRYPTED_COLUMNS AS 

/

-- -----------------------------------------------------------------------
-- USER_EPG_DAD_AUTHORIZATION
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_EPG_DAD_AUTHORIZATION AS 

/

-- -----------------------------------------------------------------------
-- USER_ERRORS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_ERRORS AS 

/

-- -----------------------------------------------------------------------
-- USER_EVALUATION_CONTEXTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_EVALUATION_CONTEXTS AS 

/

-- -----------------------------------------------------------------------
-- USER_EVALUATION_CONTEXT_TABLES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_EVALUATION_CONTEXT_TABLES AS 

/

-- -----------------------------------------------------------------------
-- USER_EVALUATION_CONTEXT_VARS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_EVALUATION_CONTEXT_VARS AS 

/

-- -----------------------------------------------------------------------
-- USER_EXTENTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_EXTENTS AS 

/

-- -----------------------------------------------------------------------
-- USER_EXTERNAL_LOCATIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_EXTERNAL_LOCATIONS AS 

/

-- -----------------------------------------------------------------------
-- USER_EXTERNAL_TABLES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_EXTERNAL_TABLES AS 

/

-- -----------------------------------------------------------------------
-- USER_FILE_GROUPS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_FILE_GROUPS AS 

/

-- -----------------------------------------------------------------------
-- USER_FILE_GROUP_EXPORT_INFO
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_FILE_GROUP_EXPORT_INFO AS 

/

-- -----------------------------------------------------------------------
-- USER_FILE_GROUP_FILES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_FILE_GROUP_FILES AS 

/

-- -----------------------------------------------------------------------
-- USER_FILE_GROUP_TABLES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_FILE_GROUP_TABLES AS 

/

-- -----------------------------------------------------------------------
-- USER_FILE_GROUP_TABLESPACES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_FILE_GROUP_TABLESPACES AS 

/

-- -----------------------------------------------------------------------
-- USER_FILE_GROUP_VERSIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_FILE_GROUP_VERSIONS AS 

/

-- -----------------------------------------------------------------------
-- USER_FREE_SPACE
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_FREE_SPACE AS 

/

-- -----------------------------------------------------------------------
-- USER_INDEXES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_INDEXES AS 

/

-- -----------------------------------------------------------------------
-- USER_INDEXTYPES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_INDEXTYPES AS 

/

-- -----------------------------------------------------------------------
-- USER_INDEXTYPE_ARRAYTYPES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_INDEXTYPE_ARRAYTYPES AS 

/

-- -----------------------------------------------------------------------
-- USER_INDEXTYPE_COMMENTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_INDEXTYPE_COMMENTS AS 

/

-- -----------------------------------------------------------------------
-- USER_INDEXTYPE_OPERATORS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_INDEXTYPE_OPERATORS AS 

/

-- -----------------------------------------------------------------------
-- USER_IND_COLUMNS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_IND_COLUMNS AS 

/

-- -----------------------------------------------------------------------
-- USER_IND_EXPRESSIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_IND_EXPRESSIONS AS 

/

-- -----------------------------------------------------------------------
-- USER_IND_PARTITIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_IND_PARTITIONS AS 

/

-- -----------------------------------------------------------------------
-- USER_IND_STATISTICS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_IND_STATISTICS AS 

/

-- -----------------------------------------------------------------------
-- USER_IND_SUBPARTITIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_IND_SUBPARTITIONS AS 

/

-- -----------------------------------------------------------------------
-- USER_INTERNAL_TRIGGERS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_INTERNAL_TRIGGERS AS 

/

-- -----------------------------------------------------------------------
-- USER_JOBS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_JOBS AS 

/

-- -----------------------------------------------------------------------
-- USER_JOIN_IND_COLUMNS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_JOIN_IND_COLUMNS AS 

/

-- -----------------------------------------------------------------------
-- USER_LIBRARIES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_LIBRARIES AS 

/

-- -----------------------------------------------------------------------
-- USER_LOBS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_LOBS AS 

/

-- -----------------------------------------------------------------------
-- USER_LOB_PARTITIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_LOB_PARTITIONS AS 

/

-- -----------------------------------------------------------------------
-- USER_LOB_SUBPARTITIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_LOB_SUBPARTITIONS AS 

/

-- -----------------------------------------------------------------------
-- USER_LOB_TEMPLATES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_LOB_TEMPLATES AS 

/

-- -----------------------------------------------------------------------
-- USER_LOG_GROUPS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_LOG_GROUPS AS 

/

-- -----------------------------------------------------------------------
-- USER_LOG_GROUP_COLUMNS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_LOG_GROUP_COLUMNS AS 

/

-- -----------------------------------------------------------------------
-- USER_METHOD_PARAMS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_METHOD_PARAMS AS 

/

-- -----------------------------------------------------------------------
-- USER_METHOD_RESULTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_METHOD_RESULTS AS 

/

-- -----------------------------------------------------------------------
-- USER_MVIEWS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_MVIEWS AS 

/

-- -----------------------------------------------------------------------
-- USER_MVIEW_AGGREGATES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_MVIEW_AGGREGATES AS 

/

-- -----------------------------------------------------------------------
-- USER_MVIEW_ANALYSIS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_MVIEW_ANALYSIS AS 

/

-- -----------------------------------------------------------------------
-- USER_MVIEW_COMMENTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_MVIEW_COMMENTS AS 

/

-- -----------------------------------------------------------------------
-- USER_MVIEW_DETAIL_RELATIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_MVIEW_DETAIL_RELATIONS AS 

/

-- -----------------------------------------------------------------------
-- USER_MVIEW_JOINS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_MVIEW_JOINS AS 

/

-- -----------------------------------------------------------------------
-- USER_MVIEW_KEYS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_MVIEW_KEYS AS 

/

-- -----------------------------------------------------------------------
-- USER_MVIEW_LOGS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_MVIEW_LOGS AS 

/

-- -----------------------------------------------------------------------
-- USER_MVIEW_REFRESH_TIMES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_MVIEW_REFRESH_TIMES AS 

/

-- -----------------------------------------------------------------------
-- USER_NESTED_TABLES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_NESTED_TABLES AS 

/

-- -----------------------------------------------------------------------
-- USER_NESTED_TABLE_COLS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_NESTED_TABLE_COLS AS 

/

-- -----------------------------------------------------------------------
-- USER_OBJECTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_OBJECTS AS 

/

-- -----------------------------------------------------------------------
-- USER_OBJECT_SIZE
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_OBJECT_SIZE AS 

/

-- -----------------------------------------------------------------------
-- USER_OBJECT_TABLES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_OBJECT_TABLES AS 

/

-- -----------------------------------------------------------------------
-- USER_OBJ_AUDIT_OPTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_OBJ_AUDIT_OPTS AS 

/

-- -----------------------------------------------------------------------
-- USER_OBJ_COLATTRS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_OBJ_COLATTRS AS 

/

-- -----------------------------------------------------------------------
-- USER_OPANCILLARY
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_OPANCILLARY AS 

/

-- -----------------------------------------------------------------------
-- USER_OPARGUMENTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_OPARGUMENTS AS 

/

-- -----------------------------------------------------------------------
-- USER_OPBINDINGS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_OPBINDINGS AS 

/

-- -----------------------------------------------------------------------
-- USER_OPERATORS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_OPERATORS AS 

/

-- -----------------------------------------------------------------------
-- USER_OPERATOR_COMMENTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_OPERATOR_COMMENTS AS 

/

-- -----------------------------------------------------------------------
-- USER_OUTLINES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_OUTLINES AS 

/

-- -----------------------------------------------------------------------
-- USER_OUTLINE_HINTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_OUTLINE_HINTS AS 

/

-- -----------------------------------------------------------------------
-- USER_PARTIAL_DROP_TABS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_PARTIAL_DROP_TABS AS 

/

-- -----------------------------------------------------------------------
-- USER_PART_COL_STATISTICS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_PART_COL_STATISTICS AS 

/

-- -----------------------------------------------------------------------
-- USER_PART_HISTOGRAMS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_PART_HISTOGRAMS AS 

/

-- -----------------------------------------------------------------------
-- USER_PART_INDEXES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_PART_INDEXES AS 

/

-- -----------------------------------------------------------------------
-- USER_PART_KEY_COLUMNS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_PART_KEY_COLUMNS AS 

/

-- -----------------------------------------------------------------------
-- USER_PART_LOBS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_PART_LOBS AS 

/

-- -----------------------------------------------------------------------
-- USER_PART_TABLES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_PART_TABLES AS 

/

-- -----------------------------------------------------------------------
-- USER_PASSWORD_LIMITS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_PASSWORD_LIMITS AS 

/

-- -----------------------------------------------------------------------
-- USER_PENDING_CONV_TABLES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_PENDING_CONV_TABLES AS 

/

-- -----------------------------------------------------------------------
-- USER_PLSQL_OBJECT_SETTINGS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_PLSQL_OBJECT_SETTINGS AS 

/

-- -----------------------------------------------------------------------
-- USER_POLICIES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_POLICIES AS 

/

-- -----------------------------------------------------------------------
-- USER_POLICY_CONTEXTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_POLICY_CONTEXTS AS 

/

-- -----------------------------------------------------------------------
-- USER_POLICY_GROUPS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_POLICY_GROUPS AS 

/

-- -----------------------------------------------------------------------
-- USER_PROCEDURES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_PROCEDURES AS 

/

-- -----------------------------------------------------------------------
-- USER_PROXIES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_PROXIES AS 

/

-- -----------------------------------------------------------------------
-- USER_PUBLISHED_COLUMNS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_PUBLISHED_COLUMNS AS 

/

-- -----------------------------------------------------------------------
-- USER_QUEUES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_QUEUES AS 

/

-- -----------------------------------------------------------------------
-- USER_QUEUE_PUBLISHERS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_QUEUE_PUBLISHERS AS 

/

-- -----------------------------------------------------------------------
-- USER_QUEUE_SCHEDULES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_QUEUE_SCHEDULES AS 

/

-- -----------------------------------------------------------------------
-- USER_QUEUE_SUBSCRIBERS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_QUEUE_SUBSCRIBERS AS 

/

-- -----------------------------------------------------------------------
-- USER_QUEUE_TABLES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_QUEUE_TABLES AS 

/

-- -----------------------------------------------------------------------
-- USER_RECYCLEBIN
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_RECYCLEBIN AS 

/

-- -----------------------------------------------------------------------
-- USER_REFRESH
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REFRESH AS 

/

-- -----------------------------------------------------------------------
-- USER_REFRESH_CHILDREN
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REFRESH_CHILDREN AS 

/

-- -----------------------------------------------------------------------
-- USER_REFS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REFS AS 

/

-- -----------------------------------------------------------------------
-- USER_REGISTERED_MVIEWS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REGISTERED_MVIEWS AS 

/

-- -----------------------------------------------------------------------
-- USER_REGISTERED_SNAPSHOTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REGISTERED_SNAPSHOTS AS 

/

-- -----------------------------------------------------------------------
-- USER_REGISTRY
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REGISTRY AS 

/

-- -----------------------------------------------------------------------
-- USER_REPAUDIT_ATTRIBUTE
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REPAUDIT_ATTRIBUTE AS 

/

-- -----------------------------------------------------------------------
-- USER_REPAUDIT_COLUMN
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REPAUDIT_COLUMN AS 

/

-- -----------------------------------------------------------------------
-- USER_REPCAT
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REPCAT AS 

/

-- -----------------------------------------------------------------------
-- USER_REPCATLOG
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REPCATLOG AS 

/

-- -----------------------------------------------------------------------
-- USER_REPCAT_REFRESH_TEMPLATES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REPCAT_REFRESH_TEMPLATES AS 

/

-- -----------------------------------------------------------------------
-- USER_REPCAT_TEMPLATE_OBJECTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REPCAT_TEMPLATE_OBJECTS AS 

/

-- -----------------------------------------------------------------------
-- USER_REPCAT_TEMPLATE_PARMS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REPCAT_TEMPLATE_PARMS AS 

/

-- -----------------------------------------------------------------------
-- USER_REPCAT_TEMPLATE_SITES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REPCAT_TEMPLATE_SITES AS 

/

-- -----------------------------------------------------------------------
-- USER_REPCAT_USER_AUTHORIZATION
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REPCAT_USER_AUTHORIZATION AS 

/

-- -----------------------------------------------------------------------
-- USER_REPCAT_USER_PARM_VALUES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REPCAT_USER_PARM_VALUES AS 

/

-- -----------------------------------------------------------------------
-- USER_REPCOLUMN
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REPCOLUMN AS 

/

-- -----------------------------------------------------------------------
-- USER_REPCOLUMN_GROUP
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REPCOLUMN_GROUP AS 

/

-- -----------------------------------------------------------------------
-- USER_REPCONFLICT
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REPCONFLICT AS 

/

-- -----------------------------------------------------------------------
-- USER_REPDDL
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REPDDL AS 

/

-- -----------------------------------------------------------------------
-- USER_REPFLAVORS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REPFLAVORS AS 

/

-- -----------------------------------------------------------------------
-- USER_REPFLAVOR_COLUMNS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REPFLAVOR_COLUMNS AS 

/

-- -----------------------------------------------------------------------
-- USER_REPFLAVOR_OBJECTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REPFLAVOR_OBJECTS AS 

/

-- -----------------------------------------------------------------------
-- USER_REPGENERATED
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REPGENERATED AS 

/

-- -----------------------------------------------------------------------
-- USER_REPGENOBJECTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REPGENOBJECTS AS 

/

-- -----------------------------------------------------------------------
-- USER_REPGROUP
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REPGROUP AS 

/

-- -----------------------------------------------------------------------
-- USER_REPGROUPED_COLUMN
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REPGROUPED_COLUMN AS 

/

-- -----------------------------------------------------------------------
-- USER_REPGROUP_PRIVILEGES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REPGROUP_PRIVILEGES AS 

/

-- -----------------------------------------------------------------------
-- USER_REPKEY_COLUMNS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REPKEY_COLUMNS AS 

/

-- -----------------------------------------------------------------------
-- USER_REPOBJECT
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REPOBJECT AS 

/

-- -----------------------------------------------------------------------
-- USER_REPPARAMETER_COLUMN
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REPPARAMETER_COLUMN AS 

/

-- -----------------------------------------------------------------------
-- USER_REPPRIORITY
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REPPRIORITY AS 

/

-- -----------------------------------------------------------------------
-- USER_REPPRIORITY_GROUP
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REPPRIORITY_GROUP AS 

/

-- -----------------------------------------------------------------------
-- USER_REPPROP
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REPPROP AS 

/

-- -----------------------------------------------------------------------
-- USER_REPRESOLUTION
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REPRESOLUTION AS 

/

-- -----------------------------------------------------------------------
-- USER_REPRESOLUTION_METHOD
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REPRESOLUTION_METHOD AS 

/

-- -----------------------------------------------------------------------
-- USER_REPRESOLUTION_STATISTICS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REPRESOLUTION_STATISTICS AS 

/

-- -----------------------------------------------------------------------
-- USER_REPRESOL_STATS_CONTROL
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REPRESOL_STATS_CONTROL AS 

/

-- -----------------------------------------------------------------------
-- USER_REPSCHEMA
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REPSCHEMA AS 

/

-- -----------------------------------------------------------------------
-- USER_REPSITES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REPSITES AS 

/

-- -----------------------------------------------------------------------
-- USER_RESOURCE_LIMITS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_RESOURCE_LIMITS AS 

/

-- -----------------------------------------------------------------------
-- USER_RESUMABLE
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_RESUMABLE AS 

/

-- -----------------------------------------------------------------------
-- USER_REWRITE_EQUIVALENCES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_REWRITE_EQUIVALENCES AS 

/

-- -----------------------------------------------------------------------
-- USER_ROLE_PRIVS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_ROLE_PRIVS AS 

/

-- -----------------------------------------------------------------------
-- USER_RSRC_CONSUMER_GROUP_PRIVS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_RSRC_CONSUMER_GROUP_PRIVS AS 

/

-- -----------------------------------------------------------------------
-- USER_RSRC_MANAGER_SYSTEM_PRIVS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_RSRC_MANAGER_SYSTEM_PRIVS AS 

/

-- -----------------------------------------------------------------------
-- USER_RULES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_RULES AS 

/

-- -----------------------------------------------------------------------
-- USER_RULESETS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_RULESETS AS 

/

-- -----------------------------------------------------------------------
-- USER_RULE_SETS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_RULE_SETS AS 

/

-- -----------------------------------------------------------------------
-- USER_RULE_SET_RULES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_RULE_SET_RULES AS 

/

-- -----------------------------------------------------------------------
-- USER_SCHEDULER_CHAINS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SCHEDULER_CHAINS AS 

/

-- -----------------------------------------------------------------------
-- USER_SCHEDULER_CHAIN_RULES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SCHEDULER_CHAIN_RULES AS 

/

-- -----------------------------------------------------------------------
-- USER_SCHEDULER_CHAIN_STEPS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SCHEDULER_CHAIN_STEPS AS 

/

-- -----------------------------------------------------------------------
-- USER_SCHEDULER_JOBS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SCHEDULER_JOBS AS 

/

-- -----------------------------------------------------------------------
-- USER_SCHEDULER_JOB_ARGS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SCHEDULER_JOB_ARGS AS 

/

-- -----------------------------------------------------------------------
-- USER_SCHEDULER_JOB_LOG
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SCHEDULER_JOB_LOG AS 

/

-- -----------------------------------------------------------------------
-- USER_SCHEDULER_JOB_RUN_DETAILS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SCHEDULER_JOB_RUN_DETAILS AS 

/

-- -----------------------------------------------------------------------
-- USER_SCHEDULER_PROGRAMS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SCHEDULER_PROGRAMS AS 

/

-- -----------------------------------------------------------------------
-- USER_SCHEDULER_PROGRAM_ARGS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SCHEDULER_PROGRAM_ARGS AS 

/

-- -----------------------------------------------------------------------
-- USER_SCHEDULER_RUNNING_CHAINS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SCHEDULER_RUNNING_CHAINS AS 

/

-- -----------------------------------------------------------------------
-- USER_SCHEDULER_RUNNING_JOBS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SCHEDULER_RUNNING_JOBS AS 

/

-- -----------------------------------------------------------------------
-- USER_SCHEDULER_SCHEDULES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SCHEDULER_SCHEDULES AS 

/

-- -----------------------------------------------------------------------
-- USER_SECONDARY_OBJECTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SECONDARY_OBJECTS AS 

/

-- -----------------------------------------------------------------------
-- USER_SEC_RELEVANT_COLS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SEC_RELEVANT_COLS AS 

/

-- -----------------------------------------------------------------------
-- USER_SEGMENTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SEGMENTS AS 

/

-- -----------------------------------------------------------------------
-- USER_SEQUENCES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SEQUENCES AS 

/

-- -----------------------------------------------------------------------
-- USER_SNAPSHOTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SNAPSHOTS AS 

/

-- -----------------------------------------------------------------------
-- USER_SNAPSHOT_LOGS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SNAPSHOT_LOGS AS 

/

-- -----------------------------------------------------------------------
-- USER_SOURCE
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SOURCE AS 

/

-- -----------------------------------------------------------------------
-- USER_SOURCE_TABLES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SOURCE_TABLES AS 

/

-- -----------------------------------------------------------------------
-- USER_SQLJ_TYPES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SQLJ_TYPES AS 

/

-- -----------------------------------------------------------------------
-- USER_SQLJ_TYPE_ATTRS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SQLJ_TYPE_ATTRS AS 

/

-- -----------------------------------------------------------------------
-- USER_SQLJ_TYPE_METHODS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SQLJ_TYPE_METHODS AS 

/

-- -----------------------------------------------------------------------
-- USER_SQLSET
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SQLSET AS 

/

-- -----------------------------------------------------------------------
-- USER_SQLSET_BINDS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SQLSET_BINDS AS 

/

-- -----------------------------------------------------------------------
-- USER_SQLSET_PLANS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SQLSET_PLANS AS 

/

-- -----------------------------------------------------------------------
-- USER_SQLSET_REFERENCES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SQLSET_REFERENCES AS 

/

-- -----------------------------------------------------------------------
-- USER_SQLSET_STATEMENTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SQLSET_STATEMENTS AS 

/

-- -----------------------------------------------------------------------
-- USER_SQLTUNE_BINDS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SQLTUNE_BINDS AS 

/

-- -----------------------------------------------------------------------
-- USER_SQLTUNE_PLANS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SQLTUNE_PLANS AS 

/

-- -----------------------------------------------------------------------
-- USER_SQLTUNE_RATIONALE_PLAN
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SQLTUNE_RATIONALE_PLAN AS 

/

-- -----------------------------------------------------------------------
-- USER_SQLTUNE_STATISTICS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SQLTUNE_STATISTICS AS 

/

-- -----------------------------------------------------------------------
-- USER_STORED_SETTINGS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_STORED_SETTINGS AS 

/

-- -----------------------------------------------------------------------
-- USER_SUBPARTITION_TEMPLATES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SUBPARTITION_TEMPLATES AS 

/

-- -----------------------------------------------------------------------
-- USER_SUBPART_COL_STATISTICS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SUBPART_COL_STATISTICS AS 

/

-- -----------------------------------------------------------------------
-- USER_SUBPART_HISTOGRAMS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SUBPART_HISTOGRAMS AS 

/

-- -----------------------------------------------------------------------
-- USER_SUBPART_KEY_COLUMNS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SUBPART_KEY_COLUMNS AS 

/

-- -----------------------------------------------------------------------
-- USER_SUBSCRIBED_COLUMNS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SUBSCRIBED_COLUMNS AS 

/

-- -----------------------------------------------------------------------
-- USER_SUBSCRIBED_TABLES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SUBSCRIBED_TABLES AS 

/

-- -----------------------------------------------------------------------
-- USER_SUBSCRIPTIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SUBSCRIPTIONS AS 

/

-- -----------------------------------------------------------------------
-- USER_SUMMARIES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SUMMARIES AS 

/

-- -----------------------------------------------------------------------
-- USER_SUMMARY_AGGREGATES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SUMMARY_AGGREGATES AS 

/

-- -----------------------------------------------------------------------
-- USER_SUMMARY_DETAIL_TABLES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SUMMARY_DETAIL_TABLES AS 

/

-- -----------------------------------------------------------------------
-- USER_SUMMARY_JOINS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SUMMARY_JOINS AS 

/

-- -----------------------------------------------------------------------
-- USER_SUMMARY_KEYS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SUMMARY_KEYS AS 

/

-- -----------------------------------------------------------------------
-- USER_SYNONYMS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SYNONYMS AS 

/

-- -----------------------------------------------------------------------
-- USER_SYS_PRIVS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_SYS_PRIVS AS 

/

-- -----------------------------------------------------------------------
-- USER_TABLES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_TABLES AS 

/

-- -----------------------------------------------------------------------
-- USER_TABLESPACES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_TABLESPACES AS 

/

-- -----------------------------------------------------------------------
-- USER_TAB_COLS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_TAB_COLS AS 

/

-- -----------------------------------------------------------------------
-- USER_TAB_COLUMNS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_TAB_COLUMNS AS 

/

-- -----------------------------------------------------------------------
-- USER_TAB_COL_STATISTICS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_TAB_COL_STATISTICS AS 

/

-- -----------------------------------------------------------------------
-- USER_TAB_COMMENTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_TAB_COMMENTS AS 

/

-- -----------------------------------------------------------------------
-- USER_TAB_HISTOGRAMS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_TAB_HISTOGRAMS AS 

/

-- -----------------------------------------------------------------------
-- USER_TAB_MODIFICATIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_TAB_MODIFICATIONS AS 

/

-- -----------------------------------------------------------------------
-- USER_TAB_PARTITIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_TAB_PARTITIONS AS 

/

-- -----------------------------------------------------------------------
-- USER_TAB_PRIVS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_TAB_PRIVS AS 

/

-- -----------------------------------------------------------------------
-- USER_TAB_PRIVS_MADE
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_TAB_PRIVS_MADE AS 

/

-- -----------------------------------------------------------------------
-- USER_TAB_PRIVS_RECD
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_TAB_PRIVS_RECD AS 

/

-- -----------------------------------------------------------------------
-- USER_TAB_STATISTICS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_TAB_STATISTICS AS 

/

-- -----------------------------------------------------------------------
-- USER_TAB_STATS_HISTORY
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_TAB_STATS_HISTORY AS 

/

-- -----------------------------------------------------------------------
-- USER_TAB_SUBPARTITIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_TAB_SUBPARTITIONS AS 

/

-- -----------------------------------------------------------------------
-- USER_TRANSFORMATIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_TRANSFORMATIONS AS 

/

-- -----------------------------------------------------------------------
-- USER_TRIGGERS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_TRIGGERS AS 

/

-- -----------------------------------------------------------------------
-- USER_TRIGGER_COLS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_TRIGGER_COLS AS 

/

-- -----------------------------------------------------------------------
-- USER_TS_QUOTAS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_TS_QUOTAS AS 

/

-- -----------------------------------------------------------------------
-- USER_TUNE_MVIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_TUNE_MVIEW AS 

/

-- -----------------------------------------------------------------------
-- USER_TYPES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_TYPES AS 

/

-- -----------------------------------------------------------------------
-- USER_TYPE_ATTRS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_TYPE_ATTRS AS 

/

-- -----------------------------------------------------------------------
-- USER_TYPE_METHODS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_TYPE_METHODS AS 

/

-- -----------------------------------------------------------------------
-- USER_TYPE_VERSIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_TYPE_VERSIONS AS 

/

-- -----------------------------------------------------------------------
-- USER_UNUSED_COL_TABS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_UNUSED_COL_TABS AS 

/

-- -----------------------------------------------------------------------
-- USER_UPDATABLE_COLUMNS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_UPDATABLE_COLUMNS AS 

/

-- -----------------------------------------------------------------------
-- USER_USERS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_USERS AS 

/

-- -----------------------------------------------------------------------
-- USER_USTATS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_USTATS AS 

/

-- -----------------------------------------------------------------------
-- USER_VARRAYS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_VARRAYS AS 

/

-- -----------------------------------------------------------------------
-- USER_VIEWS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_VIEWS AS 

/

-- -----------------------------------------------------------------------
-- USER_WARNING_SETTINGS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_WARNING_SETTINGS AS 

/

-- -----------------------------------------------------------------------
-- USER_XML_COLUMN_NAMES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_XML_COLUMN_NAMES AS 

/

-- -----------------------------------------------------------------------
-- USER_XML_INDEXES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_XML_INDEXES AS 

/

-- -----------------------------------------------------------------------
-- USER_XML_SCHEMAS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_XML_SCHEMAS AS 

/

-- -----------------------------------------------------------------------
-- USER_XML_TABLES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_XML_TABLES AS 

/

-- -----------------------------------------------------------------------
-- USER_XML_TAB_COLS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_XML_TAB_COLS AS 

/

-- -----------------------------------------------------------------------
-- USER_XML_VIEWS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_XML_VIEWS AS 

/

-- -----------------------------------------------------------------------
-- USER_XML_VIEW_COLS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW USER_XML_VIEW_COLS AS 

/

-- -----------------------------------------------------------------------
-- UTL_ALL_IND_COMPS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW UTL_ALL_IND_COMPS AS 

/

-- -----------------------------------------------------------------------
-- V$OBJECT_USAGE
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW V$OBJECT_USAGE AS 

/

-- -----------------------------------------------------------------------
-- V_$ACTIVE_INSTANCES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW V_$ACTIVE_INSTANCES AS 

/

-- -----------------------------------------------------------------------
-- V_$ACTIVE_SESS_POOL_MTH
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW V_$ACTIVE_SESS_POOL_MTH AS 

/

-- -----------------------------------------------------------------------
-- V_$ADVISOR_PROGRESS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW V_$ADVISOR_PROGRESS AS 

/

-- -----------------------------------------------------------------------
-- V_$AW_AGGREGATE_OP
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW V_$AW_AGGREGATE_OP AS 

/

-- -----------------------------------------------------------------------
-- V_$AW_ALLOCATE_OP
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW V_$AW_ALLOCATE_OP AS 

/

-- -----------------------------------------------------------------------
-- V_$AW_CALC
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW V_$AW_CALC AS 

/

-- -----------------------------------------------------------------------
-- V_$AW_LONGOPS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW V_$AW_LONGOPS AS 

/

-- -----------------------------------------------------------------------
-- V_$AW_OLAP
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW V_$AW_OLAP AS 

/

-- -----------------------------------------------------------------------
-- V_$AW_SESSION_INFO
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW V_$AW_SESSION_INFO AS 

/

-- -----------------------------------------------------------------------
-- V_$BH
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW V_$BH AS 

/

-- -----------------------------------------------------------------------
-- V_$BLOCKING_QUIESCE
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW V_$BLOCKING_QUIESCE AS 

/

-- -----------------------------------------------------------------------
-- V_$LOADISTAT
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW V_$LOADISTAT AS 

/

-- -----------------------------------------------------------------------
-- V_$LOADPSTAT
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW V_$LOADPSTAT AS 

/

-- -----------------------------------------------------------------------
-- V_$LOCK_ACTIVITY
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW V_$LOCK_ACTIVITY AS 

/

-- -----------------------------------------------------------------------
-- V_$MAX_ACTIVE_SESS_TARGET_MTH
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW V_$MAX_ACTIVE_SESS_TARGET_MTH AS 

/

-- -----------------------------------------------------------------------
-- V_$NLS_PARAMETERS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW V_$NLS_PARAMETERS AS 

/

-- -----------------------------------------------------------------------
-- V_$NLS_VALID_VALUES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW V_$NLS_VALID_VALUES AS 

/

-- -----------------------------------------------------------------------
-- V_$OPTION
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW V_$OPTION AS 

/

-- -----------------------------------------------------------------------
-- V_$PARALLEL_DEGREE_LIMIT_MTH
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW V_$PARALLEL_DEGREE_LIMIT_MTH AS 

/

-- -----------------------------------------------------------------------
-- V_$PQ_SESSTAT
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW V_$PQ_SESSTAT AS 

/

-- -----------------------------------------------------------------------
-- V_$PQ_TQSTAT
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW V_$PQ_TQSTAT AS 

/

-- -----------------------------------------------------------------------
-- V_$QUEUEING_MTH
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW V_$QUEUEING_MTH AS 

/

-- -----------------------------------------------------------------------
-- V_$RESTORE_POINT
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW V_$RESTORE_POINT AS 

/

-- -----------------------------------------------------------------------
-- V_$RSRC_CONSUMER_GROUP
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW V_$RSRC_CONSUMER_GROUP AS 

/

-- -----------------------------------------------------------------------
-- V_$RSRC_CONSUMER_GROUP_CPU_MTH
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW V_$RSRC_CONSUMER_GROUP_CPU_MTH AS 

/

-- -----------------------------------------------------------------------
-- V_$RSRC_CONS_GROUP_HISTORY
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW V_$RSRC_CONS_GROUP_HISTORY AS 

/

-- -----------------------------------------------------------------------
-- V_$RSRC_PLAN
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW V_$RSRC_PLAN AS 

/

-- -----------------------------------------------------------------------
-- V_$RSRC_PLAN_CPU_MTH
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW V_$RSRC_PLAN_CPU_MTH AS 

/

-- -----------------------------------------------------------------------
-- V_$RSRC_PLAN_HISTORY
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW V_$RSRC_PLAN_HISTORY AS 

/

-- -----------------------------------------------------------------------
-- V_$RSRC_SESSION_INFO
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW V_$RSRC_SESSION_INFO AS 

/

-- -----------------------------------------------------------------------
-- V_$SESSION_CONNECT_INFO
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW V_$SESSION_CONNECT_INFO AS 

/

-- -----------------------------------------------------------------------
-- V_$SESSION_LONGOPS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW V_$SESSION_LONGOPS AS 

/

-- -----------------------------------------------------------------------
-- V_$TEMPORARY_LOBS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW V_$TEMPORARY_LOBS AS 

/

-- -----------------------------------------------------------------------
-- V_$TIMEZONE_FILE
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW V_$TIMEZONE_FILE AS 

/

-- -----------------------------------------------------------------------
-- V_$TIMEZONE_NAMES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW V_$TIMEZONE_NAMES AS 

/

-- -----------------------------------------------------------------------
-- V_$VERSION
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW V_$VERSION AS 

/

-- -----------------------------------------------------------------------
-- _ALL_FILE_GROUPS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW _ALL_FILE_GROUPS AS 

/

-- -----------------------------------------------------------------------
-- _ALL_FILE_GROUP_EXPORT_INFO
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW _ALL_FILE_GROUP_EXPORT_INFO AS 

/

-- -----------------------------------------------------------------------
-- _ALL_FILE_GROUP_FILES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW _ALL_FILE_GROUP_FILES AS 

/

-- -----------------------------------------------------------------------
-- _ALL_FILE_GROUP_TABLES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW _ALL_FILE_GROUP_TABLES AS 

/

-- -----------------------------------------------------------------------
-- _ALL_FILE_GROUP_TABLESPACES
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW _ALL_FILE_GROUP_TABLESPACES AS 

/

-- -----------------------------------------------------------------------
-- _ALL_FILE_GROUP_VERSIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW _ALL_FILE_GROUP_VERSIONS AS 

/

-- -----------------------------------------------------------------------
-- _ALL_INSTANTIATION_DDL
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW _ALL_INSTANTIATION_DDL AS 

/

-- -----------------------------------------------------------------------
-- _ALL_REPCOLUMN
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW _ALL_REPCOLUMN AS 

/

-- -----------------------------------------------------------------------
-- _ALL_REPCOLUMN_GROUP
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW _ALL_REPCOLUMN_GROUP AS 

/

-- -----------------------------------------------------------------------
-- _ALL_REPCONFLICT
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW _ALL_REPCONFLICT AS 

/

-- -----------------------------------------------------------------------
-- _ALL_REPEXTENSIONS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW _ALL_REPEXTENSIONS AS 

/

-- -----------------------------------------------------------------------
-- _ALL_REPFLAVOR_OBJECTS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW _ALL_REPFLAVOR_OBJECTS AS 

/

-- -----------------------------------------------------------------------
-- _ALL_REPGROUPED_COLUMN
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW _ALL_REPGROUPED_COLUMN AS 

/

-- -----------------------------------------------------------------------
-- _ALL_REPPARAMETER_COLUMN
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW _ALL_REPPARAMETER_COLUMN AS 

/

-- -----------------------------------------------------------------------
-- _ALL_REPRESOLUTION
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW _ALL_REPRESOLUTION AS 

/

-- -----------------------------------------------------------------------
-- _ALL_REPSITES_NEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW _ALL_REPSITES_NEW AS 

/

-- -----------------------------------------------------------------------
-- _ALL_SQLSET_STATEMENTS_ONLY
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW _ALL_SQLSET_STATEMENTS_ONLY AS 

/

-- -----------------------------------------------------------------------
-- _ALL_SQLSET_STATEMENTS_PHV
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW _ALL_SQLSET_STATEMENTS_PHV AS 

/

-- -----------------------------------------------------------------------
-- _ALL_SQLSET_STATISTICS_ONLY
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW _ALL_SQLSET_STATISTICS_ONLY AS 

/

-- -----------------------------------------------------------------------
-- PRODUCT_PRIVS
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW PRODUCT_PRIVS AS 

/

-- -----------------------------------------------------------------------
-- PATH_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW PATH_VIEW AS 

/

-- -----------------------------------------------------------------------
-- RESOURCE_VIEW
-- -----------------------------------------------------------------------
CREATE OR REPLACE FORCE VIEW RESOURCE_VIEW AS 

/
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_sequences WHERE sequence_name = 'WWV_FLOW_SESSION_SEQ';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP SEQUENCE WWV_FLOW_SESSION_SEQ'; END IF;
END;
/

CREATE SEQUENCE WWV_FLOW_SESSION_SEQ INCREMENT BY 1 START WITH 0 NOMAXVALUE NOCYCLE NOCACHE ORDER
/

DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_sequences WHERE sequence_name = 'WWV_SEQ';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP SEQUENCE WWV_SEQ'; END IF;
END;
/

CREATE SEQUENCE WWV_SEQ INCREMENT BY 1 START WITH 0 NOMAXVALUE NOCYCLE NOCACHE ORDER
/

DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_sequences WHERE sequence_name = 'SAMPLE_SEQ';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP SEQUENCE SAMPLE_SEQ'; END IF;
END;
/

CREATE SEQUENCE SAMPLE_SEQ INCREMENT BY 1 START WITH 0 NOMAXVALUE NOCYCLE NOCACHE ORDER
/

DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_sequences WHERE sequence_name = 'SDO_IDX_TAB_SEQUENCE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP SEQUENCE SDO_IDX_TAB_SEQUENCE'; END IF;
END;
/

CREATE SEQUENCE SDO_IDX_TAB_SEQUENCE INCREMENT BY 1 START WITH 0 NOMAXVALUE NOCYCLE NOCACHE ORDER
/

DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_sequences WHERE sequence_name = 'SCHEDULER$_JOBSUFFIX_S';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP SEQUENCE SCHEDULER$_JOBSUFFIX_S'; END IF;
END;
/

CREATE SEQUENCE SCHEDULER$_JOBSUFFIX_S INCREMENT BY 1 START WITH 0 NOMAXVALUE NOCYCLE NOCACHE ORDER
/

DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_sequences WHERE sequence_name = 'XDB$NAMESUFF_SEQ';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP SEQUENCE XDB$NAMESUFF_SEQ'; END IF;
END;
/

CREATE SEQUENCE XDB$NAMESUFF_SEQ INCREMENT BY 1 START WITH 0 NOMAXVALUE NOCYCLE NOCACHE ORDER
/

DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_sequences WHERE sequence_name = 'DR$NUMBER_SEQUENCE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP SEQUENCE DR$NUMBER_SEQUENCE'; END IF;
END;
/

CREATE SEQUENCE DR$NUMBER_SEQUENCE INCREMENT BY 1 START WITH 0 NOMAXVALUE NOCYCLE NOCACHE ORDER
/

