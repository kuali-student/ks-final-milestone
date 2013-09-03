INSERT INTO KRMS_TERM_SPEC_T (ACTV,DESC_TXT,NM,NMSPC_CD,TERM_SPEC_ID,TYP,VER_NBR)
  VALUES ('Y','Matching Timeslot','MatchingTimeSlot','KS-SYS','KS-TERM-SPEC-10019','java.lang.Boolean',1)
/
INSERT INTO KRMS_TERM_SPEC_T (ACTV,DESC_TXT,NM,NMSPC_CD,TERM_SPEC_ID,TYP,VER_NBR)
  VALUES ('Y','Matching Course Code','MatchingCourse','KS-SYS','KS-TERM-SPEC-10020','java.lang.Boolean',1)
/
INSERT INTO KRMS_TERM_SPEC_T (ACTV,DESC_TXT,NM,NMSPC_CD,TERM_SPEC_ID,TYP,VER_NBR)
  VALUES ('Y','Matching Course Set','MatchingCourseSet','KS-SYS','KS-TERM-SPEC-10021','java.lang.Boolean',1)
/

--Day 1 8:00-10:00
Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Matching Timeslot Term','KS-KRMS-TERM-13422','KS-TERM-SPEC-10019',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13422','KS-KRMS-TERM-PARM-13467','kuali.term.parameter.type.timeslot.weekday.string','TH',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13422','KS-KRMS-TERM-PARM-13468','kuali.term.parameter.type.timeslot.start','32400000',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13422','KS-KRMS-TERM-PARM-13469','kuali.term.parameter.type.timeslot.end','36900000',1)
/

--Day 3 10:30-12:30
Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Matching Course Set Term','KS-KRMS-TERM-13423','KS-TERM-SPEC-10021',1)
/
Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Matching Course Set Term','KS-KRMS-TERM-13424','KS-TERM-SPEC-10021',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13423','KS-KRMS-TERM-PARM-13470','kuali.term.parameter.type.course.cluSet.id','5a11018a-9aeb-4c87-9ee4-97a180d0c724',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13424','KS-KRMS-TERM-PARM-13471','kuali.term.parameter.type.course.cluSet.id','c24cd9f9-0743-4450-b61e-06d408c7e8d0',1)
/

--Day 3 18:30-20:30
Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Matching Course Term','KS-KRMS-TERM-13425','KS-TERM-SPEC-10020',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13425','KS-KRMS-TERM-PARM-13472','kuali.term.parameter.type.course.clu.id','54414200-ea95-47dd-9718-1ec1d81f366b',1)
/