// HoldRichTextEntity
INSERT INTO KSEN_HOLD_RICH_TEXT (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('Issue-1-Desc', '<p>Issue Desc 101</p>', 'Issue Desc 101',0)
INSERT INTO KSEN_HOLD_RICH_TEXT (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('Issue-2-Desc', '<p>Issue Desc for deletion test</p>', 'Issue Desc for deletion test',0)
INSERT INTO KSEN_HOLD_RICH_TEXT (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-Hold-101', '<p>Hold Desc 101</p>', 'Hold Desc 101',0)
INSERT INTO KSEN_HOLD_RICH_TEXT (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-Hold-201', '<p>Hold Desc for deletion test</p>', 'Hold Desc for deletion test',0)
INSERT INTO KSEN_HOLD_RICH_TEXT (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-Hold-301', '<p>Hold Desc student</p>', 'Hold Desc student',0)
INSERT INTO KSEN_HOLD_RICH_TEXT (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-Hold-401', '<p>Hold Desc instructor</p>', 'Hold Desc instructor',0)

//StateProcessEntity for Hold Process
INSERT INTO KSEN_STATE_PROCESS (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.hold.restriction.process', 'kuali.hold.restriction.process', 'kuali hold restriction process', 0)
INSERT INTO KSEN_STATE_PROCESS (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.hold.issue.process', 'kuali.hold.issue.process', 'kuali hold issue process', 0)
INSERT INTO KSEN_STATE_PROCESS (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.hold.process.student', 'kuali.hold.process.student', 'kuali hold process student', 0)

// Hold States
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.hold.restriction.state.active', 'Active', 'kuali.hold.restriction.process', 'This restriction is active and should be enforced', 0)
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.hold.restriction.state.inactive', 'Inactive', 'kuali.hold.restriction.process', 'The restriction is inactive and should not be enforced', 0)

INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.hold.issue.state.active', 'Active', 'kuali.hold.issue.process', 'This issue is active and can be attached to holds ', 0)
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.hold.issue.state.inactive', 'Inactive', 'kuali.hold.issue.process', 'The hold is inactive an cannot be attached to new holds ', 0)

INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.hold.state.active', 'Active', 'kuali.hold.process.student', 'This hold is active and should be enforced', 0)
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.hold.state.released', 'Released', 'kuali.hold.process.student', 'The hold was active and the issue has been resolved', 0)
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.hold.state.canceled', 'Canceled', 'kuali.hold.process.student', 'The hold was canceled or removed because it was originally put on in error', 0)

// Hold StateProcessRelation
INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-3', 'kuali.hold.restriction.process', null, 'kuali.hold.restriction.state.active', 0)
INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-4', 'kuali.hold.restriction.process', 'kuali.hold.restriction.state.active', 'kuali.hold.restriction.state.inactive', 0)

INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-5', 'kuali.hold.issue.process', null, 'kuali.hold.issue.state.active', 0)
INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-6', 'kuali.hold.issue.process', 'kuali.hold.issue.state.active', 'kuali.hold.issue.state.inactive', 0)

INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-7', 'kuali.hold.process.student', null, 'kuali.hold.state.active', 0)
INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-8', 'kuali.hold.process.student', 'kuali.hold.state.active', 'kuali.hold.state.released', 0)
INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-9', 'kuali.hold.process.student', 'kuali.hold.state.released', 'kuali.hold.state.canceled', 0)

//HoldTypeEntity
INSERT INTO KSEN_HOLD_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.hold.restriction.type.registration', 'Registration Restriction', 'Cannot register', 0)
INSERT INTO KSEN_HOLD_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.hold.restriction.type.receive.diploma', 'Receive Diploma Restriction', 'Cannot get physical diploma (don''t mail or allow to be picked up', 0)
INSERT INTO KSEN_HOLD_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.hold.restriction.type.add.drop.class', 'Add/Drop Restriction', 'Cannot change registration', 0)
INSERT INTO KSEN_HOLD_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.hold.restriction.type.request.transcript', 'Transcript Restriction', 'Cannot order a transcript', 0)
INSERT INTO KSEN_HOLD_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.hold.restriction.type.award.degree', 'Award Degree Restriction', 'Cannot award degree', 0)
INSERT INTO KSEN_HOLD_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.hold.restriction.type.add.charges', 'Add Charges Restriction', 'Cannot submit new charges to the student''s account', 0)
INSERT INTO KSEN_HOLD_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.hold.restriction.type.library.privileges', 'Library Privileges Restriction', 'Cannot use library', 0)
INSERT INTO KSEN_HOLD_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.hold.restriction.type.dorm.access', 'Dorm Access Restriction', 'Student barred from access to the dorm', 0)
INSERT INTO KSEN_HOLD_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.hold.restriction.type.update.program', 'Update Program', 'Blocks a student from changing her degree program', 0)
INSERT INTO KSEN_HOLD_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.hold.restriction.type.access.grades', 'Access Grades', 'Restricts students access to her grades', 0)
INSERT INTO KSEN_HOLD_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.hold.restriction.type.bursar.refund', 'Bursar Refund', 'Stops a student from receiving a refund even if they have a creadit balance', 0)
INSERT INTO KSEN_HOLD_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.hold.restriction.type.verification', 'Verification', 'Blocks a student from having a request to verify her attendance or degrees processed', 0)
INSERT INTO KSEN_HOLD_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.hold.restriction.type.status', 'Status', 'Student is not allowed to register, be on campus, or use any campus facilities or resources', 0)
INSERT INTO KSEN_HOLD_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.hold.issue.type.residency', 'Residency', 'Indicates there is a discrepancy or incomplete information to establish residency status', 0)
INSERT INTO KSEN_HOLD_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.hold.issue.type.unpaid.fee', 'Unpaid fees', 'Indicates that this issue has to do with fees that were incurred but never paid', 0)
INSERT INTO KSEN_HOLD_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.hold.type.student', 'Student Hold', 'This is a hold that applies to students', 0)
INSERT INTO KSEN_HOLD_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.hold.type.instructor', 'Instructor Hold', 'This is a hold that applies to instructors', 0)

//RestrictionEntity
INSERT INTO KSEN_RESTRICTION (ID, NAME, TYPE_ID, STATE_ID, RT_DESCR_ID, VER_NBR) VALUES ('Hold-Restriction-1', 'Restriction one', 'kuali.hold.restriction.type.registration', 'kuali.hold.restriction.state.active', 'RICHTEXT-Hold-101', 0)
INSERT INTO KSEN_RESTRICTION (ID, NAME, TYPE_ID, STATE_ID, RT_DESCR_ID, VER_NBR) VALUES ('Hold-Restriction-2', 'Restriction two', 'kuali.hold.restriction.type.receive.diploma', 'kuali.hold.restriction.state.active', 'RICHTEXT-Hold-201', 0)

//IssueEntity
INSERT INTO KSEN_ISSUE (ID, NAME, TYPE_ID, STATE_ID, ORG_ID, RT_DESCR_ID, VER_NBR) VALUES ('Hold-Issue-1', 'Issue one', 'kuali.hold.issue.type.residency', 'kuali.hold.issue.state.active', '102', 'Issue-1-Desc', 0)
INSERT INTO KSEN_ISSUE (ID, NAME, TYPE_ID, STATE_ID, ORG_ID, RT_DESCR_ID, VER_NBR) VALUES ('Hold-Issue-2', 'Issue two', 'kuali.hold.issue.type.residency', 'kuali.hold.issue.state.active', '102', 'Issue-2-Desc', 0)

//HoldEntity
INSERT INTO KSEN_HOLD (ID, NAME, RT_DESCR_ID, IS_WARNING, IS_OVERRIDABLE, PERS_ID, ISSUE_ID, TYPE_ID, STATE_ID, EFF_DT, RELEASED_DT, VER_NBR) VALUES ('Hold-1', 'Hold one', 'RICHTEXT-Hold-301', 0, 1, '1', 'Hold-Issue-1', 'kuali.hold.type.student', 'kuali.hold.state.active', {ts '2011-01-01 00:00:00.0'}, {ts '2011-12-31 00:00:00.0'}, 0)
INSERT INTO KSEN_HOLD (ID, NAME, RT_DESCR_ID, IS_WARNING, IS_OVERRIDABLE, PERS_ID, ISSUE_ID, TYPE_ID, STATE_ID, EFF_DT, RELEASED_DT, VER_NBR) VALUES ('Hold-2', 'Hold two', 'RICHTEXT-Hold-401', 0, 1, '1', 'Hold-Issue-1', 'kuali.hold.type.instructor', 'kuali.hold.state.active', {ts '2011-01-01 00:00:00.0'}, {ts '2011-12-31 00:00:00.0'}, 0)

// IssueRestrictionRelationEntity
INSERT INTO KSEN_ISSRESTRCTN_RELTN(ID, ISSUE_ID, RESTRICTION_ID, VER_NBR) VALUES ('Issue-Restriction-Rel-1', 'Hold-Issue-1', 'Hold-Restriction-1', 0)
