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
INSERT INTO KSEN_STATE_PROCESS (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.course.offering.process', 'kuali.course.offering.process', 'kuali course offering process', 0)
INSERT INTO KSEN_STATE_PROCESS (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.lui.capacity.process', 'kuali.lui.capacity.process', 'kuali lui capacity process', 0)
INSERT INTO KSEN_STATE_PROCESS (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.lui.lui.relationship.process', 'kuali.lui.lui.relationship.process', 'kuali lui-lui relationship process', 0)

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
//LUI states
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.lui.state.draft', 'Draft', 'kuali.course.offering.process', 'Proposed for Departmental Planning purposed', 0)
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.lui.state.submitted', 'Submitted', 'kuali.course.offering.process', 'Submitted to central admin for approval (or scheduling)', 0)
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.lui.state.approved', 'Approved', 'kuali.course.offering.process', 'Approved and ready to be scheduled', 0)

INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.lui.lui.relation.state.active', 'Active', 'kuali.lui.lui.relationship.process', 'The relationship between the two LUIs is active ', 0)
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.lui.lui.relation.state.inactive', 'Inactive', 'kuali.lui.lui.relationship.process', 'The relationship between the two LUIs is in-active ', 0)

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

INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-10', 'kuali.course.offering.process', null, 'kuali.lui.state.draft', 0)
INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-11', 'kuali.course.offering.process', 'kuali.lui.state.draft', 'kuali.lui.state.submitted', 0)
INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-12', 'kuali.course.offering.process', 'kuali.lui.state.submitted', 'kuali.lui.state.approved', 0)

INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-13', 'kuali.lui.lui.relationship.process', null, 'kuali.lui.lui.relation.state.active', 0)
INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-14', 'kuali.lui.lui.relationship.process', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.state.inactive', 0)

//AtpTypeEntity
--INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.type.AcademicCalendar', 'Academic Calendar', 'Academic Calendar', 'http://student.kuali.org/wsdl/atp/AtpInfo', 0)
--INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.type.HolidayCalendar', 'Holiday Calendar', 'Holiday Calendar', 'http://student.kuali.org/wsdl/atp/AtpInfo', 0)
--INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.type.Fall', 'Fall', 'Fall Semester', 'http://student.kuali.org/wsdl/atp/AtpInfo', 0)
--INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.type.Spring', 'Spring', 'Spring Semester', 'http://student.kuali.org/wsdl/atp/AtpInfo', 0)

// TypeTypeRelationEntity - TODO: move to object-URI-neutral sql file
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.1', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Fall' , 0, 'kuali.atp.type.Fall is a type of Term')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.2', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.HalfFall1' , 0, 'kuali.atp.type.HalfFall1 is a type of Term')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.3', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.HalfFall2' , 0, 'kuali.atp.type.HalfFall2 is a type of Term')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.4', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.HalfSpring1' , 0, 'kuali.atp.type.HalfSpring1 is a type of Term')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.5', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.HalfSpring2' , 0, 'kuali.atp.type.HalfSpring2 is a type of Term')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.6', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Mini-mester1A' , 0, 'kuali.atp.type.Mini-mester1A is a type of Term')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.7', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Mini-mester1B' , 0, 'kuali.atp.type.Mini-mester1B is a type of Term')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.8', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Mini-mester2C' , 0, 'kuali.atp.type.Mini-mester2C is a type of Term')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.9', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Mini-mester2D' , 0, 'kuali.atp.type.Mini-mester2D is a type of Term')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.10', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Session1' , 0, 'kuali.atp.type.Session1 is a type of Term')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.11', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Session2' , 0, 'kuali.atp.type.Session2 is a type of Term')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.12', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.SessionG1' , 0, 'kuali.atp.type.SessionG1 is a type of Term')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.13', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.SessionG2' , 0, 'kuali.atp.type.SessionG2 is a type of Term')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.14', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Spring' , 0, 'kuali.atp.type.Spring is a type of Term')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.15', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.SpringBreak' , 0, 'kuali.atp.type.SpringBreak is a type of Term')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.16', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Summer' , 0, 'kuali.atp.type.Summer is a type of Term')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.17', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.SummerEve' , 0, 'kuali.atp.type.SummerEve is a type of Term')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.18', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Winter' , 0, 'kuali.atp.type.Winter is a type of Term')
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.19', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Adhoc' , 0, 'kuali.atp.type.Adhoc is a type of Term')

//LuiInstructorEntity
INSERT INTO KSEN_LUI_INSTR (ID, ORG_ID, PERS_ID, PERS_OVRID, PERCT_EFFT, VER_NBR) VALUES ('LUI-INSTR-1', 'Org-1', 'Pers-1', 'Instr-1', 30.5, 0)

//LuiCapacityEntity
INSERT INTO KSEN_LUI_CAPACITY (ID, NAME, LUI_CAPACITY_TYPE, LUI_CAPACITY_STATE, DESCR_FORMATTED, DESCR_PLAIN, MAX_SEATS, PROCESSING_ORDER, EFF_DT, EXPIR_DT, VER_NBR) VALUES ('Lui-cap-1', 'Lui Capacity', 'lui.capacity.type', 'lui.capacity.state', 'lui.capacit.descr.formatted', 'lui.capacity.descr.plain', 100, 100, {ts '2011-01-01 00:00:00.0'}, {ts '2011-12-31 00:00:00.0'}, 0)

//LuiEntity
INSERT INTO KSEN_LUI (ID, NAME, CLU_ID, LUI_TYPE, LUI_STATE, DESCR_FORMATTED, DESCR_PLAIN, EFF_DT, EXPIR_DT, MAX_SEATS, MIN_SEATS, VER_NBR) VALUES ('Lui-1', 'Lui one',  'cluId1', 'kuali.lui.type.course.offering', 'kuali.lui.state.draft', '<p>Lui Desc 101</p>', 'Lui Desc 101', {ts '2011-01-01 00:00:00.0'}, {ts '2011-12-31 00:00:00.0'}, 200, 50, 0)
INSERT INTO KSEN_LUI (ID, NAME, CLU_ID, LUI_TYPE, LUI_STATE, DESCR_FORMATTED, DESCR_PLAIN, EFF_DT, EXPIR_DT, MAX_SEATS, MIN_SEATS, VER_NBR) VALUES ('Lui-2', 'Lui rwo',  'cluId2', 'kuali.lui.type.activity.offering.lecture', 'kuali.lui.state.draft', '<p>Lui Desc 201</p>', 'Lui Desc 201', {ts '2011-01-01 00:00:00.0'}, {ts '2011-12-31 00:00:00.0'}, 200, 50, 0)
INSERT INTO KSEN_LUI (ID, NAME, CLU_ID, LUI_TYPE, LUI_STATE, DESCR_FORMATTED, DESCR_PLAIN, EFF_DT, EXPIR_DT, MAX_SEATS, MIN_SEATS, VER_NBR) VALUES ('Lui-3', 'Lui three', 'cluId3', 'kuali.lui.type.course.offering', 'kuali.lui.state.draft', '<p>Lui Desc 301</p>', 'Lui Desc 301 for deletion', {ts '2011-01-01 00:00:00.0'}, {ts '2011-12-31 00:00:00.0'}, 200, 50, 0)
INSERT INTO KSEN_LUI (ID, NAME, CLU_ID, LUI_TYPE, LUI_STATE, DESCR_FORMATTED, DESCR_PLAIN, EFF_DT, EXPIR_DT, MAX_SEATS, MIN_SEATS, VER_NBR) VALUES ('Lui-4', 'Lui four',  'cluId4', 'kuali.lui.type.activity.offering.lecture', 'kuali.lui.state.draft', '<p>Lui Desc 401</p>', 'Lui Desc 401 for deletion', {ts '2011-01-01 00:00:00.0'}, {ts '2011-12-31 00:00:00.0'}, 200, 50, 0)
INSERT INTO KSEN_LUI (ID, NAME, CLU_ID, LUI_TYPE, LUI_STATE, DESCR_FORMATTED, DESCR_PLAIN, EFF_DT, EXPIR_DT, MAX_SEATS, MIN_SEATS, VER_NBR) VALUES ('Lui-5', 'Lui five',  'cluId5', 'kuali.lui.type.activity.offering.lab', 'kuali.lui.state.draft', '<p>Lui Desc 501</p>', 'Lui Desc 501', {ts '2011-01-01 00:00:00.0'}, {ts '2011-12-31 00:00:00.0'}, 200, 50, 0)

//LuiLuiRelationEntity
INSERT INTO KSEN_LUILUI_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, LUI_RELTN_STATE, DESCR_FORMATTED, DESCR_PLAIN, LUI_ID, LUILUI_RELTN_TYPE, RELATED_LUI_ID) VALUES ('LUILUIREL-1', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.lui.lui.relation.state.active', '<p>LUILUIREL-1 Formatted</p>', 'LUILUIREL-1 Plain', 'Lui-1', 'kuali.lui.lui.relation.associated', 'Lui-2')
INSERT INTO KSEN_LUILUI_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, LUI_RELTN_STATE, DESCR_FORMATTED, DESCR_PLAIN, LUI_ID, LUILUI_RELTN_TYPE, RELATED_LUI_ID) VALUES ('LUILUIREL-2', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.lui.lui.relation.state.active', '<p>LUILUIREL-2 Formatted</p>', 'LUILUIREL-2 Plain', 'Lui-3', 'kuali.lui.lui.relation.associated', 'Lui-4')
INSERT INTO KSEN_LUILUI_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, LUI_RELTN_STATE, DESCR_FORMATTED, DESCR_PLAIN, LUI_ID, LUILUI_RELTN_TYPE, RELATED_LUI_ID) VALUES ('LUILUIREL-3', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.lui.lui.relation.state.active', '<p>LUILUIREL-3 Formatted</p>', 'LUILUIREL-3 Plain', 'Lui-5', 'kuali.lui.lui.relation.associated', 'Lui-2')

//LuiCluCluRelationEntity
INSERT INTO KSEN_LUI_CLUCLU_RELTN (ID, LUI_ID, CLUCLU_RELTN_ID) VALUES ('LUI-CLUCLU-1', 'Lui-1', 'CluClu-1')
INSERT INTO KSEN_LUI_CLUCLU_RELTN (ID, LUI_ID, CLUCLU_RELTN_ID) VALUES ('LUI-CLUCLU-2', 'Lui-1', 'CluClu-2')

//LuiUnitsDeploymentEntity
INSERT INTO KSEN_LUI_UNITS_DEPLOYMENT (ID, LUI_ID, ORG_ID) VALUES ('LUI-Dep-1', 'Lui-1', 'Org-1')

//LuiUnitsContentOwnerEntity
INSERT INTO KSEN_LUI_UNITS_CONT_OWNER (ID, LUI_ID, ORG_ID) VALUES ('LUI-Owner-1', 'Lui-1', 'Org-2')

//LuiResultValueGroupEntity
INSERT INTO KSEN_LUI_RESULT_VAL_GRP (ID, LUI_ID, RESULT_VAL_GRP_ID) VALUES ('LUI-ValGrp-1', 'Lui-1', 'Val-Group-1')
INSERT INTO KSEN_LUI_RESULT_VAL_GRP (ID, LUI_ID, RESULT_VAL_GRP_ID) VALUES ('LUI-ValGrp-2', 'Lui-1', 'Val-Group-2')
INSERT INTO KSEN_LUI_RESULT_VAL_GRP (ID, LUI_ID, RESULT_VAL_GRP_ID) VALUES ('LUI-ValGrp-3', 'Lui-1', 'Val-Group-3')

//LuiMeetingScheduleEntity
INSERT INTO KSEN_LUI_MTG_SCHE (ID, SPACE_ID, SCHEDULE_ID, LUI_ID) VALUES ('LUI-MSched-1', 'Space-1', 'Schedule-1', 'Lui-1')
INSERT INTO KSEN_LUI_MTG_SCHE (ID, SPACE_ID, SCHEDULE_ID, LUI_ID) VALUES ('LUI-MSched-2', 'Space-2', 'Schedule-2', 'Lui-1')
INSERT INTO KSEN_LUI_MTG_SCHE (ID, SPACE_ID, SCHEDULE_ID, LUI_ID) VALUES ('LUI-MSched-3', 'Space-3', 'Schedule-3', 'Lui-1')
INSERT INTO KSEN_LUI_MTG_SCHE (ID, SPACE_ID, SCHEDULE_ID, LUI_ID) VALUES ('LUI-MSched-4', 'Space-4', 'Schedule-4', 'Lui-1')

//LuiFeeEntity
INSERT INTO KSEN_LUI_FEE (ID, FEE_TYPE, RATE_TYPE, DESCR_PLAIN, DESCR_FORMATTED, FEE_KEY, LUI_ID, VER_NBR) VALUES ('LUI-Fee-1', 'fee.type.one', 'rate.type', 'fee.descr.plain', 'fee.descr.formatted', 'fee.key', 'Lui-1', 0)
INSERT INTO KSEN_LUI_FEE (ID, FEE_TYPE, RATE_TYPE, DESCR_PLAIN, DESCR_FORMATTED, FEE_KEY, LUI_ID, VER_NBR) VALUES ('LUI-Fee-2', 'fee.type.two', 'rate.type', 'fee.descr.plain', 'fee.descr.formatted', 'fee.key', 'Lui-1', 0)
INSERT INTO KSEN_LUI_FEE (ID, FEE_TYPE, RATE_TYPE, DESCR_PLAIN, DESCR_FORMATTED, FEE_KEY, LUI_ID, VER_NBR) VALUES ('LUI-Fee-3', 'fee.type.three', 'rate.type', 'fee.descr.plain', 'fee.descr.formatted', 'fee.key', 'Lui-1', 0)

//LuiExpenditureEntity
INSERT INTO KSEN_LUI_EXPENDITURE (ID, LUI_ID, VER_NBR) VALUES ('LUI-Expen-1', 'Lui-1', 0)

//LuiRevenueEntity
INSERT INTO KSEN_LUI_REVENUE (ID, FEE_TYPE, LUI_ID, VER_NBR) VALUES ('LUI-Reven-1', 'fee.type.one', 'Lui-1', 0)
INSERT INTO KSEN_LUI_REVENUE (ID, FEE_TYPE, LUI_ID, VER_NBR) VALUES ('LUI-Reven-2', 'fee.type.two', 'Lui-1', 0)

//LuiCapacityRelationEntity
INSERT INTO KSEN_LUICAPACITY_RELTN (ID, LUI_ID, LUI_CAPACITY_ID, VER_NBR) VALUES ('LUI-CapRel-1', 'Lui-1', 'Lui-cap-1', 0)

//LuiLuCodeEntity
INSERT INTO KSEN_LUI_LU_CD (ID, LUI_LUCD_TYPE, DESCR_PLAIN, DESCR_FORMATTED, VALUE, VER_NBR) VALUES ('LUI-lu-cd-1', 'lu.code.type', 'lu.code.descr.plain', 'lu.code.descr.formatted', 'code.value', 0)

// LuiIdentifierEntity
INSERT INTO KSEN_LUI_IDENT (ID, LUI_CD, DIVISION, SUFX_CD, LNG_NAME, SHRT_NAME, LUI_ID_STATE, LUI_ID_TYPE, VARTN, LUI_ID, VER_NBR) VALUES ('LUI-IDENT-1', 'CHEM123', 'CHEM', '123', 'Chemistry 123', 'Chem 123', null, 'kuali.lui.identifier.type.official', null, 'Lui-1', 0)
INSERT INTO KSEN_LUI_IDENT (ID, LUI_CD, DIVISION, SUFX_CD, LNG_NAME, SHRT_NAME, LUI_ID_STATE, LUI_ID_TYPE, VARTN, LUI_ID, VER_NBR) VALUES ('LUI-IDENT-2', 'CHEM456', 'CHEM', '456', 'Chemistry 456', 'Chem 456', null, 'kuali.lui.identifier.type.cross-listed', null, 'Lui-1', 0)
INSERT INTO KSEN_LUI_IDENT (ID, LUI_CD, DIVISION, SUFX_CD, LNG_NAME, SHRT_NAME, LUI_ID_STATE, LUI_ID_TYPE, VARTN, LUI_ID, VER_NBR) VALUES ('LUI-IDENT-3', 'BIO123', 'BIO', '123', 'Biology 123', 'Bio 123', null, 'kuali.lui.identifier.type.official', null, 'Lui-2', 0)
INSERT INTO KSEN_LUI_IDENT (ID, LUI_CD, DIVISION, SUFX_CD, LNG_NAME, SHRT_NAME, LUI_ID_STATE, LUI_ID_TYPE, VARTN, LUI_ID, VER_NBR) VALUES ('LUI-IDENT-4', 'BIO456', 'BIO', '456', 'Biology 456', 'Bio 456', null, 'kuali.lui.identifier.type.cross-listed', 'Lui-2', null, 0)
INSERT INTO KSEN_LUI_IDENT (ID, LUI_CD, DIVISION, SUFX_CD, LNG_NAME, SHRT_NAME, LUI_ID_STATE, LUI_ID_TYPE, VARTN, LUI_ID, VER_NBR) VALUES ('LUI-IDENT-5', 'GEOG123', 'GEOG', '123', 'Geography 123', 'Geog 123', null, 'kuali.lui.identifier.type.official', 'Lui-3', null, 0)
INSERT INTO KSEN_LUI_IDENT (ID, LUI_CD, DIVISION, SUFX_CD, LNG_NAME, SHRT_NAME, LUI_ID_STATE, LUI_ID_TYPE, VARTN, LUI_ID, VER_NBR) VALUES ('LUI-IDENT-6', 'MATH123', 'MATH', '123', 'Mathematics 123', 'Math 123', null, 'kuali.lui.identifier.type.official', 'Lui-4', null, 0)
INSERT INTO KSEN_LUI_IDENT (ID, LUI_CD, DIVISION, SUFX_CD, LNG_NAME, SHRT_NAME, LUI_ID_STATE, LUI_ID_TYPE, VARTN, LUI_ID, VER_NBR) VALUES ('LUI-IDENT-7', 'MATH123', 'MATH', '456', 'Mathematics 456', 'Math 456', null, 'kuali.lui.identifier.type.cross-listed', 'Lui-4', null, 0)
