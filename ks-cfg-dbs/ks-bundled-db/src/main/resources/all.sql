INSERT INTO
    KSEN_TYPE(TYPE_KEY, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, EFF_DT, EXPIR_DT, REF_OBJECT_URI, SERVICE_URI, VER_NBR, CREATETIME, CREATEID)
VALUES
    ('kuali.atp.milestone.CourseSelectionPeriodEnd1', SYS_GUID(), 'Last Day to Add Course', 'Last Day to Add Course', 'Last Day to Add Course', SYSDATE, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService', 0, SYSDATE, 'PATCH')
/
INSERT INTO
    KSEN_TYPE(TYPE_KEY, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, EFF_DT, EXPIR_DT, REF_OBJECT_URI, SERVICE_URI, VER_NBR, CREATETIME, CREATEID)
VALUES
    ('kuali.atp.milestone.CourseSelectionPeriodEnd2', SYS_GUID(), 'Last day UG to Change Credits', 'Last day UG to Change Credits', 'Last day UG to Change Credits', SYSDATE, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService', 0, SYSDATE, 'PATCH')
/
INSERT INTO
    KSEN_TYPE(TYPE_KEY, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, EFF_DT, EXPIR_DT, REF_OBJECT_URI, SERVICE_URI, VER_NBR, CREATETIME, CREATEID)
VALUES
    ('kuali.atp.milestone.CourseSelectionPeriodEnd3', SYS_GUID(), 'Last Day UG to Change Grading Method', 'Last Day UG to Change Grading Method', 'Last Day UG to Change Grading Method', SYSDATE, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService', 0, SYSDATE, 'PATCH')
/
INSERT INTO
    KSEN_TYPE(TYPE_KEY, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, EFF_DT, EXPIR_DT, REF_OBJECT_URI, SERVICE_URI, VER_NBR, CREATETIME, CREATEID)
VALUES
    ('kuali.atp.milestone.CourseSelectionPeriodEnd4', SYS_GUID(), 'Last day UG to Drop without a "W"', 'Last day UG to Drop without a "W"', 'Last day UG to Drop without a "W"', SYSDATE, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService', 0, SYSDATE, 'PATCH')
/
INSERT INTO
    KSEN_TYPE(TYPE_KEY, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, EFF_DT, EXPIR_DT, REF_OBJECT_URI, SERVICE_URI, VER_NBR, CREATETIME, CREATEID)
VALUES
    ('kuali.atp.milestone.DropDate1', SYS_GUID(), 'Last Day GR to Change Grading Method', 'Last Day GR to Change Grading Method', 'Last Day GR to Change Grading Method', SYSDATE, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService', 0, SYSDATE, 'PATCH')
/
INSERT INTO
    KSEN_TYPE(TYPE_KEY, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, EFF_DT, EXPIR_DT, REF_OBJECT_URI, SERVICE_URI, VER_NBR, CREATETIME, CREATEID)
VALUES
    ('kuali.atp.milestone.DropDate2', SYS_GUID(), 'Last day GR to Drop', 'Last day GR to Drop', 'Last day GR to Drop', SYSDATE, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService', 0, SYSDATE, 'PATCH')
/
INSERT INTO
    KSEN_TYPE(TYPE_KEY, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, EFF_DT, EXPIR_DT, REF_OBJECT_URI, SERVICE_URI, VER_NBR, CREATETIME, CREATEID)
VALUES
    ('kuali.atp.milestone.DropDate3', SYS_GUID(), 'Last day GR to Change Credits', 'Last day GR to Change Credits', 'Last day GR to Change Credits', SYSDATE, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService', 0, SYSDATE, 'PATCH')
/
INSERT INTO
    KSEN_TYPE(TYPE_KEY, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, EFF_DT, EXPIR_DT, REF_OBJECT_URI, SERVICE_URI, VER_NBR, CREATETIME, CREATEID)
VALUES
    ('kuali.atp.milestone.DropDate4', SYS_GUID(), 'Last day UG to Drop with a "W"', 'Last day UG to Drop with a "W"', 'Last day UG to Drop with a "W"', SYSDATE, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo','http://student.kuali.org/wsdl/atp/AtpService', 0, SYSDATE, 'PATCH')
/

--
-- Add to ksen_typetype_reltn table
--
INSERT INTO
    KSEN_TYPETYPE_RELTN(ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID)
VALUES
    ('kuali.type.type.relation.type.group.kuali.milestone.type.group.registration.kuali.atp.milestone.CourseSelectionPeriodEnd1', SYS_GUID(), 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', SYSDATE, null, 'kuali.milestone.type.group.registration', 'kuali.atp.milestone.CourseSelectionPeriodEnd1', 0, 1, SYSDATE, 'PATCH')
/
INSERT INTO
    KSEN_TYPETYPE_RELTN(ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID)
VALUES
    ('kuali.type.type.relation.type.group.kuali.milestone.type.group.registration.kuali.atp.milestone.CourseSelectionPeriodEnd2', SYS_GUID(), 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', SYSDATE, null, 'kuali.milestone.type.group.registration', 'kuali.atp.milestone.CourseSelectionPeriodEnd2', 0, 1, SYSDATE, 'PATCH')
/
INSERT INTO
    KSEN_TYPETYPE_RELTN(ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID)
VALUES
    ('kuali.type.type.relation.type.group.kuali.milestone.type.group.registration.kuali.atp.milestone.CourseSelectionPeriodEnd3', SYS_GUID(), 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', SYSDATE, null, 'kuali.milestone.type.group.registration', 'kuali.atp.milestone.CourseSelectionPeriodEnd3', 0, 1, SYSDATE, 'PATCH')
/
INSERT INTO
    KSEN_TYPETYPE_RELTN(ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID)
VALUES
    ('kuali.type.type.relation.type.group.kuali.milestone.type.group.registration.kuali.atp.milestone.CourseSelectionPeriodEnd4', SYS_GUID(), 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', SYSDATE, null, 'kuali.milestone.type.group.registration', 'kuali.atp.milestone.CourseSelectionPeriodEnd4', 0, 1, SYSDATE, 'PATCH')
/

INSERT INTO
    KSEN_TYPETYPE_RELTN(ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID)
VALUES
    ('kuali.type.type.relation.type.group.kuali.milestone.type.group.registration.kuali.atp.milestone.DropDate1', SYS_GUID(), 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', SYSDATE, null, 'kuali.milestone.type.group.registration', 'kuali.atp.milestone.DropDate1', 0, 1, SYSDATE, 'PATCH')
/
INSERT INTO
    KSEN_TYPETYPE_RELTN(ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID)
VALUES
    ('kuali.type.type.relation.type.group.kuali.milestone.type.group.registration.kuali.atp.milestone.DropDate2', SYS_GUID(), 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', SYSDATE, null, 'kuali.milestone.type.group.registration', 'kuali.atp.milestone.DropDate2', 0, 1, SYSDATE, 'PATCH')
/
INSERT INTO
    KSEN_TYPETYPE_RELTN(ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID)
VALUES
    ('kuali.type.type.relation.type.group.kuali.milestone.type.group.registration.kuali.atp.milestone.DropDate3', SYS_GUID(), 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', SYSDATE, null, 'kuali.milestone.type.group.registration', 'kuali.atp.milestone.DropDate3', 0, 1, SYSDATE, 'PATCH')
/
INSERT INTO
    KSEN_TYPETYPE_RELTN(ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID)
VALUES
    ('kuali.type.type.relation.type.group.kuali.milestone.type.group.registration.kuali.atp.milestone.DropDate4', SYS_GUID(), 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', SYSDATE, null, 'kuali.milestone.type.group.registration', 'kuali.atp.milestone.DropDate4', 0, 1, SYSDATE, 'PATCH')
/


--
-- update KSEN_MSTONE table
--
UPDATE KSEN_MSTONE
SET MSTONE_TYPE = 'kuali.atp.milestone.CourseSelectionPeriodEnd1',
    UPDATEID = 'PATCH',
    UPDATETIME = SYSDATE
WHERE NAME = 'Last Day to Add Course'
/
UPDATE KSEN_MSTONE
SET MSTONE_TYPE = 'kuali.atp.milestone.CourseSelectionPeriodEnd2',
    UPDATEID = 'PATCH',
    UPDATETIME = SYSDATE
WHERE NAME = 'Last day UG to Change Credits'
/
UPDATE KSEN_MSTONE
SET MSTONE_TYPE = 'kuali.atp.milestone.CourseSelectionPeriodEnd3',
    UPDATEID = 'PATCH',
    UPDATETIME = SYSDATE
WHERE NAME = 'Last Day UG to Change Grading Method'
/
UPDATE KSEN_MSTONE
SET MSTONE_TYPE = 'kuali.atp.milestone.CourseSelectionPeriodEnd4',
    UPDATEID = 'PATCH',
    UPDATETIME = SYSDATE
WHERE NAME = 'Last day UG to Drop without a "W"'
/
UPDATE KSEN_MSTONE
SET MSTONE_TYPE = 'kuali.atp.milestone.DropDate1',
    UPDATEID = 'PATCH',
    UPDATETIME = SYSDATE
WHERE NAME = 'Last Day GR to Change Grading Method'
/
UPDATE KSEN_MSTONE
SET MSTONE_TYPE = 'kuali.atp.milestone.DropDate2',
    UPDATEID = 'PATCH',
    UPDATETIME = SYSDATE
WHERE NAME = 'Last day GR to Drop'
/
UPDATE KSEN_MSTONE
SET MSTONE_TYPE = 'kuali.atp.milestone.DropDate3',
    UPDATEID = 'PATCH',
    UPDATETIME = SYSDATE
WHERE NAME = 'Last day GR to Change Credits'
/
UPDATE KSEN_MSTONE
SET MSTONE_TYPE = 'kuali.atp.milestone.DropDate4',
    UPDATEID = 'PATCH',
    UPDATETIME = SYSDATE
WHERE NAME = 'Last day UG to Drop with a "W"'
/
insert into KSEN_TYPE (TYPE_KEY, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, EFF_DT, EXPIR_DT, REF_OBJECT_URI, SERVICE_URI, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.lu.type.activity.ExperientialLearningOROther', null, 'Experiential Learning or Other', 'Experiential Learning or Other', 'Experiential Learning or Other', null, null, 'http://student.kuali.org/wsdl/lui/LuiInfo', 'http://student.kuali.org/wsdl/clu/CluService', 0, TIMESTAMP '2012-03-01 00:00:00', 'SYSTEMLOADER', null, null)
/
insert into KSEN_TYPE (TYPE_KEY, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, EFF_DT, EXPIR_DT, REF_OBJECT_URI, SERVICE_URI, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.lui.type.activity.offering.ExperientialLearningOROther', null, 'Experiential Learning or Other', 'Experiential Learning or Other', 'Experiential Learning or Other', null, null, 'http://student.kuali.org/wsdl/lui/LuiInfo', 'http://student.kuali.org/wsdl/lui/LuiService', 0, TIMESTAMP '2012-03-01 00:00:00', 'SYSTEMLOADER', null, null)
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.group.kuali.lu.type.grouping.activity.kuali.lu.type.activity.ExperientialLearningOROther', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.lu.type.grouping.activity', 'kuali.lu.type.activity.ExperientialLearningOROther', 0, 0, TIMESTAMP '2012-03-01 00:00:00', 'SYSTEMLOADER', null, null)
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.group.kuali.lui.type.grouping.activity.kuali.lui.type.activity.offering.ExperientialLearningOROther', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.lui.type.grouping.activity', 'kuali.lui.type.activity.offering.ExperientialLearningOROther', 0, 0, TIMESTAMP '2012-03-01 00:00:00', 'SYSTEMLOADER', null, null)
/

insert into KSEN_TYPE (TYPE_KEY, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, EFF_DT, EXPIR_DT, REF_OBJECT_URI, SERVICE_URI, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.lu.type.activity.LectureORSeminar', null, 'Lecture or Seminar', 'Lecture or Seminar', 'Lecture or Seminar', null, null, 'http://student.kuali.org/wsdl/lui/LuiInfo', 'http://student.kuali.org/wsdl/clu/CluService', 0, TIMESTAMP '2012-03-01 00:00:00', 'SYSTEMLOADER', null, null)
/
insert into KSEN_TYPE (TYPE_KEY, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, EFF_DT, EXPIR_DT, REF_OBJECT_URI, SERVICE_URI, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.lui.type.activity.offering.LectureORSeminar', null, 'Lecture or Seminar', 'Lecture or Seminar', 'Lecture or Seminar', null, null, 'http://student.kuali.org/wsdl/lui/LuiInfo', 'http://student.kuali.org/wsdl/lui/LuiService', 0, TIMESTAMP '2012-03-01 00:00:00', 'SYSTEMLOADER', null, null)
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.group.kuali.lu.type.grouping.activity.kuali.lu.type.activity.LectureORSeminar', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.lu.type.grouping.activity', 'kuali.lu.type.activity.LectureORSeminar', 0, 0, TIMESTAMP '2012-03-01 00:00:00', 'SYSTEMLOADER', null, null)
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.group.kuali.lui.type.grouping.activity.kuali.lui.type.activity.offering.LectureORSeminar', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.lui.type.grouping.activity', 'kuali.lui.type.activity.offering.LectureORSeminar', 0, 0, TIMESTAMP '2012-03-01 00:00:00', 'SYSTEMLOADER', null, null)
/
UPDATE KSEN_LRC_RVG SET RVG_TYPE ='kuali.result.values.group.type.fixed', RVG_STATE ='kuali.result.values.group.state.approved', NAME ='Allow students to audit', DESCR_PLAIN ='Courses flagged with this allow students to audit the course', DESCR_FORMATTED ='Courses flagged with this allow students to audit the course'
/

UPDATE KSEN_LRC_RVG SET RVG_TYPE ='kuali.result.values.group.type.multiple', RVG_STATE ='kuali.result.values.group.state.approved', NAME ='Accepts a completed notation', DESCR_PLAIN ='Courses flagged with this only allow for a completed or not completed notation.', DESCR_FORMATTED ='Courses flagged with this only allow for a completed or not completed notation.'
/

UPDATE KSEN_LRC_RVG SET RVG_TYPE ='kuali.result.values.group.type.fixed', RVG_STATE ='kuali.result.values.group.state.approved', NAME ='Design Review Narrative', DESCR_PLAIN ='Narrative grade based on a design review', DESCR_FORMATTED ='Narrative grade based on a design review'
/

UPDATE KSEN_LRC_RVG SET RVG_TYPE ='kuali.result.values.group.type.multiple', RVG_STATE ='kuali.result.values.group.state.approved', NAME ='Letter', DESCR_PLAIN ='Standard A-F Grading used by most courses and how most courses are shown on the external transcript', DESCR_FORMATTED ='Standard A-F Grading used by most courses and how most courses are shown on the external transcript'
/

UPDATE KSEN_LRC_RVG SET RVG_TYPE ='kuali.result.values.group.type.multiple', RVG_STATE ='kuali.result.values.group.state.approved', NAME ='Pass/Fail Grading', DESCR_PLAIN ='Pass/Fail grading used for courses that are only taught that way', DESCR_FORMATTED ='Pass/Fail grading used for courses that are only taught that way'
/

UPDATE KSEN_LRC_RVG SET RVG_TYPE ='kuali.result.values.group.type.range', RVG_STATE ='kuali.result.values.group.state.approved', NAME ='Percentage Grading 0-100%', DESCR_PLAIN ='Standard percentage grading -- captured on records coming in from other institutions that use this scale and then translated for transfer credit articulation', DESCR_FORMATTED ='Standard percentage grading -- captured on records coming in from other institutions that use this scale and then translated for transfer credit articulation'
/

UPDATE KSEN_LRC_RVG SET RVG_TYPE ='kuali.result.values.group.type.fixed', RVG_STATE ='kuali.result.values.group.state.approved', NAME ='Recital Review Narrative', DESCR_PLAIN ='Narrative grade based on a review of a recital', DESCR_FORMATTED ='Narrative grade based on a review of a recital'
/

UPDATE KSEN_LRC_RVG SET RVG_TYPE ='kuali.result.values.group.type.multiple', RVG_STATE ='kuali.result.values.group.state.approved', NAME ='Administrative Grade of Satisfactory', DESCR_PLAIN ='Administrative grade that can be awarded to thesis type courses. These are ones that are expected to be completed over a number of semesters and the only grading done on it wold be an indicator that they student was making satisfactory or unsatisfactory progress', DESCR_FORMATTED ='Administrative grade that can be awarded to thesis type courses. These are ones that are expected to be completed over a number of semesters and the only grading done on it wold be an indicator that they student was making satisfactory or unsatisfactory progress'
/
insert into
	KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID)
values
	('kuali.type.type.relation.type.group.kuali.milestone.type.group.instructional.kuali.atp.milestone.GradesDue', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.milestone.type.group.instructional', 'kuali.atp.milestone.GradesDue', 0, 0, TIMESTAMP '2012-03-01 00:00:00', 'SYSTEMLOADER', null, null)
/

insert into
	KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID)
values
	('kuali.type.type.relation.type.group.kuali.milestone.type.group.instructional.kuali.atp.milestone.firstdayofclasses', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.milestone.type.group.instructional', 'kuali.atp.milestone.firstdayofclasses', 0, 0, TIMESTAMP '2012-03-01 00:00:00', 'SYSTEMLOADER', null, null)
/

insert into
	KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID)
values
	('kuali.type.type.relation.type.group.kuali.milestone.type.group.instructional.kuali.atp.milestone.lastdayofclasses', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.milestone.type.group.instructional', 'kuali.atp.milestone.lastdayofclasses', 0, 0, TIMESTAMP '2012-03-01 00:00:00', 'SYSTEMLOADER', null, null)
/
-- Insert Summer1 and Summer2 term types
insert into KSEN_TYPE (TYPE_KEY, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, EFF_DT, EXPIR_DT, REF_OBJECT_URI, SERVICE_URI, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.atp.type.Summer1', null, 'Summer I Term', null, null, null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 'http://student.kuali.org/wsdl/atp/AtpService', 0, TIMESTAMP '2012-11-09 00:00:00', 'UMDDATA', null, null);
/
insert into KSEN_TYPE (TYPE_KEY, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, EFF_DT, EXPIR_DT, REF_OBJECT_URI, SERVICE_URI, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.atp.type.Summer2', null, 'Summer II Term', null, null, null, null, 'http://student.kuali.org/wsdl/atp/MilestoneInfo', 'http://student.kuali.org/wsdl/atp/AtpService', 0, TIMESTAMP '2012-11-09 00:00:00', 'UMDDATA', null, null);
/

-- Add type-type relation for Summer 1
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.group.kuali.atp.type.Summer1.kuali.atp.duration.Term', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.Summer1', 'kuali.atp.duration.Term', 0, 0, TIMESTAMP '2012-09-12 00:00:00', 'UMDDATA', null, null);
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.group.kuali.atp.type.group.term.kuali.atp.type.Summer1', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.group.term', 'kuali.atp.type.Summer1', 2, 0, TIMESTAMP '2012-03-01 00:00:00', 'UMDDATA', null, null);
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.allowed.kuali.atp.type.AcademicCalendar.kuali.atp.type.Summer1', null, 'kuali.type.type.relation.type.allowed', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.AcademicCalendar', 'kuali.atp.type.Summer1', 2, 0, TIMESTAMP '2012-03-01 00:00:00', 'UMDDATA', null, null);
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.allowed.kuali.atp.type.Summer1.kuali.milestone.type.group.instructional', null, 'kuali.type.type.relation.type.allowed', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.Summer1', 'kuali.milestone.type.group.instructional', 1, 0, TIMESTAMP '2012-03-01 00:00:00', 'UMDDATA', null, null);
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.allowed.kuali.atp.type.Summer1.kuali.milestone.type.group.registration', null, 'kuali.type.type.relation.type.allowed', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.Summer1', 'kuali.milestone.type.group.registration', 0, 0, TIMESTAMP '2012-03-01 00:00:00', 'UMDDATA', null, null);
/

-- Add type-type relation for Summer 2
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.group.kuali.atp.type.Summer2.kuali.atp.duration.Term', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.Summer2', 'kuali.atp.duration.Term', 0, 0, TIMESTAMP '2012-09-12 00:00:00', 'UMDDATA', null, null);
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.group.kuali.atp.type.group.term.kuali.atp.type.Summer2', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.group.term', 'kuali.atp.type.Summer2', 2, 0, TIMESTAMP '2012-03-01 00:00:00', 'UMDDATA', null, null);
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.allowed.kuali.atp.type.AcademicCalendar.kuali.atp.type.Summer2', null, 'kuali.type.type.relation.type.allowed', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.AcademicCalendar', 'kuali.atp.type.Summer2', 2, 0, TIMESTAMP '2012-03-01 00:00:00', 'UMDDATA', null, null);
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.allowed.kuali.atp.type.Summer2.kuali.milestone.type.group.instructional', null, 'kuali.type.type.relation.type.allowed', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.Summer2', 'kuali.milestone.type.group.instructional', 1, 0, TIMESTAMP '2012-03-01 00:00:00', 'UMDDATA', null, null);
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.allowed.kuali.atp.type.Summer2.kuali.milestone.type.group.registration', null, 'kuali.type.type.relation.type.allowed', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.Summer2', 'kuali.milestone.type.group.registration', 0, 0, TIMESTAMP '2012-03-01 00:00:00', 'UMDDATA', null, null);
/

-- Remove the season type relations
delete from KSEN_TYPETYPE_RELTN where ID='kuali.type.type.relation.type.group.kuali.atp.type.Spring.kuali.atp.season.Spring';
/
delete from KSEN_TYPETYPE_RELTN where ID='kuali.type.type.relation.type.group.kuali.atp.type.Summer.kuali.atp.season.Summer';
/
delete from KSEN_TYPETYPE_RELTN where ID='kuali.type.type.relation.type.group.kuali.atp.type.Winter.kuali.atp.season.Winter';
/
delete from KSEN_TYPETYPE_RELTN where ID='kuali.type.type.relation.type.group.kuali.atp.type.Fall.kuali.atp.season.Fall';
/

-- Delete type-type for relation between ATP type and duration type
delete from KSEN_TYPETYPE_RELTN where ID='kuali.type.type.relation.type.group.kuali.atp.type.Fall.kuali.atp.duration.Semester';
/
delete from KSEN_TYPETYPE_RELTN where ID='kuali.type.type.relation.type.group.kuali.atp.type.Spring.kuali.atp.duration.Semester';
/
delete from KSEN_TYPETYPE_RELTN where ID='kuali.type.type.relation.type.group.kuali.atp.type.Winter.kuali.atp.duration.Period';
/
delete from KSEN_TYPETYPE_RELTN where ID='kuali.type.type.relation.type.group.kuali.atp.type.Summer.kuali.atp.duration.Term';
/

-- Insert type-type relation between ATP type and duration type
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.group.kuali.atp.type.Spring.kuali.atp.duration.Term', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', TIMESTAMP '2012-11-01 00:00:00', null, 'kuali.atp.type.Spring', 'kuali.atp.duration.Term', 0, 0, TIMESTAMP '2012-11-09 00:00:00', 'UMDDATA', null, null);
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.group.kuali.atp.type.Summer.kuali.atp.duration.Term', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', TIMESTAMP '2012-11-01 00:00:00', null, 'kuali.atp.type.Summer', 'kuali.atp.duration.Term', 0, 0, TIMESTAMP '2012-11-09 00:00:00', 'UMDDATA', null, null);
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.group.kuali.atp.type.Fall.kuali.atp.duration.Term', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', TIMESTAMP '2012-11-01 00:00:00', null, 'kuali.atp.type.Fall', 'kuali.atp.duration.Term', 0, 0, TIMESTAMP '2012-11-09 00:00:00', 'UMDDATA', null, null);
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.group.kuali.atp.type.Winter.kuali.atp.duration.Term', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', TIMESTAMP '2012-11-01 00:00:00', null, 'kuali.atp.type.Winter', 'kuali.atp.duration.Term', 0, 0, TIMESTAMP '2012-11-09 00:00:00', 'UMDDATA', null, null);
/
delete from KSEN_SOC_ROR_OPTION
/
delete from KSEN_SOC_ROR_ITEM_ATTR
/
delete from KSEN_SOC_ROR_ITEM
/
delete from KSEN_SOC_ROR_ATTR
/
delete from KSEN_SOC_ROR
/
delete from KSEN_SOC_ATTR
/
delete from KSEN_SOC
/
delete from KSEN_ATPMSTONE_RELTN
/
delete from KSEN_ATPATP_RELTN
/
delete from KSEN_MSTONE
/
delete from KSEN_ATP
/
UPDATE
    KSEN_LUI
SET
    KSEN_LUI.ATP_ID = 'kuali.atp.' || SUBSTR(KSEN_LUI.ATP_ID,1,4) ||
    CASE SUBSTR(ATP_ID,5,2)
        WHEN '08'
        THEN 'Fall'
        WHEN '01'
        THEN 'Spring'
        WHEN '07'
        THEN 'Summer2'
        WHEN '05'
        THEN 'Summer1'
        WHEN '12'
        THEN 'Winter'
        ELSE 'ERROR'
    END
WHERE
    LENGTH(KSEN_LUI.ATP_ID) = 6
/
--Clear out all schedules since these point to non existant rooms/building
delete from KSEN_SCHED_CMP_TMSLOT
/
delete from KSEN_SCHED_CMP
/
delete from KSEN_SCHED
/
update KSEN_LUI set KSEN_LUI.SCHEDULE_ID = null
/
