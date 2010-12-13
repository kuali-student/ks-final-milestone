insert into KSEM_ENUM_T (ENUM_KEY, NAME, DESCR, EFF_DT, EXPIR_DT, OBJ_ID, VER_NBR ) values ('kuali.atptype.intensity', 'ATP Intensity', 'ATP Intensity' , to_date('2000-01-01', 'yyyy-mm-dd'), null, SYS_GUID(),0)/

insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, OBJ_ID, VER_NBR ) values ('a8fdd335-ecac-4075-9feb-89266fa6e4d7', 'Full Time', 'kuali.atp.intensity.fulltime', to_date('2000-01-01', 'yyyy-mm-dd'), 'kuali.atptype.intensity', null, 2320, 'Full Time', SYS_GUID(), 0)/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, OBJ_ID, VER_NBR ) values ('0c253cda-8583-4608-9df8-5ae2c22777f6', 'Part Time', 'kuali.atp.intensity.parttime', to_date('2000-01-01', 'yyyy-mm-dd'), 'kuali.atptype.intensity', null, 2321, 'Part Time', SYS_GUID(), 0)/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, OBJ_ID, VER_NBR ) values ('27cac13b-5d76-4d4e-a556-5f485410b959', 'Both', 'kuali.atp.intensity.both', to_date('2000-01-01', 'yyyy-mm-dd'), 'kuali.atptype.intensity', null, 2322, 'Both', SYS_GUID(), 0)/
insert into KSEM_ENUM_VAL_T (CD,ENUM_KEY,ID,OBJ_ID,SORT_KEY,VAL,ABBREV_VAL,VER_NBR) values ('SPEC','kuali.lu.code.UniversityClassification','205b06aa-15e1-4263-ba15-02573cf51040',SYS_GUID(),11,'Program Specialization','Specialization',0)/

