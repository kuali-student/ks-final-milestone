

-----------------------------------------------------------------------------
-- KSSC_SUBJ_CD
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSSC_SUBJ_CD';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSSC_SUBJ_CD CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSSC_SUBJ_CD
(
      ID VARCHAR2(255)
        , CREATEID VARCHAR2(255)
        , CREATETIME TIMESTAMP
        , UPDATEID VARCHAR2(255)
        , UPDATETIME TIMESTAMP
        , VER_NBR NUMBER(19) NOT NULL
        , NAME VARCHAR2(255)
        , STATE VARCHAR2(255)
        , CD VARCHAR2(255)
        , TYPE VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)

    , CONSTRAINT KSSC_SUBJ_CD_IX1 UNIQUE (CD)

)
/

ALTER TABLE KSSC_SUBJ_CD
    ADD CONSTRAINT KSSC_SUBJ_CDP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSSC_SUBJ_CD_JN_ORG
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSSC_SUBJ_CD_JN_ORG';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSSC_SUBJ_CD_JN_ORG CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSSC_SUBJ_CD_JN_ORG
(
      ID VARCHAR2(255)
        , ORG_ID VARCHAR2(255)
        , SUBJ_CD_ID VARCHAR2(255)
        , VER_NBR NUMBER(19) NOT NULL
        , EFF_DT TIMESTAMP
        , EXPIR_DT TIMESTAMP
        , OBJ_ID VARCHAR2(36)


)
/

ALTER TABLE KSSC_SUBJ_CD_JN_ORG
    ADD CONSTRAINT KSSC_SUBJ_CD_JN_ORGP1
PRIMARY KEY (ID)
/







-----------------------------------------------------------------------------
-- KSSC_SUBJ_CD_TYPE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSSC_SUBJ_CD_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSSC_SUBJ_CD_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSSC_SUBJ_CD_TYPE
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

ALTER TABLE KSSC_SUBJ_CD_TYPE
    ADD CONSTRAINT KSSC_SUBJ_CD_TYPEP1
PRIMARY KEY (TYPE_KEY)
/

