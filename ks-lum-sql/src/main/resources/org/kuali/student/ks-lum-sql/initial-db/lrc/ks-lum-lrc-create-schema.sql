
-----------------------------------------------------------------------------
-- KSLR_RESCOMP
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLR_RESCOMP';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLR_RESCOMP CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLR_RESCOMP
(
      ID VARCHAR2(255)
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , VER_NBR NUMBER(19) NOT NULL
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , NAME VARCHAR2(255)
        , STATE VARCHAR2(255)
        , RT_DESCR_ID VARCHAR2(255)
        , TYPE VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
    

)
/

ALTER TABLE KSLR_RESCOMP
    ADD CONSTRAINT KSLR_RESCOMPP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLR_RESCOMP_I1 
  ON KSLR_RESCOMP 
  (TYPE)
/
CREATE INDEX KSLR_RESCOMP_I2 
  ON KSLR_RESCOMP 
  (RT_DESCR_ID)
/





-----------------------------------------------------------------------------
-- KSLR_RESCOMP_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLR_RESCOMP_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLR_RESCOMP_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLR_RESCOMP_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLR_RESCOMP_ATTR
    ADD CONSTRAINT KSLR_RESCOMP_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLR_RESCOMP_ATTR_I1 
  ON KSLR_RESCOMP_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSLR_RESCOMP_JN_RESVALUE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLR_RESCOMP_JN_RESVALUE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLR_RESCOMP_JN_RESVALUE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLR_RESCOMP_JN_RESVALUE
(
      COMPONENT_ID VARCHAR2(255) NOT NULL
        , RESULT_ID VARCHAR2(255) NOT NULL
    

)
/








-----------------------------------------------------------------------------
-- KSLR_RESCOMP_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLR_RESCOMP_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLR_RESCOMP_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLR_RESCOMP_TYPE
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

ALTER TABLE KSLR_RESCOMP_TYPE
    ADD CONSTRAINT KSLR_RESCOMP_TYPEP1
PRIMARY KEY (TYPE_KEY)
/







-----------------------------------------------------------------------------
-- KSLR_RESCOMP_TYPE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLR_RESCOMP_TYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLR_RESCOMP_TYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLR_RESCOMP_TYPE_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLR_RESCOMP_TYPE_ATTR
    ADD CONSTRAINT KSLR_RESCOMP_TYPE_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLR_RESCOMP_TYPE_ATTR_I1 
  ON KSLR_RESCOMP_TYPE_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSLR_RESULT_VALUE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLR_RESULT_VALUE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLR_RESULT_VALUE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLR_RESULT_VALUE
(
      ID VARCHAR2(255)
        , VALUE VARCHAR2(255)
        , RSLT_COMP_ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLR_RESULT_VALUE
    ADD CONSTRAINT KSLR_RESULT_VALUEP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLR_RESULT_VALUE_I1 
  ON KSLR_RESULT_VALUE 
  (RSLT_COMP_ID)
/





-----------------------------------------------------------------------------
-- KSLR_RICH_TEXT_T
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLR_RICH_TEXT_T';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLR_RICH_TEXT_T CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLR_RICH_TEXT_T
(
      ID VARCHAR2(255)
        , FORMATTED VARCHAR2(500)
        , PLAIN VARCHAR2(2000)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLR_RICH_TEXT_T
    ADD CONSTRAINT KSLR_RICH_TEXT_TP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLR_SCALE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLR_SCALE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLR_SCALE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLR_SCALE
(
      ID VARCHAR2(255)
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , VER_NBR NUMBER(19) NOT NULL
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , NAME VARCHAR2(255)
        , RT_DESCR_ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
    

)
/

ALTER TABLE KSLR_SCALE
    ADD CONSTRAINT KSLR_SCALEP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLR_SCALE_I1 
  ON KSLR_SCALE 
  (RT_DESCR_ID)
/





-----------------------------------------------------------------------------
-- KSLR_SCALE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLR_SCALE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLR_SCALE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLR_SCALE_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLR_SCALE_ATTR
    ADD CONSTRAINT KSLR_SCALE_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLR_SCALE_ATTR_I1 
  ON KSLR_SCALE_ATTR 
  (OWNER)
/
