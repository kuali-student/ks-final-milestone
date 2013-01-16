TRUNCATE TABLE KSEN_STATEPROCESS_RELTN DROP STORAGE
/
INSERT INTO KSEN_STATEPROCESS_RELTN (ID,NEXT_STATEKEY,PROCESS_KEY,VER_NBR)
  VALUES ('PROCESS-10','kuali.lui.state.draft','kuali.course.offering.lifecycle',0)
/
INSERT INTO KSEN_STATEPROCESS_RELTN (ID,NEXT_STATEKEY,PRIOR_STATEKEY,PROCESS_KEY,VER_NBR)
  VALUES ('PROCESS-11','kuali.lui.state.submitted','kuali.lui.state.draft','kuali.course.offering.lifecycle',0)
/
INSERT INTO KSEN_STATEPROCESS_RELTN (ID,NEXT_STATEKEY,PRIOR_STATEKEY,PROCESS_KEY,VER_NBR)
  VALUES ('PROCESS-12','kuali.lui.state.approved','kuali.lui.state.submitted','kuali.course.offering.lifecycle',0)
/
INSERT INTO KSEN_STATEPROCESS_RELTN (ID,NEXT_STATEKEY,PROCESS_KEY,VER_NBR)
  VALUES ('PROCESS-13','kuali.lui.lui.relation.state.active','kuali.lui.lui.relationship.lifecycle',0)
/
INSERT INTO KSEN_STATEPROCESS_RELTN (ID,NEXT_STATEKEY,PRIOR_STATEKEY,PROCESS_KEY,VER_NBR)
  VALUES ('PROCESS-14','kuali.lui.lui.relation.state.inactive','kuali.lui.lui.relation.state.active','kuali.lui.lui.relationship.lifecycle',0)
/
INSERT INTO KSEN_STATEPROCESS_RELTN (ID,NEXT_STATEKEY,PROCESS_KEY,VER_NBR)
  VALUES ('PROCESS-15','kuali.lpr.state.planned','kuali.lpr.process.student.course.registration',0)
/
INSERT INTO KSEN_STATEPROCESS_RELTN (ID,NEXT_STATEKEY,PROCESS_KEY,VER_NBR)
  VALUES ('PROCESS-3','kuali.hold.restriction.state.active','kuali.hold.restriction.process',0)
/
INSERT INTO KSEN_STATEPROCESS_RELTN (ID,NEXT_STATEKEY,PRIOR_STATEKEY,PROCESS_KEY,VER_NBR)
  VALUES ('PROCESS-4','kuali.hold.restriction.state.inactive','kuali.hold.restriction.state.active','kuali.hold.restriction.process',0)
/
INSERT INTO KSEN_STATEPROCESS_RELTN (ID,NEXT_STATEKEY,PROCESS_KEY,VER_NBR)
  VALUES ('PROCESS-5','kuali.hold.issue.state.active','kuali.hold.issue.process',0)
/
INSERT INTO KSEN_STATEPROCESS_RELTN (ID,NEXT_STATEKEY,PRIOR_STATEKEY,PROCESS_KEY,VER_NBR)
  VALUES ('PROCESS-6','kuali.hold.issue.state.inactive','kuali.hold.issue.state.active','kuali.hold.issue.process',0)
/
INSERT INTO KSEN_STATEPROCESS_RELTN (ID,NEXT_STATEKEY,PROCESS_KEY,VER_NBR)
  VALUES ('PROCESS-7','kuali.hold.state.active','kuali.hold.process.student',0)
/
INSERT INTO KSEN_STATEPROCESS_RELTN (ID,NEXT_STATEKEY,PRIOR_STATEKEY,PROCESS_KEY,VER_NBR)
  VALUES ('PROCESS-8','kuali.hold.state.released','kuali.hold.state.active','kuali.hold.process.student',0)
/
INSERT INTO KSEN_STATEPROCESS_RELTN (ID,NEXT_STATEKEY,PRIOR_STATEKEY,PROCESS_KEY,VER_NBR)
  VALUES ('PROCESS-9','kuali.hold.state.canceled','kuali.hold.state.released','kuali.hold.process.student',0)
/
