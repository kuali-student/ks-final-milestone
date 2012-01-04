-- first the instruction population relationship
insert into KSEN_INSTR_PRSN_RELTN (INSTR_ID, PERSON_ID) values ('KUALI.PROCESS.REGISTRATION.BASIC.ELIGIBILITY.1','kuali.population.everyone')
/
insert into KSEN_INSTR_PRSN_RELTN (INSTR_ID, PERSON_ID) values ('KUALI.PROCESS.REGISTRATION.HOLDS.CLEARED.3','kuali.population.everyone')
/
insert into KSEN_INSTR_PRSN_RELTN (INSTR_ID, PERSON_ID) values ('KUALI.PROCESS.REGISTRATION.HOLDS.CLEARED.5','kuali.population.everyone')
/
insert into KSEN_INSTR_PRSN_RELTN (INSTR_ID, PERSON_ID) values ('KUALI.PROCESS.REGISTRATION.ELIGIBILITY.FOR.TERM.1','kuali.population.everyone')
/
insert into KSEN_INSTR_PRSN_RELTN (INSTR_ID, PERSON_ID) values ('KUALI.PROCESS.REGISTRATION.ELIGIBILITY.FOR.TERM.3','kuali.population.everyone')
/
insert into KSEN_INSTR_PRSN_RELTN (INSTR_ID, PERSON_ID) values ('KUALI.PROCESS.REGISTRATION.ELIGIBILITY.FOR.TERM.4','kuali.population.everyone')
/
insert into KSEN_INSTR_PRSN_RELTN (INSTR_ID, PERSON_ID) values ('KUALI.PROCESS.REGISTRATION.ELIGIBILITY.FOR.TERM.5','kuali.population.everyone')
/
insert into KSEN_INSTR_PRSN_RELTN (INSTR_ID, PERSON_ID) values ('KUALI.PROCESS.REGISTRATION.ELIGIBILITY.FOR.TERM.9','kuali.population.summer.only.student')
/
insert into KSEN_INSTR_PRSN_RELTN (INSTR_ID, PERSON_ID) values ('KUALI.PROCESS.REGISTRATION.ELIGIBLE.FOR.COURSE.1','kuali.population.everyone')
/
insert into KSEN_INSTR_PRSN_RELTN (INSTR_ID, PERSON_ID) values ('KUALI.PROCESS.REGISTRATION.ELIGIBLE.FOR.COURSE.2','kuali.population.everyone')
/
insert into KSEN_INSTR_PRSN_RELTN (INSTR_ID, PERSON_ID) values ('KUALI.PROCESS.REGISTRATION.ELIGIBLE.FOR.COURSES.1','kuali.population.everyone')
/
insert into KSEN_INSTR_PRSN_RELTN (INSTR_ID, PERSON_ID) values ('KUALI.PROCESS.REGISTRATION.ELIGIBLE.FOR.COURSES.2','kuali.population.everyone')
/
-- then the instruction message/rich text 
insert into KSEN_INSTR_MESSAGE (ID, OBJ_ID, VER_NBR,PLAIN,FORMATTED) values ('RTKEY.KUALI.PROCESS.REGISTRATION.BASIC.ELIGIBILITY.1',null,0,'A key piece of data is wrong on your biographic record.  Please come to the Registrar''s office to clear it up.',null)
/
insert into KSEN_INSTR_MESSAGE (ID, OBJ_ID, VER_NBR,PLAIN,FORMATTED) values ('RTKEY.KUALI.PROCESS.REGISTRATION.HOLDS.CLEARED.3',null,0,'Please note: you have an overdue library book',null)
/
insert into KSEN_INSTR_MESSAGE (ID, OBJ_ID, VER_NBR,PLAIN,FORMATTED) values ('RTKEY.KUALI.PROCESS.REGISTRATION.HOLDS.CLEARED.5',null,0,'You have unpaid tuition charges from last term, please contact the bursars office to resolve this matter',null)
/
insert into KSEN_INSTR_MESSAGE (ID, OBJ_ID, VER_NBR,PLAIN,FORMATTED) values ('RTKEY.KUALI.PROCESS.REGISTRATION.ELIGIBILITY.FOR.TERM.1',null,0,'Something about you as a person or your relationship with this institution needs to be fixed',null)
/
insert into KSEN_INSTR_MESSAGE (ID, OBJ_ID, VER_NBR,PLAIN,FORMATTED) values ('RTKEY.KUALI.PROCESS.REGISTRATION.ELIGIBILITY.FOR.TERM.3',null,0,'Registration period for this term has not yet begun',null)
/
insert into KSEN_INSTR_MESSAGE (ID, OBJ_ID, VER_NBR,PLAIN,FORMATTED) values ('RTKEY.KUALI.PROCESS.REGISTRATION.ELIGIBILITY.FOR.TERM.4',null,0,'Registration period for this term is closed',null)
/
insert into KSEN_INSTR_MESSAGE (ID, OBJ_ID, VER_NBR,PLAIN,FORMATTED) values ('RTKEY.KUALI.PROCESS.REGISTRATION.ELIGIBILITY.FOR.TERM.5',null,0,'You have one or more holds that need to be cleared',null)
/
insert into KSEN_INSTR_MESSAGE (ID, OBJ_ID, VER_NBR,PLAIN,FORMATTED) values ('RTKEY.KUALI.PROCESS.REGISTRATION.ELIGIBILITY.FOR.TERM.9',null,0,'Summer only students cannot register for fall, winter or spring terms',null)
/
insert into KSEN_INSTR_MESSAGE (ID, OBJ_ID, VER_NBR,PLAIN,FORMATTED) values ('RTKEY.KUALI.PROCESS.REGISTRATION.ELIGIBLE.FOR.COURSE.1',null,0,'You are not eligible to register for the term',null)
/
insert into KSEN_INSTR_MESSAGE (ID, OBJ_ID, VER_NBR,PLAIN,FORMATTED) values ('RTKEY.KUALI.PROCESS.REGISTRATION.ELIGIBLE.FOR.COURSE.2',null,0,'You do not have the necessary pre-req for xxxxx',null)
/
insert into KSEN_INSTR_MESSAGE (ID, OBJ_ID, VER_NBR,PLAIN,FORMATTED) values ('RTKEY.KUALI.PROCESS.REGISTRATION.ELIGIBLE.FOR.COURSES.1',null,0,'You are not eligible for all courses',null)
/
insert into KSEN_INSTR_MESSAGE (ID, OBJ_ID, VER_NBR,PLAIN,FORMATTED) values ('RTKEY.KUALI.PROCESS.REGISTRATION.ELIGIBLE.FOR.COURSES.2',null,0,'You are exceeding your credit limit',null)
/
-- then the main instruction table
insert into KSEN_INSTR (ID, OBJ_ID, VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,STATE_ID,TYPE_ID,EFF_DT, EXPIR_DT, PROCESS_ID, CHECK_ID, MESSAGE, POSITION, IS_WARNING, CONTINUE_ON_FAIL, IS_EXEMPTABLE) values ('KUALI.PROCESS.REGISTRATION.BASIC.ELIGIBILITY.1',null,0,'referencedataload', sysdate, 'referencedataload', sysdate, 'kuali.process.instruction.state.enabled', 'kuali.process.instruction.type', to_date ('2011-01-01', 'YYYY-MM-DD'), null,'kuali.process.registration.basic.eligibility', 'kuali.check.is.alive', 'RTKEY.KUALI.PROCESS.REGISTRATION.BASIC.ELIGIBILITY.1', 1, 0, 0, 0)
/
insert into KSEN_INSTR (ID, OBJ_ID, VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,STATE_ID,TYPE_ID,EFF_DT, EXPIR_DT, PROCESS_ID, CHECK_ID, MESSAGE, POSITION, IS_WARNING, CONTINUE_ON_FAIL, IS_EXEMPTABLE) values ('KUALI.PROCESS.REGISTRATION.HOLDS.CLEARED.3',null,0,'referencedataload', sysdate, 'referencedataload', sysdate, 'kuali.process.instruction.state.enabled', 'kuali.process.instruction.type', to_date ('2011-01-01', 'YYYY-MM-DD'), null,'kuali.process.registration.holds.cleared', 'kuali.check.has.overdue.library.book', 'RTKEY.KUALI.PROCESS.REGISTRATION.HOLDS.CLEARED.3', 3, 1, 1, 1)
/
insert into KSEN_INSTR (ID, OBJ_ID, VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,STATE_ID,TYPE_ID,EFF_DT, EXPIR_DT, PROCESS_ID, CHECK_ID, MESSAGE, POSITION, IS_WARNING, CONTINUE_ON_FAIL, IS_EXEMPTABLE) values ('KUALI.PROCESS.REGISTRATION.HOLDS.CLEARED.5',null,0,'referencedataload', sysdate, 'referencedataload', sysdate, 'kuali.process.instruction.state.enabled', 'kuali.process.instruction.type', to_date ('2011-01-01', 'YYYY-MM-DD'), null,'kuali.process.registration.holds.cleared', 'kuali.check.has.not.paid.bill.from.prior.term', 'RTKEY.KUALI.PROCESS.REGISTRATION.HOLDS.CLEARED.5', 5, 0, 1, 1)
/
insert into KSEN_INSTR (ID, OBJ_ID, VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,STATE_ID,TYPE_ID,EFF_DT, EXPIR_DT, PROCESS_ID, CHECK_ID, MESSAGE, POSITION, IS_WARNING, CONTINUE_ON_FAIL, IS_EXEMPTABLE) values ('KUALI.PROCESS.REGISTRATION.ELIGIBILITY.FOR.TERM.1',null,0,'referencedataload', sysdate, 'referencedataload', sysdate, 'kuali.process.instruction.state.enabled', 'kuali.process.instruction.type', to_date ('2011-01-01', 'YYYY-MM-DD'), null,'kuali.process.registration.eligibility.for.term', 'kuali.check.student.has.basic.eligibility', 'RTKEY.KUALI.PROCESS.REGISTRATION.ELIGIBILITY.FOR.TERM.1', 1, 0, 0, 0)
/
insert into KSEN_INSTR (ID, OBJ_ID, VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,STATE_ID,TYPE_ID,EFF_DT, EXPIR_DT, PROCESS_ID, CHECK_ID, MESSAGE, POSITION, IS_WARNING, CONTINUE_ON_FAIL, IS_EXEMPTABLE) values ('KUALI.PROCESS.REGISTRATION.ELIGIBILITY.FOR.TERM.3',null,0,'referencedataload', sysdate, 'referencedataload', sysdate, 'kuali.process.instruction.state.enabled', 'kuali.process.instruction.type', to_date ('2011-01-01', 'YYYY-MM-DD'), null,'kuali.process.registration.eligibility.for.term', 'kuali.check.registration.period.is.open', 'RTKEY.KUALI.PROCESS.REGISTRATION.ELIGIBILITY.FOR.TERM.3', 3, 0, 1, 1)
/
insert into KSEN_INSTR (ID, OBJ_ID, VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,STATE_ID,TYPE_ID,EFF_DT, EXPIR_DT, PROCESS_ID, CHECK_ID, MESSAGE, POSITION, IS_WARNING, CONTINUE_ON_FAIL, IS_EXEMPTABLE) values ('KUALI.PROCESS.REGISTRATION.ELIGIBILITY.FOR.TERM.4',null,0,'referencedataload', sysdate, 'referencedataload', sysdate, 'kuali.process.instruction.state.enabled', 'kuali.process.instruction.type', to_date ('2011-01-01', 'YYYY-MM-DD'), null,'kuali.process.registration.eligibility.for.term', 'kuali.check.registration.period.is.not.closed', 'RTKEY.KUALI.PROCESS.REGISTRATION.ELIGIBILITY.FOR.TERM.4', 4, 0, 1, 1)
/
insert into KSEN_INSTR (ID, OBJ_ID, VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,STATE_ID,TYPE_ID,EFF_DT, EXPIR_DT, PROCESS_ID, CHECK_ID, MESSAGE, POSITION, IS_WARNING, CONTINUE_ON_FAIL, IS_EXEMPTABLE) values ('KUALI.PROCESS.REGISTRATION.ELIGIBILITY.FOR.TERM.5',null,0,'referencedataload', sysdate, 'referencedataload', sysdate, 'kuali.process.instruction.state.enabled', 'kuali.process.instruction.type', to_date ('2011-01-01', 'YYYY-MM-DD'), null,'kuali.process.registration.eligibility.for.term', 'kuali.check.registration.holds.cleared', 'RTKEY.KUALI.PROCESS.REGISTRATION.ELIGIBILITY.FOR.TERM.5', 5, 0, 1, 1)
/
insert into KSEN_INSTR (ID, OBJ_ID, VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,STATE_ID,TYPE_ID,EFF_DT, EXPIR_DT, PROCESS_ID, CHECK_ID, MESSAGE, POSITION, IS_WARNING, CONTINUE_ON_FAIL, IS_EXEMPTABLE) values ('KUALI.PROCESS.REGISTRATION.ELIGIBILITY.FOR.TERM.9',null,0,'referencedataload', sysdate, 'referencedataload', sysdate, 'kuali.process.instruction.state.enabled', 'kuali.process.instruction.type', to_date ('2011-01-01', 'YYYY-MM-DD'), null,'kuali.process.registration.eligibility.for.term', 'kuali.check.is.not.summer.term', 'RTKEY.KUALI.PROCESS.REGISTRATION.ELIGIBILITY.FOR.TERM.9', 9, 0, 1, 1)
/
insert into KSEN_INSTR (ID, OBJ_ID, VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,STATE_ID,TYPE_ID,EFF_DT, EXPIR_DT, PROCESS_ID, CHECK_ID, MESSAGE, POSITION, IS_WARNING, CONTINUE_ON_FAIL, IS_EXEMPTABLE) values ('KUALI.PROCESS.REGISTRATION.ELIGIBLE.FOR.COURSE.1',null,0,'referencedataload', sysdate, 'referencedataload', sysdate, 'kuali.process.instruction.state.enabled', 'kuali.process.instruction.type', to_date ('2011-01-01', 'YYYY-MM-DD'), null,'kuali.process.registration.eligible.for.course', 'kuali.check.eligibility.for.term', 'RTKEY.KUALI.PROCESS.REGISTRATION.ELIGIBLE.FOR.COURSE.1', 1, 0, 0, 1)
/
insert into KSEN_INSTR (ID, OBJ_ID, VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,STATE_ID,TYPE_ID,EFF_DT, EXPIR_DT, PROCESS_ID, CHECK_ID, MESSAGE, POSITION, IS_WARNING, CONTINUE_ON_FAIL, IS_EXEMPTABLE) values ('KUALI.PROCESS.REGISTRATION.ELIGIBLE.FOR.COURSE.2',null,0,'referencedataload', sysdate, 'referencedataload', sysdate, 'kuali.process.instruction.state.enabled', 'kuali.process.instruction.type', to_date ('2011-01-01', 'YYYY-MM-DD'), null,'kuali.process.registration.eligible.for.course', 'kuali.check.has.the.necessary.prereq', 'RTKEY.KUALI.PROCESS.REGISTRATION.ELIGIBLE.FOR.COURSE.2', 2, 0, 1, 1)
/
insert into KSEN_INSTR (ID, OBJ_ID, VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,STATE_ID,TYPE_ID,EFF_DT, EXPIR_DT, PROCESS_ID, CHECK_ID, MESSAGE, POSITION, IS_WARNING, CONTINUE_ON_FAIL, IS_EXEMPTABLE) values ('KUALI.PROCESS.REGISTRATION.ELIGIBLE.FOR.COURSES.1',null,0,'referencedataload', sysdate, 'referencedataload', sysdate, 'kuali.process.instruction.state.enabled', 'kuali.process.instruction.type', to_date ('2011-01-01', 'YYYY-MM-DD'), null,'kuali.process.registration.eligible.for.courses', 'kuali.check.student.has.eligibility.for.each.course', 'RTKEY.KUALI.PROCESS.REGISTRATION.ELIGIBLE.FOR.COURSES.1', 1, 0, 0, 1)
/
insert into KSEN_INSTR (ID, OBJ_ID, VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,STATE_ID,TYPE_ID,EFF_DT, EXPIR_DT, PROCESS_ID, CHECK_ID, MESSAGE, POSITION, IS_WARNING, CONTINUE_ON_FAIL, IS_EXEMPTABLE) values ('KUALI.PROCESS.REGISTRATION.ELIGIBLE.FOR.COURSES.2',null,0,'referencedataload', sysdate, 'referencedataload', sysdate, 'kuali.process.instruction.state.enabled', 'kuali.process.instruction.type', to_date ('2011-01-01', 'YYYY-MM-DD'), null,'kuali.process.registration.eligible.for.courses', 'kuali.check.does.not.exceed.credit.limit', 'RTKEY.KUALI.PROCESS.REGISTRATION.ELIGIBLE.FOR.COURSES.2', 2, 0, 0, 1)
/