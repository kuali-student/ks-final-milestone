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
			COMMITMENTPERCENT FLOAT(17),
			RELATION_STATE_ID VARCHAR2(255),
			RELATION_TYPE_ID VARCHAR2(255)
   )
/

-----------------------------------------------------------------------------
-- KSEN_LPR_RV_GRP_RELTN
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LPR_RV_GRP_RELTN';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LPR_RV_GRP_RELTN CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

create table KSEN_LPR_RV_GRP_RELTN(
	LPR_ID VARCHAR2(255),
	RV_GRP_ID VARCHAR2(255)
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

DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LPR_ROSTER';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LPR_ROSTER CASCADE CONSTRAINTS PURGE'; END IF;
END;
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

DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LPRROSTER_LUI_RELTN';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LPRROSTER_LUI_RELTN CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

create table  KSEN_LPRROSTER_LUI_RELTN
  (
	    LPRROSTER_ID varchar2(255),
	    LUI_ID varchar2(255)
  )
/


-----------------------------------------------------------------------------
-- KSEN_LPR_TRANS
-----------------------------------------------------------------------------

DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LPR_TRANS';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LPR_TRANS CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

create table KSEN_LPR_TRANS
   (	
		ID varchar2(255),
		CREATEID VARCHAR2(255),
		CREATETIME timestamp(6),
		UPDATEID VARCHAR2(255),
		UPDATETIME timestamp(6),
		OBJ_ID varchar2(36),
		VER_NBR number(19,0),
		NAME VARCHAR2(255),
		RT_DESCR_ID VARCHAR2(255),
		REQ_PERSON_ID  VARCHAR2(255),
		STATE_ID varchar2(255),
		LPR_TYPE_ID varchar2(255)
   )
/

-----------------------------------------------------------------------------
-- KSEN_LPR_TRANS_ITEMS
-----------------------------------------------------------------------------
   
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LPR_TRANS_ITEMS';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LPR_TRANS_ITEMS CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

create table KSEN_LPR_TRANS_ITEMS
   (
		ID varchar2(255),
		CREATEID VARCHAR2(255),
		CREATETIME timestamp(6),
		UPDATEID VARCHAR2(255),
		UPDATETIME timestamp(6),
		OBJ_ID varchar2(36),
		VER_NBR number(19,0),
		NAME VARCHAR2(255),
		RT_DESCR_ID VARCHAR2(255),
		PERSON_ID  VARCHAR2(255),
		NEW_LUI_ID  VARCHAR2(255),
		EXIST_LUI_ID  VARCHAR2(255),
		RESULTING_LPR_ID varchar2(255),
		GROUP_ID varchar2(255),
		STATUS varchar2(255),
		LPR_TRANS_ID  VARCHAR2(255),
		STATE_ID varchar2(255),
		TYPE_ID varchar2(255)
   )
/
   
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LPR_ROSTER_ENTRY';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LPR_ROSTER_ENTRY CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

create table KSEN_LPR_ROSTER_ENTRY
  (
		ID varchar2(255),
		OBJ_ID varchar2(36),
		VER_NBR number(19,0),
		CREATEID VARCHAR2(255),
		CREATETIME TIMESTAMP (6),
		UPDATEID VARCHAR2(255),
		UPDATETIME TIMESTAMP (6),
		EFFECTIVEDATE TIMESTAMP (6),
		EXPIRATIONDATE TIMESTAMP (6),
		LPRROSTER_ID varchar2(255),
		LPR_ID varchar2(255),
		POSITION varchar2(10),
		RELATION_STATE_ID varchar2(255),
		RELATION_TYPE_ID varchar2(255)
  )
/

-----------------------------------------------------------------------------
-- KSEN_LPR_TRANS_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LPR_TRANS_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LPR_TRANS_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_LPR_TRANS_ATTR 
   (	ID VARCHAR2(255), 
			OBJ_ID VARCHAR2(36), 
			ATTR_KEY VARCHAR2(255), 
			ATTR_VALUE VARCHAR2(2000), 
			OWNER VARCHAR2(255)
   )
/


-----------------------------------------------------------------------------
-- KSEN_LPR_TRANS_ITEM_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LPR_TRANS_ITEM_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LPR_TRANS_ITEM_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_LPR_TRANS_ITEM_ATTR 
   (	ID VARCHAR2(255), 
			OBJ_ID VARCHAR2(36), 
			ATTR_KEY VARCHAR2(255), 
			ATTR_VALUE VARCHAR2(2000), 
			OWNER VARCHAR2(255)
   )
/

