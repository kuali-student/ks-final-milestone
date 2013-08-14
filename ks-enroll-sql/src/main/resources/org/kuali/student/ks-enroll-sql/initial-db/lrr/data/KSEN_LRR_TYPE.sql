TRUNCATE TABLE KSEN_LRR_TYPE DROP STORAGE
/
INSERT INTO KSEN_LRR_TYPE (NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES ('Credential Awarded','Indicates the credential that was awarded','kuali.lrr.type.credential.awarded ',0)
/
INSERT INTO KSEN_LRR_TYPE (NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES ('Credits Earned','Indicates the number of credits the student earned for successfully completing the course.  Often done via a calculation by looking at the calculated grade and either awarding the number of attempted credits if it is a passing grade.','kuali.lrr.type.credits.earned',0)
/
INSERT INTO KSEN_LRR_TYPE (NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES ('Administrative Final Grade','Administrative Grade that was recorded for this student','kuali.lrr.type.final.grade.administrative',0)
/
INSERT INTO KSEN_LRR_TYPE (NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES ('Assigned Final Grade','Grade as it was originally submitted by the faculty member on the grade sheet.','kuali.lrr.type.final.grade.assigned',0)
/
INSERT INTO KSEN_LRR_TYPE (NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES ('Calculated Final Grade','Grade calculated based on the scale or option chosen and the grade actually submitted by the faculty member.','kuali.lrr.type.final.grade.calculated',0)
/
INSERT INTO KSEN_LRR_TYPE (NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES ('Interim Grade','Grade assigned during at an interim period often used to flag students who might be struggling','kuali.lrr.type.interim.grade',0)
/
INSERT INTO KSEN_LRR_TYPE (NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES ('Standardized Test Ranking','The ranking of the student within the peer group who took the test during the same time period.  Often recorded as a percentage or a decile','kuali.lrr.type.standardized.test.ranking',0)
/
INSERT INTO KSEN_LRR_TYPE (NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES ('Standardized Test Score','A score a student got by taking standardized test.','kuali.lrr.type.standardized.test.score',0)
/
INSERT INTO KSEN_LRR_TYPE (NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES ('NA','Not defined yet.','result.source.type.notdefined.yet',1)
/
