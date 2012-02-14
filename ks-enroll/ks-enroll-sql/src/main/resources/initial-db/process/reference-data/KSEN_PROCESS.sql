-- first insert the rich text 
insert into KSEN_PROCESS_RICH_TEXT (ID, OBJ_ID, VER_NBR,PLAIN,FORMATTED) values ('RTKEY.KUALI.PROCESS.REGISTRATION.BASIC.ELIGIBILITY',null,0,'The process of checking a student''s basic eligibility to register for courses.',null)
/
insert into KSEN_PROCESS_RICH_TEXT (ID, OBJ_ID, VER_NBR,PLAIN,FORMATTED) values ('RTKEY.KUALI.PROCESS.REGISTRATION.ELIGIBILITY.FOR.TERM',null,0,'The process of checking a student''s eligibility to register for a particular term.',null)
/
insert into KSEN_PROCESS_RICH_TEXT (ID, OBJ_ID, VER_NBR,PLAIN,FORMATTED) values ('RTKEY.KUALI.PROCESS.REGISTRATION.HOLDS.CLEARED',null,0,'The process of checking a student''s eligibility to register for a particular term.',null)
/
insert into KSEN_PROCESS_RICH_TEXT (ID, OBJ_ID, VER_NBR,PLAIN,FORMATTED) values ('RTKEY.KUALI.PROCESS.REGISTRATION.ELIGIBLE.FOR.COURSE',null,0,'The process of checking a student''s eligibility to register for a particular course.',null)
/
insert into KSEN_PROCESS_RICH_TEXT (ID, OBJ_ID, VER_NBR,PLAIN,FORMATTED) values ('RTKEY.KUALI.PROCESS.REGISTRATION.ELIGIBLE.FOR.COURSES',null,0,'The process of checking a student''s eligibility and ability to register for a proposed set of courses.',null)
/
insert into KSEN_PROCESS_RICH_TEXT (ID, OBJ_ID, VER_NBR,PLAIN,FORMATTED) values ('RTKEY.KUALI.PROCESS.REGISTRATION.ACKNOWLEDGEMENTS.CONFIRMED',null,0,'The process of checking a student''s eligibility to register for a particular term.',null)
/
insert into KSEN_PROCESS_RICH_TEXT (ID, OBJ_ID, VER_NBR,PLAIN,FORMATTED) values ('RTKEY.KUALI.PROCESS.REGISTRATION.REGISTER.FOR.COURSES',null,0,'The process of checking a student''s eligibility and actually register for a proposed set of courses.',null)
/
insert into KSEN_PROCESS_RICH_TEXT (ID, OBJ_ID, VER_NBR,PLAIN,FORMATTED) values ('RTKEY.KUALI.PROCESS.ACAD.RECORD.VIEW.GRADES',null,0,'The process of checking a student''s basic ability to view grades.',null)
/
insert into KSEN_PROCESS_RICH_TEXT (ID, OBJ_ID, VER_NBR,PLAIN,FORMATTED) values ('RTKEY.KUALI.PROCESS.ACAD.RECORD.VIEW.GRADES.FOR.TERM',null,0,'The process of checking a student''s basic ability to view grades for a particular term.',null)
/
insert into KSEN_PROCESS_RICH_TEXT (ID, OBJ_ID, VER_NBR,PLAIN,FORMATTED) values ('RTKEY.KUALI.PROCESS.ACAD.RECORD.VIEW.COURSE.GRADE',null,0,'The process of checking if a student can actually view a grade in a particular course',null)
/
-- then the main process table
insert into KSEN_PROCESS (ID, OBJ_ID, VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,NAME,RT_DESCR_ID,STATE_ID,TYPE_ID,OWNER_ORG_ID) values ('kuali.process.registration.basic.eligibility',null,0,'referencedataload', sysdate, 'referencedataload', sysdate,'Basic Eligibility', 'RTKEY.KUALI.PROCESS.REGISTRATION.BASIC.ELIGIBILITY', 'kuali.process.process.type', 'kuali.process.process.state.enabled', null)
/
insert into KSEN_PROCESS (ID, OBJ_ID, VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,NAME,RT_DESCR_ID,STATE_ID,TYPE_ID,OWNER_ORG_ID) values ('kuali.process.registration.eligibility.for.term',null,0,'referencedataload', sysdate, 'referencedataload', sysdate,'Eligibility for Term', 'RTKEY.KUALI.PROCESS.REGISTRATION.ELIGIBILITY.FOR.TERM', 'kuali.process.process.type', 'kuali.process.process.state.enabled', null)
/
insert into KSEN_PROCESS (ID, OBJ_ID, VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,NAME,RT_DESCR_ID,STATE_ID,TYPE_ID,OWNER_ORG_ID) values ('kuali.process.registration.holds.cleared',null,0,'referencedataload', sysdate, 'referencedataload', sysdate,'Holds Cleared', 'RTKEY.KUALI.PROCESS.REGISTRATION.HOLDS.CLEARED', 'kuali.process.process.type', 'kuali.process.process.state.enabled', null)
/
insert into KSEN_PROCESS (ID, OBJ_ID, VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,NAME,RT_DESCR_ID,STATE_ID,TYPE_ID,OWNER_ORG_ID) values ('kuali.process.registration.eligible.for.course',null,0,'referencedataload', sysdate, 'referencedataload', sysdate,'Eligible for Course', 'RTKEY.KUALI.PROCESS.REGISTRATION.ELIGIBLE.FOR.COURSE', 'kuali.process.process.type', 'kuali.process.process.state.enabled', null)
/
insert into KSEN_PROCESS (ID, OBJ_ID, VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,NAME,RT_DESCR_ID,STATE_ID,TYPE_ID,OWNER_ORG_ID) values ('kuali.process.registration.eligible.for.courses',null,0,'referencedataload', sysdate, 'referencedataload', sysdate,'Eligible for Courses', 'RTKEY.KUALI.PROCESS.REGISTRATION.ELIGIBLE.FOR.COURSES', 'kuali.process.process.type', 'kuali.process.process.state.enabled', null)
/
insert into KSEN_PROCESS (ID, OBJ_ID, VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,NAME,RT_DESCR_ID,STATE_ID,TYPE_ID,OWNER_ORG_ID) values ('kuali.process.registration.acknowledgements.confirmed',null,0,'referencedataload', sysdate, 'referencedataload', sysdate,'Acknowledgements Confirmed', 'RTKEY.KUALI.PROCESS.REGISTRATION.ACKNOWLEDGEMENTS.CONFIRMED', 'kuali.process.process.type', 'kuali.process.process.state.enabled', null)
/
insert into KSEN_PROCESS (ID, OBJ_ID, VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,NAME,RT_DESCR_ID,STATE_ID,TYPE_ID,OWNER_ORG_ID) values ('kuali.process.registration.register.for.courses',null,0,'referencedataload', sysdate, 'referencedataload', sysdate,'Register for Courses', 'RTKEY.KUALI.PROCESS.REGISTRATION.REGISTER.FOR.COURSES', 'kuali.process.process.type', 'kuali.process.process.state.enabled', null)
/
insert into KSEN_PROCESS (ID, OBJ_ID, VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,NAME,RT_DESCR_ID,STATE_ID,TYPE_ID,OWNER_ORG_ID) values ('kuali.process.acad.record.view.grades',null,0,'referencedataload', sysdate, 'referencedataload', sysdate,'View Grades', 'RTKEY.KUALI.PROCESS.ACAD.RECORD.VIEW.GRADES', 'kuali.process.process.type', 'kuali.process.process.state.enabled', null)
/
insert into KSEN_PROCESS (ID, OBJ_ID, VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,NAME,RT_DESCR_ID,STATE_ID,TYPE_ID,OWNER_ORG_ID) values ('kuali.process.acad.record.view.grades.for.term',null,0,'referencedataload', sysdate, 'referencedataload', sysdate,'View Grades for Term', 'RTKEY.KUALI.PROCESS.ACAD.RECORD.VIEW.GRADES.FOR.TERM', 'kuali.process.process.type', 'kuali.process.process.state.enabled', null)
/
insert into KSEN_PROCESS (ID, OBJ_ID, VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,NAME,RT_DESCR_ID,STATE_ID,TYPE_ID,OWNER_ORG_ID) values ('kuali.process.acad.record.view.course.grade',null,0,'referencedataload', sysdate, 'referencedataload', sysdate,'View Course Grade', 'RTKEY.KUALI.PROCESS.ACAD.RECORD.VIEW.COURSE.GRADE', 'kuali.process.process.type', 'kuali.process.process.state.enabled', null)
/