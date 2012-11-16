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
