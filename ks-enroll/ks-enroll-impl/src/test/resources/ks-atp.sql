--
-- Copyright 2011 The Kuali Foundation Licensed under the
-- Educational Community License, Version 2.0 (the "License"); you may
-- not use this file except in compliance with the License. You may
-- obtain a copy of the License at
--
-- http://www.osedu.org/licenses/ECL-2.0
--
-- Unless required by applicable law or agreed to in writing,
-- software distributed under the License is distributed on an "AS IS"
-- BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
-- or implied. See the License for the specific language governing
-- permissions and limitations under the License.
--

//AtpStateEntity
INSERT INTO KSEN_ATP_STATE (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.atp.state.Draft', 'Draft', 'Indicates that this Time Period is just tentative', 0)
INSERT INTO KSEN_ATP_STATE (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.atp.state.Official', 'Official', 'Indicates that this Time Period has been established', 0)
INSERT INTO KSEN_ATP_STATE (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.atpatprelation.state.Active', 'Active', 'Indicates that this Time Period is just active', 0)

//AtpTypeEntity
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.atp.type.AcademicCalendar', 'Academic Calendar', 'Academic Calendar', 0)
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.atp.type.CampusCalendar', 'Campus Calendar', 'Campus Calendar', 0)
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.atp.type.Fall', 'Fall', 'Fall Semester', 0)
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.atp.type.Spring', 'Spring', 'Spring Semester', 0)

//AtpRichTextEntity
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-101', '<p>Desc</p>', 'Desc',0)

//AtpEntity
INSERT INTO KSEN_ATP (ID, NAME, START_DT, END_DT, ATP_TYPE_ID, ATP_STATE_ID, RT_DESCR_ID, VER_NBR) VALUES ('testAtpId1', 'testAtp1', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'}, 'kuali.atp.type.AcademicCalendar', 'kuali.atp.state.Draft', 'RICHTEXT-101', 0)
INSERT INTO KSEN_ATP (ID, NAME, START_DT, END_DT, ATP_TYPE_ID, ATP_STATE_ID, RT_DESCR_ID, VER_NBR) VALUES ('testAtpId2', 'testAtp2', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'}, 'kuali.atp.type.CampusCalendar', 'kuali.atp.state.Draft', 'RICHTEXT-101', 0)

//AtpAtpRelationTypeEntity
INSERT INTO KSEN_ATPATP_RELTN_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) values ('kuali.atp.atp.relation.includes', 'kuali.atp.atp.relation.includes', 'kuali.atp.atp.relation.includes', 0)

// AtpAtpRelationEntity
INSERT INTO KSEN_ATPATP_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, ATP_STATE_ID, ATP_ID, ATP_RELTN_TYPE_ID, RELATED_ATP_ID) VALUES ('ATPATPREL-1', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.atpatprelation.state.Active', 'testAtpId1', 'kuali.atp.atp.relation.includes', 'testAtpId2')

// MilestoneTypeEntity
INSERT INTO KSEN_MSTONE_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.atp.milestone.AdvanceRegistrationPeriod', 'Advance Registration Period', 'Advance Registration Period', 0) 
INSERT INTO KSEN_MSTONE_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.atp.milestone.RegistrationPeriod', 'Registration Period', 'Registration Period', 0)
INSERT INTO KSEN_MSTONE_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.atp.milestone.DropDate', 'Drop Date', 'Drop Period Ends', 0)
INSERT INTO KSEN_MSTONE_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.atp.milestone.GradesDue', 'Grades Due', 'Grades Due', 0)

// MilestoneEntity
INSERT INTO KSEN_MSTONE(ID, NAME, START_DT, END_DT, MILESTONE_TYPE_ID, MILESTONE_STATE_ID, IS_ALL_DAY, IS_DATE_RANGE, RT_DESCR_ID, VER_NBR) values ('testId', 'testId', {ts '2011-07-10 00:00:00.0'}, {ts '2011-07-20 00:00:00.0'}, 'kuali.atp.milestone.AdvanceRegistrationPeriod', 'kuali.atp.state.Draft', 0, 1, 'RICHTEXT-101', 0)
INSERT INTO KSEN_MSTONE(ID, NAME, START_DT, END_DT, MILESTONE_TYPE_ID, MILESTONE_STATE_ID, IS_ALL_DAY, IS_DATE_RANGE, RT_DESCR_ID, VER_NBR) values ('testId2', 'testId2', {ts '2011-08-01 00:00:00.0'}, {ts '2011-10-01 00:00:00.0'}, 'kuali.atp.milestone.RegistrationPeriod', 'kuali.atp.state.Draft', 0, 1, 'RICHTEXT-101', 0)
INSERT INTO KSEN_MSTONE(ID, NAME, START_DT, END_DT, MILESTONE_TYPE_ID, MILESTONE_STATE_ID, IS_ALL_DAY, IS_DATE_RANGE, RT_DESCR_ID, VER_NBR) values ('testId3', 'testId3', {ts '2011-11-01 00:00:00.0'}, null, 'kuali.atp.milestone.DropDate', 'kuali.atp.state.Draft', 1, 0, 'RICHTEXT-101', 0)
INSERT INTO KSEN_MSTONE(ID, NAME, START_DT, END_DT, MILESTONE_TYPE_ID, MILESTONE_STATE_ID, IS_ALL_DAY, IS_DATE_RANGE, RT_DESCR_ID, VER_NBR) values ('testDeleteId', 'testDeleteId', {ts '2011-11-01 00:00:00.0'}, null, 'kuali.atp.milestone.RegistrationPeriod', 'kuali.atp.state.Draft', 0, 0, 'RICHTEXT-101', 0)


