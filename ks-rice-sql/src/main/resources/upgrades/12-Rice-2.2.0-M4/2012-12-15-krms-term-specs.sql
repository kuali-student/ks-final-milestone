-- Completed Course
insert into krms_term_spec_t (term_spec_id, nm, typ, actv, ver_nbr, desc_txt, nmspc_cd)
 values (krms_term_spec_s.nextval, 'completedCourse', 'java.lang.Boolean', 'Y', 1, 'Completed course', 'KS-SYS')
/

-- Completed Courses
insert into krms_term_spec_t (term_spec_id, nm, typ, actv, ver_nbr, desc_txt, nmspc_cd)
 values (krms_term_spec_s.nextval, 'completedCourses', 'java.lang.Boolean', 'Y', 1, 'Completed courses', 'KS-SYS')
/

-- Number of Completed Courses
insert into krms_term_spec_t (term_spec_id, nm, typ, actv, ver_nbr, desc_txt, nmspc_cd)
 values (krms_term_spec_s.nextval, 'numberOfCompletedCourses', 'java.lang.Integer', 'Y', 1, ' Number of completed courses', 'KS-SYS')
/

-- Number of Credits from Completed Courses
insert into krms_term_spec_t (term_spec_id, nm, typ, actv, ver_nbr, desc_txt, nmspc_cd)
 values (krms_term_spec_s.nextval, 'numberOfCreditsFromCompletedCourses', 'java.lang.Integer', 'Y', 1, 'Number of credits from completed courses', 'KS-SYS')
/

-- Enrolled Courses
insert into krms_term_spec_t (term_spec_id, nm, typ, actv, ver_nbr, desc_txt, nmspc_cd)
 values (krms_term_spec_s.nextval, 'enrolledCourses', 'java.lang.Integer', 'Y', 1, 'Enrolled courses', 'KS-SYS')
/

-- GPA for Courses
insert into krms_term_spec_t (term_spec_id, nm, typ, actv, ver_nbr, desc_txt, nmspc_cd)
 values (krms_term_spec_s.nextval, 'GPAForCourses', 'java.lang.Integer', 'Y', 1, 'GPA for courses', 'KS-SYS')
/

-- Grade Type for Courses
insert into krms_term_spec_t (term_spec_id, nm, typ, actv, ver_nbr, desc_txt, nmspc_cd)
 values (krms_term_spec_s.nextval, 'gradeTypeForCourses', 'java.lang.Integer', 'Y', 1, 'Grade type for courses', 'KS-SYS')
/

-- Grade for Courses
--insert into krms_term_spec_t (term_spec_id, nm, typ, actv, ver_nbr, desc_txt, nmspc_cd)
-- values (krms_term_spec_s.nextval, 'gradeForCourses', 'java.lang.Integer', 'Y', 1, 'Grade for courses', 'KS-SYS')
--/

-- Number of Credits
insert into krms_term_spec_t (term_spec_id, nm, typ, actv, ver_nbr, desc_txt, nmspc_cd)
 values (krms_term_spec_s.nextval, 'numberOfCredits', 'java.lang.Integer', 'Y', 1, 'Number of credits', 'KS-SYS')
/

-- Number of Credits from Organization
insert into krms_term_spec_t (term_spec_id, nm, typ, actv, ver_nbr, desc_txt, nmspc_cd)
 values (krms_term_spec_s.nextval, 'numberOfCreditsFromOrganization', 'java.lang.Integer', 'Y', 1, 'Number of credits from organization', 'KS-SYS')
/

-- Admin Organization Permission Required
insert into krms_term_spec_t (term_spec_id, nm, typ, actv, ver_nbr, desc_txt, nmspc_cd)
 values (krms_term_spec_s.nextval, 'adminOrganizationPermissionRequired', 'java.lang.Boolean', 'Y', 1, 'Admin organization permission required', 'KS-SYS')
/

-- Score on Test
insert into krms_term_spec_t (term_spec_id, nm, typ, actv, ver_nbr, desc_txt, nmspc_cd)
 values (krms_term_spec_s.nextval, 'scoreOnTest', 'java.lang.Integer', 'Y', 1, 'Score on test', 'KS-SYS')
/

-- Admitted to Program
insert into krms_term_spec_t (term_spec_id, nm, typ, actv, ver_nbr, desc_txt, nmspc_cd)
 values (krms_term_spec_s.nextval, 'admittedToProgram', 'java.lang.Boolean', 'Y', 1, 'Admitted to program', 'KS-SYS')
/

-- Admitted to Program Limit Courses in Organization for Duration
insert into krms_term_spec_t (term_spec_id, nm, typ, actv, ver_nbr, desc_txt, nmspc_cd)
 values (krms_term_spec_s.nextval, 'admittedToProgramLimitCoursesInOrgForDuration', 'java.lang.Integer', 'Y', 1, 'Admitted to program limit courses in organization for duration', 'KS-SYS')
/