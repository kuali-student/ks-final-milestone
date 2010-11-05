insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, OBJ_ID, VER_NBR ) values ('SUBJAREA61', 'ARHU', 'ARHU', to_date('2000-01-01', 'yyyy-mm-dd'), 'kuali.lu.subjectArea', null, 61, 'Arts and Huanities', SYS_GUID(), 0)/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, OBJ_ID, VER_NBR ) values ('SUBJAREA62', 'BCHM', 'BCHM', to_date('2000-01-01', 'yyyy-mm-dd'), 'kuali.lu.subjectArea', null, 62, 'Bio Chemistry', SYS_GUID(), 0)/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, OBJ_ID, VER_NBR ) values ('SUBJAREA63', 'BIOM', 'BIOM', to_date('2000-01-01', 'yyyy-mm-dd'), 'kuali.lu.subjectArea', null, 63, 'Animal and Behavioral Sciences', SYS_GUID(), 0)/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, OBJ_ID, VER_NBR ) values ('SUBJAREA64', 'BSCI', 'BSCI', to_date('2000-01-01', 'yyyy-mm-dd'), 'kuali.lu.subjectArea', null, 64, 'Biological Sciences', SYS_GUID(), 0)/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, OBJ_ID, VER_NBR ) values ('SUBJAREA65', 'GEMS', 'GEMS', to_date('2000-01-01', 'yyyy-mm-dd'), 'kuali.lu.subjectArea', null, 65, 'Gemstone', SYS_GUID(), 0)/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, OBJ_ID, VER_NBR ) values ('SUBJAREA66', 'HONR', 'HONR', to_date('2000-01-01', 'yyyy-mm-dd'), 'kuali.lu.subjectArea', null, 66, 'Honors', SYS_GUID(), 0)/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, OBJ_ID, VER_NBR ) values ('SUBJAREA67', 'MATH', 'MATH', to_date('2000-01-01', 'yyyy-mm-dd'), 'kuali.lu.subjectArea', null, 67, 'Math', SYS_GUID(), 0)/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, OBJ_ID, VER_NBR ) values ('SUBJAREA68', 'PHYS', 'PHYS', to_date('2000-01-01', 'yyyy-mm-dd'), 'kuali.lu.subjectArea', null, 68, 'Physics', SYS_GUID(), 0)/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, OBJ_ID, VER_NBR ) values ('SUBJAREA69', 'STAT', 'STAT', to_date('2000-01-01', 'yyyy-mm-dd'), 'kuali.lu.subjectArea', null, 69, 'Statistics', SYS_GUID(), 0)/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, OBJ_ID, VER_NBR ) values ('SUBJAREA70', 'UNIV', 'UNIV', to_date('2000-01-01', 'yyyy-mm-dd'), 'kuali.lu.subjectArea', null, 70, 'UNIV', SYS_GUID(), 0)/


CREATE
    INDEX "KSEM_ENUM_VAL_I2" ON "KSEM_ENUM_VAL_T"
    (
        "CD" ASC
    )
    /