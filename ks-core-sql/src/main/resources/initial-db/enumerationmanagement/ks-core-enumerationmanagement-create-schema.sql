

-----------------------------------------------------------------------------
-- KSEM_CTX_JN_ENUM_VAL_T
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEM_CTX_JN_ENUM_VAL_T';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEM_CTX_JN_ENUM_VAL_T CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEM_CTX_JN_ENUM_VAL_T
(
      ENUM_VAL_ID VARCHAR2(255) NOT NULL
        , CTX_ID VARCHAR2(255) NOT NULL
    

)
/



CREATE INDEX KSEM_CTX_JN_ENUM_VAL_T_I1 
  ON KSEM_CTX_JN_ENUM_VAL_T 
  (CTX_ID)
/
CREATE INDEX KSEM_CTX_JN_ENUM_VAL_T_I2 
  ON KSEM_CTX_JN_ENUM_VAL_T 
  (ENUM_VAL_ID)
/





-----------------------------------------------------------------------------
-- KSEM_CTX_T
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEM_CTX_T';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEM_CTX_T CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEM_CTX_T
(
      ID VARCHAR2(255)
        , CTX_KEY VARCHAR2(255)
        , CTX_VAL VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    
    , CONSTRAINT SYS_C00285110 UNIQUE (CTX_KEY, CTX_VAL)

)
/

ALTER TABLE KSEM_CTX_T
    ADD CONSTRAINT KSEM_CTX_TP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSEM_ENUM_T
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEM_ENUM_T';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEM_ENUM_T CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEM_ENUM_T
(
      ENUM_KEY VARCHAR2(255)
        , DESCR VARCHAR2(255)
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , NAME VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSEM_ENUM_T
    ADD CONSTRAINT KSEM_ENUM_TP1
PRIMARY KEY (ENUM_KEY)
/







-----------------------------------------------------------------------------
-- KSEM_ENUM_VAL_T
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEM_ENUM_VAL_T';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEM_ENUM_VAL_T CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEM_ENUM_VAL_T
(
      ID VARCHAR2(255)
        , ABBREV_VAL VARCHAR2(255)
        , CD VARCHAR2(255)
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , SORT_KEY NUMBER(10)
        , VAL VARCHAR2(255)
        , ENUM_KEY VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSEM_ENUM_VAL_T
    ADD CONSTRAINT KSEM_ENUM_VAL_TP1
PRIMARY KEY (ID)
/


CREATE INDEX KSEM_ENUM_VAL_T_I1 
  ON KSEM_ENUM_VAL_T 
  (ENUM_KEY)
/

