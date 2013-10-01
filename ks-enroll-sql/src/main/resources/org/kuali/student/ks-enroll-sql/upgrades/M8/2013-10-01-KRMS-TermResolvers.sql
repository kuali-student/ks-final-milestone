Insert into KRMS_TYP_T (ACTV,TYP_ID,NM,NMSPC_CD,SRVC_NM,VER_NBR) values ('Y',CONCAT('KS-KRMS-TYP-', KS_RICE_ID_S.NEXTVAL),'kuali.krms.termresolver.type.course','KS-SYS','termresolverTypeService',0)
/

Insert into KRMS_TERM_SPEC_T (ACTV,DESC_TXT,NM,NMSPC_CD,TERM_SPEC_ID,TYP,VER_NBR)
  values ('Y','Admin Org Permission','AdminOrgPermission','KS-SYS','KS-KRMS-TERM-SPEC-10000','java.lang.Boolean',1)
/
Insert into KRMS_TERM_RSLVR_T (ACTV,NM,NMSPC_CD,OUTPUT_TERM_SPEC_ID,TERM_RSLVR_ID,TYP_ID,VER_NBR)
  values ('Y','AdminOrgPermission','KS-SYS','KS-KRMS-TERM-SPEC-10000','KS-KRMS-TERM-RSLVR-10000',(Select TYP_ID from KRMS_TYP_T where NM = 'kuali.krms.termresolver.type.course'),0)
/

Insert into KRMS_TERM_SPEC_T (ACTV,DESC_TXT,NM,NMSPC_CD,TERM_SPEC_ID,TYP,VER_NBR)
  values ('Y','Admitted Program At Course Campus','AdmittedToProgramAtCourseCampus','KS-SYS','KS-KRMS-TERM-SPEC-10001','java.lang.Boolean',1)
/
Insert into KRMS_TERM_RSLVR_T (ACTV,NM,NMSPC_CD,OUTPUT_TERM_SPEC_ID,TERM_RSLVR_ID,TYP_ID,VER_NBR)
  values ('Y','AdmittedToProgramAtCourseCampus','KS-SYS','KS-KRMS-TERM-SPEC-10001','KS-KRMS-TERM-RSLVR-10001',(Select TYP_ID from KRMS_TYP_T where NM = 'kuali.krms.termresolver.type.course'),0)
/

Insert into KRMS_TERM_SPEC_T (ACTV,DESC_TXT,NM,NMSPC_CD,TERM_SPEC_ID,TYP,VER_NBR)
  values ('Y','Admitted Program','AdmittedProgram','KS-SYS','KS-KRMS-TERM-SPEC-10002','java.lang.Boolean',1)
/
Insert into KRMS_TERM_RSLVR_T (ACTV,NM,NMSPC_CD,OUTPUT_TERM_SPEC_ID,TERM_RSLVR_ID,TYP_ID,VER_NBR)
  values ('Y','AdmittedProgram','KS-SYS','KS-KRMS-TERM-SPEC-10002','KS-KRMS-TERM-RSLVR-10002',(Select TYP_ID from KRMS_TYP_T where NM = 'kuali.krms.termresolver.type.course'),0)
/

Insert into KRMS_TERM_SPEC_T (ACTV,DESC_TXT,NM,NMSPC_CD,TERM_SPEC_ID,TYP,VER_NBR)
  values ('Y','Admitted Program With Class Standing','AdmittedToProgramWithClassStanding','KS-SYS','KS-KRMS-TERM-SPEC-10003','java.lang.Boolean',1)
/
Insert into KRMS_TERM_RSLVR_T (ACTV,NM,NMSPC_CD,OUTPUT_TERM_SPEC_ID,TERM_RSLVR_ID,TYP_ID,VER_NBR)
  values ('Y','AdmittedToProgramWithClassStanding','KS-SYS','KS-KRMS-TERM-SPEC-10003','KS-KRMS-TERM-RSLVR-10003',(Select TYP_ID from KRMS_TYP_T where NM = 'kuali.krms.termresolver.type.course'),0)
/

Insert into KRMS_TERM_SPEC_T (ACTV,DESC_TXT,NM,NMSPC_CD,TERM_SPEC_ID,TYP,VER_NBR)
  values ('Y','Completed Couse As Of Term','CompletedCourseForTerm','KS-SYS','KS-KRMS-TERM-SPEC-10004','java.lang.Boolean',1)
/
Insert into KRMS_TERM_RSLVR_T (ACTV,NM,NMSPC_CD,OUTPUT_TERM_SPEC_ID,TERM_RSLVR_ID,TYP_ID,VER_NBR)
  values ('Y','CompletedCourseForTerm','KS-SYS','KS-KRMS-TERM-SPEC-10004','KS-KRMS-TERM-RSLVR-10004',(Select TYP_ID from KRMS_TYP_T where NM = 'kuali.krms.termresolver.type.course'),0)
/

Insert into KRMS_TERM_SPEC_T (ACTV,DESC_TXT,NM,NMSPC_CD,TERM_SPEC_ID,TYP,VER_NBR)
  values ('Y','Completed Course Between Terms','CompletedCourseBetweenTerms','KS-SYS','KS-KRMS-TERM-SPEC-10005','java.lang.Boolean',1)
/
Insert into KRMS_TERM_RSLVR_T (ACTV,NM,NMSPC_CD,OUTPUT_TERM_SPEC_ID,TERM_RSLVR_ID,TYP_ID,VER_NBR)
  values ('Y','CompletedCourseBetweenTerms','KS-SYS','KS-KRMS-TERM-SPEC-10005','KS-KRMS-TERM-RSLVR-10005',(Select TYP_ID from KRMS_TYP_T where NM = 'kuali.krms.termresolver.type.course'),0)
/

Insert into KRMS_TERM_SPEC_T (ACTV,DESC_TXT,NM,NMSPC_CD,TERM_SPEC_ID,TYP,VER_NBR)
  values ('Y','Completed Course Prior To Term','CompletedCoursePriorToTerm','KS-SYS','KS-KRMS-TERM-SPEC-10006','java.lang.Boolean',1)
/
Insert into KRMS_TERM_RSLVR_T (ACTV,NM,NMSPC_CD,OUTPUT_TERM_SPEC_ID,TERM_RSLVR_ID,TYP_ID,VER_NBR)
  values ('Y','CompletedCoursePriorToTerm','KS-SYS','KS-KRMS-TERM-SPEC-10006','KS-KRMS-TERM-RSLVR-10006',(Select TYP_ID from KRMS_TYP_T where NM = 'kuali.krms.termresolver.type.course'),0)
/

Update KRMS_TERM_SPEC_T set NM = 'CompletedCoursesOld' where NM = 'CompletedCourses'
/
Insert into KRMS_TERM_SPEC_T (ACTV,DESC_TXT,NM,NMSPC_CD,TERM_SPEC_ID,TYP,VER_NBR)
  values ('Y','Completed Courses','CompletedCourses','KS-SYS','KS-KRMS-TERM-SPEC-10007','java.lang.Boolean',1)
/
Delete from KRMS_TERM_RSLVR_T where NM = 'CompletedCourses'
/
Insert into KRMS_TERM_RSLVR_T (ACTV,NM,NMSPC_CD,OUTPUT_TERM_SPEC_ID,TERM_RSLVR_ID,TYP_ID,VER_NBR)
  values ('Y','CompletedCourses','KS-SYS','KS-KRMS-TERM-SPEC-10007','KS-KRMS-TERM-RSLVR-10007',(Select TYP_ID from KRMS_TYP_T where NM = 'kuali.krms.termresolver.type.course'),0)
/

Update KRMS_TERM_SPEC_T set NM = 'CompletedCourseOld' where NM = 'CompletedCourse'
/
Insert into KRMS_TERM_SPEC_T (ACTV,DESC_TXT,NM,NMSPC_CD,TERM_SPEC_ID,TYP,VER_NBR)
  values ('Y','Completed Course','CompletedCourse','KS-SYS','KS-KRMS-TERM-SPEC-10008','java.lang.Boolean',1)
/
Delete from KRMS_TERM_RSLVR_T where NM = 'CompletedCourse'
/
Insert into KRMS_TERM_RSLVR_T (ACTV,NM,NMSPC_CD,OUTPUT_TERM_SPEC_ID,TERM_RSLVR_ID,TYP_ID,VER_NBR)
  values ('Y','CompletedCourse','KS-SYS','KS-KRMS-TERM-SPEC-10008','KS-KRMS-TERM-RSLVR-10008',(Select TYP_ID from KRMS_TYP_T where NM = 'kuali.krms.termresolver.type.course'),0)
/

Insert into KRMS_TERM_SPEC_T (ACTV,DESC_TXT,NM,NMSPC_CD,TERM_SPEC_ID,TYP,VER_NBR)
  values ('Y','Courses With Grade','CoursesWithGrade','KS-SYS','KS-KRMS-TERM-SPEC-10009','java.lang.Boolean',1)
/
Insert into KRMS_TERM_RSLVR_T (ACTV,NM,NMSPC_CD,OUTPUT_TERM_SPEC_ID,TERM_RSLVR_ID,TYP_ID,VER_NBR)
  values ('Y','CoursesWithGrade','KS-SYS','KS-KRMS-TERM-SPEC-10009','KS-KRMS-TERM-RSLVR-10009',(Select TYP_ID from KRMS_TYP_T where NM = 'kuali.krms.termresolver.type.course'),0)
/

Insert into KRMS_TERM_SPEC_T (ACTV,DESC_TXT,NM,NMSPC_CD,TERM_SPEC_ID,TYP,VER_NBR)
  values ('Y','Course With Grade','CourseWithGrade','KS-SYS','KS-KRMS-TERM-SPEC-10010','java.lang.Boolean',1)
/
Insert into KRMS_TERM_RSLVR_T (ACTV,NM,NMSPC_CD,OUTPUT_TERM_SPEC_ID,TERM_RSLVR_ID,TYP_ID,VER_NBR)
  values ('Y','CourseWithGrade','KS-SYS','KS-KRMS-TERM-SPEC-10010','KS-KRMS-TERM-RSLVR-10010',(Select TYP_ID from KRMS_TYP_T where NM = 'kuali.krms.termresolver.type.course'),0)
/

Update KRMS_TERM_SPEC_T set NM = 'NumberOfCreditsFromCompletedCoursesOld' where NM = 'NumberOfCreditsFromCompletedCourses'
/
Insert into KRMS_TERM_SPEC_T (ACTV,DESC_TXT,NM,NMSPC_CD,TERM_SPEC_ID,TYP,VER_NBR)
  values ('Y','Credits Earned Form Courses','NumberOfCreditsFromCompletedCourses','KS-SYS','KS-KRMS-TERM-SPEC-10011','java.lang.Integer',1)
/
Delete from KRMS_TERM_RSLVR_T where NM = 'NumberOfCreditsFromCompletedCourses'
/
Insert into KRMS_TERM_RSLVR_T (ACTV,NM,NMSPC_CD,OUTPUT_TERM_SPEC_ID,TERM_RSLVR_ID,TYP_ID,VER_NBR)
  values ('Y','NumberOfCreditsFromCompletedCourses','KS-SYS','KS-KRMS-TERM-SPEC-10011','KS-KRMS-TERM-RSLVR-10011',(Select TYP_ID from KRMS_TYP_T where NM = 'kuali.krms.termresolver.type.course'),0)
/

Update KRMS_TERM_SPEC_T set NM = 'NumberOfCreditsFromOrganizationOld' where NM = 'NumberOfCreditsFromOrganization'
/
Insert into KRMS_TERM_SPEC_T (ACTV,DESC_TXT,NM,NMSPC_CD,TERM_SPEC_ID,TYP,VER_NBR)
  values ('Y','Credits Earned From Organization','NumberOfCreditsFromOrganization','KS-SYS','KS-KRMS-TERM-SPEC-10012','java.lang.Integer',1)
/
Delete from KRMS_TERM_RSLVR_T where NM = 'NumberOfCreditsFromOrganization'
/
Insert into KRMS_TERM_RSLVR_T (ACTV,NM,NMSPC_CD,OUTPUT_TERM_SPEC_ID,TERM_RSLVR_ID,TYP_ID,VER_NBR)
  values ('Y','NumberOfCreditsFromOrganization','KS-SYS','KS-KRMS-TERM-SPEC-10012','KS-KRMS-TERM-RSLVR-10012',(Select TYP_ID from KRMS_TYP_T where NM = 'kuali.krms.termresolver.type.course'),0)
/

Insert into KRMS_TERM_SPEC_T (ACTV,DESC_TXT,NM,NMSPC_CD,TERM_SPEC_ID,TYP,VER_NBR)
  values ('Y','Credits Earned','NumberOfCreditsEarned','KS-SYS','KS-KRMS-TERM-SPEC-10013','java.lang.Boolean',1)
/
Insert into KRMS_TERM_RSLVR_T (ACTV,NM,NMSPC_CD,OUTPUT_TERM_SPEC_ID,TERM_RSLVR_ID,TYP_ID,VER_NBR)
  values ('Y','NumberOfCreditsEarned','KS-SYS','KS-KRMS-TERM-SPEC-10013','KS-KRMS-TERM-RSLVR-10013',(Select TYP_ID from KRMS_TYP_T where NM = 'kuali.krms.termresolver.type.course'),0)
/

Update KRMS_TERM_SPEC_T set NM = 'EnrolledCoursesOld' where NM = 'EnrolledCourses'
/
Insert into KRMS_TERM_SPEC_T (ACTV,DESC_TXT,NM,NMSPC_CD,TERM_SPEC_ID,TYP,VER_NBR)
  values ('Y','Enrolled Courses','EnrolledCourses','KS-SYS','KS-KRMS-TERM-SPEC-10014','java.lang.Boolean',1)
/
Delete from KRMS_TERM_RSLVR_T where NM = 'EnrolledCourses'
/
Insert into KRMS_TERM_RSLVR_T (ACTV,NM,NMSPC_CD,OUTPUT_TERM_SPEC_ID,TERM_RSLVR_ID,TYP_ID,VER_NBR)
  values ('Y','EnrolledCourses','KS-SYS','KS-KRMS-TERM-SPEC-10014','KS-KRMS-TERM-RSLVR-10014',(Select TYP_ID from KRMS_TYP_T where NM = 'kuali.krms.termresolver.type.course'),0)
/

Insert into KRMS_TERM_SPEC_T (ACTV,DESC_TXT,NM,NMSPC_CD,TERM_SPEC_ID,TYP,VER_NBR)
  values ('Y','Enrolled Course','EnrolledCourse','KS-SYS','KS-KRMS-TERM-SPEC-10015','java.lang.Boolean',1)
/
Insert into KRMS_TERM_RSLVR_T (ACTV,NM,NMSPC_CD,OUTPUT_TERM_SPEC_ID,TERM_RSLVR_ID,TYP_ID,VER_NBR)
  values ('Y','EnrolledCourse','KS-SYS','KS-KRMS-TERM-SPEC-10015','KS-KRMS-TERM-RSLVR-10018',(Select TYP_ID from KRMS_TYP_T where NM = 'kuali.krms.termresolver.type.course'),0)
/

Update KRMS_TERM_SPEC_T set NM = 'FreeFormTextOld' where NM = 'FreeFormText'
/
Insert into KRMS_TERM_SPEC_T (ACTV,DESC_TXT,NM,NMSPC_CD,TERM_SPEC_ID,TYP,VER_NBR)
  values ('Y','Free Form Text','FreeFormText','KS-SYS','KS-KRMS-TERM-SPEC-10016','java.lang.Boolean',1)
/
Delete from KRMS_TERM_RSLVR_T where NM = 'FreeFormText'
/
Insert into KRMS_TERM_RSLVR_T (ACTV,NM,NMSPC_CD,OUTPUT_TERM_SPEC_ID,TERM_RSLVR_ID,TYP_ID,VER_NBR)
  values ('Y','FreeFormText','KS-SYS','KS-KRMS-TERM-SPEC-10016','KS-KRMS-TERM-RSLVR-10020',(Select TYP_ID from KRMS_TYP_T where NM = 'kuali.krms.termresolver.type.course'),0)
/

Update KRMS_TERM_SPEC_T set NM = 'GPAForCoursesOld' where NM = 'GPAForCourses'
/
Insert into KRMS_TERM_SPEC_T (ACTV,DESC_TXT,NM,NMSPC_CD,TERM_SPEC_ID,TYP,VER_NBR)
  values ('Y','GPA For Courses','GPAForCourses','KS-SYS','KS-KRMS-TERM-SPEC-10017','java.lang.Boolean',1)
/
Delete from KRMS_TERM_RSLVR_T where NM = 'GPAForCourses'
/
Insert into KRMS_TERM_RSLVR_T (ACTV,NM,NMSPC_CD,OUTPUT_TERM_SPEC_ID,TERM_RSLVR_ID,TYP_ID,VER_NBR)
  values ('Y','GPAForCourses','KS-SYS','KS-KRMS-TERM-SPEC-10017','KS-KRMS-TERM-RSLVR-10021',(Select TYP_ID from KRMS_TYP_T where NM = 'kuali.krms.termresolver.type.course'),0)
/

Insert into KRMS_TERM_SPEC_T (ACTV,DESC_TXT,NM,NMSPC_CD,TERM_SPEC_ID,TYP,VER_NBR)
  values ('Y','GPA For Duration','GPAForDuration','KS-SYS','KS-KRMS-TERM-SPEC-10018','java.lang.Boolean',1)
/
Insert into KRMS_TERM_RSLVR_T (ACTV,NM,NMSPC_CD,OUTPUT_TERM_SPEC_ID,TERM_RSLVR_ID,TYP_ID,VER_NBR)
  values ('Y','GPAForDuration','KS-SYS','KS-KRMS-TERM-SPEC-10018','KS-KRMS-TERM-RSLVR-10022',(Select TYP_ID from KRMS_TYP_T where NM = 'kuali.krms.termresolver.type.course'),0)
/

Insert into KRMS_TERM_SPEC_T (ACTV,DESC_TXT,NM,NMSPC_CD,TERM_SPEC_ID,TYP,VER_NBR)
  values ('Y','GPA','GPA','KS-SYS','KS-KRMS-TERM-SPEC-10022','java.lang.Boolean',1)
/
Insert into KRMS_TERM_RSLVR_T (ACTV,NM,NMSPC_CD,OUTPUT_TERM_SPEC_ID,TERM_RSLVR_ID,TYP_ID,VER_NBR)
  values ('Y','GPA','KS-SYS','KS-KRMS-TERM-SPEC-10022','KS-KRMS-TERM-RSLVR-10023',(Select TYP_ID from KRMS_TYP_T where NM = 'kuali.krms.termresolver.type.course'),0)
/

Insert into KRMS_TERM_SPEC_T (ACTV,DESC_TXT,NM,NMSPC_CD,TERM_SPEC_ID,TYP,VER_NBR)
  values ('Y','Instructor Permission','InstructorPermission','KS-SYS','KS-KRMS-TERM-SPEC-10023','java.lang.Boolean',1)
/
Insert into KRMS_TERM_RSLVR_T (ACTV,NM,NMSPC_CD,OUTPUT_TERM_SPEC_ID,TERM_RSLVR_ID,TYP_ID,VER_NBR)
  values ('Y','InstructorPermission','KS-SYS','KS-KRMS-TERM-SPEC-10023','KS-KRMS-TERM-RSLVR-10024',(Select TYP_ID from KRMS_TYP_T where NM = 'kuali.krms.termresolver.type.course'),0)
/

Update KRMS_TERM_SPEC_T set NM = 'NumberOfCompletedCoursesOld' where NM = 'NumberOfCompletedCourses'
/
Insert into KRMS_TERM_SPEC_T (ACTV,DESC_TXT,NM,NMSPC_CD,TERM_SPEC_ID,TYP,VER_NBR)
  values ('Y','Number Of Completed Courses','NumberOfCompletedCourses','KS-SYS','KS-KRMS-TERM-SPEC-10024','java.lang.Integer',1)
/
Delete from KRMS_TERM_RSLVR_T where NM = 'NumberOfCompletedCourses'
/
Insert into KRMS_TERM_RSLVR_T (ACTV,NM,NMSPC_CD,OUTPUT_TERM_SPEC_ID,TERM_RSLVR_ID,TYP_ID,VER_NBR)
  values ('Y','NumberOfCompletedCourses','KS-SYS','KS-KRMS-TERM-SPEC-10024','KS-KRMS-TERM-RSLVR-10025',(Select TYP_ID from KRMS_TYP_T where NM = 'kuali.krms.termresolver.type.course'),0)
/

Update KRMS_TERM_SPEC_T set NM = 'GradeTypeForCoursesOld' where NM = 'GradeTypeForCourses'
/
Insert into KRMS_TERM_SPEC_T (ACTV,DESC_TXT,NM,NMSPC_CD,TERM_SPEC_ID,TYP,VER_NBR)
  values ('Y','Number Of Courses With Grade','GradeTypeForCourses','KS-SYS','KS-KRMS-TERM-SPEC-10025','java.lang.Integer',1)
/
Delete from KRMS_TERM_RSLVR_T where NM = 'GradeTypeForCourses'
/
Insert into KRMS_TERM_RSLVR_T (ACTV,NM,NMSPC_CD,OUTPUT_TERM_SPEC_ID,TERM_RSLVR_ID,TYP_ID,VER_NBR)
  values ('Y','GradeTypeForCourses','KS-SYS','KS-KRMS-TERM-SPEC-10025','KS-KRMS-TERM-RSLVR-10026',(Select TYP_ID from KRMS_TYP_T where NM = 'kuali.krms.termresolver.type.course'),0)
/

Insert into KRMS_TERM_SPEC_T (ACTV,DESC_TXT,NM,NMSPC_CD,TERM_SPEC_ID,TYP,VER_NBR)
  values ('Y','Number Of Enrolled Courses','NumberOfEnrolledCourses','KS-SYS','KS-KRMS-TERM-SPEC-10026','java.lang.Integer',1)
/
Insert into KRMS_TERM_RSLVR_T (ACTV,NM,NMSPC_CD,OUTPUT_TERM_SPEC_ID,TERM_RSLVR_ID,TYP_ID,VER_NBR)
  values ('Y','NumberOfEnrolledCourses','KS-SYS','KS-KRMS-TERM-SPEC-10026','KS-KRMS-TERM-RSLVR-10027',(Select TYP_ID from KRMS_TYP_T where NM = 'kuali.krms.termresolver.type.course'),0)
/

Insert into KRMS_TERM_SPEC_T (ACTV,DESC_TXT,NM,NMSPC_CD,TERM_SPEC_ID,TYP,VER_NBR)
  values ('Y','Number Of Enrollments','NumberOfEnrollments','KS-SYS','KS-KRMS-TERM-SPEC-10027','java.lang.Integer',1)
/
Insert into KRMS_TERM_RSLVR_T (ACTV,NM,NMSPC_CD,OUTPUT_TERM_SPEC_ID,TERM_RSLVR_ID,TYP_ID,VER_NBR)
  values ('Y','NumberOfEnrollments','KS-SYS','KS-KRMS-TERM-SPEC-10027','KS-KRMS-TERM-RSLVR-10028',(Select TYP_ID from KRMS_TYP_T where NM = 'kuali.krms.termresolver.type.course'),0)
/

Update KRMS_TERM_SPEC_T set NM = 'PopulationOld' where NM = 'Population'
/
Insert into KRMS_TERM_SPEC_T (ACTV,DESC_TXT,NM,NMSPC_CD,TERM_SPEC_ID,TYP,VER_NBR)
  values ('Y','Population','Population','KS-SYS','KS-KRMS-TERM-SPEC-10028','java.lang.Integer',1)
/
Delete from KRMS_TERM_RSLVR_T where NM = 'Population'
/
Insert into KRMS_TERM_RSLVR_T (ACTV,NM,NMSPC_CD,OUTPUT_TERM_SPEC_ID,TERM_RSLVR_ID,TYP_ID,VER_NBR)
  values ('Y','Population','KS-SYS','KS-KRMS-TERM-SPEC-10028','KS-KRMS-TERM-RSLVR-10029',(Select TYP_ID from KRMS_TYP_T where NM = 'kuali.krms.termresolver.type.course'),0)
/

Update KRMS_TERM_SPEC_T set NM = 'AdmittedToProgramLimitCoursesInOrgForDurationOld' where NM = 'AdmittedToProgramLimitCoursesInOrgForDuration'
/
Insert into KRMS_TERM_SPEC_T (ACTV,DESC_TXT,NM,NMSPC_CD,TERM_SPEC_ID,TYP,VER_NBR)
  values ('Y','Program Courses Organization Duration','AdmittedToProgramLimitCoursesInOrgForDuration','KS-SYS','KS-KRMS-TERM-SPEC-10029','java.lang.Integer',1)
/
Delete from KRMS_TERM_RSLVR_T where NM = 'AdmittedToProgramLimitCoursesInOrgForDuration'
/
Insert into KRMS_TERM_RSLVR_T (ACTV,NM,NMSPC_CD,OUTPUT_TERM_SPEC_ID,TERM_RSLVR_ID,TYP_ID,VER_NBR)
  values ('Y','AdmittedToProgramLimitCoursesInOrgForDuration','KS-SYS','KS-KRMS-TERM-SPEC-10029','KS-KRMS-TERM-RSLVR-10030',(Select TYP_ID from KRMS_TYP_T where NM = 'kuali.krms.termresolver.type.course'),0)
/

Insert into KRMS_TERM_SPEC_T (ACTV,DESC_TXT,NM,NMSPC_CD,TERM_SPEC_ID,TYP,VER_NBR)
  values ('Y','Score','Score','KS-SYS','KS-KRMS-TERM-SPEC-10030','java.lang.Integer',1)
/
Insert into KRMS_TERM_RSLVR_T (ACTV,NM,NMSPC_CD,OUTPUT_TERM_SPEC_ID,TERM_RSLVR_ID,TYP_ID,VER_NBR)
  values ('Y','Score','KS-SYS','KS-KRMS-TERM-SPEC-10030','KS-KRMS-TERM-RSLVR-10031',(Select TYP_ID from KRMS_TYP_T where NM = 'kuali.krms.termresolver.type.course'),0)
/

Update KRMS_TERM_T set TERM_SPEC_ID = 'KS-KRMS-TERM-SPEC-10008', DESC_TXT = 'Completed Course' where TERM_SPEC_ID = '10000'
/
Delete from KRMS_TERM_SPEC_T where TERM_SPEC_ID = '10000'
/

Update KRMS_TERM_T set TERM_SPEC_ID = 'KS-KRMS-TERM-SPEC-10007', DESC_TXT = 'Completed Courses' where TERM_SPEC_ID = '10001'
/
Delete from KRMS_TERM_SPEC_T where TERM_SPEC_ID = '10001'
/

Update KRMS_TERM_T set TERM_SPEC_ID = 'KS-KRMS-TERM-SPEC-10024', DESC_TXT = 'Number Of Completed Courses Term' where TERM_SPEC_ID = '10002'
/
Delete from KRMS_TERM_SPEC_T where TERM_SPEC_ID = '10002'
/

Update KRMS_TERM_T set TERM_SPEC_ID = 'KS-KRMS-TERM-SPEC-10011', DESC_TXT = 'Credits Earned Form Courses Term' where TERM_SPEC_ID = '10003'
/
Delete from KRMS_TERM_SPEC_T where TERM_SPEC_ID = '10003'
/

Update KRMS_TERM_T set TERM_SPEC_ID = 'KS-KRMS-TERM-SPEC-10014', DESC_TXT = 'Enrolled Courses Term' where TERM_SPEC_ID = '10004'
/
Delete from KRMS_TERM_SPEC_T where TERM_SPEC_ID = '10004'
/

Update KRMS_TERM_T set TERM_SPEC_ID = 'KS-KRMS-TERM-SPEC-10017', DESC_TXT = 'GPA For Courses Term' where TERM_SPEC_ID = '10005'
/
Delete from KRMS_TERM_SPEC_T where TERM_SPEC_ID = '10005'
/

Update KRMS_TERM_T set TERM_SPEC_ID = 'KS-KRMS-TERM-SPEC-10025', DESC_TXT = 'Number Of Courses With Grade Term' where TERM_SPEC_ID = '10006'
/
Delete from KRMS_TERM_SPEC_T where TERM_SPEC_ID = '10006'
/

Update KRMS_TERM_T set TERM_SPEC_ID = 'KS-KRMS-TERM-SPEC-10013', DESC_TXT = 'Credits Earned Term' where TERM_SPEC_ID = '10007'
/
Delete from KRMS_TERM_RSLVR_T where OUTPUT_TERM_SPEC_ID = '10007'
/
Delete from KRMS_TERM_SPEC_T where TERM_SPEC_ID = '10007'
/

Update KRMS_TERM_T set TERM_SPEC_ID = 'KS-KRMS-TERM-SPEC-10012', DESC_TXT = 'Credits Earned From Organization Term' where TERM_SPEC_ID = '10008'
/
Delete from KRMS_TERM_SPEC_T where TERM_SPEC_ID = '10008'
/

Update KRMS_TERM_T set TERM_SPEC_ID = 'KS-KRMS-TERM-SPEC-10000', DESC_TXT = 'Admin Org Permission Term' where TERM_SPEC_ID = '10009'
/
Delete from KRMS_TERM_RSLVR_T where OUTPUT_TERM_SPEC_ID = '10009'
/
Delete from KRMS_TERM_SPEC_T where TERM_SPEC_ID = '10009'
/

Update KRMS_TERM_T set TERM_SPEC_ID = 'KS-KRMS-TERM-SPEC-10002', DESC_TXT = 'Admitted Program Term' where TERM_SPEC_ID = '10011'
/
Delete from KRMS_TERM_RSLVR_T where OUTPUT_TERM_SPEC_ID = '10011'
/
Delete from KRMS_TERM_SPEC_T where TERM_SPEC_ID = '10011'
/

Update KRMS_TERM_T set TERM_SPEC_ID = 'KS-KRMS-TERM-SPEC-10016', DESC_TXT = 'Free Form Text Term' where TERM_SPEC_ID = '10013' and DESC_TXT is null
/
Update KRMS_TERM_T set TERM_SPEC_ID = 'KS-KRMS-TERM-SPEC-10016' where TERM_SPEC_ID = '10013'
/
Delete from KRMS_TERM_RSLVR_T where OUTPUT_TERM_SPEC_ID = '10013'
/
Delete from KRMS_TERM_SPEC_T where TERM_SPEC_ID = '10013'
/

Update KRMS_TERM_T set TERM_SPEC_ID = 'KS-KRMS-TERM-SPEC-10001', DESC_TXT = 'Admitted Program At Course Campus Term' where TERM_SPEC_ID = '10016'
/
Delete from KRMS_TERM_SPEC_T where TERM_SPEC_ID = '10016'
/

Update KRMS_TERM_T set TERM_SPEC_ID = 'KS-KRMS-TERM-SPEC-10028', DESC_TXT = 'Population Term' where TERM_SPEC_ID = '10018'
/
Delete from KRMS_TERM_SPEC_T where TERM_SPEC_ID = '10018'
/

Delete from KRMS_TERM_RSLVR_T where OUTPUT_TERM_SPEC_ID = '10014'
/
Delete from KRMS_TERM_SPEC_T where TERM_SPEC_ID = '10014'
/
Delete from KRMS_TERM_RSLVR_T where OUTPUT_TERM_SPEC_ID = '10010'
/
Delete from KRMS_TERM_SPEC_T where TERM_SPEC_ID = '10010'
/

Delete from KRMS_TERM_SPEC_T where TERM_SPEC_ID = '10012'
/
Delete from KRMS_TERM_SPEC_T where TERM_SPEC_ID = '10015'
/