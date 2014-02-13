INSERT INTO KRMS_TERM_SPEC_T (ACTV,DESC_TXT,NM,NMSPC_CD,TERM_SPEC_ID,TYP,VER_NBR)
  VALUES ('Y','Matching Timeslot','MatchingTimeSlot','KS-SYS','KS-KRMS-TERM-SPEC-10019','java.lang.Boolean',1)
/
INSERT INTO KRMS_TERM_SPEC_T (ACTV,DESC_TXT,NM,NMSPC_CD,TERM_SPEC_ID,TYP,VER_NBR)
  VALUES ('Y','Matching Course Code','MatchingCourse','KS-SYS','KS-KRMS-TERM-SPEC-10020','java.lang.Boolean',1)
/
INSERT INTO KRMS_TERM_SPEC_T (ACTV,DESC_TXT,NM,NMSPC_CD,TERM_SPEC_ID,TYP,VER_NBR)
  VALUES ('Y','Matching Course Set','MatchingCourseSet','KS-SYS','KS-KRMS-TERM-SPEC-10021','java.lang.Boolean',1)
/

Insert into KRMS_TERM_RSLVR_T (ACTV,NM,NMSPC_CD,OUTPUT_TERM_SPEC_ID,TERM_RSLVR_ID,TYP_ID,VER_NBR) values ('Y','MatchingTimeSlot','KS-SYS','KS-KRMS-TERM-SPEC-10019','KS-KRMS-TERM-RSLVR-10015',(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.termresolver.type.final.exam'),0)
/
Insert into KRMS_TERM_RSLVR_T (ACTV,NM,NMSPC_CD,OUTPUT_TERM_SPEC_ID,TERM_RSLVR_ID,TYP_ID,VER_NBR) values ('Y','MatchingCourse','KS-SYS','KS-KRMS-TERM-SPEC-10020','KS-KRMS-TERM-RSLVR-10016',(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.termresolver.type.final.exam'),0)
/
Insert into KRMS_TERM_RSLVR_T (ACTV,NM,NMSPC_CD,OUTPUT_TERM_SPEC_ID,TERM_RSLVR_ID,TYP_ID,VER_NBR) values ('Y','MatchingCourseSet','KS-SYS','KS-KRMS-TERM-SPEC-10021','KS-KRMS-TERM-RSLVR-10017',(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.termresolver.type.final.exam'),0)
/

--Day 1 8:00-10:00
Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Matching Timeslot Term','KS-KRMS-TERM-13422','KS-KRMS-TERM-SPEC-10019',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13422','KS-KRMS-TERM-PARM-13467','kuali.term.parameter.type.timeslot.weekday.string','TH',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13422','KS-KRMS-TERM-PARM-13468','kuali.term.parameter.type.timeslot.start','39600000',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13422','KS-KRMS-TERM-PARM-13469','kuali.term.parameter.type.timeslot.end','44100000',1)
/

--Day 3 10:30-12:30
Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Matching Course Set Term','KS-KRMS-TERM-13423','KS-KRMS-TERM-SPEC-10021',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13423','KS-KRMS-TERM-PARM-13470','kuali.term.parameter.type.course.cluSet.id','e5f1c380-04ef-c548-e040-007f0101104a',1)
/

--Day 3 18:30-20:30
Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Matching Course Term','KS-KRMS-TERM-13425','KS-KRMS-TERM-SPEC-10020',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13425','KS-KRMS-TERM-PARM-13472','kuali.term.parameter.type.course.clu.id','54414200-ea95-47dd-9718-1ec1d81f366b',1)
/

--Day 2 8:00-10:00
Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Matching Timeslot Term','KS-KRMS-TERM-13426','KS-KRMS-TERM-SPEC-10019',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13426','KS-KRMS-TERM-PARM-13473','kuali.term.parameter.type.timeslot.weekday.string','TH',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13426','KS-KRMS-TERM-PARM-13474','kuali.term.parameter.type.timeslot.start','34200000',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13426','KS-KRMS-TERM-PARM-13475','kuali.term.parameter.type.timeslot.end','38700000',1)
/

--Day 2 13:30-15:30
Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Matching Timeslot Term','KS-KRMS-TERM-13427','KS-KRMS-TERM-SPEC-10019',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13427','KS-KRMS-TERM-PARM-13476','kuali.term.parameter.type.timeslot.weekday.string','MWF',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13427','KS-KRMS-TERM-PARM-13477','kuali.term.parameter.type.timeslot.start','46800000',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13427','KS-KRMS-TERM-PARM-13478','kuali.term.parameter.type.timeslot.end','49800000',1)
/

--Day 4 8:00-10:00
Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Matching Timeslot Term','KS-KRMS-TERM-13428','KS-KRMS-TERM-SPEC-10019',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13428','KS-KRMS-TERM-PARM-13479','kuali.term.parameter.type.timeslot.weekday.string','MWF',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13428','KS-KRMS-TERM-PARM-13480','kuali.term.parameter.type.timeslot.start','32400000',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13428','KS-KRMS-TERM-PARM-13481','kuali.term.parameter.type.timeslot.end','35400000',1)
/

--Day 4 10:30-12:30
Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Matching Timeslot Term','KS-KRMS-TERM-13430','KS-KRMS-TERM-SPEC-10019',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13430','KS-KRMS-TERM-PARM-13483','kuali.term.parameter.type.timeslot.weekday.string','TH',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13430','KS-KRMS-TERM-PARM-13484','kuali.term.parameter.type.timeslot.start','50400000',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13430','KS-KRMS-TERM-PARM-13485','kuali.term.parameter.type.timeslot.end','54900000',1)
/

--Day 5 10:30-12:30
Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Matching Timeslot Term','KS-KRMS-TERM-13431','KS-KRMS-TERM-SPEC-10019',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13431','KS-KRMS-TERM-PARM-13486','kuali.term.parameter.type.timeslot.weekday.string','TH',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13431','KS-KRMS-TERM-PARM-13487','kuali.term.parameter.type.timeslot.start','28800000',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13431','KS-KRMS-TERM-PARM-13488','kuali.term.parameter.type.timeslot.end','33300000',1)
/

--Day 5 13:30-15:30
Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Matching Timeslot Term','KS-KRMS-TERM-13435','KS-KRMS-TERM-SPEC-10019',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13435','KS-KRMS-TERM-PARM-13492','kuali.term.parameter.type.timeslot.weekday.string','TH',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13435','KS-KRMS-TERM-PARM-13493','kuali.term.parameter.type.timeslot.start','45000000',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13435','KS-KRMS-TERM-PARM-13494','kuali.term.parameter.type.timeslot.end','49500000',1)
/

--Day 5 16:00-18:00
Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Matching Timeslot Term','KS-KRMS-TERM-13437','KS-KRMS-TERM-SPEC-10019',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13437','KS-KRMS-TERM-PARM-13496','kuali.term.parameter.type.timeslot.weekday.string','TH',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13437','KS-KRMS-TERM-PARM-13497','kuali.term.parameter.type.timeslot.start','61200000',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13437','KS-KRMS-TERM-PARM-13498','kuali.term.parameter.type.timeslot.end','65700000',1)
/

--Day 6 10:30-12:30
Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Matching Timeslot Term','KS-KRMS-TERM-13439','KS-KRMS-TERM-SPEC-10019',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13439','KS-KRMS-TERM-PARM-13500','kuali.term.parameter.type.timeslot.weekday.string','TH',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13439','KS-KRMS-TERM-PARM-13501','kuali.term.parameter.type.timeslot.start','55800000',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13439','KS-KRMS-TERM-PARM-13502','kuali.term.parameter.type.timeslot.end','60300000',1)
/

--Day 6 13:30-15:30
Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Matching Timeslot Term','KS-KRMS-TERM-13440','KS-KRMS-TERM-SPEC-10019',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13440','KS-KRMS-TERM-PARM-13503','kuali.term.parameter.type.timeslot.weekday.string','MWF',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13440','KS-KRMS-TERM-PARM-13504','kuali.term.parameter.type.timeslot.start','57600000',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13440','KS-KRMS-TERM-PARM-13505','kuali.term.parameter.type.timeslot.end','60600000',1)
/

--Day 4 16:00-18:00
Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Matching Timeslot Term','KS-KRMS-TERM-13443','KS-KRMS-TERM-SPEC-10019',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13443','KS-KRMS-TERM-PARM-13512','kuali.term.parameter.type.timeslot.weekday.string','MWF',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13443','KS-KRMS-TERM-PARM-13513','kuali.term.parameter.type.timeslot.start','61200000',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13443','KS-KRMS-TERM-PARM-13514','kuali.term.parameter.type.timeslot.end','64200000',1)
/
Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Matching Timeslot Term','KS-KRMS-TERM-13444','KS-KRMS-TERM-SPEC-10019',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13444','KS-KRMS-TERM-PARM-13515','kuali.term.parameter.type.timeslot.weekday.string','MW',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13444','KS-KRMS-TERM-PARM-13516','kuali.term.parameter.type.timeslot.start','61200000',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13444','KS-KRMS-TERM-PARM-13517','kuali.term.parameter.type.timeslot.end','64700000',1)
/

--Day 6 8:00-10:00
Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Matching Timeslot Term','KS-KRMS-TERM-13445','KS-KRMS-TERM-SPEC-10019',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13445','KS-KRMS-TERM-PARM-13518','kuali.term.parameter.type.timeslot.weekday.string','MWF',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13445','KS-KRMS-TERM-PARM-13519','kuali.term.parameter.type.timeslot.start','36000000',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13445','KS-KRMS-TERM-PARM-13520','kuali.term.parameter.type.timeslot.end','39000000',1)
/
Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Matching Timeslot Term','KS-KRMS-TERM-13446','KS-KRMS-TERM-SPEC-10019',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13446','KS-KRMS-TERM-PARM-13521','kuali.term.parameter.type.timeslot.weekday.string','MW',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13446','KS-KRMS-TERM-PARM-13522','kuali.term.parameter.type.timeslot.start','34200000',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13446','KS-KRMS-TERM-PARM-13523','kuali.term.parameter.type.timeslot.end','38700000',1)
/

--Day 5 8:00-10:00
Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Matching Timeslot Term','KS-KRMS-TERM-13449','KS-KRMS-TERM-SPEC-10019',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13449','KS-KRMS-TERM-PARM-13530','kuali.term.parameter.type.timeslot.weekday.string','MWF',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13449','KS-KRMS-TERM-PARM-13531','kuali.term.parameter.type.timeslot.start','43200000',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13449','KS-KRMS-TERM-PARM-13532','kuali.term.parameter.type.timeslot.end','46200000',1)
/
Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Matching Timeslot Term','KS-KRMS-TERM-13450','KS-KRMS-TERM-SPEC-10019',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13450','KS-KRMS-TERM-PARM-13533','kuali.term.parameter.type.timeslot.weekday.string','MW',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13450','KS-KRMS-TERM-PARM-13534','kuali.term.parameter.type.timeslot.start','45000000',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13450','KS-KRMS-TERM-PARM-13535','kuali.term.parameter.type.timeslot.end','49500000',1)
/

--Day 1 13:30-15:30
Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Matching Course Set Term','KS-KRMS-TERM-13429','KS-KRMS-TERM-SPEC-10021',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) 
  values ('KS-KRMS-TERM-13429','KS-KRMS-TERM-PARM-13482','kuali.term.parameter.type.course.cluSet.id','e5f1c380-04ex-c548-e040-007f0101104a',1)
/

--Day 1 16:00-18:00
Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Matching Course Set Term','KS-KRMS-TERM-13432','KS-KRMS-TERM-SPEC-10021',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) 
  values ('KS-KRMS-TERM-13432','KS-KRMS-TERM-PARM-13489','kuali.term.parameter.type.course.cluSet.id','e5f1c380-04ec-c548-e040-007f0101104a',1)
/

--Day 2 10:30-12:30
Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Matching Course Set Term','KS-KRMS-TERM-13433','KS-KRMS-TERM-SPEC-10021',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) 
  values ('KS-KRMS-TERM-13433','KS-KRMS-TERM-PARM-13490','kuali.term.parameter.type.course.cluSet.id','e5f1c380-04ey-c548-e040-007f0101104a',1)
/

--Day 2 16:00-18:00
Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Matching Course Set Term','KS-KRMS-TERM-13434','KS-KRMS-TERM-SPEC-10021',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) 
  values ('KS-KRMS-TERM-13434','KS-KRMS-TERM-PARM-13491','kuali.term.parameter.type.course.cluSet.id','e5f1c380-04ed-c548-e040-007f0101104a',1)
/

--Day 2 18:30-20:30
Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Matching Course Set Term','KS-KRMS-TERM-13436','KS-KRMS-TERM-SPEC-10021',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) 
  values ('KS-KRMS-TERM-13436','KS-KRMS-TERM-PARM-13495','kuali.term.parameter.type.course.cluSet.id','e5f1c380-04ee-c548-e040-007f0101104a',1)
/


--Day 3 16:00-18:00
Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Matching Course Set Term','KS-KRMS-TERM-13438','KS-KRMS-TERM-SPEC-10021',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) 
  values ('KS-KRMS-TERM-13438','KS-KRMS-TERM-PARM-13499','kuali.term.parameter.type.course.cluSet.id','e5f1c380-04f0-c548-e040-007f0101104a',1)
/

--Day 1 10:30-12:30
Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Matching Timeslot Term','KS-KRMS-TERM-13441','KS-KRMS-TERM-SPEC-10019',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13441','KS-KRMS-TERM-PARM-13506','kuali.term.parameter.type.timeslot.weekday.string','MWF',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13441','KS-KRMS-TERM-PARM-13507','kuali.term.parameter.type.timeslot.start','28800000',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13441','KS-KRMS-TERM-PARM-13508','kuali.term.parameter.type.timeslot.end','31800000',1)
/

Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Matching Timeslot Term','KS-KRMS-TERM-13442','KS-KRMS-TERM-SPEC-10019',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13442','KS-KRMS-TERM-PARM-13509','kuali.term.parameter.type.timeslot.weekday.string','MW',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13442','KS-KRMS-TERM-PARM-13510','kuali.term.parameter.type.timeslot.start','28800000',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13442','KS-KRMS-TERM-PARM-13511','kuali.term.parameter.type.timeslot.end','33300000',1)
/

--Day 3 08:00-10:00
Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Matching Timeslot Term','KS-KRMS-TERM-13447','KS-KRMS-TERM-SPEC-10019',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13447','KS-KRMS-TERM-PARM-13524','kuali.term.parameter.type.timeslot.weekday.string','MWF',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13447','KS-KRMS-TERM-PARM-13525','kuali.term.parameter.type.timeslot.start','39600000',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13447','KS-KRMS-TERM-PARM-13526','kuali.term.parameter.type.timeslot.end','42600000',1)
/

Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Matching Timeslot Term','KS-KRMS-TERM-13448','KS-KRMS-TERM-SPEC-10019',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13448','KS-KRMS-TERM-PARM-13527','kuali.term.parameter.type.timeslot.weekday.string','MW',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13448','KS-KRMS-TERM-PARM-13528','kuali.term.parameter.type.timeslot.start','39600000',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13448','KS-KRMS-TERM-PARM-13529','kuali.term.parameter.type.timeslot.end','44100000',1)
/

--Day 3 13:30-15:30
Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Matching Timeslot Term','KS-KRMS-TERM-13451','KS-KRMS-TERM-SPEC-10019',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13451','KS-KRMS-TERM-PARM-13536','kuali.term.parameter.type.timeslot.weekday.string','MWF',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13451','KS-KRMS-TERM-PARM-13537','kuali.term.parameter.type.timeslot.start','54000000',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13451','KS-KRMS-TERM-PARM-13538','kuali.term.parameter.type.timeslot.end','57000000',1)
/

Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Matching Timeslot Term','KS-KRMS-TERM-13452','KS-KRMS-TERM-SPEC-10019',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13452','KS-KRMS-TERM-PARM-13539','kuali.term.parameter.type.timeslot.weekday.string','MW',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13452','KS-KRMS-TERM-PARM-13540','kuali.term.parameter.type.timeslot.start','55800000',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13452','KS-KRMS-TERM-PARM-13541','kuali.term.parameter.type.timeslot.end','60300000',1)
/


--Day 4 13:30-15:30
Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Matching Timeslot Term','KS-KRMS-TERM-13453','KS-KRMS-TERM-SPEC-10019',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13453','KS-KRMS-TERM-PARM-13542','kuali.term.parameter.type.timeslot.weekday.string','MWF',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13453','KS-KRMS-TERM-PARM-13543','kuali.term.parameter.type.timeslot.start','50400000',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13453','KS-KRMS-TERM-PARM-13544','kuali.term.parameter.type.timeslot.end','53400000',1)
/

Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Matching Timeslot Term','KS-KRMS-TERM-13454','KS-KRMS-TERM-SPEC-10019',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13454','KS-KRMS-TERM-PARM-13545','kuali.term.parameter.type.timeslot.weekday.string','MW',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13454','KS-KRMS-TERM-PARM-13546','kuali.term.parameter.type.timeslot.start','50400000',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13454','KS-KRMS-TERM-PARM-13547','kuali.term.parameter.type.timeslot.end','54900000',1)
/

--Day 6 16:00-18:00
Insert into KRMS_TERM_T (DESC_TXT,TERM_ID,TERM_SPEC_ID,VER_NBR) values ('Conflict Resolution Term','KS-KRMS-TERM-13489','10013',1)
/
Insert into KRMS_TERM_PARM_T (TERM_ID,TERM_PARM_ID,NM,VAL,VER_NBR) values ('KS-KRMS-TERM-13489','KS-KRMS-TERM-PARM-13630','kuali.term.parameter.type.free.text','Conflict Resolution',1)
/