update KSMG_MESSAGE set MSG_VALUE='Find Undergrad Programs' where MSG_ID='findMajors'
/
update KSMG_MESSAGE set MSG_VALUE='Find Core Programs' where MSG_ID='findCores'
/
update KSMG_MESSAGE set MSG_VALUE='Find Credential Programs' where MSG_ID='findCredentials'
/
update KSMG_MESSAGE set MSG_VALUE='Create a Course' where MSG_ID='createCourse'
/
update KSMG_MESSAGE set MSG_VALUE='Create...' where MSG_ID='create'
/
update KSMG_MESSAGE set MSG_VALUE='Find...' where MSG_ID='viewModify'
/
update KSMG_MESSAGE set MSG_VALUE='Learning Objectives' where MSG_ID='los'
/
update KSMG_MESSAGE set MSG_VALUE='Dependency Analysis' where MSG_ID='depAnalysis'
/
INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR,OBJ_ID)
  VALUES ('course','18DFA6DC-C32A-7E41-E410-1F6D67AA5367','en','courses','Courses','1','18DFA70B-F18D-4C57-81F4-E48321A944F7')
/
INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR,OBJ_ID)
  VALUES ('course','18DFA6EB-F391-6989-FD89-CFC618D1CE2B','en','programs','Programs','1','18DFA71A-C1C7-06CD-30AD-63E4CEE4C50E')
/
INSERT INTO KSMG_MESSAGE (GRP_NAME,ID,LOCALE,MSG_ID,MSG_VALUE,VER_NBR,OBJ_ID)
  VALUES ('course','18DFA6FB-BA47-27A4-1C65-E487E79760F1','en','noRecent','You have no recently viewed items.','1','18DFA72A-F4C5-9179-D146-2F09417E8F17')
/