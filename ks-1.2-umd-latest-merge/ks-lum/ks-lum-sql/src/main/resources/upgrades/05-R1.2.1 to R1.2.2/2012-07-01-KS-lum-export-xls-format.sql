-- KSLAB-2062
-- adds XLS as one of the possible export format
delete from KSMG_MESSAGE where MSG_ID = 'xlsFormat' 
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('644E21681A474D5BA3E829514EB91BDD', 'xlsFormat', 'en', 'common', '.xls (Microsoft Excel)', null, 1) 
/