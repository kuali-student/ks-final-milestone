//StateProcessEntity
INSERT INTO KSEN_STATE_PROCESS (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.atp.process', 'kuali.atp.process', 'kuali atp state process', 0)
INSERT INTO KSEN_STATE_PROCESS (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.milestone.process', 'kuali.milestone.process', 'kuali milestone state process', 0)
INSERT INTO KSEN_STATE_PROCESS (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.atp.atp.relation.process', 'kuali.atp.atp.relation.process', 'kuali atp-atp relation state process', 0)
INSERT INTO KSEN_STATE_PROCESS (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.atp.milestone.relation.process', 'kuali.atp.milestone.relation.process', 'kuali atp-milestone relation state process', 0)

//HOLD Process
INSERT INTO KSEN_STATE_PROCESS (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.hold.restriction.process', 'kuali.hold.restriction.process', 'kuali hold restriction process', 0)
INSERT INTO KSEN_STATE_PROCESS (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.hold.issue.process', 'kuali.hold.issue.process', 'kuali hold issue process', 0)
INSERT INTO KSEN_STATE_PROCESS (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.hold.process.student', 'kuali.hold.process.student', 'kuali hold process student', 0)

//LUI Process
INSERT INTO KSEN_STATE_PROCESS (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.lui.process', 'kuali.lui.process', 'kuali lui process', 0)

//StateEntity
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.atp.state.Draft', 'Draft', 'kuali.atp.process', 'Indicates that this Time Period is just tentative', 0)
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.atp.state.Official', 'Official', 'kuali.atp.process', 'Indicates that this Time Period has been established', 0)

INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.milestone.state.Draft', 'Draft', 'kuali.milestone.process', 'Indicates that this milestone is just tentative', 0)
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.milestone.state.Official', 'Official', 'kuali.milestone.process', 'Indicates that this milestone has been established', 0)

INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.atp.atp.relation.state.active', 'Active', 'kuali.atp.atp.relation.process', 'Indicates that this Atp-Atp relation is active', 0)
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.atp.atp.relation.state.inactive', 'Inactive', 'kuali.atp.atp.relation.process', 'Indicates that this Atp-Atp relation is inactive', 0)

INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.atp.milestone.relation.state.active', 'Active', 'kuali.atp.milestone.relation.process', 'Indicates that this Atp-Milestone relation is active', 0)
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.atp.milestone.relation.state.inactive', 'Inactive', 'kuali.atp.milestone.relation.process', 'Indicates that this Atp-Milestone relation is inactive', 0)
//HOLD States
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.hold.restriction.state.active', 'Active', 'kuali.hold.restriction.process', 'This restriction is active and should be enforced', 0)
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.hold.restriction.state.inactive', 'Inactive', 'kuali.hold.restriction.process', 'The restriction is inactive and should not be enforced', 0)

INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.hold.issue.state.active', 'Active', 'kuali.hold.issue.process', 'This issue is active and can be attached to holds ', 0)
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.hold.issue.state.inactive', 'Inactive', 'kuali.hold.issue.process', 'The hold is inactive an cannot be attached to new holds ', 0)

INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.hold.state.active', 'Active', 'kuali.hold.process.student', 'This hold is active and should be enforced', 0)
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.hold.state.released', 'Released', 'kuali.hold.process.student', 'The hold was active and the issue has been resolved', 0)
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.hold.state.canceled', 'Canceled', 'kuali.hold.process.student', 'The hold was canceled or removed because it was originally put on in error', 0)

INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.lui.state.active', 'Active', 'kuali.lui.process', 'Indicates that this LUI is active', 0)
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.lui.state.inactive', 'Inactive', 'kuali.lui.process', 'Indicates that this LUI is inactive', 0)

//StateProcessRelationEntity
INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-1', 'kuali.atp.process', null, 'kuali.atp.state.Draft', 0)
INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-2', 'kuali.atp.process', 'kuali.atp.state.Draft', 'kuali.atp.state.Official', 0)

//HOLD StateProcessRelation
INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-3', 'kuali.hold.restriction.process', null, 'kuali.hold.restriction.state.active', 0)
INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-4', 'kuali.hold.restriction.process', 'kuali.hold.restriction.state.active', 'kuali.hold.restriction.state.inactive', 0)

INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-5', 'kuali.hold.issue.process', null, 'kuali.hold.issue.state.active', 0)
INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-6', 'kuali.hold.issue.process', 'kuali.hold.issue.state.active', 'kuali.hold.issue.state.inactive', 0)

INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-7', 'kuali.hold.process.student', null, 'kuali.hold.state.active', 0)
INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-8', 'kuali.hold.process.student', 'kuali.hold.state.active', 'kuali.hold.state.released', 0)
INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-9', 'kuali.hold.process.student', 'kuali.hold.state.released', 'kuali.hold.state.canceled', 0)

INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-10', 'kuali.lui.process', null, 'kuali.lui.state.active', 0)
INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-11', 'kuali.lui.process', 'kuali.lui.state.active', 'kuali.lui.state.inactive', 0)

//LuiTypeEntity
INSERT INTO KSEN_LUI_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.lui.type.course.group.offering', 'Course Group Offering', 'Used to collect tightly related course offerings into groups that all have to be taken together', 0)

// LuiRichTextEntity
INSERT INTO KSEN_LUI_RICH_TEXT (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('Lui-1-Desc', '<p>Lui Desc 101</p>', 'Issue Desc 101',0)

//LuiEntity
INSERT INTO KSEN_LUI (ID, NAME, LUI_CODE, CLU_ID, TYPE_ID, STATE_ID, RT_DESCR_ID, EFF_DT, EXP_DT, VER_NBR) VALUES ('Lui-1', 'Lui one', 'ENGL 100 section 123', 'cluId1', 'kuali.lui.type.course.group.offering', 'kuali.lui.state.active', 'Lui-1-Desc', {ts '2011-01-01 00:00:00.0'}, {ts '2011-12-31 00:00:00.0'}, 0)
