Update krms_term_spec_t set nm = 'CompletedCourse' where nm = 'completedCourse'
/
Update krms_term_spec_t set nm = 'CompletedCourses' where nm = 'completedCourses'
/
Update krms_term_spec_t set nm = 'NumberOfCompletedCourses' where nm = 'numberOfCompletedCourses'
/
Update krms_term_spec_t set nm = 'NumberOfCreditsFromCompletedCourses' where nm = 'numberOfCreditsFromCompletedCourses'
/
Update krms_term_spec_t set nm = 'EnrolledCourses' where nm = 'enrolledCourses'
/
Update krms_term_spec_t set nm = 'GradeTypeForCourses' where nm = 'gradeTypeForCourses'
/
Update krms_term_spec_t set nm = 'NumberOfCredits' where nm = 'numberOfCredits'
/
Update krms_term_spec_t set nm = 'NumberOfCreditsFromOrganization' where nm = 'numberOfCreditsFromOrganization'
/
Update krms_term_spec_t set nm = 'AdminOrganizationPermissionRequired' where nm = 'adminOrganizationPermissionRequired'
/
Update krms_term_spec_t set nm = 'ScoreOnTest' where nm = 'scoreOnTest'
/
Update krms_term_spec_t set nm = 'AdmittedToProgram' where nm = 'admittedToProgram'
/
Update krms_term_spec_t set nm = 'AdmittedToProgramLimitCoursesInOrgForDuration' where nm = 'admittedToProgramLimitCoursesInOrgForDuration'
/
Insert into krms_term_spec_t (term_spec_id, nm, typ, actv, ver_nbr, desc_txt, nmspc_cd) VALUES (krms_term_spec_s.nextval, 'FreeFormText', 'java.lang.Boolean', 'Y', 1, 'Free Form Text', 'KS-SYS')
/
insert into krms_term_rslvr_t (term_rslvr_id, nm, typ_id, output_term_spec_id, actv, ver_nbr, nmspc_cd)
values (krms_term_rslvr_s.nextval, 'FreeFormText', '10000', (Select term_spec_id from krms_term_spec_t where nm = 'FreeFormText'), 'Y', 0, 'KS-SYS')
/
insert into krms_term_rslvr_t (term_rslvr_id, nm, typ_id, output_term_spec_id, actv, ver_nbr, nmspc_cd)
values (krms_term_rslvr_s.nextval, 'CompletedCourse', '10000', (Select term_spec_id from krms_term_spec_t where nm = 'CompletedCourse'), 'Y', 1, 'KS-SYS')
/
insert into krms_term_rslvr_t (term_rslvr_id, nm, typ_id, output_term_spec_id, actv, ver_nbr, nmspc_cd)
values (krms_term_rslvr_s.nextval, 'CompletedCourses', '10000', (Select term_spec_id from krms_term_spec_t where nm = 'CompletedCourses'), 'Y', 1, 'KS-SYS')
/
insert into krms_term_rslvr_t (term_rslvr_id, nm, typ_id, output_term_spec_id, actv, ver_nbr, nmspc_cd)
values (krms_term_rslvr_s.nextval, 'NumberOfCompletedCourses', '10000', (Select term_spec_id from krms_term_spec_t where nm = 'NumberOfCompletedCourses'), 'Y', 1, 'KS-SYS')
/
insert into krms_term_rslvr_t (term_rslvr_id, nm, typ_id, output_term_spec_id, actv, ver_nbr, nmspc_cd)
values (krms_term_rslvr_s.nextval, 'NumberOfCreditsFromCompletedCourses', '10000', (Select term_spec_id from krms_term_spec_t where nm = 'NumberOfCreditsFromCompletedCourses'), 'Y', 1, 'KS-SYS')
/
insert into krms_term_rslvr_t (term_rslvr_id, nm, typ_id, output_term_spec_id, actv, ver_nbr, nmspc_cd)
values (krms_term_rslvr_s.nextval, 'EnrolledCourses', '10000', (Select term_spec_id from krms_term_spec_t where nm = 'EnrolledCourses'), 'Y', 1, 'KS-SYS')
/
insert into krms_term_rslvr_t (term_rslvr_id, nm, typ_id, output_term_spec_id, actv, ver_nbr, nmspc_cd)
values (krms_term_rslvr_s.nextval, 'GPAForCourses', '10000', (Select term_spec_id from krms_term_spec_t where nm = 'GPAForCourses'), 'Y', 1, 'KS-SYS')
/
insert into krms_term_rslvr_t (term_rslvr_id, nm, typ_id, output_term_spec_id, actv, ver_nbr, nmspc_cd)
values (krms_term_rslvr_s.nextval, 'GradeTypeForCourses', '10000', (Select term_spec_id from krms_term_spec_t where nm = 'GradeTypeForCourses'), 'Y', 1, 'KS-SYS')
/
insert into krms_term_rslvr_t (term_rslvr_id, nm, typ_id, output_term_spec_id, actv, ver_nbr, nmspc_cd)
values (krms_term_rslvr_s.nextval, 'NumberOfCredits', '10000', (Select term_spec_id from krms_term_spec_t where nm = 'NumberOfCredits'), 'Y', 1, 'KS-SYS')
/
insert into krms_term_rslvr_t (term_rslvr_id, nm, typ_id, output_term_spec_id, actv, ver_nbr, nmspc_cd)
values (krms_term_rslvr_s.nextval, 'NumberOfCreditsFromOrganization', '10000', (Select term_spec_id from krms_term_spec_t where nm = 'NumberOfCreditsFromOrganization'), 'Y', 1, 'KS-SYS')
/
insert into krms_term_rslvr_t (term_rslvr_id, nm, typ_id, output_term_spec_id, actv, ver_nbr, nmspc_cd)
values (krms_term_rslvr_s.nextval, 'AdminOrganizationPermissionRequired', '10000', (Select term_spec_id from krms_term_spec_t where nm = 'AdminOrganizationPermissionRequired'), 'Y', 1, 'KS-SYS')
/
insert into krms_term_rslvr_t (term_rslvr_id, nm, typ_id, output_term_spec_id, actv, ver_nbr, nmspc_cd)
values (krms_term_rslvr_s.nextval, 'ScoreOnTest', '10000', (Select term_spec_id from krms_term_spec_t where nm = 'ScoreOnTest'), 'Y', 1, 'KS-SYS')
/
insert into krms_term_rslvr_t (term_rslvr_id, nm, typ_id, output_term_spec_id, actv, ver_nbr, nmspc_cd)
values (krms_term_rslvr_s.nextval, 'AdmittedToProgram', '10000', (Select term_spec_id from krms_term_spec_t where nm = 'AdmittedToProgram'), 'Y', 1, 'KS-SYS')
/
insert into krms_term_rslvr_t (term_rslvr_id, nm, typ_id, output_term_spec_id, actv, ver_nbr, nmspc_cd)
values (krms_term_rslvr_s.nextval, 'AdmittedToProgramLimitCoursesInOrgForDuration', '10000', (Select term_spec_id from krms_term_spec_t where nm = 'AdmittedToProgramLimitCoursesInOrgForDuration'), 'Y', 1, 'KS-SYS')
/