DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSSC_SUBJ_CD_TYPE';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSSC_SUBJ_CD_TYPE CASCADE CONSTRAINTS PURGE'; END IF;
END;
/
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
    )
/

insert into KSSC_SUBJ_CD_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME, OBJ_ID, VER_NBR) values ('ks.core.subjectcode.usage.all', 'All of', to_date('2000-01-01', 'yyyy-mm-dd'), null, 'All', '1', 0)
/
insert into KSSC_SUBJ_CD_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME, OBJ_ID, VER_NBR) values ('ks.core.subjectcode.usage.one', 'One of', to_date('2000-01-01', 'yyyy-mm-dd'), null, 'One', '2', 0)
/
--/
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSSC_SUBJ_CD';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSSC_SUBJ_CD CASCADE CONSTRAINTS PURGE'; END IF;
END;
/
CREATE
    TABLE KSSC_SUBJ_CD
    (
        ID VARCHAR2(255) NOT NULL,
        CREATEID VARCHAR2(255),
        CREATETIME TIMESTAMP(6),
        UPDATEID VARCHAR2(255),
        UPDATETIME TIMESTAMP(6),
        VER_NBR NUMBER(19) NOT NULL,
        NAME VARCHAR2(255),
        STATE VARCHAR2(255),
        CD VARCHAR2(255),
        TYPE VARCHAR2(255),
        OBJ_ID VARCHAR2(36),
        CONSTRAINT KSSC_SUBJ_CD_P1 PRIMARY KEY (ID),
        CONSTRAINT KSSC_SUBJ_CD_FK1 FOREIGN KEY (TYPE) REFERENCES KSSC_SUBJ_CD_TYPE (TYPE_KEY),
        CONSTRAINT KSSC_SUBJ_CD_IX1 UNIQUE (CD)
    )
/
--/
DECLARE temp NUMBER;
BEGIN
	SELECT COUNT(*) INTO temp FROM user_tables WHERE table_name = 'KSSC_SUBJ_CD_JN_ORG';
	IF temp > 0 THEN EXECUTE IMMEDIATE 'DROP TABLE KSSC_SUBJ_CD_JN_ORG CASCADE CONSTRAINTS PURGE'; END IF;
END;
/
CREATE
    TABLE KSSC_SUBJ_CD_JN_ORG
    (
        ID VARCHAR2(255) NOT NULL,
        ORG_ID VARCHAR2(255),
        SUBJ_CD_ID VARCHAR2(255),
        VER_NBR NUMBER(19) NOT NULL,
        EFF_DT TIMESTAMP(6),
        EXPIR_DT TIMESTAMP(6),
        OBJ_ID VARCHAR2(36),
        CONSTRAINT KSSC_SUBJ_CD_JN_ORG_P1 PRIMARY KEY (ID),
        CONSTRAINT KSSC_SUBJ_CD_JN_ORG_FK1 FOREIGN KEY (SUBJ_CD_ID) REFERENCES KSSC_SUBJ_CD (ID)
    )
/

insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('1', 'kr', to_date('2011-04-08','yyyy-mm-dd'), 'kr', to_date('2011-04-12','yyyy-mm-dd'), 2, 'Architecture', 'Actual', 'AACH', 'ks.core.subjectcode.usage.all', '3a512547-5442-4c3b-9965-6f3a0b60866c')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('2', 'kr', to_date('2011-04-09','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 3, 'African American Studies', 'Actual', 'AASP', 'ks.core.subjectcode.usage.all', '0677e556-fb00-4d0c-8798-1ac838f658e7')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('3', 'kr', to_date('2011-04-10','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 3, 'Asian American Studies', 'Actual', 'AAST', 'ks.core.subjectcode.usage.all', 'bde40bf9-0f10-44e1-9e88-5a3a450bc76a')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('4', 'kr', to_date('2011-04-11','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 3, 'Accounting', 'Actual', 'ACCT', 'ks.core.subjectcode.usage.all', 'db04c658-f506-4393-a6bc-c26a5e779ac1')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('5', 'kr', to_date('2011-04-12','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 3, 'Arts and Humanities', 'Actual', 'ARHU', 'ks.core.subjectcode.usage.all', 'fa824f46-d252-4afc-8294-bc2d3a581fa0')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('6', 'kr', to_date('2011-04-13','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 2, 'Art History', 'Actual', 'ARTH', 'ks.core.subjectcode.usage.all', '73c56e37-6273-4061-9069-668ffdb84908')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('7', 'kr', to_date('2011-04-14','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 2, 'FineArts', 'Actual', 'ARTS', 'ks.core.subjectcode.usage.all', '22a76253-78db-4194-ae82-35f17918cbd9')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('8', 'kr', to_date('2011-04-15','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 2, 'Biochemistry', 'Actual', 'BCHM', 'ks.core.subjectcode.usage.all', 'e831a7f4-1553-4c45-a942-fabed9873c31')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('9', 'kr', to_date('2011-04-16','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 3, 'Biomedical Engineering', 'Actual', 'BENG', 'ks.core.subjectcode.usage.all', 'b481b138-fb62-4eae-ab58-9a2d34a6b257')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('12', 'kr', to_date('2011-04-19','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 2, 'Biometrics', 'Actual', 'BIOM', 'ks.core.subjectcode.usage.all', 'b2ade3b1-ec73-4e55-8ec0-c8d1deb4030c')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('13', 'kr', to_date('2011-04-20','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 2, 'Business Management', 'Actual', 'BMGT', 'ks.core.subjectcode.usage.all', '5f613895-e03c-463a-8483-e22a2bd65f68')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('14', 'kr', to_date('2011-04-21','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 2, 'Botany', 'Actual', 'BOTA', 'ks.core.subjectcode.usage.all', '3b755af6-d9ea-40ed-9159-cf338d3531c0')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('15', 'kr', to_date('2011-04-22','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 2, 'Biological Sciences', 'Actual', 'BSCI', 'ks.core.subjectcode.usage.all', '23beb29b-7e73-44bd-ae5a-b2590cb2fb48')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('16', 'kr', to_date('2011-04-23','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 2, 'Criminal Justice', 'Actual', 'CCJS', 'ks.core.subjectcode.usage.all', '4dc5f871-dd0e-4b3f-9880-3bcc2c3eca4f')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('17', 'kr', to_date('2011-04-24','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 2, 'Chemical Engineering', 'Actual', 'CHEE', 'ks.core.subjectcode.usage.all', '26514b91-66b4-44f9-a20c-97d366cf8d59')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('18', 'kr', to_date('2011-04-25','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 2, 'Chemistry', 'Actual', 'CHEM', 'ks.core.subjectcode.usage.all', '04d1f81e-dcf3-4cc7-8384-2366f84a6684')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('20', 'kr', to_date('2011-04-27','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 2, 'Civil Engineering', 'Actual', 'CIVI', 'ks.core.subjectcode.usage.all', 'c1dfcfa7-0fc1-4662-82b4-5ba944415e3c')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('21', 'kr', to_date('2011-04-28','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 2, 'CompSci', 'Actual', 'CSCI', 'ks.core.subjectcode.usage.all', '1c461855-a6b6-4890-86d7-d41d0c5f7d37')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('22', 'kr', to_date('2011-04-29','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 2, 'Economics', 'Actual', 'ECON', 'ks.core.subjectcode.usage.all', '7344fae8-3767-4c8a-9c73-70f0838108b1')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('23', 'kr', to_date('2011-04-30','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 2, 'Education', 'Actual', 'EDUC', 'ks.core.subjectcode.usage.all', '0bc2b673-2361-4c53-a5c1-b0eb5fc13241')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('24', 'kr', to_date('2011-05-01','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 2, 'English', 'Actual', 'ENGL', 'ks.core.subjectcode.usage.all', '1eb81f63-4d49-474d-a9db-4a87c3cb7aa9')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('26', 'kr', to_date('2011-05-03','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 3, 'Finance', 'Actual', 'FINA', 'ks.core.subjectcode.usage.all', '66948f84-3a2a-4e37-a1c1-83d620b2fd12')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('27', 'kr', to_date('2011-05-04','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 2, 'French', 'Actual', 'FREN', 'ks.core.subjectcode.usage.all', '678daa79-049e-4344-9d05-67a5dcfc090e')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('28', 'kr', to_date('2011-05-05','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 2, 'Gemstone', 'Actual', 'GEMS', 'ks.core.subjectcode.usage.all', 'fe6b7d6a-90b6-4e4c-be83-c7ec69002e31')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('29', 'kr', to_date('2011-05-06','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 2, 'Geography', 'Actual', 'GEOG', 'ks.core.subjectcode.usage.all', '8cc2451a-01cd-4e6e-9383-5e8d902357c3')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('30', 'kr', to_date('2011-05-07','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 2, 'Geology', 'Actual', 'GEOL', 'ks.core.subjectcode.usage.all', 'b244d1bd-cb72-4a38-88dd-f79387eccaec')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('31', 'kr', to_date('2011-05-08','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 2, 'Government', 'Actual', 'GVPT', 'ks.core.subjectcode.usage.all', '5d35d8eb-2001-4de3-bd44-3b9d0faed3ee')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('32', 'kr', to_date('2011-05-09','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 2, 'History', 'Actual', 'HIST', 'ks.core.subjectcode.usage.all', '6920a32e-0391-49c8-8254-c91a2dd801b5')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('33', 'kr', to_date('2011-05-10','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 2, 'Honors', 'Actual', 'HONR', 'ks.core.subjectcode.usage.one', 'c81ccb41-ea37-4f98-b9dd-542d21f9004d')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('34', 'kr', to_date('2011-05-11','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 2, 'International Business', 'Actual', 'INTB', 'ks.core.subjectcode.usage.all', '1f985699-b0fd-4827-bb8e-9dc6ef735bbf')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('35', 'kr', to_date('2011-05-12','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 2, 'Linguistics', 'Actual', 'LING', 'ks.core.subjectcode.usage.all', '650c4e16-23a4-407a-88fc-e9bcff76512b')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('36', 'kr', to_date('2011-05-13','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 2, 'Marketing', 'Actual', 'MARK', 'ks.core.subjectcode.usage.all', 'd0a33485-7754-4923-bc44-56bc2cc7c3bb')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('37', 'kr', to_date('2011-05-14','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 2, 'Math', 'Actual', 'MATH', 'ks.core.subjectcode.usage.all', '58992156-f68f-4a51-9b5d-c78f42471914')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('38', 'kr', to_date('2011-05-15','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 2, 'Mechanical Engineering', 'Actual', 'MENG', 'ks.core.subjectcode.usage.all', '59062a42-072b-421c-b252-21e3256f286b')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('39', 'kr', to_date('2011-05-16','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 2, 'Music', 'Actual', 'MUSC', 'ks.core.subjectcode.usage.all', 'a0a073fc-15f4-43a0-a59f-090fba286877')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('40', 'kr', to_date('2011-05-17','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 2, 'Philosophy', 'Actual', 'PHIL', 'ks.core.subjectcode.usage.all', 'e28006c3-47f3-4f48-84a4-4b87efb26690')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('41', 'kr', to_date('2011-05-18','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 2, 'Physics', 'Actual', 'PHYS', 'ks.core.subjectcode.usage.all', '00707ba9-46d3-4baf-a38b-4b13e3e80f34')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('42', 'kr', to_date('2011-05-19','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 2, 'Political Science', 'Actual', 'POLI', 'ks.core.subjectcode.usage.all', '88ca03dd-d8f3-4f41-a8eb-edf8ce1ce797')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('43', 'kr', to_date('2011-05-20','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 3, 'Psychology', 'Actual', 'PSYC', 'ks.core.subjectcode.usage.all', '4dc34d04-25a3-451d-97db-242189c2b06a')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('44', 'kr', to_date('2011-05-21','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 2, 'Public Administration', 'Actual', 'PUAD', 'ks.core.subjectcode.usage.all', '1cd8c58b-b1a6-4e15-93e0-02e3f06c5b16')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('46', 'kr', to_date('2011-05-23','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 2, 'Robotics', 'Actual', 'ROBT', 'ks.core.subjectcode.usage.all', 'e0077878-6712-48d4-88b3-5b7487393dfc')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('49', 'kr', to_date('2011-05-26','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 2, 'Sociology', 'Actual', 'SOCY', 'ks.core.subjectcode.usage.all', 'b84474a4-787e-4bf8-a40f-380e296d44ae')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('50', 'kr', to_date('2011-05-27','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 2, 'Social Work', 'Actual', 'SOWK', 'ks.core.subjectcode.usage.all', '249bba4a-c5fb-4192-8688-04059af3724f')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('51', 'kr', to_date('2011-05-28','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 2, 'Statistics', 'Actual', 'STAT', 'ks.core.subjectcode.usage.all', 'efadc2f0-e793-4297-82b8-83e0e247b5ee')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('52', 'kr', to_date('2011-05-29','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 2, 'Theater', 'Actual', 'THET', 'ks.core.subjectcode.usage.all', 'fa78480f-ba62-451f-8d1f-fac23d712878')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('53', 'kr', to_date('2011-05-30','yyyy-mm-dd'), 'kr', to_date('2011-04-13','yyyy-mm-dd'), 2, 'University Studies', 'Actual', 'UNIV', 'ks.core.subjectcode.usage.one', '48cc8048-aa6b-4f40-8e95-d7940ef758a9')
/


insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('9a8535f5-4d43-462f-a1f0-e0d606a5e3c5', '212', '6', 1, to_date('2011-04-01','yyyy-mm-dd'), null, '14F49B4F-05E7-A621-12F0-CCADCA48DF56')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('36829e62-de8e-4e93-b924-76cbeb3bc68c', '29', '3', 1, to_date('2011-04-01','yyyy-mm-dd'), null, 'F681ECB1-3DF7-309E-DDF8-C43A364BB2F2')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('60527bcc-94fb-473c-915e-9926fc62ac29', '32', '4', 1, to_date('2011-04-01','yyyy-mm-dd'), null, 'F82F6574-DFCA-25AC-AD3E-3F27A294CF13')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('bc3441df-0147-428f-893c-e1b4b1dfc0e6', '28', '5', 1, to_date('2011-04-01','yyyy-mm-dd'), null, 'A8166CED-E801-3DA3-FBD2-EAE81DD3D321')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('01469b99-f0bd-4e66-a629-85e3708b82f1', '51', '7', 1, to_date('2011-04-01','yyyy-mm-dd'), null, '36696850-347A-7BAC-9A75-764F74DAF6B7')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('928907aa-5bab-448a-879c-b68ede08995f', '66', '8', 1, to_date('2011-04-01','yyyy-mm-dd'), null, '8A9E47EC-9AD5-701E-23AB-54DF4FEDF9AD')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('051dae68-dcf7-4746-8320-49c14a799fdb', '30', '9', 2, to_date('2011-04-01','yyyy-mm-dd'), null, '3624EA50-69C7-F4EF-F54A-625D07531E8C')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('56362051-0b10-403f-8a93-f2f36e531da9', '31', '9', 2, to_date('2011-04-01','yyyy-mm-dd'), null, 'F3115B89-031B-C898-791A-38E4EFDB0FE0')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('2b2cb1c3-4924-4782-a1fd-2e258848c6a0', '65', '12', 1, to_date('2011-04-01','yyyy-mm-dd'), null, '93D71318-F83C-CA25-CCA3-0F03E60C69D9')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('11c3ebfd-8c7f-47a3-8c2d-3f14966b28d5', '32', '13', 1, to_date('2011-04-01','yyyy-mm-dd'), null, 'B455E736-4E40-2574-D14C-4916B2C6249E')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('697fc335-17c9-478b-81a5-592f89647810', '67', '14', 1, to_date('2011-04-01','yyyy-mm-dd'), null, 'A1D55E4B-7ECA-105D-FA35-163ADE45847C')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('d70a4a51-2c94-4120-bfa8-10c7b65bfe41', '65', '15', 1, to_date('2011-04-01','yyyy-mm-dd'), null, '7B80AA87-3E24-AB34-E1C9-D8FA7B6AABBB')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('d6d7638e-7b24-406d-87c2-966b7234b99a', '214', '16', 1, to_date('2011-04-01','yyyy-mm-dd'), null, 'F64E9C75-AE9C-6DBC-0E31-20778EA29384')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('5a714c02-68fd-4270-81d8-92fa36ff398e', '61', '17', 1, to_date('2011-04-01','yyyy-mm-dd'), null, 'D4520F9E-6BA9-7EA1-D83C-CD8D8E5887FD')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('2b3157d3-b551-487f-bd68-dcf44ee75e85', '68', '18', 1, to_date('2011-04-01','yyyy-mm-dd'), null, 'D96B0072-99A3-52ED-061A-9F97FB48E34B')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('75247591-ae17-4428-9e32-a7d09102c9a4', '62', '20', 1, to_date('2011-04-01','yyyy-mm-dd'), null, '35572804-0F4C-020F-8953-45D094DC5CEA')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('9cf6a79b-8b48-4b33-8cf3-88a015087104', '64', '21', 1, to_date('2011-04-01','yyyy-mm-dd'), null, '0165FD6C-AB9B-8C8C-0DC1-75A7262A2FB8')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('4d5bf3f7-666d-4481-ab27-585b259f18fc', '216', '22', 1, to_date('2011-04-01','yyyy-mm-dd'), null, 'BA1EB8FB-5745-C68E-8C0F-B2E0B6DA93D3')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('69bcf404-05bd-475e-bce9-a86cda9019f8', '33', '23', 1, to_date('2011-04-01','yyyy-mm-dd'), null, '53DDDBDE-CC48-D0EC-8EF2-B54E47B78337')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('3074b9ba-362b-429e-8539-d4de2e138bce', '50', '24', 1, to_date('2011-04-01','yyyy-mm-dd'), null, '724B74A5-DF29-9BCB-0086-1F242762FEF3')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('1a4ad723-ed41-4a24-9ad3-658bdf3aaf8c', '52', '27', 1, to_date('2011-04-01','yyyy-mm-dd'), null, '7806B734-1457-7B71-DA70-91551D62B85D')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('dd55c556-8ddc-4b73-b299-dbb388634c53', '32', '26', 1, to_date('2011-04-01','yyyy-mm-dd'), null, 'C655A4F5-1673-B019-B397-2AB4EB6606BE')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('7414fa6c-3864-4957-a470-8df62eabda2a', '29', '28', 1, to_date('2011-04-01','yyyy-mm-dd'), null, 'BA01016B-9ADF-A878-6FF3-E1E4F8F601D5')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('95a88c5e-5f5f-4a5b-8815-a0f757844204', '53', '29', 1, to_date('2011-04-01','yyyy-mm-dd'), null, 'ABC514C6-11A8-A660-BA45-8D683C8DCB76')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('79a6e36c-744a-4f8c-9bf9-3597d75aef4a', '69', '30', 1, to_date('2011-04-01','yyyy-mm-dd'), null, 'F296A9DB-D440-BFEE-2370-38960DAAF0C6')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('17855886-6a66-414b-b86c-cbd80e81cd06', '57', '31', 1, to_date('2011-04-01','yyyy-mm-dd'), null, '3949E32D-9E49-46F9-41D4-DFE632D0C3D1')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('91f2cde2-f3f1-4d84-917c-e753c5e7f069', '56', '32', 1, to_date('2011-04-01','yyyy-mm-dd'), null, 'B8D3E4A4-2D76-0F8B-8E0F-88E0A1EF0472')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('4d568a5e-594c-439d-8956-1b748d748f9a', '28', '33', 1, to_date('2011-04-01','yyyy-mm-dd'), null, '4E2B1E19-E7E5-A1E3-C034-DB0FB2F77C47')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('47f1ea3a-5480-4120-bed0-3b9a5d5a7c50', '32', '34', 1, to_date('2011-04-01','yyyy-mm-dd'), null, 'AD29044B-415C-097C-BCE4-E119CD549C48')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('65194bb2-17bb-4020-b8d4-c2ab81936132', '54', '35', 1, to_date('2011-04-01','yyyy-mm-dd'), null, 'AB11F898-6418-09B4-BE42-EAEAF9F1FB0D')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('2d597495-a0f5-4cf5-b3d9-f53dc01b276a', '32', '36', 1, to_date('2011-04-01','yyyy-mm-dd'), null, '6081E9C5-6594-DA0E-4B9B-465BEE33B5CB')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('51374978-0197-4c74-b33b-18dd9cf14015', '204', '37', 1, to_date('2011-04-01','yyyy-mm-dd'), null, '8996EE67-8383-A139-1B92-0400B3A8D1A1')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('1443ee49-3c44-4dcb-b3d0-84abd1679953', '63', '38', 1, to_date('2011-04-01','yyyy-mm-dd'), null, '246B67BC-A51E-12AD-8458-05727176201C')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('efd4a37e-e272-49a9-bb0d-d7a5156e73ea', '55', '39', 1, to_date('2011-04-01','yyyy-mm-dd'), null, '46A52677-56F8-4BDE-4C76-23F1C23AA70E')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('ec32eb00-c96a-4dcd-a562-8f8a22d01dc2', '218', '40', 1, to_date('2011-04-01','yyyy-mm-dd'), null, 'DA2F64E4-1CC0-E5DD-1E84-68FD41E47A3B')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('41d14a19-bda8-407d-a570-7aa3a849c296', '206', '41', 1, to_date('2011-04-01','yyyy-mm-dd'), null, '504254BD-B228-57AF-74FF-BEA9B095A837')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('94587ab2-e6fa-489b-ad4b-41f8f166b7df', '57', '42', 1, to_date('2011-04-02','yyyy-mm-dd'), null, '399172E6-69C3-FC23-4BA7-9F217C96E9A7')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('8ebd865e-472c-41d7-a609-a2415e9c0d9b', '58', '43', 1, to_date('2011-04-01','yyyy-mm-dd'), null, '4EDFEAE2-87B0-4B0D-E03E-6802806FA05E')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('d71c46f1-d967-4cb7-9e17-5b11e770da0a', '57', '44', 1, to_date('2011-04-01','yyyy-mm-dd'), null, '2D3E9C28-78BA-E3F3-8E6C-78CD4125C836')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('178a65df-2c52-44d7-b9a9-64c582306320', '63', '46', 1, to_date('2011-04-01','yyyy-mm-dd'), null, '6A112554-AE41-570D-C615-F88803C3014F')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('d2d55163-9afd-4031-b066-3c9741e07f75', '59', '49', 1, to_date('2011-04-01','yyyy-mm-dd'), null, '8322F677-3F26-77C2-5169-C686D4A488A8')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('7c9e843a-c644-4159-aa55-a5177c30bcee', '59', '50', 1, to_date('2011-04-01','yyyy-mm-dd'), null, '316980A6-FD38-8A0D-6FF4-71273C2D94EE')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('a9a0b182-9e3c-4dc7-bda7-d67de2bd8cb2', '204', '51', 1, to_date('2011-04-01','yyyy-mm-dd'), null, '46241149-39E5-CA66-B214-F6CE6D3509CE')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('7d1ad24c-a26f-494a-adb2-d366ae103d63', '220', '52', 1, to_date('2011-04-01','yyyy-mm-dd'), null, '82BAD407-B084-3F3E-A86A-BBC65B4FA078')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('84bfe965-acca-4166-ba21-c5a0553e5127', '29', '53', 1, to_date('2011-04-01','yyyy-mm-dd'), null, '099050C3-B454-7574-FC82-1A2C751179A9')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('8fd25f86-5547-40de-be3d-6d9f7f22160e', '28', '53', 1, to_date('2011-04-01','yyyy-mm-dd'), null, '2285EBBF-0CE0-4D2B-F93F-6E7166835F93')
/
-- Subject Codes
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('54', 'kr', to_date('2011-04-13', 'yyyy-mm-dd'), 'kr', to_date('2011-04-13', 'yyyy-mm-dd'), 0, 'Animal Science', 'Actual', 'ANSC', 'ks.core.subjectcode.usage.all', 'cd66b3e4-e0a3-4f6c-9550-b87c15a8cd8a')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('55', 'kr', to_date('2011-04-13', 'yyyy-mm-dd'), 'kr', to_date('2011-04-13', 'yyyy-mm-dd'), 0, 'Behavior, Ecology, Evolution and Systematics', 'Actual', 'BEES', 'ks.core.subjectcode.usage.all', 'c650a9be-c55c-4012-833c-762d9e6cc5dd')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('56', 'kr', to_date('2011-04-13', 'yyyy-mm-dd'), 'kr', to_date('2011-04-13', 'yyyy-mm-dd'), 0, 'Bioengineering', 'Actual', 'BIOE', 'ks.core.subjectcode.usage.all', '9f049809-3b18-495f-973a-dc324e997d87')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('57', 'kr', to_date('2011-04-13', 'yyyy-mm-dd'), 'kr', to_date('2011-04-13', 'yyyy-mm-dd'), 0, 'Cell Biology & Molecular Genetics', 'Actual', 'CBMG', 'ks.core.subjectcode.usage.all', '39016f49-24d2-4989-8d9c-79cf5f52302d')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('58', 'kr', to_date('2011-04-13', 'yyyy-mm-dd'), 'kr', to_date('2011-04-13', 'yyyy-mm-dd'), 0, 'Entomology', 'Actual', 'ENTM', 'ks.core.subjectcode.usage.all', 'e261c824-69e8-4c3a-86bf-ab2d9955d89b')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('59', 'kr', to_date('2011-04-13', 'yyyy-mm-dd'), 'kr', to_date('2011-04-13', 'yyyy-mm-dd'), 0, 'Marine-Estuarine-Environmental Sciences', 'Actual', 'MEES', 'ks.core.subjectcode.usage.all', 'a9a901dc-bcda-4e07-bc7c-6ca5fef0d893')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('60', 'kr', to_date('2011-05-06', 'yyyy-mm-dd'), 'kr', to_date('2011-04-13', 'yyyy-mm-dd'), 0, 'Molecular and Cellular Biology', 'Actual', 'MOCB', 'ks.core.subjectcode.usage.all', '43786357-b35c-4df0-9958-ea33e1a79f93')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('61', 'kr', to_date('2011-04-13', 'yyyy-mm-dd'), 'kr', to_date('2011-04-13', 'yyyy-mm-dd'), 0, 'Music Education', 'Actual', 'MUED', 'ks.core.subjectcode.usage.all', '5ffbb7a2-8eee-4095-9a63-9b51fbe990e6')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('62', 'kr', to_date('2011-04-13', 'yyyy-mm-dd'), 'kr', to_date('2011-04-13', 'yyyy-mm-dd'), 0, 'Ethnomusicology', 'Actual', 'MUET', 'ks.core.subjectcode.usage.all', 'c37a0ec5-0a4c-42f1-bb38-2196bdfd2971')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('63', 'kr', to_date('2011-04-13', 'yyyy-mm-dd'), 'kr', to_date('2011-04-13', 'yyyy-mm-dd'), 0, 'Music Performance', 'Actual', 'MUSP', 'ks.core.subjectcode.usage.all', '2de2348d-364f-469a-90be-4b97e094f4d0')
/
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('64', 'kr', to_date('2011-04-13', 'yyyy-mm-dd'), 'kr', to_date('2011-04-13', 'yyyy-mm-dd'), 0, 'Neuroscience & Cognitive Science', 'Actual', 'NACS', 'ks.core.subjectcode.usage.one', '3aab2a00-664c-4556-8dba-c50c638a6f61')
/

--Subject Codes Join Orgs
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('0b89706b-652f-43bb-bba7-0c45d28012df', '201', '54', 0, to_date('2011-04-13', 'yyyy-mm-dd'), null, '69eadfbd-a53f-4a9e-b31c-595f59ca952c')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('f387067f-84d8-4fb2-a3e2-e5baa9cdce0d', '65', '55', 0, to_date('2011-04-13', 'yyyy-mm-dd'), null, '5e02573a-69fc-439c-88dc-0717965eee6a')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('2305554e-c5fd-4bd2-8550-045bc900967e', '224', '56', 0, to_date('2011-04-13', 'yyyy-mm-dd'), null, 'dbf1be3f-f314-41c3-bde3-0e4b5b5fd625')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('b1af6447-f432-4c11-b672-6e7d180221ab', '65', '57', 0, to_date('2011-04-13', 'yyyy-mm-dd'), null, '292eeb99-348f-4092-a9f9-15757a66ab3c')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('5810d120-3afe-48c3-8ea2-94795fb58f6d', '226', '58', 0, to_date('2011-04-13', 'yyyy-mm-dd'), null, 'c582202e-0b6b-4053-8478-f4bc79ddf623')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('1b7b48fa-6178-482c-9aa0-abcc7bf8a8bf', '65', '59', 0, to_date('2011-04-13', 'yyyy-mm-dd'), null, '5c4733c3-c54e-4d04-a991-3b58c80b059a')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('1fef30a6-63a3-4c0e-93d6-98855386b298', '65', '60', 0, to_date('2011-04-13', 'yyyy-mm-dd'), null, '3a8299bc-2875-4bf4-9e22-8fe31c7ff6ae')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('7ca4c949-d82b-433e-a43f-3157bd6f7ab6', '55', '61', 0, to_date('2011-04-13', 'yyyy-mm-dd'), null, 'ffc450a7-e3e2-4de4-a78e-34c13d7474b3')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('35ad514d-c286-43d8-b788-2544fdd5dc5e', '55', '62', 0, to_date('2011-04-13', 'yyyy-mm-dd'), null, '05aaf74d-8103-4cbb-972c-744a85e65b91')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('dacf28ee-82ce-4ce1-ad29-52cc5636a6a7', '55', '63', 0, to_date('2011-04-13', 'yyyy-mm-dd'), null, 'e8c4bdbf-c666-4e98-a465-62593881634e')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('5f013bf8-6bdf-4baf-8701-012cf4782509', '65', '64', 0, to_date('2011-04-13', 'yyyy-mm-dd'), null, '74eb929d-37d9-4327-afd6-418c2a006b43')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('56e35576-6c98-4374-bbd1-7ec5729dd08d', '58', '64', 0, to_date('2011-04-13', 'yyyy-mm-dd'), null, '4da22d23-ac8a-4d07-8ee2-31c55035cba3')
/
-- Add UNIV mapping to undergrad studies college
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('badcb3f5-2938-4015-9106-8ad618d8ee70', '223', '53', 0, to_date('2011-04-13', 'yyyy-mm-dd'), null, '110cc4c7-153f-41ed-adda-f86c3d3959f5')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('72aa6312-1714-49cc-9b55-d320e3dadfde', '210', '2', 0, to_date('2011-04-13', 'yyyy-mm-dd'), null, '6609A090-C099-4C88-2318-15469FCE5D72')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('5deb76ea-4435-4815-a418-c4b199ffc1e9', '223', '28', 0, to_date('2011-04-13', 'yyyy-mm-dd'), null, '18B5FF11-A6AA-9942-DE94-466B006B4B1A')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('fa54da63-3932-4b27-912f-0246cd6c8b91', '223', '33', 0, to_date('2011-04-13', 'yyyy-mm-dd'), null, 'BFB40B98-DA64-2A91-FD92-7EB09DD10838')
/
