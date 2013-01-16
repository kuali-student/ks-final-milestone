TRUNCATE TABLE KSST_REQ_COM_TYPE DROP STORAGE
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Completed course','Must have successfully completed <course>','kuali.reqComponent.type.course.completed',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'All of required courses','Must have successfully completed all courses from <courses>','kuali.reqComponent.type.course.courseset.completed.all',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Completed n courses','Must have successfully completed no more than <n> courses from <courses>','kuali.reqComponent.type.course.courseset.completed.max',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'N of required courses','Must have successfully completed a minimum of <n> courses from <courses>','kuali.reqComponent.type.course.courseset.completed.nof',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'None of required courses','Must not have successfully completed any courses from <courses>','kuali.reqComponent.type.course.courseset.completed.none',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Maximum credit from courses','Must successfully complete no more than  <n> credits from <courses>','kuali.reqComponent.type.course.courseset.credits.completed.max',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Completed N credits from courses','Must have successfully completed a minimum of <n> credits from <courses>','kuali.reqComponent.type.course.courseset.credits.completed.nof',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Completed none credits from courses','Must not have successfully completed any credits from <courses>','kuali.reqComponent.type.course.courseset.credits.completed.none',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Enrolled in all required courses','Must be concurrently enrolled in all courses from <courses>','kuali.reqComponent.type.course.courseset.enrolled.all',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Enrolled in N of required courses','Must be concurrently enrolled in a minimum of <n> courses from <courses>','kuali.reqComponent.type.course.courseset.enrolled.nof',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Minimum overall GPA','Must have earned a minimum GPA of <GPA> in <courses>','kuali.reqComponent.type.course.courseset.gpa.min',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Grade in required courses','Must not have earned a grade of <gradeType> <grade> or higher in <courses>','kuali.reqComponent.type.course.courseset.grade.max',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Grade in required courses','Must have earned a minimum grade of <gradeType> <grade> in <courses>','kuali.reqComponent.type.course.courseset.grade.min',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Minimum grade in required courses','Must successfully complete a minimum of <n> courses from <courses> with a minimum grade of <gradeType> <grade>','kuali.reqComponent.type.course.courseset.nof.grade.min',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,EXPIR_DT,NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20130110135505', 'YYYYMMDDHH24MISS' ),TO_DATE( '20130110135505', 'YYYYMMDDHH24MISS' ),'Earned minimum n credits','Must have earned a minimum of <n> total credits','kuali.reqComponent.type.course.credits.min')
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Course repeatable for maximum credit','May be repeated for a maximum of <n> credits','kuali.reqComponent.type.course.credits.repeat.max',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,EXPIR_DT,NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20130110135505', 'YYYYMMDDHH24MISS' ),TO_DATE( '20130110135505', 'YYYYMMDDHH24MISS' ),'Minimum cumulative GPA','Must have earned a minimum cumulative GPA of <GPA>','kuali.reqComponent.type.course.cumulative.gpa.min')
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Enrolled in course','Must be concurrently enrolled in <course> ','kuali.reqComponent.type.course.enrolled',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,EXPIR_DT,NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20130110135505', 'YYYYMMDDHH24MISS' ),TO_DATE( '20130110135505', 'YYYYMMDDHH24MISS' ),'Free Text','Free Form Text','kuali.reqComponent.type.course.freeform.text')
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Not completed course','Must not have successfully completed <course> ','kuali.reqComponent.type.course.notcompleted',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Minimum credit completion in organization/department','Must have successfully completed a minimum of <n> credits from courses in the <org>','kuali.reqComponent.type.course.org.credits.completed.min',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Program admission at campus','Must be admitted to any program offered at the course campus location','kuali.reqComponent.type.course.org.program.admitted',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Instructor permission required','Permission of instructor required','kuali.reqComponent.type.course.permission.instructor.required',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Organization permisison required','Permission of <administering org> required','kuali.reqComponent.type.course.permission.org.required',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Program admission restriction','Must have been admitted to the <program> program','kuali.reqComponent.type.course.program.admitted',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Program admission for courses in duration','Students admitted to <program> may take no more than <n> courses in the <org> in <duration> <durationType>','kuali.reqComponent.type.course.program.admitted.org.duration',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Program admission restriction','Must not have been admitted to the <program> program','kuali.reqComponent.type.course.program.notadmitted',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Program admission course restriction in duration','Students not admitted to <program> may take no more than <n> courses in the <org> in <duration> <durationType>','kuali.reqComponent.type.course.program.notadmitted.org.duration',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Maximum score on test','Must have achieved a score no higher than <score> on <test> ','kuali.reqComponent.type.course.test.score.max',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Minimum score on test','Must have achieved a minimum score of <score> on <tests>','kuali.reqComponent.type.course.test.score.min',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Earning n credits','Must be admitted to program prior to earning <n> credits','kuali.reqComponent.type.program.admitted.credits',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Candidate status within duration after program entry','Must attain candidate status within <n> <duration> after program entry term','kuali.reqComponent.type.program.candidate.status.duration',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Not exceed n duration without completing program','Must not exceed <n> <duration> without completing program','kuali.reqComponent.type.program.completion.duration',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Complete program within duration after program entry','Must complete program within <n> <durations> after program entry term','kuali.reqComponent.type.program.completion.duration.afterentry',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,EXPIR_DT,NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20130110135505', 'YYYYMMDDHH24MISS' ),TO_DATE( '20130110135505', 'YYYYMMDDHH24MISS' ),'Minimum advisor approved courses','Must have <n> courses from courses approved by advisor','kuali.reqComponent.type.program.courses.advisor')
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,EXPIR_DT,NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20130110135505', 'YYYYMMDDHH24MISS' ),TO_DATE( '20130110135505', 'YYYYMMDDHH24MISS' ),'Minimum theme approved courses','Student must successfully complete <n> courses from <theme>','kuali.reqComponent.type.program.courses.theme')
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,EXPIR_DT,NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20130110135505', 'YYYYMMDDHH24MISS' ),TO_DATE( '20130110135505', 'YYYYMMDDHH24MISS' ),'Minimum advisor approved credits','Must have <n> credits from courses approved by advisor','kuali.reqComponent.type.program.credits.advisor')
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Earned more than n credits','Must not have earned more than <n> credits','kuali.reqComponent.type.program.credits.max',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Earned minimum n credits','Must have earned a minimum of <n> total credits','kuali.reqComponent.type.program.credits.min',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,EXPIR_DT,NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20130110135505', 'YYYYMMDDHH24MISS' ),TO_DATE( '20130110135505', 'YYYYMMDDHH24MISS' ),'Minimum theme approved credits','Student must successfully complete <n> credits from <theme>','kuali.reqComponent.type.program.credits.theme')
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,EXPIR_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20371231000000', 'YYYYMMDDHH24MISS' ),'Minimum cumulative GPA','Must have earned a minimum cumulative GPA of <GPA>','kuali.reqComponent.type.program.cumulative.gpa.min',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,EXPIR_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20371231000000', 'YYYYMMDDHH24MISS' ),'Minimum duration GPA','Must have earned a minimum <duration> GPA of <GPA>','kuali.reqComponent.type.program.duration.gpa.min',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Program enrolled in graduation major for final credits','<program> student must be enrolled in their graduation major for the final <n> credits taken','kuali.reqComponent.type.program.enrolled.credits.final',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Admitted to minor program for junior or senior class standing','Must be admitted to a minor program only if they have junior or senior class standing','kuali.reqComponent.type.program.minor.admitted.classstanding',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Minors as part of a program','Must earn no more than <n> minors as part of a program','kuali.reqComponent.type.program.minors.nof',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'All of required programs','Must have successfully completed all of <programs>','kuali.reqComponent.type.program.programset.completed.all',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Minimum of n programs from programs','Must have successfully completed a minimum of <n> programs from <programs>','kuali.reqComponent.type.program.programset.completed.nof',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Minimum courses from programs','Must have successfully completed a minimum of <n> courses from <programs>','kuali.reqComponent.type.program.programset.coursecompleted.nof',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Not completed any programs','Must not have successfully completed any of <programs>','kuali.reqComponent.type.program.programset.notcompleted.nof',0)
/
INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Program final credits in residence','<program> students must take their final <n> credits in residence','kuali.reqComponent.type.program.residence.credits.final',0)
/
