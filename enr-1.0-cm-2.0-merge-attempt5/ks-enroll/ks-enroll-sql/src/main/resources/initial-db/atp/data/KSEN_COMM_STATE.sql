--  INSERTING into KSEN_COMM_STATE
Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.atp.state.Draft',null,0,null,null,null,null,'Indicates that this Time Period is just tentative',null,null,'Draft','kuali.atp.process')
/

Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.atp.state.Official',null,0,null,null,null,null,'Indicates that this Time Period has been established',null,null,'Official','kuali.atp.process')
/

Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.milestone.state.Draft',null,0,null,null,null,null,'Indicates that this milestone is just tentative',null,null,'Draft','kuali.milestone.process')
/

Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.milestone.state.Official',null,0,null,null,null,null,'Indicates that this milestone has been established',null,null,'Official','kuali.milestone.process')
/

Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.atp.atp.relation.state.active',null,0,null,null,null,null,'Indicates that this Atp-Atp relation is active',null,null,'Active','kuali.atp.atp.relation.process')
/

Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.atp.atp.relation.state.inactive',null,0,null,null,null,null,'Indicates that this Atp-Atp relation is inactive',null,null,'Inactive','kuali.atp.atp.relation.process')
/

Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.atp.milestone.relation.state.active',null,0,null,null,null,null,'Indicates that this Atp-Milestone relation is active',null,null,'Active','kuali.atp.milestone.relation.process')
/

Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.atp.milestone.relation.state.inactive',null,0,null,null,null,null,'Indicates that this Atp-Milestone relation is inactive',null,null,'Inactive','kuali.atp.milestone.relation.process')
/

Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.hold.restriction.state.active',null,0,null,null,null,null,'This restriction is active and should be enforced',null,null,'Active','kuali.hold.restriction.process')
/

Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.hold.restriction.state.inactive',null,0,null,null,null,null,'The restriction is inactive and should not be enforced',null,null,'Inactive','kuali.hold.restriction.process')
/

Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.hold.issue.state.active',null,0,null,null,null,null,'This issue is active and can be attached to holds ',null,null,'Active','kuali.hold.issue.process')
/

Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.hold.issue.state.inactive',null,0,null,null,null,null,'The hold is inactive an cannot be attached to new holds ',null,null,'Inactive','kuali.hold.issue.process')
/

Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.hold.state.active',null,0,null,null,null,null,'This hold is active and should be enforced',null,null,'Active','kuali.hold.process.student')
/

Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.hold.state.released',null,0,null,null,null,null,'The hold was active and the issue has been resolved',null,null,'Released','kuali.hold.process.student')
/

Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.hold.state.canceled',null,0,null,null,null,null,'The hold was canceled or removed because it was originally put on in error',null,null,'Canceled','kuali.hold.process.student')
/

Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.lui.state.draft',null,0,null,null,null,null,'Proposed for Departmental Planning purposed',null,null,'Draft','kuali.course.offering.lifecycle')
/

Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.lui.state.submitted',null,0,null,null,null,null,'Submitted to central admin for approval (or scheduling)',null,null,'Submitted','kuali.course.offering.lifecycle')
/

Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.lui.state.approved',null,0,null,null,null,null,'Approved and ready to be scheduled',null,null,'Approved','kuali.course.offering.lifecycle')
/

Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.lui.lui.relation.state.active',null,0,null,null,null,null,'The relationship between the two LUIs is active ',null,null,'Active','kuali.lui.lui.relationship.lifecycle')
/

Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.lui.lui.relation.state.inactive',null,0,null,null,null,null,'The relationship between the two LUIs is in-active ',null,null,'Inactive','kuali.lui.lui.relationship.lifecycle')
/

Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.lui.state.scheduled',null,0,null,null,null,null,'Approved and ready to be scheduled',null,null,'Scheduled','kuali.course.offering.lifecycle')
/
Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.lui.state.deleted',null,0,null,null,null,null,'Logically deleted before ever having been offered',null,null,'Deleted','kuali.course.offering.lifecycle')
/
Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.lui.state.offered',null,0,null,null,null,null,'Offered so it shows up in list of classes so student may register for the course ',null,null,'Offered','kuali.course.offering.lifecycle')
/
Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.lui.state.suspended',null,0,null,null,null,null,'Suspends registration in the course but allows students who had already registered to stay in the course',null,null,'Suspended','kuali.course.offering.lifecycle')
/
Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.lui.state.canceled',null,0,null,null,null,null,'Once offered but now canceled',null,null,'Canceled','kuali.course.offering.lifecycle')
/
Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.lpr.state.planned',null,0,null,null,null,null,'The student plans on taking this course or program',null,null,'Planned','kuali.lpr.process.student.course.registration')
/
Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.lpr.state.registered',null,0,null,null,null,null,'The student is officially registered for the course or section',null,null,'Registered','kuali.lpr.process.student.course.registration')
/
Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.lpr.state.not.paid',null,0,null,null,null,null,'The student has registered for the course by has not paid the fee',null,null,'Fee Not Paid','kuali.lpr.process.student.course.registration')
/
Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.lpr.state.waitlisted',null,0,null,null,null,null,'The student attempted to join but has been put on the waitlist',null,null,'Waitlisted','kuali.lpr.process.student.course.registration')
/
Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.lpr.state.dropped.early',null,0,null,null,null,null,'The student was registered but subsequently dropped the course or section within the normally allotted time period',null,null,'Dropped','kuali.lpr.process.student.course.registration')
/
Insert into KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.lpr.state.dropped.late',null,0,null,null,null,null,'The student was registered but subsequently dropped the course or section past the normally allotted time period,typically resulting in a special grade or mark to so indicate',null,null,'Dropped Late','kuali.lpr.process.student.course.registration')
/
INSERT INTO KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) values ('kuali.lpr.state.assigned',null,0,null,null,null,null, 'The instructor is assigned to teach this course or section.', null, null, 'Assigned', 'kuali.lpr.process.instructor.course.assignment')
/
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.lpr.trans.item.state.new', 'New', 'kuali.lpr.process.student.course.registration', 'The student is attemptng to enroll in this course or section.', 0)
/
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.lpr.trans.registered', 'Active', 'kuali.hold.process.student', 'This hold is active and should be enforced', 0)
/
INSERT INTO KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) VALUES ('kuali.lpr.roster.state.ready',null,0,null,null,null,null,'Active Roster entry',null,null,'Assigned','kuali.lpr.roster.process.course.grading')
/
INSERT INTO KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) VALUES ('kuali.lpr.roster.state.saved',null,0,null,null,null,null,'The roster has had information entered against it and it has been saved',null,null,'Saved','kuali.lpr.roster.process.course.grading')
/
INSERT INTO KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) VALUES ('kuali.lpr.roster.state.submitted',null,0,null,null,null,null,'The roster has has been submitted',null,null,'Submitted','kuali.lpr.roster.process.course.grading')
/
INSERT INTO KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) VALUES ('kuali.roster.entry.state.active',null,0,null,null,null,null,'The roster has been created and is ready to have grades entered',null,null,'Ready','kuali.lpr.roster.process.course.grading')
/
INSERT INTO KSEN_COMM_STATE (ID, NAME, DESCR, PROCESS_KEY, VER_NBR) VALUES ('kuali.lpr.roster.state.created', 'New roster entry', 'New roster entry', 'kuali.lpr.roster.process.course.grading', 0)
/
INSERT INTO KSEN_COMM_STATE (ID, NAME, DESCR, PROCESS_KEY, VER_NBR) VALUES ('kuali.lpr.trans.item.state.succeeded', 'Transaction Item Succeeded', 'Transaction Item Succeeded', 'kuali.lpr.trans.item.process', 0)
/
INSERT INTO KSEN_COMM_STATE (ID, NAME, DESCR, PROCESS_KEY, VER_NBR) VALUES ('kuali.lpr.trans.item.state.failed', 'Transaction Item Failed', 'Transaction Item failed', 'kuali.lpr.trans.item.process', 0)
/
INSERT INTO KSEN_COMM_STATE (ID,DESCR,NAME,PROCESS_KEY) VALUES ('kuali.result.values.group.state.draft','Draft','The result is just draft and cannot yet be used','kuali.result.values.group.process')
/
INSERT INTO KSEN_COMM_STATE (ID,DESCR,NAME,PROCESS_KEY) VALUES ('kuali.result.values.group.state.approved','Approved','The result has been approved to be used and awarded','kuali.result.values.group.process')
/
INSERT INTO KSEN_COMM_STATE (ID,DESCR,NAME,PROCESS_KEY) VALUES ('kuali.result.values.group.state.retired','The result has been retired and can still exist on records but can no longer be used','Retired','kuali.result.values.group.process')
/
INSERT INTO KSEN_COMM_STATE (ID,DESCR,NAME,PROCESS_KEY) VALUES ('kuali.result.scale.state.draft ','Draft','The result scale is just draft and cannot yet be used','kuali.result.scale.process')
/
INSERT INTO KSEN_COMM_STATE (ID,DESCR,NAME,PROCESS_KEY) VALUES ('kuali.result.scale.state.approved','Approved','The result scale has been approved to be used and awarded','kuali.result.scale.process')
/
INSERT INTO KSEN_COMM_STATE (ID,DESCR,NAME,PROCESS_KEY) VALUES ('kuali.result.scale.state.retired','Retired','The result scale has been retired and can still exist on records but can no longer be used','kuali.result.scale.process')
/
INSERT INTO KSEN_COMM_STATE (ID,DESCR,NAME,PROCESS_KEY) VALUES ('kuali.result.value.state.draft ','Draft','The result value is just draft and cannot yet be used','kuali.result.value.process')
/
INSERT INTO KSEN_COMM_STATE (ID,DESCR,NAME,PROCESS_KEY) VALUES ('kuali.result.value.state.approved','Approved','The result value has been approved to be used and awarded','kuali.result.value.process')
/
INSERT INTO KSEN_COMM_STATE (ID,DESCR,NAME,PROCESS_KEY) VALUES ('kuali.result.value.state.retired','Retired','The result value has been retired and can still exist on records but can no longer be used','kuali.result.value.process')
/