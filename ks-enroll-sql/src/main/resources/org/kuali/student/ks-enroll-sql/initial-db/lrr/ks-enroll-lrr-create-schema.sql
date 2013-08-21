

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
      ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , NAME VARCHAR2(255)
        , RT_DESCR_ID VARCHAR2(255)
        , STATE_ID VARCHAR2(255)
        , TYPE_ID VARCHAR2(255)
        , LPR_ID VARCHAR2(255)
        , RESULT_VALUE_ID VARCHAR2(255)
    

)
/

ALTER TABLE KSEN_LRR
    ADD CONSTRAINT KSEN_LRRP1
PRIMARY KEY (ID)
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
      ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , ATTR_KEY VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER_ID VARCHAR2(255)
    

)
/

ALTER TABLE KSEN_LRR_ATTR
    ADD CONSTRAINT KSEN_LRR_ATTRP1
PRIMARY KEY (ID)
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

CREATE TABLE KSEN_LRR_RES_SOURCE
(
      ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , NAME VARCHAR2(255)
        , RT_DESCR_ID VARCHAR2(255)
        , TYPE_ID VARCHAR2(255)
        , ARTICULATE_ID VARCHAR2(255)
        , RES_TRANS_ID VARCHAR2(255)
    

)
/

ALTER TABLE KSEN_LRR_RES_SOURCE
    ADD CONSTRAINT KSEN_LRR_RES_SOURCEP1
PRIMARY KEY (ID)
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
      ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , ATTR_KEY VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER_ID VARCHAR2(255)
    

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

CREATE TABLE KSEN_LRR_RES_SRC_RELTN
(
      LRR_ID VARCHAR2(255)
        , RES_SRC_ID VARCHAR2(255)
    

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
      TYPE_KEY VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
        , TYPE_DESC VARCHAR2(2000)
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , NAME VARCHAR2(255)
        , REF_OBJECT_URI VARCHAR2(255)
    

)
/

ALTER TABLE KSEN_LRR_TYPE
    ADD CONSTRAINT KSEN_LRR_TYPEP1
PRIMARY KEY (TYPE_KEY)
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
(
      ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
        , FORMATTED VARCHAR2(2000)
        , PLAIN VARCHAR2(2000)


)
/

ALTER TABLE KSEN_RICH_TEXT_T
    ADD CONSTRAINT KSEN_RICH_TEXT_TP1
PRIMARY KEY (ID)
/
