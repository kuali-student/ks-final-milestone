CREATE
    TABLE KSSC_SUBJ_CD_TYPE
    (
        TYPE_KEY VARCHAR2(255) NOT NULL,
        TYPE_DESC VARCHAR2(2000),
        EFF_DT TIMESTAMP(6),
        EXPIR_DT TIMESTAMP(6),
        NAME VARCHAR2(255),
        OBJ_ID VARCHAR2(36),
        VER_NBR NUMBER(19),
        CONSTRAINT KSSC_SUBJ_CD_TYPEP1 PRIMARY KEY (TYPE_KEY)
    )/
   
insert into KSSC_SUBJ_CD_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME, OBJ_ID, VER_NBR) values ('ks.core.subjectcode.usage.all', 'All of', to_date('2000-01-01', 'yyyy-mm-dd'), null, 'All', '1', 0)
/
insert into KSSC_SUBJ_CD_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME, OBJ_ID, VER_NBR) values ('ks.core.subjectcode.usage.one', 'One of', to_date('2000-01-01', 'yyyy-mm-dd'), null, 'One', '2', 0)
/
    