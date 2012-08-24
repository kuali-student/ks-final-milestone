-----------------------------------------------------------------------------
-- KSEN_LRC_RES_VAL_GRP 
-- WARNING THIS SCHEMA IS OUT OF DATE SEE UPGRADE 16
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LRC_RES_VAL_GRP';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LRC_RES_VAL_GRP CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

create table KSEN_LRC_RES_VAL_GRP
(
   ID VARCHAR2(255),
   OBJ_ID VARCHAR2(36),
   VER_NBR NUMBER(19,0),
   CREATEID VARCHAR2(255),
   CREATETIME TIMESTAMP (6),
   UPDATEID VARCHAR2(255),
   UPDATETIME TIMESTAMP (6),
   EFFECTIVEDATE TIMESTAMP (6),
   EXPIRATIONDATE TIMESTAMP (6),
   NAME VARCHAR2(255),
   RT_DESCR_ID VARCHAR2(255),
   STATE_ID VARCHAR2(255),
   TYPE_ID VARCHAR2(255),
   MIN_VALUE VARCHAR2(255),
   MAX_VALUE VARCHAR2(255),
   INCR VARCHAR2(255),
   RES_SCALE_ID VARCHAR2(255)
)
/

-----------------------------------------------------------------------------
-- KSEN_LRC_RES_VAL_GRP_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LRC_RES_VAL_GRP_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LRC_RES_VAL_GRP_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

create table KSEN_LRC_RES_VAL_GRP_ATTR (
		ID VARCHAR2(255),
		OBJ_ID VARCHAR2(36),
		ATTR_KEY VARCHAR2(255),
		ATTR_VALUE VARCHAR2(2000),
		OWNER VARCHAR2(255)
)
/

-----------------------------------------------------------------------------
-- KSEN_LRC_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LRC_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LRC_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_LRC_TYPE
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
-- KSEN_LRC_RVGP_RV_RELTN
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LRC_RVGP_RV_RELTN';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LRC_RVGP_RV_RELTN CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

create table  KSEN_LRC_RVGP_RV_RELTN
  (
	    RES_VAL_GRP_ID varchar2(255),
	    RES_VAL_ID varchar2(255)
  )
/

-----------------------------------------------------------------------------
-- KSEN_LRC_RES_VALUE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LRC_RES_VALUE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LRC_RES_VALUE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

create table KSEN_LRC_RES_VALUE
(
   ID VARCHAR2(255),
   OBJ_ID VARCHAR2(36),
   VER_NBR NUMBER(19,0),
   CREATEID VARCHAR2(255),
   CREATETIME TIMESTAMP (6),
   UPDATEID VARCHAR2(255),
   UPDATETIME TIMESTAMP (6),
   EFFECTIVEDATE TIMESTAMP (6),
   EXPIRATIONDATE TIMESTAMP (6),
   STATE_ID VARCHAR2(255),
   TYPE_ID VARCHAR2(255),
   NAME VARCHAR2(255),
   RT_DESCR_ID VARCHAR2(255),
   RES_SCALE_ID VARCHAR2(255),
   NUMERIC_VALUE VARCHAR2(255),
   VALUE VARCHAR2(255)
)
/

-----------------------------------------------------------------------------
-- KSEN_LRC_RES_VALUE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LRC_RES_VALUE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LRC_RES_VALUE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_LRC_RES_VALUE_ATTR
(
			ID VARCHAR2(255),
			OBJ_ID VARCHAR2(36),
			ATTR_KEY VARCHAR2(255),
			ATTR_VALUE VARCHAR2(2000),
			OWNER VARCHAR2(255)
)
/

-----------------------------------------------------------------------------
-- KSEN_LRC_RES_SCALE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LRC_RES_SCALE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LRC_RES_SCALE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/


create table KSEN_LRC_RES_SCALE
(
   ID VARCHAR2(255),
   OBJ_ID VARCHAR2(36),
   VER_NBR NUMBER(19,0),
   CREATEID VARCHAR2(255),
   CREATETIME TIMESTAMP (6),
   UPDATEID VARCHAR2(255),
   UPDATETIME TIMESTAMP (6),
   EFFECTIVEDATE TIMESTAMP (6),
   EXPIRATIONDATE TIMESTAMP (6),
   NAME VARCHAR2(255),
   RT_DESCR_ID VARCHAR2(255),
   STATE_ID VARCHAR2(255),
   TYPE_ID VARCHAR2(255),
   MIN_VALUE VARCHAR2(255),
   MAX_VALUE VARCHAR2(255),
   INCR VARCHAR2(255)
)
/

-----------------------------------------------------------------------------
-- KSEN_LRC_RES_SCALE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LRC_RES_SCALE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LRC_RES_SCALE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_LRC_RES_SCALE_ATTR
(
   ID VARCHAR2(255),
   OBJ_ID VARCHAR2(36),
   ATTR_KEY VARCHAR2(255),
   ATTR_VALUE VARCHAR2(2000),
   OWNER VARCHAR2(255)
)
/

