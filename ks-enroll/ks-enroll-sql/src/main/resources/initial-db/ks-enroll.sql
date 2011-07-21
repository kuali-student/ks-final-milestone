-- AtpStateEntity
CREATE TABLE KSEN_ATP_STATE
(
   ID varchar2(255) PRIMARY KEY NOT NULL,
   OBJ_ID varchar2(36),
   VER_NBR decimal(19),
   CREATEID varchar2(255),
   CREATETIME timestamp,
   UPDATEID varchar2(255),
   UPDATETIME timestamp,
   DESCR varchar2(255),
   NAME varchar2(255)
);

INSERT INTO KSEN_ATP_STATE (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.atp.state.Draft', 'Draft', 'Indicates that this Time Period is just tentative', 0);
INSERT INTO KSEN_ATP_STATE (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.atp.state.Official', 'Official', 'Indicates that this Time Period has been established', 0);

INSERT INTO KSEN_ATP_STATE (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.milestone.state.Draft', 'Draft', 'Indicates that this milestone is just tentative', 0);
INSERT INTO KSEN_ATP_STATE (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.milestone.state.Official', 'Official', 'Indicates that this milestone has been established', 0);

INSERT INTO KSEN_ATP_STATE (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.atp.atp.relation.state.active', 'Active', 'Indicates that this Atp-Atp relation is active', 0);
INSERT INTO KSEN_ATP_STATE (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.atp.atp.relation.state.inactive', 'Inactive', 'Indicates that this Atp-Atp relation is inactive', 0);

INSERT INTO KSEN_ATP_STATE (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.atp.milestone.relation.state.active', 'Active', 'Indicates that this Atp-Milestone relation is active', 0);
INSERT INTO KSEN_ATP_STATE (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.atp.milestone.relation.state.inactive', 'Inactive', 'Indicates that this Atp-Milestone relation is inactive', 0);

-- AtpTypeEntity

CREATE TABLE KSEN_ATP_TYPE
(
   TYPE_KEY varchar2(255) PRIMARY KEY NOT NULL,
   OBJ_ID varchar2(36),
   VER_NBR decimal(19),
   TYPE_DESC varchar2(2000),
   EFF_DT timestamp,
   EXPIR_DT timestamp,
   NAME varchar2(255),
   REF_OBJECT_URI varchar2(255)
);

INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.type.AcademicCalendar', 'Academic Calendar', 'Academic Calendar', 'http:--student.kuali.org/wsdl/atp/AtpInfo', 0);
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.type.CampusCalendar', 'Campus Calendar', 'Campus Calendar', 'http:--student.kuali.org/wsdl/atp/AtpInfo', 0);
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.type.Fall', 'Fall', 'Fall Semester', 'http:--student.kuali.org/wsdl/atp/AtpInfo', 0);
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.type.Spring', 'Spring', 'Spring Semester', 'http:--student.kuali.org/wsdl/atp/AtpInfo', 0);
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.type.FallSpring', 'Fall-Spring', 'Fall and Spring Semesters', 'http:--student.kuali.org/wsdl/atp/AtpInfo', 0);
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.type.AY', 'AY', 'Full Academic Year', 'http:--student.kuali.org/wsdl/atp/AtpInfo', 0);
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.type.FY', 'FY', 'Fiscal Year', 'http:--student.kuali.org/wsdl/atp/AtpInfo', 0);
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.type.Holiday', 'Holiday', 'Holiday', 'http:--student.kuali.org/wsdl/atp/AtpInfo', 0);
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.type.SpringBreak', 'SpringBreak', 'Spring Break', 'http:--student.kuali.org/wsdl/atp/AtpInfo', 0);
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.type.Thanksgiving', 'Thanksgiving', 'Thanksgiving', 'http:--student.kuali.org/wsdl/atp/AtpInfo', 0);
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.type.HalfFall1', 'Fall Half-Semester 1', 'Fall Half-Semester 1', 'http:--student.kuali.org/wsdl/atp/AtpInfo', 0);
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.type.HalfFall2', 'Fall Half-Semester 2', 'Fall Half-Semester 2', 'http:--student.kuali.org/wsdl/atp/AtpInfo', 0);
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.type.HalfSpring1', 'Spring Half-Semester 1', 'Spring Half-Semester 1', 'http:--student.kuali.org/wsdl/atp/AtpInfo', 0);
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.type.HalfSpring2', 'Spring Half-Semester 1', 'Spring Half-Semester 2', 'http:--student.kuali.org/wsdl/atp/AtpInfo', 0);
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.type.Mini-mester1A', 'Mini Semester 1A', 'Mini Semester 1A', 'http:--student.kuali.org/wsdl/atp/AtpInfo', 0);
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.type.Mini-mester1B', 'Mini Semester 1B', 'Mini Semester 1B', 'http:--student.kuali.org/wsdl/atp/AtpInfo', 0);
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.type.Mini-mester2C', 'Mini Semester 2C', 'Mini Semester 2C', 'http:--student.kuali.org/wsdl/atp/AtpInfo', 0);
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.type.Mini-mester2D', 'Mini Semester 2D', 'Mini Semester 2D', 'http:--student.kuali.org/wsdl/atp/AtpInfo', 0);
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.type.Session1', 'Session 1', 'Session 1', 'http:--student.kuali.org/wsdl/atp/AtpInfo', 0);
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.type.Session2', 'Session 2', 'Session 2', 'http:--student.kuali.org/wsdl/atp/AtpInfo', 0);
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.type.SessionG1', 'Session G1', 'Session G1', 'http:--student.kuali.org/wsdl/atp/AtpInfo', 0);
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.type.SessionG2', 'Session G2', 'Session G2', 'http:--student.kuali.org/wsdl/atp/AtpInfo', 0);
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.type.Summer', 'Summer', 'Summer Semester', 'http:--student.kuali.org/wsdl/atp/AtpInfo', 0);
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.type.SummerEve', 'Summer Eve', 'Summer Eve', 'http:--student.kuali.org/wsdl/atp/AtpInfo', 0);
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.type.Winter', 'Winter', 'Winter', 'http:--student.kuali.org/wsdl/atp/AtpInfo', 0);
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.type.Adhoc', 'Ad hoc', 'Ad hoc', 'http:--student.kuali.org/wsdl/atp/AtpInfo', 0);

-- AtpTypes for Milestones
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.milestone.AdvanceRegistrationPeriod', 'Advance Registration Period', 'Advance Registration Period', 'http:--student.kuali.org/wsdl/atp/MilestoneInfo', 0);
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.milestone.RegistrationPeriod', 'Registration Period', 'Registration Period', 'http:--student.kuali.org/wsdl/atp/MilestoneInfo', 0);
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.milestone.DropDate', 'Drop Date', 'Drop Period Ends', 'http:--student.kuali.org/wsdl/atp/MilestoneInfo', 0);
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.milestone.GradesDue', 'Grades Due', 'Grades Due', 'http:--student.kuali.org/wsdl/atp/MilestoneInfo', 0);
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.milestone.InstructionalPeriod', 'Classes Meet Dates', 'Classes Meet Dates', 'http:--student.kuali.org/wsdl/atp/MilestoneInfo', 0);
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) VALUES ('kuali.atp.milestone.FinalExamPeriod', 'Final Examinations Period', 'Final Examinations Period', 'http:--student.kuali.org/wsdl/atp/MilestoneInfo', 0);

-- AtpTypes for AtpAtpRelations
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) values ('kuali.atp.atp.relation.includes', 'kuali.atp.atp.relation.includes', 'kuali.atp.atp.relation.includes', 'http:--student.kuali.org/wsdl/atp/AtpAtpRelationInfo', 0);
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) values ('kuali.atp.atp.relation.associated', 'kuali.atp.atp.relation.associated', 'kuali.atp.atp.relation.associated', 'http:--student.kuali.org/wsdl/atp/AtpAtpRelationInfo', 0);

-- AtpTypes for AtpMilestoneRelations
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) values ('kuali.atp.milestone.relation.owns', 'Owns', 'Indicates the ATP owns the specified milestone', 'http:--student.kuali.org/wsdl/atp/AtpMilestoneRelationInfo', 0);
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, REF_OBJECT_URI, VER_NBR) values ('kuali.atp.milestone.relation.reuses', 'Reuses', 'Indicates the ATP reuses the specified milestone that another ATP owns ', 'http:--student.kuali.org/wsdl/atp/AtpMilestoneRelationInfo', 0);

-- AtpRichTextEntity

CREATE TABLE KSEN_RICH_TEXT_T
(
   ID varchar2(255) PRIMARY KEY NOT NULL,
   OBJ_ID varchar2(36),
   VER_NBR decimal(19),
   FORMATTED varchar2(2000),
   PLAIN varchar2(2000)
);

INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-101', '<p>Desc 101</p>', 'Desc 101',0);
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-102', '<p>Desc 102</p>', 'Desc 102',0);
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-103', '<p>Desc 103</p>', 'Desc 103',0);
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-104', '<p>Desc 104</p>', 'Desc 104',0);
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-105', '<p>Desc 105</p>', 'Desc 105',0);
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-106', '<p>Desc 106</p>', 'Desc 106',0);
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-107', '<p>Desc 107</p>', 'Desc 107',0);
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-108', '<p>Desc 108</p>', 'Desc 108',0);
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-201', '<p>Desc 2</p>', 'Desc 2',0);
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-301', '<p>Desc 3</p>', 'Desc 3',0);

--  Term Rich Text entities
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-TRT-1', '<p>Desc term rich text 1</p>', 'Desc term rich text 1',0);
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-TRT-2', '<p>Desc term rich text 2</p>', 'Desc term rich text 2',0);
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-TRT-3', '<p>Desc term rich text 3</p>', 'Desc term rich text 3',0);
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-TRT-4', '<p>Desc term rich text 4</p>', 'Desc term rich text 4',0);
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-TRT-5', '<p>Desc term rich text 5</p>', 'Desc term rich text 5',0);
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-TRT-6', '<p>Desc term rich text 6</p>', 'Desc term rich text 6',0);
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-TRT-7', '<p>Desc term rich text 7</p>', 'Desc term rich text 7',0);
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-TRT-8', '<p>Desc term rich text 8</p>', 'Desc term rich text 8',0);
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-TRT-9', '<p>Desc term rich text 9</p>', 'Desc term rich text 9',0);
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-TRT-10', '<p>Desc term rich text 10</p>', 'Desc term rich text 10',0);
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-TRT-11', '<p>Desc term rich text 11</p>', 'Desc term rich text 11',0);
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-Hold-101', '<p>Hold Desc 101</p>', 'Hold Desc 101',0);
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-Hold-201', '<p>Hold Desc for deletion test</p>', 'Hold Desc for deletion test',0);


-- AtpEntity
CREATE TABLE KSEN_ATP
(
   ID varchar2(255) PRIMARY KEY NOT NULL,
   OBJ_ID varchar2(36),
   VER_NBR decimal(19),
   CREATEID varchar2(255),
   CREATETIME timestamp,
   UPDATEID varchar2(255),
   UPDATETIME timestamp,
   END_DT timestamp,
   NAME varchar2(255),
   START_DT timestamp,
   ATP_STATE_ID varchar2(255) NOT NULL,
   ATP_TYPE_ID varchar2(255) NOT NULL,
   RT_DESCR_ID varchar2(255)
);

CREATE TABLE KSEN_ATP_ATTR
(
   ID varchar2(255) PRIMARY KEY NOT NULL,
   OBJ_ID varchar2(36),
   ATTR_KEY varchar2(255),
   ATTR_VALUE varchar2(2000),
   OWNER varchar2(255)
);

INSERT INTO KSEN_ATP (ID, NAME, START_DT, END_DT, ATP_TYPE_ID, ATP_STATE_ID, RT_DESCR_ID, VER_NBR) VALUES ('testAtpId1', 'testAtp1', to_timestamp('2000-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-12-31 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.atp.type.AcademicCalendar', 'kuali.atp.state.Draft', 'RICHTEXT-101', 0);
INSERT INTO KSEN_ATP (ID, NAME, START_DT, END_DT, ATP_TYPE_ID, ATP_STATE_ID, RT_DESCR_ID, VER_NBR) VALUES ('testAtpId2', 'testAtp2', to_timestamp('2000-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-12-31 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.atp.type.CampusCalendar', 'kuali.atp.state.Draft', 'RICHTEXT-102', 0);
INSERT INTO KSEN_ATP (ID, NAME, START_DT, END_DT, ATP_TYPE_ID, ATP_STATE_ID, RT_DESCR_ID, VER_NBR) VALUES ('testDeleteAtpId1', 'testDeleteAtp1', to_timestamp('2000-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-12-31 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.atp.type.CampusCalendar', 'kuali.atp.state.Draft', 'RICHTEXT-103', 0);
INSERT INTO KSEN_ATP (ID, NAME, START_DT, END_DT, ATP_TYPE_ID, ATP_STATE_ID, RT_DESCR_ID, VER_NBR) VALUES ('testDeleteAtpId2', 'testDeleteAtp2', to_timestamp('2000-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-12-31 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.atp.type.CampusCalendar', 'kuali.atp.state.Draft', 'RICHTEXT-104', 0);
INSERT INTO KSEN_ATP (ID, NAME, START_DT, END_DT, ATP_TYPE_ID, ATP_STATE_ID, RT_DESCR_ID, VER_NBR) VALUES ('testTermId1', 'testTerm1', to_timestamp('2000-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-12-31 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.atp.type.Fall', 'kuali.atp.state.Draft', 'RICHTEXT-201', 0);
INSERT INTO KSEN_ATP (ID, NAME, START_DT, END_DT, ATP_TYPE_ID, ATP_STATE_ID, RT_DESCR_ID, VER_NBR) VALUES ('testTermId2', 'testTerm2', to_timestamp('2000-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-12-31 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.atp.type.Spring', 'kuali.atp.state.Draft', 'RICHTEXT-301', 0);

--  Term Atps for testing
INSERT INTO KSEN_ATP (ID, NAME, START_DT, END_DT, ATP_TYPE_ID, ATP_STATE_ID, RT_DESCR_ID, VER_NBR) VALUES ('termRelationTestingTerm1', 'testingTerm1', to_timestamp('2000-09-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2000-12-31 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.atp.type.Fall', 'kuali.atp.state.Draft', 'RICHTEXT-TRT-1', 0);
INSERT INTO KSEN_ATP (ID, NAME, START_DT, END_DT, ATP_TYPE_ID, ATP_STATE_ID, RT_DESCR_ID, VER_NBR) VALUES ('termRelationTestingTerm2', 'testingTerm2', to_timestamp('2001-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2001-05-31 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.atp.type.Spring', 'kuali.atp.state.Draft', 'RICHTEXT-TRT-2', 0);
INSERT INTO KSEN_ATP (ID, NAME, START_DT, END_DT, ATP_TYPE_ID, ATP_STATE_ID, RT_DESCR_ID, VER_NBR) VALUES ('termRelationTestingTerm3', 'testingTerm3', to_timestamp('2000-09-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2000-12-31 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.atp.type.Fall', 'kuali.atp.state.Official', 'RICHTEXT-TRT-7', 0);
INSERT INTO KSEN_ATP (ID, NAME, START_DT, END_DT, ATP_TYPE_ID, ATP_STATE_ID, RT_DESCR_ID, VER_NBR) VALUES ('termRelationTestingTerm4', 'testingTerm4', to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2011-05-31 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.atp.type.HalfFall1', 'kuali.atp.state.Draft', 'RICHTEXT-TRT-8', 0);
INSERT INTO KSEN_ATP (ID, NAME, START_DT, END_DT, ATP_TYPE_ID, ATP_STATE_ID, RT_DESCR_ID, VER_NBR) VALUES ('termRelationTestingTermDelete', 'testingTermDelete', to_timestamp('2031-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2031-05-31 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.atp.type.HalfFall1', 'kuali.atp.state.Draft', 'RICHTEXT-TRT-9', 0);
INSERT INTO KSEN_ATP (ID, NAME, START_DT, END_DT, ATP_TYPE_ID, ATP_STATE_ID, RT_DESCR_ID, VER_NBR) VALUES ('termRelationTestingTerm5', 'testingTerm3', to_timestamp('2000-09-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2000-12-31 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.atp.type.Fall', 'kuali.atp.state.Official', 'RICHTEXT-TRT-10', 0);
INSERT INTO KSEN_ATP (ID, NAME, START_DT, END_DT, ATP_TYPE_ID, ATP_STATE_ID, RT_DESCR_ID, VER_NBR) VALUES ('termRelationTestingTerm6', 'testingTerm4', to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2011-05-31 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.atp.type.HalfFall2', 'kuali.atp.state.Draft', 'RICHTEXT-TRT-11', 0);

INSERT INTO KSEN_ATP (ID, NAME, START_DT, END_DT, ATP_TYPE_ID, ATP_STATE_ID, RT_DESCR_ID, VER_NBR) VALUES ('termRelationTestingAcal1', 'testingAcal1', to_timestamp('2000-09-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2001-06-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.atp.type.AcademicCalendar', 'kuali.atp.state.Draft', 'RICHTEXT-TRT-3', 0);
INSERT INTO KSEN_ATP (ID, NAME, START_DT, END_DT, ATP_TYPE_ID, ATP_STATE_ID, RT_DESCR_ID, VER_NBR) VALUES ('termRelationTestingAcal2', 'testingAcal2', to_timestamp('2001-09-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2002-06-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.atp.type.AcademicCalendar', 'kuali.atp.state.Draft', 'RICHTEXT-TRT-4', 0);


--  AtpAtpRelationEntity
CREATE TABLE KSEN_ATPATP_RELTN
(
   ID varchar2(255) PRIMARY KEY NOT NULL,
   OBJ_ID varchar2(36),
   VER_NBR decimal(19),
   CREATEID varchar2(255),
   CREATETIME timestamp,
   UPDATEID varchar2(255),
   UPDATETIME timestamp,
   EFF_DT timestamp,
   EXPIR_DT timestamp,
   ATP_ID varchar2(255),
   ATP_STATE_ID varchar2(255),
   ATP_RELTN_TYPE_ID varchar2(255),
   RELATED_ATP_ID varchar2(255)
);

INSERT INTO KSEN_ATPATP_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, ATP_STATE_ID, ATP_ID, ATP_RELTN_TYPE_ID, RELATED_ATP_ID) VALUES ('ATPATPREL-1', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.atp.atp.relation.state.active', 'testAtpId1', 'kuali.atp.atp.relation.associated', 'testAtpId2');
INSERT INTO KSEN_ATPATP_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, ATP_STATE_ID, ATP_ID, ATP_RELTN_TYPE_ID, RELATED_ATP_ID) VALUES ('ATPATPREL-2', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.atp.atp.relation.state.active', 'testAtpId1', 'kuali.atp.atp.relation.includes', 'testTermId1');
INSERT INTO KSEN_ATPATP_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, ATP_STATE_ID, ATP_ID, ATP_RELTN_TYPE_ID, RELATED_ATP_ID) VALUES ('ATPATPREL-3', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.atp.atp.relation.state.active', 'testTermId1', 'kuali.atp.atp.relation.includes', 'testTermId2');
INSERT INTO KSEN_ATPATP_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, ATP_STATE_ID, ATP_ID, ATP_RELTN_TYPE_ID, RELATED_ATP_ID) VALUES ('ATPATPREL-4', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.atp.atp.relation.state.active', 'testAtpId1', 'kuali.atp.atp.relation.includes', 'testDeleteAtpId1');
INSERT INTO KSEN_ATPATP_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, ATP_STATE_ID, ATP_ID, ATP_RELTN_TYPE_ID, RELATED_ATP_ID) VALUES ('ATPATPREL-5', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.atp.atp.relation.state.active', 'testAtpId1', 'kuali.atp.atp.relation.includes', 'testDeleteAtpId2');

CREATE TABLE KSEN_ATPATP_RELTN_ATTR
(
   ID varchar2(255) PRIMARY KEY NOT NULL,
   OBJ_ID varchar2(36),
   ATTR_KEY varchar2(255),
   ATTR_VALUE varchar2(2000),
   OWNER varchar2(255)
);

--  MilestoneEntity
CREATE TABLE KSEN_MSTONE
(
   ID varchar2(255) PRIMARY KEY NOT NULL,
   OBJ_ID varchar2(36),
   VER_NBR decimal(19),
   CREATEID varchar2(255),
   CREATETIME timestamp,
   UPDATEID varchar2(255),
   UPDATETIME timestamp,
   END_DT timestamp,
   IS_ALL_DAY decimal(1),
   IS_DATE_RANGE decimal(1),
   NAME varchar2(255),
   START_DT timestamp,
   MILESTONE_STATE_ID varchar2(255),
   MILESTONE_TYPE_ID varchar2(255),
   RT_DESCR_ID varchar2(255)
);

INSERT INTO KSEN_MSTONE(ID, NAME, START_DT, END_DT, MILESTONE_TYPE_ID, MILESTONE_STATE_ID, IS_ALL_DAY, IS_DATE_RANGE, RT_DESCR_ID, VER_NBR) values ('testId', 'testId', to_timestamp('2011-07-10 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2011-07-20 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.atp.milestone.AdvanceRegistrationPeriod', 'kuali.milestone.state.Draft', 0, 1, 'RICHTEXT-105', 0);
INSERT INTO KSEN_MSTONE(ID, NAME, START_DT, END_DT, MILESTONE_TYPE_ID, MILESTONE_STATE_ID, IS_ALL_DAY, IS_DATE_RANGE, RT_DESCR_ID, VER_NBR) values ('testId2', 'testId2', to_timestamp('2011-08-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2011-10-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.atp.milestone.RegistrationPeriod', 'kuali.milestone.state.Draft', 0, 1, 'RICHTEXT-106', 0);
INSERT INTO KSEN_MSTONE(ID, NAME, START_DT, END_DT, MILESTONE_TYPE_ID, MILESTONE_STATE_ID, IS_ALL_DAY, IS_DATE_RANGE, RT_DESCR_ID, VER_NBR) values ('testId3', 'testId3', to_timestamp('2011-11-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), null, 'kuali.atp.milestone.DropDate', 'kuali.milestone.state.Draft', 1, 0, 'RICHTEXT-107', 0);
INSERT INTO KSEN_MSTONE(ID, NAME, START_DT, END_DT, MILESTONE_TYPE_ID, MILESTONE_STATE_ID, IS_ALL_DAY, IS_DATE_RANGE, RT_DESCR_ID, VER_NBR) values ('testDeleteId', 'testDeleteId', to_timestamp('2011-11-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), null, 'kuali.atp.milestone.RegistrationPeriod', 'kuali.milestone.state.Draft', 0, 0, 'RICHTEXT-108', 0);

--  AtpMilestoneRelationEntity
CREATE TABLE KSEN_ATPMSTONE_RELTN
(
   ID varchar2(255) PRIMARY KEY NOT NULL,
   OBJ_ID varchar2(36),
   VER_NBR decimal(19),
   CREATEID varchar2(255),
   CREATETIME timestamp,
   UPDATEID varchar2(255),
   UPDATETIME timestamp,
   EFF_DT timestamp,
   EXPIR_DT timestamp,
   ATP_ID varchar2(255),
   ATP_STATE_ID varchar2(255),
   AM_RELTN_TYPE_ID varchar2(255),
   MSTONE_ID varchar2(255)
);

CREATE TABLE KSEN_ATPMSOTNE_RELTN_ATTR
(
   ID varchar2(255) PRIMARY KEY NOT NULL,
   OBJ_ID varchar2(36),
   ATTR_KEY varchar2(255),
   ATTR_VALUE varchar2(2000),
   OWNER varchar2(255)
);

INSERT INTO KSEN_ATPMSTONE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, ATP_STATE_ID, ATP_ID, MSTONE_ID, AM_RELTN_TYPE_ID) values ('ATPMSTONEREL-1', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.atp.milestone.relation.state.active', 'testAtpId1', 'testId', 'kuali.atp.milestone.relation.owns');
INSERT INTO KSEN_ATPMSTONE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, ATP_STATE_ID, ATP_ID, MSTONE_ID, AM_RELTN_TYPE_ID) values ('ATPMSTONEREL-2', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.atp.milestone.relation.state.active', 'testAtpId2', 'testId2', 'kuali.atp.milestone.relation.owns');
INSERT INTO KSEN_ATPMSTONE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, ATP_STATE_ID, ATP_ID, MSTONE_ID, AM_RELTN_TYPE_ID) values ('ATPMSTONEREL-3', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.atp.milestone.relation.state.active', 'testDeleteAtpId1', 'testId', 'kuali.atp.milestone.relation.owns');
INSERT INTO KSEN_ATPMSTONE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, ATP_STATE_ID, ATP_ID, MSTONE_ID, AM_RELTN_TYPE_ID) values ('ATPMSTONEREL-4', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.atp.milestone.relation.state.active', 'testDeleteAtpId2', 'testId2', 'kuali.atp.milestone.relation.owns');

-- StateEntity
CREATE TABLE KSEN_COMM_STATE
(
   ID varchar2(255) PRIMARY KEY NOT NULL,
   OBJ_ID varchar2(36),
   VER_NBR decimal(19),
   CREATEID varchar2(255),
   CREATETIME timestamp,
   UPDATEID varchar2(255),
   UPDATETIME timestamp,
   DESCR varchar2(255),
   EFF_DT timestamp,
   EXPIR_DT timestamp,
   NAME varchar2(255),
   PROCESS_KEY varchar2(255)
);

INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.atp.state.Draft', 'Draft', 'kuali.atp.process', 'Indicates that this Time Period is just tentative', 0);
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.atp.state.Official', 'Official', 'kuali.atp.process', 'Indicates that this Time Period has been established', 0);

INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.milestone.state.Draft', 'Draft', 'kuali.milestone.process', 'Indicates that this milestone is just tentative', 0);
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.milestone.state.Official', 'Official', 'kuali.milestone.process', 'Indicates that this milestone has been established', 0);

INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.atp.atp.relation.state.active', 'Active', 'kuali.atp.atp.relation.process', 'Indicates that this Atp-Atp relation is active', 0);
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.atp.atp.relation.state.inactive', 'Inactive', 'kuali.atp.atp.relation.process', 'Indicates that this Atp-Atp relation is inactive', 0);

INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.atp.milestone.relation.state.active', 'Active', 'kuali.atp.milestone.relation.process', 'Indicates that this Atp-Milestone relation is active', 0);
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.atp.milestone.relation.state.inactive', 'Inactive', 'kuali.atp.milestone.relation.process', 'Indicates that this Atp-Milestone relation is inactive', 0);

INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.hold.restriction.state.active', 'Active', 'kuali.hold.restriction.process', 'This restriction is active and should be enforced', 0);
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.hold.restriction.state.inactive', 'Inactive', 'kuali.hold.restriction.process', 'The restriction is inactive and should not be enforced', 0);

INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.hold.issue.state.active', 'Active', 'kuali.hold.issue.process', 'This issue is active and can be attached to holds ', 0);
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.hold.issue.state.inactive', 'Inactive', 'kuali.hold.issue.process', 'The hold is inactive an cannot be attached to new holds ', 0);

INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.hold.state.active', 'Active', 'kuali.hold.process.student', 'This hold is active and should be enforced', 0);
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.hold.state.released', 'Released', 'kuali.hold.process.student', 'The hold was active and the issue has been resolved', 0);
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.hold.state.canceled', 'Canceled', 'kuali.hold.process.student', 'The hold was canceled or removed because it was originally put on in error', 0);

--LUI states
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.lui.state.draft', 'Draft', 'kuali.course.offering.process', 'Proposed for Departmental Planning purposed', 0);
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.lui.state.submitted', 'Submitted', 'kuali.course.offering.process', 'Submitted to central admin for approval (or scheduling)', 0);
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.lui.state.approved', 'Approved', 'kuali.course.offering.process', 'Approved and ready to be scheduled', 0);

INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.lui.lui.relation.state.active', 'Active', 'kuali.lui.lui.relationship.process', 'The relationship between the two LUIs is active ', 0);
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.lui.lui.relation.state.inactive', 'Inactive', 'kuali.lui.lui.relationship.process', 'The relationship between the two LUIs is in-active ', 0);


-- StateProcessEntity
CREATE TABLE KSEN_STATE_PROCESS
(
   ID varchar2(255) PRIMARY KEY NOT NULL,
   OBJ_ID varchar2(36),
   VER_NBR decimal(19),
   CREATEID varchar2(255),
   CREATETIME timestamp,
   UPDATEID varchar2(255),
   UPDATETIME timestamp,
   DESCR varchar2(255),
   EFF_DT timestamp,
   EXPIR_DT timestamp,
   NAME varchar2(255)
);

INSERT INTO KSEN_STATE_PROCESS (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.atp.process', 'kuali.atp.process', 'kuali atp state process', 0);
INSERT INTO KSEN_STATE_PROCESS (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.milestone.process', 'kuali.milestone.process', 'kuali milestone state process', 0);
INSERT INTO KSEN_STATE_PROCESS (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.atp.atp.relation.process', 'kuali.atp.atp.relation.process', 'kuali atp-atp relation state process', 0);
INSERT INTO KSEN_STATE_PROCESS (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.atp.milestone.relation.process', 'kuali.atp.milestone.relation.process', 'kuali atp-milestone relation state process', 0);

-- HOLD Process
INSERT INTO KSEN_STATE_PROCESS (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.hold.restriction.process', 'kuali.hold.restriction.process', 'kuali hold restriction process', 0);

--StateProcessEntity for Hold Process
INSERT INTO KSEN_STATE_PROCESS (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.hold.issue.process', 'kuali.hold.issue.process', 'kuali hold issue process', 0);
INSERT INTO KSEN_STATE_PROCESS (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.hold.process.student', 'kuali.hold.process.student', 'kuali hold process student', 0);

INSERT INTO KSEN_STATE_PROCESS (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.course.offering.process', 'kuali.course.offering.process', 'kuali course offering process', 0);
INSERT INTO KSEN_STATE_PROCESS (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.lui.capacity.process', 'kuali.lui.capacity.process', 'kuali lui capacity process', 0);
INSERT INTO KSEN_STATE_PROCESS (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.lui.lui.relationship.process', 'kuali.lui.lui.relationship.process', 'kuali lui-lui relationship process', 0);


-- StateProcessRelationEntity
CREATE TABLE KSEN_STATEPROCESS_RELTN
(
   ID varchar2(255) PRIMARY KEY NOT NULL,
   OBJ_ID varchar2(36),
   VER_NBR decimal(19),
   CREATEID varchar2(255),
   CREATETIME timestamp,
   UPDATEID varchar2(255),
   UPDATETIME timestamp,
   NEXT_STATEKEY varchar2(255),
   PRIOR_STATEKEY varchar2(255),
   PROCESS_KEY varchar2(255)
);

INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-1', 'kuali.atp.process', null, 'kuali.atp.state.Draft', 0);
INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-2', 'kuali.atp.process', 'kuali.atp.state.Draft', 'kuali.atp.state.Official', 0);

-- HOLD StateProcessRelation
INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-3', 'kuali.hold.restriction.process', null, 'kuali.hold.restriction.state.active', 0);
INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-4', 'kuali.hold.restriction.process', 'kuali.hold.restriction.state.active', 'kuali.hold.restriction.state.inactive', 0);

--StateProcessRelationEntity
INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-5', 'kuali.hold.issue.process', null, 'kuali.hold.issue.state.active', 0);
INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-6', 'kuali.hold.issue.process', 'kuali.hold.issue.state.active', 'kuali.hold.issue.state.inactive', 0);

INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-7', 'kuali.hold.process.student', null, 'kuali.hold.state.active', 0);
INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-8', 'kuali.hold.process.student', 'kuali.hold.state.active', 'kuali.hold.state.released', 0);
INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-9', 'kuali.hold.process.student', 'kuali.hold.state.released', 'kuali.hold.state.canceled', 0);

INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-10', 'kuali.course.offering.process', null, 'kuali.lui.state.draft', 0);
INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-11', 'kuali.course.offering.process', 'kuali.lui.state.draft', 'kuali.lui.state.submitted', 0);
INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-12', 'kuali.course.offering.process', 'kuali.lui.state.submitted', 'kuali.lui.state.approved', 0);

INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-13', 'kuali.lui.lui.relationship.process', null, 'kuali.lui.lui.relation.state.active', 0);
INSERT INTO KSEN_STATEPROCESS_RELTN(ID, PROCESS_KEY, PRIOR_STATEKEY, NEXT_STATEKEY, VER_NBR)VALUES('PROCESS-14', 'kuali.lui.lui.relationship.process', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.state.inactive', 0);


--  TypeTypeRelationEntity
CREATE TABLE KSEN_TYPETYPE_RELTN
(
   ID varchar2(255) PRIMARY KEY NOT NULL,
   OBJ_ID varchar2(36),
   VER_NBR decimal(19),
   CREATEID varchar2(255),
   CREATETIME timestamp,
   UPDATEID varchar2(255),
   UPDATETIME timestamp,
   EFF_DT timestamp,
   EXPIR_DT timestamp,
   NAME varchar2(255),
   OWNER_TYPE_ID varchar2(255),
   RANK decimal(10),
   RELATED_TYPE_ID varchar2(255),
   TYPETYPE_RELATION_TYPE varchar2(255),
   RT_DESCR_ID varchar2(255)
);

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('TYPETYPEREL-0', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.atp.atp.relation.precedes', 'kuali.atp.type.Fall', 'kuali.atp.type.Spring', 0, 'Fall precedes Spring');
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('TYPETYPEREL-1', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.contains', 'kuali.atp.type.AY', 'kuali.atp.type.Fall', 0, 'Academic year contains semester');
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('TYPETYPEREL-2', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.contains', 'kuali.atp.type.AY', 'kuali.atp.type.Spring', 1, 'Academic year contains semester');
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('TYPETYPEREL-3', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.contains', 'kuali.atp.type.AY', 'kuali.atp.type.Summer', 2, 'Academic year contains semester');
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('TYPETYPEREL-4', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.Holiday', 'kuali.atp.type.SpringBreak' , 0, 'Spring break is a holiday');
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('TYPETYPEREL-5', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.Holiday', 'kuali.atp.type.Thanksgiving' , 0, 'Thanksgiving is a holiday');
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.1', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Fall' , 0, 'kuali.atp.type.Fall is a type of Term');
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.2', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.HalfFall1' , 0, 'kuali.atp.type.HalfFall1 is a type of Term');
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.3', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.HalfFall2' , 0, 'kuali.atp.type.HalfFall2 is a type of Term');
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.4', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.HalfSpring1' , 0, 'kuali.atp.type.HalfSpring1 is a type of Term');
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.5', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.HalfSpring2' , 0, 'kuali.atp.type.HalfSpring2 is a type of Term');
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.6', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Mini-mester1A' , 0, 'kuali.atp.type.Mini-mester1A is a type of Term');
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.7', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Mini-mester1B' , 0, 'kuali.atp.type.Mini-mester1B is a type of Term');
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.8', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Mini-mester2C' , 0, 'kuali.atp.type.Mini-mester2C is a type of Term');
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.9', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Mini-mester2D' , 0, 'kuali.atp.type.Mini-mester2D is a type of Term');
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.10', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Session1' , 0, 'kuali.atp.type.Session1 is a type of Term');
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.11', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Session2' , 0, 'kuali.atp.type.Session2 is a type of Term');
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.12', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.SessionG1' , 0, 'kuali.atp.type.SessionG1 is a type of Term');
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.13', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.SessionG2' , 0, 'kuali.atp.type.SessionG2 is a type of Term');
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.14', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Spring' , 0, 'kuali.atp.type.Spring is a type of Term');
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.15', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.SpringBreak' , 0, 'kuali.atp.type.SpringBreak is a type of Term');
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.16', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Summer' , 0, 'kuali.atp.type.Summer is a type of Term');
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.17', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.SummerEve' , 0, 'kuali.atp.type.SummerEve is a type of Term');
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.18', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Winter' , 0, 'kuali.atp.type.Winter is a type of Term');
INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.19', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Adhoc' , 0, 'kuali.atp.type.Adhoc is a type of Term');

--  Allowed type relations
INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.1', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.AcademicCalendar', 'kuali.atp.type.FallSpring', 1, 'AcademicCalendar can contain FallSpring');
INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.2', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.AcademicCalendar', 'kuali.atp.type.Fall', 2, 'AcademicCalendar can contain Fall');
INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.3', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.Fall', 'kuali.atp.type.HalfFall1', 1, 'Fall can contain HalfFall1');
INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.4', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.Fall', 'kuali.atp.type.HalfFall2', 2, 'Fall can contain HalfFall2');
INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.5', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.AcademicCalendar', 'kuali.atp.type.Winter', 3, 'AcademicCalendar can contain Winter');
INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.6', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.AcademicCalendar', 'kuali.atp.type.Spring', 4, 'AcademicCalendar can contain Spring');
INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.7', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.Spring', 'kuali.atp.type.HalfSpring1', 1, 'Spring can contain HalfSpring1');
INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.8', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.Spring', 'kuali.atp.type.SpringBreak', 2, 'Spring can contain SpringBreak');
INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.9', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.Spring', 'kuali.atp.type.HalfSpring2', 3, 'Spring can contain HalfSpring2');
INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.10', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.AcademicCalendar', 'kuali.atp.type.Session1', 5, 'AcademicCalendar can contain Session1');
INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.11', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.Session1', 'kuali.atp.type.Mini-mester1A', 1, 'Session1 can contain Mini-mester1A');
INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.12', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.Session1', 'kuali.atp.type.Mini-mester1B', 2, 'Session1 can contain Mini-mester1B');
INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.13', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.Summer', 'kuali.atp.type.Session2', 2, 'Summer can contain Session2');
INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.14', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.Session2', 'kuali.atp.type.Mini-mester2C', 1, 'Session2 can contain Mini-mester2C');
INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.15', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.Session2', 'kuali.atp.type.Mini-mester2D', 2, 'Session2 can contain Mini-mester2D');
INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.16', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.AcademicCalendar', 'kuali.atp.type.SummerEve', 6, 'AcademicCalendar can contain SummerEve');
INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.17', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.SummerEve', 'kuali.atp.type.SessionG1', 1, 'SummerEve can contain SessionG1');
INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.18', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.SummerEve', 'kuali.atp.type.SessionG2', 2, 'SummerEve can contain SessionG2');

INSERT INTO KSEN_MSTONE(ID, NAME, START_DT, END_DT, MILESTONE_TYPE_ID, MILESTONE_STATE_ID, IS_ALL_DAY, IS_DATE_RANGE, RT_DESCR_ID, VER_NBR) values ('testKeyDate1', 'testKeyDate1', to_timestamp('2001-09-10 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2001-09-20 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.atp.milestone.AdvanceRegistrationPeriod', 'kuali.milestone.state.Draft', 0, 1, 'RICHTEXT-TRT-5', 0);
INSERT INTO KSEN_MSTONE(ID, NAME, START_DT, END_DT, MILESTONE_TYPE_ID, MILESTONE_STATE_ID, IS_ALL_DAY, IS_DATE_RANGE, RT_DESCR_ID, VER_NBR) values ('testKeyDate2', 'testKeyDate2', to_timestamp('2001-08-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2001-10-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.atp.milestone.RegistrationPeriod', 'kuali.milestone.state.Draft', 0, 1, 'RICHTEXT-TRT-6', 0);

--  Term to Term AtpAtpRelations for testing
INSERT INTO KSEN_ATPATP_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, ATP_STATE_ID, ATP_ID, ATP_RELTN_TYPE_ID, RELATED_ATP_ID) VALUES ('termRelationTestingRel-TermTerm-1', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.atp.atp.relation.state.active', 'termRelationTestingTerm1', 'kuali.atp.atp.relation.includes', 'termRelationTestingTerm2');
INSERT INTO KSEN_ATPATP_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, ATP_STATE_ID, ATP_ID, ATP_RELTN_TYPE_ID, RELATED_ATP_ID) VALUES ('termRelationTestingRel-TermTerm-2', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.atp.atp.relation.state.active', 'termRelationTestingTerm3', 'kuali.atp.atp.relation.includes', 'termRelationTestingTerm4');

--  Acal to Term AtpAtpRelations for testing
INSERT INTO KSEN_ATPATP_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, ATP_STATE_ID, ATP_ID, ATP_RELTN_TYPE_ID, RELATED_ATP_ID) VALUES ('termRelationTestingRel-AcalTerm-1', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.atp.atp.relation.state.active', 'termRelationTestingAcal1', 'kuali.atp.atp.relation.includes', 'termRelationTestingTerm1');
INSERT INTO KSEN_ATPATP_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, ATP_STATE_ID, ATP_ID, ATP_RELTN_TYPE_ID, RELATED_ATP_ID) VALUES ('termRelationTestingRel-AcalTerm-2', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.atp.atp.relation.state.active', 'termRelationTestingAcal2', 'kuali.atp.atp.relation.includes', 'termRelationTestingTerm2');

--  Term to KeyDate AtpMilestoneRelations for testing
INSERT INTO KSEN_ATPMSTONE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, ATP_STATE_ID, ATP_ID, MSTONE_ID, AM_RELTN_TYPE_ID) values ('termRelationTestingRel-TermDate-1', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.atp.milestone.relation.state.active', 'termRelationTestingTerm1', 'testKeyDate1', 'kuali.atp.milestone.relation.owns');

-- HoldRichTextEntity
CREATE TABLE KSEN_HOLD_RICH_TEXT
(
   ID varchar2(255) PRIMARY KEY NOT NULL,
   OBJ_ID varchar2(36),
   VER_NBR decimal(19),
   FORMATTED varchar2(2000),
   PLAIN varchar2(2000)
);

INSERT INTO KSEN_HOLD_RICH_TEXT (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('Issue-1-Desc', '<p>Issue Desc 101</p>', 'Issue Desc 101',0);
INSERT INTO KSEN_HOLD_RICH_TEXT (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('Issue-2-Desc', '<p>Issue Desc for deletion test</p>', 'Issue Desc for deletion test',0);
INSERT INTO KSEN_HOLD_RICH_TEXT (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-Hold-101', '<p>Hold Desc 101</p>', 'Hold Desc 101',0);
INSERT INTO KSEN_HOLD_RICH_TEXT (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-Hold-201', '<p>Hold Desc for deletion test</p>', 'Hold Desc for deletion test',0);
INSERT INTO KSEN_HOLD_RICH_TEXT (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-Hold-301', '<p>Hold Desc student</p>', 'Hold Desc student',0);
INSERT INTO KSEN_HOLD_RICH_TEXT (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-Hold-401', '<p>Hold Desc instructor</p>', 'Hold Desc instructor',0);

-- Hold States
CREATE TABLE KSEN_STATE_ATTR
(
   ID varchar2(255) PRIMARY KEY NOT NULL,
   OBJ_ID varchar2(36),
   ATTR_KEY varchar2(255),
   ATTR_VALUE varchar2(2000),
   OWNER varchar2(255)
);

--HoldTypeEntity
CREATE TABLE KSEN_HOLD_TYPE
(
   TYPE_KEY varchar2(255) PRIMARY KEY NOT NULL,
   OBJ_ID varchar2(36),
   VER_NBR decimal(19),
   TYPE_DESC varchar2(2000),
   EFF_DT timestamp,
   EXPIR_DT timestamp,
   NAME varchar2(255)
);

CREATE TABLE KSEN_HOLD_TYPE_ATTR
(
   ID varchar2(255) PRIMARY KEY NOT NULL,
   OBJ_ID varchar2(36),
   ATTR_KEY varchar2(255),
   ATTR_VALUE varchar2(2000),
   OWNER varchar2(255)
);

INSERT INTO KSEN_HOLD_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.hold.restriction.type.registration', 'Registration Restriction', 'Cannot register', 0);
INSERT INTO KSEN_HOLD_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.hold.restriction.type.receive.diploma', 'Receive Diploma Restriction', 'Cannot get physical diploma (don''t mail or allow to be picked up', 0);
INSERT INTO KSEN_HOLD_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.hold.restriction.type.add.drop.class', 'Add/Drop Restriction', 'Cannot change registration', 0);
INSERT INTO KSEN_HOLD_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.hold.restriction.type.request.transcript', 'Transcript Restriction', 'Cannot order a transcript', 0);
INSERT INTO KSEN_HOLD_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.hold.restriction.type.award.degree', 'Award Degree Restriction', 'Cannot award degree', 0);
INSERT INTO KSEN_HOLD_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.hold.restriction.type.add.charges', 'Add Charges Restriction', 'Cannot submit new charges to the student''s account', 0);
INSERT INTO KSEN_HOLD_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.hold.restriction.type.library.privileges', 'Library Privileges Restriction', 'Cannot use library', 0);
INSERT INTO KSEN_HOLD_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.hold.restriction.type.dorm.access', 'Dorm Access Restriction', 'Student barred from access to the dorm', 0);
INSERT INTO KSEN_HOLD_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.hold.restriction.type.update.program', 'Update Program', 'Blocks a student from changing her degree program', 0);
INSERT INTO KSEN_HOLD_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.hold.restriction.type.access.grades', 'Access Grades', 'Restricts students access to her grades', 0);
INSERT INTO KSEN_HOLD_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.hold.restriction.type.bursar.refund', 'Bursar Refund', 'Stops a student from receiving a refund even if they have a creadit balance', 0);
INSERT INTO KSEN_HOLD_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.hold.restriction.type.verification', 'Verification', 'Blocks a student from having a request to verify her attendance or degrees processed', 0);
INSERT INTO KSEN_HOLD_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.hold.restriction.type.status', 'Status', 'Student is not allowed to register, be on campus, or use any campus facilities or resources', 0);
INSERT INTO KSEN_HOLD_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.hold.issue.type.residency', 'Residency', 'Indicates there is a discrepancy or incomplete information to establish residency status', 0);
INSERT INTO KSEN_HOLD_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.hold.issue.type.unpaid.fee', 'Unpaid fees', 'Indicates that this issue has to do with fees that were incurred but never paid', 0);
INSERT INTO KSEN_HOLD_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.hold.type.student', 'Student Hold', 'This is a hold that applies to students', 0);
INSERT INTO KSEN_HOLD_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.hold.type.instructor', 'Instructor Hold', 'This is a hold that applies to instructors', 0);

--RestrictionEntity
CREATE TABLE KSEN_RESTRICTION
(
   ID varchar2(255) PRIMARY KEY NOT NULL,
   OBJ_ID varchar2(36),
   VER_NBR decimal(19),
   CREATEID varchar2(255),
   CREATETIME timestamp,
   UPDATEID varchar2(255),
   UPDATETIME timestamp,
   NAME varchar2(255),
   RT_DESCR_ID varchar2(255),
   STATE_ID varchar2(255) NOT NULL,
   TYPE_ID varchar2(255) NOT NULL
);

CREATE TABLE KSEN_RESTRICTION_ATTR
(
   ID varchar2(255) PRIMARY KEY NOT NULL,
   OBJ_ID varchar2(36),
   ATTR_KEY varchar2(255),
   ATTR_VALUE varchar2(2000),
   OWNER varchar2(255)
);

INSERT INTO KSEN_RESTRICTION (ID, NAME, TYPE_ID, STATE_ID, RT_DESCR_ID, VER_NBR) VALUES ('Hold-Restriction-1', 'Restriction one', 'kuali.hold.restriction.type.registration', 'kuali.hold.restriction.state.active', 'RICHTEXT-Hold-101', 0);
INSERT INTO KSEN_RESTRICTION (ID, NAME, TYPE_ID, STATE_ID, RT_DESCR_ID, VER_NBR) VALUES ('Hold-Restriction-2', 'Restriction two', 'kuali.hold.restriction.type.receive.diploma', 'kuali.hold.restriction.state.active', 'RICHTEXT-Hold-201', 0);

--IssueEntity
CREATE TABLE KSEN_ISSUE
(
   ID varchar2(255) PRIMARY KEY NOT NULL,
   OBJ_ID varchar2(36),
   VER_NBR decimal(19),
   CREATEID varchar2(255),
   CREATETIME timestamp,
   UPDATEID varchar2(255),
   UPDATETIME timestamp,
   NAME varchar2(255),
   ORG_ID varchar2(255),
   RT_DESCR_ID varchar2(255),
   STATE_ID varchar2(255),
   TYPE_ID varchar2(255) NOT NULL
);

CREATE TABLE KSEN_ISSUE_ATTR
(
   ID varchar2(255) PRIMARY KEY NOT NULL,
   OBJ_ID varchar2(36),
   ATTR_KEY varchar2(255),
   ATTR_VALUE varchar2(2000),
   OWNER varchar2(255)
);

INSERT INTO KSEN_ISSUE (ID, NAME, TYPE_ID, STATE_ID, ORG_ID, RT_DESCR_ID, VER_NBR) VALUES ('Hold-Issue-1', 'Issue one', 'kuali.hold.issue.type.residency', 'kuali.hold.issue.state.active', '102', 'Issue-1-Desc', 0);
INSERT INTO KSEN_ISSUE (ID, NAME, TYPE_ID, STATE_ID, ORG_ID, RT_DESCR_ID, VER_NBR) VALUES ('Hold-Issue-2', 'Issue two', 'kuali.hold.issue.type.residency', 'kuali.hold.issue.state.active', '102', 'Issue-2-Desc', 0);

--HoldEntity
CREATE TABLE KSEN_HOLD
(
   ID varchar2(255) PRIMARY KEY NOT NULL,
   OBJ_ID varchar2(36),
   VER_NBR decimal(19),
   CREATEID varchar2(255),
   CREATETIME timestamp,
   UPDATEID varchar2(255),
   UPDATETIME timestamp,
   EFF_DT timestamp,
   IS_OVERRIDABLE decimal(1),
   IS_WARNING decimal(1),
   NAME varchar2(255),
   PERS_ID varchar2(255),
   RELEASED_DT timestamp,
   RT_DESCR_ID varchar2(255),
   STATE_ID varchar2(255) NOT NULL,
   TYPE_ID varchar2(255) NOT NULL,
   ISSUE_ID varchar2(255) NOT NULL
);

CREATE TABLE KSEN_HOLD_ATTR
(
   ID varchar2(255) PRIMARY KEY NOT NULL,
   OBJ_ID varchar2(36),
   ATTR_KEY varchar2(255),
   ATTR_VALUE varchar2(2000),
   OWNER varchar2(255)
);

INSERT INTO KSEN_HOLD (ID, NAME, RT_DESCR_ID, IS_WARNING, IS_OVERRIDABLE, PERS_ID, ISSUE_ID, TYPE_ID, STATE_ID, EFF_DT, RELEASED_DT, VER_NBR) VALUES ('Hold-1', 'Hold one', 'RICHTEXT-Hold-301', 0, 1, '1', 'Hold-Issue-1', 'kuali.hold.type.student', 'kuali.hold.state.active', to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2011-12-31 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 0);
INSERT INTO KSEN_HOLD (ID, NAME, RT_DESCR_ID, IS_WARNING, IS_OVERRIDABLE, PERS_ID, ISSUE_ID, TYPE_ID, STATE_ID, EFF_DT, RELEASED_DT, VER_NBR) VALUES ('Hold-2', 'Hold two', 'RICHTEXT-Hold-401', 0, 1, '1', 'Hold-Issue-1', 'kuali.hold.type.instructor', 'kuali.hold.state.active', to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2011-12-31 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 0);

CREATE TABLE KSLP_LPR
(
   ID varchar2(255) PRIMARY KEY NOT NULL,
   OBJ_ID varchar2(36),
   VER_NBR decimal(19),
   CREATEID varchar2(255),
   CREATETIME timestamp,
   UPDATEID varchar2(255),
   UPDATETIME timestamp,
   EFFECTIVEDATE timestamp,
   EXPIRATIONDATE timestamp,
   LUIID varchar2(255),
   PERSONID varchar2(255),
   RELATION_STATE_ID varchar2(255),
   RELATION_TYPE_ID varchar2(255)
);

CREATE TABLE KSLP_LPR_ATTR
(
   ID varchar2(255) PRIMARY KEY NOT NULL,
   OBJ_ID varchar2(36),
   ATTR_KEY varchar2(255),
   ATTR_VALUE varchar2(2000),
   OWNER varchar2(255)
);

CREATE TABLE KSLP_LPR_STATE
(
   ID varchar2(255) PRIMARY KEY NOT NULL,
   OBJ_ID varchar2(36),
   VER_NBR decimal(19),
   CREATEID varchar2(255),
   CREATETIME timestamp,
   UPDATEID varchar2(255),
   UPDATETIME timestamp,
   DESCR varchar2(255),
   EFFECTIVEDATE timestamp,
   EXPIRATIONDATE timestamp,
   NAME varchar2(255)
);

CREATE TABLE KSLP_LPR_TYPE
(
   TYPE_KEY varchar2(255) PRIMARY KEY NOT NULL,
   OBJ_ID varchar2(36),
   VER_NBR decimal(19),
   TYPE_DESC varchar2(2000),
   EFF_DT timestamp,
   EXPIR_DT timestamp,
   NAME varchar2(255)
);

-- LuiPersonRelationStateEntity
-- - Course and Section - Student - LPR States
INSERT INTO KSLP_LPR_STATE (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.lpr.state.planned', 'Planned', 'The student plans on taking this course or program', 0);
INSERT INTO KSLP_LPR_STATE (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.lpr.state.registered', 'Registered', 'The student is officially registered for the course or section', 0);
INSERT INTO KSLP_LPR_STATE (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.lpr.state.not.paid', 'Fee Not Paid', 'The student has registered for the course by has not paid the fee', 0);
INSERT INTO KSLP_LPR_STATE (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.lpr.state.waitlisted', 'Waitlisted', 'The student attempted to join but has been put on the waitlist', 0);
INSERT INTO KSLP_LPR_STATE (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.lpr.state.dropped.early', 'Dropped', 'The student was registered but subsequently dropped the course or section within the normally allotted time period', 0);
INSERT INTO KSLP_LPR_STATE (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.lpr.state.dropped.late', 'Dropped Late', 'The student was registered but subsequently dropped the course or section past the normally allotted time period, typically resulting in a special grade or mark to so indicate', 0);
-- - Course and Section - Instructor - LPR States
-- - Program - Student - LPR States

-- LuiPersonRelationTypeEntity
-- - Course and Section Instructor - LPR Types
INSERT INTO KSLP_LPR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lpr.type.instructor.main', 'Main Instructor', 'Main instructor(s) responsible for course or section', 0);
INSERT INTO KSLP_LPR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lpr.type.instructor.assistant', 'Assistant Instructor', 'Person who assists the main instructor but is still considered an "instructor"', 0);
INSERT INTO KSLP_LPR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lpr.type.instructor.support', 'Support instructor', 'Persons who support the course but not in any official teaching role', 0);

-- - Course and Section Student LPR Types, 0
INSERT INTO KSLP_LPR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lpr.type.registrant', 'Registrant', 'Registrant who is taking the course', 0);

-- - Program LPR Types
INSERT INTO KSLP_LPR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lpr.enrollee', 'Enrollee', 'Enrollee in the program', 0);
INSERT INTO KSLP_LPR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lpr.advisor', 'Advisor', 'Advisor to students in the program', 0);

-- - Experiential Learning LPR Types
INSERT INTO KSLP_LPR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lpr.instructor.supervisor', 'Supervisor', 'Supervisor', 0);

-- - Tests and Exam LPR Types
INSERT INTO KSLP_LPR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lpr.exam.proctor', 'Proctor', 'Person who administers a test or examination', 0);

-- LuiPersonRelationEntity
INSERT INTO KSLP_LPR (ID, PERSONID, LUIID, EFFECTIVEDATE, EXPIRATIONDATE, RELATION_TYPE_ID, RELATION_STATE_ID, VER_NBR) VALUES ('testLprId1', 'testPersonId1', 'testLuiId1', to_timestamp('2000-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-12-31 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.lpr.type.registrant', 'kuali.lpr.state.registered', 0);

--LuiTypeEntity
CREATE TABLE KSEN_LUI_TYPE
(
   TYPE_KEY varchar2(255) PRIMARY KEY NOT NULL,
   OBJ_ID varchar2(36),
   VER_NBR decimal(19),
   TYPE_DESC varchar2(2000),
   EFF_DT timestamp,
   EXPIR_DT timestamp,
   NAME varchar2(255)
);

INSERT INTO KSEN_LUI_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.lui.type.course.offering', 'Course Offering', 'An offering of a course for a particular term', 0);
INSERT INTO KSEN_LUI_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.lui.type.registration.group', 'registration Group', 'The collection of activity offerings of a single course that are grouped together for registration purposes', 0);
INSERT INTO KSEN_LUI_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.lui.type.activity.offering.lecture', 'Lecture', 'Instructor presentation of course materials', 0);
INSERT INTO KSEN_LUI_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.lui.type.activity.offering.lab', 'Lab', 'Student working on projects in a defined laboratory space. Instructors are on-hand for students to ask questions and guidance', 0);

INSERT INTO KSEN_LUI_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.lui.lui.relation.associated', 'Associated', 'The first LUI is associated with the second LUI', 0);

-- LuiRichTextEntity
CREATE TABLE KSEN_LUI_RICH_TEXT
(
   ID varchar2(255) PRIMARY KEY NOT NULL,
   OBJ_ID varchar2(36),
   VER_NBR decimal(19),
   FORMATTED varchar2(2000),
   PLAIN varchar2(2000)
);

INSERT INTO KSEN_LUI_RICH_TEXT (ID, FORMATTED, PLAIN, VER_NBR) VALUES ('Lui-1-Desc', '<p>Lui Desc 101</p>', 'Lui Desc 101',0);
INSERT INTO KSEN_LUI_RICH_TEXT (ID, FORMATTED, PLAIN, VER_NBR) VALUES ('Lui-2-Desc', '<p>Lui Desc 201</p>', 'Lui Desc 201',0);
INSERT INTO KSEN_LUI_RICH_TEXT (ID, FORMATTED, PLAIN, VER_NBR) VALUES ('Lui-3-Desc', '<p>Lui Desc 301</p>', 'Lui Desc 301 for deletion',0);
INSERT INTO KSEN_LUI_RICH_TEXT (ID, FORMATTED, PLAIN, VER_NBR) VALUES ('Lui-4-Desc', '<p>Lui Desc 401</p>', 'Lui Desc 401 for deletion',0);

--LuiInstructorEntity
CREATE TABLE KSEN_LUI_INSTR
(
   ID varchar2(255) PRIMARY KEY NOT NULL,
   OBJ_ID varchar2(36),
   VER_NBR decimal(19),
   CREATEID varchar2(255),
   CREATETIME timestamp,
   UPDATEID varchar2(255),
   UPDATETIME timestamp,
   ORG_ID varchar2(255),
   PERCT_EFFT float(126),
   PERS_ID varchar2(255),
   PERS_OVRID varchar2(255)
);

INSERT INTO KSEN_LUI_INSTR (ID, ORG_ID, PERS_ID, PERS_OVRID, PERCT_EFFT, VER_NBR) VALUES ('LUI-INSTR-1', 'Org-1', 'Pers-1', 'Instr-1', 30.5, 0);

--LuiEntity
CREATE TABLE KSEN_LUI
(
   ID varchar2(255) PRIMARY KEY NOT NULL,
   OBJ_ID varchar2(36),
   VER_NBR decimal(19),
   CREATEID varchar2(255),
   CREATETIME timestamp,
   UPDATEID varchar2(255),
   UPDATETIME timestamp,
   ATP_ID varchar2(255),
   CLU_ID varchar2(255),
   EFF_DT timestamp,
   EXP_DT timestamp,
   LUI_CODE varchar2(255),
   MAX_SEATS decimal(10),
   MIN_SEATS decimal(10),
   NAME varchar2(255),
   STDY_SUBJ_AREA varchar2(255),
   RT_DESCR_ID varchar2(255),
   STATE_ID varchar2(255) NOT NULL,
   TYPE_ID varchar2(255) NOT NULL
);

CREATE TABLE KSEN_LUI_ATTR
(
   ID varchar2(255) PRIMARY KEY NOT NULL,
   OBJ_ID varchar2(36),
   ATTR_KEY varchar2(255),
   ATTR_VALUE varchar2(2000),
   OWNER varchar2(255)
);

INSERT INTO KSEN_LUI (ID, NAME, LUI_CODE, CLU_ID, TYPE_ID, STATE_ID, RT_DESCR_ID, EFF_DT, EXP_DT, VER_NBR) VALUES ('Lui-1', 'Lui one', 'ENGL 100 section 123', 'cluId1', 'kuali.lui.type.course.offering', 'kuali.lui.state.draft', 'Lui-1-Desc', to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2011-12-31 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 0);
INSERT INTO KSEN_LUI (ID, NAME, LUI_CODE, CLU_ID, TYPE_ID, STATE_ID, RT_DESCR_ID, EFF_DT, EXP_DT, VER_NBR) VALUES ('Lui-2', 'Lui rwo', 'ENGL 100 section 124', 'cluId2', 'kuali.lui.type.activity.offering.lecture', 'kuali.lui.state.draft', 'Lui-2-Desc', to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2011-12-31 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 0);
INSERT INTO KSEN_LUI (ID, NAME, LUI_CODE, CLU_ID, TYPE_ID, STATE_ID, RT_DESCR_ID, EFF_DT, EXP_DT, VER_NBR) VALUES ('Lui-3', 'Lui three', 'ENGL 100 section 223', 'cluId3', 'kuali.lui.type.course.offering', 'kuali.lui.state.draft', 'Lui-3-Desc', to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2011-12-31 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 0);
INSERT INTO KSEN_LUI (ID, NAME, LUI_CODE, CLU_ID, TYPE_ID, STATE_ID, RT_DESCR_ID, EFF_DT, EXP_DT, VER_NBR) VALUES ('Lui-4', 'Lui four', 'ENGL 100 section 224', 'cluId4', 'kuali.lui.type.activity.offering.lecture', 'kuali.lui.state.draft', 'Lui-4-Desc', to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2011-12-31 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 0);

--LuiLuiRelationEntity
CREATE TABLE KSEN_LUILUI_RELTN
(
   ID varchar2(255) PRIMARY KEY NOT NULL,
   OBJ_ID varchar2(36),
   VER_NBR decimal(19),
   CREATEID varchar2(255),
   CREATETIME timestamp,
   UPDATEID varchar2(255),
   UPDATETIME timestamp,
   EFF_DT timestamp,
   EXP_DT timestamp,
   NAME varchar2(255),
   RT_DESCR_ID varchar2(255),
   LUI_ID varchar2(255),
   TYPE_ID varchar2(255) NOT NULL,
   STATE_ID varchar2(255) NOT NULL,
   RELATED_LUI_ID varchar2(255)
);

INSERT INTO KSEN_LUILUI_RELTN(ID, VER_NBR, EFF_DT, EXP_DT, STATE_ID, LUI_ID, TYPE_ID, RELATED_LUI_ID) VALUES ('LUILUIREL-1', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.lui.lui.relation.state.active', 'Lui-1', 'kuali.lui.lui.relation.associated', 'Lui-2');
INSERT INTO KSEN_LUILUI_RELTN(ID, VER_NBR, EFF_DT, EXP_DT, STATE_ID, LUI_ID, TYPE_ID, RELATED_LUI_ID) VALUES ('LUILUIREL-2', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.lui.lui.relation.state.active', 'Lui-3', 'kuali.lui.lui.relation.associated', 'Lui-4');

--Lui jn LuiInstructorEntity
CREATE TABLE KSEN_LUI_JN_LUI_INSTR
(
   LUI_ID varchar2(255) NOT NULL,
   LUI_INSTR_ID varchar2(255) NOT NULL
);

INSERT INTO KSEN_LUI_JN_LUI_INSTR(LUI_ID, LUI_INSTR_ID) VALUES ('Lui-1', 'LUI-INSTR-1');

-- IssueRestrictionRelationEntity
CREATE TABLE KSEN_ISSRESTRCTN_RELTN
(
   ID varchar2(255) PRIMARY KEY NOT NULL,
   ISSUE_ID varchar2(255),
   RESTRICTION_ID varchar2(255),
   OBJ_ID varchar2(36),
   VER_NBR decimal(19),
   CREATEID varchar2(255),
   CREATETIME timestamp,
   UPDATEID varchar2(255),
   UPDATETIME timestamp
);

INSERT INTO KSEN_ISSRESTRCTN_RELTN(ID, ISSUE_ID, RESTRICTION_ID, VER_NBR) VALUES ('Issue-Restriction-Rel-1', 'Hold-Issue-1', 'Hold-Restriction-1', 0);

ALTER TABLE KSEN_LUI ADD HAS_WTLST NUMBER(22);
ALTER TABLE KSEN_LUI ADD IS_WTLSTCHK_REQ NUMBER(22);
ALTER TABLE KSEN_LUI ADD STDY_TITLE VARCHAR2(255);
ALTER TABLE KSEN_LUI ADD WTLST_MAX NUMBER(22);
