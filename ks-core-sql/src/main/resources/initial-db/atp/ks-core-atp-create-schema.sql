
-----------------------------------------------------------------------------
-- KSAP_ATP
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSAP_ATP';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSAP_ATP CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSAP_ATP
(
      ID VARCHAR2(255)
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , VER_NBR NUMBER(19) NOT NULL
        , END_DT TIMESTAMP
        , NAME VARCHAR2(255)
        , START_DT TIMESTAMP
        , STATE VARCHAR2(255)
        , RT_DESCR_ID VARCHAR2(255)
        , TYPE VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
    

)
/

ALTER TABLE KSAP_ATP
    ADD CONSTRAINT KSAP_ATPP1
PRIMARY KEY (ID)
/


CREATE INDEX KSAP_ATP_I1 
  ON KSAP_ATP 
  (TYPE)
/
CREATE INDEX KSAP_ATP_I2 
  ON KSAP_ATP 
  (RT_DESCR_ID)
/





-----------------------------------------------------------------------------
-- KSAP_ATP_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSAP_ATP_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSAP_ATP_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSAP_ATP_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSAP_ATP_ATTR
    ADD CONSTRAINT KSAP_ATP_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSAP_ATP_ATTR_I1 
  ON KSAP_ATP_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSAP_ATP_DUR_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSAP_ATP_DUR_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSAP_ATP_DUR_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSAP_ATP_DUR_TYPE
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

ALTER TABLE KSAP_ATP_DUR_TYPE
    ADD CONSTRAINT KSAP_ATP_DUR_TYPEP1
PRIMARY KEY (TYPE_KEY)
/







-----------------------------------------------------------------------------
-- KSAP_ATP_DUR_TYPE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSAP_ATP_DUR_TYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSAP_ATP_DUR_TYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSAP_ATP_DUR_TYPE_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSAP_ATP_DUR_TYPE_ATTR
    ADD CONSTRAINT KSAP_ATP_DUR_TYPE_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSAP_ATP_DUR_TYPE_ATTR_I1 
  ON KSAP_ATP_DUR_TYPE_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSAP_ATP_SEASONAL_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSAP_ATP_SEASONAL_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSAP_ATP_SEASONAL_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSAP_ATP_SEASONAL_TYPE
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

ALTER TABLE KSAP_ATP_SEASONAL_TYPE
    ADD CONSTRAINT KSAP_ATP_SEASONAL_TYPEP1
PRIMARY KEY (TYPE_KEY)
/







-----------------------------------------------------------------------------
-- KSAP_ATP_SEASONAL_TYPE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSAP_ATP_SEASONAL_TYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSAP_ATP_SEASONAL_TYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSAP_ATP_SEASONAL_TYPE_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSAP_ATP_SEASONAL_TYPE_ATTR
    ADD CONSTRAINT KSAP_ATP_SEASONAL_TYPE_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSAP_ATP_SEAS_TYPE_ATTR_I1 
  ON KSAP_ATP_SEASONAL_TYPE_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSAP_ATP_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSAP_ATP_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSAP_ATP_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSAP_ATP_TYPE
(
      TYPE_KEY VARCHAR2(255)
        , TYPE_DESC VARCHAR2(2000)
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , NAME VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , DUR_TYPE VARCHAR2(255)
        , SEASONAL_TYPE VARCHAR2(255)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSAP_ATP_TYPE
    ADD CONSTRAINT KSAP_ATP_TYPEP1
PRIMARY KEY (TYPE_KEY)
/


CREATE INDEX KSAP_ATP_TYPE_I1 
  ON KSAP_ATP_TYPE 
  (SEASONAL_TYPE)
/
CREATE INDEX KSAP_ATP_TYPE_I2 
  ON KSAP_ATP_TYPE 
  (DUR_TYPE)
/





-----------------------------------------------------------------------------
-- KSAP_ATP_TYPE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSAP_ATP_TYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSAP_ATP_TYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSAP_ATP_TYPE_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSAP_ATP_TYPE_ATTR
    ADD CONSTRAINT KSAP_ATP_TYPE_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSAP_ATP_TYPE_ATTR_I1 
  ON KSAP_ATP_TYPE_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSAP_DT_RANGE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSAP_DT_RANGE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSAP_DT_RANGE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSAP_DT_RANGE
(
      DATERANGE_KEY VARCHAR2(255)
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , VER_NBR NUMBER(19) NOT NULL
        , END_DT TIMESTAMP
        , NAME VARCHAR2(255)
        , START_DT TIMESTAMP
        , STATE VARCHAR2(255)
        , ATP_ID VARCHAR2(255)
        , RT_DESCR_ID VARCHAR2(255)
        , DT_RANGE_TYPE_ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
    

)
/

ALTER TABLE KSAP_DT_RANGE
    ADD CONSTRAINT KSAP_DT_RANGEP1
PRIMARY KEY (DATERANGE_KEY)
/


CREATE INDEX KSAP_DT_RANGE_I1 
  ON KSAP_DT_RANGE 
  (DT_RANGE_TYPE_ID)
/
CREATE INDEX KSAP_DT_RANGE_I2 
  ON KSAP_DT_RANGE 
  (RT_DESCR_ID)
/
CREATE INDEX KSAP_DT_RANGE_I3 
  ON KSAP_DT_RANGE 
  (ATP_ID)
/





-----------------------------------------------------------------------------
-- KSAP_DT_RANGE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSAP_DT_RANGE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSAP_DT_RANGE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSAP_DT_RANGE_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSAP_DT_RANGE_ATTR
    ADD CONSTRAINT KSAP_DT_RANGE_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSAP_DT_RANGE_ATTR_I1 
  ON KSAP_DT_RANGE_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSAP_DT_RANGE_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSAP_DT_RANGE_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSAP_DT_RANGE_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSAP_DT_RANGE_TYPE
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

ALTER TABLE KSAP_DT_RANGE_TYPE
    ADD CONSTRAINT KSAP_DT_RANGE_TYPEP1
PRIMARY KEY (TYPE_KEY)
/







-----------------------------------------------------------------------------
-- KSAP_DT_RANGE_TYPE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSAP_DT_RANGE_TYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSAP_DT_RANGE_TYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSAP_DT_RANGE_TYPE_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSAP_DT_RANGE_TYPE_ATTR
    ADD CONSTRAINT KSAP_DT_RANGE_TYPE_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSAP_DT_RANGE_TYPE_ATTR_I1 
  ON KSAP_DT_RANGE_TYPE_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSAP_MLSTN
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSAP_MLSTN';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSAP_MLSTN CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSAP_MLSTN
(
      ID VARCHAR2(255)
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , VER_NBR NUMBER(19) NOT NULL
        , MLSTN_DT TIMESTAMP
        , NAME VARCHAR2(255)
        , STATE VARCHAR2(255)
        , ATP_ID VARCHAR2(255)
        , RT_DESCR_ID VARCHAR2(255)
        , MLSTN_TYPE_ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
    

)
/

ALTER TABLE KSAP_MLSTN
    ADD CONSTRAINT KSAP_MLSTNP1
PRIMARY KEY (ID)
/


CREATE INDEX KSAP_MLSTN_I1 
  ON KSAP_MLSTN 
  (ATP_ID)
/
CREATE INDEX KSAP_MLSTN_I2 
  ON KSAP_MLSTN 
  (RT_DESCR_ID)
/
CREATE INDEX KSAP_MLSTN_I3 
  ON KSAP_MLSTN 
  (MLSTN_TYPE_ID)
/





-----------------------------------------------------------------------------
-- KSAP_MLSTN_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSAP_MLSTN_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSAP_MLSTN_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSAP_MLSTN_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSAP_MLSTN_ATTR
    ADD CONSTRAINT KSAP_MLSTN_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSAP_MLSTN_ATTR_I1 
  ON KSAP_MLSTN_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSAP_MLSTN_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSAP_MLSTN_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSAP_MLSTN_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSAP_MLSTN_TYPE
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

ALTER TABLE KSAP_MLSTN_TYPE
    ADD CONSTRAINT KSAP_MLSTN_TYPEP1
PRIMARY KEY (TYPE_KEY)
/







-----------------------------------------------------------------------------
-- KSAP_MLSTN_TYPE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSAP_MLSTN_TYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSAP_MLSTN_TYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSAP_MLSTN_TYPE_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSAP_MLSTN_TYPE_ATTR
    ADD CONSTRAINT KSAP_MLSTN_TYPE_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSAP_MLSTN_TYPE_ATTR_I1 
  ON KSAP_MLSTN_TYPE_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSAP_RICH_TEXT_T
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSAP_RICH_TEXT_T';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSAP_RICH_TEXT_T CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSAP_RICH_TEXT_T
(
      ID VARCHAR2(255)
        , FORMATTED VARCHAR2(2000)
        , PLAIN VARCHAR2(2000)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSAP_RICH_TEXT_T
    ADD CONSTRAINT KSAP_RICH_TEXT_TP1
PRIMARY KEY (ID)
/
