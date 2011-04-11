
-- Course Dependency Filter Enumerations

insert into KSEM_ENUM_T (ENUM_KEY, DESCR, EFF_DT, EXPIR_DT, NAME, OBJ_ID, VER_NBR) values ('kuali.dependency.course.types', 'Course Dependency Types', to_date('2000-01-01', 'yyyy-mm-dd'), null, 'Course Dependency Types', SYS_GUID(), 1)
/
insert into KSEM_ENUM_T (ENUM_KEY, DESCR, EFF_DT, EXPIR_DT, NAME, OBJ_ID, VER_NBR) values ('kuali.dependency.program.types', 'Program Dependency Types', to_date('2000-01-01', 'yyyy-mm-dd'), null, 'Program Dependency Types', SYS_GUID(), 2)
/
insert into KSEM_ENUM_T (ENUM_KEY, DESCR, EFF_DT, EXPIR_DT, NAME, OBJ_ID, VER_NBR) values ('kuali.dependency.courseSet.types', 'Course Set Dependency Types', to_date('2000-01-01', 'yyyy-mm-dd'), null, 'Course Set Dependency Types', SYS_GUID(), 4)
/


insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('850a28c9-ebfb-4199-8769-6693404ce4af', 'cross-listed', 'crossListed', to_date('2000-01-01', 'yyyy-mm-dd'), null, 1, 'Cross-listed Courses', 'kuali.dependency.course.types', SYS_GUID(), 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b7d8c960-fb52-4db9-8a02-99c6ad802d74', 'Jointly-Offered', 'joint', to_date('2000-01-01', 'yyyy-mm-dd'), null, 0, 'Jointly-Offered Courses', 'kuali.dependency.course.types', SYS_GUID(), 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5fcd4c7c-e638-4028-8287-68d7bd1f4988', 'Student Elig & Prereq', 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', to_date('2000-01-01', 'yyyy-mm-dd'), null, 3, 'Student Eligibility and Preqrequiste', 'kuali.dependency.course.types', SYS_GUID(), 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('fa86a628-9601-4308-8a28-71d406bd19e9', 'Antirequisite', 'kuali.statement.type.course.academicReadiness.antireq', to_date('2000-01-01', 'yyyy-mm-dd'), null, 4, 'Antirequisite', 'kuali.dependency.course.types', SYS_GUID(), 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('09a9e57b-1078-4bfe-88fd-9bb7fc189b8c', 'Corequisite', 'kuali.statement.type.course.academicReadiness.coreq', to_date('2000-01-01', 'yyyy-mm-dd'), null, 0, 'Corequisite', 'kuali.dependency.course.types', SYS_GUID(), 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('777dc110-1707-41ed-9014-13e6e769d815', 'Courses that Restrict Credit', 'kuali.statement.type.course.credit.restriction', to_date('2000-01-01', 'yyyy-mm-dd'), null, 0, 'Courses that Restrict Credit', 'kuali.dependency.course.types', SYS_GUID(), 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f32403a1-cc9d-4004-9a95-f5da290b55e2', 'cluSet', 'cluSet', to_date('2000-01-01', 'yyyy-mm-dd'), null, 2, 'All Course Sets', 'kuali.dependency.courseSet.types', SYS_GUID(), 4)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8804149a-038a-47f7-932d-bcf392d3d263', 'Completion Requirements', 'kuali.statement.type.program.completion', to_date('2000-01-01', 'yyyy-mm-dd'), null, 3, 'Completion Requirements', 'kuali.dependency.program.types', SYS_GUID(), 2)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c8f2e35a-9bb2-4803-903a-ab877133840e', 'Entrance Requirements', 'kuali.statement.type.program.entrance', to_date('2000-01-01', 'yyyy-mm-dd'), null, 1, 'Entrance Requirements', 'kuali.dependency.program.types', SYS_GUID(), 2)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2805b962-ad17-401d-bd53-b1fba35a3ef1', 'Satisfactory Progress Requirements', 'kuali.statement.type.program.satisfactoryProgress', to_date('2000-01-01', 'yyyy-mm-dd'), null, 2, 'Satisfactory Progress Requirements', 'kuali.dependency.program.types', SYS_GUID(), 2)
/
