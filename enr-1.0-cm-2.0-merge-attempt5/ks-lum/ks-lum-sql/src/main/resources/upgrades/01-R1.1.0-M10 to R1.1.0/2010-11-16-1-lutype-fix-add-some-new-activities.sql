update KSLU_LUTYPE set name = 'Web Lecture' where TYPE_KEY = 'kuali.lu.type.activity.WebLecture'
/
INSERT INTO KSLU_LUTYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Web Discuss',SYS_GUID (),'Web-based or technologically-mediated activities replacing standard discussion sections','kuali.lu.type.activity.WebDiscussion',0)
/
INSERT INTO KSLU_LUTYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Homework',SYS_GUID (),'Student''s doing homework, problem sets and reading assignments and writing','kuali.lu.type.activity.Homework',0)
/