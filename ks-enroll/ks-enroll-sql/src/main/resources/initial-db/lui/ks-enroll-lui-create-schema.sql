-----------------------------------------------------------------------------
-- KSEN_LUI
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LUI';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LUI CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_LUI 
   (	ID VARCHAR2(255), 
			OBJ_ID VARCHAR2(36), 
			VER_NBR NUMBER(19,0), 
			CREATEID VARCHAR2(255), 
			CREATETIME TIMESTAMP (6), 
			UPDATEID VARCHAR2(255), 
			UPDATETIME TIMESTAMP (6), 
			ATP_ID VARCHAR2(255), 
			CLU_ID VARCHAR2(255), 
			EFF_DT TIMESTAMP (6), 
			EXP_DT TIMESTAMP (6), 
			MAX_SEATS NUMBER(10,0), 
			MIN_SEATS NUMBER(10,0), 
			NAME VARCHAR2(255), 
			REF_URL VARCHAR2(255), 
			RT_DESCR_ID VARCHAR2(255), 
			STATE_ID VARCHAR2(255), 
			TYPE_ID VARCHAR2(255), 
			OFFIC_LUI_ID VARCHAR2(255)
   )
/

-----------------------------------------------------------------------------
-- KSEN_LUILUI_RELTN
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LUILUI_RELTN';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LUILUI_RELTN CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_LUILUI_RELTN 
   (	ID VARCHAR2(255), 
			OBJ_ID VARCHAR2(36), 
			VER_NBR NUMBER(19,0), 
			CREATEID VARCHAR2(255), 
			CREATETIME TIMESTAMP (6), 
			UPDATEID VARCHAR2(255), 
			UPDATETIME TIMESTAMP (6), 
			EFF_DT TIMESTAMP (6), 
			EXP_DT TIMESTAMP (6), 
			NAME VARCHAR2(255), 
			RT_DESCR_ID VARCHAR2(255), 
			LUI_ID VARCHAR2(255), 
			TYPE_ID VARCHAR2(255), 
			STATE_ID VARCHAR2(255), 
			RELATED_LUI_ID VARCHAR2(255)
   )
/

--------------------------------------------------------
--  KSEN_LUILUI_RELTN_ATTR
--------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LUILUI_RELTN_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LUILUI_RELTN_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

  CREATE TABLE KSEN_LUILUI_RELTN_ATTR
   (  ID VARCHAR2(255), 
			OBJ_ID VARCHAR2(36),  
			ATTR_KEY VARCHAR2(255), 
			ATTR_VALUE VARCHAR2(2000), 
			OWNER VARCHAR2(255)
   ) 
/

-----------------------------------------------------------------------------
-- KSEN_LUI_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LUI_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LUI_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_LUI_ATTR 
   (	ID VARCHAR2(255), 
			OBJ_ID VARCHAR2(36), 
			ATTR_KEY VARCHAR2(255), 
			ATTR_VALUE VARCHAR2(2000), 
			OWNER VARCHAR2(255)
   )
/

-----------------------------------------------------------------------------
-- KSEN_LUI_INSTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LUI_INSTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LUI_INSTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_LUI_INSTR 
   (	ID VARCHAR2(255), 
			OBJ_ID VARCHAR2(36), 
			VER_NBR NUMBER(19,0), 
			CREATEID VARCHAR2(255), 
			CREATETIME TIMESTAMP (6), 
			UPDATEID VARCHAR2(255), 
			UPDATETIME TIMESTAMP (6), 
			ORG_ID VARCHAR2(255), 
			PERCT_EFFT FLOAT(126), 
			PERS_ID VARCHAR2(255), 
			PERS_OVRID VARCHAR2(255)
   )
/

-----------------------------------------------------------------------------
-- KSEN_LUI_JN_LUI_INSTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LUI_JN_LUI_INSTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LUI_JN_LUI_INSTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_LUI_JN_LUI_INSTR 
   (	LUI_ID VARCHAR2(255), 
			LUI_INSTR_ID VARCHAR2(255)
   )
/

--------------------------------------------------------
--  KSEN_LUI_JN_LUI_IDENT
--------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LUI_JN_LUI_IDENT';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LUI_JN_LUI_IDENT CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_LUI_JN_LUI_IDENT
	( LUI_ID VARCHAR2(255), 
		ALT_LUI_ID VARCHAR2(255)
	)
/

-----------------------------------------------------------------------------
-- KSEN_LUI_RICH_TEXT
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LUI_RICH_TEXT';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LUI_RICH_TEXT CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_LUI_RICH_TEXT 
   (	ID VARCHAR2(255), 
			OBJ_ID VARCHAR2(36), 
			VER_NBR NUMBER(19,0), 
			FORMATTED VARCHAR2(2000), 
			PLAIN VARCHAR2(2000)
   )
/

-----------------------------------------------------------------------------
-- KSEN_LUI_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LUI_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LUI_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_LUI_TYPE 
   (	TYPE_KEY VARCHAR2(255), 
			OBJ_ID VARCHAR2(36), 
			VER_NBR NUMBER(19,0), 
			TYPE_DESC VARCHAR2(2000), 
			EFF_DT TIMESTAMP (6), 
			EXPIR_DT TIMESTAMP (6), 
			NAME VARCHAR2(255)
   )
/

--------------------------------------------------------
--  KSEN_LUI_TYPE_ATTR
--------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LUI_TYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LUI_TYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/
      
CREATE TABLE KSEN_LUI_TYPE_ATTR
   (  ID VARCHAR2(255),
			OBJ_ID VARCHAR2(36),
			ATTR_KEY VARCHAR2(255),
			ATTR_VALUE VARCHAR2(2000),
			OWNER VARCHAR2(255)
   ) 
/

--------------------------------------------------------
--  KSEN_LUI_IDENT
--------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LUI_IDENT';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LUI_IDENT CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

      
  CREATE TABLE KSEN_LUI_IDENT
   (  ID VARCHAR2(255),
			OBJ_ID VARCHAR2(36),
			VER_NBR NUMBER(19,0),
			CREATEID VARCHAR2(255),
			CREATETIME TIMESTAMP (6),
			UPDATEID VARCHAR2(255),
			UPDATETIME TIMESTAMP (6), 
			CD VARCHAR2(255), 
			DIVISION VARCHAR2(255),
			LNG_NAME VARCHAR2(255), 
			SHRT_NAME VARCHAR2(255), 
			ST VARCHAR2(255), 
			SUFX_CD VARCHAR2(255), 
			TYPE VARCHAR2(255), 
			VARTN VARCHAR2(255) 
   ) 
/

--------------------------------------------------------
--  KSEN_LUI_IDENT_ATTR
--------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LUI_IDENT_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LUI_IDENT_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

  CREATE TABLE KSEN_LUI_IDENT_ATTR 
   (  ID VARCHAR2(255), 
			OBJ_ID VARCHAR2(36), 
			ATTR_KEY VARCHAR2(255), 
			ATTR_VALUE VARCHAR2(2000), 
			OWNER VARCHAR2(255)
   ) 
/

--------------------------------------------------------
--  KSEN_LUI_LUCD
--------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LUI_LUCD';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LUI_LUCD CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

  CREATE TABLE KSEN_LUI_LUCD
   (  ID VARCHAR2(255),
			OBJ_ID VARCHAR2(36),
			VER_NBR NUMBER(19,0), 
			CREATEID VARCHAR2(255),
			CREATETIME TIMESTAMP (6), 
			UPDATEID VARCHAR2(255),
			UPDATETIME TIMESTAMP (6), 
			TYPE VARCHAR2(255CHAR), 
			VALUE VARCHAR2(255), 
			RT_DESCR_ID VARCHAR2(255),
			LUI_ID VARCHAR2(255)
   )
/

--------------------------------------------------------
--  KSEN_LUI_LUCD_ATTR
--------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LUI_LUCD_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LUI_LUCD_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/
      
  CREATE TABLE KSEN_LUI_LUCD_ATTR
   (  ID VARCHAR2(255), 
			OBJ_ID VARCHAR2(36), 
			ATTR_KEY VARCHAR2(255), 
			ATTR_VALUE VARCHAR2(2000), 
			OWNER VARCHAR2(255)
   )
/

 --------------------------------------------------------
--  KSEN_LUI_MTG_SCHE
--------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LUI_MTG_SCHE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LUI_MTG_SCHE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

  CREATE TABLE KSEN_LUI_MTG_SCHE
   (  ID VARCHAR2(255),
      OBJ_ID VARCHAR2(36),
      VER_NBR NUMBER(19,0),
      CREATEID VARCHAR2(255),
      CREATETIME TIMESTAMP (6),
      UPDATEID VARCHAR2(255),
      UPDATETIME TIMESTAMP (6),
      SPACE_ID VARCHAR2(255),
      TM_PRD VARCHAR2(255),
      LUI_ID VARCHAR2(255)
   )
 /

 --------------------------------------------------------
--  KSEN_LUI_RV_GRP_RELTN
--------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
  SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_LUI_RV_GRP_RELTN';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_LUI_RV_GRP_RELTN CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

create table  KSEN_LUI_RV_GRP_RELTN
  (
        ID VARCHAR2(255),
	    LUI_ID varchar2(255),
	    RV_GRP_ID varchar2(255)
  )
/