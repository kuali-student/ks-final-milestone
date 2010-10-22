

-----------------------------------------------------------------------------
-- KSCO_COMMENT
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSCO_COMMENT';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSCO_COMMENT CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSCO_COMMENT
(
      ID VARCHAR2(255)
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , VER_NBR NUMBER(19) NOT NULL
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , STATE VARCHAR2(255)
        , RT_DESCR_ID VARCHAR2(255)
        , REFERENCE VARCHAR2(255)
        , TYPE VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
    

)
/

ALTER TABLE KSCO_COMMENT
    ADD CONSTRAINT KSCO_COMMENTP1
PRIMARY KEY (ID)
/


CREATE INDEX KSCO_COMMENT_I1 
  ON KSCO_COMMENT 
  (TYPE)
/
CREATE INDEX KSCO_COMMENT_I2 
  ON KSCO_COMMENT 
  (RT_DESCR_ID)
/
CREATE INDEX KSCO_COMMENT_I3 
  ON KSCO_COMMENT 
  (REFERENCE)
/





-----------------------------------------------------------------------------
-- KSCO_COMMENT_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSCO_COMMENT_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSCO_COMMENT_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSCO_COMMENT_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSCO_COMMENT_ATTR
    ADD CONSTRAINT KSCO_COMMENT_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSCO_COMMENT_ATTR_I1 
  ON KSCO_COMMENT_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSCO_COMMENT_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSCO_COMMENT_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSCO_COMMENT_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSCO_COMMENT_TYPE
(
      TYPE_KEY VARCHAR2(255)
        , TYPE_DESC VARCHAR2(2000)
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , NAME VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSCO_COMMENT_TYPE
    ADD CONSTRAINT KSCO_COMMENT_TYPEP1
PRIMARY KEY (TYPE_KEY)
/







-----------------------------------------------------------------------------
-- KSCO_COMMENT_TYPE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSCO_COMMENT_TYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSCO_COMMENT_TYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSCO_COMMENT_TYPE_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSCO_COMMENT_TYPE_ATTR
    ADD CONSTRAINT KSCO_COMMENT_TYPE_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSCO_COMMENT_TYPE_ATTR_I1 
  ON KSCO_COMMENT_TYPE_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSCO_REFERENCE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSCO_REFERENCE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSCO_REFERENCE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSCO_REFERENCE
(
      ID VARCHAR2(255)
        , REFERENCE_ID VARCHAR2(255)
        , REFERENCE_TYPE VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    
    , CONSTRAINT SYS_C0033779 UNIQUE (REFERENCE_ID, REFERENCE_TYPE)

)
/

ALTER TABLE KSCO_REFERENCE
    ADD CONSTRAINT KSCO_REFERENCEP1
PRIMARY KEY (ID)
/


CREATE INDEX KSCO_REFERENCE_I1 
  ON KSCO_REFERENCE 
  (REFERENCE_TYPE)
/





-----------------------------------------------------------------------------
-- KSCO_REFERENCE_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSCO_REFERENCE_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSCO_REFERENCE_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSCO_REFERENCE_TYPE
(
      TYPE_KEY VARCHAR2(255)
        , TYPE_DESC VARCHAR2(2000)
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , NAME VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSCO_REFERENCE_TYPE
    ADD CONSTRAINT KSCO_REFERENCE_TYPEP1
PRIMARY KEY (TYPE_KEY)
/







-----------------------------------------------------------------------------
-- KSCO_REFERENCE_TYPE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSCO_REFERENCE_TYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSCO_REFERENCE_TYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSCO_REFERENCE_TYPE_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSCO_REFERENCE_TYPE_ATTR
    ADD CONSTRAINT KSCO_REFERENCE_TYPE_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSCO_REFERENCE_TYPE_ATTR_I1 
  ON KSCO_REFERENCE_TYPE_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSCO_RICH_TEXT_T
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSCO_RICH_TEXT_T';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSCO_RICH_TEXT_T CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSCO_RICH_TEXT_T
(
      ID VARCHAR2(255)
        , FORMATTED VARCHAR2(2000)
        , PLAIN VARCHAR2(2000)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSCO_RICH_TEXT_T
    ADD CONSTRAINT KSCO_RICH_TEXT_TP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSCO_TAG
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSCO_TAG';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSCO_TAG CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSCO_TAG
(
      ID VARCHAR2(255)
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , VER_NBR NUMBER(19) NOT NULL
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , NAME_SPACE VARCHAR2(255)
        , PREDICATE VARCHAR2(255)
        , STATE VARCHAR2(255)
        , VAL VARCHAR2(255)
        , REFERENCE VARCHAR2(255)
        , TYPE VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
    

)
/

ALTER TABLE KSCO_TAG
    ADD CONSTRAINT KSCO_TAGP1
PRIMARY KEY (ID)
/


CREATE INDEX KSCO_TAG_I1 
  ON KSCO_TAG 
  (REFERENCE)
/
CREATE INDEX KSCO_TAG_I2 
  ON KSCO_TAG 
  (TYPE)
/





-----------------------------------------------------------------------------
-- KSCO_TAG_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSCO_TAG_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSCO_TAG_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSCO_TAG_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSCO_TAG_ATTR
    ADD CONSTRAINT KSCO_TAG_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSCO_TAG_ATTR_I1 
  ON KSCO_TAG_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSCO_TAG_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSCO_TAG_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSCO_TAG_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSCO_TAG_TYPE
(
      TYPE_KEY VARCHAR2(255)
        , TYPE_DESC VARCHAR2(2000)
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , NAME VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSCO_TAG_TYPE
    ADD CONSTRAINT KSCO_TAG_TYPEP1
PRIMARY KEY (TYPE_KEY)
/







-----------------------------------------------------------------------------
-- KSCO_TAG_TYPE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSCO_TAG_TYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSCO_TAG_TYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSCO_TAG_TYPE_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSCO_TAG_TYPE_ATTR
    ADD CONSTRAINT KSCO_TAG_TYPE_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSCO_TAG_TYPE_ATTR_I1 
  ON KSCO_TAG_TYPE_ATTR 
  (OWNER)
/