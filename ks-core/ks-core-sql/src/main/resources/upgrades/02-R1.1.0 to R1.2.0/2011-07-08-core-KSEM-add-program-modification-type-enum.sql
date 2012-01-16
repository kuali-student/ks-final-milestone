-- Add a new enumeration
insert into KSEM_ENUM_T (ENUM_KEY, DESCR, EFF_DT, EXPIR_DT, NAME, OBJ_ID, VER_NBR) values ('kuali.lu.programModificationType', 'Program Modification Type Enumeration', to_date('2000-01-01', 'yyyy-mm-dd'), null, 'Program Modification Type Enumeration', '4E6748C8-A90F-68F4-1B2B-A6F81C3AA7E4', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3d95e653-692a-42a9-a6f8-32b59045c78a', 'Rename Major/Specialization', 'RenameMajorSpecialization', to_date('2000-01-01', 'yyyy-mm-dd'), null, 1, 'Rename Major/Specialization', 'kuali.lu.programModificationType', 'E165B344-25A1-4096-38D3-5DB0AF2CA046', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('52a00c2c-dc04-4cf2-b450-f77db5495b7b', 'Add Specialization', 'AddSpecialization', to_date('2000-01-01', 'yyyy-mm-dd'), null, 2, 'Add Specialization', 'kuali.lu.programModificationType', 'A4AD2695-E627-E76E-ED1A-4A67A4271CBA', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a33495e5-f14a-48a2-8f1c-8ed356c2746f', 'Change Program Requirements', 'ChangeProgramRequirements', to_date('2000-01-01', 'yyyy-mm-dd'), null, 3, 'Change Program Requirements', 'kuali.lu.programModificationType', 'DAE31188-1DFC-5AD5-4B37-AF8EFA2ACEA1', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1f919bdd-5391-4452-8b4d-862d8cea322d', 'Other', 'Other', to_date('2000-01-01', 'yyyy-mm-dd'), null, 4, 'Other', 'kuali.lu.programModificationType', '82C3EE84-3D57-242A-5B27-365E222A928F', 1)
/