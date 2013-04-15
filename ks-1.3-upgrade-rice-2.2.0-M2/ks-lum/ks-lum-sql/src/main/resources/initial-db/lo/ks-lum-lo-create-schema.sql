


-----------------------------------------------------------------------------
-- KSLO_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLO_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLO_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLO_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLO_ATTR
    ADD CONSTRAINT KSLO_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLO_ATTR_I1 
  ON KSLO_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSLO_LO
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLO_LO';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLO_LO CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLO_LO
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
        , ST VARCHAR2(255)
        , RT_DESCR_ID VARCHAR2(255)
        , LO_REPO_ID VARCHAR2(255)
        , LOTYPE_ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
    

)
/

ALTER TABLE KSLO_LO
    ADD CONSTRAINT KSLO_LOP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLO_LO_I1 
  ON KSLO_LO 
  (RT_DESCR_ID)
/
CREATE INDEX KSLO_LO_I2 
  ON KSLO_LO 
  (LOTYPE_ID)
/
CREATE INDEX KSLO_LO_I3 
  ON KSLO_LO 
  (LO_REPO_ID)
/





-----------------------------------------------------------------------------
-- KSLO_LO_ALLOWED_RELTN_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLO_LO_ALLOWED_RELTN_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLO_LO_ALLOWED_RELTN_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLO_LO_ALLOWED_RELTN_TYPE
(
      ID VARCHAR2(255)
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , VER_NBR NUMBER(19) NOT NULL
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , LO_TYPE_ID VARCHAR2(255)
        , LO_REL_TYPE_ID VARCHAR2(255)
        , LOLO_RELTN_TYPE_ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
    

)
/

ALTER TABLE KSLO_LO_ALLOWED_RELTN_TYPE
    ADD CONSTRAINT KSLO_LO_ALLOWED_RELTN_TYPEP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLO_LO_CATEGORY
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLO_LO_CATEGORY';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLO_LO_CATEGORY CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLO_LO_CATEGORY
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
        , LO_CATEGORY_TYPE_ID VARCHAR2(255)
        , LO_REPO_ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
    

)
/

ALTER TABLE KSLO_LO_CATEGORY
    ADD CONSTRAINT KSLO_LO_CATEGORYP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLO_LO_CATEGORY_I1 
  ON KSLO_LO_CATEGORY 
  (RT_DESCR_ID)
/
CREATE INDEX KSLO_LO_CATEGORY_I2 
  ON KSLO_LO_CATEGORY 
  (LO_REPO_ID)
/
CREATE INDEX KSLO_LO_CATEGORY_I3 
  ON KSLO_LO_CATEGORY 
  (LO_CATEGORY_TYPE_ID)
/





-----------------------------------------------------------------------------
-- KSLO_LO_CATEGORY_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLO_LO_CATEGORY_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLO_LO_CATEGORY_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLO_LO_CATEGORY_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLO_LO_CATEGORY_ATTR
    ADD CONSTRAINT KSLO_LO_CATEGORY_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLO_LO_CATEGORY_ATTR_I1 
  ON KSLO_LO_CATEGORY_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSLO_LO_CATEGORY_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLO_LO_CATEGORY_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLO_LO_CATEGORY_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLO_LO_CATEGORY_TYPE
(
      ID VARCHAR2(255)
        , DESCR VARCHAR2(255)
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , NAME VARCHAR2(255)
        , TYPE_KEY VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
        , TYPE_DESC VARCHAR2(2000)
    

)
/

ALTER TABLE KSLO_LO_CATEGORY_TYPE
    ADD CONSTRAINT KSLO_LO_CATEGORY_TYPEP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLO_LO_CATEGORY_TYPE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLO_LO_CATEGORY_TYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLO_LO_CATEGORY_TYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLO_LO_CATEGORY_TYPE_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLO_LO_CATEGORY_TYPE_ATTR
    ADD CONSTRAINT KSLO_LO_CATEGORY_TYPE_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLO_LO_CATEGORY_TYPE_ATTR_I1 
  ON KSLO_LO_CATEGORY_TYPE_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSLO_LO_JN_LOCATEGORY
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLO_LO_JN_LOCATEGORY';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLO_LO_JN_LOCATEGORY CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLO_LO_JN_LOCATEGORY
(
      ID VARCHAR2(255)
        , LO_ID VARCHAR2(255)
        , LOCATEGORY_ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    
    , CONSTRAINT SYS_C0011301 UNIQUE (LO_ID, LOCATEGORY_ID)

)
/

ALTER TABLE KSLO_LO_JN_LOCATEGORY
    ADD CONSTRAINT KSLO_LO_JN_LOCATEGORYP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLO_LO_JN_LOCATEGORY_I1 
  ON KSLO_LO_JN_LOCATEGORY 
  (LOCATEGORY_ID)
/
CREATE INDEX KSLO_LO_JN_LOCATEGORY_I2 
  ON KSLO_LO_JN_LOCATEGORY 
  (LO_ID)
/





-----------------------------------------------------------------------------
-- KSLO_LO_RELTN
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLO_LO_RELTN';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLO_LO_RELTN CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLO_LO_RELTN
(
      ID VARCHAR2(255)
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , VER_NBR NUMBER(19) NOT NULL
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , ST VARCHAR2(255)
        , LO_ID VARCHAR2(255)
        , LO_LO_RELATION_TYPE_ID VARCHAR2(255)
        , RELATED_LO_ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
    

)
/

ALTER TABLE KSLO_LO_RELTN
    ADD CONSTRAINT KSLO_LO_RELTNP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLO_LO_RELTN_I1 
  ON KSLO_LO_RELTN 
  (LO_LO_RELATION_TYPE_ID)
/
CREATE INDEX KSLO_LO_RELTN_I2 
  ON KSLO_LO_RELTN 
  (RELATED_LO_ID)
/
CREATE INDEX KSLO_LO_RELTN_I3 
  ON KSLO_LO_RELTN 
  (LO_ID)
/





-----------------------------------------------------------------------------
-- KSLO_LO_RELTN_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLO_LO_RELTN_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLO_LO_RELTN_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLO_LO_RELTN_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLO_LO_RELTN_ATTR
    ADD CONSTRAINT KSLO_LO_RELTN_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLO_LO_RELTN_ATTR_I1 
  ON KSLO_LO_RELTN_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSLO_LO_RELTN_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLO_LO_RELTN_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLO_LO_RELTN_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLO_LO_RELTN_TYPE
(
      ID VARCHAR2(255)
        , DESCR VARCHAR2(255)
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , NAME VARCHAR2(255)
        , REV_DESCR VARCHAR2(255)
        , REV_NAME VARCHAR2(255)
        , TYPE_KEY VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
        , TYPE_DESC VARCHAR2(2000)
    

)
/

ALTER TABLE KSLO_LO_RELTN_TYPE
    ADD CONSTRAINT KSLO_LO_RELTN_TYPEP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLO_LO_RELTN_TYPE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLO_LO_RELTN_TYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLO_LO_RELTN_TYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLO_LO_RELTN_TYPE_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLO_LO_RELTN_TYPE_ATTR
    ADD CONSTRAINT KSLO_LO_RELTN_TYPE_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLO_LO_RELTN_TYPE_ATTR_I1 
  ON KSLO_LO_RELTN_TYPE_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSLO_LO_REPOSITORY
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLO_LO_REPOSITORY';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLO_LO_REPOSITORY CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLO_LO_REPOSITORY
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
        , LO_ROOT_ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
    

)
/

ALTER TABLE KSLO_LO_REPOSITORY
    ADD CONSTRAINT KSLO_LO_REPOSITORYP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLO_LO_REPOSITORY_I1 
  ON KSLO_LO_REPOSITORY 
  (LO_ROOT_ID)
/
CREATE INDEX KSLO_LO_REPOSITORY_I2 
  ON KSLO_LO_REPOSITORY 
  (RT_DESCR_ID)
/





-----------------------------------------------------------------------------
-- KSLO_LO_REPOSITORY_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLO_LO_REPOSITORY_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLO_LO_REPOSITORY_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLO_LO_REPOSITORY_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLO_LO_REPOSITORY_ATTR
    ADD CONSTRAINT KSLO_LO_REPOSITORY_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLO_LO_REPOSITORY_ATTR_I1 
  ON KSLO_LO_REPOSITORY_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSLO_LO_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLO_LO_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLO_LO_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLO_LO_TYPE
(
      ID VARCHAR2(255)
        , DESCR VARCHAR2(255)
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , NAME VARCHAR2(255)
        , TYPE_KEY VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
        , TYPE_DESC VARCHAR2(2000)
    

)
/

ALTER TABLE KSLO_LO_TYPE
    ADD CONSTRAINT KSLO_LO_TYPEP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSLO_LO_TYPE_ATTR
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLO_LO_TYPE_ATTR';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLO_LO_TYPE_ATTR CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLO_LO_TYPE_ATTR
(
      ID VARCHAR2(255)
        , ATTR_NAME VARCHAR2(255)
        , ATTR_VALUE VARCHAR2(2000)
        , OWNER VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLO_LO_TYPE_ATTR
    ADD CONSTRAINT KSLO_LO_TYPE_ATTRP1
PRIMARY KEY (ID)
/


CREATE INDEX KSLO_LO_TYPE_ATTR_I1 
  ON KSLO_LO_TYPE_ATTR 
  (OWNER)
/





-----------------------------------------------------------------------------
-- KSLO_RICH_TEXT_T
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSLO_RICH_TEXT_T';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSLO_RICH_TEXT_T CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSLO_RICH_TEXT_T
(
      ID VARCHAR2(255)
        , FORMATTED VARCHAR2(500)
        , PLAIN VARCHAR2(2000)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSLO_RICH_TEXT_T
    ADD CONSTRAINT KSLO_RICH_TEXT_TP1
PRIMARY KEY (ID)
/




