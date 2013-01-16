TRUNCATE TABLE KSEN_COMM_STATE DROP STORAGE
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY,VER_NBR)
  VALUES ('This issue is active and can be attached to holds ','kuali.hold.issue.state.active','Active','kuali.hold.issue.process',0)
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY,VER_NBR)
  VALUES ('The hold is inactive an cannot be attached to new holds ','kuali.hold.issue.state.inactive','Inactive','kuali.hold.issue.process',0)
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY,VER_NBR)
  VALUES ('This restriction is active and should be enforced','kuali.hold.restriction.state.active','Active','kuali.hold.restriction.process',0)
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY,VER_NBR)
  VALUES ('The restriction is inactive and should not be enforced','kuali.hold.restriction.state.inactive','Inactive','kuali.hold.restriction.process',0)
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY,VER_NBR)
  VALUES ('This hold is active and should be enforced','kuali.hold.state.active','Active','kuali.hold.process.student',0)
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY,VER_NBR)
  VALUES ('The hold was canceled or removed because it was originally put on in error','kuali.hold.state.canceled','Canceled','kuali.hold.process.student',0)
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY,VER_NBR)
  VALUES ('The hold was active and the issue has been resolved','kuali.hold.state.released','Released','kuali.hold.process.student',0)
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY,VER_NBR)
  VALUES ('New roster entry','kuali.lpr.roster.state.created','New roster entry','kuali.lpr.roster.process.course.grading',0)
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY,VER_NBR)
  VALUES ('Active Roster entry','kuali.lpr.roster.state.ready','Assigned','kuali.lpr.roster.process.course.grading',0)
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY,VER_NBR)
  VALUES ('The roster has had information entered against it and it has been saved','kuali.lpr.roster.state.saved','Saved','kuali.lpr.roster.process.course.grading',0)
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY,VER_NBR)
  VALUES ('The roster has has been submitted','kuali.lpr.roster.state.submitted','Submitted','kuali.lpr.roster.process.course.grading',0)
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY,VER_NBR)
  VALUES ('The instructor is assigned to teach this course or section.','kuali.lpr.state.assigned','Assigned','kuali.lpr.process.instructor.course.assignment',0)
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY,VER_NBR)
  VALUES ('The student was registered but subsequently dropped the course or section within the normally allotted time period','kuali.lpr.state.dropped.early','Dropped','kuali.lpr.process.student.course.registration',0)
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY,VER_NBR)
  VALUES ('The student was registered but subsequently dropped the course or section past the normally allotted time period,typically resulting in a special grade or mark to so indicate','kuali.lpr.state.dropped.late','Dropped Late','kuali.lpr.process.student.course.registration',0)
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY,VER_NBR)
  VALUES ('The student has registered for the course by has not paid the fee','kuali.lpr.state.not.paid','Fee Not Paid','kuali.lpr.process.student.course.registration',0)
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY,VER_NBR)
  VALUES ('The student plans on taking this course or program','kuali.lpr.state.planned','Planned','kuali.lpr.process.student.course.registration',0)
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY,VER_NBR)
  VALUES ('The student is officially registered for the course or section','kuali.lpr.state.registered','Registered','kuali.lpr.process.student.course.registration',0)
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY,VER_NBR)
  VALUES ('The student attempted to join but has been put on the waitlist','kuali.lpr.state.waitlisted','Waitlisted','kuali.lpr.process.student.course.registration',0)
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY,VER_NBR)
  VALUES ('Transaction Item failed','kuali.lpr.trans.item.state.failed','Transaction Item Failed','kuali.lpr.trans.item.process',0)
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY,VER_NBR)
  VALUES ('The student is attemptng to enroll in this course or section.','kuali.lpr.trans.item.state.new','New','kuali.lpr.process.student.course.registration',0)
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY,VER_NBR)
  VALUES ('Transaction Item Succeeded','kuali.lpr.trans.item.state.succeeded','Transaction Item Succeeded','kuali.lpr.trans.item.process',0)
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY,VER_NBR)
  VALUES ('This hold is active and should be enforced','kuali.lpr.trans.registered','Active','kuali.hold.process.student',0)
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY,VER_NBR)
  VALUES ('The result (grade) has been vetted and is ready to be published to the student','kuali.lrr.state.accepted','Accepted','kuali.lrr.course.final.grading.lifecycle',0)
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY,VER_NBR)
  VALUES ('The result (grade) has been deleted','kuali.lrr.state.deleted','Deleted','kuali.lrr.course.final.grading.lifecycle',0)
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY,VER_NBR)
  VALUES ('The result (grade) has been entered and has been saved but not yet been submitted','kuali.lrr.state.saved','Saved','kuali.lrr.course.final.grading.lifecycle',0)
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY,VER_NBR)
  VALUES ('The result (grade) has been formally submitted','kuali.lrr.state.submitted','Submitted','kuali.lrr.course.final.grading.lifecycle',0)
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY,VER_NBR)
  VALUES ('The relationship between the two LUIs is active ','kuali.lui.lui.relation.state.active','Active','kuali.lui.lui.relationship.lifecycle',0)
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY,VER_NBR)
  VALUES ('The relationship between the two LUIs is in-active ','kuali.lui.lui.relation.state.inactive','Inactive','kuali.lui.lui.relationship.lifecycle',0)
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY,VER_NBR)
  VALUES ('Approved and ready to be scheduled','kuali.lui.state.approved','Approved','kuali.course.offering.lifecycle',0)
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY,VER_NBR)
  VALUES ('Once offered but now canceled','kuali.lui.state.canceled','Canceled','kuali.course.offering.lifecycle',0)
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY,VER_NBR)
  VALUES ('Logically deleted before ever having been offered','kuali.lui.state.deleted','Deleted','kuali.course.offering.lifecycle',0)
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY,VER_NBR)
  VALUES ('Proposed for Departmental Planning purposed','kuali.lui.state.draft','Draft','kuali.course.offering.lifecycle',0)
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY,VER_NBR)
  VALUES ('Offered so it shows up in list of classes so student may register for the course ','kuali.lui.state.offered','Offered','kuali.course.offering.lifecycle',0)
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY,VER_NBR)
  VALUES ('Approved and ready to be scheduled','kuali.lui.state.scheduled','Scheduled','kuali.course.offering.lifecycle',0)
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY,VER_NBR)
  VALUES ('Submitted to central admin for approval (or scheduling)','kuali.lui.state.submitted','Submitted','kuali.course.offering.lifecycle',0)
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY,VER_NBR)
  VALUES ('Suspends registration in the course but allows students who had already registered to stay in the course','kuali.lui.state.suspended','Suspended','kuali.course.offering.lifecycle',0)
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY)
  VALUES ('Approved','kuali.result.scale.state.approved','The result scale has been approved to be used and awarded','kuali.result.scale.process')
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY)
  VALUES ('Draft','kuali.result.scale.state.draft ','The result scale is just draft and cannot yet be used','kuali.result.scale.process')
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY)
  VALUES ('Retired','kuali.result.scale.state.retired','The result scale has been retired and can still exist on records but can no longer be used','kuali.result.scale.process')
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY)
  VALUES ('Approved','kuali.result.value.state.approved','The result value has been approved to be used and awarded','kuali.result.value.process')
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY)
  VALUES ('Draft','kuali.result.value.state.draft ','The result value is just draft and cannot yet be used','kuali.result.value.process')
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY)
  VALUES ('Retired','kuali.result.value.state.retired','The result value has been retired and can still exist on records but can no longer be used','kuali.result.value.process')
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY)
  VALUES ('Approved','kuali.result.values.group.state.approved','The result has been approved to be used and awarded','kuali.result.values.group.process')
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY)
  VALUES ('Draft','kuali.result.values.group.state.draft','The result is just draft and cannot yet be used','kuali.result.values.group.process')
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY)
  VALUES ('The result has been retired and can still exist on records but can no longer be used','kuali.result.values.group.state.retired','Retired','kuali.result.values.group.process')
/
INSERT INTO KSEN_COMM_STATE (DESCR,ID,NAME,PROCESS_KEY,VER_NBR)
  VALUES ('The roster has been created and is ready to have grades entered','kuali.roster.entry.state.active','Ready','kuali.lpr.roster.process.course.grading',0)
/
