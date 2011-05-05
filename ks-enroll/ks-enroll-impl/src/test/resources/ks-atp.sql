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

//AtpTypeEntity
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.atp.type.AcademicCalendar', 'Academic Calendar', 'Academic Calendar', 0)
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.atp.type.CampusCalendar', 'Campus Calendar', 'Campus Calendar', 0)
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.atp.type.Fall', 'Fall', 'Fall Semester', 0)
INSERT INTO KSEN_ATP_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.atp.type.Spring', 'Spring', 'Spring Semester', 0)

//AtpRichTextEntity
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-101', '<p>Desc</p>', 'Desc',0)

//AtpEntity
INSERT INTO KSEN_ATP (ID, NAME, START_DT, END_DT, ATP_TYPE_ID, ATP_STATE_ID, VER_NBR) VALUES ('testAtpId1', 'testAtp1', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'}, 'kuali.atp.type.AcademicCalendar', 'kuali.atp.state.Draft', 0)