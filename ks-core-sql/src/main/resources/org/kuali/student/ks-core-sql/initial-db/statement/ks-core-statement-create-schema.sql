

-----------------------------------------------------------------------------
-- KSST_NL_USAGE_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSST_NL_USAGE_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSST_NL_USAGE_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSST_NL_USAGE_TYPE
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

ALTER TABLE KSST_NL_USAGE_TYPE
    ADD CONSTRAINT KSST_NL_USAGE_TYPEP1
PRIMARY KEY (TYPE_KEY)
/







-----------------------------------------------------------------------------
-- KSST_OBJECT_SUB_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSST_OBJECT_SUB_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSST_OBJECT_SUB_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSST_OBJECT_SUB_TYPE
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

ALTER TABLE KSST_OBJECT_SUB_TYPE
    ADD CONSTRAINT KSST_OBJECT_SUB_TYPEP1
PRIMARY KEY (TYPE_KEY)
/







-----------------------------------------------------------------------------
-- KSST_OBJECT_SUB_TYPE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSST_OBJECT_SUB_TYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSST_OBJECT_SUB_TYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSST_OBJECT_SUB_TYPE_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSST_OBJECT_SUB_TYPE_ATTR
    ADD CONSTRAINT KSST_OBJECT_SUB_TYPE_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSST_OBJECT_SUB_TYPE_ATTR_I1 
  ON KSST_OBJECT_SUB_TYPE_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSST_OBJECT_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSST_OBJECT_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSST_OBJECT_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSST_OBJECT_TYPE
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

ALTER TABLE KSST_OBJECT_TYPE
    ADD CONSTRAINT KSST_OBJECT_TYPEP1
PRIMARY KEY (TYPE_KEY)
/







-----------------------------------------------------------------------------
-- KSST_OBJECT_TYPE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSST_OBJECT_TYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSST_OBJECT_TYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSST_OBJECT_TYPE_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSST_OBJECT_TYPE_ATTR
    ADD CONSTRAINT KSST_OBJECT_TYPE_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSST_OBJECT_TYPE_ATTR_I1 
  ON KSST_OBJECT_TYPE_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSST_OBJ_TYP_JN_OBJ_SUB_TYP
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSST_OBJ_TYP_JN_OBJ_SUB_TYP';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSST_OBJ_TYP_JN_OBJ_SUB_TYP CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSST_OBJ_TYP_JN_OBJ_SUB_TYP
(
      OBJ_TYPE_ID VARCHAR2(255) NOT NULL
        , OBJ_SUB_TYPE_ID VARCHAR2(255) NOT NULL
    
    , CONSTRAINT SYS_C0011792 UNIQUE (OBJ_SUB_TYPE_ID)

)
/



CREATE INDEX KSST_OBJ_TYP_JN_OBJ_SUBTYP_I2 
  ON KSST_OBJ_TYP_JN_OBJ_SUB_TYP 
  (OBJ_TYPE_ID)
/





-----------------------------------------------------------------------------
-- KSST_RCTYP_JN_RCFLDTYP
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSST_RCTYP_JN_RCFLDTYP';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSST_RCTYP_JN_RCFLDTYP CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSST_RCTYP_JN_RCFLDTYP
(
      REQ_COMP_TYPE_ID VARCHAR2(255) NOT NULL
        , REQ_COMP_FIELD_TYPE_ID VARCHAR2(255) NOT NULL
    
    , CONSTRAINT IX1 UNIQUE (REQ_COMP_TYPE_ID, REQ_COMP_FIELD_TYPE_ID)

)
/



CREATE INDEX KSST_RCTYP_JN_RCFLDTYP_I1 
  ON KSST_RCTYP_JN_RCFLDTYP 
  (REQ_COMP_FIELD_TYPE_ID)
/
CREATE INDEX KSST_RCTYP_JN_RCFLDTYP_I2 
  ON KSST_RCTYP_JN_RCFLDTYP 
  (REQ_COMP_TYPE_ID)
/





-----------------------------------------------------------------------------
-- KSST_RC_JN_RC_FIELD
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSST_RC_JN_RC_FIELD';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSST_RC_JN_RC_FIELD CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSST_RC_JN_RC_FIELD
(
      REQ_COM_ID VARCHAR2(255) NOT NULL
        , REQ_COM_FIELD_ID VARCHAR2(255) NOT NULL
    
    , CONSTRAINT SYS_C0011797 UNIQUE (REQ_COM_FIELD_ID)

)
/



CREATE INDEX KSST_RC_JN_RC_FIELD_I1 
  ON KSST_RC_JN_RC_FIELD 
  (REQ_COM_ID)
/





-----------------------------------------------------------------------------
-- KSST_REF_STMT_REL
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSST_REF_STMT_REL';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSST_REF_STMT_REL CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSST_REF_STMT_REL
(
      ID VARCHAR2(255)
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , VER_NBR NUMBER(19) NOT NULL
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , REF_OBJ_ID VARCHAR2(255)
        , REF_OBJ_TYPE_KEY VARCHAR2(255)
        , ST VARCHAR2(255)
        , REF_STMT_REL_TYPE_ID VARCHAR2(255)
        , STMT_ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
    

)
/

ALTER TABLE KSST_REF_STMT_REL
    ADD CONSTRAINT KSST_REF_STMT_RELP1
PRIMARY KEY (ID)
/


CREATE INDEX KSST_REF_STMT_REL_I1 
  ON KSST_REF_STMT_REL 
  (REF_STMT_REL_TYPE_ID)
/
CREATE INDEX KSST_REF_STMT_REL_I2 
  ON KSST_REF_STMT_REL 
  (STMT_ID)
/





-----------------------------------------------------------------------------
-- KSST_REF_STMT_REL_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSST_REF_STMT_REL_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSST_REF_STMT_REL_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSST_REF_STMT_REL_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSST_REF_STMT_REL_ATTR
    ADD CONSTRAINT KSST_REF_STMT_REL_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSST_REF_STMT_REL_ATTR_I1 
  ON KSST_REF_STMT_REL_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSST_REF_STMT_REL_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSST_REF_STMT_REL_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSST_REF_STMT_REL_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSST_REF_STMT_REL_TYPE
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

ALTER TABLE KSST_REF_STMT_REL_TYPE
    ADD CONSTRAINT KSST_REF_STMT_REL_TYPEP1
PRIMARY KEY (TYPE_KEY)
/







-----------------------------------------------------------------------------
-- KSST_REF_STMT_REL_TYPE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSST_REF_STMT_REL_TYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSST_REF_STMT_REL_TYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSST_REF_STMT_REL_TYPE_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSST_REF_STMT_REL_TYPE_ATTR
    ADD CONSTRAINT KSST_REF_STMT_REL_TYPE_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSST_REF_STMT_REL_TYP_ATTR_I1 
  ON KSST_REF_STMT_REL_TYPE_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSST_REQ_COM
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSST_REQ_COM';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSST_REQ_COM CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSST_REQ_COM
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
        , RT_DESCR_ID VARCHAR2(255)
        , REQ_COM_TYPE_ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
    

)
/

ALTER TABLE KSST_REQ_COM
    ADD CONSTRAINT KSST_REQ_COMP1
PRIMARY KEY (ID)
/


CREATE INDEX KSST_REQ_COM_I1 
  ON KSST_REQ_COM 
  (REQ_COM_TYPE_ID)
/
CREATE INDEX KSST_REQ_COM_I2 
  ON KSST_REQ_COM 
  (RT_DESCR_ID)
/





-----------------------------------------------------------------------------
-- KSST_REQ_COM_FIELD
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSST_REQ_COM_FIELD';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSST_REQ_COM_FIELD CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSST_REQ_COM_FIELD
(
      ID VARCHAR2(255)
        , REQ_COM_FIELD_TYPE VARCHAR2(255)
        , VALUE VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSST_REQ_COM_FIELD
    ADD CONSTRAINT KSST_REQ_COM_FIELDP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSST_REQ_COM_FIELD_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSST_REQ_COM_FIELD_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSST_REQ_COM_FIELD_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSST_REQ_COM_FIELD_TYPE
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
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSST_REQ_COM_FIELD_TYPE
    ADD CONSTRAINT KSST_REQ_COM_FIELD_TYPEP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSST_REQ_COM_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSST_REQ_COM_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSST_REQ_COM_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSST_REQ_COM_TYPE
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

ALTER TABLE KSST_REQ_COM_TYPE
    ADD CONSTRAINT KSST_REQ_COM_TYPEP1
PRIMARY KEY (TYPE_KEY)
/







-----------------------------------------------------------------------------
-- KSST_REQ_COM_TYPE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSST_REQ_COM_TYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSST_REQ_COM_TYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSST_REQ_COM_TYPE_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSST_REQ_COM_TYPE_ATTR
    ADD CONSTRAINT KSST_REQ_COM_TYPE_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSST_REQ_COM_TYPE_ATTR_I1 
  ON KSST_REQ_COM_TYPE_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSST_REQ_COM_TYPE_NL_TMPL
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSST_REQ_COM_TYPE_NL_TMPL';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSST_REQ_COM_TYPE_NL_TMPL CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSST_REQ_COM_TYPE_NL_TMPL
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , LANGUAGE VARCHAR2(2)
        , NL_USUAGE_TYPE_KEY VARCHAR2(255)
        , TEMPLATE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSST_REQ_COM_TYPE_NL_TMPL
    ADD CONSTRAINT KSST_REQ_COM_TYPE_NL_TMPLP1
PRIMARY KEY (ID)
/


CREATE INDEX KSST_REQ_COM_TYPE_NL_TMPL_I1 
  ON KSST_REQ_COM_TYPE_NL_TMPL 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSST_RICH_TEXT_T
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSST_RICH_TEXT_T';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSST_RICH_TEXT_T CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSST_RICH_TEXT_T
(
      ID VARCHAR2(255)
        , FORMATTED VARCHAR2(2000)
        , PLAIN VARCHAR2(2000)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSST_RICH_TEXT_T
    ADD CONSTRAINT KSST_RICH_TEXT_TP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSST_RSTMT_RTYP_JN_OSUB_TYP
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSST_RSTMT_RTYP_JN_OSUB_TYP';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSST_RSTMT_RTYP_JN_OSUB_TYP CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSST_RSTMT_RTYP_JN_OSUB_TYP
(
      REF_STMT_REL_TYPE_ID VARCHAR2(255) NOT NULL
        , OBJ_SUB_TYPE_ID VARCHAR2(255) NOT NULL
    

)
/



CREATE INDEX KSST_RSTMT_RTYP_JN_OSUBTYP_I1 
  ON KSST_RSTMT_RTYP_JN_OSUB_TYP 
  (OBJ_SUB_TYPE_ID)
/
CREATE INDEX KSST_RSTMT_RTYP_JN_OSUBTYP_I2 
  ON KSST_RSTMT_RTYP_JN_OSUB_TYP 
  (REF_STMT_REL_TYPE_ID)
/





-----------------------------------------------------------------------------
-- KSST_RSTMT_RTYP_JN_STMT_TYP
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSST_RSTMT_RTYP_JN_STMT_TYP';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSST_RSTMT_RTYP_JN_STMT_TYP CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSST_RSTMT_RTYP_JN_STMT_TYP
(
      REF_STMT_REL_TYPE_ID VARCHAR2(255) NOT NULL
        , STMT_TYPE_ID VARCHAR2(255) NOT NULL
    

)
/



CREATE INDEX KSST_RSTMT_RTYP_JN_ST_TYP_I1 
  ON KSST_RSTMT_RTYP_JN_STMT_TYP 
  (REF_STMT_REL_TYPE_ID)
/
CREATE INDEX KSST_RSTMT_RTYP_JN_ST_TYP_I2 
  ON KSST_RSTMT_RTYP_JN_STMT_TYP 
  (STMT_TYPE_ID)
/





-----------------------------------------------------------------------------
-- KSST_STMT
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSST_STMT';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSST_STMT CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSST_STMT
(
      ID VARCHAR2(255)
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , VER_NBR NUMBER(19) NOT NULL
        , NAME VARCHAR2(255)
        , OPERATOR VARCHAR2(255)
        , ST VARCHAR2(255)
        , RT_DESCR_ID VARCHAR2(255)
        , STMT_TYPE_ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
    

)
/

ALTER TABLE KSST_STMT
    ADD CONSTRAINT KSST_STMTP1
PRIMARY KEY (ID)
/


CREATE INDEX KSST_STMT_I1 
  ON KSST_STMT 
  (STMT_TYPE_ID)
/
CREATE INDEX KSST_STMT_I2 
  ON KSST_STMT 
  (RT_DESCR_ID)
/





-----------------------------------------------------------------------------
-- KSST_STMT_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSST_STMT_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSST_STMT_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSST_STMT_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSST_STMT_ATTR
    ADD CONSTRAINT KSST_STMT_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSST_STMT_ATTR_I1 
  ON KSST_STMT_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSST_STMT_JN_REQ_COM
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSST_STMT_JN_REQ_COM';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSST_STMT_JN_REQ_COM CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSST_STMT_JN_REQ_COM
(
      STMT_ID VARCHAR2(255) NOT NULL
        , REQ_COM_ID VARCHAR2(255) NOT NULL
    

)
/



CREATE INDEX KSST_STMT_JN_REQ_COM_I1 
  ON KSST_STMT_JN_REQ_COM 
  (STMT_ID)
/
CREATE INDEX KSST_STMT_JN_REQ_COM_I2 
  ON KSST_STMT_JN_REQ_COM 
  (REQ_COM_ID)
/





-----------------------------------------------------------------------------
-- KSST_STMT_JN_STMT
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSST_STMT_JN_STMT';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSST_STMT_JN_STMT CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSST_STMT_JN_STMT
(
      STMT_ID VARCHAR2(255) NOT NULL
        , CHLD_STMT_ID VARCHAR2(255) NOT NULL
    
    , CONSTRAINT SYS_C0011839 UNIQUE (CHLD_STMT_ID)

)
/



CREATE INDEX KSST_STMT_JN_STMT_I1 
  ON KSST_STMT_JN_STMT 
  (STMT_ID)
/





-----------------------------------------------------------------------------
-- KSST_STMT_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSST_STMT_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSST_STMT_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSST_STMT_TYPE
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

ALTER TABLE KSST_STMT_TYPE
    ADD CONSTRAINT KSST_STMT_TYPEP1
PRIMARY KEY (TYPE_KEY)
/







-----------------------------------------------------------------------------
-- KSST_STMT_TYPE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSST_STMT_TYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSST_STMT_TYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSST_STMT_TYPE_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSST_STMT_TYPE_ATTR
    ADD CONSTRAINT KSST_STMT_TYPE_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSST_STMT_TYPE_ATTR_I1 
  ON KSST_STMT_TYPE_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSST_STMT_TYP_JN_RC_TYP
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSST_STMT_TYP_JN_RC_TYP';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSST_STMT_TYP_JN_RC_TYP CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSST_STMT_TYP_JN_RC_TYP
(
      ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
        , REQ_COM_TYPE_ID VARCHAR2(255)
        , SORT_ORDER NUMBER(10)
        , STMT_TYPE_ID VARCHAR2(255)
    

)
/

ALTER TABLE KSST_STMT_TYP_JN_RC_TYP
    ADD CONSTRAINT KSST_STMT_TYP_JN_RC_TYPP1
PRIMARY KEY (ID)
/


CREATE INDEX KSST_STMT_TYP_JN_RC_TYP_I1 
  ON KSST_STMT_TYP_JN_RC_TYP 
  (REQ_COM_TYPE_ID)
/
CREATE INDEX KSST_STMT_TYP_JN_RC_TYP_I2 
  ON KSST_STMT_TYP_JN_RC_TYP 
  (STMT_TYPE_ID)
/





-----------------------------------------------------------------------------
-- KSST_STMT_TYP_JN_STMT_TYP
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSST_STMT_TYP_JN_STMT_TYP';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSST_STMT_TYP_JN_STMT_TYP CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSST_STMT_TYP_JN_STMT_TYP
(
      ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
        , CHLD_STMT_TYPE_ID VARCHAR2(255)
        , SORT_ORDER NUMBER(10)
        , STMT_TYPE_ID VARCHAR2(255)
    

)
/

ALTER TABLE KSST_STMT_TYP_JN_STMT_TYP
    ADD CONSTRAINT KSST_STMT_TYP_JN_STMT_TYPP1
PRIMARY KEY (ID)
/


CREATE INDEX KSST_STMT_TYP_JN_STMT_TYP_I1 
  ON KSST_STMT_TYP_JN_STMT_TYP 
  (CHLD_STMT_TYPE_ID)
/
CREATE INDEX KSST_STMT_TYP_JN_STMT_TYP_I2 
  ON KSST_STMT_TYP_JN_STMT_TYP 
  (STMT_TYPE_ID)
/





-----------------------------------------------------------------------------
-- KSST_USAGE_TYPE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSST_USAGE_TYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSST_USAGE_TYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSST_USAGE_TYPE_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSST_USAGE_TYPE_ATTR
    ADD CONSTRAINT KSST_USAGE_TYPE_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSST_USAGE_TYPE_ATTR_I1 
  ON KSST_USAGE_TYPE_ATTR 
  (OWNER)
/

