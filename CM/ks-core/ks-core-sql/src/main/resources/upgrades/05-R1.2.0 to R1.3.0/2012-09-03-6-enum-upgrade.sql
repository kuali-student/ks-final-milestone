
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
UPDATE KSEM_ENUM_VAL_T SET abbrev_val = 'Course that Restricts Credits' , val = 'Course that Restricts Credits' WHERE id ='777dc110-1707-41ed-9014-13e6e769d815'
/
--KSLAB-2615 part 1
update ksem_enum_val_t set VAL = 'Student Eligibility and Prerequisite' where cd = 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq' and enum_key = 'kuali.dependency.course.types'
/

--KSLAB-2615 part 2
update ksem_enum_val_t set VAL = 'Cross-Listed Courses' where cd = 'crossListed' and enum_key = 'kuali.dependency.course.types'
/

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


delete from KSEM_ENUM_VAL_T  where ENUM_KEY='kuali.enum.type.cip2000'
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e4e05398-b9f3-4361-93a1-08a808efc6ea', '----', '----', null, null, 1, 'Central/Middle and Eastern European Studies.', 'kuali.enum.type.cip2000',  '456E1982767C4F11A9908039FA2076FD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('69a936d4-3520-423a-a496-5d554431e05b', '----', '----', null, null, 2, 'Business Administration and Management, General.', 'kuali.enum.type.cip2000',  '3B134A9B5574401AA40A7FC0DB694F6E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d64ee415-5a13-4d7f-8574-c4bea1e989e9', '------', '------', null, null, 3, 'Sociology.', 'kuali.enum.type.cip2000', 'F8796150D39743159EA4EFCB09B7AFE5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a9c3dc7d-9c66-47c7-9226-74ed04ee01bb', '------', '------', null, null, 4, 'Corrections and Criminal Justice.', 'kuali.enum.type.cip2000',  '503EA6F08E834998AC90DFAC9AB576DD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0abde861-c7b5-4b76-9241-416b7dfa6133', '------', '------', null, null, 5, 'Cosmetology and Related Personal Grooming Services.', 'kuali.enum.type.cip2000',  'DFB59149695047948104385CCBA44CB4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a6fe1a89-183a-4b43-9625-067ae4931f4e', '------', '------', null, null, 6, 'City/Urban, Community and Regional Planning.', 'kuali.enum.type.cip2000',  '8F5B70B82CAE4C14B21EC02E93F29289', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('41a154cb-dec2-4629-bfff-ce65a66d96c9', '01.', '01.', null, null, 7, 'AGRICULTURE, AGRICULTURE OPERATIONS, AND RELATED SCIENCES.', 'kuali.enum.type.cip2000',  'A4F090890D5042568C235EF4CCB68C26', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1ae6396e-98b2-43b4-9e41-559ac71aab94', '01.00', '01.00', null, null, 8, 'Agriculture, General.', 'kuali.enum.type.cip2000',  '1ACF9A02E06B497697C8960A0FBE3203', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4fcad01c-b5d5-4ded-a9c4-295aa77aef5b', '01.0000', '01.0000', null, null, 9, 'Agriculture, General.', 'kuali.enum.type.cip2000',  '429B0EC32E7648C8A4604A0FF644AA03', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1b3776bd-fa97-4a19-a74b-15497737d64b', '01.01', '01.01', null, null, 10, 'Agricultural Business and Management.', 'kuali.enum.type.cip2000',  '3B066876DFC748ECA8F62967197B4DFD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ca156457-65f4-4a2d-9805-c46428252a35', '01.0101', '01.0101', null, null, 11, 'Agricultural Business and Management, General.', 'kuali.enum.type.cip2000',  'C08097C72334418291DD502680E169DA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('decd77e9-af03-40d7-8d46-1c00021a89c8', '01.0102', '01.0102', null, null, 12, 'Agribusiness/Agricultural Business Operations.', 'kuali.enum.type.cip2000',  'C25FA7837D4F42B7A983459A9DD43197', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('79e4f752-87e8-4f14-a7a7-1350f9de35c0', '01.0103', '01.0103', null, null, 13, 'Agricultural Economics.', 'kuali.enum.type.cip2000',  'C99C0830CB564763A9A76B0E0D2B3A7A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('17f4063d-5957-42db-8dac-5d13ed33bcfb', '01.0104', '01.0104', null, null, 14, 'Farm/Farm and Ranch Management.', 'kuali.enum.type.cip2000',  'DED079CEC16D44E3B364F4F7511C18E0', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a509c767-2368-4e98-bfaf-1faaa7912584', '01.0105', '01.0105', null, null, 15, 'Agricultural/Farm Supplies Retailing and Wholesaling.',  'kuali.enum.type.cip2000', '0B6604344C4F49E68B0435F09833856C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1650f9f3-e971-436d-bea2-c9dae7d7925d', '01.0106', '01.0106', null, null, 16, 'Agricultural Business Technology.', 'kuali.enum.type.cip2000',  '942A5033493944C8B025AE84FF83FDAC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('312dff40-5403-4c76-9d71-61eca97e59d7', '01.0199', '01.0199', null, null, 17, 'Agricultural Business and Management, Other.', 'kuali.enum.type.cip2000',  'FFC3CDD4C17643ADA9A02E9C3A429D17', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c21ae902-6c59-4e43-b2a0-731eb84362b1', '01.02', '01.02', null, null, 18, 'Agricultural Mechanization.', 'kuali.enum.type.cip2000',  'DD4A395ACD10432EB7852BA7B83CFD21', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('cb453043-e8a4-4372-be09-690acac9eb50', '01.0201', '01.0201', null, null, 19, 'Agricultural Mechanization, General.', 'kuali.enum.type.cip2000',  '41F4F32CDB8D44A99B135A9840898558', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('11b00ec7-5655-4078-97f3-8d2cbc340ac3', '01.0204', '01.0204', null, null, 20, 'Agricultural Power Machinery Operation.', 'kuali.enum.type.cip2000',  'FE235083A2AC43C6BC64ACCA79570878', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('26cd6c1a-f0d4-40c0-a331-1ccf3da36952', '01.0205', '01.0205', null, null, 21, 'Agricultural Mechanics and Equipment/Machine Technology.',  'kuali.enum.type.cip2000', 'ED594D1C4A4F4F61A4ECF1D5EDE2EFCB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d8da0184-2f7c-4930-9a7f-b50f6e2cceb1', '01.0299', '01.0299', null, null, 22, 'Agricultural Mechanization, Other.', 'kuali.enum.type.cip2000',  '56467E656E894517AB83CBCAF397E8F7', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('80bcae58-0f9a-48f4-88d8-ff3fbcef44f5', '01.03', '01.03', null, null, 23, 'Agricultural Production Operations.', 'kuali.enum.type.cip2000',  'EC183989F3FD4C9E8FB0C48B1224BFCE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('fd106bfd-43eb-4f60-8962-e4575b6dad39', '01.0301', '01.0301', null, null, 24, 'Agricultural Production Operations, General.', 'kuali.enum.type.cip2000',  'E8F8E5AB25B44D8AA3563B57500AB898', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('fb5d50f4-ebeb-4fd7-a142-257d8ad5f7d9', '01.0302', '01.0302', null, null, 25, 'Animal/Livestock Husbandry and Production.', 'kuali.enum.type.cip2000',  '91FD0BCBEF8943E6B5A0F688DB989840', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4705d4a3-90c7-4d00-802c-4de829a3e4e3', '01.0303', '01.0303', null, null, 26, 'Aquaculture.', 'kuali.enum.type.cip2000', 'C651A8FE60A14100B51147D255837532', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('80ff68ee-43d8-48ad-8c7e-1449a881b401', '01.0304', '01.0304', null, null, 27, 'Crop Production.', 'kuali.enum.type.cip2000',  'ACBDD467FA474BB2AA778BB4FC4380F6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('83b9c3bf-05ad-45ce-a0d3-3253fca38497', '01.0306', '01.0306', null, null, 28, 'Dairy Husbandry and Production.', 'kuali.enum.type.cip2000',  '4A8831B2B8484F988A2A4A527448A477', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ccd03eb7-3057-4f4d-affa-ca0a591544cd', '01.0307', '01.0307', null, null, 29, 'Horse Husbandry/Equine Science and Management.', 'kuali.enum.type.cip2000',  'FABB9C2AC60F4B2CB327B533F13D8E85', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('131817f1-b262-490e-82dd-35021cbb2cbb', '01.0399', '01.0399', null, null, 30, 'Agricultural Production Operations, Other.', 'kuali.enum.type.cip2000',  'F99E11B2866A4F5986C59B82BA61D03F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a03c7a65-64c2-44c1-8c00-58cb1a13d322', '01.04', '01.04', null, null, 31, 'Agricultural and Food Products Processing.', 'kuali.enum.type.cip2000',  'D71C82A3561946FAA37DF8FD24FAF6A7', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a5df82a3-ae0e-4625-910c-5c094096381e', '01.0401', '01.0401', null, null, 32, 'Agricultural and Food Products Processing.', 'kuali.enum.type.cip2000',  '83F12FCF2D9041E193FD6DE96BA01049', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c1a82dc3-3e2f-4ca4-9a87-aaa4d0d4a191', '01.05', '01.05', null, null, 33, 'Agricultural and Domestic Animal Services.', 'kuali.enum.type.cip2000',  '530FB8C6228F4AA28C3A547B6FF69C2F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0dd13169-c1a8-433c-a4d0-9789b8bb4d9e', '01.0501', '01.0501', null, null, 34, 'Agricultural/Farm Supplies Retailing and Wholesaling.',  'kuali.enum.type.cip2000', 'A869874CE4DA4682B74A989854BE298F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e54b8224-7f87-40c4-98c0-864d8da20589', '01.0504', '01.0504', null, null, 35, 'Dog/Pet/Animal Grooming.', 'kuali.enum.type.cip2000',  '55D88569A1994C4DB09A4C80C8E22273', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('16cf7483-571b-438e-924e-fabb0e9d803c', '01.0505', '01.0505', null, null, 36, 'Animal Training.', 'kuali.enum.type.cip2000',  '8A713A4637964BB28FA010871FCCC808', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('638156ce-b907-4132-b734-7e1672b1045d', '01.0507', '01.0507', null, null, 37, 'Equestrian/Equine Studies.', 'kuali.enum.type.cip2000',  '483489B01A8F467EA6C70EE934EFD652', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8441f309-4791-4c90-b848-5e45d8fdd455', '01.0508', '01.0508', null, null, 38, 'Taxidermy/Taxidermist.', 'kuali.enum.type.cip2000',  'FB1BB4519B574D7FA46486A89F30B9AB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('53c80929-d625-4fed-9313-ed7fea0b2b19', '01.0599', '01.0599', null, null, 39, 'Agricultural and Domestic Animal Services, Other.', 'kuali.enum.type.cip2000',  '4CF99B64A7024261AF99B73312D08616', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('91575c25-26e4-49e9-bbae-d905fb823a5c', '01.06', '01.06', null, null, 40, 'Applied Horticulture and Horticultural Business Services.',  'kuali.enum.type.cip2000', '58D0EC88EF2841F9AF442FB9A192D9E1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3d9e769b-62ee-48a4-8b40-7e2e8ecb5bbf', '01.0601', '01.0601', null, null, 41, 'Applied Horticulture/Horticulture Operations, General.',  'kuali.enum.type.cip2000', '31E41B24877F460681F5AF9A389D4D3B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('891c2f82-fbf2-4b29-9b06-9b502caa7e97', '01.0603', '01.0603', null, null, 42, 'Ornamental Horticulture.', 'kuali.enum.type.cip2000',  '72A09F7F78C24265943D31ED88EE7009', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0ce533c9-f7e3-4168-b211-b8b511cceee1', '01.0604', '01.0604', null, null, 43, 'Greenhouse Operations and Management.', 'kuali.enum.type.cip2000',  '5407B82CB8664B51BF0DA14B53584F70', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('07a1ee65-3d16-46e9-8827-b36b86c8f2b3', '01.0605', '01.0605', null, null, 44, 'Landscaping and Groundskeeping.', 'kuali.enum.type.cip2000',  '416B898E4DBB48EF8B983E8E287BC202', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d2dd80d9-9da8-4935-b663-ae5969765c3d', '01.0606', '01.0606', null, null, 45, 'Plant Nursery Operations and Management.', 'kuali.enum.type.cip2000',  '77BABABB274240F4994CC43E0F87B237', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b46a1095-bea5-418c-a3ce-5371cea7a568', '01.0607', '01.0607', null, null, 46, 'Turf and Turfgrass Management.', 'kuali.enum.type.cip2000',  '3C892A6D0D3E4CD1B6D53617D8203AFD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c9f3cfee-2063-4cea-9d5c-3c9a3de815bc', '01.0608', '01.0608', null, null, 47, 'Floriculture/Floristry Operations and Management.', 'kuali.enum.type.cip2000',  'DD7230A8440F4CCEBC56AECC1357131D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bd6b3cbd-6b72-475b-b40d-aaf8a7efd06e', '01.0699', '01.0699', null, null, 48, 'Applied Horticulture/Horticultural Business Services, Other.',  'kuali.enum.type.cip2000', '75F6B69BB7B5452C80EE90559A022533', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('452d0110-0620-4074-86fe-2169f909c973', '01.07', '01.07', null, null, 49, 'International Agriculture.', 'kuali.enum.type.cip2000',  '204E1F84490B41B2BD9A3EA9B43E4AA4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6b5d9dfa-d215-448e-b5f5-cb536c187a38', '01.0701', '01.0701', null, null, 50, 'International Agriculture.', 'kuali.enum.type.cip2000',  '8CBD620E1BAF40F681F717A0D15AAA3C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1724173b-e4d2-4596-a32d-a7e95e2a1e22', '01.08', '01.08', null, null, 51, 'Agricultural Public Services.', 'kuali.enum.type.cip2000',  '3435C6091999434F9415B579BB4533DA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a3309237-9431-488c-9676-b95ceb33be8a', '01.0801', '01.0801', null, null, 52, 'Agricultural and Extension Education Services.', 'kuali.enum.type.cip2000',  '2B7BB040AF1C41B2B3BE60E30B1FB02C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('60bc6d15-ada0-4afb-8fc4-e26d90d310be', '01.0802', '01.0802', null, null, 53, 'Agricultural Communication/Journalism.', 'kuali.enum.type.cip2000',  'B2A13EB45F48437585F0928BCA9B4BE5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7488d121-a95b-4719-9bec-b8a84d3f9430', '01.0899', '01.0899', null, null, 54, 'Agricultural Public Services, Other.', 'kuali.enum.type.cip2000',  'B655AD8B609B4080B3D1B65A550FB3A8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a4d742ec-d74a-4789-8a55-ffc90f831a37', '01.09', '01.09', null, null, 55, 'Animal Sciences.', 'kuali.enum.type.cip2000', '613A28F1938B490EAD063A1308473B59', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('37a7f204-4000-4af2-9c05-38e728e20e4a', '01.0901', '01.0901', null, null, 56, 'Animal Sciences, General.', 'kuali.enum.type.cip2000',  '7909C10DC19F4949AB82D91EEAB2539E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8638c1a4-f718-4bf3-8232-64da36fad3e9', '01.0902', '01.0902', null, null, 57, 'Agricultural Animal Breeding.', 'kuali.enum.type.cip2000',  'B524512CDF9042CC886891D1FF91E567', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('79b3f4b5-9857-4128-9ee0-9cbfbf4c73bb', '01.0903', '01.0903', null, null, 58, 'Animal Health.', 'kuali.enum.type.cip2000', 'C19B8355E1314601A682D704382FC51F',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e7005efd-07e7-4ad1-a293-a6b091a14d55', '01.0904', '01.0904', null, null, 59, 'Animal Nutrition.', 'kuali.enum.type.cip2000',  '1E49AD57BD2445EABDFDF89FC845FB11', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('515bbe1d-4f16-4da6-bd03-db846e96b24c', '01.0905', '01.0905', null, null, 60, 'Dairy Science.', 'kuali.enum.type.cip2000', '031F117E2AF94482A80424D4D45E5C43',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a02aeaf2-5fff-41bb-9e5a-9aad02881209', '01.0906', '01.0906', null, null, 61, 'Livestock Management.', 'kuali.enum.type.cip2000',  '9826E26905DA462697E7E3266FDBD1B7', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0a38d105-1fc6-4ade-b31a-a334412d1812', '01.0907', '01.0907', null, null, 62, 'Poultry Science.', 'kuali.enum.type.cip2000',  '33E0B11583AE4706B52551617A113912', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('376553af-26d2-420b-ae94-a0b8f7f47876', '01.0999', '01.0999', null, null, 63, 'Animal Sciences, Other.', 'kuali.enum.type.cip2000',  '7816BC22F3264C8DAEFCF9482382CDAF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2cf9159a-aa85-40a4-8a97-e8f59e1247ca', '01.10', '01.10', null, null, 64, 'Food Science and Technology.', 'kuali.enum.type.cip2000',  '6824C86D790F4350A2CC41CDDA8CA32C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('929050ce-18bb-4037-bf39-b8b314c2c471', '01.1001', '01.1001', null, null, 65, 'Food Science.', 'kuali.enum.type.cip2000', 'E77BC4FEB4D74B7BB4D8E87A546394A9',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('eb26120e-5cb4-43d5-adb0-c9e7cd1e20b6', '01.1002', '01.1002', null, null, 66, 'Food Technology and Processing.', 'kuali.enum.type.cip2000',  'ABF131942D0C4E4AAE6BBB2CA2C8CAE7', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e5f754ce-7778-4136-995d-136d1ae21ffd', '01.1099', '01.1099', null, null, 67, 'Food Science and Technology, Other.', 'kuali.enum.type.cip2000',  'EF3AAFD46B024DE9B32A8CC0E8C7CA24', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('05e66d8b-09fc-4a24-a55d-eb6432394089', '01.11', '01.11', null, null, 68, 'Plant Sciences.', 'kuali.enum.type.cip2000', 'A5C259182CB142139FE970914CEDE873', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('743e8479-45fa-4207-a9b3-7c6ef8cdadfd', '01.11', '01.11', null, null, 69, '01.', 'kuali.enum.type.cip2000', '8EA1E3817F464F229166509F160FC3E3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9e3f126a-e8cf-4a83-8f46-33301b69bb05', '01.1101', '01.1101', null, null, 70, 'Plant Sciences, General.', 'kuali.enum.type.cip2000',  '60B858D6DC7644EE9BEB80A66A797163', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f0d4be57-9f9e-4bd0-b985-b65a064d4a05', '01.1102', '01.1102', null, null, 71, 'Agronomy and Crop Science.', 'kuali.enum.type.cip2000',  '89A39CDCAF9F4EC890908857916C4B49', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('447a0fe6-af32-4176-8402-b85c5fb28908', '01.1103', '01.1103', null, null, 72, 'Horticultural Science.', 'kuali.enum.type.cip2000',  'DC732E9EA1A74B2E80AF7ABFE574A953', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e54cf0b2-872d-4916-b495-3e4ac55a1d26', '01.1104', '01.1104', null, null, 73, 'Agricultural and Horticultural Plant Breeding.', 'kuali.enum.type.cip2000',  '609A8A5883D74C788FD7DB1825747523', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('75d50545-0f62-4751-9631-5d318c8a7051', '01.1105', '01.1105', null, null, 74, 'Plant Protection and Integrated Pest Management.', 'kuali.enum.type.cip2000',  'A4CEBFA656154BB39EBA056210BC641D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a1961c91-1fa9-4cf0-bfce-106ae4c3fb58', '01.1106', '01.1106', null, null, 75, 'Range Science and Management.', 'kuali.enum.type.cip2000',  '0FB42A4BEDD946A98FD8083782162C5D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c532b910-1572-418f-bae6-ac0a0861124b', '01.1199', '01.1199', null, null, 76, 'Plant Sciences, Other.', 'kuali.enum.type.cip2000',  '242FC5E5AE224E589FD8FC94DF1189E5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e182c384-8937-4e82-8557-08ebc78d3a69', '01.12', '01.12', null, null, 77, 'Soil Sciences.', 'kuali.enum.type.cip2000', '1A35725B5BF44489B9530387A18559B6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ce4233d7-80c9-4fed-b5a1-d3931f7598c9', '01.1201', '01.1201', null, null, 78, 'Soil Science and Agronomy, General.', 'kuali.enum.type.cip2000',  'F4291984637743478B26E922129DFDDA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('db7e9407-7673-4778-913b-857a3743bb9c', '01.1201', '01.1201', null, null, 79, '(Moved from 02.', 'kuali.enum.type.cip2000', 'BC4FBF5F92984E18A0F78021E831CA24',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('369bac6b-a007-4cf5-a726-985bebc27282', '01.1202', '01.1202', null, null, 80, 'Soil Chemistry and Physics.', 'kuali.enum.type.cip2000',  'C57CED3E353545FBBB11D03A0AADCDB2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('fd1aa2bf-e243-4248-992a-2c98d68e3df1', '01.1203', '01.1203', null, null, 81, 'Soil Microbiology.', 'kuali.enum.type.cip2000',  '376B6101327D4AD2B5E27CE12D46AA79', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d8ad8833-07b7-4573-9056-b4b940b6fa00', '01.1299', '01.1299', null, null, 82, 'Soil Sciences, Other.', 'kuali.enum.type.cip2000',  '70C402843D2A4B19972479BEB6B358CE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7fb8cc7f-1171-4dee-988e-08f579c3add5', '01.99', '01.99', null, null, 83, 'Agriculture, Agriculture Operations, and Related Sciences, Other.',  'kuali.enum.type.cip2000', '2449B900FB974CFBB9CDD57863B2363E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a948e55a-038e-4803-bbdf-202ff66a8559', '01.9999', '01.9999', null, null, 84, 'Agriculture, Agriculture Operations, and Related Sciences, Other.',  'kuali.enum.type.cip2000', '80B08DF406E6416D8E10F8941F1610AC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('88606d69-dcbf-4760-a0e5-5e353b04bcd4', '02', '02', null, null, 85, 'AGRICULTURAL SCIENCES.', 'kuali.enum.type.cip2000', 'F7912286F04144FEA7BFF51446C8638F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('358ca0b7-9c9c-47b6-82dd-5006f97abde4', '03.', '03.', null, null, 86, 'NATURAL RESOURCES AND CONSERVATION.', 'kuali.enum.type.cip2000',  '4993B31CD24F45E29406E10F60DF4639', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e2402621-8d21-4b03-a17f-2a7e45c0a4d1', '03.01', '03.01', null, null, 87, 'Natural Resources Conservation and Research.', 'kuali.enum.type.cip2000',  '5BC4E34C694243D4ADC8B4AD198B5466', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f878b1ff-971f-408a-bc5b-633345d182f9', '03.0101', '03.0101', null, null, 88, 'Natural Resources/Conservation, General.', 'kuali.enum.type.cip2000',  '18A56EA6C97A4DFD84C102A35CE92C63', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f8612a6c-5d92-450c-8ce6-62500d94a839', '03.0102', '03.0102', null, null, 89, 'Environmental Science/Studies.', 'kuali.enum.type.cip2000',  '3B2E2BCBD3DA47268984C426E98717C6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c69bb4f3-57a3-4987-9aff-6ad7288a125e', '03.0103', '03.0103', null, null, 90, 'Environmental Studies.', 'kuali.enum.type.cip2000',  '29317F492C574480B63B32A995193EA2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('93262f2c-7079-4927-8d51-9db9744d8f21', '03.0104', '03.0104', null, null, 91, 'Environmental Science.', 'kuali.enum.type.cip2000',  'F7882209E31449A3B2F9C0C751DCB0E1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2c705680-c11b-4a16-9a36-1747e1c23ccc', '03.0199', '03.0199', null, null, 92, 'Natural Resources Conservation and Research, Other.', 'kuali.enum.type.cip2000',  'E8F10B28D5FA47AAAC58074E3A4D9B76', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0618d088-5a43-4eb6-84d4-a1ab0a316838', '03.02', '03.02', null, null, 93, 'Natural Resources Management and Policy.', 'kuali.enum.type.cip2000',  'C19AF9063A624977AB0B095CEB496142', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('64bfa694-6c56-43a3-a795-409a1182cf8e', '03.0201', '03.0201', null, null, 94, 'Natural Resources Management and Policy.', 'kuali.enum.type.cip2000',  'A608C459F60543B8B42D580F7613F7E0', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('70c24408-a1f1-4740-9963-a89d124391eb', '03.0203', '03.0203', null, null, 95, 'Natural Resources Law Enforcement and Protective Services.',  'kuali.enum.type.cip2000', 'C7296C685ABD4E12A0E86DA40F1C9C85', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('de43eb1a-516a-44ee-a5ed-94aeb1983746', '03.0204', '03.0204', null, null, 96, 'Natural Resource Economics.', 'kuali.enum.type.cip2000',  '8C425230EC304E7ABB3627651726D3AD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4597e249-c14e-49a8-8f78-1a832a4600e3', '03.0205', '03.0205', null, null, 97, 'Water, Wetlands, and Marine Resources Management.', 'kuali.enum.type.cip2000',  '4F3345814BFC4DA38578F3E50DC9D7E9', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6264be5f-804d-49f5-a864-c2713f489a2f', '03.0206', '03.0206', null, null, 98, 'Land Use Planning and Management/Development.', 'kuali.enum.type.cip2000',  'BCD93FBEE26342E3A7C1310631C3316B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('cce1d531-5873-41d1-adc7-0a0fdd3e41c7', '03.0299', '03.0299', null, null, 99, 'Natural Resources Management and Policy, Other.', 'kuali.enum.type.cip2000',  '899A2275BA104212BDD8A01E389F2382', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('42f1636b-51e0-4102-a27a-0a1ffb03fee5', '03.03', '03.03', null, null, 100, 'Fishing and Fisheries Sciences and Management.', 'kuali.enum.type.cip2000',  '0261B5F297ED4ADD907D6D65265A4A3D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5d8cd8c1-89b9-474f-b1a5-da559308fc02', '03.0301', '03.0301', null, null, 101, 'Fishing and Fisheries Sciences and Management.', 'kuali.enum.type.cip2000',  '1EB95BDC7A3E4F96859E52192E5E4B23', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('91397ea3-0491-481d-8210-4be9322aee0d', '03.04', '03.04', null, null, 102, 'Forest Production and Processing.', 'kuali.enum.type.cip2000',  'DFB475A012824E98B0E1A929923A7654', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('14e9f9df-11c8-4dc6-bffc-d3cbf5e76909', '03.0401', '03.0401', null, null, 103, 'Forest Harvesting and Production Technology/Technician.',  'kuali.enum.type.cip2000', '65A9324C3E104587B5BA2D23760BD54C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('71fae881-224d-4853-9a03-9e7c67242253', '03.0404', '03.0404', null, null, 104, 'Forest Products Technology/Technician.', 'kuali.enum.type.cip2000',  'CA8C523563C541129FE13B9B5C3CCE5D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9991a81c-992c-4da1-94b1-f1bd5e4b35d5', '03.0405', '03.0405', null, null, 105, 'Logging/Timber Harvesting.', 'kuali.enum.type.cip2000',  '555C48C042014EEB816894E9129BC6B3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('14ff4704-8264-46b0-a2b6-216cb8c79538', '03.0499', '03.0499', null, null, 106, 'Forest Production and Processing, Other.', 'kuali.enum.type.cip2000',  '72D05B55AD8547C0B95A3D5638FEC958', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6feb70ab-148e-43e2-a428-048707ba0111', '03.05', '03.05', null, null, 107, 'Forestry.', 'kuali.enum.type.cip2000', 'D52E16846F124F46904AC2CB3FAB1CBC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3fb54417-e059-4dbc-9b96-f98864cf0b04', '03.0501', '03.0501', null, null, 108, 'Forestry, General.', 'kuali.enum.type.cip2000',  'D1E8A404ACBC4E689BEE29C1FBFD1CC8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8a1c89cb-b31c-4edc-bc32-f7069de34c13', '03.0502', '03.0502', null, null, 109, 'Forest Sciences and Biology.', 'kuali.enum.type.cip2000',  '562D1627FA5D4A0E8F546DDAA2C0E24B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('599fa687-62d6-4c4c-a7ba-6f1923bc063b', '03.0506', '03.0506', null, null, 110, 'Forest Management/Forest Resources Management.', 'kuali.enum.type.cip2000',  'F64521234E5646308DC5A9C6AF012FD8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ec354274-619d-4514-b613-62db227bbafb', '03.0508', '03.0508', null, null, 111, 'Urban Forestry.', 'kuali.enum.type.cip2000',  'D9C46A7CE5CB43529CDC54C452E67F4D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('887690f6-5ea5-4119-ac02-a5d54db4622f', '03.0509', '03.0509', null, null, 112, 'Wood Science and Wood Products/Pulp and Paper Technology.',  'kuali.enum.type.cip2000', '30D71EA0787A4081BACFA6FA6B917437', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d27439d9-995c-437c-bc18-f44e9ca4a751', '03.0510', '03.0510', null, null, 113, 'Forest Resources Production and Management.', 'kuali.enum.type.cip2000',  '4D2E333B5BF246D3926679889939CC09', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bacb225e-a971-4298-86d9-1df832cba23a', '03.0511', '03.0511', null, null, 114, 'Forest Technology/Technician.', 'kuali.enum.type.cip2000',  'D77B635FB5814CEEAAE749FC5197CAF9', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('02dea1bd-96fe-4939-9cce-9e21c074d892', '03.0599', '03.0599', null, null, 115, 'Forestry, Other.', 'kuali.enum.type.cip2000',  'B8C2B067B1014EBEACF64AC9B0C675A5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7d64129a-b1db-41dd-8640-792ff90f562d', '03.06', '03.06', null, null, 116, 'Wildlife and Wildlands Science and Management.', 'kuali.enum.type.cip2000',  '5B34267ED90F4B848EFF316607CD6373', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('dad10a29-3379-4442-bb2e-abdc9ea1648c', '03.0601', '03.0601', null, null, 117, 'Wildlife and Wildlands Science and Management.', 'kuali.enum.type.cip2000',  '90821B01B6504B3CB551525B17781240', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e1314358-e7e3-457b-bfd6-f0e577ff0d43', '03.99', '03.99', null, null, 118, 'Natural Resources and Conservation, Other.', 'kuali.enum.type.cip2000',  'C8B87EC6632844D48DFDA0D80E2E00B5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f4aa52c4-59ea-4a83-832c-b3b724cb374b', '03.9999', '03.9999', null, null, 119, 'Natural Resources and Conservation, Other.', 'kuali.enum.type.cip2000',  '287A4401A1CC4C2F80DD46F5281E2A90', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('27e9938c-db0c-40aa-8831-88f3a02045a6', '04.', '04.', null, null, 120, 'ARCHITECTURE AND RELATED SERVICES.', 'kuali.enum.type.cip2000',  'C529AF3CAC884F61A0DA1974CF1269B6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7b518060-917c-4c2d-999b-b291ddad9ca9', '04.02', '04.02', null, null, 121, 'Architecture.', 'kuali.enum.type.cip2000', 'CDBE163AF8AB4F0EA06C701A4E06E3B6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9be7c90b-470c-4153-9ee0-ef328616f699', '04.0201', '04.0201', null, null, 122, 'Architecture (BArch, BA/BS, MArch, MA/MS, PhD).', 'kuali.enum.type.cip2000',  '978E2C2FE7DB420DA2AD7E26EA91091B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('729e81dd-f014-43b1-a961-e4f64532ca86', '04.03', '04.03', null, null, 123, 'City/Urban, Community and Regional Planning.', 'kuali.enum.type.cip2000',  '1191019E25A14ED38555A60C9B65FDE2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a422ab64-ec22-4efe-8dcc-0d029a3e7f1f', '04.0301', '04.0301', null, null, 124, 'City/Urban, Community and Regional Planning.', 'kuali.enum.type.cip2000',  '4F56E23C98144137A36510D574D581B5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2990b82e-3010-4c1e-b20a-63f06759df0d', '04.04', '04.04', null, null, 125, 'Environmental Design.', 'kuali.enum.type.cip2000',  'B8181AF4B8864A1D9F94C47EE34E109E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ef3c7d8f-3873-4f19-bb14-6c2b25adafe4', '04.0401', '04.0401', null, null, 126, 'Environmental Design/Architecture.', 'kuali.enum.type.cip2000',  '8688FE40CDC64F70B0DF35CD1FAE61A9', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6c2d50aa-ebf2-41ef-b803-27fde95c8a23', '04.05', '04.05', null, null, 127, 'Interior Architecture.', 'kuali.enum.type.cip2000',  'F38F7FE07E494FEE98230AF059799A18', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2b8723ca-c068-4fc2-8def-e01e990e2ec0', '04.0501', '04.0501', null, null, 128, 'Interior Architecture.', 'kuali.enum.type.cip2000',  '2661AF6DD1144D48950DED7CB822CC87', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0327cd64-8829-4346-bd2b-046df7fae64d', '04.06', '04.06', null, null, 129, 'Landscape Architecture.', 'kuali.enum.type.cip2000',  '01A672D53BAA4C65B38B321661799FFD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5b7e1b4e-9e96-400c-bdd9-f5fae107258d', '04.0601', '04.0601', null, null, 130, 'Landscape Architecture (BS, BSLA, BLA, MSLA, MLA, PhD).',  'kuali.enum.type.cip2000', '850EA73AF1F54497B34A59A174609192', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('60049591-437b-4a96-85fd-6650159df88f', '04.07', '04.07', null, null, 131, 'Architectural Urban Design and Planning.', 'kuali.enum.type.cip2000',  '47EC037726FA41229FED2CB90C021A1A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d5c75950-d983-4576-ab0a-e60cc8902846', '04.0701', '04.0701', null, null, 132, 'Architectural Urban Design and Planning.', 'kuali.enum.type.cip2000',  '4B63C450D3DD4B31B6C8B204572718E9', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('eac6e61a-7853-4219-bf46-8a6e70ad00b0', '04.08', '04.08', null, null, 133, 'Architectural History and Criticism.', 'kuali.enum.type.cip2000',  '102ED76343924DADB607FD1BAB97EA66', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0c0ca172-6b5b-439c-a4f0-7a1b3208f4ce', '04.0801', '04.0801', null, null, 134, 'Architectural History and Criticism, General.', 'kuali.enum.type.cip2000',  '66EE0F7902A148648930BBE195890E40', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d8043c9a-f164-4d7a-8e27-26280a0a175d', '04.09', '04.09', null, null, 135, 'Architectural Technology/Technician.', 'kuali.enum.type.cip2000',  '1AC7B66F672D4EC39BDF0CD8D2595ABE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('65f9d878-c2d1-4f59-8476-ea0daa71274d', '04.0901', '04.0901', null, null, 136, 'Architectural Technology/Technician.', 'kuali.enum.type.cip2000',  '5DF5F691578C4DE98879AD8497159652', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('814473b7-1974-440d-aaad-1624bdf5522e', '04.99', '04.99', null, null, 137, 'Architecture and Related Services, Other.', 'kuali.enum.type.cip2000',  'AD6B3799902347A48A9F1357F78248A7', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0b630770-533c-4509-a20f-38e8b4f1d8b7', '04.9999', '04.9999', null, null, 138, 'Architecture and Related Services, Other.', 'kuali.enum.type.cip2000',  'BC2FB9A7E545468287365AE01C594869', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('413159b6-48f3-4058-82c6-49b1aa343c63', '05.', '05.', null, null, 139, 'AREA, ETHNIC, CULTURAL, AND GENDER STUDIES.', 'kuali.enum.type.cip2000',  '79EA62622A5A42A0BE41ADA25BB4F8E7', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bcf767dd-a361-4c1f-983e-d8cf09d1ee3a', '05.01', '05.01', null, null, 140, 'Area Studies.', 'kuali.enum.type.cip2000', '0A0890B783794855B4A84365D8A1BCC9', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('21fc4c7d-2eef-4fda-b658-358cfeec8305', '05.0101', '05.0101', null, null, 141, 'African Studies.', 'kuali.enum.type.cip2000',  '81C5E1AA031B43F68A717E08D1D4B42F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6c113ec8-bc9a-486a-8dd2-67b4c9307d61', '05.0102', '05.0102', null, null, 142, 'American/United States Studies/Civilization.', 'kuali.enum.type.cip2000',  '1E15A4DC29B94F5D9C051F31ED559CD2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4e64859f-a420-47bd-ae68-ded235ed0171', '05.0103', '05.0103', null, null, 143, 'Asian Studies/Civilization.', 'kuali.enum.type.cip2000',  '045B1752B42640C9A5E6A6A1A9AACA73', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ecd39912-e893-4481-95c2-a2679863e37f', '05.0104', '05.0104', null, null, 144, 'East Asian Studies.', 'kuali.enum.type.cip2000',  '0FFF616B3C45411A9BD16EF2A259F975', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2ca177d7-c83c-43eb-8d5e-d6f5d31524f2', '05.0105', '05.0105', null, null, 145, 'Central/Middle and Eastern European Studies.', 'kuali.enum.type.cip2000',  '42C1DF54EC7A4F6AB1DBA80CB9FF9A34', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bbef6e87-7f6d-40dc-a6e9-1b1f41e6fa83', '05.0106', '05.0106', null, null, 146, 'European Studies/Civilization.', 'kuali.enum.type.cip2000',  '9F5FA3D59BAB411A93FBF1C2A4655BA1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b4554140-8b84-46d5-a2ed-175477dac605', '05.0107', '05.0107', null, null, 147, 'Latin American Studies.', 'kuali.enum.type.cip2000',  '0BE74D560A124F42A9572A4FA6B222A4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7290f448-7050-4ca7-a825-d8fecc39fae8', '05.0108', '05.0108', null, null, 148, 'Near and Middle Eastern Studies.', 'kuali.enum.type.cip2000',  'D7D9F599D8C7409A8E639CDEF2338960', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2cd4a51a-5fe5-4c4f-8e3e-d79cdc30527f', '05.0109', '05.0109', null, null, 149, 'Pacific Area/Pacific Rim Studies.', 'kuali.enum.type.cip2000',  'C9A521F4D90045F184CC51CE90866A50', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('57c4a798-df43-4418-8204-f0c311d0cde7', '05.0110', '05.0110', null, null, 150, 'Russian Studies.', 'kuali.enum.type.cip2000',  'AAB26264F3D2431BAB6D93A521A3FA90', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('cb01af85-0901-4b79-ac92-25b03d0a0ddf', '05.0111', '05.0111', null, null, 151, 'Scandinavian Studies.', 'kuali.enum.type.cip2000',  '491C0A2977DE4EAB935C51A6296161EF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('dd63654d-00d7-424a-a879-85c389891ca6', '05.0112', '05.0112', null, null, 152, 'South Asian Studies.', 'kuali.enum.type.cip2000',  'C53D7E08A2764C8AA1EDF027A4673B3D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f28dd682-8373-451d-93ac-d50e1c5e45f6', '05.0113', '05.0113', null, null, 153, 'Southeast Asian Studies.', 'kuali.enum.type.cip2000',  '338876BE33304BA7B0D3C9234E102F5A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('263e74f4-842e-4e70-922a-ddc4e7f8c7ff', '05.0114', '05.0114', null, null, 154, 'Western European Studies.', 'kuali.enum.type.cip2000',  '1C0BA5B21D0640B5B7EBEC31469EC1BF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('509661de-7707-47f6-baba-9405d995db45', '05.0115', '05.0115', null, null, 155, 'Canadian Studies.', 'kuali.enum.type.cip2000',  'FA9DBA44301440948EC0A2A76312C9F6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('dd788a6d-30cd-41e8-b222-11fa83e88627', '05.0116', '05.0116', null, null, 156, 'Balkans Studies.', 'kuali.enum.type.cip2000',  'BF1E8C4B63CE421E9A2A9AB49142F8D8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('67e1bf91-f7c5-4e93-8e0b-cb2632ef967b', '05.0117', '05.0117', null, null, 157, 'Baltic Studies.', 'kuali.enum.type.cip2000',  'C8419CCFFEDC4F83B60CD8F9EEB55F6A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('685fab9a-b501-4546-a8b6-f3e8d22c2678', '05.0118', '05.0118', null, null, 158, 'Slavic Studies.', 'kuali.enum.type.cip2000',  '2A0A5D32A5D2404EA449F4A90A0A649F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1804cf08-dc35-4d68-8a85-c7c1661726e9', '05.0119', '05.0119', null, null, 159, 'Caribbean Studies.', 'kuali.enum.type.cip2000',  'B2811255F79C455790326E2BDF04981B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('978899f2-142a-4088-9332-c8ef9d680797', '05.0120', '05.0120', null, null, 160, 'Ural-Altaic and Central Asian Studies.', 'kuali.enum.type.cip2000',  '9E55D06F86354CB093C23ED23CEAFC72', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('aa0569dc-e477-4155-bcd2-2a45f52fcc5c', '05.0121', '05.0121', null, null, 161, 'Commonwealth Studies.', 'kuali.enum.type.cip2000',  '0754D32615194FDDB8C1EF6C22CB67CD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('75f9fe99-3a12-4f3c-bc0a-636ba22ddbda', '05.0122', '05.0122', null, null, 162, 'Regional Studies (U.', 'kuali.enum.type.cip2000',  '1118FB9DBB914432BC6DBC6C453BC89C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bd64f6e8-0e41-4cf0-9633-563f4cc314b6', '05.0123', '05.0123', null, null, 163, 'Chinese Studies.', 'kuali.enum.type.cip2000',  '17715D03E549457DB63BCEEA300ADA35', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7ed4e1c0-0d52-4f14-a196-8c76367a8819', '05.0124', '05.0124', null, null, 164, 'French Studies.', 'kuali.enum.type.cip2000',  'F1A73675B2634B4FA4F1F4D098ED4DCF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f0898fea-c5b0-49ad-b8a6-94e37128371a', '05.0125', '05.0125', null, null, 165, 'German Studies.', 'kuali.enum.type.cip2000',  '7FE7CD7250C94A73BC72FA464938CE26', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7e56af88-9a98-49a8-8767-8f47f75e034b', '05.0126', '05.0126', null, null, 166, 'Italian Studies.', 'kuali.enum.type.cip2000',  'E9E7DDFA0DED4C8B85DADC9B7CE37DB7', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('04288321-b42f-431a-9d67-388728575ae3', '05.0127', '05.0127', null, null, 167, 'Japanese Studies.', 'kuali.enum.type.cip2000',  'DE4C071D287346BC88E7E724214B4D52', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c5293e75-7284-4e29-9bd0-6dd42f32435c', '05.0128', '05.0128', null, null, 168, 'Korean Studies.', 'kuali.enum.type.cip2000',  '8E840D0E9FCE43218A93F9034B427CB8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('63a977ef-a3de-4804-b73e-3a82528a28d8', '05.0129', '05.0129', null, null, 169, 'Polish Studies.', 'kuali.enum.type.cip2000',  '40970F1151DE4B3E829B44288A7BA6D7', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8965d0ce-6a1d-4be3-849e-e395ddd17254', '05.0130', '05.0130', null, null, 170, 'Spanish and Iberian Studies.', 'kuali.enum.type.cip2000',  '37B4477EA61441E79FD68D7704966A1E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8fe28a4e-0509-4bc2-b5ac-7e390c84fdf5', '05.0131', '05.0131', null, null, 171, 'Tibetan Studies.', 'kuali.enum.type.cip2000',  '498D3BEE490745DCB170C05504ECB77A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5e2a1c77-9347-4dba-ba10-3cd54c1c7544', '05.0132', '05.0132', null, null, 172, 'Ukraine Studies.', 'kuali.enum.type.cip2000',  '23128D11CA4740109397D5A12FAD50EF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('155235bb-0411-41a5-878f-a12c359444ee', '05.0199', '05.0199', null, null, 173, 'Area Studies, Other.', 'kuali.enum.type.cip2000',  '7F02088118C1480EB3AA031C115203EF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b0decd95-2f2d-47a5-a2d4-f07bf8353ed5', '05.02', '05.02', null, null, 174, 'Ethnic, Cultural Minority, and Gender Studies.', 'kuali.enum.type.cip2000',  '3557CF5A3ABA4174AB5A5284A40DAD46', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('99147cf4-a9c6-469a-9987-9e0add3a8e2c', '05.0201', '05.0201', null, null, 175, 'African-American/Black Studies.', 'kuali.enum.type.cip2000',  '25BF08497AEE462FBC5ED56CE306831A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4fd0184f-89cd-45f3-a0af-ceb755c81d94', '05.0202', '05.0202', null, null, 176, 'American Indian/Native American Studies.', 'kuali.enum.type.cip2000',  'B1E12EA87C554B1CB6D6BBC749EE794A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('39c26a82-b122-4b5d-b4c7-8c9ef9cf7983', '05.0203', '05.0203', null, null, 177, 'Hispanic-American, Puerto Rican, and Mexican-American/Chicano Studies.',  'kuali.enum.type.cip2000', 'FA5330FC20F74BA2994FC12875B9CA9D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('be3a272c-6dca-44df-8efd-786f65c70695', '05.0204', '05.0204', null, null, 178, 'Islamic Studies.', 'kuali.enum.type.cip2000',  'BFA46AA5CBF1418391F0437A4442922E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5798a392-99d6-4908-87f8-7726c9f6de76', '05.0205', '05.0205', null, null, 179, 'Jewish/Judaic Studies.', 'kuali.enum.type.cip2000',  '059F99BD7D4940CDB835807226611D0E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5dab5090-94b9-4b87-8b02-48f9ce9def46', '05.0206', '05.0206', null, null, 180, 'Asian-American Studies.', 'kuali.enum.type.cip2000',  'ABE64749F26743E7A41A3BF580B55863', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9e5cc437-4867-4697-9d6f-51ba0443c4be', '05.0207', '05.0207', null, null, 181, 'Women''s Studies.', 'kuali.enum.type.cip2000',  '3D2C5C1BF8714A16AF23BF4C328F8BCC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('37c570b0-6ee5-4808-9d99-cd87a9f03ee3', '05.0208', '05.0208', null, null, 182, 'Gay/Lesbian Studies.', 'kuali.enum.type.cip2000',  'C60955C1617C442DAB27100FD4BBED1B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('75dcc652-2117-4977-88e6-8d576684abc7', '05.0299', '05.0299', null, null, 183, 'Ethnic, Cultural Minority, and Gender Studies, Other.',  'kuali.enum.type.cip2000', 'CC7901F214BA476494B6D123B9CB3646', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0d52e6af-7801-4906-93f1-98f14640dedf', '05.99', '05.99', null, null, 184, 'Area, Ethnic, Cultural, and Gender Studies, Other.', 'kuali.enum.type.cip2000',  '261CBAE227914AC6ADBD0AE2935D37C3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4ddac811-6ce0-4976-b845-1b1da78f7d95', '05.9999', '05.9999', null, null, 185, 'Area, Ethnic, Cultural, and Gender Studies, Other.', 'kuali.enum.type.cip2000',  'E86DD9E6EE58404D977604AB43E7C7C3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ae9d8b0d-4458-4d90-8d42-4bdd65dcb73b', '08.', '08.', null, null, 186, 'MARKETING OPERATIONS/MARKETING AND DISTRIBUTION.', 'kuali.enum.type.cip2000',  'BEA40F05317E469EA78679C6A39ABFAA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('58db9fef-069a-4418-b947-eb1cf7fec462', '09.', '09.', null, null, 187, 'COMMUNICATION, JOURNALISM, AND RELATED PROGRAMS.', 'kuali.enum.type.cip2000',  '6E02E92B217B4DC6B728042C7A61B77A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('84836410-9042-43cc-9d87-9ed61137e61e', '09.01', '09.01', null, null, 188, 'Communication and Media Studies.', 'kuali.enum.type.cip2000',  '3C90133612E64B85990C0AAC83F9A794', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ffd7acd5-73da-406f-b44e-91f5fa9e6748', '09.0101', '09.0101', null, null, 189, 'Communication Studies/Speech Communication and Rhetoric.',  'kuali.enum.type.cip2000', '87D7EBAE8C274EC9B1E82A51777B75D3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('83d89688-7545-40b3-b85e-94a72e70000f', '09.0102', '09.0102', null, null, 190, 'Mass Communication/Media Studies.', 'kuali.enum.type.cip2000',  '1DF13FDAF1574A1D9C4AF4F697ABF3E1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('264c62cb-0a35-4319-a745-b36fe75310e7', '09.0199', '09.0199', null, null, 191, 'Communication and Media Studies, Other.', 'kuali.enum.type.cip2000',  '0DDD9C0308164C08BD6C488C51FC468C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0dadd452-fe41-4c3b-a008-4acfb4ddf5da', '09.02', '09.02', null, null, 192, 'Advertising.', 'kuali.enum.type.cip2000', '6825D9CF981249A0BD5C07C2B818204D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9845f036-b5f5-4410-869a-baeebe9afc36', '09.0201', '09.0201', null, null, 193, 'Advertising.', 'kuali.enum.type.cip2000', 'F9CEE6C14653402DA12F5AA03A6A51F3',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6cf0e89b-6154-4ab1-93bc-6333b0ddf472', '09.04', '09.04', null, null, 194, 'Journalism.', 'kuali.enum.type.cip2000', 'A56102F6354942F5A2271BF90CCC474A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('eb8e434e-03ab-452c-9939-9f9b651880ec', '09.0401', '09.0401', null, null, 195, 'Journalism.', 'kuali.enum.type.cip2000', '78963C85EC8544169BB5774CBC581169', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d305410f-f678-42e6-a317-e7a0974e4891', '09.0402', '09.0402', null, null, 196, 'Broadcast Journalism.', 'kuali.enum.type.cip2000',  '8A31CE5D79D2406CAB7DC77E39BF7D31', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f5b46c57-fa2d-4ff8-aecd-71a57c4a2e55', '09.0403', '09.0403', null, null, 197, 'Mass Communications.', 'kuali.enum.type.cip2000',  '9E24D50397674D6D943D4FBB0E8745EE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ebb4421e-8895-433f-aa6c-e88db26c5551', '09.0404', '09.0404', null, null, 198, 'Photojournalism.', 'kuali.enum.type.cip2000',  '1FFF73B6F6D9431780359E97AAEE9013', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('05f504a0-116c-45e3-b570-d453c46d88b1', '09.0499', '09.0499', null, null, 199, 'Journalism, Other.', 'kuali.enum.type.cip2000',  '6B38168B04E741AB8D237293C8021EC1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('47ade3ff-104e-45b8-9c7e-4364ebe7ba72', '09.05', '09.05', null, null, 200, 'Public Relations and Organizational Communications.', 'kuali.enum.type.cip2000',  '14552D964C484C0C82A3E9CED44CE3D2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b630c1a4-f711-4c0c-ac66-de9543afcc6c', '09.0501', '09.0501', null, null, 201, 'Public Relations and Organizational Communications.',  'kuali.enum.type.cip2000', '97BE36DA1FAA48948306A45968143609', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('eb1a4bc6-bbc5-4f34-8ac8-45223fd2c904', '09.07', '09.07', null, null, 202, 'Radio, Television, and Digital Communication.', 'kuali.enum.type.cip2000',  '38954968DEC0471DAFB17AE642D0C68A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ff2732f4-c312-49b1-8198-05edf94a0b72', '09.0701', '09.0701', null, null, 203, 'Radio and Television.', 'kuali.enum.type.cip2000',  '08A5FB696A6A43D48C51EE5EE666F27C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('646cce5c-4665-47f0-a0e6-31207e7abcff', '09.0702', '09.0702', null, null, 204, 'Digital Communication and Media/Multimedia.', 'kuali.enum.type.cip2000',  '71E36AE0C50C490C8E495A7E6B3E9800', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5f74d838-b0ee-4e3b-ae1d-51db49b549ec', '09.0799', '09.0799', null, null, 205, 'Radio, Television, and Digital Communication, Other.',  'kuali.enum.type.cip2000', 'BF76B006D4C843CB8962DC1646961C2A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('de3abb89-7ea5-4ae5-8560-b254e34ebbdd', '09.09', '09.09', null, null, 206, 'Public Relations, Advertising, and Applied Communication.',  'kuali.enum.type.cip2000', '4F6C6971C8F1478B80C07D565BAA1294', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4fd78396-5fbc-4dc1-aac9-ffdfc00b6d30', '09.0901', '09.0901', null, null, 207, 'Organizational Communication, General.', 'kuali.enum.type.cip2000',  'DC5572C0400E4C4CA4C94ECB8621D049', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('be7f24ea-fa06-4c29-91ea-3d25e31f5f7c', '09.0902', '09.0902', null, null, 208, 'Public Relations/Image Management.', 'kuali.enum.type.cip2000',  '6038213E6BC04EC59EA36E9D4A90A782', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('cdc97dc8-aa22-4eaf-bc75-b49c6b503813', '09.0903', '09.0903', null, null, 209, 'Advertising.', 'kuali.enum.type.cip2000', '83A6D5CA81764186954035F979690370',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('075d5597-024d-474b-88f1-c68d3ce7020e', '09.0904', '09.0904', null, null, 210, 'Political Communication.', 'kuali.enum.type.cip2000',  'C37EE01CDE124B3AB4033283FAB9964E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('dbd522e5-87ed-4619-8037-0c1416194b8f', '09.0905', '09.0905', null, null, 211, 'Health Communication.', 'kuali.enum.type.cip2000',  '27C4A7A35D314572AB15F5B55C629592', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e981f9f0-cc84-46da-b0bf-3bf92dbcf07b', '09.0999', '09.0999', null, null, 212, 'Public Relations, Advertising, and Applied Communication, Other (NEW) Any  instructional program in organizational communication, public relations, and advertising not listed above.', 'kuali.enum.type.cip2000',  '8F2C4E6C25E84857889F52384FC93F95', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3ca7399b-0799-4fe5-a558-1b8a020b54e9', '09.10', '09.10', null, null, 213, 'Publishing.', 'kuali.enum.type.cip2000', '1F6D0119A1BB4147815CDD003C85BAEA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9b067af4-85d9-44ba-82db-af2ba68d4c8a', '09.1001', '09.1001', null, null, 214, 'Publishing.', 'kuali.enum.type.cip2000', 'A700C40342074F24AAA88379B340263C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('730320ce-ad58-4f40-b13d-482566f59279', '09.99', '09.99', null, null, 215, 'Communication, Journalism, and Related Programs, Other.',  'kuali.enum.type.cip2000', '618DDF0C4B9A4890B9FC7A8A32EBEE83', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a5ac0c10-44b4-4b14-b1cf-ba21ff49b127', '09.9999', '09.9999', null, null, 216, 'Communication, Journalism, and Related Programs, Other.',  'kuali.enum.type.cip2000', '0EDE4927253B43FEBF6C4B5BD89F986B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('287a197f-c848-41e4-b156-a81e05496485', '10.', '10.', null, null, 217, 'COMMUNICATIONS TECHNOLOGIES/TECHNICIANS AND SUPPORT SERVICES.',  'kuali.enum.type.cip2000', '6A1C9C4EDB2E47BE941DE1D8B6C775D3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4448da22-ac9d-42b0-ad08-55bb0ee92090', '10.01', '10.01', null, null, 218, 'Communications Technology/Technician.', 'kuali.enum.type.cip2000',  '42A32F4FB678407BAFF227988109B14B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('de6eb66f-76ca-46ef-a776-66c20a5ff808', '10.0101', '10.0101', null, null, 219, 'Educational/Instructional Media Technology/Technician.',  'kuali.enum.type.cip2000', '54B243A8795C4CFE879396BFF337A009', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('328a2c69-131a-4ad3-8c46-2d102e3cd859', '10.0103', '10.0103', null, null, 220, 'Photographic Technology/Technician.', 'kuali.enum.type.cip2000',  '4636DB8E19CC4B56AB494BF22BB55F81', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('80f07152-7011-4d50-9b5a-c1140b7f223d', '10.0104', '10.0104', null, null, 221, 'Radio and Television Broadcasting Technology/Technician.',  'kuali.enum.type.cip2000', '613AC9E415214B029361FA5F3CB63B81', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6539b285-0bcc-4ac1-b435-83c3eefad691', '10.0105', '10.0105', null, null, 222, 'Communications Technology/Technician.', 'kuali.enum.type.cip2000',  '2D8EA2F7471D4CB09E7B8CD554198211', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9a28dfad-e45c-49b8-ae9a-0a357d9fe99f', '10.0199', '10.0199', null, null, 223, 'Communications Technology/Technician, Other.', 'kuali.enum.type.cip2000',  'B8D497C7BD72498B9852F7C9328A4EA2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('dae4ff95-68a9-492b-87a2-dac76acf0c7e', '10.02', '10.02', null, null, 224, 'Audiovisual Communications Technologies/Technicians.', 'kuali.enum.type.cip2000',  '49B9C0576430435CB0FD7DA5E7A1D3A2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('80d85f09-8abe-43af-ac2d-cd0f37739371', '10.0201', '10.0201', null, null, 225, 'Photographic and Film/Video Technology/Technician and Assistant.',  'kuali.enum.type.cip2000', '28BF2E2D159046A5866BB551B8F53573', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4f3c13e7-8a48-4117-a990-fe924f248d3d', '10.0202', '10.0202', null, null, 226, 'Radio and Television Broadcasting Technology/Technician.',  'kuali.enum.type.cip2000', 'A7304390FB7E491AADE9E069FE12AB6A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0feef173-db9a-49cb-a104-a8d68e57b9e6', '10.0203', '10.0203', null, null, 227, 'Recording Arts Technology/Technician.', 'kuali.enum.type.cip2000',  '7D48BD13CD324753A76CFA10F0C12614', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6edba127-5f22-4688-b289-19737141d1ee', '10.0299', '10.0299', null, null, 228, 'Audiovisual Communications Technologies/Technicians, Other.',  'kuali.enum.type.cip2000', '4A37BF949294426DBAA932F60FF089EA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d430285a-1430-4475-983c-af372b4c7c4a', '10.03', '10.03', null, null, 229, 'Graphic Communications.', 'kuali.enum.type.cip2000',  '47039686B78A47408F3AAAC78C8593D1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1c9ee3de-5d9b-4971-bb31-f0b9f57f1f01', '10.0301', '10.0301', null, null, 230, 'Graphic Communications, General.', 'kuali.enum.type.cip2000',  'BEF6B6C23B6B4D838FC06ADF30B228AB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6f6a48c2-7464-4d4f-bf12-ac5b2c549cc0', '10.0302', '10.0302', null, null, 231, 'Printing Management.', 'kuali.enum.type.cip2000',  '4E2194A9C59A470297BF83ABAC4BC2CB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('80e69138-26ac-417b-817a-2ee379d13d29', '10.0303', '10.0303', null, null, 232, 'Prepress/Desktop Publishing and Digital Imaging Design.',  'kuali.enum.type.cip2000', '52B1B95A1AC746EAA5FE109BD990235A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('922fbeaf-875a-4b2f-b903-d31fc97ee22c', '10.0304', '10.0304', null, null, 233, 'Animation, Interactive Technology, Video Graphics and Special Effects.',  'kuali.enum.type.cip2000', '2F77F48D00374C1E9697FFE0DFBC16D1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d2ca4724-9842-4d03-b8e4-9c7325a2ecbe', '10.0305', '10.0305', null, null, 234, 'Graphic and Printing Equipment Operator, General Production.',  'kuali.enum.type.cip2000', '4890E3D0827A4B05965AE4CDC771DFBB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('cb9bbbf0-a189-4afc-8bfa-5aa3905f5852', '10.0306', '10.0306', null, null, 235, 'Platemaker/Imager.', 'kuali.enum.type.cip2000',  '121E793550E747E9ABFEE2356BC75298', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('52221c7d-6bc4-4334-85de-b159a489ed85', '10.0307', '10.0307', null, null, 236, 'Printing Press Operator.', 'kuali.enum.type.cip2000',  '4EF1E601167740FC8459BABF9C8F04D0', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('90368e91-9e7b-47f3-8777-d389a0179be3', '10.0308', '10.0308', null, null, 237, 'Computer Typography and Composition Equipment Operator.',  'kuali.enum.type.cip2000', 'A1FAFDC0C6FE4D5A89C7839002B9028B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('77c1c12c-7120-4819-ad1e-ebea689a1ef5', '10.0399', '10.0399', null, null, 238, 'Graphic Communications, Other.', 'kuali.enum.type.cip2000',  'A47B55BAFAAA4F95B49CF24393B2FFF0', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('90584c21-ae62-4f29-99a2-8f5ed1ccc9d4', '10.99', '10.99', null, null, 239, 'Communications Technologies/Technicians and Support Services, Other.',  'kuali.enum.type.cip2000', 'ED844F2AD99C42728846C8199246037A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bb600fdc-a33c-4509-a52c-493dcc7e126e', '10.9999', '10.9999', null, null, 240, 'Communications Technologies/Technicians and Support Services, Other.',  'kuali.enum.type.cip2000', 'D3D664CD163E4C02B6FC5F4786BAA1F2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9cb5804d-f6dd-44d5-bd1d-be6e9954014a', '11.', '11.', null, null, 241, 'COMPUTER AND INFORMATION SCIENCES AND SUPPORT SERVICES.', 'kuali.enum.type.cip2000',  'EFA0AAFA807F4F1FB5D55B024F8C47F4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('25df01da-0f98-44fb-9c43-b69888aa8d4b', '11.01', '11.01', null, null, 242, 'Computer and Information Sciences, General .', 'kuali.enum.type.cip2000',  '6306033D70D948FBB27F402828C2A1D2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5b205056-8048-4274-80b0-2d3e9cac6ff8', '11.0101', '11.0101', null, null, 243, 'Computer and Information Sciences, General.', 'kuali.enum.type.cip2000',  'F6ADCBC658C34E2AAD4EA644F9BA6C96', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5fd4ed13-adaa-462b-8dd6-77d9d7295212', '11.0102', '11.0102', null, null, 244, 'Artificial Intelligence and Robotics.', 'kuali.enum.type.cip2000',  '3D27C789AF1542DA8AF1F7E4C3A82231', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e83f6890-685d-454d-9010-b9efefa1d9b5', '11.0103', '11.0103', null, null, 245, 'Information Technology.', 'kuali.enum.type.cip2000',  '37CB5D7353334658A62F4222B887F11C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('fa160771-c8ca-4d2d-b6bc-7455fd7a5801', '11.0199', '11.0199', null, null, 246, 'Computer and Information Sciences, Other.', 'kuali.enum.type.cip2000',  '338AACF130494F8EAE25EB337A2799C7', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('82da6969-cc9b-4aaf-a79d-42e5b247c204', '11.02', '11.02', null, null, 247, 'Computer Programming.', 'kuali.enum.type.cip2000',  '0B2C7482A78844F59B07A2177B80730D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1309fa19-1544-4f6a-9e0c-0e1d19c74dd7', '11.0201', '11.0201', null, null, 248, 'Computer Programming/Programmer, General.', 'kuali.enum.type.cip2000',  'C5759CF51D26422F9147B5146B1EC5AB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('dc271ffc-3fb1-4176-8121-7a7aa05b8e77', '11.0202', '11.0202', null, null, 249, 'Computer Programming, Specific Applications.', 'kuali.enum.type.cip2000',  '5BA29E4ACA1C493EB27F4D4FDFF76B89', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ccb21b16-4bb2-42d9-864f-0905f200b3ab', '11.0203', '11.0203', null, null, 250, 'Computer Programming, Vendor/Product Certification.',  'kuali.enum.type.cip2000', '8E9F4778364D4E37A119AC563AE44FFF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2b90addb-cd6c-49f6-a890-3ca3b39c5674', '11.0299', '11.0299', null, null, 251, 'Computer Programming, Other.', 'kuali.enum.type.cip2000',  '5FBB3EC23E174AB983E6E7886C0ED7C5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('85c1ca73-d631-496a-8376-2d2d15ffe4c5', '11.03', '11.03', null, null, 252, 'Data Processing.', 'kuali.enum.type.cip2000', '7944BC87742940719DB8B9AE7709A211',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('acaa1136-763d-4d5f-b8f6-cf287957a8b8', '11.0301', '11.0301', null, null, 253, 'Data Processing and Data Processing Technology/Technician.',  'kuali.enum.type.cip2000', '1020581458DC4F8785E92CA52532224D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('47af8be5-d4c1-4983-9ac0-dc867f45cafc', '11.04', '11.04', null, null, 254, 'Information Science/Studies.', 'kuali.enum.type.cip2000',  '7CD98DF53FEC4F1C99C9E8C86EB499AC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('91d90ab7-b710-492f-aa7f-dec92d28752a', '11.0401', '11.0401', null, null, 255, 'Information Science/Studies.', 'kuali.enum.type.cip2000',  '322E15B046A5470395675A809253A498', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4b917af1-c604-4e78-8dbb-17ce38010971', '11.05', '11.05', null, null, 256, 'Computer Systems Analysis.', 'kuali.enum.type.cip2000',  '0F88E567CA394452AD7FD3E783726D8F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c3e168ec-9ae6-4660-ab4b-32ccf7527f90', '11.0501', '11.0501', null, null, 257, 'Computer Systems Analysis/Analyst.', 'kuali.enum.type.cip2000',  '34C50353EB8A4FDAA792E6C83921F004', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('48eb37f5-0374-4bb6-b8f6-fd05d0d502ac', '11.06', '11.06', null, null, 258, 'Data Entry/Microcomputer Applications.', 'kuali.enum.type.cip2000',  'EFEF30B2A4A84FB8B0BF6239F2162CF3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a1b36adb-c5a8-4380-b76b-1fac22bd7cb6', '11.0601', '11.0601', null, null, 259, 'Data Entry/Microcomputer Applications, General.', 'kuali.enum.type.cip2000',  '9F4CB771E0CA4B7BB8AD6D985F6949F1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2975346d-224d-48e2-b784-b4258d9eb46c', '11.0602', '11.0602', null, null, 260, 'Word Processing.', 'kuali.enum.type.cip2000',  '7ECB65E7036D4B6DBE22478DD13AA394', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c8db5546-1225-404f-9f81-bcf61a66ad42', '11.0699', '11.0699', null, null, 261, 'Data Entry/Microcomputer Applications, Other.', 'kuali.enum.type.cip2000',  'E52BA83B24154869B189D3196C17A4A6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b7fd8dc2-4cb0-4208-9995-400b42795a43', '11.07', '11.07', null, null, 262, 'Computer Science.', 'kuali.enum.type.cip2000', '6D5376F56CCE457DAC8AA89AC6CE4B7C',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9760c33d-b191-4028-ab4c-b4bb5254f461', '11.0701', '11.0701', null, null, 263, 'Computer Science.', 'kuali.enum.type.cip2000',  'C47DD10E346F40AEA107ECE1CBEB5782', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d4d1bbda-7609-4427-8e67-1f44f7575458', '11.08', '11.08', null, null, 264, 'Computer Software and Media Applications.', 'kuali.enum.type.cip2000',  '179BBB37FB4E4C2E81C06515949DB9D7', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8d21f818-69c6-46a1-ace6-56e7e65b571a', '11.0801', '11.0801', null, null, 265, 'Web Page, Digital/Multimedia and Information Resources Design.',  'kuali.enum.type.cip2000', 'D7BAC0470FFF4FCFB4EA2D571331D082', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1e557291-add6-481d-9fcf-338e5805d779', '11.0802', '11.0802', null, null, 266, 'Data Modeling/Warehousing and Database Administration.',  'kuali.enum.type.cip2000', '9EB76459C92849D9BF914ABA656B3981', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bee292dc-bc41-4e1d-82da-52c67190625e', '11.0803', '11.0803', null, null, 267, 'Computer Graphics.', 'kuali.enum.type.cip2000',  '928C95017D914DB5AA48773B69675516', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('61ba83a9-1011-40b4-b200-365495f39464', '11.0899', '11.0899', null, null, 268, 'Computer Software and Media Applications, Other.', 'kuali.enum.type.cip2000',  '55DF913E66BE4EBE8C932631A5AB1A5B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8064ad3f-ec71-4044-a15b-0c1b75e8ce86', '11.09', '11.09', null, null, 269, 'Computer Systems Networking and Telecommunications.', 'kuali.enum.type.cip2000',  'E340AD6C44814BD1B6313EF6F0005A6B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b1c44eca-25d0-428a-a113-40b644f5591a', '11.0901', '11.0901', null, null, 270, 'Computer Systems Networking and Telecommunications.',  'kuali.enum.type.cip2000', '3FE6A16276EF4B9994BD9073FE23463C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('014c6b37-ee89-40d9-8551-f9904bd1b5ce', '11.10', '11.10', null, null, 271, 'Computer/Information Technology Administration and Management.',  'kuali.enum.type.cip2000', '27D35E37810B4ED0BF36F2E07B299ECD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8b623d5b-378f-4dd8-9c2c-7be17f621e66', '11.1001', '11.1001', null, null, 272, 'System Administration/Administrator.', 'kuali.enum.type.cip2000',  '63EB351D92834CD3B398934664F03C3F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e0697252-081b-4eed-adff-d36dffb90151', '11.1002', '11.1002', null, null, 273, 'System, Networking, and LAN/WAN Management/Manager.',  'kuali.enum.type.cip2000', '2380C4099B6E4BDE8EFFF8331AB6B68C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4cb115b2-0063-41e7-939d-21f3a9d32584', '11.1003', '11.1003', null, null, 274, 'Computer and Information Systems Security.', 'kuali.enum.type.cip2000',  'ADF275FAC4344426B5348722D7A99210', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('27a36ae8-e44b-4ae1-b6b9-4fc95d4f06ad', '11.1004', '11.1004', null, null, 275, 'Web/Multimedia Management and Webmaster.', 'kuali.enum.type.cip2000',  '365BCB1265AD4573BA598907B465E667', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('31fafe16-2df1-41bf-aecc-16f0c1bd4de7', '11.1099', '11.1099', null, null, 276, 'Computer/Information Technology Services Administration andManagement, Other.',  'kuali.enum.type.cip2000', '9DA4102A0ABF45D8BBAE73E39F6FA98A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('39e801aa-5cb6-49c9-865b-4bfbd58334a3', '11.99', '11.99', null, null, 277, 'Computer and Information Sciences and Support Services, Other.',  'kuali.enum.type.cip2000', '1F67D1E603E049E4A90646CAF9705512', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ebcfe7d4-696b-4201-be76-e3f429da6f80', '11.9999', '11.9999', null, null, 278, 'Computer and Information Sciences and Support Services, Other.',  'kuali.enum.type.cip2000', 'D99CF16434404EF9B0C3E52998D2D570', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1a489857-f960-4699-a5c0-0813a6c2c0ee', '12.', '12.', null, null, 279, 'PERSONAL AND CULINARY SERVICES.', 'kuali.enum.type.cip2000',  'A80B733E352C47DBAC598E53C2095803', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a833edc9-452b-41b3-95fd-a3feb8bab106', '12.02', '12.02', null, null, 280, 'Gaming and Sports Officiating Services.', 'kuali.enum.type.cip2000',  '3B405B309C254444B6B795601F95445D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b65bbb7c-6c96-4c08-aa7e-957468bbb7cb', '12.0203', '12.0203', null, null, 281, 'Gaming Attendant/Manager.', 'kuali.enum.type.cip2000',  'C25B4AD6B9A5458BA2F2C238FCFF1F01', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f9e68783-0d5a-4c7a-a650-fae8b1195ba5', '12.0204', '12.0204', null, null, 282, 'Umpire/Referee/Sports Officiating.', 'kuali.enum.type.cip2000',  '9D6ED0F4C75143148D4B5C001E1B5D0A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c7520f63-768e-4bd4-8c39-698f132bd2fc', '12.0299', '12.0299', null, null, 283, 'Gaming and Sports Officiating Services, Other.', 'kuali.enum.type.cip2000',  'FF8C5C99C2B947C19F8C1E2204CB1F0B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6f5fe123-6f85-4798-bccf-fc9901eb885a', '12.03', '12.03', null, null, 284, 'Funeral Service and Mortuary Science.', 'kuali.enum.type.cip2000',  'F881992952794B2BBBF2093844C19875', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c199bb2f-f014-412e-be5c-a43d424a7ac0', '12.0301', '12.0301', null, null, 285, 'Funeral Service and Mortuary Science, General.', 'kuali.enum.type.cip2000',  '0A2A1B4606C04D4A985E871B452E0820', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9ba1f721-9198-4fa0-8321-c7f86ec76bb1', '12.0302', '12.0302', null, null, 286, 'Funeral Direction/Service.', 'kuali.enum.type.cip2000',  '42B8AF5D09654F1C9089FD8C7CF23CE4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('fc7d33b2-d7b0-4fa3-a19f-c5773bfd0cc0', '12.0303', '12.0303', null, null, 287, 'Mortuary Science and Embalming/Embalmer.', 'kuali.enum.type.cip2000',  'DCA04932AD284D5F8A64219AAA2EDDC3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('695e7ec7-8c8c-4342-bf43-6095cf71d166', '12.0399', '12.0399', null, null, 288, 'Funeral Service and Mortuary Science, Other.', 'kuali.enum.type.cip2000',  'A67F366AB38449B4876C90DCECDB0DEB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('971d9297-e099-4c1c-8e7b-05f7e7d58407', '12.04', '12.04', null, null, 289, 'Cosmetology and Related Personal Grooming Services.', 'kuali.enum.type.cip2000',  '63A5C3BCC6C24E9DB4F046A4B97181E2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5c5deab3-b092-4165-8f15-a5624f127467', '12.0401', '12.0401', null, null, 290, 'Cosmetology/Cosmetologist, General.', 'kuali.enum.type.cip2000',  '546DBDC800684DB19B44AD78A519CB7B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e8a05cdf-2729-4a6c-85c4-02cd72f9977b', '12.0402', '12.0402', null, null, 291, 'Barbering/Barber.', 'kuali.enum.type.cip2000',  '8127D64717DD44EF8931BB6EC75D5A99', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9952a7ba-a019-47bc-9d23-d3ff72771c6c', '12.0403', '12.0403', null, null, 292, 'Cosmetologist.', 'kuali.enum.type.cip2000', 'DED0D7D13E4D4C8CA7A5AF1922E1AA0A',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('846b4da1-4e91-4ae9-8e68-ceba685b806e', '12.0404', '12.0404', null, null, 293, 'Electrolysis/Electrology and Electrolysis Technician.',  'kuali.enum.type.cip2000', 'DA7517BE3DE24A6BB2FB91F9E0AEE502', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b1d34c08-1821-4d74-9ca9-15c072d0be49', '12.0405', '12.0405', null, null, 294, 'Massage.', 'kuali.enum.type.cip2000', '36BA2DD8C4F14B63A6906BA47085B207', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('58ab2ad8-e0d1-49cf-b51d-567ce57bf4ba', '12.0406', '12.0406', null, null, 295, 'Make-Up Artist/Specialist.', 'kuali.enum.type.cip2000',  '2D69A91C06014F4AA114242AFC3A29C3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3abaa982-9c73-47bf-8d94-402f3ba90ab9', '12.0407', '12.0407', null, null, 296, 'Hair Styling/Stylist and Hair Design.', 'kuali.enum.type.cip2000',  'D769157D7FBE491A97F9C479435CD1F6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('628d399e-361b-4089-9dd4-9502ba999f0d', '12.0408', '12.0408', null, null, 297, 'Facial Treatment Specialist/Facialist.', 'kuali.enum.type.cip2000',  '7C8CF28FDF224E5896F1602148DB6017', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('29d0837c-b0dd-4d33-8803-8ef470387874', '12.0409', '12.0409', null, null, 298, 'Aesthetician/Esthetician and Skin Care Specialist.', 'kuali.enum.type.cip2000',  '1EDF04271108422FB42F74C58D02C68B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('21c40a2d-a2e9-417d-9edd-c2c298fe0a0c', '12.0410', '12.0410', null, null, 299, 'Nail Technician/Specialist and Manicurist.', 'kuali.enum.type.cip2000',  '77D9CD095E864033B7BCC15C6192BF84', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('15e8f21d-5e68-4589-acea-b758cc597d28', '12.0411', '12.0411', null, null, 300, 'Permanent Cosmetics/Makeup and Tattooing.', 'kuali.enum.type.cip2000',  '8E2DADF4FBAE4122857C9FE403A0C41F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('66fbde1b-e93c-4e23-8b89-9d4a5ee488a7', '12.0412', '12.0412', null, null, 301, 'Salon/Beauty Salon Management/Manager.', 'kuali.enum.type.cip2000',  'B620E67DF64145AB90B0F7DD998F39E9', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2cc2cf86-65c0-48d8-b3bb-a7dfaa7e757f', '12.0413', '12.0413', null, null, 302, 'Cosmetology, Barber/Styling, and Nail Instructor.', 'kuali.enum.type.cip2000',  '6121136F8600465D9758A7C6CB26E984', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d23e1bf7-beb8-468d-bd73-dc2f97bcb624', '12.0499', '12.0499', null, null, 303, 'Cosmetology and Related Personal Grooming Arts, Other.',  'kuali.enum.type.cip2000', '67FB58715C2E4C10A0AA306A84CDDF55', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6f941954-0822-4728-86db-c7c46a54f8c1', '12.05', '12.05', null, null, 304, 'Culinary Arts and Related Services.', 'kuali.enum.type.cip2000',  'E075193E082E479489A32D0143C02EED', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0e11cbf9-5905-4985-9e25-25e0b6bb5ec5', '12.0500', '12.0500', null, null, 305, 'Cooking and Related Culinary Arts, General.', 'kuali.enum.type.cip2000',  '59F8DC2455EB4CA6888A8674D3FB835A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('42c8c0e6-2dec-4505-89da-199c01ef68a8', '12.0501', '12.0501', null, null, 306, 'Baking and Pastry Arts/Baker/Pastry Chef.', 'kuali.enum.type.cip2000',  'C97AC1680D194795B3A6AB201FDAB96F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d4372aab-904e-4bfd-b0ef-e90560b9e9be', '12.0502', '12.0502', null, null, 307, 'Bartending/Bartender.', 'kuali.enum.type.cip2000',  'EDBEB5EA4A69490A83E97F6F35156E7D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('316b09db-224d-4eba-890f-7b55883f5d04', '12.0503', '12.0503', null, null, 308, 'Culinary Arts/Chef Training.', 'kuali.enum.type.cip2000',  '9C4B7403DD0C4961A90577270B26AE6F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e108f9a5-8b05-493d-ab63-a0551c6bd3b2', '12.0504', '12.0504', null, null, 309, 'Restaurant, Culinary, and Catering Management/Manager.',  'kuali.enum.type.cip2000', 'DFFB8372FE75456AA707B1CE5E5A2486', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6902c709-a22b-4e6d-b52b-b5b606d767d9', '12.0505', '12.0505', null, null, 310, 'Food Preparation/Professional Cooking/Kitchen Assistant.',  'kuali.enum.type.cip2000', '27F00E6D633742D0A5282F4554A43629', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a19ca7d1-0842-48bf-a33f-43aae2f689c3', '12.0506', '12.0506', null, null, 311, 'Meat Cutting/Meat Cutter.', 'kuali.enum.type.cip2000',  'D665AEE24D7E414C954F0AFF0ECA679B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b7bb8033-1c19-424d-8211-dff7cfd114d4', '12.0507', '12.0507', null, null, 312, 'Food Service, Waiter/Waitress, and Dining Room Management/Manager.',  'kuali.enum.type.cip2000', 'B09180D95BF3484AA1447B9DF276F94A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('332b0ab6-0c4d-4032-b7f2-a41ed3b84c7c', '12.0508', '12.0508', null, null, 313, 'Institutional Food Workers.', 'kuali.enum.type.cip2000',  '317C5B94F3AC4379A2B3EE7A0AB5DC4C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('32ec8dc8-0a81-4842-b56c-ed515a8bad98', '12.0599', '12.0599', null, null, 314, 'Culinary Arts and Related Services, Other.', 'kuali.enum.type.cip2000',  'DE37D1D09229489690793F6BE8E141BF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9bb96711-47a0-47ce-a7d4-c18235bc50e9', '12.99', '12.99', null, null, 315, 'Personal and Culinary Services, Other.', 'kuali.enum.type.cip2000',  'C092C2B70FC54866A37F132B40C97F8A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('faaf927f-ca0d-4642-9cc1-ebc86266e3b7', '12.9999', '12.9999', null, null, 316, 'Personal and Culinary Services, Other.', 'kuali.enum.type.cip2000',  '5FE3FEB77A534D6380EB2642514DD9C8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2401f259-0d41-496f-a8ba-34d216addbdf', '13.', '13.', null, null, 317, 'EDUCATION.', 'kuali.enum.type.cip2000', '951A9FBA5BAC4BE0B4D3E7FABDC1CA79', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7c80cbf5-953c-4e98-bef3-8b450f54dade', '13.01', '13.01', null, null, 318, 'Education, General.', 'kuali.enum.type.cip2000',  'DE64EFD346B143ACB7C28EA8C200780F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('83825bba-0071-4948-aa21-27a2e3eff547', '13.0101', '13.0101', null, null, 319, 'Education, General.', 'kuali.enum.type.cip2000',  '154E0BF0E90A462D92824CD61AEA3980', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7acd642f-daba-4f30-b307-3ea8996fb2dd', '13.02', '13.02', null, null, 320, 'Bilingual, Multilingual, and Multicultural Education.', 'kuali.enum.type.cip2000',  '40043E231DC74F2F9309DFFF63CB15FE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ae0c20f2-6ed4-4fd4-b774-543c510c0278', '13.0201', '13.0201', null, null, 321, 'Bilingual and Multilingual Education.', 'kuali.enum.type.cip2000',  '54B8192BA3CD40D892331F67B6E2EF0F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9f269017-46da-4da0-a190-0891ea589a88', '13.0202', '13.0202', null, null, 322, 'Multicultural Education.', 'kuali.enum.type.cip2000',  '48D7CE89F76B4B648B505085522A524F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('be5b4be0-4e64-4b3f-9bb1-3e8247cac723', '13.0203', '13.0203', null, null, 323, 'Indian/Native American Education.', 'kuali.enum.type.cip2000',  '08221D3FACE148D9A2924F000D4ABBDF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('65eebb1f-3d9b-416a-88d5-00d12bba3356', '13.0299', '13.0299', null, null, 324, 'Bilingual, Multilingual, and Multicultural Education, Other.',  'kuali.enum.type.cip2000', '139A50E38D514C6192A9D30B8E70A330', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7d192063-6531-4fc9-885e-13bce42626a3', '13.03', '13.03', null, null, 325, 'Curriculum and Instruction.', 'kuali.enum.type.cip2000',  '9C25E3B31AEF4AFBA1A46927E098F522', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('663775a1-decd-423e-b76b-7050f6950077', '13.0301', '13.0301', null, null, 326, 'Curriculum and Instruction.', 'kuali.enum.type.cip2000',  '9062E642ADBF45DAA6B436E546D749C5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8cf3bd13-78ad-4d66-bf77-aba04a58cc3c', '13.04', '13.04', null, null, 327, 'Educational Administration and Supervision.', 'kuali.enum.type.cip2000',  'A0BAE28CCC7F47339BDFAB1F5CF14A5F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('cb053cae-0ac3-4bf5-bca6-220e8c4aac53', '13.0401', '13.0401', null, null, 328, 'Educational Leadership and Administration, General.',  'kuali.enum.type.cip2000', '02999F578F584095BA32B8983DF6044E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5088f2bc-c1c8-45ab-9acb-9b2254a18cd9', '13.0402', '13.0402', null, null, 329, 'Administration of Special Education.', 'kuali.enum.type.cip2000',  'C749681F5BD0438589039081BE787D80', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0a0126fa-e356-47a9-9a73-c788849333fc', '13.0403', '13.0403', null, null, 330, 'Adult and Continuing Education Administration.', 'kuali.enum.type.cip2000',  'EF5B2F04AA944A5F90DA381FA826F938', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('91052d64-0bf9-4697-b6ea-1af50a5f2032', '13.0404', '13.0404', null, null, 331, 'Educational, Instructional, and Curriculum Supervision.',  'kuali.enum.type.cip2000', '2B63777FEB5E45ACBA9E7F2DD6F0F046', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('76a5d73c-f79e-4138-9809-ddb0ad49ccfb', '13.0405', '13.0405', null, null, 332, 'Elementary and Middle and Secondary Education Administration.',  'kuali.enum.type.cip2000', 'ED746F9099D244728F8564624A1A34AE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7c7e2913-920c-4448-9514-6a89313b0383', '13.0406', '13.0406', null, null, 333, 'Higher Education/Higher Education Administration.', 'kuali.enum.type.cip2000',  'C1612FBFD1FF4727AF2F147227186C8F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6b352ea2-7df4-46e5-8eaa-f361ffa0fdb7', '13.0407', '13.0407', null, null, 334, 'Community College Education.', 'kuali.enum.type.cip2000',  '78EABED84A4E4ED3BDF195CBE3DE8EED', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2fe572eb-8382-466f-a7e4-f78ae9a266a4', '13.0408', '13.0408', null, null, 335, 'Elementary and Middle School Administration/Principalship.',  'kuali.enum.type.cip2000', '65991C2A92B74133B536133A728351C3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('cea32fdc-0f96-465b-b9f3-598403d1d0a3', '13.0409', '13.0409', null, null, 336, 'Secondary School Administration/Principalship.', 'kuali.enum.type.cip2000',  '9DE9987B6A64436DB91FF843A3CEADE5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('764c8455-a321-46ac-b5f1-8b5f4e637cd6', '13.0410', '13.0410', null, null, 337, 'Urban Education and Leadership.', 'kuali.enum.type.cip2000',  '7F0D0D04F88240B9BAD39F426B53D538', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b126a7d9-3566-496b-8d4c-0a5b0a71b1e5', '13.0411', '13.0411', null, null, 338, 'Superintendency and Educational System Administration.',  'kuali.enum.type.cip2000', 'C53BECBF59084BADBB32ED1D788AEB18', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f50b80ef-c335-474b-a501-feedcb6bcba2', '13.0499', '13.0499', null, null, 339, 'Educational Administration and Supervision, Other.', 'kuali.enum.type.cip2000',  'AF66F597872C4683916807CEFFA2CDEC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8ae6ca80-6ce0-4bec-8d8d-87251bde53b1', '13.05', '13.05', null, null, 340, 'Educational/Instructional Media Design.', 'kuali.enum.type.cip2000',  'D473080C04314EA3A8A3AD6FAA8D8A4F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6285b144-05fb-4fd6-aa2f-380e89f41f4d', '13.0501', '13.0501', null, null, 341, 'Educational/Instructional Media Design.', 'kuali.enum.type.cip2000',  'C286A2308DD64D038CE109082D4B9F0A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d6b5f618-2ee0-4b93-b8ce-674af33b9b4d', '13.06', '13.06', null, null, 342, 'Educational Assessment, Evaluation, and Research.', 'kuali.enum.type.cip2000',  'DB2770FC8251447ABD23AEFF46B2F68F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('45431697-f7c9-4560-abf6-49ba7b89e211', '45.0201', '45.0201', null, null, 1273, 'Anthropology.', 'kuali.enum.type.cip2000', 'E40DF83589BA42B88E193E48FA380414',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e07ca998-cf15-4c45-9c66-494257c1a676', '45.0202', '45.0202', null, null, 1274, 'Physical Anthropology.', 'kuali.enum.type.cip2000',  '382BC3C656424FB3A94212A996905832', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('560387be-aed4-40e0-86f1-ab85e2c38bf6', '45.0299', '45.0299', null, null, 1275, 'Anthropology, Other.', 'kuali.enum.type.cip2000',  '494307B6A7CC4D27853BA7A9214728EF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('51b4634a-ab34-4e58-a9a5-3c56269e2ecc', '45.03', '45.03', null, null, 1276, 'Archeology.', 'kuali.enum.type.cip2000', '345339F59FD149C8B66087B2F8EBCD46', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ba8fc116-0180-4f36-b58c-0aba8c6798e4', '45.0301', '45.0301', null, null, 1277, 'Archeology.', 'kuali.enum.type.cip2000', '8D1E0BD1FA914BF99C5D85B359C0EB06',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5bf2430d-8e4f-4c8d-9044-9c6d9d0fe270', '45.04', '45.04', null, null, 1278, 'Criminology.', 'kuali.enum.type.cip2000', '58EEC426D8D94E308AAB806B37B181A2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8372145e-9d8c-4531-a8ce-49cd950e0269', '45.0401', '45.0401', null, null, 1279, 'Criminology.', 'kuali.enum.type.cip2000', 'C006E522BF674D0585F3A5A5AAF79172',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a8884140-832b-4654-b7a1-af1aef5f155f', '45.05', '45.05', null, null, 1280, 'Demography and Population Studies.', 'kuali.enum.type.cip2000',  'C022D2983FD5487087BEEEB2F5CC27AB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('eeb98f8e-22da-462b-96c6-b7a047a2ddb2', '45.0501', '45.0501', null, null, 1281, 'Demography and Population Studies.', 'kuali.enum.type.cip2000',  'CC442BE229014D5E9DAF832D891B86D3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6c4b7b79-67f8-4487-a0c8-70730b6cd4d4', '45.06', '45.06', null, null, 1282, 'Economics.', 'kuali.enum.type.cip2000', 'B875D02EA99C40F49F4A9D9E1A5A728D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a7f2c24f-f73a-4620-b52a-13fd209f7c61', '45.0601', '45.0601', null, null, 1283, 'Economics, General.', 'kuali.enum.type.cip2000',  '1EB3DDF3A3CB4AF094609D7B0C2A17E6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a9494263-f5ac-4612-a351-20e6c4cb6aa0', '45.0602', '45.0602', null, null, 1284, 'Applied Economics.', 'kuali.enum.type.cip2000',  'CC65FB04A42942FC9C7FAF2A9B053EBD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6b32ea4a-1b93-4edb-ac9f-0057f8c8d265', '45.0603', '45.0603', null, null, 1285, 'Econometrics and Quantitative Economics.', 'kuali.enum.type.cip2000',  '2125693A0ACE4666BF7A5C91AEC918D8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('57d4f9e5-05ce-434b-a6f9-aff4e47a22f5', '45.0604', '45.0604', null, null, 1286, 'Development Economics and International Development.',  'kuali.enum.type.cip2000', 'B7CB3A1B9C4542EEB7E9B1F4297F168C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('37c782c3-6fb2-4465-a465-43544a461614', '45.0605', '45.0605', null, null, 1287, 'International Economics.', 'kuali.enum.type.cip2000',  '280CC46284604F04B22A74ACDA3E3CA2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e7747e68-b4dd-4cef-aaa6-9f5d553c8a3d', '45.0699', '45.0699', null, null, 1288, 'Economics, Other.', 'kuali.enum.type.cip2000',  '516FBD37FAB74055B08BE8F30244A19B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5926f80e-5d55-4624-8fcc-8e22f7087784', '45.07', '45.07', null, null, 1289, 'Geography and Cartography.', 'kuali.enum.type.cip2000',  'B4D8D901ACFA4EC38B2401345EA60636', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6cf50ab3-5e14-4a73-8b8e-6bd8f7eeb0fa', '45.0701', '45.0701', null, null, 1290, 'Geography.', 'kuali.enum.type.cip2000', '6F703C8E06B441FF8E1A81D5569413DB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2e55bdad-4122-4643-82cb-d09033d20b95', '45.0702', '45.0702', null, null, 1291, 'Cartography.', 'kuali.enum.type.cip2000', '0114648D697B423D99FF2ECD026CFEE4',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('28188666-89ba-4b07-9fad-043731c63ae5', '45.0799', '45.0799', null, null, 1292, 'Geography, Other.', 'kuali.enum.type.cip2000',  'C83B190095614EEE8D390D6CBB753B63', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f1ad48ff-e43e-479f-ab1f-5593c6a1342b', '45.08', '45.08', null, null, 1293, 'History.', 'kuali.enum.type.cip2000', 'BCDAD18EA21245ECAFACAFC29115D5C3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('64971e14-4b2f-48d4-8c9b-28450f26f3b3', '45.0801', '45.0801', null, null, 1294, 'History, General.', 'kuali.enum.type.cip2000',  '9D61E6113A6342EAA96879AD790D5672', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0d6fb184-bfbe-44ed-b6f5-68248a3f7bc4', '45.0802', '45.0802', null, null, 1295, 'American (United States) History.', 'kuali.enum.type.cip2000',  'A28F8FE8AB14479D836BD08F32321EC9', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('59dfeff1-a3e9-4766-9d04-4218a1b667e7', '45.0803', '45.0803', null, null, 1296, 'European History.', 'kuali.enum.type.cip2000',  'B987A2EC4C164685BF4764C1FCB737A0', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5574d6c1-a5cc-4a78-a8f6-a8e5ef4d58b5', '45.0804', '45.0804', null, null, 1297, 'History and Philosophy of Science and Technology.', 'kuali.enum.type.cip2000',  '5379A092A70C4EB4B0812239098583D8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('51a35a43-245d-4b17-8585-0a073c60130b', '45.0805', '45.0805', null, null, 1298, 'Public/Applied History and Archival Administration.',  'kuali.enum.type.cip2000', 'DF79B1B752FC43ACABB63EE74A47EFBC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('42c6c954-566c-463a-b66c-1ebde4a2550e', '45.0899', '45.0899', null, null, 1299, 'History, Other.', 'kuali.enum.type.cip2000',  '0637D70B473247CFACF4F7A6188A0756', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d0c80035-a801-4148-8a47-7ba7a10f29f6', '45.09', '45.09', null, null, 1300, 'International Relations and Affairs.', 'kuali.enum.type.cip2000',  'FCD889CAD1AD473F9044ED797D73BED0', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ba3cd3ba-1373-4165-8661-716708951f6e', '45.0901', '45.0901', null, null, 1301, 'International Relations and Affairs.', 'kuali.enum.type.cip2000',  'BC33837DD71241A098F86207356C2EF4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('207aab2b-b0c9-4064-a83b-0ba8865f05c7', '45.10', '45.10', null, null, 1302, 'Political Science and Government.', 'kuali.enum.type.cip2000',  '18B69B3FF96943E485FB055EB8354875', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b1e6dab7-0442-4818-9ac3-adb8d5fc478f', '45.1001', '45.1001', null, null, 1303, 'Political Science and Government, General.', 'kuali.enum.type.cip2000',  '95EFA0125B594767B69FB3DF408ADB34', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('116145a2-bdca-4155-9e41-23bcd3b6161f', '45.1002', '45.1002', null, null, 1304, 'American Government and Politics (United States).', 'kuali.enum.type.cip2000',  '03CDF97CDC164E9E8F9C032CE53B5BC2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('16381c67-8ce0-4985-8756-bc00afe2ec44', '45.1003', '45.1003', null, null, 1305, 'Canadian Government and Politics.', 'kuali.enum.type.cip2000',  'D200C470D12C48F1BA6B7A15CF3FFE28', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3e98649d-f0c7-4200-a5fc-37f6934700d4', '45.1099', '45.1099', null, null, 1306, 'Political Science and Government, Other.', 'kuali.enum.type.cip2000',  'D76E26A5360540D9A338A05A5E8EB3A2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3f61bd06-8d36-4fcc-990f-60c3687b2e64', '45.11', '45.11', null, null, 1307, 'Sociology.', 'kuali.enum.type.cip2000', '39CD1FDEA2C14ADAB1C2A29A5171C31C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e4f3b1e1-239b-49d1-b21f-513d24af26d0', '45.1101', '45.1101', null, null, 1308, 'Sociology.', 'kuali.enum.type.cip2000', '4EE086836BCD44C69C399EBAC37950DC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ad030022-4939-4032-9581-d955fb2f63ca', '45.12', '45.12', null, null, 1309, 'Urban Studies/Affairs.', 'kuali.enum.type.cip2000',  'B84729BA106141B7BD43B6D55A4509E6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f2219aa6-ca59-49a5-8a85-dab4ad2de611', '45.1201', '45.1201', null, null, 1310, 'Urban Studies/Affairs.', 'kuali.enum.type.cip2000',  '38240156F3D64B02B7B3144A5611D3DD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bfa9367f-0fb1-4349-bdb5-bf902b5cb88d', '45.99', '45.99', null, null, 1311, 'Social Sciences, Other.', 'kuali.enum.type.cip2000',  '8AA6FB4F75D64117B7181535BC85F2F2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f9daee4a-91f6-4ab9-a078-b1dd5b0c04ce', '45.9999', '45.9999', null, null, 1312, 'Social Sciences, Other.', 'kuali.enum.type.cip2000',  '9B7B6B99C8034A8B8407873BE327E79E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9824c802-8ce3-45bd-a57e-7e1039c80895', '46.', '46.', null, null, 1313, 'CONSTRUCTION TRADES.', 'kuali.enum.type.cip2000', '495E18F809C340E88C1414EC8950A90B',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3239e9ab-a4fa-46c8-9bde-1ec13f4d5d4d', '46.00', '46.00', null, null, 1314, 'Construction Trades, General.', 'kuali.enum.type.cip2000',  '2CEE2C76C7E840BC98FE096F41E22C3E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0328eb66-2161-4ac0-9681-6259906973ac', '46.0000', '46.0000', null, null, 1315, 'Construction Trades, General.', 'kuali.enum.type.cip2000',  'DEE51DADD20D4CD29783E3367DDE9ACF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('270612a4-5db6-4cec-aee5-9d2edc5ed2d1', '46.01', '46.01', null, null, 1316, 'Mason/Masonry.', 'kuali.enum.type.cip2000', '8AD51529387046D1B8B608F469F66A41', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4e1c5e9d-acd7-436f-9e1b-d5e09fb50e83', '46.0101', '46.0101', null, null, 1317, 'Mason/Masonry.', 'kuali.enum.type.cip2000',  '23F2EA1F5B22465280AB3BCEB9C75DA4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5b470567-3618-4a75-bd5e-631aee9dbc4f', '46.02', '46.02', null, null, 1318, 'Carpenters.', 'kuali.enum.type.cip2000', '2C9401E8B464495C8293D86E219AEBB5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('cd5f40c7-2876-417b-87ff-8e8db5c1ed5e', '46.0201', '46.0201', null, null, 1319, 'Carpentry/Carpenter.', 'kuali.enum.type.cip2000',  '2908479F9E734E18AF79A68E9F856C57', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b25c031d-ae65-46c5-b0db-4282d17e644a', '46.03', '46.03', null, null, 1320, 'Electrical and Power Transmission Installers.', 'kuali.enum.type.cip2000',  'FFC6E9080B894EB9A0E87D473756B63E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('726480b2-6bf9-4fba-a57c-e88e63d805a9', '46.0301', '46.0301', null, null, 1321, 'Electrical and Power Transmission Installation/Installer, General.',  'kuali.enum.type.cip2000', '0DCF72C1F29B480888439B242B5D4712', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('33cfbf47-1aa9-4618-905e-7b4f5ca1e343', '46.0302', '46.0302', null, null, 1322, 'Electrician.', 'kuali.enum.type.cip2000', 'D8A59C9E980D461FAF54AD9D4BB33BA3',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7cd8117e-cbf1-4d79-a0af-cd8333775c3e', '46.0303', '46.0303', null, null, 1323, 'Lineworker.', 'kuali.enum.type.cip2000', '27CDF89211CB4BE49C5BD5A88A15D5DA',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('47c8285d-0406-4890-85dc-e90f32e6bef4', '46.0399', '46.0399', null, null, 1324, 'Electrical and Power Transmission Installers, Other.',  'kuali.enum.type.cip2000', 'E0034820D49B40CAAC2C7245B9D04FAA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7b25a194-0d17-4835-992a-8011b648afcc', '46.04', '46.04', null, null, 1325, 'Building/Construction Finishing, Management, and Inspection.',  'kuali.enum.type.cip2000', 'C873F6E0EA704D1BA8C90C4E9ABCFE7D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('65add096-2640-4767-acc6-7d4103e9d30b', '46.0401', '46.0401', null, null, 1326, 'Building/Property Maintenance and Management.', 'kuali.enum.type.cip2000',  '36BD63AE928A47848AE7D476DBF601B9', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1f5f0c02-273e-4035-8b9b-14338b7973f2', '46.0402', '46.0402', null, null, 1327, 'Concrete Finishing/Concrete Finisher.', 'kuali.enum.type.cip2000',  'B4B7221956604FAB839338CC9238588B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c8a50cc4-2a2e-4014-aaf5-2440caf4be67', '46.0403', '46.0403', null, null, 1328, 'Building/Home/Construction Inspection/Inspector.', 'kuali.enum.type.cip2000',  'E08979AC902D4080AB4BA1E35AABB421', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c806f5f7-973e-4709-9660-2daae1280f71', '46.0404', '46.0404', null, null, 1329, 'Drywall Installation/Drywaller.', 'kuali.enum.type.cip2000',  '8482D2E704DD4AAF8F1E3B9D033B1BC2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('02928025-56b9-4a63-bdd9-9cf2785e7f65', '46.0406', '46.0406', null, null, 1330, 'Glazier.', 'kuali.enum.type.cip2000', '46A22891C27C4A4690294606D48D13A0', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('44805827-1c11-4ac1-98d5-d422b6cfaa84', '46.0408', '46.0408', null, null, 1331, 'Painting/Painter and Wall Coverer.', 'kuali.enum.type.cip2000',  '15295F4E112B48A8816BA0D671AEE45A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ebf4e628-af33-40e4-b016-0d2496426cd0', '46.0410', '46.0410', null, null, 1332, 'Roofer.', 'kuali.enum.type.cip2000', '5088EA23A94547FDB5636720AB035285', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3da03e71-9084-4b02-86aa-78c0dd8cb0c6', '46.0411', '46.0411', null, null, 1333, 'Metal Building Assembly/Assembler.', 'kuali.enum.type.cip2000',  'A070F2756248454885446803CA84C4DB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('20c2be86-aedb-4f14-a45f-6077ad7a1960', '46.0412', '46.0412', null, null, 1334, 'Building/Construction Site Management/Manager.', 'kuali.enum.type.cip2000',  '4A2BE8FA0B554E3DA84137DB00957EFD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('80fee899-a3b2-4db9-8515-cf0b0381abfa', '46.0499', '46.0499', null, null, 1335, 'Building/Construction Finishing, Management, and Inspection, Other.',  'kuali.enum.type.cip2000', '4A363C0B85154B1EAA357F0AE61A2442', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f2a1299e-61cf-411a-b6a3-d60ee4789022', '46.05', '46.05', null, null, 1336, 'Plumbing and Related Water Supply Services.', 'kuali.enum.type.cip2000',  '4277F4EC2BBD41ABB88CDFC83B1BF40B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('60751b88-1748-41bb-a9d5-b2df99be874e', '46.0501', '46.0501', null, null, 1337, 'Plumber and Pipefitter.', 'kuali.enum.type.cip2000',  '716A5ED4A0C14736A14849455D515102', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bdf99dc9-9560-4bad-bf2e-831732032067', '46.0502', '46.0502', null, null, 1338, 'Pipefitting/Pipefitter and Sprinkler Fitter.', 'kuali.enum.type.cip2000',  '1BF31671C81E49628A0868AA08D491FD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('553bb204-36f2-4ed2-9b8c-52c11fd14d66', '46.0503', '46.0503', null, null, 1339, 'Plumbing Technology/Plumber.', 'kuali.enum.type.cip2000',  'ADB158B9323648068FB44D82F7E2C9A5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b97aaa70-4e68-4171-a39e-b48b4b67306b', '46.0504', '46.0504', null, null, 1340, 'Well Drilling/Driller.', 'kuali.enum.type.cip2000',  '6DAFDDCA90254BDCADB121D48D2D93BA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1a5396de-30ec-44d9-80f0-68f39f02c11d', '46.0505', '46.0505', null, null, 1341, 'Blasting/Blaster.', 'kuali.enum.type.cip2000',  '43EA865AEA394ECFB9EF5ACFD03A612E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('757d4b8d-a7d8-4feb-97c1-7881e12871e8', '46.0599', '46.0599', null, null, 1342, 'Plumbing and Related Water Supply Services, Other.',  'kuali.enum.type.cip2000', 'CDAD8CF3B1C143E59141279B2DE6BC98', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b048de29-98b8-4944-ac76-68cac14b368a', '46.99', '46.99', null, null, 1343, 'Construction Trades, Other.', 'kuali.enum.type.cip2000',  '494D27AC203846FCB7ED829472DFFFDD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('07fd041a-61a9-4a3c-a34a-e3c9fd01afd0', '46.9999', '46.9999', null, null, 1344, 'Construction Trades, Other.', 'kuali.enum.type.cip2000',  '2E81249393D94F53866F525005B92887', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('58afa4e2-501b-4c2e-8df3-3700a5d0b608', '47.', '47.', null, null, 1345, 'MECHANIC AND REPAIR TECHNOLOGIES/TECHNICIANS.', 'kuali.enum.type.cip2000',  '3BEDBB9CD3F543338DF7EE415647562E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4240fad8-3ae2-4db8-b451-4898a5f7976c', '47.00', '47.00', null, null, 1346, 'Mechanics and Repairers, General.', 'kuali.enum.type.cip2000',  '5C9D4AD3793F4D5FB0ACA493B631C153', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('593e9e37-1fc9-463e-8fda-d1e2004f0fe1', '47.0000', '47.0000', null, null, 1347, 'Mechanics and Repairers, General.', 'kuali.enum.type.cip2000',  'D674616BA38A471D9EB991BB7E806826', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('aec56f05-cb07-452b-a5b6-a47ef356decb', '47.01', '47.01', null, null, 1348, 'Electrical/Electronics Maintenance and Repair Technology.',  'kuali.enum.type.cip2000', '5561C837B1AB42A3B7E5462C4FD76384', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('204bce4d-ea8f-4f92-89a0-739be881c43f', '47.0101', '47.0101', null, null, 1349, 'Electrical/Electronics Equipment Installation and Repair, General.',  'kuali.enum.type.cip2000', '9BFA7CD1EE404B9CAF1C7B3E87A67E82', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1edbec8e-0b1d-4339-8b2e-ab5cf48cdd94', '47.0102', '47.0102', null, null, 1350, 'Business Machine Repair.', 'kuali.enum.type.cip2000',  'E7B1F7293F3749F8A204E33061BF58D0', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d6614f98-8c43-4fe1-85dd-a312475241bb', '47.0103', '47.0103', null, null, 1351, 'Communications Systems Installation and Repair Technology.',  'kuali.enum.type.cip2000', '7C037734BC5B45B5AB1DC4615B4A200B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6c05103d-3f0e-481b-beb3-c58030f1dc28', '47.0104', '47.0104', null, null, 1352, 'Computer Installation and Repair Technology/Technician.',  'kuali.enum.type.cip2000', '20645CAD562644C08434757541F7D20E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('fbe58a3f-d57b-4ae2-9456-8b88779e44f1', '47.0105', '47.0105', null, null, 1353, 'Industrial Electronics Technology/Technician.', 'kuali.enum.type.cip2000',  'EAB1F7CB53424D34AD330FE282F7E95E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1437235b-86d3-414e-8bb3-56246fb6c597', '47.0106', '47.0106', null, null, 1354, 'Appliance Installation and Repair Technology/Technician.',  'kuali.enum.type.cip2000', '02B8A22EC91645A396E809FD8D4726E6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('041a90a0-fed2-4c0d-86fe-9213000a41fd', '47.0110', '47.0110', null, null, 1355, 'Security System Installation, Repair, and Inspection Technology/Technician.',  'kuali.enum.type.cip2000', '1F72C21037134BF9B76AE82286F8B017', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ea35f30f-2a6b-4b5c-8792-a3aa36f849af', '47.0199', '47.0199', null, null, 1356, 'Electrical/Electronics Maintenance and Repair Technology, Other.',  'kuali.enum.type.cip2000', '02E258BD68A04CA08F56A5DA81E1CF9F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6e328f67-0bfe-426f-b29c-2ffb30ec3c09', '47.02', '47.02', null, null, 1357, 'Heating, Air Conditioning, Ventilation and Refrigeration Maintenance  Technology/Technician (HAC, HACR, HVAC, HVACR).', 'kuali.enum.type.cip2000', '107FB3542D1144068EE74D0FF2375536', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c0ea6f1d-fdb3-431d-8743-da6603b3172c', '47.0201', '47.0201', null, null, 1358, 'Heating, Air Conditioning, Ventilation and Refrigeration Maintenance  Technology/Technician (HAC, HACR, HVAC, HVACR).', 'kuali.enum.type.cip2000', '961317A690AC4630953A1AD25371F578', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('fcc1ce2c-d654-4b5b-9ef0-646b4e535862', '47.03', '47.03', null, null, 1359, 'Heavy/Industrial Equipment Maintenance Technologies.', 'kuali.enum.type.cip2000',  'F1B020FE63634474956393F1E0561BFF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('248c6a5f-6c67-491d-a96c-ea75932d0ee4', '47.0302', '47.0302', null, null, 1360, 'Heavy Equipment Maintenance Technology/Technician.',  'kuali.enum.type.cip2000', '6D5E76BF9CB642E39006340297DF3FCD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2313bc78-c486-4f54-805b-6ba976f80255', '47.0303', '47.0303', null, null, 1361, 'Industrial Mechanics and Maintenance Technology.', 'kuali.enum.type.cip2000',  'E6587F0AD6134F8C972660A8E88A6931', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('aa6799ac-9af3-46ac-bcd8-43e930c5c376', '47.0399', '47.0399', null, null, 1362, 'Heavy/Industrial Equipment Maintenance Technologies, Other.',  'kuali.enum.type.cip2000', '70A2156F6E854C53A54B7D243B893DEF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1935640b-9b0d-409b-8d2d-1383beac1c3a', '47.04', '47.04', null, null, 1363, 'Precision Systems Maintenance and Repair Technologies.',  'kuali.enum.type.cip2000', 'F0A0CAE31AA1494E8DE5315EAB1C08EE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('db5724fa-ddc9-4e25-8b56-1a9266a6966b', '47.0401', '47.0401', null, null, 1364, 'Instrumentation Calibration and Repair.', 'kuali.enum.type.cip2000',  '50DDCD5E3F344C47A2065E3DC3E99328', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f0abffa8-ea29-4eb4-ac1b-2721a8950f44', '47.0402', '47.0402', null, null, 1365, 'Gunsmithing/Gunsmith.', 'kuali.enum.type.cip2000',  'E46ED672A9444146885809F76AA2F964', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f02be553-f354-4463-81ce-719aa71faa44', '47.0403', '47.0403', null, null, 1366, 'Locksmithing and Safe Repair.', 'kuali.enum.type.cip2000',  '2430792AF0214A84A8D9671DB5F51642', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d547ca58-01b2-416a-8568-0b67f5f60167', '47.0404', '47.0404', null, null, 1367, 'Musical Instrument Fabrication and Repair.', 'kuali.enum.type.cip2000',  'DE9F4AB172E649A4A28AB07A9436D522', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('40ebb529-3c54-4cfb-9722-50f9c7856f7c', '47.0408', '47.0408', null, null, 1368, 'Watchmaking and Jewelrymaking.', 'kuali.enum.type.cip2000',  '88C4A8EFDB5841489EDF0B7B916436EA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('86bb224a-4b8f-4da4-91c6-ac0f48a54f5a', '47.0409', '47.0409', null, null, 1369, 'Parts and Warehousing Operations and Maintenance Technology/Technician.',  'kuali.enum.type.cip2000', '7808AF0FA2FB4FE9A421F7F501B77FBB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e5726467-34b5-4f39-bbee-e87ad893e2d3', '47.0499', '47.0499', null, null, 1370, 'Precision Systems Maintenance and Repair Technologies, Other.',  'kuali.enum.type.cip2000', '423C3F8CC01A441081ED4B164B5A939A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('cbb3b574-5dcf-4b71-9a89-69505709c8ca', '47.05', '47.05', null, null, 1371, 'Stationary Energy Sources Installers and Operators.', 'kuali.enum.type.cip2000',  'E03BD0E2C95048008D08348E69DC6C4F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8ff4ac42-23ef-4376-877a-9971896bbafc', '47.0501', '47.0501', null, null, 1372, 'Stationary Energy Sources Installer and Operator.', 'kuali.enum.type.cip2000',  '3F15F47880AE491392FD22B215255A8F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e78dfa05-154e-492e-a976-d69efb6c69d1', '47.06', '47.06', null, null, 1373, 'Vehicle Maintenance and Repair Technologies.', 'kuali.enum.type.cip2000',  '75C483CF920E487A8E94A66F4AC9E110', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('938771ad-bc3f-40fb-bc36-5a579b45858a', '47.0603', '47.0603', null, null, 1374, 'Autobody/Collision and Repair Technology/Technician.',  'kuali.enum.type.cip2000', 'F6FB2620B5064AEAB5DF147958FA7BC2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8a6427a8-e456-4ba6-af02-68f369ba78ce', '47.0604', '47.0604', null, null, 1375, 'Automobile/Automotive Mechanics Technology/Technician.',  'kuali.enum.type.cip2000', '5B115BABB3574739BC6A0CAB745A4BC7', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('da5277af-fb2b-459a-bfad-d0732e8fc9a3', '47.0605', '47.0605', null, null, 1376, 'Diesel Mechanics Technology/Technician.', 'kuali.enum.type.cip2000',  'BA4B91CF4B3F4F00AF64CD7D0248B9F8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('23e9a91d-f504-481c-ba50-076297f81605', '47.0606', '47.0606', null, null, 1377, 'Small Engine Mechanics and Repair Technology/Technician.',  'kuali.enum.type.cip2000', '6FC71BD28E8645CF98FA0DEE030E54B1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2021832d-e4ec-49d5-aebd-f99378cecafc', '47.0607', '47.0607', null, null, 1378, 'Airframe Mechanics and Aircraft Maintenance Technology/Technician.',  'kuali.enum.type.cip2000', 'B5BFC3987A9B41669A634CAC7187E8F1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('77458943-6ce9-415c-b4ef-3b14996c0779', '47.0608', '47.0608', null, null, 1379, 'Aircraft Powerplant Technology/Technician.', 'kuali.enum.type.cip2000',  '72820E9B058041639AA36B0CDC36FC60', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('53e6cf8f-bbc4-48bd-8257-e5fbcff8a04e', '47.0609', '47.0609', null, null, 1380, 'Avionics Maintenance Technology/Technician.', 'kuali.enum.type.cip2000',  '64107F7BD10446E19EDF4AAB2D8E06C8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5382e3ef-96f3-44bc-9390-4a91fbcd0407', '47.0610', '47.0610', null, null, 1381, 'Bicycle Mechanics and Repair Technology/Technician.',  'kuali.enum.type.cip2000', 'E8D3DCB533A0477D9CA32F256FDD196C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ac3a2c2a-3792-44b1-a747-d8555eab6ffc', '47.0611', '47.0611', null, null, 1382, 'Motorcycle Maintenance and Repair Technology/Technician.',  'kuali.enum.type.cip2000', '22E9944E925744C1BCF10DE705C00EE1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2d0d62d0-9965-48e7-aba8-be9fbb694d17', '47.0612', '47.0612', null, null, 1383, 'Vehicle Emissions Inspection and Maintenance Technology/Technician.',  'kuali.enum.type.cip2000', '0329BA3556F2421D90935852997E5BD1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('86a08ada-4c5a-4d52-b0d5-1c7213ab6c90', '47.0613', '47.0613', null, null, 1384, 'Medium/Heavy Vehicle and Truck Technology/Technician.',  'kuali.enum.type.cip2000', '078E93E51BF3408E93150F9250E88542', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3a09f3fe-1e92-4ac8-a6e9-3c9152767162', '47.0614', '47.0614', null, null, 1385, 'Alternative Fuel Vehicle Technology/Technician.', 'kuali.enum.type.cip2000',  '138DD4917F8A459E980949C60AA4001B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d818520c-0df2-4824-b54b-92cf1e48ed12', '47.0615', '47.0615', null, null, 1386, 'Engine Machinist.', 'kuali.enum.type.cip2000',  'FA93261BD80C4629BAFD6225B6C29A95', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('be0fcb34-d68b-4231-a892-b0854cfc841e', '47.0616', '47.0616', null, null, 1387, 'Marine Maintenance/Fitter and Ship Repair Technology/Technician.',  'kuali.enum.type.cip2000', '9AF57F5127A04537A6AEE916894C6608', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c4517e4b-b2cd-46ff-aa2c-95c328c97867', '47.0699', '47.0699', null, null, 1388, 'Vehicle Maintenance and Repair Technologies, Other.',  'kuali.enum.type.cip2000', 'DFB7361B42F349819EC3B54823346F89', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('94255fc6-8916-40c7-b502-8fe284a275a8', '47.99', '47.99', null, null, 1389, 'Mechanic and Repair Technologies/Technicians, Other.', 'kuali.enum.type.cip2000',  '3BFCC32900DF496192146574FC55DEB0', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6d901a73-14de-47e8-8c5e-1878d6dfa711', '47.9999', '47.9999', null, null, 1390, 'Mechanic and Repair Technologies/Technicians, Other.',  'kuali.enum.type.cip2000', '9AE6BB4E756A4786921FF67F2DB5DCF1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('420ee47c-b155-44a1-9aa3-39358938278c', '48.', '48.', null, null, 1391, 'PRECISION PRODUCTION.', 'kuali.enum.type.cip2000', '8514867B565D4C6485BC1BF60CD1AE37',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('13338e6d-94bb-4b70-8e44-27a976d698e6', '48.00', '48.00', null, null, 1392, 'Precision Production Trades, General.', 'kuali.enum.type.cip2000',  '14570904DB8744C4ABC04549EAE8DDD9', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('441cdcc5-3310-4b03-a79a-f9ba13c5a45a', '48.0000', '48.0000', null, null, 1393, 'Precision Production Trades, General.', 'kuali.enum.type.cip2000',  'D1A93E65321A4E2FB0BA27A048377C52', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6266d079-3ca5-4e9c-9683-9b3ee093c1d7', '48.01', '48.01', null, null, 1394, 'Drafting.', 'kuali.enum.type.cip2000', '76D00C7263994981B161957F183DBF78', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('99a440f1-03e1-442c-86b2-1920962c2312', '48.0101', '48.0101', null, null, 1395, 'Drafting, General.', 'kuali.enum.type.cip2000',  '8FB1125EBFDF4FF1A47005FB28A7CE2E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3a153e6f-148d-439f-91d9-df99c42dc3e4', '48.0102', '48.0102', null, null, 1396, 'Architectural Drafting.', 'kuali.enum.type.cip2000',  'B118CBBBB2364B3EA919A2868F0F7BF9', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3fc60e8b-cf86-4d0e-9a45-299730aade01', '48.0103', '48.0103', null, null, 1397, 'Civil/Structural Drafting.', 'kuali.enum.type.cip2000',  '2159536BF6C94828B5AE7B245A28CFDB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('074c8524-0054-4a72-a8a2-3bc97b296172', '48.0104', '48.0104', null, null, 1398, 'Electrical/Electronics Drafting.', 'kuali.enum.type.cip2000',  'BD6FB0F3FE344D3FA4439F1B3C5FEAD3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('360a24ed-70bb-480d-a729-abfc6d694d3c', '48.0105', '48.0105', null, null, 1399, 'Mechanical Drafting.', 'kuali.enum.type.cip2000',  'EF2A351305724313A0966C48C9EEB71B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c4eb535f-bb0a-468f-82b8-39b7e887373e', '48.0199', '48.0199', null, null, 1400, 'Drafting, Other.', 'kuali.enum.type.cip2000',  '2B48D227736441C0A07304857FC25672', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('812c77b8-c39a-47dd-8eb2-7365e15f1146', '48.02', '48.02', null, null, 1401, 'Graphic and Printing Equipment Operators.', 'kuali.enum.type.cip2000',  '8BD8EE95CF6B4B5293D93CB15A691FB8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a0f4757f-bd3a-443f-9399-1a5ca30237eb', '48.0201', '48.0201', null, null, 1402, 'Graphic and Printing Equipment Operator, General.', 'kuali.enum.type.cip2000',  '2CA176B63E354732B2182E0BDC15BD34', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5c302e7d-9bc9-45ca-bac8-62cb406c1bc4', '48.0205', '48.0205', null, null, 1403, 'Mechanical Typesetter and Composer.', 'kuali.enum.type.cip2000',  '32D501A1477743CC8C362C31A9A7C626', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('eab0110b-c74b-418f-8df7-6c05c9cf38ab', '48.0206', '48.0206', null, null, 1404, 'Lithographer and Platemaker.', 'kuali.enum.type.cip2000',  '98228F49A5234581A3F47B3E01661244', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('82421756-c0fc-4cce-99df-09b793bdf26d', '48.0208', '48.0208', null, null, 1405, 'Printing Press Operator.', 'kuali.enum.type.cip2000',  'CD4CFD67C78D4AFBB10BB24DDFCAC678', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('35b22cd6-252a-4511-b3aa-cef4c75264dd', '48.0211', '48.0211', null, null, 1406, 'Computer Typography and Composition Equipment Operator.',  'kuali.enum.type.cip2000', '3C2062D296B74494B5A98FF7C3E4C415', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2f02b97a-6702-46bf-86a0-0fd76f8f78a5', '48.0212', '48.0212', null, null, 1407, 'Desktop Publishing Equipment Operator.', 'kuali.enum.type.cip2000',  '95FE3F710BFF46C5B0D4DE476E2B8A27', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5cee09bb-4150-4c18-b7a4-4cc529414f28', '48.0299', '48.0299', null, null, 1408, 'Graphic and Printing Equipment Operators, Other.', 'kuali.enum.type.cip2000',  '123747C4C9F04FB7AF988526B64AC2BA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b9b936ae-1c44-4f21-b10f-6919f7035b8b', '48.03', '48.03', null, null, 1409, 'Leatherworking and Upholstery.', 'kuali.enum.type.cip2000',  '83F1F89CE124486AB4F432CD36F82BEF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('cc6328e5-bd2d-4348-8aa6-000cb3dbbc46', '48.0303', '48.0303', null, null, 1410, 'Upholstery/Upholsterer.', 'kuali.enum.type.cip2000',  'F0705D50B9A54668954A02CAA1CD8C62', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('84d16d52-82fa-47a3-9bf1-eb272bb9354a', '48.0304', '48.0304', null, null, 1411, 'Shoe, Boot and Leather Repair.', 'kuali.enum.type.cip2000',  '656DE7DC0FFD42E29E55F6FF98A07DE9', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f006aa4e-c73d-4f63-a211-b8a4e8043692', '48.0399', '48.0399', null, null, 1412, 'Leatherworking and Upholstery, Other.', 'kuali.enum.type.cip2000',  '9231EA3429E84EA59FC780E630138CA7', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d057f2c4-2677-4d39-ac54-3c0f835b2149', '48.05', '48.05', null, null, 1413, 'Precision Metal Working.', 'kuali.enum.type.cip2000',  'C8CB84757FE54E24AFE92E72FC9358D1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ddc3928c-8985-49f9-b49d-f439dee4fa41', '48.0501', '48.0501', null, null, 1414, 'Machine Tool Technology/Machinist.', 'kuali.enum.type.cip2000',  'D3323339702B446A9E29A28F937E571B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3f6840e0-402e-49c4-b845-61c9dd0c754d', '48.0503', '48.0503', null, null, 1415, 'Machine Shop Technology/Assistant.', 'kuali.enum.type.cip2000',  '52C67FEC53C14902A05669BFB03D3BF7', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8405f23b-820e-4e5f-9429-88f61a468c4a', '48.0506', '48.0506', null, null, 1416, 'Sheet Metal Technology/Sheetworking.', 'kuali.enum.type.cip2000',  '3850332DCEB342D7860DD4265060094D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d726343c-e8ae-42bf-a4fa-4c819f20d3c0', '48.0507', '48.0507', null, null, 1417, 'Tool and Die Technology/Technician.', 'kuali.enum.type.cip2000',  '05DD360DECC04763922231DE1C326947', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4b93b674-ea79-444a-ac7e-d8f9adbccbb6', '48.0508', '48.0508', null, null, 1418, 'Welding Technology/Welder.', 'kuali.enum.type.cip2000',  '796DED2FC11B4ABB93F18EE1D13A87FB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c0828cc3-5c77-453b-b960-e87f80b35f7e', '48.0509', '48.0509', null, null, 1419, 'Ironworking/Ironworker.', 'kuali.enum.type.cip2000',  '8535BAF7E13B4E19A4E4ACF5360B77E6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c612c706-1c1f-4263-9904-15aa8a7556f6', '48.0599', '48.0599', null, null, 1420, 'Precision Metal Working, Other.', 'kuali.enum.type.cip2000',  '39F5EFECA79549EE829998168DBF9926', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f446c645-b58e-49e0-b838-25f15af57fd0', '48.07', '48.07', null, null, 1421, 'Woodworking.', 'kuali.enum.type.cip2000', 'A61CD9498CAC4940A396ADBA869DD62C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('971db7b5-cace-4222-9ed6-499091f30229', '48.0701', '48.0701', null, null, 1422, 'Woodworking, General.', 'kuali.enum.type.cip2000',  'AAD57C7B8D93450B8585A0374F30F430', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('02b88655-3945-486d-a570-1007181687bb', '48.0702', '48.0702', null, null, 1423, 'Furniture Design and Manufacturing.', 'kuali.enum.type.cip2000',  'CCB18C00EAB04458BBA64DD81B9BD545', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c6e33370-ebfb-43dd-a1f8-d9039f0f0def', '48.0703', '48.0703', null, null, 1424, 'Cabinetmaking and Millwork/Millwright.', 'kuali.enum.type.cip2000',  'EE75914959CC4FFCA0DD879047B6D803', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('025d72b4-415d-49a8-969f-cb1a0c4775fb', '48.0799', '48.0799', null, null, 1425, 'Woodworking, Other.', 'kuali.enum.type.cip2000',  'AE3C09E54175429586D94B83873805FE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3f78ef65-0da2-451c-8f88-f9c8d4bc4ded', '48.08', '48.08', null, null, 1426, 'Boilermaking/Boilermaker.', 'kuali.enum.type.cip2000',  '47F753AF66E9478F8CB225F68B4F1C61', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5caf7ebe-847a-4efc-9beb-65f164f1a7f9', '48.0801', '48.0801', null, null, 1427, 'Boilermaking/Boilermaker.', 'kuali.enum.type.cip2000',  '89A244D5EEFA4960A47320440842E6D6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4d10029b-f0aa-45e9-b3cb-10e9bb10a696', '48.99', '48.99', null, null, 1428, 'Precision Production, Other.', 'kuali.enum.type.cip2000',  '9B44E890D2C24790AA4D8F91BFE49F53', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('876a0803-07b0-4911-8628-ced4a73435bd', '48.9999', '48.9999', null, null, 1429, 'Precision Production, Other.', 'kuali.enum.type.cip2000',  '6DB810A3521348EE9DC8E6A763329104', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e7cd0aa7-90ac-42a3-a316-084f6fd54815', '49.', '49.', null, null, 1430, 'TRANSPORTATION AND MATERIALS MOVING.', 'kuali.enum.type.cip2000',  '903182956BB442128CA378743A8F52BB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a67f1e8f-e7f8-4beb-b133-0c0cb8a81a6f', '49.01', '49.01', null, null, 1431, 'Air Transportation.', 'kuali.enum.type.cip2000',  'B409651AEF744C5CA473F7C4A89C0959', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('802fd98a-32e8-436d-9ebb-65f4d08c62d4', '49.0101', '49.0101', null, null, 1432, 'Aeronautics/Aviation/Aerospace Science and Technology, General.',  'kuali.enum.type.cip2000', 'DC8843F48ACF497A8E1D47E463A27DAF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bc3ad088-9bce-4915-b741-7d334800e325', '49.0102', '49.0102', null, null, 1433, 'Airline/Commercial/Professional Pilot and Flight Crew.',  'kuali.enum.type.cip2000', '3A3D9D45E7614706835B3DC9467EDE14', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('284ad2f0-3188-4704-9e41-60abe136cef2', '49.0104', '49.0104', null, null, 1434, 'Aviation/Airway Management and Operations.', 'kuali.enum.type.cip2000',  '3FF1FF8D10244A37B5B03AA7C1B09A9A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d9a3353b-679f-4ef0-afa7-2b998cb52f25', '49.0105', '49.0105', null, null, 1435, 'Air Traffic Controller.', 'kuali.enum.type.cip2000',  '67926A4F87974370A7764CF18DAF79A4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b2d4651e-739e-4150-b5a4-745ebee18a19', '49.0106', '49.0106', null, null, 1436, 'Airline Flight Attendant.', 'kuali.enum.type.cip2000',  '1B88C30D16BF49B482F3D27293016EB4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1d356099-05e2-408e-9b45-e0b1259e126f', '49.0107', '49.0107', null, null, 1437, 'Aircraft Pilot (Private).', 'kuali.enum.type.cip2000',  '65500386B6BA45C7BC422027DC2BD5AC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5a65a8da-9ffb-4cb9-bc72-30b61a8fe276', '49.0108', '49.0108', null, null, 1438, 'Flight Instructor.', 'kuali.enum.type.cip2000',  'BD752C7742C14C09AC0C9E57985F6455', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e435f9f6-02e7-437f-a3e7-9fcf779ba5ca', '49.0199', '49.0199', null, null, 1439, 'Air Transportation, Other.', 'kuali.enum.type.cip2000',  '7356AEE13F7140FB8FCBBBCA668FE513', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8bfe9354-463e-46ca-99a4-c6f5ebcbccd9', '49.02', '49.02', null, null, 1440, 'Ground Transportation.', 'kuali.enum.type.cip2000',  'D9B6FA66BBF449859EFEACB93770DF47', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c8b65f83-1ed3-418b-802a-9d983ac820d0', '49.0202', '49.0202', null, null, 1441, 'Construction/Heavy Equipment/Earthmoving Equipment Operation.',  'kuali.enum.type.cip2000', '492EEC6F63824A508B103C83D2C81F1B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5f54b3aa-dedd-4053-aebd-bad60f4f991c', '49.0205', '49.0205', null, null, 1442, 'Truck and Bus Driver/Commercial Vehicle Operation.',  'kuali.enum.type.cip2000', '633DA69C5D664503B35BCC9B744C1BA3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5d7bdf11-27d2-4bec-af7d-0e9a8c593519', '49.0206', '49.0206', null, null, 1443, 'Mobil Crane Operation/Operator.', 'kuali.enum.type.cip2000',  'DF5E05E0C4C34479879F518037B4101F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2f4aa245-db42-43ba-8710-041299e93401', '49.0299', '49.0299', null, null, 1444, 'Ground Transportation, Other.', 'kuali.enum.type.cip2000',  '871938D624CA45239187062DA43E42AD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ec872cb0-e470-4406-9e17-bb198bbd5c56', '49.03', '49.03', null, null, 1445, 'Marine Transportation.', 'kuali.enum.type.cip2000',  '14319BB929DC42F6A8C201CEFC6B25DF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4641097a-4b45-4836-9e1a-21fc31cae426', '49.0303', '49.0303', null, null, 1446, 'Commercial Fishing.', 'kuali.enum.type.cip2000',  'AC1E979D93B84FB3A9DC981DE97189C1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ec935638-8779-41a9-8255-8fd0699e5123', '49.0304', '49.0304', null, null, 1447, 'Diver, Professional and Instructor.', 'kuali.enum.type.cip2000',  '9DA18EB5F8FA4190B33B83FDC33CC647', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('048c48f1-0a97-4617-a68a-7e45cb783297', '49.0306', '49.0306', null, null, 1448, 'Marine Maintenance and Ship Repairer.', 'kuali.enum.type.cip2000',  'AA2792A7A7E7430C9119DCF67D10B1D6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('86cd6077-d836-4cfd-8e53-69c865a98b54', '49.0309', '49.0309', null, null, 1449, 'Marine Science/Merchant Marine Officer.', 'kuali.enum.type.cip2000',  'A80409A317894272814DA9E8F169DC9A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8ab616c9-3f65-4afd-b4fc-b9ddd859160b', '49.0399', '49.0399', null, null, 1450, 'Marine Transportation, Other.', 'kuali.enum.type.cip2000',  '97CB9BEC02FA44B1BBA19C9F3488FD11', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e0b1c248-6811-4638-9b36-fda5405b2a92', '49.99', '49.99', null, null, 1451, 'Transportation and Materials Moving, Other.', 'kuali.enum.type.cip2000',  '43C06F5BBF4A4C559734D8277C7FF3D1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9fd35596-022a-4048-8de1-db08aa6a8182', '49.9999', '49.9999', null, null, 1452, 'Transportation and Materials Moving, Other.', 'kuali.enum.type.cip2000',  '29491D232D8A424BA9F1F33F59F735F7', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8715b642-5f40-47e3-b9bf-08d4717b6f8a', '50.', '50.', null, null, 1453, 'VISUAL AND PERFORMING ARTS.', 'kuali.enum.type.cip2000',  '5DA5A7E84D4F406EB81C5D22E88C63B7', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('002ef704-8452-4d17-b481-238046f19d1c', '50.01', '50.01', null, null, 1454, 'Visual and Performing Arts, General.', 'kuali.enum.type.cip2000',  '8D71E44C8DBB441CAB8CD189AD14FBAA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d388a20b-142d-412e-aeb4-88ee0ad93ba8', '50.0101', '50.0101', null, null, 1455, 'Visual and Performing Arts, General.', 'kuali.enum.type.cip2000',  '3211D3A95CDF4AA696B6AE77D6F23C81', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('73348192-04bb-40dd-842f-aa2d618b359a', '50.02', '50.02', null, null, 1456, 'Crafts/Craft Design, Folk Art and Artisanry.', 'kuali.enum.type.cip2000',  '2A43F1B260874402B9D45A42450536F3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('effd5171-ed63-4c16-ac9e-1588b93db0e3', '50.0201', '50.0201', null, null, 1457, 'Crafts/Craft Design, Folk Art and Artisanry.', 'kuali.enum.type.cip2000',  '167B78B0DFC84188957A9188D0F1A8CB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('059bb38d-a8ae-4f2c-a6c9-32d00698845d', '50.03', '50.03', null, null, 1458, 'Dance.', 'kuali.enum.type.cip2000', '064C93D0DC854AF6BBF8D451B52805D8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ffef753e-1650-4db6-bae0-cf34506abf1b', '50.0301', '50.0301', null, null, 1459, 'Dance, General.', 'kuali.enum.type.cip2000',  '4C04CFCE852645D4A1588CCDB13812A7', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d5709727-42aa-466f-8d7c-7547e24dfa59', '50.0302', '50.0302', null, null, 1460, 'Ballet.', 'kuali.enum.type.cip2000', '697ED622F0DB461389097B57AC18F534', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9059099a-cf9a-43a3-a52a-a034c4cc61d8', '50.0399', '50.0399', null, null, 1461, 'Dance, Other.', 'kuali.enum.type.cip2000', '82025E7C7E4A49D58A9360BB8A4292B5',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1178175e-d9fb-4350-930b-005da7fe8556', '50.04', '50.04', null, null, 1462, 'Design and Applied Arts.', 'kuali.enum.type.cip2000',  '16A73DDF5D3F46278C5C868926E5659E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3e284199-8bf6-42c3-9061-3f30644048ae', '50.0401', '50.0401', null, null, 1463, 'Design and Visual Communications, General.', 'kuali.enum.type.cip2000',  '44594BCFFB2B4377A15FB2CCB61D5A45', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('40388342-238a-4187-b321-c0a4851b988a', '50.0402', '50.0402', null, null, 1464, 'Commercial and Advertising Art.', 'kuali.enum.type.cip2000',  '57E62555E06644E48186F18A25C052A4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('35e1ed8c-54ff-4502-a375-0e943641b540', '50.0404', '50.0404', null, null, 1465, 'Industrial Design.', 'kuali.enum.type.cip2000',  '3B164D5106B2406A918E88BBC8065FBE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d95c2068-067e-4104-b22c-6ee21c4c21d3', '50.0406', '50.0406', null, null, 1466, 'Commercial Photography.', 'kuali.enum.type.cip2000',  'BC43D8CA52E54577A8E6393CE45DFC7D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2f8dcf12-921a-438b-bab2-677a37a0a175', '50.0407', '50.0407', null, null, 1467, 'Fashion/Apparel Design.', 'kuali.enum.type.cip2000',  '2DC346B1718645459EC9425D54A5A285', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d9a4027e-22b8-4001-9724-0f3bfd1978bc', '50.0408', '50.0408', null, null, 1468, 'Interior Design.', 'kuali.enum.type.cip2000',  '1FD36947CF25459F9BB80382DCE37494', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('73cd753d-dcb0-4f10-900b-19085fa63155', '50.0409', '50.0409', null, null, 1469, 'Graphic Design.', 'kuali.enum.type.cip2000',  'A5E67794BCAF4D9D85A3ACEFA5736D4A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f614792e-a7a6-4865-9705-e26f89a7ba59', '50.0410', '50.0410', null, null, 1470, 'Illustration.', 'kuali.enum.type.cip2000', 'B3C92F27030E43CA88E239D5E8067C33',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7d508613-6798-4659-956e-0418baca70d8', '50.0499', '50.0499', null, null, 1471, 'Design and Applied Arts, Other.', 'kuali.enum.type.cip2000',  'CA0A766550AC40CA925C6037FA195DB4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4d95329b-73f7-4ae2-bbc1-1064920c5d30', '50.05', '50.05', null, null, 1472, 'Drama/Theatre Arts and Stagecraft.', 'kuali.enum.type.cip2000',  '8F43C5D5AFB14E2994C3E57C921E0747', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6db42c5c-78e7-4b51-93df-db50f0f73c24', '50.0501', '50.0501', null, null, 1473, 'Drama and Dramatics/Theatre Arts, General.', 'kuali.enum.type.cip2000',  '3DB628F438A74EAEBC6EC7F744C45A95', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('158c854b-a046-41c5-8531-2b6d5f015ee3', '50.0502', '50.0502', null, null, 1474, 'Technical Theatre/Theatre Design and Technology.', 'kuali.enum.type.cip2000',  'FD6B3422162543F4B4D6E6B51A7495EC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8d525898-6c59-48df-95ea-0ae1cf9ca20e', '50.0503', '50.0503', null, null, 1475, 'Acting and Directing.', 'kuali.enum.type.cip2000',  '7BE9B55F875F41D8A066A5F90F755ECB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('55be27f9-fde3-49cb-8eaa-d1ebd471cf89', '50.0504', '50.0504', null, null, 1476, 'Playwriting and Screenwriting.', 'kuali.enum.type.cip2000',  'AC55D30F39B44D29BCAA5BF1F8A47CE6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d1bd62aa-5427-4ca8-9a5b-c06ab1d8434d', '50.0505', '50.0505', null, null, 1477, 'Theatre Literature, History and Criticism.', 'kuali.enum.type.cip2000',  '5F1C25700F2D46DABB0C77B3E3CD429B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('eff6fff3-51b2-441c-ad35-7456508d97f1', '50.0506', '50.0506', null, null, 1478, 'Acting.', 'kuali.enum.type.cip2000', '65801D6CC95C4A51B7BB34CFA073DD11', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4379629c-8d72-4ccc-809c-dbfa6afbb115', '50.0507', '50.0507', null, null, 1479, 'Directing and Theatrical Production.', 'kuali.enum.type.cip2000',  '713F102DA4464A79B919CBDD6743290B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('74fefaad-91db-4936-960f-182bc733f7e1', '50.0508', '50.0508', null, null, 1480, 'Theatre/Theatre Arts Management.', 'kuali.enum.type.cip2000',  'EF481B5FF4054296B92FCAF374609379', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e6f58db7-5598-4cb8-904c-f92cca1166fe', '50.0599', '50.0599', null, null, 1481, 'Dramatic/Theatre Arts and Stagecraft, Other.', 'kuali.enum.type.cip2000',  'EF9E0098C90E420EAD55D6CA9B7624F4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('fd3a8f8e-6277-477b-8635-96bd4e62eec7', '50.06', '50.06', null, null, 1482, 'Film/Video and Photographic Arts.', 'kuali.enum.type.cip2000',  'DBF9997F93E24EF9A22BF333C8D6D63A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('84802cbb-7e8e-481f-b6cf-b70de489fa7e', '50.0601', '50.0601', null, null, 1483, 'Film/Cinema Studies.', 'kuali.enum.type.cip2000',  '8B87C885A15C480692814565282C3026', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4755e5c2-c462-4236-a3ce-1e90d0a8e352', '50.0602', '50.0602', null, null, 1484, 'Cinematography and Film/Video Production.', 'kuali.enum.type.cip2000',  '3B9122C8E95445A9B36A744154A590FD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('aa69d4d6-4164-4901-b3eb-6215f4f0674c', '50.0605', '50.0605', null, null, 1485, 'Photography.', 'kuali.enum.type.cip2000', 'E20A75F5F9674585A787047D2C657EF1',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b5ca1c14-4279-4c80-9d9a-73aeb952aadf', '50.0699', '50.0699', null, null, 1486, 'Film/Video and Photographic Arts, Other.', 'kuali.enum.type.cip2000',  'A198E39C61544089AFDD1037C5CEDBA2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1ec545ce-a559-47d6-b96e-b8801cf0a8b0', '50.07', '50.07', null, null, 1487, 'Fine and Studio Art.', 'kuali.enum.type.cip2000',  '50BC704F76824B379049B394585BDF41', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6f119d28-862a-450f-b02c-779cb3829ec1', '50.0701', '50.0701', null, null, 1488, 'Art/Art Studies, General.', 'kuali.enum.type.cip2000',  '44751121E315475F85A03DBC07F340CD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0374fd8f-c9fc-438d-9a48-095007f5ee17', '50.0702', '50.0702', null, null, 1489, 'Fine/Studio Arts, General.', 'kuali.enum.type.cip2000',  '56BEC12E413249AFB2C39E62A603D19A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2354dda7-236f-4a79-940f-d9d6803d9b49', '50.0703', '50.0703', null, null, 1490, 'Art History, Criticism and Conservation.', 'kuali.enum.type.cip2000',  '9D3B18D3FECF4639AB18122221E98DA0', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1e57f402-bc85-4781-94c7-acd3a2f84856', '50.0704', '50.0704', null, null, 1491, 'Arts Management.', 'kuali.enum.type.cip2000',  'BBF457076646468D85DD404AFEF8F44A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('cd7aabcb-3c4f-4501-9371-5c08cd78c9e9', '50.0705', '50.0705', null, null, 1492, 'Drawing.', 'kuali.enum.type.cip2000', '3328C55BCC104A57A18DE1E3DFAA2D4D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9e4a8194-ee6e-4599-8a0b-842bf92cd7b3', '50.0706', '50.0706', null, null, 1493, 'Intermedia/Multimedia.', 'kuali.enum.type.cip2000',  'FD7A94210435487DAEF3E5368C3798C4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2a5c6178-181c-40f7-a6b2-6f1bfadc09ac', '50.0708', '50.0708', null, null, 1494, 'Painting.', 'kuali.enum.type.cip2000', '7C9C24AA3B3C448BBC0C80292E52F772', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('23ddc70a-bbd8-4c69-9b7e-a2ee9ce0f1a0', '50.0709', '50.0709', null, null, 1495, 'Sculpture.', 'kuali.enum.type.cip2000', '21A3276C30724A0B90A1A5539C86A718', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('eb99c3b9-25f0-448f-a528-f3ef7bb822d1', '50.0710', '50.0710', null, null, 1496, 'Printmaking.', 'kuali.enum.type.cip2000', '64AA391649BF476B98C214838F32D5DA',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('98b98eeb-bba7-4091-9e69-a82c3bc372a9', '50.0711', '50.0711', null, null, 1497, 'Ceramic Arts and Ceramics.', 'kuali.enum.type.cip2000',  '17BD52117D114CB798B4E96D23069618', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4fd9d33d-3edc-4042-89a4-bab2a8c8a290', '50.0712', '50.0712', null, null, 1498, 'Fiber, Textile and Weaving Arts.', 'kuali.enum.type.cip2000',  'CEEA6B6E719B4D8699A10516D2CFAA17', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('080e902f-bc1c-49fb-900d-8cc6b70cd67a', '50.0713', '50.0713', null, null, 1499, 'Metal and Jewelry Arts.', 'kuali.enum.type.cip2000',  '93E94E5F77A64703A850B7F96B7C1660', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('35199412-5020-4ec1-af46-d5220706263c', '50.0799', '50.0799', null, null, 1500, 'Fine Arts and Art Studies, Other.', 'kuali.enum.type.cip2000',  '002249B5F744401DB003606C92051E6C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8c913d43-08fe-4780-8f7d-0d742d02bde2', '50.09', '50.09', null, null, 1501, 'Music.', 'kuali.enum.type.cip2000', '5ECA46D097414383AC123F0C519313AE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9583f767-e961-4279-9128-8ff313cd1821', '50.0901', '50.0901', null, null, 1502, 'Music, General.', 'kuali.enum.type.cip2000',  'EEF7D8FF86914C109D4C7D0EFC65B384', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('29d6b2a3-0481-4418-8b45-4c0f1211cc17', '50.0902', '50.0902', null, null, 1503, 'Music History, Literature, and Theory.', 'kuali.enum.type.cip2000',  'F8AA71CEDB42428FA6F128DE1652BB20', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d178f292-6e2f-4318-93dd-08d252017899', '50.0903', '50.0903', null, null, 1504, 'Music Performance, General.', 'kuali.enum.type.cip2000',  'B2E0F6BF3B294CDC978155CE505D24D8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('20ba9ed9-ba42-4cf7-b963-b53d9863ae5f', '50.0904', '50.0904', null, null, 1505, 'Music Theory and Composition.', 'kuali.enum.type.cip2000',  'A0017A8ED934402B9DF0122B214D1442', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c1a2765c-e2ff-43e7-aee9-9a1714127b0a', '50.0905', '50.0905', null, null, 1506, 'Musicology and Ethnomusicology.', 'kuali.enum.type.cip2000',  '46CD32CE5BE84AE682629F0AA0CF5F06', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('44f9c8c7-9487-4933-81df-2adb051a3664', '50.0906', '50.0906', null, null, 1507, 'Conducting.', 'kuali.enum.type.cip2000', '7670B2E1AEA243B591030AEC1014B6EB',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('872b58c3-5b61-43a7-8413-886b544d5107', '50.0907', '50.0907', null, null, 1508, 'Piano and Organ.', 'kuali.enum.type.cip2000',  '670DFCC16E4B4810A3E8A72F5533F724', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7792a669-a86d-4cc4-a473-bdf493904789', '50.0908', '50.0908', null, null, 1509, 'Voice and Opera.', 'kuali.enum.type.cip2000',  'E7DF877FA266484C985FB1171DB96908', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c459acab-cbc9-4e1f-94b1-088e8d5a120c', '50.0909', '50.0909', null, null, 1510, 'Music Management and Merchandising.', 'kuali.enum.type.cip2000',  'C00F43D4FB444F8A9F60B3E257F8C675', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b485f2b3-9ae9-4ced-8c5e-5ae5ae947b1b', '50.0910', '50.0910', null, null, 1511, 'Jazz/Jazz Studies.', 'kuali.enum.type.cip2000',  '7EDB2F17C8D7471C8C12720A1EC8C69A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e179eaee-ce77-4a38-ae32-98dd3c2b0411', '50.0911', '50.0911', null, null, 1512, 'Violin, Viola, Guitar and Other Stringed Instruments.',  'kuali.enum.type.cip2000', '8AC621B46AEF41C29D8C596A6B5F29B6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1673e620-ba61-4f90-9d2b-5ad7c1a777f1', '50.0912', '50.0912', null, null, 1513, 'Music Pedagogy.', 'kuali.enum.type.cip2000',  '145DC869A4394D9BB8C317BAC5B6E508', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0ca29b86-36b2-4c49-8ba3-ed231ba416ab', '50.0999', '50.0999', null, null, 1514, 'Music, Other.', 'kuali.enum.type.cip2000', '668DA2AF9E4748ABB52A6C1DA3640060',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7a22ed84-4215-424e-a9a3-c08bcb2ef4da', '50.99', '50.99', null, null, 1515, 'Visual and Performing Arts, Other.', 'kuali.enum.type.cip2000',  '2126F1B53DD448E9AA405B79B8C972DD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('fd5cace3-7d7b-4857-b2aa-b51eecdd67de', '50.9999', '50.9999', null, null, 1516, 'Visual and Performing Arts, Other.', 'kuali.enum.type.cip2000',  'AC458BECDB004781B76B3779156A0D8E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7c016c45-75f8-4033-94c5-e37ac0cca2ad', '51.', '51.', null, null, 1517, 'HEALTH PROFESSIONS AND RELATED CLINICAL SCIENCES.', 'kuali.enum.type.cip2000',  '091579C883B14663AA0762C9CEDBAFCC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('62006544-2cd6-4997-b6dd-e583fef66c32', '51.00', '51.00', null, null, 1518, 'Health Services/Allied Health/Health Sciences, General.',  'kuali.enum.type.cip2000', '2CEF7A2C996446D398B521BF3AA295AC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('dd6bcf1c-9fc2-4bbd-bb85-d3b5eed29e82', '51.0000', '51.0000', null, null, 1519, 'Health Services/Allied Health/Health Sciences, General.',  'kuali.enum.type.cip2000', 'B2ED56BFBFF94921A81C9ADFF49F5325', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6cb57a80-4097-452b-b7ec-cb54060a3765', '51.01', '51.01', null, null, 1520, 'Chiropractic (DC).', 'kuali.enum.type.cip2000',  '2CF9D10B1FFF40B6A2B4E00D0CF1735A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b5507699-c578-4551-a402-4eb06e46d0c8', '51.0101', '51.0101', null, null, 1521, 'Chiropractic (DC).', 'kuali.enum.type.cip2000',  'A27546C8399E4CF88E7984C490B28C6C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('74a18cb4-507d-468a-9eba-7659b3c588d4', '51.02', '51.02', null, null, 1522, 'Communication Disorders Sciences and Services.', 'kuali.enum.type.cip2000',  '0A173D85E54842A190236A8689E8F259', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e4799c71-4048-44d6-a645-03bd8023fe95', '51.0201', '51.0201', null, null, 1523, 'Communication Disorders, General.', 'kuali.enum.type.cip2000',  '10CA5A10B440426891E07B5275341639', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('80642f86-6bdd-4d33-821c-9cba87d1e579', '51.0202', '51.0202', null, null, 1524, 'Audiology/Audiologist and Hearing Sciences.', 'kuali.enum.type.cip2000',  'C6D691AF2E4A47549240BAD1604505C9', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('547c4939-018d-464e-9a46-dacbf2cb680e', '51.0203', '51.0203', null, null, 1525, 'Speech-Language Pathology/Pathologist.', 'kuali.enum.type.cip2000',  '861FDA77276E4A8CBF86216FCDD189FE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('22c6d88f-f95b-4cc4-a84b-61f5cbc89207', '51.0204', '51.0204', null, null, 1526, 'Audiology/Audiologist and Speech-Language Pathology/Pathologist.',  'kuali.enum.type.cip2000', '0CEDD1468FF24D9BB093792ACB763E76', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6b39dc4b-84b3-402e-9aab-677f1982c1ae', '51.0205', '51.0205', null, null, 1527, 'Sign Language Interpretation/Interpreter.', 'kuali.enum.type.cip2000',  '0A626ED805B7478EA815AD873980530A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4a352134-1f2a-437b-acf1-0fc5ee769171', '51.0299', '51.0299', null, null, 1528, 'Communication Disorders Sciences and Services, Other.',  'kuali.enum.type.cip2000', '7A521C4FDEE54092A214DED974717D11', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('cd2f5ebc-e841-43cb-8aaa-d4b66365905a', '51.03', '51.03', null, null, 1529, 'Community Health Services.', 'kuali.enum.type.cip2000',  'DE2B28CB3A204BC3A0CF7DD9DED21EED', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5105ccec-28ed-49cc-81b0-e8a48dded6c1', '51.0301', '51.0301', null, null, 1530, 'Community Health Liaison.', 'kuali.enum.type.cip2000',  '19164B3D4EC24D2FACE7BF82EB33E863', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b7efa031-c414-4586-945c-8350ef0b8c8d', '51.04', '51.04', null, null, 1531, 'Dentistry (DDS, DMD).', 'kuali.enum.type.cip2000',  '82C7355E80044F6FA17313D53C8CCB52', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7951d5a8-a04d-4c20-aac4-ec6fa10cf6be', '51.0401', '51.0401', null, null, 1532, 'Dentistry (DDS, DMD).', 'kuali.enum.type.cip2000',  'A13EBB0544DF4857B448845BE93D05E5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5cbdcd58-9b2b-48cc-b3c2-cabf59b18efe', '51.05', '51.05', null, null, 1533, 'Advanced/Graduate Dentistry and Oral Sciences (Cert.', 'kuali.enum.type.cip2000',  'EE5C62ED6D1149DB97CEBAB588F5BC8C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b0d2cbd5-722a-4c35-ae84-f4c156c16e37', '51.0501', '51.0501', null, null, 1534, 'Dental Clinical Sciences, General (MS, PhD).', 'kuali.enum.type.cip2000',  'BF23960A57614078932B515725042C4B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('87909c24-a4c7-4639-843f-fb94ae426ca3', '51.0502', '51.0502', null, null, 1535, 'Advanced General Dentistry (Cert.', 'kuali.enum.type.cip2000',  '3AA799080B7A459888D7E8EF5233EDEE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d4d81420-fc5a-4967-89f2-b2126ecd2a69', '51.0503', '51.0503', null, null, 1536, 'Oral Biology and Oral Pathology (MS, PhD).', 'kuali.enum.type.cip2000',  '880E538B77FD40A79DBFC491E7D3D585', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8487ab93-f933-4a80-a3ec-d4d56ff10984', '51.0504', '51.0504', null, null, 1537, 'Dental Public Health and Education (Cert.', 'kuali.enum.type.cip2000',  '9D6C32D6AA5940DD8C8F5AE4B906F3F8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('dc4599aa-b6a4-406d-aff3-0746c83fcde1', '51.0505', '51.0505', null, null, 1538, 'Dental Materials (MS, PhD).', 'kuali.enum.type.cip2000',  '86527B28635C43AD83B8578D41C78E20', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('08b55410-b319-4f61-9f90-45177a87b1c2', '51.0506', '51.0506', null, null, 1539, 'Endodontics/Endodontology (Cert.', 'kuali.enum.type.cip2000',  '9BA19B236B174A7F99FD1C98D0709C18', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('302e4bbc-3fc7-4a86-a55f-ff6195bbec0f', '51.0507', '51.0507', null, null, 1540, 'Oral/Maxillofacial Surgery (Cert.', 'kuali.enum.type.cip2000',  '3BECAD645B6E49DEBDB2B2D388523F23', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('45bde5f8-3945-4d3d-970f-0ad277588419', '51.0508', '51.0508', null, null, 1541, 'Orthodontics/Orthodontology (Cert.', 'kuali.enum.type.cip2000',  '82116CCFBE1F465C856880060FAF4194', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d47d463b-da4a-4bda-85ba-69eb6a29e542', '51.0509', '51.0509', null, null, 1542, 'Pediatric Dentistry/Pedodontics (Cert.', 'kuali.enum.type.cip2000',  'E6293B1B1BA947D3B8A001C532B5C118', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bf3265fd-d3b1-4be5-b757-6e6ec92bbcec', '51.0510', '51.0510', null, null, 1543, 'Periodontics/Periodontology (Cert.', 'kuali.enum.type.cip2000',  'E768E4EAD3DA4B04921AB27E4C51A9AC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('16bbfc32-5ab8-4fea-a264-57ae9c698062', '51.0511', '51.0511', null, null, 1544, 'Prosthodontics/Prosthodontology (Cert.', 'kuali.enum.type.cip2000',  'A9F371513CC9468181D1D058D611AF50', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0b0d2946-47ee-4ade-aeba-30edf5c9c403', '51.0599', '51.0599', null, null, 1545, 'Advanced/Graduate Dentistry and Oral Sciences, Other.',  'kuali.enum.type.cip2000', 'F416DA8A718D4CFCBFEDAC93C5B907AA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('502e01b2-a624-440c-aab1-224accd6b77b', '51.06', '51.06', null, null, 1546, 'Dental Support Services and Allied Professions.', 'kuali.enum.type.cip2000',  'E8F94834B4514FB6AB0394A7767AE44E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a34a6387-75ae-4a5f-b8e7-eeb517633293', '51.0601', '51.0601', null, null, 1547, 'Dental Assisting/Assistant.', 'kuali.enum.type.cip2000',  'B89365861D32480285246A599D5483CD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('cb72955f-21e5-4f1f-8b9d-e037bbb43c70', '51.0602', '51.0602', null, null, 1548, 'Dental Hygiene/Hygienist.', 'kuali.enum.type.cip2000',  '763E02B2926A4E1FBC9D56F815A50D91', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e3b049d9-1e31-4790-9433-de8cf8f2b4d3', '51.0603', '51.0603', null, null, 1549, 'Dental Laboratory Technology/Technician.', 'kuali.enum.type.cip2000',  '207BCC7EA3104364B770E88DDB4DDD56', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0225f72a-b549-4099-9315-37530d30fc4a', '51.0699', '51.0699', null, null, 1550, 'Dental Services and Allied Professions, Other.', 'kuali.enum.type.cip2000',  '2746B01333004CBEB3462F6C2D1AA0C5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6aee6bc6-8419-48f4-ba97-4a2bf4d71919', '51.07', '51.07', null, null, 1551, 'Health and Medical Administrative Services.', 'kuali.enum.type.cip2000',  'FEAB31953CB34CA8AD71CF4E7D9A6E52', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bbd9ab04-b404-4d9f-bb1f-0cbc1811fe5d', '51.0701', '51.0701', null, null, 1552, 'Health/Health Care Administration/Management.', 'kuali.enum.type.cip2000',  'C89880129D554C85B43DC0BF078B5215', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a48274cb-2684-4321-b274-33a9fb8294af', '51.0702', '51.0702', null, null, 1553, 'Hospital and Health Care Facilities Administration/Management.',  'kuali.enum.type.cip2000', '4741C641820A44329AE60EC54F84C15A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7b4bde34-28d0-4517-b3e9-e15c8492becd', '51.0703', '51.0703', null, null, 1554, 'Health Unit Coordinator/Ward Clerk.', 'kuali.enum.type.cip2000',  '06802C6B023B40DCA642458FC8B0E94C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5a94c25c-ece1-48b6-8e47-2d20963a139e', '51.0704', '51.0704', null, null, 1555, 'Health Unit Manager/Ward Supervisor.', 'kuali.enum.type.cip2000',  'D1B49465669A4E9AA8F1F511BA1A9D05', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('11d34c50-036a-4415-a9cc-c7d0c046c67a', '51.0705', '51.0705', null, null, 1556, 'Medical Office Management/Administration.', 'kuali.enum.type.cip2000',  'B5596E3DD76C48D2AA02951D3687F7A8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('df07506d-1b2b-444d-962f-568d8746a4e5', '51.0706', '51.0706', null, null, 1557, 'Health Information/Medical Records Administration/Administrator.',  'kuali.enum.type.cip2000', 'F06654B2882E49C1A92BDBF8862F09B0', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('202fd2d5-f7bb-4bd7-b00c-470756a82e4d', '51.0707', '51.0707', null, null, 1558, 'Health Information/Medical Records Technology/Technician.',  'kuali.enum.type.cip2000', 'A7FC7EFE625E4568BC234B0AD78AD302', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e1ac791f-b026-48e3-a30c-1578090c335c', '51.0708', '51.0708', null, null, 1559, 'Medical Transcription/Transcriptionist.', 'kuali.enum.type.cip2000',  '8C9EAB6BAF3E4C429239A194E9EF41F6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2a4e5085-95ca-424c-a2c1-a8001ec4e986', '51.0709', '51.0709', null, null, 1560, 'Medical Office Computer Specialist/Assistant.', 'kuali.enum.type.cip2000',  'BF57493FE76A48B8A2A046D9CFE885C2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c17b834e-7b73-4ff6-aa53-d406cc61e88f', '51.0710', '51.0710', null, null, 1561, 'Medical Office Assistant/Specialist.', 'kuali.enum.type.cip2000',  '18858F39C10C436999467D645D85ED01', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('efcb96a3-c652-4639-91f6-e581230d85ad', '51.0711', '51.0711', null, null, 1562, 'Medical/Health Management and Clinical Assistant/Specialist.',  'kuali.enum.type.cip2000', 'EBB076FF153E4FAAA416377B658F512B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9b8536fd-a25b-43d5-ae31-bd38e0fcb3db', '51.0712', '51.0712', null, null, 1563, 'Medical Reception/Receptionist.', 'kuali.enum.type.cip2000',  '28BC626B26074C24AC409C9F652F122B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7b6796c3-490d-4df9-b6b5-de99ce0f5356', '51.0713', '51.0713', null, null, 1564, 'Medical Insurance Coding Specialist/Coder.', 'kuali.enum.type.cip2000',  '826FA5C86F9B4E76AB35ECF5F7D7BD42', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0b0c0bae-5f5c-4cc2-b8f7-8e2f94a0d4b3', '51.0714', '51.0714', null, null, 1565, 'Medical Insurance Specialist/Medical Biller.', 'kuali.enum.type.cip2000',  '191BA73BB7EF499096C45AF38DFD3B8B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('56ad931e-2bb5-4eb4-8398-4aa284f75bcf', '51.0715', '51.0715', null, null, 1566, 'Health/Medical Claims Examiner.', 'kuali.enum.type.cip2000',  '9A7FDBBA3929424483F409F88027CF50', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('56ee4fd3-6beb-4152-a361-d57d076b5b14', '51.0716', '51.0716', null, null, 1567, 'Medical Administrative/Executive Assistant and Medical Secretary.',  'kuali.enum.type.cip2000', 'E617968C362E4E6CA2A43479870CC3F1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2cbe680c-ad96-47ab-9ea7-973eac10c359', '51.0717', '51.0717', null, null, 1568, 'Medical Staff Services Technology/Technician.', 'kuali.enum.type.cip2000',  '5769F31C1A72446FBBBBA1BD944C8136', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9af49689-0bbe-4035-a352-9274a40b0338', '51.0799', '51.0799', null, null, 1569, 'Health and Medical Administrative Services, Other.',  'kuali.enum.type.cip2000', '1A7D3A9ACE95473FB74DC3123BCEBB8A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8a25b8fe-bfd5-4e89-aba6-86de56d10967', '51.08', '51.08', null, null, 1570, 'Allied Health and Medical Assisting Services.', 'kuali.enum.type.cip2000',  '76027E1F1EF54FED9CD16C055661DF59', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c87f2d27-bd7f-44c1-bc8d-f70bbcc483e8', '51.0801', '51.0801', null, null, 1571, 'Medical/Clinical Assistant.', 'kuali.enum.type.cip2000',  '7F8E53605C5F4C61A6E4AEDC25D50EFC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f744593f-d0a3-442f-baf7-cd28565edc4f', '51.0802', '51.0802', null, null, 1572, 'Clinical/Medical Laboratory Assistant.', 'kuali.enum.type.cip2000',  'AEFD58875A5040D1986A6C653434FA60', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1875a422-4aa1-429e-8e84-bf61fd553873', '51.0803', '51.0803', null, null, 1573, 'Occupational Therapist Assistant.', 'kuali.enum.type.cip2000',  'C63C549E76C244268D621BD93E07E23E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('86e0eea4-8418-4f20-ad4b-b3900419bdbf', '51.0804', '51.0804', null, null, 1574, 'Ophthalmic Medical Assistant.', 'kuali.enum.type.cip2000',  '535A54FC20DD48A9A67F7F4B544FEB72', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('88cd806b-4bd4-402d-857a-26c8dc4ccb53', '51.0805', '51.0805', null, null, 1575, 'Pharmacy Technician/Assistant.', 'kuali.enum.type.cip2000',  'B8EACFD3A3BC44DE970833D09E2D6DF3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d6da595b-7819-4763-bef9-afde9c587d8c', '51.0806', '51.0806', null, null, 1576, 'Physical Therapist Assistant.', 'kuali.enum.type.cip2000',  '08770B04CF594209BD29E580E60DFA01', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('172736c1-76e8-4821-b2bc-d50e44fba558', '51.0807', '51.0807', null, null, 1577, 'Physician Assistant.', 'kuali.enum.type.cip2000',  '4FFACF17091C4066AB9FFA251D421DFD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('382382c1-7203-4ce6-885e-afc62ab56762', '51.0808', '51.0808', null, null, 1578, 'Veterinary/Animal Health Technology/Technician and Veterinary Assistant.',  'kuali.enum.type.cip2000', 'DFB7AFB1316C45DEB605054275D72AC9', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('51a8837c-c12b-4788-9990-c01bb40c2270', '51.0809', '51.0809', null, null, 1579, 'Anesthesiologist Assistant.', 'kuali.enum.type.cip2000',  '99B9B3C73F554E9F8832E95FD8EB5922', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('df68b010-a5df-46a9-aea1-ef7970d50e53', '51.0810', '51.0810', null, null, 1580, 'Emergency Care Attendant (EMT Ambulance).', 'kuali.enum.type.cip2000',  'FDD388B3FA564166BC8204C9C0118AA8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('616cf44f-79c4-483b-8594-4e4f8fe741b0', '51.0811', '51.0811', null, null, 1581, 'Pathology/Pathologist Assistant.', 'kuali.enum.type.cip2000',  'F556B2A63B6949319AD91D16E15F2128', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c9a54372-203e-4c48-a937-a3a266a5d1eb', '51.0812', '51.0812', null, null, 1582, 'Respiratory Therapy Technician/Assistant.', 'kuali.enum.type.cip2000',  '04F3562379E54F729437704A11F2BD7A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('39981144-2b10-4bef-9d16-0ad0797d919a', '51.0813', '51.0813', null, null, 1583, 'Chiropractic Assistant/Technician.', 'kuali.enum.type.cip2000',  '91236847AA4846A99E870E5772C08866', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5f0cee4d-b838-49b0-b1b4-86979ad4ae35', '51.0899', '51.0899', null, null, 1584, 'Allied Health and Medical Assisting Services, Other.',  'kuali.enum.type.cip2000', '22B1651DE3834E4D8EA1316A37F9C710', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7df4a68e-721d-48d9-9715-f9db5c18e4b2', '51.09', '51.09', null, null, 1585, 'Allied Health Diagnostic, Intervention, and Treatment Professions.',  'kuali.enum.type.cip2000', 'E47346BD59AF44F182FB989447D52D09', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ca6a68c8-9b75-4442-b571-29380d581485', '51.0901', '51.0901', null, null, 1586, 'Cardiovascular Technology/Technologist.', 'kuali.enum.type.cip2000',  '4955313C37124B0B9C437C8DFDB86F8C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0c9ac977-715a-4f11-93c2-1a59f633bb4c', '51.0902', '51.0902', null, null, 1587, 'Electrocardiograph Technology/Technician.', 'kuali.enum.type.cip2000',  '53A148D215C74D8D889AEB98A2BC56FC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c86f46c4-3cf7-4d90-9498-9fab64393952', '51.0903', '51.0903', null, null, 1588, 'Electroneurodiagnostic/Electroencephalographic Technology/Technologist.',  'kuali.enum.type.cip2000', '55E714108AAF41DA83E4C299C2E6D7CB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e737211f-cdf8-4596-8bf8-23d002670955', '51.0904', '51.0904', null, null, 1589, 'Emergency Medical Technology/Technician (EMT Paramedic).',  'kuali.enum.type.cip2000', 'DCC0F83842C649989A3CD318BE1D018E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('41fff932-c5fd-4d7d-a49d-ae66fdaedda3', '51.0905', '51.0905', null, null, 1590, 'Nuclear Medical Technology/Technologist.', 'kuali.enum.type.cip2000',  '382AA539548E41DD8401325B1B11C492', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7b7dbf8c-eb15-42b0-bf19-252e0af5aced', '51.0906', '51.0906', null, null, 1591, 'Perfusion Technology/Perfusionist.', 'kuali.enum.type.cip2000',  'A59828D5438A40388F9745C45A499690', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('00c85d14-ca35-44be-8a83-e034fc8b7535', '51.0907', '51.0907', null, null, 1592, 'Medical Radiologic Technology/Science -Radiation Therapist.',  'kuali.enum.type.cip2000', '71D6C6E923194E43AFAD86C05A146F8D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('20b2f582-ce41-4f2a-adee-9382e480ce49', '51.0908', '51.0908', null, null, 1593, 'Respiratory Care Therapy/Therapist.', 'kuali.enum.type.cip2000',  'A0BAF77D4EE44C13B4B35B781A1854B4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7c6f72ef-ed22-4663-a79e-387d613df55e', '51.0909', '51.0909', null, null, 1594, 'Surgical Technology/Technologist.', 'kuali.enum.type.cip2000',  'AB3A56E7DACC45359C2F70C736FC4458', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a91990c6-b4ad-4f3d-9db0-40e1a9af80c1', '51.0910', '51.0910', null, null, 1595, 'Diagnostic Medical Sonography/Sonographer and Ultrasound Technician.',  'kuali.enum.type.cip2000', '3276C979F9FC46D3BC0F4C4D6606D375', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('467ac9ea-5643-4e3a-a3e6-0e616b9c8c20', '51.0911', '51.0911', null, null, 1596, 'Radiologic Technology/Science -Radiographer.', 'kuali.enum.type.cip2000',  'A7562233FA294A2499BC6EBBD1E0724B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f1a1cfac-6774-4d80-9633-38baa8e9663c', '51.0912', '51.0912', null, null, 1597, 'Physician Assistant.', 'kuali.enum.type.cip2000',  '67B5C24CA66649C9B3462D9E89526FC5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3fe708cf-9ea6-4818-bf48-d31a34e14d62', '51.0913', '51.0913', null, null, 1598, 'Athletic Training/Trainer.', 'kuali.enum.type.cip2000',  '10744364226E4540BA5B7263B56794CE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6fa8c8c5-dc19-4a25-b8ea-8d19a53eda46', '51.0914', '51.0914', null, null, 1599, 'Gene/Genetic Therapy.', 'kuali.enum.type.cip2000',  'D4E3E848E22B4280B01BB0B765317B81', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4315a3a6-2e1d-4771-bc0f-34c3eba38974', '51.0915', '51.0915', null, null, 1600, 'Cardiopulmonary Technology/Technologist.', 'kuali.enum.type.cip2000',  '7A497B9C577C41D59BBC0E64F7C04470', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('fc72ebe6-4a6d-4990-8a52-e8aa7a128515', '51.0916', '51.0916', null, null, 1601, 'Radiation Protection/Health Physics Technician.', 'kuali.enum.type.cip2000',  '5770029881484462B06B070B0B849395', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('fafa53dc-4462-4873-a4a9-7927266693c8', '51.0999', '51.0999', null, null, 1602, 'Allied Health Diagnostic, Intervention, and Treatment Professions, Other.',  'kuali.enum.type.cip2000', '467042642D3A46FDB9987A9573DD718A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4b266cac-a9f3-4917-bfb2-aee0b4a09f96', '51.10', '51.10', null, null, 1603, 'Clinical/Medical Laboratory Science and Allied Professions.',  'kuali.enum.type.cip2000', 'D0B3A83D44084EE5AAEFDC0FB2A2DEFD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e99c883a-1644-4ff5-b222-5f6e7d28d07e', '51.1001', '51.1001', null, null, 1604, 'Blood Bank Technology Specialist.', 'kuali.enum.type.cip2000',  'F9EB4F62ED4D4EB793C378E195F94971', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('47e6e49e-054a-4811-b715-e0b7dd9bb5bf', '51.1002', '51.1002', null, null, 1605, 'Cytotechnology/Cytotechnologist.', 'kuali.enum.type.cip2000',  '9CE26B8294934F3D92FA7071E4D81BDC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f9b2b8fd-c6f8-4727-973b-471f4c807ee7', '51.1003', '51.1003', null, null, 1606, 'Hematology Technology/Technician.', 'kuali.enum.type.cip2000',  '079E9AF6D8B4400AB3523AB2E3EF76D4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('db8c7d31-9218-424c-a5eb-43a0c0cfe843', '51.1004', '51.1004', null, null, 1607, 'Clinical/Medical Laboratory Technician.', 'kuali.enum.type.cip2000',  'E902FA290A8345978CA67FF0CD615ACA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('67a05a40-a2d8-4924-a931-5a00d3c31456', '51.1005', '51.1005', null, null, 1608, 'Clinical Laboratory Science/Medical Technology/Technologist.',  'kuali.enum.type.cip2000', '18472049BAB34E7F9CD64E185353FE67', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0a77b4bf-0978-4215-ba18-7fb628aae4b7', '51.1006', '51.1006', null, null, 1609, 'Ophthalmic Laboratory Technology/Technician.', 'kuali.enum.type.cip2000',  '1F676330803749138A4F434E83AFBCCA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('71e0262e-030d-4575-b52c-f3d463f282d6', '51.1007', '51.1007', null, null, 1610, 'Histologic Technology/Histotechnologist.', 'kuali.enum.type.cip2000',  'D8AE81024ECA4BB4BB5BFBDA692E65EE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('fd44702e-5159-4e83-ac89-c5f5b4ca4da1', '51.1008', '51.1008', null, null, 1611, 'Histologic Technician.', 'kuali.enum.type.cip2000',  '53D3AF5EA885455396286A25791D0ED3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('38424e95-19a4-405c-80d1-3ac785d6a63c', '51.1009', '51.1009', null, null, 1612, 'Phlebotomy/Phlebotomist.', 'kuali.enum.type.cip2000',  'EB6B0413FB4341DEA4177089C42B858B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('79e62eb4-ee43-4318-8a51-ed97ed9cd878', '51.1010', '51.1010', null, null, 1613, 'Cytogenetics/Genetics/Clinical Genetics Technology/Technologist.',  'kuali.enum.type.cip2000', 'DE25FF7DA5FD48729ACF9C6D08723CF8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('19ddb5b2-81f0-4d30-8130-0b66fa64a4b8', '51.1011', '51.1011', null, null, 1614, 'Renal/Dialysis Technologist/Technician.', 'kuali.enum.type.cip2000',  '9DF6D60454CE4350A5DC2AD31005DB56', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5659e007-6750-4328-a429-c4758c2d6987', '51.1099', '51.1099', null, null, 1615, 'Clinical/Medical Laboratory Science and Allied Professions, Other.',  'kuali.enum.type.cip2000', '0C9C8D6B68364A0C96E8288E7AAE4A7A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5f24fece-9471-4741-ba4a-ed2288fc506d', '51.11', '51.11', null, null, 1616, 'Health/Medical Preparatory Programs.', 'kuali.enum.type.cip2000',  'B44E3AACFFC44978A22C6E578DCF1355', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1321c724-8d9f-4046-aca0-fd95d43fe924', '51.1101', '51.1101', null, null, 1617, 'Pre-Dentistry Studies.', 'kuali.enum.type.cip2000',  '9A6738986B8C48059235E1FC5E26B194', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7f9c529c-42cb-4ddb-bae1-c5a0a9504e7a', '51.1102', '51.1102', null, null, 1618, 'Pre-Medicine/Pre-Medical Studies.', 'kuali.enum.type.cip2000',  '9608412B3879494D9BD8AF5AC584AC81', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bad03e02-612e-435a-8c6e-5063bab4b76b', '51.1103', '51.1103', null, null, 1619, 'Pre-Pharmacy Studies.', 'kuali.enum.type.cip2000',  '9B8D2FE6DF5846C8AE5DEECF22AEEAEF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0ede4691-d72d-4327-892d-91f904cbf197', '51.1104', '51.1104', null, null, 1620, 'Pre-Veterinary Studies.', 'kuali.enum.type.cip2000',  'FEC5D34AC9CD47EE8782AAE8A0472CDD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3235e013-f543-41ba-b785-4bd2ebe22686', '51.1105', '51.1105', null, null, 1621, 'Pre-Nursing Studies.', 'kuali.enum.type.cip2000',  'C2B5382EC5A0483FB07CC942A8582E2A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('da6e6969-4773-4421-99c5-3a616b323510', '51.1199', '51.1199', null, null, 1622, 'Health/Medical Preparatory Programs, Other.', 'kuali.enum.type.cip2000',  '68A113412DD24157AF005CE53D958651', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6492deda-c130-45ee-a5eb-d5ccfec8c262', '51.12', '51.12', null, null, 1623, 'Medicine (MD).', 'kuali.enum.type.cip2000', 'C28941A95DBC4BC08D43EB0E0EC73F07', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6d5d5ad8-8e42-42a9-990f-40c389142820', '51.1201', '51.1201', null, null, 1624, 'Medicine (MD).', 'kuali.enum.type.cip2000',  'E7DB920196634207AD6788821DB7C70A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3082e9d7-4265-4a3d-9471-8cf9a5af3422', '51.13', '51.13', null, null, 1625, 'Medical Basic Sciences.', 'kuali.enum.type.cip2000',  '3E88959991EE44C09379C8432BE48D2E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b819b84b-5745-4f68-8c5f-12c0d751d922', '51.1301', '51.1301', null, null, 1626, 'Medical Anatomy.', 'kuali.enum.type.cip2000',  'EA57A713AD264474935D2B11B18654F3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('952a63b9-8060-4f0a-bd8a-0e50bc46cd60', '51.1302', '51.1302', null, null, 1627, 'Medical Biochemistry.', 'kuali.enum.type.cip2000',  '21B6E09745864DD9BDE1826B667439A2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bbfaa0e3-35f6-49c4-8d37-a3e2d9116614', '51.1303', '51.1303', null, null, 1628, 'Medical Biomathematics and Biometrics.', 'kuali.enum.type.cip2000',  'DDF5502479804CFA9944FE9B1B0DBEFE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7919b92b-0a73-4080-8034-a4cb3c7536e9', '51.1304', '51.1304', null, null, 1629, 'Medical Biophysics.', 'kuali.enum.type.cip2000',  'D5E0FC9F840F43EF9B5427E14DF2C5EA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6bfab61d-97d6-421c-a3aa-1539c97646cb', '51.1305', '51.1305', null, null, 1630, 'Medical Cell Biology.', 'kuali.enum.type.cip2000',  '0263A8991BD34BEC86CF232F55337163', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a9558947-0467-413f-b0a1-3397d9218952', '51.1306', '51.1306', null, null, 1631, 'Medical Genetics.', 'kuali.enum.type.cip2000',  'D5B1C2ACF05E4D77BDF124BA289C07F0', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('70aa92dc-c2c9-4902-9b2e-25dcf93b17fa', '51.1307', '51.1307', null, null, 1632, 'Medical Immunology.', 'kuali.enum.type.cip2000',  'C5BB78E8437A42A28BB6FFCB3C144588', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('19c5b39b-c54a-49d7-9f30-8c8bb054a7c9', '51.1308', '51.1308', null, null, 1633, 'Medical Microbiology.', 'kuali.enum.type.cip2000',  '5574FF1DEE9D4493B8DEE5E4997A1C6A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ff40db66-923f-48c6-bcfd-8b544ea6ecd7', '51.1309', '51.1309', null, null, 1634, 'Medical Molecular Biology.', 'kuali.enum.type.cip2000',  'EC2A3F06F5FB4453AB38789C79A681D2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('dd2b6864-eb6c-4227-98de-ec01c2b2f9ea', '51.1310', '51.1310', null, null, 1635, 'Medical Neurobiology.', 'kuali.enum.type.cip2000',  'C23A89D1077E477A96B65C94E8E19BC9', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d14db77c-3008-41ba-98f5-2e06bdf16b5a', '51.1311', '51.1311', null, null, 1636, 'Medical Nutrition.', 'kuali.enum.type.cip2000',  '0F825CF48D62416AA4BA9D8118E3EF87', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3278ce7e-3582-43d6-b05d-d157872e3dd7', '51.1312', '51.1312', null, null, 1637, 'Medical Pathology.', 'kuali.enum.type.cip2000',  '66566B41932E45D6929DF18F1D26129F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d8fef608-bf80-4533-ab51-3e24bb87323f', '51.1313', '51.1313', null, null, 1638, 'Medical Physiology.', 'kuali.enum.type.cip2000',  'A22F77D27C944ABABD53CCF6A48A8D9F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ede851f3-f752-4b0f-8db2-abeb3d2811b0', '51.1314', '51.1314', null, null, 1639, 'Medical Toxicology.', 'kuali.enum.type.cip2000',  'C5BAD1484D944BDEA71EA4E9FAADA41E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('76fba0da-37a7-4f56-9510-5dd1a3635c22', '51.1399', '51.1399', null, null, 1640, 'Medical Basic Sciences, Other.', 'kuali.enum.type.cip2000',  'B2F0E78E365E469A95B54562FA8DB01C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('15148867-1da6-48e6-9c1e-3548243e0e0b', '51.14', '51.14', null, null, 1641, 'Medical Clinical Sciences/Graduate Medical Studies.', 'kuali.enum.type.cip2000',  '4A87F7281763426CA4CC608DEFE0A839', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3549cdc1-902b-4339-b44e-8f206eb3f5cd', '51.1401', '51.1401', null, null, 1642, 'Medical Scientist (MS, PhD).', 'kuali.enum.type.cip2000',  '4BDF812A419F45D3904B5B9A33596439', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0dacd0b0-2185-41ca-9915-f2a6a142322f', '51.15', '51.15', null, null, 1643, 'Mental and Social Health Services and Allied Professions.',  'kuali.enum.type.cip2000', '28244CF63CDA45A79869B859D77F4B8B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('fb3ec9ca-490f-40dc-b6d4-7a64c449deaa', '51.1501', '51.1501', null, null, 1644, 'Substance Abuse/Addiction Counseling.', 'kuali.enum.type.cip2000',  '53DDA112B0E24B099E5EBEAFABE51773', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6384c336-b0fd-48c9-85d5-206351437de6', '51.1502', '51.1502', null, null, 1645, 'Psychiatric/Mental Health Services Technician.', 'kuali.enum.type.cip2000',  '612BDB9399704BA9AF098F9374FAE678', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('428fdd68-0dc1-4c55-a225-653496b2cbe6', '51.1503', '51.1503', null, null, 1646, 'Clinical/Medical Social Work.', 'kuali.enum.type.cip2000',  '3D92167CCCA14DE6BA15A7F4B2A6AD28', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7ae22547-dc22-4e34-9114-b0ab79fd0ae2', '51.1504', '51.1504', null, null, 1647, 'Community Health Services/Liaison/Counseling.', 'kuali.enum.type.cip2000',  'AB652FCF73924714A8058EA6DDE9493E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('21830a82-1ce7-4ea7-b0de-b3b372f6c42f', '51.1505', '51.1505', null, null, 1648, 'Marriage and Family Therapy/Counseling.', 'kuali.enum.type.cip2000',  '03861B6161594BA39F8888975DB22DFF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('aa6c77c1-8245-432f-bca2-fad13dfce544', '51.1506', '51.1506', null, null, 1649, 'Clinical Pastoral Counseling/Patient Counseling.', 'kuali.enum.type.cip2000',  '9C4595791DBE42E688795BFD656B00BE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2a932c1d-78de-42f7-a598-9dde083e6d3a', '51.1507', '51.1507', null, null, 1650, 'Psychoanalysis and Psychotherapy.', 'kuali.enum.type.cip2000',  '8FF39593F19148038DD66EA15530DCF5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('eabc3202-984a-42a9-a538-b3c70a9258f2', '51.1508', '51.1508', null, null, 1651, 'Mental Health Counseling/Counselor.', 'kuali.enum.type.cip2000',  '64DE55A749254ABA83E87E006E1630E3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6e36d244-a14f-4936-91d7-2b36a197cf39', '51.1509', '51.1509', null, null, 1652, 'Genetic Counseling/Counselor.', 'kuali.enum.type.cip2000',  'D0AE3FB685E64FF0B572A6EB7C57FA6F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('719ee598-cfa1-47a2-abe9-0c42ecdc8dac', '51.1599', '51.1599', null, null, 1653, 'Mental and Social Health Services and Allied Professions, Other.',  'kuali.enum.type.cip2000', '24393CFB02C740AD9B04CFD7B2F44780', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('04f3a5ac-de8a-4cff-bcd3-6258ddcb6270', '51.16', '51.16', null, null, 1654, 'Nursing.', 'kuali.enum.type.cip2000', '4AB137BA762D43C093787AD78303B5F4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4a0270d0-fe8f-4650-bb1f-1944680fd0eb', '51.1601', '51.1601', null, null, 1655, 'Nursing/Registered Nurse (RN, ASN, BSN, MSN).', 'kuali.enum.type.cip2000',  '144FF8EE5D7049A0B0A33DDB9C867060', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5936c96d-f223-46fb-9711-5f5f66e857a7', '51.1602', '51.1602', null, null, 1656, 'Nursing Administration (MSN, MS, PhD).', 'kuali.enum.type.cip2000',  'AC6C4BB0ECB246488E64A7B66D2ED622', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5fad5f34-ec72-4e05-9da7-3e48cdfd0e99', '51.1603', '51.1603', null, null, 1657, 'Adult Health Nurse/Nursing.', 'kuali.enum.type.cip2000',  '60ACE6673415451CBBE9942F10892509', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('45aa899e-489e-4670-919b-29d60de1009d', '51.1604', '51.1604', null, null, 1658, 'Nurse Anesthetist.', 'kuali.enum.type.cip2000',  'FBD3AEFE4E3B48BAA3E29D193B26EE91', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ed519dde-9217-4351-ab59-f57c1daca8ea', '51.1605', '51.1605', null, null, 1659, 'Family Practice Nurse/Nurse Practitioner.', 'kuali.enum.type.cip2000',  'EF7C713BC4E84F62814B6323857BD5D0', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d19e5165-62e9-4f5f-b8fb-eb67266b4fe8', '51.1606', '51.1606', null, null, 1660, 'Maternal/Child Health and Neonatal Nurse/Nursing.', 'kuali.enum.type.cip2000',  '9AC584DA1183465AABBEB5725310E0C3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('707eaa71-8753-4f19-b116-ee5c220d354a', '51.1607', '51.1607', null, null, 1661, 'Nurse Midwife/Nursing Midwifery.', 'kuali.enum.type.cip2000',  '7F655093A78E4E1FB77EEDE217DE3F81', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b9557922-4195-4268-a16e-eb9a2c2c01c6', '51.1608', '51.1608', null, null, 1662, 'Nursing Science (MS, PhD).', 'kuali.enum.type.cip2000',  'CD5EE4B0D31648D1A97AFE979E2FABBD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('589976eb-8da0-41fa-bf39-2502ff0afe4f', '51.1609', '51.1609', null, null, 1663, 'Pediatric Nurse/Nursing.', 'kuali.enum.type.cip2000',  '2B36607BD6D24B5D9B6CE91D210532C7', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('768d296f-3fce-410c-a595-d44b1377d500', '51.1610', '51.1610', null, null, 1664, 'Psychiatric/Mental Health Nurse/Nursing.', 'kuali.enum.type.cip2000',  '1A47D839D3844AA1956F0599F1ACBD76', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('34dcacbf-8f5b-4e03-8caf-76af7a83a9c4', '51.1611', '51.1611', null, null, 1665, 'Public Health/Community Nurse/Nursing.', 'kuali.enum.type.cip2000',  'D013D83F5B7C433BBA0BCE9F7E31DDF5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('cc875963-e6d4-4dfe-b252-ef5090804d08', '51.1612', '51.1612', null, null, 1666, 'Perioperative/Operating Room and Surgical Nurse/Nursing.',  'kuali.enum.type.cip2000', 'E85CE71089154F69B3B630246F06EED9', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4225dba9-30ef-4783-b411-b473f984de24', '51.1613', '51.1613', null, null, 1667, 'Licensed Practical/Vocational Nurse Training (LPN, LVN, Cert.',  'kuali.enum.type.cip2000', '2A49FBD6F0A345F3B002F01EC0E74807', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('71d3c1be-8a6f-4fb0-81ae-1ec439b36638', '51.1614', '51.1614', null, null, 1668, 'Nurse/Nursing Assistant/Aide and Patient Care Assistant.',  'kuali.enum.type.cip2000', 'D266DCE2BE6E46C9AC07B6A7B2403D68', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0f4dca5e-fd90-4a51-bd35-12856024ee0e', '51.1615', '51.1615', null, null, 1669, 'Home Health Aide.', 'kuali.enum.type.cip2000',  'B969ED727A5445A98E5EB4BA5425FBEF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e0a8eddc-77c1-4caa-a8c8-cb9063ce2081', '51.1616', '51.1616', null, null, 1670, 'Clinical Nurse Specialist.', 'kuali.enum.type.cip2000',  '03AED902827C4F949E07C1E607BF7AB4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('dc83b2c0-c56d-4392-92f3-bb20ba35bd71', '51.1617', '51.1617', null, null, 1671, 'Critical Care Nursing.', 'kuali.enum.type.cip2000',  '8EA745565ADC4506B408373D11AB0252', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e22cbf92-b17c-4864-9bd3-aea9044796d7', '51.1618', '51.1618', null, null, 1672, 'Occupational and Environmental Health Nursing.', 'kuali.enum.type.cip2000',  '36906AE0A086456791ED1227D5B427B2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d46a1c00-ca5b-493e-9434-43d0f75f6e8c', '51.1699', '51.1699', null, null, 1673, 'Nursing, Other.', 'kuali.enum.type.cip2000',  'E553A7F4843845D5978BD9ABCB397AB9', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8e764089-0444-4331-a91d-eac94c0fff91', '51.17', '51.17', null, null, 1674, 'Optometry (OD).', 'kuali.enum.type.cip2000', '1CEF574A83E04984B317ECEC0C271DA1',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f17232d7-ffa0-44ab-be87-d5ed61495c66', '51.1701', '51.1701', null, null, 1675, 'Optometry (OD).', 'kuali.enum.type.cip2000',  'EA8958411020453CAEC502B10EFFC3F7', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e9dec819-e985-4df3-92dd-2b2d794e77da', '51.18', '51.18', null, null, 1676, 'Ophthalmic and Optometric Support Services and Allied Professions.',  'kuali.enum.type.cip2000', 'CB464AE6BBBA448AA99B212E78EC052B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('49835237-95d0-4b5f-9ede-8a412e35101e', '51.1801', '51.1801', null, null, 1677, 'Opticianry/Ophthalmic Dispensing Optician.', 'kuali.enum.type.cip2000',  '89A075F2190946FAA61E1545CBBB51B0', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4c3f42a1-e116-4442-86d7-32e696bee230', '51.1802', '51.1802', null, null, 1678, 'Optometric Technician/Assistant.', 'kuali.enum.type.cip2000',  'E2CB6DB4218E482FAD3D018F3639F193', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('495b1037-d927-4c15-b5b7-70de9204e663', '51.1803', '51.1803', null, null, 1679, 'Ophthalmic Technician/Technologist.', 'kuali.enum.type.cip2000',  '146E220559E9484093BB14D56DB6394D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('44dac5e6-e840-4e4c-ac1a-1f0eeacbd879', '51.1804', '51.1804', null, null, 1680, 'Orthoptics/Orthoptist.', 'kuali.enum.type.cip2000',  '187132D165844CD384101862D8E978F1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f2b49c21-b2c6-490f-a7b4-e60669f238cf', '51.1899', '51.1899', null, null, 1681, 'Ophthalmic and Optometric Support Services and Allied Professions, Other.',  'kuali.enum.type.cip2000', 'EE23EDEF328343DCB194D5F3144AB018', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('55a711a0-f1e2-488c-b887-86243deb5210', '51.19', '51.19', null, null, 1682, 'Osteopathic Medicine/Osteopathy (DO).', 'kuali.enum.type.cip2000',  '8ED176E62E5C4BF095EC5AF7D27887C6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d4519d35-a1c3-4f57-a9b8-4209c763e94b', '51.1901', '51.1901', null, null, 1683, 'Osteopathic Medicine/Osteopathy (DO).', 'kuali.enum.type.cip2000',  '8418DA09345E470F8E0AABC2CB440EAF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a341b16e-09ac-4775-a28d-7db2863856f8', '51.20', '51.20', null, null, 1684, 'Pharmacy, Pharmaceutical Sciences, and Administration.',  'kuali.enum.type.cip2000', 'F3A0919F7E2A4251B6F0C189FA7E7569', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a703b776-701f-4a5c-b53c-3e7836bf546a', '51.2001', '51.2001', null, null, 1685, 'Pharmacy (PharmD [USA], PharmD or BS/BPharm [Canada]).',  'kuali.enum.type.cip2000', '183CC5087C674DB39DE2F4B043C8DC5C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4fc69f27-63fa-49d0-9bca-16b03fb98cea', '51.2002', '51.2002', null, null, 1686, 'Pharmacy Administration and Pharmacy Policy and Regulatory Affairs (MS,  PhD).', 'kuali.enum.type.cip2000', 'A34F00E06BB64F57BF8D34C826A45F91', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1584f969-26bd-4a09-b7d1-e4893e2b1571', '51.2003', '51.2003', null, null, 1687, 'Pharmaceutics and Drug Design (MS, PhD).', 'kuali.enum.type.cip2000',  'F24F9E617D44470FB011014EF552AD32', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2862473c-e6a1-4719-b5c1-16a485ce9437', '51.2004', '51.2004', null, null, 1688, 'Medicinal and Pharmaceutical Chemistry (MS, PhD).', 'kuali.enum.type.cip2000',  '71BE026EB38E4EAE8DB2A61C19A1C8E5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9ba956d3-c7cb-4705-9460-6169720b4b56', '51.2005', '51.2005', null, null, 1689, 'Natural Products Chemistry and Pharmacognosy (MS, PhD).',  'kuali.enum.type.cip2000', '548C9BAA20E74B2CB321B1431A5880ED', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4cefcff5-2558-4547-924c-b9568f1d766b', '51.2006', '51.2006', null, null, 1690, 'Clinical and Industrial Drug Development (MS, PhD).',  'kuali.enum.type.cip2000', '021AF8584BD34099836D9B5171A42354', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e6b9344f-86f0-4cf0-a314-dbf244fdcfb6', '51.2007', '51.2007', null, null, 1691, 'Pharmacoeconomics/Pharmaceutical Economics (MS, PhD).',  'kuali.enum.type.cip2000', 'A7887B2A792648CA8071A656F31F33D5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5dd9de65-8ede-4c74-bf05-7ee8b0d50c41', '51.2008', '51.2008', null, null, 1692, 'Clinical, Hospital, and Managed Care Pharmacy (MS, PhD).',  'kuali.enum.type.cip2000', '805A955B71A5474C9DC3BABA13ECEB45', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7175eb18-6cb2-4063-a913-e861557f57b4', '51.2009', '51.2009', null, null, 1693, 'Industrial and Physical Pharmacy and Cosmetic Sciences (MS, PhD).',  'kuali.enum.type.cip2000', '4348A3973CC1423E8379BAED5BF4CD22', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a638708c-03e0-48f2-8483-269a4af00fa0', '51.2099', '51.2099', null, null, 1694, 'Pharmacy, Pharmaceutical Sciences, and Administration, Other.',  'kuali.enum.type.cip2000', 'C5163E4AFCB34AFCBCF86521D66DEFF9', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('334ead0a-f6cd-45ef-8f91-47a6c2cbb229', '51.21', '51.21', null, null, 1695, 'Podiatric Medicine/Podiatry (DPM).', 'kuali.enum.type.cip2000',  '1D92FB2AF55A480BB4E7A377E23B6FB5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3c618005-23e5-4c0f-ad16-589f8aae6c7e', '51.2101', '51.2101', null, null, 1696, 'Podiatric Medicine/Podiatry (DPM).', 'kuali.enum.type.cip2000',  '9C10F95B52424E328E4F309241AE56EE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c9b88d0d-76d5-4620-b7d1-728d1f3650d2', '51.22', '51.22', null, null, 1697, 'Public Health.', 'kuali.enum.type.cip2000', '49541DF9C43E46F1A5F5FD5A98045631', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b3d9b6cb-7b54-4895-9766-1e5bc3213ca1', '51.2201', '51.2201', null, null, 1698, 'Public Health, General (MPH, DPH).', 'kuali.enum.type.cip2000',  '79AC881BAD044F4F9F3D16DCEE26422A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1b3cf53e-22a2-4f7a-afa6-7c25aaa6f4be', '51.2202', '51.2202', null, null, 1699, 'Environmental Health.', 'kuali.enum.type.cip2000',  '65A070145E184A2EA517DF75E89402B5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e7e2e710-3744-4a73-949d-5750b395b0d3', '51.2203', '51.2203', null, null, 1700, 'Epidemiology.', 'kuali.enum.type.cip2000', '59CE5DA4A5994DBD8B9205FB20E809B9',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('251d4107-61cb-45b4-bb88-b5640832ac0e', '51.2204', '51.2204', null, null, 1701, 'Health and Medical Biostatistics.', 'kuali.enum.type.cip2000',  '5B3446D378114F7C87BA67395E0E87E2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d6ad6e16-01cd-4bae-9dd0-998dfff7e1ad', '51.2205', '51.2205', null, null, 1702, 'Health/Medical Physics.', 'kuali.enum.type.cip2000',  '3AA43D67A55A4D1CA86AAE683983CCA2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('22377a98-d525-4c35-9037-70ba435982c5', '51.2206', '51.2206', null, null, 1703, 'Occupational Health and Industrial Hygiene.', 'kuali.enum.type.cip2000',  '931E7BCC88CC4A9CA5D9B19D1440E254', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4b591da5-e9e9-493b-b699-22830d3015fa', '51.2207', '51.2207', null, null, 1704, 'Public Health Education and Promotion.', 'kuali.enum.type.cip2000',  '6DFF8AE647DD4CB0BF1C2EDAD8B6488C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('595e0036-1dd0-4ba2-85e1-6c2a1b9b7f7a', '51.2208', '51.2208', null, null, 1705, 'Community Health and Preventive Medicine.', 'kuali.enum.type.cip2000',  'CA24C54CF404430FB8B1CA83B75FEC8A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f427894c-7405-44c3-ad7f-7272b85c4892', '51.2209', '51.2209', null, null, 1706, 'Maternal and Child Health.', 'kuali.enum.type.cip2000',  '0918C8C4C3764E939E976C9B0BD7BF3C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('eb32ab41-8798-4345-82ab-176b3bb185f1', '51.2210', '51.2210', null, null, 1707, 'International Public Health/International Health.', 'kuali.enum.type.cip2000',  '3CF90B004437427A95C37DC841EC28A6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f1853c7d-1e9a-427d-957b-a593180c5772', '51.2211', '51.2211', null, null, 1708, 'Health Services Administration.', 'kuali.enum.type.cip2000',  '0CB3748F900E43A498AEE4A0B13918C3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7c8e1b06-49f4-46b3-97b5-4c9fe6081084', '51.2299', '51.2299', null, null, 1709, 'Public Health, Other.', 'kuali.enum.type.cip2000',  'DA9E1E82B6554F6E9E301E440461EA35', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8d76f4c8-a6c9-4804-8fd9-9edfc47f6967', '51.23', '51.23', null, null, 1710, 'Rehabilitation and Therapeutic Professions.', 'kuali.enum.type.cip2000',  'B5C69BF1FB864ECF8EB03A59C868565B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0e355f01-970c-4eca-89f4-7b66ce9090f7', '51.2301', '51.2301', null, null, 1711, 'Art Therapy/Therapist.', 'kuali.enum.type.cip2000',  'A51BC98D26A042D18A2793BC8C20D408', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('97b522a5-f0ae-428d-9be5-ad585f7068e2', '51.2302', '51.2302', null, null, 1712, 'Dance Therapy/Therapist.', 'kuali.enum.type.cip2000',  'D1C4CDCC66874CB6AE8D7F89AC5D8B29', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('71739af2-5c4d-4598-9d92-e8ecd9f28cac', '51.2303', '51.2303', null, null, 1713, 'Hypnotherapy.', 'kuali.enum.type.cip2000', '9F97144E43F34641978071BA0FABC6B0',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('cf6e2b9a-822e-456a-b0d5-07fe6a933d39', '51.2304', '51.2304', null, null, 1714, 'Movement Therapy.', 'kuali.enum.type.cip2000',  'C281F3D301DF4600B6E54DC403240A6C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('cfdbba59-aa46-42bf-98ea-ec8f39e71882', '51.2305', '51.2305', null, null, 1715, 'Music Therapy/Therapist.', 'kuali.enum.type.cip2000',  'E4AFDA8E78974ECF8F5D6880823E0B16', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5f5f8f48-6ad5-4594-953b-7ee3456588f1', '51.2306', '51.2306', null, null, 1716, 'Occupational Therapy/Therapist.', 'kuali.enum.type.cip2000',  '05F27D452E1E46C4A2975D8F0B1D669A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('cd7e518a-b7a9-4219-8c76-b8d5135f0f6d', '51.2307', '51.2307', null, null, 1717, 'Orthotist/Prosthetist.', 'kuali.enum.type.cip2000',  '05781F87197F4C8985C3B6979C2FFCB3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f7c5f7b3-49bc-4e0d-9768-c3c78b8a1fc9', '51.2308', '51.2308', null, null, 1718, 'Physical Therapy/Therapist.', 'kuali.enum.type.cip2000',  '71D728F8C4D14D809185BA8DEC66439B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b5a5f77c-35b7-41df-8a30-35cfc0291319', '51.2309', '51.2309', null, null, 1719, 'Therapeutic Recreation/Recreational Therapy.', 'kuali.enum.type.cip2000',  '419E932F507F42809FDFFC37B6BB6081', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ca08fec0-c221-40b3-a5e3-5f30d4363030', '51.2310', '51.2310', null, null, 1720, 'Vocational Rehabilitation Counseling/Counselor.', 'kuali.enum.type.cip2000',  'B02D838B015E40CBB57B45334015FFF8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a133acb1-79dd-4d17-a3f4-6bc52e4cd84f', '51.2311', '51.2311', null, null, 1721, 'Kinesiotherapy/Kinesiotherapist.', 'kuali.enum.type.cip2000',  '2D9171D9A59A4CB4A9D2688C382DA2E3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bdb54cf6-1507-4a66-9df4-8cf7959304f3', '51.2312', '51.2312', null, null, 1722, 'Assistive/Augmentative Technology and Rehabiliation Engineering.',  'kuali.enum.type.cip2000', 'CB419BD547844B1E9C870B335A66C3C1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('58663b31-146f-4dbe-a856-219ac611bfd4', '51.2399', '51.2399', null, null, 1723, 'Rehabilitation and Therapeutic Professions, Other.',  'kuali.enum.type.cip2000', 'A2A0FC89EE9A471381D8BB0F2A3B6333', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bbafea1d-62f7-44c0-85d1-ff222a8802dc', '51.24', '51.24', null, null, 1724, 'Veterinary Medicine (DVM).', 'kuali.enum.type.cip2000',  'C187832DA6D14E5C92BC22934BC1EC03', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ddcc5e82-0607-4c2e-af97-486136a21f27', '51.2401', '51.2401', null, null, 1725, 'Veterinary Medicine (DVM).', 'kuali.enum.type.cip2000',  '94B26A06C4BD493F8BBE0C4E25C50C15', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ae5eae72-b8b7-4a84-aba8-63b62e331fc7', '51.25', '51.25', null, null, 1726, 'Veterinary Biomedical and Clinical Sciences (Cert.', 'kuali.enum.type.cip2000',  '31BA0104536E4327826E29BC152E58DC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9604128f-1847-4631-b5d8-c94ac4e8cdaf', '51.2501', '51.2501', null, null, 1727, 'Veterinary Sciences/Veterinary Clinical Sciences, General (Cert.',  'kuali.enum.type.cip2000', 'E34CF93084F8494B80AD104C6C74AA3F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('908cae63-9f9d-43d2-8ca4-fccbb8c2479d', '51.2502', '51.2502', null, null, 1728, 'Veterinary Anatomy (Cert.', 'kuali.enum.type.cip2000',  '90AAD21D672F42EF96131C82225020BF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5532ddff-58d5-4cf6-816d-30dd9c716f43', '51.2503', '51.2503', null, null, 1729, 'Veterinary Physiology (Cert.', 'kuali.enum.type.cip2000',  '63339B143613476FACF6E49B69D78F29', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e227b330-98ce-4513-a22f-f9d8f13729e6', '51.2504', '51.2504', null, null, 1730, 'Veterinary Microbiology and Immunobiology (Cert.', 'kuali.enum.type.cip2000',  'E5C38587831A49218065330CAB5F5A78', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('50e0d903-8fb0-4e47-a129-ba1b68f31c01', '51.2505', '51.2505', null, null, 1731, 'Veterinary Pathology and Pathobiology (Cert.', 'kuali.enum.type.cip2000',  'BB11B45895A1448FB44EC66E73F35D45', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3f62c67b-e613-4ebb-a74a-35e96f0e1bd3', '51.2506', '51.2506', null, null, 1732, 'Veterinary Toxicology and Pharmacology (Cert.', 'kuali.enum.type.cip2000',  '46209F75DC6E4E70A9A3EC0B9AA955F4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a37d95e8-aa14-4d1c-8877-5a4d93bfb72e', '51.2507', '51.2507', null, null, 1733, 'Large Animal/Food Animal and Equine Surgery and Medicine (Cert.',  'kuali.enum.type.cip2000', '59D019CF2AEA405EB24FC3DBAAEBC48C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4c39e60d-a461-4c69-b82f-4c7177038317', '51.2508', '51.2508', null, null, 1734, 'Small/Companion Animal Surgery and Medicine (Cert.',  'kuali.enum.type.cip2000', '508868D0EA814225BF8BB121285DE2E8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('14777abe-cecd-4f4a-a65d-e0691d37a2c5', '51.2509', '51.2509', null, null, 1735, 'Comparative and Laboratory Animal Medicine (Cert.', 'kuali.enum.type.cip2000',  '26148CC1DFB4461C9E010F6D2288C2B3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5754b0f9-e09d-424c-8e1a-1813ab7706b4', '51.2510', '51.2510', null, null, 1736, 'Veterinary Preventive Medicine Epidemiology, and Public Health (Cert.',  'kuali.enum.type.cip2000', '1A0996B9F62441A9BAD17DE9F836268C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('28bb49c6-aa9a-431c-98ee-3797103c62d6', '51.2511', '51.2511', null, null, 1737, 'Veterinary Infectious Diseases (Cert.', 'kuali.enum.type.cip2000',  '35D4AEDD551A4424A5D8BF6498542C7F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('19d708ed-8c22-43b5-9a45-dabee761d9cb', '51.2599', '51.2599', null, null, 1738, 'Veterinary Biomedical and Clinical Sciences, Other (Cert.',  'kuali.enum.type.cip2000', 'DABE5DCCDCC249BFA81B9D2297684B8F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('30668c5f-76d0-4c7b-8155-45b973b93177', '51.26', '51.26', null, null, 1739, 'Health Aides/Attendants/Orderlies.', 'kuali.enum.type.cip2000',  '3C26839D9DBD4F07B33F2855AB451344', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d188eda5-672a-4761-8f80-2cab9ef447f8', '51.2601', '51.2601', null, null, 1740, 'Health Aide.', 'kuali.enum.type.cip2000', '6E66444EEF99466DBF9856D4E5999524',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b3ea0468-09d2-4f6a-99e8-23a9436a383a', '51.2602', '51.2602', null, null, 1741, 'Home Health Aide/Home Attendant.', 'kuali.enum.type.cip2000',  'EF665310C24C4ACFB0AE06E49026D810', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f2fbcfd5-beec-4999-8436-30f4a865b0c1', '51.2603', '51.2603', null, null, 1742, 'Medication Aide.', 'kuali.enum.type.cip2000',  '39C2280C428B4F509CF42B58769646F4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('37bb8518-86d7-4d7b-ba5b-17ff8ba1ba1e', '51.2699', '51.2699', null, null, 1743, 'Health Aides/Attendants/Orderlies, Other.', 'kuali.enum.type.cip2000',  '1C3BA247E28A4F8C9F20A530EC765E6E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bc0acfab-7d78-4c32-8ce5-11e091c89f9b', '51.27', '51.27', null, null, 1744, 'Medical Illustration and Informatics.', 'kuali.enum.type.cip2000',  'D55F8F33C8CC497388EE364158E8546C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('68cbf921-8674-49e9-91f2-f4d4ac3018e1', '51.2701', '51.2701', null, null, 1745, 'Acupuncture and Oriental Medicine.', 'kuali.enum.type.cip2000',  'CE7B378827594BECA80A82A1D7624BB7', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('585f08d2-d88e-4f36-a696-25a7ab7727d3', '51.2702', '51.2702', null, null, 1746, 'Medical Dietitian.', 'kuali.enum.type.cip2000',  'EB6D0D48189C405E8B954FA98F1AEA43', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f034900f-eb2b-46d9-863d-f7ac78164994', '51.2703', '51.2703', null, null, 1747, 'Medical Illustration/Medical Illustrator.', 'kuali.enum.type.cip2000',  '106177C0E38E4285851261FF72CDE0CB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ee11dad6-4fa0-426d-ac56-3d7277303dac', '51.2704', '51.2704', null, null, 1748, 'Naturopathic Medicine.', 'kuali.enum.type.cip2000',  '1D4B8DF98E984AFC9BBB8E81141247B3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7cc9c3c3-52ec-4d62-b8bc-9b9b677a8343', '51.2705', '51.2705', null, null, 1749, 'Psychoanalysis.', 'kuali.enum.type.cip2000',  '127974FB799D4600976A61AB67DE0C6E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2b82c1e4-ad70-4552-9c9e-c11fafe1d841', '51.2706', '51.2706', null, null, 1750, 'Medical Informatics.', 'kuali.enum.type.cip2000',  'D57DEB844F9B46EC9FF8CC9353B59ACC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('331d3720-cc85-4656-86ad-cac816db3aa0', '51.2799', '51.2799', null, null, 1751, 'Medical Illustration and Informatics, Other.', 'kuali.enum.type.cip2000',  '7FD1E5DA50B440A385E332D49D8E0208', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d7689c9c-4e9f-4a31-9990-a724cbef8b43', '51.31', '51.31', null, null, 1752, 'Dietetics and Clinical Nutrition Services.', 'kuali.enum.type.cip2000',  '8459B39297B94A58ADC1C6ACAAC4D237', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('885361bb-606b-4e84-b192-9497f72f4a41', '51.3101', '51.3101', null, null, 1753, 'Dietetics/Dietitian (RD).', 'kuali.enum.type.cip2000',  '0FC862FB52CF47E5BD4E74960561E2BC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('21af8555-8edc-4e5a-9746-a94ff48ee6cf', '51.3102', '51.3102', null, null, 1754, 'Clinical Nutrition/Nutritionist.', 'kuali.enum.type.cip2000',  '3ED59A8EE18847A5AF1D08D456DB433F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('92625731-e075-4681-8652-21a6c1d281c4', '51.3103', '51.3103', null, null, 1755, 'Dietetic Technician (DTR).', 'kuali.enum.type.cip2000',  '3A413C8426164B6DB20087D16C5C1355', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('de80e118-a711-4827-ab09-630b5f5d767c', '51.3104', '51.3104', null, null, 1756, 'Dietitian Assistant.', 'kuali.enum.type.cip2000',  'A5489A11B48E410F88F0B66942B3F0DE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0f5ecfeb-e939-493e-9a60-0cb2b2e86658', '51.3199', '51.3199', null, null, 1757, 'Dietetics and Clinical Nutrition Services, Other.', 'kuali.enum.type.cip2000',  '458BFEEABE2D4F66B56BA7C67AA8C022', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d6b137fb-6088-47f9-9fb0-00a5d7c8abd4', '51.32', '51.32', null, null, 1758, 'Bioethics/Medical Ethics.', 'kuali.enum.type.cip2000',  'D6B5C41EFAB74623810FEAB85E73EDF0', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f9eb7edd-c073-49b7-a9f7-ddd43c7daed9', '51.3201', '51.3201', null, null, 1759, 'Bioethics/Medical Ethics.', 'kuali.enum.type.cip2000',  '7D375D2DB5364ABE8B608EA53CA5B3CF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('87e454fe-0456-4517-a60c-8df8af1673ec', '51.33', '51.33', null, null, 1760, 'Alternative and Complementary Medicine and Medical Systems.',  'kuali.enum.type.cip2000', 'E1F739445D1B491AA56BFB56D6FCC223', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1bf4501b-1fee-4353-88f5-b223d0aa4a2b', '51.3301', '51.3301', null, null, 1761, 'Acupuncture.', 'kuali.enum.type.cip2000', 'CE82D4545A0048B6AE295D9F63B1028F',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9025f6e1-66ba-4df4-8f30-f0b36457719a', '51.3302', '51.3302', null, null, 1762, 'Traditional Chinese/Asian Medicine and Chinese Herbology.',  'kuali.enum.type.cip2000', '5E6EE738DD4F4DAC9F60D26C0A0DD870', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a51afe5a-805c-477a-abd3-473af07f2409', '51.3303', '51.3303', null, null, 1763, 'Naturopathic Medicine/Naturopathy (ND).', 'kuali.enum.type.cip2000',  '2C75B5B83D3D491181333C52F592481B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('68d03e48-201b-49bf-9669-8a16bc1d67ea', '51.3304', '51.3304', null, null, 1764, 'Homeopathic Medicine/Homeopathy.', 'kuali.enum.type.cip2000',  '256585F068D34500896A6554CFDAB98B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('69d2d534-63c5-4ae0-9087-fca17dcd1605', '51.3305', '51.3305', null, null, 1765, 'Ayurvedic Medicine/Ayurveda.', 'kuali.enum.type.cip2000',  'C125CB1CC44D4A49AFB7BEA4A3136180', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e430f3b1-9be8-4d23-9af8-a8721a7e112c', '51.3399', '51.3399', null, null, 1766, 'Alternative and Complementary Medicine and Medical Systems, Other.',  'kuali.enum.type.cip2000', '7C0682EE0E7B411C926DCB4A641BDFFA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7aa76fdb-bdf2-458e-8faa-0430778675ed', '51.34', '51.34', null, null, 1767, 'Alternative and Complementary Medical Support Services.',  'kuali.enum.type.cip2000', 'AB0F733FBA4748BCABCDB3E9A50B5F08', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6836b0be-a74c-4e02-9423-b03f1ecd1292', '51.3401', '51.3401', null, null, 1768, 'Direct Entry Midwifery (LM, CPM).', 'kuali.enum.type.cip2000',  'ABD5C01651F84F92A892D5B4AA9A067F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('abb594ab-bc69-4e0a-ba02-37ae6dc8391a', '51.3499', '51.3499', null, null, 1769, 'Alternative and Complementary Medical Support Services, Other.',  'kuali.enum.type.cip2000', '9EE641EA1CC34C9AB286EE1FC471C407', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('fa88059e-240d-4ee9-aef8-04cc11e53831', '51.35', '51.35', null, null, 1770, 'Somatic Bodywork and Related Therapeutic Services.', 'kuali.enum.type.cip2000',  '75CB27B3394E4E0992EA2B4BD70A24F2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b82dee6f-eff9-46f6-8a6b-9f4a625eb62f', '51.3501', '51.3501', null, null, 1771, 'Massage Therapy/Therapeutic Massage.', 'kuali.enum.type.cip2000',  'DE2B9F526761446589BFD10202E86AD1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('61628f1c-12be-420b-a025-db938dc5fcd0', '51.3502', '51.3502', null, null, 1772, 'Asian Bodywork Therapy.', 'kuali.enum.type.cip2000',  '2FC4BE43CADB49F2A625BD19CEEC76EF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('198b0279-36cd-4512-8f99-60b3774c6be0', '51.3503', '51.3503', null, null, 1773, 'Somatic Bodywork.', 'kuali.enum.type.cip2000',  '9A3F825B62F9428BA02D0F014AFDA0EA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('005d8f8b-ffcc-4ed9-94e8-ca179cab9d4c', '51.3599', '51.3599', null, null, 1774, 'Somatic Bodywork and Related Therapeutic Services, Other.',  'kuali.enum.type.cip2000', '837220B7285C46019F83571E2F91491B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6d43d65d-4deb-43b1-a658-11d565efdee1', '51.36', '51.36', null, null, 1775, 'Movement and Mind-Body Therapies and Education.', 'kuali.enum.type.cip2000',  '76ECF6E2524E4623BA484EDB18F3DD3A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f97dd83c-86fe-4afd-9b30-b44d9b231411', '51.3601', '51.3601', null, null, 1776, 'Movement Therapy and Movement Education.', 'kuali.enum.type.cip2000',  '63227C718B844688A04FD150A0AC5368', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bd818d47-eb0e-405c-af63-5eacb2ca4444', '51.3602', '51.3602', null, null, 1777, 'Yoga Teacher Training/Yoga Therapy.', 'kuali.enum.type.cip2000',  '35E5A3871BAB46C3A822BA67BCD0F3FC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f2457024-1762-4297-b283-93f6efb9fb55', '51.3603', '51.3603', null, null, 1778, 'Hypnotherapy/Hypnotherapist.', 'kuali.enum.type.cip2000',  '25B216ADE8CC4FA986ECE410A82C8681', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('514c23e4-7ce7-4bb7-9ad0-3d354f3dfc70', '51.3699', '51.3699', null, null, 1779, 'Movement and Mind-Body Therapies and Education, Other.',  'kuali.enum.type.cip2000', '54D106C5E0F64F14B43A73D710A948FF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('03b7344e-2284-4719-89de-e04bb2cd95fb', '51.37', '51.37', null, null, 1780, 'Energy and Biologically Based Therapies.', 'kuali.enum.type.cip2000',  '94F54A1031FF4A9F8A60C02163BE8429', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('083da482-dd69-49f7-bad4-4a7242f51b45', '51.3701', '51.3701', null, null, 1781, 'Aromatherapy.', 'kuali.enum.type.cip2000', '7820B1512B7E494E9BA3C8AF20380B17',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a241c108-43fc-4bd8-8f0f-47f747411367', '51.3702', '51.3702', null, null, 1782, 'Herbalism/Herbalist.', 'kuali.enum.type.cip2000',  '299941C568D64DDFAE9F7F14A985735B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('cc273033-0ffb-43fd-a475-8ccf8b13a4e6', '51.3703', '51.3703', null, null, 1783, 'Polarity Therapy.', 'kuali.enum.type.cip2000',  'E16D4A1C442A4BCABF1C25AF51307F63', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('11016b4e-cf07-4b04-8768-7f60024f011c', '51.3704', '51.3704', null, null, 1784, 'Reiki.', 'kuali.enum.type.cip2000', 'E0367A427C454A6F94AF8B4ECE701E62', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4d93e945-94f3-4979-b8f3-55bca2e273ae', '51.3799', '51.3799', null, null, 1785, 'Energy and Biologically Based Therapies, Other.', 'kuali.enum.type.cip2000',  '58DDD8740B3C42EDAEB0CAA14C851C4D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('fcf25b46-b162-4e01-b4f2-f42c9d231c16', '51.99', '51.99', null, null, 1786, 'Health Professions and Related Clinical Sciences, Other.',  'kuali.enum.type.cip2000', '204FCE29E37342EBB87C6E27714421BA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bf962765-7cde-458f-9d78-975c971cf501', '51.9999', '51.9999', null, null, 1787, 'Health Professions and Related Clinical Sciences, Other.',  'kuali.enum.type.cip2000', 'E84DE60C07D947F3B20DFE4D01580BEA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4744e90f-3e82-468f-b554-edaad13a29b6', '52.', '52.', null, null, 1788, 'BUSINESS, MANAGEMENT, MARKETING, AND RELATED SUPPORT SERVICES.',  'kuali.enum.type.cip2000', 'D3889A90126E461AAD53F4F549263DDA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9ca6e745-8014-4bab-8b92-cfa144441863', '52.01', '52.01', null, null, 1789, 'Business/Commerce, General.', 'kuali.enum.type.cip2000',  '0BED41135D904170AAC7B3EA008CB6E5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9aed3040-549d-40b1-b060-8c49a653ca2f', '52.0101', '52.0101', null, null, 1790, 'Business/Commerce, General.', 'kuali.enum.type.cip2000',  '09E1FAD0351F486FA28A21C5889D2839', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ccc43e78-7f11-4c71-9fd5-758022a9217f', '52.02', '52.02', null, null, 1791, 'Business Administration, Management and Operations.', 'kuali.enum.type.cip2000',  'E393500B59354F5188FE97548DDABD55', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('82412072-ca30-485f-a431-1a6ecccad03a', '52.0201', '52.0201', null, null, 1792, 'Business Administration and Management, General.', 'kuali.enum.type.cip2000',  'A14EFAB9AC3242F096B9323AB9E26965', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('cb6fea79-a432-4eae-9bb9-126e3d813f0d', '52.0202', '52.0202', null, null, 1793, 'Purchasing, Procurement/Acquisitions and Contracts Management.',  'kuali.enum.type.cip2000', 'E9247FD9DFD5490DA0AD7E78EAA6A531', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('43ebed7d-8840-49a0-bca5-e8c2e558fac1', '52.0203', '52.0203', null, null, 1794, 'Logistics and Materials Management.', 'kuali.enum.type.cip2000',  '46CDF85870ED4C8C8A88C966079AEA45', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('89d461f5-7bb1-4cbb-af2a-aeb641586241', '52.0204', '52.0204', null, null, 1795, 'Office Management and Supervision.', 'kuali.enum.type.cip2000',  'EF6EA008D183423E98D69DC77BB6F502', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('08b30095-d1b0-4aac-99e1-7bbd7192eec7', '52.0205', '52.0205', null, null, 1796, 'Operations Management and Supervision.', 'kuali.enum.type.cip2000',  '49DA0BA5EBB242E28D87C8D9BC6E2C4E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ce7b281b-8444-4faa-aa0d-8e2a73f9d30b', '52.0206', '52.0206', null, null, 1797, 'Non-Profit/Public/Organizational Management.', 'kuali.enum.type.cip2000',  '9BC11ED381FC4736BD383A8792B63F13', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8bec1ea6-09f5-488c-9c3f-5056e8e15c30', '52.0207', '52.0207', null, null, 1798, 'Customer Service Management.', 'kuali.enum.type.cip2000',  '8B27880E6C4F426BAD66349909F528B1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7e25f76b-d951-42a7-9e0f-bdaf6923d439', '52.0208', '52.0208', null, null, 1799, 'E-Commerce/Electronic Commerce.', 'kuali.enum.type.cip2000',  'CD064B1C137A40FCBE45F7BC80602B49', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8e4b1d48-9267-4fad-8db2-e22baad1b4cd', '52.0209', '52.0209', null, null, 1800, 'Transportation/Transportation Management.', 'kuali.enum.type.cip2000',  '7A591177E87B41B2BE06C7D5789E35BE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('17f07fc6-2422-4d28-842a-1c6141276a8f', '52.0299', '52.0299', null, null, 1801, 'Business Administration, Management and Operations, Other.',  'kuali.enum.type.cip2000', 'BC44A5D468D74FFAB01DE5A9A2A25EE6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ae62f426-8dc1-47f8-8b18-53106a4ef5e4', '52.03', '52.03', null, null, 1802, 'Accounting and Related Services.', 'kuali.enum.type.cip2000',  'F3BA4A98AC3E47F19519AD34F13BC0A3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1a24cd73-62e0-4ce4-af47-c76291028c18', '52.0301', '52.0301', null, null, 1803, 'Accounting.', 'kuali.enum.type.cip2000', 'EB0C705C6C364E908A2341F1E6BEF8D3',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2d532739-ffcf-492b-9868-2dabc7c6c6d9', '52.0302', '52.0302', null, null, 1804, 'Accounting Technology/Technician and Bookkeeping.', 'kuali.enum.type.cip2000',  'C85A34577B8540E9A8FC70E74F412064', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e8df8df1-bff5-4b2b-b5c6-49dcbf79d9ad', '52.0303', '52.0303', null, null, 1805, 'Auditing.', 'kuali.enum.type.cip2000', '3CE8BD5CF37B446980B29FD7B7B55D80', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0e929d26-162e-44ab-82fd-6e2dd6ac37d6', '52.0304', '52.0304', null, null, 1806, 'Accounting and Finance.', 'kuali.enum.type.cip2000',  '5CD3D2B0100648F09F101ADC298ED16A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('463858f0-5db3-479f-89f0-04d415a0425f', '52.0305', '52.0305', null, null, 1807, 'Accounting and Business/Management.', 'kuali.enum.type.cip2000',  'D0334E500C284C86AF2621A86F8718E6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('777dc4af-9480-42f1-a5b6-772fb844c0bb', '52.0399', '52.0399', null, null, 1808, 'Accounting and Related Services, Other.', 'kuali.enum.type.cip2000',  'DDBCB941A417474B8D42EFD7D1D46300', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('df453d10-f853-4cca-bbbb-24495f49797f', '52.04', '52.04', null, null, 1809, 'Business Operations Support and Assistant Services.', 'kuali.enum.type.cip2000',  'A183F92D871A41EE8A8BB9FD9BA4F7A6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('45044ca0-9380-40c6-ac82-08b0c85f729a', '52.0401', '52.0401', null, null, 1810, 'Administrative Assistant and Secretarial Science, General.',  'kuali.enum.type.cip2000', '2B60E8906D1F469DBA27975568EB4395', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f5b355dc-fc33-4887-97db-3a1c68f9fa66', '52.0402', '52.0402', null, null, 1811, 'Executive Assistant/Executive Secretary.', 'kuali.enum.type.cip2000',  '1111DE0B2AAC4960A35BACF5BF66BE99', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('27fbed6b-1f27-48a9-bef6-be9463e2e6e5', '52.0403', '52.0403', null, null, 1812, 'Legal Administrative Assistant/Secretary.', 'kuali.enum.type.cip2000',  '83F37583E19F41F598606C7C351CACB6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('76f32711-3809-452d-a91e-b6bf6658d523', '52.0404', '52.0404', null, null, 1813, 'Medical Secretary.', 'kuali.enum.type.cip2000',  '0051BAA293CC4A4EBB6BD4625E38000F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('73c69825-0d8f-4bcf-9aee-13db5ec07a24', '52.0405', '52.0405', null, null, 1814, 'Court Reporting/Court Reporter.', 'kuali.enum.type.cip2000',  '23F5AB866FB7463ABE17FDC67A25453E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a5988b16-f36e-4320-9b12-815546051c08', '52.0406', '52.0406', null, null, 1815, 'Receptionist.', 'kuali.enum.type.cip2000', '0192B62DAA01432A89C7455469E9E190',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a40d6923-bf1d-45be-92e9-db8cd60b4c4e', '52.0407', '52.0407', null, null, 1816, 'Business/Office Automation/Technology/Data Entry.', 'kuali.enum.type.cip2000',  '01174EC418084049AD9EBE9E29AA3E68', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e5216931-4a4f-497e-a3f2-0329c5a9a52f', '52.0408', '52.0408', null, null, 1817, 'General Office Occupations and Clerical Services.', 'kuali.enum.type.cip2000',  '710D8EC03BE0496B9FA39F79D1209C68', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('cdc8a998-098c-407f-8838-4d5d00574d72', '52.0409', '52.0409', null, null, 1818, 'Parts, Warehousing, and Inventory Management Operations.',  'kuali.enum.type.cip2000', '0E48680385BC4851B0A5552616459CD8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a3c44c42-f91d-4cf3-8b0e-20c5c2165eb4', '52.0410', '52.0410', null, null, 1819, 'Traffic, Customs, and Transportation Clerk/Technician.',  'kuali.enum.type.cip2000', '521C231722B64C97B18BE3F0E7F68C38', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('40627f7a-0936-4334-a860-b36b3dfb359e', '52.0411', '52.0411', null, null, 1820, 'Customer Service Support/Call Center/Teleservice Operation.',  'kuali.enum.type.cip2000', 'E16CEF927CD94A3C93A68B3F8292A382', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('768b9c00-08ce-4551-a65a-f58ca3ff8bad', '52.0499', '52.0499', null, null, 1821, 'Business Operations Support and Secretarial Services, Other.',  'kuali.enum.type.cip2000', '57575CFE511F4A2494CD62C76A91F0A7', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('be54c937-0b24-43aa-8313-b1f4253559bb', '52.05', '52.05', null, null, 1822, 'Business/Corporate Communications.', 'kuali.enum.type.cip2000',  '18577944AE814376924AAB2F008D1178', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('725aceb0-4269-440b-aa10-d27fdb3050ce', '52.0501', '52.0501', null, null, 1823, 'Business/Corporate Communications.', 'kuali.enum.type.cip2000',  'B85429E34B0345D092D280660AB9B476', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('72290304-5112-4b11-aef0-8a9b85f1a1c2', '52.06', '52.06', null, null, 1824, 'Business/Managerial Economics.', 'kuali.enum.type.cip2000',  '185766D4CCDD4EBE9958DCBBD6206A70', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b08cc48c-1cb2-446a-848c-57c57fce94e8', '52.0601', '52.0601', null, null, 1825, 'Business/Managerial Economics.', 'kuali.enum.type.cip2000',  'E6AF66331CEC4C61877848CE5D92235F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ff0b58fe-597b-4b54-85b4-dd07978385b0', '52.07', '52.07', null, null, 1826, 'Entrepreneurial and Small Business Operations.', 'kuali.enum.type.cip2000',  '9211CE2FDF524756A4DBE45B8DBD5C06', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('428732d3-c80f-4a2a-a53d-b088fe69aab8', '52.0701', '52.0701', null, null, 1827, 'Entrepreneurship/Entrepreneurial Studies.', 'kuali.enum.type.cip2000',  '5DA9C3FE9EEB46549A21C83AE0A492CA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('42acf28c-cb14-4d33-a8e4-7ce4b45d3738', '52.0702', '52.0702', null, null, 1828, 'Franchising and Franchise Operations.', 'kuali.enum.type.cip2000',  '0A1C71264F3A46EC9ABD5C3EDAD2C4DD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b698e920-e14f-48f2-aec6-06c6a8e08d82', '52.0703', '52.0703', null, null, 1829, 'Small Business Administration/Management.', 'kuali.enum.type.cip2000',  '0001C7DBA35B4CE5BE0F25DFC423DC66', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6b3a3749-e8f0-4d6b-8fb8-e33590c46754', '52.0799', '52.0799', null, null, 1830, 'Entrepreneurial and Small Business Operations, Other.',  'kuali.enum.type.cip2000', '37C1FB3801DA4119AA437F4515C8A04C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ca301e14-70ac-48a1-b936-f6da36aa3666', '52.08', '52.08', null, null, 1831, 'Finance and Financial Management Services.', 'kuali.enum.type.cip2000',  '942C608CD8204E7A99EC170F849A8F3A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f3747003-6b37-4e39-8017-ed938e7b5632', '52.0801', '52.0801', null, null, 1832, 'Finance, General.', 'kuali.enum.type.cip2000',  '840CE89C1E144BC9B58E0430E2FCCAE2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('aa4a7de1-17a3-4b77-938d-9da8fc0bfde3', '52.0802', '52.0802', null, null, 1833, 'Actuarial Science.', 'kuali.enum.type.cip2000',  'D0FC43B4BDBF4F0FAAF3DD843DE71578', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c6d25269-53e7-4955-a4e4-d8501c5ff775', '52.0803', '52.0803', null, null, 1834, 'Banking and Financial Support Services.', 'kuali.enum.type.cip2000',  '6A0FE1112282441B91DAB464224FEF90', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7c6ece43-f2bd-4a6b-92f2-343863ac21a4', '52.0804', '52.0804', null, null, 1835, 'Financial Planning and Services.', 'kuali.enum.type.cip2000',  '87CFBE4FD36D419DA525CAFD86D24806', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ae383537-c215-4ab3-9202-da392dd41ee6', '52.0805', '52.0805', null, null, 1836, 'Insurance and Risk Management.', 'kuali.enum.type.cip2000',  '4EAD26BC1A374DDB805FA1BEBB7BCB39', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8279300b-bbc5-4c72-b62d-d2b5338a3715', '52.0806', '52.0806', null, null, 1837, 'International Finance.', 'kuali.enum.type.cip2000',  '1E3D07D5E0414917B179E01994BC7EFD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d932abcc-87d5-4f6a-aa9c-ba1d3684eb3d', '52.0807', '52.0807', null, null, 1838, 'Investments and Securities.', 'kuali.enum.type.cip2000',  '40B8EDA27BCD4EB4BA01006C60598B7F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c386b7f3-5ee2-4567-9d8f-abe139a523f1', '52.0808', '52.0808', null, null, 1839, 'Public Finance.', 'kuali.enum.type.cip2000',  'CE9FE4F22E404AEE935E5636AEAC96D0', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4b9357a3-53e5-488c-a8d5-7fbecb640c82', '52.0809', '52.0809', null, null, 1840, 'Credit Management.', 'kuali.enum.type.cip2000',  '895D2A81008B4341B4BCD52B3B91E8C1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('995baf4c-a1ef-4352-89af-025c7d573c8d', '52.0899', '52.0899', null, null, 1841, 'Finance and Financial Management Services, Other.', 'kuali.enum.type.cip2000',  '577461BFEBE94B02B606CED96EFDA3C3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b8b48560-750b-4746-8550-7b255c18bdfb', '52.09', '52.09', null, null, 1842, 'Hospitality Administration/Management.', 'kuali.enum.type.cip2000',  '886737219AC742779C425DE41F6EE0BF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7066cba0-28a5-472b-9425-e2ba481ce1a3', '52.0901', '52.0901', null, null, 1843, 'Hospitality Administration/Management, General.', 'kuali.enum.type.cip2000',  'B21CD9AE8EAF476CB831FFB9D49EA606', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('43689423-34cd-42b0-89bd-5af98959922f', '52.0902', '52.0902', null, null, 1844, 'Hotel, Motel, and Restaurant Management.', 'kuali.enum.type.cip2000',  '3E5567EDFB7545189A2CD4EB8BDFC2BC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f0928b7a-3637-4a4d-aba5-c624fed5baeb', '52.0903', '52.0903', null, null, 1845, 'Tourism and Travel Services Management.', 'kuali.enum.type.cip2000',  '70BF8999BB1C410E85C8B75F862EE778', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7bba3623-e3de-4907-8980-d7cb2b1a3049', '52.0904', '52.0904', null, null, 1846, 'Hotel/Motel Administration/Management.', 'kuali.enum.type.cip2000',  'E83431125803467AA30D97EB47567810', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bce41d4f-85ca-4488-9cb2-8341b6686d73', '52.0905', '52.0905', null, null, 1847, 'Restaurant/Food Services Management.', 'kuali.enum.type.cip2000',  '3C6816A64E2A48599240CA2085E62545', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('03e6a15b-6885-44c8-ad53-5210b474e804', '52.0906', '52.0906', null, null, 1848, 'Resort Management.', 'kuali.enum.type.cip2000',  'F23A69EFA4CC42B7B67D146B628809BA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d47561a5-821c-4a2b-bb91-97fc039649e3', '52.0999', '52.0999', null, null, 1849, 'Hospitality Administration/Management, Other.', 'kuali.enum.type.cip2000',  '6E25C51114C34458A8F147F233616CE6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c85797cc-904d-442a-890c-fe2a9eb7a0e4', '52.10', '52.10', null, null, 1850, 'Human Resources Management and Services.', 'kuali.enum.type.cip2000',  '78770173ABCB457B82DA22E6B8E66A1B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('72135853-329c-4078-93cc-0c708ce323c9', '52.1001', '52.1001', null, null, 1851, 'Human Resources Management/Personnel Administration, General.',  'kuali.enum.type.cip2000', '642100BB985849FCA8A832A9441B243B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bcf84155-c7a1-4407-9075-0f24f5fe1d20', '52.1002', '52.1002', null, null, 1852, 'Labor and Industrial Relations.', 'kuali.enum.type.cip2000',  '44115172FA8446B0963DC9ED8284A926', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8d684653-9f22-4bab-b69d-43f475c9d15e', '52.1003', '52.1003', null, null, 1853, 'Organizational Behavior Studies.', 'kuali.enum.type.cip2000',  '72ECFD71EEFE4591A0AC3B9E75BC3224', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b347e3b5-c3bf-4635-9988-2c3829f65d97', '52.1004', '52.1004', null, null, 1854, 'Labor Studies.', 'kuali.enum.type.cip2000',  'E8BECCDBF1274A6EA4B036F6B5D3C02B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('61aa73ab-e715-4cb3-92e2-cf233004c44e', '52.1005', '52.1005', null, null, 1855, 'Human Resources Development.', 'kuali.enum.type.cip2000',  'CA65ABED4F7E44F2B9B8019B339928DF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('79b670e1-512a-401f-bf9f-02b4f353955d', '52.1099', '52.1099', null, null, 1856, 'Human Resources Management and Services, Other.', 'kuali.enum.type.cip2000',  '60A075D2D69947DDB15A06BB0A5AD674', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('affcb7e7-09d6-450b-838a-df63be7ffd01', '52.11', '52.11', null, null, 1857, 'International Business.', 'kuali.enum.type.cip2000',  'F1B3A2BAA2A342949CE2ADA06FBA841F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('142c2cde-c887-4b24-8daf-7a319a116544', '52.1101', '52.1101', null, null, 1858, 'International Business/Trade/Commerce.', 'kuali.enum.type.cip2000',  '3AE17C6FC5334B6B9556218E0166C243', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('719daf72-7ccb-48e4-898d-135ccc7af3c3', '52.12', '52.12', null, null, 1859, 'Management Information Systems and Services.', 'kuali.enum.type.cip2000',  'A151802D637F45AB9F25CE9FF145966C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('529a257c-2dcb-4684-af8e-5fbd1b49b9c4', '52.1201', '52.1201', null, null, 1860, 'Management Information Systems, General.', 'kuali.enum.type.cip2000',  '7B4ECD90C1BA4269804906B522CC905B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f50c7b3f-6b91-4d48-83f4-ecc41db67fc4', '52.1202', '52.1202', null, null, 1861, 'Business Computer Programming/Programmer.', 'kuali.enum.type.cip2000',  '2F1D7E5A55FA4290B0175D0F46F59433', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('625b82ba-002c-440c-9110-a15de26f6da7', '52.1203', '52.1203', null, null, 1862, 'Business Systems Analysis and Design.', 'kuali.enum.type.cip2000',  '7DF496AD82C94134A6C950A44D1C5683', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('411a1809-a1c0-4681-b929-8b9ff12bee59', '52.1204', '52.1204', null, null, 1863, 'Business Systems Networking.', 'kuali.enum.type.cip2000',  '728053889C1C45A6A009BED8EF2FC25B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('579b8322-7e34-4b1f-88f9-d13c784e7817', '52.1205', '52.1205', null, null, 1864, 'Business Computer Facilities Operator.', 'kuali.enum.type.cip2000',  '64E18DCA01954F7D9DC090728F1AB08F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4227e741-27d5-4f7b-a97e-1e3e4285e154', '52.1206', '52.1206', null, null, 1865, 'Information Resources Management/CIO Training.', 'kuali.enum.type.cip2000',  'F6361D2D267140CDB775DE5C0FA99F7D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8f641c6b-0add-4e74-b569-b62b3017d4c7', '52.1207', '52.1207', null, null, 1866, 'Knowledge Management.', 'kuali.enum.type.cip2000',  'A9C154A58BB246E19C633F3A369614A1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2980bf94-ee19-4645-9233-1a8c5ecb0c1e', '52.1299', '52.1299', null, null, 1867, 'Management Information Systems and Services, Other.',  'kuali.enum.type.cip2000', '4A688FE7CC3A4D4091B8641CB6747613', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('71bf5ba6-7ad5-48e6-956d-f2a76837ecf1', '52.13', '52.13', null, null, 1868, 'Management Sciences and Quantitative Methods.', 'kuali.enum.type.cip2000',  '61B87B1D34DE4F1D9A274F93101E4CC8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f291f767-63d6-4a66-81cf-46620b0a673e', '52.1301', '52.1301', null, null, 1869, 'Management Science, General.', 'kuali.enum.type.cip2000',  'FC423CDBDC4B4CB2AF8DB47ED5E29E50', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c4b37f9f-48c0-4af9-8ca1-458a0b9e903d', '52.1302', '52.1302', null, null, 1870, 'Business Statistics.', 'kuali.enum.type.cip2000',  'FAAA6DC8BB4548A6B57768B88FD2BAC9', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f9d35455-ee88-4923-96fc-8a636a44a605', '52.1304', '52.1304', null, null, 1871, 'Actuarial Science.', 'kuali.enum.type.cip2000',  '0AF8BCBA27524C43833BCDD4969D7F85', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c4ab2223-e1c4-4821-910c-80bb1b2f1bc2', '52.1399', '52.1399', null, null, 1872, 'Management Sciences and Quantitative Methods, Other.',  'kuali.enum.type.cip2000', '3D161DF1EDC643B5A7E25C2F3379E8BF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0ef516a2-475c-4c3d-baa9-96234f787dcf', '52.14', '52.14', null, null, 1873, 'Marketing.', 'kuali.enum.type.cip2000', '6318A73592F34FEAADEA969E13CF2B7D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f000a072-93f0-4169-ab17-616227e9e45b', '52.1401', '52.1401', null, null, 1874, 'Marketing/Marketing Management, General.', 'kuali.enum.type.cip2000',  '38AD7FE0C1A94021B1EA6BB6423FCCD5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('07f786fc-7f40-41fd-a3b6-c32d9c6bd105', '52.1402', '52.1402', null, null, 1875, 'Marketing Research.', 'kuali.enum.type.cip2000',  'E41E35701ADA4F6CB31DB398BB21BAD5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b8f95fbf-a952-445d-b063-515f0aa3965c', '52.1403', '52.1403', null, null, 1876, 'International Marketing.', 'kuali.enum.type.cip2000',  'E0F12619C9D24570A9509B1D797B114E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('091da5f4-0f36-422c-9525-24d700863abc', '52.1499', '52.1499', null, null, 1877, 'Marketing, Other.', 'kuali.enum.type.cip2000',  'FAA194A14E0D4A9AAA26C870624CA62B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('675d5694-4048-4188-9546-75c1ffab6d86', '52.15', '52.15', null, null, 1878, 'Real Estate.', 'kuali.enum.type.cip2000', 'A555C55A9A754263954B00D5247ADF40', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c9a9c0ca-7e55-4636-ba61-ed1905809968', '52.1501', '52.1501', null, null, 1879, 'Real Estate.', 'kuali.enum.type.cip2000', '67C32A89F5D14BD9BA28EBCE29E17E61',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('681ea801-99e0-4012-b20e-f37b07ec6782', '52.16', '52.16', null, null, 1880, 'Taxation.', 'kuali.enum.type.cip2000', 'E5B829536A9840A49B0C112AA49ADD70', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7b59d198-7787-4e53-8905-7d7cbd89ff8e', '52.1601', '52.1601', null, null, 1881, 'Taxation.', 'kuali.enum.type.cip2000', '8D5CC1AAE1A24DAB8EE5969A71B59A3B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bb173f8d-8dd3-49e5-8752-23b48a8cb57e', '52.17', '52.17', null, null, 1882, 'Insurance.', 'kuali.enum.type.cip2000', '85DDB77A0AFE44D488E332AE19F1807F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b4cd1fcc-3609-4b0d-bf15-7160888f8977', '52.1701', '52.1701', null, null, 1883, 'Insurance.', 'kuali.enum.type.cip2000', 'A081CA9AD2BE450880578980793652D0', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('330d793d-a95c-4742-856b-f19e89873a88', '52.18', '52.18', null, null, 1884, 'General Sales, Merchandising and Related Marketing Operations.',  'kuali.enum.type.cip2000', 'EF1DBE6D22E14F9784DB3889917A96BA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2c5fd2c7-431c-4265-b01f-97b08b281c60', '52.1801', '52.1801', null, null, 1885, 'Sales, Distribution, and Marketing Operations, General.',  'kuali.enum.type.cip2000', 'DA7CD399C2E347D4A49D30D444D365C9', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7bbc71a1-8e3d-4346-b913-620e139d2285', '52.1802', '52.1802', null, null, 1886, 'Merchandising and Buying Operations.', 'kuali.enum.type.cip2000',  '8BB6CE80B1894F19AC912DB126740F6D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5d416048-3eb8-4d99-8b77-f5d726380497', '52.1803', '52.1803', null, null, 1887, 'Retailing and Retail Operations.', 'kuali.enum.type.cip2000',  'CE4A61C667BC43818BA4E6DC6199A0CC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('54c33cb0-cfc9-4695-aa04-fc080455a8d2', '52.1804', '52.1804', null, null, 1888, 'Selling Skills and Sales Operations.', 'kuali.enum.type.cip2000',  'F4DA311FDAD94D1CAC0C6B1518D27A8C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('985170dd-e2bb-474c-812a-e0fea1f4d9c6', '52.1899', '52.1899', null, null, 1889, 'General Merchandising, Sales, and Related Marketing Operations, Other.',  'kuali.enum.type.cip2000', 'A7DF3A78878D4A77ABB709B5E5538948', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bdcfce8e-efa4-45b6-b59c-1b68b05a65d8', '52.19', '52.19', null, null, 1890, 'Specialized Sales, Merchandising and Marketing Operations.',  'kuali.enum.type.cip2000', '8DA127B12A94412286DABA02E777B004', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('dffc5aca-c57c-43ea-a4c0-09a0f230191a', '52.1901', '52.1901', null, null, 1891, 'Auctioneering.', 'kuali.enum.type.cip2000',  '3604998A8D334F47909710F21DAB1718', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('93e18e5e-0138-43ae-8c24-7d39591fb718', '52.1902', '52.1902', null, null, 1892, 'Fashion Merchandising.', 'kuali.enum.type.cip2000',  'E7354B7595514AE5A5452F601F797B86', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f75533e7-0256-4de0-b5e5-7b42c520e4e8', '52.1903', '52.1903', null, null, 1893, 'Fashion Modeling.', 'kuali.enum.type.cip2000',  '316F433A5C5B4FA38B65701CDDABFA38', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4c99355e-bc9c-4f36-a439-0aae029c693f', '52.1904', '52.1904', null, null, 1894, 'Apparel and Accessories Marketing Operations.', 'kuali.enum.type.cip2000',  'F5BAA2086A6346DD93D9044E61B42EFB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9dade61a-6c5a-4db8-90af-a4dcabe73f03', '52.1905', '52.1905', null, null, 1895, 'Tourism and Travel Services Marketing Operations.', 'kuali.enum.type.cip2000',  '1D27621936D041CDB22CD48ED15AD72E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5ece61b9-aaf9-4101-823c-31b7eb3760f7', '52.1906', '52.1906', null, null, 1896, 'Tourism Promotion Operations.', 'kuali.enum.type.cip2000',  'CF8987F4F47143F79225C7E818269ABD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('52141861-dbbe-4dda-ba5f-746a533e70a8', '52.1907', '52.1907', null, null, 1897, 'Vehicle and Vehicle Parts and Accessories Marketing Operations.',  'kuali.enum.type.cip2000', '033B0761BA114D4AAEFA3B06658F7DE9', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('741fb8ca-69c7-4fe0-b96f-bbb7f46f9171', '52.1908', '52.1908', null, null, 1898, 'Business and Personal/Financial Services Marketing Operations.',  'kuali.enum.type.cip2000', '7AF9065AB3654B1094FC5B4D364523EF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6400d868-77ed-4ebb-8722-08e568e49063', '52.1909', '52.1909', null, null, 1899, 'Special Products Marketing Operations.', 'kuali.enum.type.cip2000',  '90EF295E290C4F1A8169CAD816F23DE6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4bdc0a6c-3ba2-4d33-a331-8237fe732ae0', '52.1910', '52.1910', null, null, 1900, 'Hospitality and Recreation Marketing Operations.', 'kuali.enum.type.cip2000',  '00C904BAF57E4BCB83546F7F2478BB9E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('07f5a21b-8c8f-4496-9286-002c1aebc08d', '52.1999', '52.1999', null, null, 1901, 'Specialized Merchandising, Sales, and Marketing Operations, Other.',  'kuali.enum.type.cip2000', '5F50B00EBBA24DB59746EEA68D678EB6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('61269f7b-9b27-43d5-927c-bebe35f4ef0d', '52.20', '52.20', null, null, 1902, 'Construction Management.', 'kuali.enum.type.cip2000',  '77E1D5910A4E4EA2AC192C83037438D6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('be5d6ef0-a417-427b-877d-c4429e8a8304', '52.2001', '52.2001', null, null, 1903, 'Construction Management.', 'kuali.enum.type.cip2000',  'B6BED1FED3C1425F9CEDB8AE8015C629', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('413fb036-81c4-4292-b50c-e2cdbf6d011d', '52.99', '52.99', null, null, 1904, 'Business, Management, Marketing, and Related Support Services, Other.',  'kuali.enum.type.cip2000', '138C142E9A2748FE8E3BD1C31F617D4D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('138dba04-af94-4113-ad98-354eed126910', '52.9999', '52.9999', null, null, 1905, 'Business, Management, Marketing, and Related Support Services, Other.',  'kuali.enum.type.cip2000', 'BED63CF054C149289D3909CBFF4ED31B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0b83fd28-2ef6-4cc4-9b27-2c33224140b6', '53.', '53.', null, null, 1906, 'HIGH SCHOOL/SECONDARY DIPLOMAS AND CERTIFICATES.', 'kuali.enum.type.cip2000',  'F10702250AB14919884A165158A59323', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('95ed5a40-9f9c-464a-9e03-5577ed2fc15f', '53.', '53.', null, null, 1907, 'Programs for Series 53.', 'kuali.enum.type.cip2000',  '74F0CC7BFC9F415ABAB787EAE015B074', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5bf4d2c0-7bcc-4c7b-a5e0-d28822a58164', '53.01', '53.01', null, null, 1908, 'High School/Secondary Diploma Programs.', 'kuali.enum.type.cip2000',  'E1CA26F117214FDD84E4DDA4B670CA98', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('463ba8e2-777b-4bde-be2f-2df83a6d6439', '53.0101', '53.0101', null, null, 1909, 'Regular/General High School/Secondary Diploma Program.',  'kuali.enum.type.cip2000', 'F0CDD4EFF3DE45A2BCF9C8D30390E222', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('fa0fd8fa-1fdd-4c45-8a87-d5aa860a573e', '53.0102', '53.0102', null, null, 1910, 'College/University Preparatory and Advanced High School/Secondary Diploma  Program.', 'kuali.enum.type.cip2000', 'AB28AD064F2E4A859E8861F97B263860', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a3ee0876-d637-41ad-9828-1d20802ec97d', '53.0103', '53.0103', null, null, 1911, 'Vocational High School and Secondary Business/Vocational-Industrial/Occupational Diploma Program.', 'kuali.enum.type.cip2000', '1223785A8527492EA28543BDCD28C6FE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('89909a3d-e9a5-42ce-94b5-d3761b17cdb1', '53.0104', '53.0104', null, null, 1912, 'Honors/Regents High School/Secondary Diploma Program.',  'kuali.enum.type.cip2000', '39973CB98145491B88087E61BEAFEFCB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ef0aec27-8d35-473e-9915-fbf6b447248b', '53.0105', '53.0105', null, null, 1913, 'Adult High School/Secondary Diploma Program (NEW).',  'kuali.enum.type.cip2000', '8ABEB957735144E492AFAADCF3EB3582', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b359c91e-6990-49ee-8856-b1d792193f05', '53.0199', '53.0199', null, null, 1914, 'High School/Secondary Diploma Programs, Other.', 'kuali.enum.type.cip2000',  '172E7B98F7BF437DBE758F01AFB94C28', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0d1a2cdd-7449-4053-8549-e09dc7b031ca', '53.02', '53.02', null, null, 1915, 'High School/Secondary Certificate Programs.', 'kuali.enum.type.cip2000',  '58B87AFB2D004700BD30D1D35D4702E9', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6b0259bb-cdfd-40db-b85e-0e851e0697e2', '53.0201', '53.0201', null, null, 1916, 'High School Equivalence Certificate Program.', 'kuali.enum.type.cip2000',  '925DAA599FB94728B1D368FC67C0D0F5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5075c049-5f13-4508-9855-ef566099cd2e', '53.0202', '53.0202', null, null, 1917, 'High School Certificate of Competence Program.', 'kuali.enum.type.cip2000',  '3ED70714E8ED4692BFC1B9B6085A373E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('aff14d31-acd4-457d-96ff-850475e84a1f', '53.0203', '53.0203', null, null, 1918, 'Certificate of IEP Completion Program.', 'kuali.enum.type.cip2000',  '5B095B4D586A4DCAA4A5FC7AFD2C9E64', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('46cb4260-2e1f-46b5-bcc2-09d6900ea57d', '53.0299', '53.0299', null, null, 1919, 'High School/Secondary Certificates, Other.', 'kuali.enum.type.cip2000',  'E874747F4A8A4D7C95C301DE1E207E32', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('394b18c6-2a9f-406c-8ba2-7edef6234ba8', '54.', '54.', null, null, 1920, 'HISTORY (NEW) Instructional programs that focus on the study and interpretation of  past events, institutions, issues, and cultures.', 'kuali.enum.type.cip2000', '0EA958A297DF4376A19B6FE30A74147B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e52bd3af-53a4-4938-8650-ab79c95c6e45', '54.01', '54.01', null, null, 1921, 'History.', 'kuali.enum.type.cip2000', '288C1E760A9D4442B9F473EB21C07DAB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('cf6871bd-7714-4909-9bef-e2b8399f668f', '54.0101', '54.0101', null, null, 1922, 'History, General.', 'kuali.enum.type.cip2000',  '1173467EC3A14DECB2DD8A9633B45140', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('22d24de5-6d52-49c9-9345-ba323aed5051', '54.0102', '54.0102', null, null, 1923, 'American History (United States).', 'kuali.enum.type.cip2000',  '6283B714FAE1441A9D7452A5AAD12FC7', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('60caa153-9e62-44c2-a2d3-9e36e5c77e0a', '54.0103', '54.0103', null, null, 1924, 'European History.', 'kuali.enum.type.cip2000',  'B3479980E72E45FEBE0D7475D85AA854', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6a007097-6a02-497c-801f-800c99592ba3', '54.0104', '54.0104', null, null, 1925, 'History and Philosophy of Science and Technology.', 'kuali.enum.type.cip2000',  'F2AC004B66B74900A89CC30D8C2C4AC4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('791c3102-9fe5-4c55-9878-6f91099679dc', '54.0105', '54.0105', null, null, 1926, 'Public/Applied History and Archival Administration.',  'kuali.enum.type.cip2000', 'F0C66D992DE3470AB2E81A879D1EBE7C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8687713f-c2ee-4557-8547-9e7b717489a0', '54.0106', '54.0106', null, null, 1927, 'Asian History.', 'kuali.enum.type.cip2000',  '4C77E0797F16485B9CAF09BD39F95B10', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b01cd177-8530-42d7-a8df-e1e3218ef25c', '54.0107', '54.0107', null, null, 1928, 'Canadian History.', 'kuali.enum.type.cip2000',  '7E422441C6004D20B2648D8A9F0625AD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d48f884f-b1ac-4203-9398-1963dc91c761', '54.0199', '54.0199', null, null, 1929, 'History, Other.', 'kuali.enum.type.cip2000',  'C67FCE921A2740EF8398A63EEB9C4864', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ba6efb7d-393a-4239-b95b-3cb23579a0ea', '55.', '55.', null, null, 1930, 'Programs for Series 55 (French/Canadian Language and Literature/Letters) are located  in appendix A.', 'kuali.enum.type.cip2000', '60356B6BE3494F40B7F12AE88848E663', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6284dfab-19eb-4484-a597-3e61d8975639', '60.', '60.', null, null, 1931, 'Residency Programs.', 'kuali.enum.type.cip2000', '0877810A416D49868B36A3BC3F8CBEA3',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d329ce90-0d2b-4058-a02a-3529bc4d10e0', '60.01', '60.01', null, null, 1932, 'Dental Residency Programs.', 'kuali.enum.type.cip2000',  'E9018E164A704C948E0471E425D920DA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d1a7983f-6b89-48e8-9c4b-b68953924a89', '60.0101', '60.0101', null, null, 1933, 'Dental/Oral Surgery Specialty.', 'kuali.enum.type.cip2000',  '20230696BF2D472EBEBEABE46B93F809', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3565de44-3e4a-4130-b585-52b2eb64c6b1', '60.0102', '60.0102', null, null, 1934, 'Dental Public Health Specialty.', 'kuali.enum.type.cip2000',  'E915CAD5F77145FCB4F74B499DE14F21', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('cd481a4e-23dc-49a6-8576-0f4281ff3ce7', '60.0103', '60.0103', null, null, 1935, 'Endodontics Specialty.', 'kuali.enum.type.cip2000',  '4886F356D9554CFD9396C206ECDE217D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d6e6fb11-83e2-4a01-b3a3-24d2039962d1', '60.0104', '60.0104', null, null, 1936, 'Oral Pathology Specialty.', 'kuali.enum.type.cip2000',  'D703350F5BCD4D5691E7D8CC5F45E12D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7a409e85-717c-4bd3-ba45-ba466ee001a5', '60.0105', '60.0105', null, null, 1937, 'Orthodontics Specialty.', 'kuali.enum.type.cip2000',  '65C85281583B4916B0C3C185578BC049', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a2a8ff23-5540-4a09-a399-59fc15c65502', '60.0106', '60.0106', null, null, 1938, 'Pedodontics Specialty.', 'kuali.enum.type.cip2000',  '4D648CAE464343A68BDC8D94C68812FE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4fc07c99-e34c-4687-81b8-c388601595d5', '60.0107', '60.0107', null, null, 1939, 'Periodontics Specialty.', 'kuali.enum.type.cip2000',  '3D279302991140F29212FC80885D6B92', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a00a2083-cfc5-4787-b41e-3e52fa5979e3', '60.0108', '60.0108', null, null, 1940, 'Prosthodontics Specialty.', 'kuali.enum.type.cip2000',  '58916A32D06D42EB8E40089AC247257B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5a6d6439-e9ad-441d-ad8c-6b85bce62112', '60.0199', '60.0199', null, null, 1941, 'Dental Residency Program, Other.', 'kuali.enum.type.cip2000',  '8D5064885BF44E7BB4372DFE58F72092', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2c5e5bd2-e12e-44df-80e7-c559f146a613', '60.02', '60.02', null, null, 1942, 'Medical Residency Programs.', 'kuali.enum.type.cip2000',  '1BBE9BA117C74BAFA07341827D8804E6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2312396c-ddff-47f1-9ea6-7657fcef93ce', '60.0201', '60.0201', null, null, 1943, 'Aerospace Medicine.', 'kuali.enum.type.cip2000',  '253FA898BCDD4164AC928D7194DFBCA0', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f1ba6ff4-ca93-4743-91cb-113f7f04a402', '60.0202', '60.0202', null, null, 1944, 'Allergies and Immunology.', 'kuali.enum.type.cip2000',  'DC482F867B604A0A9DDED0436FDD21EF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c697e3c9-1083-4697-a8b1-ae7ac074f3e0', '60.0203', '60.0203', null, null, 1945, 'Anesthesiology.', 'kuali.enum.type.cip2000',  '5BDE1A0ECD62412FA68B166047B5868C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7f3b9506-83f6-4777-80ce-1938cbe43d25', '60.0204', '60.0204', null, null, 1946, 'Blood Banking.', 'kuali.enum.type.cip2000',  'DFADB232CD7A46AEB93BAE831D741126', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a98a27de-3481-48ed-be69-f4ad46aca50b', '60.0205', '60.0205', null, null, 1947, 'Cardiology.', 'kuali.enum.type.cip2000', 'E1CCAD4BA86249E7B30C545278157CC6',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('50ea7552-80cd-4f0d-bde7-7562733ce730', '60.0206', '60.0206', null, null, 1948, 'Chemical Pathology.', 'kuali.enum.type.cip2000',  '2DEFE40678E044C9BC0486C519AA043E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b0e0d1a5-61da-4d8f-975d-0bcbf1323af3', '60.0207', '60.0207', null, null, 1949, 'Child/Pediatric Neurology.', 'kuali.enum.type.cip2000',  '68C8DD1C98AC444298EA0AD0EDD556FD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6602b5d3-fa4b-4393-9e21-538b184bff76', '60.0208', '60.0208', null, null, 1950, 'Child Psychiatry.', 'kuali.enum.type.cip2000',  'BB0143986752483281C049190DE90CF3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a1fd513a-5940-4acd-8800-be91893dfdc8', '60.0209', '60.0209', null, null, 1951, 'Colon and Rectal Surgery.', 'kuali.enum.type.cip2000',  '57DFDAD7A3654732AEA8670E20D25063', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d12a93cd-517f-4191-89e7-c73b5af3b779', '60.0210', '60.0210', null, null, 1952, 'Critical Care Anesthesiology.', 'kuali.enum.type.cip2000',  '7B147D5947DF4B45A4DE6D7989779ED6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('39aa92cb-66d3-48ff-b44b-9e20979a074a', '60.0211', '60.0211', null, null, 1953, 'Critical Care Medicine.', 'kuali.enum.type.cip2000',  'F2EADAE0F7AF4D959E653D9A8516D7A4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2a5167d8-019c-49e8-99af-7ffcf20b0687', '60.0212', '60.0212', null, null, 1954, 'Critical Care Surgery.', 'kuali.enum.type.cip2000',  '12A63F20C35449EDA028B409907414EB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('caabe0f1-4a36-4d6d-9aaf-44e0b3d0df1a', '60.0213', '60.0213', null, null, 1955, 'Dermatology.', 'kuali.enum.type.cip2000', '72E32939E572404E92AB8F5534930869',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('481810a0-63dd-42a2-ad0d-4ce513402a9b', '60.0214', '60.0214', null, null, 1956, 'Dermatopathology.', 'kuali.enum.type.cip2000',  '43DFF00AA3E84A7BA9178296EB4D2BB1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ee1c362b-572d-4793-8146-d3f83a072226', '60.0215', '60.0215', null, null, 1957, 'Diagnostic Radiology.', 'kuali.enum.type.cip2000',  '1C04CA95FCA541219BFA876971039CE9', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7729a929-398f-4dd8-9cbc-ce55cdc9352f', '60.0216', '60.0216', null, null, 1958, 'Emergency Medicine.', 'kuali.enum.type.cip2000',  'BB14000461D6448FBEC6392DAAD6ECC0', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('004d3dae-7085-460b-bddb-8ebde8735ca2', '60.0217', '60.0217', null, null, 1959, 'Endocrinology and Metabolism.', 'kuali.enum.type.cip2000',  'A85E83D0019741C580B8AA014B6B9736', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('36ce8701-391d-4f40-aaae-a9e12ff4e648', '60.0218', '60.0218', null, null, 1960, 'Family Medicine.', 'kuali.enum.type.cip2000',  'BA74BD5D77524A448C4D5FBA3ECF55D5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('149d7f9a-6991-4dff-bf68-69625ca191d7', '60.0219', '60.0219', null, null, 1961, 'Forensic Pathology.', 'kuali.enum.type.cip2000',  '3467B792464A40B0A16FF52AF5122760', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('089ddecc-8261-4a37-aad4-5411aeedace0', '60.0220', '60.0220', null, null, 1962, 'Gastroenterology.', 'kuali.enum.type.cip2000',  '868BFCACE18D4D6983833D8822375729', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d900b340-828b-46bd-a8ed-d61d9379365c', '60.0221', '60.0221', null, null, 1963, 'General Surgery.', 'kuali.enum.type.cip2000',  '705BCD86EAEF4038A68739F93E312C76', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('16d0abc4-5a7d-491f-b156-af2f1bac91b1', '60.0222', '60.0222', null, null, 1964, 'Geriatric Medicine.', 'kuali.enum.type.cip2000',  '3E4095BDC4274BB68EEA4A3D95B08FDD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('69a742f0-7132-4088-ad4f-a980c2b54bde', '60.0223', '60.0223', null, null, 1965, 'Hand Surgery.', 'kuali.enum.type.cip2000', '635B24C055D74511A6A0A0B1A4E37DAE',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ec623a01-4423-4591-be6f-60293cde045f', '60.0224', '60.0224', null, null, 1966, 'Hematology.', 'kuali.enum.type.cip2000', 'BDAF3879A15942FA9B0351FD54AA5402',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3c306c32-4263-49e7-b544-00829fe4e830', '60.0225', '60.0225', null, null, 1967, 'Hematological Pathology.', 'kuali.enum.type.cip2000',  '39192A3CE7FE4F7884CEE851CFE116B6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('87b4edb0-04b7-41a2-96d6-0cb28bcadfde', '60.0226', '60.0226', null, null, 1968, 'Immunopathology.', 'kuali.enum.type.cip2000',  '84BDCAC766844ECC9351D7CB7A71ACCC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a8c36673-748b-4ddb-beeb-bcabd279e8f0', '60.0227', '60.0227', null, null, 1969, 'Infectious Disease.', 'kuali.enum.type.cip2000',  'B8D1ADFC3C4945639493A9B39DF7E445', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d37113f8-f2cc-4ed4-9356-bd6309b8669f', '60.0228', '60.0228', null, null, 1970, 'Internal Medicine.', 'kuali.enum.type.cip2000',  '3781835FC7B34D87AC48859B3FFE383A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('82f54ae0-4967-4fa0-9e98-e00affc52c14', '60.0229', '60.0229', null, null, 1971, 'Laboratory Medicine.', 'kuali.enum.type.cip2000',  'AA4FB096A7E54B9F9C2CE975B2E5B0AA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('51900eb5-6067-4483-ac36-0e0d572b8f15', '60.0230', '60.0230', null, null, 1972, 'Musculoskeletal Oncology.', 'kuali.enum.type.cip2000',  '24B2AC4AE7054C5092607B95084BA668', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('236348b4-2544-468d-8759-601e4c6f9096', '60.0231', '60.0231', null, null, 1973, 'Neonatal-Perinatal Medicine.', 'kuali.enum.type.cip2000',  'B226A16AED0843F08F4C167CDD1DFF9E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f309c23e-6af4-4801-8870-8dc18a738cba', '60.0232', '60.0232', null, null, 1974, 'Nephrology.', 'kuali.enum.type.cip2000', '170FF3AF9462457DA008850947490F84',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('49ad13f5-a3b6-497b-8f79-76035199f410', '60.0233', '60.0233', null, null, 1975, 'Neurological Surgery/Neurosurgery.', 'kuali.enum.type.cip2000',  '771662D5E2774A2983FD42D56A4D1D41', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('19914dbc-3263-4cf5-b45f-475c6f716e20', '60.0234', '60.0234', null, null, 1976, 'Neurology.', 'kuali.enum.type.cip2000', '4F2EAECA570149629DC7C208E242428C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('dca1ca51-339a-4a11-b3d2-06fa9e1e6e3b', '60.0235', '60.0235', null, null, 1977, 'Neuropathology.', 'kuali.enum.type.cip2000',  '9EF5B71A018748EAACD3454D797898BD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3981137b-eecc-41e2-8837-83befbc977a7', '60.0236', '60.0236', null, null, 1978, 'Nuclear Medicine.', 'kuali.enum.type.cip2000',  '8DD4AE8BE4984EB4A4F09E9343F159CB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('31aa1f22-1a82-48b7-8178-26e9af0a1538', '60.0237', '60.0237', null, null, 1979, 'Nuclear Radiology.', 'kuali.enum.type.cip2000',  'A37369CE07DE4FC5B48CC4E918404359', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e1873871-ba1c-4219-83d3-65a899c72f18', '60.0238', '60.0238', null, null, 1980, 'Obstetrics and Gynecology.', 'kuali.enum.type.cip2000',  '9D4246E98E47445AAB7E0AC40C655568', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('53025b91-7507-4da6-adfa-0424227b621b', '60.0239', '60.0239', null, null, 1981, 'Occupational Medicine.', 'kuali.enum.type.cip2000',  '2D2E464E02804A8EAEACEC67DC8EDD0F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9e7457e1-cbec-4b04-bb02-b0416aa8de68', '60.0240', '60.0240', null, null, 1982, 'Oncology.', 'kuali.enum.type.cip2000', 'B81437CD31A847E38F985977EDB21309', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1c2eeb33-1760-4f40-afaa-d29cf8a069ba', '60.0241', '60.0241', null, null, 1983, 'Ophthalmology.', 'kuali.enum.type.cip2000',  '12B1DD6743844B7E829E3F37A8940237', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0c4c5b66-c995-4a97-91fa-824842ed0470', '60.0242', '60.0242', null, null, 1984, 'Orthopedics/Orthopedic Surgery.', 'kuali.enum.type.cip2000',  '9D9AFFA3C2134D6C80AAF00A3E8A978E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5a43006d-7e16-4c36-af15-73df59e00bbe', '60.0243', '60.0243', null, null, 1985, 'Otolaryngology.', 'kuali.enum.type.cip2000',  'D5F4D708A8794219A164C31308C15FC5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('03fb66f1-3855-4081-a356-b82d16daae5f', '60.0244', '60.0244', null, null, 1986, 'Pathology.', 'kuali.enum.type.cip2000', 'D63ABDF1CB534B26A1DB07C3DF3024B4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a6735863-a186-4bb1-a8c4-ecd4a5561af4', '60.0245', '60.0245', null, null, 1987, 'Pediatric Cardiology.', 'kuali.enum.type.cip2000',  '7678A2A0E87946B5B9DD347C23E2E662', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7a062396-8f21-4c4e-aff2-e59a2349d0c3', '60.0246', '60.0246', null, null, 1988, 'Pediatric Endocrinology.', 'kuali.enum.type.cip2000',  '04E6F42D67D5472FB3644F45DE62E4FB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5036afec-2e5f-4bd9-ac11-fd4f9e8d31fb', '60.0247', '60.0247', null, null, 1989, 'Pediatric Hemato-Oncology.', 'kuali.enum.type.cip2000',  '2A28961846FF4F4F8FA73953420E6EBC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('57e61a48-70f1-436e-bef2-04f187554962', '60.0248', '60.0248', null, null, 1990, 'Pediatric Nephrology.', 'kuali.enum.type.cip2000',  '314E45EAE6BA41618FE0C2F299E27EAE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('210215aa-1bac-4c57-acfa-514d0c76d20e', '60.0249', '60.0249', null, null, 1991, 'Pediatric Orthopedics.', 'kuali.enum.type.cip2000',  'C0F1F8D42D744AF1B6C37F766FD2A392', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4504cea4-fb31-4dd1-b786-750cc1cb7588', '60.0250', '60.0250', null, null, 1992, 'Pediatric Surgery.', 'kuali.enum.type.cip2000',  'D1C870F0702A4122AADA129F8E082A67', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9dce2bf7-512c-4010-b573-7aa57e97610b', '60.0251', '60.0251', null, null, 1993, 'Pediatrics.', 'kuali.enum.type.cip2000', 'C2B1FC3D41DA4F618E5B8E884A997F9B',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a7aed755-a080-4449-b921-a1897dc6ff74', '60.0252', '60.0252', null, null, 1994, 'Physical and Rehabilitation Medicine.', 'kuali.enum.type.cip2000',  '87332650E1A84B808667C54AD3E8E42E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b42532a0-a76e-4281-bd56-c22570c4b287', '60.0253', '60.0253', null, null, 1995, 'Plastic Surgery.', 'kuali.enum.type.cip2000',  '02CA9D70C8A641E7AB1DDE3F7EE6DEA4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ab19145b-71c3-4209-b2df-9ca5859adef5', '60.0254', '60.0254', null, null, 1996, 'Preventive Medicine.', 'kuali.enum.type.cip2000',  '4CA4F0664536427AAD4E80B80977736A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e9de54e3-4a76-4a39-bfa4-5d1d94d738e7', '60.0255', '60.0255', null, null, 1997, 'Psychiatry.', 'kuali.enum.type.cip2000', 'EBF66A1A834747CCA9ADF0769301DBAE',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('cfd1a62e-a975-4ca0-924f-c52786fe3886', '60.0256', '60.0256', null, null, 1998, 'Public Health Medicine.', 'kuali.enum.type.cip2000',  'F02C67FA553945B2B1EAFDAA1B5FDEA4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f5d36246-63e6-4867-b2a8-4ab081fda8f2', '60.0257', '60.0257', null, null, 1999, 'Pulmonary Disease.', 'kuali.enum.type.cip2000',  '59F83672B3FB4E988A50929A0F2F60B2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b24b9cad-4ff5-4e74-a159-63b272de192e', '60.0258', '60.0258', null, null, 2000, 'Radiation Oncology.', 'kuali.enum.type.cip2000',  '5BDDEFD0F6694CBB9A84B23EFE8009AB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7bea1073-f1ec-4f28-8a8a-a146ba1aa793', '60.0259', '60.0259', null, null, 2001, 'Radioisotopic Pathology.', 'kuali.enum.type.cip2000',  '14E859C879E44D7DBAD8D428200328B7', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('905024d3-5880-4b71-9967-1210c7cb988d', '60.0260', '60.0260', null, null, 2002, 'Rheumatology.', 'kuali.enum.type.cip2000', '31B474FD81344C3F8DB8C6AF97DD1026',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('480172b0-fbdb-4a98-a974-0b18706a77e6', '60.0261', '60.0261', null, null, 2003, 'Sports Medicine.', 'kuali.enum.type.cip2000',  'F305A55CC3DA4BFE81D6450D25FBF401', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b35bc79c-b87b-435d-8bc8-c22feef62556', '60.0262', '60.0262', null, null, 2004, 'Thoracic Surgery.', 'kuali.enum.type.cip2000',  'B2B506243AD8495BAC68822E2AD94A43', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d768fa0e-992d-4145-8e80-11b2d4481ce7', '60.0263', '60.0263', null, null, 2005, 'Urology.', 'kuali.enum.type.cip2000', 'A551CBA0113F4BAA93B440D8CF34AA7F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('84a604d9-6b52-4db0-8b50-be85f6fa8939', '60.0264', '60.0264', null, null, 2006, 'Vascular Surgery.', 'kuali.enum.type.cip2000',  'B238226D9AAE4421A1C5B1A975F86C66', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d8d70838-9351-4406-ac54-e0d57abeed1a', '60.0265', '60.0265', null, null, 2007, 'Adult Reconstructive Orthopedics (Orthopedic Surgery).',  'kuali.enum.type.cip2000', 'C3E81FABE26C4BA29FBBE29A4E66E14D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('18fc6a96-b123-4514-813d-ebcac168bcbf', '60.0266', '60.0266', null, null, 2008, 'Child Neurology.', 'kuali.enum.type.cip2000',  '11953592A7844910A34F05F308B966FE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0e02ad10-8ac6-41cc-932e-64033692943f', '60.0267', '60.0267', null, null, 2009, 'Cytopathology.', 'kuali.enum.type.cip2000',  '5BBF89688C0843C8A6E023AFC2EEC2AE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('18d4bc67-25c7-420a-a8bd-ac7b0d9fb6a1', '60.0268', '60.0268', null, null, 2010, 'Geriatric Medicine (Internal Medicine).', 'kuali.enum.type.cip2000',  'C56D2404FE604872B81FC3474266D81F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5cadee3d-3ce6-4a0c-bbdf-a7750b3ff79d', '60.0269', '60.0269', null, null, 2011, 'Pediatric Urology.', 'kuali.enum.type.cip2000',  '67FEADD7148F4BE9A8C7D391B9A3A0D3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4a4a3e2e-9cf4-4f07-abe5-ec9c72c07bf9', '60.0270', '60.0270', null, null, 2012, 'Physical Medicine and Rehabilitation/Psychiatry.', 'kuali.enum.type.cip2000',  '9D79CEBF1BE144CB942316F9B5C20755', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('22e28dcd-c6e5-4182-ac16-9ba1d02552cf', '60.0271', '60.0271', null, null, 2013, 'Orthopedic Surgery of the Spine.', 'kuali.enum.type.cip2000',  '3032ACE8C92646FDB117102D726CEB2E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c14c2f9a-3fe5-4be7-9f5f-4219cde342fd', '60.0299', '60.0299', null, null, 2014, 'Medical Residency Programs, Other.', 'kuali.enum.type.cip2000',  '84E11FC8F81D4395BA040A18A051E48C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4906aa82-8d13-4c0f-9666-0ec178474db9', '60.03', '60.03', null, null, 2015, 'Veterinary Residency Programs.', 'kuali.enum.type.cip2000',  'E5DCFA6E136C4C8A8DD9F9D839023945', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b12b3c48-2f08-4c23-90e8-d9c9c0895067', '60.0301', '60.0301', null, null, 2016, 'Veterinary Anesthesiology.', 'kuali.enum.type.cip2000',  '12F760D5772E4E198C2808A271557B4B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8cedd123-d540-4381-b04f-26a9a9f295a3', '60.0302', '60.0302', null, null, 2017, 'Veterinary Dentistry.', 'kuali.enum.type.cip2000',  'AD5CD00F5ED346318695C817882855E4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1a917e26-a624-418e-aa3f-238cff276bed', '60.0303', '60.0303', null, null, 2018, 'Veterinary Dermatology.', 'kuali.enum.type.cip2000',  '7796E24ECC574099A763F7AC8148254B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d5e788e8-13c6-4e41-88eb-d9d70e3f34ca', '60.0304', '60.0304', null, null, 2019, 'Veterinary Emergency and Critical Care Medicine.', 'kuali.enum.type.cip2000',  '7FE58C5BBF9B41E1B17636FFFB7C8385', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('99a1af17-d0ea-425c-afce-5e0ebbbbb5be', '60.0305', '60.0305', null, null, 2020, 'Veterinary Internal Medicine.', 'kuali.enum.type.cip2000',  '4FB75B5C8248497FB2B3F957B8304368', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('be9e0cc2-7980-4558-97ff-7e470740fd8f', '60.0306', '60.0306', null, null, 2021, 'Laboratory Animal Medicine.', 'kuali.enum.type.cip2000',  'E7B1123F892E4602B7613C9BC4548054', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('938013d8-0b34-4ecf-bfdb-7859434713c0', '60.0307', '60.0307', null, null, 2022, 'Veterinary Microbiology.', 'kuali.enum.type.cip2000',  'C5AA19EB0AB64033B744B83ABE2F7819', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3b0cda09-f707-4f71-b8cf-f0996edd3802', '60.0308', '60.0308', null, null, 2023, 'Veterinary Nutrition.', 'kuali.enum.type.cip2000',  'A0DF03E49251413C94CE2C58A7A8C0F6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a720b261-7c93-468b-a02b-82056762c1fe', '60.0309', '60.0309', null, null, 2024, 'Veterinary Ophthalmology.', 'kuali.enum.type.cip2000',  '5D314659BE4F4FFB8AB1F8D49435B91D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0fc187ad-f072-4132-8fcf-455b2554f068', '60.0310', '60.0310', null, null, 2025, 'Veterinary Pathology.', 'kuali.enum.type.cip2000',  'AB3D3887E9D64A23AC70F53D9C75352E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8cc8b431-5931-4f03-b92a-31462233f522', '60.0311', '60.0311', null, null, 2026, 'Veterinary Practice.', 'kuali.enum.type.cip2000',  'E20CB7CE71674250BBF9BE7FB0E688FC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('42f30fc3-b414-41bb-baa1-6f6641e3efd4', '60.0312', '60.0312', null, null, 2027, 'Veterinary Preventive Medicine.', 'kuali.enum.type.cip2000',  'E3FA2C68BFFA447F9702FA466C2CB83F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('55c2561d-d646-44e0-a88e-52e20b8462ca', '60.0313', '60.0313', null, null, 2028, 'Veterinary Radiology.', 'kuali.enum.type.cip2000',  '0CA4F63326E64674A2A61A0B2DC50EC3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('be04ff31-8679-4c90-ab4a-59719c57de40', '60.0314', '60.0314', null, null, 2029, 'Veterinary Surgery.', 'kuali.enum.type.cip2000',  '2F018D44719C499F8A4E5D9542CB976A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('650cb449-011c-4ad9-9bbc-33251901ce43', '60.0315', '60.0315', null, null, 2030, 'Theriogenology.', 'kuali.enum.type.cip2000',  'C8E47F5A54FC47B8918F6CDBF5CF6A15', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ebc51854-0756-476c-b89b-6fb508127d65', '60.0316', '60.0316', null, null, 2031, 'Veterinary Toxicology.', 'kuali.enum.type.cip2000',  '0416BB633ACF4FB1B06C5A1A1E572DFF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('29903470-a9f2-4e93-8c1b-fc71196ccf6d', '60.0317', '60.0317', null, null, 2032, 'Zoological Medicine.', 'kuali.enum.type.cip2000',  '530E59C8E1114D70A3DDF2B1894F18CF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7cf79b8e-8ffb-4c24-9c86-48f8b8cb4c2e', '60.0399', '60.0399', null, null, 2033, 'Veterinary Residency Programs, Other.', 'kuali.enum.type.cip2000',  '5A0D04509B31426B9481931DBBF8DE85', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b574b635-41d5-4eb9-b7b6-27914637b08f', '26.0202', '26.0202', null, null, 821, 'Biochemistry.', 'kuali.enum.type.cip2000', 'B684028C71DB4605B64141B0CC689852',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ced86745-69f9-48cf-8f72-fc2c2a9c4c8e', '26.0203', '26.0203', null, null, 822, 'Biophysics.', 'kuali.enum.type.cip2000', 'EA119271740543888296938FBAFCF26B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c2e710bb-0d20-459f-a6fa-097016a1911e', '26.0204', '26.0204', null, null, 823, 'Molecular Biology.', 'kuali.enum.type.cip2000',  'EF0753B6FBD24C288463D88EE303A010', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c30b70ed-35d1-46dc-9bd1-32a4e7f36731', '26.0205', '26.0205', null, null, 824, 'Molecular Biochemistry.', 'kuali.enum.type.cip2000',  'A9DC9B3BD3604E79BC81E63E370521E9', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f104fa58-c50f-4a72-8a16-0b16beaf1755', '26.0206', '26.0206', null, null, 825, 'Molecular Biophysics.', 'kuali.enum.type.cip2000',  '2C51514D81F64DEFB23A4EEF8CCAC9E3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('13349891-4dd9-4c98-a483-efa65d25fb5d', '26.0207', '26.0207', null, null, 826, 'Structural Biology.', 'kuali.enum.type.cip2000',  '072045D22D5045B2834B15EC03A0113B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b3e5af42-349b-4b03-8657-0caa93adda60', '26.0208', '26.0208', null, null, 827, 'Photobiology.', 'kuali.enum.type.cip2000', '8222C367BCC34442994A642A76E3EC74',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f8e3b5bf-a30d-489e-8d3d-1210f636cc02', '26.0209', '26.0209', null, null, 828, 'Radiation Biology/Radiobiology.', 'kuali.enum.type.cip2000',  'CFB2478E4E954F2FAAC84531756E72A8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3903eb71-5104-4dbf-a4a0-49b74b211c09', '26.0210', '26.0210', null, null, 829, 'Biochemistry/Biophysics and Molecular Biology.', 'kuali.enum.type.cip2000',  '6903AE86EEAB41F0A5EE304455FCC619', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d8eabaea-617f-4b36-b01e-7b2d1de6bc0d', '26.0299', '26.0299', null, null, 830, 'Biochemistry, Biophysics and Molecular Biology, Other.',  'kuali.enum.type.cip2000', '1C9BECFBA98D42F58AEBD8DAEACD86BD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('223371b6-4db2-429f-bc8e-df93b8889e55', '26.03', '26.03', null, null, 831, 'Botany/Plant Biology.', 'kuali.enum.type.cip2000',  '889B39601A874533817FD8EFEE2BDD06', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('90c01399-6f50-4a48-a647-f42dede0ece1', '26.0301', '26.0301', null, null, 832, 'Botany/Plant Biology.', 'kuali.enum.type.cip2000',  'F15CD79F97D44B56B7416A9A9AC1B476', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('469c3bd9-8d49-4e58-9730-2fec63853955', '26.0305', '26.0305', null, null, 833, 'Plant Pathology/Phytopathology.', 'kuali.enum.type.cip2000',  'EB1BF8F364AA4A9F925B1257A5ED53DD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f9400648-ab77-4591-a208-f9faaf8d6103', '26.0307', '26.0307', null, null, 834, 'Plant Physiology.', 'kuali.enum.type.cip2000',  '74A1804EFDCD4F9B85293107F4CC31A5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('77768cb0-1d04-4fb1-b540-ada1195d8d6f', '26.0308', '26.0308', null, null, 835, 'Plant Molecular Biology.', 'kuali.enum.type.cip2000',  '89DD6E85761D4295AE5971835CAE68F6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ba819fb5-fc47-468f-954e-2ea8c167d6ed', '26.0399', '26.0399', null, null, 836, 'Botany/Plant Biology, Other.', 'kuali.enum.type.cip2000',  '4567D02D5F2C45AE8B2FE4174B258720', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bed35b65-4523-4065-92df-9f288a92b682', '26.04', '26.04', null, null, 837, 'Cell/Cellular Biology and Anatomical Sciences.', 'kuali.enum.type.cip2000',  '7314AC4A45FA467FBF86E68FE67EE4CC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1b26872b-505e-458f-9838-3b30acfac35b', '26.0401', '26.0401', null, null, 838, 'Cell/Cellular Biology and Histology.', 'kuali.enum.type.cip2000',  'DBB8C322DDAA491A9A5EE4794A5B9624', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2743e9df-2b81-4036-bc18-346e423fc81b', '26.0402', '26.0402', null, null, 839, 'Molecular Biology.', 'kuali.enum.type.cip2000',  '3172C667D0794EC588E03C20D3E48688', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9ea5185a-683c-4b63-9b72-18a7c6a6054a', '26.0403', '26.0403', null, null, 840, 'Anatomy.', 'kuali.enum.type.cip2000', '850D2C543F2E41ADBB1C5032A8D80429', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('02744eb8-1e84-46cd-bcda-2a33a45b0f49', '26.0404', '26.0404', null, null, 841, 'Developmental Biology and Embryology.', 'kuali.enum.type.cip2000',  '28248B9AE19048DBA366894301FB303A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b1e48160-13f3-4b12-99ea-5c173849e9e1', '26.0405', '26.0405', null, null, 842, 'Neuroanatomy.', 'kuali.enum.type.cip2000', 'F5159FDDC00F43B985D2445D54A1C3CD',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('201ea08a-bf09-48a5-b5ec-1a52f7ec85bd', '26.0406', '26.0406', null, null, 843, 'Cell/Cellular and Molecular Biology.', 'kuali.enum.type.cip2000',  '1ED3A47533BB4233A7966A6A51DCAD16', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9f36e590-9789-423c-bdfa-c49b99659207', '26.0407', '26.0407', null, null, 844, 'Cell Biology and Anatomy.', 'kuali.enum.type.cip2000',  'BBCC66789E3E41E8A96C64B4993F9A2A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('45535782-e6be-4ca8-a027-ee6973f63847', '26.0499', '26.0499', null, null, 845, 'Cell/Cellular Biology and Anatomical Sciences, Other.',  'kuali.enum.type.cip2000', '2ECF3405FA7E40EF8F06A10908A6CEAA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3a449c85-592e-44cf-a6f5-6cd3740e39e5', '26.05', '26.05', null, null, 846, 'Microbiological Sciences and Immunology.', 'kuali.enum.type.cip2000',  '1B8E950D01B84CB4AA53809167A05885', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ae8bc99c-889d-4402-b103-180c03ab9c36', '26.0501', '26.0501', null, null, 847, 'Microbiology/Bacteriology.', 'kuali.enum.type.cip2000',  '53D85DECE3AB44428B9B26CA35233385', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ba673845-c4f0-4134-96c0-b62b670cc46a', '26.0502', '26.0502', null, null, 848, 'Microbiology, General.', 'kuali.enum.type.cip2000',  '7883499A7E834E0F89D5D6DDD2EF1CDA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ebbae285-cd9b-49d5-ab78-b36c1cdf5ac9', '26.0503', '26.0503', null, null, 849, 'Medical Microbiology and Bacteriology.', 'kuali.enum.type.cip2000',  '389B19CF4D8542F4B6BFFF7B3C509A8A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('06455e50-bf68-435b-ace6-88175eaaa290', '26.0504', '26.0504', null, null, 850, 'Virology.', 'kuali.enum.type.cip2000', '40FB66D5E04345CB9B6385FDB2F077EE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f2513192-172f-4b63-89b4-6bf88a9c1ffd', '26.0505', '26.0505', null, null, 851, 'Parasitology.', 'kuali.enum.type.cip2000', '195F5E8C32764D6BB0FBEE6983907616',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6daff999-6218-4836-94b0-ab4ba5ecbfc6', '26.0506', '26.0506', null, null, 852, 'Mycology.', 'kuali.enum.type.cip2000', '4CFAA116259B4A0AB37D39A73BBB51B6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5aaaa258-bf23-487d-ae42-af8d330bd325', '26.0507', '26.0507', null, null, 853, 'Immunology.', 'kuali.enum.type.cip2000', '152698927656495F93CC0F94B800BD93', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c095c2bb-2fcf-4971-94c5-96048bf8f883', '26.0599', '26.0599', null, null, 854, 'Microbiological Sciences and Immunology, Other.', 'kuali.enum.type.cip2000',  'F9E5464E28494E5F90154026C1FF0C02', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('91373a99-4a81-4b46-a4b3-4360ffb6a0d2', '26.06', '26.06', null, null, 855, 'Miscellaneous Biological Specializations.', 'kuali.enum.type.cip2000',  '8B3AE3B7A55942369D7D62C615C29B85', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('73bed09e-f3f7-441f-b326-f9f0a3151dea', '26.0601', '26.0601', null, null, 856, 'Anatomy.', 'kuali.enum.type.cip2000', 'E098428BA1444A8091C2FF422D786590', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ad392962-ffed-4322-8ec9-0eed2ffce8b2', '26.0603', '26.0603', null, null, 857, 'Ecology.', 'kuali.enum.type.cip2000', 'DCA2FCBE7084416D86663861F3507719', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f3756dde-71f5-4766-8ae9-df845d80560f', '26.0607', '26.0607', null, null, 858, 'Marine/Aquatic Biology (Moved, Report under 26.', 'kuali.enum.type.cip2000',  '21B4172D4F4048DFB049DA0D959B8E4A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7137c945-57bd-4c80-8613-a6ff319dfcbb', '26.0608', '26.0608', null, null, 859, 'Neuroscience.', 'kuali.enum.type.cip2000', '4890414A62484AA9A97ABBB6C8A8DCCC',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('135881d1-ef78-46e6-9abd-58044dd259cc', '26.0609', '26.0609', null, null, 860, 'Nutrition Sciences.', 'kuali.enum.type.cip2000',  '0C5428AF7CC8498B811A000D6A3F0B3E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e7ea6c9b-9748-4edd-9b8e-d2a6cb98b83e', '26.0610', '26.0610', null, null, 861, 'Parasitology.', 'kuali.enum.type.cip2000', '68656D89109B4A28A290321F767BB361',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4c3a75d2-f45b-45f1-abc0-b06b5adb2464', '26.0611', '26.0611', null, null, 862, 'Radiation Biology/Radiobiology.', 'kuali.enum.type.cip2000',  '0403E62B851F41FE987D8BCF6799EDAD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2b46ffbb-ea57-4f5d-95ce-238951da2220', '26.0612', '26.0612', null, null, 863, 'Toxicology.', 'kuali.enum.type.cip2000', 'C28A38A4985E4CB78BAFA91E7AA3C0E2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('dc6c223b-855d-4e27-9e04-16dde7bb0bd3', '26.0613', '26.0613', null, null, 864, 'Genetics, Plant and Animal.', 'kuali.enum.type.cip2000',  '8D67A78D80654D7CA98594B56B84CCDF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1137d705-72a6-49a0-b64b-5ffc6de1d5e8', '26.0614', '26.0614', null, null, 865, 'Biometrics.', 'kuali.enum.type.cip2000', '0015B85CCD5C44F5B53E39B4C35650FD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bb9d20db-cf9a-4c13-a1f1-56c7a770827a', '26.0615', '26.0615', null, null, 866, 'Biostatistics.', 'kuali.enum.type.cip2000', '98D52751BC854930BFA6D68F24EC7C83',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2e8761bf-d91f-4708-a571-705e920a08a2', '26.0616', '26.0616', null, null, 867, 'Biotechnology.', 'kuali.enum.type.cip2000', 'EFD720F8F2C54861BD0643969C65A3F5',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('16439d38-3e14-489d-bc6b-e64c3dd65db5', '26.0617', '26.0617', null, null, 868, 'Evolutionary Biology.', 'kuali.enum.type.cip2000',  '26681C3E1E9F42908678C6023120362A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1e24bdad-670f-4037-b8b6-a0b86cb15c79', '26.0618', '26.0618', null, null, 869, 'Biological Immunology.', 'kuali.enum.type.cip2000',  '37B501B8D6984C7FAB3A1F1EC0DCC495', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ba56db65-4738-41cd-ad81-9a8e468c2be6', '26.0619', '26.0619', null, null, 870, 'Virology.', 'kuali.enum.type.cip2000', '884994DF5C2C401DA9914CB7F9088E1C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ba047c96-86e4-42c4-bccb-957a325cf57b', '26.0699', '26.0699', null, null, 871, 'Miscellaneous Biological Speclizations, Other.', 'kuali.enum.type.cip2000',  'E36C24AA97D849CE86409C42766A262A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('31513c7b-0926-499f-ae89-223cb69b875d', '26.07', '26.07', null, null, 872, 'Zoology/Animal Biology.', 'kuali.enum.type.cip2000',  '62F71DA050EC430A93334BC241E3AADD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5ccda062-c632-4426-92ab-29a28f8f8791', '26.0701', '26.0701', null, null, 873, 'Zoology/Animal Biology.', 'kuali.enum.type.cip2000',  '89815C07F83C47EC8273C4BBFEAC0F3A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('74646a96-4d37-4551-9dfb-4700e1f81696', '26.0702', '26.0702', null, null, 874, 'Entomology.', 'kuali.enum.type.cip2000', 'BFCDB443A76D4D5385D7E746BFCE5F40', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('10e26c7e-5303-437d-a7e3-73797fa1529f', '26.0704', '26.0704', null, null, 875, 'Pathology, Human and Animal.', 'kuali.enum.type.cip2000',  '0A1F0DDD688A4CDBB5B3E6F33F8340AE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('34ace1c9-d445-44fd-8809-278ddcd0a1f5', '26.0705', '26.0705', null, null, 876, 'Pharmacology.', 'kuali.enum.type.cip2000', 'D64A30B323C04EACA026CB1D3D22F925',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1908ea92-fc21-41a0-8fb7-8b8db4a5caae', '26.0706', '26.0706', null, null, 877, 'Physiology, Human and Animal.', 'kuali.enum.type.cip2000',  '14CCC8FE76A84DA3870E82275FC722C1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d667c44d-5d79-45a3-a392-26c33d7b686c', '26.0707', '26.0707', null, null, 878, 'Animal Physiology.', 'kuali.enum.type.cip2000',  'D3F26C70D9914CB082810E0FE7E5804B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('cb7a2ef4-a026-4eb4-9e60-05947cd9b062', '26.0708', '26.0708', null, null, 879, 'Animal Behavior and Ethology.', 'kuali.enum.type.cip2000',  '44B9D20E5D7E47418C08C64AF4AFD16C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b82ecf2c-38c2-41e5-94e2-8e03628ec93e', '26.0709', '26.0709', null, null, 880, 'Wildlife Biology.', 'kuali.enum.type.cip2000',  'D779E1FFD2C8434CB6134B58AF262545', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7ef73d4e-b521-4bf4-a129-1c01776f1b65', '26.0799', '26.0799', null, null, 881, 'Zoology/Animal Biology, Other.', 'kuali.enum.type.cip2000',  '9BD875BA77704A1C989D124A927D5375', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3a3f168f-8c6c-4605-bc10-80528cdd30aa', '26.08', '26.08', null, null, 882, 'Genetics.', 'kuali.enum.type.cip2000', 'A29BA3CC0F7244BE81F06DA74AEBA132', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f8f94b97-cc21-4ab3-afc0-6a292802abc4', '26.0801', '26.0801', null, null, 883, 'Genetics, General.', 'kuali.enum.type.cip2000',  '1F64CEE5136C4279AFE9DABE1B149639', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5e7ef8ff-0fe8-45c9-9044-b87e25ab8d25', '26.0802', '26.0802', null, null, 884, 'Molecular Genetics.', 'kuali.enum.type.cip2000',  'FA566408D4374CB7BF0FF729E136665A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1d70c2a9-93d6-4457-a2fb-906579f11352', '26.0803', '26.0803', null, null, 885, 'Microbial and Eukaryotic Genetics.', 'kuali.enum.type.cip2000',  '762F7FEDCDBE4417B9749CB833993299', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('29acb6b6-1f69-4e19-9a85-6b9c83e7682c', '26.0804', '26.0804', null, null, 886, 'Animal Genetics.', 'kuali.enum.type.cip2000',  '18120552E9E34565AB650CE39A3AC393', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('17a91c9a-f651-48da-a7e1-28720f3c9ad5', '26.0805', '26.0805', null, null, 887, 'Plant Genetics.', 'kuali.enum.type.cip2000',  'D2D2F5726FA04C669AB76BD669E246E6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('432957d7-09a0-4d9f-a09c-2746015c9a43', '26.0806', '26.0806', null, null, 888, 'Human/Medical Genetics.', 'kuali.enum.type.cip2000',  'C3EEC17EA65048BB91203A312C3BE3A5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7eecee47-7af3-4a00-9f6a-1d3e90aaee1f', '26.0899', '26.0899', null, null, 889, 'Genetics, Other.', 'kuali.enum.type.cip2000',  '9F3562393DA34EE28BFB020034EECF1A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4062cb6e-3e4b-4202-8f5f-0fd04d0e88b9', '26.09', '26.09', null, null, 890, 'Physiology, Pathology and Related Sciences.', 'kuali.enum.type.cip2000',  '5438509929E24BBEBC6BD87637FA3B0C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ed59c832-5d99-4110-b412-4fa9a13f8bc4', '26.0901', '26.0901', null, null, 891, 'Physiology, General.', 'kuali.enum.type.cip2000',  '108366CD55764578A1F554F3F694715D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ada0c548-4008-4a67-8bbd-9e4d024c3adf', '26.0902', '26.0902', null, null, 892, 'Molecular Physiology.', 'kuali.enum.type.cip2000',  '51C7F762EDD44C39A884BEFB40BFE4F4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bcb16550-663a-4278-9360-7e268fdc667c', '26.0903', '26.0903', null, null, 893, 'Cell Physiology.', 'kuali.enum.type.cip2000',  '4C64B5216FE84B2ABF92A9C47E72A8B5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9db54a11-bb96-40a3-a03f-bf55de3124e2', '26.0904', '26.0904', null, null, 894, 'Endocrinology.', 'kuali.enum.type.cip2000', '537C7EEB11924428B9DBFD1329EB58C9',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('aa950794-1a2b-43b1-93d9-d091a86c7d41', '26.0905', '26.0905', null, null, 895, 'Reproductive Biology.', 'kuali.enum.type.cip2000',  'A444766C2D9141F2A1E404F6C47B1694', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5b381d0e-a376-4c5d-a671-598fd0b8142d', '26.0906', '26.0906', null, null, 896, 'Neurobiology and Neurophysiology.', 'kuali.enum.type.cip2000',  '1C1696D47339496F886751A26FDE181A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e8b8cf79-db02-434f-b141-92e6f0ab40e5', '26.0907', '26.0907', null, null, 897, 'Cardiovascular Science.', 'kuali.enum.type.cip2000',  'E9160FFEF3F045F69480B52D5CBFD621', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8a995112-d0f9-45e1-ba3a-e7ce52eb4eb3', '26.0908', '26.0908', null, null, 898, 'Exercise Physiology.', 'kuali.enum.type.cip2000',  '06F0B2DCEA2342CA84197A48E37671BC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8b30caf8-209e-471b-83b0-d657749c0de8', '26.0909', '26.0909', null, null, 899, 'Vision Science/Physiological Optics.', 'kuali.enum.type.cip2000',  'DE2C03F4113145C6804721D3A75F1365', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a19895c6-e005-489c-adc2-0af2c025064f', '26.0910', '26.0910', null, null, 900, 'Pathology/Experimental Pathology.', 'kuali.enum.type.cip2000',  '35B3793352104367AB91DAD3E5B2B642', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d278bc62-6d67-468d-b0a0-802703b03512', '26.0911', '26.0911', null, null, 901, 'Oncology and Cancer Biology.', 'kuali.enum.type.cip2000',  '6DCD477E405A45D4918050D66F8BFA9C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('23b0cabf-e38a-46ac-966a-5fdbb0e70848', '26.0999', '26.0999', null, null, 902, 'Physiology, Pathology, and Related Sciences, Other.',  'kuali.enum.type.cip2000', 'F4B521A94034462D972A416C2A9E894D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e5eec61c-35a4-4e94-9256-b8e2981364ef', '26.10', '26.10', null, null, 903, 'Pharmacology and Toxicology.', 'kuali.enum.type.cip2000',  'C8420CD67C284D5683F9EA254CC29E5A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f78289a8-e895-422e-9715-af3b80b6d47a', '26.1001', '26.1001', null, null, 904, 'Pharmacology.', 'kuali.enum.type.cip2000', '70344AD60AAE4221AB82173CC81F844E',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0d9a513f-3c7a-4b80-9547-d4727c8518cb', '26.1002', '26.1002', null, null, 905, 'Molecular Pharmacology.', 'kuali.enum.type.cip2000',  '6CFB172711D24B6F97ED68D601AC28BD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('cd4c4d66-5994-4814-ba07-d1caa5e2b7cd', '26.1003', '26.1003', null, null, 906, 'Neuropharmacology.', 'kuali.enum.type.cip2000',  '8863A1276226494B9F9CC01D642FA3BB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bb6fffc4-89e2-4d64-b8be-2e15d0754cec', '26.1004', '26.1004', null, null, 907, 'Toxicology.', 'kuali.enum.type.cip2000', '8F013987408C477EA85BEF0EC5ABC3C7', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('331d6073-0377-424e-915a-718d286937c7', '26.1005', '26.1005', null, null, 908, 'Molecular Toxicology.', 'kuali.enum.type.cip2000',  '8DAD607B98094B03B971223DFE861BF5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('86c74f8c-486e-42b9-97ad-aa47c97ac001', '26.1006', '26.1006', null, null, 909, 'Environmental Toxicology.', 'kuali.enum.type.cip2000',  '60CA35059C2443FB97650D9B3D28649B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('11e6fc2f-d204-4273-98bf-6b1c0ba7526a', '26.1007', '26.1007', null, null, 910, 'Pharmacology and Toxicology.', 'kuali.enum.type.cip2000',  'B10E73E4D40043B5BD0BAF470F7015B7', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3069e8e0-a9f7-4beb-ae0c-1a71553240e3', '26.1099', '26.1099', null, null, 911, 'Pharmacology and Toxicology, Other.', 'kuali.enum.type.cip2000',  '0271B191480643B8963575EA8F30A7A0', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('323af58d-4a84-4ca5-bf6e-6417626b0d72', '26.11', '26.11', null, null, 912, 'Biomathematics and Bioinformatics.', 'kuali.enum.type.cip2000',  '686A888649394BA781A850F4345170DD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('68b7c650-6200-4d2c-aa6d-d819eeb398bc', '26.1101', '26.1101', null, null, 913, 'Biometry/Biometrics.', 'kuali.enum.type.cip2000',  'B855D382B5B74FC0A08DC0241436B7AA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8ca6ed48-b668-48f3-90dc-bb40bd3080cd', '26.1102', '26.1102', null, null, 914, 'Biostatistics.', 'kuali.enum.type.cip2000', 'E96C6D6FD6014564B1BBACD851D6A2E1',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('63d164d7-b7ff-4ff0-995d-6c073fd84f80', '26.1103', '26.1103', null, null, 915, 'Bioinformatics.', 'kuali.enum.type.cip2000',  'F93E29F653D04E6A8C69D6B4489A68F4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('07178a01-a741-408d-957f-8936389e8cfe', '26.1199', '26.1199', null, null, 916, 'Biomathematics and Bioinformatics, Other.', 'kuali.enum.type.cip2000',  '34C49036E2694D2BB683CA1CEA27AAEE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9bfa8ff7-d4f4-4b67-8f4f-da3eec104719', '26.12', '26.12', null, null, 917, 'Biotechnology.', 'kuali.enum.type.cip2000', 'CD67BBBBFEB54252AF31D4DDCE86936E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1fcce644-15da-41ed-ba13-553ac4e39232', '26.1201', '26.1201', null, null, 918, 'Biotechnology.', 'kuali.enum.type.cip2000', 'F0399DD144254D709DBEF449C94C7C81',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8dbfe473-4721-4d2d-ba78-a47d9222b261', '26.13', '26.13', null, null, 919, 'Ecology, Evolution, Systematics, and Population Biology.',  'kuali.enum.type.cip2000', 'E9336C55CBA144C488E2FC99CF4D07E8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5ce1030b-4082-4162-9ed1-50b240be32c4', '26.1301', '26.1301', null, null, 920, 'Ecology.', 'kuali.enum.type.cip2000', '810BE3FA2E0F4066B3A4BD05670105A5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e704d885-74bc-4cc4-b3c6-eccd95c20542', '26.1302', '26.1302', null, null, 921, 'Marine Biology and Biological Oceanography.', 'kuali.enum.type.cip2000',  '254932961DB746BAA5E5C8540B54F68D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7ba57dd4-c61e-4082-bbb4-25eadaa6ce75', '26.1303', '26.1303', null, null, 922, 'Evolutionary Biology.', 'kuali.enum.type.cip2000',  'DAFE3DB0C0A747059E7F8EA7C53B68A4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('33321237-ceb2-4a4d-bbd4-4720b537d90c', '26.1304', '26.1304', null, null, 923, 'Aquatic Biology/Limnology.', 'kuali.enum.type.cip2000',  '4340BC7375DA47EAB3D313780701B985', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('50a1dad6-9019-46d6-94e9-33a4b671c161', '26.1305', '26.1305', null, null, 924, 'Environmental Biology.', 'kuali.enum.type.cip2000',  '174514E84F834E3684541A4FBAA85032', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('02f528fe-4189-4d32-8bac-505606b3114c', '26.1306', '26.1306', null, null, 925, 'Population Biology.', 'kuali.enum.type.cip2000',  '83923C9F19E64DC9B3935EB4724C7C4E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('98a1fca0-2258-46fc-999e-deb5b54f60e4', '26.1307', '26.1307', null, null, 926, 'Conservation Biology.', 'kuali.enum.type.cip2000',  'D893E472BF0845B5BA4D493C85654865', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6d2a79d9-22af-4c20-b00a-6b483c02bf99', '26.1308', '26.1308', null, null, 927, 'Systematic Biology/Biological Systematics.', 'kuali.enum.type.cip2000',  '016A3BD20EB7458FA89A341D909DAF7F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2447dd14-06c3-4e87-9049-e34799375dc6', '26.1309', '26.1309', null, null, 928, 'Epidemiology.', 'kuali.enum.type.cip2000', '734D7C3E3EB840A0A15BD7447371AAE8',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('fc2c098e-5b7d-4b80-9563-e4993d734555', '26.1399', '26.1399', null, null, 929, 'Ecology, Evolution, Systematics and Population Biology, Other.',  'kuali.enum.type.cip2000', '1B10AE0426B24870A0CDC33968BAD5DF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('691ec619-82a2-4f75-a53e-feae4deee2b0', '26.99', '26.99', null, null, 930, 'Biological and Biomedical Sciences, Other.', 'kuali.enum.type.cip2000',  'CA71E48CDB494CB5887B40823F1AEC27', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e5497891-a435-437b-ba40-49e8b7fd5380', '26.9999', '26.9999', null, null, 931, 'Biological and Biomedical Sciences, Other.', 'kuali.enum.type.cip2000',  '4E2D468302394925A71590C7409FB9FA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f49f6c9a-29af-46ad-826f-618f4ad5b92f', '27.', '27.', null, null, 932, 'MATHEMATICS AND STATISTICS.', 'kuali.enum.type.cip2000',  'E3027B166F65470BAF7D372992E8A33B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('96b0a159-8c5b-47d6-afe7-6e3eaa274e52', '27.01', '27.01', null, null, 933, 'Mathematics.', 'kuali.enum.type.cip2000', '80726BAAA16E4C58BBAFFAA391139082', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7251a9f9-c4ad-4c1e-88ec-84a66ea31669', '27.0101', '27.0101', null, null, 934, 'Mathematics, General.', 'kuali.enum.type.cip2000',  '87990BB6DE56424E999CC8B2619378DB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e342b3a9-db46-4963-9a00-8d6af67b9dff', '27.0102', '27.0102', null, null, 935, 'Algebra and Number Theory.', 'kuali.enum.type.cip2000',  'CE68D69682CC4FDB8D8980ACDF75599F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4ef67731-7247-490c-bf22-73c426a55e48', '27.0103', '27.0103', null, null, 936, 'Analysis and Functional Analysis.', 'kuali.enum.type.cip2000',  'D5BF9562C7C2431BBDE6F4AC0042DC9A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e60d795c-7fb3-43a0-8025-850722456b95', '27.0104', '27.0104', null, null, 937, 'Geometry/Geometric Analysis.', 'kuali.enum.type.cip2000',  '418CD7EEE8B14B5E83535541D02B9A57', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('69efcb88-7288-480d-93b6-521437e17ad0', '27.0105', '27.0105', null, null, 938, 'Topology and Foundations.', 'kuali.enum.type.cip2000',  '6A195DB365E3461CB4A6725863753935', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2c7b5024-c2e5-4520-a4c3-eb99478a96b5', '27.0199', '27.0199', null, null, 939, 'Mathematics, Other.', 'kuali.enum.type.cip2000',  '4EB132A8D325484D86D8883DE758FF2F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ddc5c8f0-3b31-4c6f-8aeb-da9d37a7f598', '27.03', '27.03', null, null, 940, 'Applied Mathematics.', 'kuali.enum.type.cip2000',  '0431E6071A6D4A87AD5966EECA03B006', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('dbff9834-3510-43ec-b81a-6b887e0fe969', '27.0301', '27.0301', null, null, 941, 'Applied Mathematics.', 'kuali.enum.type.cip2000',  '6B2F6D8ED2034C8386052F14619B1FD4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('18f5fd26-f302-4b03-8542-b8c0da69e677', '27.0302', '27.0302', null, null, 942, 'Operations Research.', 'kuali.enum.type.cip2000',  '3381296AF020449EA9681F7D029E9EE6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b9ad3400-8f64-46c3-90b5-f64a32fedd7a', '27.0303', '27.0303', null, null, 943, 'Computational Mathematics.', 'kuali.enum.type.cip2000',  'D5685E398B2C42D5BC1DA91E1DFF3678', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('291ee98a-d9e8-4973-a021-9a0ca5a6d175', '27.0399', '27.0399', null, null, 944, 'Applied Mathematics, Other.', 'kuali.enum.type.cip2000',  '6A1763972ACF49D1851A71AB31CF34FC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8105becd-0cd9-4183-9f04-058d4b57fe1c', '27.05', '27.05', null, null, 945, 'Statistics.', 'kuali.enum.type.cip2000', 'A9C1AED721B64E8485068757007D5492', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a1298765-2aa5-4b92-9a5c-f2306f1b332b', '27.0501', '27.0501', null, null, 946, 'Statistics, General.', 'kuali.enum.type.cip2000',  'A916EDAABDEE4D099139F1182B300F91', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('99177fcb-6a16-4cb3-baf8-1afc9cad7a45', '27.0502', '27.0502', null, null, 947, 'Mathematical Statistics and Probability.', 'kuali.enum.type.cip2000',  '00D7CB810A7743688C24CFC3CF594D64', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('aea79286-f915-40e8-b739-8d0d72761ca4', '27.0599', '27.0599', null, null, 948, 'Statistics, Other.', 'kuali.enum.type.cip2000',  '790690A9BC264B90B8A7F64F40E2C776', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('63c9eebc-c5d1-46df-8013-d60d75986a36', '27.99', '27.99', null, null, 949, 'Mathematics and Statistics, Other.', 'kuali.enum.type.cip2000',  '5440702F40F6413A928C0EDE4AEFE868', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e0af592e-c498-4823-88f9-ddb13fe98021', '27.9999', '27.9999', null, null, 950, 'Mathematics and Statistics, Other.', 'kuali.enum.type.cip2000',  'F6347330EE8647D89D160F77954B33CF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('abf095dc-7e19-4914-8c8c-536d0e7703a4', '28.', '28.', null, null, 951, 'RESERVE OFFICER TRAINING CORPS (JROTC, ROTC).', 'kuali.enum.type.cip2000',  'E269968674B947669A0D218B727C258A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('dfc3d6a8-6806-43e1-a7a6-bca6b71f4f78', '28.', '28.', null, null, 952, 'Programs for Series 28.', 'kuali.enum.type.cip2000',  '07C10D49C27645D194355B1962257BB0', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d8a9cd4d-e4fe-4bc2-80d5-60be490943e9', '28.01', '28.01', null, null, 953, 'Air Force JROTC/ROTC.', 'kuali.enum.type.cip2000',  '0170CD7416D849F4924313D55BCCC352', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('19087336-415f-4a38-9eb8-8fc78ddd1c33', '28.0101', '28.0101', null, null, 954, 'Air Force JROTC/ROTC.', 'kuali.enum.type.cip2000',  '591FF121B13C403DAD2993F5BE61D36B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('afbc1729-bae4-4c10-be65-8ab49bbd212e', '28.03', '28.03', null, null, 955, 'Army JROTC/ROTC.', 'kuali.enum.type.cip2000', 'FBB487C3721C45DC88EC0E583975DD05',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5d446095-3774-4df0-93fd-5a2449bfae10', '28.0301', '28.0301', null, null, 956, 'Army JROTC/ROTC.', 'kuali.enum.type.cip2000',  '1B6A8262DC5B40E1A874056B1C57FB4F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('42c0632c-8583-436e-a9cd-59ece52ccf16', '28.04', '28.04', null, null, 957, 'Navy/Marine Corps JROTC/ROTC.', 'kuali.enum.type.cip2000',  '4E8A78BC2389461A8CDA82D822660268', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0e8c33f8-1d69-4820-9afc-fbdbdcfce552', '28.0401', '28.0401', null, null, 958, 'Navy/Marine Corps JROTC/ROTC.', 'kuali.enum.type.cip2000',  '1B0BEE0F66F54209BC4F4C5CEBCDDA3C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('eeb3f8d3-21dd-4ed8-9e6b-ee4c89550dc1', '29.', '29.', null, null, 959, 'MILITARY TECHNOLOGIES.', 'kuali.enum.type.cip2000', '1705674A729642ECAE941AB6FC8EAB30',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f526923f-fa18-4ea7-925d-8e90efde7430', '29.01', '29.01', null, null, 960, 'Military Technologies.', 'kuali.enum.type.cip2000',  'BDFC817952C74EEDBC53EA72CA511E50', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8dc76cf7-3b67-4027-8123-7e886ac0a7c9', '29.0101', '29.0101', null, null, 961, 'Military Technologies.', 'kuali.enum.type.cip2000',  'EC5C347DB61A46FF964169F9A8DDB7A1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f0638a1e-f7c5-4e18-ad8b-749db437e342', '30.', '30.', null, null, 962, 'MULTI/INTERDISCIPLINARY STUDIES.', 'kuali.enum.type.cip2000',  '6AA8E4A6FABF4868B2915ED958611CBE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('273183b2-b5ab-48d6-815a-dc53d3235af4', '30.01', '30.01', null, null, 963, 'Biological and Physical Sciences.', 'kuali.enum.type.cip2000',  '5C1338F1C1D641BAB422F2EBC50D66A1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('50ac655f-5039-45f9-82d8-1fc5738e90c3', '30.0101', '30.0101', null, null, 964, 'Biological and Physical Sciences.', 'kuali.enum.type.cip2000',  '58577A6EDE0B493D9DAE99AA74AC715F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('96a479a5-bcac-43a3-8b6c-5ce74873d600', '30.05', '30.05', null, null, 965, 'Peace Studies and Conflict Resolution.', 'kuali.enum.type.cip2000',  '7E971F6D91E449C086A284864C228A22', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ab95be82-0371-4bc5-86d0-fbd4e17796ca', '30.0501', '30.0501', null, null, 966, 'Peace Studies and Conflict Resolution.', 'kuali.enum.type.cip2000',  '995E996FA50248DAA67BB726AEC2CFED', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ad101fc0-a7b8-4cd9-a825-acbe634701c8', '30.06', '30.06', null, null, 967, 'Systems Science and Theory.', 'kuali.enum.type.cip2000',  '10D0FD43384A4FB8BA5B41B03B6C1AA1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3373365d-c2ce-4c17-8327-289764e7fd27', '30.0601', '30.0601', null, null, 968, 'Systems Science and Theory.', 'kuali.enum.type.cip2000',  '1482B17EF7324A2AB01A60B194B32E37', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6ea303a9-096d-4797-ba90-1b5cccd29374', '30.08', '30.08', null, null, 969, 'Mathematics and Computer Science.', 'kuali.enum.type.cip2000',  'AE88247975C44179B497F714A01EAC52', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6841a205-cf37-4c5b-8228-8d22809891d5', '30.0801', '30.0801', null, null, 970, 'Mathematics and Computer Science.', 'kuali.enum.type.cip2000',  '70D4D66AE2F548A1950BB2EF8D468D75', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('55b6ae90-0a1d-4fe8-8f2d-5653e00affbb', '30.10', '30.10', null, null, 971, 'Biopsychology.', 'kuali.enum.type.cip2000', 'F47D164A95F4497681F11B6F3AF4E85A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('043cca71-82a0-41c2-ae48-53d1d930fe05', '30.1001', '30.1001', null, null, 972, 'Biopsychology.', 'kuali.enum.type.cip2000', 'FB22727B5DD7440BB5A9056BFEA9F981',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('17647962-f57a-4e8f-8f13-cf79002de9cd', '30.11', '30.11', null, null, 973, 'Gerontology.', 'kuali.enum.type.cip2000', 'E6B2B9D3D8FD4A848B517E649AD9CFD2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('36193fce-20ef-4b3c-866a-b0f02aab1127', '30.1101', '30.1101', null, null, 974, 'Gerontology.', 'kuali.enum.type.cip2000', 'C57188A7A7F846C79CE8635F518F3AE2',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b1f6a576-56c9-4f7f-b77c-7ff961931f41', '30.12', '30.12', null, null, 975, 'Historic Preservation and Conservation.', 'kuali.enum.type.cip2000',  'EC029A3A4A1245CCA2807D59E623D703', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1bb4e6d3-1dfd-4c5c-8b44-0b74a312d0ba', '30.1201', '30.1201', null, null, 976, 'Historic Preservation and Conservation.', 'kuali.enum.type.cip2000',  '160D4DC411134581906FAAB27AB9E399', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('10d56b6c-8ece-4237-96e5-302c98a3c925', '30.1202', '30.1202', null, null, 977, 'Cultural Resource Management and Policy Analysis.', 'kuali.enum.type.cip2000',  'BE6756994D914C9BB5D45ACA91D9FD73', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9347a0ea-cb2c-400c-92ff-9b61c062465c', '30.1299', '30.1299', null, null, 978, 'Historic Preservation and Conservation, Other.', 'kuali.enum.type.cip2000',  '79E3F8343BF141828B2C95A5CDA1E946', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('93ce3a6e-8838-4595-9c09-7a110f10ac6e', '30.13', '30.13', null, null, 979, 'Medieval and Renaissance Studies.', 'kuali.enum.type.cip2000',  '71C67F1C7A1B467DABC2A2B9B3F838C1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('280ac500-8cea-4bf7-9828-fa20a0e82b18', '30.1301', '30.1301', null, null, 980, 'Medieval and Renaissance Studies.', 'kuali.enum.type.cip2000',  '28BFDBB271954BE7885469F220838640', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6a568475-2529-40af-bb22-f46d35b5a4ad', '30.14', '30.14', null, null, 981, 'Museology/Museum Studies.', 'kuali.enum.type.cip2000',  '870C690CF7BB48EE8FD903C40FEEBCD2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2c612b59-78e7-457d-a4ee-8c3a1a89be30', '30.1401', '30.1401', null, null, 982, 'Museology/Museum Studies.', 'kuali.enum.type.cip2000',  'B4254CE2CC0C48D5BD34B97D87C9AA9A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('07060a75-7e36-4d1a-9928-11c624643056', '30.15', '30.15', null, null, 983, 'Science, Technology and Society.', 'kuali.enum.type.cip2000',  '0B5F5F8CB2E74348BAAB7845BDFF69C2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ee821b32-b4a0-4b85-925f-364948741d0c', '30.1501', '30.1501', null, null, 984, 'Science, Technology and Society.', 'kuali.enum.type.cip2000',  '69C6AD8ED1F745068DD06BCE792B47DE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f81c099f-8c23-4374-8f58-c4152579b5e4', '30.16', '30.16', null, null, 985, 'Accounting and Computer Science.', 'kuali.enum.type.cip2000',  '75123A49B9BF4499B3C8F21D3380C78F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c99846ec-9e69-4d70-953c-f1244960014c', '30.1601', '30.1601', null, null, 986, 'Accounting and Computer Science.', 'kuali.enum.type.cip2000',  '926B8CBC2A0C4F21B0A420AEE1D4138C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('90b747dc-f3f2-4a64-abd3-69d0a8f6a0e4', '30.17', '30.17', null, null, 987, 'Behavioral Sciences.', 'kuali.enum.type.cip2000',  '68217B3DD74744A5B621C030FC09ACE5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('27fc93d4-4b53-43ac-a620-53c1101f6e1d', '30.1701', '30.1701', null, null, 988, 'Behavioral Sciences.', 'kuali.enum.type.cip2000',  '307D811CA41E48DD9E2EA8217CA5950E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a1a1cfd0-818a-49cb-aec1-55d25dcae47c', '30.18', '30.18', null, null, 989, 'Natural Sciences.', 'kuali.enum.type.cip2000', 'BC7B5BBB70634135A90A0632F0BCBDDC',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7154c9f2-0e1c-4a62-b076-842ecd4ba58c', '30.1801', '30.1801', null, null, 990, 'Natural Sciences.', 'kuali.enum.type.cip2000',  '39580ECA9C894CE69DB3B18EE748FCAD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f0bbe388-9cc5-4b2a-9f9b-315aa91eadf7', '30.19', '30.19', null, null, 991, 'Nutrition Sciences.', 'kuali.enum.type.cip2000',  'E4FEB9A0C3A346E598D72224038D974A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('23b01a43-c054-444b-bdeb-ca6041cb1306', '30.1901', '30.1901', null, null, 992, 'Nutrition Sciences.', 'kuali.enum.type.cip2000',  'A26ABDB54E18468BBF63E3A56EF5AAA0', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('38c6625f-1a8f-4bee-ad2e-daa1c83f51b6', '30.20', '30.20', null, null, 993, 'International/Global Studies.', 'kuali.enum.type.cip2000',  '08E8BB9585974D098AE47743DCB8B575', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d1ce9eaa-5dc6-4507-84a2-ffd7a0b3d54f', '30.2001', '30.2001', null, null, 994, 'International/Global Studies.', 'kuali.enum.type.cip2000',  '773D814B465742FC86A8E16D55A457B0', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('39ab5990-864b-4095-99eb-2d04e7e90bdd', '30.21', '30.21', null, null, 995, 'Holocaust and Related Studies.', 'kuali.enum.type.cip2000',  '35AFBF85E9214336A5AFC47A0330759C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b93eedc6-4fb5-4215-9d29-c339490e2222', '30.2101', '30.2101', null, null, 996, 'Holocaust and Related Studies.', 'kuali.enum.type.cip2000',  '5AE60947FC2E4456B1AAF0009DD818AD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d13fc7a6-802b-464f-9fdf-28ee2b49a148', '30.22', '30.22', null, null, 997, 'Classical and Ancient Studies.', 'kuali.enum.type.cip2000',  'BA32545F2420463FB08EF6FBF36BE4BA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bddd48a2-d8e6-44a2-97a4-44439403ab36', '30.2201', '30.2201', null, null, 998, 'Ancient Studies/Civilization.', 'kuali.enum.type.cip2000',  '7CA5BECA9A924F5282F61F6BDD704522', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8f986055-5ce7-4578-ae32-c78a87228ffb', '30.2202', '30.2202', null, null, 999, 'Classical, Ancient Mediterranean and Near Eastern Studies and Archaeology.',  'kuali.enum.type.cip2000', 'BFBCF4D71B304BF6B7724C48BF6A405B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ba157df4-7884-4431-8ed9-a6fc53a18255', '30.23', '30.23', null, null, 1000, 'Intercultural/Multicultural and Diversity Studies.', 'kuali.enum.type.cip2000',  'B25F5098F68F48B38367A88DC93859DA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3ae48c55-a908-4b96-87ec-36eb1ce43c77', '30.2301', '30.2301', null, null, 1001, 'Intercultural/Multicultural and Diversity Studies.',  'kuali.enum.type.cip2000', '3321811595234DAA883D04836379D1F4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('799029fd-3dd7-4a6a-ab3c-b9b04fb40282', '30.24', '30.24', null, null, 1002, 'Neuroscience.', 'kuali.enum.type.cip2000', 'D5D17F67B965434DAF767A6544A235FD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9a655a2a-0091-436e-acd0-541be9f04d7a', '30.2401', '30.2401', null, null, 1003, 'Neuroscience.', 'kuali.enum.type.cip2000', '0F91EB27D28147BAB3B5E515EF071978',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0f83c23b-7626-47cc-9684-19262092682f', '30.25', '30.25', null, null, 1004, 'Cognitive Science.', 'kuali.enum.type.cip2000',  '93F305D471FE491A9C556986CAD8750F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('826efa4b-9166-457c-8dcc-faba4fda5dbf', '30.2501', '30.2501', null, null, 1005, 'Cognitive Science.', 'kuali.enum.type.cip2000',  'DA55418799E34317ABCB0EBD7EACC1AF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e76669f9-3558-4283-8862-2e1f1a209f7b', '30.99', '30.99', null, null, 1006, 'Multi/Interdisciplinary Studies, Other.', 'kuali.enum.type.cip2000',  '062FE4337F29400C8EB8F3F065011046', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d2dd9204-1e15-443b-9e41-08c0d9502e67', '30.9999', '30.9999', null, null, 1007, 'Multi-/Interdisciplinary Studies, Other.', 'kuali.enum.type.cip2000',  '58D131C1A93C4A5E828EFD3DDB7953D4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('37716c3c-2a39-436e-ad66-1af4eedeb90e', '31.', '31.', null, null, 1008, 'PARKS, RECREATION, LEISURE, AND FITNESS STUDIES.', 'kuali.enum.type.cip2000',  '671F83D1670E4BCF9B81837C20857BB8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6c88d8cd-e5ee-4248-a505-dcbd07936e14', '31.01', '31.01', null, null, 1009, 'Parks, Recreation and Leisure Studies.', 'kuali.enum.type.cip2000',  'A331983AC51742D7A5AF3276D4DD7B97', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0cf4d4ea-388b-494f-9d44-3431fe61a044', '31.0101', '31.0101', null, null, 1010, 'Parks, Recreation and Leisure Studies.', 'kuali.enum.type.cip2000',  'EF24D866084D4BF8B23CBAB684A18028', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0c0163c7-30c6-43d5-ba5c-64c082ecf556', '31.03', '31.03', null, null, 1011, 'Parks, Recreation and Leisure Facilities Management.', 'kuali.enum.type.cip2000',  'DB869EDF62C4402AB53EE6F7944E28FC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('21fb6c57-bd26-4259-85b1-8572391c3ee7', '31.0301', '31.0301', null, null, 1012, 'Parks, Recreation and Leisure Facilities Management.',  'kuali.enum.type.cip2000', '16ECA9C6A4B643AA82C7FF9A6D623EC1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('21ecf68d-cd1a-4f7e-9c5b-9cfade426627', '31.05', '31.05', null, null, 1013, 'Health and Physical Education/Fitness.', 'kuali.enum.type.cip2000',  '5A3CA5DE1F3C4F7A9918D5BE5AB666EA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a794006e-fe5a-476b-9a8f-9b70a2be7a7e', '31.0501', '31.0501', null, null, 1014, 'Health and Physical Education, General.', 'kuali.enum.type.cip2000',  '087E2BA11C0B462F8E2601038FF4C9EB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1c7f80ef-7793-4bf8-b4c6-a652fd6c4d4f', '31.0502', '31.0502', null, null, 1015, 'Adapted Physical Education/Therapeutic Recreation.',  'kuali.enum.type.cip2000', '95C39C56B0F24905844E8D07EB83142D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e1b84735-7a6e-4cb7-8ea3-ce94a08d16c3', '31.0503', '31.0503', null, null, 1016, 'Athletic Training and Sports Medicine.', 'kuali.enum.type.cip2000',  '28139DDFE26F4729AE440475946C45A5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8d80fc52-82bb-42e5-9ff7-9e3bd12cab19', '31.0504', '31.0504', null, null, 1017, 'Sport and Fitness Administration/Management.', 'kuali.enum.type.cip2000',  'EF18EC3444984AA28B425623FD158233', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('565ce096-b9a1-4ea6-8f3c-363e679f09fd', '31.0505', '31.0505', null, null, 1018, 'Kinesiology and Exercise Science.', 'kuali.enum.type.cip2000',  'CB37019BB137492FA24BF1878E56FAFE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('37a9d202-fa21-4ee1-92d5-782016fdd6de', '31.0506', '31.0506', null, null, 1019, 'Socio-Psychological Sports Studies.', 'kuali.enum.type.cip2000',  '755C8B4CBAF144DD8D331CC55BA2749B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7efc15f7-3a08-400e-a8db-f1462c716958', '31.0599', '31.0599', null, null, 1020, 'Health and Physical Education/Fitness, Other.', 'kuali.enum.type.cip2000',  '885DE9759520464AA542229B9283580B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a77e13ce-bf8d-4e83-9f61-5dd800168136', '31.99', '31.99', null, null, 1021, 'Parks, Recreation, Leisure, and Fitness Studies, Other.',  'kuali.enum.type.cip2000', '477FABE17F4645FE9AB448E20C987E25', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6148413d-f28d-469c-a2ee-6d2ebb1bf894', '31.9999', '31.9999', null, null, 1022, 'Parks, Recreation, Leisure, and Fitness Studies, Other.',  'kuali.enum.type.cip2000', 'E5C63CA1787B459586BB6CD850DA5A68', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('baae8e15-3577-4e44-b4d0-09d5e541bbb8', '32-37.', '32-37.', null, null, 1023, 'Programs for Series 32-37 (Personal Improvement and Leisure programs) are  located in chapter V.', 'kuali.enum.type.cip2000', '0D65A615225C425D912A210758C0B4D3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ba3eed1a-8d25-408b-b5d5-9c8681649d60', '32.', '32.', null, null, 1024, 'BASIC SKILLS.', 'kuali.enum.type.cip2000', '7FE61B3AA66E473690BBC4BDA7179816', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b2afb7ff-c75c-4cba-913b-6cf77d4ec664', '32.01', '32.01', null, null, 1025, 'Basic Skills.', 'kuali.enum.type.cip2000', '5CFCE0D230D749699DAF2AE165744298', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0903b80a-9991-4a4c-bdaa-75348190edbd', '32.0101', '32.0101', null, null, 1026, 'Basic Skills, General.', 'kuali.enum.type.cip2000',  '666A01BB888B4624A7D245EA08B68477', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3762bffe-c474-402e-8fdb-4ac884eb8f5e', '32.0104', '32.0104', null, null, 1027, 'Numeracy and Computational Skills.', 'kuali.enum.type.cip2000',  '219A8C76F2404105A8C68D0646DB4C43', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0a7f0837-4ec2-481a-8d63-3661356fa5e7', '32.0105', '32.0105', null, null, 1028, 'Job-Seeking/Changing Skills.', 'kuali.enum.type.cip2000',  '75C7FB450A31427880EC8216106D71C1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3e3a4dc2-b37b-4785-b47a-575bc02d4e11', '32.0107', '32.0107', null, null, 1029, 'Career Exploration/Awareness Skills.', 'kuali.enum.type.cip2000',  '7C27B940E0624B67B3ECDEF19E99B782', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c8eccb25-e0c5-4d7d-b5ee-0fd596b7fbc9', '32.0108', '32.0108', null, null, 1030, 'Literacy and Communication Skills.', 'kuali.enum.type.cip2000',  'EA1EA292E5E94EE98210E9267D814DED', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0f259d86-35f3-43ea-a983-0c314dc69e60', '32.0109', '32.0109', null, null, 1031, 'Second Language Learning.', 'kuali.enum.type.cip2000',  '5CEA8CDCBB7F4087AE445746922224FB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2e059114-25d7-4b0b-b4c4-58b69dd22742', '32.0199', '32.0199', null, null, 1032, 'Basic Skills, Other.', 'kuali.enum.type.cip2000',  '4CFABD8AF0AE4D81809078E22C28710B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0bd5c204-f1a7-433e-95c4-432aed795123', '33.', '33.', null, null, 1033, 'CITIZENSHIP ACTIVITIES.', 'kuali.enum.type.cip2000',  '9EC735CB91654F84A0EE7A87DF713968', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6d075041-4f3a-4d7e-9f50-5f996a223306', '33.01', '33.01', null, null, 1034, 'Citizenship Activities.', 'kuali.enum.type.cip2000',  'CABF06446F1542818A7A07912139A752', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('eefaafe0-76a6-4446-b2cd-fbe1d0da1f74', '33.0101', '33.0101', null, null, 1035, 'Citizenship Activities, General.', 'kuali.enum.type.cip2000',  '38E11F62E03F43CD9A7A2B67F8062FA1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2fb9196c-c30e-428e-b36d-5fef94d467b8', '33.0102', '33.0102', null, null, 1036, 'American Citizenship Education.', 'kuali.enum.type.cip2000',  '343797B2173F4230BC0DE5D88117C147', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d650f765-0b8d-4cad-aafc-2f8628569508', '33.0103', '33.0103', null, null, 1037, 'Community Awareness.', 'kuali.enum.type.cip2000',  '1656B1492A30434ABB2376648B892D7C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('851c76e1-3cb6-454c-8dec-6b19bfdb4b7e', '33.0104', '33.0104', null, null, 1038, 'Community Involvement.', 'kuali.enum.type.cip2000',  '870288D9A83B4C189443EBE2C5EED5AE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('367c273f-40fc-45bd-8835-0c114972e757', '33.0105', '33.0105', null, null, 1039, 'Canadian Citizenship Education.', 'kuali.enum.type.cip2000',  '7E660D86EA544D228E9A22B894AD6B01', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ecc9f791-7db7-4d73-b18d-60b0a87f58ec', '33.0199', '33.0199', null, null, 1040, 'Citizenship Activities, Other.', 'kuali.enum.type.cip2000',  'DFEB1926A1EF4A90A8675A577CECDD1C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f8f77348-b603-4146-884e-f3c098dd6f34', '34.', '34.', null, null, 1041, 'HEALTH-RELATED KNOWLEDGE AND SKILLS.', 'kuali.enum.type.cip2000',  'AE53A7BB0AC64833AA20E0AF21DFA26C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('54b23056-4b0d-40d0-8394-8e94e4fa7337', '34.01', '34.01', null, null, 1042, 'Health-Related Knowledge and Skills.', 'kuali.enum.type.cip2000',  '53921ABD122C408C8FF290217D536BAB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3e91270b-b5c8-4b38-850a-1b821ad47dd4', '34.0102', '34.0102', null, null, 1043, 'Birthing and Parenting Knowledge and Skills.', 'kuali.enum.type.cip2000',  '7A6F82EC1B8246F78975034D375EE9CC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f9a76997-2bb0-4c1d-a8c4-b124d64d13c1', '34.0103', '34.0103', null, null, 1044, 'Personal Health Improvement and Maintenance.', 'kuali.enum.type.cip2000',  '8D43B3EB97E841D29FAE6AC92DA83530', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5de1a7a5-038d-41b5-920d-d12d139011d4', '34.0104', '34.0104', null, null, 1045, 'Addiction Prevention and Treatment.', 'kuali.enum.type.cip2000',  'B5B38A2D16144B559823E0B17425DDFB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bfc9c09a-d32c-4206-b100-2f68d917d232', '34.0199', '34.0199', null, null, 1046, 'Health-Related Knowledge and Skills, Other.', 'kuali.enum.type.cip2000',  'AD982CD34806493F867C3C3076211083', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1568932c-660a-43fb-8d39-a0362276ac1c', '35.', '35.', null, null, 1047, 'INTERPERSONAL AND SOCIAL SKILLS.', 'kuali.enum.type.cip2000',  '9F55F5271DF3498582AAD9BB027263E9', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('78c13038-0465-48c5-bcd3-8b474c69acf9', '35.01', '35.01', null, null, 1048, 'Interpersonal and Social Skills.', 'kuali.enum.type.cip2000',  'A59C618025BA4978A9AE1030E0A29778', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ac3755ba-28ec-4681-8438-2ba0bfa03b1a', '35.0101', '35.0101', null, null, 1049, 'Interpersonal and Social Skills, General.', 'kuali.enum.type.cip2000',  '07645D7662594831A9ECAE212981C54D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c3a076e2-9c34-4d72-a2ae-691a1d14191e', '35.0102', '35.0102', null, null, 1050, 'Interpersonal Relationships Skills.', 'kuali.enum.type.cip2000',  'CC710830EC6D48A1A7DF59C1FE57707B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b5cd587a-8491-49cc-8bcf-6bfd6ed97ede', '35.0103', '35.0103', null, null, 1051, 'Business and Social Skills.', 'kuali.enum.type.cip2000',  '5F6B6E0185AD4CD49D8FF4DEE6D8CE7C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('42ac7db3-f58a-4b52-ad8f-62d26ba8d8f4', '35.0199', '35.0199', null, null, 1052, 'Interpersonal Social Skills, Other.', 'kuali.enum.type.cip2000',  '04B720ADFBD148FE82A1D9450CFC3FBE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f67241e1-e4dc-4d7e-8bed-a74f17f6be70', '36.', '36.', null, null, 1053, 'LEISURE AND RECREATIONAL ACTIVITIES.', 'kuali.enum.type.cip2000',  'DD9D137F86334707B01D83E1E047973F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a1f835d0-2cbc-423b-84e6-f3bfda062e20', '36.01', '36.01', null, null, 1054, 'Leisure and Recreational Activities.', 'kuali.enum.type.cip2000',  'CEB5EB12C1B44670AC5ED7A5082201A1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('81622d9b-00c6-4ded-a7ce-38d0ce677437', '36.0101', '36.0101', null, null, 1055, 'Leisure and Recreational Activities, General.', 'kuali.enum.type.cip2000',  '48FBA69F97494B798B9B4B707E9E3033', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b38f727e-0570-46f1-a35f-2cd1076e559a', '36.0102', '36.0102', null, null, 1056, 'Handicrafts and Model-Making.', 'kuali.enum.type.cip2000',  '9F879E887A39490DA3C4D410EB146B6E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('37c568f1-aefe-4889-9ef0-e8ede41a32f8', '36.0103', '36.0103', null, null, 1057, 'Board, Card and Role-Playing Games.', 'kuali.enum.type.cip2000',  '95798EADC872446DAFE5FFFFB3CD031B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('92320f8f-ac5e-4f6c-9d6c-c3a8fab53479', '36.0105', '36.0105', null, null, 1058, 'Home Maintenance and Improvement.', 'kuali.enum.type.cip2000',  '0FFCB5FCB3B4469EAAEC66A09AD0F8CB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9e9525e4-c202-4a8a-9fe6-56546eee5829', '36.0106', '36.0106', null, null, 1059, 'Nature Appreciation.', 'kuali.enum.type.cip2000',  '35C69486E94D41E39C0974096AD4E263', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('cddd905c-b631-4f65-ba78-a201c110834a', '36.0107', '36.0107', null, null, 1060, 'Pet Ownership and Care.', 'kuali.enum.type.cip2000',  'E50E6036574347CBA20EAF18E0627CD3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f27b24dc-281d-40b4-b6b6-0e44e6c48d89', '36.0108', '36.0108', null, null, 1061, 'Sports and Exercise.', 'kuali.enum.type.cip2000',  'ECD8B8FE9F534EAC9794E96D5DD39B22', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e56d8710-4029-47d1-895d-927fb5546602', '36.0109', '36.0109', null, null, 1062, 'Travel and Exploration.', 'kuali.enum.type.cip2000',  '9322F3457F6941F98B82FACCEC21E335', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c9119c91-86a7-4d5f-88ae-adfc4ec0d1bf', '36.0110', '36.0110', null, null, 1063, 'Art.', 'kuali.enum.type.cip2000', '4FBFE3E568764D638DAF29C672DDD1DC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d518b39a-5691-48e9-b7a6-1f3ad1f3e9ad', '36.0111', '36.0111', null, null, 1064, 'Collecting.', 'kuali.enum.type.cip2000', 'CE4BD1E12AFA4DE9A6698B9259C58A38',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2514da54-806e-43d4-9e23-f9ac99242a5b', '36.0112', '36.0112', null, null, 1065, 'Cooking and Other Domestic Skills.', 'kuali.enum.type.cip2000',  'F34AA7AFBCBC41CEABE33AF07DD6F183', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a84ed8a7-9fcf-4814-a7f1-38ad1d7b40bf', '36.0113', '36.0113', null, null, 1066, 'Computer Games and Programming Skills.', 'kuali.enum.type.cip2000',  '4191FEC87C4E49F6A9CCAC634788B709', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a944c060-989c-46dc-b169-f03d4bfcc822', '36.0114', '36.0114', null, null, 1067, 'Dancing.', 'kuali.enum.type.cip2000', 'C7DAF627947D4321843DBD54A5A42E25', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('05900d59-5565-4e9b-a89f-caddbe567e62', '36.0115', '36.0115', null, null, 1068, 'Music.', 'kuali.enum.type.cip2000', '5BABC40905444569BA938BC27745EDA9', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b9f73aa2-35c7-4990-8b2f-38746db8713a', '36.0116', '36.0116', null, null, 1069, 'Reading.', 'kuali.enum.type.cip2000', '4F19B8E99A6648F9ABF32658A1E0C326', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('76d8504f-1568-4d68-9838-dabd0b0b54c4', '36.0117', '36.0117', null, null, 1070, 'Theatre/Theater.', 'kuali.enum.type.cip2000',  'B958BE9A26434E28BC19B53B1F889756', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b2064fd3-b1bb-4c1f-adbc-81490c7cd5bf', '36.0118', '36.0118', null, null, 1071, 'Writing.', 'kuali.enum.type.cip2000', '3DFB5F7F59C84BD18304818BA0C2CAF4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e1ba701a-4628-4a9c-a942-22a2b4cd6e6d', '36.0119', '36.0119', null, null, 1072, 'Aircraft Pilot (Private).', 'kuali.enum.type.cip2000',  '4E3CC9C239154ACE821C45ABF3AFF351', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('cd996fe1-bdff-4baf-81b0-381b372eb067', '36.0199', '36.0199', null, null, 1073, 'Leisure and Recreational Activities, Other.', 'kuali.enum.type.cip2000',  'D7FDE5EDB3FB4BF1AD094DE97BDBA1D5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('77038d67-2bd6-4cd3-8a6d-6fed2be44bf8', '37.', '37.', null, null, 1074, 'PERSONAL AWARENESS AND SELF-IMPROVEMENT.', 'kuali.enum.type.cip2000',  'E0D2A36E77C44170820512A8F0C43533', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c710bad9-71b6-4988-9649-bd87beb42991', '37.01', '37.01', null, null, 1075, 'Personal Awareness and Self-Improvement.', 'kuali.enum.type.cip2000',  '3422369030344CB8AA6B725BD6CA1109', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('854effb1-044a-417f-9d23-41aee2ed0620', '37.0101', '37.0101', null, null, 1076, 'Self-Awareness and Personal Assessment.', 'kuali.enum.type.cip2000',  'B3BC83B47BD44D5CB3F648C6869B369A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('84d04ea1-1adc-492e-8485-c3f95da868fc', '37.0102', '37.0102', null, null, 1077, 'Stress Management and Coping Skills.', 'kuali.enum.type.cip2000',  '5C14FA8442F4415AA20068E220BEFB19', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bd9b677a-c368-4824-9059-ee28605b6a2c', '37.0103', '37.0103', null, null, 1078, 'Personal Decision-Making Skills.', 'kuali.enum.type.cip2000',  '9E41975408BE4D7086A4B0F3B6F3FA50', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('065dafc3-245d-4ae6-8bb6-35bfc154de81', '37.0104', '37.0104', null, null, 1079, 'Self-Esteem and Values Clarification.', 'kuali.enum.type.cip2000',  'AC887B5F0D8F4DCFA6FC570CF0E69922', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('04264904-1d13-43b7-9fa0-c40d89d094b6', '37.0199', '37.0199', null, null, 1080, 'Personal Awareness and Self-Improvement, Other.', 'kuali.enum.type.cip2000',  '55E7493E53B040F488E33F3A044B8A6F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4b172f41-4277-4176-8bb1-a600e3097de2', '38.', '38.', null, null, 1081, 'PHILOSOPHY AND RELIGIOUS STUDIES.', 'kuali.enum.type.cip2000',  'A1924DC213A2483F8581AEE017456616', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('86095290-a3c9-4960-b9f0-685a0342f24d', '38.01', '38.01', null, null, 1082, 'Philosophy.', 'kuali.enum.type.cip2000', 'E3D4D5EF64514A0E9D4367B6E4AB78CF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('555eae9a-ea7a-4244-b13d-9bf19f91c2b0', '38.0101', '38.0101', null, null, 1083, 'Philosophy.', 'kuali.enum.type.cip2000', '73AF9E3C46734757BA3DAF74E9207F11',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4e049cc7-3482-4e81-b5e7-102e153a7f8a', '38.0102', '38.0102', null, null, 1084, 'Logic.', 'kuali.enum.type.cip2000', '30227346B46F40F7AD2533D75C1793A8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0c597949-64f9-4f91-8373-9c1904fc751d', '38.0103', '38.0103', null, null, 1085, 'Ethics.', 'kuali.enum.type.cip2000', '0DFB7E05D3714D6AB02C21EFE3D57D4A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f2801c74-2baa-4837-a770-156111d00daf', '38.0199', '38.0199', null, null, 1086, 'Philosophy, Other.', 'kuali.enum.type.cip2000',  '7E7AD7E6242A441790F590A7049E48CA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8c4bb61a-6b06-41aa-84a1-b6d5a3182725', '38.02', '38.02', null, null, 1087, 'Religion/Religious Studies.', 'kuali.enum.type.cip2000',  'C47FCED599DE45D7B0C7FC279D5A82CD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f3f44e9e-05a4-44d7-bcec-09e2e7c5c0ed', '38.0201', '38.0201', null, null, 1088, 'Religion/Religious Studies.', 'kuali.enum.type.cip2000',  '281A17B86255480C86B22A16F027DD68', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('230e4462-4f85-464e-b41a-4a7c846cbb87', '38.0202', '38.0202', null, null, 1089, 'Buddhist Studies.', 'kuali.enum.type.cip2000',  '42765D4AD4784D3990C08906FA964EF5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('95298334-eb35-40c8-b617-10d00eaec2a6', '38.0203', '38.0203', null, null, 1090, 'Christian Studies.', 'kuali.enum.type.cip2000',  '354E50F5C66A4C00810DEA78585F620E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c6c3dc7e-b5d1-4b36-8f76-f36a4f7d0276', '38.0204', '38.0204', null, null, 1091, 'Hindu Studies.', 'kuali.enum.type.cip2000',  '80BF5108FEC84E8DA410102AAEABA639', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a0a35fd7-2369-445a-929a-19bd2ba76d17', '38.0205', '38.0205', null, null, 1092, 'Islamic Studies.', 'kuali.enum.type.cip2000',  'A241A6B5708E47D38299C0F2E57D0E84', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('de8c549e-ab09-4f83-b41c-ee5b6e081ee7', '38.0206', '38.0206', null, null, 1093, 'Jewish/Judaic Studies.', 'kuali.enum.type.cip2000',  'AE1C3DEC86554072B2A89EDB4A47073C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b414f168-4014-413c-bef1-31c00d89e152', '38.0299', '38.0299', null, null, 1094, 'Religion/Religious Studies, Other.', 'kuali.enum.type.cip2000',  '1886D90FC21A48FBB3196E8833E063FD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('dcc912ee-787f-445d-9113-21943e1567a1', '38.99', '38.99', null, null, 1095, 'Philosophy and Religious Studies, Other.', 'kuali.enum.type.cip2000',  '4C9607013B9C4084AF5D1FF73592FC41', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bf539458-0812-46e3-b9ee-0ada9258668f', '38.9999', '38.9999', null, null, 1096, 'Philosophy and Religious Studies, Other.', 'kuali.enum.type.cip2000',  '7D5866C9433543D89576F4AAB94E626A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a447b53a-5ad3-435e-bef1-b011530dd677', '39.', '39.', null, null, 1097, 'THEOLOGY AND RELIGIOUS VOCATIONS.', 'kuali.enum.type.cip2000',  '3EB18F26DC4B437095586F5FA1EE7C0C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3051615a-6520-498d-b8a4-b1828c8bbf73', '39.01', '39.01', null, null, 1098, 'Biblical and Other Theological Languages and Literatures.',  'kuali.enum.type.cip2000', 'DA7D5FE4F2C949A190B854BB8F515BAE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2fc8c41a-a914-45ef-9242-2b4e47873093', '39.0101', '39.0101', null, null, 1099, 'Biblical and Other Theological Languages and Literatures.',  'kuali.enum.type.cip2000', '1972A94BBB744DC58D8F63E366C9A20F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f5775d77-2499-471d-88d5-aa1cee737944', '39.02', '39.02', null, null, 1100, 'Bible/Biblical Studies.', 'kuali.enum.type.cip2000',  '0B5562F259C64C79BBB8D0F2FFB5C321', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6168d223-a745-4eef-a7bf-53dbbad359d0', '39.0201', '39.0201', null, null, 1101, 'Bible/Biblical Studies.', 'kuali.enum.type.cip2000',  'D5990C0C9F9B4C609F5D4D84F0FC2A16', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6e571bfe-b638-4f29-a925-48c9352e99b5', '39.03', '39.03', null, null, 1102, 'Missions/Missionary Studies and Missiology.', 'kuali.enum.type.cip2000',  '6A1B76C967434FED8ADC0C1FF85FFE6D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e0e0c67f-26b6-4c44-9e43-3a1c44bdf1e2', '39.0301', '39.0301', null, null, 1103, 'Missions/Missionary Studies and Missiology.', 'kuali.enum.type.cip2000',  '68AA4E172A314051AC473B977EF36037', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c02fc34f-cd59-4f2a-8655-5d5bcd248e99', '39.04', '39.04', null, null, 1104, 'Religious Education.', 'kuali.enum.type.cip2000',  '4EB036F9FB2A4387AB0EC8488657F7E4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('98cbdd1c-c9a1-4d55-92ed-2957636aafeb', '39.0401', '39.0401', null, null, 1105, 'Religious Education.', 'kuali.enum.type.cip2000',  '7740825203AE495DBD1F0DAFB08B6AA9', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bca24780-9261-4fef-bb77-b4677d7d05af', '39.05', '39.05', null, null, 1106, 'Religious/Sacred Music.', 'kuali.enum.type.cip2000',  'D0A5EFBE469244B9BA40384256286FE6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('45a181f9-8fea-4b8b-aa4c-cfec1196f834', '39.0501', '39.0501', null, null, 1107, 'Religious/Sacred Music.', 'kuali.enum.type.cip2000',  '237D832B27434AE58E42ED1F6FBD39D9', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('06f8d88c-7a91-42b4-bfdb-a3af166fd8f5', '39.06', '39.06', null, null, 1108, 'Theological and Ministerial Studies.', 'kuali.enum.type.cip2000',  '04EB00EB7F6A48A8AB794949EA1BB299', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bb433321-0c16-41b3-87e1-3dafc7c4b733', '39.0601', '39.0601', null, null, 1109, 'Theology/Theological Studies.', 'kuali.enum.type.cip2000',  '96D7C51C91984549B0ED96829E2CD600', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1aa153cb-16cb-4d95-bbac-89e7e4c7c9e2', '39.0602', '39.0602', null, null, 1110, 'Divinity/Ministry (BD, MDiv.', 'kuali.enum.type.cip2000',  'FA51E1E26C7A446FBAE948FC79CA3734', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ea9dc258-6d96-4ba1-bbb2-b277aaa47ffd', '39.0603', '39.0603', null, null, 1111, 'Rabbinical Studies (M.', 'kuali.enum.type.cip2000',  '590A0FCF3F1042238126E1B204A3EA24', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c2b0d334-0fe0-4f0b-be5c-31d0a7516ec1', '39.0604', '39.0604', null, null, 1112, 'Pre-Theology/Pre-Ministerial Studies.', 'kuali.enum.type.cip2000',  'DDDA8AA2A3FC4C738B6BC68B4CD24DCA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('462916dc-659f-4670-b40a-86e5a10ac5f6', '39.0605', '39.0605', null, null, 1113, 'Rabbinical Studies (M.', 'kuali.enum.type.cip2000',  '1273946EF00742499A3B92EEB411CC6A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('eef68e64-5940-4373-bdc5-1d30c388fc1f', '39.0606', '39.0606', null, null, 1114, 'Talmudic Studies.', 'kuali.enum.type.cip2000',  'C78F034BAF0F412AAAB928B532190BB2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('45d5c99f-3d6c-417e-b701-d02f2e7121df', '39.0699', '39.0699', null, null, 1115, 'Theological and Ministerial Studies, Other.', 'kuali.enum.type.cip2000',  '1308184414374900846A83B41D11A841', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('fbcd99e4-02ce-4ea5-86e6-94a8a393f3c5', '39.07', '39.07', null, null, 1116, 'Pastoral Counseling and Specialized Ministries.', 'kuali.enum.type.cip2000',  '3BF4D79DAD814506B53376BD560F171E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('052a65e8-dd54-4ef5-a2b5-810bdc405f0a', '39.0701', '39.0701', null, null, 1117, 'Pastoral Studies/Counseling.', 'kuali.enum.type.cip2000',  '6E0001C8BB7C4AE08EB0F95E6DCCC62D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('59b47a71-85c3-418c-8987-ff726ebf8010', '39.0702', '39.0702', null, null, 1118, 'Youth Ministry.', 'kuali.enum.type.cip2000',  '63CC75979D97424CBD880E0051C05516', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3c4f1304-d9dd-4771-998b-129b4ca48dfc', '39.0799', '39.0799', null, null, 1119, 'Pastoral Counseling and Specialized Ministries, Other.',  'kuali.enum.type.cip2000', '0F21423C820748F4901CD7C3D55126EA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3276d308-679e-48d5-8bc8-79fc0fd03c03', '39.99', '39.99', null, null, 1120, 'Theology and Religious Vocations, Other.', 'kuali.enum.type.cip2000',  '0BD391251C334D909B4C9459C17B244D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d6928de7-eb00-45d9-8cca-187332dafdb5', '39.9999', '39.9999', null, null, 1121, 'Theology and Religious Vocations, Other.', 'kuali.enum.type.cip2000',  'B0E1A7A23085475983834984F7F50E35', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('41b80928-e327-4828-95b5-e3a3894c384a', '40.', '40.', null, null, 1122, 'PHYSICAL SCIENCES.', 'kuali.enum.type.cip2000', '81153280BC9543E792E0A55B6E1AC353', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('966da390-0c45-4db1-817f-01c0507fdc29', '40.01', '40.01', null, null, 1123, 'Physical Sciences.', 'kuali.enum.type.cip2000',  'AF2E15D4A80B43BAAC2D3CABAEA213D7', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a952e694-c31a-4eed-99ca-b0f98c138618', '40.0101', '40.0101', null, null, 1124, 'Physical Sciences.', 'kuali.enum.type.cip2000',  '4DC4CD7F4110414A94DFB9848A482326', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('dee03497-ca12-4295-8d8c-84211bdc09fc', '40.02', '40.02', null, null, 1125, 'Astronomy and Astrophysics.', 'kuali.enum.type.cip2000',  '64FAF6E7BC5F4F94881CB47344E4C2C5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('96647921-adf4-4ff4-946f-ab1225ca51fc', '40.0201', '40.0201', null, null, 1126, 'Astronomy.', 'kuali.enum.type.cip2000', '89A65C21785D454580E7B99A4F0480F7', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('53f4f9f1-d42d-44ec-b2e6-445a2e4e4de8', '40.0202', '40.0202', null, null, 1127, 'Astrophysics.', 'kuali.enum.type.cip2000', 'FB09E0CA576841D58A7CE00B86031302',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('570a0c76-a42b-476b-a96d-d36a52a73baf', '40.0203', '40.0203', null, null, 1128, 'Planetary Astronomy and Science.', 'kuali.enum.type.cip2000',  '55138D6807EA4ABE9ADB9C562F40D024', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0bc5eb24-2619-417a-851f-f431d5dff654', '40.0299', '40.0299', null, null, 1129, 'Astronomy and Astrophysics, Other.', 'kuali.enum.type.cip2000',  '5BBDEA9966B84156BB0E1354EC13A127', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('fe8da506-6810-4d88-b1ec-22c2d46aed10', '40.03', '40.03', null, null, 1130, 'Astrophysics.', 'kuali.enum.type.cip2000', 'D1DA136F1E2243D6957D9B64DFCFBC0F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bfd97ccc-1811-41b5-9269-dddf26c6957f', '40.0301', '40.0301', null, null, 1131, 'Astrophysics.', 'kuali.enum.type.cip2000', 'A3089B0668024D8EAA13C6124D3C0724',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('aa556356-bb4b-4bbc-846c-2f53c5d82bec', '40.04', '40.04', null, null, 1132, 'Atmospheric Sciences and Meteorology.', 'kuali.enum.type.cip2000',  '4A1BB83B8F714259B8945921076FBFEB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8a6331e7-0152-4557-981d-77348886df89', '40.0401', '40.0401', null, null, 1133, 'Atmospheric Sciences and Meteorology, General.', 'kuali.enum.type.cip2000',  'B73FCEF710464D8292511C753D6AF30F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ce45cfa3-1aa2-4f30-aeb7-d42181a405a9', '40.0402', '40.0402', null, null, 1134, 'Atmospheric Chemistry and Climatology.', 'kuali.enum.type.cip2000',  'C8EE5BB5184F4ECB9A95EB22FEDC5478', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('792fc57a-055c-4640-857c-775b9a164a97', '40.0403', '40.0403', null, null, 1135, 'Atmospheric Physics and Dynamics.', 'kuali.enum.type.cip2000',  'ADBF7C3CE41D4F6FADCB1CC3D0C374CF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d0264044-7ed7-4fdf-a429-3c826b919f53', '40.0404', '40.0404', null, null, 1136, 'Meteorology.', 'kuali.enum.type.cip2000', '38B2FDD9EEDD48E18631C63E0BF25CDD',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2e149d9e-35be-4c56-b431-00ccdcdbfc39', '40.0499', '40.0499', null, null, 1137, 'Atmospheric Sciences and Meteorology, Other.', 'kuali.enum.type.cip2000',  '3B687B2B34B047F89FE20011DC5172DF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('299e5131-2300-4685-8062-b29ac09e6ca1', '40.05', '40.05', null, null, 1138, 'Chemistry.', 'kuali.enum.type.cip2000', '751664600EC14D09B8865F0E5F309348', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e13dcb03-0198-4a67-9370-5ee798e7705b', '40.0501', '40.0501', null, null, 1139, 'Chemistry, General.', 'kuali.enum.type.cip2000',  '31C1C689AEA14856A40BD01228266EFA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ece3fe04-ca59-452c-9e33-13be4d489c90', '40.0502', '40.0502', null, null, 1140, 'Analytical Chemistry.', 'kuali.enum.type.cip2000',  '3D9A8F999693424E9363E1460E007775', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c7742411-689b-44f7-a98d-cd71bd5ceba6', '40.0503', '40.0503', null, null, 1141, 'Inorganic Chemistry.', 'kuali.enum.type.cip2000',  'E7742123D83F4BBC94D79EBC501CCBB3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('99ac04aa-9995-48ea-8e06-55a859fce956', '40.0504', '40.0504', null, null, 1142, 'Organic Chemistry.', 'kuali.enum.type.cip2000',  '57992FE417B344B29A29315BC3EAC79A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6e919a7b-8432-42a9-bf3a-8b1ec8eaf9ed', '40.0505', '40.0505', null, null, 1143, 'Medicinal/Pharmaceutical Chemistry.', 'kuali.enum.type.cip2000',  '2056FB7A5B7641C5892511FDCEF6E899', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e7706494-6dc5-43c6-8d03-693d00c25c8e', '40.0506', '40.0506', null, null, 1144, 'Physical and Theoretical Chemistry.', 'kuali.enum.type.cip2000',  '66CFA80FE2044B41B1055173446A53D3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a9ec9acf-425f-464d-8b06-8f3c50899fa2', '40.0507', '40.0507', null, null, 1145, 'Polymer Chemistry.', 'kuali.enum.type.cip2000',  '7BE6D753B653439381DD8FDD08F3295C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3ebccf2f-f93d-4bc1-898f-6b6c992f9204', '40.0508', '40.0508', null, null, 1146, 'Chemical Physics.', 'kuali.enum.type.cip2000',  '2EEA1B66F9614654A3A6D0D4333EC20E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b1e4c64c-7832-490c-8c6d-3778309ba6bc', '40.0599', '40.0599', null, null, 1147, 'Chemistry, Other.', 'kuali.enum.type.cip2000',  '2636B58A5ABD4BABADC0E22D212BD20C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f9207e69-5d75-4acb-9f5b-d166b890ac43', '40.06', '40.06', null, null, 1148, 'Geological and Earth Sciences/Geosciences.', 'kuali.enum.type.cip2000',  '653838EDDADE4729A21451B264F212C4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8cc9ae85-5c88-4ff2-9a7c-6f21cf46f854', '40.0601', '40.0601', null, null, 1149, 'Geology/Earth Science, General.', 'kuali.enum.type.cip2000',  '8EC6B3BDD6C4479AAB4C39C431929957', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('fc6688a4-a24e-4772-ba21-da60028dbf9a', '40.0602', '40.0602', null, null, 1150, 'Geochemistry.', 'kuali.enum.type.cip2000', 'B502AB771C904F539FC1C3EEB2B384D1',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('58692ae3-4358-4ee9-8770-abbdb189bbe4', '40.0603', '40.0603', null, null, 1151, 'Geophysics and Seismology.', 'kuali.enum.type.cip2000',  'FEA95A6656B44C3992F56D07EA840417', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ed7c1665-02d8-4dac-bfd4-af92a565f25c', '40.0604', '40.0604', null, null, 1152, 'Paleontology.', 'kuali.enum.type.cip2000', '61B14FFF43734AA7B0661CA911619BD9',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('627dd32c-8818-41b4-babc-7a365a32111b', '40.0605', '40.0605', null, null, 1153, 'Hydrology and Water Resources Science.', 'kuali.enum.type.cip2000',  '9915B2C997A043A3802236E1BC7AF7DF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('567d5846-3027-4a25-adb4-381b962c55c2', '40.0606', '40.0606', null, null, 1154, 'Geochemistry and Petrology.', 'kuali.enum.type.cip2000',  '5590E678EB6E41F6B31B6A20E66D0C29', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6d8591a0-9c23-4391-b560-2840ead670c4', '40.0607', '40.0607', null, null, 1155, 'Oceanography, Chemical and Physical.', 'kuali.enum.type.cip2000',  'E66EBC1D4D2748DBB950C655077EADDD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('582d234b-c055-4742-8b3e-b72e48a8b285', '40.0699', '40.0699', null, null, 1156, 'Geological and Earth Sciences/Geosciences, Other.', 'kuali.enum.type.cip2000',  '497D1AFBA3234F7EAA7092794C21776B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e48bc03a-85a6-4667-b24a-34dd0774d6ab', '40.07', '40.07', null, null, 1157, 'Miscellaneous Physical Sciences.', 'kuali.enum.type.cip2000',  'A01E38894A1F4DB3A780D467DCF7BB00', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('17ad7215-4f4d-4848-9aba-892dfddd9350', '40.0701', '40.0701', null, null, 1158, 'Metallurgy.', 'kuali.enum.type.cip2000', '10D3D476A6EA46BE8460E6AAB943A2B5',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5af664ee-3820-48fa-94de-34dca1fb727b', '40.0702', '40.0702', null, null, 1159, 'Oceanography, Chemical and Physical.', 'kuali.enum.type.cip2000',  'F10177833B074E0EB09991DDD8403634', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('633a7d66-9972-4787-bbb1-b70c068de38d', '40.0703', '40.0703', null, null, 1160, 'Earth and Planetary Sciences.', 'kuali.enum.type.cip2000',  '4CC5B2A705BF498CAA7FEE91E69034BA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0df897b1-093f-403f-8a65-31b1ff9864db', '40.0799', '40.0799', null, null, 1161, 'Miscellaneous Physical Sciences, Other.', 'kuali.enum.type.cip2000',  '501EBC7AFFE0444188C784BADCEA6488', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5cf89333-fc44-4674-ab83-dbb641e4a001', '40.08', '40.08', null, null, 1162, 'Physics.', 'kuali.enum.type.cip2000', '056AA7285263489B93D173B24E32B085', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9652cb05-850d-4234-90d2-423a7ae43e7c', '40.0801', '40.0801', null, null, 1163, 'Physics, General.', 'kuali.enum.type.cip2000',  '71937028091B497BA6E06BFF84EF3969', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d7776020-6a7c-4353-ad01-783e39fee443', '40.0802', '40.0802', null, null, 1164, 'Atomic/Molecular Physics.', 'kuali.enum.type.cip2000',  'EF19BD7A21C3416F868E3A0947DB1077', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ad5d5a3e-c789-4736-a311-b10e98db2fe1', '40.0804', '40.0804', null, null, 1165, 'Elementary Particle Physics.', 'kuali.enum.type.cip2000',  '17553FB24F1D4C7F976F74F574686B9F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f4f29b59-1acb-4fd6-8b10-2df290250486', '40.0805', '40.0805', null, null, 1166, 'Plasma and High-Temperature Physics.', 'kuali.enum.type.cip2000',  '08747435993145F7A712DA3EED23E21E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('794643a6-0395-4bc1-84d3-f7c8f3ac3694', '40.0806', '40.0806', null, null, 1167, 'Nuclear Physics.', 'kuali.enum.type.cip2000',  '91B338A090CE4257868A4D1A64018B9C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1976d5ba-1681-46d6-ad2e-747be5b51d5a', '40.0807', '40.0807', null, null, 1168, 'Optics/Optical Sciences.', 'kuali.enum.type.cip2000',  '16F8AECD478942C89C0CF321239F2DA9', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bc9940bc-0920-40c5-bf49-8605be934be4', '40.0808', '40.0808', null, null, 1169, 'Solid State and Low-Temperature Physics.', 'kuali.enum.type.cip2000',  'D219F4E7FDA547E6AD3BECEE8066A0D9', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5093683d-b601-475d-b88c-b17b1a053b6c', '40.0809', '40.0809', null, null, 1170, 'Acoustics.', 'kuali.enum.type.cip2000', 'CFD21A16965F406997B8ADF4B028E022', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8329b462-8ed3-4f4d-abd6-66f2152299de', '40.0810', '40.0810', null, null, 1171, 'Theoretical and Mathematical Physics.', 'kuali.enum.type.cip2000',  '33F1592CD91449269E27AE0DDF869CC9', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0bb8d635-8774-4bd2-8c18-62af97b7d127', '40.0899', '40.0899', null, null, 1172, 'Physics, Other.', 'kuali.enum.type.cip2000',  'D65A75B51C2C4080A0CFB2731C79855E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('86de22c7-89b7-4f63-a2a3-9e5080c1ab7e', '40.99', '40.99', null, null, 1173, 'Physical Sciences, Other.', 'kuali.enum.type.cip2000',  '29579F3263F24BD69EBBDB54824125F0', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b5ed2866-ca90-4a06-83aa-7115ee02187c', '40.9999', '40.9999', null, null, 1174, 'Physical Sciences, Other.', 'kuali.enum.type.cip2000',  '8B5716EE931A4E5EA8CE5BFDE6B9B679', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a335ac40-7470-47c2-824f-a5221e779b5b', '41.', '41.', null, null, 1175, 'SCIENCE TECHNOLOGIES/TECHNICIANS.', 'kuali.enum.type.cip2000',  '24B03E1CB77742A78AB8CCB874DD8B3D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('95af4ce3-fa3f-4f19-84a6-f5dcfad4b620', '41.01', '41.01', null, null, 1176, 'Biology Technician/Biotechnology Laboratory Technician.',  'kuali.enum.type.cip2000', '0FA685EBB6CA4AFE8F9A36600CF14064', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ae0020ec-a831-4076-852c-6bc265f81ae4', '41.0101', '41.0101', null, null, 1177, 'Biology Technician/Biotechnology Laboratory Technician.',  'kuali.enum.type.cip2000', 'DBE1FA0AE0EA4828A586341D56D0E90B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('41452023-ff93-4045-aa7d-e235fd9a97f2', '41.02', '41.02', null, null, 1178, 'Nuclear and Industrial Radiologic Technologies/Technicians.',  'kuali.enum.type.cip2000', '805389849C2F4DAA839BBAC59E3C9A96', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7c981fdf-69b5-4ac0-8434-1c0d1cc8535f', '41.0204', '41.0204', null, null, 1179, 'Industrial Radiologic Technology/Technician.', 'kuali.enum.type.cip2000',  'A17F7DB371104903A7A5F631C93190A5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('85d5afcc-6889-4ed8-808c-d927fa68f3b8', '41.0205', '41.0205', null, null, 1180, 'Nuclear/Nuclear Power Technology/Technician.', 'kuali.enum.type.cip2000',  'B9F79CCDED014A0C854B9E8A1B9FCE6B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('94ecaf69-c023-48a7-8bf2-6d554f9d551d', '41.0299', '41.0299', null, null, 1181, 'Nuclear and Industrial Radiologic Technologies/Technicians, Other.',  'kuali.enum.type.cip2000', 'CEDBB12A068541B7A99C96DEA3358FD9', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9a7cb6f9-e5ec-4ae0-9af2-09d66f88c9fe', '41.03', '41.03', null, null, 1182, 'Physical Science Technologies/Technicians.', 'kuali.enum.type.cip2000',  '00C80C6F3E3E448092A52668169AC910', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f9ff929f-a0f0-4a08-a58a-04f1432b677f', '41.0301', '41.0301', null, null, 1183, 'Chemical Technology/Technician.', 'kuali.enum.type.cip2000',  'A00C1C6E1FFC4821BCD1D5A92C2BDACC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('70f1af2d-1973-4656-b6cf-3e31fc5d1564', '41.0399', '41.0399', null, null, 1184, 'Physical Science Technologies/Technicians, Other.', 'kuali.enum.type.cip2000',  'E43747C45E8E412CB809B8A7AECE316B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('dbefd8ef-aa49-4850-bc71-abc0ad2c9e49', '41.99', '41.99', null, null, 1185, 'Science Technologies/Technicians, Other.', 'kuali.enum.type.cip2000',  '29D7BCDFEDEA47F6B6D8C3FBA5F7A9A0', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6438eaef-9eca-4963-b03e-fd954310348d', '41.9999', '41.9999', null, null, 1186, 'Science Technologies/Technicians, Other.', 'kuali.enum.type.cip2000',  '6DB8F0DCFBB742838C536ECAC8F898F8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('fc808171-b450-4c87-9580-ad936563e33f', '42.', '42.', null, null, 1187, 'PSYCHOLOGY.', 'kuali.enum.type.cip2000', '39D56B8BED3C42F88B6CDD0522DA50D0', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b7785016-0d5d-452b-9007-b1ca5399edbc', '42.01', '42.01', null, null, 1188, 'Psychology, General.', 'kuali.enum.type.cip2000',  'DA91A76F7DAB4A16BC86585496EEDFCF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('31b66067-6f17-4e4f-8a0e-f19d7329ce19', '42.0101', '42.0101', null, null, 1189, 'Psychology, General.', 'kuali.enum.type.cip2000',  '973B3DE7D7064D769444D8F8933CE443', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('86422af4-af60-4fca-b59f-4c9584c80075', '42.02', '42.02', null, null, 1190, 'Clinical Psychology.', 'kuali.enum.type.cip2000',  '146DDDC960674AEA9A5CEBC60B51E173', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('843e1aa2-12ff-4884-8651-59ec14ce2efe', '42.0201', '42.0201', null, null, 1191, 'Clinical Psychology.', 'kuali.enum.type.cip2000',  '09167A6D14A24DB487639466CE6430F0', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4db81300-0397-48a3-afac-2ec35fdcc4af', '42.03', '42.03', null, null, 1192, 'Cognitive Psychology and Psycholinguistics.', 'kuali.enum.type.cip2000',  '94E11AB6AE344CD6A130EA229C1BFEFC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d79ec833-e672-406d-8311-7472594bc568', '42.0301', '42.0301', null, null, 1193, 'Cognitive Psychology and Psycholinguistics.', 'kuali.enum.type.cip2000',  '6CC6DCDC9E3C436DABF84752FF648937', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('779e99c2-c172-4e05-8fc3-807234337c95', '42.04', '42.04', null, null, 1194, 'Community Psychology.', 'kuali.enum.type.cip2000',  '6316940C05A24982A1E8E6FB55CC11E8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('476246a6-53b6-41b3-909c-c2e9a6daba05', '42.0401', '42.0401', null, null, 1195, 'Community Psychology.', 'kuali.enum.type.cip2000',  'C834882E1E1F444E91ADD890E66BA3A4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('13d05e3f-9d12-4a95-a418-083f193fdf0a', '42.05', '42.05', null, null, 1196, 'Comparative Psychology.', 'kuali.enum.type.cip2000',  'E3859649D86146D9AF78001D1D3CBCBF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2d47036c-0e49-407a-9482-e40fb1e53d44', '42.0501', '42.0501', null, null, 1197, 'Comparative Psychology.', 'kuali.enum.type.cip2000',  '9D7CA42A6F8F4A64AFBBD7D5E284EB9C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6211ece0-bf5a-44b7-8469-e200b0959ee5', '42.06', '42.06', null, null, 1198, 'Counseling Psychology.', 'kuali.enum.type.cip2000',  'E3A1BE3A16614CDAACF77EACB634642C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e5099691-aaf1-4aa9-b240-136cf1c67f75', '42.0601', '42.0601', null, null, 1199, 'Counseling Psychology.', 'kuali.enum.type.cip2000',  'C2C7756753B64A05A40D9AFBACC91B08', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('317eaef8-7bca-4f1a-9ad3-0a73a8084dc3', '42.07', '42.07', null, null, 1200, 'Developmental and Child Psychology.', 'kuali.enum.type.cip2000',  '79CF5D237184428FAE3B89496CFE6643', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ba32479a-6b6d-4286-a9e1-297eddcf27ba', '42.0701', '42.0701', null, null, 1201, 'Developmental and Child Psychology.', 'kuali.enum.type.cip2000',  '3E0D4BB8FEF44F61A3CDE9C5D9856999', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c78c2137-1662-4e8b-ac57-0ec3d507fd71', '42.08', '42.08', null, null, 1202, 'Experimental Psychology.', 'kuali.enum.type.cip2000',  'B05F7B02D11248C78F864BEF23AD25BC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9896a1e4-f35a-498c-aa7f-9a74496c2a9f', '42.0801', '42.0801', null, null, 1203, 'Experimental Psychology.', 'kuali.enum.type.cip2000',  '5FA317E4DB614FFE9FF5D7C5EE46F915', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('df2f0a01-0e18-4397-b3a9-f36364c8ac09', '42.09', '42.09', null, null, 1204, 'Industrial and Organizational Psychology.', 'kuali.enum.type.cip2000',  '52C7E9B2AA0C49A3A0BF0692A650FF89', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('05606e99-6e13-4c45-8f36-694a612b088b', '42.0901', '42.0901', null, null, 1205, 'Industrial and Organizational Psychology.', 'kuali.enum.type.cip2000',  'F31AE8F45D5F4398B3A6D5B4B37E5F8D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('034f7160-0c2e-42da-84a3-27a8beb3d430', '42.10', '42.10', null, null, 1206, 'Personality Psychology.', 'kuali.enum.type.cip2000',  '379F19FE1F724FCC8D7E57F1B4E00710', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7523711f-3c14-4db4-a874-79a18c3727e2', '42.1001', '42.1001', null, null, 1207, 'Personality Psychology.', 'kuali.enum.type.cip2000',  '4C0CE58E85A54C138E90E4B97290E486', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1e0df5fc-b3c8-4e0e-94e9-38ddda35375a', '42.11', '42.11', null, null, 1208, 'Physiological Psychology/Psychobiology.', 'kuali.enum.type.cip2000',  '3EF721AA374D4207B8E2E8702CDAFEF3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f04be59d-c6d0-45ca-b788-64f87dd6e9f6', '42.1101', '42.1101', null, null, 1209, 'Physiological Psychology/Psychobiology.', 'kuali.enum.type.cip2000',  'A2BFDE11963A4976A4B750A65DEAE276', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3de83a8c-c7e5-49c1-a5ae-3453b1451d02', '42.16', '42.16', null, null, 1210, 'Social Psychology.', 'kuali.enum.type.cip2000',  'EA1D7416B5B44EFEBD58572FB554721E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3fb3d57e-72c3-4f38-8a86-ca3fc77d5792', '42.1601', '42.1601', null, null, 1211, 'Social Psychology.', 'kuali.enum.type.cip2000',  '6C5CE16CA61F4B8285BFD41DAE89F7C6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('147d90bb-e68f-4654-afaf-f2d48bd21d23', '42.17', '42.17', null, null, 1212, 'School Psychology.', 'kuali.enum.type.cip2000',  'F24B1072A7FF4B70BD60AAC8399EC859', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b573e128-ea64-4fa7-aec2-4c7a2feca213', '42.1701', '42.1701', null, null, 1213, 'School Psychology.', 'kuali.enum.type.cip2000',  '2E3694EDC3AB4A4A90FCD778AC51DEA9', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('57f3f6f2-3c0d-4798-a9dc-70200d037413', '42.18', '42.18', null, null, 1214, 'Educational Psychology.', 'kuali.enum.type.cip2000',  '7CF5ECBE33E74C5E99AB83DDE1EBCF5D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('287ed099-35f1-42a9-8b7a-9e0628378eb1', '42.1801', '42.1801', null, null, 1215, 'Educational Psychology.', 'kuali.enum.type.cip2000',  '09104119DC3045C1B6C62A2D1EC06309', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c6bb3c4e-caf0-4f03-ba23-21547451f371', '42.19', '42.19', null, null, 1216, 'Psychometrics and Quantitative Psychology.', 'kuali.enum.type.cip2000',  '40490424C4004A3CA0DC4A18691BBD64', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c158bdf6-f1de-4761-9f34-e4d2b8b6f0c7', '42.1901', '42.1901', null, null, 1217, 'Psychometrics and Quantitative Psychology.', 'kuali.enum.type.cip2000',  '438BD0CBE7A548198D0B2D3B2DFD660E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bd85bd2c-1364-43c3-93a1-17580501104d', '42.20', '42.20', null, null, 1218, 'Clinical Child Psychology.', 'kuali.enum.type.cip2000',  'DA6E4CD11CC24ADBB462AABED854E6BD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6f6bf7a9-ca40-4cdc-b44f-1d0bbb98c64e', '42.2001', '42.2001', null, null, 1219, 'Clinical Child Psychology.', 'kuali.enum.type.cip2000',  '296311C4DC3B48158C11D752FF9D93F1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('fe4f0136-c979-4fb9-b842-8e2864017a39', '42.21', '42.21', null, null, 1220, 'Environmental Psychology.', 'kuali.enum.type.cip2000',  '98AD96ACA70F4CCBACA8477CA637188B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('acad01b0-1b1b-4167-af42-b3c5870ff6b7', '42.2101', '42.2101', null, null, 1221, 'Environmental Psychology.', 'kuali.enum.type.cip2000',  '9E9959363D824228937EC46A3E41D50C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d96efb37-880d-4bd0-82b7-5ef5835671ff', '42.22', '42.22', null, null, 1222, 'Geropsychology.', 'kuali.enum.type.cip2000', 'E415B9E71DAB42F087830B5B6F91D0D0',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f4d42570-efd4-4fa2-b270-317a1208e617', '42.2201', '42.2201', null, null, 1223, 'Geropsychology.', 'kuali.enum.type.cip2000',  '35E56F61587C4F1F9581940FE867FAD4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('10e5b07b-ae18-4ca6-9bb2-1d9494668d9b', '42.23', '42.23', null, null, 1224, 'Health Psychology.', 'kuali.enum.type.cip2000',  '960832158A2542F3A2A22D988EF75B67', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f8b1e379-6593-4423-ae9e-3a1a3f759867', '42.2301', '42.2301', null, null, 1225, 'Health/Medical Psychology.', 'kuali.enum.type.cip2000',  '44A5AD32AC9E40239DFFED322A717FED', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bc6c7273-4a84-46bb-b91d-4cfabe426b12', '42.24', '42.24', null, null, 1226, 'Psychopharmacology.', 'kuali.enum.type.cip2000',  '9E78473074D54E30AA3C51D4DF3AA0D8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d5904dc9-fd53-4fae-811a-b487506260f1', '42.2401', '42.2401', null, null, 1227, 'Psychopharmacology.', 'kuali.enum.type.cip2000',  '5F6ABDEC8AC14AC1A32EB745DE65E3F5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('30902147-bbd8-45a3-84c2-ae9381715baf', '42.25', '42.25', null, null, 1228, 'Family Psychology.', 'kuali.enum.type.cip2000',  '687B6577CDA14264A931D7CB860069EE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a2f59e52-9fc2-4e5c-8acf-01df025a2502', '42.2501', '42.2501', null, null, 1229, 'Family Psychology.', 'kuali.enum.type.cip2000',  'DCAFE5E9A7494A7387FB40C0D9D7A589', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0b106aa1-f6d7-495e-afe7-44d4af87d312', '42.26', '42.26', null, null, 1230, 'Forensic Psychology.', 'kuali.enum.type.cip2000',  'B7C0425D09BE47039101E41B81655516', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0b4b26d5-6311-484c-bfd0-a8a1d35910eb', '42.2601', '42.2601', null, null, 1231, 'Forensic Psychology.', 'kuali.enum.type.cip2000',  '9F68C6B4C7704162B18152B1B53FEE51', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7fb35952-5a34-4776-a518-5e40d3d9e939', '42.99', '42.99', null, null, 1232, 'Psychology, Other.', 'kuali.enum.type.cip2000',  '6B68DD8BD60C4F87A03806E4B94F9707', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('550277cd-70b9-4762-b343-be338c1f434e', '42.9999', '42.9999', null, null, 1233, 'Psychology, Other.', 'kuali.enum.type.cip2000',  '0991D40D5420447B82F285B13D95736F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('607348f2-fe70-4078-81a8-708b111a7908', '43.', '43.', null, null, 1234, 'SECURITY AND PROTECTIVE SERVICES.', 'kuali.enum.type.cip2000',  '1535832FF6DB4742B78A59FFF28792E3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('73681450-a8b7-441c-98e3-f460127d72c6', '43.01', '43.01', null, null, 1235, 'Criminal Justice and Corrections.', 'kuali.enum.type.cip2000',  '93F9E42EF7C1479EA2E3E81A31D8EEA1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('67006b00-f766-4757-aee0-f9734aeaaa3e', '43.0102', '43.0102', null, null, 1236, 'Corrections.', 'kuali.enum.type.cip2000', '23B9B58E53F84E8D925397E8BA674161',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('595a3a34-f6ef-46a0-8afc-503bf8752b85', '43.0103', '43.0103', null, null, 1237, 'Criminal Justice/Law Enforcement Administration.', 'kuali.enum.type.cip2000',  '20D0384277C5470B86FB10FBC27704D8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e2ade507-1d4c-4618-93f1-2aeca47e8e13', '43.0104', '43.0104', null, null, 1238, 'Criminal Justice/Safety Studies.', 'kuali.enum.type.cip2000',  'CD5447DCF89E4987A4B0FA56A198EA1E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('73803fed-9e6d-4fde-959b-f2f6e4b9f83e', '43.0106', '43.0106', null, null, 1239, 'Forensic Science and Technology.', 'kuali.enum.type.cip2000',  '599528BE631F469EB07E8266046DB821', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c88c3116-2dd3-49dd-888e-1b3a77a49c56', '43.0107', '43.0107', null, null, 1240, 'Criminal Justice/Police Science.', 'kuali.enum.type.cip2000',  'CC8BC40B981940ED8E6670E076446562', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3116501d-4d54-4d30-8bed-ebc58d86edd9', '43.0109', '43.0109', null, null, 1241, 'Security and Loss Prevention Services.', 'kuali.enum.type.cip2000',  '78AA25EA6D5044DBB000DC9D2987A7F4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e49b1be3-e2ed-4a1a-ae22-f45610afa5f9', '43.0110', '43.0110', null, null, 1242, 'Juvenile Corrections.', 'kuali.enum.type.cip2000',  '4F94E588C02F4F9BA21B8FCA9197B949', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6433e77a-28c1-49ab-a39c-20266910cca7', '43.0111', '43.0111', null, null, 1243, 'Criminalistics and Criminal Science.', 'kuali.enum.type.cip2000',  '0653800A98E34F1FAF09CA3CEB312E2D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('457eaaaa-1e40-470b-8c02-e6a3a2afdf90', '43.0112', '43.0112', null, null, 1244, 'Securities Services Administration/Management.', 'kuali.enum.type.cip2000',  'E180BB2AA5C7496D9CB4CF1C92EE5022', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5513ba44-8a71-4c44-94e5-d464aabf2d7c', '43.0113', '43.0113', null, null, 1245, 'Corrections Administration.', 'kuali.enum.type.cip2000',  'FF8A23740BBA42C8A2F3B7640D9E57A7', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9673e332-6790-4247-bce9-c27e533c4e24', '43.0199', '43.0199', null, null, 1246, 'Corrections and Criminal Justice, Other.', 'kuali.enum.type.cip2000',  '8E7836A3149842EE973807A68892FB21', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('002d0603-6eb4-47b0-8427-02f75f41fd7a', '43.02', '43.02', null, null, 1247, 'Fire Protection.', 'kuali.enum.type.cip2000', '327DBDA3C8D641F2859E9D7D8694AB7A',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('cd948b36-54e2-4096-bfe9-a5e3988f9376', '43.0201', '43.0201', null, null, 1248, 'Fire Protection and Safety Technology/Technician.', 'kuali.enum.type.cip2000',  '912F8FE5C4F54820AA26A1F43FFF9F95', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('41bc48e9-4b52-4d29-b09e-a2ad4335314c', '43.0202', '43.0202', null, null, 1249, 'Fire Services Administration.', 'kuali.enum.type.cip2000',  'F2B39681206143938B2A9FA08F899700', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('79d22abd-19a8-4be9-97ec-aee2d43c12d0', '43.0203', '43.0203', null, null, 1250, 'Fire Science/Fire-fighting.', 'kuali.enum.type.cip2000',  '736E1729C2D64D97A4F798D3B712FB6F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('cd730891-ba4f-4e07-a9b8-3ee3e6e733df', '43.0299', '43.0299', null, null, 1251, 'Fire Protection, Other.', 'kuali.enum.type.cip2000',  '6C2A23E0E5194FB8812BD4B756C4C341', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('04c5cabe-5079-430b-9a4a-b3330102f4e9', '43.99', '43.99', null, null, 1252, 'Security and Protective Services, Other.', 'kuali.enum.type.cip2000',  '0A55171EA3B44A9F9ECD133F11BA3A35', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a2dfb73e-50e8-4d46-a6bc-7ccef91b3eda', '43.9999', '43.9999', null, null, 1253, 'Security and Protective Services, Other.', 'kuali.enum.type.cip2000',  'D1F7353B60C74F52BCA42BE27E37C49E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('159f3b64-4c5f-42c9-b828-460c338160f8', '44.', '44.', null, null, 1254, 'PUBLIC ADMINISTRATION AND SOCIAL SERVICE PROFESSIONS.', 'kuali.enum.type.cip2000',  'B0ABD8B55405423895371FAAA83828D5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1c353e36-fb33-47f6-9980-011a8a73bae2', '44.00', '44.00', null, null, 1255, 'Human Services, General.', 'kuali.enum.type.cip2000',  '09FDA59408AB4CE0900D3DA764A69768', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('217db00b-e889-4ee3-9a09-5d7431096e43', '44.0000', '44.0000', null, null, 1256, 'Human Services, General.', 'kuali.enum.type.cip2000',  '9121929354084BD19C3414D8ECD7AD58', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('fabfe6b3-2616-4ab1-b468-612387f121ce', '44.02', '44.02', null, null, 1257, 'Community Organization and Advocacy.', 'kuali.enum.type.cip2000',  'ACA48AB49C7B4E80BE5A4A6A624A67E4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('517bfc34-f464-4a99-923a-67921db3e819', '44.0201', '44.0201', null, null, 1258, 'Community Organization and Advocacy.', 'kuali.enum.type.cip2000',  '79F1D85162C24CBABF3BE7CF70E4FD11', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2474cde3-dc72-440c-97e2-e01bb10e4c4e', '44.04', '44.04', null, null, 1259, 'Public Administration.', 'kuali.enum.type.cip2000',  '37F739CFC73A431C9D044DEB2D312D88', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('65aeeeb2-ac96-4899-97db-967e7422c7f6', '44.0401', '44.0401', null, null, 1260, 'Public Administration.', 'kuali.enum.type.cip2000',  '0030F5A3FBA24641BAB0A3344756591C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e7a59125-0eda-43cd-8e34-7ab3633b7b14', '44.05', '44.05', null, null, 1261, 'Public Policy Analysis.', 'kuali.enum.type.cip2000',  '319BFA2875ED4F8DAE79A99AB87C4A64', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1419513b-bfcf-4a95-b45a-48273751aefa', '44.0501', '44.0501', null, null, 1262, 'Public Policy Analysis.', 'kuali.enum.type.cip2000',  '518D51A488C741D0A84EEEA402D1483E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('27c3d4e5-5bc8-414a-98a5-70cee7097489', '44.07', '44.07', null, null, 1263, 'Social Work.', 'kuali.enum.type.cip2000', 'FBA7C8958DBD4909A5C62980D5A8390D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('44dcde1a-3d11-4fbe-b186-86208cb9737d', '44.0701', '44.0701', null, null, 1264, 'Social Work.', 'kuali.enum.type.cip2000', '8943D4B0956441EC91DE08F66FC050DC',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ad478237-c1ff-41c2-b9a7-a955b608dc75', '44.0702', '44.0702', null, null, 1265, 'Youth Services/Administration.', 'kuali.enum.type.cip2000',  'F9115ADE6F5B42F49F9DA90F7418C5ED', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ab26089b-1e90-4a3e-9dea-216956ecea8f', '44.0799', '44.0799', null, null, 1266, 'Social Work, Other.', 'kuali.enum.type.cip2000',  'F592DBBFB48B480A9BCE3833EBC1F7A8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('77514f8c-5458-4097-b636-21eb5a6d1934', '44.99', '44.99', null, null, 1267, 'Public Administration and Social Service Professions, Other.',  'kuali.enum.type.cip2000', 'D26523B9A46C448E94F221BB0A80B85A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0bde6777-2734-47d2-985a-6beba9d8757d', '44.9999', '44.9999', null, null, 1268, 'Public Administration and Social Service Professions, Other.',  'kuali.enum.type.cip2000', '40D658E6BDB54D3A8CCE8966DB474498', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('44af76ce-0058-45e5-8154-b669edcf8fc7', '45.', '45.', null, null, 1269, 'SOCIAL SCIENCES.', 'kuali.enum.type.cip2000', '0F2C3C91578C4C308122E60DD94F3590', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('edca5342-23ff-4886-85ed-1b3e9f154f14', '45.01', '45.01', null, null, 1270, 'Social Sciences, General.', 'kuali.enum.type.cip2000',  'AF47B1376F2449039F21CF990F8F5C38', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e0e1c189-c18d-4cad-bc7c-aa9b0a662ee4', '45.0101', '45.0101', null, null, 1271, 'Social Sciences, General.', 'kuali.enum.type.cip2000',  '270266FDDD964428B999767967617DB3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('afaf73eb-2278-403e-81c7-c59a8f135485', '45.02', '45.02', null, null, 1272, 'Anthropology.', 'kuali.enum.type.cip2000', '86182370CF454F99A55DE0019971461F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b770fae3-565a-4987-a17a-8ace4dd81c78', '13.0601', '13.0601', null, null, 343, 'Educational Evaluation and Research.', 'kuali.enum.type.cip2000',  'C27FFD5012E74D6DAB8CE1ECCE65A00E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6bbf9a39-0465-44aa-8777-8cdadc454c19', '13.0603', '13.0603', null, null, 344, 'Educational Statistics and Research Methods.', 'kuali.enum.type.cip2000',  '770D7ED511884E55887FC92BCC6B2A7B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('dad99d55-309e-4542-b467-0e3a2c44f11c', '13.0604', '13.0604', null, null, 345, 'Educational Assessment, Testing, and Measurement.', 'kuali.enum.type.cip2000',  'B53BA81BB5064F388CCD8F12B9EDB039', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c2989b91-336c-46df-aff5-c1fc3b7be98e', '13.0699', '13.0699', null, null, 346, 'Educational Assessment, Evaluation, and Research, Other.',  'kuali.enum.type.cip2000', 'D33781E7C265481E8507384EFB73719C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('feafdaaa-bf23-40cc-9da1-9890db26c511', '13.07', '13.07', null, null, 347, 'International and Comparative Education.', 'kuali.enum.type.cip2000',  'C2139AD4C96E459F8F447B729CF04B90', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('51681b02-b02e-4436-9472-bb838f9c1a72', '13.0701', '13.0701', null, null, 348, 'International and Comparative Education.', 'kuali.enum.type.cip2000',  'BDD381EF4FD04052B7983971BACBFD92', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('54df6dac-45b0-42e6-97db-9f495a14ce35', '13.08', '13.08', null, null, 349, 'Educational Psychology.', 'kuali.enum.type.cip2000',  '40B5EC9145934DBCB6E547E5D76C7D04', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b61064d2-1ff1-4534-bbe7-bcae476d9bd0', '13.0802', '13.0802', null, null, 350, 'Educational Psychology.', 'kuali.enum.type.cip2000',  '59A2E086C0E44F85ADAB6EDB7385B684', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9b0996ca-11a1-4f50-8dda-d84eaa8c10c1', '13.09', '13.09', null, null, 351, 'Social and Philosophical Foundations of Education.', 'kuali.enum.type.cip2000',  '26FCD691605F42BC8A6FC3F82FD34A38', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a1389782-e55c-442f-b9b7-81afd3e7333d', '13.0901', '13.0901', null, null, 352, 'Social and Philosophical Foundations of Education.', 'kuali.enum.type.cip2000',  '1B9B9C6A6AB64F9FA013BFE2F0367CA8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5a4ddc4e-b136-4c27-aa21-ee17362ea4f8', '13.10', '13.10', null, null, 353, 'Special Education and Teaching.', 'kuali.enum.type.cip2000',  'B9CD7F62E3544AA7B9DF0F4DC5EA7105', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('70686f80-12f5-4df5-bbe1-321502b2f8e8', '13.1001', '13.1001', null, null, 354, 'Special Education and Teaching, General.', 'kuali.enum.type.cip2000',  '8A4F668E434F437FAF08CFC97A1B4DD6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2b797bc6-1b88-4dd0-8b5e-c51ff4890811', '13.1003', '13.1003', null, null, 355, 'Education/Teaching of Individuals with Hearing Impairments Including  Deafness.', 'kuali.enum.type.cip2000', '43547CF169D7464BACE5CA0FF43DD025', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('dbd01cf7-332d-48d0-8a96-33add1be81d8', '13.1004', '13.1004', null, null, 356, 'Education/Teaching of the Gifted and Talented.', 'kuali.enum.type.cip2000',  '517A27F6244D4A72A5A8334F718A9B25', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('fdb05831-be06-4754-8721-077605d2d672', '13.1005', '13.1005', null, null, 357, 'Education/Teaching of Individuals with Emotional Disturbances.',  'kuali.enum.type.cip2000', '8B4C0A9C83ED4C73853FD580D793E5A1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b66bebdd-74f4-47ae-81dc-85d95f53ef5c', '13.1006', '13.1006', null, null, 358, 'Education/Teaching of Individuals with Mental Retardation.',  'kuali.enum.type.cip2000', 'B146E64E118744B6BEBC83262056F3B2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('100d0a5d-52e2-4b0c-9ef1-b7d7561e0225', '13.1007', '13.1007', null, null, 359, 'Education/Teaching of Individuals with Multiple Disabilities.',  'kuali.enum.type.cip2000', '8D66A7ADB69140C7BF59626E38FF5696', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('82c452cb-d671-488d-8390-1a6adfd698f9', '13.1008', '13.1008', null, null, 360, 'Education/Teaching of Individuals with Orthopedic and Other Physical Health  Impairments.', 'kuali.enum.type.cip2000', '5FA023768A924F048960EACEE3D6779E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('cb8315ef-c40d-46b5-9f83-58b9587d41da', '13.1009', '13.1009', null, null, 361, 'Education/Teaching of Individuals with Vision Impairments Including  Blindness.', 'kuali.enum.type.cip2000', '73D5AEE9DF954FAB8A12E4FF8B5FBB14', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('92583fcc-2d17-429a-9fd1-fcbb6475acb9', '13.1011', '13.1011', null, null, 362, 'Education/Teaching of Individuals with Specific Learning Disabilities.',  'kuali.enum.type.cip2000', '67D371D0D129405E901560364E6BC106', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d58fb0e2-4730-4743-b5ac-84a33bbd4ff4', '13.1012', '13.1012', null, null, 363, 'Education/Teaching of Individuals with Speech or Language Impairments.',  'kuali.enum.type.cip2000', 'B7D591A5387240E18B8B8DF2BC1A1FC6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ee7503d5-cb47-40b4-96ba-48ff8418acc5', '13.1013', '13.1013', null, null, 364, 'Education/Teaching of Individuals with Autism.', 'kuali.enum.type.cip2000',  '4DDF876D17AC4AD28C7F400EDE1D2E1F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bedd6e2e-8a62-4fad-9f3e-9727bf80ae71', '13.1014', '13.1014', null, null, 365, 'Education/Teaching of Individuals Who are Developmentally Delayed.',  'kuali.enum.type.cip2000', 'F8ECACCD22C8488F9F6E7A48296CA215', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2a12584d-4834-46aa-8cfc-a13478928fa7', '13.1015', '13.1015', null, null, 366, 'Education/Teaching of Individuals in Early Childhood Special Education  Programs.', 'kuali.enum.type.cip2000', 'C1855F2D2E0A40B2A86E4194111F88CE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('118b57a5-bdd5-4ae0-9376-39fa60af9a53', '13.1016', '13.1016', null, null, 367, 'Education/Teaching of Individuals with Traumatic Brain Injuries.',  'kuali.enum.type.cip2000', 'D8B49107268F4C77BE34FDA552F93D14', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b0ca32d5-71a0-438b-b068-d50bff013364', '13.1099', '13.1099', null, null, 368, 'Special Education and Teaching, Other.', 'kuali.enum.type.cip2000',  '0DF38A1A3E22423D8FBB549F6B8C2F38', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a3c81955-2150-4d60-9f02-0ba526b7c9f9', '13.11', '13.11', null, null, 369, 'Student Counseling and Personnel Services.', 'kuali.enum.type.cip2000',  '2F47893D916C4BD180EDA8A2EF896688', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('727d4b7e-d52b-4965-a34c-8441bb4c6c27', '13.1101', '13.1101', null, null, 370, 'Counselor Education/School Counseling and Guidance Services.',  'kuali.enum.type.cip2000', '3B226C058DE24B40B76923DFE759E303', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3252cbb9-3a3f-472f-a1d3-15b6187a561d', '13.1102', '13.1102', null, null, 371, 'College Student Counseling and Personnel Services.', 'kuali.enum.type.cip2000',  '9E5191BFB32E4A1C923243B723236BD5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('038f8320-afbd-4a6d-b3ad-c09993d77369', '13.1199', '13.1199', null, null, 372, 'Student Counseling and Personnel Services, Other.', 'kuali.enum.type.cip2000',  '5E5793BF813446939915919B8B32A932', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0e7cbb07-aa71-459e-b3ed-9a3199286565', '13.12', '13.12', null, null, 373, 'Teacher Education and Professional Development, Specific Levels and Methods.',  'kuali.enum.type.cip2000', '5F455D78A7E0402DA3A881A5F276AC17', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('52a41c60-0802-48f2-ae8a-05a4ba7b04c8', '13.1201', '13.1201', null, null, 374, 'Adult and Continuing Education and Teaching.', 'kuali.enum.type.cip2000',  '172A4F61ACD148499952BF696171DEC7', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('16375aa0-2eb4-4fe9-b6aa-a14db1b3899a', '13.1202', '13.1202', null, null, 375, 'Elementary Education and Teaching.', 'kuali.enum.type.cip2000',  'ADA7F3828098420789FF3A747A33FDE7', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2777b8b4-3357-4bd6-b005-ff24224d2463', '13.1203', '13.1203', null, null, 376, 'Junior High/Intermediate/Middle School Education and Teaching.',  'kuali.enum.type.cip2000', '13C1DD6A3F0B4508A23D3412049A7D6F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f84d6f98-f697-402a-a96c-254f7f9a1b49', '13.1204', '13.1204', null, null, 377, 'Pre-Elementary/Early Childhood/Kindergarten Teacher Education.',  'kuali.enum.type.cip2000', '90EA64F1B8414B61975416E621466F37', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('fc886a3a-1bc8-4344-834e-f55a864d037d', '13.1205', '13.1205', null, null, 378, 'Secondary Education and Teaching.', 'kuali.enum.type.cip2000',  'B30C4D6149A74665AF6F1352ACBBC5FE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a5b67c99-f374-4fd4-bb0a-7282120ae7bc', '13.1206', '13.1206', null, null, 379, 'Teacher Education, Multiple Levels.', 'kuali.enum.type.cip2000',  'F453BDFCDE8D439289B8FECCCC581ACD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('da4e936b-1d67-42dd-bdae-ab1b66fc64e8', '13.1207', '13.1207', null, null, 380, 'Montessori Teacher Education.', 'kuali.enum.type.cip2000',  '9EA9D425B0B84C59A3B46636A486DFE0', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('74851bc0-87fb-46d2-a7ff-0255fffe8229', '13.1208', '13.1208', null, null, 381, 'Waldorf/Steiner Teacher Education.', 'kuali.enum.type.cip2000',  '5F65FFDF263F498DB59EE48148A40DB5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('24b13e26-ccb2-45f0-928e-f65581ff8641', '13.1209', '13.1209', null, null, 382, 'Kindergarten/Preschool Education and Teaching.', 'kuali.enum.type.cip2000',  '066BA2DDD6C144698DD3C64E57544A17', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e226d2cb-66d0-425a-be48-9928f4b75188', '13.1210', '13.1210', null, null, 383, 'Early Childhood Education and Teaching.', 'kuali.enum.type.cip2000',  'E4AAFDAF7EF6474D8BB0D5A37536381E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d7def7ba-2d26-4381-b5a5-368505da2c8a', '13.1299', '13.1299', null, null, 384, 'Teacher Education and Professional Development, Specific Levels and Methods,  Other.', 'kuali.enum.type.cip2000', '9A04B4259D2846ABBECE53EBFE5181EE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6c70b639-4aef-48bb-bab9-266af45a335c', '13.13', '13.13', null, null, 385, 'Teacher Education and Professional Development, Specific Subject Areas.',  'kuali.enum.type.cip2000', 'EADD3DB3DB3C4FCA9115AFD1969DBF23', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0c9f40df-f436-49cf-b037-1749c65f8a04', '13.1301', '13.1301', null, null, 386, 'Agricultural Teacher Education.', 'kuali.enum.type.cip2000',  '9ACE5218AD854F3795908B6351B45217', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('265c5579-eb83-4524-8f99-1805f293d134', '13.1302', '13.1302', null, null, 387, 'Art Teacher Education.', 'kuali.enum.type.cip2000',  '3F0F96DB51F84554A142E4057061877F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('527aac0c-0762-4fb4-b2cc-13522d738e90', '13.1303', '13.1303', null, null, 388, 'Business Teacher Education.', 'kuali.enum.type.cip2000',  '9C76D6AE99164B698F1CB2DA34E7BB19', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f9c1fb6f-bde3-4467-9690-996e98aab793', '13.1304', '13.1304', null, null, 389, 'Driver and Safety Teacher Education.', 'kuali.enum.type.cip2000',  '2F0FCE03C86646129A4B3E45B1F170B1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3a9c1e01-d007-447e-b57c-db5c176a9f87', '13.1305', '13.1305', null, null, 390, 'English/Language Arts Teacher Education.', 'kuali.enum.type.cip2000',  'D49688D2E30F44C0A7B329DA09ED1C4B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8388a3d1-2b2e-4b9c-abe4-8cfeade10cc2', '13.1306', '13.1306', null, null, 391, 'Foreign Language Teacher Education.', 'kuali.enum.type.cip2000',  '578032E0045B4462A0251E7690C49EB8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('114c404e-b42a-4bd2-948e-54b872039217', '13.1307', '13.1307', null, null, 392, 'Health Teacher Education.', 'kuali.enum.type.cip2000',  '1BC567D8E8BF4206A83423FB1F187FB2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0aa57476-c481-4552-9fe4-12adbd48649a', '13.1308', '13.1308', null, null, 393, 'Family and Consumer Sciences/Home Economics Teacher Education.',  'kuali.enum.type.cip2000', 'F7B71433322146F38DE2A39A99584CD6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('05b4e703-7608-45f8-8915-cbdd418e1ba1', '13.1309', '13.1309', null, null, 394, 'Technology Teacher Education/Industrial Arts Teacher Education.',  'kuali.enum.type.cip2000', '88A2DCDF714D43899F617C1B4EF26BF5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('42a6cd26-bec4-47bf-8746-d4690331d099', '13.1310', '13.1310', null, null, 395, 'Sales and Marketing Operations/Marketing and Distribution Teacher  Education.', 'kuali.enum.type.cip2000', '19CB81F7D5C9407AADABBFCA73F20A12', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('29dcedfb-bd48-4816-a1e9-38d1d5487c52', '13.1311', '13.1311', null, null, 396, 'Mathematics Teacher Education.', 'kuali.enum.type.cip2000',  '1D1E13151E204E9196BA5C03662A626E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('18d14e98-ebd9-48ab-8b80-3ea0474c5e21', '13.1312', '13.1312', null, null, 397, 'Music Teacher Education.', 'kuali.enum.type.cip2000',  '12EFAA19D142416EAAEE3ADA39B7B3DC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('857d183b-871a-41e9-9595-09de11771dfa', '13.1314', '13.1314', null, null, 398, 'Physical Education Teaching and Coaching.', 'kuali.enum.type.cip2000',  'F185E6C806B440B793F90AA6F54C4B54', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('aa3fb216-6d3b-41d1-b7c2-7d4c64d6ed35', '13.1315', '13.1315', null, null, 399, 'Reading Teacher Education.', 'kuali.enum.type.cip2000',  '323A6C7A21904CBD8441FDAB9D94F553', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9dcd39cd-8996-49f5-bf05-1e2423884916', '13.1316', '13.1316', null, null, 400, 'Science Teacher Education/General Science Teacher Education.',  'kuali.enum.type.cip2000', '0C034076F99247109C2B6AB1ACC0B6AD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e7287ea9-361b-4e37-a4a6-f8557ccc7ea3', '13.1317', '13.1317', null, null, 401, 'Social Science Teacher Education.', 'kuali.enum.type.cip2000',  '1BBA75EEC68D4810854074D0A4B0967F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('39aee6b2-8a93-4528-ba4f-5ff74909323b', '13.1318', '13.1318', null, null, 402, 'Social Studies Teacher Education.', 'kuali.enum.type.cip2000',  '6336006F1A214F58B15B48ECA601AE35', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1f77e51b-5700-4521-a224-2f7540b965f1', '13.1319', '13.1319', null, null, 403, 'Technical Teacher Education.', 'kuali.enum.type.cip2000',  'AC218D9193EA43F2978D44545510D618', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('134ef590-ff43-43f3-873e-776a6b832cd2', '13.1320', '13.1320', null, null, 404, 'Trade and Industrial Teacher Education.', 'kuali.enum.type.cip2000',  'E221B60C95BF4607984F5782E1255FAC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('872bf925-186a-44df-a340-6b526e615822', '13.1321', '13.1321', null, null, 405, 'Computer Teacher Education.', 'kuali.enum.type.cip2000',  'E611C984F0304F75928444D65A7709C0', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('41dffa5e-dc79-43f6-9566-6318b814a752', '13.1322', '13.1322', null, null, 406, 'Biology Teacher Education.', 'kuali.enum.type.cip2000',  '73861EB155D445C1ACAF20BC8D070BFE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('09c0951e-6333-49d4-b68f-ba2f11045671', '13.1323', '13.1323', null, null, 407, 'Chemistry Teacher Education.', 'kuali.enum.type.cip2000',  'F8C336F93D81476C8BB71C2F9F28F292', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('cdf7b9f7-249d-4b7e-9720-5f027c3811fe', '13.1324', '13.1324', null, null, 408, 'Drama and Dance Teacher Education.', 'kuali.enum.type.cip2000',  '5AC7B5FD94684A52AC4F5B8CC4F3E7C7', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e2098b6f-557a-49fd-a7b4-17441cd3a2f6', '13.1325', '13.1325', null, null, 409, 'French Language Teacher Education.', 'kuali.enum.type.cip2000',  '04046104264546DEA2EC1DF899EF276D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('91dfbe82-4413-4a49-ae48-94fac3337570', '13.1326', '13.1326', null, null, 410, 'German Language Teacher Education.', 'kuali.enum.type.cip2000',  '9AAB6E4A71A9483BAAB2D3DAC49C8075', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('dcca41d1-86a3-4277-b6d8-0653b4d216ef', '13.1327', '13.1327', null, null, 411, 'Health Occupations Teacher Education.', 'kuali.enum.type.cip2000',  '962D4C159BB0427F8432201C6F7FD5ED', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1f49e5f7-2a7d-4aba-93a3-c5b58a1780fd', '13.1328', '13.1328', null, null, 412, 'History Teacher Education.', 'kuali.enum.type.cip2000',  '25133CE70918438DB0C18F8E1AE19709', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a9a4a143-9775-4aa7-8773-7b3855189572', '13.1329', '13.1329', null, null, 413, 'Physics Teacher Education.', 'kuali.enum.type.cip2000',  'C4C89E205D01432DBE0136E5A1699394', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('72564381-8019-4305-9671-c5ea1c19d527', '13.1330', '13.1330', null, null, 414, 'Spanish Language Teacher Education.', 'kuali.enum.type.cip2000',  '41382290CDA24890AFFA776BD5F176D1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8e894448-af1c-4270-8511-e9ac2f4189fa', '13.1331', '13.1331', null, null, 415, 'Speech Teacher Education.', 'kuali.enum.type.cip2000',  'CCA544ED9A7946A99AE1EEC95297FBB6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9d8f376d-8d85-4045-a7ec-8bedddb6570c', '13.1332', '13.1332', null, null, 416, 'Geography Teacher Education.', 'kuali.enum.type.cip2000',  '22D65E3DC6514CBC969FB8D82A50E9CB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('cafa0677-d3e4-4978-b305-c315ade46137', '13.1333', '13.1333', null, null, 417, 'Latin Teacher Education.', 'kuali.enum.type.cip2000',  '303CAA01E127455589B165228BCCF4A2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9ed376b3-9e57-46eb-8d49-1fc728b53c7f', '13.1334', '13.1334', null, null, 418, 'School Librarian/School Library Media Specialist.', 'kuali.enum.type.cip2000',  '511456A86A244FCAA803C8409CD13AA7', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c9702a8f-ab15-4536-96f8-512317cbeba1', '13.1335', '13.1335', null, null, 419, 'Psychology Teacher Education.', 'kuali.enum.type.cip2000',  'F463FC8C63874049931D6536D8B129E5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4f01c655-b892-41fc-9dcc-e970f0f38861', '13.1399', '13.1399', null, null, 420, 'Teacher Education and Professional Development, Specific Subject Areas,  Other.', 'kuali.enum.type.cip2000', 'C11BA936BB2E421599143816809E8E72', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('248e689b-9e69-4614-927a-a691505835fc', '13.14', '13.14', null, null, 421, 'Teaching English or French as a Second or Foreign Language.',  'kuali.enum.type.cip2000', '11A487CE923E4429A569587A176F686A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('71d4c3cd-6dd7-4e98-8c3f-bd0694615c5b', '13.1401', '13.1401', null, null, 422, 'Teaching English as a Second or Foreign Language/ESL Language Instructor.',  'kuali.enum.type.cip2000', 'FFABA2D8DE684238A7E5EC6F5147EAC2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3384442b-438d-4d6f-abe5-c3aaf206f86e', '13.1402', '13.1402', null, null, 423, 'Teaching French as a Second or Foreign Language.', 'kuali.enum.type.cip2000',  '71A81C761636491FBE2057D0494DBFBA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8fa96f23-3db6-4782-a373-ec66a10168bf', '13.1499', '13.1499', null, null, 424, 'Teaching English or French as a Second or Foreign Language, Other.',  'kuali.enum.type.cip2000', 'CC2FBC89398847D0A7AEABD5F20E1527', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1e493e71-8c44-448a-a353-aa60ae2d83d7', '13.15', '13.15', null, null, 425, 'Teaching Assistants/Aides.', 'kuali.enum.type.cip2000',  '59EC70BE517C41C7B3CFE349A1871354', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('643446f7-87d0-492c-9d23-799414b92788', '13.1501', '13.1501', null, null, 426, 'Teacher Assistant/Aide.', 'kuali.enum.type.cip2000',  'E274D6AC101E430EBC189D62C1BC6347', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4e57de05-2bbc-470b-88a5-50cd128e7381', '13.1502', '13.1502', null, null, 427, 'Adult Literacy Tutor/Instructor.', 'kuali.enum.type.cip2000',  '702804A7202B4511939C801410DAC531', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5fca75c9-41aa-4d83-a79a-2fac295b9049', '13.1599', '13.1599', null, null, 428, 'Teaching Assistants/Aides, Other.', 'kuali.enum.type.cip2000',  'B7CE675FFA754B05BA0B502BF5D2546A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('dcdc2ccb-cf7c-4400-8232-4b01e031619b', '13.99', '13.99', null, null, 429, 'Education, Other.', 'kuali.enum.type.cip2000', 'CDE3DE357AD548ADBC312CF8745DA317',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7b985e13-820f-43c1-a441-4276d7daa187', '13.9999', '13.9999', null, null, 430, 'Education, Other.', 'kuali.enum.type.cip2000',  '64C95FFBD7A840AD9E4398F898270831', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('92d8fcf4-2821-4476-beca-f4e457fedfe1', '14.', '14.', null, null, 431, 'ENGINEERING.', 'kuali.enum.type.cip2000', 'B40E59E44483485BB9CB44F30C90B273', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ef4a9c2f-db17-4953-baae-edfb370c1a9a', '14.01', '14.01', null, null, 432, 'Engineering, General.', 'kuali.enum.type.cip2000',  '0C35425A881E4C4DBC716362CB6740F8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('70025de9-9467-4e85-b949-d3f432e7afe5', '14.0101', '14.0101', null, null, 433, 'Engineering, General.', 'kuali.enum.type.cip2000',  '46A63B98F1DA45B9AAFF2701D687F5FD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('88c9dc9b-7ac8-4767-845c-1f7e6efa7fc2', '14.02', '14.02', null, null, 434, 'Aerospace, Aeronautical and Astronautical Engineering.', 'kuali.enum.type.cip2000',  'D19DF243607E4D71BAA198939D0BBCDA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1142a90b-1e96-45a8-9832-c286a4586f11', '14.0201', '14.0201', null, null, 435, 'Aerospace, Aeronautical and Astronautical Engineering.',  'kuali.enum.type.cip2000', '369A204D0033465BA63AF767CD865DFB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2d39c985-6529-4434-9da9-6092f601fe90', '14.03', '14.03', null, null, 436, 'Agricultural/Biological Engineering and Bioengineering.',  'kuali.enum.type.cip2000', '2EDC9BEF043F49178A4E857CB4A75586', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('939e7697-e80b-42a0-af12-1d5651643dfa', '14.0301', '14.0301', null, null, 437, 'Agricultural/Biological Engineering and Bioengineering.',  'kuali.enum.type.cip2000', 'CED764B707FD45119D0C9250D42E1606', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8a33ffba-dd57-457c-b0ed-0cf1651e06f5', '14.04', '14.04', null, null, 438, 'Architectural Engineering.', 'kuali.enum.type.cip2000',  'A62D5C98934349418444A9B1409164E8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('76a5c375-c9cd-4bed-8752-b5fa45f51d9d', '14.0401', '14.0401', null, null, 439, 'Architectural Engineering.', 'kuali.enum.type.cip2000',  'B83561CA8B094578B82ADE397F11D345', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1872d796-1c4d-467d-a894-ba68641eb5d3', '14.05', '14.05', null, null, 440, 'Biomedical/Medical Engineering.', 'kuali.enum.type.cip2000',  'ABFDCFE1CAC84499A9F2D7E68D5B4127', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('21e14ff4-dde2-4243-bb11-f903858183c3', '14.0501', '14.0501', null, null, 441, 'Biomedical/Medical Engineering.', 'kuali.enum.type.cip2000',  'DA517F1871C74A1384B4E5FC934A0B17', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a284568a-e397-4053-8a6c-4f179b678614', '14.06', '14.06', null, null, 442, 'Ceramic Sciences and Engineering.', 'kuali.enum.type.cip2000',  '72AEBAE0C38942E19ECAFC851B12676D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4956ecc7-a5dd-427a-9b88-4d208f545d9c', '14.0601', '14.0601', null, null, 443, 'Ceramic Sciences and Engineering.', 'kuali.enum.type.cip2000',  '461AB40FD9DB4CBBA6CDE0B205C334AD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('42d33aee-15c9-4132-b1d7-6d7d90102bf8', '14.07', '14.07', null, null, 444, 'Chemical Engineering.', 'kuali.enum.type.cip2000',  'CE0C3D436FF243928E0698EA2A99E2C3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9be6375a-0f84-4188-b240-84e90c701201', '14.0701', '14.0701', null, null, 445, 'Chemical Engineering.', 'kuali.enum.type.cip2000',  '263ACFC64C414697A9FB2A5715263DB2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bc37bdbb-633a-4c8c-b055-f3a6c96f5e7c', '14.08', '14.08', null, null, 446, 'Civil Engineering.', 'kuali.enum.type.cip2000', '75FC371129D349239E8B4D271EA602A2',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('241bea32-5198-4e8a-83e2-f7d67198108f', '14.0801', '14.0801', null, null, 447, 'Civil Engineering, General.', 'kuali.enum.type.cip2000',  'B036E209F7EB4EF7A8E971CF21FB4B7F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e293a014-5373-4d92-9133-d066700b393f', '14.0802', '14.0802', null, null, 448, 'Geotechnical Engineering.', 'kuali.enum.type.cip2000',  '0E465C23ADB14B29BF798986091AB5D2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4ffad1f3-1ade-476a-838c-19b7b424c8c9', '14.0803', '14.0803', null, null, 449, 'Structural Engineering.', 'kuali.enum.type.cip2000',  '4A1AA74753C945FEB85C0750EDE58F50', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f813cd00-c70c-44bd-8126-471bd313f951', '14.0804', '14.0804', null, null, 450, 'Transportation and Highway Engineering.', 'kuali.enum.type.cip2000',  '5B0A50C5454946789A32D182A8B3669D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f5dac5d6-25a8-4e88-9432-cc17c943650e', '14.0805', '14.0805', null, null, 451, 'Water Resources Engineering.', 'kuali.enum.type.cip2000',  'B9CC54F46F7A47E3A65648FEE0AF4F61', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6b8e1844-02ce-42e3-bc64-67f73bb6de16', '14.0899', '14.0899', null, null, 452, 'Civil Engineering, Other.', 'kuali.enum.type.cip2000',  'BE37A557B351489DB587EBF8CDB64E09', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0d1ae233-a642-43d7-a58a-2dd568ddf5d8', '14.09', '14.09', null, null, 453, 'Computer Engineering, General.', 'kuali.enum.type.cip2000',  '0720E7CB591D45EDAD87B67F57FD7F68', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('261a996b-84f4-4e8d-988d-bd67288eaa3a', '14.0901', '14.0901', null, null, 454, 'Computer Engineering, General.', 'kuali.enum.type.cip2000',  '18ABD4023B774B9D9D0E3BB23A7354E7', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c2200d1e-cef6-46fb-9304-e292bc0d1cbb', '14.0902', '14.0902', null, null, 455, 'Computer Hardware Engineering.', 'kuali.enum.type.cip2000',  '08DAB18041E440C188B5B8BC5E4EBFCD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7532f359-08a7-4351-8bca-37bef6a97035', '14.0903', '14.0903', null, null, 456, 'Computer Software Engineering.', 'kuali.enum.type.cip2000',  '3D6772B3A58843829B7B7874829F1D9C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6f9fa41b-e9b6-4d03-a517-a94b49544743', '14.0999', '14.0999', null, null, 457, 'Computer Engineering, Other.', 'kuali.enum.type.cip2000',  '5E768290628243ED9D7CE44AB5326765', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3df9e333-9bf1-4752-a0f6-eda747c89ebc', '14.10', '14.10', null, null, 458, 'Electrical, Electronics and Communications Engineering.',  'kuali.enum.type.cip2000', 'A5FE6896718C4386BA354D634584DD82', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4e199c0f-d223-498e-b687-94b0c42d0250', '14.1001', '14.1001', null, null, 459, 'Electrical, Electronics and Communications Engineering.',  'kuali.enum.type.cip2000', '2358124CB15E4F8C8AEED8383ABF227C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('261a2ab4-bc10-4df5-8582-915f1ca95776', '14.11', '14.11', null, null, 460, 'Engineering Mechanics.', 'kuali.enum.type.cip2000',  '9F3034E85253418DB007ED72CA1BDF92', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bfb05b34-f466-4cd3-bd7b-267f49006d53', '14.1101', '14.1101', null, null, 461, 'Engineering Mechanics.', 'kuali.enum.type.cip2000',  '191C9FD9543448EEA6EDA0A5D88CF8C9', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bc4fb8c8-7da0-46b0-8f11-725e480a59c0', '14.12', '14.12', null, null, 462, 'Engineering Physics.', 'kuali.enum.type.cip2000',  '30C32CD35FE747DD984F566CFDD684EF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f0a4fd51-415a-43db-9479-a6442175ff30', '14.1201', '14.1201', null, null, 463, 'Engineering Physics.', 'kuali.enum.type.cip2000',  'B41CA50425654CF7B34954F88EE4FF8E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b4fa77fb-b0c1-4c04-9cac-9977a4823a28', '14.13', '14.13', null, null, 464, 'Engineering Science.', 'kuali.enum.type.cip2000',  'B019E74A755E481FBB27A0F82EFBFEB8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('26206cf1-6de3-41f0-af6d-44a01cdf2fef', '14.1301', '14.1301', null, null, 465, 'Engineering Science.', 'kuali.enum.type.cip2000',  '4236FB9EBC684C81B270809554243A6A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('956196cd-5beb-4620-be10-00f8ab8b8dec', '14.14', '14.14', null, null, 466, 'Environmental/Environmental Health Engineering.', 'kuali.enum.type.cip2000',  'DE2BE9A039D049C3A11DA2CB299CEC54', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5d895c0f-5f64-4656-b96d-a437fd3d5279', '14.1401', '14.1401', null, null, 467, 'Environmental/Environmental Health Engineering.', 'kuali.enum.type.cip2000',  '09C8DEC836F94C6F88AC8971A50EF17E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5802a024-4d6c-4322-bc0d-1a73a462c863', '14.15', '14.15', null, null, 468, 'Geological Engineering.', 'kuali.enum.type.cip2000',  '0411099BE3D240EC82096AA341A37385', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7749d3c9-4d28-4b0b-a39c-60f30ae39029', '14.1501', '14.1501', null, null, 469, 'Geological Engineering.', 'kuali.enum.type.cip2000',  'F49E618F82AA481DBB4359D486026105', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('dcdd8050-5691-45c3-be69-2c429e7c8216', '14.16', '14.16', null, null, 470, 'Geophysical Engineering.', 'kuali.enum.type.cip2000',  '1F366CECD9DE4E96B249C18D9A7DD61F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('596e52b2-beb2-48ff-a305-90ddb312f560', '14.1601', '14.1601', null, null, 471, 'Geophysical Engineering.', 'kuali.enum.type.cip2000',  'CB9E601FE47A4C55942F431A5794F263', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3098097d-2151-413f-bd51-10f806fa1970', '14.17', '14.17', null, null, 472, 'Industrial/Manufacturing Engineering.', 'kuali.enum.type.cip2000',  '850B287D19034C268B8F30BA8541CA99', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f43566b1-9608-49a4-abe9-9f5fd053cbec', '14.1701', '14.1701', null, null, 473, 'Industrial/Manufacturing Engineering.', 'kuali.enum.type.cip2000',  '434B2DFCD16D41B2B834DE8101AAB8AF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a60d13bf-b946-43f7-9ba7-552e0a235e33', '14.18', '14.18', null, null, 474, 'Materials Engineering Instructional content is defined in code 14.',  'kuali.enum.type.cip2000', 'E23253846E9E4DDD8A367A9A4BC30EDA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('dc390286-8dd0-4fc6-8347-bde5f4300521', '14.1801', '14.1801', null, null, 475, 'Materials Engineering.', 'kuali.enum.type.cip2000',  '3E77B06CF69A4AB892F466CF3D80B389', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d763058b-ca5b-4811-96ed-558444a7def0', '14.19', '14.19', null, null, 476, 'Mechanical Engineering.', 'kuali.enum.type.cip2000',  '016A8BB005854A52AE76078CA70FE581', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('fc766969-da3b-40ce-adbc-5e5085d5ac44', '14.1901', '14.1901', null, null, 477, 'Mechanical Engineering.', 'kuali.enum.type.cip2000',  '6AC8592DB5F94D4BBAFFB366BB45DC3E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ceb29781-8cf1-405b-a2b3-8337dd5f57e1', '14.20', '14.20', null, null, 478, 'Metallurgical Engineering.', 'kuali.enum.type.cip2000',  'D2A5CB0F31FC4AD5866B65D628AD87ED', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('fbd4ff30-ab70-425f-822b-ab8679c7a7c2', '14.2001', '14.2001', null, null, 479, 'Metallurgical Engineering.', 'kuali.enum.type.cip2000',  '4CBD951638CD4DF7A48F38BC44A4B7C1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c6d88401-6c8f-4db2-a8ab-d807e4acf6fa', '14.21', '14.21', null, null, 480, 'Mining and Mineral Engineering.', 'kuali.enum.type.cip2000',  '8907EF7F27174DF7AAB6AB9DC0505CF7', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('294662db-10e4-4e45-bf41-c69d92a03ac6', '14.2101', '14.2101', null, null, 481, 'Mining and Mineral Engineering.', 'kuali.enum.type.cip2000',  'A3BBD35E9D3B4B5C9BD25C79009BFD10', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4d276063-10e5-4dcf-99c0-3c35a62a2616', '14.22', '14.22', null, null, 482, 'Naval Architecture and Marine Engineering.', 'kuali.enum.type.cip2000',  'C4A6B538C4834A858F5432264DA925A0', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6b0a6bab-c7dd-4d1d-803c-1f91a90d4dc7', '14.2201', '14.2201', null, null, 483, 'Naval Architecture and Marine Engineering.', 'kuali.enum.type.cip2000',  '98AC0712200F40F6B7436D061F750F16', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('745b6980-761a-4c99-96fd-1e7e59dbecec', '14.23', '14.23', null, null, 484, 'Nuclear Engineering.', 'kuali.enum.type.cip2000',  'D88C5B4AB7D046C68F86AE30E574D026', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('52db70b1-75da-463a-b228-0fe83052a229', '14.2301', '14.2301', null, null, 485, 'Nuclear Engineering.', 'kuali.enum.type.cip2000',  'D26A7379637242C499C63E15CCC6B41D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bc8ecef5-e7d7-450d-acfe-fa2a69c503e0', '14.24', '14.24', null, null, 486, 'Ocean Engineering.', 'kuali.enum.type.cip2000', 'B2F0493E1788431C8EF96F89C95E87A0',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8cbd3624-8a7f-47b0-b181-9e1d5ae9ae9f', '14.2401', '14.2401', null, null, 487, 'Ocean Engineering.', 'kuali.enum.type.cip2000',  '51A111598B7C4BAD90CF165DD6BA7A08', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('422593a9-c977-4ca3-94eb-d887d56d452f', '14.25', '14.25', null, null, 488, 'Petroleum Engineering.', 'kuali.enum.type.cip2000',  'A1E8A28EA5404B5AA60E05BF47F4269D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('dda0ac4d-ec94-4cb2-90c2-261edbcc85cf', '14.2501', '14.2501', null, null, 489, 'Petroleum Engineering.', 'kuali.enum.type.cip2000',  'E6CC6AE5764A441CACBDE6582EFAA3BD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a1b8c9d3-4c10-4a0d-846d-dd98003ef4e8', '14.27', '14.27', null, null, 490, 'Systems Engineering.', 'kuali.enum.type.cip2000',  'E974ABAE76C747F0AE6CB241F6CF75EF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3ce8762e-ba49-420d-85fc-a6172c557f49', '14.2701', '14.2701', null, null, 491, 'Systems Engineering.', 'kuali.enum.type.cip2000',  '3BE1A9E7E58B4254A19AB808A3F37C11', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9effe617-28f8-4e1c-91f4-40c7a94f6f67', '14.28', '14.28', null, null, 492, 'Textile Sciences and Engineering.', 'kuali.enum.type.cip2000',  'D3E3F254743641C3A5F44B3851B8D9FB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d1bec056-d4da-4cf7-afa8-e8045280477b', '14.2801', '14.2801', null, null, 493, 'Textile Sciences and Engineering.', 'kuali.enum.type.cip2000',  '1708019B440C4D97917E80F598E9F522', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('811c7e71-bcf7-4077-8745-c345418a3f9f', '14.29', '14.29', null, null, 494, 'Engineering Design.', 'kuali.enum.type.cip2000',  '2D62D3405E424236A0996FFC1A316675', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f919249d-3e4d-409c-961d-4502aab8ef3e', '14.2901', '14.2901', null, null, 495, 'Engineering Design.', 'kuali.enum.type.cip2000',  'D45164467913409A9121C2C21DF17ED9', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('89956ad2-38bf-4c19-a692-d31b34d64564', '14.30', '14.30', null, null, 496, 'Engineering/Industrial Management.', 'kuali.enum.type.cip2000',  'E7264DCE0A9A4650BA4FEBD08896BD4A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('534e0aa7-9776-4b6c-90ca-8d53e0ea3dd0', '14.3001', '14.3001', null, null, 497, 'Engineering/Industrial Management.', 'kuali.enum.type.cip2000',  'EF0FFFDF3BB046CF80E94F9789F45F6E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8345fd2e-529d-4369-bc9f-fd89055c7ccc', '14.31', '14.31', null, null, 498, 'Materials Science.', 'kuali.enum.type.cip2000', 'AD431B2FF3AD44858276FB91E20B9560',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('253640cc-73a4-4cfb-86b3-f0079d93c453', '14.3101', '14.3101', null, null, 499, 'Materials Science.', 'kuali.enum.type.cip2000',  'A01ACD849C9F4ABCA1F36C12FE32D298', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9bb5648b-6593-4508-bd63-36455fca4e93', '14.32', '14.32', null, null, 500, 'Polymer/Plastics Engineering.', 'kuali.enum.type.cip2000',  '7DEF87440813455CA3E9197157213E86', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('62a2db1c-0d0c-48c2-af24-ec0b3c67401c', '14.3201', '14.3201', null, null, 501, 'Polymer/Plastics Engineering.', 'kuali.enum.type.cip2000',  '1315EC0A1B3E4384A2E9ED4CA121539A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9b490dce-0899-4360-a8f3-839c899e7d9e', '14.33', '14.33', null, null, 502, 'Construction Engineering.', 'kuali.enum.type.cip2000',  '1DC3135FB9704C2783E8465DD4C07C1B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('867de606-4db4-4348-b286-3e6e875ea73f', '14.3301', '14.3301', null, null, 503, 'Construction Engineering.', 'kuali.enum.type.cip2000',  'F9BB18156F0149DE92C359FFFA30F6A4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3e7f32ce-a868-431d-9024-ff6ccfc47d67', '14.34', '14.34', null, null, 504, 'Forest Engineering.', 'kuali.enum.type.cip2000',  '4520657328D54456AE3805F8BB611710', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6cdf5d91-aae5-4e00-8a83-fc46e689df48', '14.3401', '14.3401', null, null, 505, 'Forest Engineering.', 'kuali.enum.type.cip2000',  'FE1155C7A22B46199C3F3E38B7FC01CC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3a00922d-0d51-407a-b875-36e28c513d41', '14.35', '14.35', null, null, 506, 'Industrial Engineering.', 'kuali.enum.type.cip2000',  'D11EBC3E93544982B0A89B6D4D84012C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('668ebf6f-1395-447f-b76e-bdb74f21fe82', '14.3501', '14.3501', null, null, 507, 'Industrial Engineering.', 'kuali.enum.type.cip2000',  '6C6D3E38E98A4DA3AE9B23EF11086C13', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1a261355-7795-4a85-82e7-5ce45262ab55', '14.36', '14.36', null, null, 508, 'Manufacturing Engineering.', 'kuali.enum.type.cip2000',  'C337885101BD49F49D6D4101DF1780C5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c2aaa7bf-d70c-43f9-b0b6-7a22a53464db', '14.3601', '14.3601', null, null, 509, 'Manufacturing Engineering.', 'kuali.enum.type.cip2000',  '36BF4A56BCA14DDA8AC093D990F8B250', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('48900581-be74-4e3e-81c9-d8bd37bda5e6', '14.37', '14.37', null, null, 510, 'Operations Research.', 'kuali.enum.type.cip2000',  '8FDA5358C03E45D9BD63EBF470A0C703', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0096bb00-a92f-49f9-87a9-98a99850a2a4', '14.3701', '14.3701', null, null, 511, 'Operations Research.', 'kuali.enum.type.cip2000',  '8B9358D1231E4ACDA05B1AF682044EBA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('72be4778-2f80-40a3-b9da-9fd5f3aed51b', '14.38', '14.38', null, null, 512, 'Surveying Engineering.', 'kuali.enum.type.cip2000',  '24679AD4997F4BD18DA8920FCD6F4760', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('99b0a398-b371-4daa-82a1-29a3c1d7f838', '14.3801', '14.3801', null, null, 513, 'Surveying Engineering.', 'kuali.enum.type.cip2000',  'A27FC3A825754266B47790051040F74A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('64dbd9d0-aedc-4d32-94c0-aa225ac8fb65', '14.39', '14.39', null, null, 514, 'Geological/Geophysical Engineering.', 'kuali.enum.type.cip2000',  '8A2361070E664552978DD900BA03EEB4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5ed0ef0f-007e-4919-bc30-0ba28c1bc459', '14.3901', '14.3901', null, null, 515, 'Geological/Geophysical Engineering.', 'kuali.enum.type.cip2000',  'E46F73AD95534342BBD8697DF0AC5F6D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8368ff0f-0452-4917-9f06-5bf7746eebe2', '14.99', '14.99', null, null, 516, 'Engineering, Other.', 'kuali.enum.type.cip2000',  '68698F70BBAD451B98DA30C9E9FB81EF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('80ff6224-1c31-4985-897e-7531ae76ff04', '14.9999', '14.9999', null, null, 517, 'Engineering, Other.', 'kuali.enum.type.cip2000',  '4940D8CC48B243FE9F6DC840D10E70B9', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2ed60b15-6e05-4f0e-8680-7485fd9aef5f', '15.', '15.', null, null, 518, 'ENGINEERING TECHNOLOGIES/TECHNICIANS.', 'kuali.enum.type.cip2000',  'D4DC079D69FC4C598DA104571999D7C0', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a27ceade-5cac-4bb6-b563-e6682eba2988', '15.00', '15.00', null, null, 519, 'Engineering Technology, General.', 'kuali.enum.type.cip2000',  '73D6654A30F2438DBD5948D51D2B6C62', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0b4ab548-b611-442a-b1cf-92fe3dd33e91', '15.0000', '15.0000', null, null, 520, 'Engineering Technology, General.', 'kuali.enum.type.cip2000',  'FC577E89042C4BEB826D6416E3CAA532', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('df757254-4252-44e3-bb5d-af010f51a374', '15.01', '15.01', null, null, 521, 'Architectural Engineering Technologies/Technicians.', 'kuali.enum.type.cip2000',  '2A0E0868F9FE40949353A913D890AD11', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('00bf8629-5c83-4834-a0c2-e0dbfd227c5b', '15.0101', '15.0101', null, null, 522, 'Architectural Engineering Technology/Technician.', 'kuali.enum.type.cip2000',  '771D0AE4A4524E11A0BF6590A8C1BC89', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b3d2e475-b4da-42ba-9ca2-ed3d9a8d395c', '15.02', '15.02', null, null, 523, 'Civil Engineering Technologies/Technicians.', 'kuali.enum.type.cip2000',  '63BF6603E8A44A3991DE16BA2922233E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8c286b16-f898-4636-9b3e-7aec0ff57e31', '15.0201', '15.0201', null, null, 524, 'Civil Engineering Technology/Technician.', 'kuali.enum.type.cip2000',  '7BB5680C20DE4D2AB5AD662F12A1199C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('01a0d512-d6e5-4a7f-9af2-94933368e712', '15.03', '15.03', null, null, 525, 'Electrical Engineering Technologies/Technicians.', 'kuali.enum.type.cip2000',  '65A6E0D17B0E4D0BBF404C18DAA15412', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3824d57a-9dc8-4b42-b4af-14df734ec5a4', '15.0301', '15.0301', null, null, 526, 'Computer Engineering Technology/Technician.', 'kuali.enum.type.cip2000',  '65409E89827A40F8A3448BCE7B3F680E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7bedd289-9956-4a86-9c78-a00d48bdbbfd', '15.0303', '15.0303', null, null, 527, 'Electrical, Electronic and Communications Engineering Technology/Technician.',  'kuali.enum.type.cip2000', 'B65FAB2AD95747CD877E6030CE3E890D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e2b55ff6-7aad-4ebd-a7d1-73fbef508053', '15.0304', '15.0304', null, null, 528, 'Laser and Optical Technology/Technician.', 'kuali.enum.type.cip2000',  'B2E69C3B0364498682204A81DD2F27DB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3468b879-da3b-40ac-8601-a0f1f0f72b0a', '15.0305', '15.0305', null, null, 529, 'Telecommunications Technology/Technician.', 'kuali.enum.type.cip2000',  '2C370A10E0A0401FAA5C77783A8EB550', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d6e69903-fa29-4513-bae4-5e838913d6bc', '15.0399', '15.0399', null, null, 530, 'Electrical and Electronic Engineering Technologies/Technicians, Other.',  'kuali.enum.type.cip2000', 'C88C950B43914614AD5C3FDF88C5B7FC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7bdb50ab-fb78-4d4c-af58-869e3f07b646', '15.04', '15.04', null, null, 531, 'Electromechanical Instrumentation and Maintenance Technologies/Technicians.',  'kuali.enum.type.cip2000', '382420E8BFBF481D8D6962D71E6C69DF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7e404152-62f2-481d-9b23-93867160cfec', '15.0401', '15.0401', null, null, 532, 'Biomedical Technology/Technician.', 'kuali.enum.type.cip2000',  '91DC3639451A4F0E8CD02D3F1E4B892A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5f7dfcde-0836-4e4f-973f-5261aff0289c', '15.0402', '15.0402', null, null, 533, 'Computer Maintenance Technology/Technician.', 'kuali.enum.type.cip2000',  '4EBF71660D374436A13B9FD366633DEC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c2044013-f3c1-402c-9835-cd4a49cd9a34', '15.0403', '15.0403', null, null, 534, 'Electromechanical Technology/Electromechanical Engineering Technology.',  'kuali.enum.type.cip2000', 'CDA876651CB5443EA672AAF65175FE95', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('50cc2a51-6e51-47d9-81b4-badf0cc01012', '15.0404', '15.0404', null, null, 535, 'Instrumentation Technology/Technician.', 'kuali.enum.type.cip2000',  'DB41FC322C9D4ED68968C74E4E6A1732', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8a57d739-c5ed-4795-8e82-6cc913cd0045', '15.0405', '15.0405', null, null, 536, 'Robotics Technology/Technician.', 'kuali.enum.type.cip2000',  'BA476ED75C7743FE8A3423656C65E8F5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7a44d909-07ef-4e53-92f0-c64bac41c912', '15.0499', '15.0499', null, null, 537, 'Electromechanical and Instrumentation and Maintenance Technologies/Technicians,  Other.', 'kuali.enum.type.cip2000', 'AA89FA5FA3644C13AFA8277E261D8D4E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3f6f75b1-a3fc-4886-8384-81107025710f', '15.05', '15.05', null, null, 538, 'Environmental Control Technologies/Technicians.', 'kuali.enum.type.cip2000',  '2C906DD4676A4A399F3BA0B880113CE2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8dbc08f7-ea60-48cb-b375-c2902006df70', '15.0501', '15.0501', null, null, 539, 'Heating, Air Conditioning and Refrigeration Technology/Technician  (ACH/ACR/ACHR/HRAC/HVAC/AC Technology).', 'kuali.enum.type.cip2000', '33699FAE559F4BFF9F62EB9B09C95793', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7a20da7e-5245-4ae7-a84b-bc10b9d3c3a4', '15.0503', '15.0503', null, null, 540, 'Energy Management and Systems Technology/Technician.',  'kuali.enum.type.cip2000', 'DD024C606B42466F965F3AEC1BE5F0C8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c88d86cf-bb58-490d-83d2-4618179dcb2d', '15.0505', '15.0505', null, null, 541, 'Solar Energy Technology/Technician.', 'kuali.enum.type.cip2000',  'A5F79212770348BD8B5C1BE22648F4AE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a5cac510-ed80-4cf1-aa9c-9727fb4f1fbb', '15.0506', '15.0506', null, null, 542, 'Water Quality and Wastewater Treatment Management and Recycling  Technology/Technician.', 'kuali.enum.type.cip2000', 'D2D74A6D9BF14D1598629044BC578C06', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6057d393-b010-4d82-a647-73b7f5855e68', '15.0507', '15.0507', null, null, 543, 'Environmental Engineering Technology/Environmental Technology.',  'kuali.enum.type.cip2000', '52182CCCB61A45B8B06BD952115F665B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('424fdaf7-22ed-491e-b1f9-946eca727af9', '15.0508', '15.0508', null, null, 544, 'Hazardous Materials Management and Waste Technology/Technician.',  'kuali.enum.type.cip2000', 'C26FAE7615D74C1090EDD109824652F7', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('43374e04-e67d-4f67-930b-00615f83b523', '15.0599', '15.0599', null, null, 545, 'Environmental Control Technologies/Technicians, Other.',  'kuali.enum.type.cip2000', 'F38BB6D0FC0E4F239E280AA2147C65F3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5bd05847-9c14-453d-90a5-f91f843a677e', '15.06', '15.06', null, null, 546, 'Industrial Production Technologies/Technicians.', 'kuali.enum.type.cip2000',  '94124693F94842A487751FE466388D32', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7b3671b7-2916-4a0b-a184-31355a10bae0', '15.0603', '15.0603', null, null, 547, 'Industrial/Manufacturing Technology/Technician.', 'kuali.enum.type.cip2000',  '762FF6634F7747078A119AA9B6FE9B90', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3a123fb9-5f75-4607-967c-12702bcc8243', '15.0607', '15.0607', null, null, 548, 'Plastics Engineering Technology/Technician.', 'kuali.enum.type.cip2000',  'B5046632A5F448CCBC51A3D817453B32', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6ec5bb92-e7a7-471e-8b91-5ae93198439a', '15.0611', '15.0611', null, null, 549, 'Metallurgical Technology/Technician.', 'kuali.enum.type.cip2000',  '1A17E963144A4F32AF8A5666AB77D7DD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('78493560-3bfc-41c5-a785-a2dc030e30ee', '15.0612', '15.0612', null, null, 550, 'Industrial Technology/Technician.', 'kuali.enum.type.cip2000',  'ABAE912E0A234332AECFAA17322E65FD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('83b88eae-deec-4fe7-a87b-850e93d97740', '15.0613', '15.0613', null, null, 551, 'Manufacturing Technology/Technician.', 'kuali.enum.type.cip2000',  '7E2157014FFB4616B4C4ED0FAC3B9CEA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d26398ad-8ee4-4d55-9204-c9231d0e55e4', '15.0699', '15.0699', null, null, 552, 'Industrial Production Technologies/Technicians, Other.',  'kuali.enum.type.cip2000', '47D35FE0714A401080F270227A1406F3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('37d4408c-65b4-4c0e-866b-c336e679fdc5', '15.07', '15.07', null, null, 553, 'Quality Control and Safety Technologies/Technicians.', 'kuali.enum.type.cip2000',  'B2FDF3264ADD472B980D2A6E767440C2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2ac0b2f4-edc0-4a88-824a-630d7577f2d4', '15.0701', '15.0701', null, null, 554, 'Occupational Safety and Health Technology/Technician.',  'kuali.enum.type.cip2000', 'B66F9B219F89431A8BBC3A4894A80D64', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5d36f680-3cc6-4ad9-9c2c-e80000ab19c3', '15.0702', '15.0702', null, null, 555, 'Quality Control Technology/Technician.', 'kuali.enum.type.cip2000',  '665744A6CCED45F5897D11B71BF3B837', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('53edc8d4-6a95-41b8-8840-05d3f9e541b4', '15.0703', '15.0703', null, null, 556, 'Industrial Safety Technology/Technician.', 'kuali.enum.type.cip2000',  '6CA8A2222D7B498DAA78428B0ED37A2D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('65e76471-ff5f-4930-b75b-9dedf8827e2e', '15.0704', '15.0704', null, null, 557, 'Hazardous Materials Information Systems Technology/Technician.',  'kuali.enum.type.cip2000', 'F356FC01096F42A0B04C4B011173BEC5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8e123d93-21e7-4779-8193-bdf8268a505a', '15.0799', '15.0799', null, null, 558, 'Quality Control and Safety Technologies/Technicians, Other.',  'kuali.enum.type.cip2000', '6D51E9BF78D1495C8E522CCDF60F8648', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('20437415-1669-4302-b0b0-acac967753ce', '15.08', '15.08', null, null, 559, 'Mechanical Engineering Related Technologies/Technicians.',  'kuali.enum.type.cip2000', 'E94F087CE8E74350B1F5B2762C2EA8D6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9bb93eea-1aaa-4700-ae1a-6db996797bcb', '15.0801', '15.0801', null, null, 560, 'Aeronautical/Aerospace Engineering Technology/Technician.',  'kuali.enum.type.cip2000', '1A33B628F28E40AF8E6D7F3863D147B2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8d097623-13bc-4ae9-ac78-87b978b99e28', '15.0803', '15.0803', null, null, 561, 'Automotive Engineering Technology/Technician.', 'kuali.enum.type.cip2000',  'F3A1E7872D2B4B8EAF92CB122641EE2F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('35b253ea-76e4-47c6-86aa-3987ef0a6618', '15.0805', '15.0805', null, null, 562, 'Mechanical Engineering/Mechanical Technology/Technician.',  'kuali.enum.type.cip2000', 'EA8F18AABC0246E7BFD68F2EDE880132', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c27578d6-ae8a-433a-abc6-043627c5ca8a', '15.0899', '15.0899', null, null, 563, 'Mechanical Engineering Related Technologies/Technicians, Other.',  'kuali.enum.type.cip2000', 'B196E45363314E4B956018D3D5DCC678', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('265e2bfe-cb09-4116-b3e7-769d2006e782', '15.09', '15.09', null, null, 564, 'Mining and Petroleum Technologies/Technicians.', 'kuali.enum.type.cip2000',  '6E5F13F7A0F140CC91747F24C3F32C52', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1ba1c8ef-92e6-4255-aff6-b74520ad8daa', '15.0901', '15.0901', null, null, 565, 'Mining Technology/Technician.', 'kuali.enum.type.cip2000',  'DEF1871968094A859D825AFCD47E45F4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a0a63c6a-80a7-42bb-b8bd-41e5af03e269', '15.0903', '15.0903', null, null, 566, 'Petroleum Technology/Technician.', 'kuali.enum.type.cip2000',  '5A40671D91124EB7B8346C16E93C0A07', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('09ebd07a-fc6c-464d-b426-610fcbcd9cf4', '15.0999', '15.0999', null, null, 567, 'Mining and Petroleum Technologies/Technicians, Other.',  'kuali.enum.type.cip2000', '3B17B749D093453CADA2284D0155A94B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e9b545ec-cf8d-4f74-8e31-eb3edb6de2f4', '15.10', '15.10', null, null, 568, 'Construction Engineering Technologies.', 'kuali.enum.type.cip2000',  '35BEF525D49A48ED8C70B71B497F9C3F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('431715e5-8c62-4768-98e7-5d1c0e8b2771', '15.1001', '15.1001', null, null, 569, 'Construction Engineering Technology/Technician.', 'kuali.enum.type.cip2000',  '08A91EE21844411DB67A7712DD8CB463', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('979e9c3d-f2d9-4a57-ae9c-d839b042419b', '15.11', '15.11', null, null, 570, 'Engineering-Related Technologies.', 'kuali.enum.type.cip2000',  '0E924F2FCE8240659B4772D96E5D9A18', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('eea6c402-1737-4329-bb37-8d858b6a8bf3', '15.1101', '15.1101', null, null, 571, 'Engineering Technology/Technician, General.', 'kuali.enum.type.cip2000',  '09328A6B8A8F471BADEE414AC15D2758', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c9c1ff36-1aee-4a4b-9ed9-fc3939fd6c61', '15.1102', '15.1102', null, null, 572, 'Surveying Technology/Surveying.', 'kuali.enum.type.cip2000',  'D7317480B830409C978BCDA6604BDD9B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('994718ff-9550-44b2-8c26-6b45acbdc69b', '15.1103', '15.1103', null, null, 573, 'Hydraulics and Fluid Power Technology/Technician.', 'kuali.enum.type.cip2000',  '914B9B5B4B5F4B29AFD84B94A473151D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('57235e64-162d-4d90-b034-89e3e5503581', '15.1199', '15.1199', null, null, 574, 'Engineering-Related Technologies, Other.', 'kuali.enum.type.cip2000',  'A4A79D03BC944C4A9B357D85EDB77A7E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('adab5607-03f2-4c2f-ad4a-76b2515fe378', '15.12', '15.12', null, null, 575, 'Computer Engineering Technologies/Technicians.', 'kuali.enum.type.cip2000',  '98D71CB75C0C419AA5E78646FA66175F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('381f37e7-5e84-48df-8473-3379e5c07a98', '15.1201', '15.1201', null, null, 576, 'Computer Engineering Technology/Technician.', 'kuali.enum.type.cip2000',  '9B5B5FF86CF9418A86A72BEE7EDB0944', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('df0e671f-8c68-49d3-a1f9-387c179f4feb', '15.1202', '15.1202', null, null, 577, 'Computer Technology/Computer Systems Technology.', 'kuali.enum.type.cip2000',  '921F2D941CE944828BCC082C2470B23B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a8d3b56e-14ec-4af3-a4c9-8b33564c8b07', '15.1203', '15.1203', null, null, 578, 'Computer Hardware Technology/Technician.', 'kuali.enum.type.cip2000',  '301AAE060D5F4623ADB98C3848B70168', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d4e78a13-15b8-403a-8fb2-f2debd6adee2', '15.1204', '15.1204', null, null, 579, 'Computer Software Technology/Technician.', 'kuali.enum.type.cip2000',  'FFE5F470C9B44A829B93F0C1DC032B7B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a22636b7-31dc-4a1e-b80d-2503041b3a36', '15.1299', '15.1299', null, null, 580, 'Computer Engineering Technologies/Technicians, Other.',  'kuali.enum.type.cip2000', 'FDBEDE6E5AE3400E82A2931D864298BE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f1b56da2-c52e-4084-8cb6-d11b92eb4dd5', '15.13', '15.13', null, null, 581, 'Drafting/Design Engineering Technologies/Technicians.', 'kuali.enum.type.cip2000',  '308812956E6243A79606AF399CD9EE15', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d67d68ab-4478-424b-a054-d54c552a1f92', '15.1301', '15.1301', null, null, 582, 'Drafting and Design Technology/Technician, General.',  'kuali.enum.type.cip2000', '082E75F2291F4A0B9D4462B960D17347', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a5220f92-bb4e-4a09-99b0-26974bfba6ec', '15.1302', '15.1302', null, null, 583, 'CAD/CADD Drafting and/or Design Technology/Technician.',  'kuali.enum.type.cip2000', 'BB4C18F505524C98AC5B41E9A53D6A10', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('92088002-3f9d-4cd0-9370-1b558be6c957', '15.1303', '15.1303', null, null, 584, 'Architectural Drafting and Architectural CAD/CADD.', 'kuali.enum.type.cip2000',  'FD2632C2F85541128957DE029CFA3A3B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ad5c1491-6be5-4d33-b1a6-6c06b228a901', '15.1304', '15.1304', null, null, 585, 'Civil Drafting and Civil Engineering CAD/CADD.', 'kuali.enum.type.cip2000',  '1EFEB2D6EA5747F1A0191DF3F01F7D32', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('78348512-d6c1-4d84-a9d0-8fce5054b2f8', '15.1305', '15.1305', null, null, 586, 'Electrical/Electronics Drafting and Electrical/Electronics CAD/CADD.',  'kuali.enum.type.cip2000', '2DE3771762D745438DD8250B6E24F6AF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8b517a16-b265-4650-8a8e-c6cd9f04edaa', '15.1306', '15.1306', null, null, 587, 'Mechanical Drafting and Mechanical Drafting CAD/CADD.',  'kuali.enum.type.cip2000', '2FE1931BBBA54298817A081E24C1B7B9', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('26c56aec-127a-4da7-950e-1f62e65511ff', '15.1399', '15.1399', null, null, 588, 'Drafting/Design Engineering Technologies/Technicians, Other.',  'kuali.enum.type.cip2000', '7A4D410A4A9645449DBDB3AAA09C170D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e4286982-0f45-44bd-839d-e7821f8bbe33', '15.14', '15.14', null, null, 589, 'Nuclear Engineering Technologies/Technicians.', 'kuali.enum.type.cip2000',  '0DDE03CDB7BD4638B90E67978BA0A558', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ab6bf236-b8f6-41ff-b8e6-6dfdeb875171', '15.1401', '15.1401', null, null, 590, 'Nuclear Engineering Technology/Technician.', 'kuali.enum.type.cip2000',  '3C237DB5792E4A29A83E0F6852C26016', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b728e1e7-8057-4b21-b76e-ec5705c5458c', '15.15', '15.15', null, null, 591, 'Engineering-Related Fields.', 'kuali.enum.type.cip2000',  'A166A728F0EC4DE7A2C2261EEBFC8FCA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c6a1c94e-02ed-455d-ae74-c9bd5873ae13', '15.1501', '15.1501', null, null, 592, 'Engineering/Industrial Management.', 'kuali.enum.type.cip2000',  '0FC635C6A370430CABDAB54979B05F5C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('cf37bec8-d3cd-4191-90bd-7fd54bb48727', '15.99', '15.99', null, null, 593, 'Engineering Technologies/Technicians, Other.', 'kuali.enum.type.cip2000',  '3F1364C211CF4CBFBD39503818AEFCE8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('20af02a9-8560-4a01-8742-53995e953fc2', '15.9999', '15.9999', null, null, 594, 'Engineering Technologies/Technicians, Other.', 'kuali.enum.type.cip2000',  'EAF5B2D17ABF4CD493B7818B411B319C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7a7bba3b-6cb0-4d69-8f2a-e959a586063d', '16.', '16.', null, null, 595, 'FOREIGN LANGUAGES, LITERATURES, AND LINGUISTICS.', 'kuali.enum.type.cip2000',  '28853950A45B40E486A2B9826A1B3E3C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('45f7fef6-301d-4168-bf78-7989ea569b9a', '16.01', '16.01', null, null, 596, 'Linguistic, Comparative, and Related Language Studies and Services.',  'kuali.enum.type.cip2000', '15EF369552AA4E4EAB90B43854072879', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0dbac481-ce2f-4e17-8e02-4ace46c06aa6', '16.0101', '16.0101', null, null, 597, 'Foreign Languages and Literatures, General.', 'kuali.enum.type.cip2000',  '1288F0D948ED422BB4607C85C9C77081', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('cf82386d-141a-47ab-ae4b-83f4f458ff56', '16.0102', '16.0102', null, null, 598, 'Linguistics.', 'kuali.enum.type.cip2000', '325B264E87F6400CB10E2F31E9B13D6A',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5f401cdc-ef84-4199-bcbd-9d2f99f9d28a', '16.0103', '16.0103', null, null, 599, 'Language Interpretation and Translation.', 'kuali.enum.type.cip2000',  'E18FF0F1F23A42C48B387EFE13144C4E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5fe34907-911d-4fe8-9d1f-2febfbff616d', '16.0104', '16.0104', null, null, 600, 'Comparative Literature.', 'kuali.enum.type.cip2000',  'DB79978EC3B14F4A965A898FF1A99D4F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('afa737b1-270a-4923-b202-cf7f0ab7a947', '16.0199', '16.0199', null, null, 601, 'Linguistic, Comparative, and Related Language Studies and Services, Other.',  'kuali.enum.type.cip2000', 'F0FC225BF97747A9A5EC64641904CB88', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b074f32a-fd5b-4306-9a0b-66d6e6baae50', '16.02', '16.02', null, null, 602, 'African Languages, Literatures, and Linguistics.', 'kuali.enum.type.cip2000',  'E51301F4D93D42F399D4C8D705FBC578', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3b82e50a-1cfa-44c1-89a2-b15b53c4100a', '16.0201', '16.0201', null, null, 603, 'African Languages, Literatures, and Linguistics.', 'kuali.enum.type.cip2000',  '65AE9F5AA0264BA9BF706DA17A7208A3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5ae6e1a8-3d1d-47ff-8750-f422c9e0af9f', '16.03', '16.03', null, null, 604, 'East Asian Languages, Literatures, and Linguistics.', 'kuali.enum.type.cip2000',  '81E1FABDAF2A40048206D632A393C9A4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('537310cf-5592-4b1f-8e3c-73226145186e', '16.0300', '16.0300', null, null, 605, 'East Asian Languages, Literatures, and Linguistics, General.',  'kuali.enum.type.cip2000', 'F098A9D217444F05BB7EA73B901E3018', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6f403a44-4c34-413a-9ca9-1d4c9664e11d', '16.0301', '16.0301', null, null, 606, 'Chinese Language and Literature.', 'kuali.enum.type.cip2000',  'FFCA0503CC9D4DF09A2B5145B2E1A6AA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('236cefa7-8a7d-4ed9-8e98-34ec94b98d46', '16.0302', '16.0302', null, null, 607, 'Japanese Language and Literature.', 'kuali.enum.type.cip2000',  'A4882EDB33B64FF7AEAF9AA2C6B2BFF8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b92dbc49-555b-4073-a5d0-d7c3b75b667f', '16.0303', '16.0303', null, null, 608, 'Korean Language and Literature.', 'kuali.enum.type.cip2000',  'CF6C8C3D0B7E4B5EB4DB37384A68B4F9', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9e1e80bf-4e43-4f8b-8b5f-f7108cf24eec', '16.0304', '16.0304', null, null, 609, 'Tibetan Language and Literature.', 'kuali.enum.type.cip2000',  '25B584B7DC0E4ED99303A9F126A8896B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('914960dd-c4b2-46d1-b392-e6cb96a39b03', '16.0399', '16.0399', null, null, 610, 'East Asian Languages, Literatures, and Linguistics, Other.',  'kuali.enum.type.cip2000', '9E4A67512CA94901A08065BC5A221005', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bfe48c1e-805c-49f6-9cf5-2d5e8fe0940e', '16.04', '16.04', null, null, 611, 'Slavic, Baltic and Albanian Languages, Literatures, and Linguistics.',  'kuali.enum.type.cip2000', '3C8889A2EABA45199C3431D6EAB150C4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('83741fd1-c861-4ccb-bafb-4ec475878842', '16.0400', '16.0400', null, null, 612, 'Slavic Languages, Literatures, and Linguistics, General.',  'kuali.enum.type.cip2000', '6CDD54A4071D4CB68250E2D217B0AFB8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8de662e6-b477-4d7a-a72d-1b3b0d28affa', '16.0401', '16.0401', null, null, 613, 'Baltic Languages, Literatures, and Linguistics.', 'kuali.enum.type.cip2000',  'F96732D7CFC84A2B8E74258CDD074EE2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('600e53fd-1e83-446c-860e-85c9dd78e7e0', '16.0402', '16.0402', null, null, 614, 'Russian Language and Literature.', 'kuali.enum.type.cip2000',  '669A3D3156EE40B8848BDA9602B79178', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('503bc3cc-148f-4b2a-a01f-249f0e897e10', '16.0403', '16.0403', null, null, 615, 'Slavic Languages and Literatures (Other Than Russian).',  'kuali.enum.type.cip2000', 'B3DB24B773914F8EA78DD1997695B143', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3612e2e3-48a0-48e4-b27e-bd7114a1d983', '16.0404', '16.0404', null, null, 616, 'Albanian Language and Literature.', 'kuali.enum.type.cip2000',  '4841429D35CA4857A92F378FAF658D32', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0d4fa5ca-9c2d-4605-aee9-2a00ffc0cde6', '16.0405', '16.0405', null, null, 617, 'Bulgarian Language and Literature.', 'kuali.enum.type.cip2000',  'E5F0C6BBAFAF45228AB53DA148316893', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1decef1a-2266-435b-acfb-c6ed408c4061', '16.0406', '16.0406', null, null, 618, 'Czech Language and Literature.', 'kuali.enum.type.cip2000',  '4F5FCC6B3591405ABAEDD1C435BC11CA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('da7b5b7f-f8d2-4bcf-b966-73e5a4298ccd', '16.0407', '16.0407', null, null, 619, 'Polish Language and Literature.', 'kuali.enum.type.cip2000',  '319EBEF770734437843F205D54163C61', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b67eecec-d51a-4a57-8a20-34015731a319', '16.0408', '16.0408', null, null, 620, 'Serbian, Croatian, and Serbo-Croatian Languages and Literatures.',  'kuali.enum.type.cip2000', '70EA4352CD6F4F5994C71A826024DEC2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f95672c0-26f5-4aec-b3d0-060d594e4a20', '16.0409', '16.0409', null, null, 621, 'Slovak Language and Literature.', 'kuali.enum.type.cip2000',  'DA2CB4AAA13C4BF6AEA68AB31FA1475F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('29120525-06a4-47df-923b-bc221a506797', '16.0410', '16.0410', null, null, 622, 'Ukrainian Language and Literature.', 'kuali.enum.type.cip2000',  'E74FB18EFFB04BFF8FF81FA6113820C4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('04dbee5b-bc6a-4cc0-b37a-8a07c7b9a76f', '16.0499', '16.0499', null, null, 623, 'Slavic, Baltic, and Albanian Languages, Literatures, and Linguistics, Other.',  'kuali.enum.type.cip2000', '17BEC19D3E464CB3AE60E6B344D97295', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d27d213b-5820-4031-8e5e-ceb7e2f7807c', '16.05', '16.05', null, null, 624, 'Germanic Languages, Literatures, and Linguistics.', 'kuali.enum.type.cip2000',  '7D279F8DF8994C0D8368BF1991725D79', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('abf3d8df-149f-492c-862e-326ce519a036', '16.0500', '16.0500', null, null, 625, 'Germanic Languages, Literatures, and Linguistics, General.',  'kuali.enum.type.cip2000', 'AAFA2F285CCC4840845C7992AB72F430', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('90ba6a3c-024a-484e-9ff8-208b55cabb36', '16.0501', '16.0501', null, null, 626, 'German Language and Literature.', 'kuali.enum.type.cip2000',  'DDE655E5AACC43768B76802F3D39FBCA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('de2ac64c-7419-4580-a062-8bb148cecd38', '16.0502', '16.0502', null, null, 627, 'Scandinavian Languages, Literatures, and Linguistics.',  'kuali.enum.type.cip2000', '1FB5A6587B6745E4894EF9F4C63775EF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1240e766-8511-4370-aa26-c48841c63649', '16.0503', '16.0503', null, null, 628, 'Danish Language and Literature.', 'kuali.enum.type.cip2000',  '1B472B6C0C4840EFB06680BF63B4A98B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('fccd6295-3fd6-4248-b943-92f0e81b1280', '16.0504', '16.0504', null, null, 629, 'Dutch/Flemish Language and Literature.', 'kuali.enum.type.cip2000',  'BCCFC97702CD49E787462AF3E851D96B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a2aa095c-c93e-4c4a-87d3-dc7eaa05c5f2', '16.0505', '16.0505', null, null, 630, 'Norwegian Language and Literature.', 'kuali.enum.type.cip2000',  '28E195F115104909A9B2FEE80260EF7B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f6f0ed3b-a123-4eba-b21b-bd0104cee08a', '16.0506', '16.0506', null, null, 631, 'Swedish Language and Literature.', 'kuali.enum.type.cip2000',  '72B4C8E292E7412FAAC2B99F7F9C66A5', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4530397b-83e0-43a3-a3b2-35e3e90bca01', '16.0599', '16.0599', null, null, 632, 'Germanic Languages, Literatures, and Linguistics, Other.',  'kuali.enum.type.cip2000', '3E2F7D07C67241F687F4F1A0F0A0F9F3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('79c41430-334d-46e6-9ee3-72346045a93e', '16.06', '16.06', null, null, 633, 'Modern Greek Language and Literature.', 'kuali.enum.type.cip2000',  'B5B4333F1EE54FFE9FD93DF2AF681F00', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('745d3c64-18e7-4916-a3fb-aab368c95745', '16.0601', '16.0601', null, null, 634, 'Modern Greek Language and Literature.', 'kuali.enum.type.cip2000',  '24BA8FD421B846B9BDC915EE51F2AA3B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('56d4ba95-308a-43b6-a2b0-d8645324871c', '16.07', '16.07', null, null, 635, 'South Asian Languages, Literatures, and Linguistics.', 'kuali.enum.type.cip2000',  'EC8FF0551BD44775A253C47EEEEC11C9', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d492ef0e-ef89-4177-b385-8cf28eeb1d31', '16.07 S', '16.07 S', null, null, 636, null, 'kuali.enum.type.cip2000', '6896413263E9475F84C58288DF0BAA71', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('57a8ce18-3e63-44a1-bbc5-a922d22e8fda', '16.0700', '16.0700', null, null, 637, 'South Asian Languages, Literatures, and Linguistics, General.',  'kuali.enum.type.cip2000', '83795091597B45319531C318C04DD1A4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c55d4116-d8ab-4fb0-a7bc-d6c3a75c9fec', '16.0701', '16.0701', null, null, 638, 'Hindi Language and Literature.', 'kuali.enum.type.cip2000',  'B2EB65A505FC433E992AA533CB9311AD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ea23aa3e-0476-4277-982b-22f95357028b', '16.0702', '16.0702', null, null, 639, 'Sanskrit and Classical Indian Languages, Literatures, and Linguistics.',  'kuali.enum.type.cip2000', 'C1DC717614DE4AA597D2CF41DBE538D0', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5595d951-4b19-4917-80a7-900a04037ac0', '16.0703', '16.0703', null, null, 640, 'South Asian Languages, Literatures, and Linguistics.',  'kuali.enum.type.cip2000', '849B7675774343F39DC4BB616D462E6D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2fdf6361-757c-476e-97d2-39fcb440ff3e', '16.0704', '16.0704', null, null, 641, 'Bengali Language and Literature.', 'kuali.enum.type.cip2000',  'ACBF2694835C4ED585D7A441233A06F2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0738e342-bb1c-4e06-9066-e0148b2321bc', '16.0705', '16.0705', null, null, 642, 'Punjabi Language and Literature.', 'kuali.enum.type.cip2000',  'D05680E6144E4237A10408CD75DB8B89', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1606e9b5-d9c9-443d-b9c3-f1f99b017526', '16.0706', '16.0706', null, null, 643, 'Tamil Language and Literature.', 'kuali.enum.type.cip2000',  '0621EF44F9104F8E884AC3C88AC3BF19', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e1390b7f-98fd-4f79-98c8-29a241dd5554', '16.0707', '16.0707', null, null, 644, 'Urdu Language and Literature.', 'kuali.enum.type.cip2000',  'B0194EEBDEE0449F88C6F368937E5025', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ab8d4691-5294-4a48-ab5e-ae0ff4316bed', '16.0799', '16.0799', null, null, 645, 'South Asian Languages, Literatures, and Linguistics, Other.',  'kuali.enum.type.cip2000', '4807EA0B71D54D29889441B74419ACF4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6ff0c417-b97f-4424-9118-eda77238d041', '16.08', '16.08', null, null, 646, 'Iranian/Persian Languages, Literatures, and Linguistics.',  'kuali.enum.type.cip2000', '447D8FD6C1454FCCAF8FE64A90059CBE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9645bdcf-d56c-42a1-9a0b-45067b808ddb', '16.0801', '16.0801', null, null, 647, 'Iranian/Persian Languages, Literatures, and Linguistics.',  'kuali.enum.type.cip2000', 'B9283F4CB69E4820A293FB0E1D6990AA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6a26f692-fb50-4060-92e8-b2449ec19847', '16.09', '16.09', null, null, 648, 'Romance Languages, Literatures, and Linguistics.', 'kuali.enum.type.cip2000',  '5BA414649643464BB59CA59EF9C29984', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c464d39f-bb1e-4e05-978c-2640d48e6382', '16.0900', '16.0900', null, null, 649, 'Romance Languages, Literatures, and Linguistics, General.',  'kuali.enum.type.cip2000', '09409B77BFD741FCAED54B5FE8E0E117', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d2383719-d3aa-4c91-9147-43e362bd943e', '16.0901', '16.0901', null, null, 650, 'French Language and Literature.', 'kuali.enum.type.cip2000',  '466E46E7D46B47199839A9D4FB25CD85', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('dca5cd07-4056-4ec5-b5cd-6e6a8f62161f', '16.0902', '16.0902', null, null, 651, 'Italian Language and Literature.', 'kuali.enum.type.cip2000',  'EEE78157240749038DDCE6F578689C05', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0549d8bb-8e46-408f-ab46-3db38366671f', '16.0904', '16.0904', null, null, 652, 'Portuguese Language and Literature.', 'kuali.enum.type.cip2000',  '0F949FA44EA141259A1408E31A901565', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9eabfb4e-7e0d-4a11-b16e-9daa5d3fccb3', '16.0905', '16.0905', null, null, 653, 'Spanish Language and Literature.', 'kuali.enum.type.cip2000',  'C4A9E15F8A9341C4BBC7D919FDD11DDE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3f9b09d8-22c2-4adc-ad77-d4d842749a82', '16.0906', '16.0906', null, null, 654, 'Romanian Language and Literature.', 'kuali.enum.type.cip2000',  '9DF9328737984410BF2CC3C80D386EF2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('fb0e7272-9d49-4ab8-b36b-7175c060b52b', '16.0907', '16.0907', null, null, 655, 'Catalan Language and Literature.', 'kuali.enum.type.cip2000',  '242FF07737324C248D0772DCCC4E0012', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d20b9c72-f73c-4736-b9e5-5335257d738a', '16.0999', '16.0999', null, null, 656, 'Romance Languages, Literatures, and Linguistics, Other.',  'kuali.enum.type.cip2000', 'C4397680E7254DDA803C5A541125AA99', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b2c23060-435a-434b-bba5-09f13af0259f', '16.10', '16.10', null, null, 657, 'American Indian/Native American Languages, Literatures, and Linguistics.',  'kuali.enum.type.cip2000', 'A56249A0FE834900AADE00A8F1A2AE81', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4be26942-c719-474c-b013-5e60eb9b8e66', '16.1001', '16.1001', null, null, 658, 'American Indian/Native American Languages, Literatures, and Linguistics.',  'kuali.enum.type.cip2000', '4EB868853E924F8B8FC6D197D7395DA9', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('dd88143b-29b5-4687-989b-a90065c3ebc0', '16.11', '16.11', null, null, 659, 'Middle/Near Eastern and Semitic Languages, Literatures, and Linguistics.',  'kuali.enum.type.cip2000', '7C872A83E4BC4AF59FB2432AE436D744', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e69511cc-f1dc-4522-876e-483138dd971e', '16.1100', '16.1100', null, null, 660, 'Semitic Languages, Literatures, and Linguistics, General.',  'kuali.enum.type.cip2000', 'CDB23F7DA4F04F11A1AD0DBAFECD1C41', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('abf3fc92-4b8e-499c-b8ac-acc6a0e05784', '16.1101', '16.1101', null, null, 661, 'Arabic Language and Literature.', 'kuali.enum.type.cip2000',  '97D12116C2B948C4B26ADDD5D55A06CA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f834157a-c274-47d9-adf2-9f21dd34ebbf', '16.1102', '16.1102', null, null, 662, 'Hebrew Language and Literature.', 'kuali.enum.type.cip2000',  '8BF5B9A1F8C34CBDAC81BD2AA25BB98E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1f3d7e39-7345-4520-a324-f1df93583ece', '16.1103', '16.1103', null, null, 663, 'Ancient Near Eastern and Biblical Languages, Literatures, and Linguistics.',  'kuali.enum.type.cip2000', '2B4DE992D5EA4E7BA4D83B16E26A859A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4b1f6669-d164-4f8f-84b0-b08378e7fc05', '16.1199', '16.1199', null, null, 664, 'Middle/Near Eastern and Semitic Languages, Literatures, and Linguistics,  Other.', 'kuali.enum.type.cip2000', '1033C611A6324B169A0E513FDE5D5F28', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('68333969-47f6-490f-bea4-72c6d5cecfde', '16.12', '16.12', null, null, 665, 'Classics and Classical Languages, Literatures, and Linguistics.',  'kuali.enum.type.cip2000', '5F9E28970BBC49E693124106A07077BE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7e0ed06d-0c09-4b1f-abbb-5aabc2b85685', '16.1200', '16.1200', null, null, 666, 'Classics and Classical Languages, Literatures, and Linguistics, General.',  'kuali.enum.type.cip2000', '5BDF4EB45F7D4D909354C7021B4D1550', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e6937fb6-ea5c-4bdf-977a-66c0f102dd8a', '16.1201', '16.1201', null, null, 667, 'Classics and Classical Languages and Literatures.', 'kuali.enum.type.cip2000',  'DCF97E4A7D5A4486BCA340673F59D248', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c96923ee-8b08-4ef7-b1b1-d99e0e50c185', '16.1202', '16.1202', null, null, 668, 'Ancient/Classical Greek Language and Literature.', 'kuali.enum.type.cip2000',  '42AD5EBCE9524C6A953D57C0AFDC6CBE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('99aeee48-fd79-45bd-b791-e3a8e1e94ea7', '16.1203', '16.1203', null, null, 669, 'Latin Language and Literature.', 'kuali.enum.type.cip2000',  '258FF6FC7E4D4F608D1B1E77919D6BAC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7962630c-bd2e-4830-bdea-547c36055963', '16.1299', '16.1299', null, null, 670, 'Classics and Classical Languages, Literatures, and Linguistics, Other.',  'kuali.enum.type.cip2000', '640706ED844244DD846C8DEDD94B8224', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3cc769fc-8d6d-4e4b-89e2-ba3aee90dd1d', '16.13', '16.13', null, null, 671, 'Celtic Languages, Literatures, and Linguistics.', 'kuali.enum.type.cip2000',  '6417B389EDF948D392F7008E78556A46', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ef624837-86d2-4db1-a316-08f9a8987cc4', '16.1301', '16.1301', null, null, 672, 'Celtic Languages, Literatures, and Linguistics.', 'kuali.enum.type.cip2000',  '3CB90DDD7897487B8ED524CED52E6884', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a1526322-865e-4501-aec9-1cdb16bcbb91', '16.14', '16.14', null, null, 673, 'Southeast Asian and Australasian/Pacific Languages, Literatures, and Linguistics.',  'kuali.enum.type.cip2000', 'C4546D4A5EA4477D8175D0EB32A784DD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4a95280e-b87d-4e9d-8bd2-07b2ba20c769', '16.1400', '16.1400', null, null, 674, 'Southeast Asian Languages, Literatures, and Linguistics, General.',  'kuali.enum.type.cip2000', 'FA3884850BF94CC8AFAC2325AA6461E8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ed33c38c-98fa-4b65-a488-e79cb19da550', '16.1401', '16.1401', null, null, 675, 'Australian/Oceanic/Pacific Languages, Literatures, and Linguistics.',  'kuali.enum.type.cip2000', '57C0FFCF76B2433BA827979D550B8435', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6f710700-aced-415f-a35a-7f56bf8e3be5', '16.1402', '16.1402', null, null, 676, 'Bahasa Indonesian/Bahasa Malay Languages and Literatures.',  'kuali.enum.type.cip2000', '9D00E41C81564E77BA1552AC2E5D9FB0', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b315efba-91f1-4939-9bb3-bfe4c1df3a76', '16.1403', '16.1403', null, null, 677, 'Burmese Language and Literature.', 'kuali.enum.type.cip2000',  'CFE4A82C774F4036903E73D41D69F34D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('afa934e4-e7d6-428e-b7da-3c87f1b9a76a', '16.1404', '16.1404', null, null, 678, 'Filipino/Tagalog Language and Literature.', 'kuali.enum.type.cip2000',  '2E7A481585C44AADA5AA86BCA8EEDCB8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('83183451-e5da-4dcd-bd6e-bcbbd974f867', '16.1405', '16.1405', null, null, 679, 'Khmer/Cambodian Language and Literature.', 'kuali.enum.type.cip2000',  'D006EE14BD454B098C05E34B050DDD85', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bd8a01da-8d53-4b69-b85b-eec543401e92', '16.1406', '16.1406', null, null, 680, 'Lao/Laotian Language and Literature.', 'kuali.enum.type.cip2000',  '80DA748ABAAF4D98BCDF70E63C1577F1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('484d626e-e9a2-4d8d-ba45-27cdd399429e', '16.1407', '16.1407', null, null, 681, 'Thai Language and Literature.', 'kuali.enum.type.cip2000',  '42CE32F1CBF649048F2C5602F4F07148', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2d2a494d-3985-4997-82fe-32777695e7ea', '16.1408', '16.1408', null, null, 682, 'Vietnamese Language and Literature.', 'kuali.enum.type.cip2000',  'C45D450C1EFB4AB4A052BFDE383C76AD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5d103e24-a277-491b-bbea-be624095b9db', '16.1499', '16.1499', null, null, 683, 'Southeast Asian and Australasian/Pacific Languages, Literatures, and  Linguistics, Other.', 'kuali.enum.type.cip2000', 'F35885B85D6241688C8FB531FEC852B4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c669d408-d513-421a-a310-f6ac1aecabdb', '16.15', '16.15', null, null, 684, 'Turkic, Ural-Altaic, Caucasian, and Central Asian Languages, Literatures, and  Lingustics.', 'kuali.enum.type.cip2000', '2664AAA8E7254750B3F4B89B31146160', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('21c0e672-8124-4923-aa06-c376729eeccb', '16.1501', '16.1501', null, null, 685, 'Turkish Language and Literature.', 'kuali.enum.type.cip2000',  '5F01E76C04064545B1D9F33DD7215224', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2e956755-f337-4c48-b998-338df0bee800', '16.1502', '16.1502', null, null, 686, 'Finnish and Related Languages, Literatures, and Linguistics.',  'kuali.enum.type.cip2000', '093F48681E8848B29CC98A80638D8D8E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1049e7b5-daaf-4ae0-a01f-12ee64bff3a5', '16.1503', '16.1503', null, null, 687, 'Hungarian/Magyar Language and Literature.', 'kuali.enum.type.cip2000',  'C4A6CCBB4F9942C3862C5E5D146F48CF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('706f77e2-d2ae-4fd1-a646-d72bad29fd02', '16.1504', '16.1504', null, null, 688, 'Mongolian Language and Literature.', 'kuali.enum.type.cip2000',  '3B6CD06A5CF74D039057298A4B8836FC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('432264c1-0a30-4167-9a4d-11a03b73bf22', '16.1599', '16.1599', null, null, 689, 'Turkic, Ural-Altaic, Caucasian, and Central Asian Languages, Literatures, and  Linguistics, Other.', 'kuali.enum.type.cip2000', '553856C7CDE7493CB25CAD409A4B8BFA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('65ced35d-8df1-4930-b980-2d59136d00a4', '16.16', '16.16', null, null, 690, 'American Sign Language.', 'kuali.enum.type.cip2000',  '21BFBAF80FAB4551A8BFB89D2A7A663C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bbe38ca7-4203-4924-be22-5146e351cb88', '16.1601', '16.1601', null, null, 691, 'American Sign Language (ASL).', 'kuali.enum.type.cip2000',  'E888B61EEDE64BF4A726E8D62DF0B744', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0040baf9-ce46-4793-b91a-f9d2e1695991', '16.1602', '16.1602', null, null, 692, 'Linguistics of ASL and Other Sign Languages.', 'kuali.enum.type.cip2000',  'FDB6EEBB10DA4E55959E3EAF92DB1183', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('dfff0166-88fe-4fd0-820f-e0dc69a559d4', '16.1603', '16.1603', null, null, 693, 'Sign Language Interpretation and Translation.', 'kuali.enum.type.cip2000',  '5AAD1BF9E853429796C04283588752A1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0cf22600-00b5-41a8-9497-a8514f236f4d', '16.1699', '16.1699', null, null, 694, 'American Sign Language, Other.', 'kuali.enum.type.cip2000',  'D7EE4DE5AB55481AB101022AF22CDF34', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('359c6486-9fad-4ac0-b487-c113b2341d12', '16.99', '16.99', null, null, 695, 'Foreign Languages, Literatures, and Linguistics, Other.',  'kuali.enum.type.cip2000', '9401A87DCBEC4C6F85959CB32172FF1D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('458e5bfa-7164-4399-8566-152200d67a1d', '16.9999', '16.9999', null, null, 696, 'Foreign Languages, Literatures, and Linguistics, Other.',  'kuali.enum.type.cip2000', 'F2583916D8894004B646841AC4AE675B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e8ef5876-d5ba-41aa-9247-11a4a566f3ba', '19.', '19.', null, null, 697, 'FAMILY AND CONSUMER SCIENCES/HUMAN SCIENCES.', 'kuali.enum.type.cip2000',  '7559AE8D0E1B4C4B85FF1713D8A2ED55', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d31836bd-bc0a-4be8-b600-f66fd845092c', '19.00', '19.00', null, null, 698, 'Work and Family Studies.', 'kuali.enum.type.cip2000',  'A74F204888C34AB4976FF2B880D6B652', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0906a1ac-10e2-42e8-bb56-c4f26e833d00', '19.0000', '19.0000', null, null, 699, 'Work and Family Studies.', 'kuali.enum.type.cip2000',  '18B00C3A36724BE99C9226B3BE18D6AE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c267abac-489b-4482-bc3a-adb9a372a3d4', '19.01', '19.01', null, null, 700, 'Family and Consumer Sciences/Human Sciences, General.', 'kuali.enum.type.cip2000',  'F724BE6194D346FEA27AAFFE196F07D1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b8a4aaa4-4ccb-482b-ac7b-312654d172ad', '19.0101', '19.0101', null, null, 701, 'Family and Consumer Sciences/Human Sciences, General.',  'kuali.enum.type.cip2000', 'DFE33C28DA0847D98A0B482E53BBE04B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('cbf2f49e-27ab-4ac5-a3c7-ba464a07b9a0', '19.02', '19.02', null, null, 702, 'Family and Consumer Sciences/Human Sciences Business Services.',  'kuali.enum.type.cip2000', '09C98E21255E4BC291CEBD85816AAB69', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('60ffb331-1489-4bd3-80dd-625bd0adfab1', '19.0201', '19.0201', null, null, 703, 'Business Family and Consumer Sciences/Human Sciences.',  'kuali.enum.type.cip2000', 'DE7E53DABA874EEA961251990D372241', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4550878d-19cb-4783-a449-0611e5777500', '19.0202', '19.0202', null, null, 704, 'Family and Consumer Sciences/Human Sciences Communication.',  'kuali.enum.type.cip2000', '4770CB48D3BC42668CBDB2A476911F2E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3d7a54e0-40a5-4bcf-92d6-4167e00e5610', '19.0203', '19.0203', null, null, 705, 'Consumer Merchandising/Retailing Management.', 'kuali.enum.type.cip2000',  '6206C8A467234D168CE3E24A0056C48A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ba34c7e8-02b0-4a78-87c0-515fa58e7636', '19.0299', '19.0299', null, null, 706, 'Family and Consumer Sciences/Human Sciences Business Services, Other.',  'kuali.enum.type.cip2000', 'A7D6305BF7F745D88EBF65C46443F357', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('22a6bd57-5aa1-4e29-bc17-7145fa600d47', '19.03', '19.03', null, null, 707, 'Family and Community Studies.', 'kuali.enum.type.cip2000',  '48DAC634D49642478EFF9268A41C4027', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('cec0798a-d1a3-45a0-87ff-88c9ee53f28a', '19.0301', '19.0301', null, null, 708, 'Family and Community Studies.', 'kuali.enum.type.cip2000',  'E234A0A2656C43B395C3E8A09E9CF83B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3a2f0693-4784-4ce5-841c-8923ed947f36', '19.04', '19.04', null, null, 709, 'Family and Consumer Economics and Related Studies.', 'kuali.enum.type.cip2000',  'C4EDD8240CF545D88337EE137732503A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1fc59f47-042f-44d1-b264-a61088f54796', '19.0401', '19.0401', null, null, 710, 'Family Resource Management Studies, General.', 'kuali.enum.type.cip2000',  '133F2BB991CE4C76874A21070951DF6A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d84238c9-d36a-431c-90fa-47bad4ee803d', '19.0402', '19.0402', null, null, 711, 'Consumer Economics.', 'kuali.enum.type.cip2000',  'C690F64DD8C745BC9BA0BD53E40B817F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e0c73078-4e40-47b9-911d-c10ca008ccf1', '19.0403', '19.0403', null, null, 712, 'Consumer Services and Advocacy.', 'kuali.enum.type.cip2000',  'F6817C5A25F643088019ED4849479315', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a3f5d070-6ca3-4725-ab1d-983e865dbbfc', '19.0499', '19.0499', null, null, 713, 'Family and Consumer Economics and Related Services, Other.',  'kuali.enum.type.cip2000', '9E820AF4AD044CDDB54808DD150A3517', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('fcdce500-a325-4545-80f6-7e0097d84241', '19.05', '19.05', null, null, 714, 'Foods, Nutrition, and Related Services.', 'kuali.enum.type.cip2000',  '3F1C23DE56A74FA2A227F9A00CDABBCE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ab9aca35-ca1a-4278-b86d-98957a744775', '19.0501', '19.0501', null, null, 715, 'Foods, Nutrition, and Wellness Studies, General.', 'kuali.enum.type.cip2000',  '846AD2AA957F4D2E98A2657C12D1E42A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3cdf9655-277a-432b-acf7-9083d1e0c7b1', '19.0502', '19.0502', null, null, 716, 'Foods and Nutrition Science.', 'kuali.enum.type.cip2000',  '9A107477BE2340A69DE5D7ACFE79B2B2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('07352900-a564-4e58-b371-8a2fc68a480a', '19.0503', '19.0503', null, null, 717, 'Dietetics/Human Nutritional Services.', 'kuali.enum.type.cip2000',  '6229D49E551447F8B4203E5096DA573F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c669a44a-a1c9-4009-9324-e575d9ad0b98', '19.0504', '19.0504', null, null, 718, 'Human Nutrition.', 'kuali.enum.type.cip2000',  '6DF0D47007D54EB59FF93986E42FAD20', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('28ebec3a-c852-4890-9a66-338b0563ab70', '19.0505', '19.0505', null, null, 719, 'Foodservice Systems Administration/Management.', 'kuali.enum.type.cip2000',  '6E5FEC1AE1D74A72B3E535034705D8AC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b6b6f4e6-81c2-4ddd-a09c-032df5b9391e', '19.0599', '19.0599', null, null, 720, 'Foods, Nutrition, and Related Services, Other.', 'kuali.enum.type.cip2000',  'F8E86D3A7B344B13ADB68298FC01EB54', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b843d2cd-5162-4f0c-97fb-01e1076dc9ec', '19.06', '19.06', null, null, 721, 'Housing and Human Environments.', 'kuali.enum.type.cip2000',  '304D31D03AC444A5B9ECF0A1B2EAEBB8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('99bfffc4-feb6-4afa-a0ce-77bad184c01b', '19.0601', '19.0601', null, null, 722, 'Housing and Human Environments, General.', 'kuali.enum.type.cip2000',  '13DBAEE9C44742EA92AD93C924839C9E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('367b3666-39f4-4d60-9a61-c63e2526a4f8', '19.0603', '19.0603', null, null, 723, 'Interior Environments.', 'kuali.enum.type.cip2000',  'C463266648E648AE976D393BD5399601', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('04feb0ad-9e81-4671-b044-3e022521081d', '19.0604', '19.0604', null, null, 724, 'Facilities Planning and Management.', 'kuali.enum.type.cip2000',  'E5036E9F180E410692EB0C6CBDA11D6B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e1e9a605-8b9a-4f51-b483-14c359f03e23', '19.0605', '19.0605', null, null, 725, 'Home Furnishings and Equipment Installers.', 'kuali.enum.type.cip2000',  'A03C99B2CB334A6BB12DA6D7BB2AE9B2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4d9c0756-8a41-4d27-9eb7-f6aaa06d58e3', '19.0699', '19.0699', null, null, 726, 'Housing and Human Environments, Other.', 'kuali.enum.type.cip2000',  '355BF46EDB6C422993310A4A82F3C155', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3c94f10a-233d-4385-a3cd-fc6679ed11ab', '19.07', '19.07', null, null, 727, 'Human Development, Family Studies, and Related Services.',  'kuali.enum.type.cip2000', '30F16EEB1D1A42C19DE5F3F74AB6F07F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3ace688e-4c34-4d47-b95c-6b783b99d4ff', '19.0701', '19.0701', null, null, 728, 'Human Development and Family Studies, General.', 'kuali.enum.type.cip2000',  'E18A199EC8384B768DA27B11972D35DE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('839eccba-fb72-49cd-98de-d90957b95049', '19.0702', '19.0702', null, null, 729, 'Adult Development and Aging.', 'kuali.enum.type.cip2000',  'A5C153E15AFA49C885A638441E15BDBE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('49a00414-38ce-4d99-a9af-58cd7c00a592', '19.0703', '19.0703', null, null, 730, 'Family and Marriage Counseling.', 'kuali.enum.type.cip2000',  '02AAB81F05F24D2C9AAFD2987BFFC43A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bea6dd3e-06ba-491c-ba19-9bba6e59c642', '19.0704', '19.0704', null, null, 731, 'Family Systems.', 'kuali.enum.type.cip2000',  '992D2BE16D8E431E912761503DE634AB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5c1a7303-dc18-471f-bb79-3888dd34c92d', '19.0705', '19.0705', null, null, 732, 'Gerontological Services.', 'kuali.enum.type.cip2000',  '5FAA58ECB62646358383A7D66E103316', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0ae320e6-99fa-4c1f-b416-525524a544b3', '19.0706', '19.0706', null, null, 733, 'Child Development.', 'kuali.enum.type.cip2000',  'B955EF1BD0DA4FDC8DF1BA9EB7E29BAA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3f201bdc-3ae6-4c5e-aeb8-e2e029d2c3fc', '19.0707', '19.0707', null, null, 734, 'Family and Community Services.', 'kuali.enum.type.cip2000',  '8E1FD2A88C064772B02A5D8077F2D666', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('10b2e034-37a0-4474-ad0c-2ac0ffd2a438', '19.0708', '19.0708', null, null, 735, 'Child Care and Support Services Management.', 'kuali.enum.type.cip2000',  '88BEC8DCF07740F99C221A614D26C4DA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('03b3b2aa-b665-4be2-95ca-50e039859876', '19.0709', '19.0709', null, null, 736, 'Child Care Provider/Assistant.', 'kuali.enum.type.cip2000',  '724818207A2F44B5ACAFF8A66A6F9F8C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('dc9d65ed-ccbd-485d-a57d-3b2d0fb6a7c4', '19.0799', '19.0799', null, null, 737, 'Human Development, Family Studies, and Related Services, Other.',  'kuali.enum.type.cip2000', 'FB3EEBB444CF49D497CF4445B2BEB576', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('03b98b83-30b6-4e9a-bd27-51e16a9d359a', '19.09', '19.09', null, null, 738, 'Apparel and Textiles.', 'kuali.enum.type.cip2000',  'BA6F392C223E40EA81ADD04D9C748E49', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('550593f6-9332-4e6b-b33b-dda68bdeeb73', '19.0901', '19.0901', null, null, 739, 'Apparel and Textiles, General.', 'kuali.enum.type.cip2000',  'C72C1DC8E0D3463298D4224549E8F1C2', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e5313de0-ec70-4672-a513-d9235cd3f6a0', '19.0902', '19.0902', null, null, 740, 'Apparel and Textile Manufacture.', 'kuali.enum.type.cip2000',  '8B22CB0B56F84192BF37C11A6A6F1F2B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('46c10c83-b515-4ecc-b83e-d94943e2c856', '19.0904', '19.0904', null, null, 741, 'Textile Science.', 'kuali.enum.type.cip2000',  '1530932EDF7E400A85BC75D6A642A269', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('77167e41-9f6b-43dd-87c8-e7b88189ec19', '19.0905', '19.0905', null, null, 742, 'Apparel and Textile Marketing Management.', 'kuali.enum.type.cip2000',  'AD45EEE7FD834A4AB547F18066812FF7', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6280645b-a404-4fc4-b386-256dcb34d1af', '19.0906', '19.0906', null, null, 743, 'Fashion and Fabric Consultant.', 'kuali.enum.type.cip2000',  '9859767973634B4682DAEA5D0D0E0525', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('22f6943b-ee06-446b-b5f0-8aef25733a42', '19.0999', '19.0999', null, null, 744, 'Apparel and Textiles, Other.', 'kuali.enum.type.cip2000',  '2699C7E184E04D7C9EB8EFA897A449A6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2e24f2de-6e47-436d-95f5-367af76dcb4e', '19.99', '19.99', null, null, 745, 'Family and Consumer Sciences/Human Sciences, Other.', 'kuali.enum.type.cip2000',  '08B1E728062D4B04A12E1E257D780849', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('684e6ae4-43ff-4e87-affc-d2383e622049', '199999', '199999', null, null, 746, 'Family and Consumer Sciences/Human Sciences, Other.', 'kuali.enum.type.cip2000',  '72A4FA1E45624A6D924DE66B5E9D327A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d53428f0-bac6-4a60-8833-96716bbd1f84', '20.', '20.', null, null, 747, 'VOCATIONAL HOME ECONOMICS.', 'kuali.enum.type.cip2000',  '6243BE431C7C41FBBAF1C5E85B98C640', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('75243a8f-2e09-44be-b47c-d08ede568efb', '20.01', '20.01', null, null, 748, 'CONSUMER AND HOMEMAKING EDUCATION (Non-postsecondary level).',  'kuali.enum.type.cip2000', '4133D2E500CA4425B43D2692182CD335', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d1ed9835-9232-42f8-9c6e-973303b8fb8d', '21.', '21.', null, null, 749, 'TECHNOLOGY EDUCATION/INDUSTRIAL ARTS.', 'kuali.enum.type.cip2000',  '08B2E620EE184813A6023E01DD812A57', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('133e271a-20bf-4389-a2df-e564773cd783', '21.', '21.', null, null, 750, 'Programs for Series 21.', 'kuali.enum.type.cip2000',  'DF7F305946174DCABC486640B95B19C3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('98e1eb7b-7e28-4b28-831d-a684321dedea', '21.01', '21.01', null, null, 751, 'Technology Education/Industrial Arts Programs.', 'kuali.enum.type.cip2000',  '9F999ED5DFF44DEBAC9AD12D1665946F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c42a5be9-1ae0-4dfc-b209-74e2775b8bdc', '21.0101', '21.0101', null, null, 752, 'Technology Education/Industrial Arts.', 'kuali.enum.type.cip2000',  '4B83F71F6E384BAE9B1B1E14C707EE25', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c4726974-0250-4be6-8328-61fd9ddd36bf', '22.', '22.', null, null, 753, 'LEGAL PROFESSIONS AND STUDIES.', 'kuali.enum.type.cip2000',  '9A1F098DEAF34960B754CF9DE751DF19', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('224309eb-f053-470b-8220-f6e8b027994e', '22.00', '22.00', null, null, 754, 'Non-Professional General Legal Studies (Undergraduate).',  'kuali.enum.type.cip2000', 'A7416B04FDF64E278B3FD61856D8A808', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('01f9c57c-12b4-4d37-a371-3df66c297bdd', '22.0000', '22.0000', null, null, 755, 'Legal Studies, General.', 'kuali.enum.type.cip2000',  'A520649076144FDAA6FC6413E7AC6730', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2033b36f-b487-402b-8daa-1f40a3b9adb0', '22.0001', '22.0001', null, null, 756, 'Pre-Law Studies.', 'kuali.enum.type.cip2000',  '76EA3816F18D4FAB9C2DCAE8CFC0E2B4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('78b57db5-37ff-406d-a883-71bdd07ef6de', '22.01', '22.01', null, null, 757, 'Law (LL.', 'kuali.enum.type.cip2000', 'F808AC95F9194E02B01571FE8957EF37', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('62a13571-0e17-4b49-a238-c56fd1d0714f', '22.0101', '22.0101', null, null, 758, 'Law (LL.', 'kuali.enum.type.cip2000', '744FCC12A633416084D7DA459B631A1D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bb840822-e9a5-4aa2-8867-29243d4ae60c', '22.0102', '22.0102', null, null, 759, 'Pre-Law Studies.', 'kuali.enum.type.cip2000',  '31803F32462842D8B890FD9C9C12564A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('2cd66d89-2349-4331-9517-aefdbdab5021', '22.0103', '22.0103', null, null, 760, 'Paralegal/Legal Assistant.', 'kuali.enum.type.cip2000',  'A45890D76DCC4AD7AF301DCFAFD57044', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('12c68f7e-ff5e-4a06-aa84-674dc50d6b76', '22.0104', '22.0104', null, null, 761, 'Judicial Science/Legal Specialization.', 'kuali.enum.type.cip2000',  '33E4F27C8C4C4543BE12F5990AAE4CFF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e115019a-c5fb-4875-ac5e-ed7b8b5742ad', '22.0199', '22.0199', null, null, 762, 'Law and Legal Studies, Other.', 'kuali.enum.type.cip2000',  'EB3014146090443485661FCD9D590553', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('21f36582-b11a-4c32-9b66-7df24ca25309', '22.02', '22.02', null, null, 763, 'Legal Research and Advanced Professional Studies (Post-LL.',  'kuali.enum.type.cip2000', '2E2FADAD6304408DA50F0393690A6A29', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a4c40eba-b09e-4463-876a-4f5165bb92b1', '22.0201', '22.0201', null, null, 764, 'Advanced Legal Research/Studies, General (LL.', 'kuali.enum.type.cip2000',  '466190E7710145A884545EAFCF1009BB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('fbc74c7f-8582-415e-9c2e-19232cb5a6ab', '22.0202', '22.0202', null, null, 765, 'Programs for Foreign Lawyers (LL.', 'kuali.enum.type.cip2000',  '67F1B6357F0F4C71B0410C4240FE8FF7', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8c2aa804-4c90-4ce2-a6ca-84f38a43402a', '22.0203', '22.0203', null, null, 766, 'American/U.', 'kuali.enum.type.cip2000', 'F69C249E9760457A9A13D1F30CBA112F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('634a6d7b-111a-4144-887c-5003bc085260', '22.0204', '22.0204', null, null, 767, 'Canadian Law/Legal Studies/Jurisprudence (LL.', 'kuali.enum.type.cip2000',  'BC7C53A8BAC44DD190A48AD432655151', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4e1755a1-62a0-4853-828c-a99f83b9aed4', '22.0205', '22.0205', null, null, 768, 'Banking, Corporate, Finance, and Securities Law (LL.',  'kuali.enum.type.cip2000', 'EAC033B630F14AD88BFF5D2D56A53B94', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8e027a94-94b1-4f73-b9ad-14bb37383fe8', '22.0206', '22.0206', null, null, 769, 'Comparative Law (LL.', 'kuali.enum.type.cip2000',  '128A9D2911504542BF672E3D39310CEF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7b14d7c4-2c7d-4b74-b5a9-46d39586a62f', '22.0207', '22.0207', null, null, 770, 'Energy, Environment, and Natural Resources Law (LL.',  'kuali.enum.type.cip2000', '5F9A44442CF346D6B54F5717A6ADA6EB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('9d56ce88-a23f-4c1c-8d61-a7da334893d8', '22.0208', '22.0208', null, null, 771, 'Health Law (LL.', 'kuali.enum.type.cip2000',  'D55A93F9D4784BF0A0115D0C459C4BBD', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b20a6a0a-756d-485d-a358-59be9cf2fc87', '22.0209', '22.0209', null, null, 772, 'International Law and Legal Studies (LL.', 'kuali.enum.type.cip2000',  'C439A40A96D44EDCA52D8ADC6ADADD1C', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d8caa5b4-0e04-4ade-830b-a2cc0b181dd7', '22.0210', '22.0210', null, null, 773, 'International Business, Trade, and Tax Law (LL.', 'kuali.enum.type.cip2000',  '6FEAAFA398754CA99E8167E3F6C9B716', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7daf214b-e94d-4d9f-bbb4-0c239789be6b', '22.0211', '22.0211', null, null, 774, 'Tax Law/Taxation (LL.', 'kuali.enum.type.cip2000',  '05DA1902EC464DEE8E05884F7DEC20C1', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('f1d48ecb-b4d7-40cf-b0a7-b3331488b910', '22.0299', '22.0299', null, null, 775, 'Legal Research and Advanced Professional Studies, Other.',  'kuali.enum.type.cip2000', '7AA2F9B5DE4645F9A61A70CFD3F95A63', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('18549c22-d1a1-43e2-aa3e-33b4595fd4d2', '22.03', '22.03', null, null, 776, 'Legal Support Services.', 'kuali.enum.type.cip2000',  '6DBA94C692F4414FB192F0C459048AB3', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e1f2bd29-f28d-415c-ab48-a6ae0b5c2388', '22.0301', '22.0301', null, null, 777, 'Legal Administrative Assistant/Secretary.', 'kuali.enum.type.cip2000',  'C868B6016E5B454A93FE6DDA73CF7BF0', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('757da53f-4d03-4b8e-af63-281f0e2d4edb', '22.0302', '22.0302', null, null, 778, 'Legal Assistant/Paralegal.', 'kuali.enum.type.cip2000',  'B0A227E89120437FBEAC0FB753CC9579', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('49b1680e-f9e9-4a5c-a2a6-b885b9e93740', '22.0303', '22.0303', null, null, 779, 'Court Reporting/Court Reporter.', 'kuali.enum.type.cip2000',  '20EFD77D4AA74E6689D9ABA4716590E8', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4edb440e-5174-4d4a-a16d-8f40d83aa021', '22.0399', '22.0399', null, null, 780, 'Legal Support Services, Other.', 'kuali.enum.type.cip2000',  '550EB2A8149D4EEE96A4FB9B5FEB0D5D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bf91cc2c-425a-4e9a-a527-b4f27d60c089', '22.99', '22.99', null, null, 781, 'Legal Professions and Studies, Other.', 'kuali.enum.type.cip2000',  '416964FBE0EE4CD89FDA5EBDC3CE1436', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c497e1de-7a24-43e2-a7a7-b8d5ca9e2d4c', '22.9999', '22.9999', null, null, 782, 'Legal Professions and Studies, Other.', 'kuali.enum.type.cip2000',  'A578936E76EC41DC8B855A92B368AB8D', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d5c049d0-0492-4ef2-91ca-f42b4b63c1f8', '23.', '23.', null, null, 783, 'ENGLISH LANGUAGE AND LITERATURE/LETTERS.', 'kuali.enum.type.cip2000',  '779B0874646F4396AD129ED8F97EEC7F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c06d62a6-e342-403d-9444-2eef72914c3a', '23.01', '23.01', null, null, 784, 'English Language and Literature, General.', 'kuali.enum.type.cip2000',  '16EC7E7672AC46E18525E330FBBDA8EF', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('bf8cc2d3-0a28-474b-9a91-00c019b60657', '23.0101', '23.0101', null, null, 785, 'English Language and Literature, General.', 'kuali.enum.type.cip2000',  'C4EA9336DD2346A19DAECAEF4FCB46AB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('6942a21e-4def-449b-93c6-b49c30878d18', '23.03', '23.03', null, null, 786, 'Comparative Literature.', 'kuali.enum.type.cip2000',  '48B923AD3B214BBABA175D64A9B82FCE', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('21217539-780b-4388-a0b0-4220c9e026a3', '23.0301', '23.0301', null, null, 787, 'Comparative Literature.', 'kuali.enum.type.cip2000',  '9D8DAA62CF744903B89AB02B4AFD22FC', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e93594c6-e9da-4588-8340-b562c84cb058', '23.04', '23.04', null, null, 788, 'English Composition.', 'kuali.enum.type.cip2000',  '8E87066DB5864B419058BB8E5BE7E50B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('db35b759-caef-4624-b59c-8af7ecced8c2', '23.0401', '23.0401', null, null, 789, 'English Composition.', 'kuali.enum.type.cip2000',  'CF131FB69424470DABAA5CE3D1555749', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4db5a44d-bd65-4858-95dd-5de40064a2e5', '23.05', '23.05', null, null, 790, 'Creative Writing.', 'kuali.enum.type.cip2000', 'C3A6DFDA38084B24AF1F480BA5B10013',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('010847f7-84f5-4f0b-88f7-65396f4db0c8', '23.0501', '23.0501', null, null, 791, 'Creative Writing.', 'kuali.enum.type.cip2000',  '62007CC9B15944C8B96F5B60BE10C834', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c5e456f6-c098-4156-ae28-cc2c9a6ef3eb', '23.07', '23.07', null, null, 792, 'American Literature (United States and Canadian.', 'kuali.enum.type.cip2000',  '03135C74FFA04328AD932A4755709EBA', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d92ec5ac-6a22-44db-bf3b-fb0cc136a014', '23.0701', '23.0701', null, null, 793, 'American Literature (United States).', 'kuali.enum.type.cip2000',  'B3994F28392346178922F992A5C5CE74', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c688196e-6412-4af6-9cf8-9ada66620444', '23.0702', '23.0702', null, null, 794, 'American Literature (Canadian).', 'kuali.enum.type.cip2000',  'D34A38B19EB54629BF34C1C70DFA82E6', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e35fb46b-c69a-4018-9808-34faebf65d15', '23.08', '23.08', null, null, 795, 'English Literature (British and Commonwealth).', 'kuali.enum.type.cip2000',  '6A57B6424F0343BF8A3B602D88DD2243', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ede8c05c-095a-4dc2-a2cc-c3ef622669e4', '23.0801', '23.0801', null, null, 796, 'English Literature (British and Commonwealth).', 'kuali.enum.type.cip2000',  '0625F0A36836422E900CF2A7961C6D98', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('765ee7ab-6fe3-498f-826d-3730f9ecff3d', '23.10', '23.10', null, null, 797, 'Speech and Rhetorical Studies.', 'kuali.enum.type.cip2000',  'F80C605D38DD4E26A0458CBCBAAC5C11', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('385f5828-93c4-47b4-868e-17455eade7bb', '23.1001', '23.1001', null, null, 798, 'Speech and Rhetorical Studies.', 'kuali.enum.type.cip2000',  '7425BAA06C4E4A0EB6B80D7ECD6CE41A', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('606803ff-87aa-43f2-af73-6d0943589dd9', '23.11', '23.11', null, null, 799, 'Technical and Business Writing.', 'kuali.enum.type.cip2000',  '12CB328C1D0E4F4688AFD6D2CCDE5E28', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('974d8294-6d96-4ecd-888c-482ee1deb2b8', '23.1101', '23.1101', null, null, 800, 'Technical and Business Writing.', 'kuali.enum.type.cip2000',  '124EB6A2CA6F40CD8D8C56A39594224F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('70a7a4cd-587f-4d46-b169-d8ab396a5eed', '23.99', '23.99', null, null, 801, 'English Language and Literature/Letters, Other.', 'kuali.enum.type.cip2000',  '31193FC75572464CB0987EE0A568FC8F', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('8645c32f-1b56-4d34-af06-815f314221a8', '23.9999', '23.9999', null, null, 802, 'English Language and Literature/Letters, Other.', 'kuali.enum.type.cip2000',  '845CDA4D68854F5B9B0AE28744667255', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3ddc092d-d858-4288-b64e-c3819fccc7cb', '24.', '24.', null, null, 803, 'LIBERAL ARTS AND SCIENCES, GENERAL STUDIES AND HUMANITIES.', 'kuali.enum.type.cip2000',  '420564B7C2A248F9BC50775C0A01F425', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1ec95408-55bc-4e13-8655-1d7504895b9b', '24.01', '24.01', null, null, 804, 'Liberal Arts and Sciences, General Studies and Humanities.',  'kuali.enum.type.cip2000', 'A0C21EFB7FAB473F8414272C8B5E4049', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('ab58221c-20ee-4e5a-b66e-505a4c2f1b70', '24.0101', '24.0101', null, null, 805, 'Liberal Arts and Sciences/Liberal Studies.', 'kuali.enum.type.cip2000',  '5C3D59A7C61C45DC8FF9220C627515A4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e7e74f48-ee7d-4897-afad-048efc04446e', '24.0102', '24.0102', null, null, 806, 'General Studies.', 'kuali.enum.type.cip2000',  'A7F04CCC427344EEBEC2C8058292B1BB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('1d6862d2-108d-4c21-ac3d-550db5a754e2', '24.0103', '24.0103', null, null, 807, 'Humanities/Humanistic Studies.', 'kuali.enum.type.cip2000',  'B4A3462A833F4B56BA3C8354A116E407', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('c4b54d6f-5b74-47d6-a002-e8bdf15350d5', '24.0199', '24.0199', null, null, 808, 'Liberal Arts and Sciences, General Studies and Humanities, Other.',  'kuali.enum.type.cip2000', 'ACEAA6176C234E119F8519B7774ACD1B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('cd7bb86d-1003-454a-8425-c018661ebbb8', '25.', '25.', null, null, 809, 'LIBRARY SCIENCE.', 'kuali.enum.type.cip2000', '31A05E8CA4584470AA00A918C84D1EF4', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('04453570-4c0a-4ad7-b6b9-cbee869bc2b9', '25.01', '25.01', null, null, 810, 'Library Science/Librarianship.', 'kuali.enum.type.cip2000',  'C7B3BA6C37FD4E178BBC965AC24E3966', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7a47a572-3e31-4f37-acc6-292c03f0153c', '25.0101', '25.0101', null, null, 811, 'Library Science/Librarianship.', 'kuali.enum.type.cip2000',  '4C5FFA3520CF459AB6B6341F9A169779', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('acfc96b8-d23f-453a-bfe1-12100f82ffb8', '25.03', '25.03', null, null, 812, 'Library Assistant.', 'kuali.enum.type.cip2000', '54FEECD471624880B262D5B298C272E6',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('aa295445-ecf1-4a58-bbfc-b615026f6849', '25.0301', '25.0301', null, null, 813, 'Library Assistant/Technician.', 'kuali.enum.type.cip2000',  'CC7ABFD9F4BC44338FAECAD7AF477E22', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('793fe17d-e3cd-4b97-9154-e26661fc2181', '25.99', '25.99', null, null, 814, 'Library Science, Other.', 'kuali.enum.type.cip2000',  '85D74DE2BBB24A4984FE6B98D09BC903', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('87e69123-2c80-456f-9a3e-eb73fd34ba00', '25.9999', '25.9999', null, null, 815, 'Library Science, Other.', 'kuali.enum.type.cip2000',  'A7E24ADFC01444B391E066E698F12D17', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('076a3bf4-c2e3-4b13-9573-4152f100f903', '26.', '26.', null, null, 816, 'BIOLOGICAL AND BIOMEDICAL SCIENCES.', 'kuali.enum.type.cip2000',  '92F51795F95047F2978EC7764D1AA0EB', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0b66b3fd-2a5b-4e53-b1ff-ab397d55f3b0', '26.01', '26.01', null, null, 817, 'Biology, General.', 'kuali.enum.type.cip2000', '762A682220E045F5A1CBE9D66E57166F',  0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('5d5d66ae-c804-4f02-a23b-c90f35dd47d3', '26.0101', '26.0101', null, null, 818, 'Biology/Biological Sciences, General.', 'kuali.enum.type.cip2000',  'A995779EB575431090F6397A902A353B', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('e82c4500-458c-4fba-a6a0-1f5e72e3fceb', '26.0102', '26.0102', null, null, 819, 'Biomedical Sciences, General.', 'kuali.enum.type.cip2000',  'B54791F90FCE47439A51B640013AFD1E', 0)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('0f3dc9d9-c12d-4058-8e94-6bc9a209930a', '26.02', '26.02', null, null, 820, 'Biochemistry, Biophysics and Molecular Biology.', 'kuali.enum.type.cip2000',  '3632B107B77C4F8F943EE7B03CB320AC', 0)
/
