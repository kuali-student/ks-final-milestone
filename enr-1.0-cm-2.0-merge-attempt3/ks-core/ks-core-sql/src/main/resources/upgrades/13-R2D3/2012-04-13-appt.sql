---------------------------------------------
 --- APPT. RULE TYPES
---------------------------------------------


delete from KSEM_ENUM_VAL_T where obj_id = '04112012221'
/
delete from KSEM_ENUM_VAL_T where obj_id = '04112012222'
/
delete from KSEM_ENUM_VAL_T where obj_id = '04112012223'
/
delete from KSEM_ENUM_VAL_T where obj_id = '04112012224'
/
delete from ksem_enum_t where obj_id = 'enroll'
/

insert into KSEM_ENUM_T (ENUM_KEY, DESCR, EFF_DT, EXPIR_DT, NAME, OBJ_ID, VER_NBR)
values ('kuali.enum.type.slotrule', 'Slot Code', sysdate, null, 'Slot Codes', 'enroll', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR)
values ('04112012221', 'Undergrad Standard', '05', sysdate, null, 1, '2,4,6;480;960;kuali.atp.duration.Minutes;15;kuali.atp.duration.Minutes;0', 'kuali.enum.type.slotrule', '04112012221', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR)
values ('04112012222', 'Undergrad Special', '06', sysdate, null, 2, '3,5;540;1020;kuali.atp.duration.Minutes;30;kuali.atp.duration.Minutes;0', 'kuali.enum.type.slotrule', '04112012222', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR)
values ('04112012223', 'AdvancedRegistration', '07', sysdate, null, 1, '2,3,6;600;1080;kuali.atp.duration.Minutes;45;kuali.atp.duration.Minutes;0', 'kuali.enum.type.slotrule', '04112012223', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR)
values ('04112012224', 'Graduate/Profession', '08', sysdate, null, 1, '2,3,4,5;480;1020;kuali.atp.duration.Minutes;20;kuali.atp.duration.Minutes;0', 'kuali.enum.type.slotrule', '04112012224', 1)
/
