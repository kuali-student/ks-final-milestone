

-----------------------------------------------------------------------------
-- KSMG_MESSAGE
-----------------------------------------------------------------------------
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSMG_MESSAGE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSMG_MESSAGE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/

CREATE TABLE KSMG_MESSAGE
(
      ID VARCHAR2(255)
        , MSG_ID VARCHAR2(255)
        , LOCALE VARCHAR2(255)
        , GRP_NAME VARCHAR2(255)
        , MSG_VALUE VARCHAR2(2000)
        , OBJ_ID VARCHAR2(36)
        , VER_NBR NUMBER(19)
    

)
/

ALTER TABLE KSMG_MESSAGE
    ADD CONSTRAINT KSMG_MESSAGEP1
PRIMARY KEY (ID)
/



