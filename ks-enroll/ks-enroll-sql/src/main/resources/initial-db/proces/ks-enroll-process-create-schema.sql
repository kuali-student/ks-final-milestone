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
      PROCESS_STATE_ID VARCHAR2(255),
      PROCESS_TYPE_ID VARCHAR2(255),
      OWNER_ORG_ID  VARCHAR2(255)
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
    CHECK_STATE_ID VARCHAR2(255),
    CHECK_TYPE_ID VARCHAR2(255),
    ISSUE_ID VARCHAR2(255),
    MILESTONE_ID  VARCHAR2(255),
    AGENDA_ID   VARCHAR2(255),
    PROCESS_ID  VARCHAR2(255)
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
    INSTR_STATE_ID VARCHAR2(255),
    INSTR_TYPE_ID VARCHAR2(255),
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
-- KSEN_INSTR_PRSN_RELTN
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_INSTR_PRSN_RELTN';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_INSTR_PRSN_RELTN CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_INSTR_PRSN_RELTN
(
	INSTR_ID VARCHAR2(255),
	PERSON_ID VARCHAR2(255)
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