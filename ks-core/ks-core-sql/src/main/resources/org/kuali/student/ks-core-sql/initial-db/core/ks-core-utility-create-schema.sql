

-----------------------------------------------------------------------------
-- KSEN_CODE_GENERATOR_LOCKS
-- Used by CourseOfferingServiceImpl.generateActivityOfferingCode
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSEN_CODE_GENERATOR_LOCKS';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSEN_CODE_GENERATOR_LOCKS CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSEN_CODE_GENERATOR_LOCKS
(
      ID VARCHAR2(255)
        , OBJ_ID VARCHAR2(36)
        , CODE VARCHAR2(255) NOT NULL
        , UNIQUE_KEY VARCHAR2(255) NOT NULL
        , NAMESPACE VARCHAR2(255) NOT NULL

    , CONSTRAINT KSEN_CODE_GENERATOR_LOCKS_I1 UNIQUE (CODE, UNIQUE_KEY, NAMESPACE)

)
/

ALTER TABLE KSEN_CODE_GENERATOR_LOCKS
    ADD CONSTRAINT KSEN_CODE_GENERATOR_LOCKSP1
PRIMARY KEY (ID)
/



-----------------------------------------------------------------------------
-- KS_DB_VERSION
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KS_DB_VERSION';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KS_DB_VERSION CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KS_DB_VERSION
(
      VERSION VARCHAR2(255)
        , MODULE_NAME VARCHAR2(255)
        , UPGRADE_TIME TIMESTAMP default SYSDATE
        , BUILD_ID VARCHAR2(255)
        , BUILD_TIMESTAMP VARCHAR2(255)


)
/

