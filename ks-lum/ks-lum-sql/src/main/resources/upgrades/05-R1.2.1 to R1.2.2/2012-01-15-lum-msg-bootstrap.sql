-- Add message for person suggest on collaborator section view
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('D9E0819F1C514D03B80772D22E6E2D2C', 'collaboratorNameSuggest', 'en', 'course', 'Name', '7B5F42AD3B314226813877A185A2D5F0', 1)
/
-- Add message for reference data
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('d073bc38-8c4d-48b8-b240-35ecea572c79', 'crossListedAndJoints', 'en', 'course', 'Cross Listed and Jointly Offered Courses', '64fcbc4a-fe76-4ed7-b334-ff31ade5a87f', 1)
/
-- KSLAB-2062
-- adds XLS as one of the possible export format
delete from KSMG_MESSAGE where MSG_ID = 'xlsFormat' 
/
INSERT INTO KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
VALUES ('644E21681A474D5BA3E829514EB91BDD', 'xlsFormat', 'en', 'common', '.xls (Microsoft Excel)', null, 1) 
/