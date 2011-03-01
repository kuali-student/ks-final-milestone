
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
        , VER_NBR NUMBER(19) NOT NULL
        , CURR_VER_END TIMESTAMP
        , CURR_VER_START TIMESTAMP
        , SEQ_NUM NUMBER(19)
        , VER_CMT VARCHAR2(255)
        , VER_IND_ID VARCHAR2(255)
        , VER_FROM_ID VARCHAR2(255)
        , CAN_CREATE_LUI NUMBER(1)
        , DEF_ENRL_EST NUMBER(10)
        , DEF_MAX_ENRL NUMBER(10)
        , EFF_DT TIMESTAMP
        , EXP_FIRST_ATP VARCHAR2(255)
        , EXPIR_DT TIMESTAMP
        , HAS_EARLY_DROP_DEDLN NUMBER(1)
        , CLU_INTSTY_QTY VARCHAR2(255)
        , CLU_INTSTY_TYPE VARCHAR2(255)
        , IS_ENRL NUMBER(1)
        , IS_HAZR_DISBLD_STU NUMBER(1)
        , LAST_ADMIT_ATP VARCHAR2(255)
        , LAST_ATP VARCHAR2(255)
        , NEXT_REVIEW_PRD VARCHAR2(255)
        , REF_URL VARCHAR2(255)
        , ST VARCHAR2(255)
        , ATP_DUR_TYP_KEY VARCHAR2(255)
        , TM_QUANTITY NUMBER(10)
        , STDY_SUBJ_AREA VARCHAR2(255)
        , ACCT_ID VARCHAR2(255)
        , RT_DESCR_ID VARCHAR2(255)
        , FEE_ID VARCHAR2(255)
        , LUTYPE_ID VARCHAR2(255)
        , OFFIC_CLU_ID VARCHAR2(255)
        , PRI_INSTR_ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
    
    , CONSTRAINT SYS_C0011370 UNIQUE (VER_IND_ID, SEQ_NUM)

)
/

ALTER TABLE KSLU_CLU
    ADD CONSTRAINT KSLU_CLUP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_CLU_I1 
  ON KSLU_CLU 
  (PRI_INSTR_ID)
/
CREATE INDEX KSLU_CLU_I2 
  ON KSLU_CLU 
  (LUTYPE_ID)
/
CREATE INDEX KSLU_CLU_I3 
  ON KSLU_CLU 
  (RT_DESCR_ID)
/
CREATE INDEX KSLU_CLU_I4 
  ON KSLU_CLU 
  (FEE_ID)
/
CREATE INDEX KSLU_CLU_I5 
  ON KSLU_CLU 
  (OFFIC_CLU_ID)
/
CREATE INDEX KSLU_CLU_I6 
  ON KSLU_CLU 
  (ACCT_ID)
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
        , VER_NBR NUMBER(19) NOT NULL
        , CLU_RELTN_REQ NUMBER(1)
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , ST VARCHAR2(255)
        , CLU_ID VARCHAR2(255)
        , LU_RELTN_TYPE_ID VARCHAR2(255)
        , RELATED_CLU_ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
    

)
/

ALTER TABLE KSLU_CLUCLU_RELTN
    ADD CONSTRAINT KSLU_CLUCLU_RELTNP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_CLUCLU_RELTN_I1 
  ON KSLU_CLUCLU_RELTN 
  (CLU_ID)
/
CREATE INDEX KSLU_CLUCLU_RELTN_I2 
  ON KSLU_CLUCLU_RELTN 
  (RELATED_CLU_ID)
/
CREATE INDEX KSLU_CLUCLU_RELTN_I3 
  ON KSLU_CLUCLU_RELTN 
  (LU_RELTN_TYPE_ID)
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
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_CLUCLU_RELTN_ATTR
    ADD CONSTRAINT KSLU_CLUCLU_RELTN_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_CLUCLU_RELTN_ATTR_I1 
  ON KSLU_CLUCLU_RELTN_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSLU_CLURES_JN_RESOPT
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLURES_JN_RESOPT';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLURES_JN_RESOPT CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLURES_JN_RESOPT
(
      CLU_RES_ID VARCHAR2(255) NOT NULL
        , RES_OPT_ID VARCHAR2(255) NOT NULL
    
    , CONSTRAINT SYS_C0011378 UNIQUE (RES_OPT_ID)

)
/



CREATE INDEX KSLU_CLURES_JN_RESOPT_I1 
  ON KSLU_CLURES_JN_RESOPT 
  (CLU_RES_ID)
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
        , VER_NBR NUMBER(19) NOT NULL
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , ORG_ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
    

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
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_CLU_ACCRED_ATTR
    ADD CONSTRAINT KSLU_CLU_ACCRED_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_CLU_ACCRED_ATTR_I1 
  ON KSLU_CLU_ACCRED_ATTR 
  (OWNER)
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
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

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
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_CLU_ACCT_ATTR
    ADD CONSTRAINT KSLU_CLU_ACCT_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_CLU_ACCT_ATTR_I1 
  ON KSLU_CLU_ACCT_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSLU_CLU_ACCT_JN_AFFIL_ORG
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_ACCT_JN_AFFIL_ORG';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_ACCT_JN_AFFIL_ORG CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_ACCT_JN_AFFIL_ORG
(
      CLU_ACCT_ID VARCHAR2(255) NOT NULL
        , AFFIL_ORG_ID VARCHAR2(255) NOT NULL
    

)
/



CREATE INDEX KSLU_CLU_ACCT_JN_AFFIL_ORG_I1 
  ON KSLU_CLU_ACCT_JN_AFFIL_ORG 
  (AFFIL_ORG_ID)
/
CREATE INDEX KSLU_CLU_ACCT_JN_AFFIL_ORG_I2 
  ON KSLU_CLU_ACCT_JN_AFFIL_ORG 
  (CLU_ACCT_ID)
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
        , IS_PR NUMBER(1)
        , ORG_ID VARCHAR2(255)
        , TYPE VARCHAR2(255)
        , CLU_ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_CLU_ADMIN_ORG
    ADD CONSTRAINT KSLU_CLU_ADMIN_ORGP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_CLU_ADMIN_ORG_I1 
  ON KSLU_CLU_ADMIN_ORG 
  (CLU_ID)
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
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_CLU_ADMIN_ORG_ATTR
    ADD CONSTRAINT KSLU_CLU_ADMIN_ORG_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_CLU_ADMIN_ORG_ATTR_I1 
  ON KSLU_CLU_ADMIN_ORG_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSLU_CLU_AFFIL_ORG
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_AFFIL_ORG';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_AFFIL_ORG CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_AFFIL_ORG
(
      ID VARCHAR2(255)
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , ORG_ID VARCHAR2(255)
        , PERCT NUMBER(19)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_CLU_AFFIL_ORG
    ADD CONSTRAINT KSLU_CLU_AFFIL_ORGP1
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
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_CLU_ATP_TYPE_KEY
    ADD CONSTRAINT KSLU_CLU_ATP_TYPE_KEYP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_CLU_ATP_TYPE_KEY_I1 
  ON KSLU_CLU_ATP_TYPE_KEY 
  (CLU_ID)
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
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_CLU_ATTR
    ADD CONSTRAINT KSLU_CLU_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_CLU_ATTR_I1 
  ON KSLU_CLU_ATTR 
  (OWNER)
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
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

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
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , VER_NBR NUMBER(19) NOT NULL
        , RT_DESCR_ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
    

)
/

ALTER TABLE KSLU_CLU_FEE
    ADD CONSTRAINT KSLU_CLU_FEEP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_CLU_FEE_I1 
  ON KSLU_CLU_FEE 
  (RT_DESCR_ID)
/





-----------------------------------------------------------------------------
-- KSLU_CLU_FEEREC_JN_AFFIL_ORG
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_FEEREC_JN_AFFIL_ORG';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_FEEREC_JN_AFFIL_ORG CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_FEEREC_JN_AFFIL_ORG
(
      CLU_FEE_REC_ID VARCHAR2(255) NOT NULL
        , AFFIL_ORG_ID VARCHAR2(255) NOT NULL
    

)
/



CREATE INDEX KSLU_CLU_FEEREC_JN_AFF_ORG_I1 
  ON KSLU_CLU_FEEREC_JN_AFFIL_ORG 
  (AFFIL_ORG_ID)
/
CREATE INDEX KSLU_CLU_FEEREC_JN_AFF_ORG_I2 
  ON KSLU_CLU_FEEREC_JN_AFFIL_ORG 
  (CLU_FEE_REC_ID)
/





-----------------------------------------------------------------------------
-- KSLU_CLU_FEE_AMOUNT
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_FEE_AMOUNT';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_FEE_AMOUNT CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_FEE_AMOUNT
(
      ID VARCHAR2(255)
        , CURRENCY_QUANT NUMBER(10)
        , CURRENCY_TYPE VARCHAR2(255)
        , CLUE_FEE_REC_ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_CLU_FEE_AMOUNT
    ADD CONSTRAINT KSLU_CLU_FEE_AMOUNTP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_CLU_FEE_AMOUNT_I1 
  ON KSLU_CLU_FEE_AMOUNT 
  (CLUE_FEE_REC_ID)
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
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_CLU_FEE_ATTR
    ADD CONSTRAINT KSLU_CLU_FEE_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_CLU_FEE_ATTR_I1 
  ON KSLU_CLU_FEE_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSLU_CLU_FEE_JN_CLU_FEE_REC
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_FEE_JN_CLU_FEE_REC';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_FEE_JN_CLU_FEE_REC CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_FEE_JN_CLU_FEE_REC
(
      CLU_FEE_ID VARCHAR2(255) NOT NULL
        , CLU_FEE_REC_ID VARCHAR2(255) NOT NULL
    

)
/



CREATE INDEX KSLU_CLUFEE_JN_CLUFEE_REC_I1 
  ON KSLU_CLU_FEE_JN_CLU_FEE_REC 
  (CLU_FEE_REC_ID)
/
CREATE INDEX KSLU_CLUFEE_JN_CLUFEE_REC_I2 
  ON KSLU_CLU_FEE_JN_CLU_FEE_REC 
  (CLU_FEE_ID)
/





-----------------------------------------------------------------------------
-- KSLU_CLU_FEE_REC
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_FEE_REC';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_FEE_REC CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_FEE_REC
(
      ID VARCHAR2(255)
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , VER_NBR NUMBER(19) NOT NULL
        , FEE_TYPE VARCHAR2(255)
        , RATE_TYPE VARCHAR2(255)
        , RT_DESCR_ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
    

)
/

ALTER TABLE KSLU_CLU_FEE_REC
    ADD CONSTRAINT KSLU_CLU_FEE_RECP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_CLU_FEE_REC_I1 
  ON KSLU_CLU_FEE_REC 
  (RT_DESCR_ID)
/





-----------------------------------------------------------------------------
-- KSLU_CLU_FEE_REC_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_FEE_REC_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_FEE_REC_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_FEE_REC_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_CLU_FEE_REC_ATTR
    ADD CONSTRAINT KSLU_CLU_FEE_REC_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_CLU_FEE_REC_ATTR_I1 
  ON KSLU_CLU_FEE_REC_ATTR 
  (OWNER)
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
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

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
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

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
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_CLU_INSTR_ATTR
    ADD CONSTRAINT KSLU_CLU_INSTR_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_CLU_INSTR_ATTR_I1 
  ON KSLU_CLU_INSTR_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSLU_CLU_JN_ACCRED
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_JN_ACCRED';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_JN_ACCRED CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_JN_ACCRED
(
      CLU_ID VARCHAR2(255) NOT NULL
        , CLU_ACCRED_ID VARCHAR2(255) NOT NULL
    
    , CONSTRAINT SYS_C0011436 UNIQUE (CLU_ACCRED_ID)

)
/



CREATE INDEX KSLU_CLU_JN_ACCRED_I1 
  ON KSLU_CLU_JN_ACCRED 
  (CLU_ID)
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
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_CLU_JN_CAMP_LOC
    ADD CONSTRAINT KSLU_CLU_JN_CAMP_LOCP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_CLU_JN_CAMP_LOC_I1 
  ON KSLU_CLU_JN_CAMP_LOC 
  (CLU_ID)
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
    
    , CONSTRAINT SYS_C0011441 UNIQUE (ALT_CLU_ID)

)
/



CREATE INDEX KSLU_CLU_JN_CLU_IDENT_I2 
  ON KSLU_CLU_JN_CLU_IDENT 
  (CLU_ID)
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
    
    , CONSTRAINT SYS_C0011444 UNIQUE (CLU_INSTR_ID)

)
/



CREATE INDEX KSLU_CLU_JN_CLU_INSTR_I1 
  ON KSLU_CLU_JN_CLU_INSTR 
  (CLU_ID)
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
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_CLU_JN_SUBJ_ORG
    ADD CONSTRAINT KSLU_CLU_JN_SUBJ_ORGP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_CLU_JN_SUBJ_ORG_I1 
  ON KSLU_CLU_JN_SUBJ_ORG 
  (CLU_ID)
/





-----------------------------------------------------------------------------
-- KSLU_CLU_LO_ALOW_RELTN_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_LO_ALOW_RELTN_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_LO_ALOW_RELTN_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_LO_ALOW_RELTN_TYPE
(
      ID VARCHAR2(255)
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , VER_NBR NUMBER(19) NOT NULL
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , LO_TYPE_ID VARCHAR2(255)
        , CLULO_RELTN_TYPE_ID VARCHAR2(255)
        , LU_TYPE_ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
    

)
/

ALTER TABLE KSLU_CLU_LO_ALOW_RELTN_TYPE
    ADD CONSTRAINT KSLU_CLU_LO_ALOW_RELTN_TYPEP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_CLU_LO_ALOW_REL_TYPE_I1 
  ON KSLU_CLU_LO_ALOW_RELTN_TYPE 
  (LU_TYPE_ID)
/
CREATE INDEX KSLU_CLU_LO_ALOW_REL_TYPE_I2 
  ON KSLU_CLU_LO_ALOW_RELTN_TYPE 
  (CLULO_RELTN_TYPE_ID)
/





-----------------------------------------------------------------------------
-- KSLU_CLU_LO_RELTN
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_LO_RELTN';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_LO_RELTN CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_LO_RELTN
(
      ID VARCHAR2(255)
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , VER_NBR NUMBER(19) NOT NULL
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , LO_ID VARCHAR2(255)
        , ST VARCHAR2(255)
        , TYPE VARCHAR2(255)
        , CLU_ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
    

)
/

ALTER TABLE KSLU_CLU_LO_RELTN
    ADD CONSTRAINT KSLU_CLU_LO_RELTNP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_CLU_LO_RELTN_I1 
  ON KSLU_CLU_LO_RELTN 
  (CLU_ID)
/





-----------------------------------------------------------------------------
-- KSLU_CLU_LO_RELTN_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_LO_RELTN_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_LO_RELTN_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_LO_RELTN_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_CLU_LO_RELTN_ATTR
    ADD CONSTRAINT KSLU_CLU_LO_RELTN_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_CLU_LO_RELTN_ATTR_I1 
  ON KSLU_CLU_LO_RELTN_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSLU_CLU_LO_RELTN_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_LO_RELTN_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_LO_RELTN_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_LO_RELTN_TYPE
(
      TYPE_KEY VARCHAR2(255)
        , TYPE_DESC VARCHAR2(2000)
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , NAME VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_CLU_LO_RELTN_TYPE
    ADD CONSTRAINT KSLU_CLU_LO_RELTN_TYPEP1
PRIMARY KEY (TYPE_KEY)
/







-----------------------------------------------------------------------------
-- KSLU_CLU_LO_RELTN_TYPE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_LO_RELTN_TYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_LO_RELTN_TYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_LO_RELTN_TYPE_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_CLU_LO_RELTN_TYPE_ATTR
    ADD CONSTRAINT KSLU_CLU_LO_RELTN_TYPE_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_CLU_LO_REL_TYPE_ATTR_I1 
  ON KSLU_CLU_LO_RELTN_TYPE_ATTR 
  (OWNER)
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
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , VER_NBR NUMBER(19) NOT NULL
        , EFF_DT TIMESTAMP
        , END_CYCLE VARCHAR2(255)
        , EXPIR_DT TIMESTAMP
        , START_CYCLE VARCHAR2(255)
        , ST VARCHAR2(255)
        , CLU_ID VARCHAR2(255)
        , CLU_PUB_TYPE_ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
    

)
/

ALTER TABLE KSLU_CLU_PUBL
    ADD CONSTRAINT KSLU_CLU_PUBLP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_CLU_PUBL_I1 
  ON KSLU_CLU_PUBL 
  (CLU_ID)
/
CREATE INDEX KSLU_CLU_PUBL_I2 
  ON KSLU_CLU_PUBL 
  (CLU_PUB_TYPE_ID)
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
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_CLU_PUBL_ATTR
    ADD CONSTRAINT KSLU_CLU_PUBL_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_CLU_PUBL_ATTR_I1 
  ON KSLU_CLU_PUBL_ATTR 
  (OWNER)
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
    
    , CONSTRAINT SYS_C009456 UNIQUE (CLU_INSTR_ID)

)
/








-----------------------------------------------------------------------------
-- KSLU_CLU_PUBL_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_PUBL_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_PUBL_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_PUBL_TYPE
(
      TYPE_KEY VARCHAR2(255)
        , TYPE_DESC VARCHAR2(2000)
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , NAME VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_CLU_PUBL_TYPE
    ADD CONSTRAINT KSLU_CLU_PUBL_TYPEP1
PRIMARY KEY (TYPE_KEY)
/







-----------------------------------------------------------------------------
-- KSLU_CLU_PUBL_TYPE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_PUBL_TYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_PUBL_TYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_PUBL_TYPE_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_CLU_PUBL_TYPE_ATTR
    ADD CONSTRAINT KSLU_CLU_PUBL_TYPE_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_CLU_PUBL_TYPE_ATTR_I1 
  ON KSLU_CLU_PUBL_TYPE_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSLU_CLU_PUBL_VARI
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_PUBL_VARI';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_PUBL_VARI CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_PUBL_VARI
(
      ID VARCHAR2(255)
        , VARI_KEY VARCHAR2(255)
        , VARI_VALUE VARCHAR2(255)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    
    , CONSTRAINT SYS_C0011470 UNIQUE (VARI_KEY, OWNER)

)
/

ALTER TABLE KSLU_CLU_PUBL_VARI
    ADD CONSTRAINT KSLU_CLU_PUBL_VARIP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_CLU_PUBL_VARI_I1 
  ON KSLU_CLU_PUBL_VARI 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSLU_CLU_PUB_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_PUB_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_PUB_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_PUB_TYPE
(
      TYPE_KEY VARCHAR2(255)
        , TYPE_DESC VARCHAR2(2000)
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , NAME VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
    

)
/

ALTER TABLE KSLU_CLU_PUB_TYPE
    ADD CONSTRAINT KSLU_CLU_PUB_TYPEP1
PRIMARY KEY (TYPE_KEY)
/







-----------------------------------------------------------------------------
-- KSLU_CLU_PUB_TYPE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_PUB_TYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_PUB_TYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_PUB_TYPE_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
    

)
/

ALTER TABLE KSLU_CLU_PUB_TYPE_ATTR
    ADD CONSTRAINT KSLU_CLU_PUB_TYPE_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_CLU_PUB_TYPE_ATTR_I1 
  ON KSLU_CLU_PUB_TYPE_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSLU_CLU_RESULT_TYPE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_RESULT_TYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_RESULT_TYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_RESULT_TYPE_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_CLU_RESULT_TYPE_ATTR
    ADD CONSTRAINT KSLU_CLU_RESULT_TYPE_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_CLU_RESULT_TYPE_ATTR_I1 
  ON KSLU_CLU_RESULT_TYPE_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSLU_CLU_RSLT
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_RSLT';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_RSLT CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_RSLT
(
      ID VARCHAR2(255)
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , VER_NBR NUMBER(19) NOT NULL
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , ST VARCHAR2(255)
        , CLU_ID VARCHAR2(255)
        , TYPE_KEY_ID VARCHAR2(255)
        , RT_DESCR_ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
    

)
/

ALTER TABLE KSLU_CLU_RSLT
    ADD CONSTRAINT KSLU_CLU_RSLTP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_CLU_RSLT_I1 
  ON KSLU_CLU_RSLT 
  (TYPE_KEY_ID)
/
CREATE INDEX KSLU_CLU_RSLT_I2 
  ON KSLU_CLU_RSLT 
  (CLU_ID)
/
CREATE INDEX KSLU_CLU_RSLT_I3 
  ON KSLU_CLU_RSLT 
  (RT_DESCR_ID)
/





-----------------------------------------------------------------------------
-- KSLU_CLU_RSLT_LU_ALOW_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_RSLT_LU_ALOW_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_RSLT_LU_ALOW_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_RSLT_LU_ALOW_TYPE
(
      ID VARCHAR2(255)
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , VER_NBR NUMBER(19) NOT NULL
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , CLU_RSLT_TYPE_ID VARCHAR2(255)
        , LU_TYPE_ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
    

)
/

ALTER TABLE KSLU_CLU_RSLT_LU_ALOW_TYPE
    ADD CONSTRAINT KSLU_CLU_RSLT_LU_ALOW_TYPEP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_CLU_RSLT_LU_ALOW_TYPE_I1 
  ON KSLU_CLU_RSLT_LU_ALOW_TYPE 
  (CLU_RSLT_TYPE_ID)
/
CREATE INDEX KSLU_CLU_RSLT_LU_ALOW_TYPE_I2 
  ON KSLU_CLU_RSLT_LU_ALOW_TYPE 
  (LU_TYPE_ID)
/





-----------------------------------------------------------------------------
-- KSLU_CLU_RSLT_TYP
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_RSLT_TYP';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_RSLT_TYP CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_RSLT_TYP
(
      TYPE_KEY VARCHAR2(255)
        , TYPE_DESC VARCHAR2(2000)
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , NAME VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_CLU_RSLT_TYP
    ADD CONSTRAINT KSLU_CLU_RSLT_TYPP1
PRIMARY KEY (TYPE_KEY)
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
        , VER_NBR NUMBER(19) NOT NULL
        , ADMIN_ORG_ID VARCHAR2(255)
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , REFERENCEABLE NUMBER(1)
        , REUSABLE NUMBER(1)
        , NAME VARCHAR2(255)
        , ST VARCHAR2(255)
        , TYPE VARCHAR2(255)
        , RT_DESCR_ID VARCHAR2(255)
        , MEM_QUERY_ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
    

)
/

ALTER TABLE KSLU_CLU_SET
    ADD CONSTRAINT KSLU_CLU_SETP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_CLU_SET_I1 
  ON KSLU_CLU_SET 
  (MEM_QUERY_ID)
/
CREATE INDEX KSLU_CLU_SET_I2 
  ON KSLU_CLU_SET 
  (RT_DESCR_ID)
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
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_CLU_SET_ATTR
    ADD CONSTRAINT KSLU_CLU_SET_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_CLU_SET_ATTR_I1 
  ON KSLU_CLU_SET_ATTR 
  (OWNER)
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



CREATE INDEX KSLU_CLU_SET_JN_CLU_I1 
  ON KSLU_CLU_SET_JN_CLU 
  (CLU_SET_ID)
/
CREATE INDEX KSLU_CLU_SET_JN_CLU_I2 
  ON KSLU_CLU_SET_JN_CLU 
  (CLU_ID)
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



CREATE INDEX KSLU_CLU_SET_JN_CLU_SET_I1 
  ON KSLU_CLU_SET_JN_CLU_SET 
  (CLU_SET_PARENT_ID)
/
CREATE INDEX KSLU_CLU_SET_JN_CLU_SET_I2 
  ON KSLU_CLU_SET_JN_CLU_SET 
  (CLU_SET_CHILD_ID)
/





-----------------------------------------------------------------------------
-- KSLU_CLU_SET_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_SET_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_SET_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_SET_TYPE
(
      TYPE_KEY VARCHAR2(255)
        , TYPE_DESC VARCHAR2(2000)
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , NAME VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_CLU_SET_TYPE
    ADD CONSTRAINT KSLU_CLU_SET_TYPEP1
PRIMARY KEY (TYPE_KEY)
/







-----------------------------------------------------------------------------
-- KSLU_CLU_SET_TYPE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_CLU_SET_TYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_CLU_SET_TYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_CLU_SET_TYPE_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_CLU_SET_TYPE_ATTR
    ADD CONSTRAINT KSLU_CLU_SET_TYPE_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_CLU_SET_TYPE_ATTR_I1 
  ON KSLU_CLU_SET_TYPE_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSLU_DLVMTHD_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_DLVMTHD_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_DLVMTHD_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_DLVMTHD_TYPE
(
      TYPE_KEY VARCHAR2(255)
        , TYPE_DESC VARCHAR2(2000)
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , NAME VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_DLVMTHD_TYPE
    ADD CONSTRAINT KSLU_DLVMTHD_TYPEP1
PRIMARY KEY (TYPE_KEY)
/







-----------------------------------------------------------------------------
-- KSLU_DLVMTHD_TYPE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_DLVMTHD_TYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_DLVMTHD_TYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_DLVMTHD_TYPE_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_DLVMTHD_TYPE_ATTR
    ADD CONSTRAINT KSLU_DLVMTHD_TYPE_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_DLVMTHD_TYPE_ATTR_I1 
  ON KSLU_DLVMTHD_TYPE_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSLU_INSTFRMT_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_INSTFRMT_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_INSTFRMT_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_INSTFRMT_TYPE
(
      TYPE_KEY VARCHAR2(255)
        , TYPE_DESC VARCHAR2(2000)
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , NAME VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_INSTFRMT_TYPE
    ADD CONSTRAINT KSLU_INSTFRMT_TYPEP1
PRIMARY KEY (TYPE_KEY)
/







-----------------------------------------------------------------------------
-- KSLU_INSTFRMT_TYPE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_INSTFRMT_TYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_INSTFRMT_TYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_INSTFRMT_TYPE_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_INSTFRMT_TYPE_ATTR
    ADD CONSTRAINT KSLU_INSTFRMT_TYPE_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_INSTFRMT_TYPE_ATTR_I1 
  ON KSLU_INSTFRMT_TYPE_ATTR 
  (OWNER)
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
        , VER_NBR NUMBER(19) NOT NULL
        , ATP_ID VARCHAR2(255)
        , EFF_DT TIMESTAMP
        , EXP_DT TIMESTAMP
        , LUI_CODE VARCHAR2(255)
        , MAX_SEATS NUMBER(10)
        , ST VARCHAR2(255)
        , CLU_ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
    

)
/

ALTER TABLE KSLU_LUI
    ADD CONSTRAINT KSLU_LUIP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_LUI_I1 
  ON KSLU_LUI 
  (CLU_ID)
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
        , VER_NBR NUMBER(19) NOT NULL
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , ST VARCHAR2(255)
        , LULU_RELTN_TYPE_ID VARCHAR2(255)
        , LUI_ID VARCHAR2(255)
        , RELATED_LUI_ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
    

)
/

ALTER TABLE KSLU_LUILUI_RELTN
    ADD CONSTRAINT KSLU_LUILUI_RELTNP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_LUILUI_RELTN_I1 
  ON KSLU_LUILUI_RELTN 
  (RELATED_LUI_ID)
/
CREATE INDEX KSLU_LUILUI_RELTN_I2 
  ON KSLU_LUILUI_RELTN 
  (LULU_RELTN_TYPE_ID)
/
CREATE INDEX KSLU_LUILUI_RELTN_I3 
  ON KSLU_LUILUI_RELTN 
  (LUI_ID)
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
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_LUILUI_RELTN_ATTR
    ADD CONSTRAINT KSLU_LUILUI_RELTN_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_LUILUI_RELTN_ATTR_I1 
  ON KSLU_LUILUI_RELTN_ATTR 
  (OWNER)
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
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_LUI_ATTR
    ADD CONSTRAINT KSLU_LUI_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_LUI_ATTR_I1 
  ON KSLU_LUI_ATTR 
  (OWNER)
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
        , VER_NBR NUMBER(19) NOT NULL
        , DESCR VARCHAR2(255)
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , NAME VARCHAR2(255)
        , REV_DESC VARCHAR2(255)
        , REV_NAME VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
    

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
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_LULU_RELTN_TYPE_ATTR
    ADD CONSTRAINT KSLU_LULU_RELTN_TYPE_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_LULU_RELTN_TYPE_ATTR_I1 
  ON KSLU_LULU_RELTN_TYPE_ATTR 
  (OWNER)
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



CREATE INDEX KSLU_LULU_RELTYP_JN_LUTYP_I1 
  ON KSLU_LULU_RELTN_TYPE_JN_LU_TYP 
  (LULU_RELTN_TYPE_ID)
/
CREATE INDEX KSLU_LULU_RELTYP_JN_LUTYP_I2 
  ON KSLU_LULU_RELTN_TYPE_JN_LU_TYP 
  (LU_TYPE_ID)
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
        , OBJ_ID VARCHAR2(36)
        , DLVR_MTHD VARCHAR2(255)
        , INSTR_FRMT VARCHAR2(255)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_LUTYPE
    ADD CONSTRAINT KSLU_LUTYPEP1
PRIMARY KEY (TYPE_KEY)
/







-----------------------------------------------------------------------------
-- KSLU_LU_CD_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_LU_CD_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_LU_CD_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_LU_CD_TYPE
(
      TYPE_KEY VARCHAR2(255)
        , TYPE_DESC VARCHAR2(2000)
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , NAME VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_LU_CD_TYPE
    ADD CONSTRAINT KSLU_LU_CD_TYPEP1
PRIMARY KEY (TYPE_KEY)
/







-----------------------------------------------------------------------------
-- KSLU_LU_CD_TYPE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_LU_CD_TYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_LU_CD_TYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_LU_CD_TYPE_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_LU_CD_TYPE_ATTR
    ADD CONSTRAINT KSLU_LU_CD_TYPE_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_LU_CD_TYPE_ATTR_I1 
  ON KSLU_LU_CD_TYPE_ATTR 
  (OWNER)
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
        , VER_NBR NUMBER(19) NOT NULL
        , DESCR VARCHAR2(255)
        , TYPE VARCHAR2(255)
        , VALUE VARCHAR2(255)
        , CLU_ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
    

)
/

ALTER TABLE KSLU_LU_CODE
    ADD CONSTRAINT KSLU_LU_CODEP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_LU_CODE_I1 
  ON KSLU_LU_CODE 
  (CLU_ID)
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
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_LU_CODE_ATTR
    ADD CONSTRAINT KSLU_LU_CODE_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_LU_CODE_ATTR_I1 
  ON KSLU_LU_CODE_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSLU_LU_LU_ALOW_RELTN_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_LU_LU_ALOW_RELTN_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_LU_LU_ALOW_RELTN_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_LU_LU_ALOW_RELTN_TYPE
(
      ID VARCHAR2(255)
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , VER_NBR NUMBER(19) NOT NULL
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , LU_TYPE_ID VARCHAR2(255)
        , LU_REL_TYPE_ID VARCHAR2(255)
        , LU_LU_RELTN_TYPE_ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
    

)
/

ALTER TABLE KSLU_LU_LU_ALOW_RELTN_TYPE
    ADD CONSTRAINT KSLU_LU_LU_ALOW_RELTN_TYPEP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_LU_LU_ALOW_RELTN_TYPE_I1 
  ON KSLU_LU_LU_ALOW_RELTN_TYPE 
  (LU_REL_TYPE_ID)
/
CREATE INDEX KSLU_LU_LU_ALOW_RELTN_TYPE_I2 
  ON KSLU_LU_LU_ALOW_RELTN_TYPE 
  (LU_LU_RELTN_TYPE_ID)
/
CREATE INDEX KSLU_LU_LU_ALOW_RELTN_TYPE_I3 
  ON KSLU_LU_LU_ALOW_RELTN_TYPE 
  (LU_TYPE_ID)
/





-----------------------------------------------------------------------------
-- KSLU_LU_PUBL_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_LU_PUBL_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_LU_PUBL_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_LU_PUBL_TYPE
(
      TYPE_KEY VARCHAR2(255)
        , TYPE_DESC VARCHAR2(2000)
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , NAME VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_LU_PUBL_TYPE
    ADD CONSTRAINT KSLU_LU_PUBL_TYPEP1
PRIMARY KEY (TYPE_KEY)
/







-----------------------------------------------------------------------------
-- KSLU_LU_PUBL_TYPE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_LU_PUBL_TYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_LU_PUBL_TYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_LU_PUBL_TYPE_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_LU_PUBL_TYPE_ATTR
    ADD CONSTRAINT KSLU_LU_PUBL_TYPE_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_LU_PUBL_TYPE_ATTR_I1 
  ON KSLU_LU_PUBL_TYPE_ATTR 
  (OWNER)
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
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_LU_TYPE_ATTR
    ADD CONSTRAINT KSLU_LU_TYPE_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_LU_TYPE_ATTR_I1 
  ON KSLU_LU_TYPE_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSLU_MEMSHIP
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_MEMSHIP';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_MEMSHIP CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_MEMSHIP
(
      ID VARCHAR2(255)
        , SEARCH_TYPE_KEY VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_MEMSHIP
    ADD CONSTRAINT KSLU_MEMSHIPP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_MEMSHIP_KSLU_SPARAM
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_MEMSHIP_KSLU_SPARAM';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_MEMSHIP_KSLU_SPARAM CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_MEMSHIP_KSLU_SPARAM
(
      KSLU_MEMSHIP_ID VARCHAR2(255) NOT NULL
        , SEARCHPARAMETERS_ID VARCHAR2(255) NOT NULL
    
    , CONSTRAINT SYS_C0011543 UNIQUE (SEARCHPARAMETERS_ID)

)
/



CREATE INDEX KSLU_MEMSHIP_KSLU_SPARAM_I1 
  ON KSLU_MEMSHIP_KSLU_SPARAM 
  (KSLU_MEMSHIP_ID)
/





-----------------------------------------------------------------------------
-- KSLU_RICH_TEXT_T
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_RICH_TEXT_T';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_RICH_TEXT_T CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_RICH_TEXT_T
(
      ID VARCHAR2(255)
        , FORMATTED VARCHAR2(500)
        , PLAIN VARCHAR2(2000)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_RICH_TEXT_T
    ADD CONSTRAINT KSLU_RICH_TEXT_TP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_RSLTUSAGE_LU_ALOW_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_RSLTUSAGE_LU_ALOW_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_RSLTUSAGE_LU_ALOW_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_RSLTUSAGE_LU_ALOW_TYPE
(
      ID VARCHAR2(255)
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , VER_NBR NUMBER(19) NOT NULL
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , LU_TYPE_ID VARCHAR2(255)
        , CLU_RSLT_USAGE_TYPE_ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
    

)
/

ALTER TABLE KSLU_RSLTUSAGE_LU_ALOW_TYPE
    ADD CONSTRAINT KSLU_RSLTUSAGE_LU_ALOW_TYPEP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_RSLTUSAGE_LU_ALOW_TYP_I1 
  ON KSLU_RSLTUSAGE_LU_ALOW_TYPE 
  (CLU_RSLT_USAGE_TYPE_ID)
/
CREATE INDEX KSLU_RSLTUSAGE_LU_ALOW_TYP_I2 
  ON KSLU_RSLTUSAGE_LU_ALOW_TYPE 
  (LU_TYPE_ID)
/





-----------------------------------------------------------------------------
-- KSLU_RSLT_COMP_USG_ALOW_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_RSLT_COMP_USG_ALOW_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_RSLT_COMP_USG_ALOW_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_RSLT_COMP_USG_ALOW_TYPE
(
      ID VARCHAR2(255)
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , VER_NBR NUMBER(19) NOT NULL
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , RSLT_COMP_ID VARCHAR2(255)
        , RSLT_USG_TYPE_ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
    

)
/

ALTER TABLE KSLU_RSLT_COMP_USG_ALOW_TYPE
    ADD CONSTRAINT KSLU_RSLT_COMP_USG_ALOW_TYPP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_RSLTCOMP_USG_ALOW_TYP_I1 
  ON KSLU_RSLT_COMP_USG_ALOW_TYPE 
  (RSLT_USG_TYPE_ID)
/





-----------------------------------------------------------------------------
-- KSLU_RSLT_OPT
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_RSLT_OPT';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_RSLT_OPT CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_RSLT_OPT
(
      ID VARCHAR2(255)
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , VER_NBR NUMBER(19) NOT NULL
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , RES_COMP_ID VARCHAR2(255)
        , ST VARCHAR2(255)
        , RT_DESCR_ID VARCHAR2(255)
        , RES_USAGE_ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
    

)
/

ALTER TABLE KSLU_RSLT_OPT
    ADD CONSTRAINT KSLU_RSLT_OPTP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_RSLT_OPT_I1 
  ON KSLU_RSLT_OPT 
  (RES_USAGE_ID)
/
CREATE INDEX KSLU_RSLT_OPT_I2 
  ON KSLU_RSLT_OPT 
  (RT_DESCR_ID)
/





-----------------------------------------------------------------------------
-- KSLU_RSLT_USG_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_RSLT_USG_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_RSLT_USG_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_RSLT_USG_TYPE
(
      TYPE_KEY VARCHAR2(255)
        , TYPE_DESC VARCHAR2(2000)
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , NAME VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_RSLT_USG_TYPE
    ADD CONSTRAINT KSLU_RSLT_USG_TYPEP1
PRIMARY KEY (TYPE_KEY)
/







-----------------------------------------------------------------------------
-- KSLU_RSLT_USG_TYPE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_RSLT_USG_TYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_RSLT_USG_TYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_RSLT_USG_TYPE_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_RSLT_USG_TYPE_ATTR
    ADD CONSTRAINT KSLU_RSLT_USG_TYPE_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_RSLT_USG_TYPE_ATTR_I1 
  ON KSLU_RSLT_USG_TYPE_ATTR 
  (OWNER)
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
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    
    , CONSTRAINT SYS_C0011561 UNIQUE (RSRC_TYPE_ID, CLU_ID)

)
/

ALTER TABLE KSLU_RSRC
    ADD CONSTRAINT KSLU_RSRCP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLU_RSRC_I1 
  ON KSLU_RSRC 
  (CLU_ID)
/





-----------------------------------------------------------------------------
-- KSLU_SPARAM
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_SPARAM';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_SPARAM CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_SPARAM
(
      ID VARCHAR2(255)
        , SEARCH_PARAM_KEY VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_SPARAM
    ADD CONSTRAINT KSLU_SPARAMP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLU_SPARAM_KSLU_SPVALUE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_SPARAM_KSLU_SPVALUE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_SPARAM_KSLU_SPVALUE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_SPARAM_KSLU_SPVALUE
(
      KSLU_SPARAM_ID VARCHAR2(255) NOT NULL
        , VALUES_ID VARCHAR2(255) NOT NULL
    
    , CONSTRAINT SYS_C0011566 UNIQUE (VALUES_ID)

)
/



CREATE INDEX KSLU_SPARAM_KSLU_SPVALUE_I1 
  ON KSLU_SPARAM_KSLU_SPVALUE 
  (KSLU_SPARAM_ID)
/





-----------------------------------------------------------------------------
-- KSLU_SPVALUE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLU_SPVALUE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLU_SPVALUE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLU_SPVALUE
(
      ID VARCHAR2(255)
        , VALUE VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLU_SPVALUE
    ADD CONSTRAINT KSLU_SPVALUEP1
PRIMARY KEY (ID)
/


