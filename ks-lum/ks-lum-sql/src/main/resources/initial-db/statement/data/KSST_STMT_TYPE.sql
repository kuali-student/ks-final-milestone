TRUNCATE TABLE KSST_STMT_TYPE DROP STORAGE
/
INSERT INTO KSST_STMT_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Academic Readiness Rules','Rules used in the evaluation of a person''s academic readiness for enrollment.','kuali.statement.type.course',0)
/
INSERT INTO KSST_STMT_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Antirequisite','Add courses that, if completed, would prevent a student from enrolling in this course.','kuali.statement.type.course.academicReadiness.antireq',0)
/
INSERT INTO KSST_STMT_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Corequisite','Add conditions that will restrict student enrollment, addressing restrictions to majors, locations, credit level requirements, etc. or Add courses, with or without grade requirements, which a student must have completed in order to enroll.','kuali.statement.type.course.academicReadiness.coreq',0)
/
INSERT INTO KSST_STMT_TYPE (EFF_DT,NAME,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Prerequisites','kuali.statement.type.course.academicReadiness.prereq',0)
/
INSERT INTO KSST_STMT_TYPE (EFF_DT,NAME,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Student Eligibility','kuali.statement.type.course.academicReadiness.studentEligibility',0)
/
INSERT INTO KSST_STMT_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Student Eligibilities + Prerequisite','Add conditions that will restrict student enrollment, addressing restrictions to majors, locations, credit level requirements, etc. or Add courses, with or without grade requirements, which a student must have completed in order to enroll.','kuali.statement.type.course.academicReadiness.studentEligibilityPrereq',0)
/
INSERT INTO KSST_STMT_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Repeatable for Credit','Course repeatable for credit.','kuali.statement.type.course.credit.repeatable',0)
/
INSERT INTO KSST_STMT_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Courses that Restrict Credit','Enrollment in or completion of another course that will restrict the credits to be awarded.','kuali.statement.type.course.credit.restriction',0)
/
INSERT INTO KSST_STMT_TYPE (EFF_DT,NAME,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Credit Constraints','kuali.statement.type.course.creditConstraints',0)
/
INSERT INTO KSST_STMT_TYPE (EFF_DT,NAME,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Enrollment Elligibility','kuali.statement.type.course.enrollmentEligibility',0)
/
INSERT INTO KSST_STMT_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Recommended Preparation','The courses and/or preparation added here will not prevent students from registering, but will be printed in the catalog.','kuali.statement.type.course.recommendedPreparation',0)
/
INSERT INTO KSST_STMT_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Program Requirements List','List of program requirements (rules).','kuali.statement.type.program',0)
/
INSERT INTO KSST_STMT_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Completion Requirements','Add conditions that will define how students may successfully complete this program.','kuali.statement.type.program.completion',0)
/
INSERT INTO KSST_STMT_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Entrance Requirements','Add conditions that will restrict student entrance into this program.','kuali.statement.type.program.entrance',0)
/
INSERT INTO KSST_STMT_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Satisfactory Progress Requirements','Add conditions that will define how students will maintain satisfactory standing in this program.','kuali.statement.type.program.satisfactoryProgress',0)
/
