-----------------------------------------------------------------------------
-- KSLP_LPR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLP_LPR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLP_LPR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLP_LPR 
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
-- KSLP_LPR_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLP_LPR_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLP_LPR_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLP_LPR_ATTR 
   (	ID VARCHAR2(255), 
			OBJ_ID VARCHAR2(36), 
			ATTR_KEY VARCHAR2(255), 
			ATTR_VALUE VARCHAR2(2000), 
			OWNER VARCHAR2(255)
   )
/

-----------------------------------------------------------------------------
-- KSLP_LPR_STATE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLP_LPR_STATE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLP_LPR_STATE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLP_LPR_STATE 
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
-- KSLP_LPR_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLP_LPR_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLP_LPR_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLP_LPR_TYPE 
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

