-- rich text first
insert into KSEN_CHECK_RICH_TEXT (ID, OBJ_ID, VER_NBR,PLAIN,FORMATTED) values ('RTKEY.KUALI.CHECK.IS.ALIVE',null,0,'Checks if student is actually alive',null)
/
insert into KSEN_CHECK_RICH_TEXT (ID, OBJ_ID, VER_NBR,PLAIN,FORMATTED) values ('RTKEY.KUALI.CHECK.HAS.OVERDUE.LIBRARY.BOOK',null,0,'Checks if student has an overdue library book',null)
/
insert into KSEN_CHECK_RICH_TEXT (ID, OBJ_ID, VER_NBR,PLAIN,FORMATTED) values ('RTKEY.KUALI.CHECK.HAS.NOT.PAID.BILL.FROM.PRIOR.TERM',null,0,'Checks if student has an unpaid bill from a prior term',null)
/
insert into KSEN_CHECK_RICH_TEXT (ID, OBJ_ID, VER_NBR,PLAIN,FORMATTED) values ('RTKEY.KUALI.CHECK.STUDENT.HAS.BASIC.ELIGIBILITY',null,0,'Checks all the checks defined in the basic eligibility process',null)
/
insert into KSEN_CHECK_RICH_TEXT (ID, OBJ_ID, VER_NBR,PLAIN,FORMATTED) values ('RTKEY.KUALI.CHECK.REGISTRATION.PERIOD.IS.OPEN',null,0,'Checks that the registration period is open',null)
/
insert into KSEN_CHECK_RICH_TEXT (ID, OBJ_ID, VER_NBR,PLAIN,FORMATTED) values ('RTKEY.KUALI.CHECK.REGISTRATION.PERIOD.IS.NOT.CLOSED',null,0,'Checks that the registration period is not yet closed',null)
/
insert into KSEN_CHECK_RICH_TEXT (ID, OBJ_ID, VER_NBR,PLAIN,FORMATTED) values ('RTKEY.KUALI.CHECK.REGISTRATION.HOLDS.CLEARED',null,0,'Checks that the checks in the registration holds process',null)
/
insert into KSEN_CHECK_RICH_TEXT (ID, OBJ_ID, VER_NBR,PLAIN,FORMATTED) values ('RTKEY.KUALI.CHECK.IS.NOT.SUMMER.TERM',null,0,'Checks that this is not the summer term',null)
/
insert into KSEN_CHECK_RICH_TEXT (ID, OBJ_ID, VER_NBR,PLAIN,FORMATTED) values ('RTKEY.KUALI.CHECK.ELIGIBILITY.FOR.TERM',null,0,'Checks all the checks that the student is eligible for the term',null)
/
insert into KSEN_CHECK_RICH_TEXT (ID, OBJ_ID, VER_NBR,PLAIN,FORMATTED) values ('RTKEY.KUALI.CHECK.HAS.THE.NECESSARY.PREREQ',null,0,'Checks that the student has all the necessary pee-requisites to take the course',null)
/
insert into KSEN_CHECK_RICH_TEXT (ID, OBJ_ID, VER_NBR,PLAIN,FORMATTED) values ('RTKEY.KUALI.CHECK.STUDENT.HAS.ELIGIBILITY.FOR.EACH.COURSE',null,0,'Checks all the checks that make sure the student is eligible for a particular course but does it for all the courses in the proposed set of courses',null)
/
insert into KSEN_CHECK_RICH_TEXT (ID, OBJ_ID, VER_NBR,PLAIN,FORMATTED) values ('RTKEY.KUALI.CHECK.DOES.NOT.EXCEED.CREDIT.LIMIT',null,0,'Checks that the student has not exceeded her credit limit',null)
/
-- then the main check table
INSERT INTO KSEN_CHECK (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, NAME, RT_DESCR_ID, STATE_ID, TYPE_ID, ISSUE_ID, MILESTONE_TYPE_ID, AGENDA_ID, PROCESS_ID) values ('kuali.check.is.alive', null,0,'referencedataload',sysdate,'referencedataload',sysdate,'is alive','RTKEY.KUALI.CHECK.IS.ALIVE','kuali.process.check.state.enabled','kuali.process.check.type.rule.direct', '','','kuali.agenda.is.alive','')
/
INSERT INTO KSEN_CHECK (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, NAME, RT_DESCR_ID, STATE_ID, TYPE_ID, ISSUE_ID, MILESTONE_TYPE_ID, AGENDA_ID, PROCESS_ID) values ('kuali.check.has.overdue.library.book', null,0,'referencedataload',sysdate,'referencedataload',sysdate,'has overdue library book','RTKEY.KUALI.CHECK.HAS.OVERDUE.LIBRARY.BOOK','kuali.process.check.state.enabled','kuali.process.check.type.hold', 'kuali.hold.issue.library.book.overdue','','','')
/
INSERT INTO KSEN_CHECK (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, NAME, RT_DESCR_ID, STATE_ID, TYPE_ID, ISSUE_ID, MILESTONE_TYPE_ID, AGENDA_ID, PROCESS_ID) values ('kuali.check.has.not.paid.bill.from.prior.term', null,0,'referencedataload',sysdate,'referencedataload',sysdate,'has not paid bill from prior term','RTKEY.KUALI.CHECK.HAS.NOT.PAID.BILL.FROM.PRIOR.TERM','kuali.process.check.state.enabled','kuali.process.check.type.hold', 'kuali.hold.issue.financial.unpaid.tuition.prior.term','','','')
/
INSERT INTO KSEN_CHECK (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, NAME, RT_DESCR_ID, STATE_ID, TYPE_ID, ISSUE_ID, MILESTONE_TYPE_ID, AGENDA_ID, PROCESS_ID) values ('kuali.check.student.has.basic.eligibility', null,0,'referencedataload',sysdate,'referencedataload',sysdate,'student has basic eligibility','RTKEY.KUALI.CHECK.STUDENT.HAS.BASIC.ELIGIBILITY','kuali.process.check.state.enabled','kuali.process.check.type.process', '','','','kuali.process.registration.basic.eligibility')
/
INSERT INTO KSEN_CHECK (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, NAME, RT_DESCR_ID, STATE_ID, TYPE_ID, ISSUE_ID, MILESTONE_TYPE_ID, AGENDA_ID, PROCESS_ID) values ('kuali.check.registration.period.is.open', null,0,'referencedataload',sysdate,'referencedataload',sysdate,'registration period is open','RTKEY.KUALI.CHECK.REGISTRATION.PERIOD.IS.OPEN','kuali.process.check.state.enabled','kuali.process.check.type.milestone.startdate', '','kuali.atp.milestone.RegistrationPeriod','','')
/
INSERT INTO KSEN_CHECK (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, NAME, RT_DESCR_ID, STATE_ID, TYPE_ID, ISSUE_ID, MILESTONE_TYPE_ID, AGENDA_ID, PROCESS_ID) values ('kuali.check.registration.period.is.not.closed', null,0,'referencedataload',sysdate,'referencedataload',sysdate,'registration period is not closed','RTKEY.KUALI.CHECK.REGISTRATION.PERIOD.IS.NOT.CLOSED','kuali.process.check.state.enabled','kuali.process.check.type.milestone.deadline', '','kuali.atp.milestone.RegistrationPeriod','','')
/
INSERT INTO KSEN_CHECK (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, NAME, RT_DESCR_ID, STATE_ID, TYPE_ID, ISSUE_ID, MILESTONE_TYPE_ID, AGENDA_ID, PROCESS_ID) values ('kuali.check.registration.holds.cleared', null,0,'referencedataload',sysdate,'referencedataload',sysdate,'Registration Holds Cleared','RTKEY.KUALI.CHECK.REGISTRATION.HOLDS.CLEARED','kuali.process.check.state.enabled','kuali.process.check.type.process', '','','','kuali.process.registration.holds.cleared')
/
INSERT INTO KSEN_CHECK (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, NAME, RT_DESCR_ID, STATE_ID, TYPE_ID, ISSUE_ID, MILESTONE_TYPE_ID, AGENDA_ID, PROCESS_ID) values ('kuali.check.is.not.summer.term', null,0,'referencedataload',sysdate,'referencedataload',sysdate,'is not Summer Term','RTKEY.KUALI.CHECK.IS.NOT.SUMMER.TERM','kuali.process.check.state.enabled','kuali.process.check.type.rule.direct', '','','kuali.agenda.is.not.summer.term','')
/
INSERT INTO KSEN_CHECK (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, NAME, RT_DESCR_ID, STATE_ID, TYPE_ID, ISSUE_ID, MILESTONE_TYPE_ID, AGENDA_ID, PROCESS_ID) values ('kuali.check.eligibility.for.term', null,0,'referencedataload',sysdate,'referencedataload',sysdate,'Eligibility for Term','RTKEY.KUALI.CHECK.ELIGIBILITY.FOR.TERM','kuali.process.check.state.enabled','kuali.process.check.type.process', '','','','kuali.process.registration.eligibility.for.term')
/
INSERT INTO KSEN_CHECK (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, NAME, RT_DESCR_ID, STATE_ID, TYPE_ID, ISSUE_ID, MILESTONE_TYPE_ID, AGENDA_ID, PROCESS_ID) values ('kuali.check.has.the.necessary.prereq', null,0,'referencedataload',sysdate,'referencedataload',sysdate,'Has the necessary pre-req','RTKEY.KUALI.CHECK.HAS.THE.NECESSARY.PREREQ','kuali.process.check.state.enabled','kuali.process.check.type.rule.indirect', '','','','')
/
INSERT INTO KSEN_CHECK (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, NAME, RT_DESCR_ID, STATE_ID, TYPE_ID, ISSUE_ID, MILESTONE_TYPE_ID, AGENDA_ID, PROCESS_ID) values ('kuali.check.student.has.eligibility.for.each.course', null,0,'referencedataload',sysdate,'referencedataload',sysdate,'student has eligibility for each course','RTKEY.KUALI.CHECK.STUDENT.HAS.ELIGIBILITY.FOR.EACH.COURSE','kuali.process.check.state.enabled','kuali.process.check.type.process', '','','','kuali.process.registration.eligible.for.course')
/
INSERT INTO KSEN_CHECK (ID, OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, NAME, RT_DESCR_ID, STATE_ID, TYPE_ID, ISSUE_ID, MILESTONE_TYPE_ID, AGENDA_ID, PROCESS_ID) values ('kuali.check.does.not.exceed.credit.limit', null,0,'referencedataload',sysdate,'referencedataload',sysdate,'does not exceed credit limit','RTKEY.KUALI.CHECK.DOES.NOT.EXCEED.CREDIT.LIMIT','kuali.process.check.state.enabled','kuali.process.check.type.value.max', '','','','')
/