insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
  values ('0f5badac-1fd9-4105-8f77-b258cce23408', 'cluCourseFormatsDurationType', 'en', 'course', 'Duration Type', '47F8296F-4AF6-44CD-AE4E-93ED817FAFFA', 1)
/
insert into KSMG_MESSAGE (ID, MSG_ID, LOCALE, GRP_NAME, MSG_VALUE, OBJ_ID, VER_NBR) 
  values ('ffd033f7-c27c-4458-a478-d02ce3b5e636', 'cluCourseFormatsDurationType-help', 'en', 'course', 'First select the duration type (term, month, week, weekend, day) then select the count of the duration for the course. Most courses will have 1 or 2 terms, for example.', '0F2734CD-CD5D-F883-58EF-3F53C57DEE51', 1)
/
update KSMG_MESSAGE set MSG_VALUE='First select the duration type (term, month, week, weekend, day) then select the count of the duration for the course. Most courses will have 1 or 2 terms, for example.' where MSG_ID='cluDurationLiteral-instruct'
/
update KSMG_MESSAGE set MSG_VALUE='Duration' where MSG_ID='cluDurationQuantity'
/