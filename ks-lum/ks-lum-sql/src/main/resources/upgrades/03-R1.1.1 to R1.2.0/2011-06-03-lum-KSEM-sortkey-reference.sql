
-- Course Dependency Filter Enumerations
update KSEM_ENUM_VAL_T set SORT_KEY=1 where ID='b7d8c960-fb52-4db9-8a02-99c6ad802d74'
/
update KSEM_ENUM_VAL_T set SORT_KEY=2 where ID='850a28c9-ebfb-4199-8769-6693404ce4af'
/
update KSEM_ENUM_VAL_T set SORT_KEY=3 where ID='5fcd4c7c-e638-4028-8287-68d7bd1f4988'
/
update KSEM_ENUM_VAL_T set SORT_KEY=4 where ID='09a9e57b-1078-4bfe-88fd-9bb7fc189b8c'
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b1ad331a-c607-40de-ad68-6e66a9aaff0c', 'Recommended Preparation', 'kuali.statement.type.course.recommendedPreparation', to_date('2000-01-01', 'yyyy-mm-dd'), null, 5, 'Recommended Preparation', 'kuali.dependency.course.types', SYS_GUID(), 1)
/
update KSEM_ENUM_VAL_T set SORT_KEY=6 where ID='fa86a628-9601-4308-8a28-71d406bd19e9'
/
update KSEM_ENUM_VAL_T set SORT_KEY=7 where ID='777dc110-1707-41ed-9014-13e6e769d815'
/
