-----------------------------------------------------------------------------
-- KSEN_ATP
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_ATP';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_ATP CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_ATP
(
	ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(36) NULL ,
	ATP_TYPE             VARCHAR2(255) NOT NULL ,
	ATP_STATE            VARCHAR2(255) NOT NULL ,
	NAME                 VARCHAR2(255) NULL ,
	DESCR_PLAIN          VARCHAR2(4000) NOT NULL ,
	DESCR_FORMATTED      VARCHAR2(4000) NULL ,
	ATP_CD               VARCHAR2(255) NULL ,
	END_DT               TIMESTAMP(6) NOT NULL ,
	START_DT             TIMESTAMP(6) NOT NULL ,
	ADMIN_ORG_ID         VARCHAR2(50) NULL ,
	VER_NBR              NUMBER(19) NULL ,
	CREATETIME           TIMESTAMP(6) NULL ,
	CREATEID             VARCHAR2(255) NULL ,
	UPDATETIME           TIMESTAMP(6) NULL ,
	UPDATEID             VARCHAR2(255) NULL 
)
/

CREATE UNIQUE INDEX KSEN_ATP_P ON KSEN_ATP
(ID   ASC)
/

CREATE  INDEX KSEN_ATP_I1 ON KSEN_ATP
(ATP_TYPE   ASC)
/

CREATE  INDEX KSEN_ATP_I2 ON KSEN_ATP
(ATP_CD   ASC)
/

CREATE  INDEX KSEN_ATP_I3 ON KSEN_ATP
(START_DT   ASC)
/

-----------------------------------------------------------------------------
-- KSEN_ATPATP_RELTN
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_ATPATP_RELTN';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_ATPATP_RELTN CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_ATPATP_RELTN 
   (	
    ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(36) NULL ,
	ATP_TYPE             VARCHAR2(255) NOT NULL ,
	ATP_STATE            VARCHAR2(255) NOT NULL ,
	ATP_ID               VARCHAR2(255) NOT NULL ,
	RELATED_ATP_ID       VARCHAR2(255) NOT NULL ,
	EFF_DT               TIMESTAMP(6) NULL ,
	EXPIR_DT             TIMESTAMP(6) NULL ,
	VER_NBR              NUMBER(19) NULL ,
	CREATETIME           TIMESTAMP(6) NULL ,
	CREATEID             VARCHAR2(255) NULL ,
	UPDATETIME           TIMESTAMP(6) NULL ,
	UPDATEID             VARCHAR2(255) NULL
   )
/

CREATE UNIQUE INDEX KSEN_ATPATP_RELTN_P ON KSEN_ATPATP_RELTN
(ID   ASC)
/

CREATE  INDEX KSEN_ATPATP_RELTN_I1 ON KSEN_ATPATP_RELTN
(ATP_TYPE   ASC)
/

CREATE  INDEX KSEN_ATPATP_RELTN_IF1 ON KSEN_ATPATP_RELTN
(ATP_ID   ASC)
/

CREATE  INDEX KSEN_ATPATP_RELTN_IF2 ON KSEN_ATPATP_RELTN
(RELATED_ATP_ID   ASC)
/

-----------------------------------------------------------------------------
-- KSEN_ATPATP_RELTN_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_ATPATP_RELTN_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_ATPATP_RELTN_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_ATPATP_RELTN_ATTR 
   (	
    ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(36) NULL ,
	ATTR_KEY             VARCHAR2(255) NULL ,
	ATTR_VALUE           VARCHAR2(4000) NULL ,
	OWNER             VARCHAR2(255) NULL 
   )
/

CREATE UNIQUE INDEX KSEN_ATPATP_RELTN_ATTR_P ON KSEN_ATPATP_RELTN_ATTR
(ID   ASC)
/

CREATE  INDEX KSEN_ATPATP_RELTN_ATTR_IF1 ON KSEN_ATPATP_RELTN_ATTR
(OWNER   ASC)
/


-----------------------------------------------------------------------------
-- KSEN_ATPMSTONE_RELTN_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_ATPMSTONE_RELTN_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_ATPMSTONE_RELTN_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_ATPMSTONE_RELTN_ATTR 
   (	ID VARCHAR2(255), 
			OBJ_ID VARCHAR2(36), 
			ATTR_KEY VARCHAR2(255), 
			ATTR_VALUE VARCHAR2(2000), 
			OWNER VARCHAR2(255)
   )
/

-----------------------------------------------------------------------------
-- KSEN_ATPMSTONE_RELTN
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_ATPMSTONE_RELTN';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_ATPMSTONE_RELTN CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_ATPMSTONE_RELTN 
   (	
		   	ID                   VARCHAR2(255) NOT NULL ,
			OBJ_ID               VARCHAR2(36) NULL ,
			VER_NBR              NUMBER(19) NULL ,
			CREATEID             VARCHAR2(255) NULL ,
			CREATETIME           TIMESTAMP(6) NULL ,
			UPDATEID             VARCHAR2(255) NULL ,
			UPDATETIME           TIMESTAMP(6) NULL ,
			ATP_ID               VARCHAR2(255) NULL ,
			MSTONE_ID            VARCHAR2(255) NULL 
   )
/

CREATE UNIQUE INDEX KSEN_ATPMSTONE_RELTN_P ON KSEN_ATPMSTONE_RELTN
(ID   ASC)
/

CREATE  INDEX KSEN_ATPMSTONE_RELTN_IF1 ON KSEN_ATPMSTONE_RELTN
(ATP_ID   ASC)
/

CREATE  INDEX KSEN_ATPMSTONE_RELTN_IF2 ON KSEN_ATPMSTONE_RELTN
(MSTONE_ID   ASC)
/

-----------------------------------------------------------------------------
-- KSEN_ATP_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_ATP_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_ATP_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_ATP_ATTR 
   (	
    ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(36) NULL ,
	ATTR_KEY             VARCHAR2(255) NULL ,
	ATTR_VALUE           VARCHAR2(4000) NULL ,
	OWNER             VARCHAR2(255) NULL 
   )
/

CREATE UNIQUE INDEX KSEN_ATP_ATTR_P ON KSEN_ATP_ATTR
(ID   ASC)
/

CREATE  INDEX KSEN_ATP_ATTR_IF1 ON KSEN_ATP_ATTR
(OWNER   ASC)
/

-----------------------------------------------------------------------------
-- KSEN_ATP_STATE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_ATP_STATE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_ATP_STATE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_ATP_STATE 
   (	ID VARCHAR2(255), 
			OBJ_ID VARCHAR2(36), 
			VER_NBR NUMBER(19,0), 
			CREATEID VARCHAR2(255), 
			CREATETIME TIMESTAMP (6), 
			UPDATEID VARCHAR2(255), 
			UPDATETIME TIMESTAMP (6), 
			DESCR VARCHAR2(255), 
			NAME VARCHAR2(255)
   )
/

-----------------------------------------------------------------------------
-- KSEN_ATP_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_ATP_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_ATP_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_ATP_TYPE 
   (	TYPE_KEY VARCHAR2(255), 
			OBJ_ID VARCHAR2(36), 
			VER_NBR NUMBER(19,0), 
			TYPE_DESC VARCHAR2(2000), 
			EFF_DT TIMESTAMP (6), 
			EXPIR_DT TIMESTAMP (6), 
			NAME VARCHAR2(255), 
			REF_OBJECT_URI VARCHAR2(255)
   )
/

-----------------------------------------------------------------------------
-- KSEN_COMM_STATE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_COMM_STATE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_COMM_STATE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_COMM_STATE 
   (	ID VARCHAR2(255), 
			OBJ_ID VARCHAR2(36), 
			VER_NBR NUMBER(19,0), 
			CREATEID VARCHAR2(255), 
			CREATETIME TIMESTAMP (6), 
			UPDATEID VARCHAR2(255), 
			UPDATETIME TIMESTAMP (6), 
			DESCR VARCHAR2(255), 
			EFF_DT TIMESTAMP (6), 
			EXPIR_DT TIMESTAMP (6), 
			NAME VARCHAR2(255), 
			PROCESS_KEY VARCHAR2(255)
   )
/

-----------------------------------------------------------------------------
-- KSEN_MSTONE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_MSTONE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_MSTONE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_MSTONE 
   (	
    ID                   VARCHAR2(255) NOT NULL ,
    OBJ_ID               VARCHAR2(36) NULL ,
    MSTONE_TYPE          VARCHAR2(255) NOT NULL ,
    MSTONE_STATE         VARCHAR2(255) NOT NULL ,
    NAME                 VARCHAR2(255) NULL ,
    DESCR_PLAIN          VARCHAR2(4000) NOT NULL ,
    DESCR_FORMATTED      VARCHAR2(4000) NULL ,
    IS_ALL_DAY           NUMBER(1) NOT NULL ,
    IS_INSTRCT_DAY       NUMBER(1) NOT NULL ,
    IS_RELATIVE          NUMBER(1) NOT NULL ,
    RELATIVE_ANCHOR_MSTONE_ID VARCHAR2(255) NULL ,
    IS_DATE_RANGE        NUMBER(1) NOT NULL ,
    START_DT             TIMESTAMP(6) NULL,
    END_DT               TIMESTAMP(6) NULL,
    VER_NBR              NUMBER(19) NULL ,
    CREATETIME           TIMESTAMP(6) NULL ,
    CREATEID             VARCHAR2(255) NULL ,
    UPDATETIME           TIMESTAMP(6) NULL ,
    UPDATEID             VARCHAR2(255) NULL
   )
/

CREATE UNIQUE INDEX KSEN_MSTONE_P ON KSEN_MSTONE
(ID   ASC)
/

CREATE  INDEX KSEN_MSTONE_I1 ON KSEN_MSTONE
(MSTONE_TYPE   ASC)
/

CREATE  INDEX KSEN_MSTONE_I2 ON KSEN_MSTONE
(START_DT   ASC)
/

-----------------------------------------------------------------------------
-- KSEN_RESTRICTION
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_RESTRICTION';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_RESTRICTION CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_RESTRICTION 
   (	ID VARCHAR2(255), 
			OBJ_ID VARCHAR2(36), 
			VER_NBR NUMBER(19,0), 
			CREATEID VARCHAR2(255), 
			CREATETIME TIMESTAMP (6), 
			UPDATEID VARCHAR2(255), 
			UPDATETIME TIMESTAMP (6), 
			NAME VARCHAR2(255), 
			RT_DESCR_ID VARCHAR2(255), 
			STATE_ID VARCHAR2(255), 
			TYPE_ID VARCHAR2(255)
   )
/

-----------------------------------------------------------------------------
-- KSEN_RESTRICTION_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_RESTRICTION_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_RESTRICTION_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_RESTRICTION_ATTR 
   (	ID VARCHAR2(255), 
			OBJ_ID VARCHAR2(36), 
			ATTR_KEY VARCHAR2(255), 
			ATTR_VALUE VARCHAR2(2000), 
			OWNER VARCHAR2(255)
   )
/

-----------------------------------------------------------------------------
-- KSEN_RICH_TEXT_T
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_RICH_TEXT_T';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_RICH_TEXT_T CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_RICH_TEXT_T 
   (	ID VARCHAR2(255), 
			OBJ_ID VARCHAR2(36), 
			VER_NBR NUMBER(19,0), 
			FORMATTED VARCHAR2(2000), 
			PLAIN VARCHAR2(2000)
   )
/

-----------------------------------------------------------------------------
-- KSEN_STATEPROCESS_RELTN
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_STATEPROCESS_RELTN';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_STATEPROCESS_RELTN CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_STATEPROCESS_RELTN 
   (	ID VARCHAR2(255), 
			OBJ_ID VARCHAR2(36), 
			VER_NBR NUMBER(19,0), 
			CREATEID VARCHAR2(255), 
			CREATETIME TIMESTAMP (6), 
			UPDATEID VARCHAR2(255), 
			UPDATETIME TIMESTAMP (6), 
			NEXT_STATEKEY VARCHAR2(255), 
			PRIOR_STATEKEY VARCHAR2(255), 
			PROCESS_KEY VARCHAR2(255)
   )
/

-----------------------------------------------------------------------------
-- KSEN_STATE_PROCESS
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_STATE_PROCESS';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_STATE_PROCESS CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_STATE_PROCESS 
   (	ID VARCHAR2(255), 
			OBJ_ID VARCHAR2(36), 
			VER_NBR NUMBER(19,0), 
			CREATEID VARCHAR2(255), 
			CREATETIME TIMESTAMP (6), 
			UPDATEID VARCHAR2(255), 
			UPDATETIME TIMESTAMP (6), 
			DESCR VARCHAR2(255), 
			EFF_DT TIMESTAMP (6), 
			EXPIR_DT TIMESTAMP (6), 
			NAME VARCHAR2(255)
   )
/

-----------------------------------------------------------------------------
-- KSEN_MSTONE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_MSTONE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_MSTONE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_MSTONE_ATTR
   (	
    ID                   VARCHAR2(255) NOT NULL ,
    OBJ_ID               VARCHAR2(36) NULL ,
    ATTR_KEY             VARCHAR2(255) NULL ,
    ATTR_VALUE           VARCHAR2(4000) NULL ,
    OWNER             VARCHAR2(255) NULL
   )
/

CREATE UNIQUE INDEX KSEN_MSTONE_ATTR_P ON KSEN_MSTONE_ATTR
(ID   ASC)
/

CREATE  INDEX KSEN_MSTONE_ATTR_IF1 ON KSEN_MSTONE_ATTR
(OWNER   ASC)
/

-----------------------------------------------------------------------------
-- KSEN_ATPTYPE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_ATPTYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_ATPTYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_ATPTYPE_ATTR
   (	ID VARCHAR2(255),
			OBJ_ID VARCHAR2(36),
			ATTR_KEY VARCHAR2(255),
			ATTR_VALUE VARCHAR2(2000),
			OWNER VARCHAR2(255)
   )
/


