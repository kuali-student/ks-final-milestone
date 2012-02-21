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

/*CREATE TABLE KSEN_LUI
(
	ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(36) NULL ,
	LUI_TYPE             VARCHAR2(255) NOT NULL ,
	LUI_STATE            VARCHAR2(255) NOT NULL ,
	NAME                 VARCHAR2(255) NULL ,
	DESCR_PLAIN          VARCHAR2(4000) NOT NULL ,
	DESCR_FORMATTED      VARCHAR2(4000) NULL ,
	EFF_DT               TIMESTAMP(6) NULL ,
	EXPIR_DT             TIMESTAMP(6) NULL ,
	CLU_ID               VARCHAR2(255) NULL ,
	ATP_ID               VARCHAR2(255) NULL ,
	MAX_SEATS            NUMBER(10) NULL ,
	MIN_SEATS            NUMBER(10) NULL ,
	REF_URL              VARCHAR2(255) NULL ,
	VER_NBR              NUMBER(19) NULL ,
	CREATETIME           TIMESTAMP(6) NULL ,
	CREATEID             VARCHAR2(255) NULL ,
	UPDATETIME           TIMESTAMP(6) NULL ,
	UPDATEID             VARCHAR2(255) NULL 
);*/

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
(
	ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(36) NULL ,
	LUILUI_RELTN_TYPE    VARCHAR2(255) NOT NULL ,
	LUI_RELTN_STATE      VARCHAR2(255) NOT NULL ,
	NAME                 VARCHAR2(255) NOT NULL ,
	DESCR_PLAIN          VARCHAR2(4000) NOT NULL ,
	DESCR_FORMATTED      VARCHAR2(4000) NULL ,
	LUI_ID               VARCHAR2(255) NULL ,
	RELATED_LUI_ID       VARCHAR2(255) NULL ,
	EFF_DT               TIMESTAMP(6) NULL ,
	EXPIR_DT             TIMESTAMP(6) NULL ,
	VER_NBR              NUMBER(19) NULL ,
	CREATETIME           TIMESTAMP(6) NULL ,
	CREATEID             VARCHAR2(255) NULL ,
	UPDATETIME           TIMESTAMP(6) NULL ,
	UPDATEID             VARCHAR2(255) NULL
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

CREATE UNIQUE INDEX KSEN_LUILUI_RELTN_P ON KSEN_LUILUI_RELTN
(ID   ASC);




CREATE  INDEX KSEN_LUILUI_RELTN_I1 ON KSEN_LUILUI_RELTN
(LUILUI_RELTN_TYPE   ASC,RELATED_LUI_ID   ASC);



CREATE  INDEX KSEN_LUILUI_RELTN_I2 ON KSEN_LUILUI_RELTN
(LUILUI_RELTN_TYPE   ASC,LUI_ID   ASC);



CREATE TABLE KSEN_LUILUI_RELTN_ATTR
(
	ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(36) NULL ,
	ATTR_KEY             VARCHAR2(255) NULL ,
	ATTR_VALUE           VARCHAR2(2000) NULL ,
	OWNER_ID             VARCHAR2(255) NULL
)
/

/*CREATE TABLE KSEN_LUI_ATTR
(
	ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(36) NULL ,
	ATTR_KEY             VARCHAR2(255) NULL ,
	ATTR_VALUE           VARCHAR2(2000) NULL ,
	OWNER_ID             VARCHAR2(255) NULL 
);*/

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
			NAME VARCHAR2(255),
			REF_OBJECT_URI VARCHAR2(255)
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

/*
CREATE TABLE KSEN_LUI_IDENT
(
	ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(36) NULL ,
	LUI_ID_TYPE          VARCHAR2(255) NOT NULL ,
	LUI_ID_STATE         VARCHAR2(255) NOT NULL ,
	LUI_CD               VARCHAR2(255) NULL ,
	SHRT_NAME            VARCHAR2(255) NULL ,
	LNG_NAME             VARCHAR2(255) NULL ,
	DIVISION             VARCHAR2(255) NULL ,
	SUFX_CD              VARCHAR2(255) NULL ,
	VARTN                VARCHAR2(255) NULL ,
	VER_NBR              NUMBER(19) NULL ,
	CREATETIME           TIMESTAMP(6) NULL ,
	CREATEID             VARCHAR2(255) NULL ,
	UPDATETIME           TIMESTAMP(6) NULL ,
	UPDATEID             VARCHAR2(255) NULL ,
	LUI_ID               VARCHAR2(255) NULL 
);*/
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
/*CREATE TABLE KSEN_LUI_IDENT_ATTR
(
	ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(36) NULL ,
	ATTR_KEY             VARCHAR2(255) NULL ,
	ATTR_VALUE           VARCHAR2(2000) NULL ,
	OWNER_ID             VARCHAR2(255) NULL 
);*/

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


/*
 Note: table name change. 
 CREATE TABLE KSEN_LUI_LU_CD
(
	ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(36) NULL ,
	LUI_LUCD_TYPE        VARCHAR2(255) NOT NULL ,
	DESCR_PLAIN          VARCHAR2(4000) NOT NULL ,
	DESCR_FORMATTED      VARCHAR2(4000) NULL ,
	VALUE                VARCHAR2(255) NULL ,
	VER_NBR              NUMBER(19) NULL ,
	CREATEID             VARCHAR2(255) NULL ,
	CREATETIME           TIMESTAMP(6) NULL ,
	UPDATEID             VARCHAR2(255) NULL ,
	UPDATETIME           TIMESTAMP(6) NULL ,
	LUI_ID               VARCHAR2(255) NULL 
);*/
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
/*CREATE TABLE KSEN_LUI_LU_CD_ATTR
(
	ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(36) NULL ,
	ATTR_KEY             VARCHAR2(255) NULL ,
	ATTR_VALUE           VARCHAR2(2000) NULL ,
	OWNER_ID             VARCHAR2(255) NULL 
);
*/
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
 
 /*CREATE TABLE KSEN_LUI_MTG_SCHE
(
	ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(36) NULL ,
	SPACE_ID             VARCHAR2(255) NULL ,
	SCHEDULE_ID          VARCHAR2(255) NULL ,
	LUI_ID               VARCHAR2(255) NULL 
);*/
 
--------------------------------------------------------
--  KSEN_LUI_MTG_SCHE_ATTR
--------------------------------------------------------
 
/*CREATE TABLE KSEN_LUI_MTG_SCHE_ATTR
(
	ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(36) NULL ,
	ATTR_KEY             VARCHAR2(255) NULL ,
	ATTR_VALUE           VARCHAR2(2000) NULL ,
	OWNER_ID             VARCHAR2(255) NULL 
);
*/ 

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
  );

--------------------------------------------------------
--  KSEN_LUI_AFFILIATED_ORG
--------------------------------------------------------

/*CREATE TABLE KSEN_LUI_AFFILIATED_ORG
(
	ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(36) NULL ,
	AFFILIATED_ORG_TYPE  VARCHAR2(255) NOT NULL ,
	AFFILIATED_ORG_STATE VARCHAR2(255) NOT NULL ,
	ORG_ID               VARCHAR2(255) NOT NULL ,
	PERCENTAGE           NUMBER NULL ,
	EFF_DT               TIMESTAMP(6) NULL ,
	EXPIR_DT             TIMESTAMP(6) NULL ,
	VER_NBR              NUMBER(19) NULL ,
	CREATETIME           TIMESTAMP(6) NULL ,
	CREATEID             VARCHAR2(255) NULL ,
	UPDATETIME           TIMESTAMP(6) NULL ,
	UPDATEID             VARCHAR2(255) NULL ,
	EXPENDITURE_ID       VARCHAR2(255) NULL ,
	REVENUE_ID           VARCHAR2(255) NULL 
);*/

--------------------------------------------------------
--  KSEN_LUI_AFFIL_ORG_ATTR
--------------------------------------------------------

/*CREATE TABLE KSEN_LUI_AFFIL_ORG_ATTR
(
	ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(36) NULL ,
	ATTR_KEY             VARCHAR2(255) NULL ,
	ATTR_VALUE           VARCHAR2(2000) NULL ,
	OWNER_ID             VARCHAR2(255) NULL 
);*/

--------------------------------------------------------
--  KSEN_LUI_CAPACITY
--------------------------------------------------------

/*CREATE TABLE KSEN_LUI_CAPACITY
(
	ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(36) NULL ,
	LUI_CAPACITY_TYPE    VARCHAR2(255) NOT NULL ,
	LUI_CAPACITY_STATE   VARCHAR2(255) NOT NULL ,
	NAME                 VARCHAR2(255) NULL ,
	DESCR_PLAIN          VARCHAR2(4000) NOT NULL ,
	DESCR_FORMATTED      VARCHAR(4000) NULL ,
	MAX_SEATS            NUMBER NULL ,
	PROCESSING_ORDER     NUMBER NULL ,
	EFF_DT               TIMESTAMP(6) NULL ,
	EXPIR_DT             TIMESTAMP(6) NULL ,
	VER_NBR              NUMBER(19) NULL ,
	CREATETIME           TIMESTAMP(6) NULL ,
	CREATEID             VARCHAR2(255) NULL ,
	UPDATETIME           TIMESTAMP(6) NULL ,
	UPDATEID             VARCHAR2(255) NULL 
);*/

--------------------------------------------------------
--  KSEN_LUI_CLUCLU_RELTN
--------------------------------------------------------

/*CREATE TABLE KSEN_LUI_CLUCLU_RELTN
(
	ID                   VARCHAR2(255) NOT NULL ,
	LUI_ID               VARCHAR2(255) NULL ,
	CLUCLU_RELTN_ID      VARCHAR2(255) NULL 
);*/

--------------------------------------------------------
--  KSEN_LUI_CURRENCY_AMT
--------------------------------------------------------

/*CREATE TABLE KSEN_LUI_CURRENCY_AMT
(
	ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(36) NULL ,
	CURRENCY_TYPE        VARCHAR2(255) NULL ,
	CURRENCY_QUANTITY    NUMBER NULL ,
	VER_NBR              NUMBER(19) NULL ,
	CREATETIME           TIMESTAMP(6) NULL ,
	CREATEID             VARCHAR2(255) NULL ,
	UPDATETIME           TIMESTAMP(6) NULL ,
	UPDATEID             VARCHAR2(255) NULL ,
	FEE_ID               VARCHAR2(255) NULL 
);*/

--------------------------------------------------------
--  KSEN_LUI_EXPEND_ATTR
--------------------------------------------------------

/*CREATE TABLE KSEN_LUI_EXPEND_ATTR
(
	ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(36) NULL ,
	ATTR_KEY             VARCHAR2(255) NULL ,
	ATTR_VALUE           VARCHAR2(2000) NULL ,
	OWNER_ID             VARCHAR2(255) NULL 
);*/

--------------------------------------------------------
--  KSEN_LUI_EXPENDITURE
--------------------------------------------------------

/*CREATE TABLE KSEN_LUI_EXPENDITURE
(
	ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(36) NULL ,
	VER_NBR              NUMBER(19) NULL ,
	CREATETIME           TIMESTAMP(6) NULL ,
	CREATEID             VARCHAR2(255) NULL ,
	UPDATETIME           TIMESTAMP(6) NULL ,
	UPDATEID             VARCHAR2(255) NULL ,
	LUI_ID               VARCHAR2(255) NULL 
);*/

--------------------------------------------------------
--  KSEN_LUI_FEE
--------------------------------------------------------

/*CREATE TABLE KSEN_LUI_FEE
(
	ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(36) NULL ,
	FEE_TYPE             VARCHAR2(255) NULL ,
	RATE_TYPE            VARCHAR2(255) NULL ,
	DESCR_PLAIN          VARCHAR2(4000) NOT NULL ,
	DESCR_FORMATTED      VARCHAR2(4000) NULL ,
	FEE_KEY              VARCHAR2(255) NULL ,
	VER_NBR              NUMBER(19) NULL ,
	CREATETIME           TIMESTAMP(6) NULL ,
	CREATEID             VARCHAR2(255) NULL ,
	UPDATETIME           TIMESTAMP(6) NULL ,
	UPDATEID             VARCHAR2(255) NULL ,
	LUI_ID               VARCHAR2(255) NULL 
);*/

--------------------------------------------------------
--  KSEN_LUI_FEE_ATTR
--------------------------------------------------------

/*CREATE TABLE KSEN_LUI_FEE_ATTR
(
	ID                   VARCHAR2(255) NOT NULL ,
	OBJ_ID               VARCHAR2(36) NULL ,
	ATTR_KEY             VARCHAR2(255) NULL ,
	ATTR_VALUE           VARCHAR2(2000) NULL ,
	OWNER_ID             VARCHAR2(255) NULL 
);
*/