-----------------------------------------------------------------------------
-- KSEN_LRR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LRR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LRR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_LRR
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
			LPR_ID varchar2(255),
			RESULT_VALUE_ID varchar2(255)
   )
/

-----------------------------------------------------------------------------
-- KSEN_LRR_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LRR_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LRR_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_LRR_ATTR
   (
			ID VARCHAR2(255),
			OBJ_ID VARCHAR2(36),
			ATTR_KEY VARCHAR2(255),
			ATTR_VALUE VARCHAR2(2000),
			OWNER VARCHAR2(255)
   )
/

-----------------------------------------------------------------------------
-- KSEN_LRR_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LRR_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LRR_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_LRR_TYPE
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
-- KSEN_LRR_RES_SOURCE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LRR_RES_SOURCE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LRR_RES_SOURCE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

create table KSEN_LRR_RES_SOURCE
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
   TYPE_ID VARCHAR2(255),
   ARTICULATE_ID VARCHAR2(255),
   RES_TRANS_ID VARCHAR2(255)
)
/

-----------------------------------------------------------------------------
-- KSEN_LRR_RES_SOURCE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LRR_RES_SOURCE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LRR_RES_SOURCE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_LRR_RES_SOURCE_ATTR
   (
			ID VARCHAR2(255),
			OBJ_ID VARCHAR2(36),
			ATTR_KEY VARCHAR2(255),
			ATTR_VALUE VARCHAR2(2000),
			OWNER VARCHAR2(255)
   )
/

-----------------------------------------------------------------------------
-- KSEN_LRR_RES_SRC_RELTN
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LRR_RES_SRC_RELTN';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LRR_RES_SRC_RELTN CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

create table KSEN_LRR_RES_SRC_RELTN
  (
	    LRR_ID varchar2(255),
	    RES_SRC_ID varchar2(255)
  )
/

