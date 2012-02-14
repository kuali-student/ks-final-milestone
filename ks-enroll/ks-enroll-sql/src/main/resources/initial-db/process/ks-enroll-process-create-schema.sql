-----------------------------------------------------------------------------
-- KSEN_PROCESS
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_PROCESS';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_PROCESS CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_PROCESS
 (
      ID VARCHAR2(255),
      OBJ_ID VARCHAR2(36),
      VER_NBR NUMBER(19,0),
      CREATEID VARCHAR2(255),
      CREATETIME TIMESTAMP (6),
      UPDATEID VARCHAR2(255),
      UPDATETIME TIMESTAMP (6),
      NAME VARCHAR2(255),
      RT_DESCR_ID VARCHAR2(255),
      STATE_ID VARCHAR2(255),
      TYPE_ID VARCHAR2(255),
      OWNER_ORG_ID  VARCHAR2(255)
 )
/

-----------------------------------------------------------------------------
-- KSEN_PROCESS_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_PROCESS_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_PROCESS_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_PROCESS_TYPE
(
    TYPE_KEY VARCHAR2(255),
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
-- KSEN_PROCESS_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_PROCESS_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_PROCESS_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_PROCESS_ATTR
(
        ID VARCHAR2(255),
        ATTR_NAME VARCHAR2(255),
        ATTR_VALUE VARCHAR2(2000),
        OWNER VARCHAR2(255),
        OBJ_ID VARCHAR2(36),
        VER_NBR NUMBER(19)
)
/

-----------------------------------------------------------------------------
-- KSEN_PROCESS_TYPE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_PROCESS_TYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_PROCESS_TYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_PROCESS_TYPE_ATTR
(
        ID VARCHAR2(255),
        ATTR_NAME VARCHAR2(255),
        ATTR_VALUE VARCHAR2(2000),
        OWNER VARCHAR2(255),
        OBJ_ID VARCHAR2(36),
        VER_NBR NUMBER(19)
)
/

-----------------------------------------------------------------------------
-- KSEN_PROCESS_RICH_TEXT
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_PROCESS_RICH_TEXT';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_PROCESS_RICH_TEXT CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_PROCESS_RICH_TEXT
(
    ID VARCHAR2(255),
    OBJ_ID VARCHAR2(36),
    VER_NBR NUMBER(19,0),
    FORMATTED VARCHAR2(2000),
    PLAIN VARCHAR2(2000)
)
/

-----------------------------------------------------------------------------
-- KSEN_CHECK
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_CHECK';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_CHECK CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_CHECK
(
    ID VARCHAR2(255),
    OBJ_ID VARCHAR2(36),
    VER_NBR NUMBER(19,0),
    CREATEID VARCHAR2(255),
    CREATETIME TIMESTAMP (6),
    UPDATEID VARCHAR2(255),
    UPDATETIME TIMESTAMP (6),
    NAME VARCHAR2(255),
    RT_DESCR_ID VARCHAR2(255),
    STATE_ID VARCHAR2(255),
    TYPE_ID VARCHAR2(255),
    ISSUE_ID VARCHAR2(255),
    MILESTONE_TYPE_ID  VARCHAR2(255),
    AGENDA_ID   VARCHAR2(255),
    PROCESS_ID  VARCHAR2(255)
)
/

-----------------------------------------------------------------------------
-- KSEN_CHECK_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_CHECK_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_CHECK_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_CHECK_TYPE
(
    TYPE_KEY VARCHAR2(255),
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
-- KSEN_CHECK_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_CHECK_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_CHECK_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_CHECK_ATTR
(
    ID VARCHAR2(255),
    ATTR_NAME VARCHAR2(255),
    ATTR_VALUE VARCHAR2(2000),
    OWNER VARCHAR2(255),
    OBJ_ID VARCHAR2(36),
    VER_NBR NUMBER(19)
)
/

-----------------------------------------------------------------------------
-- KSEN_CHECK_TYPE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_CHECK_TYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_CHECK_TYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_CHECK_TYPE_ATTR
(
    ID VARCHAR2(255),
    ATTR_NAME VARCHAR2(255),
    ATTR_VALUE VARCHAR2(2000),
    OWNER VARCHAR2(255),
    OBJ_ID VARCHAR2(36),
    VER_NBR NUMBER(19)
)
/

-----------------------------------------------------------------------------
-- KSEN_CHECK_RICH_TEXT
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_CHECK_RICH_TEXT';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_CHECK_RICH_TEXT CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_CHECK_RICH_TEXT
(    
    ID VARCHAR2(255),
    OBJ_ID VARCHAR2(36),
    VER_NBR NUMBER(19,0),
    FORMATTED VARCHAR2(2000),
    PLAIN VARCHAR2(2000)
)
/

-----------------------------------------------------------------------------
-- KSEN_INSTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_INSTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_INSTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_INSTR
(
    ID VARCHAR2(255),
    OBJ_ID VARCHAR2(36),
    VER_NBR NUMBER(19,0),
    CREATEID VARCHAR2(255),
    CREATETIME TIMESTAMP (6),
    UPDATEID VARCHAR2(255),
    UPDATETIME TIMESTAMP (6),
    STATE_ID VARCHAR2(255),
    TYPE_ID VARCHAR2(255),
    EFF_DT TIMESTAMP (6),
    EXPIR_DT TIMESTAMP (6),
    PROCESS_ID  VARCHAR2(255),
    CHECK_ID  VARCHAR2(255),
    MESSAGE VARCHAR2(255),
    POSITION NUMBER(19),
    IS_WARNING NUMBER(1),
    CONTINUE_ON_FAIL NUMBER(1),
    IS_EXEMPTABLE NUMBER(1)
)
/

-----------------------------------------------------------------------------
-- KSEN_INSTR_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_INSTR_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_INSTR_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_INSTR_ATTR
(
    ID VARCHAR2(255),
    ATTR_NAME VARCHAR2(255),
    ATTR_VALUE VARCHAR2(2000),
    OWNER VARCHAR2(255),
    OBJ_ID VARCHAR2(36),
    VER_NBR NUMBER(19)
)
/

-----------------------------------------------------------------------------
-- KSEN_INSTR_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_INSTR_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_INSTR_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_INSTR_TYPE
(
    TYPE_KEY VARCHAR2(255),
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
-- KSEN_INSTR_TYPE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_INSTR_TYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_INSTR_TYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_INSTR_TYPE_ATTR
(
    ID VARCHAR2(255),
    ATTR_NAME VARCHAR2(255),
    ATTR_VALUE VARCHAR2(2000),
    OWNER VARCHAR2(255),
    OBJ_ID VARCHAR2(36),
    VER_NBR NUMBER(19)
)
/

-----------------------------------------------------------------------------
-- KSEN_INSTR_POPLTN_RELTN
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_INSTR_POPLTN_RELTN';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_INSTR_POPLTN_RELTN CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_INSTR_POPLTN_RELTN
(
	INSTR_ID VARCHAR2(255),
    POPLTN_ID VARCHAR2(255)
)
/

-----------------------------------------------------------------------------
-- KSEN_INSTR_ATPTYPE_RELTN
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_INSTR_ATPTYPE_RELTN';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_INSTR_ATPTYPE_RELTN CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_INSTR_ATPTYPE_RELTN
(
	INSTR_ID VARCHAR2(255),
	ATP_TYPE_ID VARCHAR2(255)
)
/


-----------------------------------------------------------------------------
-- KSEN_INSTR_MESSAGE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_INSTR_MESSAGE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_INSTR_MESSAGE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_INSTR_MESSAGE
(    
    ID VARCHAR2(255),
    OBJ_ID VARCHAR2(36),
    VER_NBR NUMBER(19,0),
    FORMATTED VARCHAR2(2000),
    PLAIN VARCHAR2(2000)
)
/


-----------------------------------------------------------------------------
-- KSEN_POPULATION
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_POPULATION';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_POPULATION CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_POPULATION
(
    ID VARCHAR2(255),
    OBJ_ID VARCHAR2(36),
    VER_NBR NUMBER(19,0),
    CREATEID VARCHAR2(255),
    CREATETIME TIMESTAMP (6),
    UPDATEID VARCHAR2(255),
    UPDATETIME TIMESTAMP (6),
    STATE_ID VARCHAR2(255),
    TYPE_ID VARCHAR2(255)
)
/
