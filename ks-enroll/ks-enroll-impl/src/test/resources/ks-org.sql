--
-- Copyright 2010 The Kuali Foundation Licensed under the
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

//TYPES
insert into KSOR_ORG_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.CorporateEntity', 'A legal corporate entity', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Corporate Entity')
/
insert into KSOR_ORG_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.Board', 'A Board of Directors', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Board')
/
insert into KSOR_ORG_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.Division', 'A Division', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Division')
/
insert into KSOR_ORG_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.School', 'A School', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'School')
/
insert into KSOR_ORG_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.Program', 'A Program', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Program')
/
insert into KSOR_ORG_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.Center', 'A Center', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Center')
/
insert into KSOR_ORG_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.College', 'A College', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'College')
/
insert into KSOR_ORG_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.Department', 'A Department', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Department')
/
insert into KSOR_ORG_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.Office', 'An Office', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Office')
/
insert into KSOR_ORG_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.Association', 'An Association', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Association')
/
insert into KSOR_ORG_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.AdvisoryGroup', 'An Advisory Group', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Advisory Group')
/
insert into KSOR_ORG_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.WorkGroup', 'A group organized for a particular purpose', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Work Group')
/
insert into KSOR_ORG_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.Section', 'A Section', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Section')
/
insert into KSOR_ORG_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.Senate', 'A Deliberative body', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Senate')
/
insert into KSOR_ORG_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.Committee', 'A Committee', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Committee')
/
insert into KSOR_ORG_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.AdhocCommittee', 'An Adhoc Committee', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Adhoc Committee')
/
insert into KSOR_ORG_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.COC', 'A committee that approves curriculum', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'COC')
/


//ORGS
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('1', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Kuali University System', 'KUSystem', 'Active', 'kuali.org.CorporateEntity')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('3', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Chancellor''s Office', 'ChancellorsOffice', 'Active', 'kuali.org.Office')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('4', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Kuali University', 'KU', 'Active', 'kuali.org.CorporateEntity')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('5', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Board of Trustees', 'Trustees', 'Active', 'kuali.org.Board')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('6', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Office of the President', 'PresidentsOffice', 'Active', 'kuali.org.Office')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('7', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'President''s Cabinet for College-Wide Planning', 'PresidentsCabinet', 'Active', 'kuali.org.AdvisoryGroup')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('8', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'President''s Council for College-Wide Planning', 'PresidentsCouncil', 'Active', 'kuali.org.AdvisoryGroup')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('9', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Internal Audit', 'Audit', 'Active', 'kuali.org.Office')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('10', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Legal Counsel', 'Legal', 'Active', 'kuali.org.Office')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('11', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'The Foundation', 'Foundation', 'Active', 'kuali.org.Office')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('12', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Intercollegiate Athletics', 'Athletics', 'Active', 'kuali.org.Department')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('13', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Alumni Relations', 'AlumniRelations', 'Active', 'kuali.org.Office')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('14', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Alumni Association', 'AlumniAssociation', 'Active', 'kuali.org.Association')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('15', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Academic Affairs (EVPP)', 'Academics', 'Active', 'kuali.org.Division')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('16', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Administration and Finance', 'Administration', 'Active', 'kuali.org.Division')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('17', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Student Affairs', 'Students', 'Active', 'kuali.org.Division')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('18', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Research and Economic Development', 'Research', 'Active', 'kuali.org.Division')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('19', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Office of the Executive Vice President and Provost', 'EVPPOffice', 'Active', 'kuali.org.Office')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('20', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Office of the VP for Business Administration and Finance', 'VPAdminOffice', 'Active', 'kuali.org.Office')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('21', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Office of the VP for Student Affairs', 'VPStudentsOffice', 'Active', 'kuali.org.Office')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('22', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Office of the VP for Research and Economic Development', 'VPResearchOffice', 'Active', 'kuali.org.Office')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('23', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Information Technology', 'IT', 'Active', 'kuali.org.Department')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('24', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'The Undergraduate Program', 'UndergraduateProgram', 'Active', 'kuali.org.Program')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('25', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Office of the Dean of Students', 'DeanStudentsOffice', 'Active', 'kuali.org.Office')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('26', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'The College of Graduate and Professional Studies', 'GraduateSchool', 'Active', 'kuali.org.Program')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('27', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'The Graduate School Office', 'GraduateOffice', 'Active', 'kuali.org.Office')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('28', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'The College of Arts and Humanities', 'CollegeArtsHum', 'Active', 'kuali.org.College')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('29', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'The College of Behavioral and Social Science', 'CollegeBSOS', 'Active', 'kuali.org.College')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('30', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'The College of Life and Chemical Sciences ', 'CollegeLCS', 'Active', 'kuali.org.College')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('31', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'The College of Engineering', 'CollegeEng', 'Active', 'kuali.org.College')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('32', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'The Kuali School of Business', 'BusinessSchool', 'Active', 'kuali.org.School')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('33', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'The College of Education', 'CollegeEducation', 'Active', 'kuali.org.College')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('34', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'The Kuali Medical School', 'MedicalSchool', 'Active', 'kuali.org.School')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('35', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Office of the Dean of the College of Arts and Humanities', 'OfficeOfDeanArtsHum', 'Active', 'kuali.org.Office')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('36', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Office of the Dean of the College of Behavioral and Social Science (BSOS)', 'OfficeOfDeanBSOS', 'Active', 'kuali.org.Office')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('37', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Office of the Dean of the College of Engineering', 'OfficeOfDeanEng', 'Active', 'kuali.org.Office')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('38', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Office of the Dean of the College of Life and Chemical Sciences ', 'OfficeOfDeanLCS', 'Active', 'kuali.org.Office')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('39', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Extended Studies Program', 'ExtendedStudies', 'Active', 'kuali.org.Program')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('40', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'College of Distance Education', 'DistanceEducation', 'Active', 'kuali.org.College')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('41', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Learning Communities Programs', 'LearningCommunities', 'Active', 'kuali.org.Program')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('42', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Undergraduate Research Program', 'URP', 'Active', 'kuali.org.Program')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('43', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Freshman Experiential Learning Group', 'FELG', 'Active', 'kuali.org.Program')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('44', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Seminar Based Advising', 'SeminarAdvising', 'Active', 'kuali.org.Program')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('45', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Residence Based Advising', 'ResidenceAdvising', 'Active', 'kuali.org.Program')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('46', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Cross-Registration Program', 'CrossRegistration', 'Active', 'kuali.org.Program')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('47', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Study Abroad Center', 'StudyAbroad', 'Active', 'kuali.org.Center')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('48', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Honors Program', 'HonorsProgram', 'Active', 'kuali.org.Program')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('49', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Gender Studies Program', 'GenderStudies', 'Active', 'kuali.org.Program')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('50', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'English Dept', 'English', 'Active', 'kuali.org.Department')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('51', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Fine Arts Dept', 'FineArts', 'Active', 'kuali.org.Department')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('52', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'French Dept', 'French', 'Active', 'kuali.org.Department')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('53', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Geography Dept', 'Geography', 'Active', 'kuali.org.Department')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('54', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Linguistics Dept', 'Linguistics', 'Active', 'kuali.org.Department')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('55', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Music Dept', 'Music', 'Active', 'kuali.org.Department')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('56', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'History Dept', 'History', 'Active', 'kuali.org.Department')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('57', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Political Science Dept', 'PolySci', 'Active', 'kuali.org.Department')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('58', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Psychology Dept', 'Psychology', 'Active', 'kuali.org.Department')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('59', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Sociology Dept', 'Sociology', 'Active', 'kuali.org.Department')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('60', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Interdisciplinary Studies in Social Science Program', 'InterdiscBSOS', 'Active', 'kuali.org.Office')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('61', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Chemical Engineering Dept', 'Chemical', 'Active', 'kuali.org.Department')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('62', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Civil Engineering Dept', 'Civil', 'Active', 'kuali.org.Department')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('63', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Mechanical Engineering Dept', 'Mechanical', 'Active', 'kuali.org.Department')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('64', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Computer Science Dept', 'CompSci', 'Active', 'kuali.org.Department')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('65', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Biology Dept', 'Biology', 'Active', 'kuali.org.Department')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('66', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Biochemistry Dept', 'Biochemistry', 'Active', 'kuali.org.Department')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('67', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Botany Dept', 'Botany', 'Active', 'kuali.org.Department')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('68', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Chemistry Dept', 'Chemistry', 'Active', 'kuali.org.Department')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('69', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Geology Dept', 'Geology', 'Active', 'kuali.org.Department')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('70', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Accounting Section', 'Accounting', 'Active', 'kuali.org.Section')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('71', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Finance Section', 'Finance', 'Active', 'kuali.org.Section')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('72', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'International Business Section', 'InternationalBusiness', 'Active', 'kuali.org.Section')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('73', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Marketing Section', 'Marketing ', 'Active', 'kuali.org.Section')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('74', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Section w/in the Provost Office that deals with all the academic programs', 'AcademicPrograms', 'Active', 'kuali.org.Section')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('75', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Section w/in the Provost Office that deals with assessing the quality of the programs', 'OutcomesAssessment', 'Active', 'kuali.org.Section')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('76', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Section w/in the Provost Office that deals with academic services', 'AcademicServices', 'Active', 'kuali.org.Section')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('77', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Division of Academic Affairs', 'DivisionAcademicAffaris', 'Active', 'kuali.org.Section')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('78', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Housing', 'Housing', 'Active', 'kuali.org.Office')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('79', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Dining', 'Dining', 'Active', 'kuali.org.Office')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('80', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Transportation', 'Transportation', 'Active', 'kuali.org.Office')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('81', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Student Activities', 'StudentActivities', 'Active', 'kuali.org.Office')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('82', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Community Involvement', 'Community', 'Active', 'kuali.org.Office')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('83', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Recreation', 'Recreation', 'Active', 'kuali.org.Office')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('84', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Health', 'Health', 'Active', 'kuali.org.Office')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('85', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Wellness', 'Wellness', 'Active', 'kuali.org.Office')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('86', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Spirituality', 'Spirituality', 'Active', 'kuali.org.Office')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('87', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Career and Social Development', 'Career', 'Active', 'kuali.org.Office')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('88', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Drug & Alcohol Awareness', 'DrugAlcohol', 'Active', 'kuali.org.Program')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('89', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Greek Affairs', 'GreekProgram', 'Active', 'kuali.org.Program')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('90', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Multicultural and Diversity', 'DiversityProgram', 'Active', 'kuali.org.Program')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('91', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'New Student Life Programs', 'NewStudentLifeProgram', 'Active', 'kuali.org.Program')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('92', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Off-Campus Life', 'OffCampusLifeProgram', 'Active', 'kuali.org.Program')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('93', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Peer Counseling Services', 'PeerCounselingServices', 'Active', 'kuali.org.Program')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('94', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Student Conduct & Judicial Affairs', 'StudentConductJudicial', 'Active', 'kuali.org.Program')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('95', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Student Disability Services', 'StudentDisabilityServices', 'Active', 'kuali.org.Program')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('96', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Student Legal Services', 'StudentLegalServices', 'Active', 'kuali.org.Program')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('97', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Student Organizations', 'StudentOrganizations', 'Active', 'kuali.org.Program')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('98', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Admissions and Enrollment Planning', 'Admissions', 'Active', 'kuali.org.Department')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('99', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Financial Aid Office (Scholarships and Student Aid)', 'FinancialAid', 'Active', 'kuali.org.Department')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('100', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Undergraduate Admissions', 'Admissions', 'Active', 'kuali.org.Department')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('102', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Records and Registration (Registrar''s Office)', 'Registrar', 'Active', 'kuali.org.Department')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('103', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Curriculum Section of the Registrar''s Office', 'CurriculumSection', 'Active', 'kuali.org.Section')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('104', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Institutional Research', 'InstitutionalResearch', 'Active', 'kuali.org.Office')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('105', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Advising and Orientation Office', 'Advising', 'Active', 'kuali.org.Department')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('106', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Career Services Department', 'CareerServices', 'Active', 'kuali.org.Department')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('107', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Student Accounts', 'StudentAccounts', 'Active', 'kuali.org.Department')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('108', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', 'This committee coordinates the advising programs of the various colleges and advises the Vice President for Academic Affairs on issues of interest or concern to advisors.', {ts '2000-01-01 00:00:00.0'}, null, 'Academic Advising Committee', 'AdvisingCommittee', 'Active', 'kuali.org.Committee')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('109', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', 'This Committee considers policies and procedures related to academic standards, Admissions, and advising for both prospective and enrolled undergraduate students.  Jurisdiction includes Admissions Appeals.  ', {ts '2000-01-01 00:00:00.0'}, null, 'Academic Standards and Admissions Committee', 'AdmissionsCommittee', 'Active', 'kuali.org.Committee')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('110', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', 'Invites nominations for honorary degrees based on a candidate''s record of scholarship, research, and/or creative activity and recommends names directly to the Faculty Senate Executive Board for the awarding of honorary degrees.', {ts '2000-01-01 00:00:00.0'}, null, 'Committee on Honorary Degrees ', 'HonoraryDegreesCommittee', 'Active', 'kuali.org.Committee')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('112', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', 'The purpose of the Council is to enhance programmatic and institutional articulation between Kuali University and institutions and programs that are significant or potentially significant sources of transfer students.', {ts '2000-01-01 00:00:00.0'}, null, 'Articulation Coordination Council', 'ArticulationCouncil', 'Active', 'kuali.org.Committee')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('115', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', 'See Budget Cabinet', {ts '2000-01-01 00:00:00.0'}, null, 'Presidents Advisory Committee on Budget Priorities and Planning', 'BudgetPrioritiesCommittee', 'Active', 'kuali.org.AdvisoryGroup')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('121', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Faculty Senate Executive Board', 'SenateExcecutiveBoard', 'Active', 'kuali.org.Committee')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('124', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Offices of Research Assurances', 'ResearchAssurances', 'Active', 'kuali.org.Office')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('129', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Office of New Student Programs', 'NewStudentPrograms', 'Active', 'kuali.org.Office')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('136', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Faculty Development and Administrative Relations Council', 'FDARC', 'Active', 'kuali.org.Committee')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('137', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Resource Policies and Allocations Council', 'RPAC', 'Active', 'kuali.org.Committee')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('138', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', 'The Judiciary and Appeals Committee (JAC) hears cases in which a serious violation of the Conduct Code may result in suspension or expulsion from the University.     JAC may also hear cases of minor nature.', {ts '2000-01-01 00:00:00.0'}, null, 'Judiciary and Appeals Committee (JAC)', 'JAC', 'Active', 'kuali.org.Committee')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('139', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', 'The JAC hears cases in boards of five persons each, at least two of whom are students.   The JAC makes its recommendation to the Dean of Students.', {ts '2000-01-01 00:00:00.0'}, null, 'Judiciary Board assembled to hear a particular case', 'JAC-BoardFor ?', 'Active', 'kuali.org.Committee')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('142', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Departmental Representatives to the COC', 'Dept COC Reps', 'Active', 'kuali.org.WorkGroup')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('143', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'College of Arts and Hum Curriculum Committee', 'Arts & Hum COC', 'Active', 'kuali.org.COC')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('144', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'College of Behavioral and Social Sciences Curriculum Committee', 'BSOS COC', 'Active', 'kuali.org.COC')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('145', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'College of Life and Chemical Sciences Curriculum Committee', 'Life & Chem Sciences COC', 'Active', 'kuali.org.COC')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('146', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'College of Engineering Curriculum Committee', 'Engineering COC', 'Active', 'kuali.org.COC')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('147', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'English Dept Curriculum Committee', 'English COC', 'Active', 'kuali.org.COC')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('148', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Fine Arts Dept Curriculum Committee', 'FineArts COC', 'Active', 'kuali.org.COC')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('149', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'French Dept Curriculum Committee', 'French COC', 'Active', 'kuali.org.COC')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('150', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Geography Dept Curriculum Committee', 'Geography COC', 'Active', 'kuali.org.COC')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('151', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Linguistics Dept Curriculum Committee', 'Linguistics COC', 'Active', 'kuali.org.COC')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('152', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Music Dept Curriculum Committee', 'Music COC', 'Active', 'kuali.org.COC')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('153', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'History Dept Curriculum Committee', 'History COC', 'Active', 'kuali.org.COC')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('154', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Political Science Dept Curriculum Committee', 'PolySci COC', 'Active', 'kuali.org.COC')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('155', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Psychology Dept Curriculum Committee', 'Psychology COC', 'Active', 'kuali.org.COC')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('156', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Sociology Dept Curriculum Committee', 'Sociology COC', 'Active', 'kuali.org.COC')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('157', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Interdisciplinary BSOS COC', 'InterdiscBSOS COC', 'Active', 'kuali.org.COC')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('158', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Chemical Engineering Dept Curriculum Committee', 'Chemical COC', 'Active', 'kuali.org.COC')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('159', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Civil Engineering Dept Curriculum Committee', 'Civil COC', 'Active', 'kuali.org.COC')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('160', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Mechanical Engineering Dept Curriculum Committee', 'Mechanical COC', 'Active', 'kuali.org.COC')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('161', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Computer Science Dept Curriculum Committee', 'CompSci COC', 'Active', 'kuali.org.COC')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('162', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Biology Dept Curriculum Committee', 'Biology COC', 'Active', 'kuali.org.COC')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('163', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Biochemistry Dept Curriculum Committee', 'Biochemistry COC', 'Active', 'kuali.org.COC')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('164', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Botany Dept Curriculum Committee', 'Botany COC', 'Active', 'kuali.org.COC')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('165', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Chemistry Dept Curriculum Committee', 'Chemistry COC', 'Active', 'kuali.org.COC')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('166', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Geology Dept Curriculum Committee', 'Geology COC', 'Active', 'kuali.org.COC')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('167', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', '', {ts '2000-01-01 00:00:00.0'}, null, 'Gender Studies Curriculum Committee', 'GenderStudies COC', 'Active', 'kuali.org.COC')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('168', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, 'Committee charted by Geology Dept. head to evaluate dept. curriculum', 'This committee was chartered by the Head of the Geology Department to review and possibly revamp the geology Department''s curriculum', {ts '2000-01-01 00:00:00.0'}, null, 'Business School Curriculum Committee', 'Business School COC', 'Active', 'kuali.org.COC')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('169', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, 'Committee charted by Geology Dept. head to evaluate dept. curriculum', 'This committee was chartered by the Head of the Geology Department to review and possibly revamp the geology Department''s curriculum', {ts '2000-01-01 00:00:00.0'}, null, 'Education School Curriculum Committee', 'Education School COC', 'Active', 'kuali.org.COC')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('170', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, 'Committee charted by Geology Dept. head to evaluate dept. curriculum', 'This committee was chartered by the Head of the Geology Department to review and possibly revamp the geology Department''s curriculum', {ts '2000-01-01 00:00:00.0'}, null, 'Medical School Curriculum Committee', 'Medical School COC', 'Active', 'kuali.org.COC')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('171', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, 'Committee charted by Geology Dept. head to evaluate dept. curriculum', 'This committee was chartered by the Head of the Geology Department to review and possibly revamp the geology Department''s curriculum', {ts '2000-01-01 00:00:00.0'}, null, 'Ad hoc Committee for Geology Curriculum Innovation', 'Geology CIC', 'Active', 'kuali.org.AdhocCommittee')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('111', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', 'The purpose of the Council is to enhance programmatic and institutional articulation between Kuali University and institutions and programs that are significant or potentially significant sources of transfer students.', {ts '2000-01-01 00:00:00.0'}, null, 'Administrative Support Programs Advisory Committee', 'ASPAC', 'Active', 'kuali.org.Committee')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('113', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', 'Allocation of new revenues, final policy for faculty and professional staff compensation increases and, when necessary, assignment of budget reduction targets are made centrally by the university�s Budget Cabinet. The Budget Cabinet is strictly advisory t', {ts '2000-01-01 00:00:00.0'}, null, 'University Budget Advisory Committee (BAC)', 'BAC', 'Active', 'kuali.org.Committee')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('114', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', 'See Budget Cabinet', {ts '2000-01-01 00:00:00.0'}, null, 'Budget Cabinet', 'BudgetCabinet', 'Active', 'kuali.org.AdvisoryGroup')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('116', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', 'Capital project information is submitted via a vice president to the Capital Projects Advisory Committee (CPAC) for review of all capital improvements greater than $250,000. The Committee is advisory to the President, and the President''s approval is requi', {ts '2000-01-01 00:00:00.0'}, null, 'Business and Finance Advisory Committee', 'FinanceAdvisoryCommittee', 'Active', 'kuali.org.AdvisoryGroup')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('117', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', 'The Career Services Council reviews and sets policies as they relate to legal, ethical, and professional practice related to the recruitment, hiring, and selection of students and alumni in experiential learning and full-time employment. The Council facil', {ts '2000-01-01 00:00:00.0'}, null, 'Capital Projects Advisory Committee (CPAC)', 'CPAC', 'Active', 'kuali.org.AdvisoryGroup')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('118', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', 'The Council of Deans has members from each college. It is the principal source in higher education of collective views on all matters relating to education and research. It aims to respond dynamically to the changes and trends in higher education.   The C', {ts '2000-01-01 00:00:00.0'}, null, 'Career Services Council', 'CareerServicesCouncil', 'Active', 'kuali.org.Committee')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('119', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', 'The Department Chairs Council [cabinet] shall discuss and establish positions on matters of University concern with particular implications for academic departments and programs of the University. The Council shall provide for communication between the De', {ts '2000-01-01 00:00:00.0'}, null, 'Council of Deans', 'TheDeans', 'Active', 'kuali.org.Committee')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('122', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', 'The Institutional Review Board (IRB) is a federally mandated committee responsible for review of all research involving human participants for compliance with the federal regulations.   The IRB is sponsored by the Offices of Research Assurances and the Vi', {ts '2000-01-01 00:00:00.0'}, null, 'Information Technology Services Advisory Committee (ITSAC)', 'ITSAC', 'Active', 'kuali.org.Committee')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('125', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', 'The mission of the Council on International Programs (CIP) is to promote, improve, and expand the internationalization/ globalization of the University.   The Council�s goals are to further internationalization in the following areas: � Strengthen undergr', {ts '2000-01-01 00:00:00.0'}, null, 'Investment Committee', 'InvestmentCommittee', 'Active', 'kuali.org.Committee')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('126', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', 'Sponsored by both the Vice President for Student Affairs and the Provost, the Learning Community Advisory Committee exists to � Advise the Co-Directors of Learning Communities and the Associate Provost for Academic Programs and the Associate Vice Presiden', {ts '2000-01-01 00:00:00.0'}, null, 'Council on International Programs', 'CIP', 'Active', 'kuali.org.Committee')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('127', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', 'The University Orientation Committee, composed of University faculty and staff, is responsible for student orientation programs. The undergraduate colleges of the university, in cooperation with the Office of New Student Programs, have responsibility for ', {ts '2000-01-01 00:00:00.0'}, null, 'Learning Community Advisory Committee', 'LCAC', 'Active', 'kuali.org.Committee')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('130', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', 'The Special Student Fee and Tuition Committee (SSFTC) is a statutory committee of five students and five university employees that considers proposed student activity changes and makes recommendations to the President concerning special student fee change', {ts '2000-01-01 00:00:00.0'}, null, 'Residency Review Committee', 'ResidencyCommittee', 'Active', 'kuali.org.Committee')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('131', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', 'The University Student Government Association is the foremost student organization at Kuali University. Along with providing comprehensive representation of the views and opinions of the student body, the SGA attempts to coordinate the efforts of the mult', {ts '2000-01-01 00:00:00.0'}, null, 'Special Student Fee and Tuition Committee', 'SSFTC', 'Active', 'kuali.org.Committee')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('132', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', 'The Graduate and Professional Student Association represents the graduate and professional students'' perspective on campus issues and serves as a liaison between graduate/professional students and the university administration. Each department is permitte', {ts '2000-01-01 00:00:00.0'}, null, 'Student Government Association ', 'SGA', 'Active', 'kuali.org.Committee')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('133', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', 'The Senate has legislative responsibility for general academic and educational policy. It also serves to facilitate communication among faculty, students, and administration. Finally, it cooperates with the administration in conflict resolution. The Senat', {ts '2000-01-01 00:00:00.0'}, null, 'The Graduate and Professional Student Association', 'GPSA', 'Active', 'kuali.org.Committee')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('134', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', 'The Academic Affairs Council coordinates the creation and consideration of policy in the area of academic affairs, including but not limited to instructional policies, honors programs, and the academic calendar.    They also coordinate the policy proposal', {ts '2000-01-01 00:00:00.0'}, null, 'University Senate', 'Senate', 'Active', 'kuali.org.Senate')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('140', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', 'Recommends and develops policies and procedures for university-wide curricular standards, reviews catalog offerings and degree requirements, and initiates discussions on future curricular matters. The committee reviews college proposals and makes recommen', {ts '2000-01-01 00:00:00.0'}, null, 'Faculty Governance Council', 'FacultyGovernance', 'Active', 'kuali.org.Committee')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('2', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', 'A (put a number here) member Board of Regents, including one full-time student governs the Kuali University System.   The Regents oversee the system''s academic, administrative, and financial operations; formulate policy; and appoint the Kuali Chancellor ', {ts '2000-01-01 00:00:00.0'}, null, 'Board of Regents', 'BORG', 'Active', 'kuali.org.Board')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('101', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', 'The charge of the Enrollment Management Council is to give special attention to three areas: � Increasing the effectiveness of recruitment, marketing and financial aid programs directed at new freshmen.  � Improving success in recruiting transfer students', {ts '2000-01-01 00:00:00.0'}, null, 'Enrollment Management Council', 'EMC', 'Active', 'kuali.org.Committee')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('120', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', 'The Department Chairs Council [cabinet] shall discuss and establish positions on matters of University concern with particular implications for academic departments and programs of the University. The Council shall provide for communication between the De', {ts '2000-01-01 00:00:00.0'}, null, 'Department Chairs Cabinet', 'TheDeptChairs', 'Active', 'kuali.org.Committee')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('123', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', 'The Institutional Review Board (IRB) is a federally mandated committee responsible for review of all research involving human participants for compliance with the federal regulations.   The IRB is sponsored by the Offices of Research Assurances and the Vi', {ts '2000-01-01 00:00:00.0'}, null, 'Institutional Review Board (IRB)', 'IRB', 'Active', 'kuali.org.Committee')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('128', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', 'The University Orientation Committee, composed of University faculty and staff, is responsible for student orientation programs. The undergraduate colleges of the university, in cooperation with the Office of New Student Programs, have responsibility for ', {ts '2000-01-01 00:00:00.0'}, null, 'University Orientation Committee', 'OrientationCommittee', 'Active', 'kuali.org.Committee')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('135', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', 'The Academic Affairs Council coordinates the creation and consideration of policy in the area of academic affairs, including but not limited to instructional policies, honors programs, and the academic calendar.    They also coordinate the policy proposal', {ts '2000-01-01 00:00:00.0'}, null, 'Academic Affairs Council', 'AAC', 'Active', 'kuali.org.Committee')
/
insert into KSOR_ORG (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, SHRT_DESCR, LNG_DESCR, EFF_DT, EXPIR_DT, LNG_NAME, SHRT_NAME, ST, TYPE) values ('141', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, '', 'Recommends and develops policies and procedures for university-wide curricular standards, reviews catalog offerings and degree requirements, and initiates discussions on future curricular matters. The committee reviews college proposals and makes recommen', {ts '2000-01-01 00:00:00.0'}, null, 'Senate Subcommittee on Curricula', 'COC', 'Active', 'kuali.org.COC')
/

//ORG HIERARCHY

insert into KSOR_ORG_HIRCHY (ID, DESCR, EFF_DT, EXPIR_DT, NAME, ROOT_ORG) values ('kuali.org.hierarchy.Curriculum', 'Hierarchy used to Manage Curriculum', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Curriculum', '141')
/
insert into KSOR_ORG_HIRCHY (ID, DESCR, EFF_DT, EXPIR_DT, NAME, ROOT_ORG) values ('kuali.org.hierarchy.Main', 'Main Organizational Hierarchy', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Main', '4')
/


//ORG ORG RELATION TYPE
insert into KSOR_ORG_ORG_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME, REV_DESCR, REV_NAME, ORG_HIRCHY) values ('kuali.org.Part', 'Indicates that one organization is simply a part of another larger organization', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Part', 'contains', 'contains', 'kuali.org.hierarchy.Main')
/
insert into KSOR_ORG_ORG_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME, REV_DESCR, REV_NAME, ORG_HIRCHY) values ('kuali.org.Section', 'Indicates that one organization is section of another larger organization', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Section', 'has section', 'has section', 'kuali.org.hierarchy.Main')
/
insert into KSOR_ORG_ORG_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME, REV_DESCR, REV_NAME, ORG_HIRCHY) values ('kuali.org.Report', 'Describes where the head of one organization directly reports to the head of the higher level organization', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Report', 'is reported to by', 'is reported to by', 'kuali.org.hierarchy.Main')
/
insert into KSOR_ORG_ORG_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME, REV_DESCR, REV_NAME, ORG_HIRCHY) values ('kuali.org.Administer', 'Describes where one organization is administratively responsible for another organization that is not simply "part" of that organization and where the relationship cannot be said to be so strong that there is a "reporting" relationship', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Administer', 'is responsible for', 'is responsible for', 'kuali.org.hierarchy.Main')
/
insert into KSOR_ORG_ORG_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME, REV_DESCR, REV_NAME, ORG_HIRCHY) values ('kuali.org.Chartered', 'Indicates that one organization exists because it is chartered or recognized or sponsored by another organization', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Chartered', 'charters', 'charters', 'kuali.org.hierarchy.Main')
/
insert into KSOR_ORG_ORG_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME, REV_DESCR, REV_NAME, ORG_HIRCHY) values ('kuali.org.Functional', 'Functionally connected by not administratiavely Used for mapping matrix responsibilities', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Functional', 'is functionally responsible to', 'is functionally responsible to', 'kuali.org.hierarchy.Main')
/
insert into KSOR_ORG_ORG_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME, REV_DESCR, REV_NAME, ORG_HIRCHY) values ('kuali.org.Collaborate', 'Peer Organizations Collaborating Together', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Collaborate', 'collaborates with', 'collaborates with', 'kuali.org.hierarchy.Main')
/
insert into KSOR_ORG_ORG_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME, REV_DESCR, REV_NAME, ORG_HIRCHY) values ('kuali.org.Sponsor', 'Sponsors, creates or charters another group, typically a committee or sub-committee for a particular task.', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Sponsor', 'is sponsored by', 'is sponsored by', 'kuali.org.hierarchy.Main')
/
insert into KSOR_ORG_ORG_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME, REV_DESCR, REV_NAME, ORG_HIRCHY) values ('kuali.org.HQ', 'The headquarters or administrative office for a larger division', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'HQ', 'has as its headquarters', 'has as its headquarters', 'kuali.org.hierarchy.Main')
/
insert into KSOR_ORG_ORG_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME, REV_DESCR, REV_NAME, ORG_HIRCHY) values ('kuali.org.Board', 'Holds a Board of Trustee Relationship to the organization', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Board', 'has as its board of trustees', 'has as its board of trustees', 'kuali.org.hierarchy.Main')
/
insert into KSOR_ORG_ORG_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME, REV_DESCR, REV_NAME, ORG_HIRCHY) values ('kuali.org.Advisory', 'Indicates that one organization provides advice to the leadership of another organization', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Advisory', 'is advised by', 'is advised by', 'kuali.org.hierarchy.Main')
/
insert into KSOR_ORG_ORG_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME, REV_DESCR, REV_NAME, ORG_HIRCHY) values ('kuali.org.Subcommittee', 'Indicates this is a subcommittee of another committee or organization', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Subcommittee', 'has as a subcommittee ', 'has as a subcommittee ', 'kuali.org.hierarchy.Main')
/
insert into KSOR_ORG_ORG_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME, REV_DESCR, REV_NAME, ORG_HIRCHY) values ('kuali.org.CurriculumChild', 'Indicates that one organization is the child of another organization in the curriculum Hierarchy', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Curriculum Child', 'is parent of', 'is parent of', 'kuali.org.hierarchy.Curriculum')
/

//ORG PERSON RELATION TYPE
insert into KSOR_ORG_PERS_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.PersonRelation.President', 'President', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'President')
/
insert into KSOR_ORG_PERS_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.PersonRelation.EVPP', 'Executive VP and Provost', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Executive Vice President and Provost')
/
insert into KSOR_ORG_PERS_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.PersonRelation.ViceChancellor', 'Vice Chancellor', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Vice Chancellor')
/
insert into KSOR_ORG_PERS_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.PersonRelation.VicePresident', 'Vice President', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Vice President')
/
insert into KSOR_ORG_PERS_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.PersonRelation.AssocDean', 'Associate Dean', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Associate Dean')
/
insert into KSOR_ORG_PERS_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.PersonRelation.AssocProvost', 'Associate Provost',{ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Associate Provost')
/
insert into KSOR_ORG_PERS_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.PersonRelation.Chancellor', 'Chancellor', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Chancellor')
/
insert into KSOR_ORG_PERS_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.PersonRelation.Dean', 'Dean', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Dean')
/
insert into KSOR_ORG_PERS_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.PersonRelation.ExecutiveOfficer', 'Executive Officer', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Executive Officer')
/
insert into KSOR_ORG_PERS_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.PersonRelation.AdministrativeOfficer', 'Administrative Officer', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Administrative Officer')
/
insert into KSOR_ORG_PERS_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.PersonRelation.Director', 'Director', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Director')
/
insert into KSOR_ORG_PERS_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.PersonRelation.Head', 'Head', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Head')
/
insert into KSOR_ORG_PERS_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.PersonRelation.Ex-Officio', 'Ex-Officio Member', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Ex-Officio Member')
/
insert into KSOR_ORG_PERS_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.PersonRelation.AdminMember', 'Administrative Member', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Administrative Member')
/
insert into KSOR_ORG_PERS_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.PersonRelation.AdminAssistant', 'Administrative Assistant', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Administrative Assistant')
/
insert into KSOR_ORG_PERS_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.PersonRelation.Member', 'Member', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Member')
/
insert into KSOR_ORG_PERS_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.PersonRelation.Registrar', 'Registrar', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Registrar')
/
insert into KSOR_ORG_PERS_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.PersonRelation.AssocRegistrar', 'Associate Registrar', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Associate Registrar')
/
insert into KSOR_ORG_PERS_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.PersonRelation.Chair', 'Chair', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Chair')
/
insert into KSOR_ORG_PERS_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.PersonRelation.Co-Chair', 'Co-Chair', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Co-Chair')
/
insert into KSOR_ORG_PERS_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.PersonRelation.Officer', 'Officer', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Officer')
/
insert into KSOR_ORG_PERS_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.PersonRelation.Treasurer', 'Treasurer', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Treasurer')
/
insert into KSOR_ORG_PERS_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.PersonRelation.Secretary', 'Secretary', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Secretary')
/
insert into KSOR_ORG_PERS_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.PersonRelation.Coordinator', 'Coordinator', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Coordinator')
/
insert into KSOR_ORG_PERS_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.PersonRelation.Rep', 'Representative of a constituency', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Representative')
/
insert into KSOR_ORG_PERS_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.PersonRelation.Professor', 'Professor', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Professor')
/

//ORG POSITION RESTRICTION
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('1', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, 'Appointed by the Governor.  With the exception of the student member, each Regent is appointed for a term of five years, and may not serve more than two consecutive terms. The student regent is appointed for a one-year term, and may be reappointed. Regent', '100', 1, '1', 1, 'Member of the Board of Regents', '2', 'kuali.org.PersonRelation.Member')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('2', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Treasurer of the Board of Regents', '2', 'kuali.org.PersonRelation.Treasurer')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('3', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, 'Appointed by the Board of Regents.  The Chancellor is the administrative and executive head of Kuali University and exercises complete executive authority over the University, subject to the direction of the President.   The Chancellor is responsible for ', '100', 1, '1', 1, 'Chancellor', '3', 'kuali.org.PersonRelation.Chancellor')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('4', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chief Academic Officer and Executive Vice Chancellor', '3', 'kuali.org.PersonRelation.ViceChancellor')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('5', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, 'As chief executive officer, the president reports directly the  Board of Regents and is ultimately responsible to that governing board for the supervision and administration of academic, business and fiscal operations, as well as all other matters pertain', '100', 1, '1', 1, 'President of Kuali University', '6', 'kuali.org.PersonRelation.President')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('6', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, 'The role of the Foundation Vice President includes developing policies, procedures, guidelines and long-range plans for the operation of the Kuali University Foundation, overseeing the financial affairs and investments of the Foundation, and serving as ad', '100', 1, '1', 1, 'Foundation Vice President ', '11', 'kuali.org.PersonRelation.VicePresident')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('7', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, 'The Provost is responsible for the strategic leadership and operational delivery of the University''s academic objectives. This includes oversight of the University''s academic staff with the aim of recruiting and retaining staff of the highest distinction ', '100', 1, '1', 1, 'Provost and Executive Vice President for Academic Affairs', '15', 'kuali.org.PersonRelation.EVPP')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('8', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, 'The Vice President for Student Affairs oversees student services and programs for the entire campus community. Composed of 15 departments and over 1500 staff members, the Division of Student Affairs is involved in all aspects of a student�s life including', '100', 1, '1', 1, 'Vice President of the Student Affairs', '17', 'kuali.org.PersonRelation.VicePresident')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('9', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'VP for Business Administration and Finance', '16', 'kuali.org.PersonRelation.VicePresident')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('10', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, 'The dean is the chief academic, administrative, and budgetary officer of the Graduate School and reports directly to the Provost. The Dean of Graduate School at Kuali University oversees recruitment and retention of quality students and faculty.', '100', 1, '1', 1, 'Dean of the Graduate School', '26', 'kuali.org.PersonRelation.Dean')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('11', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, 'The Associate Provost for Academic Affairs and Dean of Undergraduate Studies at Kuali University serves as chief of staff to the provost and is responsible for substantial planning and personnel functions in the university. The associate provost oversees ', '100', 1, '1', 1, 'Associate Provost for Academic Affairs and Dean of Undergraduate Studies', '19', 'kuali.org.PersonRelation.AssocProvost')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('12', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, 'The Associate Provost for Academic Affairs and Dean of Undergraduate Studies at Kuali University serves as chief of staff to the provost and is responsible for substantial planning and personnel functions in the university. The associate provost oversees ', '100', 1, '1', 1, 'Associate Provost for Academic Affairs and Dean of Undergraduate Studies', '24', 'kuali.org.PersonRelation.Dean')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('17', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, 'The Dean of Students Office is responsible for planning, coordinating and implementing a variety of programs and services which are designed to assist and support students in achieving academic and personal success. Areas of oversight include: Drug & Alco', '100', 1, '1', 1, 'Dean of Students', '19', 'kuali.org.PersonRelation.Dean')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('18', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Vice Provost for Information Technology & CIO ', '23', 'kuali.org.PersonRelation.AssocProvost')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('19', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Dean of Arts and Humanities', '28', 'kuali.org.PersonRelation.Dean')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('20', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the English Department', '50', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('21', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the Fine Arts Department', '51', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('22', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the French Department', '52', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('23', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the Geography Department', '53', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('24', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the Linguistics Department', '54', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('25', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the Music Department', '55', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('26', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Dean for Behavioral and Social Sciences', '29', 'kuali.org.PersonRelation.Dean')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('27', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the History Dept', '56', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('28', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the Political Science Dept', '57', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('29', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the Psychology Dept', '58', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('30', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the Sociology Dept', '59', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('31', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Head of the Interdisciplinary Studies in Social Science Program', '60', 'kuali.org.PersonRelation.Head')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('32', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Dean of Engineering', '31', 'kuali.org.PersonRelation.Dean')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('33', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the Chemical Engineering Dept', '61', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('34', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the Civil Engineering Dept', '62', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('35', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the Mechanical Engineering Dept', '63', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('36', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the Computer Science Dept', '64', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('37', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Dean of the Science College', '30', 'kuali.org.PersonRelation.Dean')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('38', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the Biology Dept', '65', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('39', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the Biochemistry Dept', '66', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('40', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the Botany Dept', '67', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('41', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the Chemistry Dept', '68', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('42', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the Geology Dept', '69', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('43', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Dean of the Business School', '32', 'kuali.org.PersonRelation.Dean')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('44', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Head of the Accounting Section', '70', 'kuali.org.PersonRelation.Head')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('45', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Head of the Finance Section', '71', 'kuali.org.PersonRelation.Head')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('46', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Head of the International Business Section', '72', 'kuali.org.PersonRelation.Head')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('47', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Head of the Marketing Section', '73', 'kuali.org.PersonRelation.Head')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('48', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Dean of the Medical School', '34', 'kuali.org.PersonRelation.Dean')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('49', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Deal of the College of Education', '33', 'kuali.org.PersonRelation.Dean')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('50', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Dean of the College of Distance Education', '40', 'kuali.org.PersonRelation.Dean')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('51', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the Extended Studies Program', '39', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('52', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, 'Administers and supervises the implementation of all university financial aid policies, office procedures and funding programs. Supervise all office personnel. Plans, organizes and supervises the operational functions of the Financial Aid Office.', '100', 1, '1', 1, 'Director of Financial Aid', '99', 'kuali.org.PersonRelation.Director')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('53', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, 'Oversee undergraduate students'' college choice, transition to college, retention, and graduation rates. Manage and coordinate recruitment strategies with campus units involved in those areas, including the undergraduate admissions, scholarships and studen', '100', 1, '1', 1, 'Director of Admissions and Enrollment Planning', '98', 'kuali.org.PersonRelation.Director')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('54', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, 'The Registrar oversee customer service to students, faculty members, administrators, alumni, and the general public in the areas of record keeping, course scheduling, course registration, exam scheduling, grades collection, program audit and evaluation, g', '100', 1, '1', 1, 'Registrar', '102', 'kuali.org.PersonRelation.Registrar')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('55', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, 'The Registrar oversee customer service to students, faculty members, administrators, alumni, and the general public in the areas of record keeping, course scheduling, course registration, exam scheduling, grades collection, program audit and evaluation, g', '100', 1, '1', 1, 'Associate Registrar for Curriculumn', '103', 'kuali.org.PersonRelation.AssocRegistrar')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('56', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, 'The Registrar oversee customer service to students, faculty members, administrators, alumni, and the general public in the areas of record keeping, course scheduling, course registration, exam scheduling, grades collection, program audit and evaluation, g', '100', 1, '1', 1, 'Administrative Assistant in the Curriculumn Section of the Registrar''s Office', '102', 'kuali.org.PersonRelation.AdminAssistant')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('57', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, 'The Director Student Accounts is responsible for tuition and student fee assessment, payment and collection.  ', '100', 1, '1', 1, 'Bursar', '107', 'kuali.org.PersonRelation.Director')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('58', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, 'Member terms are for one year beginning on July 1. Members may be reappointed by the units they represent.    The University Academic Advising Committee meets once a month throughout the academic year. The Committee membership is to include representative', '100', 1, '1', 1, 'Member of the Academic Advising Committee', '108', 'kuali.org.PersonRelation.Member')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('59', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, 'Committee membership includes: Associate Provost for Academic Programs, Council of Deans, Department Chairs Cabinet, Faculty, Senate, Student Government Association, Student Affairs ', '100', 1, '1', 1, 'Member of ASPAC', '111', 'kuali.org.PersonRelation.Member')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('60', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, 'The Council''s membership is to include representatives from: Admissions. Provost Division, Student Affairs, Each Academic College (approximately half should be faculty members), Senate, Student Government � preferably a transfer student', '100', 1, '1', 1, 'Member of the Articulation Coordination Council', '112', 'kuali.org.PersonRelation.Member')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('62', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, 'This committee consists of: Council of Deans member, Department Chairs Cabinet, Executive Vice President & Provost, Faculty, Senate Past President, Senate President, Senate President-Elect,  Student Government Association member', '100', 1, '1', 1, '', '113', 'kuali.org.PersonRelation.Member')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('63', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, 'This committee consists of: Associate Vice President - Budget & Planning, Executive Vice President & Provost, President of Kuali University, Vice President for Business & Finance, Vice President for Student Affairs', '100', 1, '1', 1, 'Member of Budget Cabinet', '114', 'kuali.org.PersonRelation.Member')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('64', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, 'The Council of Deans has members from each college.', '100', 1, '1', 1, 'Member of The Council of Deans', '119', 'kuali.org.PersonRelation.Member')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('65', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, 'Chairs of each academic department or program', '100', 1, '1', 1, 'Member of Department Chairs Cabinet', '120', 'kuali.org.PersonRelation.Member')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('66', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '�45CFR46.107 IRB Membership (a) Each IRB shall have at least five members, with varying backgrounds to promote complete and adequate review of research activities commonly conducted by the institution. The IRB shall be sufficiently qualified through the e', '100', 1, '1', 1, 'Member of the IRB', '123', 'kuali.org.PersonRelation.Member')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('67', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, 'The JAC is appointed by the President and is composed of ten faculty members nominated by the members of the University community or the Faculty Senate, five staff members nominated by the Vice President for Student Affairs, five graduate students nominat', '100', 1, '1', 1, 'Member of the JAC', '138', 'kuali.org.PersonRelation.Member')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('68', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, 'Student Government members are elected by the students to a one year term. ', '100', 1, '1', 1, 'Student Government Representative', '132', 'kuali.org.PersonRelation.Rep')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('69', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, 'Student members are elected by the students for a one-year term. ', '100', 1, '1', 1, 'Grad Student Representataive', '133', 'kuali.org.PersonRelation.Rep')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('70', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Senate President', '134', 'kuali.org.PersonRelation.President')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('71', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the AAC', '135', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('72', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the FDARC', '136', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('73', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of  the RPAC', '137', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('74', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the JAC', '138', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('75', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the Faculty Governance Council', '140', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('76', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, 'Must be a faculty member, term for 3 years', '100', 1, '1', 1, 'Chair of the COC', '141', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('77', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, 'Filled by the Associate Provost for Academic Planning and Programs', '100', 1, '1', 1, 'Ex-Officio Member of the COC', '141', 'kuali.org.PersonRelation.Ex-Officio')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('78', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, 'Six faculty members and 4 students, terms for 1 year', '100', 1, '1', 1, 'Member of the COC', '141', 'kuali.org.PersonRelation.Member')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('79', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, 'Ex-Officio- non voting filled by the Associate Registrar for Curriculum', '100', 1, '1', 1, 'Executive Officer of the COC', '141', 'kuali.org.PersonRelation.ExecutiveOfficer')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('80', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, 'Administrative member of the committee, member of the Registrar''s office who reports to the Associate Registrar for Curriculum', '100', 1, '1', 1, 'Administrative Member of the COC', '141', 'kuali.org.PersonRelation.AdminMember')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('81', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the English COC', '147', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('82', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, 'Two Co-Chairs one from the Studio Art section and one from the Art History sections', '100', 1, '1', 1, 'Co-Chair of the FineArts COC', '148', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('83', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the French COC', '149', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('84', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the Geography COC', '150', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('85', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the Linguistics COC', '151', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('86', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the Music COC', '152', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('87', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the History COC', '153', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('88', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the PolySci COC', '154', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('89', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the Psychology COC', '155', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('90', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the Sociology COC', '156', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('91', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, 'There is really no COC and the Chair is by definition the head of the Interdisciplinary Office', '100', 1, '1', 1, 'Chair of the InterdiscBSOS COC', '157', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('92', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the Chemical COC', '158', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('93', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the Civil COC', '159', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('94', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the Mechanical COC', '160', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('95', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the CompSci COC', '161', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('96', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the Biology COC', '162', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('97', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the Biochemistry COC', '163', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('98', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the Botany COC', '164', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('99', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the Chemistry COC', '165', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('100', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the Geology COC', '166', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('101', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Chair of the GenderStudies COC', '167', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('102', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Member of the English COC', '147', 'kuali.org.PersonRelation.Member')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('103', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Member of the FineArts COC', '148', 'kuali.org.PersonRelation.Member')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('104', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Member of the French COC', '149', 'kuali.org.PersonRelation.Member')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('105', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Member of the Geography COC', '150', 'kuali.org.PersonRelation.Member')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('106', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Member of the Linguistics COC', '151', 'kuali.org.PersonRelation.Member')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('107', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Member of the Music COC', '152', 'kuali.org.PersonRelation.Member')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('108', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Member of the History COC', '153', 'kuali.org.PersonRelation.Member')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('109', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Member of the PolySci COC', '154', 'kuali.org.PersonRelation.Member')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('110', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Member of the Psychology COC', '155', 'kuali.org.PersonRelation.Member')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('111', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Member of the Sociology COC', '156', 'kuali.org.PersonRelation.Member')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('112', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Member of the InterdiscBSOS COC', '157', 'kuali.org.PersonRelation.Member')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('113', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Member of the Chemical COC', '158', 'kuali.org.PersonRelation.Member')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('114', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Member of the Civil COC', '159', 'kuali.org.PersonRelation.Member')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('115', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Member of the Mechanical COC', '160', 'kuali.org.PersonRelation.Member')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('116', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Member of the CompSci COC', '161', 'kuali.org.PersonRelation.Member')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('117', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Member of the Biology COC', '162', 'kuali.org.PersonRelation.Member')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('118', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Member of the Biochemistry COC', '163', 'kuali.org.PersonRelation.Member')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('119', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Member of the Botany COC', '164', 'kuali.org.PersonRelation.Member')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('120', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Member of the Chemistry COC', '165', 'kuali.org.PersonRelation.Member')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('122', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Member of the GenderStudies COC', '167', 'kuali.org.PersonRelation.Member')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('123', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Member of the Geology COC', '166', 'kuali.org.PersonRelation.Member')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('124', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Administrative Member of the English COC', '147', 'kuali.org.PersonRelation.AdminMember')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('125', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Administrative Member of the FineArts COC', '148', 'kuali.org.PersonRelation.AdminMember')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('126', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Administrative Member of the French COC', '149', 'kuali.org.PersonRelation.AdminMember')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('127', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Administrative Member of the Geography COC', '150', 'kuali.org.PersonRelation.AdminMember')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('128', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Administrative Member of the Linguistics COC', '151', 'kuali.org.PersonRelation.AdminMember')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('129', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Administrative Member of the Music COC', '152', 'kuali.org.PersonRelation.AdminMember')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('130', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Administrative Member of the History COC', '153', 'kuali.org.PersonRelation.AdminMember')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('131', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Administrative Member of the PolySci COC', '154', 'kuali.org.PersonRelation.AdminMember')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('132', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Administrative Member of the Psychology COC', '155', 'kuali.org.PersonRelation.AdminMember')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('133', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Administrative Member of the Sociology COC', '156', 'kuali.org.PersonRelation.AdminMember')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('134', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Administrative Member of the InterdiscBSOS COC', '157', 'kuali.org.PersonRelation.AdminMember')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('135', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Administrative Member of the Chemical COC', '158', 'kuali.org.PersonRelation.AdminMember')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('136', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Administrative Member of the Civil COC', '159', 'kuali.org.PersonRelation.AdminMember')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('137', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Administrative Member of the Mechanical COC', '160', 'kuali.org.PersonRelation.AdminMember')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('138', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Administrative Member of the CompSci COC', '161', 'kuali.org.PersonRelation.AdminMember')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('139', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Administrative Member of the Biology COC', '162', 'kuali.org.PersonRelation.AdminMember')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('140', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Administrative Member of the Biochemistry COC', '163', 'kuali.org.PersonRelation.AdminMember')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('141', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Administrative Member of the Botany COC', '164', 'kuali.org.PersonRelation.AdminMember')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('142', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Administrative Member of the Chemistry COC', '165', 'kuali.org.PersonRelation.AdminMember')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('144', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Administrative Member of the GenderStudies COC', '167', 'kuali.org.PersonRelation.AdminMember')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('145', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '', '100', 1, '1', 1, 'Administrative Member of the Geology COC', '166', 'kuali.org.PersonRelation.AdminMember')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('146', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, 'Composed of ALL the Administrative Members of each of the Departmental COCs', '100', 1, '1', 1, 'Ex-Officio Member of the Dept COC Reps', '142', 'kuali.org.PersonRelation.Ex-Officio')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('147', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, 'Named Chair', '100', 1, '1', 1, 'Chair of the Geology CIC', '171', 'kuali.org.PersonRelation.Chair')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('148', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, '8 Other Named Faculty members from the department', '100', 1, '1', 1, 'Member of the Geology CIC', '171', 'kuali.org.PersonRelation.Member')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('149', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, 'Must be the Chair of the Departmental COC', '100', 1, '1', 1, 'Ex-Officio Member of the Geology CIC', '171', 'kuali.org.PersonRelation.Ex-Officio')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('150', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, 'Administrative Officer of Chemical Engineering Dept', '100', 1, '1', 1, 'Administrative Officer of Chemical Engineering Dept', '61', 'kuali.org.PersonRelation.AdministrativeOfficer')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('151', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, 'Administrative Officer of Civil Engineering Dept', '100', 1, '1', 1, 'Administrative Officer of Civil Engineering Dept', '62', 'kuali.org.PersonRelation.AdministrativeOfficer')
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE) values ('152', 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 'TESTUSER', {ts '2000-01-01 00:00:00.0'}, 1, 'Administrative Officer of Engineering College', '100', 1, '1', 1, 'Administrative Officer of Engineering College', '31', 'kuali.org.PersonRelation.AdministrativeOfficer')
/

//ORG ORG RELATION
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('1', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '2', '1', 'kuali.org.Board')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('2', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '4', '1', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('3', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '5', '4', 'kuali.org.Board')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('4', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '6', '4', 'kuali.org.HQ')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('5', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '7', '6', 'kuali.org.Advisory')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('6', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '8', '6', 'kuali.org.Advisory')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('7', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '9', '6', 'kuali.org.Report')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('8', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '10', '6', 'kuali.org.Report')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('9', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '11', '6', 'kuali.org.Administer')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('10', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '12', '6', 'kuali.org.Administer')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('11', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '13', '6', 'kuali.org.Administer')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('12', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '14', '13', 'kuali.org.Administer')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('13', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '15', '4', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('14', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '16', '4', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('15', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '17', '4', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('16', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '18', '4', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('17', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '19', '15', 'kuali.org.HQ')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('18', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '20', '16', 'kuali.org.HQ')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('19', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '21', '17', 'kuali.org.HQ')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('20', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '22', '18', 'kuali.org.HQ')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('21', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '23', '19', 'kuali.org.Report')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('22', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '25', '21', 'kuali.org.Report')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('23', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '25', '24', 'kuali.org.HQ')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('24', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '24', '15', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('25', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '26', '15', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('26', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '27', '26', 'kuali.org.HQ')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('27', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '28', '15', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('28', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '29', '15', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('29', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '31', '15', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('30', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '30', '15', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('31', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '32', '15', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('32', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '33', '15', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('33', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '34', '15', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('34', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '35', '28', 'kuali.org.HQ')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('35', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '36', '29', 'kuali.org.HQ')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('36', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '37', '31', 'kuali.org.HQ')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('37', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '38', '30', 'kuali.org.HQ')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('38', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '39', '15', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('39', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '50', '28', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('40', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '51', '28', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('41', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '52', '28', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('42', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '53', '28', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('43', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '54', '28', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('44', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '55', '28', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('45', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '56', '29', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('46', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '57', '29', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('47', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '58', '29', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('48', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '59', '29', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('49', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '60', '29', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('50', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '60', '56', 'kuali.org.Collaborate')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('51', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '60', '57', 'kuali.org.Collaborate')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('52', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '60', '58', 'kuali.org.Collaborate')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('53', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '60', '59', 'kuali.org.Collaborate')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('54', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '61', '31', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('55', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '62', '31', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('56', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '63', '31', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('57', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '64', '31', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('58', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '70', '32', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('59', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '71', '32', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('60', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '72', '32', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('61', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '73', '32', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('62', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '65', '30', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('63', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '66', '30', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('64', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '67', '30', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('65', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '68', '30', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('66', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '69', '30', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('67', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '40', '39', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('68', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '74', '19', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('69', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '75', '19', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('70', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '76', '19', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('71', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '98', '16', 'kuali.org.Report')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('72', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '99', '16', 'kuali.org.Report')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('73', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '98', '16', 'kuali.org.Report')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('74', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '102', '16', 'kuali.org.Report')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('75', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '103', '102', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('76', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '104', '16', 'kuali.org.Report')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('77', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '106', '87', 'kuali.org.Report')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('78', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '105', '16', 'kuali.org.Report')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('79', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '39', '16', 'kuali.org.Report')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('80', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '26', '19', 'kuali.org.Report')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('81', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '77', '16', 'kuali.org.Report')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('82', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '107', '16', 'kuali.org.Report')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('83', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '108', '105', 'kuali.org.Advisory')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('84', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '108', '15', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('85', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '109', '98', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('86', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '110', '121', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('87', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '111', '19', 'kuali.org.Advisory')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('88', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '112', '98', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('89', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '113', '19', 'kuali.org.Advisory')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('90', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '114', '6', 'kuali.org.Advisory')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('91', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '115', '114', 'kuali.org.Advisory')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('92', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '116', '20', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('93', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '117', '6', 'kuali.org.Advisory')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('94', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '118', '106', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('95', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '119', '19', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('96', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '120', '19', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('97', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '121', '134', 'kuali.org.Board')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('98', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '122', '23', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('99', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '123', '124', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('100', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '124', '18', 'kuali.org.Report')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('101', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '125', '71', 'kuali.org.Advisory')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('102', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '126', '41', 'kuali.org.Advisory')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('103', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '127', '17', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('104', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '127', '41', 'kuali.org.Advisory')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('105', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '128', '129', 'kuali.org.Advisory')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('106', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '129', '105', 'kuali.org.Report')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('107', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '130', '102', 'kuali.org.Report')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('108', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '131', '6', 'kuali.org.Advisory')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('109', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '132', '4', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('110', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '133', '132', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('111', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '134', '4', 'kuali.org.Part')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('112', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '134', '6', 'kuali.org.Collaborate')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('113', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '135', '134', 'kuali.org.Subcommittee')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('114', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '136', '134', 'kuali.org.Subcommittee')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('115', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '137', '134', 'kuali.org.Subcommittee')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('116', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '138', '134', 'kuali.org.Subcommittee')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('117', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '139', '138', 'kuali.org.Subcommittee')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('118', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '140', '134', 'kuali.org.Subcommittee')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('119', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '141', '135', 'kuali.org.Subcommittee')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('120', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '24', '141', 'kuali.org.CurriculumChild')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('121', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '28', '24', 'kuali.org.CurriculumChild')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('122', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '29', '24', 'kuali.org.CurriculumChild')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('123', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '31', '24', 'kuali.org.CurriculumChild')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('124', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '30', '24', 'kuali.org.CurriculumChild')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('125', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '26', '141', 'kuali.org.CurriculumChild')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('126', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '28', '26', 'kuali.org.CurriculumChild')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('127', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '29', '26', 'kuali.org.CurriculumChild')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('128', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '31', '26', 'kuali.org.CurriculumChild')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('129', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '30', '26', 'kuali.org.CurriculumChild')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('130', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '147', '28', 'kuali.org.CurriculumChild')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('131', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '148', '28', 'kuali.org.CurriculumChild')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('132', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '149', '28', 'kuali.org.CurriculumChild')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('133', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '150', '28', 'kuali.org.CurriculumChild')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('134', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '151', '28', 'kuali.org.CurriculumChild')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('135', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '152', '28', 'kuali.org.CurriculumChild')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('136', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '153', '28', 'kuali.org.CurriculumChild')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('137', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '154', '29', 'kuali.org.CurriculumChild')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('138', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '155', '29', 'kuali.org.CurriculumChild')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('139', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '156', '29', 'kuali.org.CurriculumChild')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('140', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '157', '29', 'kuali.org.CurriculumChild')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('141', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '158', '31', 'kuali.org.CurriculumChild')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('142', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '159', '31', 'kuali.org.CurriculumChild')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('143', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '160', '31', 'kuali.org.CurriculumChild')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('144', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '161', '31', 'kuali.org.CurriculumChild')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('145', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '162', '30', 'kuali.org.CurriculumChild')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('146', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '163', '30', 'kuali.org.CurriculumChild')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('147', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '164', '30', 'kuali.org.CurriculumChild')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('148', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '165', '30', 'kuali.org.CurriculumChild')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('149', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '166', '30', 'kuali.org.CurriculumChild')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('150', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '167', '141', 'kuali.org.CurriculumChild')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('151', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '168', '141', 'kuali.org.CurriculumChild')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('152', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '169', '141', 'kuali.org.CurriculumChild')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('153', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '170', '141', 'kuali.org.CurriculumChild')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('154', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '143', '28', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('155', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '144', '29', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('156', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '145', '30', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('157', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '146', '31', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('158', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '147', '50', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('159', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '148', '51', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('160', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '149', '52', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('161', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '150', '53', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('162', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '151', '54', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('163', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '152', '55', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('164', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '153', '56', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('165', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '154', '57', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('166', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '155', '58', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('167', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '156', '59', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('168', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '157', '60', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('169', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '158', '61', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('170', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '159', '62', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('171', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '160', '63', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('172', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '161', '64', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('173', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '162', '65', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('174', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '163', '66', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('175', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '164', '67', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('176', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '165', '68', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('177', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '166', '69', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('178', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '167', '49', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('179', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '168', '32', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('180', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '169', '33', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('181', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '170', '34', 'kuali.org.Chartered')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('182', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '166', '171', 'kuali.org.Functional')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, RELATED_ORG, ORG, TYPE) values ('183', 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 'TESTUSER', {ts '2009-01-22 00:00:00.0'}, 1, {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Active', '171', '69', 'kuali.org.Chartered')
/


//ORG attributes
insert into KSOR_ORG_ATTR (ID, ATTR_VALUE, ATTR_NAME, OWNER) values ('2', 'Governors', 'ks.org.attr.Alias', '2')
/
insert into KSOR_ORG_ATTR (ID, ATTR_VALUE, ATTR_NAME, OWNER) values ('4', 'KU', 'ks.org.attr.Alias', '4')
/
insert into KSOR_ORG_ATTR (ID, ATTR_VALUE, ATTR_NAME, OWNER) values ('11', 'Fundraising, Endowment Management', 'ks.org.attr.Alias', '11')
/
insert into KSOR_ORG_ATTR (ID, ATTR_VALUE, ATTR_NAME, OWNER) values ('18', 'Research', 'ks.org.attr.Alias', '18')
/
insert into KSOR_ORG_ATTR (ID, ATTR_VALUE, ATTR_NAME, OWNER) values ('19', 'EVPP', 'ks.org.attr.Alias', '19')
/
insert into KSOR_ORG_ATTR (ID, ATTR_VALUE, ATTR_NAME, OWNER) values ('20', 'CFO, COO', 'ks.org.attr.Alias', '20')
/
insert into KSOR_ORG_ATTR (ID, ATTR_VALUE, ATTR_NAME, OWNER) values ('26', 'The Graduate School or Program', 'ks.org.attr.Alias', '26')
/
insert into KSOR_ORG_ATTR (ID, ATTR_VALUE, ATTR_NAME, OWNER) values ('28', 'Faculty of Arts&Hum', 'ks.org.attr.Alias', '28')
/
insert into KSOR_ORG_ATTR (ID, ATTR_VALUE, ATTR_NAME, OWNER) values ('29', 'Faculty of BSOS', 'ks.org.attr.Alias', '29')
/
insert into KSOR_ORG_ATTR (ID, ATTR_VALUE, ATTR_NAME, OWNER) values ('32', 'The B-School', 'ks.org.attr.Alias', '32')
/
insert into KSOR_ORG_ATTR (ID, ATTR_VALUE, ATTR_NAME, OWNER) values ('33', 'The Ed-School', 'ks.org.attr.Alias', '33')
/
insert into KSOR_ORG_ATTR (ID, ATTR_VALUE, ATTR_NAME, OWNER) values ('34', 'Med-School', 'ks.org.attr.Alias', '34')
/
insert into KSOR_ORG_ATTR (ID, ATTR_VALUE, ATTR_NAME, OWNER) values ('39', 'Continuing Eduction, Non-Credit, Not for Credit', 'ks.org.attr.Alias', '39')
/
insert into KSOR_ORG_ATTR (ID, ATTR_VALUE, ATTR_NAME, OWNER) values ('40', 'Distance Learning', 'ks.org.attr.Alias', '40')
/
insert into KSOR_ORG_ATTR (ID, ATTR_VALUE, ATTR_NAME, OWNER) values ('41', 'Experimental, International, Experiential, Alternative', 'ks.org.attr.Alias', '41')
/
insert into KSOR_ORG_ATTR (ID, ATTR_VALUE, ATTR_NAME, OWNER) values ('46', 'Exchange, Non-Institute', 'ks.org.attr.Alias', '46')
/
insert into KSOR_ORG_ATTR (ID, ATTR_VALUE, ATTR_NAME, OWNER) values ('47', 'International, Foreign Exchange', 'ks.org.attr.Alias', '47')
/
insert into KSOR_ORG_ATTR (ID, ATTR_VALUE, ATTR_NAME, OWNER) values ('49', 'Women''s Studies (old)', 'ks.org.attr.Alias', '49')
/
insert into KSOR_ORG_ATTR (ID, ATTR_VALUE, ATTR_NAME, OWNER) values ('51', 'FA', 'ks.org.attr.Alias', '51')
/
insert into KSOR_ORG_ATTR (ID, ATTR_VALUE, ATTR_NAME, OWNER) values ('57', 'PolySci', 'ks.org.attr.Alias', '57')
/
insert into KSOR_ORG_ATTR (ID, ATTR_VALUE, ATTR_NAME, OWNER) values ('58', 'Psych', 'ks.org.attr.Alias', '58')
/
insert into KSOR_ORG_ATTR (ID, ATTR_VALUE, ATTR_NAME, OWNER) values ('59', 'Socs', 'ks.org.attr.Alias', '59')
/
insert into KSOR_ORG_ATTR (ID, ATTR_VALUE, ATTR_NAME, OWNER) values ('61', 'Chem E', 'ks.org.attr.Alias', '61')
/
insert into KSOR_ORG_ATTR (ID, ATTR_VALUE, ATTR_NAME, OWNER) values ('63', 'Mech E', 'ks.org.attr.Alias', '63')
/
insert into KSOR_ORG_ATTR (ID, ATTR_VALUE, ATTR_NAME, OWNER) values ('64', 'Comp Sci', 'ks.org.attr.Alias', '64')
/
insert into KSOR_ORG_ATTR (ID, ATTR_VALUE, ATTR_NAME, OWNER) values ('66', 'Biochem', 'ks.org.attr.Alias', '66')
/
insert into KSOR_ORG_ATTR (ID, ATTR_VALUE, ATTR_NAME, OWNER) values ('132', 'Alma Mater Society', 'ks.org.attr.Alias', '132')
/
insert into KSOR_ORG_ATTR (ID, ATTR_VALUE, ATTR_NAME, OWNER) values ('133', 'Graduate and Professional Student Society', 'ks.org.attr.Alias', '133')
/
insert into KSOR_ORG_ATTR (ID, ATTR_VALUE, ATTR_NAME, OWNER) values ('134', 'Faculty', 'ks.org.attr.Alias', '134')
/
insert into KSOR_ORG_ATTR (ID, ATTR_VALUE, ATTR_NAME, OWNER) values ('141', 'The Curriculum Committee', 'ks.org.attr.Alias', '141')
/
insert into KSOR_ORG_ATTR (ID, ATTR_VALUE, ATTR_NAME, OWNER) values ('142', 'Curriculumn Coordinators', 'ks.org.attr.Alias', '142')
/
insert into KSOR_ORG_ATTR (ID, ATTR_VALUE, ATTR_NAME, OWNER) values ('171', 'Geology CIC', 'ks.org.attr.Alias', '171')
/
insert into KSOR_ORG_ATTR (ID, ATTR_VALUE, ATTR_NAME, OWNER) values ('1', 'KU System', 'ks.org.attr.Alias', '1')
/

//ORG Person Relation
insert into KSOR_ORG_PERS_RELTN (ID, ORG, PERS_ID, EFF_DT, EXPIR_DT, ORG_PERS_RELTN_TYPE, ST, VER_NBR) values ('1', '68', 'KIM-1', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'kuali.org.PersonRelation.Head', 'Active', 1)
/
insert into KSOR_ORG_PERS_RELTN (ID, ORG, PERS_ID, EFF_DT, EXPIR_DT, ORG_PERS_RELTN_TYPE, ST, VER_NBR) values ('2', '28', 'KIM-2', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'kuali.org.PersonRelation.AdminAssistant', 'Active', 1)
/
insert into KSOR_ORG_PERS_RELTN (ID, ORG, PERS_ID, EFF_DT, EXPIR_DT, ORG_PERS_RELTN_TYPE, ST, VER_NBR) values ('3', '147', 'KIM-3', {ts '2000-01-01 00:00:00.0'}, {ts '2020-12-31 00:00:00.0'}, 'kuali.org.PersonRelation.Coordinator', 'Active', 1)
/
insert into KSOR_ORG_PERS_RELTN (ID, ORG, PERS_ID, EFF_DT, EXPIR_DT, ORG_PERS_RELTN_TYPE, ST, VER_NBR) values ('4', '68', 'KIM-4', {ts '2000-01-01 00:00:00.0'}, null,'kuali.org.PersonRelation.Professor', 'Active', 1)
/
insert into KSOR_ORG_PERS_RELTN (ID, ORG, PERS_ID, EFF_DT, EXPIR_DT, ORG_PERS_RELTN_TYPE, ST, VER_NBR) values ('5', '53', 'KIM-1', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'kuali.org.PersonRelation.Head', 'Active', 1)
/
insert into KSOR_ORG_PERS_RELTN (ID, ORG, PERS_ID, EFF_DT, EXPIR_DT, ORG_PERS_RELTN_TYPE, ST, VER_NBR) values ('6', '68', 'KIM-1', {ts '2000-01-01 00:00:00.0'}, null,'kuali.org.PersonRelation.Professor', 'Active', 1)
/
insert into KSOR_ORG_PERS_RELTN (ID, ORG, PERS_ID, EFF_DT, EXPIR_DT, ORG_PERS_RELTN_TYPE, ST, VER_NBR) values ('7', '31', 'user1', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'kuali.org.PersonRelation.AdministrativeOfficer', 'Active', 1)
/
insert into KSOR_ORG_PERS_RELTN (ID, ORG, PERS_ID, EFF_DT, EXPIR_DT, ORG_PERS_RELTN_TYPE, ST, VER_NBR) values ('8', '61', 'user2', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'kuali.org.PersonRelation.AdministrativeOfficer', 'Active', 1)
/
insert into KSOR_ORG_PERS_RELTN (ID, ORG, PERS_ID, EFF_DT, EXPIR_DT, ORG_PERS_RELTN_TYPE, ST, VER_NBR) values ('9', '62', 'user2', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},'kuali.org.PersonRelation.AdministrativeOfficer', 'Active', 1)
/

//
insert into KSOR_ORG_TYPE_JN_ORG_PERRL_TYP (ORG_TYPE_ID, ORG_PERS_RELTN_TYPE_ID) VALUES ('kuali.org.Department', 'kuali.org.PersonRelation.Professor')
/
insert into KSOR_ORG_TYPE_JN_ORG_PERRL_TYP (ORG_TYPE_ID, ORG_PERS_RELTN_TYPE_ID) VALUES ('kuali.org.School', 'kuali.org.PersonRelation.Head')
/
insert into KSOR_ORG_TYPE_JN_ORG_PERRL_TYP (ORG_TYPE_ID, ORG_PERS_RELTN_TYPE_ID) VALUES ('kuali.org.School', 'kuali.org.PersonRelation.Professor')
/

// Org Person Relation Type
insert into KSOR_ORG_JN_ORG_PERS_REL_TYPE (ORG_ID, ORG_PERS_RELTN_TYPE_ID) VALUES ('68', 'kuali.org.PersonRelation.Head')
/

// Org Hierarchy Type Join
insert into KSOR_ORG_HIRCHY_JN_ORG_TYPE (ORG_HIRCHY_ID, ORG_TYPE_ID) values ('kuali.org.hierarchy.Main', 'kuali.org.CorporateEntity')
/
insert into KSOR_ORG_HIRCHY_JN_ORG_TYPE (ORG_HIRCHY_ID, ORG_TYPE_ID) values ('kuali.org.hierarchy.Main', 'kuali.org.Board')
/
insert into KSOR_ORG_HIRCHY_JN_ORG_TYPE (ORG_HIRCHY_ID, ORG_TYPE_ID) values ('kuali.org.hierarchy.Main', 'kuali.org.Division')
/
insert into KSOR_ORG_HIRCHY_JN_ORG_TYPE (ORG_HIRCHY_ID, ORG_TYPE_ID) values ('kuali.org.hierarchy.Main', 'kuali.org.School')
/
insert into KSOR_ORG_HIRCHY_JN_ORG_TYPE (ORG_HIRCHY_ID, ORG_TYPE_ID) values ('kuali.org.hierarchy.Main', 'kuali.org.Program')
/
insert into KSOR_ORG_HIRCHY_JN_ORG_TYPE (ORG_HIRCHY_ID, ORG_TYPE_ID) values ('kuali.org.hierarchy.Main', 'kuali.org.Center')
/
insert into KSOR_ORG_HIRCHY_JN_ORG_TYPE (ORG_HIRCHY_ID, ORG_TYPE_ID) values ('kuali.org.hierarchy.Main', 'kuali.org.College')
/
insert into KSOR_ORG_HIRCHY_JN_ORG_TYPE (ORG_HIRCHY_ID, ORG_TYPE_ID) values ('kuali.org.hierarchy.Main', 'kuali.org.Department')
/
insert into KSOR_ORG_HIRCHY_JN_ORG_TYPE (ORG_HIRCHY_ID, ORG_TYPE_ID) values ('kuali.org.hierarchy.Main', 'kuali.org.Office')
/
insert into KSOR_ORG_HIRCHY_JN_ORG_TYPE (ORG_HIRCHY_ID, ORG_TYPE_ID) values ('kuali.org.hierarchy.Main', 'kuali.org.Association')
/
insert into KSOR_ORG_HIRCHY_JN_ORG_TYPE (ORG_HIRCHY_ID, ORG_TYPE_ID) values ('kuali.org.hierarchy.Main', 'kuali.org.AdvisoryGroup')
/
insert into KSOR_ORG_HIRCHY_JN_ORG_TYPE (ORG_HIRCHY_ID, ORG_TYPE_ID) values ('kuali.org.hierarchy.Main', 'kuali.org.WorkGroup')
/
insert into KSOR_ORG_HIRCHY_JN_ORG_TYPE (ORG_HIRCHY_ID, ORG_TYPE_ID) values ('kuali.org.hierarchy.Main', 'kuali.org.Section')
/
insert into KSOR_ORG_HIRCHY_JN_ORG_TYPE (ORG_HIRCHY_ID, ORG_TYPE_ID) values ('kuali.org.hierarchy.Main', 'kuali.org.Senate')
/
insert into KSOR_ORG_HIRCHY_JN_ORG_TYPE (ORG_HIRCHY_ID, ORG_TYPE_ID) values ('kuali.org.hierarchy.Main', 'kuali.org.Committee')
/
insert into KSOR_ORG_HIRCHY_JN_ORG_TYPE (ORG_HIRCHY_ID, ORG_TYPE_ID) values ('kuali.org.hierarchy.Main', 'kuali.org.AdhocCommittee')
/
insert into KSOR_ORG_HIRCHY_JN_ORG_TYPE (ORG_HIRCHY_ID, ORG_TYPE_ID) values ('kuali.org.hierarchy.Main', 'kuali.org.COC')
/
