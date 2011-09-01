-----------------------------------------------------------------------------
-- KSEN_LPR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LPR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LPR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_LPR 
   (	ID VARCHAR2(255), 
			OBJ_ID VARCHAR2(36), 
			VER_NBR NUMBER(19,0), 
			CREATEID VARCHAR2(255), 
			CREATETIME TIMESTAMP (6), 
			UPDATEID VARCHAR2(255), 
			UPDATETIME TIMESTAMP (6), 
			EFFECTIVEDATE TIMESTAMP (6), 
			EXPIRATIONDATE TIMESTAMP (6), 
			LUIID VARCHAR2(255), 
			PERSONID VARCHAR2(255), 
			RELATION_STATE_ID VARCHAR2(255), 
			RELATION_TYPE_ID VARCHAR2(255)
   )
/

-----------------------------------------------------------------------------
-- KSEN_LPR_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LPR_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LPR_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_LPR_ATTR 
   (	ID VARCHAR2(255), 
			OBJ_ID VARCHAR2(36), 
			ATTR_KEY VARCHAR2(255), 
			ATTR_VALUE VARCHAR2(2000), 
			OWNER VARCHAR2(255)
   )
/

-----------------------------------------------------------------------------
-- KSEN_LPR_STATE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LPR_STATE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LPR_STATE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_LPR_STATE 
   (	ID VARCHAR2(255), 
			OBJ_ID VARCHAR2(36), 
			VER_NBR NUMBER(19,0), 
			CREATEID VARCHAR2(255), 
			CREATETIME TIMESTAMP (6), 
			UPDATEID VARCHAR2(255), 
			UPDATETIME TIMESTAMP (6), 
			DESCR VARCHAR2(255), 
			EFFECTIVEDATE TIMESTAMP (6), 
			EXPIRATIONDATE TIMESTAMP (6), 
			NAME VARCHAR2(255)
   )
/

-----------------------------------------------------------------------------
-- KSEN_LPR_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LPR_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LPR_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_LPR_TYPE 
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

create table KSEN_LPR_ROSTER
   (
		ID varchar2(255),
		CREATEID VARCHAR2(255),
		CREATETIME timestamp,
		UPDATEID VARCHAR2(255),
		UPDATETIME timestamp,
		OBJ_ID varchar2(36),
		VER_NBR number,
		NAME VARCHAR2(255),
		RT_DESCR_ID VARCHAR2(255),
		MAX_CAPACITY  number,
		CHECK_IN_REQ number,
		STATE_ID varchar2(255),
		TYPE_ID varchar2(255),
		ATP_DUR_TYP_KEY varchar2(255),
		TM_QUANTITY number
   )
/

create table  KSEN_LPRROSTER_LUI_RELTN
  (
	    LPRROSTER_ID varchar2(255),
	    LUI_ID varchar2(255)
  )
/
