INSERT INTO KRMS_TERM_SPEC_T (ACTV,DESC_TXT,NM,NMSPC_CD,TERM_SPEC_ID,TYP,VER_NBR)
  VALUES ('Y','Matching Timeslot','MatchingTimeSlot','KS-SYS','KS-TERM-SPEC-10019','java.lang.Boolean',1)
/
INSERT INTO KRMS_TERM_SPEC_T (ACTV,DESC_TXT,NM,NMSPC_CD,TERM_SPEC_ID,TYP,VER_NBR)
  VALUES ('Y','Matching Course Code','MatchingCourse','KS-SYS','KS-TERM-SPEC-10020','java.lang.Boolean',1)
/
INSERT INTO KRMS_TERM_SPEC_T (ACTV,DESC_TXT,NM,NMSPC_CD,TERM_SPEC_ID,TYP,VER_NBR)
  VALUES ('Y','Matching Course Set','MatchingCourseSet','KS-SYS','KS-TERM-SPEC-10021','java.lang.Boolean',1)
/

--Terms
Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Matching Timeslot Term','KS-KRMS-TERM-13422','KS-TERM-SPEC-10019',1)
/
Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Matching Course Code Term','KS-KRMS-TERM-13423','KS-TERM-SPEC-10020',1)
/
Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Matching Course Set Term','KS-KRMS-TERM-13424','KS-TERM-SPEC-10021',1)
/

--Term parameters
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13422','KS-KRMS-TERM-PARM-13467','kuali.term.parameter.type.timeslot.weekday.string','MWF',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13422','KS-KRMS-TERM-PARM-13468','kuali.term.parameter.type.timeslot.start','1362117600000',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13422','KS-KRMS-TERM-PARM-13469','kuali.term.parameter.type.timeslot.end','1362125700000',1)
/

Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13423','KS-KRMS-TERM-PARM-13470','kuali.term.parameter.type.course.clu.id','0d216734-4d47-46cf-a240-f2bd56d3b8ae',1)
/

Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13424','KS-KRMS-TERM-PARM-13471','kuali.term.parameter.type.course.cluSet.id','c24cd9f9-0743-4450-b61e-06d408c7e8d0',1)
/